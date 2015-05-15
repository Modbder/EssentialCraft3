package ec3.common.block;

import ec3.common.tile.TileMRUCoil_Hardener;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class BlockMRUCoil_Hardener extends BlockContainer{

	protected BlockMRUCoil_Hardener(Material p_i45386_1_) {
		super(p_i45386_1_);
		// TODO Auto-generated constructor stub
	}
	
	public BlockMRUCoil_Hardener() {
		super(Material.rock);
		// TODO Auto-generated constructor stub
	}
	
    public boolean isOpaqueCube()
    {
        return false;
    }
    
	@Override
    public int getRenderBlockPass()
    {
        return 0;
    }
    
    public boolean renderAsNormalBlock()
    {
        return false;
    }
    
    public int getRenderType()
    {
        return 2634;
    }

	@Override
	public TileEntity createNewTileEntity(World var1, int var2) {
		// TODO Auto-generated method stub
		return new TileMRUCoil_Hardener();
	}
}
