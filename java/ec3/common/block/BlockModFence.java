package ec3.common.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockFence;
import net.minecraft.block.material.Material;

public class BlockModFence extends BlockFence{
	
	public BlockModFence(Material p_i45394_1_, String texture)
	{
		super(texture, p_i45394_1_);
	}

	public BlockModFence(Material p_i45394_1_) {
		super("essentialcraft:fortifiedStone", p_i45394_1_);
	}
	
	public BlockModFence() {
		super("essentialcraft:fortifiedStone", Material.rock);
	}

}
