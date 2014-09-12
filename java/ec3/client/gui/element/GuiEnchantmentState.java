package ec3.client.gui.element;

import DummyCore.Utils.MathUtils;
import DummyCore.Utils.MiscUtils;
import ec3.api.ITEHasMRU;
import ec3.common.item.ItemBoundGem;
import ec3.common.mod.EssentialCraftCore;
import ec3.common.tile.TileMagicalEnchanter;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.inventory.IInventory;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.util.ResourceLocation;

public class GuiEnchantmentState extends GuiTextField{
	
	public TileEntity tile;
	public int slotNum;
	
	public GuiEnchantmentState(int i, int j, TileEntity t, int slot)
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
		this.drawTexturedModalRect(posX, posY, 0, 0, 17, 18);
		this.drawTexturedModalRect(posX+17, posY, 1, 0, 16, 18);
		this.drawTexturedModalRect(posX+17+16, posY, 1, 0, 16, 18);
		this.drawTexturedModalRect(posX+17+32, posY, 1, 0, 16, 18);
		this.drawTexturedModalRect(posX+17+48, posY, 1, 0, 16, 18);
		this.drawTexturedModalRect(posX+17+64, posY, 1, 0, 16, 18);
		this.drawTexturedModalRect(posX+17+80, posY, 1, 0, 17, 18);
		this.drawTexturedModalRect(posX+17+97, posY, 0, 0, 17, 18);
		this.drawTexturedModalRect(posX+17+111, posY, 1, 0, 17, 18);
		this.drawText(posX, posY);
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
		FontRenderer fontRenderer = Minecraft.getMinecraft().fontRenderer;
		IInventory inventory = (IInventory) tile;
		if(inventory.getStackInSlot(1) == null)
	    {
	        fontRenderer.drawStringWithShadow("Nothing To Enchant!", posX+4, posY+5, 0xffff00);
	    }else
	        {
	        	if(inventory.getStackInSlot(2) != null)
	        	{
	        		fontRenderer.drawStringWithShadow("Output Must Be Empty!", posX+4, posY+5, 0x00ff00);
	        	}else
	        	{
	        		if(((TileMagicalEnchanter) tile).getMRU() < 100)
	        		{
	        			fontRenderer.drawStringWithShadow("No MRU!", posX+4, posY+5, 0xff0000);
	        		}else
	        		{
	        			if(!inventory.getStackInSlot(1).isItemEnchantable())
	        			{
	        				fontRenderer.drawStringWithShadow("Can't Enchant This!", posX+4, posY+5, 0xff0000);
	        			}else
	        			{
	        				try {
								if(((TileMagicalEnchanter) tile).getEnchantmentsForStack(inventory.getStackInSlot(1)) == null || ((TileMagicalEnchanter) tile).getEnchantmentsForStack(inventory.getStackInSlot(1)).isEmpty())
								{
									fontRenderer.drawStringWithShadow("Can't Enchant This!", posX+4, posY+5, 0xff0000);
								}else
								{
									if(((TileMagicalEnchanter) tile).getMaxPower() <= 0)
									{
										fontRenderer.drawStringWithShadow("No Bookshelves!", posX+4, posY+5, 0xff0000);
									}else
									{
										fontRenderer.drawStringWithShadow("Enchanting Item...", posX+4, posY+5, 0x00ff00);
									}
								}
							} catch (Exception e) {
								fontRenderer.drawStringWithShadow("Can't Enchant This!", posX+4, posY+5, 0xff0000);
								return;
							}
	        			}
	        		}
	        	}
	        }
	    fontRenderer.drawStringWithShadow(((TileMagicalEnchanter) tile).getMaxPower()+"", posX+122, posY+5, 0xffffff);
	}

}
