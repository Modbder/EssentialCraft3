package ec3.client.gui.element;

import org.lwjgl.opengl.GL11;

import DummyCore.Utils.MathUtils;
import DummyCore.Utils.MiscUtils;
import ec3.api.ITEHasMRU;
import ec3.common.item.ItemBoundGem;
import ec3.common.mod.EssentialCraftCore;
import net.minecraft.client.Minecraft;
import net.minecraft.inventory.IInventory;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.util.ResourceLocation;

public class GuiMoonState extends GuiTextField{
	
	public GuiMoonState(int i, int j)
	{
		super(i,j);
	}

	@Override
	public ResourceLocation getElementTexture() {
		// TODO Auto-generated method stub
		return super.getElementTexture();
	}

	@Override
	public void draw(int posX, int posY) {
		this.drawTexturedModalRect(posX, posY, 0, 0, 18, 18);
		MiscUtils.bindTexture("essentialcraft", "textures/gui/gui_moon_phases.png");
		int moonPhase = Minecraft.getMinecraft().theWorld.getMoonPhase();
		this.drawTexturedModalRect(posX+1, posY+1, 16*moonPhase, 0, 16, 16);
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
