package ec3.common.block;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import DummyCore.Utils.MiscUtils;
import ec3.common.mod.EssentialCraftCore;
import ec3.common.tile.TileRayTower;
import ec3.network.proxy.ClientProxy;
import ec3.utils.cfg.Config;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class BlockRayTower extends BlockContainer{

	protected BlockRayTower() {
		super(Material.iron);
		// TODO Auto-generated constructor stub
	}
	
    public void onBlockAdded(World w, int i, int j, int k)
    {
        super.onBlockAdded(w, i, j, k);
        if(w.getBlock(i, j+1, k) == Blocks.air)
        {
        	if(w.getBlockMetadata(i, j, k) == 0)
        	{
        		w.setBlock(i, j+1, k, BlocksCore.rayTower,1,3);
        		w.setBlockMetadataWithNotify(i, j+1, k, 1, 2);
        	}
        }
    }
    
    public boolean canPlaceBlockAt(World p_149742_1_, int p_149742_2_, int p_149742_3_, int p_149742_4_)
    {
        return p_149742_1_.getBlock(p_149742_2_, p_149742_3_, p_149742_4_).isReplaceable(p_149742_1_, p_149742_2_, p_149742_3_, p_149742_4_) && p_149742_1_.getBlock(p_149742_2_, p_149742_3_+1, p_149742_4_) == Blocks.air;
    }

	@Override
	 public boolean renderAsNormalBlock()
	    {
	        return false;
	    }
	    
	@Override
	    public int getRenderType()
	    {
	        return 2634;
	    }
	    
	@Override
	    public boolean isOpaqueCube()
	    {
	        return false;
	    }
	    
	@Override
	    public int getRenderBlockPass()
	    {
	        return 0;
	    }
	
	@Override
	 public boolean onBlockActivated(World par1World, int par2, int par3, int par4, EntityPlayer par5EntityPlayer, int par6, float par7, float par8, float par9)
	    {
	        if (par1World.isRemote)
	        {
	            return true;
	        }else
	        {
	        	if(!par5EntityPlayer.isSneaking())
	        	{
	        		if(par1World.getBlockMetadata(par2, par3, par4) == 0)
	        		{
	        			par5EntityPlayer.openGui(EssentialCraftCore.core, Config.guiID[0], par1World, par2, par3, par4);
	        		}else
	        		{
	        			par5EntityPlayer.openGui(EssentialCraftCore.core, Config.guiID[0], par1World, par2, par3-1, par4);
	        		}
	            	return true;
	        	}else
	        	{
	        		return false;
	        	}
	        }
	    }
	
	@Override
    public void breakBlock(World par1World, int par2, int par3, int par4, Block par5, int par6)
    {
    	MiscUtils.dropItemsOnBlockBreak(par1World, par2, par3, par4, par5, par6);
    	if(par1World.getBlock(par2, par3-1, par4) == BlocksCore.rayTower)
    	{
    		par1World.setBlock(par2, par3-1, par4, Blocks.air,0,3);
    	}
    	if(par1World.getBlock(par2, par3+1, par4) == BlocksCore.rayTower)
    	{
    		par1World.setBlock(par2, par3+1, par4, Blocks.air,0,3);
    	}
    	super.breakBlock(par1World, par2, par3, par4, par5, par6);
        par1World.removeTileEntity(par2, par3, par4);
    }
	
	@Override
	public TileEntity createNewTileEntity(World p_149915_1_, int p_149915_2_) {
		// TODO Auto-generated method stub
		return new TileRayTower();
	}
	
	@Override
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister p_149651_1_)
    {
		ClientProxy.mruIcon = p_149651_1_.registerIcon("essentialcraft:mru");
    }

}
