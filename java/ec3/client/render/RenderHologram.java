package ec3.client.render;

import DummyCore.Utils.MiscUtils;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.renderer.entity.RenderBiped;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.boss.BossStatus;
import net.minecraft.entity.boss.IBossDisplayData;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import ec3.common.entity.EntityHologram;
import ec3.common.item.ItemsCore;

@SideOnly(Side.CLIENT)
public class RenderHologram extends RenderBiped
{
    private static final ResourceLocation textures = new ResourceLocation("essentialcraft","textures/entities/boss.png");
    /** The model of the enderman */
    private ModelBiped model;
    public RenderHologram()
    {
        super(new ModelBiped(1,0,64,32), 0.5F);
        this.model = (ModelBiped)super.mainModel;
        this.setRenderPassModel(this.model);
    }

    protected void preRenderCallback(EntityLivingBase p_77041_1_, float p_77041_2_) 
    {
    	float s = 1.0F;
    	GL11.glScalef(s, s, s);
    	
    	GL11.glDisable(GL11.GL_ALPHA_TEST);
    	GL11.glEnable(GL11.GL_BLEND);
    	GL11.glBlendFunc(GL11.GL_ONE, GL11.GL_ONE_MINUS_SRC_ALPHA);
    	
    	GL11.glColor4f(1, 1, 1, 0.2F);
    	
    }

    protected ResourceLocation getEntityTexture(Entity p_110775_1_)
    {
        return textures;
    }

    public void doRender(Entity p_76986_1_, double p_76986_2_, double p_76986_4_, double p_76986_6_, float p_76986_8_, float p_76986_9_)
    {
    	BossStatus.setBossStatus((IBossDisplayData) p_76986_1_, true);
    	
        super.doRender(p_76986_1_, p_76986_2_, p_76986_4_, p_76986_6_, p_76986_8_, p_76986_9_);
        
        EntityHologram h = (EntityHologram) p_76986_1_;
        
        int meta = 76;
        
        if(h.attackID == -1)
        	meta = 76;
        
        if(h.attackID == 0)
        	meta = 70;
        
        if(h.attackID == 1)
        	meta = 73;
        
        if(h.attackID == 2)
        	meta = 72;
        
        if(h.attackID == 3)
        	meta = 71;
        
        GL11.glScalef(2,2,2);
        
        MiscUtils.renderItemStack_Full(new ItemStack(ItemsCore.genericItem,1,meta), 0, 0, 0, p_76986_2_/2, p_76986_4_/2, p_76986_6_/2, p_76986_1_.ticksExisted%360, 0, 1, 1, 1, 0, 1.2F, 0);
        
        GL11.glScalef(0.5F, 0.5F, 0.5F);
    }
}