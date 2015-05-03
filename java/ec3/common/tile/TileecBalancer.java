package ec3.common.tile;

import java.util.UUID;

import net.minecraft.tileentity.TileEntity;
import ec3.api.EnumStructureType;
import ec3.api.IStructurePiece;

public class TileecBalancer extends TileEntity implements IStructurePiece{
	public TileecController controller;
	public UUID uuid = UUID.randomUUID();
	
	@Override
	public EnumStructureType getStructure() {
		return EnumStructureType.MRUCUContaigementChamber;
	}

	@Override
	public TileEntity structureController() {
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
	public void updateEntity() 
	{
		super.updateEntity();
		if(this.controller != null)
		{
			if(this.controller.getMRUCU() != null)
			{
				this.controller.getMRUCU().setFlag(true);
			}
		}
	}
}
