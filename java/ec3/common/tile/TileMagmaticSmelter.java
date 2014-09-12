package ec3.common.tile;

import java.util.List;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.Vec3;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fluids.FluidTankInfo;
import net.minecraftforge.fluids.IFluidTank;
import net.minecraftforge.oredict.OreDictionary;
import DummyCore.Utils.Coord3D;
import DummyCore.Utils.DummyDistance;
import DummyCore.Utils.MathUtils;
import ec3.api.ApiCore;
import ec3.api.ITEHasMRU;
import ec3.api.MagicianTableRecipe;
import ec3.api.MagicianTableRecipes;
import ec3.api.RadiatingChamberRecipe;
import ec3.api.RadiatingChamberRecipes;
import ec3.common.block.BlocksCore;
import ec3.common.item.ItemBoundGem;
import ec3.common.item.ItemsCore;
import ec3.utils.common.ECUtils;
import ec3.utils.common.EnumOreColoring;

public class TileMagmaticSmelter extends TileMRUGeneric implements IFluidTank{
	
	public int progressLevel, smeltingLevel;
	public FluidTank lavaTank = new FluidTank(8000);
	
	public TileMagmaticSmelter()
	{
		this.maxMRU = (int) ApiCore.DEVICE_MAX_MRU_GENERIC;
		this.setSlotsNum(8);
	}
	
	@Override
	public void updateEntity()
	{
		super.updateEntity();
		ECUtils.mruIn(this, 0);
		ECUtils.spawnMRUParticles(this, 0);
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
		
		if(!worldObj.isRemote)
		{
			if(FluidContainerRegistry.getFluidForFilledItem(getStackInSlot(1)) != null && this.getStackInSlot(2) == null)
			{
				if(lavaTank.getFluid() == null)
				{
					lavaTank.fill(FluidContainerRegistry.getFluidForFilledItem(getStackInSlot(1)), true);
					setInventorySlotContents(2,consumeItem(getStackInSlot(1)));
					this.decrStackSize(1, 1);
				}
				else if (lavaTank.getFluidAmount() != 8000 && lavaTank.getFluid().isFluidEqual(FluidContainerRegistry.getFluidForFilledItem(getStackInSlot(1))))
				{
					lavaTank.fill(FluidContainerRegistry.getFluidForFilledItem(getStackInSlot(1)), true);
					setInventorySlotContents(2,consumeItem(getStackInSlot(1)));
					this.decrStackSize(1, 1);
				}
			}
		}
		
		ItemStack ore = this.getStackInSlot(3);
		if(ore != null && !this.worldObj.isRemote)
		{
			int[] oreIds = OreDictionary.getOreIDs(ore);
			
			String oreName = "Unknown";
			if(oreIds.length > 0)
				oreName = OreDictionary.getOreName(oreIds[0]);
			int metadata = -1;
			for(int i = 0; i < EnumOreColoring.values().length; ++i)
			{
				EnumOreColoring oreColor = EnumOreColoring.values()[i];
				if(oreName.equalsIgnoreCase(oreColor.oreName))
				{
					metadata = i;
					break;
				}
			}
			if(metadata != -1)
			{
				if(this.getStackInSlot(4) == null)
				{
					if(this.getMRU()-50 >= 0 && this.lavaTank != null && this.lavaTank.getFluid() != null && this.lavaTank.getFluid().getFluid() == FluidRegistry.LAVA && this.lavaTank.getFluidAmount() > 0)
					{
						this.setMRU(this.getMRU()-50);
						if(this.worldObj.rand.nextFloat() <= 0.1F)
							this.drain(1, true);
						this.worldObj.spawnParticle("flame", xCoord+0.5D+MathUtils.randomDouble(this.worldObj.rand)/2.2D, yCoord, zCoord+0.5D+MathUtils.randomDouble(this.worldObj.rand)/2.2D, 0, -0.1D, 0);
						++this.progressLevel;
						if(this.progressLevel >= 20*20)
						{
							this.decrStackSize(3, 1);
							int suggestedStackSize = EnumOreColoring.values()[metadata].dropAmount;
							if(this.worldObj.rand.nextFloat() <= 0.33F)
								suggestedStackSize = EnumOreColoring.values()[metadata].dropAmount*2;
							this.setInventorySlotContents(4, new ItemStack(ItemsCore.magicalAlloy,suggestedStackSize,metadata));
							this.progressLevel = 0;
							if(this.getStackInSlot(7) == null)
							{
								this.setInventorySlotContents(7, new ItemStack(ItemsCore.magicalSlag,1,0));
							}else
							{
								if(this.getStackInSlot(7).getItem() == ItemsCore.magicalSlag && this.getStackInSlot(7).stackSize < 64)
								{
									ItemStack slagIS = this.getStackInSlot(7);
									slagIS.stackSize += 1;
									this.setInventorySlotContents(7, slagIS);
								}
							}
						}
					}
				}else
				if(this.getStackInSlot(4).getItem() == ItemsCore.magicalAlloy && this.getStackInSlot(4).getItemDamage() == metadata && this.getStackInSlot(4).stackSize+2 <= this.getStackInSlot(4).getMaxStackSize() && this.getStackInSlot(4).stackSize + 4 <= this.getInventoryStackLimit())
				{
					if(this.getMRU()-50 >= 0 && this.lavaTank != null && this.lavaTank.getFluid() != null && this.lavaTank.getFluid().getFluid() == FluidRegistry.LAVA && this.lavaTank.getFluidAmount() > 0)
					{
						this.setMRU(this.getMRU()-50);
						if(this.worldObj.rand.nextFloat() <= 0.1F)
							this.drain(1, true);
						this.worldObj.spawnParticle("flame", xCoord+0.5D+MathUtils.randomDouble(this.worldObj.rand)/2.2D, yCoord, zCoord+0.5D+MathUtils.randomDouble(this.worldObj.rand)/2.2D, 0, -0.1D, 0);
						++this.progressLevel;
						if(this.progressLevel >= 20*20)
						{
							this.decrStackSize(3, 1);
							int suggestedStackSize = EnumOreColoring.values()[metadata].dropAmount;
							if(this.worldObj.rand.nextFloat() <= 0.33F)
								suggestedStackSize = EnumOreColoring.values()[metadata].dropAmount*2;
							ItemStack is = this.getStackInSlot(4);
							is.stackSize += suggestedStackSize;
							if(is.stackSize > is.getMaxStackSize())
								is.stackSize = is.getMaxStackSize();
							this.setInventorySlotContents(4, is);
							this.progressLevel = 0;
							if(this.getStackInSlot(7) == null)
							{
								this.setInventorySlotContents(7, new ItemStack(ItemsCore.magicalSlag,1,0));
							}else
							{
								if(this.getStackInSlot(7).getItem() == ItemsCore.magicalSlag && this.getStackInSlot(7).stackSize < 64)
								{
									ItemStack slagIS = this.getStackInSlot(7);
									slagIS.stackSize += 1;
									this.setInventorySlotContents(7, slagIS);
								}
							}
						}
					}
				}
			}else
			{
				this.progressLevel = 0;
			}
		}else
			this.progressLevel = 0;
		
		ItemStack alloy = this.getStackInSlot(5);
		if(alloy != null && this.getStackInSlot(5).getItem() == ItemsCore.magicalAlloy)
		{
			EnumOreColoring oreColor = EnumOreColoring.values()[alloy.getItemDamage()];
			String oreName = oreColor.oreName;
			String outputName = oreColor.outputName;
			String suggestedIngotName;
			if(outputName.isEmpty())
				suggestedIngotName = "ingot"+oreName.substring(3);
			else
				suggestedIngotName = outputName;
			List<ItemStack> oreLst = OreDictionary.getOres(suggestedIngotName);
			
			if(oreLst != null && !oreLst.isEmpty() && !this.worldObj.isRemote)
			{
				ItemStack ingotStk = oreLst.get(0).copy();
				if(this.getStackInSlot(6) == null)
				{
					if(this.getMRU()-70 >= 0)
					{
						this.setMRU(this.getMRU()-70);
						this.worldObj.spawnParticle("flame", xCoord+0.5D+MathUtils.randomDouble(this.worldObj.rand)/2.2D, yCoord, zCoord+0.5D+MathUtils.randomDouble(this.worldObj.rand)/2.2D, 0, -0.1D, 0);
						++this.smeltingLevel;
						if(this.smeltingLevel >= 20*2)
						{
							this.decrStackSize(5, 1);
							int suggestedStackSize = 2;
							ingotStk.stackSize = suggestedStackSize;
							this.setInventorySlotContents(6, ingotStk);
							this.smeltingLevel = 0;
							if(this.getStackInSlot(7) == null)
							{
								this.setInventorySlotContents(7, new ItemStack(ItemsCore.magicalSlag,1,0));
							}else
							{
								if(this.getStackInSlot(7).getItem() == ItemsCore.magicalSlag && this.getStackInSlot(7).stackSize < 64)
								{
									ItemStack slagIS = this.getStackInSlot(7);
									slagIS.stackSize += 1;
									this.setInventorySlotContents(7, slagIS);
								}
							}
						}
					}
				}else
				if(this.getStackInSlot(6).isItemEqual(ingotStk) && this.getStackInSlot(6).stackSize+2 <= this.getStackInSlot(6).getMaxStackSize() && this.getStackInSlot(6).stackSize + 2 <= this.getInventoryStackLimit())
				{
					if(this.getMRU()-70 >= 0)
					{
						this.setMRU(this.getMRU()-70);
						this.worldObj.spawnParticle("flame", xCoord+0.5D+MathUtils.randomDouble(this.worldObj.rand)/2.2D, yCoord, zCoord+0.5D+MathUtils.randomDouble(this.worldObj.rand)/2.2D, 0, -0.1D, 0);
						++this.smeltingLevel;
						if(this.smeltingLevel >= 20*2)
						{
							this.decrStackSize(5, 1);
							int suggestedStackSize = 2;
							ItemStack is = this.getStackInSlot(6);
							is.stackSize += suggestedStackSize;
							if(is.stackSize > is.getMaxStackSize())
								is.stackSize = is.getMaxStackSize();
							this.setInventorySlotContents(6, is);
							this.smeltingLevel = 0;
							if(this.getStackInSlot(7) == null)
							{
								this.setInventorySlotContents(7, new ItemStack(ItemsCore.magicalSlag,1,0));
							}else
							{
								if(this.getStackInSlot(7).getItem() == ItemsCore.magicalSlag && this.getStackInSlot(7).stackSize < 64)
								{
									ItemStack slagIS = this.getStackInSlot(7);
									slagIS.stackSize += 1;
									this.setInventorySlotContents(7, slagIS);
								}
							}
						}
					}
				}
			}else
				this.smeltingLevel = 0;
		}else
			this.smeltingLevel = 0;
	}
	
	 public static ItemStack consumeItem(ItemStack stack)
	 {
		 if (stack.stackSize == 1) 
		 {
			 if (stack.getItem().hasContainerItem(stack)) 
			 {
				 return stack.getItem().getContainerItem(stack);
			 }else 
			 {
				 return null;
			 }
		 }else
		 {
			 stack.splitStack(1);
			 return stack;
		 }
	 }
	
    @SideOnly(Side.CLIENT)
    @Override
    public AxisAlignedBB getRenderBoundingBox()
    {
    	AxisAlignedBB bb = INFINITE_EXTENT_AABB;
    	return bb;
    }
    
	@Override
    public void readFromNBT(NBTTagCompound i)
    {
		super.readFromNBT(i);
		lavaTank.readFromNBT(i);
    }
	
	@Override
    public void writeToNBT(NBTTagCompound i)
    {
    	super.writeToNBT(i);
    	lavaTank.writeToNBT(i);
    }

	@Override
	public FluidStack getFluid() {
		// TODO Auto-generated method stub
		return lavaTank.getFluid();
	}

	@Override
	public int getFluidAmount() {
		// TODO Auto-generated method stub
		return lavaTank.getFluidAmount();
	}

	@Override
	public int getCapacity() {
		// TODO Auto-generated method stub
		return lavaTank.getCapacity();
	}

	@Override
	public FluidTankInfo getInfo() {
		// TODO Auto-generated method stub
		return lavaTank.getInfo();
	}

	@Override
	public int fill(FluidStack resource, boolean doFill) {
		// TODO Auto-generated method stub
		return lavaTank.fill(resource, doFill);
	}

	@Override
	public FluidStack drain(int maxDrain, boolean doDrain) {
		// TODO Auto-generated method stub
		return lavaTank.drain(maxDrain, doDrain);
	}
}
