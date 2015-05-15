package ec3.common.item;

import java.util.List;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ec3.common.tile.TileElementalCrystal;
import DummyCore.Utils.MiscUtils;
import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

public class ItemBlockElementalCrystal extends ItemBlock{

	public ItemBlockElementalCrystal(Block p_i45328_1_) {
		super(p_i45328_1_);
        this.setMaxDamage(0);
        this.setHasSubtypes(true);
	}
    
    public boolean onItemUse(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, World par3World, int par4, int par5, int par6, int par7, float par8, float par9, float par10)
    {
    	float size = 0;
    	float fire = 0;
    	float water = 0;
    	float earth = 0;
    	float air = 0;
    	if(MiscUtils.getStackTag(par1ItemStack) != null)
    	{
    		size = MiscUtils.getStackTag(par1ItemStack).getFloat("size");
    		fire = MiscUtils.getStackTag(par1ItemStack).getFloat("fire");
    		water = MiscUtils.getStackTag(par1ItemStack).getFloat("water");
    		earth = MiscUtils.getStackTag(par1ItemStack).getFloat("earth");
    		air = MiscUtils.getStackTag(par1ItemStack).getFloat("air");
    	}
        Block i1 = par3World.getBlock(par4, par5, par6);

        if (i1 == Blocks.snow && (par3World.getBlockMetadata(par4, par5, par6) & 7) < 1)
        {
            par7 = 1;
        }
        else if (i1 != Blocks.vine && i1 != Blocks.tallgrass && i1 != Blocks.deadbush
                && (i1 == null || !i1.isReplaceable(par3World, par4, par5, par6)))
        {
            if (par7 == 0)
            {
                --par5;
            }

            if (par7 == 1)
            {
                ++par5;
            }

            if (par7 == 2)
            {
                --par6;
            }

            if (par7 == 3)
            {
                ++par6;
            }

            if (par7 == 4)
            {
                --par4;
            }

            if (par7 == 5)
            {
                ++par4;
            }
        }

        if (par1ItemStack.stackSize == 0)
        {
            return false;
        }
        else if (!par2EntityPlayer.canPlayerEdit(par4, par5, par6, par7, par1ItemStack))
        {
            return false;
        }
        else if (par5 == 255)
        {
            return false;
        }
        else if (par3World.canPlaceEntityOnSide(this.field_150939_a, par4, par5, par6, false, par7, par2EntityPlayer, par1ItemStack))
        {
            Block block = field_150939_a;
            int j1 = this.getMetadata(par1ItemStack.getItemDamage());
            int k1 = block.onBlockPlaced(par3World, par4, par5, par6, par7, par8, par9, par10, j1);

            if (placeBlockAt(par1ItemStack, par2EntityPlayer, par3World, par4, par5, par6, par7, par8, par9, par10, k1))
            {
            	par3World.setBlockMetadataWithNotify(par4, par5, par6, par7, 3);
                par3World.playSoundEffect((double)((float)par4 + 0.5F), (double)((float)par5 + 0.5F), (double)((float)par6 + 0.5F), block.stepSound.func_150496_b(), (block.stepSound.getVolume() + 1.0F) / 2.0F, block.stepSound.getPitch() * 0.8F);
                --par1ItemStack.stackSize;
                TileElementalCrystal c = (TileElementalCrystal) par3World.getTileEntity(par4, par5, par6);
                if(c != null)
                {
                	c.size = size;
                	c.fire = fire;
                	c.water = water;
                	c.earth = earth;
                	c.air = air;
                }
		           
            }

            return true;
        }
        else
        {
            return false;
        }
    }

    /**
     * Returns the metadata of the block which this Item (ItemBlock) can place
     */
    public int getMetadata(int par1)
    {
        return par1;
    }
    
    @SuppressWarnings({ "unchecked", "rawtypes" })
    @SideOnly(Side.CLIENT)
    public void getSubItems(Item p_150895_1_, CreativeTabs p_150895_2_, List p_150895_3_)
    {
    	for(int i = 0; i < 5; ++i)
    	{
        	for(int i1 = 0; i1 < 4; ++i1)
        	{
        		ItemStack crystalStack = new ItemStack(p_150895_1_,1,0);
        		NBTTagCompound tag = new NBTTagCompound();
        		tag.setFloat("size", (i)*25);
        		float[] elements = new float[]{0,0,0,0};
        		elements[i1] = 100F;
        		tag.setFloat("fire", elements[0]);
        		tag.setFloat("water", elements[1]);
        		tag.setFloat("earth", elements[2]);
        		tag.setFloat("air", elements[3]);
        		crystalStack.setTagCompound(tag);
        		p_150895_3_.add(crystalStack);
        	}
    	}
    	for(int i = 0; i < 5; ++i)
    	{
    		ItemStack crystalStack = new ItemStack(p_150895_1_,1,0);
    		NBTTagCompound tag = new NBTTagCompound();
    		tag.setFloat("size", (i)*25);
    		float[] elements = new float[]{100,100,100,100};
    		tag.setFloat("fire", elements[0]);
    		tag.setFloat("water", elements[1]);
    		tag.setFloat("earth", elements[2]);
    		tag.setFloat("air", elements[3]);
    		crystalStack.setTagCompound(tag);
    		p_150895_3_.add(crystalStack);
    	}
    	for(int i = 0; i < 5; ++i)
    	{
    		ItemStack crystalStack = new ItemStack(p_150895_1_,1,0);
    		NBTTagCompound tag = new NBTTagCompound();
    		tag.setFloat("size", (i)*25);
    		float[] elements = new float[]{0,0,0,0};
    		tag.setFloat("fire", elements[0]);
    		tag.setFloat("water", elements[1]);
    		tag.setFloat("earth", elements[2]);
    		tag.setFloat("air", elements[3]);
    		crystalStack.setTagCompound(tag);
    		p_150895_3_.add(crystalStack);
    	}
    }
    
}
