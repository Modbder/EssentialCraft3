package ec3.common.block;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import DummyCore.Utils.MiscUtils;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ec3.common.item.ItemsCore;
import ec3.common.mod.EssentialCraftCore;
import ec3.common.tile.TileColdDistillator;
import ec3.common.tile.TileCorruption;
import ec3.common.tile.TileEmberForge;
import ec3.common.tile.TileEnderGenerator;
import ec3.common.tile.TileFlowerBurner;
import ec3.common.tile.TileHeatGenerator;
import ec3.common.tile.TileMagicalEnchanter;
import ec3.common.tile.TileMagicalFurnace;
import ec3.common.tile.TileMagicalTeleporter;
import ec3.common.tile.TileMoonWell;
import ec3.common.tile.TilePotionSpreader;
import ec3.common.tile.TileSunRayAbsorber;
import ec3.utils.cfg.Config;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import net.minecraftforge.event.ForgeEventFactory;

public class BlockEmberForge extends BlockContainer{
	
	public IIcon[] renderIcon = new IIcon[2];

	public BlockEmberForge(Material p_i45394_1_) {
		super(p_i45394_1_);
	}
	
	public BlockEmberForge() {
		super(Material.rock);
	}


	@Override
	public TileEntity createNewTileEntity(World p_149915_1_, int p_149915_2_) {
		// TODO Auto-generated method stub
		return new TileEmberForge();
	}
	
	@Override
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister p_149651_1_)
    {
    	this.renderIcon[1] = p_149651_1_.registerIcon("essentialcraft:ember_anvil_top");
    	this.renderIcon[0] = p_149651_1_.registerIcon("essentialcraft:fortifiedStone");
    }
	
    public IIcon getIcon(int par1, int par2)
    {
    	if(par1 == 1)
    	{
    		 return this.renderIcon[1];
    	}
        return this.renderIcon[0];
    }
	
}
