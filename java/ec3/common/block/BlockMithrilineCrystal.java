package ec3.common.block;

import java.util.List;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ec3.common.tile.TileMithrilineCrystal;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class BlockMithrilineCrystal extends BlockContainer{

	public BlockMithrilineCrystal(Material p_i45394_1_) {
		super(p_i45394_1_);
		this.setStepSound(soundTypeGlass);
	}

    public int damageDropped(int meta)
    {
    	return meta/3*3;
    }
	
	public BlockMithrilineCrystal() {
		super(Material.rock);
		this.setStepSound(soundTypeGlass);
	}
	
    @SuppressWarnings({ "unchecked", "rawtypes" })
	@SideOnly(Side.CLIENT)
    public void getSubBlocks(Item p_149666_1_, CreativeTabs p_149666_2_, List p_149666_3_)
    {
        p_149666_3_.add(new ItemStack(p_149666_1_, 1, 0));
        p_149666_3_.add(new ItemStack(p_149666_1_, 1, 3));
        p_149666_3_.add(new ItemStack(p_149666_1_, 1, 6));
        p_149666_3_.add(new ItemStack(p_149666_1_, 1, 9));
        p_149666_3_.add(new ItemStack(p_149666_1_, 1, 12));
    }
	
	@Override
    public void breakBlock(World par1World, int par2, int par3, int par4, Block par5, int par6)
    {
		
		super.breakBlock(par1World, par2, par3, par4, par5, par6);
		par6 %= 3;
		if(par6 == 0)
		{
			par1World.setBlockToAir(par2, par3+1, par4);
			par1World.setBlockToAir(par2, par3+2, par4);
		}
		if(par6 == 1)
		{
			par1World.setBlockToAir(par2, par3-1, par4);
			par1World.setBlockToAir(par2, par3+1, par4);
		}
		if(par6 == 2)
		{
			par1World.setBlockToAir(par2, par3-1, par4);
			par1World.setBlockToAir(par2, par3-2, par4);
		}
    }
	
    public void onBlockAdded(World w, int i, int j, int k)
    {
        super.onBlockAdded(w, i, j, k);
        int meta = w.getBlockMetadata(i, j, k);
        if(meta%3 == 0)
        {
        	w.setBlock(i, j+1, k, this, meta+1, 3);
        	w.setBlock(i, j+2, k, this, meta+2, 3);
        }
    }
    
    public boolean canPlaceBlockAt(World p_149742_1_, int p_149742_2_, int p_149742_3_, int p_149742_4_)
    {
        return p_149742_1_.getBlock(p_149742_2_, p_149742_3_, p_149742_4_).isReplaceable(p_149742_1_, p_149742_2_, p_149742_3_, p_149742_4_) && p_149742_1_.getBlock(p_149742_2_, p_149742_3_+1, p_149742_4_).isReplaceable(p_149742_1_, p_149742_2_, p_149742_3_+1, p_149742_4_) && p_149742_1_.getBlock(p_149742_2_, p_149742_3_+2, p_149742_4_).isReplaceable(p_149742_1_, p_149742_2_, p_149742_3_+2, p_149742_4_);
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
	public TileEntity createNewTileEntity(World p_149915_1_, int p_149915_2_) {
		// TODO Auto-generated method stub
		return p_149915_2_%3 == 0 ? new TileMithrilineCrystal() : null;
	}
	
    public float getEnchantPowerBonus(World world, int x, int y, int z)
    {
    	int meta = world.getBlockMetadata(x, y, z);
    	return meta == 3 ? 15 : meta == 6 ? 30 : meta == 9 ? 60 : 0;
    }
	
}
