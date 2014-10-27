package ec3.common.item;

import java.util.List;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ec3.common.block.BlocksCore;
import ec3.common.world.WorldGenDestroyedHouse;
import ec3.common.world.WorldGenElderMRUCC;
import ec3.common.world.WorldGenOldCatacombs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.util.WeightedRandomChestContent;
import net.minecraft.world.World;

public class TestItem_EC extends Item{
    public boolean onItemUse(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, World w, int x, int y, int z, int par7, float par8, float par9, float par10)
    {
    	if(!w.isRemote)
    	{
			w.setBlock(x, y, z, Blocks.chest,0,3);
			w.setBlock(x, y-1, z, BlocksCore.voidStone);
			TileEntityChest chest = (TileEntityChest) w.getTileEntity(x, y, z);
            if (chest != null)
            {
                WeightedRandomChestContent.generateChestContents(w.rand, WorldGenOldCatacombs.generatedItems, chest, w.rand.nextInt(12)+6);
                IInventory inv = chest;
                for(int i = 0; i < inv.getSizeInventory(); ++i)
                {
                	ItemStack stk = inv.getStackInSlot(i);
                	if(stk != null && stk.getItem() instanceof ItemBaublesWearable)
                	{
                		ItemBaublesWearable.initRandomTag(stk, w.rand);
                	}
                }
            }
    	}
        return true;
    }
    
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, List par3List, boolean par4)
    {
    	
    }
}
