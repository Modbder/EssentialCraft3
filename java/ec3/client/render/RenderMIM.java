package ec3.client.render;

import DummyCore.Utils.Coord2D;
import DummyCore.Utils.MathUtils;
import DummyCore.Utils.MiscUtils;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import org.lwjgl.opengl.GL11;

import ec3.common.tile.TileMIM;

@SideOnly(Side.CLIENT)
public class RenderMIM extends TileEntitySpecialRenderer
{

    public RenderMIM()
    {
    }

    /**
     * Actually renders the given argument. This is a synthetic bridge method, always casting down its argument and then
     * handing it off to a worker function which does the actual work. In all probabilty, the class Render is generic
     * (Render<T extends Entity) and this method has signature public void func_76986_a(T entity, double d, double d1,
     * double d2, float f, float f1). But JAD is pre 1.5 so doesn't do that.
     */
    public void doRender(TileEntity p_76986_1_, double p_76986_2_, double p_76986_4_, double p_76986_6_, float p_76986_8_, float p_76986_9_)
    {
    	RenderHelper.disableStandardItemLighting();
      
        TileMIM tile = (TileMIM) p_76986_1_;
        float rotation = tile.getWorldObj().getWorldTime() % 360;

        for(int i = 0; i < 6; ++i)
        {
        	int itemsInSlots = 0;
        	for(int j = 0; j < 9; ++j)
        	{
        		if(tile.getStackInSlot(1+(i*9+j)) != null)
        			++itemsInSlots;
        	}
        	for(int j = 0; j < itemsInSlots; ++j)
        	{
        		float offsetX = 0.5F;
        		float offsetZ = 0.5F;
        		Coord2D offset = MathUtils.polarOffset(new Coord2D(offsetX,offsetZ), rotation+(j*(360/itemsInSlots)), 0.1F+i*(1F/12F));
        		GL11.glDisable(GL11.GL_LIGHTING);
        		GL11.glColor3f(1, 1, 1);
        		MiscUtils.renderItemStack_Full(tile.getStackInSlot(1+(i*9+j)), tile.xCoord, tile.yCoord, tile.zCoord, p_76986_2_, p_76986_4_, p_76986_6_, 90-rotation-(j*(360/itemsInSlots)), 0, 1, 1, 1, offset.x, i*(1F/6F), offset.z);
        		GL11.glEnable(GL11.GL_LIGHTING);
        	}
        }
        
    	RenderHelper.enableStandardItemLighting();
    }


	@Override
	public void renderTileEntityAt(TileEntity p_147500_1_, double p_147500_2_,
			double p_147500_4_, double p_147500_6_, float p_147500_8_) {
		if(p_147500_1_.getWorldObj().getBlockMetadata(p_147500_1_.xCoord, p_147500_1_.yCoord, p_147500_1_.zCoord) == 0)
		this.doRender((TileEntity) p_147500_1_, p_147500_2_, p_147500_4_, p_147500_6_, p_147500_8_, 0);
	}
}