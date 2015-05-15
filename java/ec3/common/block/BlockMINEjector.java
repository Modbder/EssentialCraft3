package ec3.common.block;

import java.util.List;

import DummyCore.Utils.MiscUtils;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ec3.common.mod.EssentialCraftCore;
import ec3.common.tile.TileAMINEjector;
import ec3.common.tile.TileMINEjector;
import ec3.utils.cfg.Config;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class BlockMINEjector extends BlockContainer{

	public BlockMINEjector(Material p_i45394_1_) {
		super(p_i45394_1_);
	}
	
    public void setBlockBoundsBasedOnState(IBlockAccess p_149719_1_, int p_149719_2_, int p_149719_3_, int p_149719_4_)
    {
    	int metadata = p_149719_1_.getBlockMetadata(p_149719_2_, p_149719_3_, p_149719_4_);
    	if(metadata == 0 || metadata == 6)
    	{
    		this.setBlockBounds(0.35F, 0F, 0.35F, 0.65F, 0.5F, 0.65F);
    	}else
        	if(metadata == 1 || metadata == 7)
        	{
        		this.setBlockBounds(0.35F, 0.45F, 0.35F, 0.65F, 1F, 0.65F);
        	}else
            	if(metadata == 2 || metadata == 8)
            	{
            		this.setBlockBounds(0.35F, 0.35F, 0F, 0.65F, 0.65F, 0.5F);
            	}else
                	if(metadata == 3 || metadata == 9)
                	{
                		this.setBlockBounds(0.35F, 0.35F, 0.5F, 0.65F, 0.65F, 1F);
                	}else
                    	if(metadata == 4 || metadata == 10)
                    	{
                    		this.setBlockBounds(0F, 0.35F, 0.35F, 0.5F, 0.65F, 0.65F);
                    	}else
                        	if(metadata == 5 || metadata == 11)
                        	{
                        		this.setBlockBounds(0.5F, 0.35F, 0.35F, 1F, 0.65F, 0.65F);
                        	}
    	super.setBlockBoundsBasedOnState(p_149719_1_, p_149719_2_, p_149719_3_, p_149719_4_);
    }
	
	public BlockMINEjector() {
		super(Material.rock);
	}
	
	@Override
    public void breakBlock(World par1World, int par2, int par3, int par4, Block par5, int par6)
    {
		MiscUtils.dropItemsOnBlockBreak(par1World, par2, par3, par4, par5, par6);
		super.breakBlock(par1World, par2, par3, par4, par5, par6);
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
    
    @SuppressWarnings({ "unchecked", "rawtypes" })
	@SideOnly(Side.CLIENT)
    public void getSubBlocks(Item p_149666_1_, CreativeTabs p_149666_2_, List p_149666_3_)
    {
    	super.getSubBlocks(p_149666_1_, p_149666_2_, p_149666_3_);
    	p_149666_3_.add(new ItemStack(p_149666_1_,1,6));
    }
    
    public int getRenderType()
    {
        return 2634;
    }

	@Override
	public TileEntity createNewTileEntity(World p_149915_1_, int metadata) {
		// TODO Auto-generated method stub
		return metadata < 6 ? new TileMINEjector() : new TileAMINEjector();
	}
	
    @Override
    public int onBlockPlaced(World w, int x, int y, int z, int side, float hitX, float hitY, float hitZ, int meta)
    {
        return meta == 0 ? ForgeDirection.OPPOSITES[side] : ForgeDirection.OPPOSITES[side] + 6;
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
	        		par5EntityPlayer.openGui(EssentialCraftCore.core, Config.guiID[0], par1World, par2, par3, par4);
	            	return true;
	        	}
	        	else
	        	{
	        		return false;
	        	}
	        }
	    }
	
}
