package ec3.client.gui.element;

import DummyCore.Client.GuiElement;
import DummyCore.Utils.MathUtils;
import DummyCore.Utils.MiscUtils;
import ec3.api.ITEHasMRU;
import ec3.common.mod.EssentialCraftCore;
import net.minecraft.util.IIcon;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.IFluidTank;

public class GuiFluidTank extends GuiElement{
	private ResourceLocation rec = new ResourceLocation("essentialcraft","textures/gui/mruStorage.png");
	
	public int x;
	public int y;
	public IFluidTank tile;
	
	public GuiFluidTank(int i, int j, IFluidTank t)
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
		this.drawTexturedModalRect(posX, posY, 0, 0, 18, 54);
		this.drawTexturedModalRect(posX, posY+53, 0, 71, 18, 1);
		if(tile != null)
		{
			FluidStack fStk = tile.getFluid();
			if(fStk != null && fStk.amount > 0)
			{
				IIcon icon = fStk.getFluid().getIcon(fStk);
				int scale = MathUtils.pixelatedTextureSize(fStk.amount, tile.getCapacity(), 52);
				MiscUtils.drawTexture(posX+1, posY+1+(52-scale), icon, 16, scale, 1);
			}
		}
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
