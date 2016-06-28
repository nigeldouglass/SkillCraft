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

package com.fatality.skillcraft.api.skills.api;

import com.fatality.skillcraft.api.skills.api.utils.ISkillUpdate;
import net.minecraft.entity.player.EntityPlayer;

import java.util.ArrayList;
import java.util.List;

public class SkillUpdate implements ISkillUpdate {
	
	private static final SkillUpdate instance = new SkillUpdate();
	private List<Update> updates = new ArrayList<Update>();
	
	public static SkillUpdate instance() {
		return instance;
	}
	
	@Override
	public void updateExp(EntityPlayer player, SkillBase skill, int exp) {
		updates.add(new Update(player, skill, exp));
	}
	
	public List<Update> updateTick() {
		return updates;
	}
	
	public void clear() {
		updates.clear();
	}
	
	public class Update {
		EntityPlayer player;
		SkillBase skill;
		int exp;
		
		private Update(EntityPlayer player, SkillBase skill, int exp) {
			this.player = player;
			this.skill = skill;
			this.exp = exp;
		}
		
		public EntityPlayer getPlayer() {
			return this.player;
		}
		
		public SkillBase getSkill() {
			return this.skill;
		}
		
		public int getExp() {
			return this.exp;
		}
	}
	
}
