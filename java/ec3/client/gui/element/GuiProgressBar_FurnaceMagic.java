package ec3.client.gui.element;

import DummyCore.Utils.MathUtils;
import DummyCore.Utils.MiscUtils;
import ec3.common.tile.TileFurnaceMagic;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;

public class GuiProgressBar_FurnaceMagic extends GuiTextField{
	public TileFurnaceMagic tile;
	
	public GuiProgressBar_FurnaceMagic(int i, int j, TileEntity table)
	{
		super(i,j);
		tile = (TileFurnaceMagic) table;
	}

	@Override
	public ResourceLocation getElementTexture() {
		// TODO Auto-generated method stub
		return super.getElementTexture();
	}

	@Override
	public void draw(int posX, int posY) {
		MiscUtils.bindTexture("essentialcraft", "textures/gui/progressBars.png");
		int current = (int) tile.progressLevel;
		if(current == 0)
			current = tile.smeltingLevel;
		
		int max = (int) TileFurnaceMagic.smeltingTime/(tile.getBlockMetadata()/4+1);
		//System.out.println(current);
		int progress = MathUtils.pixelatedTextureSize(current, max, 25);
		this.drawTexturedModalRect(posX, posY, 0, 17, 24, 17);
		this.drawTexturedModalRect(posX, posY, 0, 0, progress, 17);
	}

	@Override
	public int getX() {
		// TODO Auto-generated method stub
		return super.getX();
	}

	@Override
	public int getY() {
		// TODO Auto-generated method stub
		return super.getY();
	}

	@Override
	public void drawText(int posX, int posY) {
		
	}

}
