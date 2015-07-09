package ec3.common.inventory;

import ec3.common.item.ItemBoundGem;
import ec3.common.tile.TileNewMIMCraftingManager;
import ec3.common.tile.TileNewMIMExportNode;
import ec3.common.tile.TileNewMIMImportNode;
import ec3.common.tile.TileNewMIMInventoryStorage;
import ec3.common.tile.TileNewMIMScreen;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;

public class ContainerNewMIM extends Container{
	
	public static class SlotBGTEClassDepenant extends Slot
	{
		public SlotBGTEClassDepenant(IInventory inv, int id,int x, int y,Class<? extends TileEntity> c) {
			super(inv, id, x, y);
			dependant = c;
		}

		Class<? extends TileEntity> dependant;
		
	    public boolean isItemValid(ItemStack stk)
	    {
	    	if(stk == null)
	    		return false;
	    	
	    	if(!(stk.getItem() instanceof ItemBoundGem))
	    		return false;
	    	
	    	if(stk.getTagCompound() == null || !(stk.getTagCompound().hasKey("pos")))
	    		return false;
	    	
	    	TileEntity tile = (TileEntity) this.inventory;
	    	
	    	if(stk.getTagCompound().getInteger("dim") != tile.getWorldObj().provider.dimensionId)
	    		return false;
	    	
	    	int[] coords = ItemBoundGem.getCoords(stk);
	    	
	    	TileEntity t = tile.getWorldObj().getTileEntity(coords[0], coords[1], coords[2]);
	    	
	    	if(t == null)
	    		return false;
	    	
	    	if(!(dependant.isAssignableFrom(t.getClass())))
	    		return false;
	    	
	    	return true;
	    }
	}
	
    private IInventory inv;
    public ContainerNewMIM(InventoryPlayer par1InventoryPlayer, TileEntity par2)
    {
        this.inv = (IInventory) par2;
        this.addSlotToContainer(new SlotBoundEssence(inv, 0, 5, 147));
        
        Class<TileNewMIMInventoryStorage> isc = TileNewMIMInventoryStorage.class;
        Class<TileNewMIMScreen> msc = TileNewMIMScreen.class;
        Class<TileNewMIMCraftingManager> cmc = TileNewMIMCraftingManager.class;
        Class<TileNewMIMExportNode> enc = TileNewMIMExportNode.class;
        Class<TileNewMIMImportNode> inc = TileNewMIMImportNode.class;
        
        this.addSlotToContainer(new SlotBGTEClassDepenant(inv, 1, 28, 20,isc));
        this.addSlotToContainer(new SlotBGTEClassDepenant(inv, 2, 46, 20,isc));
        this.addSlotToContainer(new SlotBGTEClassDepenant(inv, 3, 64, 20,isc));
        this.addSlotToContainer(new SlotBGTEClassDepenant(inv, 4, 28, 38,isc));
        this.addSlotToContainer(new SlotBGTEClassDepenant(inv, 5, 46, 38,isc));
        this.addSlotToContainer(new SlotBGTEClassDepenant(inv, 6, 64, 38,isc));
        
        this.addSlotToContainer(new SlotBGTEClassDepenant(inv, 7, 91, 20,msc));
        this.addSlotToContainer(new SlotBGTEClassDepenant(inv, 8, 109, 20,msc));
        this.addSlotToContainer(new SlotBGTEClassDepenant(inv, 9, 91, 38,msc));
        this.addSlotToContainer(new SlotBGTEClassDepenant(inv, 10, 109, 38,msc));
        
        this.addSlotToContainer(new SlotBGTEClassDepenant(inv, 11, 136, 20,cmc));
        this.addSlotToContainer(new SlotBGTEClassDepenant(inv, 12, 154, 20,cmc));
        this.addSlotToContainer(new SlotBGTEClassDepenant(inv, 13, 172, 20,cmc));
        this.addSlotToContainer(new SlotBGTEClassDepenant(inv, 14, 136, 38,cmc));
        this.addSlotToContainer(new SlotBGTEClassDepenant(inv, 15, 154, 38,cmc));
        this.addSlotToContainer(new SlotBGTEClassDepenant(inv, 16, 172, 38,cmc));
        
        for(int i = 0; i < 18; ++i)
        	this.addSlotToContainer(new SlotBGTEClassDepenant(inv, 17+i, 28+i%9*18, 74+i/9*18,inc));
        
        for(int i = 0; i < 18; ++i)
        	this.addSlotToContainer(new SlotBGTEClassDepenant(inv, 35+i, 28+i%9*18, 128+i/9*18,enc));
        
        int i;

        for (i = 0; i < 3; ++i)
        {
            for (int j = 0; j < 9; ++j)
            {
                this.addSlotToContainer(new Slot(par1InventoryPlayer, j + i * 9 + 9, 18 + j * 18,174+ i * 18));
            }
        }

        for (i = 0; i < 9; ++i)
        {
            this.addSlotToContainer(new Slot(par1InventoryPlayer, i, 18 + i * 18, 232));
        }
    }

	@Override
	public boolean canInteractWith(EntityPlayer p_75145_1_) {
		// TODO Auto-generated method stub
		return inv.isUseableByPlayer(p_75145_1_);
	}

    public ItemStack transferStackInSlot(EntityPlayer p_82846_1_, int p_82846_2_)
    {
        ItemStack itemstack = null;
        Slot slot = (Slot)this.inventorySlots.get(p_82846_2_);

        if (slot != null && slot.getHasStack())
        {
            ItemStack itemstack1 = slot.getStack();
            itemstack = itemstack1.copy();

            if (p_82846_2_ < inv.getSizeInventory())
            {
                if (!this.mergeItemStack(itemstack1, inv.getSizeInventory(), 36+inv.getSizeInventory(), true))
                {
                    if (itemstack1.stackSize == 0)
                    {
                        slot.putStack((ItemStack)null);
                    }
                    slot.onSlotChange(itemstack1, itemstack);
                    return null;
                }

                
            }
            else if (p_82846_2_ > inv.getSizeInventory())
            {
            	for(int i = 0; i < inv.getSizeInventory(); ++i)
            	{
                    if (this.mergeItemStack(itemstack1, i, i+1, false))
                    {
                        if (itemstack1.stackSize == 0)
                        {
                            slot.putStack((ItemStack)null);
                        }
                        return null;
                    }
            	}
            }
            if (p_82846_2_ > inv.getSizeInventory() && p_82846_2_ < 27+inv.getSizeInventory())
            {
                if (!this.mergeItemStack(itemstack1, 27+inv.getSizeInventory(), 36+inv.getSizeInventory(), false))
                {
                    if (itemstack1.stackSize == 0)
                    {
                        slot.putStack((ItemStack)null);
                    }
                    return null;
                }
            }
            else if (p_82846_2_ > 27+inv.getSizeInventory() && p_82846_2_ < 36+inv.getSizeInventory() && !this.mergeItemStack(itemstack1, inv.getSizeInventory(), 27+inv.getSizeInventory(), false))
            {
                if (itemstack1.stackSize == 0)
                {
                    slot.putStack((ItemStack)null);
                }
                return null;
            }
            if (itemstack.stackSize == 0)
            {
                slot.putStack((ItemStack)null);
            }
            if (itemstack1.stackSize == 0)
            {
                slot.putStack((ItemStack)null);
            }
            else
            {
                slot.onSlotChanged();
            }

            if (itemstack1.stackSize == itemstack.stackSize)
            {
                return null;
            }

            slot.onPickupFromSlot(p_82846_1_, itemstack1);
        }

        return itemstack;
    }
}
