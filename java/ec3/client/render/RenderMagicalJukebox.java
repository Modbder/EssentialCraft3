package ec3.client.render;

import java.util.List;

import DummyCore.Utils.Coord3D;
import DummyCore.Utils.DummyDistance;
import DummyCore.Utils.MathUtils;
import DummyCore.Utils.MiscUtils;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelEnderCrystal;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityEnderCrystal;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.AdvancedModelLoader;
import net.minecraftforge.client.model.IModelCustom;

import org.lwjgl.opengl.GL11;

import ec3.api.ITEHasMRU;
import ec3.client.model.ModelFloatingCube;
import ec3.client.model.ModelSolarPrism;
import ec3.common.item.ItemBoundGem;
import ec3.common.item.ItemsCore;
import ec3.common.tile.TileMagicalJukebox;
import ec3.common.tile.TileMagicalQuarry;
import ec3.common.tile.TileMagicalRepairer;
import ec3.common.tile.TileMonsterHarvester;
import ec3.common.tile.TileMonsterHolder;
import ec3.common.tile.TileRayTower;
import ec3.utils.common.ECUtils;
import ec3.utils.common.PlayerTickHandler;

@SideOnly(Side.CLIENT)
public class RenderMagicalJukebox extends TileEntitySpecialRenderer
{
	public static final ResourceLocation textures = new ResourceLocation("essentialcraft:textures/special/models/magicalJukebox.png");
    public static final IModelCustom model = AdvancedModelLoader.loadModel(new ResourceLocation("essentialcraft:textures/special/models/MagicalJukebox.obj"));

    public RenderMagicalJukebox()
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
    	float[][] coloring = new float[][]{{1,0,0},{1,0,1},{0,0,1},{0,1,1},{0,1,0},{1,1,0},{1,1,1}};
    	
    	
        TileMagicalJukebox tile = (TileMagicalJukebox) p_76986_1_;
        float rotation = 90;
        int currentSupposedTimingColor = (int) (tile.getWorldObj().getWorldTime() % (7*30));
        currentSupposedTimingColor/= 30;
        ItemStack is = tile.getStackInSlot(1);
        
        float upperIndex = tile.getWorldObj().getWorldTime() % 40;
        
        if(upperIndex < 20)
        {
        	upperIndex = (20 - upperIndex);
        }else
        {
        	upperIndex -= 20;
        }
        
        float upperIndex1 = tile.getWorldObj().getWorldTime() % 30;
        
        if(upperIndex1 < 15)
        {
        	upperIndex1 = (15 - upperIndex1);
        }else
        {
        	upperIndex1 -= 15;
        }
        
		if(is != null && is.getItem() == ItemsCore.record_secret && tile.recordCooldownTime > 0)
		{
			GL11.glColor3f(coloring[currentSupposedTimingColor][0], coloring[currentSupposedTimingColor][1], coloring[currentSupposedTimingColor][2]);
		}
    	
    	RenderHelper.disableStandardItemLighting();
    	GL11.glPushMatrix(); 
    	ECUtils.renderMRUBeam(p_76986_1_, 0, p_76986_2_, p_76986_4_, p_76986_6_, p_76986_8_, p_76986_9_);
    	GL11.glPopMatrix();
    	
        GL11.glPushMatrix();
        GL11.glTranslatef((float)p_76986_2_+0.5F, (float)p_76986_4_, (float)p_76986_6_+0.5F);
        
		if(is != null && is.getItem() == ItemsCore.record_secret && tile.recordCooldownTime > 0)
		{
			GL11.glScalef(1.0F-(upperIndex/60F) , 1.0F-((upperIndex1)/40F), 1.0F-((20-upperIndex)/60F));
		}
        this.bindTexture(textures);
        this.model.renderAll();
        GL11.glPopMatrix();
        
    	GL11.glPushMatrix(); 
    	if(is != null && is.getItem() == ItemsCore.record_secret && tile.recordCooldownTime > 0)
    		MiscUtils.renderItemStack_Full(tile.getStackInSlot(1),p_76986_1_.xCoord+0.5D,p_76986_1_.yCoord+10D , p_76986_1_.zCoord+0.5D, p_76986_2_, p_76986_4_, p_76986_6_, rotation,0F, 1, 1, 1, 0.5F, 0.65F-(upperIndex1)/40F,0.5F);
    	else
    		MiscUtils.renderItemStack_Full(tile.getStackInSlot(1),p_76986_1_.xCoord+0.5D,p_76986_1_.yCoord+10D , p_76986_1_.zCoord+0.5D, p_76986_2_, p_76986_4_, p_76986_6_, rotation,0F, 1, 1, 1, 0.5F, 0.65F,0.5F);
    	GL11.glPopMatrix();
    	RenderHelper.enableStandardItemLighting();
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