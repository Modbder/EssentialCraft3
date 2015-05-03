package ec3.client.gui.element;

import java.util.Random;

import org.lwjgl.opengl.GL11;

import DummyCore.Utils.MathUtils;
import DummyCore.Utils.MiscUtils;
import ec3.api.ITEHasMRU;
import ec3.common.item.ItemBoundGem;
import ec3.common.mod.EssentialCraftCore;
import net.minecraft.client.Minecraft;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.IInventory;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.util.ResourceLocation;

public class GuiHeightState extends GuiTextField{
	
	public TileEntity tile;
	public int mru;
	
	public GuiHeightState(int i, int j, TileEntity t)
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
		this.drawTexturedModalRect(posX-18, posY-1, 0, 0, 18, 18);
		this.drawTexturedModalRect(posX, posY, 0, 1, 18, 17);
		this.drawTexturedModalRect(posX, posY-16, 0, 1, 18, 16);
		this.drawTexturedModalRect(posX, posY-16-16, 0, 0, 18, 17);
		GL11.glColor3f(0, 0.6F, 0);
		MiscUtils.drawScaledTexturedRect(posX+1, posY, Blocks.grass.getIcon(1, 0), 16, 1, 1);
		GL11.glColor3f(1, 1, 1);
		MiscUtils.drawScaledTexturedRect(posX+1, posY+1, Blocks.dirt.getIcon(0, 0), 16, 2, 1);
		MiscUtils.drawScaledTexturedRect(posX+1, posY+3, Blocks.stone.getIcon(0, 0), 16, 11, 1);
		MiscUtils.drawScaledTexturedRect(posX+1, posY+15, Blocks.bedrock.getIcon(0, 0), 16, 1, 1);
		Random rnd = new Random(143535645L);
		for(int i = 0; i < 10; ++i)
		{
			MiscUtils.drawScaledTexturedRect(posX+1+rnd.nextInt(15), posY+3+rnd.nextInt(11), Blocks.gravel.getIcon(0, 0), 2, 2, 2);
		}
		for(int i = 0; i < 2; ++i)
		{
			MiscUtils.drawScaledTexturedRect(posX+1+rnd.nextInt(15), posY+9+rnd.nextInt(11), Blocks.diamond_block.getIcon(0, 0), 1, 1, 2);
		}
		for(int i = 0; i < 12; ++i)
		{
			MiscUtils.drawScaledTexturedRect(posX+1+rnd.nextInt(15), posY+3+rnd.nextInt(11), Blocks.coal_block.getIcon(0, 0), 1, 1, 2);
		}
		for(int i = 0; i < 6; ++i)
		{
			MiscUtils.drawScaledTexturedRect(posX+1+rnd.nextInt(15), posY+3+rnd.nextInt(11), Blocks.stained_hardened_clay.getIcon(0, 8), 1, 1, 2);
		}
		for(int i = 0; i < 4; ++i)
		{
			MiscUtils.drawScaledTexturedRect(posX+1+rnd.nextInt(15), posY+6+rnd.nextInt(8), Blocks.gold_block.getIcon(0, 8), 1, 1, 2);
		}
		for(int i = 0; i < 8; ++i)
		{
			MiscUtils.drawScaledTexturedRect(posX+1+rnd.nextInt(15), posY+12+rnd.nextInt(2), Blocks.lava.getIcon(0, 0), 1, 1, 2);
		}
		for(int i = 0; i < 8; ++i)
		{
			MiscUtils.drawScaledTexturedRect(posX+1+rnd.nextInt(15), posY+14, Blocks.bedrock.getIcon(0, 0), 1, 1, 2);
		}
		rnd = null;
		int pos = MathUtils.pixelatedTextureSize(tile.yCoord, 256, 50);
		if(pos > 45)pos = 45;
		GL11.glColor3f(0, 1, 0);
		MiscUtils.drawScaledTexturedRect(posX+1, posY+14-pos, Blocks.emerald_block.getIcon(0, 0), 16, 1, 2);
		GL11.glColor3f(1,1,1);
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
		Minecraft.getMinecraft().fontRenderer.drawStringWithShadow(tile.yCoord+"", posX-15-Integer.toString(tile.yCoord).length(), posY+5, 0xffffff);
	}

}
