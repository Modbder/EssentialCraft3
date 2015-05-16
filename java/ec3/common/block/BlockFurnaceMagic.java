package ec3.common.block;

import java.util.List;

import DummyCore.Utils.MiscUtils;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ec3.common.mod.EssentialCraftCore;
import ec3.common.tile.TileFurnaceMagic;
import ec3.utils.cfg.Config;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockFurnaceMagic extends BlockContainer{
	
	public static IIcon[] frontIcons = new IIcon[4];
	public static IIcon[] sideIcons = new IIcon[4];
	public static String[] names = new String[]{"fortified","magic","pale","void"};
	
	public BlockFurnaceMagic()
	{
		super(Material.rock);
	}
	
	@Override
    public void breakBlock(World par1World, int par2, int par3, int par4, Block par5, int par6)
    {
    	MiscUtils.dropItemsOnBlockBreak(par1World, par2, par3, par4, par5, par6);
    	super.breakBlock(par1World, par2, par3, par4, par5, par6);
    }
	
	@Override
	public IIcon getIcon(IBlockAccess par1IBlockAccess, int par2, int par3, int par4, int par5)
    {
		int meta = par1IBlockAccess.getBlockMetadata(par2, par3, par4);
		return (meta%4)+2 == par5 ? frontIcons[meta/4] : sideIcons[meta/4];
    }
	
    public int damageDropped(int meta)
    {
    	return meta/4*4;
    }
    
    @SuppressWarnings({ "unchecked", "rawtypes" })
	@SideOnly(Side.CLIENT)
    public void getSubBlocks(Item p_149666_1_, CreativeTabs p_149666_2_, List p_149666_3_)
    {
        p_149666_3_.add(new ItemStack(p_149666_1_, 1, 0));
        p_149666_3_.add(new ItemStack(p_149666_1_, 1, 4));
        p_149666_3_.add(new ItemStack(p_149666_1_, 1, 8));
        p_149666_3_.add(new ItemStack(p_149666_1_, 1, 12));
    }
	
    public void onBlockAdded(World w, int x, int y, int z)
    {
        super.onBlockAdded(w, x, y, z);
        this.setBlockRotation(w, x, y, z);
    }
    
    private void setBlockRotation(World w, int x, int y, int z)
    {
        if (!w.isRemote)
        {
            Block block = w.getBlock(x, y, z - 1);
            Block block1 = w.getBlock(x, y, z + 1);
            Block block2 = w.getBlock(x - 1,y, z);
            Block block3 = w.getBlock(x + 1, y, z);
            int b0 = w.getBlockMetadata(x, y, z)/4*4;

            if (block.func_149730_j() && !block1.func_149730_j())
            {
                b0 += 1;
                w.setBlockMetadataWithNotify(x, y, z, b0, 3);
                return;
            }

            if (block1.func_149730_j() && !block.func_149730_j())
            {
                b0 += 0;
                w.setBlockMetadataWithNotify(x, y, z, b0, 3);
                return;
            }

            if (block2.func_149730_j() && !block3.func_149730_j())
            {
                b0 += 3;
                w.setBlockMetadataWithNotify(x, y, z, b0, 3);
                return;
            }

            if (block3.func_149730_j() && !block2.func_149730_j())
            {
                b0 += 2;
                w.setBlockMetadataWithNotify(x, y, z, b0, 3);
                return;
            }
        }
    }
    
    public void onBlockPlacedBy(World w, int p_149689_2_, int p_149689_3_, int p_149689_4_, EntityLivingBase p_149689_5_, ItemStack p_149689_6_)
    {
        int l = MathHelper.floor_double((double)(p_149689_5_.rotationYaw * 4.0F / 360.0F) + 0.5D) & 3;
        int meta = w.getBlockMetadata(p_149689_2_, p_149689_3_, p_149689_4_)/4*4;
        if (l == 0)
        {
            w.setBlockMetadataWithNotify(p_149689_2_, p_149689_3_, p_149689_4_, meta, 3);
        }

        if (l == 1)
        {
            w.setBlockMetadataWithNotify(p_149689_2_, p_149689_3_, p_149689_4_, meta+3, 3);
        }

        if (l == 2)
        {
            w.setBlockMetadataWithNotify(p_149689_2_, p_149689_3_, p_149689_4_, meta+1, 3);
        }

        if (l == 3)
        {
            w.setBlockMetadataWithNotify(p_149689_2_, p_149689_3_, p_149689_4_, meta+2, 3);
        }
    }
	
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(int side, int meta)
    {
    	return 3 == side ? frontIcons[meta/4] : sideIcons[meta/4];
    }
    
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister r)
    {
    	sideIcons[0] = r.registerIcon("essentialcraft:fortifiedStone");
    	sideIcons[1] = r.registerIcon("essentialcraft:magicPlatingBlock");
    	sideIcons[2] = r.registerIcon("essentialcraft:palePlatingBlock");
    	sideIcons[3] = r.registerIcon("essentialcraft:voidStone");
    	for(int i = 0; i < 4; ++i)
    		frontIcons[i] = r.registerIcon("essentialcraft:furnace_"+names[i]);
    }
    
    public TileEntity createNewTileEntity(World w, int meta)
    {
    	return new TileFurnaceMagic();
    }
    
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
