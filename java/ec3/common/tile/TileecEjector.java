package ec3.common.tile;

import java.util.UUID;

import net.minecraft.tileentity.TileEntity;
import ec3.api.EnumStructureType;
import ec3.api.IStructurePiece;
import ec3.api.ITERequiresMRU;
import ec3.api.ITEStoresMRU;

public class TileecEjector extends TileEntity implements ITEStoresMRU, IStructurePiece{
	public TileecController controller;
	public UUID uuid = UUID.randomUUID();
	
	@Override
	public int getMRU() {
		if(controller!=null)
			return controller.getMRU();
		return 0;
	}

	@Override
	public int getMaxMRU() {
		// TODO Auto-generated method stub
		return 60000;
	}

	@Override
	public boolean setMRU(int i) {
		if(controller != null)
		{
			if(i < this.getMRU())
				return controller.setMRU(i);
		}
		return false;
	}

	@Override
	public float getBalance() {
		if(controller != null)
		{
			return controller.getBalance();
		}
		return 1;
	}

	@Override
	public boolean setBalance(float f) {
		if(controller != null)
		{
			return controller.setBalance(f);
		}
		return false;
	}

	@Override
	public UUID getUUID() {
		// TODO Auto-generated method stub
		return uuid;
	}

	@Override
	public EnumStructureType getStructure() {
		// TODO Auto-generated method stub
		return EnumStructureType.MRUCUContaigementChamber;
	}

	@Override
	public TileEntity structureController() {
		// TODO Auto-generated method stub
		return controller;
	}

	@Override
	public void setStructureController(TileEntity tile,
			EnumStructureType structure) {
		if(tile instanceof TileecController && structure == this.getStructure())
		{
			controller = (TileecController) tile;
		}
		
	}

	@Override
	public boolean setMaxMRU(float f) {
		if(controller != null)
		{
			return controller.setMaxMRU(f);
		}
		return false;
	}

}
