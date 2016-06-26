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

package com.fatality.skillcraft;

import com.fatality.skillcraft.proxy.IProxy;
import com.fatality.skillcraft.utils.ModInfo;
import net.minecraft.init.Items;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;

import java.util.List;

@Mod(modid = ModInfo.MOD_ID, name = ModInfo.MOD_NAME, version = ModInfo.VERSION)
public class SkillCraft {
	
	@Mod.Instance(ModInfo.MOD_ID)
	public static SkillCraft instance;
	
	@SidedProxy(clientSide = ModInfo.CLIENT_PROXY_CLASS, serverSide = ModInfo.SERVER_PROXY_CLASS)
	public static IProxy proxy;
	
	public static SimpleNetworkWrapper channel;
	
	@Mod.EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		proxy.registerBlocks();
		proxy.registerItems();
		proxy.registerRecipes();
		proxy.registerOreDict();
		proxy.registerWorldGen();
		proxy.registerGUIs();
		proxy.registerRenderers();
	}
	
	@Mod.EventHandler
	public void init(FMLInitializationEvent event) {
		proxy.registerEvents();
	}
	
	@Mod.EventHandler
	public void postInit(FMLPostInitializationEvent event) {
	}

}
