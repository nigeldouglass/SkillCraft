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

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.util.ResourceLocation;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class GuiMenu {
	
	private static int slots;
	private List<GuiMenuComponent> menuComponent = new ArrayList<GuiMenuComponent>();
	private int xPos, yPos;
	private int height, width;
	private int selectColour;
	private int selected = 0;
	private float scroll;
	private GuiSliderButton slider;
	private int scrollOffset;
	private float scale = 1F;
	
	/**
	 * @param xPosition X Position
	 * @param yPosition Y Position
	 * @param width     Width of the menu
	 * @param height    Height of the menu. (Must be multiple of 11)
	 * @param scale     Text scale for the menu
	 */
	public GuiMenu(int xPosition, int yPosition, int width, int height, float scale) {
		this.xPos = xPosition;
		this.yPos = yPosition;
		this.height = height;
		this.width = width;
		this.slots = (Math.round(height / 11));
		this.selectColour = 0026163;
		if (scale <= 1) {
			this.scale = scale;
		}
	}
	
	/**
	 * @param xPosition    X Position
	 * @param yPosition    Y Position
	 * @param width        Width of the menu
	 * @param height       Height of the menu. (Must be multiple of 11)
	 * @param scale        Text scale for the menu
	 * @param selectColour Set the select colour based 00000000
	 */
	public GuiMenu(int xPosition, int yPosition, int width, int height, float scale, int selectColour) {
		this.xPos = xPosition;
		this.yPos = yPosition;
		this.height = height;
		this.width = width;
		this.slots = (Math.round(height / 11));
		this.selectColour = selectColour;
		if (scale <= 1) {
			this.scale = scale;
		}
	}
	
	/**
	 * @param obj new Object[]{"Hi %a", 'a', Items.apple}
	 * @Description add new Component to the menu
	 */
	public void addComponent(Object... obj) {
		this.menuComponent.add(new GuiMenuComponent(menuComponent.size(), obj));
	}
	
	/**
	 * @param ID the position of where it is in the menu. E.g 5th one would be
	 *           4.
	 * @Description remove given Component from the menu
	 */
	public void removeComponent(int ID) {
		for (int i = ID + 1; i < this.menuComponent.size(); i++) {
			this.menuComponent.get(i).updateID(i - 1);
		}
		this.menuComponent.remove(ID);
	}
	
	/**
	 * @param ID  the position of where it is in the menu. E.g 5th one would be
	 *            4.
	 * @param obj new Object[]{"Hi %a", 'a', Items.apple}
	 * @Description edit the value of a menu component
	 */
	public void editValue(int ID, Object... obj) {
		GuiMenuComponent getComponent = ((GuiMenuComponent) this.menuComponent.get(ID));
		getComponent.setValue(obj);
	}
	
	/**
	 * @param ID position of where it is in the menu. E.g 5th one would be 4.
	 * @return object array Object[]{"Hi %a", 'a', Items.apple}
	 */
	public Object[] getValue(int ID) {
		GuiMenuComponent getComponent = ((GuiMenuComponent) this.menuComponent.get(ID));
		return getComponent.getData();
	}
	
	public int getComponentAsID(int ID) {
		GuiMenuComponent getComponent = ((GuiMenuComponent) this.menuComponent.get(ID));
		return getComponent.getID();
	}
	
	/**
	 * @param ID Button ID
	 * @return returns a GuiButton that needs to be added to buttonlist in a
	 * gui.
	 */
	public GuiButton addButton(int ID) {
		return this.slider = new GuiSliderButton(ID, xPos + width + 3, yPos, height);
	}
	
	/**
	 * @Description update the slider and scroll values
	 */
	public void update() {
		if (slider != null) {
			this.scroll = slider.getSlider();
		}
	}
	
	/**
	 * @param gui the GuiContainer you are calling the method from. E.g this
	 * @Description render the menu
	 */
	public void render(GuiContainer gui) {
		GuiHelper.addElement(xPos + width + 5, yPos, width+12, 0, 2, height, "", 1F,
				new ResourceLocation("skillcraft:textures/guis/gui.png"), gui);
		
		int maxShow = slots;
		if (menuComponent.size() < maxShow) {
			maxShow = menuComponent.size();
			scrollOffset = 0;
		} else {
			scrollOffset = (int) ((menuComponent.size() - maxShow) * ((scroll) / (height - 9)));
		}
		int lastValue = slots + scrollOffset;
		int yOffset = 0;
		
		for (int k = scrollOffset; k < lastValue; k++) {
			if (k <= menuComponent.size() - 1) {
				Object[] text = this.getValue(k);
				if (k == selected) {
					GuiHelper.addElement(xPos, yPos + (yOffset * 11), 0, 0, width, 11, "", scale,
							new ResourceLocation("skillcraft:textures/guis/gui.png"), selectColour, gui);
				} else {
					GuiHelper.addElement(xPos, yPos + (yOffset * 11), 0, 0, width, 11, "", scale,
							new ResourceLocation("skillcraft:textures/guis/gui.png"), gui);
				}
				GuiHelper.addText(GuiHelper.getCenter(text[0] + "", xPos, width, scale),
						yPos + (yOffset * 11) + 2 * (1 / scale), text[0] + "", scale, new Color(50, 50, 50).hashCode(), false);
				
			} else {
				GuiHelper.addElement(xPos, yPos + (yOffset * 11), 0, 11, width, 11, "", 0.5F,
						new ResourceLocation("skillcraft:textures/guis/gui.png"), gui);
			}
			yOffset++;
		}
		if (maxShow != slots)
			GuiHelper.addElement(xPos, yPos + (yOffset * 11) - 1, 0, 22, width, 1, "", 0.5F,
					new ResourceLocation("skillcraft:textures/guis/gui.png"), gui);
	}
	
	/**
	 * @param x Mouse X Position
	 * @param y Mouse Y Position
	 * @Description Call on mouse click to get what menu component has been
	 * clicked
	 */
	public void mouseClicked(int x, int y) {
		if (x >= xPos && x <= xPos + width - 2 && y >= yPos && y <= yPos + height) {
			int select = ((y - yPos) / 11) + scrollOffset;
			if (menuComponent.size() > select) {
				this.setSelected(select);
			}
		}
	}
	
	/**
	 * @return object array of the selected component Object[]{"Hi %a", 'a',
	 * Items.apple}
	 */
	public Object[] getSelected() {
		return this.getValue(selected);
	}
	
	public void setSelected(int selected) {
		this.selected = selected;
	}
	
	public int getSize() {
		return menuComponent.size();
	}
	
}

