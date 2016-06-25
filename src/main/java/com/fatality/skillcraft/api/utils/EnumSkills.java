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

package com.fatality.skillcraft.api.utils;

import net.minecraft.util.IStringSerializable;

import java.awt.*;
import java.util.Arrays;

public enum EnumSkills implements IStringSerializable {
	AGRICULTURE("Agriculture", 0, new Color(16, 63, 12).hashCode()),
	MINING("Mining", 1, new Color(130, 130, 130).hashCode()),
	FISHING("Fishing", 2, new Color(92, 183, 255).hashCode()),
	FORESTRY("Forestry", 3, new Color(60, 125, 5).hashCode()),
	COOKING("Cooking", 4, new Color(160, 100, 40).hashCode()),
	;
	
	private static final EnumSkills[] META = new EnumSkills[values().length];
	
	static {
		for (EnumSkills ore : values()) {
			if (META[ore.getMeta()] != null) {
				System.out.println(ore.getName() + " meta cannot be the same as another ore type.");
			} else {
				META[ore.getMeta()] = ore;
			}
		}
	}
	
	private final String name;
	private final int meta;
	private final int colour;
	
	EnumSkills(String name, int meta, int colour) {
		this.name = name;
		this.meta = meta;
		this.colour = colour;
	}
	
	public static EnumSkills byMeta(int meta) {
		if (meta < 0 || meta >= META.length) {
			meta = 0;
		}
		
		return META[meta];
	}
	
	public int getMeta() {
		return this.meta;
	}
	
	public int getColour() {
		return this.colour;
	}
	
	public String getUnlocalizedName() {
		return this.name.toLowerCase().replaceAll(" ", "_").toLowerCase();
	}
	
	public String getName() {
		return this.name.toLowerCase();
	}
	
	public String getNameUpper() {
		return this.name;
	}
}