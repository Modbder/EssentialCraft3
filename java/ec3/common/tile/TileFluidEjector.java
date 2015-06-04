package ec3.common.tile;

import net.minecraftforge.common.util.ForgeDirection;

public class TileFluidEjector extends TileMRUGeneric {

	public ForgeDirection getRotation()
	{
		int metadata = this.worldObj.getBlockMetadata(xCoord, yCoord, zCoord);
		if(metadata > 5) metadata -= 6;
		return ForgeDirection.getOrientation(metadata);
	}
	

	 public TileFluidEjector()
	 {
		 super();
		 this.setSlotsNum(0);
		 this.setMaxMRU(0);

	 }


	@Override
	public int[] getOutputSlots() {
		return new int[0];
	}
	
}
