package ec3.common.block;

import ec3.common.tile.TileNewMIMImportNode_Persistant;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class BlockNewMIMImporter_Persistant extends BlockNewMIMImporter{

	public BlockNewMIMImporter_Persistant() 
	{
		super();
	}
    
	@Override
	public TileEntity createNewTileEntity(World p_149915_1_, int metadata) {
		return new TileNewMIMImportNode_Persistant();
	}

	
}
