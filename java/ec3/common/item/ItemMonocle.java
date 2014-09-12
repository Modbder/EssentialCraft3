package ec3.common.item;

import ec3.api.IItemAllowsSeeingMRUCU;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.item.Item;
import net.minecraft.util.IIcon;

public class ItemMonocle extends Item implements IItemAllowsSeeingMRUCU{
	
	public IIcon icon;
	public ItemMonocle() {
		super();
		this.maxStackSize = 1;
		this.setMaxDamage(16);
	}
	
	@Override
	public boolean isFull3D()
    {
        return true;
    }
    
	@Override
    public IIcon getIconFromDamage(int par1)
    {
        return icon;
    }
    
    @Override
    public void registerIcons(IIconRegister par1IconRegister)
    {
        this.icon = par1IconRegister.registerIcon("essentialcraft:magicMonocle");
    }
    
    @Override
    public int getItemEnchantability()
    {
        return 10;
    }

}
