package ec3.common.item;

import DummyCore.Utils.MiscUtils;
import ec3.utils.common.ECUtils;
import net.minecraft.block.Block;
import net.minecraft.block.BlockMobSpawner;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntityMobSpawner;
import net.minecraft.world.World;

public class ItemSpawnerCollector extends ItemStoresMRUInNBT {

	public ItemSpawnerCollector() {
		super();	
		this.setMaxMRU(5000);
		this.maxStackSize = 1;
		this.bFull3D = true;
	}
    
	@Override
    public boolean onItemUseFirst(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ)
    {
    	Block b = world.getBlock(x, y, z);
    	if(b != null && b instanceof BlockMobSpawner)
    	{
    		if(world.getTileEntity(x, y, z) == null || !(world.getTileEntity(x, y, z) instanceof TileEntityMobSpawner))
    			return false;
    		
        	if(ECUtils.tryToDecreaseMRUInStorage(player, -5000) || this.setMRU(stack, -5000))
        	{
	    		TileEntityMobSpawner t = (TileEntityMobSpawner) world.getTileEntity(x, y, z);
	    		NBTTagCompound mobTag = new NBTTagCompound();
	    		t.writeToNBT(mobTag);
	    		ItemStack collectedSpawner = new ItemStack(ItemsCore.collectedSpawner,1,0);
	    		MiscUtils.getStackTag(collectedSpawner).setTag("monsterSpawner", mobTag);
	    		EntityItem item = new EntityItem(world,x+0.5D,y+0.5D,z+0.5D,collectedSpawner);
	    		if(!world.isRemote)
	    			world.spawnEntityInWorld(item);
	    		
	    		world.setBlockToAir(x, y, z);
	    		player.swingItem();
        	}
    	}

        return false;
    }
}
