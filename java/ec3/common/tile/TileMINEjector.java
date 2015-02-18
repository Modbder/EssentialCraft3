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

public class TileMINEjector extends TileMRUGeneric
{
	
	public ForgeDirection getRotation()
	{
		int metadata = this.worldObj.getBlockMetadata(xCoord, yCoord, zCoord);
		if(metadata > 5) metadata -= 6;
		return ForgeDirection.getOrientation(metadata);
	}
	
	public boolean ejectItem(IInventory inv, int slotNum)
	{
		ItemStack ejected = inv.getStackInSlot(slotNum);
		if(ejected != null)
		{
			if(this.getStackInSlot(1) == null)
			{
				ItemStack inserted = ejected.copy();inserted.stackSize = 1;
				this.setInventorySlotContents(1, inserted);
				inv.decrStackSize(slotNum, 1);
				return true;
			}else
			{
				if(this.getStackInSlot(1).stackSize + 1 <= this.getStackInSlot(1).getMaxStackSize())
				{
					++this.getStackInSlot(1).stackSize;
					inv.decrStackSize(slotNum, 1);
					return true;
				}
			}
		}
		return false;
	}
	
	public int[] getAccessibleSlotsFromSide(ForgeDirection side)
	{
		if(this.worldObj.getTileEntity(xCoord+side.offsetX, yCoord+side.offsetY, zCoord+side.offsetZ) != null)
		{
			TileEntity tile = this.worldObj.getTileEntity(xCoord+side.offsetX, yCoord+side.offsetY, zCoord+side.offsetZ);
			if(tile instanceof ISidedInventory)
			{
				return ((ISidedInventory)tile).getAccessibleSlotsFromSide(side.getOpposite().ordinal());
			}else
				if(tile instanceof IInventory)
				{
					int[] ret = new int[((IInventory)tile).getSizeInventory()];
					for(int i = 0; i < ret.length; ++i)
						ret[i]= i;
					return ret;
				}
			
		}
		return null;
	}
	
	public int findItemToEject(IInventory inv)
	{
		if(this.getAccessibleSlotsFromSide(this.getRotation()) != null && this.getAccessibleSlotsFromSide(this.getRotation()).length > 0)
		{
			ItemStack filter = this.getStackInSlot(0);
			int[] slots = this.getAccessibleSlotsFromSide(this.getRotation());
			for(int i = 0; i < slots.length; ++i)
			{
				
				ItemStack stk = inv.getStackInSlot(slots[i]);
				if(stk == null)
					continue;
				if(filter == null || ECUtils.canFilterAcceptItem(new InventoryMagicFilter(filter), stk, filter))
				{
					return slots[i];
				}
			}
		}
		return -1;
	}

	 public TileMINEjector()
	 {
		 super();
		 this.setSlotsNum(2);
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
			if(this.getAccessibleSlotsFromSide(this.getRotation()) != null)
			{
				IInventory inv = (IInventory) this.worldObj.getTileEntity(xCoord+this.getRotation().offsetX, yCoord+this.getRotation().offsetY, zCoord+this.getRotation().offsetZ);
				if(this.findItemToEject(inv) != -1)
					this.ejectItem(inv, this.findItemToEject(inv));
			}
    }

	
}
