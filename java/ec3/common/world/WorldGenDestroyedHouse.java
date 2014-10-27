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

public class WorldGenDestroyedHouse extends WorldGenerator{
	
	public int floorsAmount;
	
	public WorldGenDestroyedHouse(int i)
	{
		floorsAmount = i;
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
		int rad = r.nextInt(8)+5;
		int floorSize = 3;
		for(int i = 0; i < floorsAmount+1; ++i)
		{
			generateFloor(w,r,x,y+5*i,z,i,rad);
		}
		return false;
	}
	
	public void generateFloor(World w, Random r,int x, int y, int z,int floorNum, int size)
	{
		for(int dx = -size; dx <= size; ++dx)
		{
			for(int dz = -size; dz <= size; ++dz)
			{
				for(int dy = 0; dy < 5; ++dy)
				{

					w.setBlock(x+dx, y+dy, z+dz, Blocks.air,0,3);
					int tryInt = dy+1;
					if(w.rand.nextInt(tryInt) == 0)
						w.setBlock(x+dx, y+dy, z+dz, BlocksCore.concrete);
					if(dy == 0 || dy == 4)
					{
						w.setBlock(x+dx, y+dy, z+dz, BlocksCore.fortifiedStone,0,3);
					}
					if(dx == -size || dx == size)
					{
						w.setBlock(x+dx, y+dy, z+dz, BlocksCore.fortifiedStone,0,3);
						if(dy > 0 && dy < 4 && dz > -size+1 && dz < size-1)
						{
							w.setBlock(x+dx, y+dy, z+dz, BlocksCore.fortifiedGlass,0,3);
						}
					}
					if(dz == -size || dz == size)
					{
						w.setBlock(x+dx, y+dy, z+dz, BlocksCore.fortifiedStone,0,3);
						if(dy > 0 && dy < 4 && dx > -size+1 && dx < size-1)
						{
							w.setBlock(x+dx, y+dy, z+dz, BlocksCore.fortifiedGlass,0,3);
						}
					}
					if(floorsAmount == 0)floorsAmount = 1;
					if(r.nextInt(floorsAmount*10)<floorNum)
						w.createExplosion(null, x+dx, y+dy, z+dz, 3+(floorNum/3), true);
				}
			}
		}
	}

}
