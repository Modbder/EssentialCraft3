package ec3.common.item;

import java.util.List;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ec3.common.world.WorldGenMRUTower;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class TestItem_EC extends Item{
    public boolean onItemUse(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, World w, int x, int y, int z, int par7, float par8, float par9, float par10)
    {
    	if(!w.isRemote)
    	{
    		new WorldGenMRUTower().generate(w, w.rand, x, y, z);
    	}
        return true;
    }
    
    @SuppressWarnings("rawtypes")
	@SideOnly(Side.CLIENT)
    public void addInformation(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, List par3List, boolean par4)
    {
    	
    }
}
