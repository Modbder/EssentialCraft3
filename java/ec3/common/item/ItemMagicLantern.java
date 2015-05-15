package ec3.common.item;

import ec3.common.block.BlocksCore;
import ec3.utils.common.ECUtils;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class ItemMagicLantern extends ItemStoresMRUInNBT {

	public ItemMagicLantern() {
		super();	
		this.setMaxMRU(5000);
		this.maxStackSize = 1;
		this.bFull3D = true;
	}
	
	@Override
	public void onUpdate(ItemStack itemStack, World world, Entity entity, int indexInInventory, boolean isCurrentItem)
	{
		if(!world.isRemote)
		{
			int fX = MathHelper.floor_double(entity.posX);
			int fY = MathHelper.floor_double(entity.posY);
			int fZ = MathHelper.floor_double(entity.posZ);
			Block b = world.getBlock(fX, fY, fZ);
			if(b == Blocks.air)
			{
				if(isCurrentItem)
				{
					world.setBlock(fX, fY, fZ, BlocksCore.torch, 1, 3);
					world.scheduleBlockUpdate(fX, fY,fZ, BlocksCore.torch, 20);
				}
			}
		}
		super.onUpdate(itemStack, world, entity, indexInInventory, isCurrentItem);
	}
	
    
    public boolean onItemUseFirst(ItemStack stack, EntityPlayer player, World world, int px, int y, int pz, int side, float hitX, float hitY, float hitZ)
    {
    	ForgeDirection dir = ForgeDirection.getOrientation(side);
    	Block b = world.getBlock(px+dir.offsetX, y+dir.offsetY, pz+dir.offsetZ);
    	if(b == Blocks.air)
    	{
	        if(player.inventory.hasItem(ItemsCore.magicalSlag) && (ECUtils.tryToDecreaseMRUInStorage(player, -100) || this.setMRU(stack, -100)))
	        {
	        	int slotID = -1;
	        	for(int i = 0; i < player.inventory.getSizeInventory(); ++i)
	        	{
	        		ItemStack stk = player.inventory.getStackInSlot(i);
	        		if(stk != null && stk.getItem() == ItemsCore.magicalSlag)
	        		{
	        			slotID = i;
	        			break;
	        		}
	        	}
	        	if(slotID != -1)
	        	{
	        		player.inventory.decrStackSize(slotID, 1);
	        		world.setBlock(px+dir.offsetX, y+dir.offsetY, pz+dir.offsetZ, BlocksCore.torch, 0, 3);
	        		player.swingItem();
	        	}
	        }
    	}
        return false;
    }
    
}
