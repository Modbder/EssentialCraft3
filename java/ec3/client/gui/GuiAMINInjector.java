package ec3.client.gui;

import DummyCore.Client.GuiCommon;
import DummyCore.Utils.MiscUtils;
import ec3.api.ITEHasMRU;
import ec3.client.gui.element.GuiBalanceState;
import ec3.client.gui.element.GuiHeightState;
import ec3.client.gui.element.GuiMRUGenerated;
import ec3.client.gui.element.GuiMRUState;
import ec3.client.gui.element.GuiMRUStorage;
import ec3.client.gui.element.GuiMoonState;
import ec3.common.tile.TileAMINEjector;
import ec3.common.tile.TileAMINInjector;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.tileentity.TileEntity;

public class GuiAMINInjector extends GuiCommon{

	public GuiAMINInjector(Container c, TileEntity tile) {
		super(c,tile);
	}
	
    public void initGui()
    {
        super.initGui();
	    int k = (this.width - this.xSize) / 2;
	    int l = (this.height - this.ySize) / 2;
        this.buttonList.add(new GuiButton(0,k+82,l+29,12,12,"+"));
        this.buttonList.add(new GuiButton(1,k+82,l+47,12,12,"-"));
    }
    
    public void drawScreen(int p_73863_1_, int p_73863_2_, float p_73863_3_)
    {
    	super.drawScreen(p_73863_1_, p_73863_2_, p_73863_3_);
	    int k = (this.width - this.xSize) / 2;
	    int l = (this.height - this.ySize) / 2;
	    this.fontRendererObj.drawString(((TileAMINInjector)this.genericTile).slotnum+"", k+85, l+40, 0x000000);
	    TileAMINInjector e = (TileAMINInjector) this.genericTile;
	    IInventory inv = (IInventory) genericTile.getWorldObj().getTileEntity(genericTile.xCoord+e.getRotation().offsetX, genericTile.yCoord+e.getRotation().offsetY, genericTile.zCoord+e.getRotation().offsetZ);
	    if(inv == null)
	    {
	    	((GuiButton)this.buttonList.get(0)).enabled = false;
	    	((GuiButton)this.buttonList.get(1)).enabled = false;
	    }else
	    {
	    	if(e.slotnum - 1 < 0)
	    		((GuiButton)this.buttonList.get(1)).enabled = false;
	    	else
	    		((GuiButton)this.buttonList.get(1)).enabled = true;
	    	
	    	if(e.slotnum + 1 >= inv.getSizeInventory())
	    		((GuiButton)this.buttonList.get(0)).enabled = false;
	    	else
	    		((GuiButton)this.buttonList.get(0)).enabled = true;
	    }
    }
	
    protected void actionPerformed(GuiButton par1GuiButton) 
    {
    	TileAMINInjector e = (TileAMINInjector) this.genericTile;
    	IInventory inv = (IInventory) genericTile.getWorldObj().getTileEntity(genericTile.xCoord+e.getRotation().offsetX, genericTile.yCoord+e.getRotation().offsetY, genericTile.zCoord+e.getRotation().offsetZ);
    	
	    if(inv == null)
	    {
	    	return;
	    }else
	    {
	    	if(par1GuiButton.id == 0)
	    	{
	    		if(e.slotnum + 1 >= inv.getSizeInventory())
	    			return;
	    	}else
	    	{
	    		if(e.slotnum - 1 < 0)
	    			return;
	    	}
	    }
    	
		if(par1GuiButton.id == 0)
			e.slotnum += 1;
		else
			e.slotnum -= 1;
    	MiscUtils.handleButtonPress(par1GuiButton.id, this.getClass(), par1GuiButton.getClass(), this.mc.thePlayer, genericTile.xCoord, genericTile.yCoord, genericTile.zCoord);
    }

}
