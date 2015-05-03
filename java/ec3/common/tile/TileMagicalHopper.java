package ec3.common.tile;

import java.util.List;

import DummyCore.Utils.DataStorage;
import DummyCore.Utils.DummyData;
import ec3.common.inventory.InventoryMagicFilter;
import ec3.common.item.ItemFilter;
import ec3.utils.common.ECUtils;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.util.ForgeDirection;

public class TileMagicalHopper extends TileMRUGeneric
{
	public static int itemHopRadius = 3;
	public static int itemDelay = 20;
	
	public int delay = 0;
	
	public ForgeDirection getRotation()
	{
		int metadata = this.worldObj.getBlockMetadata(xCoord, yCoord, zCoord);
		if(metadata > 5) metadata -= 6;
		return ForgeDirection.getOrientation(metadata);
	}

	 public TileMagicalHopper()
	 {
		 super();
		 this.setSlotsNum(1);
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
    
    @SuppressWarnings("unchecked")
	@Override
    public void updateEntity() 
    {
    	super.updateEntity();
    	if(delay <= 0 && !this.worldObj.isBlockIndirectlyGettingPowered(xCoord, yCoord, zCoord))
    	{
    		ForgeDirection r = this.getRotation();
    		AxisAlignedBB teleportBB = AxisAlignedBB.getBoundingBox(xCoord+r.offsetX, yCoord+r.offsetY, zCoord+r.offsetZ, xCoord+1+r.offsetX, yCoord+1+r.offsetY, zCoord+1+r.offsetZ);
    		delay = itemDelay;
    		List<EntityItem> items = this.worldObj.getEntitiesWithinAABB(EntityItem.class, AxisAlignedBB.getBoundingBox(xCoord, yCoord, zCoord, xCoord+1, yCoord+1, zCoord+1).expand(itemHopRadius, itemHopRadius, itemHopRadius));
    		List<EntityItem> doNotTouch = this.worldObj.getEntitiesWithinAABB(EntityItem.class, teleportBB);
    		
    		for(int i = 0; i < items.size(); ++i)
    		{
    			EntityItem item = items.get(i);
    			if(this.canTeleport(item) && !doNotTouch.contains(item))
    			{
    				item.setPositionAndRotation(xCoord+0.5D+r.offsetX, yCoord+0.5D+r.offsetY, zCoord+0.5D+r.offsetZ, 0, 0);
    			}
    		}
    	}else
    	{
    		--delay;
    	}
    }

    public boolean canTeleport(EntityItem item)
    {
    	if(item.getEntityItem() == null)
    		return false;
    	
    	if(this.getStackInSlot(0) == null || !(this.getStackInSlot(0).getItem() instanceof ItemFilter))
    		return true;
    	
    	return ECUtils.canFilterAcceptItem(new InventoryMagicFilter(this.getStackInSlot(0)), item.getEntityItem(), this.getStackInSlot(0));
    }
    
    public static void setupConfig(Configuration cfg)
    {
    	try
    	{
	    	cfg.load();
	    	String[] cfgArrayString = cfg.getStringList("MagicalHopperSettings", "tileentities", new String[]{
	    			"Radius in which the hopper will detect items(int):3",
	    			"Delay between item detection in ticks:20"
	    			},"");
	    	String dataString="";
	    	
	    	for(int i = 0; i < cfgArrayString.length; ++i)
	    		dataString+="||"+cfgArrayString[i];
	    	
	    	DummyData[] data = DataStorage.parseData(dataString);
	    	
	    	itemHopRadius = Integer.parseInt(data[0].fieldValue);
	    	itemDelay = Integer.parseInt(data[1].fieldValue);
	    	
	    	cfg.save();
    	}catch(Exception e)
    	{
    		return;
    	}
    }
	
}
