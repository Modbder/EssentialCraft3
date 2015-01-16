package ec3.api;

import net.minecraft.block.Block;

public class StructureBlock {
	
	public Block blk;
	public int metadata;
	public int x,y,z;
	
	public StructureBlock(Block b, int meta, int i, int j, int k)
	{
		blk = b;
		metadata = meta;
		x = i;
		y = j;
		z = k;
	}

}
