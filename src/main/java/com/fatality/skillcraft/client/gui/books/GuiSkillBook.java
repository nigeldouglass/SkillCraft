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

import com.fatality.skillcraft.api.utils.EnumSkills;
import com.fatality.skillcraft.utils.GuiHelper;
import com.fatality.skillcraft.utils.ModInfo;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.util.ResourceLocation;

import java.awt.*;
import java.util.Random;

public class GuiSkillBook extends GuiContainer {
	
	public final ResourceLocation background = new ResourceLocation(ModInfo.MOD_ID + ":textures/gui/skillbook.png");
	
	private EntityPlayer player;
	
	private	boolean showLevel = false;
	
	public GuiSkillBook(Container inventorySlotsIn, EntityPlayer player) {
		super(inventorySlotsIn);
		this.player = player;
	}
	
	public void initGui() {
		super.initGui();
	}
	
	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
		Minecraft.getMinecraft().getTextureManager().bindTexture(background);
		int x = guiLeft - 60;
		// Top
		GuiHelper.drawRectWithUV(0 + x, 10, 0, 0, 12, 12, 12, 12, background);
		GuiHelper.drawRectWithUV(12 + x, 10, 12, 0, 300, 12, 1, 12, background);
		GuiHelper.drawRectWithUV(312 + x, 10, 14, 0, 12, 12, 12, 12, background);
		
		// Middle
		GuiHelper.drawRectWithUV(0 + x, 22, 0, 12, 12, 200, 12, 1, background);
		GuiHelper.drawRectWithUV(311 + x, 22, 13, 12, 12, 200, 12, 1, background);
		GuiHelper.drawRectWithUV(12 + x, 22, 13, 13, 299, 200, 1, 1, background);
		
		// Bottom
		GuiHelper.drawRectWithUV(0 + x, 222, 0, 13, 12, 12, 12, 12, background);
		GuiHelper.drawRectWithUV(12 + x, 222, 12, 13, 300, 12, 1, 12, background);
		GuiHelper.drawRectWithUV(312 + x, 222, 14, 13, 12, 12, 12, 12, background);
		
		x += 10;
		int y = 20;
		int select = 0;
		for (int j = 0; j < 2; j++) {
			for (int i = 1; i <= 3; i++) {
				if (select > EnumSkills.values().length - 1)
					break;
				
				GlStateManager.pushMatrix();
				int c = EnumSkills.byMeta(select).getColour();
				
				if ((c & -67108864) == 0) {
					c |= -16777216;
				}
				
				float red = (float) (c >> 16 & 255) / 255.0F;
				float green = (float) (c >> 8 & 255) / 255.0F;
				float blue = (float) (c & 255) / 255.0F;
				float alpha = (float) (c >> 24 & 255) / 255.0F;
				
				GlStateManager.color(red, green, blue, alpha);
				this.drawTexturedModalRect(x, y + 2, 206, 50, 50, 50);
				GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
				this.drawTexturedModalRect(x, y, 206, 0, 50, 50);
				
				this.fontRendererObj.drawString(EnumSkills.byMeta(select).getNameUpper(), x, y + 55, EnumSkills.byMeta(select).getColour(), false);
				if(showLevel) {
					Minecraft.getMinecraft().getTextureManager().bindTexture(background);
					GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
					this.drawTexturedModalRect(x+14, y+2, 184, 0, 22, 14);
					
					Random r = new Random();
					this.fontRendererObj.drawString(r.nextInt(80) + 10 + "", x + 19, y + 3, new Color(255, 63, 5).hashCode(), true);
				}
				GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
				Minecraft.getMinecraft().getTextureManager().bindTexture(background);
				GlStateManager.popMatrix();
				
				
				y += 70;
				select += 1;
			}
			
			x += 70;
			y = 20;
		}
	}
	
	@Override
	protected void keyTyped(char typedChar, int keyCode) {
		if(typedChar=='l')
			this.showLevel = !showLevel;
	}
}
