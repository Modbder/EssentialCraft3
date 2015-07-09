package ec3.client.gui;

import org.lwjgl.opengl.GL11;
import DummyCore.Client.GuiCommon;
import DummyCore.Client.GuiElement;
import ec3.common.tile.TileCrafter;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public class GuiCrafter extends GuiCommon{
	
	public TileCrafter crafter;

	public GuiCrafter(Container c, TileCrafter inv) {
		super(c);
		guiGenLocation = new ResourceLocation("textures/gui/container/crafting_table.png");
		crafter = inv;
	}
	
	@Override
	protected void drawGuiContainerBackgroundLayer(float f1,int i1, int i2) {
		GL11.glColor3f(1, 1, 1);
        int k = (this.width - this.xSize) / 2;
        int l = (this.height - this.ySize) / 2;
        this.mc.renderEngine.bindTexture(guiGenLocation);
        this.drawTexturedModalRect(k, l, 0, 0, this.xSize, this.ySize);
        
        RenderHelper.disableStandardItemLighting();
        RenderHelper.enableGUIStandardItemLighting();
        
		for(int i = 0; i < this.inventorySlots.inventorySlots.size(); ++i)
		{
			Slot slt = (Slot) this.inventorySlots.inventorySlots.get(i);
			renderSlot(slt);
			GL11.glColor3f(1, 1, 1);
			
		}

		GL11.glColor3f(1, 1, 1);
		
		RenderHelper.enableStandardItemLighting();
		for(int i = 0; i < this.elementList.size(); ++i)
		{
			GuiElement element = elementList.get(i);
			Minecraft.getMinecraft().renderEngine.bindTexture(element.getElementTexture());
			element.draw(k+element.getX(),l+element.getY());
			GL11.glColor3f(1, 1, 1);
		}
	}
	
	public void initGui() 
	{
		super.initGui();
	}
	
	public void renderSlot(Slot slt)
	{
        int k = (this.width - this.xSize) / 2;
        int l = (this.height - this.ySize) / 2;
        this.mc.renderEngine.bindTexture(slotLocation);
        if(slt.slotNumber != 9)
        	this.drawTexturedModalRect(k+slt.xDisplayPosition-1, l+slt.yDisplayPosition-1, 7, 83, 18, 18);
        
        if(slt.slotNumber < 9)
        {
	        if(this.crafter.hasFrame())
	        {
	        	if(!slt.getHasStack())
	        	{
		        	ItemStack[] retStk = this.crafter.getRecipeFromFrame();
		        	if(retStk[slt.slotNumber] != null)
		        	{
		                itemRender.zLevel = 100.0F;
		                this.zLevel = 100.0F;
		                GL11.glColor4d(0.5D, 0.5D, 0.5D, 1D);
		                itemRender.renderWithColor = false;
		                itemRender.renderItemAndEffectIntoGUI(this.fontRendererObj, this.mc.getTextureManager(), retStk[slt.slotNumber], k+slt.xDisplayPosition, l+slt.yDisplayPosition);
		                itemRender.renderWithColor = true;
		                itemRender.zLevel = 0.0F;
		                this.zLevel = 0.0F;
		        	}
	        	}
	        }
        }
	}

}
