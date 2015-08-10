package ec3.client.gui;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import DummyCore.Utils.MiscUtils;
import ec3.common.inventory.InventoryCraftingFrame;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;

public class GuiCraftingFrame extends GuiContainer{
	
	public InventoryCraftingFrame crafter;

	public GuiCraftingFrame(Container c, InventoryCraftingFrame inv) {
		super(c);
		crafter = inv;
	}
	
	@Override
    protected boolean checkHotbarKeys(int slot)
    {
		return false;
    }
	
	@SuppressWarnings("unchecked")
	public void initGui() 
	{
	    int k = (this.width - this.xSize) / 2;
	    int l = (this.height - this.ySize) / 2;
		this.buttonList.add(new GuiButton(0, k+6, l+6, 20, 20, ""));
		super.initGui();
	}
	
    protected void actionPerformed(GuiButton par1GuiButton) 
    {
    	MiscUtils.handleButtonPress(par1GuiButton.id, this.getClass(), par1GuiButton.getClass(), this.mc.thePlayer, 0, 0, 0);
    }

	@Override
	protected void drawGuiContainerBackgroundLayer(float p_146976_1_,
			int p_146976_2_, int p_146976_3_) {
		MiscUtils.bindTexture("minecraft", "textures/gui/container/crafting_table.png");
		this.drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
	}
	
    public void drawScreen(int mX, int mY, float partialTicks)
    {
    	if(!this.crafter.filterStack.isItemEqual(mc.thePlayer.getCurrentEquippedItem()))
    		this.crafter.filterStack = mc.thePlayer.getCurrentEquippedItem();
    	if(!ItemStack.areItemStackTagsEqual(this.crafter.filterStack, mc.thePlayer.getCurrentEquippedItem()))
    		this.crafter.filterStack = mc.thePlayer.getCurrentEquippedItem();
    	
    	super.drawScreen(mX, mY, partialTicks);
	     for (int ik = 0; ik < this.buttonList.size(); ++ik)
	     {
	    	 RenderHelper.disableStandardItemLighting();
	    	 GL11.glColor3f(1, 1, 1);
	    	 GuiButton btn  = (GuiButton) this.buttonList.get(ik);
	    	 boolean hover = mX >= btn.xPosition && mY >= btn.yPosition && mX < btn.xPosition + btn.width && mY < btn.yPosition + btn.height;
	    	 int id = btn.id;
	    	 if(id == 0)
	    	 {
	    		 MiscUtils.bindTexture("essentialcraft", "textures/gui/guiFilterButtons.png");
	    		 if(MiscUtils.getStackTag(this.crafter.filterStack).getBoolean("ignoreOreDict"))
	    		 {
	    			 this.drawTexturedModalRect(btn.xPosition, btn.yPosition, 20, 40, 20, 20);
	    		 }else
	    		 {
	    			 this.drawTexturedModalRect(btn.xPosition, btn.yPosition, 0, 40, 20, 20);
	    		 }
	    	 }
	    	 if(hover)
	    	 {
		    	 if(id == 0)
		    	 {
		    		 List<String> drawedLst = new ArrayList<String>();
		    		 if(MiscUtils.getStackTag(this.crafter.filterStack).getBoolean("ignoreOreDict"))
		    		 {
		    			 drawedLst.add("Ore Dictionary: Ignored");
		    		 }else
		    		 {
		    			 drawedLst.add("Ore Dictionary: Not Ignored");
		    		 }
		    		 drawHoveringText(drawedLst, mX, mY, fontRendererObj);
		    		 
		    	 }
	    	 }
	     }
    }
    
    @SuppressWarnings({ "rawtypes", "unchecked" })
	protected void drawHoveringText(List p_146283_1_, int p_146283_2_, int p_146283_3_, FontRenderer font)
    {
    	GL11.glDisable(GL11.GL_LIGHTING);
        if (!p_146283_1_.isEmpty())
        {
            GL11.glDisable(GL12.GL_RESCALE_NORMAL);
            RenderHelper.disableStandardItemLighting();
            GL11.glDisable(GL11.GL_LIGHTING);
            GL11.glDisable(GL11.GL_DEPTH_TEST);
            int k = 0;
            Iterator<String> iterator = p_146283_1_.iterator();

            while (iterator.hasNext())
            {
                String s = (String)iterator.next();
                int l = font.getStringWidth(s);

                if (l > k)
                {
                    k = l;
                }
            }

            int j2 = p_146283_2_ + 12;
            int k2 = p_146283_3_ - 12;
            int i1 = 8;

            if (p_146283_1_.size() > 1)
            {
                i1 += 2 + (p_146283_1_.size() - 1) * 10;
            }

            if (j2 + k > this.width)
            {
                j2 -= 28 + k;
            }

            if (k2 + i1 + 6 > this.height)
            {
                k2 = this.height - i1 - 6;
            }

            this.zLevel = 600.0F;
            itemRender.zLevel = 600.0F;
            int j1 = -267386872;
            this.drawGradientRect(j2 - 3, k2 - 4, j2 + k + 3, k2 - 3, j1, j1);
            this.drawGradientRect(j2 - 3, k2 + i1 + 3, j2 + k + 3, k2 + i1 + 4, j1, j1);
            this.drawGradientRect(j2 - 3, k2 - 3, j2 + k + 3, k2 + i1 + 3, j1, j1);
            this.drawGradientRect(j2 - 4, k2 - 3, j2 - 3, k2 + i1 + 3, j1, j1);
            this.drawGradientRect(j2 + k + 3, k2 - 3, j2 + k + 4, k2 + i1 + 3, j1, j1);
            int k1 = 1347420415;
            int l1 = (k1 & 16711422) >> 1 | k1 & -16777216;
            this.drawGradientRect(j2 - 3, k2 - 3 + 1, j2 - 3 + 1, k2 + i1 + 3 - 1, k1, l1);
            this.drawGradientRect(j2 + k + 2, k2 - 3 + 1, j2 + k + 3, k2 + i1 + 3 - 1, k1, l1);
            this.drawGradientRect(j2 - 3, k2 - 3, j2 + k + 3, k2 - 3 + 1, k1, k1);
            this.drawGradientRect(j2 - 3, k2 + i1 + 2, j2 + k + 3, k2 + i1 + 3, l1, l1);

            for (int i2 = 0; i2 < p_146283_1_.size(); ++i2)
            {
                String s1 = (String)p_146283_1_.get(i2);
                font.drawStringWithShadow(s1, j2, k2, -1);

                if (i2 == 0)
                {
                    k2 += 2;
                }

                k2 += 10;
            }

            this.zLevel = 0.0F;
            itemRender.zLevel = 0.0F;
            GL11.glEnable(GL11.GL_LIGHTING);
            GL11.glEnable(GL11.GL_DEPTH_TEST);
            RenderHelper.enableStandardItemLighting();
            GL11.glEnable(GL12.GL_RESCALE_NORMAL);
        }
        GL11.glEnable(GL11.GL_LIGHTING);
        GL11.glColor3f(1, 1, 1);
    }

}
