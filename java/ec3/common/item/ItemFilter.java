package ec3.common.item;

import java.util.List;

import ec3.common.inventory.ContainerFilter;
import ec3.common.mod.EssentialCraftCore;
import ec3.utils.cfg.Config;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;

public class ItemFilter extends Item{
	
	public IIcon[] itemIcons = new IIcon[4];
	
    public ItemStack onItemRightClick(ItemStack is, World w, EntityPlayer p)
    {
    	p.openGui(EssentialCraftCore.core, Config.guiID[0], w, 0, -1, 0);
    	return is;
    }
	protected int containerMatchesItem(Container openContainer) 
	{       
        return 0;
	}
	
    @Override
    public void registerIcons(IIconRegister par1IconRegister)
    {
    	itemIcons[0] = par1IconRegister.registerIcon("essentialcraft:itemFilter");
    	itemIcons[1] = par1IconRegister.registerIcon("essentialcraft:itemFilterAdvanced");
    	itemIcons[2] = par1IconRegister.registerIcon("essentialcraft:itemFilterB");
    	itemIcons[3] = par1IconRegister.registerIcon("essentialcraft:itemFilterAdvancedB");
    }
    
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public void getSubItems(Item par1, CreativeTabs par2CreativeTabs, List par3List)
    {
        for (int var4 = 0; var4 < 4; ++var4)
        {
        	ItemStack min = new ItemStack(par1, 1, var4);
            par3List.add(min);
        }
    }
    
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public void addInformation(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, List par3List, boolean par4) 
    {
    	if(par1ItemStack.getItemDamage() == 1 || par1ItemStack.getItemDamage() == 3)
    		par3List.add(StatCollector.translateToLocal("ec3.txt.desc.advanced"));
    	if(par1ItemStack.getItemDamage() > 1)
    		par3List.add(StatCollector.translateToLocal("ec3.txt.desc.blacklist"));
    	else
    		par3List.add(StatCollector.translateToLocal("ec3.txt.desc.whitelist"));
    }
    
    public IIcon getIconFromDamage(int par1)
    {
    	return itemIcons[par1];
    }
	
	@Override
	public void onUpdate(ItemStack itemStack, World world, Entity entity, int indexInInventory, boolean isCurrentItem)
	{
		if (world.isRemote || !isCurrentItem)
		{
			return;
		}
		if(((EntityPlayer) entity).openContainer == null || !(((EntityPlayer) entity).openContainer instanceof ContainerFilter))
		{
			return;
		}
		int containerType = containerMatchesItem(((EntityPlayer) entity).openContainer);
		if (containerType == 0) 
		{
	        ContainerFilter c = (ContainerFilter) ((EntityPlayer) entity).openContainer;
                c.saveToNBT(itemStack);
		}
	}

	public ItemFilter()
	{
		this.setHasSubtypes(true);
		this.setMaxDamage(0);
	}
}
