package ec3.common.item;

import ec3.utils.common.WindRelations;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.World;

public class ItemWindKeeper extends Item{
	public int rel;
	
	public ItemWindKeeper(int windReleased)
	{
		super();
		rel = windReleased;
		
	}
	
    public ItemStack onItemRightClick(ItemStack p_77659_1_, World p_77659_2_, EntityPlayer p_77659_3_)
    {
    	--p_77659_1_.stackSize;
    	p_77659_3_.playSound("dig.glass", 1, 1);
    	if(!p_77659_2_.isRemote)
    	{
    		WindRelations.increasePlayerWindRelations(p_77659_3_, rel);
    		p_77659_3_.addChatMessage(new ChatComponentText(EnumChatFormatting.DARK_AQUA+""+EnumChatFormatting.ITALIC+"You hear a very pale 'Thank you'..."));
    	}
        return p_77659_1_;
    }
	
}
