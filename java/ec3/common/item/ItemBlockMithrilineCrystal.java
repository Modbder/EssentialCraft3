package ec3.common.item;

import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

public class ItemBlockMithrilineCrystal extends ItemBlock{

	public ItemBlockMithrilineCrystal(Block p_i45328_1_) {
		super(p_i45328_1_);
		this.setHasSubtypes(true);
		this.setMaxDamage(0);
	}

    public int getMetadata(int par1)
    {
        return par1;
    }
    
    public String getUnlocalizedName(ItemStack p_77667_1_)
    {
    	int meta = p_77667_1_.getItemDamage()/3;
    	String added = "mithriline";
    	if(meta == 1)
    		added = "pale";
    	if(meta == 2)
    		added = "void";
    	if(meta == 3)
    		added = "demonic";
    	if(meta == 4)
    		added = "shade";
    	return super.getUnlocalizedName(p_77667_1_)+"."+added;
    }
    
}
