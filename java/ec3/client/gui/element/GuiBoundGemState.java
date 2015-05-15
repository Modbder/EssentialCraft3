package ec3.client.gui.element;

import DummyCore.Utils.MathUtils;
import ec3.api.ITEHasMRU;
import ec3.common.item.ItemBoundGem;
import net.minecraft.client.Minecraft;
import net.minecraft.inventory.IInventory;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;

public class GuiBoundGemState extends GuiTextField{
	
	public TileEntity tile;
	public int slotNum;
	
	public GuiBoundGemState(int i, int j, TileEntity t, int slot)
	{
		super(i,j);
		tile = t;
		slotNum = slot;
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
		IInventory inventory = (IInventory) tile;
        if(inventory.getStackInSlot(slotNum) == null || !(inventory.getStackInSlot(slotNum).getItem() instanceof ItemBoundGem))
        {
        	Minecraft.getMinecraft().fontRenderer.drawString("No Bound Gem!", posX+6, posY+5, 0xff0000, true);
        }else
        {
        	if(inventory.getStackInSlot(slotNum).getTagCompound() == null)
            {
        		Minecraft.getMinecraft().fontRenderer.drawString("Gem Not Bound!", posX+4, posY+5, 0xff0000, true);
            }else
            {
            	int o[] = ItemBoundGem.getCoords(inventory.getStackInSlot(slotNum));
              	if(this.tile.getWorldObj().getTileEntity(o[0], o[1], o[2]) == null)
                {
              		Minecraft.getMinecraft().fontRenderer.drawString("No Tile At Pos!", posX+5, posY+5, 0xff0000, true);
                }else
                {
	               	if(!(this.tile.getWorldObj().getTileEntity(o[0], o[1], o[2]) instanceof ITEHasMRU))
	               	{
	               		Minecraft.getMinecraft().fontRenderer.drawString("Not Magical!", posX+12, posY+5, 0xff0000, true);
	               	}else
	               	{
						if(!(MathUtils.getDifference(tile.xCoord, o[0]) <= 16 && MathUtils.getDifference(tile.yCoord, o[1]) <= 16 && MathUtils.getDifference(tile.zCoord, o[2]) <= 16))
						{
							Minecraft.getMinecraft().fontRenderer.drawString("Not In Range!", posX+8, posY+5, 0xff0000, true);
						}else
						{
	                    	 if(((ITEHasMRU)(tile.getWorldObj().getTileEntity(o[0], o[1], o[2]))).getMRU() <= 0)
	                    	 {
	                    		 Minecraft.getMinecraft().fontRenderer.drawString("No MRU In Tile!", posX+6, posY+5, 0xff0000, true);
	                    	 }else
	                    	 {
	                    		 Minecraft.getMinecraft().fontRenderer.drawString("Working", posX+22, posY+5, 0x00ff00, true);
	                    	 }
						}
	               	}
               	}
            }
        }
	}

}
