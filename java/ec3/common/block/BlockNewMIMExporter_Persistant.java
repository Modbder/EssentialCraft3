package ec3.common.block;

import ec3.common.tile.TileNewMIMExportNode_Persistant;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class BlockNewMIMExporter_Persistant extends BlockNewMIMExporter{

	public BlockNewMIMExporter_Persistant() 
	{
		super();
	}
    
	@Override
	public TileEntity createNewTileEntity(World p_149915_1_, int metadata) {
		return new TileNewMIMExportNode_Persistant();
	}

	
}
