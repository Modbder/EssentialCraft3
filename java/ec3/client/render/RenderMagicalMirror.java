package ec3.client.render;

import DummyCore.Utils.MathUtils;
import DummyCore.Utils.MiscUtils;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelEnderCrystal;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityEnderCrystal;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Vec3;
import net.minecraftforge.client.model.AdvancedModelLoader;
import net.minecraftforge.client.model.IModelCustom;

import org.lwjgl.opengl.GL11;

import ec3.api.ITEHasMRU;
import ec3.client.model.ModelFloatingCube;
import ec3.client.model.ModelSolarPrism;
import ec3.common.item.ItemBoundGem;
import ec3.common.tile.TileMagicalMirror;
import ec3.common.tile.TileRayTower;
import ec3.utils.common.ECUtils;

@SideOnly(Side.CLIENT)
public class RenderMagicalMirror extends TileEntitySpecialRenderer
{
    public static final ResourceLocation textures = new ResourceLocation("essentialcraft:textures/special/models/assembler/armTextures.png");
    public static final ResourceLocation glass = new ResourceLocation("essentialcraft:textures/special/models/assembler/mirror.png");
    public static final IModelCustom model = AdvancedModelLoader.loadModel(new ResourceLocation("essentialcraft:textures/special/models/assembler/Mirror.obj"));

    public RenderMagicalMirror()
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
    	TileMagicalMirror tile = (TileMagicalMirror) p_76986_1_;
    	RenderHelper.disableStandardItemLighting();
        GL11.glPushMatrix();
        GL11.glTranslatef((float)p_76986_2_+0.5F, (float)p_76986_4_-0.25F, (float)p_76986_6_+0.5F);
        float timeIndex = Minecraft.getMinecraft().theWorld.getWorldTime()%120;
        float yIndex = 1.0F;
        if(timeIndex <= 60)
        	yIndex = timeIndex/240F;
        else
        	yIndex = 0.5F-timeIndex/240F;
        GL11.glTranslatef(0, yIndex-0.25F, 0);
        if(tile.inventoryPos != null)
        {
	        double d0 = tile.inventoryPos.x - (tile.xCoord+0.5F);
	        double d1 = tile.inventoryPos.y - (tile.yCoord+0.5F+yIndex);
	        double d2 = tile.inventoryPos.z - (tile.zCoord+0.5F);
	        double d3 = (double)MathHelper.sqrt_double(d0 * d0 + d2 * d2);
	        float f = -(float)(Math.atan2(d2, d0) * 180.0D / Math.PI)-90;
	        float f1 = -(float)(-(Math.atan2(d1, d3) * 180.0D / Math.PI));
	        
	        GL11.glRotated(f, 0, 1, 0);
	        GL11.glRotated(f1, 1, 0, 0);
	        
        }

        
        this.bindTexture(textures);
        this.model.renderPart("pCube2");
        this.bindTexture(glass);
        if(tile.pulsing)
        {
        	 timeIndex = Minecraft.getMinecraft().theWorld.getWorldTime()%20;
        	 float colorIndex = 1.0F;
        	 if(timeIndex <= 10)
        	 {
        		 colorIndex = 1.0F - timeIndex/10;
        	 }else
        	 {
        		 colorIndex = (timeIndex-10)/10;
        	 }
        	 GL11.glColor3f(1, colorIndex, 1);
        }
        this.model.renderPart("pPlane1");
        GL11.glRotatef(180, 0, 1, 0);
        this.model.renderPart("pPlane1");
        GL11.glColor3f(1, 1, 1);
        GL11.glPopMatrix();
        ECUtils.renderMRUBeam(p_76986_1_, 0, p_76986_2_, p_76986_4_, p_76986_6_, p_76986_8_, p_76986_9_);
        RenderHelper.enableStandardItemLighting();
        
        if(tile.transferingStack != null)
        {
        	if(tile.transferTime < 20)
        	{
        		MiscUtils.renderItemStack_Full(tile.transferingStack, tile.xCoord, tile.yCoord, tile.zCoord, p_76986_2_, p_76986_4_, p_76986_6_, tile.getWorldObj().getWorldTime()%360, 0, 1, 1, 1, 0.5F, -0.3F+((float)tile.transferTime/20F), 0.5F);
        	}else
        	{
        		Vec3 vec = Vec3.createVectorHelper(tile.inventoryPos.x+0.5F - (tile.xCoord+0.5F), tile.inventoryPos.y - (tile.yCoord+0.5F), tile.inventoryPos.z - (tile.zCoord+0.5F));
        		
        		MiscUtils.renderItemStack_Full(tile.transferingStack, tile.xCoord, tile.yCoord, tile.zCoord, p_76986_2_, p_76986_4_, p_76986_6_, tile.getWorldObj().getWorldTime()%360, 0, 1, 1, 1, 0.5F+((float)vec.xCoord*(tile.transferTime-20F)/40), 0.5F+((float)vec.yCoord*(tile.transferTime-20F)/40), 0.5F+((float)vec.zCoord*(tile.transferTime-20F)/40));
        	}
        }
    }

    /**
     * Returns the location of an entity's texture. Doesn't seem to be called unless you call Render.bindEntityTexture.
     */
    protected ResourceLocation getEntityTexture(TileEntity p_110775_1_)
    {
        return textures;
    }

	@Override
	public void renderTileEntityAt(TileEntity p_147500_1_, double p_147500_2_,
			double p_147500_4_, double p_147500_6_, float p_147500_8_) {
		if(p_147500_1_.getWorldObj().getBlockMetadata(p_147500_1_.xCoord, p_147500_1_.yCoord, p_147500_1_.zCoord) == 0)
		this.doRender((TileEntity) p_147500_1_, p_147500_2_, p_147500_4_, p_147500_6_, p_147500_8_, 0);
	}
}