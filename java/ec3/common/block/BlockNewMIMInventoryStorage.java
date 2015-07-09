package ec3.common.block;

import DummyCore.Utils.MiscUtils;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ec3.common.mod.EssentialCraftCore;
import ec3.common.tile.TileNewMIMInventoryStorage;
import ec3.utils.cfg.Config;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockNewMIMInventoryStorage extends BlockContainer{
	
	public BlockNewMIMInventoryStorage() 
	{
		super(Material.rock);
	}
	
	public IIcon tbIcon;
	public IIcon siIcon;
	
	@Override
	public TileEntity createNewTileEntity(World w, int meta) {
		return new TileNewMIMInventoryStorage();
	}
	
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(IBlockAccess w, int x, int y, int z, int side)
    {
    	return side <= 1 ? tbIcon : siIcon;
    }
	
    public IIcon getIcon(int side, int meta)
    {
        return side <= 1 ? tbIcon : siIcon;
    }
    
	@Override
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister reg)
    {
		tbIcon = blockIcon = reg.registerIcon("essentialcraft:mimInventoryHandler_TB");
		siIcon = reg.registerIcon("essentialcraft:mimInventoryHandler");
    }
	
	@Override
    public void breakBlock(World par1World, int par2, int par3, int par4, Block par5, int par6)
    {
		MiscUtils.dropItemsOnBlockBreak(par1World, par2, par3, par4, par5, par6);
		super.breakBlock(par1World, par2, par3, par4, par5, par6);
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
