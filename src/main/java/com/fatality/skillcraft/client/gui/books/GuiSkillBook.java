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

package com.fatality.skillcraft.client.gui.books;

import com.fatality.skillcraft.api.skills.SkillRegistry;
import com.fatality.skillcraft.api.skills.api.Level;
import com.fatality.skillcraft.api.skills.api.SkillBase;
import com.fatality.skillcraft.api.utils.GuiHelper;
import com.fatality.skillcraft.api.utils.GuiMenu;
import com.fatality.skillcraft.api.utils.experience;
import com.fatality.skillcraft.common.skills.data.PlayerSkill;
import com.fatality.skillcraft.common.skills.data.SkillProvider;
import com.fatality.skillcraft.utils.INode;
import com.fatality.skillcraft.utils.ModInfo;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.util.ResourceLocation;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GuiSkillBook extends GuiContainer {
	
	public final ResourceLocation background = new ResourceLocation(ModInfo.MOD_ID + ":textures/guis/skillbook.png");
	public final ResourceLocation backgroundPage = new ResourceLocation(ModInfo.MOD_ID + ":textures/guis/skillbook_1.png");
	int per = 0;
	private EntityPlayer player;
	private boolean showLevel = false;
	private int selectedSkill = -1;
	
	private List<INode> skillNodes = new ArrayList<INode>();
	
	private GuiMenu menu;
	private boolean updateMenu = false;
	
	public GuiSkillBook(Container inventorySlotsIn, EntityPlayer player) {
		super(inventorySlotsIn);
		this.player = player;
	}
	
	public void initGui() {
		super.initGui();
		this.buttonList.clear();
		skillNodes.clear();
		
		int col = 0;
		int row = 0;
		for (int i = 0; i < SkillRegistry.instance().getRegisteredSkills().size(); i++) {
			SkillBase skill = SkillRegistry.instance().getRegisteredSkills().get(i);
			if (row == 2) {
				row = 0;
				col += 1;
			}
			skillNodes.add(new INode(guiLeft + 17 + (col * 83), guiTop + (row * 70), "apple", 1.15F, 4, 4, skill.getSkillName(), skill.getBadgeColour()));
			row += 1;
		}
		if (updateMenu && selectedSkill != -1) {
			SkillBase skill = SkillRegistry.instance().getRegisteredSkills().get(selectedSkill);
			updateMenu = true;
			menu = new GuiMenu(guiLeft + 16, guiTop + 20, 39, 111, 1F, new Color(200, 190, 170).hashCode());
			buttonList.add(menu.addButton(0));
			for (Level lvl : skill.getSkillLevels()) {
				menu.addComponent(new Object[]{"lvl: " + lvl.getLevel(), lvl.getTitle(), lvl.getDescription()});
			}
		}
		
	}
	
	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
		Minecraft.getMinecraft().getTextureManager().bindTexture(background);
		this.drawTexturedModalRect(guiLeft, guiTop - 30, 0, 0, 160, 203);
		selectedSkill = 0;
		if (selectedSkill == -1) {
			
			Minecraft.getMinecraft().getTextureManager().bindTexture(backgroundPage);
			this.drawTexturedModalRect(guiLeft + 6, guiTop - 23, 0, 0, 160, 203);
			
			GuiHelper.addText(GuiHelper.getCenter("Skill Book", guiLeft, 160, 2F), guiTop - 20, "Skill Book", 2F, new Color(180, 170, 150).hashCode(),
					false);
			Minecraft.getMinecraft().getTextureManager().bindTexture(background);
			
			for (int i = 0; i < skillNodes.size(); i++) {
				skillNodes.get(i).render(this, mouseX, mouseY);
			}
			
			this.drawTexturedModalRect(guiLeft + 40, guiTop + 141, 218, 105, 30, 22);
			
			this.drawTexturedModalRect(guiLeft + 80, guiTop + 141, 218, 127, 30, 22);
			if (updateMenu) {
				buttonList.clear();
				updateMenu = false;
			}
		} else {
			SkillBase skill = SkillRegistry.instance().getRegisteredSkills().get(selectedSkill);
			//updateMenu = false;
			if (updateMenu == false) {
				updateMenu = true;
				menu = new GuiMenu(guiLeft + 16, guiTop + 20, 39, 111, 1F, new Color(200, 190, 170).hashCode());
				buttonList.add(menu.addButton(0));
				
				for (Level lvl : skill.getSkillLevels()) {
					menu.addComponent(new Object[]{"lvl: " + lvl.getLevel(), lvl.getTitle(), lvl.getDescription()});
				}
			}
			PlayerSkill current = SkillProvider.get(Minecraft.getMinecraft().thePlayer).getSkill(skill.getSkillName());
			
			float previousLevel = experience.getRequireExp(current.getLevel());
			
			float nextLevel = experience.getRequireExp(current.getLevel() + 1) - previousLevel;
			float currentLevel = current.getExp() - previousLevel;
			int exp = (int) Math.floor((currentLevel/nextLevel)*128);
			
			skill.renderGUIBackground(this, partialTicks, mouseX, mouseY, guiLeft + 6, guiTop - 23);
			GuiHelper.addText(GuiHelper.getCenter(skill.getSkillName(), guiLeft, 160, 2F), guiTop - 20, skill.getSkillName(), 2F, new Color(180, 170, 150).hashCode(),
					false);
			
			Minecraft.getMinecraft().getTextureManager().bindTexture(background);
			this.drawTexturedModalRect(guiLeft + 11, guiTop + 139, 218, 105, 30, 22);
			if (per == 128)
				per = 0;
			
			skill.renderGUIForground(this, partialTicks, per, mouseY, guiLeft + 6, guiTop - 23, exp, current.getLevel(), menu);
			per++;
		}
	}
	
	@Override
	protected void keyTyped(char typedChar, int keyCode) {
		if (typedChar == 'l')
			this.showLevel = !showLevel;
		
		if (keyCode == 1) {
			this.mc.displayGuiScreen((GuiScreen) null);
			
			if (this.mc.currentScreen == null) {
				this.mc.setIngameFocus();
			}
		}
	}
	
	protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
		super.mouseClicked(mouseX, mouseY, mouseButton);
		menu.mouseClicked(mouseX, mouseY);
	}
}
