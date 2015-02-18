package ec3.common.tile;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.relauncher.Side;
import ec3.api.ITEHasMRU;
import ec3.common.block.BlocksCore;
import ec3.common.inventory.InventoryMagicFilter;
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
import net.minecraftforge.common.util.ForgeDirection;

public class TileAMINInjector extends TileMINInjector
{
	public int slotnum = 0;
	
	public ForgeDirection getRotation()
	{
		int metadata = this.worldObj.getBlockMetadata(xCoord, yCoord, zCoord);
		if(metadata > 5) metadata -= 6;
		return ForgeDirection.getOrientation(metadata);
	}
	
	public boolean injectItem(IInventory inv, int slotNum)
	{
		if(inv == null)return false;
		
		ItemStack injected = inv.getStackInSlot(slotNum);
		if(this.getStackInSlot(slotNum+64) != null)
		{
			if(injected != null)
			{
				if(injected.stackSize+1 <= injected.getMaxStackSize())
				{
					injected.stackSize += 1;
					this.decrStackSize(slotNum+64, 1);
					return true;
				}
			}else
			{
				ItemStack inserted = this.getStackInSlot(slotNum+64).copy();inserted.stackSize = 1;
				inv.setInventorySlotContents(slotNum, inserted);
				this.decrStackSize(slotNum+64, 1);
				return true;
			}
		}
		return false;
	}
	
	public int getAvaiableSlotsNum()
	{
		IInventory inv = (IInventory) this.worldObj.getTileEntity(xCoord+this.getRotation().offsetX, yCoord+this.getRotation().offsetY, zCoord+this.getRotation().offsetZ);
		if(inv != null)
			return inv.getSizeInventory();
		return 0;
	}
	
	public int[] getAccessibleSlotsFromSide(ForgeDirection side)
	{
		return null;
	}
	
	public int findItemToInject(IInventory inv, int prevSlotnum)
	{
		if(inv == null)return -1;
		for(int i = prevSlotnum; i < inv.getSizeInventory() && i < 64; ++i)
		{
			ItemStack filter = this.getStackInSlot(i);
			{
				ItemStack stk = this.getStackInSlot(i+64);
				ItemStack in = inv.getStackInSlot(i);
				if(filter == null || stk == null)
					continue;
				if(ECUtils.canFilterAcceptItem(new InventoryMagicFilter(filter), stk, filter) && (in == null || (in.stackSize+1 <= in.getMaxStackSize() && in.isItemEqual(stk) && ItemStack.areItemStackTagsEqual(in, stk))))
				{
					return i;
				}
			}
		}
		return -1;
	}

	 public TileAMINInjector()
	 {
		 super();
		 this.setSlotsNum(128);
		 this.setMaxMRU(0);

	 }
	 
	@Override
    public void readFromNBT(NBTTagCompound par1NBTTagCompound)
    {
    	super.readFromNBT(par1NBTTagCompound);
    }
    
    @Override
    public void writeToNBT(NBTTagCompound par1NBTTagCompound)
    {
    	super.writeToNBT(par1NBTTagCompound);
    }
    
    @Override
    public void updateEntity() 
    {
    	super.updateEntity();
		if(!this.worldObj.isBlockIndirectlyGettingPowered(xCoord, yCoord, zCoord))
			{
				IInventory inv = (IInventory) this.worldObj.getTileEntity(xCoord+this.getRotation().offsetX, yCoord+this.getRotation().offsetY, zCoord+this.getRotation().offsetZ);
				if(inv != null)
					for(int i = 0; i < inv.getSizeInventory(); ++i)
						if(this.findItemToInject(inv,i) != -1)
							this.injectItem(inv, this.findItemToInject(inv,i));
			}
    }

	
}
