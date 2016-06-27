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

package com.fatality.skillcraft.common.skills;

import com.fatality.skillcraft.api.skills.api.ISkill;
import com.fatality.skillcraft.common.skills.events.EventAgriculture;
import net.minecraft.util.ResourceLocation;

import java.awt.*;

public class SkillAgriculture implements ISkill {
	@Override
	public Object getEventClass() {
		return new EventAgriculture();
	}
	
	@Override
	public String getSkillName() {
		return "Agriculture";
	}
	
	@Override
	public int defaultLevel() {
		return 0;
	}
	
	@Override
	public int getBadgeColour() {
		return new Color(16, 63, 12).hashCode();
		/*
		MINING("Mining", 1, new Color(130, 130, 130).hashCode()),
		FISHING("Fishing", 2, new Color(92, 183, 255).hashCode()),
		FORESTRY("Forestry", 3, new Color(60, 125, 5).hashCode()),
		COOKING("Cooking", 4, new Color(160, 100, 40).hashCode()),
		 */
	}
	
	@Override
	public ResourceLocation getResourceLocation() {
		return null;
	}
	
	@Override
	public int getIconX() {
		return 0;
	}
	
	@Override
	public int getIconY() {
		return 0;
	}
}
