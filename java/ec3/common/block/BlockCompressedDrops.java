package ec3.common.block;

import java.util.List;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ec3.common.item.ItemDrop;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;

public class BlockCompressedDrops extends Block{

	public IIcon[] iconArray = new IIcon[5];
	
	public BlockCompressedDrops() 
	{
		super(Material.rock);
	}
	
    @SuppressWarnings({ "unchecked", "rawtypes" })
	@SideOnly(Side.CLIENT)
    public void getSubBlocks(Item p_149666_1_, CreativeTabs p_149666_2_, List p_149666_3_)
    {
    	for(int i = 0; i < 5; ++i)
    		p_149666_3_.add(new ItemStack(p_149666_1_, 1, i));
    }
	
    @Override
    public int damageDropped(int p_149692_1_)
    {
        return p_149692_1_;
    }
	
	@Override
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister reg)
    {
		for(int i = 0; i < 5; ++i)
			iconArray[i] = reg.registerIcon("essentialcraft:compressed/drops_"+ItemDrop.dropNames[i]);
    }
	
	@Override
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(int i, int j)
    {
		return iconArray[j];
    }

}
