package ec3.common.inventory;

import java.util.UUID;

import DummyCore.Utils.MiscUtils;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;

public class InventoryCraftingFrame implements IInventory{
	
	public ItemStack[] inventory = new ItemStack[10];
	public UUID randomUUID;
	public ItemStack filterStack;
	
	public InventoryCraftingFrame(ItemStack filter)
	{
		if(!filter.hasTagCompound())
		{
			NBTTagCompound theTag = MiscUtils.getStackTag(filter);
			randomUUID = UUID.randomUUID();
			theTag.setString("uniqueID", randomUUID.toString());
		}
		this.readFromNBTTagCompound(MiscUtils.getStackTag(filter));
		filterStack = filter;
	}

	@Override
	public int getSizeInventory() {
		return inventory.length;
	}

	@Override
	public ItemStack getStackInSlot(int slot) {
		return inventory[slot];
	}

	@Override
	public ItemStack decrStackSize(int slot, int amount) {
		if (inventory[slot] == null)
			return null;
		ItemStack returnStack;
		if (inventory[slot].stackSize > amount)
		{
			returnStack = inventory[slot].splitStack(amount);
		}
		else
		{
			returnStack = inventory[slot];
			inventory[slot] = null;
		}
		this.markDirty();
		return returnStack;
	}

	@Override
	public ItemStack getStackInSlotOnClosing(int slot) {
		ItemStack returnStack = getStackInSlot(slot);
		setInventorySlotContents(slot, null);
		return returnStack;
	}

	@Override
	public void setInventorySlotContents(int slot, ItemStack stack) {
		inventory[slot] = stack;
		
	}

	@Override
	public String getInventoryName() {
		return "ec3.inventory.craftingFrame";
	}

	@Override
	public boolean hasCustomInventoryName() {
		return false;
	}

	@Override
	public int getInventoryStackLimit() {
		return 64;
	}

	@Override
	public void markDirty() {
	}

	@Override
	public boolean isUseableByPlayer(EntityPlayer p_70300_1_) {
		return true;
	}

	@Override
	public void openInventory() {
		
	}

	@Override
	public void closeInventory() {
		
	}

	@Override
	public boolean isItemValidForSlot(int slot, ItemStack stack) {
		return true;
	}
	
	public void readFromNBTTagCompound(NBTTagCompound tag)
	{
        NBTTagCompound inventoryTag = ((NBTTagCompound) tag.getTag("inventory"));
        if (inventoryTag == null)
        {
        	return;
        }
        
        if (this.randomUUID == null)
        {
        	randomUUID = UUID.fromString(tag.getString("uniqueID"));
        	//Not actually sure if this can happen, but it is Java, so the more null checks, the better!
        	if (randomUUID == null)
        	{
        		randomUUID = UUID.randomUUID();
        	}
        }
        
        NBTTagList actualInventory = inventoryTag.getTagList("items", 10);
        for (int i = 0; i < actualInventory.tagCount() && i < this.inventory.length; i++)
        {
        	NBTTagCompound indexTag = (NBTTagCompound) actualInventory.getCompoundTagAt(i);
        	int index = indexTag.getInteger("index");
    		try 
    		{
    			inventory[index] = ItemStack.loadItemStackFromNBT(indexTag);
    		} 
    		catch (NullPointerException e)
    		{
    			inventory[index] = null;
    		}
        }

	}
	
    public void writeToNBT(NBTTagCompound tag)
    {
        NBTTagList items = new NBTTagList();

        for (int i = 0; i < this.inventory.length; i++)
        {
            if (this.inventory[i] != null)
            {
                NBTTagCompound indexTag = new NBTTagCompound();
                items.appendTag(indexTag);
                indexTag.setInteger("index", i);
                inventory[i].writeToNBT(indexTag);
            }
        }
        NBTTagCompound inventoryTag = new NBTTagCompound();
        inventoryTag.setTag("items", items);
        tag.setTag("inventory", inventoryTag);
        //Can this ever happen? I guess it only happens on server, so should be impossible, but just in case - here is a NPE check.
        if(this.randomUUID == null)this.randomUUID = UUID.randomUUID();
        tag.setString("uniqueID", this.randomUUID.toString());
    }

}
