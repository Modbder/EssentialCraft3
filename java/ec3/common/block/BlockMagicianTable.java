package ec3.common.block;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import DummyCore.Utils.MiscUtils;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ec3.api.MagicianTableUpgrades;
import ec3.common.item.ItemsCore;
import ec3.common.mod.EssentialCraftCore;
import ec3.common.tile.TileCorruption;
import ec3.common.tile.TileMagicianTable;
import ec3.common.tile.TileMoonWell;
import ec3.utils.cfg.Config;
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
import net.minecraft.world.World;
import net.minecraftforge.event.ForgeEventFactory;

public class BlockMagicianTable extends BlockContainer{

	public BlockMagicianTable() {
		super(Material.rock);
	}
	
	public BlockMagicianTable(Material p_i45394_1_) {
		super(p_i45394_1_);
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
    
    public int getRenderType()
    {
        return 2634;
    }

	@Override
	public TileEntity createNewTileEntity(World p_149915_1_, int p_149915_2_) {
		// TODO Auto-generated method stub
		return new TileMagicianTable();
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
	        		ItemStack currentItem = par5EntityPlayer.getCurrentEquippedItem();
	        		if(currentItem == null || !MagicianTableUpgrades.isItemUpgrade(currentItem))
	        		{
		        		par5EntityPlayer.openGui(EssentialCraftCore.core, Config.instance.guiID[0], par1World, par2, par3, par4);
		            	return true;
	        		}else
	        		{
	        			TileMagicianTable table = (TileMagicianTable) par1World.getTileEntity(par2, par3, par4);
	        			if(table.upgrade != -1)
	        			{
	        				ItemStack dropped = MagicianTableUpgrades.createStackByUpgradeID(table.upgrade);
	        				if(dropped != null)
	        				{
	        					if(dropped.stackSize == 0)dropped.stackSize = 1;
	        					EntityItem itm = new EntityItem(par1World, par2+0.5D, par3+1.5D, par4+0.5D, dropped);
	        					itm.delayBeforeCanPickup = 30;
	        					table.upgrade = -1;
	        					table.syncTick = 0;
	        					par1World.spawnEntityInWorld(itm);
	        				}
	        			}else
	        			{
        					table.upgrade = MagicianTableUpgrades.getUpgradeIDByItemStack(currentItem);
        					table.syncTick = 0;
	        				par5EntityPlayer.inventory.decrStackSize(par5EntityPlayer.inventory.currentItem, 1);
	        			}
	        			return true;
	        		}
	        	}
	        	else
	        	{
        			TileMagicianTable table = (TileMagicianTable) par1World.getTileEntity(par2, par3, par4);
        			if(table.upgrade != -1)
        			{
        				ItemStack dropped = MagicianTableUpgrades.createStackByUpgradeID(table.upgrade);
        				if(dropped != null)
        				{
        					if(dropped.stackSize == 0)dropped.stackSize = 1;
        					EntityItem itm = new EntityItem(par1World, par2+0.5D, par3+1.5D, par4+0.5D, dropped);
        					itm.delayBeforeCanPickup = 30;
        					table.upgrade = -1;
        					table.syncTick = 0;
        					par1World.spawnEntityInWorld(itm);
        				}
        			}
	        		return false;
	        	}
	        }
	    }
}
