package ec3.common.world;

import static net.minecraftforge.common.ChestGenHooks.VILLAGE_BLACKSMITH;

import java.util.Random;

import DummyCore.Utils.Coord3D;
import DummyCore.Utils.MathUtils;
import ec3.utils.common.ECUtils;
import net.minecraft.block.Block;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.tileentity.TileEntityMobSpawner;
import net.minecraft.util.WeightedRandomChestContent;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;
import net.minecraftforge.common.ChestGenHooks;
import net.minecraftforge.common.DungeonHooks;

public class WorldGenElderMRUCC extends WorldGenerator{
	public int type;
	
	public WorldGenElderMRUCC(int i)
	{
		type = i;
	}
	@Override
	public boolean generate(World var1, Random var2, int var3, int var4,
			int var5) {
		if(type == 0)
		{
			for(int x = -2; x<= 2; ++x)
			{
				for(int z = -2; z<= 2; ++z)
				{
					var1.setBlock(var3+x, var4, var5+z, Block.getBlockFromName("minecraft:stonebrick"), 0, 3);
					if((x == -2 || x == 2)||(z == -2 || z == 2))
					{
						var1.setBlock(var3+x, var4+1, var5+z, Block.getBlockFromName("minecraft:cobblestone_wall"), 0, 3);
					}
					if(x == 0 && z == 0)
					{
						var1.setBlock(var3+x, var4+1, var5+z, Block.getBlockFromName("minecraft:stonebrick"), 3, 3);
						ECUtils.createMRUCUAt(var1, new Coord3D(var3+x+0.5F,var4+1.5F,var5+z+0.5F), 5000+var2.nextInt(5000), MathUtils.randomFloat(var2)+1.0F, false, false);
					}
				}
			}
		}
		if(type == 1)
		{
			for(int x = -4; x<= 4; ++x)
			{
				for(int z = -4; z<= 4; ++z)
				{
					var1.setBlock(var3+x, var4, var5+z, Block.getBlockFromName("minecraft:stonebrick"), var2.nextInt(3), 3);
					if((x == -4 || x == 4)||(z == -4 || z == 4))
					{
						var1.setBlock(var3+x, var4+1, var5+z, Block.getBlockFromName("minecraft:cobblestone_wall"), 0, 3);
					}
					if(x == 0 && z == 0)
					{
						var1.setBlock(var3+x, var4+1, var5+z, Block.getBlockFromName("minecraft:stonebrick"), 3, 3);
						ECUtils.createMRUCUAt(var1, new Coord3D(var3+x+0.5F,var4+1.5F,var5+z+0.5F), 5000+var2.nextInt(5000), MathUtils.randomFloat(var2)+1.0F, false, false);
					}
					if((x == -3 && z == -3) || (x == 3 && z == -3) || (x == 3 && z == 3) || (x == -3 && z == 3))
					{
						var1.setBlock(var3+x, var4+1, var5+z, Block.getBlockFromName("minecraft:chest"), var2.nextInt(4), 3);
                        TileEntityChest tileentitychest = (TileEntityChest)var1.getTileEntity(var3+x, var4+1, var5+z);

                        if (tileentitychest != null)
                        {
                            WeightedRandomChestContent.generateChestContents(var2, ChestGenHooks.getItems(VILLAGE_BLACKSMITH, var2), tileentitychest, ChestGenHooks.getCount(VILLAGE_BLACKSMITH, var2));
                        }
					}
				}
			}
		}
		if(type == 2)
		{
			for(int x = -4; x<= 4; ++x)
			{
				for(int z = -4; z<= 4; ++z)
				{
					var1.setBlock(var3+x, var4, var5+z, Block.getBlockFromName("minecraft:stonebrick"), var2.nextInt(3), 3);
					var1.setBlock(var3+x, var4+1, var5+z, Block.getBlockFromName("minecraft:stonebrick"), var2.nextInt(3), 3);
					if((x == -4 || x == 4)||(z == -4 || z == 4))
					{
						var1.setBlock(var3+x, var4+2, var5+z, Block.getBlockFromName("minecraft:cobblestone_wall"), 0, 3);
					}
					if(x == 0 && z == 0)
					{
						var1.setBlock(var3+x, var4+2, var5+z, Block.getBlockFromName("minecraft:stonebrick"), 3, 3);
						ECUtils.createMRUCUAt(var1, new Coord3D(var3+x+0.5F,var4+1.5F,var5+z+0.5F), 5000+var2.nextInt(5000), MathUtils.randomFloat(var2)+1.0F, false, false);
					}
					if(x != 3 && x != -3 && z != 3 && z != -3 && ((x == -2 || x == 2)||(z == -2 || z == 2)))
					{
						var1.setBlock(var3+x, var4+2, var5+z, Block.getBlockFromName("minecraft:cobblestone_wall"), 0, 3);
					}
					if(x != 4 && x != -4 && z != 4 && z != -4 && ((x == -3 || x == 3)||(z == -3 || z == 3)))
					{
						var1.setBlock(var3+x, var4+1, var5+z, Block.getBlockFromName("minecraft:lava"), 0, 3);
					}
				}
			}
		}
		if(type == 3)
		{
			for(int x = -4; x<= 4; ++x)
			{
				for(int z = -4; z<= 4; ++z)
				{
					var1.setBlock(var3+x, var4, var5+z, Block.getBlockFromName("minecraft:stonebrick"), var2.nextInt(3), 3);
					var1.setBlock(var3+x, var4+4, var5+z, Block.getBlockFromName("minecraft:stonebrick"), var2.nextInt(3), 3);
					if((x == -4 || x == 4)||(z == -4 || z == 4))
					{
						var1.setBlock(var3+x, var4+1, var5+z, Block.getBlockFromName("minecraft:iron_bars"), 0, 3);
						var1.setBlock(var3+x, var4+2, var5+z, Block.getBlockFromName("minecraft:stonebrick"), var2.nextInt(3), 3);
						var1.setBlock(var3+x, var4+3, var5+z, Block.getBlockFromName("minecraft:iron_bars"), 0, 3);
					}
					if((x == -4 && z == -4) || (x == 4 && z == -4) || (x == 4 && z == 4) || (x == -4 && z == 4))
					{
						var1.setBlock(var3+x, var4+1, var5+z, Block.getBlockFromName("minecraft:stonebrick"), var2.nextInt(3), 3);
						var1.setBlock(var3+x, var4+3, var5+z, Block.getBlockFromName("minecraft:stonebrick"), var2.nextInt(3), 3);
					}
					if(x == 0 && z == 0)
					{
						var1.setBlock(var3+x, var4+1, var5+z, Block.getBlockFromName("minecraft:stonebrick"), 3, 3);
						ECUtils.createMRUCUAt(var1, new Coord3D(var3+x+0.5F,var4+1.5F,var5+z+0.5F), 5000+var2.nextInt(5000), MathUtils.randomFloat(var2)+1.0F, false, false);
					}
					if((x == -3 && z == -3) || (x == 3 && z == -3) || (x == 3 && z == 3) || (x == -3 && z == 3))
					{
						var1.setBlock(var3+x, var4+1, var5+z, Block.getBlockFromName("minecraft:chest"), var2.nextInt(4), 3);
                        TileEntityChest tileentitychest = (TileEntityChest)var1.getTileEntity(var3+x, var4+1, var5+z);

                        if (tileentitychest != null)
                        {
                            WeightedRandomChestContent.generateChestContents(var2, ChestGenHooks.getItems(VILLAGE_BLACKSMITH, var2), tileentitychest, ChestGenHooks.getCount(VILLAGE_BLACKSMITH, var2));
                        }
					}
					if((x == -1 && z == 0) || (x == 1 && z == 0) || (x == 0 && z == 1) || (x == 0 && z == -1))
					{
						var1.setBlock(var3+x, var4+1, var5+z, Block.getBlockFromName("minecraft:mob_spawner"), 0, 3);
                        TileEntityMobSpawner tileentitymobspawner = (TileEntityMobSpawner)var1.getTileEntity(var3+x, var4+1, var5+z);

                        if (tileentitymobspawner != null)
                        {
                        	tileentitymobspawner.func_145881_a().setEntityName(DungeonHooks.getRandomDungeonMob(var2));
                        }
					}
				}
			}
		}
		
		return false;
	}

}
