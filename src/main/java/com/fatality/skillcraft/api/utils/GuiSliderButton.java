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

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

public class GuiSliderButton extends GuiButton {
	
	protected ResourceLocation texture;
	protected int width, height;
	private int xPosition, yPosition;
	private int textureX, textureY;
	private float sliderValue = 0F, maxValue;
	
	private int id;
	private boolean wasClicking, isScrolling;
	
	/**
	 * @param ID
	 *            Button ID
	 * @param x
	 *            X Position
	 * @param y
	 *            Y Position
	 * @param maxValue
	 *            the max value you want the slider to go to. Normally the hight
	 *            of your menu.
	 *
	 */
	public GuiSliderButton(int ID, int x, int y, float maxValue) {
		super(ID, x, y, 6, 9, "");
		this.id = ID;
		this.xPosition = x;
		this.yPosition = y;
		this.width = 6;
		this.height = 9;
		this.texture = new ResourceLocation("skillcraft:textures/guis/gui.png");
		this.textureX = 39;
		this.textureY = 0;
		this.maxValue = maxValue - height;
	}
	
	/**
	 * @param ID
	 *            Button ID
	 * @param x
	 *            X Position
	 * @param y
	 *            Y Position
	 * @param width
	 *            width of the texture
	 * @param height
	 *            height of the texture
	 * @param maxValue
	 *            the max value you want the slider to go to. Normally the hight
	 *            of your menu.
	 * @param resourceLocation
	 *            Resource location file
	 * @param textureX
	 *            texture x position
	 * @param textureY
	 *            texture y position
	 */
	public GuiSliderButton(int ID, int x, int y, int width, int height, float maxValue, ResourceLocation resourceLocation, int textureX, int textureY) {
		super(ID, x, y, width, height, "");
		this.id = ID;
		this.xPosition = x;
		this.yPosition = y;
		this.width = width;
		this.height = height;
		this.texture = resourceLocation;
		this.textureX = textureX;
		this.textureY = textureY;
		this.maxValue = maxValue - height;
	}
	
	protected int getHoverState(boolean flag) {
		return 0;
	}
	
	@Override
	protected void mouseDragged(Minecraft minecraft, int i, int j) {
		if (!this.visible) {
			return;
		}
		
		if (isScrolling) {
			sliderValue = (float) (j - (yPosition + 4)) / (float) (this.height - 8);
			
			if (sliderValue < 0.0F) {
				sliderValue = 0.0F;
			}
			
			if (sliderValue > maxValue) {
				sliderValue = maxValue;
			}
		}
	}
	
	public void drawButton(Minecraft mc, int i, int j) {
		
		if (this.visible) {
			
			boolean flag = Mouse.isButtonDown(0);
			int k1 = this.xPosition;
			int k2 = k1 + this.width;
			
			int l1 = this.yPosition;
			int l2 = l1 + (int) maxValue;
			if (flag && i >= k1 && j >= l1 && i < k2 && j < l2) {
				this.isScrolling = true;
			}
			
			if (!flag) {
				this.isScrolling = false;
			}
			
			int scrollING = 0;
			if (isScrolling) {
				scrollING = this.width;
			}
			
			this.wasClicking = flag;
			
			mc.getTextureManager().bindTexture(texture);
			GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
			GL11.glEnable(GL11.GL_BLEND);
			OpenGlHelper.glBlendFunc(770, 771, 1, 0);
			GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
			this.drawTexturedModalRect(this.xPosition, this.yPosition + (int) sliderValue, textureX + scrollING, textureY,
					this.width, this.height);
			this.mouseDragged(mc, i, j);
		}
	}
	
	/**
	 * @return the current sliders value
	 */
	public float getSlider() {
		return sliderValue;
	}
	
	/**
	 * @return the maxValue that was setup in the initialisation
	 */
	public float getMaxValue() {
		return maxValue;
	}
}