package ec3.common.block;

import ec3.common.mod.EssentialCraftCore;
import ec3.common.tile.TilePlayerPentacle;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class BlockPlayerPentacle extends BlockContainer{
	
	public BlockPlayerPentacle() {
		super(Material.rock);
		this.setBlockBounds(0, 0, 0, 1, 0.0625F, 1);
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

	@Override
	public TileEntity createNewTileEntity(World var1, int var2) {
		// TODO Auto-generated method stub
		return new TilePlayerPentacle();
	}
	
	@Override
	 public boolean onBlockActivated(World par1World, int par2, int par3, int par4, EntityPlayer par5EntityPlayer, int par6, float par7, float par8, float par9)
	    {
	        if(!par5EntityPlayer.isSneaking())
	        {
		       	EssentialCraftCore.proxy.openPentacleGUIForPlayer(par1World.getTileEntity(par2, par3, par4));
		        return true;
	        }
	        else
	        {
	        	return false;
	        }
	    }
	
	@Override
    public void breakBlock(World par1World, int par2, int par3, int par4, Block par5, int par6)
    {
		super.breakBlock(par1World, par2, par3, par4, par5, par6);
    }
}
