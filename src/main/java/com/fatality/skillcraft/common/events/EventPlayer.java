/**
 * Created by Fatality
 */

/* You are free to:
 * 
 * Share — copy and redistribute the material in any medium or format
 * Adapt — remix, transform, and build upon the material
 * for any purpose, even commercially.
 * The licensor cannot revoke these freedoms as long as you follow the license terms.
 * Under the following terms:
 * Attribution — You must give appropriate credit, provide a link to the license, and indicate if changes were made. You may do so in any reasonable manner, but not in any way that suggests the licensor endorses you or your use.
 * ShareAlike — If you remix, transform, or build upon the material, you must distribute your contributions under the same license as the original.
 * No additional restrictions — You may not apply legal terms or technological measures that legally restrict others from doing anything the license permits.
 * Notices:
 * 
 * You do not have to comply with the license for elements of the material in the public domain or where your use is permitted by an applicable exception or limitation.
 * No warranties are given. The license may not give you all of the permissions necessary for your intended use. For example, other rights such as publicity, privacy, or moral rights may limit how you use the material.
 */

package com.fatality.skillcraft.common.events;

import com.fatality.skillcraft.api.skills.SkillRegistry;
import com.fatality.skillcraft.api.skills.api.SkillBase;
import com.fatality.skillcraft.api.skills.api.SkillUpdate;
import com.fatality.skillcraft.api.utils.experience;
import com.fatality.skillcraft.common.items.Items;
import com.fatality.skillcraft.common.skills.data.PlayerSkill;
import com.fatality.skillcraft.common.skills.data.SkillCapability;
import com.fatality.skillcraft.common.skills.data.SkillProvider;
import com.fatality.skillcraft.utils.ModInfo;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.PlayerTickEvent;

public class EventPlayer {
	
	@SubscribeEvent
	public void firstJoin(EntityJoinWorldEvent event) {
		if (event.getEntity() instanceof EntityPlayer) {
			EntityPlayer player = (EntityPlayer) event.getEntity();
			NBTTagCompound entityData = player.getEntityData();
			if (!entityData.getBoolean("joinedBefore")) {
				entityData.setBoolean("joinedBefore", true);
				player.inventory.addItemStackToInventory(new ItemStack(Items.ITEM_SKILL_BOOK.getItem()));
			}
			
			if (event.getEntity() instanceof EntityPlayer && !player.worldObj.isRemote) {
				for (SkillBase skills : SkillRegistry.instance().getRegisteredSkills()) {
					SkillProvider.get((EntityPlayer) event.getEntity()).addSkill(new PlayerSkill(skills.getSkillName(), skills.defaultLevel(), 0));
				}
			}
		}
	}
	
	@SubscribeEvent
	public void AttachCapability(AttachCapabilitiesEvent.Entity event) {
		if (!event.getEntity().hasCapability(SkillProvider.SKILLS, null) && event.getEntity() instanceof EntityPlayer) {
			event.addCapability(new ResourceLocation(ModInfo.MOD_NAME, "skills"), new SkillProvider(new SkillCapability()));
		}
	}
	
	@SubscribeEvent
	public void onClonePlayer(PlayerEvent.Clone event) {
		if (event.isWasDeath()) {
			SkillProvider.get(event.getEntityPlayer()).loadNBTData(SkillProvider.get(event.getOriginal()).saveNBTData());
			SkillProvider.get(event.getEntityPlayer()).dataChange(event.getEntityPlayer());
		}
	}
	
	@SubscribeEvent
	public void update(PlayerTickEvent event) {
		World world = event.player.worldObj;
		
		if (!world.isRemote && !SkillUpdate.instance().updateTick().isEmpty()) {
			
			for (SkillUpdate.Update updates : SkillUpdate.instance().updateTick()) {
				PlayerSkill current = SkillProvider.get(updates.getPlayer()).getSkill(updates.getSkill().getSkillName());
				int currentExp = current.getExp();
				int newExp = currentExp + updates.getExp();
				int level = current.getLevel();
				
				for (int i = level; i <= experience.MAX_LEVEL; i++) {
					int nextLevelReq = experience.getRequireExp(i + 1);
					if (currentExp < nextLevelReq && newExp >= nextLevelReq) {
						level = i + 1;
					} else {
						break;
					}
				}
				if(level>current.getLevel())
					experience.levelUp(updates.getPlayer(), updates.getSkill(), level);
				
				if(level<current.getLevel())
					experience.levelDown(updates.getPlayer(), updates.getSkill(), level);
				
				SkillProvider.get(updates.getPlayer()).updateSkill(updates.getPlayer(), current.getName(), level, newExp);
			}
			
			SkillUpdate.instance().clear();
		}
	}
}
