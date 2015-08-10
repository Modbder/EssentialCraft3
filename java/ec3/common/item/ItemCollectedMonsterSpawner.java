package ec3.common.item;

import java.util.List;

import DummyCore.Utils.MiscUtils;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class ItemCollectedMonsterSpawner extends Item
{
	public ItemCollectedMonsterSpawner()
	{
		this.setHasSubtypes(true);
	}
	
    public boolean onItemUse(ItemStack stk, EntityPlayer placer, World w, int x, int y, int z, int side, float vecX, float vecY, float vecZ)
    {
    	ForgeDirection placedDir = ForgeDirection.VALID_DIRECTIONS[side];
    	int nX = x + placedDir.offsetX;
    	int nY = y + placedDir.offsetY;
    	int nZ = z + placedDir.offsetZ;
    	if(placer.canPlayerEdit(nX, nY, nZ, stk.getItemDamage(), stk))
    	{
    		if(Blocks.mob_spawner.canPlaceBlockAt(w, nX, nY, nZ))
    		{
    			NBTTagCompound tag = MiscUtils.getStackTag(stk);
    			if(tag.hasKey("monsterSpawner"))
    			{
    				NBTTagCompound spawnerTag = tag.getCompoundTag("monsterSpawner");
    				spawnerTag.setInteger("x", nX);
    				spawnerTag.setInteger("y", nY);
    				spawnerTag.setInteger("z", nZ);
    				if(w.setBlock(nX, nY, nZ, Blocks.mob_spawner, 0, 3))
    				{
    					TileEntity tile = w.getTileEntity(nX, nY, nZ);
    					if(tile != null)
    						tile.readFromNBT(spawnerTag);
    					
    					placer.swingItem();
    					--stk.stackSize;
    				}
    			}
    		}
    	}
    	return false;
    }
    
    @SuppressWarnings("rawtypes")
	@SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, EntityPlayer player, List lst, boolean isCurrentItem) 
    {
    	
    }
}
