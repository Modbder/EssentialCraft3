package ec3.client.gui.element;

import ec3.common.tile.TileecController;
import ec3.common.tile.TileecStateChecker;
import net.minecraft.client.Minecraft;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;

public class GuiResistanceState extends GuiTextField{
	
	public TileEntity tile;
	
	public GuiResistanceState(int i, int j, TileEntity t)
	{
		super(i,j);
		tile = t;
	}

	@Override
	public ResourceLocation getElementTexture() {
		// TODO Auto-generated method stub
		return super.getElementTexture();
	}

	@Override
	public void draw(int posX, int posY) {
		super.draw(posX, posY);
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
		if(tile instanceof TileecStateChecker)
		{
			TileecController controllerTile = (TileecController) ((TileecStateChecker)tile).structureController();
			if(controllerTile != null)
				Minecraft.getMinecraft().fontRenderer.drawString(controllerTile.resistance+" MROV", posX+2, posY+5, 0xffffff, true);
		}
	}

}
