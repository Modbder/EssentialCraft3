package ec3.client.gui.element;

import org.lwjgl.opengl.GL11;

import DummyCore.Utils.MathUtils;
import DummyCore.Utils.MiscUtils;
import ec3.common.tile.TilePotionSpreader;
import net.minecraft.init.Items;
import net.minecraft.potion.Potion;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.util.ResourceLocation;

public class GuiPotionState extends GuiTextField{
	public TilePotionSpreader tile;
	
	public GuiPotionState(int i, int j, TileEntity t)
	{
		super(i,j);
		tile = (TilePotionSpreader) t;
	}

	@Override
	public ResourceLocation getElementTexture() {
		// TODO Auto-generated method stub
		return super.getElementTexture();
	}

	@Override
	public void draw(int posX, int posY) {
		this.drawTexturedModalRect(posX, posY, 0, 0, 17, 17);
		this.drawTexturedModalRect(posX+19, posY, 1, 0, 17, 17);
		this.drawTexturedModalRect(posX, posY+19, 0, 1, 17, 17);
		this.drawTexturedModalRect(posX+19, posY+19, 1, 1, 17, 17);
		this.drawTexturedModalRect(posX+17, posY, 1, 0, 2, 17);
		this.drawTexturedModalRect(posX+17, posY+19, 1, 1, 2, 17);
		this.drawTexturedModalRect(posX, posY+17, 0, 1, 17, 2);
		this.drawTexturedModalRect(posX+19, posY+17, 1, 1, 17, 2);
		this.drawTexturedModalRect(posX+17, posY+17, 1, 1, 2, 2);
		GL11.glPushMatrix();
		IIcon icon = Items.potionitem.getIconFromDamage(0);
		MiscUtils.drawTexture_Items(posX+9, posY+9, icon, 18, 18, 10);
		if(tile.potionID != -1)
		{
			icon = Items.potionitem.getIconFromDamageForRenderPass(0,0);
			int j = Potion.potionTypes[tile.potionID].getLiquidColor();
			float f = 0F;
			float f1 = 0F;
			float f2 = 0F;
	        f += (float)(j >> 16 & 255) / 255.0F;
	        f1 += (float)(j >> 8 & 255) / 255.0F;
	        f2 += (float)(j >> 0 & 255) / 255.0F;
			GL11.glColor3f(f, f1, f2);
			int scale = MathUtils.pixelatedTextureSize(8-tile.potionUseTime/2, 8, 16);
			int scaledPos = scale - 4;
			if(scaledPos < 0)scaledPos = 0;
			MiscUtils.drawTexture_Items(posX+9, posY+9+scaledPos, icon, 18, 18-scale, 10);
		}
		GL11.glPopMatrix();
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
		
	}

}
