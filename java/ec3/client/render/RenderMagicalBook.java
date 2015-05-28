package ec3.client.render;

import net.minecraft.client.model.ModelBook;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.IItemRenderer;

public class RenderMagicalBook implements IItemRenderer{
	protected static RenderItem itemRender = new RenderItem();

	public static final ResourceLocation bookTextures = new ResourceLocation("essentialcraft","textures/special/models/book.png");
	public static final ModelBook book = new ModelBook();
	
	@Override
	public boolean handleRenderType(ItemStack item, ItemRenderType type) {
		return false;
	}

	@Override
	public boolean shouldUseRenderHelper(ItemRenderType type, ItemStack item,
			ItemRendererHelper helper) {
		return false;
	}

	@Override
	public void renderItem(ItemRenderType type, ItemStack item, Object... data)
	{
		
	}

}
