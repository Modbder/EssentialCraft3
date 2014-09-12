package ec3.client.gui;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.opengl.GL11;

import ec3.client.gui.element.GuiElement;
import DummyCore.Utils.MiscUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.tileentity.TileEntity;

public class GuiCommon extends GuiContainer{
	
	public List<GuiElement> elementList = new ArrayList();
	public TileEntity genericTile;

	public GuiCommon(Container c) {
		super(c);
	}
	
	public GuiCommon(Container c, TileEntity tile) {
		this(c);
		genericTile = tile;
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float f1,int i1, int i2) {
        int k = (this.width - this.xSize) / 2;
        int l = (this.height - this.ySize) / 2;
        MiscUtils.bindTexture("essentialcraft", "textures/gui/gui_generic.png");
        this.drawTexturedModalRect(k, l, 0, 0, this.xSize, this.ySize);
		for(int i = 0; i < this.inventorySlots.inventorySlots.size(); ++i)
		{
			Slot slt = (Slot) this.inventorySlots.inventorySlots.get(i);
			renderSlot(slt);
			GL11.glColor3f(1, 1, 1);
		}
		for(int i = 0; i < this.elementList.size(); ++i)
		{
			GuiElement element = elementList.get(i);
			Minecraft.getMinecraft().renderEngine.bindTexture(element.getElementTexture());
			element.draw(k+element.getX(),l+element.getY());
			GL11.glColor3f(1, 1, 1);
		}
	}
	
	public void renderSlot(Slot slt)
	{
        int k = (this.width - this.xSize) / 2;
        int l = (this.height - this.ySize) / 2;
		MiscUtils.bindTexture("essentialcraft", "textures/gui/slot_common.png");
		this.drawTexturedModalRect(k+slt.xDisplayPosition-1, l+slt.yDisplayPosition-1, 0, 0, 18, 18);
	}
	
	

}
