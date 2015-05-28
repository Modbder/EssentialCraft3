package ec3.client.render;

import org.lwjgl.opengl.GL11;

import ec3.common.item.ItemOrbitalRemote;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraftforge.client.IItemRenderer;

public class RenderOrbitalRemote implements IItemRenderer{
	protected static RenderItem itemRender = new RenderItem();

	@Override
	public boolean handleRenderType(ItemStack item, ItemRenderType type) {
		return type == ItemRenderType.EQUIPPED_FIRST_PERSON;
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
			if(item.getItem() instanceof ItemOrbitalRemote)
			{
				{
					IIcon icon = item.getIconIndex();
					GL11.glPushMatrix();
					GL11.glRotatef(90, 1, 1, 0);
					ItemRenderer.renderItemIn2D(Tessellator.instance, icon.getMinU(), icon.getMinV(), icon.getMaxU(), icon.getMaxV(), icon.getIconWidth(), icon.getIconHeight(), 0.0625F);
					GL11.glPopMatrix();
					
				}
			}
		}

	}

}
