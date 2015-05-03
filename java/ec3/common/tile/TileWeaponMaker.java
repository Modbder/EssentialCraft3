package ec3.common.tile;

import DummyCore.Utils.MiscUtils;
import ec3.api.GunRegistry;
import ec3.api.GunRegistry.GunMaterial;
import ec3.api.GunRegistry.LenseMaterial;
import ec3.api.GunRegistry.ScopeMaterial;
import ec3.common.item.ItemGenericEC3;
import ec3.common.item.ItemGun;
import ec3.common.item.ItemMRUStorageNBTTag;
import ec3.common.item.ItemsCore;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.oredict.OreDictionary;

public class TileWeaponMaker extends TileMRUGeneric{
	
	public int index;
	public ItemStack previewStack;
	
	public TileWeaponMaker()
	{
		super();
		this.setSlotsNum(19);
	}
	
	@Override
	public void updateEntity()
	{
		super.updateEntity();
	}
	
	@Override
    public void readFromNBT(NBTTagCompound i)
    {
		if(i.hasKey("preview"))
			previewStack = ItemStack.loadItemStackFromNBT(i.getCompoundTag("preview"));
		else
			previewStack = null;
		
		index = i.getInteger("index");
		super.readFromNBT(i);
    }
	
	@Override
    public void writeToNBT(NBTTagCompound i)
    {
		if(previewStack != null)
		{
			NBTTagCompound tag = new NBTTagCompound();
			previewStack.writeToNBT(tag);
			i.setTag("preview", tag);
		}else
			i.removeTag("preview");
		
		i.setInteger("index", index);
    	super.writeToNBT(i);
    }
	
	public String getBase()
	{
		String baseName = "";
		if(this.index == 0)
		{
			for(int i = 5; i < 8; ++i)
			{
				if(this.getStackInSlot(i) != null)
				{
					for(int j = 0; j < GunRegistry.gunMaterials.size(); ++j)
					{
						GunMaterial material = GunRegistry.gunMaterials.get(j);
						if(this.getStackInSlot(i).isItemEqual(material.recipe))
						{
							if(baseName.isEmpty())
							{
								baseName = material.id;
							}else
							{
								if(!baseName.equalsIgnoreCase(material.id))
									return "";
							}
						}
					}
				}else
					return "";
			}
		}
		if(this.index == 1)
		{
			for(int i = 5; i < 9; ++i)
			{
				if(this.getStackInSlot(i) != null)
				{
					for(int j = 0; j < GunRegistry.gunMaterials.size(); ++j)
					{
						GunMaterial material = GunRegistry.gunMaterials.get(j);
						if(this.getStackInSlot(i).isItemEqual(material.recipe))
						{
							if(baseName.isEmpty())
							{
								baseName = material.id;
							}else
							{
								if(!baseName.equalsIgnoreCase(material.id))
									return "";
							}
						}
					}
				}else
					return "";
			}
		}
		if(this.index == 2)
		{
			for(int i = 7; i < 10; ++i)
			{
				if(this.getStackInSlot(i) != null)
				{
					for(int j = 0; j < GunRegistry.gunMaterials.size(); ++j)
					{
						GunMaterial material = GunRegistry.gunMaterials.get(j);
						if(this.getStackInSlot(i).isItemEqual(material.recipe))
						{
							if(baseName.isEmpty())
							{
								baseName = material.id;
							}else
							{
								if(!baseName.equalsIgnoreCase(material.id))
									return "";
							}
						}
					}
				}else
					return "";
			}
		}
		if(this.index == 3)
		{
			for(int i = 6; i < 13; ++i)
			{
				if(this.getStackInSlot(i) != null)
				{
					for(int j = 0; j < GunRegistry.gunMaterials.size(); ++j)
					{
						GunMaterial material = GunRegistry.gunMaterials.get(j);
						if(this.getStackInSlot(i).isItemEqual(material.recipe))
						{
							if(baseName.isEmpty())
							{
								baseName = material.id;
							}else
							{
								if(!baseName.equalsIgnoreCase(material.id))
									return "";
							}
						}
					}
				}else
					return "";
			}
		}
		return baseName;
	}
	
	public String getHandle()
	{
		String handleName = "";
		if(this.index == 0)
		{
			if(this.getStackInSlot(9) != null)
			{
				for(int i = 0; i < GunRegistry.gunMaterials.size(); ++i)
				{
					GunMaterial material = GunRegistry.gunMaterials.get(i);
					if(this.getStackInSlot(9).isItemEqual(material.recipe))
						return material.id;
				}
			}
		}
		if(this.index == 1)
		{
			for(int i = 10; i < 13; ++i)
			{
				if(this.getStackInSlot(i) != null)
				{
					for(int j = 0; j < GunRegistry.gunMaterials.size(); ++j)
					{
						GunMaterial material = GunRegistry.gunMaterials.get(j);
						if(this.getStackInSlot(i).isItemEqual(material.recipe))
						{
							if(handleName.isEmpty())
							{
								handleName = material.id;
							}else
							{
								if(!handleName.equalsIgnoreCase(material.id))
									return "";
							}
						}
					}
				}else
					return "";
			}
		}
		if(this.index == 2)
		{
			for(int i = 12; i < 14; ++i)
			{
				if(this.getStackInSlot(i) != null)
				{
					for(int j = 0; j < GunRegistry.gunMaterials.size(); ++j)
					{
						GunMaterial material = GunRegistry.gunMaterials.get(j);
						if(this.getStackInSlot(i).isItemEqual(material.recipe))
						{
							if(handleName.isEmpty())
							{
								handleName = material.id;
							}else
							{
								if(!handleName.equalsIgnoreCase(material.id))
									return "";
							}
						}
					}
				}else
					return "";
			}
		}
		if(this.index == 3)
		{
			for(int i = 15; i < 19; ++i)
			{
				if(this.getStackInSlot(i) != null)
				{
					for(int j = 0; j < GunRegistry.gunMaterials.size(); ++j)
					{
						GunMaterial material = GunRegistry.gunMaterials.get(j);
						if(this.getStackInSlot(i).isItemEqual(material.recipe))
						{
							if(handleName.isEmpty())
							{
								handleName = material.id;
							}else
							{
								if(!handleName.equalsIgnoreCase(material.id))
									return "";
							}
						}
					}
				}else
					return "";
			}
		}
		return handleName;
	}
	
	public String getDevice()
	{
		String deviceName = "";
		if(this.index == 0)
		{
			if(this.getStackInSlot(8) != null)
			{
				for(int i = 0; i < GunRegistry.gunMaterials.size(); ++i)
				{
					GunMaterial material = GunRegistry.gunMaterials.get(i);
					if(this.getStackInSlot(8).isItemEqual(material.recipe))
						return material.id;
				}
			}
		}
		if(this.index == 1)
		{
			if(this.getStackInSlot(9) != null)
			{
				for(int i = 0; i < GunRegistry.gunMaterials.size(); ++i)
				{
					GunMaterial material = GunRegistry.gunMaterials.get(i);
					if(this.getStackInSlot(9).isItemEqual(material.recipe))
						return material.id;
				}
			}
		}
		if(this.index == 2)
		{
			for(int i = 10; i < 12; ++i)
			{
				if(this.getStackInSlot(i) != null)
				{
					for(int j = 0; j < GunRegistry.gunMaterials.size(); ++j)
					{
						GunMaterial material = GunRegistry.gunMaterials.get(j);
						if(this.getStackInSlot(i).isItemEqual(material.recipe))
						{
							if(deviceName.isEmpty())
							{
								deviceName = material.id;
							}else
							{
								if(!deviceName.equalsIgnoreCase(material.id))
									return "";
							}
						}
					}
				}else
					return "";
			}
		}
		if(this.index == 3)
		{
			for(int i = 13; i < 15; ++i)
			{
				if(this.getStackInSlot(i) != null)
				{
					for(int j = 0; j < GunRegistry.gunMaterials.size(); ++j)
					{
						GunMaterial material = GunRegistry.gunMaterials.get(j);
						if(this.getStackInSlot(i).isItemEqual(material.recipe))
						{
							if(deviceName.isEmpty())
							{
								deviceName = material.id;
							}else
							{
								if(!deviceName.equalsIgnoreCase(material.id))
									return "";
							}
						}
					}
				}else
					return "";
			}
		}
		return deviceName;
	}
	
	public String getLense()
	{
		String lenseName = "";
		if(this.index == 0 || this.index == 1 || this.index == 2)
		{
			if(this.getStackInSlot(3) != null)
			{
				for(int i = 0; i < GunRegistry.lenseMaterials.size(); ++i)
				{
					LenseMaterial material = GunRegistry.lenseMaterials.get(i);
					if(this.getStackInSlot(3).isItemEqual(material.recipe))
						return material.id;
				}
			}
		}
		if(this.index == 3)
		{
			if(this.getStackInSlot(3) != null)
			{
				if(this.getStackInSlot(3).getItem() instanceof ItemGenericEC3 && this.getStackInSlot(3).getItemDamage() == 32)
				{
					for(int i = 4; i < 6; ++i)
					{
						if(this.getStackInSlot(i) != null)
						{
							for(int j = 0; j < GunRegistry.lenseMaterials.size(); ++j)
							{
								LenseMaterial material = GunRegistry.lenseMaterials.get(j);
								if(this.getStackInSlot(i).isItemEqual(material.recipe))
								{
									if(lenseName.isEmpty())
									{
										lenseName = material.id;
									}else
									{
										if(!lenseName.equalsIgnoreCase(material.id))
											return "";
									}
								}
							}
						}else
							return "";
					}
				}
			}
		}
		return lenseName;
	}
	
	public String getScope()
	{
		String scopeName = "";
		if(this.index == 0 || this.index == 1)
		{
			if(this.getStackInSlot(4) != null)
			{
				for(int i = 0; i < GunRegistry.scopeMaterials.size(); ++i)
				{
					ScopeMaterial material = GunRegistry.scopeMaterials.get(i);
					if(this.getStackInSlot(4).isItemEqual(material.recipe))
						return material.id;
				}
			}
		}
		
		if(this.index == 2)
		{
			if(this.getStackInSlot(4) != null)
			{
				for(int i = 0; i < GunRegistry.scopeMaterials.size(); ++i)
				{
					ScopeMaterial material = GunRegistry.scopeMaterials.get(i);
					if(this.getStackInSlot(4).isItemEqual(material.recipe))
					{
						for(int j = 5; j < 7; ++j)
						{
							if(this.getStackInSlot(j) != null)
							{
								for(int k = 0; k < GunRegistry.scopeMaterialsSniper.size(); ++k)
								{
									ScopeMaterial material1 = GunRegistry.scopeMaterialsSniper.get(k);
									if(this.getStackInSlot(j).isItemEqual(material1.recipe))
									{
										if(scopeName.isEmpty())
										{
											scopeName = material1.id;
										}else
										{
											if(!scopeName.equalsIgnoreCase(material1.id))
												return "";
										}
									}
								}
							}else
								return "";
						}
					}
				}
			}
		}
		
		return scopeName;
	}
	
	public boolean areIngridientsCorrect()
	{
		//If output is empty
		if(this.getStackInSlot(0) == null)
		{
			if(this.getStackInSlot(1) != null && OreDictionary.getOreIDs(this.getStackInSlot(1)).length > 0 && OreDictionary.getOreName(OreDictionary.getOreIDs(this.getStackInSlot(1))[0]).equalsIgnoreCase("elementalCore"))
			{
				if(this.getStackInSlot(2) != null && this.getStackInSlot(2).getItem() instanceof ItemMRUStorageNBTTag && this.getStackInSlot(2).getItemDamage() == 1)
				{
					String base = this.getBase();
					String handle = this.getHandle();
					String scope = this.getScope();
					String device = this.getDevice();
					String lense = this.getLense();
					if(index == 0)
					{
						if(!base.isEmpty() && !handle.isEmpty() && !device.isEmpty())
							return true;
					}
					if(index == 1)
					{
						if(!base.isEmpty() && !handle.isEmpty() && !device.isEmpty())
							return true;
					}
					if(index == 2)
					{
						if(!base.isEmpty() && !handle.isEmpty() && !device.isEmpty() && !scope.isEmpty())
							return true;
					}
					if(index == 3)
					{
						if(!base.isEmpty() && !handle.isEmpty() && !device.isEmpty() && !lense.isEmpty())
							return true;
					}
				}
			}
		}
		return false;
	}
	
	public void previewWeapon()
	{
		this.previewStack = null;
		if(this.getStackInSlot(0) == null)
		{
			if(this.getStackInSlot(1) != null && OreDictionary.getOreIDs(this.getStackInSlot(1)).length > 0 && OreDictionary.getOreName(OreDictionary.getOreIDs(this.getStackInSlot(1))[0]).equalsIgnoreCase("elementalCore"))
			{
				if(this.getStackInSlot(2) != null && this.getStackInSlot(2).getItem() instanceof ItemMRUStorageNBTTag && this.getStackInSlot(2).getItemDamage() == 1)
				{
					if(this.index == 0)
						this.previewStack = new ItemStack(ItemsCore.pistol);
					if(this.index == 1)
						this.previewStack = new ItemStack(ItemsCore.rifle);
					if(this.index == 2)
						this.previewStack = new ItemStack(ItemsCore.sniper);
					if(this.index == 3)
						this.previewStack = new ItemStack(ItemsCore.gatling);
					
					String lense = this.getLense();
					String base = this.getBase();
					String handle = this.getHandle();
					String scope = this.getScope();
					String device = this.getDevice();
					NBTTagCompound tag = MiscUtils.getStackTag(previewStack);
					tag.setString("lense", lense);
					tag.setString("base", base);
					tag.setString("handle", handle);
					tag.setString("scope", scope);
					tag.setString("device", device);
				}
			}
		}
	}
	
	public void makeWeapon()
	{
		if(this.areIngridientsCorrect())
		{
			ItemStack result = this.previewStack.copy();
			ItemGun.calculateGunStats(result);
			this.setInventorySlotContents(0, result);
			for(int i = 1; i < this.getSizeInventory(); ++i)
				this.decrStackSize(i, 1);
		}
	}
	
	@Override
	public ItemStack decrStackSize(int par1, int par2) 
	{
		ItemStack is = super.decrStackSize(par1, par2);
		this.markDirty();
		return is;
	}
	
    @Override
    public void setInventorySlotContents(int par1, ItemStack par2ItemStack)
    {
    	super.setInventorySlotContents(par1, par2ItemStack);
    	this.markDirty();
    }
    
    public void markDirty()
    {
    	super.markDirty();
    	this.previewWeapon();
    	this.syncTick = 0;
    }
}
