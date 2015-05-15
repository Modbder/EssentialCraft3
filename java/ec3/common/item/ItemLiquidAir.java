package ec3.common.item;

import ec3.common.registry.PotionRegistry;
import ec3.utils.common.ECUtils;
import ec3.utils.common.WindRelations;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemLiquidAir extends Item {

	public ItemLiquidAir() {
		super();	
	}
	
	@Override
    public ItemStack onEaten(ItemStack p_77654_1_, World p_77654_2_, EntityPlayer p_77654_3_)
    {
		p_77654_3_.inventory.decrStackSize(p_77654_3_.inventory.currentItem,1);
		ECUtils.calculateAndAddPE(p_77654_3_, PotionRegistry.paranormalLightness, 8*60*20, 2*60*20);
		WindRelations.increasePlayerWindRelations(p_77654_3_, 100);
        return p_77654_1_;
    }

    /**
     * How long it takes to use or consume an item
     */
    public int getMaxItemUseDuration(ItemStack p_77626_1_)
    {
        return 32;
    }

    /**
     * returns the action that specifies what animation to play when the items is being used
     */
    public EnumAction getItemUseAction(ItemStack p_77661_1_)
    {
        return EnumAction.drink;
    }

    /**
     * Called whenever this item is equipped and the right mouse button is pressed. Args: itemStack, world, entityPlayer
     */
    public ItemStack onItemRightClick(ItemStack p_77659_1_, World p_77659_2_, EntityPlayer p_77659_3_)
    {
    	
        p_77659_3_.setItemInUse(p_77659_1_, this.getMaxItemUseDuration(p_77659_1_));
        return p_77659_1_;
    }

}
