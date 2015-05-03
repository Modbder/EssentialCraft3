package ec3.client.gui;

import org.lwjgl.opengl.GL11;

import ec3.common.entity.EntityDemon;
import ec3.common.inventory.ContainerDemon;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import DummyCore.Client.GuiCommon;

public class GuiDemon extends GuiCommon{

	public ResourceLocation DguiGenLocation = new ResourceLocation("essentialcraft","textures/gui/demon.png");
	
	public GuiDemon(Container c)
	{
		super(c);
	}
	
    private void drawItemStack(ItemStack p_146982_1_, int p_146982_2_, int p_146982_3_, String p_146982_4_)
    {
        FontRenderer font = null;
        if (p_146982_1_ != null) font = p_146982_1_.getItem().getFontRenderer(p_146982_1_);
        if (font == null) font = fontRendererObj;
        itemRender.renderWithColor = false;
        itemRender.renderItemAndEffectIntoGUI(font, this.mc.getTextureManager(), p_146982_1_, p_146982_2_, p_146982_3_);
        itemRender.renderItemOverlayIntoGUI(font, this.mc.getTextureManager(), p_146982_1_, p_146982_2_, p_146982_3_ - 0, p_146982_4_);
        itemRender.renderWithColor = true;
    }
	
	@Override
	protected void drawGuiContainerBackgroundLayer(float f1,int i1, int i2) 
	{
		GL11.glColor3f(1, 1, 1);
        int k = (this.width - this.xSize) / 2;
        int l = (this.height - this.ySize) / 2;
        this.mc.renderEngine.bindTexture(DguiGenLocation);
        this.drawTexturedModalRect(k, l, 0, 0, this.xSize, this.ySize);
        ContainerDemon cd = ContainerDemon.class.cast(this.inventorySlots);
        EntityDemon demon = cd.trader;
        if(demon != null && demon.desiredItem != null)
        {
        	GL11.glTranslatef(0, 0, 100);
        	this.drawItemStack(demon.desiredItem, k + 79, l + 29, demon.desiredItem.stackSize+"");
        	this.fontRendererObj.drawString(demon.desiredItem.getDisplayName(), k + 5, l + 59, 0xffffff);
        	GL11.glTranslatef(0, 0, -100);
        }
	}
}
