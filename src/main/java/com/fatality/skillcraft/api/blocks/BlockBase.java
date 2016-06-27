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

package com.fatality.skillcraft.api.blocks;

import com.fatality.skillcraft.utils.ModInfo;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;


public abstract class BlockBase extends Block {
	
	protected String resourcePath = "";
	protected String groupName = "";
	protected String unlocalizedName = "";
	
	public BlockBase(Material material, String unlocalName, String resourcePath) {
		super(material);
		setHardness(2.0F);
		setResistance(5.0F);
		this.groupName = resourcePath;
		this.unlocalizedName = unlocalName;
		this.resourcePath = String.format("%s/%s", resourcePath, unlocalName);
	}
	
	public String getName() {
		return unlocalizedName;
	}
	
	public String getResourcePath() {
		return resourcePath;
	}
	
	@Override
	public String getUnlocalizedName() {
		String unlocalizedName = super.getUnlocalizedName();
		unlocalizedName = unlocalizedName.substring(unlocalizedName.indexOf(".") + 1);
		
		return String.format("tile.%s.%s.%s", ModInfo.MOD_ID, groupName, unlocalizedName);
	}
}
