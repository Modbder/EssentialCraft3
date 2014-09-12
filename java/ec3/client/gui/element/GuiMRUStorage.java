package ec3.client.gui.element;

import DummyCore.Utils.MathUtils;
import DummyCore.Utils.MiscUtils;
import ec3.api.ITEHasMRU;
import ec3.common.mod.EssentialCraftCore;
import net.minecraft.util.IIcon;
import net.minecraft.util.ResourceLocation;

public class GuiMRUStorage extends GuiElement{
	private ResourceLocation rec = new ResourceLocation("essentialcraft","textures/gui/mruStorage.png");
	
	public int x;
	public int y;
	public ITEHasMRU tile;
	
	public GuiMRUStorage(int i, int j, ITEHasMRU t)
	{
		x = i;
		y = j;
		tile = t;
	}

	@Override
	public ResourceLocation getElementTexture() {
		// TODO Auto-generated method stub
		return rec;
	}

	@Override
	public void draw(int posX, int posY) {
		this.drawTexturedModalRect(posX, posY, 0, 0, 18, 72);
		int percentageScaled = MathUtils.pixelatedTextureSize(tile.getMRU(), tile.getMaxMRU(), 72);
		IIcon icon = (IIcon) EssentialCraftCore.proxy.getClientIcon("mru");
		MiscUtils.drawTexture(posX+1, posY-1+(74-percentageScaled), icon, 16, percentageScaled-2, 0);
	}

	@Override
	public int getX() {
		// TODO Auto-generated method stub
		return x;
	}

	@Override
	public int getY() {
		// TODO Auto-generated method stub
		return y;
	}

}
