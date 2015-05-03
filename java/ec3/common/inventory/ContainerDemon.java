package ec3.common.inventory;

import ec3.common.entity.EntityDemon;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerDemon extends Container{

	public EntityDemon trader;
	
	public ContainerDemon(EntityPlayer p, EntityDemon inv)
	{
		trader = inv;
		
		this.addSlotToContainer(new Slot(inv,0, 80,30));
        int i;

        
        for (i = 0; i < 3; ++i)
        {
            for (int j = 0; j < 9; ++j)
            {
                this.addSlotToContainer(new Slot(p.inventory, j + i * 9 + 9, 8 + j * 18,84+ i * 18));
            }
        }

        for (i = 0; i < 9; ++i)
        {
            this.addSlotToContainer(new Slot(p.inventory, i, 8 + i * 18, 142));
        }
	}
	
	@Override
	public boolean canInteractWith(EntityPlayer p) 
	{
		return trader.isUseableByPlayer(p);
	}
	
    public ItemStack transferStackInSlot(EntityPlayer p, int slot)
    {
    	return null;
    }

}
