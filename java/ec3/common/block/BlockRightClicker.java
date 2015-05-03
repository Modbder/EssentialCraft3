package ec3.common.block;

import java.util.List;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import DummyCore.Utils.MiscUtils;
import ec3.common.mod.EssentialCraftCore;
import ec3.common.tile.TileRightClicker;
import ec3.utils.cfg.Config;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockRightClicker extends BlockContainer{

	public IIcon[] blockIcons = new IIcon[2];
	public IIcon[] metaRelatedIcons = new IIcon[6];
	
	public BlockRightClicker() {
		super(Material.rock);
	}
	
    public boolean isOpaqueCube()
    {
        return false;
    }
	
	@Override
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister p_149651_1_)
    {
    	this.metaRelatedIcons[0] = p_149651_1_.registerIcon("essentialcraft:rightClicker");
    	this.metaRelatedIcons[1] = p_149651_1_.registerIcon("essentialcraft:rightClicker_sneaky");
    	this.metaRelatedIcons[2] = p_149651_1_.registerIcon("essentialcraft:rightClickerExtended");
    	this.metaRelatedIcons[3] = p_149651_1_.registerIcon("essentialcraft:rightClickerExtended_sneaky");
    	this.metaRelatedIcons[4] = p_149651_1_.registerIcon("essentialcraft:rightClickerExtendedAdvanced");
    	this.metaRelatedIcons[5] = p_149651_1_.registerIcon("essentialcraft:rightClickerExtendedAdvanced_sneaky");
    	
    	this.blockIcons[0] = p_149651_1_.registerIcon("essentialcraft:fortifiedStone");
    }
	
    @SuppressWarnings({ "unchecked", "rawtypes" })
	@SideOnly(Side.CLIENT)
    public void getSubBlocks(Item p_149666_1_, CreativeTabs p_149666_2_, List p_149666_3_)
    {
        p_149666_3_.add(new ItemStack(p_149666_1_, 1, 0));
        p_149666_3_.add(new ItemStack(p_149666_1_, 1, 1));
        p_149666_3_.add(new ItemStack(p_149666_1_, 1, 2));
        p_149666_3_.add(new ItemStack(p_149666_1_, 1, 3));
        p_149666_3_.add(new ItemStack(p_149666_1_, 1, 4));
        p_149666_3_.add(new ItemStack(p_149666_1_, 1, 5));
    }
	
    public IIcon getIcon(int par1, int par2)
    {
        return par1 == 3 ? metaRelatedIcons[par2] : blockIcons[0];
    }

    @SideOnly(Side.CLIENT)
    public IIcon getIcon(IBlockAccess w, int x, int y, int z, int side)
    {
    	int meta = w.getBlockMetadata(x, y, z);
    	 TileRightClicker tile = TileRightClicker.class.cast(w.getTileEntity(x, y, z));
    	 return tile.getStackInSlot(2) != null && tile.getStackInSlot(2).getItem() instanceof ItemBlock ? ItemBlock.class.cast(tile.getStackInSlot(2).getItem()).field_150939_a.getIcon(side, tile.getStackInSlot(2).getItemDamage()) : side == tile.rotation ? metaRelatedIcons[meta] : blockIcons[0];
    }

	@Override
    public void breakBlock(World par1World, int par2, int par3, int par4, Block par5, int par6)
    {
		MiscUtils.dropItemsOnBlockBreak(par1World, par2, par3, par4, par5, par6);
		super.breakBlock(par1World, par2, par3, par4, par5, par6);
    }
	

	@Override
	public TileEntity createNewTileEntity(World p_149915_1_, int metadata) {
		return new TileRightClicker();
	}
	
    public void onBlockPlacedBy(World w, int x, int y, int z, EntityLivingBase p_149689_5_, ItemStack p_149689_6_)
    {
        int l = determineOrientation(w, x, y, z, p_149689_5_);
        TileRightClicker tile = TileRightClicker.class.cast(w.getTileEntity(x, y, z));
        tile.rotation = l;
    }
    
    public static int determineOrientation(World p_150071_0_, int p_150071_1_, int p_150071_2_, int p_150071_3_, EntityLivingBase p_150071_4_)
    {
        if (MathHelper.abs((float)p_150071_4_.posX - (float)p_150071_1_) < 2.0F && MathHelper.abs((float)p_150071_4_.posZ - (float)p_150071_3_) < 2.0F)
        {
            double d0 = p_150071_4_.posY + 1.82D - (double)p_150071_4_.yOffset;

            if (d0 - (double)p_150071_2_ > 2.0D)
            {
                return 1;
            }

            if ((double)p_150071_2_ - d0 > 0.0D)
            {
                return 0;
            }
        }

        int l = MathHelper.floor_double((double)(p_150071_4_.rotationYaw * 4.0F / 360.0F) + 0.5D) & 3;
        return l == 0 ? 2 : (l == 1 ? 5 : (l == 2 ? 3 : (l == 3 ? 4 : 0)));
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
