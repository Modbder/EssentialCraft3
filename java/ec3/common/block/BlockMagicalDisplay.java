package ec3.common.block;

import java.util.Random;

import ec3.common.item.ItemsCore;
import ec3.common.tile.TileMagicalDisplay;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockMagicalDisplay extends BlockContainer{

	public BlockMagicalDisplay() {
		super(Material.rock);
	}
	
    public Item getItemDropped(int p_149650_1_, Random p_149650_2_, int p_149650_3_)
    {
    	return ItemsCore.genericItem;
    }
    
    public int damageDropped(int p_149692_1_)
    {
        return 27;
    }
    

	@Override
	public TileEntity createNewTileEntity(World p_149915_1_, int p_149915_2_) {
		return new TileMagicalDisplay();
	}


    public boolean onBlockActivated(World w, int x, int y, int z, EntityPlayer p, int p_149727_6_, float p_149727_7_, float p_149727_8_, float p_149727_9_)
    {
    	ItemStack is = p.getCurrentEquippedItem();
    	TileMagicalDisplay display = (TileMagicalDisplay) w.getTileEntity(x, y, z);
    	if(is != null)
    	{
    		if(display.getStackInSlot(0) == null)
    		{
    			ItemStack sett = is.copy();
    			sett.stackSize = 1;
    			display.setInventorySlotContents(0, sett);
    			p.inventory.decrStackSize(p.inventory.currentItem, 1);
    			
    		}else
    		{
    			ItemStack dropped = display.getStackInSlot(0);
				if(dropped != null && !w.isRemote)
				{
					if(dropped.stackSize == 0)dropped.stackSize = 1;
					EntityItem itm = new EntityItem(w, x+0.5D, y+0.5D, z+0.5D, dropped);
					itm.delayBeforeCanPickup = 30;
					display.setInventorySlotContents(0, null);
					w.spawnEntityInWorld(itm);
				}
    		}
    		display.syncTick = 0;
    		//...
    	}
    	else
    	{
    		if(p.isSneaking())
    		{
    			ItemStack dropped = display.getStackInSlot(0);
				if(dropped != null && !w.isRemote)
				{
					if(dropped.stackSize == 0)dropped.stackSize = 1;
					EntityItem itm = new EntityItem(w, x+0.5D, y+0.5D, z+0.5D, dropped);
					itm.delayBeforeCanPickup = 30;
					display.setInventorySlotContents(0, null);
					w.spawnEntityInWorld(itm);
					display.syncTick = 1;
				}
    		}
    		else
    		{
	    		++display.type;
	    		if(display.type >= 3)
	    			display.type = 0;
	    		display.syncTick = 1;
    		}
    	}
    	return true;
    }
	
    @Override
    public int onBlockPlaced(World w, int x, int y, int z, int side, float hitX, float hitY, float hitZ, int meta)
    {
        return side;
    }
    
    public void setBlockBoundsBasedOnState(IBlockAccess p_149719_1_, int p_149719_2_, int p_149719_3_, int p_149719_4_)
    {
    	int metadata = p_149719_1_.getBlockMetadata(p_149719_2_, p_149719_3_, p_149719_4_);
    	if(metadata == 0)
    	{
    		this.setBlockBounds(0, 0.95F, 0, 1, 1, 1F);
    	}
    	if(metadata == 1)
    	{
    		this.setBlockBounds(0, 0, 0, 1, 0.05F, 1F);
    	}
    	if(metadata == 2)
    	{
    		this.setBlockBounds(0, 0, 0.95F, 1, 1, 1F);
    	}
    	if(metadata == 3)
    	{
    		this.setBlockBounds(0, 0, 0.0F, 1, 1, 0.05F);
    	}
    	if(metadata == 4)
    	{
    		this.setBlockBounds(0.95F, 0, 0, 1, 1, 1F);
    	}
    	if(metadata == 5)
    	{
    		this.setBlockBounds(0.0F, 0, 0, 0.05F, 1, 1);
    	}
    	super.setBlockBoundsBasedOnState(p_149719_1_, p_149719_2_, p_149719_3_, p_149719_4_);
    }
    
    public boolean isOpaqueCube()
    {
        return false;
    }
    
	@Override
    public int getRenderBlockPass()
    {
        return 0;
    }
    
    public boolean renderAsNormalBlock()
    {
        return false;
    }
    
    public int getRenderType()
    {
        return 2634;
    }
}
