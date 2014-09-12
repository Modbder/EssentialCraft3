package ec3.common.block;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import DummyCore.Utils.MiscUtils;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ec3.common.item.ItemsCore;
import ec3.common.mod.EssentialCraftCore;
import ec3.common.tile.TileCorruption;
import ec3.common.tile.TileElementalCrystal;
import ec3.common.tile.TileMagicalQuarry;
import ec3.common.tile.TileMonsterHolder;
import ec3.common.tile.TileMoonWell;
import ec3.utils.cfg.Config;
import ec3.utils.common.ECUtils;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.event.ForgeEventFactory;

public class BlockElementalCrystal extends BlockContainer{

	public BlockElementalCrystal(Material p_i45394_1_) {
		super(p_i45394_1_);
	}

	public BlockElementalCrystal() {
		super(Material.rock);
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
		return new TileElementalCrystal();
	}
	
	@Override
    protected void dropBlockAsItem(World par1World, int par2, int par3, int par4, ItemStack par5ItemStack)
    {
        if (!par1World.isRemote && par1World.getGameRules().getGameRuleBooleanValue("doTileDrops"))
        {
        	par5ItemStack.stackSize = 1;
        	TileElementalCrystal c = (TileElementalCrystal) par1World.getTileEntity(par2, par3, par4);
            float f = 0.7F;
            double d0 = (double)(par1World.rand.nextFloat() * f) + (double)(1.0F - f) * 0.5D;
            double d1 = (double)(par1World.rand.nextFloat() * f) + (double)(1.0F - f) * 0.5D;
            double d2 = (double)(par1World.rand.nextFloat() * f) + (double)(1.0F - f) * 0.5D;
            if(c != null)
            {
            	MiscUtils.getStackTag(par5ItemStack).setFloat("size", c.size);
            	MiscUtils.getStackTag(par5ItemStack).setFloat("fire", c.fire);
            	MiscUtils.getStackTag(par5ItemStack).setFloat("water", c.water);
            	MiscUtils.getStackTag(par5ItemStack).setFloat("earth", c.earth);
            	MiscUtils.getStackTag(par5ItemStack).setFloat("air", c.air);
            }
            EntityItem entityitem = new EntityItem(par1World, (double)par2 + d0, (double)par3 + d1, (double)par4 + d2, par5ItemStack);
            entityitem.delayBeforeCanPickup = 10;
            par1World.spawnEntityInWorld(entityitem);
        }
    }
	
    public int getLightValue(IBlockAccess world, int x, int y, int z)
    {
    	try
    	{
	        Block block = world.getBlock(x, y, z);
	        if (block == this)
	        {
	            TileElementalCrystal tile = (TileElementalCrystal) world.getTileEntity(x, y, z);
	            float size = tile.size;
	            
	            float floatSize = size/100F;
	            
	            int lightSize = (int) (floatSize*15);
	            
	            return lightSize;
	        }
	        return getLightValue();
    	}catch(Exception e)
    	{
        	return getLightValue();
    	}
    }

}
