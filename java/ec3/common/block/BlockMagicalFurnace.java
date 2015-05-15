package ec3.common.block;

import DummyCore.Utils.MiscUtils;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ec3.common.mod.EssentialCraftCore;
import ec3.common.tile.TileMagicalFurnace;
import ec3.utils.cfg.Config;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

public class BlockMagicalFurnace extends BlockContainer{
	
	public IIcon[] renderIcon = new IIcon[2];

	public BlockMagicalFurnace(Material p_i45394_1_) {
		super(p_i45394_1_);
	}
	
	public BlockMagicalFurnace() {
		super(Material.rock);
	}


	@Override
	public TileEntity createNewTileEntity(World p_149915_1_, int p_149915_2_) {
		// TODO Auto-generated method stub
		return new TileMagicalFurnace();
	}
	
	@Override
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister p_149651_1_)
    {
    	this.renderIcon[1] = p_149651_1_.registerIcon("essentialcraft:magic_furnace_top");
    	this.renderIcon[0] = p_149651_1_.registerIcon("essentialcraft:fortifiedStone");
    }
	
	@Override
    public void breakBlock(World par1World, int par2, int par3, int par4, Block par5, int par6)
    {
		MiscUtils.dropItemsOnBlockBreak(par1World, par2, par3, par4, par5, par6);
		super.breakBlock(par1World, par2, par3, par4, par5, par6);
    }
	
    public IIcon getIcon(int par1, int par2)
    {
    	if(par1 == 1)
    	{
    		 return this.renderIcon[1];
    	}
        return this.renderIcon[0];
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
