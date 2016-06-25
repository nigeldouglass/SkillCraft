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

package com.fatality.skillcraft.common.items.books;

import com.fatality.skillcraft.SkillCraft;
import com.fatality.skillcraft.api.items.ItemBase;
import com.fatality.skillcraft.api.recipes.IRecipes;
import com.fatality.skillcraft.common.items.Items;
import com.fatality.skillcraft.utils.GuiHandler;
import com.fatality.skillcraft.utils.SkillCraftCreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.ShapedOreRecipe;

public class ItemBook extends ItemBase implements IRecipes{
	public ItemBook() {
		super("skillbook", "books");
		this.setCreativeTab(SkillCraftCreativeTabs.GENERAL);
	}
	
	@Override
	public void RegisterRecipes() {
		GameRegistry.addRecipe(new ShapedOreRecipe(Items.ITEM_SKILL_BOOK.getStack(1),
				"SPP",
				"SDP",
				"SPP",
				'S', "stickWood",
				'P', "paper",
				'D', "dirt"
		));
	}
	
	@Override
	public String getUnlocalizedName(ItemStack stack) {
		return super.getUnlocalizedName();
	}
	
	@Override
	public ActionResult<ItemStack> onItemRightClick(ItemStack itemStackIn, World worldIn, EntityPlayer playerIn,
	                                                EnumHand hand) {
		BlockPos pos = playerIn.getPosition();
		playerIn.openGui(SkillCraft.instance, GuiHandler.GUI_SKILL_BOOK, worldIn, pos.getX(), pos.getY(), pos.getZ());
		return new ActionResult<ItemStack>(EnumActionResult.PASS, itemStackIn);
	}
}
