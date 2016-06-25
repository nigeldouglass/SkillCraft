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

package com.fatality.skillcraft.utils;

import com.fatality.skillcraft.api.blocks.BlockBase;
import com.fatality.skillcraft.api.items.ItemBase;

import net.minecraft.block.Block;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.block.statemap.DefaultStateMapper;

import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Register {
	
	public static Block registerBlock(Class<? extends Block> blockClass, Class<? extends ItemBlock> itemClass) {
		try {
			Block block = blockClass.getConstructor().newInstance();
			ItemBlock itemBlock = itemClass.getConstructor(Block.class).newInstance(block);
			String name = ((BlockBase) block).getName();
			
			block.setRegistryName(ModInfo.MOD_ID, name);
			block.setUnlocalizedName(name);
			itemBlock.setRegistryName(block.getRegistryName());
			
			GameRegistry.register(block);
			GameRegistry.register(itemBlock);
			
			if (FMLCommonHandler.instance().getSide().isClient()) {
				registerBlockRender((BlockBase) block);
			}
			
			return block;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static Item registerItem(Class<? extends Item> itemClass) {
		try {
			Item item = itemClass.getConstructor().newInstance();
			String name = ((ItemBase) item).getName();
			
			item.setRegistryName(ModInfo.MOD_ID, name);
			item.setUnlocalizedName(name);
			
			GameRegistry.register(item);
			
			if (FMLCommonHandler.instance().getSide().isClient()) {
				registerItemRenderer((ItemBase) item);
			}
			
			return item;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@SideOnly(Side.CLIENT)
	public static void registerBlockRender(BlockBase block) {
		final String resourcePath = String.format("%s:%s", ModInfo.MOD_ID, block.getResourcePath());
		
		ModelLoader.setCustomStateMapper(block, new DefaultStateMapper() {
			@Override
			protected ModelResourceLocation getModelResourceLocation(IBlockState state) {
				return new ModelResourceLocation(resourcePath, getPropertyString(state.getProperties()));
			}
		});
		
		List<ItemStack> subBlocks = new ArrayList<ItemStack>();
		block.getSubBlocks(Item.getItemFromBlock(block), null, subBlocks);
		
		for (ItemStack itemStack : subBlocks) {
			IBlockState blockState = block.getStateFromMeta(itemStack.getItemDamage());
			ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(block), itemStack.getItemDamage(), new ModelResourceLocation(resourcePath, getKeys(blockState.getProperties())));
		}
	}
	
	@SideOnly(Side.CLIENT)
	public static void registerItemRenderer(ItemBase item) {
		List<ItemStack> subBlocks = new ArrayList<ItemStack>();
		item.getSubItems(item, null, subBlocks);
		int i = 0;
		for (ItemStack itemStack : subBlocks) {
			String addon = "";
			if (subBlocks.size() > 1) {
				String split = "[.]";
				String[] pathBroken = subBlocks.get(i).getUnlocalizedName().split(split);
				addon = "_" + pathBroken[pathBroken.length - 1];
			}
			final String resourcePath = String.format("%s:%s%s", ModInfo.MOD_ID, item.getResourcePath(), addon);
			ModelLoader.setCustomModelResourceLocation(item, itemStack.getItemDamage(), new ModelResourceLocation(resourcePath, "inventory"));
			i++;
		}
	}
	
	public static String getKeys(Map<IProperty<?>, Comparable<?>> values) {
		String propertyString = "";
		for (Map.Entry<IProperty<?>, Comparable<?>> json : values.entrySet()) {
			IProperty<?> key = (IProperty) json.getKey();
			propertyString = String.format("%s=%s", key.getName(), getValue(key, (Comparable) json.getValue()));
		}
		return propertyString;
	}
	
	private static <T extends Comparable<T>> String getValue(IProperty<T> variants, Comparable<?> value) {
		return variants.getName((T) value);
	}
	
}