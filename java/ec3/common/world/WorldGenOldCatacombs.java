package ec3.common.world;

import static net.minecraftforge.common.ChestGenHooks.DUNGEON_CHEST;

import java.util.Random;

import DummyCore.Utils.MathUtils;
import ec3.common.block.BlocksCore;
import ec3.common.item.ItemBaublesWearable;
import ec3.common.item.ItemsCore;
import net.minecraft.block.Block;
import net.minecraft.block.BlockBush;
import net.minecraft.block.BlockLeaves;
import net.minecraft.block.BlockVine;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.util.Direction;
import net.minecraft.util.Vec3;
import net.minecraft.util.WeightedRandomChestContent;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;
import net.minecraftforge.common.ChestGenHooks;
import net.minecraftforge.common.util.ForgeDirection;

public class WorldGenOldCatacombs extends WorldGenerator{

	public static final WeightedRandomChestContent[] generatedItems = new WeightedRandomChestContent[] {
		new WeightedRandomChestContent(ItemsCore.titanite, 0, 8, 32, 20),
		new WeightedRandomChestContent(ItemsCore.twinkling_titanite, 0, 2, 16, 10),
		new WeightedRandomChestContent(ItemsCore.genericItem, 5, 1, 16, 15),
		new WeightedRandomChestContent(ItemsCore.genericItem, 6, 1, 16, 15),
		new WeightedRandomChestContent(ItemsCore.genericItem, 7, 1, 16, 15),
		new WeightedRandomChestContent(ItemsCore.genericItem, 8, 1, 16, 15), 
		new WeightedRandomChestContent(ItemsCore.genericItem, 9, 1, 16, 15),
		new WeightedRandomChestContent(ItemsCore.genericItem, 10, 1, 16, 15),
		new WeightedRandomChestContent(ItemsCore.genericItem, 11, 1, 16, 15),
		new WeightedRandomChestContent(ItemsCore.genericItem, 20, 1, 12, 10),
		new WeightedRandomChestContent(ItemsCore.genericItem, 3, 1, 16, 15),
		new WeightedRandomChestContent(ItemsCore.genericItem, 35, 1, 1, 6),
		new WeightedRandomChestContent(ItemsCore.genericItem, 36, 1, 1, 6),
		new WeightedRandomChestContent(ItemsCore.genericItem, 37, 1, 1, 6),
		new WeightedRandomChestContent(ItemsCore.magicalSlag, 0, 1, 16, 70),
		new WeightedRandomChestContent(ItemsCore.ember, 0, 1, 16, 10),
		new WeightedRandomChestContent(ItemsCore.ember, 1, 1, 16, 10),
		new WeightedRandomChestContent(ItemsCore.ember, 2, 1, 16, 10),
		new WeightedRandomChestContent(ItemsCore.ember, 3, 1, 16, 10),
		new WeightedRandomChestContent(ItemsCore.ember, 4, 1, 16, 10),
		new WeightedRandomChestContent(ItemsCore.ember, 5, 1, 16, 10),
		new WeightedRandomChestContent(ItemsCore.ember, 7, 1, 16, 10),
		new WeightedRandomChestContent(ItemsCore.ember, 6, 1, 16, 10),
		new WeightedRandomChestContent(ItemsCore.bauble, 0, 1, 1, 15)
	};
	
	public int maxSizeTries = 100;
	public int corridorMinLength = 32;
	public int corridorMaxLength = 64;
	
	public float lootRoomChance = 0.003F;
	public float destroyedRoomChance = 0.012F;
	public float greenRoomChance = 0.012F;
	public float weirdRoomChance = 0.003F;
	public float experimentRoomChance = 0.003F;
	
	public Block blockToGenerateOf;
	
	public boolean isFirstTry = true;
	
	public ForgeDirection cameFrom;
	@Override
	public boolean generate(World w, Random r,int x, int y, int z)
	{
		int lengthGenned = r.nextInt(corridorMaxLength-corridorMinLength)+corridorMinLength;		
		ForgeDirection[] genTry = new ForgeDirection[4];
		if(this.cameFrom != ForgeDirection.SOUTH)
			genTry[0] = ForgeDirection.NORTH;
		if(this.cameFrom != ForgeDirection.NORTH)
			genTry[1] = ForgeDirection.SOUTH;
		if(this.cameFrom != ForgeDirection.EAST)
			genTry[2] = ForgeDirection.WEST;
		if(this.cameFrom != ForgeDirection.WEST)
			genTry[3] = ForgeDirection.EAST;
		if(isFirstTry && maxSizeTries > 0)
		{
			for(int i = 0; i < 4; ++i)
			{
				if(genTry[i] != null)
					if(generateCorridor(w,x,y,z,lengthGenned,genTry[i],r.nextFloat()<=greenRoomChance))
					{
						WorldGenOldCatacombs catacombs = new WorldGenOldCatacombs();
						catacombs.maxSizeTries = this.maxSizeTries/4;
						catacombs.cameFrom = genTry[i];
						catacombs.generate(w, r, x+ForgeDirection.values()[i+2].offsetX*10, y, z+ForgeDirection.values()[i+2].offsetZ*10);
					}
			}
		}
		else
		{
			if(w.rand.nextFloat() < 0.03F)
				generateWayUp(w,x,y,z);
			if(w.getBlock(x, y, z) != Blocks.chest)
			{
				w.setBlock(x, y, z, Blocks.chest,0,3);
				w.setBlock(x, y-1, z, BlocksCore.voidStone);
				TileEntityChest chest = (TileEntityChest) w.getTileEntity(x, y, z);
	            if (chest != null)
	            {
	                WeightedRandomChestContent.generateChestContents(w.rand, generatedItems, chest, w.rand.nextInt(12)+6);
	                IInventory inv = chest;
	                for(int i = 0; i < inv.getSizeInventory(); ++i)
	                {
	                	ItemStack stk = inv.getStackInSlot(i);
	                	if(stk != null && stk.getItem() instanceof ItemBaublesWearable)
	                	{
	                		ItemBaublesWearable.initRandomTag(stk, w.rand);
	                	}
	                }
	            }
			}
		}
		return false;
	}
	
	public boolean generateCorridor(World w, int x, int y, int z, int length, ForgeDirection direction, boolean green)
	{
		Block concrete = BlocksCore.concrete;
		int generatedLength = 0;
		Block generateFrom = this.getBlockToGenFrom();
		boolean gen = true;
		while(gen)
		{
			if(w.rand.nextFloat() < this.destroyedRoomChance)
				generateBrokenPath(w,x,y,z);
			Block check_blk = w.getBlock(x, y, z);
			if(direction.offsetX >= 0 && direction.offsetZ >= 0)
			{
				for(int dx = x-direction.offsetZ*2; dx <= x+direction.offsetZ*2; ++dx)
				{
					for(int dy = -2; dy <= 2; ++dy)
					{
						for(int dz = z-direction.offsetX*2; dz <= z+direction.offsetX*2; ++dz)
						{
							Block b = w.getBlock(dx, y+dy, dz);
							if(b != Blocks.chest && b != Blocks.air && !(b instanceof BlockBush) && !(b instanceof BlockVine) && !(b instanceof BlockLeaves) && (b != BlocksCore.concrete) && (b != BlocksCore.root))
							{
								w.setBlock(dx, y+dy, dz, generateFrom, 0, 3);
							}
						}
					}
				}
				for(int dx = x-direction.offsetZ; dx <= x+direction.offsetZ; ++dx)
				{
					for(int dy = -1; dy <= 1; ++dy)
					{
						for(int dz = z-direction.offsetX; dz <= z+direction.offsetX; ++dz)
						{
							Block b = w.getBlock(dx, y+dy, dz);
							if(b != Blocks.chest && b != Blocks.air && !(b instanceof BlockBush) && !(b instanceof BlockVine) && !(b instanceof BlockLeaves) && (b != BlocksCore.concrete) && (b != BlocksCore.root))
								w.setBlock(dx, y+dy, dz, Blocks.air, 0, 3);
						}
					}
				}
			}
			if(direction.offsetX < 0 && direction.offsetZ >= 0)
			{
				for(int dx = x-direction.offsetZ*2; dx <= x+direction.offsetZ*2; ++dx)
				{
					for(int dy = -2; dy <= 2; ++dy)
					{
						for(int dz = z+direction.offsetX*2; dz <= z-direction.offsetX*2; ++dz)
						{
							Block b = w.getBlock(dx, y+dy, dz);
							if(b != Blocks.chest && b != Blocks.air && !(b instanceof BlockBush) && !(b instanceof BlockVine) && !(b instanceof BlockLeaves) && (b != BlocksCore.concrete) && (b != BlocksCore.root))
								w.setBlock(dx, y+dy, dz, generateFrom, 0, 3);
						}
					}
				}
				for(int dx = x-direction.offsetZ; dx <= x+direction.offsetZ; ++dx)
				{
					for(int dy = -1; dy <= 1; ++dy)
					{
						for(int dz = z+direction.offsetX; dz <= z-direction.offsetX; ++dz)
						{
							Block b = w.getBlock(dx, y+dy, dz);
							if(b != Blocks.chest && b != Blocks.air && !(b instanceof BlockBush) && !(b instanceof BlockVine) && !(b instanceof BlockLeaves) && (b != BlocksCore.concrete) && (b != BlocksCore.root))
								w.setBlock(dx, y+dy, dz, Blocks.air, 0, 3);
						}
					}
				}
			}
			if(direction.offsetX >= 0 && direction.offsetZ < 0)
			{
				for(int dx = x+direction.offsetZ*2; dx <= x-direction.offsetZ*2; ++dx)
				{
					for(int dy = -2; dy <= 2; ++dy)
					{
						for(int dz = z-direction.offsetX*2; dz <= z+direction.offsetX*2; ++dz)
						{
							Block b = w.getBlock(dx, y+dy, dz);
							if(b != Blocks.chest && b != Blocks.air && !(b instanceof BlockBush) && !(b instanceof BlockVine) && !(b instanceof BlockLeaves) && (b != BlocksCore.concrete) && (b != BlocksCore.root))
								if(!w.isAirBlock(dx, y+dy, dz))
									w.setBlock(dx, y+dy, dz, generateFrom, 0, 3);
						}
					}
				}
				for(int dx = x+direction.offsetZ; dx <= x-direction.offsetZ; ++dx)
				{
					for(int dy = -1; dy <= 1; ++dy)
					{
						for(int dz = z-direction.offsetX; dz <= z+direction.offsetX; ++dz)
						{
							Block b = w.getBlock(dx, y+dy, dz);
							if(b != Blocks.chest && b != Blocks.air && !(b instanceof BlockBush) && !(b instanceof BlockVine) && !(b instanceof BlockLeaves) && (b != BlocksCore.concrete) && (b != BlocksCore.root))
								w.setBlock(dx, y+dy, dz, Blocks.air, 0, 3);
						}
					}
				}
			}
			if(direction.offsetX < 0 && direction.offsetZ < 0)
			{
				for(int dx = x+direction.offsetZ*2; dx <= x-direction.offsetZ*2; ++dx)
				{
					for(int dy = -2; dy <= 2; ++dy)
					{
						for(int dz = z+direction.offsetX*2; dz <= z-direction.offsetX*2; ++dz)
						{
							Block b = w.getBlock(dx, y+dy, dz);
							if(b != Blocks.chest && b != Blocks.air && !(b instanceof BlockBush) && !(b instanceof BlockVine) && !(b instanceof BlockLeaves) && (b != BlocksCore.concrete) && (b != BlocksCore.root))
								if(!w.isAirBlock(dx, y+dy, dz))
									w.setBlock(dx, y+dy, dz, generateFrom, 0, 3);
						}
					}
				}
				for(int dx = x+direction.offsetZ; dx <= x-direction.offsetZ; ++dx)
				{
					for(int dy = -1; dy <= 1; ++dy)
					{
						for(int dz = z+direction.offsetX; dz <= z-direction.offsetX; ++dz)
						{
							Block b = w.getBlock(dx, y+dy, dz);
							if(b != Blocks.chest && b != Blocks.air && !(b instanceof BlockBush) && !(b instanceof BlockVine) && !(b instanceof BlockLeaves) && (b != BlocksCore.concrete) && (b != BlocksCore.root))
								w.setBlock(dx, y+dy, dz, Blocks.air, 0, 3);
						}
					}
				}
			}
			++generatedLength;
			if(generatedLength >= length)
			{
				gen = false;
				return true;
			}
			x += direction.offsetX;
			z += direction.offsetZ;
			if(w.rand.nextFloat() < this.greenRoomChance)
				generateGrownPath(w,x,y,z);
		}
		return false;
	}
	
	public Block getBlockToGenFrom()
	{
		return this.blockToGenerateOf == null ? BlocksCore.fortifiedStone : blockToGenerateOf;
	}
	
	public void generateBrokenPath(World w, int x, int y, int z)
	{
		for(int i = -2; i <= 2; ++i)
		{
			for(int j = -2; j <= 2; ++j)
			{
				for(int k = -2; k <= 2; ++k)
				{
					int tryInt = j+3;
					if(w.rand.nextInt(tryInt) == 0)
						w.setBlock(x+i, y+j, z+k, BlocksCore.concrete);
				}
			}
		}
	}
	
	public void generateGrownPath(World w, int x, int y, int z)
	{
		Vec3 rootVec = Vec3.createVectorHelper(MathUtils.randomDouble(w.rand)*3, -6, MathUtils.randomDouble(w.rand)*3);
		for(int vi = 0; vi <= 6; ++vi)
		{
			w.setBlock((int) (x+rootVec.xCoord/vi), (int) (y-2-rootVec.yCoord/vi), (int) (z+rootVec.zCoord/vi), BlocksCore.root);
		}
		for(int i = -3; i <= 3; ++i)
		{
			for(int j = -3; j <= 3; ++j)
			{
				for(int k = -3; k <= 3; ++k)
				{
					if(w.rand.nextInt(3) == 0)
					{
						Block b = w.getBlock(x+i, y+j, z+k);
						if(b != Blocks.chest && b != Blocks.air && !(b instanceof BlockBush) && !(b instanceof BlockVine) && !(b instanceof BlockLeaves) && (b != BlocksCore.concrete) && (b != BlocksCore.root))
							w.setBlock(x+i, y+j, z+k, Blocks.leaves);
					}
				}
			}
		}
	}
	
	public boolean generateWayUp(World w, int x, int y, int z)
	{
		int maxY = y;
		while(maxY < 256)
		{
			++maxY;
			boolean isAir = true;
			for(int i = 0; i < 10; ++i)
			{
				if(!w.isAirBlock(x, maxY+i, z))
					isAir = false;
			}
			if(isAir)
				break;
		}
		
		for(int dx = -2; dx <= 2; ++dx)
		{
			for(int dy = y+3; dy < maxY; ++dy)
			{
				for(int dz = -2; dz <= 2; ++dz)
				{
					w.setBlock(x+dx, dy, z+dz, getBlockToGenFrom(), 0, 3);
				}
			}
		}
		
		for(int dx = -1; dx <= 1; ++dx)
		{
			for(int dy = y+3; dy < maxY; ++dy)
			{
				for(int dz = -1; dz <= 1; ++dz)
				{
					w.setBlock(x+dx, dy, z+dz, Blocks.air, 0, 3);
				}
			}
		}
		
		for(int dy = y-3; dy < maxY; ++dy)
		{
			w.setBlock(x-1, dy, z, Blocks.ladder, 5, 3);
		}
		return true;
	}

}
