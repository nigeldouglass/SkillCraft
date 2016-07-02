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

package com.fatality.skillcraft.proxy;

import com.fatality.skillcraft.SkillCraft;
import com.fatality.skillcraft.api.recipes.IRecipes;
import com.fatality.skillcraft.api.skills.SkillRegistry;
import com.fatality.skillcraft.api.skills.SkillsAPI;
import com.fatality.skillcraft.api.skills.api.SkillBase;
import com.fatality.skillcraft.api.skills.api.events.IHaveEvent;
import com.fatality.skillcraft.common.blocks.Blocks;
import com.fatality.skillcraft.common.events.EventPlayer;
import com.fatality.skillcraft.common.items.Items;
import com.fatality.skillcraft.common.messages.MessageUpdateAllCaps;
import com.fatality.skillcraft.common.skills.Skills;
import com.fatality.skillcraft.common.skills.data.ISkillCapability;
import com.fatality.skillcraft.common.skills.data.SkillCapability;
import com.fatality.skillcraft.utils.GuiHandler;
import com.fatality.skillcraft.utils.ModInfo;
import com.fatality.skillcraft.utils.api.SkillsAPIImpl;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;

public class CommonProxy implements IProxy {
	
	public static SimpleNetworkWrapper network;
	
	@Override
	public void initialiseAPI() {
		SkillsAPI.setup(new SkillsAPIImpl());
	}
	
	@Override
	public void registerSkills() {
		Skills.registerSkills();
	}
	
	@Override
	public void registerBlocks() {
		Blocks.registerBlocks();
	}
	
	@Override
	public void registerItems() {
		Items.registerItems();
	}
	
	@Override
	public void registerRecipes() {
		for (Items item : Items.values()) {
			if (item.getItem() instanceof IRecipes)
				((IRecipes) item.getItem()).RegisterRecipes();
		}
		
		for (Blocks block : Blocks.values()) {
			if (block.getBlock() instanceof IRecipes)
				((IRecipes) block.getBlock()).RegisterRecipes();
			
		}
	}
	
	@Override
	public void registerOreDict() {
		
	}
	
	@Override
	public void registerWorldGen() {
		
	}
	
	@Override
	public void registerGUIs() {
		NetworkRegistry.INSTANCE.registerGuiHandler(SkillCraft.instance, new GuiHandler());
	}
	
	@Override
	public void registerRenderers() {
		
	}
	
	@Override
	public void registerEvents() {
		MinecraftForge.EVENT_BUS.register(new EventPlayer());
		for (SkillBase skill : SkillRegistry.instance().getRegisteredSkills()) {
			if (skill instanceof IHaveEvent)
				if (((IHaveEvent) skill).getEventClass() != null) {
					MinecraftForge.EVENT_BUS.register(((IHaveEvent) skill).getEventClass());
				}
		}
	}
	
	@Override
	public void registerMessages() {
		int messageCounter = 0;
		network = NetworkRegistry.INSTANCE.newSimpleChannel(ModInfo.MESSENGER);
		
		network.registerMessage(MessageUpdateAllCaps.UpdateAllCapsHandler.class, MessageUpdateAllCaps.class, messageCounter++, Side.CLIENT);
	}
	
	@Override
	public void registerCapabilities() {
		CapabilityManager.INSTANCE.register(ISkillCapability.class, SkillCapability.SkillsCapabilityHandler.handler, SkillCapability.class);
	}
}