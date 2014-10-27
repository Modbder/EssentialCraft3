package ec3.common.block;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ec3.common.item.ItemsCore;
import ec3.common.tile.TileCorruption;
import ec3.network.proxy.ClientProxy;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import net.minecraftforge.event.ForgeEventFactory;

public class BlockCorruption_Light extends BlockContainer{
	
	public IIcon[] iconArray = new IIcon[8];

	protected BlockCorruption_Light(Material p_i45394_1_) {
		super(p_i45394_1_);
	}
	
    public boolean isOpaqueCube()
    {
        return false;
    }
    
	@Override
    public int getRenderBlockPass()
    {
        return 1;
    }
    
    public AxisAlignedBB getCollisionBoundingBoxFromPool(World p_149668_1_, int p_149668_2_, int p_149668_3_, int p_149668_4_)
    {
        return null;
    }
    
    public boolean renderAsNormalBlock()
    {
        return false;
    }
    
    public int getRenderType()
    {
        return 2634;
    }
    
    @SideOnly(Side.CLIENT)
    public void getSubBlocks(Item p_149666_1_, CreativeTabs p_149666_2_, List p_149666_3_)
    {
    	for(int i = 0; i < 8; ++i)
    		p_149666_3_.add(new ItemStack(p_149666_1_, 1, i));
    }
    
    @Override
    public int damageDropped(int p_149692_1_)
    {
        return p_149692_1_;
    }
    
    @Override
    public Item getItemDropped(int p_149650_1_, Random p_149650_2_, int p_149650_3_)
    {
        return null;
    }
    
    @Override
    public int quantityDropped(Random p_149745_1_)
    {
        return 0;
    }

	@Override
	public TileEntity createNewTileEntity(World p_149915_1_, int p_149915_2_) {
		// TODO Auto-generated method stub
		return new TileCorruption();
	}
	
	@Override
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister p_149651_1_)
    {
		String folderName = textureName.substring(textureName.indexOf(':')+1, textureName.length());
		for(int i = 0; i < 8; ++i)
		{
			iconArray[i] = p_149651_1_.registerIcon("essentialcraft:corruption/"+folderName+"/"+i);
		}
		ClientProxy.chaosIcon = p_149651_1_.registerIcon("essentialcraft:chaos");
		ClientProxy.frozenIcon = p_149651_1_.registerIcon("essentialcraft:frozen");
    }
	
	@Override
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(int i, int j)
    {
        return iconArray[j];
    }
}
