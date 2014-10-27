package ec3.client.render;

import org.lwjgl.opengl.GL11;

import DummyCore.Utils.MiscUtils;
import ec3.client.model.ModelArmorEC3;
import ec3.common.item.ItemArmorMod;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.IItemRenderer;

public class ArmorRenderer implements IItemRenderer{
	
	public static final ModelArmorEC3 theModel = new ModelArmorEC3(1.0F);

	@Override
	public boolean handleRenderType(ItemStack item, ItemRenderType type) {
		if(item.getItem() instanceof ItemArmorMod)
			return true;
		return false;
	}

	@Override
	public boolean shouldUseRenderHelper(ItemRenderType type, ItemStack item,
			ItemRendererHelper helper) {
		if(item.getItem() instanceof ItemArmorMod)
			return true;
		return false;
	}

	@Override
	public void renderItem(ItemRenderType type, ItemStack item, Object... data) {
		if(item.getItem() instanceof ItemArmorMod)
		{
			ItemArmorMod armor = (ItemArmorMod) item.getItem();
			int atype = armor.armorType;
			String texture = armor.getArmorTexture(item, null, atype, null);
			int index = texture.indexOf(":");
			if(index != -1)
			{
				String modName = texture.substring(0, index);
				String tName = texture.substring(index+1);
				MiscUtils.bindTexture(modName, tName);
                ModelBiped modelbiped = theModel;
                modelbiped.bipedHead.showModel = atype == 0;
                modelbiped.bipedHeadwear.showModel = atype == 0;
                modelbiped.bipedBody.showModel = atype == 1 || atype == 2;
                modelbiped.bipedRightArm.showModel = atype == 1;
                modelbiped.bipedLeftArm.showModel = atype == 1;
                modelbiped.bipedRightLeg.showModel = atype == 2 || atype == 3;
                modelbiped.bipedLeftLeg.showModel = atype == 2 || atype == 3;
                float f5 = 0.0625F;
                if(atype == 0)
                	GL11.glTranslatef(0, 1.2F, 0);
                if(atype == 1)
                	GL11.glTranslatef(0, 2F, 0);
                if(atype == 2)
                	GL11.glTranslatef(0, 2.7F, 0);
                if(atype == 3)
                	GL11.glTranslatef(0, 2.7F, 0);
                GL11.glRotatef(180, 1, 0, 0);
                GL11.glScalef(2F, 2F, 2F);
                modelbiped.render(Minecraft.getMinecraft().thePlayer, 0, 0, 0F, 0F, 0, f5);
			}
		}
	}

}
