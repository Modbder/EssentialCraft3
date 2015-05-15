package ec3.common.block;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ec3.common.tile.TileSolarPrism;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;

public class BlockSolarPrism extends BlockContainer{

	protected BlockSolarPrism(Material p_i45394_1_) {
		super(p_i45394_1_);
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
        return -1;
    }
    
    public AxisAlignedBB getCollisionBoundingBoxFromPool(World p_149668_1_, int p_149633_2_, int p_149633_3_, int p_149633_4_)
    {
    	return AxisAlignedBB.getBoundingBox((double)p_149633_2_ - 1, (double)p_149633_3_ + 0.4F, (double)p_149633_4_ - 1, (double)p_149633_2_ + 2, (double)p_149633_3_ + 0.6F, (double)p_149633_4_ + 2);
    }
    
    @SideOnly(Side.CLIENT)
    public AxisAlignedBB getSelectedBoundingBoxFromPool(World p_149633_1_, int p_149633_2_, int p_149633_3_, int p_149633_4_)
    {
        return AxisAlignedBB.getBoundingBox((double)p_149633_2_ - 1, (double)p_149633_3_ + 0.4F, (double)p_149633_4_ - 1, (double)p_149633_2_ + 2, (double)p_149633_3_ + 0.6F, (double)p_149633_4_ + 2);
    }


	@Override
	public TileEntity createNewTileEntity(World p_149915_1_, int p_149915_2_) {
		// TODO Auto-generated method stub
		return new TileSolarPrism();
	}
	
}
