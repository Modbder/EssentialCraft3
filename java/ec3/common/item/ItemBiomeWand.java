package ec3.common.item;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ec3.utils.common.ECUtils;
import DummyCore.Utils.MiscUtils;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;

public class ItemBiomeWand extends ItemStoresMRUInNBT {

	public ItemBiomeWand() {
		super();	
		this.setMaxMRU(5000);
		this.maxStackSize = 1;
		this.bFull3D = true;
	}
	
	public static boolean isBiomeSaved(ItemStack stack)
	{
		NBTTagCompound tag = MiscUtils.getStackTag(stack);
		return tag.hasKey("biome");
	}
	
	public static int getBiomeID(ItemStack stack)
	{
		NBTTagCompound tag = MiscUtils.getStackTag(stack);
		if(isBiomeSaved(stack))
			return tag.getInteger("biome");
		return -1;
	}
	
	public static void setBiomeID(ItemStack stack, int bID, boolean remove)
	{
		NBTTagCompound tag = MiscUtils.getStackTag(stack);
		if(remove)
		{
			tag.removeTag("biome");
			return;
		}else
		{
			tag.setInteger("biome", bID);
		}
		stack.setTagCompound(tag);
	}
    
    public boolean onItemUseFirst(ItemStack stack, EntityPlayer player, World world, int px, int y, int pz, int side, float hitX, float hitY, float hitZ)
    {
    	if(!player.isSneaking())
    	{
	    	if(isBiomeSaved(stack))
	    	{
		    	if(ECUtils.tryToDecreaseMRUInStorage(player, -100) || this.setMRU(stack, -100))
		    	{
		    		for(int x = px-1; x <= px+1; ++x)
		    		{
			    		for(int z = pz-1; z <= pz+1; ++z)
			    		{
			    			MiscUtils.changeBiome(world, BiomeGenBase.getBiome(getBiomeID(stack)), x, z);
					    	player.swingItem();
			    		}
		    		}
		    	}
	    	}else
	    	{
				int cbiome = world.getBiomeGenForCoords(px, pz).biomeID;
				setBiomeID(stack,cbiome,false);
				player.swingItem();
	    	}
    	}else
    	{
    		setBiomeID(stack,0,true);
    		player.swingItem();
    	}
        return false;
    }
    
    @SideOnly(Side.CLIENT)
    public int getColorFromItemStack(ItemStack par1ItemStack, int par2)
    {
    	if(isBiomeSaved(par1ItemStack))
    		return BiomeGenBase.getBiome(getBiomeID(par1ItemStack)).color;
        return 0x00ffff;
    }
}
