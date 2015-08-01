package ec3.common.item;

import ec3.utils.common.ECUtils;
import net.minecraft.block.Block;
import net.minecraft.block.BlockMobSpawner;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
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
	    		Block id = world.getBlock(x, y, z);
	    		Entity e = EntityList.createEntityByName(t.func_145881_a().getEntityNameToSpawn(), world);
	    		int meta = EntityList.getEntityID(e);
	    		e.setDead();
	    		ItemStack s = new ItemStack(id,1,meta);
	    		EntityItem item = new EntityItem(world, x, y+1, z, s);
	    		if(!world.isRemote)
	    			world.spawnEntityInWorld(item);
	    		player.swingItem();
	    		world.setBlock(x, y, z, Blocks.air,0,3);
        	}
    	}

        return false;
    }
}
