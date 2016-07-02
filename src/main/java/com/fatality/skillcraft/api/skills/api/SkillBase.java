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

import com.fatality.skillcraft.api.skills.api.utils.ISkill;
import com.fatality.skillcraft.api.utils.GuiHelper;
import com.fatality.skillcraft.api.utils.GuiMenu;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.util.ResourceLocation;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class SkillBase implements ISkill {
	
	private ResourceLocation background = new ResourceLocation("skillcraft:textures/guis/skillbook_1.png");
	private String name;
	private int defaultLevel;
	private List<Level> skillLevels = new ArrayList<Level>();
	private int badgeColour = new Color(0, 0, 0).hashCode();
	
	public SkillBase(String name, int defaultLevel) {
		this.name = name;
		this.defaultLevel = defaultLevel;
	}
	
	@Override
	public String getSkillName() {
		return this.name;
	}
	
	@Override
	public int defaultLevel() {
		return this.defaultLevel;
	}
	
	@Override
	public int getBadgeColour() {
		return this.badgeColour;
	}
	
	public void setBadgeColour(int badgeColour) {
		this.badgeColour = badgeColour;
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
	
	@Override
	public List<Level> getSkillLevels() {
		return this.skillLevels;
	}
	
	public void addLevel(Level level) {
		this.skillLevels.add(level);
	}
	
	public void renderGUIBackground(Gui gui, float partialTicks, int mouseX, int mouseY, int guiLeft, int guiTop) {
		Minecraft.getMinecraft().getTextureManager().bindTexture(background);
		gui.drawTexturedModalRect(guiLeft, guiTop, 0, 0, 148, 189);
	}
	
	public void renderGUIForground(GuiContainer gui, float partialTicks, int mouseX, int mouseY, int guiLeft, int guiTop, int exp, int level, GuiMenu menu) {
		Minecraft.getMinecraft().getTextureManager().bindTexture(background);
		gui.drawTexturedModalRect(guiLeft + 10, guiTop + 23, 0, 189, 128, 5);
		gui.drawTexturedModalRect(guiLeft + 10, guiTop + 23, 0, 194, exp, 5);
		GuiHelper.addText(GuiHelper.getCenter(String.valueOf(level), guiLeft - 6, 160, 1F), guiTop + 30, String.valueOf(level), 1F, new Color(180, 170, 150).hashCode(),
				false);
		menu.update();
		menu.render(gui);
		if (menu.getSelected().length == 3) {
			GuiHelper.addText(GuiHelper.getCenter((String) menu.getSelected()[1], guiLeft+74, 73, 1.25F), guiTop + 40, (String) menu.getSelected()[1], 1F, new Color(180, 170, 150).hashCode(),
					false);
		}
	}
	
	public ResourceLocation getBackgroundTexture() {
		return this.background;
	}
	
}
