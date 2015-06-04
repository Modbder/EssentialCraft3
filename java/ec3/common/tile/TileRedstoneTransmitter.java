package ec3.common.tile;

public class TileRedstoneTransmitter extends TileMRUGeneric
{
	public int updateTime = 0;
	
	 public TileRedstoneTransmitter()
	 {
		 super();
		 this.setSlotsNum(1);
		 this.setMaxMRU(0);

	 }

	public int getRedstonePower(int[] c)
	{
		return this.worldObj.getStrongestIndirectPower(c[0], c[1], c[2]);
	}
	
	public void updateEntity() 
	{
		super.updateEntity();
		if(updateTime == 0)
		{
			updateTime = 20;
			this.worldObj.notifyBlocksOfNeighborChange(xCoord, yCoord, zCoord, getBlockType());
		}else
			--updateTime;
	}

	@Override
	public int[] getOutputSlots() {
		return new int[]{0};
	}
	 
}
