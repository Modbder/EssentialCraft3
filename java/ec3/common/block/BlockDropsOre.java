package ec3.common.block;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ec3.common.item.ItemsCore;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import net.minecraftforge.event.ForgeEventFactory;

public class BlockDropsOre extends Block{

	public IIcon[] icons = new IIcon[4];
	
	
	@Override
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister reg)
    {
		icons[0] = reg.registerIcon("essentialcraft:elemental_base");
		icons[1] = reg.registerIcon("essentialcraft:elemental_nether");
		icons[2] = reg.registerIcon("essentialcraft:elemental_end");
		icons[3] = reg.registerIcon("essentialcraft:elemental");
    }
	
    public IIcon getIcon(int par1, int par2)
    {
        return par2 < 5 ? icons[0] : par2 >= 5 && par2 < 10 ? icons[1] : icons[2];
    }
	
	public BlockDropsOre() {
		super(Material.rock);
	}
	
    public boolean isOpaqueCube()
    {
        return true;
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
    	for(int i = 0; i < 15; ++i)
    		p_149666_3_.add(new ItemStack(p_149666_1_, 1, i));
    }
    
    @Override
    public int damageDropped(int p_149692_1_)
    {
    	p_149692_1_ %= 5;
        return p_149692_1_ == 0 ? 4 : p_149692_1_-1;
    }
    
    @Override
    public Item getItemDropped(int p_149650_1_, Random p_149650_2_, int p_149650_3_)
    {
        return ItemsCore.drops;
    }
    
    @Override
    public int quantityDropped(Random p_149745_1_)
    {
        return 1+p_149745_1_.nextInt(2);
    }

    public ArrayList<ItemStack> getDrops(World world, int x, int y, int z, int metadata, int fortune)
    {
        ArrayList<ItemStack> ret = new ArrayList<ItemStack>();

        int count = quantityDropped(metadata, fortune, world.rand);
        for(int i = 0; i < count; i++)
        {
            Item item = getItemDropped(metadata, world.rand, fortune);
            if (item != null)
            {
                ret.add(new ItemStack(item, 1, damageDropped(metadata)));
            }
        }
        return ret;
    }
    
    @Override
    public void dropBlockAsItemWithChance(World p_149690_1_, int p_149690_2_, int p_149690_3_, int p_149690_4_, int p_149690_5_, float p_149690_6_, int p_149690_7_)
    {
    	super.dropBlockAsItemWithChance(p_149690_1_, p_149690_2_, p_149690_3_, p_149690_4_, p_149690_5_, p_149690_6_, p_149690_7_);
    }
}
