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

package com.fatality.skillcraft.api.skills;

import com.fatality.skillcraft.api.skills.api.ISkill;
import com.fatality.skillcraft.api.skills.api.ISkillRegistry;

import java.util.ArrayList;
import java.util.List;

public class SkillRegistry implements ISkillRegistry<ISkill> {
	
	private static final SkillRegistry instance = new SkillRegistry();
	private List<ISkill> registry = new ArrayList<ISkill>();
	
	public static SkillRegistry instance() {
		return instance;
	}
	
	@Override
	public ISkill registerSkill(Class<? extends ISkill> skillClass) {
		try {
			ISkill skill = skillClass.getConstructor().newInstance();
			if (skill == null)
				throw new RuntimeException(String.format("Cannot register a null skill to Skill Registry caused by %s", skill.getClass()));
			if (skill.getSkillName().isEmpty())
				throw new RuntimeException(String.format("Cannot register a skill with no name to Skill Registry caused by %s", skill.getClass()));
			
			registry.add(skill);
			return skill;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@Override
	public List<ISkill> getRegisteredSkills() {
		return registry;
	}
}
