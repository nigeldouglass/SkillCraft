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

package com.fatality.skillcraft.common.blocks;

import com.fatality.skillcraft.api.blocks.BlockBase;
import com.fatality.skillcraft.utils.Register;
import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

public enum Blocks {
	;
	
	private final Class<? extends BlockBase> blockClass;
	private final Class<? extends ItemBlock> itemBlockClass;
	private Block block;
	
	Blocks(Class<? extends BlockBase> blockClass) {
		this(blockClass, ItemBlock.class);
	}
	
	Blocks(Class<? extends BlockBase> blockClass, Class<? extends ItemBlock> itemBlockClass) {
		this.blockClass = blockClass;
		this.itemBlockClass = itemBlockClass;
	}
	
	public static void registerBlocks() {
		for (Blocks block : Blocks.values()) {
			block.register();
		}
	}
	
	public ItemStack getStack() {
		return new ItemStack(block);
	}
	
	public ItemStack getStack(int size) {
		return new ItemStack(block, size);
	}
	
	public ItemStack getStack(int size, int meta) {
		return new ItemStack(block, size, meta);
	}
	
	public Block getBlock() {
		return this.block;
	}
	
	private void register() {
		block = Register.registerBlock(blockClass, itemBlockClass);
	}
}
