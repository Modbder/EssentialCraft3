package ec3.common.tile;

import java.util.ArrayList;
import java.util.Hashtable;

import cpw.mods.fml.common.registry.GameRegistry;
import DummyCore.Utils.MiscUtils;
import DummyCore.Utils.Pair;
import ec3.common.item.ItemInventoryGem;
import ec3.common.mod.EssentialCraftCore;
import ec3.network.PacketNBT;
import ec3.utils.common.ECUtils;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;

public class TileNewMIMInventoryStorage extends TileMRUGeneric{

	public int updateTime = 0;
	ArrayList<Pair<Integer[],IInventory>> counted = new ArrayList<Pair<Integer[],IInventory>>();
	ArrayList<IInventory> countedT = new ArrayList<IInventory>();
	public ArrayList<ItemStack> items = new ArrayList<ItemStack>();
	ArrayList<EntityPlayerMP> plrs = new ArrayList<EntityPlayerMP>();
	boolean requireSync = false;
	
	public TileNewMIMInventoryStorage()
	{
		this.setMaxMRU(0);
		this.setSlotsNum(6*9);
	}
	
	@Override
	public int[] getOutputSlots() {
		return new int[0];
	}
	
	/**
	 * 
	 * @return A full list of all Inventories available for the device. Will only return valid tiles of existing blocks
	 */
	public ArrayList<IInventory> getInventories()
	{
		ArrayList<IInventory> retLst = new ArrayList<IInventory>();
		
		for(Pair<Integer[],IInventory> p : counted)
		{
			if(this.worldObj.blockExists(p.obj1[0], p.obj1[1], p.obj1[2]) && this.worldObj.getTileEntity(p.obj1[0], p.obj1[1], p.obj1[2]) instanceof IInventory)
				retLst.add(p.getSecond());
		}
		
		return retLst;
	}
	
	/**
	 * Decreases the stack sizes in possible inventories, until either the stacksize was satisfied, or there are no more stacks of that type in inventories. Also calls a full rebuild on inventories and stacks. Has a null check for inventories. Returns a stk.stackSize if the desired stack was not found.
	 * @param stk the stack to pull. Stacksize unbound.
	 * @return 0 if the pull was successful, anything greater than 0, but lower than stk.stackSize if the request was not fully satisfied.
	 */
	public int retrieveStack(ItemStack stk, boolean oreDict, boolean actuallyRetrieve)
	{
		int index = -1;
		for(int i = 0; i < items.size(); ++i)
		{
			ItemStack is = items.get(i);
			if(is != null)
			{
				if((is.isItemEqual(stk) && ItemStack.areItemStackTagsEqual(stk, is)) || (oreDict && ECUtils.oreDictionaryCompare(stk,is)))
				{
					index = i;
					break;
				}
			}
		}
		if(stk.stackSize == 0)
			stk.stackSize = 1;
		
		int ret = stk.stackSize;
		if(index != -1)
		{
			fG:for(int i = 0; i < countedT.size(); ++i)
			{
				if(countedT.get(i) == null)
					continue;
				
				for(int j = 0; j < countedT.get(i).getSizeInventory(); ++j)
				{
					ItemStack s = countedT.get(i).getStackInSlot(j);
					if(s != null && s.isItemEqual(stk) && ItemStack.areItemStackTagsEqual(stk, s) || (oreDict && ECUtils.oreDictionaryCompare(stk,s)))
					{
						if(ret >= s.stackSize)
						{
							ret -= s.stackSize;
							
							if(actuallyRetrieve)
								countedT.get(i).setInventorySlotContents(j, null);
							
							if(ret < 1)
								break fG;
								
							continue;
						}else
						{
							if(actuallyRetrieve)
								countedT.get(i).decrStackSize(j, ret);
							
							ret = 0;
							break fG;
						}
					}
				}
			}
		}
		
		updateTime = 0;
		requireSync = true;
		return ret;
	}
	
	/**
	 * 
	 * @return A list of all items there are.
	 */
	public ArrayList<ItemStack> getAllItems()
	{
		return items;
	}
	
	/**
	 * 
	 * @param namePart - a name part to search the items. Not case-sensitive!
	 * @return A list of items matching the name.
	 */
	public ArrayList<ItemStack> getItemsByName(String namePart)
	{
		ArrayList<ItemStack> retLst = new ArrayList<ItemStack>();
		
		for(int i = 0; i < items.size(); ++i)
		{
			ItemStack stk = items.get(i);
			if(stk.getDisplayName().contains(namePart.toLowerCase()))
				retLst.add(stk);
		}
		
		return retLst;
	}
	
	/**
	 * Tries to add a given ItemStack to all linked inventories. Will not try to increase sizes first - sadly optimization is more important here.
	 * @param is - the itemstack to insert. Can be null, will return false then.
	 * @return true if the ItemStack was inserted, false if not, or particaly not.
	 */
	public boolean insertItemStack(ItemStack is)
	{
		if(is == null)
			return false;
		
		for(int i = 0; i < counted.size(); ++i)
		{
			Integer[] coords = counted.get(i).getFirst();
			IInventory inv = counted.get(i).getSecond();
			
			if(!this.worldObj.blockExists(coords[0], coords[1], coords[2]))
				continue;
			
			if(inv == null)
				continue;
			
			for(int j = 0; j < inv.getSizeInventory(); ++j)
			{
				ItemStack stk = inv.getStackInSlot(j);
				if(stk != null)
				{
					if(stk.isItemEqual(is) && ItemStack.areItemStackTagsEqual(stk, is))
					{
						if(stk.stackSize < stk.getMaxStackSize())
						{
							if(stk.stackSize+is.stackSize <= stk.getMaxStackSize())
							{
								stk.stackSize += is.stackSize;
								return true;
							}else
							{
								int diff = stk.getMaxStackSize() - stk.stackSize;
								stk.stackSize = stk.getMaxStackSize();
								is.stackSize -= diff;
								continue;
							}
						}
					}
				}
			}
			for(int j = 0; j < inv.getSizeInventory(); ++j)
			{
				ItemStack stk = inv.getStackInSlot(j);
				if(stk == null && inv.isItemValidForSlot(j, is))
				{
					inv.setInventorySlotContents(j, is.copy());
					return true;
				}
			}
		}
		
		return false;
	}
	
	/**
	 * Re-calculates all the items there are. I wish there would be a better way to do this. Especially, if I didn't have to do this every tick, since it is pretty resource-intensive. However, if I do not do this every tick then dupes are possible.
	 */
	public void rebuildItems()
	{
		Hashtable<String,Integer> found = new Hashtable<String,Integer>();
		Hashtable<String,ItemStack> foundByID = new Hashtable<String,ItemStack>();
		ArrayList<String> ids = new ArrayList<String>();
		ArrayList<ItemStack> oldCopy = new ArrayList<ItemStack>();
		oldCopy.addAll(items);
		
		items.clear();
		for(int i = 0; i < countedT.size(); ++i)
		{
			if(countedT.get(i) == null)
				continue;
			
			for(int j = 0; j < countedT.get(i).getSizeInventory(); ++j)
			{
				ItemStack stk = countedT.get(i).getStackInSlot(j);
				if(stk != null && stk.stackSize > 0)
				{
					String id = GameRegistry.findUniqueIdentifierFor(stk.getItem()).toString()+"@"+stk.getItemDamage();
					if(stk.getTagCompound() == null || stk.getTagCompound().hasNoTags())
					{
						if(found.containsKey(id))
						{
							found.put(id, found.get(id)+stk.stackSize);
						}else
						{
							found.put(id, stk.stackSize);
							foundByID.put(id, stk);
							ids.add(id);
						}
					}else
					{
						items.add(stk.copy());
					}
				}
			}
		}
		
		for(int i = 0; i < ids.size(); ++i)
		{
			ItemStack cID = foundByID.get(ids.get(i)).copy();
			cID.stackSize = found.get(ids.get(i));
			items.add(cID);
		}
		
		found.clear();
		foundByID.clear();
		ids.clear();
		
		found = null;
		foundByID = null;
		ids = null;
		
		if(items.size() == oldCopy.size())
		{
			for(int i = 0; i < oldCopy.size(); ++i)
			{
				if((items.get(i) == null && oldCopy.get(i) == null) || (items.get(i).isItemEqual(oldCopy.get(i)) && ItemStack.areItemStackTagsEqual(items.get(i), oldCopy.get(i)) && items.get(i).stackSize == oldCopy.get(i).stackSize))
					continue;
				else
					requireSync = true;
			}
			
		}else
			requireSync = true;
		
		packets(false);
	}
	
	/**
	 * Re-create the list of all inventories there are.
	 */
	public void rebuildInventories()
	{
		updateTime = 20;
		counted.clear();
		countedT.clear();
		for(int i = 0; i < 6*9; ++i)
		{
			ItemStack stk = this.getStackInSlot(i);
			if(stk != null && stk.getItem() instanceof ItemInventoryGem && stk.hasTagCompound())
			{
				int[] coords = ItemInventoryGem.getCoords(stk);
				if(coords != null && coords.length > 0)
				{
					int x = coords[0];
					int y = coords[1];
					int z = coords[2];
					int dim = MiscUtils.getStackTag(stk).getInteger("dim");
					if(dim == this.worldObj.provider.dimensionId)
					{
						if(this.worldObj.blockExists(x, y, z) && this.worldObj.getBlock(x, y, z) instanceof ITileEntityProvider)
						{
							TileEntity tile = this.worldObj.getTileEntity(x, y, z);
							if(tile instanceof IInventory)
							{
								if(!(tile instanceof TileNewMIMInventoryStorage) && !countedT.contains(tile))
								{
									countedT.add((IInventory) tile);
									counted.add(new Pair<Integer[],IInventory>(new Integer[]{x,y,z},(IInventory)tile));
								}
							}
						}
					}
				}
			}
		}
	}
	
	public void openInventory(EntityPlayer p) 
	{
		if(!this.worldObj.isRemote)
		{
			this.plrs.add((EntityPlayerMP) p);
			requireSync = true;
		}
	}
	
	public void closeInventory(EntityPlayer p) 
	{
		if(!this.worldObj.isRemote)
		{
			this.plrs.remove(plrs.indexOf((EntityPlayerMP) p));
		}
	}
	
	public void updateEntity() 
	{
		super.updateEntity();
		
		if(updateTime <= 0)
			rebuildInventories();
		else
			--updateTime;
		
		if(!this.worldObj.isRemote)
			rebuildItems();
	}
	
	/**
	 * I wish there would be a better way of doing this, however, vanilla won't sync items in inventories unless they are not opened.
	 */
	public void packets(boolean force)
	{
		for(int i = 0; i < plrs.size(); ++i)
		{
			EntityPlayerMP player = plrs.get(i);
			if(player == null || player.isDead || player.dimension != this.worldObj.provider.dimensionId)
			{
				plrs.remove(i);
			}
		}
		
		
		if(!requireSync && !force)
			return;
		
		if(!plrs.isEmpty())
		{
			NBTTagCompound sentTag = new NBTTagCompound();
			NBTTagList lst = new NBTTagList();
			for(int i = 0; i < items.size(); ++i)
			{
				NBTTagCompound itmTag = new NBTTagCompound();
				items.get(i).writeToNBT(itmTag);
				itmTag.setInteger("stackSize", items.get(i).stackSize);
				lst.appendTag(itmTag);
			}
			sentTag.setTag("items", lst);
			sentTag.setInteger("x", xCoord);
			sentTag.setInteger("y", yCoord);
			sentTag.setInteger("z", zCoord);
			
			PacketNBT packet = new PacketNBT(sentTag).setID(3);
			
			for(int i = 0; i < plrs.size(); ++i)
			{
				EssentialCraftCore.network.sendTo(packet, plrs.get(i));
			}
		}
		requireSync = false;
	}
	
    @Override
    public void setInventorySlotContents(int par1, ItemStack par2ItemStack)
    {
    	super.setInventorySlotContents(par1, par2ItemStack);
    	updateTime = 0;
    	requireSync = true;
    }

}
