package ec3.common.block;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ec3.common.mod.EssentialCraftCore;
import ec3.common.tile.TileNewMIMScreen;
import ec3.utils.cfg.Config;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class BlockMIMScreen extends BlockContainer{
	
	public IIcon[] blockIcons = new IIcon[2];
	
	public BlockMIMScreen() {
		super(Material.rock);
	}
	
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(IBlockAccess w, int x, int y, int z, int side)
    {
    	int meta = w.getBlockMetadata(x, y, z);
    	return side == meta ? blockIcons[1] : blockIcons[0];
    }
	
    public IIcon getIcon(int side, int meta)
    {
        return side == 3 ? blockIcons[1] : blockIcons[0];
    }
    
	@Override
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister p_149651_1_)
    {
    	this.blockIcons[0] = p_149651_1_.registerIcon("essentialcraft:mimInventoryHandler_TB");
    	this.blockIcons[1] = p_149651_1_.registerIcon("essentialcraft:mimScreen");
    }
	
    public boolean canProvidePower()
    {
        return true;
    }
    
    @Override
    public int onBlockPlaced(World w, int x, int y, int z, int side, float hitX, float hitY, float hitZ, int meta)
    {
        return ForgeDirection.values()[side].ordinal();
    }

	@Override
	public TileEntity createNewTileEntity(World p_149915_1_, int p_149915_2_) {
		return new TileNewMIMScreen();
	}
	
    public boolean onBlockActivated(World par1World, int par2, int par3, int par4, EntityPlayer par5EntityPlayer, int p_149727_6_, float p_149727_7_, float p_149727_8_, float p_149727_9_)
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
        	}else
        	{
        		return false;
        	}
        }
    }
}
