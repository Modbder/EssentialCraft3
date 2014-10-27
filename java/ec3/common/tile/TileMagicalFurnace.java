package ec3.common.tile;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.relauncher.Side;
import ec3.api.ITEHasMRU;
import ec3.common.block.BlocksCore;
import ec3.common.item.ItemBoundGem;
import ec3.common.item.ItemsCore;
import ec3.common.mod.EssentialCraftCore;
import ec3.utils.common.ECUtils;
import DummyCore.Utils.Coord3D;
import DummyCore.Utils.DataStorage;
import DummyCore.Utils.DummyData;
import DummyCore.Utils.ITEHasGameData;
import DummyCore.Utils.MathUtils;
import DummyCore.Utils.MiscUtils;
import net.minecraft.enchantment.EnchantmentData;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.play.server.S07PacketRespawn;
import net.minecraft.network.play.server.S1DPacketEntityEffect;
import net.minecraft.potion.PotionEffect;
import net.minecraft.server.MinecraftServer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.util.MathHelper;
import net.minecraft.world.Teleporter;
import net.minecraft.world.WorldProvider;
import net.minecraft.world.WorldServer;
import net.minecraftforge.common.ForgeHooks;

public class TileMagicalFurnace extends TileMRUGeneric
{
	 public int progressLevel;
	 
	 public TileMagicalFurnace()
	 {
		 this.setSlotsNum(1);
	 }
	 
	@Override
    public void readFromNBT(NBTTagCompound par1NBTTagCompound)
    {
    	super.readFromNBT(par1NBTTagCompound);
    	progressLevel = par1NBTTagCompound.getInteger("progress");
    }
    
    @Override
    public void writeToNBT(NBTTagCompound par1NBTTagCompound)
    {
    	super.writeToNBT(par1NBTTagCompound);
    	par1NBTTagCompound.setInteger("progress", progressLevel);
    }
    
    @Override
    public void updateEntity() 
    {
    	this.maxMRU = 50000;
    	super.updateEntity();
    	this.spawnParticles();
		ECUtils.mruIn(this, 0);
		ECUtils.spawnMRUParticles(this, 0);
		smelt();
		IInventory inv = this;
		int slotNum = 0;
		TileEntity tile = this;
		if(inv.getStackInSlot(slotNum) != null && inv.getStackInSlot(slotNum).getItem() instanceof ItemBoundGem && inv.getStackInSlot(slotNum).getTagCompound() != null)
		{
			ItemStack s = inv.getStackInSlot(slotNum);
			int[] o = ItemBoundGem.getCoords(s);
			if(MathUtils.getDifference(tile.xCoord, o[0]) <= 16 && MathUtils.getDifference(tile.yCoord, o[1]) <= 16 && MathUtils.getDifference(tile.zCoord, o[2]) <= 16)
			{
    			if(tile.getWorldObj().getTileEntity(o[0], o[1], o[2]) != null && tile.getWorldObj().getTileEntity(o[0], o[1], o[2]) instanceof ITEHasMRU)
    			{
    				this.setBalance(((ITEHasMRU) tile.getWorldObj().getTileEntity(o[0], o[1], o[2])).getBalance());
    			}
    		}
		}
    }
    
    public boolean isStructureCorrect()
    {
    	boolean flag = true;
    	for(int x = -2; x <= 2; ++x)
    	{
        	for(int z = -2; z <= 2; ++z)
        	{
        		flag = this.worldObj.getBlock(xCoord+x, yCoord-1, zCoord+z) == BlocksCore.voidStone;
        		if(!flag)
        		{
        			
        			return false;
        		}
        	}
    	}
    	flag = this.worldObj.getBlock(xCoord+2, yCoord, zCoord+2) == BlocksCore.voidStone &&
    			this.worldObj.getBlock(xCoord-2, yCoord, zCoord+2) == BlocksCore.voidStone &&  
    			this.worldObj.getBlock(xCoord+2, yCoord, zCoord-2) == BlocksCore.voidStone &&
    			this.worldObj.getBlock(xCoord-2, yCoord, zCoord-2) == BlocksCore.voidStone &&
    			this.worldObj.getBlock(xCoord+2, yCoord+1, zCoord+2) == BlocksCore.heatGenerator &&
    			this.worldObj.getBlock(xCoord-2, yCoord+1, zCoord+2) == BlocksCore.heatGenerator &&  
    			this.worldObj.getBlock(xCoord+2, yCoord+1, zCoord-2) == BlocksCore.heatGenerator &&
    	    	this.worldObj.getBlock(xCoord-2, yCoord+1, zCoord-2) == BlocksCore.heatGenerator;
    	return flag;
    }
    
    public void smelt()
    {
    	ItemStack smeltingStack = this.getSmeltingStack();
    	EntityItem smeltingItem = this.getSmeltingItem();
    	if(this.isStructureCorrect() && this.getMRU() > 0 && smeltingStack != null && smeltingItem != null)
    	{
    		if(this.progressLevel == 0)
    			this.worldObj.playSoundEffect(xCoord+0.5, yCoord+0.5, zCoord+0.5, "fire.fire", 1.0F, 1);
    		ItemStack mainSmelting = smeltingStack.copy();
    		++this.progressLevel;
    		this.setMRU(this.getMRU() - 25);
    		if(this.progressLevel >= 20)
    		{
    			this.progressLevel = 0;
    			this.worldObj.playSoundEffect(xCoord+0.5, yCoord+0.5, zCoord+0.5, "liquid.lavapop", 1.0F, 1);
    			ItemStack s = FurnaceRecipes.smelting().getSmeltingResult(mainSmelting);
    			s.stackSize += 1;
    			if(!this.worldObj.isRemote)
    			{
    				--smeltingItem.getEntityItem().stackSize;
    				if(smeltingItem.getEntityItem().stackSize <= 0)
    				{
    					smeltingItem.setPosition(0, 0, 0);
    					smeltingItem.setDead();
    				}
    			}
    			if(!this.worldObj.isRemote && this.worldObj.rand.nextFloat() <= 0.3F)
    			{
        			EntityItem smelted = new EntityItem(this.worldObj,this.xCoord-1.5,this.yCoord+2.15,this.zCoord-1.5,s.copy());
    				this.worldObj.spawnEntityInWorld(smelted);
    				smelted.motionX = 0;
    				smelted.motionY = 0;
    				smelted.motionZ = 0;
    			}
    			EntityItem smelted = new EntityItem(this.worldObj,this.xCoord+2.5,this.yCoord+2.15,this.zCoord+2.5,s.copy());
    			if(!this.worldObj.isRemote)
    				this.worldObj.spawnEntityInWorld(smelted);
				smelted.motionX = 0;
				smelted.motionY = 0;
				smelted.motionZ = 0;
    			if(!this.worldObj.isRemote && this.worldObj.rand.nextFloat() <= 0.1F)
    			{
        			EntityItem slag = new EntityItem(this.worldObj,this.xCoord-1.5,this.yCoord+2.15,this.zCoord+2.5,new ItemStack(ItemsCore.magicalSlag));
        				this.worldObj.spawnEntityInWorld(slag);
        				smelted.motionX = 0;
        				smelted.motionY = 0;
        				smelted.motionZ = 0;
    			}
    			EntityItem slag = new EntityItem(this.worldObj,this.xCoord+2.5,this.yCoord+2.15,this.zCoord-1.5,new ItemStack(ItemsCore.magicalSlag));
    			if(!this.worldObj.isRemote)
    				this.worldObj.spawnEntityInWorld(slag);
				smelted.motionX = 0;
				smelted.motionY = 0;
				smelted.motionZ = 0;
    			s.stackSize = 0;
    			s = null;
    			
    			
    		}
    	}else
    	{
    		this.progressLevel = 0;
    	}
    }
    
    public EntityItem getSmeltingItem()
    {
    	EntityItem ret = null;
    	List<EntityItem> l = this.worldObj.getEntitiesWithinAABB(EntityItem.class, AxisAlignedBB.getBoundingBox(xCoord, yCoord, zCoord, xCoord+1, yCoord+2, zCoord+1));
    	if(!l.isEmpty())
    	{
    		List<ItemStack> canBeSmelted = new ArrayList();
    		for(int i = 0; i < l.size(); ++i)
    		{
    			EntityItem item = l.get(i);
    			if(FurnaceRecipes.smelting().getSmeltingResult(item.getEntityItem()) != null)
    				return item;
    		}
    	}
    	return ret;
    }
    
    public ItemStack getSmeltingStack()
    {
    	ItemStack ret = null;
    	List<EntityItem> l = this.worldObj.getEntitiesWithinAABB(EntityItem.class, AxisAlignedBB.getBoundingBox(xCoord, yCoord, zCoord, xCoord+1, yCoord+2, zCoord+1));
    	if(!l.isEmpty())
    	{
    		List<ItemStack> canBeSmelted = new ArrayList();
    		for(int i = 0; i < l.size(); ++i)
    		{
    			EntityItem item = l.get(i);
    			if(FurnaceRecipes.smelting().getSmeltingResult(item.getEntityItem()) != null)
    				canBeSmelted.add(item.getEntityItem());
    		}
    		if(!canBeSmelted.isEmpty())
    		{
    			ret = canBeSmelted.get(0);
    		}
    	}
    	return ret;
    }
    
    public void spawnParticles()
    {
    	if(this.isStructureCorrect())
    	{
    		//for(int i = 0; i < 100; ++i)
    		{
    			EssentialCraftCore.proxy.spawnParticle("cSpellFX", xCoord+0.5F+MathUtils.randomFloat(this.worldObj.rand)*3, yCoord, zCoord+0.5F+MathUtils.randomFloat(this.worldObj.rand)*3, 0,2, 0);
    		}
    	}
    }
	
}
