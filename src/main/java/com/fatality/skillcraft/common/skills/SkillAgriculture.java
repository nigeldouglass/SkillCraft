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

import com.fatality.skillcraft.api.skills.api.Level;
import com.fatality.skillcraft.api.skills.api.SkillBase;
import com.fatality.skillcraft.api.skills.api.events.IHandlePlace;
import com.fatality.skillcraft.api.skills.api.events.IHaveEvent;
import com.fatality.skillcraft.common.skills.events.EventAgriculture;
import com.fatality.skillcraft.utils.ModInfo;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.init.Blocks;
import net.minecraft.util.ResourceLocation;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class SkillAgriculture extends SkillBase implements IHaveEvent, IHandlePlace {
	
	public SkillAgriculture() {
		super("Agriculture", 1);
		addLevel(new Level(1, "New World", "A whole new world."));
		addLevel(new Level(3, "Every Grower Helps", "Growing the world."));
		addLevel(new Level(7, "Plantation", "We are ready for harvest."));
		addLevel(new Level(13, "Expansion", "Trials are coming."));
		setBadgeColour(new Color(16, 63, 12).hashCode());
	}
	
	@Override
	public Map<Block, Integer> getBlocksPlaced() {
		Map<Block, Integer> blocks = new HashMap<Block, Integer>();
		blocks.put(Blocks.CACTUS, 5);
		return blocks;
	}
	
	@Override
	public Object getEventClass() {
		return new EventAgriculture(this);
	}
	
	@Override
	public void renderGUIBackground(Gui gui, float partialTicks, int mouseX, int mouseY, int guiLeft, int guiTop) {
		super.renderGUIBackground(gui, partialTicks, mouseX, mouseY, guiLeft, guiTop);
		ResourceLocation background = new ResourceLocation(ModInfo.MOD_ID + ":textures/gui/skillbook.png");
		Minecraft.getMinecraft().getTextureManager().bindTexture(background);
		gui.drawTexturedModalRect(guiLeft+3, guiTop + 164, 218, 105, 30, 22);
	}
}
