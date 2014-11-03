package ec3.common.world;

import static net.minecraftforge.common.ChestGenHooks.DUNGEON_CHEST;

import java.util.Random;

import DummyCore.Utils.MathUtils;
import ec3.common.block.BlocksCore;
import ec3.common.item.ItemsCore;
import net.minecraft.block.Block;
import net.minecraft.block.BlockBush;
import net.minecraft.block.BlockLeaves;
import net.minecraft.block.BlockVine;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.util.Direction;
import net.minecraft.util.Vec3;
import net.minecraft.util.WeightedRandomChestContent;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;
import net.minecraftforge.common.ChestGenHooks;
import net.minecraftforge.common.util.ForgeDirection;

public class WorldGenMRUSpreader extends WorldGenerator{
	
	
	public WorldGenMRUSpreader()
	{
	}
	public static final WeightedRandomChestContent[] generatedItems = new WeightedRandomChestContent[] {
		new WeightedRandomChestContent(ItemsCore.titanite, 0, 8, 64, 20),
		new WeightedRandomChestContent(ItemsCore.twinkling_titanite, 0, 2, 16, 10),
		new WeightedRandomChestContent(ItemsCore.genericItem, 5, 1, 64, 15),
		new WeightedRandomChestContent(ItemsCore.genericItem, 6, 1, 64, 15),
		new WeightedRandomChestContent(ItemsCore.genericItem, 7, 1, 64, 15),
		new WeightedRandomChestContent(ItemsCore.genericItem, 8, 1, 64, 15), 
		new WeightedRandomChestContent(ItemsCore.genericItem, 9, 1, 64, 15),
		new WeightedRandomChestContent(ItemsCore.genericItem, 10, 1, 64, 15),
		new WeightedRandomChestContent(ItemsCore.genericItem, 11, 1, 64, 15),
		new WeightedRandomChestContent(ItemsCore.genericItem, 20, 1, 12, 10),
		new WeightedRandomChestContent(ItemsCore.genericItem, 3, 1, 64, 15),
		new WeightedRandomChestContent(ItemsCore.genericItem, 35, 1, 1, 6),
		new WeightedRandomChestContent(ItemsCore.genericItem, 36, 1, 1, 6),
		new WeightedRandomChestContent(ItemsCore.genericItem, 37, 1, 1, 6)
	};

	@Override
	public boolean generate(World w, Random r,int x, int y, int z)
	{
		for(int dx = 0; dx < 3; ++dx)
		{
			for(int dz = 0; dz < 3; ++dz)
			{
				for(int dy = 0; dy < 7; ++dy)
				{
					if(dy == 0)
					{
						if(dx == 1 && dz == 1)
						{
							w.setBlock(x+dx, y, z+dz, BlocksCore.magicPlating,0,3);
							if(w.isAirBlock(x+dx, y-1, z+dz))
								w.setBlock(x+dx, y-1, z+dz, BlocksCore.levitator,0,3);
						}else
						{
							w.setBlock(x+dx, y, z+dz, BlocksCore.fortifiedStone,0,3);
						}
					}else
					{
						if((dx == 0 || dx == 2) && (dz == 0 || dz == 2) && dy < 5)
						{
							w.setBlock(x+dx, y+dy, z+dz, BlocksCore.fence[2],0,3);
						}else
						{
							if((dx == 1) && (dz == 1) && dy < 6)
							{
								w.setBlock(x+dx, y+dy, z+dz, BlocksCore.fence[1],0,3);
							}else
							{
								if((dx == 1) && (dz == 1) && dy == 6)
								{
									w.setBlock(x+dx, y+dy, z+dz, BlocksCore.spreader,0,3);
								}
							}
						}
					}
				}
			}
		}
		return false;
	}

}
