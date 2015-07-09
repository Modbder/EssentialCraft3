package ec3.client.gui;

import org.lwjgl.opengl.GL11;

import DummyCore.Client.GuiCommon;
import DummyCore.Client.GuiElement;
import ec3.api.ITEHasMRU;
import ec3.client.gui.element.GuiMRUStorage;
import net.minecraft.client.Minecraft;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;

public class GuiNewMIM extends GuiCommon{

	public GuiNewMIM(Container c, TileEntity tile) {
		super(c,tile);
		guiGenLocation = new ResourceLocation("essentialcraft","textures/gui/mim.png");
		this.elementList.add(new GuiMRUStorage(4, 72, (ITEHasMRU) tile));
		this.xSize = 196;
		this.ySize = 256;
	}
	
	@Override
	protected void drawGuiContainerBackgroundLayer(float f1,int i1, int i2) {
		GL11.glColor3f(1, 1, 1);
        int k = (this.width - this.xSize) / 2;
        int l = (this.height - this.ySize) / 2;
        this.mc.renderEngine.bindTexture(guiGenLocation);
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

}
