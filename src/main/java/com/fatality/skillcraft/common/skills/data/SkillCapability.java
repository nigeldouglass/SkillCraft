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

package com.fatality.skillcraft.common.skills.data;

import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.fml.common.FMLCommonHandler;

import java.util.ArrayList;
import java.util.List;

public class SkillCapability implements ISkillCapability {
	
	List<PlayerSkill> skills = new ArrayList<PlayerSkill>();
	
	public PlayerSkill getSkill(String name) {
		
		for (PlayerSkill skill : skills) {
			if (skill.getName().equalsIgnoreCase(name))
				return skill;
		}
		
		return null;
	}
	
	public List<PlayerSkill> getAllSkills() {
		return skills;
	}
	
	public void updateSkill(String name, int level, int exp) {
		for (PlayerSkill skill : skills) {
			if (skill.getName().equalsIgnoreCase(name)) {
				skill.setLevel(level);
				skill.setExp(exp);
			}
		}
	}
	
	public void addSkill(PlayerSkill level) {
		boolean match = false;
		
		for (int i = 0; i < skills.size(); i++) {
			if (skills.get(i).getName().equalsIgnoreCase(level.getName())) {
				match = true;
			}
		}
		
		if (!match || skills.isEmpty()) {
			skills.add(level);
		}
	}
	
	@Override
	public NBTTagCompound saveNBTData() {
		return (NBTTagCompound) SkillsCapabilityHandler.handler.writeNBT(SkillProvider.SKILLS, this, null);
	}
	
	@Override
	public void loadNBTData(NBTTagCompound nbt) {
		SkillsCapabilityHandler.handler.readNBT(SkillProvider.SKILLS, this, null, nbt);
	}
	
	public static class SkillsCapabilityHandler implements Capability.IStorage<ISkillCapability> {
		
		public static final SkillsCapabilityHandler handler = new SkillsCapabilityHandler();
		
		@Override
		public NBTBase writeNBT(Capability<ISkillCapability> capability, ISkillCapability instance, EnumFacing side) {
			
			NBTTagCompound tag = new NBTTagCompound();
			NBTTagList list = new NBTTagList();
			
			for (PlayerSkill skill : instance.getAllSkills()) {
				NBTTagCompound skillNBT = new NBTTagCompound();
				
				skillNBT.setString("name", skill.getName());
				skillNBT.setInteger("level", skill.getLevel());
				skillNBT.setInteger("exp", skill.getExp());
				
				list.appendTag(skillNBT);
			}
			
			tag.setTag("list", list);
			
			System.out.println(tag);
			return tag;
		}
		
		@Override
		public void readNBT(Capability<ISkillCapability> capability, ISkillCapability instance, EnumFacing side, NBTBase nbt) {
			NBTTagCompound nbtData = (NBTTagCompound) nbt;
			NBTTagList list = nbtData.getTagList("list", 10);
			
			for (int i = 0; i < list.tagCount(); i++) {
				NBTTagCompound skillNBT = (NBTTagCompound) list.get(i);
				
				String name = skillNBT.getString("name");
				int level = skillNBT.getInteger("level");
				int exp = skillNBT.getInteger("exp");
				
				instance.addSkill(new PlayerSkill(name, level, exp));
			}
		}
	}
}
