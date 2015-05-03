package ec3.client.render;

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
import net.minecraft.entity.item.EntityEnderCrystal;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.AdvancedModelLoader;
import net.minecraftforge.client.model.IModelCustom;

import org.lwjgl.opengl.GL11;

import ec3.api.ITEHasMRU;
import ec3.client.model.ModelCrystal;
import ec3.client.model.ModelFloatingCube;
import ec3.client.model.ModelSolarPrism;
import ec3.common.item.ItemBoundGem;
import ec3.common.tile.TileElementalCrystal;
import ec3.common.tile.TileRayTower;
import ec3.utils.common.ECUtils;

@SideOnly(Side.CLIENT)
public class RenderElementalCrystal extends TileEntitySpecialRenderer
{
    public static final ResourceLocation textures = new ResourceLocation("essentialcraft:textures/special/models/MCrystalTex.png");
    public static final ResourceLocation neutral = new ResourceLocation("essentialcraft:textures/special/models/MCrystalTex.png");
    public static final ResourceLocation fire = new ResourceLocation("essentialcraft:textures/special/models/FCrystalTex.png");
    public static final ResourceLocation water = new ResourceLocation("essentialcraft:textures/special/models/WCrystalTex.png");
    public static final ResourceLocation earth = new ResourceLocation("essentialcraft:textures/special/models/ECrystalTex.png");
    public static final ResourceLocation air = new ResourceLocation("essentialcraft:textures/special/models/ACrystalTex.png");
    public static final ModelCrystal crystal = new ModelCrystal();

    public RenderElementalCrystal()
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
    	
    	TileElementalCrystal crystal_tile = (TileElementalCrystal) p_76986_1_;
    	
    	int metadata = crystal_tile.getBlockMetadata();
    	
        GL11.glPushMatrix();
        float scale = MathUtils.getPercentage((int) crystal_tile.size, 100)/100F;
        
        if(metadata == 1)
        {
	        GL11.glTranslatef((float)p_76986_2_+0.5F, (float)p_76986_4_+1.4F-(1.0F-scale)*1.4F, (float)p_76986_6_+0.5F);
	        GL11.glScalef(scale, scale, scale);
	        GL11.glRotatef(180, 1, 0, 0);
        }
        
        if(metadata == 0)
        {
	        GL11.glTranslatef((float)p_76986_2_+0.5F, (float)p_76986_4_-0.4F+(1.0F-scale)*1.4F, (float)p_76986_6_+0.5F);
	        GL11.glScalef(scale, scale, scale);
	        GL11.glRotatef(0, 1, 0, 0);
        }
        
        if(metadata == 2)
        {
	        GL11.glTranslatef((float)p_76986_2_+0.5F, (float)p_76986_4_+0.5F, (float)p_76986_6_-0.5F+(1.0F-scale)*1.5F);
	        GL11.glScalef(scale, scale, scale);
	        GL11.glRotatef(90, 1, 0, 0);
        }
        
        if(metadata == 4)
        {
	        GL11.glTranslatef((float)p_76986_2_-0.5F+(1.0F-scale)*1.5F, (float)p_76986_4_+0.5F, (float)p_76986_6_+0.5F);
	        GL11.glScalef(scale, scale, scale);
	        GL11.glRotatef(90, 1, 0, 0);
	        GL11.glRotatef(270, 0, 0, 1);
        }
        
        if(metadata == 3)
        {
	        GL11.glTranslatef((float)p_76986_2_+0.5F, (float)p_76986_4_+0.5F, (float)p_76986_6_+1.5F-(1.0F-scale)*1.5F);
	        GL11.glScalef(scale, scale, scale);
	        GL11.glRotatef(-90, 1, 0, 0);
	        
        }
        
        if(metadata == 5)
        {
	        GL11.glTranslatef((float)p_76986_2_+1.5F-(1.0F-scale)*1.5F, (float)p_76986_4_+0.5F, (float)p_76986_6_+0.5F);
	        GL11.glScalef(scale, scale, scale);
	        GL11.glRotatef(90, 1, 0, 0);
	        GL11.glRotatef(90, 0, 0, 1);
        }
        
        this.bindTexture(textures);
        RenderHelper.disableStandardItemLighting();
        GL11.glDisable(GL11.GL_LIGHTING);
        GL11.glDisable(GL11.GL_CULL_FACE);
        GL11.glDisable(GL11.GL_BLEND);
        GL11.glColor4f(1, 1, 1, 0.5F);
        GL11.glEnable(GL11.GL_BLEND);
        OpenGlHelper.glBlendFunc(770, 771, 1, 0);
        crystal.renderModel(0.0625F);
        
        this.bindTexture(fire);
        GL11.glColor4f(1, 1, 1, crystal_tile.fire/100F);
        crystal.renderModel(0.0625F);
        
        this.bindTexture(water);
        GL11.glColor4f(1, 1, 1, crystal_tile.water/100F);
        crystal.renderModel(0.0625F);
        
        this.bindTexture(earth);
        GL11.glColor4f(1, 1, 1, crystal_tile.earth/100F);
        crystal.renderModel(0.0625F);
        
        this.bindTexture(air);
        GL11.glColor4f(1, 1, 1, crystal_tile.air/100F);
        crystal.renderModel(0.0625F);
        
        GL11.glEnable(GL11.GL_LIGHTING);
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
		this.doRender((TileEntity) p_147500_1_, p_147500_2_, p_147500_4_, p_147500_6_, p_147500_8_, 0);
	}
}