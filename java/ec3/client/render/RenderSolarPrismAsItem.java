package ec3.client.render;

import org.lwjgl.opengl.GL11;

import DummyCore.Utils.MathUtils;
import DummyCore.Utils.MiscUtils;
import ec3.client.model.ModelCrystal;
import ec3.client.model.ModelSolarPrism;
import ec3.common.item.ItemBlockElementalCrystal;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.IItemRenderer;

public class RenderSolarPrismAsItem implements IItemRenderer{

    public static final ResourceLocation textures = new ResourceLocation("essentialcraft:textures/special/models/solarPrism.png");
    public static final ModelSolarPrism prism = new ModelSolarPrism();
	
	@Override
	public boolean handleRenderType(ItemStack item, ItemRenderType type) {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean shouldUseRenderHelper(ItemRenderType type, ItemStack item,
			ItemRendererHelper helper) {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public void renderItem(ItemRenderType type, ItemStack item, Object... data) {
		Minecraft.getMinecraft().renderEngine.bindTexture(textures);
		GL11.glPushMatrix();
			GL11.glEnable(GL11.GL_BLEND);
			if(type == ItemRenderType.INVENTORY)
			{
				GL11.glTranslatef(0, -1, 0);
				GL11.glScalef(0.6F, 1F, 0.6F);
			}
			prism.render(0.0625F);
			GL11.glDisable(GL11.GL_BLEND);
		GL11.glPopMatrix();
	}

}
