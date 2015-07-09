package ec3.common.tile;

public class TileCreativeMRUSource extends TileMRUGeneric{

	public TileCreativeMRUSource()
	{
		this.setSlotsNum(0);
		this.setMaxMRU(100000);
	}
	
	
	@Override
	public int[] getOutputSlots() {
		return new int[0];
	}

	public void updateEntity() 
	{
		this.setMRU(this.getMaxMRU());
		super.updateEntity();
	}
}
