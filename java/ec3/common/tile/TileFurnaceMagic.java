package ec3.common.tile;

import java.util.List;

import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.oredict.OreDictionary;
import DummyCore.Utils.DataStorage;
import DummyCore.Utils.DummyData;
import DummyCore.Utils.MathUtils;
import ec3.api.ApiCore;
import ec3.api.ITEHasMRU;
import ec3.common.item.ItemBoundGem;
import ec3.common.item.ItemsCore;
import ec3.utils.common.ECUtils;
import ec3.utils.common.EnumOreColoring;

public class TileFurnaceMagic extends TileMRUGeneric{
	
	public int progressLevel, smeltingLevel;
	
	public static float cfgMaxMRU = ApiCore.DEVICE_MAX_MRU_GENERIC;
	public static boolean generatesCorruption = false;
	public static int genCorruption = 2;
	public static int mruUsage = 25;
	public static int smeltingTime = 400;
	
	public TileFurnaceMagic()
	{
		super();
		this.maxMRU = (int)cfgMaxMRU;
		this.setSlotsNum(3);
	}
	
	@Override
    public void readFromNBT(NBTTagCompound i)
    {
		super.readFromNBT(i);
		progressLevel = i.getInteger("progress");
		smeltingLevel = i.getInteger("smelting");
    }
	
	@Override
    public void writeToNBT(NBTTagCompound i)
    {
    	super.writeToNBT(i);
    	i.setInteger("progress", progressLevel);
    	i.setInteger("smelting", smeltingLevel);
    }
	
	@Override
	public void updateEntity()
	{
		int usage = mruUsage;
		int time = smeltingTime/(this.getBlockMetadata()/4+1);
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
		if(!this.worldObj.isBlockIndirectlyGettingPowered(xCoord, yCoord, zCoord))
		{
			ItemStack ore = this.getStackInSlot(1);
			if(ore != null)
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
					if(this.getStackInSlot(2) == null)
					{
						if(this.getMRU()-usage >= 0)
						{
							this.setMRU(this.getMRU()-usage);
							this.worldObj.spawnParticle("flame", xCoord+0.5D+MathUtils.randomDouble(this.worldObj.rand)/2.2D, yCoord, zCoord+0.5D+MathUtils.randomDouble(this.worldObj.rand)/2.2D, 0, -0.1D, 0);
							++this.progressLevel;
							
			    			if(generatesCorruption)
			    				ECUtils.increaseCorruptionAt(worldObj, xCoord, yCoord, zCoord, this.worldObj.rand.nextInt(genCorruption));
							
			    			if(this.progressLevel >= time && !this.worldObj.isRemote)
							{
								this.decrStackSize(1, 1);
								int suggestedStackSize = EnumOreColoring.values()[metadata].dropAmount;
								this.setInventorySlotContents(2, new ItemStack(ItemsCore.magicalAlloy,suggestedStackSize,metadata));
								this.progressLevel = 0;
								this.syncTick = 0;
							}
						}
					}else
					if(this.getStackInSlot(2).getItem() == ItemsCore.magicalAlloy && this.getStackInSlot(2).getItemDamage() == metadata && this.getStackInSlot(2).stackSize+1 <= this.getStackInSlot(2).getMaxStackSize() && this.getStackInSlot(2).stackSize + 1 <= this.getInventoryStackLimit())
					{
						if(this.getMRU()-usage >= 0)
						{
							this.setMRU(this.getMRU()-usage);
							
							this.worldObj.spawnParticle("flame", xCoord+0.5D+MathUtils.randomDouble(this.worldObj.rand)/2.2D, yCoord, zCoord+0.5D+MathUtils.randomDouble(this.worldObj.rand)/2.2D, 0, -0.1D, 0);
							++this.progressLevel;
							
			    			if(generatesCorruption)
			    				ECUtils.increaseCorruptionAt(worldObj, xCoord, yCoord, zCoord, this.worldObj.rand.nextInt(genCorruption));
			    			
							if(this.progressLevel >= time && !this.worldObj.isRemote)
							{
								this.decrStackSize(1, 1);
								int suggestedStackSize = EnumOreColoring.values()[metadata].dropAmount;
								
								ItemStack is = this.getStackInSlot(2);
								is.stackSize += suggestedStackSize;
								if(is.stackSize > is.getMaxStackSize())
									is.stackSize = is.getMaxStackSize();
								this.setInventorySlotContents(2, is);
								this.progressLevel = 0;
								this.syncTick = 0;
							}
						}
					}
				}else
				{
					this.progressLevel = 0;
				}
			}else
				this.progressLevel = 0;
			
			ItemStack alloy = this.getStackInSlot(1);
			if(alloy != null && this.getStackInSlot(1).getItem() == ItemsCore.magicalAlloy)
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
				
				if(oreLst != null && !oreLst.isEmpty())
				{
					ItemStack ingotStk = oreLst.get(0).copy();
					if(this.getStackInSlot(2) == null)
					{
						if(this.getMRU()-usage >= 0)
						{
							this.setMRU(this.getMRU()-usage);
							this.worldObj.spawnParticle("flame", xCoord+0.5D+MathUtils.randomDouble(this.worldObj.rand)/2.2D, yCoord, zCoord+0.5D+MathUtils.randomDouble(this.worldObj.rand)/2.2D, 0, -0.1D, 0);
							++this.smeltingLevel;
							
			    			if(generatesCorruption)
			    				ECUtils.increaseCorruptionAt(worldObj, xCoord, yCoord, zCoord, this.worldObj.rand.nextInt(genCorruption));
							
			    			if(this.smeltingLevel >= time && !this.worldObj.isRemote)
							{
								this.decrStackSize(1, 1);
								int suggestedStackSize = 2;
								ingotStk.stackSize = suggestedStackSize;
								this.setInventorySlotContents(2, ingotStk);
								this.smeltingLevel = 0;
								this.syncTick = 0;
							}
						}
					}else
					if(this.getStackInSlot(2).isItemEqual(ingotStk) && this.getStackInSlot(2).stackSize+2 <= this.getStackInSlot(2).getMaxStackSize() && this.getStackInSlot(2).stackSize + 2 <= this.getInventoryStackLimit())
					{
						if(this.getMRU()-usage >= 0)
						{
							this.setMRU(this.getMRU()-usage);
							this.worldObj.spawnParticle("flame", xCoord+0.5D+MathUtils.randomDouble(this.worldObj.rand)/2.2D, yCoord, zCoord+0.5D+MathUtils.randomDouble(this.worldObj.rand)/2.2D, 0, -0.1D, 0);
							++this.smeltingLevel;
			    			if(generatesCorruption)
			    				ECUtils.increaseCorruptionAt(worldObj, xCoord, yCoord, zCoord, this.worldObj.rand.nextInt(genCorruption));
							if(this.smeltingLevel >= time && !this.worldObj.isRemote)
							{
								this.decrStackSize(1, 1);
								int suggestedStackSize = 2;
								ItemStack is = this.getStackInSlot(2);
								is.stackSize += suggestedStackSize;
								if(is.stackSize > is.getMaxStackSize())
									is.stackSize = is.getMaxStackSize();
								this.setInventorySlotContents(2, is);
								this.smeltingLevel = 0;
								this.syncTick = 0;
							}
						}
					}
				}else
					this.smeltingLevel = 0;
			}else
				this.smeltingLevel = 0;
		}
	}
	
    public static void setupConfig(Configuration cfg)
    {
    	try
    	{
	    	cfg.load();
	    	String[] cfgArrayString = cfg.getStringList("FurnaceMagicSettings", "tileentities", new String[]{
	    			"Max MRU:"+ApiCore.DEVICE_MAX_MRU_GENERIC,
	    			"MRU Usage:25",
	    			"Can this device actually generate corruption:false",
	    			"The amount of corruption generated each tick(do not set to 0!):3",
	    			"Ticks required to smelt:400"
	    			},"");
	    	String dataString="";
	    	
	    	for(int i = 0; i < cfgArrayString.length; ++i)
	    		dataString+="||"+cfgArrayString[i];
	    	
	    	DummyData[] data = DataStorage.parseData(dataString);
	    	
	    	mruUsage = Integer.parseInt(data[1].fieldValue);
	    	cfgMaxMRU = Float.parseFloat(data[0].fieldValue);
	    	generatesCorruption = Boolean.parseBoolean(data[2].fieldValue);
	    	genCorruption = Integer.parseInt(data[3].fieldValue);
	    	smeltingTime = Integer.parseInt(data[4].fieldValue);
	    	
	    	cfg.save();
    	}catch(Exception e)
    	{
    		return;
    	}
    }
}
