package ec3.client.gui.element;

import ec3.api.ITEHasMRU;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;

public class GuiMRUState extends GuiTextField{
	
	public ITEHasMRU tile;
	public int mru;
	
	public GuiMRUState(int i, int j, ITEHasMRU t, int mruToSearch)
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
		this.drawTexturedModalRect(posX, posY, 0, 0, 17, 18);
		this.drawTexturedModalRect(posX+17, posY, 1, 0, 16, 18);
		this.drawTexturedModalRect(posX+17+16, posY, 1, 0, 16, 18);
		this.drawTexturedModalRect(posX+17+32, posY, 1, 0, 16, 18);
		this.drawTexturedModalRect(posX+17+48, posY, 1, 0, 16, 18);
		this.drawTexturedModalRect(posX+17+64, posY, 1, 0, 16, 18);
		this.drawTexturedModalRect(posX+17+80, posY, 1, 0, 16, 18);
		this.drawTexturedModalRect(posX+17+96, posY, 1, 0, 16, 18);
		this.drawTexturedModalRect(posX+17+111, posY, 1, 0, 17, 18);
		drawText(posX,posY);
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
		Minecraft.getMinecraft().fontRenderer.drawString(""+this.tile.getMRU()+"/"+this.tile.getMaxMRU()+" MRU", posX+2, posY+5, 0xffffff, true);
	}

}
