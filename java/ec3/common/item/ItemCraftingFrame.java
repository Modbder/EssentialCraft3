package ec3.common.item;

import java.util.List;

import ec3.common.inventory.ContainerCraftingFrame;
import ec3.common.inventory.InventoryCraftingFrame;
import ec3.common.mod.EssentialCraftCore;
import ec3.utils.cfg.Config;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

public class ItemCraftingFrame extends Item{
	
	public IIcon[] itemIcons = new IIcon[2];
	
    public ItemStack onItemRightClick(ItemStack is, World w, EntityPlayer p)
    {
    	p.openGui(EssentialCraftCore.core, Config.guiID[0], w, 0, -2, 0);
    	return is;
    }
	protected int containerMatchesItem(Container openContainer) 
	{       
        return 0;
	}
	
    @Override
    public void registerIcons(IIconRegister par1IconRegister)
    {
    	super.registerIcons(par1IconRegister);
    }
    
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public void addInformation(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, List par3List, boolean par4) 
    {
    	InventoryCraftingFrame inv = new InventoryCraftingFrame(par1ItemStack);
    	if(inv != null)
    	{
    		par3List.add("Current recipe:");
    		for(int i = 0; i < 9; ++i)
    		{
    			ItemStack stk = inv.getStackInSlot(i);
    			
    			if(stk == null)
    				par3List.add(i+": Empty");
    			else
    				par3List.add(i+": "+stk.getDisplayName());
    		}
    		
    		ItemStack stk = inv.getStackInSlot(9);
			if(stk == null)
				par3List.add("Result: None");
			else
				par3List.add("Result: "+stk.getDisplayName());
    	}
    }
    
	@Override
	public void onUpdate(ItemStack itemStack, World world, Entity entity, int indexInInventory, boolean isCurrentItem)
	{
		if (world.isRemote || !isCurrentItem)
		{
			return;
		}
		if(((EntityPlayer) entity).openContainer == null || !(((EntityPlayer) entity).openContainer instanceof ContainerCraftingFrame))
		{
			return;
		}
		int containerType = containerMatchesItem(((EntityPlayer) entity).openContainer);
		if (containerType == 0) 
		{
			ContainerCraftingFrame c = (ContainerCraftingFrame) ((EntityPlayer) entity).openContainer;
                c.saveToNBT(itemStack);
		}
	}

	public ItemCraftingFrame()
	{
		this.setMaxStackSize(1);
	}
}
