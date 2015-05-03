package ec3.client.render;

import org.lwjgl.opengl.GL11;

import DummyCore.Utils.MathUtils;
import DummyCore.Utils.MiscUtils;
import ec3.client.model.ModelCrystal;
import ec3.common.item.ItemBlockElementalCrystal;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.IItemRenderer;

public class RenderElementalCrystalAsItem implements IItemRenderer{

    public static final ResourceLocation textures = new ResourceLocation("essentialcraft:textures/special/models/MCrystalTex.png");
    public static final ResourceLocation neutral = new ResourceLocation("essentialcraft:textures/special/models/MCrystalTex.png");
    public static final ResourceLocation fire = new ResourceLocation("essentialcraft:textures/special/models/FCrystalTex.png");
    public static final ResourceLocation water = new ResourceLocation("essentialcraft:textures/special/models/WCrystalTex.png");
    public static final ResourceLocation earth = new ResourceLocation("essentialcraft:textures/special/models/ECrystalTex.png");
    public static final ResourceLocation air = new ResourceLocation("essentialcraft:textures/special/models/ACrystalTex.png");
    public static final ModelCrystal crystal = new ModelCrystal();
	
	@Override
	public boolean handleRenderType(ItemStack item, ItemRenderType type) {
		// TODO Auto-generated method stub
		return item.getItem() instanceof ItemBlockElementalCrystal;
	}

	@Override
	public boolean shouldUseRenderHelper(ItemRenderType type, ItemStack item,
			ItemRendererHelper helper) {
		// TODO Auto-generated method stub
		return item.getItem() instanceof ItemBlockElementalCrystal;
	}

	@Override
	public void renderItem(ItemRenderType type, ItemStack item, Object... data) {
		if(item.getItem() instanceof ItemBlockElementalCrystal)
		{
			if(type == ItemRenderType.INVENTORY || type == ItemRenderType.ENTITY)
			{
				NBTTagCompound tag = MiscUtils.getStackTag(item);
				float size = 100F;
				float fireF = 0F;
				float waterF = 0F;
				float earthF = 0F;
				float airF = 0F;
				
				if(tag.hasKey("size"))
					size = tag.getFloat("size");
				
				if(tag.hasKey("fire"))
					fireF = tag.getFloat("fire");
				if(tag.hasKey("water"))
					waterF = tag.getFloat("water");
				if(tag.hasKey("earth"))
					earthF = tag.getFloat("earth");
				if(tag.hasKey("air"))
					airF = tag.getFloat("air");
				
		        GL11.glPushMatrix();
		        float scale = MathUtils.getPercentage((int) size, 100)/100F;
		        GL11.glTranslatef(0.0F, 1.2F-(1.0F-scale)*1.4F, 0.0F);
		        
		        GL11.glScalef(scale, scale, scale);
		        GL11.glRotatef(180, 1, 0, 0);
		        Minecraft.getMinecraft().renderEngine.bindTexture(textures);
		        RenderHelper.disableStandardItemLighting();
		        GL11.glDisable(GL11.GL_LIGHTING);
		        GL11.glDisable(GL11.GL_CULL_FACE);
		        GL11.glDisable(GL11.GL_BLEND);
		        GL11.glColor4f(1, 1, 1, 0.5F);
		        GL11.glEnable(GL11.GL_BLEND);
		        OpenGlHelper.glBlendFunc(770, 771, 1, 0);
		        crystal.renderModel(0.0625F);
		        
		        Minecraft.getMinecraft().renderEngine.bindTexture(fire);
		        GL11.glColor4f(1, 1, 1, fireF/100F);
		        crystal.renderModel(0.0625F);
		        
		        Minecraft.getMinecraft().renderEngine.bindTexture(water);
		        GL11.glColor4f(1, 1, 1, waterF/100F);
		        crystal.renderModel(0.0625F);
		        
		        Minecraft.getMinecraft().renderEngine.bindTexture(earth);
		        GL11.glColor4f(1, 1, 1, earthF/100F);
		        crystal.renderModel(0.0625F);
		        
		        Minecraft.getMinecraft().renderEngine.bindTexture(air);
		        GL11.glColor4f(1, 1, 1, airF/100F);
		        crystal.renderModel(0.0625F);
		        
		        GL11.glEnable(GL11.GL_LIGHTING);
		        GL11.glPopMatrix();
		        RenderHelper.enableStandardItemLighting();
			}
			
			if(type == ItemRenderType.EQUIPPED || type == ItemRenderType.EQUIPPED_FIRST_PERSON)
			{
				NBTTagCompound tag = MiscUtils.getStackTag(item);
				float size = 100F;
				float fireF = 0F;
				float waterF = 0F;
				float earthF = 0F;
				float airF = 0F;
				
				if(tag.hasKey("size"))
					size = tag.getFloat("size");
				
				if(tag.hasKey("fire"))
					fireF = tag.getFloat("fire");
				if(tag.hasKey("water"))
					waterF = tag.getFloat("water");
				if(tag.hasKey("earth"))
					earthF = tag.getFloat("earth");
				if(tag.hasKey("air"))
					airF = tag.getFloat("air");
				
		        GL11.glPushMatrix();
		        float scale = MathUtils.getPercentage((int) size, 100)/100F;
		        GL11.glTranslatef(0.5F, 1.8F-(1.0F-scale)*1.4F, 0.5F);
		        
		        GL11.glScalef(scale, scale, scale);
		        GL11.glRotatef(180, 1, 0, 0);
		        Minecraft.getMinecraft().renderEngine.bindTexture(textures);
		        RenderHelper.disableStandardItemLighting();
		        GL11.glDisable(GL11.GL_LIGHTING);
		        GL11.glDisable(GL11.GL_CULL_FACE);
		        GL11.glDisable(GL11.GL_BLEND);
		        GL11.glColor4f(1, 1, 1, 0.5F);
		        GL11.glEnable(GL11.GL_BLEND);
		        OpenGlHelper.glBlendFunc(770, 771, 1, 0);
		        crystal.renderModel(0.0625F);
		        
		        Minecraft.getMinecraft().renderEngine.bindTexture(fire);
		        GL11.glColor4f(1, 1, 1, fireF/100F);
		        crystal.renderModel(0.0625F);
		        
		        Minecraft.getMinecraft().renderEngine.bindTexture(water);
		        GL11.glColor4f(1, 1, 1, waterF/100F);
		        crystal.renderModel(0.0625F);
		        
		        Minecraft.getMinecraft().renderEngine.bindTexture(earth);
		        GL11.glColor4f(1, 1, 1, earthF/100F);
		        crystal.renderModel(0.0625F);
		        
		        Minecraft.getMinecraft().renderEngine.bindTexture(air);
		        GL11.glColor4f(1, 1, 1, airF/100F);
		        crystal.renderModel(0.0625F);
		        
		        GL11.glEnable(GL11.GL_LIGHTING);
		        GL11.glPopMatrix();
		        RenderHelper.enableStandardItemLighting();
			}
		}
		
	}

}
