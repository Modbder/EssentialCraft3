package ec3.common.tile;

import java.util.List;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
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
import ec3.common.item.ItemBoundGem;
import ec3.utils.common.ECUtils;

public class TileMonsterHolder extends TileMRUGeneric{
	public static float rad = 12F;
	public static float cfgMaxMRU = ApiCore.DEVICE_MAX_MRU_GENERIC;
	public static boolean generatesCorruption = false;
	public static int genCorruption = 1;
	public static int mruUsage = 1;
	
	public TileMonsterHolder()
	{
		 super();
		this.maxMRU = (int) cfgMaxMRU;
		this.setSlotsNum(1);
	}
	
	@SuppressWarnings("unchecked")
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
	    	List<EntityLivingBase> lst = tile.getWorldObj().getEntitiesWithinAABB(EntityLivingBase.class, AxisAlignedBB.getBoundingBox(tile.xCoord-32, tile.yCoord-32, tile.zCoord-32, tile.xCoord+32, tile.yCoord+32, tile.zCoord+32));
	    	if(!lst.isEmpty())
	    	{
	    		for(int i = 0; i < lst.size(); ++i)
	    		{
	    			EntityLivingBase e = lst.get(i);
	    			if(!(e instanceof EntityPlayer))
	    			{
	    				if(getMRU() > mruUsage)
	    				{
		    				Coord3D tilePos = new Coord3D(tile.xCoord+0.5D,tile.yCoord+0.5D,tile.zCoord+0.5D);
		    				Coord3D mobPosition = new Coord3D(e.posX,e.posY,e.posZ);
		    				DummyDistance dist = new DummyDistance(tilePos,mobPosition);
		    				if(dist.getDistance() < rad && dist.getDistance() >= rad-3)
		    				{
		    					Vec3 posVector = Vec3.createVectorHelper(tilePos.x-mobPosition.x,tilePos.y-mobPosition.y ,tilePos.z-mobPosition.z);
		    					e.setPositionAndRotation(tilePos.x-posVector.xCoord/1.1D, tilePos.y-posVector.yCoord/1.1D, tilePos.z-posVector.zCoord/1.1D, e.rotationYaw, e.rotationPitch);
		    				}
		    				if(!this.worldObj.isRemote)
		    					this.setMRU(this.getMRU()-mruUsage);
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
    
    public static void setupConfig(Configuration cfg)
    {
    	try
    	{
	    	cfg.load();
	    	String[] cfgArrayString = cfg.getStringList("MonsterHolderSettings", "tileentities", new String[]{
	    			"Max MRU:"+ApiCore.DEVICE_MAX_MRU_GENERIC,
	    			"MRU Usage Per Mob:1",
	    			"Can this device actually generate corruption:false",
	    			"The amount of corruption generated each tick(do not set to 0!):1",
	    			"Radius to hold mobs within:12.0"
	    			},"");
	    	String dataString="";
	    	
	    	for(int i = 0; i < cfgArrayString.length; ++i)
	    		dataString+="||"+cfgArrayString[i];
	    	
	    	DummyData[] data = DataStorage.parseData(dataString);
	    	
	    	mruUsage = Integer.parseInt(data[1].fieldValue);
	    	cfgMaxMRU = Float.parseFloat(data[0].fieldValue);
	    	generatesCorruption = Boolean.parseBoolean(data[2].fieldValue);
	    	genCorruption = Integer.parseInt(data[3].fieldValue);
	    	rad = Float.parseFloat(data[4].fieldValue);
	    	
	    	cfg.save();
    	}catch(Exception e)
    	{
    		return;
    	}
    }
}
