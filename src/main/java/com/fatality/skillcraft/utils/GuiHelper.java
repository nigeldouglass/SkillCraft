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

package com.fatality.skillcraft.utils;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

import static org.lwjgl.opengl.GL11.*;

public class GuiHelper {
	
	public static void drawRectWithUV(float xPosition, float yPosition, float u, float v, float width, float height,
	                                  float textureWidth, float textureHeight,
	                                  ResourceLocation texture) {
		GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
		float scaleX = 1.0F / 256;
		float scaleY = 1.0F / 256;
		
		GlStateManager.pushMatrix();
		Minecraft.getMinecraft().getTextureManager().bindTexture(texture);
		GlStateManager.enableBlend();
		GlStateManager.blendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
		
		Tessellator tessellator = Tessellator.getInstance();
		VertexBuffer vertexbuffer = tessellator.getBuffer();
		vertexbuffer.begin(7, DefaultVertexFormats.POSITION_TEX);
		vertexbuffer.pos((double) xPosition, (double) (yPosition + height), 0.0D)
				.tex((double) (u * scaleX), (double) ((v + textureHeight) * scaleY)).endVertex();
		vertexbuffer.pos((double) (xPosition + width), (double) (yPosition + height), 0.0D)
				.tex((double) ((u + textureWidth) * scaleX), (double) ((v + textureHeight) * scaleY))
				.endVertex();
		vertexbuffer.pos((double) (xPosition + width), (double) yPosition, 0.0D)
				.tex((double) ((u + textureWidth) * scaleX), (double) (v * scaleY)).endVertex();
		vertexbuffer.pos((double) xPosition, (double) yPosition, 0.0D).tex((double) (u * scaleX), (double) (v * scaleY))
				.endVertex();
		tessellator.draw();
		
		GlStateManager.popMatrix();
		GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
	}
	
	public static void addElement(int xPosition, int yPosition, int xTexturePosition, int yTexturePosition, int xSize,
	                              int ySize, String text, float scale, ResourceLocation texture, GuiContainer gui) {
		GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
		renderElement(xPosition, yPosition, xTexturePosition, yTexturePosition, xSize, ySize, text, scale, texture,
				gui);
	}
	
	private static void renderElement(int xPosition, int yPosition, int xTexturePosition, int yTexturePosition,
	                                  int xSize, int ySize, String text, float scale, ResourceLocation texture, GuiContainer gui) {
		GlStateManager.pushMatrix();
		Minecraft.getMinecraft().getTextureManager().bindTexture(texture);
		GlStateManager.enableBlend();
		OpenGlHelper.glBlendFunc(770, 771, 1, 0);
		GlStateManager.blendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
		gui.drawTexturedModalRect(xPosition, yPosition, xTexturePosition, yTexturePosition, xSize, ySize);
		if (!text.equals("")) {
			glScalef(scale, scale, scale);
			int l = 14737632;
			float amend = (1 / scale);
			FontRenderer fontrenderer = Minecraft.getMinecraft().fontRendererObj;
			gui.drawCenteredString(fontrenderer, text, (int) ((xPosition + xSize / 2) * amend) + 1,
					(int) ((yPosition + (ySize - (9 / amend)) / 2) * amend) + 1, l);
		}
		GlStateManager.popMatrix();
		GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
	}
	
	public static float getCenter(String string, int xPos, int width, float scale) {
		return ((xPos + width / 2)
				- Minecraft.getMinecraft().fontRendererObj.getStringWidth(string) / (2 / scale));
	}
	
	public static void addText(float xPosition, float yPosition, String text, float scale, int colour, boolean shadow) {
		GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
		GlStateManager.pushMatrix();
		GlStateManager.translate(0, 0, 5);
		GlStateManager.scale(scale, scale, 0F);
		GlStateManager.translate(xPosition / scale, yPosition / scale, 5);
		FontRenderer fontrenderer = Minecraft.getMinecraft().fontRendererObj;
		if (shadow) {
			fontrenderer.drawStringWithShadow(text, 0, 0, colour);
		} else {
			fontrenderer.drawString(text, 0, 0, colour);
		}
		GlStateManager.popMatrix();
		GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
	}
	
}
