package ec3.common.block;

import java.util.Random;

import ec3.common.item.ItemsCore;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.init.Items;
import net.minecraft.item.Item;

public class BlockMagicalFruit extends Block{

	public BlockMagicalFruit() {
		super(Material.cactus);
		this.setStepSound(soundTypeGrass);
	}

	public Item getItemDropped(int p_149650_1_, Random p_149650_2_, int p_149650_3_)
    {
        return ItemsCore.fruit;
    }

    /**
     * Returns the quantity of items to drop on block destruction.
     */
    public int quantityDropped(Random p_149745_1_)
    {
        return 3 + p_149745_1_.nextInt(5);
    }

    /**
     * Returns the usual quantity dropped by the block plus a bonus of 1 to 'i' (inclusive).
     */
    public int quantityDroppedWithBonus(int p_149679_1_, Random p_149679_2_)
    {
        int j = this.quantityDropped(p_149679_2_) + p_149679_2_.nextInt(1 + p_149679_1_);

        if (j > 9)
        {
            j = 9;
        }

        return j;
    }
}
