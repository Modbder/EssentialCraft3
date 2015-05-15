package ec3.common.item;

import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;

public class ItemBlockGeneric extends ItemBlock{

	public ItemBlockGeneric(Block p_i45328_1_) {
		super(p_i45328_1_);
		this.setHasSubtypes(true);
		this.setMaxDamage(0);
	}

    public int getMetadata(int par1)
    {
        return par1;
    }
    
}
