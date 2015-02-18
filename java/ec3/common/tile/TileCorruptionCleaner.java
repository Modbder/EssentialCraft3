package ec3.common.tile;

import java.util.Arrays;
import java.util.List;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.Vec3;
import net.minecraftforge.common.config.Configuration;
import DummyCore.Utils.Coord3D;
import DummyCore.Utils.DataStorage;
import DummyCore.Utils.DummyData;
import DummyCore.Utils.DummyDistance;
import DummyCore.Utils.MathUtils;
import ec3.api.ApiCore;
import ec3.api.ITEHasMRU;
import ec3.common.block.BlockCorruption_Light;
import ec3.common.item.ItemBoundGem;
import ec3.utils.common.ECUtils;

public class TileCorruptionCleaner extends TileMRUGeneric{
	
	public Coord3D cleared;
	public int clearTime = 0;
	
	public static int maxRadius = 8;
	public static boolean removeBlock = false;
	public static int mruUsage = 20;
	public static int ticksRequired = 200;
	public static float cfgMaxMRU = ApiCore.DEVICE_MAX_MRU_GENERIC;
	
	public TileCorruptionCleaner()
	{
		 super();
		this.maxMRU = (int) cfgMaxMRU;
		this.setSlotsNum(1);
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
		if(!this.worldObj.isBlockIndirectlyGettingPowered(xCoord, yCoord, zCoord))
		{
			if(cleared == null)
			{
				if(!this.worldObj.isRemote)
				{
					int offsetX = (int) (MathUtils.randomDouble(this.worldObj.rand)*maxRadius);
					int offsetY = (int) (MathUtils.randomDouble(this.worldObj.rand)*maxRadius);
					int offsetZ = (int) (MathUtils.randomDouble(this.worldObj.rand)*maxRadius);
					Block b = this.worldObj.getBlock(xCoord+offsetX, yCoord+offsetY, zCoord+offsetZ);
					if(b instanceof BlockCorruption_Light)
					{
						cleared = new Coord3D(xCoord+offsetX, yCoord+offsetY, zCoord+offsetZ);
						clearTime = ticksRequired;
					}
				}
			}else
			{
				if(!this.worldObj.isRemote)
				{
					Block b = this.worldObj.getBlock((int)cleared.x, (int)cleared.y, (int)cleared.z);
					if(!(b instanceof BlockCorruption_Light))
					{
						cleared = null;
						clearTime = 0;
						return;
					}
					if(this.getMRU() - mruUsage > 0)
					{
						--clearTime;
						this.setMRU(this.getMRU()-mruUsage);
						if(clearTime <= 0)
						{
							int metadata = this.worldObj.getBlockMetadata((int)cleared.x, (int)cleared.y, (int)cleared.z);
							if(metadata == 0 || removeBlock)
								this.worldObj.setBlockToAir((int)cleared.x, (int)cleared.y, (int)cleared.z);
							else
								this.worldObj.setBlockMetadataWithNotify((int)cleared.x, (int)cleared.y, (int)cleared.z, metadata-1, 3);
							this.cleared = null;
						}
					}
				}
			}
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
		if(i.hasKey("coord"))
		{
			String str = i.getString("coord");
			if(!str.equals("null"))
			{
				DummyData[] coordData = DataStorage.parseData(i.getString("coord"));
				cleared = new Coord3D(Double.parseDouble(coordData[0].fieldValue),Double.parseDouble(coordData[1].fieldValue),Double.parseDouble(coordData[2].fieldValue));
			}else
			{
				cleared = null;
			}
		}
		clearTime = i.getInteger("clear");
		super.readFromNBT(i);
    }
	
	@Override
    public void writeToNBT(NBTTagCompound i)
    {
		if(cleared != null)
		{
			i.setString("coord", cleared.toString());
		}else
		{
			i.setString("coord", "null");
		}
		i.setInteger("clear", clearTime);
    	super.writeToNBT(i);
    }
	
    public static void setupConfig(Configuration cfg)
    {
    	try
    	{
	    	cfg.load();
	    	String[] cfgArrayString = cfg.getStringList("CorruptionCleanerSettings", "tileentities", new String[]{
	    			"Max MRU:"+ApiCore.DEVICE_MAX_MRU_GENERIC,
	    			"MRU Usage per Tick:20",
	    			"Required time to destroy corruption:200",
	    			"Should remove corruption blocks instead of reducing their growth:false",
	    			"A radius in which the device will detect corruption blocks:8"
	    			},"");
	    	String dataString="";
	    	
	    	for(int i = 0; i < cfgArrayString.length; ++i)
	    		dataString+="||"+cfgArrayString[i];
	    	
	    	DummyData[] data = DataStorage.parseData(dataString);
	    	
	    	maxRadius = Integer.parseInt(data[4].fieldValue);
	    	removeBlock = Boolean.parseBoolean(data[3].fieldValue);
	    	mruUsage = Integer.parseInt(data[1].fieldValue);
	    	ticksRequired = Integer.parseInt(data[2].fieldValue);
	    	cfgMaxMRU = Float.parseFloat(data[0].fieldValue);
	    	
	    	cfg.save();
    	}catch(Exception e)
    	{
    		return;
    	}
    }
}
