package ec3.common.inventory;

import ec3.common.tile.TileWeaponMaker;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;

public class ContainerWeaponBench extends Container{

    public ContainerWeaponBench(InventoryPlayer par1InventoryPlayer, TileEntity par2)
    {
    	TileWeaponMaker maker = TileWeaponMaker.class.cast(par2);
    	
        this.addSlotToContainer(new Slot((IInventory) par2, 0, 152, 37));
        
        if(maker.index == 0)
        {
        	//Elemental Core
        	this.addSlotToContainer(new Slot((IInventory) par2, 1, 40, 37));
        	//Soul Sphere
        	this.addSlotToContainer(new Slot((IInventory) par2, 2, 60, 57));
        	//Lens
        	this.addSlotToContainer(new Slot((IInventory) par2, 3, 20, 17));
        	//Scope
        	this.addSlotToContainer(new Slot((IInventory) par2, 4, 60, 17));
        	//Base
        	this.addSlotToContainer(new Slot((IInventory) par2, 5, 40, 17));
        	this.addSlotToContainer(new Slot((IInventory) par2, 6, 60, 37));
        	this.addSlotToContainer(new Slot((IInventory) par2, 7, 80, 37));
        	//Mechanism
        	this.addSlotToContainer(new Slot((IInventory) par2, 8, 80, 17));
        	//Handle
        	this.addSlotToContainer(new Slot((IInventory) par2, 9, 80, 57));
        }
        
        if(maker.index == 1)
        {
        	//Elemental Core
        	this.addSlotToContainer(new Slot((IInventory) par2, 1, 40, 37));
        	//Soul Sphere
        	this.addSlotToContainer(new Slot((IInventory) par2, 2, 60, 37));
        	//Lens
        	this.addSlotToContainer(new Slot((IInventory) par2, 3, 20, 37));
        	//Scope
        	this.addSlotToContainer(new Slot((IInventory) par2, 4, 40, 17));
        	//Base
        	this.addSlotToContainer(new Slot((IInventory) par2, 5, 20, 17));
        	this.addSlotToContainer(new Slot((IInventory) par2, 6, 60, 17));
        	this.addSlotToContainer(new Slot((IInventory) par2, 7, 80, 37));
        	this.addSlotToContainer(new Slot((IInventory) par2, 8, 60, 57));
        	//Mechanism
        	this.addSlotToContainer(new Slot((IInventory) par2, 9, 80, 17));
        	//Handle
        	this.addSlotToContainer(new Slot((IInventory) par2, 10, 20, 57));
        	this.addSlotToContainer(new Slot((IInventory) par2, 11, 40, 57));
        	this.addSlotToContainer(new Slot((IInventory) par2, 12, 80, 57));
        }
        
        if(maker.index == 2)
        {
        	//Elemental Core
        	this.addSlotToContainer(new Slot((IInventory) par2, 1, 60, 37));
        	//Soul Sphere
        	this.addSlotToContainer(new Slot((IInventory) par2, 2, 40, 57));
        	//Lens
        	this.addSlotToContainer(new Slot((IInventory) par2, 3, 20, 37));
        	//Scope
        	this.addSlotToContainer(new Slot((IInventory) par2, 4, 60, 17));
        	this.addSlotToContainer(new Slot((IInventory) par2, 5, 40, 17));
        	this.addSlotToContainer(new Slot((IInventory) par2, 6, 80, 17));
        	//Base
        	this.addSlotToContainer(new Slot((IInventory) par2, 7, 40, 37));
        	this.addSlotToContainer(new Slot((IInventory) par2, 8, 80, 37));
        	this.addSlotToContainer(new Slot((IInventory) par2, 9, 100, 37));
        	//Mechanism
        	this.addSlotToContainer(new Slot((IInventory) par2, 10, 100, 17));
        	this.addSlotToContainer(new Slot((IInventory) par2, 11, 60, 57));
        	//Handle
        	this.addSlotToContainer(new Slot((IInventory) par2, 12, 80, 57));
        	this.addSlotToContainer(new Slot((IInventory) par2, 13, 100, 57));
        }
        
        if(maker.index == 3)
        {
        	//Elemental Core
        	this.addSlotToContainer(new Slot((IInventory) par2, 1, 80, 37));
        	//Soul Sphere
        	this.addSlotToContainer(new Slot((IInventory) par2, 2, 40, 57));
        	//Lens
        	this.addSlotToContainer(new Slot((IInventory) par2, 3, 20, 37));
        	this.addSlotToContainer(new Slot((IInventory) par2, 4, 20, 17));
        	this.addSlotToContainer(new Slot((IInventory) par2, 5, 20, 57));
        	//Scope
        	//No scope for the Gatling
        	//Base
        	this.addSlotToContainer(new Slot((IInventory) par2, 6, 40, 17));
        	this.addSlotToContainer(new Slot((IInventory) par2, 7, 60, 17));
        	this.addSlotToContainer(new Slot((IInventory) par2, 8, 80, 17));
        	this.addSlotToContainer(new Slot((IInventory) par2, 9, 100, 17));
        	this.addSlotToContainer(new Slot((IInventory) par2, 10, 120, 17));
        	this.addSlotToContainer(new Slot((IInventory) par2, 11, 40, 37));
        	this.addSlotToContainer(new Slot((IInventory) par2, 12, 120, 37));
        	//Mechanism
        	this.addSlotToContainer(new Slot((IInventory) par2, 13, 60, 37));
        	this.addSlotToContainer(new Slot((IInventory) par2, 14, 100, 37));
        	//Handle
        	this.addSlotToContainer(new Slot((IInventory) par2, 15, 60, 57));
        	this.addSlotToContainer(new Slot((IInventory) par2, 16, 80, 57));
        	this.addSlotToContainer(new Slot((IInventory) par2, 17, 100, 57));
        	this.addSlotToContainer(new Slot((IInventory) par2, 18, 120, 57));
        }
        
        int i;

        for (i = 0; i < 3; ++i)
        {
            for (int j = 0; j < 9; ++j)
            {
                this.addSlotToContainer(new Slot(par1InventoryPlayer, j + i * 9 + 9, 8 + j * 18,84+ i * 18));
            }
        }

        for (i = 0; i < 9; ++i)
        {
            this.addSlotToContainer(new Slot(par1InventoryPlayer, i, 8 + i * 18, 142));
        }
    }

	@Override
	public boolean canInteractWith(EntityPlayer p_75145_1_) {
		return true;
	}

	@Override
    public ItemStack transferStackInSlot(EntityPlayer par1EntityPlayer, int par2)
    {
        return null;
    }
}
