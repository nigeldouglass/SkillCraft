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

import com.fatality.skillcraft.api.skills.api.SkillBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;

public class experience {
	
	public static final int MAX_LEVEL = 55;
	
	public static int getRequireExp(int level) {
		double exp = 0.000000;
		
		for (int i = 1; i < level; i++) {
			exp += Math.floor((i + 300 * Math.pow(2.000, i / 7.000)) / 4);
		}
		return (int) exp;
	}
	
	public static void levelUp(EntityPlayer player, SkillBase skill, int level) {
		for (EntityPlayer p : player.getEntityWorld().playerEntities) {
			p.addChatMessage(new TextComponentString(String.format("%s%s%s has achieved level %s in %s%s", TextFormatting.GREEN, player.getName(), TextFormatting.WHITE, level, TextFormatting.RED, skill.getSkillName())));
		}
	}
	
	public static void levelDown(EntityPlayer player, SkillBase skill, int level) {
		for (EntityPlayer p : player.getEntityWorld().playerEntities) {
			p.addChatMessage(new TextComponentString(String.format("%s%s%s was deranked to level %s in %s%s", TextFormatting.GREEN, player.getName(), TextFormatting.WHITE, level, TextFormatting.RED, skill.getSkillName())));
		}
	}
	
}
