package ec3.common.tile;

import java.util.ArrayList;
import java.util.Hashtable;

import cpw.mods.fml.common.registry.GameRegistry;
import ec3.common.item.ItemBoundGem;
import ec3.common.tile.TileNewMIMCraftingManager.CraftingPattern;
import ec3.utils.common.ECUtils;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;

public class TileNewMIM extends TileMRUGeneric{

	public ArrayList<ItemStack> current = new ArrayList<ItemStack>();
	public ArrayList<CraftingPattern> crafts = new ArrayList<CraftingPattern>();
	public ArrayList<TileNewMIMScreen> screens = new ArrayList<TileNewMIMScreen>();
	public ArrayList<TileNewMIMCraftingManager> managers = new ArrayList<TileNewMIMCraftingManager>();
	int tickTime;
	boolean exporting;
	
	public TileNewMIM()
	{
		this.setSlotsNum(55);
		this.setMaxMRU(5000);
	}
	
	@Override
	public int[] getOutputSlots() {
		return new int[0];
	}
	
	public void updateEntity() 
	{
		super.updateEntity();
		ECUtils.manage(this, 0);
		rebuildAllItems();
		if(tickTime == 0)
		{
			tickTime = 20;
			manageScreens();
			manageCrafters();
			rebuildAllCrafts();
		}else
		{
			if(tickTime == 9)
			{
				for(int i = 35; i < 53; ++i)
				{
					ItemStack gem = this.getStackInSlot(i);
					if(gem != null && gem.getItem() instanceof ItemBoundGem && gem.getTagCompound() != null && gem.getTagCompound().hasKey("pos") && gem.getTagCompound().getInteger("dim") == this.worldObj.provider.dimensionId)
					{
						int[] c = ItemBoundGem.getCoords(gem);
						if(this.worldObj.blockExists(c[0], c[1], c[2]))
						{
							TileEntity t = this.worldObj.getTileEntity(c[0], c[1], c[2]);
							if(t != null && t instanceof TileNewMIMExportNode)
							{
								TileNewMIMExportNode.class.cast(t).exportAllPossibleItems(this);
							}
						}
					}
				}
			}
			if(tickTime == 10)
			{
				for(int i = 17; i < 35; ++i)
				{
					ItemStack gem = this.getStackInSlot(i);
					if(gem != null && gem.getItem() instanceof ItemBoundGem && gem.getTagCompound() != null && gem.getTagCompound().hasKey("pos") && gem.getTagCompound().getInteger("dim") == this.worldObj.provider.dimensionId)
					{
						int[] c = ItemBoundGem.getCoords(gem);
						if(this.worldObj.blockExists(c[0], c[1], c[2]))
						{
							TileEntity t = this.worldObj.getTileEntity(c[0], c[1], c[2]);
							if(t != null && t instanceof TileNewMIMImportNode)
							{
								TileNewMIMImportNode.class.cast(t).importAllPossibleItems(this);
							}
						}
					}
				}
			}
			--tickTime;
		}
	}
	
	public void manageScreens()
	{
		screens.clear();
		for(int i = 7; i < 11; ++i)
		{
			ItemStack gem = this.getStackInSlot(i);
			if(gem != null && gem.getItem() instanceof ItemBoundGem && gem.getTagCompound() != null && gem.getTagCompound().hasKey("pos") && gem.getTagCompound().getInteger("dim") == this.worldObj.provider.dimensionId)
			{
				int[] c = ItemBoundGem.getCoords(gem);
				if(this.worldObj.blockExists(c[0], c[1], c[2]))
				{
					TileEntity t = this.worldObj.getTileEntity(c[0], c[1], c[2]);
					if(t != null && t instanceof TileNewMIMScreen)
					{
						TileNewMIMScreen.class.cast(t).parent = this;
						screens.add(TileNewMIMScreen.class.cast(t));
					}
				}
			}
		}
	}
	
	public void manageCrafters()
	{
		managers.clear();
		for(int i = 11; i < 17; ++i)
		{
			ItemStack gem = this.getStackInSlot(i);
			if(gem != null && gem.getItem() instanceof ItemBoundGem && gem.getTagCompound() != null && gem.getTagCompound().hasKey("pos") && gem.getTagCompound().getInteger("dim") == this.worldObj.provider.dimensionId)
			{
				int[] c = ItemBoundGem.getCoords(gem);
				if(this.worldObj.blockExists(c[0], c[1], c[2]))
				{
					TileEntity t = this.worldObj.getTileEntity(c[0], c[1], c[2]);
					if(t != null && t instanceof TileNewMIMCraftingManager)
					{
						TileNewMIMCraftingManager.class.cast(t).parent = this;
						managers.add(TileNewMIMCraftingManager.class.cast(t));
					}
				}
			}
		}
	}
	
	/**
	 * Tries to insert the given ItemStack to all available inventories.
	 * @param is - the itemstack to insert. Can be null, false will then be returned
	 * @return true if the operation was sucessfull, and the Inserter MUST set the ItemStack to null. False otherwise. Note, that the ItemStack may have been changed(the stacksize)
	 */
	public boolean addItemStackToSystem(ItemStack is)
	{
		if(is == null)
			return false;
		
		for(int i = 1; i < 7; ++i)
		{
			ItemStack gem = this.getStackInSlot(i);
			if(gem != null && gem.getItem() instanceof ItemBoundGem && gem.getTagCompound() != null && gem.getTagCompound().hasKey("pos") && gem.getTagCompound().getInteger("dim") == this.worldObj.provider.dimensionId)
			{
				int[] c = ItemBoundGem.getCoords(gem);
				if(this.worldObj.blockExists(c[0], c[1], c[2]))
				{
					TileEntity t = this.worldObj.getTileEntity(c[0], c[1], c[2]);
					if(t != null && t instanceof TileNewMIMInventoryStorage)
					{
						if(TileNewMIMInventoryStorage.class.cast(t).insertItemStack(is))
							return true;
						else
							continue;
					}
				}
			}
		}
		return false;
	}
	
	public ArrayList<CraftingPattern> getAllCrafts()
	{
		return crafts;
	}
	
	public ArrayList<CraftingPattern> getCraftsByName(String namePart)
	{
		ArrayList<CraftingPattern> retLst = new ArrayList<CraftingPattern>();
		
		for(int i = 0; i < crafts.size(); ++i)
		{
			ItemStack stk = crafts.get(i).result;
			if(stk.getDisplayName().toLowerCase().contains(namePart.toLowerCase()))
				retLst.add(crafts.get(i));
		}
		
		return retLst;
	}
	
	public int craftFromTheSystem(ItemStack is, int times)
	{
		if(is == null || times < 1)
			return 0;
		
		for(int i = 11; i < 17; ++i)
		{
			ItemStack gem = this.getStackInSlot(i);
			if(gem != null && gem.getItem() instanceof ItemBoundGem && gem.getTagCompound() != null && gem.getTagCompound().hasKey("pos") && gem.getTagCompound().getInteger("dim") == this.worldObj.provider.dimensionId)
			{
				int[] c = ItemBoundGem.getCoords(gem);
				if(this.worldObj.blockExists(c[0], c[1], c[2]))
				{
					TileEntity t = this.worldObj.getTileEntity(c[0], c[1], c[2]);
					if(t != null && t instanceof TileNewMIMCraftingManager)
					{
						TileNewMIMCraftingManager cm = TileNewMIMCraftingManager.class.cast(t);
						int crafted = cm.craft(is, times);
						times -= crafted;
						
						if(times <= 0)
							break;
					}
				}
			}
		}
		
		return times;
	}
	
	public int retrieveItemStackFromSystem(ItemStack is, boolean oreDict,boolean doRetrieve)
	{
		if(is == null)
			return 0;
		
		int left = is.stackSize;
		int oldSize = is.stackSize;
		
		for(int i = 1; i < 7; ++i)
		{
			ItemStack gem = this.getStackInSlot(i);
			if(gem != null && gem.getItem() instanceof ItemBoundGem && gem.getTagCompound() != null && gem.getTagCompound().hasKey("pos") && gem.getTagCompound().getInteger("dim") == this.worldObj.provider.dimensionId)
			{
				int[] c = ItemBoundGem.getCoords(gem);
				if(this.worldObj.blockExists(c[0], c[1], c[2]))
				{
					TileEntity t = this.worldObj.getTileEntity(c[0], c[1], c[2]);
					if(t != null && t instanceof TileNewMIMInventoryStorage)
					{
						int newLeft = TileNewMIMInventoryStorage.class.cast(t).retrieveStack(is,oreDict,doRetrieve);
						if(newLeft == 0)
						{
							if(!doRetrieve)
								is.stackSize = oldSize;
							return 0;
						}
						else
						{
							left = newLeft;
							is.stackSize = newLeft;
							continue;
						}
					}
				}
			}
		}
		
		return left;
	}
	
	public boolean isParent(TileNewMIMScreen screen)
	{
		return screens.contains(screen);
	}
	
	public boolean isParent(TileNewMIMCraftingManager manager)
	{
		return managers.contains(manager);
	}
	
	public ArrayList<ItemStack> getItemsByName(String namePart)
	{
		ArrayList<ItemStack> retLst = new ArrayList<ItemStack>();
		
		for(int i = 0; i < current.size(); ++i)
		{
			ItemStack stk = current.get(i);
			if(stk.getDisplayName().toLowerCase().contains(namePart.toLowerCase()))
				retLst.add(stk);
		}
		
		return retLst;
	}
	
	public ArrayList<ItemStack> getAllItems()
	{
		ArrayList<ItemStack> retLst = new ArrayList<ItemStack>();
		retLst.addAll(current);
		return retLst;
	}
	
	public void openAllStorages(EntityPlayer p)
	{
		for(int i = 1; i < 7; ++i)
		{
			ItemStack gem = this.getStackInSlot(i);
			if(gem != null && gem.getItem() instanceof ItemBoundGem && gem.getTagCompound() != null && gem.getTagCompound().hasKey("pos") && gem.getTagCompound().getInteger("dim") == this.worldObj.provider.dimensionId)
			{
				int[] c = ItemBoundGem.getCoords(gem);
				if(this.worldObj.blockExists(c[0], c[1], c[2]))
				{
					TileEntity t = this.worldObj.getTileEntity(c[0], c[1], c[2]);
					if(t != null && t instanceof TileNewMIMInventoryStorage)
					{
						TileNewMIMInventoryStorage.class.cast(t).openInventory(p);
					}
				}
			}
		}
	}
	
	public void closeAllStorages(EntityPlayer p)
	{
		for(int i = 1; i < 7; ++i)
		{
			ItemStack gem = this.getStackInSlot(i);
			if(gem != null && gem.getItem() instanceof ItemBoundGem && gem.getTagCompound() != null && gem.getTagCompound().hasKey("pos") && gem.getTagCompound().getInteger("dim") == this.worldObj.provider.dimensionId)
			{
				int[] c = ItemBoundGem.getCoords(gem);
				if(this.worldObj.blockExists(c[0], c[1], c[2]))
				{
					TileEntity t = this.worldObj.getTileEntity(c[0], c[1], c[2]);
					if(t != null && t instanceof TileNewMIMInventoryStorage)
					{
						TileNewMIMInventoryStorage.class.cast(t).closeInventory(p);
					}
				}
			}
		}
	}
	
	public void rebuildAllItems()
	{
		current.clear();
		Hashtable<String,Integer> allItems = new Hashtable<String,Integer>();
		Hashtable<String,ItemStack> foundByID = new Hashtable<String,ItemStack>();
		ArrayList<String> ids = new ArrayList<String>();
		
		for(int i = 1; i < 7; ++i)
		{
			ItemStack gem = this.getStackInSlot(i);
			if(gem != null && gem.getItem() instanceof ItemBoundGem && gem.getTagCompound() != null && gem.getTagCompound().hasKey("pos") && gem.getTagCompound().getInteger("dim") == this.worldObj.provider.dimensionId)
			{
				int[] c = ItemBoundGem.getCoords(gem);
				if(this.worldObj.blockExists(c[0], c[1], c[2]))
				{
					TileEntity t = this.worldObj.getTileEntity(c[0], c[1], c[2]);
					if(t != null && t instanceof TileNewMIMInventoryStorage)
					{
						ArrayList<ItemStack> items = TileNewMIMInventoryStorage.class.cast(t).getAllItems();
						for(int j = 0; j < items.size(); ++j)
						{
							ItemStack itm = items.get(j);
							if(itm != null)
							{
								String id = GameRegistry.findUniqueIdentifierFor(itm.getItem()).toString() +"@"+ itm.getItemDamage();
								if(itm.getTagCompound() == null || itm.getTagCompound().hasNoTags())
								{
									if(allItems.containsKey(id))
									{
										allItems.put(id, allItems.get(id)+itm.stackSize);
									}else
									{
										allItems.put(id, itm.stackSize);
										foundByID.put(id, itm);
										ids.add(id);
									}
								}else
								{
									current.add(itm.copy());
								}
							}
						}
					}
				}
			}
		}
		
		for(int i = 0; i < ids.size(); ++i)
		{
			ItemStack cID = foundByID.get(ids.get(i)).copy();
			cID.stackSize = allItems.get(ids.get(i));
			current.add(cID);
		}
		
		allItems.clear();
		foundByID.clear();
		ids.clear();
		
		allItems = null;
		foundByID = null;
		ids = null;
	}
	
	public void rebuildAllCrafts()
	{
		crafts.clear();
		for(int i = 11; i < 17; ++i)
		{
			ItemStack gem = this.getStackInSlot(i);
			if(gem != null && gem.getItem() instanceof ItemBoundGem && gem.getTagCompound() != null && gem.getTagCompound().hasKey("pos") && gem.getTagCompound().getInteger("dim") == this.worldObj.provider.dimensionId)
			{
				int[] c = ItemBoundGem.getCoords(gem);
				if(this.worldObj.blockExists(c[0], c[1], c[2]))
				{
					TileEntity t = this.worldObj.getTileEntity(c[0], c[1], c[2]);
					if(t != null && t instanceof TileNewMIMCraftingManager)
					{
						this.crafts.addAll(TileNewMIMCraftingManager.class.cast(t).getAllRecipes());
					}
				}
			}
		}
	}

}
