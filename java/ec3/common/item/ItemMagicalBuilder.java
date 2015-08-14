package ec3.common.item;

import java.util.List;

import ec3.utils.common.ECUtils;
import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatStyle;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.MovingObjectPosition.MovingObjectType;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import net.minecraft.world.WorldSettings.GameType;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.event.world.BlockEvent.BreakEvent;
import DummyCore.Utils.Coord3D;
import DummyCore.Utils.DummyDistance;
import DummyCore.Utils.MathUtils;
import DummyCore.Utils.MiscUtils;

public class ItemMagicalBuilder extends ItemStoresMRUInNBT
{
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
    public void addInformation(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, List par3List, boolean par4) 
    {
		switch(par1ItemStack.getItemDamage())
		{
			case 0:
			{
				par3List.add(StatCollector.translateToLocal("ec3.txt.fillMode.normal"));
				break;
			}
			case 1:
			{
				par3List.add(StatCollector.translateToLocal("ec3.txt.fillMode.air"));
				break;
			}
			case 2:
			{
				par3List.add(StatCollector.translateToLocal("ec3.txt.fillMode.replaceSelected"));
				break;
			}
			case 3:
			{
				par3List.add(StatCollector.translateToLocal("ec3.txt.fillMode.replaceButSelected"));
				break;
			}
			case 4:
			{
				par3List.add(StatCollector.translateToLocal("ec3.txt.fillMode.replaceAll"));
				break;
			}
		}
		
		if(this.hasFirstPoint(par1ItemStack))
			par3List.add(StatCollector.translateToLocal("ec3.txt.p1")+"| X:"+this.getFirstPoint(par1ItemStack).x+", Y:"+this.getFirstPoint(par1ItemStack).y+", Z:"+this.getFirstPoint(par1ItemStack).z);
		if(this.hasSecondPoint(par1ItemStack))
			par3List.add(StatCollector.translateToLocal("ec3.txt.p2")+"| X:"+this.getSecondPoint(par1ItemStack).x+", Y:"+this.getSecondPoint(par1ItemStack).y+", Z:"+this.getSecondPoint(par1ItemStack).z);
		if(this.hasStoredBlock(par1ItemStack) && this.retrieveStackFromNBT(par1ItemStack) != null)
			par3List.add(StatCollector.translateToLocal("ec3.txt.storedStack")+": "+this.retrieveStackFromNBT(par1ItemStack).getDisplayName());
		
		par3List.add(" ");
		
		super.addInformation(par1ItemStack, par2EntityPlayer, par3List, par4);
    }
	
	protected MovingObjectPosition getMovingObjectPositionFromPlayer(World w, EntityPlayer p, boolean held)
	{
		return super.getMovingObjectPositionFromPlayer(w, p, held);
	}
	
    @SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
    public void getSubItems(Item par1, CreativeTabs par2CreativeTabs, List par3List)
    {
    	for(int i = 0; i < 5; ++i)
    	{
	        for (int var4 = 0; var4 < 1; ++var4)
	        {
	        	ItemStack min = new ItemStack(par1, 1, i);
	        	ECUtils.initMRUTag(min, maxMRU);
	        	ItemStack max = new ItemStack(par1, 1, i);
	        	ECUtils.initMRUTag(max, maxMRU);
	        	ECUtils.getStackTag(max).setInteger("mru", ECUtils.getStackTag(max).getInteger("maxMRU"));
	            par3List.add(min);
	            par3List.add(max);
	        }
    	}
    }
    
    public ItemStack onItemRightClick(ItemStack is, World w, EntityPlayer p)
    {
    	
    	MovingObjectPosition mop = this.getMovingObjectPositionFromPlayer(w, p, p.capabilities.isCreativeMode);
    	
    	if(mop!=null && mop.typeOfHit==MovingObjectType.BLOCK)
    	{
    		if(!p.isSneaking())
    		{
	    		boolean hasPos1 = this.hasFirstPoint(is);
	    		boolean hasPos2 = this.hasSecondPoint(is);
	    		
	    		if(!hasPos1)
	    		{
	    			this.setFirstPoint(is, mop.blockX, mop.blockY, mop.blockZ);
	    			if(p.worldObj.isRemote)
	    				p.addChatMessage(new ChatComponentText("[Magical Builder] First position: "+mop.blockX+","+mop.blockY+","+mop.blockZ).setChatStyle(new ChatStyle().setColor(EnumChatFormatting.DARK_PURPLE)));
	    			return is;
	    		}
	    		
	    		if(!hasPos2)
	    		{
	    			Coord3D first = this.getFirstPoint(is);
	    			Coord3D second = new Coord3D(mop.blockX, mop.blockY, mop.blockZ);
	    			DummyDistance dist = new DummyDistance(first,second);
	    			if(dist.getDistance() > 48)
	    			{
		    			if(p.worldObj.isRemote)
		    				p.addChatMessage(new ChatComponentText("[Magical Builder]The distance between points ("+dist.getDistance()+") is too large, max: 48!").setChatStyle(new ChatStyle().setColor(EnumChatFormatting.DARK_PURPLE)));
		    			return is;
	    			}
	    			this.setSecondPoint(is, mop.blockX, mop.blockY, mop.blockZ);
	    			if(p.worldObj.isRemote)
	    				p.addChatMessage(new ChatComponentText("[Magical Builder] Second position: "+mop.blockX+","+mop.blockY+","+mop.blockZ).setChatStyle(new ChatStyle().setColor(EnumChatFormatting.DARK_PURPLE)));
	    			return is;
	    		}
	    		
	    		if(hasPos1 && hasPos2)
	    		{
	    			if(p.worldObj.isRemote)
	    				p.addChatMessage(new ChatComponentText("[Magical Builder]Both points already set! Shift-rightclick air to reset!").setChatStyle(new ChatStyle().setColor(EnumChatFormatting.DARK_PURPLE)));
	    		}
    		}else
    		{
    			if(this.setStoredStack(is, w, mop.blockX, mop.blockY, mop.blockZ))
    			{
    				if(this.retrieveStackFromNBT(is) != null)
    				{
    					if(p.worldObj.isRemote)
    						p.addChatMessage(new ChatComponentText("[Magical Builder]Set the block to: "+this.retrieveStackFromNBT(is).getDisplayName()).setChatStyle(new ChatStyle().setColor(EnumChatFormatting.DARK_PURPLE)));
    				}
    			}
    		}
    	}else if(mop==null)
    	{
    		if(p.isSneaking())
    		{
    			this.resetPoints(is);
    			
    			if(p.worldObj.isRemote)
					p.addChatMessage(new ChatComponentText("[Magical Builder]Both points reseted!").setChatStyle(new ChatStyle().setColor(EnumChatFormatting.DARK_PURPLE)));
    		}else
    		{
    			if(this.hasFirstPoint(is) && this.hasSecondPoint(is) && ((this.hasStoredBlock(is) && this.retrieveStackFromNBT(is) != null) || is.getItemDamage() == 1))
    			{
    				int setted = this.setAreaToBlock(p, is);
    				if(p.worldObj.isRemote)
    					p.addChatMessage(new ChatComponentText("[Magical Builder]Filled selected area! "+setted+" blocks got replaced!").setChatStyle(new ChatStyle().setColor(EnumChatFormatting.DARK_PURPLE)));
    			}
    			
    		}
    	}
    	
        return is;
    }
	
	public Coord3D getFirstPoint(ItemStack is)
	{
		return new Coord3D(MiscUtils.getStackTag(is).getInteger("p1_x"),MiscUtils.getStackTag(is).getInteger("p1_y"),MiscUtils.getStackTag(is).getInteger("p1_z"));
	}
	
	public Coord3D getSecondPoint(ItemStack is)
	{
		return new Coord3D(MiscUtils.getStackTag(is).getInteger("p2_x"),MiscUtils.getStackTag(is).getInteger("p2_y"),MiscUtils.getStackTag(is).getInteger("p2_z"));
	}
	
	public boolean resetPoints(ItemStack is)
	{
		NBTTagCompound tag = MiscUtils.getStackTag(is);
		if(!tag.hasKey("p1_x") && !tag.hasKey("p2_x"))
			return false;
		
		tag.removeTag("p1_x");
		tag.removeTag("p1_y");
		tag.removeTag("p1_z");
		tag.removeTag("p2_x");
		tag.removeTag("p2_y");
		tag.removeTag("p2_z");
		
		return true;
	}
	
	public boolean hasFirstPoint(ItemStack is)
	{
		return MiscUtils.getStackTag(is).hasKey("p1_x");
	}
	
	public boolean hasSecondPoint(ItemStack is)
	{
		return MiscUtils.getStackTag(is).hasKey("p2_x");
	}
	
	public void setFirstPoint(ItemStack is, int x, int y, int z)
	{
		MiscUtils.getStackTag(is).setInteger("p1_x", x);
		MiscUtils.getStackTag(is).setInteger("p1_y", y);
		MiscUtils.getStackTag(is).setInteger("p1_z", z);
	}
	
	public void setSecondPoint(ItemStack is, int x, int y, int z)
	{
		MiscUtils.getStackTag(is).setInteger("p2_x", x);
		MiscUtils.getStackTag(is).setInteger("p2_y", y);
		MiscUtils.getStackTag(is).setInteger("p2_z", z);
	}
	
	public boolean hasStoredBlock(ItemStack is)
	{
		return MiscUtils.getStackTag(is).hasKey("storedStackTag");
	}
	
	public ItemStack retrieveStackFromNBT(ItemStack is)
	{
		if(!hasStoredBlock(is))
			return null;
		
		return ItemStack.loadItemStackFromNBT(MiscUtils.getStackTag(is).getCompoundTag("storedStackTag"));
	}
	
	public void nullifyStoredStack(ItemStack is)
	{
		MiscUtils.getStackTag(is).removeTag("storedStackTag");
	}
	
	public boolean setStoredStack(ItemStack is, World w, int x, int y, int z)
	{
		if(!w.isAirBlock(x, y, z))
		{
			ItemStack stored = new ItemStack(w.getBlock(x, y, z),1,w.getBlockMetadata(x, y, z));
			NBTTagCompound tag = new NBTTagCompound();
			stored.writeToNBT(tag);
			stored = null;
			MiscUtils.getStackTag(is).setTag("storedStackTag", tag);
			return true;
		}
		return false;
	}
	
	public int findPlayerISSlot(EntityPlayer e, ItemStack is)
	{
		if(is == null)
			return -1;
		
		if(e.capabilities.isCreativeMode)
			return Integer.MAX_VALUE;
		
		for(int i = 0; i < e.inventory.getSizeInventory(); ++i)
		{
			ItemStack stk = e.inventory.getStackInSlot(i);
			if(stk != null && stk.isItemEqual(is))
				return i;
		}
		return -1;
	}
	
	public int decreasePlayerStackInSlot(EntityPlayer e, ItemStack is, int slot)
	{
		if(e.capabilities.isCreativeMode)
			return Integer.MAX_VALUE;
		
		e.inventory.decrStackSize(slot, 1);
		if(e.inventory.getStackInSlot(slot) == null || e.inventory.getStackInSlot(slot).stackSize <= 0)
			return findPlayerISSlot(e,is);
		
		return slot;
	}

	public int setAreaToBlock(EntityPlayer e, ItemStack is)
	{
		Coord3D start = this.getFirstPoint(is);
		Coord3D end = this.getSecondPoint(is);
		int diffX = MathHelper.floor_double(MathUtils.module(end.x-start.x));
		int diffY = MathHelper.floor_double(MathUtils.module(end.y-start.y));
		int diffZ = MathHelper.floor_double(MathUtils.module(end.z-start.z));
		ItemStack setTo = this.retrieveStackFromNBT(is);
		int slotNum = findPlayerISSlot(e,setTo);
		int itemsSet = 0;
		if(is.getItemDamage() == 1)
			slotNum = Integer.MAX_VALUE;
		
		if(is.getItemDamage() == 2 || is.getItemDamage() == 3)
			slotNum = this.hasStoredBlock(is) && setTo != null ? Integer.MAX_VALUE : -1;
		
		if(slotNum != -1)
		{
			for(int x = 0; x <= diffX; ++x)
			{
				int dx = x;
				if(start.x >= end.x)
					dx = MathHelper.floor_double(end.x + x);
				else
					dx = MathHelper.floor_double(start.x + x);
				
				for(int y = 0; y <= diffY; ++y)
				{
					int dy = y;
					if(start.y >= end.y)
						dy = MathHelper.floor_double(end.y + y);
					else
						dy = MathHelper.floor_double(start.y + y);
					
					for(int z = 0; z <= diffZ; ++z)
					{
						int dz = z;
						if(start.z >= end.z)
							dz = (int) (end.z + z);
						else
							dz = (int) (start.z + z);
						
						ItemStack settedTo = setTo != null ? setTo.copy() : new ItemStack(Blocks.air,1,0);
						
						if(is.getItemDamage() == 0)
						{
							if(e.worldObj.isAirBlock(dx, dy, dz))
							{
								if(!e.canPlayerEdit(dx, dy, dz, 0, settedTo))
									continue;
								
								if(!(ECUtils.tryToDecreaseMRUInStorage(e, -25) || this.setMRU(is, -25)))
									return itemsSet;
									
								slotNum = this.decreasePlayerStackInSlot(e, settedTo, slotNum);
								//e.worldObj.setBlock(dx, dy, dz, Block.getBlockFromItem(setTo.getItem()), setTo.getItemDamage(), 3);
								if(ForgeHooks.onPlaceItemIntoWorld(settedTo, e, e.worldObj, dx, dy, dz, 0, 0, 0, 0))
									++itemsSet;
								else
								{
									settedTo.stackSize = 1;
									if(!e.inventory.addItemStackToInventory(settedTo))
										e.dropPlayerItemWithRandomChoice(settedTo, false);
								}
								
								if(slotNum == -1)
									return itemsSet;
							}
						}
						if(is.getItemDamage() == 1)
						{
							if(!e.canPlayerEdit(dx, dy, dz, 0, settedTo))
								continue;
							
							if(!(ECUtils.tryToDecreaseMRUInStorage(e, -250) || this.setMRU(is, -250)))
								return itemsSet;
							
							if(e.worldObj.getBlock(dx, dy, dz).getBlockHardness(e.worldObj, dx, dy, dz) >= 0 && !e.worldObj.isRemote)
							{
								GameType type = GameType.SURVIVAL;
								if(e.capabilities.isCreativeMode)
									type = GameType.CREATIVE;
								if(!e.capabilities.allowEdit)
									type = GameType.ADVENTURE;
								
								BreakEvent be = ForgeHooks.onBlockBreakEvent(e.worldObj, type, (EntityPlayerMP) e, dx, dy, dz);
								if(!be.isCanceled())
								{
									e.worldObj.getBlock(dx, dy, dz).dropBlockAsItem(e.worldObj, dx, dy, dz, e.worldObj.getBlockMetadata(dx, dy, dz), 0);
									e.worldObj.setBlockToAir(dx, dy, dz);
									++itemsSet;
								}
							}
						}
						if(is.getItemDamage() == 2)
						{
							if(!e.canPlayerEdit(dx, dy, dz, 0, settedTo))
								continue;
							
							if(e.worldObj.getBlock(dx, dy, dz).getBlockHardness(e.worldObj, dx, dy, dz) >= 0 && !e.worldObj.isRemote)
							{
								ItemStack worldStack = new ItemStack(e.worldObj.getBlock(dx, dy, dz),1,e.worldObj.getBlockMetadata(dx, dy, dz));
								if(worldStack != null && setTo.isItemEqual(worldStack))
								{
									if(!(ECUtils.tryToDecreaseMRUInStorage(e, -250) || this.setMRU(is, -250)))
										return itemsSet;
									
									GameType type = GameType.SURVIVAL;
									if(e.capabilities.isCreativeMode)
										type = GameType.CREATIVE;
									if(!e.capabilities.allowEdit)
										type = GameType.ADVENTURE;
									
									BreakEvent be = ForgeHooks.onBlockBreakEvent(e.worldObj, type, (EntityPlayerMP) e, dx, dy, dz);
									if(!be.isCanceled())
									{
										e.worldObj.getBlock(dx, dy, dz).dropBlockAsItem(e.worldObj, dx, dy, dz, e.worldObj.getBlockMetadata(dx, dy, dz), 0);
										e.worldObj.setBlockToAir(dx, dy, dz);
										++itemsSet;
									}
								}
								worldStack = null;
							}
						}
						if(is.getItemDamage() == 3)
						{
							if(!e.canPlayerEdit(dx, dy, dz, 0, settedTo))
								continue;
							
							if(e.worldObj.getBlock(dx, dy, dz).getBlockHardness(e.worldObj, dx, dy, dz) >= 0 && !e.worldObj.isRemote)
							{
								ItemStack worldStack = new ItemStack(e.worldObj.getBlock(dx, dy, dz),1,e.worldObj.getBlockMetadata(dx, dy, dz));
								if(worldStack != null && !setTo.isItemEqual(worldStack))
								{
									if(!(ECUtils.tryToDecreaseMRUInStorage(e, -250) || this.setMRU(is, -250)))
										return itemsSet;
									
									GameType type = GameType.SURVIVAL;
									if(e.capabilities.isCreativeMode)
										type = GameType.CREATIVE;
									if(!e.capabilities.allowEdit)
										type = GameType.ADVENTURE;
									
									BreakEvent be = ForgeHooks.onBlockBreakEvent(e.worldObj, type, (EntityPlayerMP) e, dx, dy, dz);
									if(!be.isCanceled())
									{
										e.worldObj.getBlock(dx, dy, dz).dropBlockAsItem(e.worldObj, dx, dy, dz, e.worldObj.getBlockMetadata(dx, dy, dz), 0);
										e.worldObj.setBlockToAir(dx, dy, dz);
										++itemsSet;
									}
								}
								worldStack = null;
							}
						}
						if(is.getItemDamage() == 4)
						{
							if(!e.canPlayerEdit(dx, dy, dz, 0, settedTo))
								continue;
							
							if(e.worldObj.isAirBlock(dx, dy, dz))
							{
								if(!(ECUtils.tryToDecreaseMRUInStorage(e, -25) || this.setMRU(is, -25)))
									return itemsSet;
								
								slotNum = this.decreasePlayerStackInSlot(e, setTo, slotNum);
								
								if(ForgeHooks.onPlaceItemIntoWorld(settedTo, e, e.worldObj, dx, dy, dz, 0, 0, 0, 0))
									++itemsSet;
								else
								{
									settedTo.stackSize = 1;
									if(!e.inventory.addItemStackToInventory(settedTo))
										e.dropPlayerItemWithRandomChoice(settedTo, false);
								}
								
								if(slotNum == -1)
									return itemsSet;
							}else
							{
								if(e.worldObj.getBlock(dx, dy, dz).getBlockHardness(e.worldObj, dx, dy, dz) >= 0 && !e.worldObj.isRemote)
								{
									slotNum = this.decreasePlayerStackInSlot(e, setTo, slotNum);
									
									if(!(ECUtils.tryToDecreaseMRUInStorage(e, -300) || this.setMRU(is, -300)))
										return itemsSet;
									
									GameType type = GameType.SURVIVAL;
									if(e.capabilities.isCreativeMode)
										type = GameType.CREATIVE;
									if(!e.capabilities.allowEdit)
										type = GameType.ADVENTURE;
									
									BreakEvent be = ForgeHooks.onBlockBreakEvent(e.worldObj, type, (EntityPlayerMP) e, dx, dy, dz);
									if(!be.isCanceled())
									{
										e.worldObj.getBlock(dx, dy, dz).dropBlockAsItem(e.worldObj, dx, dy, dz, e.worldObj.getBlockMetadata(dx, dy, dz), 0);
										e.worldObj.setBlock(dx, dy, dz, Block.getBlockFromItem(setTo.getItem()), setTo.getItemDamage(), 3);
										++itemsSet;
									}
									
									if(slotNum == -1)
										return itemsSet;
								}
							}
						}
						settedTo = null;
					}
				}
			}
		}
		return itemsSet;
	}

	
}
