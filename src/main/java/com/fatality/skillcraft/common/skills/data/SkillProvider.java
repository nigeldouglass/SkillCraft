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

package com.fatality.skillcraft.common.skills.data;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.INBTSerializable;

import javax.annotation.Nullable;


public class SkillProvider implements ICapabilityProvider, INBTSerializable<NBTTagCompound> {
	
	@CapabilityInject(ISkillCapability.class)
	public static Capability<ISkillCapability> SKILLS = null;
	
	private ISkillCapability inst = null;
	
	public SkillProvider() {
		this.inst = new SkillCapability();
	}
	
	public SkillProvider(ISkillCapability cap) {
		this.inst = cap;
	}
	
	public static ISkillCapability get(EntityPlayer player) {
		return player.hasCapability(SKILLS, null) ? player.getCapability(SKILLS, null) : null;
	}
	
	@Override
	public NBTTagCompound serializeNBT() {
		return inst.saveNBTData();
	}
	
	@Override
	public void deserializeNBT(NBTTagCompound nbt) {
		inst.loadNBTData(nbt);
	}
	
	@Override
	public boolean hasCapability(Capability<?> capability, @Nullable EnumFacing facing) {
		return SKILLS != null && capability == SKILLS;
	}
	
	@Override
	public <T> T getCapability(Capability<T> capability, @Nullable EnumFacing facing) {
		if (SKILLS != null && capability == SKILLS) return (T) inst;
		return null;
	}
}
