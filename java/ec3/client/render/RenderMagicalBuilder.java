package ec3.client.render;

import org.lwjgl.opengl.GL11;

import ec3.common.item.ItemMagicalBuilder;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraftforge.client.IItemRenderer;

public class RenderMagicalBuilder implements IItemRenderer{
	protected static RenderItem itemRender = new RenderItem();

	@Override
	public boolean handleRenderType(ItemStack item, ItemRenderType type) {
		return type == ItemRenderType.EQUIPPED_FIRST_PERSON || type == ItemRenderType.INVENTORY;
	}

	@Override
	public boolean shouldUseRenderHelper(ItemRenderType type, ItemStack item,
			ItemRendererHelper helper) {
		return false;
	}

	@Override
	public void renderItem(ItemRenderType type, ItemStack item, Object... data)
	{
		if(item != null)
		{
			if(item.getItem() instanceof ItemMagicalBuilder)
			{
				ItemMagicalBuilder builder = ItemMagicalBuilder.class.cast(item.getItem());
				if(type == ItemRenderType.INVENTORY)
				{
					itemRender.renderItemIntoGUI(Minecraft.getMinecraft().fontRenderer, Minecraft.getMinecraft().renderEngine, item, 0, 0, true);
					if(builder.hasStoredBlock(item))
					{
						if(builder.retrieveStackFromNBT(item) != null)
						{
							ItemStack rendered = builder.retrieveStackFromNBT(item);
							GL11.glPushMatrix();
								
								GL11.glTranslatef(8, 8, 0);
								GL11.glScalef(0.5F, 0.5F, 0.5F);
								itemRender.renderItemIntoGUI(Minecraft.getMinecraft().fontRenderer, Minecraft.getMinecraft().renderEngine, rendered, 0, 0, true);
								GL11.glScalef(2, 2, 2);
								
							GL11.glPopMatrix();
						}
					}
				}else
				{
					IIcon icon = item.getIconIndex();
					GL11.glPushMatrix();
					GL11.glRotatef(90, 1, 1, 0);
					ItemRenderer.renderItemIn2D(Tessellator.instance, icon.getMinU(), icon.getMaxU(), icon.getMinV(), icon.getMaxV(), icon.getIconWidth(), icon.getIconHeight(), 0.0625F);
					GL11.glPopMatrix();
					
				}
			}
		}

	}

}
