package ec3.client.gui;

import org.lwjgl.opengl.GL11;

import DummyCore.Client.GuiCommon;
import DummyCore.Client.GuiElement;
import ec3.api.ITEHasMRU;
import ec3.client.gui.element.GuiBalanceState;
import ec3.client.gui.element.GuiBoundGemState;
import ec3.client.gui.element.GuiHeightState;
import ec3.client.gui.element.GuiMRUGenerated;
import ec3.client.gui.element.GuiMRUState;
import ec3.client.gui.element.GuiMRUStorage;
import ec3.client.gui.element.GuiMoonState;
import net.minecraft.client.Minecraft;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.tileentity.TileEntity;

public class GuiMIM extends GuiCommon{

	public GuiMIM(Container c, TileEntity tile) {
		super(c,tile);
		this.elementList.add(new GuiMRUStorage(-18, 0, (ITEHasMRU) tile));
		this.elementList.add(new GuiMRUState(0, -18, (ITEHasMRU) tile, 0));
		this.ySize = 104 + 6*18;
	}
	
	@Override
	protected void drawGuiContainerBackgroundLayer(float f1,int i1, int i2) {
        int k = (this.width - this.xSize) / 2;
        int l = (this.height - this.ySize) / 2;
        this.mc.renderEngine.bindTexture(guiGenLocation);
        this.drawTexturedModalRect(k, l, 0, 0, this.xSize, 80);
        this.drawTexturedModalRect(k, l+64, 0, 18, this.xSize, this.ySize);
        this.drawTexturedModalRect(k+60, l+16, 6, 6, 28, 54);
        this.drawTexturedModalRect(k+88, l+16, 6, 6, 28, 54);
        this.drawTexturedModalRect(k+60, l+96, 6, 6, 28, 54);
        this.drawTexturedModalRect(k+88, l+96, 6, 6, 28, 54);
        for(int i = 0 ; i < 6; ++i)
        	this.drawTexturedModalRect(k+6+i*24, l+60+95, 6, 6, 28, 54);
        this.drawTexturedModalRect(k+141, l+60+95, 6, 6, 28, 54);
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

}
