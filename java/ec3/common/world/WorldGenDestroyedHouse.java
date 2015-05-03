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
	
	public int floorsAmount, rad;
	
	public WorldGenDestroyedHouse(int i)
	{
		floorsAmount = i;
	}
	
	public WorldGenDestroyedHouse(int i, int j)
	{
		floorsAmount = i;
		rad = j;
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
	
	public int getGroundToGenerate(World w, int x, int y, int z)
	{
		while(y > 5)
		{
			if(!w.isAirBlock(x, y, z) && w.getBlock(x, y, z) != Blocks.water)
			{
				if(w.getBlock(x, y, z) != BlocksCore.concrete && w.getBlock(x, y, z) != BlocksCore.fortifiedStone)
				{
					break;
				}else
				{
					return -1;
				}
			}
			--y;
		}
		if(y == 5)
			return -1;
		return y;
	}

	@Override
	public boolean generate(World w, Random r,int x, int y, int z)
	{
		int genY = getGroundToGenerate(w,x,y,z);
		if(genY != -1)
		{
			y = genY;
			if(rad == 0)
				rad = r.nextInt(6)+3;
			int floorSize = 3;
			for(int i = 0; i < floorsAmount+1; ++i)
			{
				generateFloor(w,r,x,y+5*i,z,i,rad);
			}
			return true;
		}
		return false;
	}
	
	public void generateFloor(World w, Random r,int x, int y, int z,int floorNum, int size)
	{
		for(int dx = -size; dx <= size; ++dx)
		{
			for(int dz = -size; dz <= size; ++dz)
			{
				if(floorNum == 0)
				{
					if(((dx == -size || dx == size) && (dz == -size || dz == size)) || (dx == 0 && dz == 0))
					{
						w.setBlock(x+dx, y-1, z+dz, BlocksCore.levitator,0,3);
					}
				}
				for(int dy = 0; dy < 5; ++dy)
				{
					if(w.getBlock(x+dx, y+dy, z+dz) != Blocks.water)
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
					{
		                ECExplosion explosion = new ECExplosion(w,null,x+dx, y+dy, z+dz, 3+(floorNum/3));
		                explosion.doExplosionA();
		                explosion.doExplosionB(true);
					}
				}
			}
		}
	}

}
