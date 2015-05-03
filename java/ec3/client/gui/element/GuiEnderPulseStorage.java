package ec3.client.gui.element;

import DummyCore.Utils.MathUtils;
import DummyCore.Utils.MiscUtils;
import ec3.api.ITEHasMRU;
import ec3.common.item.ItemBoundGem;
import ec3.common.mod.EssentialCraftCore;
import ec3.common.tile.TileMithrilineFurnace;
import net.minecraft.client.Minecraft;
import net.minecraft.inventory.IInventory;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;

public class GuiEnderPulseStorage extends GuiTextField{
	
	public TileEntity tile;
	private ResourceLocation rec = new ResourceLocation("essentialcraft","textures/gui/mithrilineFurnaceElements.png");
	
	public GuiEnderPulseStorage(int i, int j, TileEntity t)
	{
		super(i,j);
		tile = t;
	}

	@Override
	public ResourceLocation getElementTexture() {
		// TODO Auto-generated method stub
		return rec;
	}

	@Override
	public void draw(int posX, int posY) {
		
		TileMithrilineFurnace furnace = (TileMithrilineFurnace) tile;
		
		float current = furnace.energy;
		float max = furnace.maxEnergy;
		
    	float m = current/max*100;
    	int n = MathHelper.floor_float(m/100*18);
		
		this.drawTexturedModalRect(posX, posY, 0, 14, 18, 18);
		this.drawTexturedModalRect(posX, posY+18-n, 18, 14+(18-n), 18, n);
		
		drawText(posX,posY);
		
		this.drawTexturedModalRect(posX+70, posY-49, 0, 32, 28, 28);
		
		this.drawTexturedModalRect(posX+77, posY-16, 0, 0, 14, 14);
		
		current = furnace.progress;
		max = furnace.reqProgress;
		if(max > 0)
		{
	    	m = current/max*100;
	    	n = MathHelper.floor_float(m/100*14);
	    	this.drawTexturedModalRect(posX+77, posY-16+14-n, 14, (14-n), 14, n);
		}
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
