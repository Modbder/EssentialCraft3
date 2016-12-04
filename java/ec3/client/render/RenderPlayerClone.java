package ec3.client.render;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ec3.common.entity.EntityPlayerClone;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.renderer.entity.RenderBiped;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;

import java.util.Map;

import org.lwjgl.opengl.GL11;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.minecraft.MinecraftProfileTexture;
import com.mojang.authlib.minecraft.MinecraftProfileTexture.Type;

@SideOnly(Side.CLIENT)
public class RenderPlayerClone extends RenderBiped
{
    private static ResourceLocation textures = AbstractClientPlayer.locationStevePng;
    /** The model of the Steve */
    private ModelBiped model;
    public RenderPlayerClone()
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
    	textures = AbstractClientPlayer.locationStevePng;
    	EntityPlayer player = (EntityPlayer) ((EntityPlayerClone)p_76986_1_).playerToAttack;
    	if(player != null)
    	{
    	GameProfile gp = player.getGameProfile();
    	if(gp != null)
    	{
    		Minecraft mine = Minecraft.getMinecraft();
    		Map map = mine.func_152342_ad().func_152788_a(gp);
    		
    		if (map.containsKey(Type.SKIN))
            {
                textures = mine.func_152342_ad().func_152792_a((MinecraftProfileTexture)map.get(Type.SKIN), Type.SKIN);
            }
    	}
    	}

        super.doRender(p_76986_1_, p_76986_2_, p_76986_4_, p_76986_6_, p_76986_8_, p_76986_9_);
    }
}
