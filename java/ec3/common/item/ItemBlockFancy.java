package ec3.common.item;

import java.util.List;

import ec3.common.block.BlockFancy;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;

public class ItemBlockFancy extends ItemBlock{

	public ItemBlockFancy(Block p_i45328_1_) {
		super(p_i45328_1_);
		this.setHasSubtypes(true);
		this.setMaxDamage(0);
	}

    public int getMetadata(int par1)
    {
        return par1;
    }
    
    @SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
    public void addInformation(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, List par3List, boolean par4) 
    {
		par3List.add(StatCollector.translateToLocal("ec3.desc.fancy."+BlockFancy.overlays[par1ItemStack.getItemDamage()]));
    }
    
}
