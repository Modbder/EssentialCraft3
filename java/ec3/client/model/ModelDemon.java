package ec3.client.model;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;

public class ModelDemon extends ModelBiped{
	
	 public ModelRenderer bipedWings;
	 public ModelRenderer bipedWingsBack;
	 private static final ResourceLocation wingsTexture = new ResourceLocation("essentialcraft","textures/special/models/demon_wings.png");
    public ModelDemon(float scale, float bodyPos, int textureWidth, int textureHeight)
    {
        this.bipedWings = new ModelRenderer(this, 0, 0);
        this.bipedWings.addBox(-16.0F, -4.0F, 2.1F, 32, 16, 0, 0);
        this.bipedWings.setRotationPoint(0.0F, 0.0F + bodyPos, 0.0F);
    }
    
   public void render(Entity p_78088_1_, float p_78088_2_, float p_78088_3_, float p_78088_4_, float p_78088_5_, float p_78088_6_, float p_78088_7_)
   {
	   super.render(p_78088_1_, p_78088_2_, p_78088_3_, p_78088_4_, p_78088_5_, p_78088_6_, p_78088_7_);
	   Minecraft.getMinecraft().renderEngine.bindTexture(wingsTexture);
       GL11.glEnable(GL11.GL_BLEND);
       GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
       this.bipedWings.render(p_78088_7_);
   }
   
   public void setRotationAngles(float p_78087_1_, float p_78087_2_, float p_78087_3_, float p_78087_4_, float p_78087_5_, float p_78087_6_, Entity p_78087_7_)
   {
	   super.setRotationAngles(p_78087_1_, p_78087_2_, p_78087_3_, p_78087_4_, p_78087_5_, p_78087_6_, p_78087_7_);
	   this.bipedWings.rotateAngleX = this.bipedBody.rotateAngleX;
	   this.bipedWings.rotateAngleY = this.bipedBody.rotateAngleY;
	   this.bipedWings.rotateAngleZ = this.bipedBody.rotateAngleZ;
   }
}
