package ec3.common.tile;

import codechicken.lib.math.MathHelper;
import ec3.common.block.BlocksCore;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class TilePlayerPentacle extends TileEntity
{
	public int tier = -1;
	public int sCheckTick = 0;
	
	public int getEnderstarEnergy()
	{
		int[][] coords = new int[][]{{2,0,1},{2,0,-1},{-2,0,1},{-2,0,-1},{1,0,2},{-1,0,2},{1,0,-2},{-1,0,-2},
		{1,1,1},{-1,1,1},{1,1,-1},{-1,1,-1},
		{2,2,2},{-2,2,2},{2,2,-2},{-2,2,-2},
		{2,3,0},{-2,3,0},{0,3,2},{0,3,-2}
		};
		int tierCheck = 0;
		if(tier == 0)
			tierCheck = 8;
		if(tier == 1)
			tierCheck = 12;
		if(tier == 2)
			tierCheck = 16;
		if(tier >= 3)
			tierCheck = 20;
		double energy = 0;
		double consumeModifier = 0.36D;
		for(int i = 0; i < tierCheck; ++i)
		{
			TileEntity tile = this.worldObj.getTileEntity(xCoord+coords[i][0], yCoord+coords[i][1], zCoord+coords[i][2]);
			if(tile instanceof TileMithrilineCrystal)
			{
				TileMithrilineCrystal tmc = TileMithrilineCrystal.class.cast(tile);
				if(tmc.energy > TileMithrilineCrystal.maxEnergy)
					tmc.energy = TileMithrilineCrystal.maxEnergy;
				if(tier < 3)
					energy += tmc.energy;
				else
					energy += tmc.energy*2;
			}
		}
		
		return MathHelper.floor_double(energy*consumeModifier);
	}
	
	public boolean consumeEnderstarEnergy(int consumed)
	{
		int[][] coords = new int[][]{{2,0,1},{2,0,-1},{-2,0,1},{-2,0,-1},{1,0,2},{-1,0,2},{1,0,-2},{-1,0,-2},
		{1,1,1},{-1,1,1},{1,1,-1},{-1,1,-1},
		{2,2,2},{-2,2,2},{2,2,-2},{-2,2,-2},
		{2,3,0},{-2,3,0},{0,3,2},{0,3,-2}
		};
		int tierCheck = 0;
		if(tier == 0)
			tierCheck = 8;
		if(tier == 1)
			tierCheck = 12;
		if(tier == 2)
			tierCheck = 16;
		if(tier >= 3)
			tierCheck = 20;
		double aconsumed = 0;
		double consumeModifier = 0.36D;
		for(int i = 0; i < tierCheck; ++i)
		{
			TileEntity tile = this.worldObj.getTileEntity(xCoord+coords[i][0], yCoord+coords[i][1], zCoord+coords[i][2]);
			if(tile instanceof TileMithrilineCrystal)
			{
				TileMithrilineCrystal tmc = TileMithrilineCrystal.class.cast(tile);
				if(tmc.energy>TileMithrilineCrystal.maxEnergy)
					tmc.energy = TileMithrilineCrystal.maxEnergy;
				if(tier < 3)
					aconsumed += tmc.energy;
				else
					aconsumed += tmc.energy*2;
				if(aconsumed*consumeModifier >= consumed)
					break;
			}
		}
		if(aconsumed*consumeModifier < consumed)
			return false;
		
		aconsumed = 0;
		for(int i = 0; i < tierCheck; ++i)
		{
			TileEntity tile = this.worldObj.getTileEntity(xCoord+coords[i][0], yCoord+coords[i][1], zCoord+coords[i][2]);
			if(tile instanceof TileMithrilineCrystal)
			{
				TileMithrilineCrystal tmc = TileMithrilineCrystal.class.cast(tile);
				
				if(tier < 3)
					aconsumed += tmc.energy;
				else
					aconsumed += tmc.energy*2;
				tmc.energy = 0;
				if(aconsumed*consumeModifier >= consumed)
				{
					double diff = consumed - aconsumed*consumeModifier;
					if(diff > 0)
						tmc.energy += MathHelper.floor_double(diff);
					return true;
				}
			}
		}
		return false;
	}
	
	@Override
	public void updateEntity()
	{
		if(sCheckTick == 0)
		{
			checkStructureAndTier();
			sCheckTick = 200;
		}else
			--sCheckTick;
		
		float movement = this.worldObj.getWorldTime() % 60;
		if(movement > 30)movement = 30 - movement+30F;
		int[][] coords = new int[][]{{2,0,1},{2,0,-1},{-2,0,1},{-2,0,-1},{1,0,2},{-1,0,2},{1,0,-2},{-1,0,-2},
		{1,1,1},{-1,1,1},{1,1,-1},{-1,1,-1},
		{2,2,2},{-2,2,2},{2,2,-2},{-2,2,-2},
		{2,3,0},{-2,3,0},{0,3,2},{0,3,-2}
		};
		if(tier >= 0)
		{
			for(int i = 0; i < 8; ++i)
			{
				this.worldObj.spawnParticle("reddust", xCoord+coords[i][0]+0.5D, yCoord+coords[i][1]+movement/30, zCoord+coords[i][2]+0.5D, -1, 1, 0);
				this.worldObj.spawnParticle("reddust", xCoord+coords[i][0]+0.5D, yCoord+coords[i][1]+2+movement/30, zCoord+coords[i][2]+0.5D, -1, 1, 0);
			}
		}
		if(tier >= 1)
		{
			for(int i = 8; i < 12; ++i)
			{
				this.worldObj.spawnParticle("reddust", xCoord+coords[i][0]+0.5D, yCoord+coords[i][1]+movement/30, zCoord+coords[i][2]+0.5D, -1, 0, 1);
				this.worldObj.spawnParticle("reddust", xCoord+coords[i][0]+0.5D, yCoord+coords[i][1]+2+movement/30, zCoord+coords[i][2]+0.5D, -1, 0, 1);
			}
		}
		if(tier >= 2)
		{
			for(int i = 12; i < 16; ++i)
			{
				this.worldObj.spawnParticle("reddust", xCoord+coords[i][0]+0.5D, yCoord+coords[i][1]+movement/30, zCoord+coords[i][2]+0.5D, 0.1D, 0, 0.1D);
				this.worldObj.spawnParticle("reddust", xCoord+coords[i][0]+0.5D, yCoord+coords[i][1]+2+movement/30, zCoord+coords[i][2]+0.5D, 0.1D, 0, 0.1D);
			}
		}
		if(tier >= 3)
		{
			for(int i = 16; i < 20; ++i)
			{
				this.worldObj.spawnParticle("reddust", xCoord+coords[i][0]+0.5D, yCoord+coords[i][1]+movement/30, zCoord+coords[i][2]+0.5D, 1D, 0, 0D);
				this.worldObj.spawnParticle("reddust", xCoord+coords[i][0]+0.5D, yCoord+coords[i][1]+2+movement/30, zCoord+coords[i][2]+0.5D, 1D, 0, 0D);
			}
		}
	}
	
	public void checkStructureAndTier()
	{
		World w = this.worldObj;
		int x = this.xCoord;
		int y = this.yCoord - 1;
		int z = this.zCoord;
		tier = -1;
		if(w.getBlock(x, y, z) == BlocksCore.invertedBlock
		&& w.getBlock(x+1, y, z) == BlocksCore.invertedBlock
		&& w.getBlock(x-1, y, z) == BlocksCore.invertedBlock
		&& w.getBlock(x, y, z+1) == BlocksCore.invertedBlock
		&& w.getBlock(x, y, z-1) == BlocksCore.invertedBlock
		&& w.getBlock(x+2, y, z-1) == BlocksCore.invertedBlock
		&& w.getBlock(x+2, y, z+1) == BlocksCore.invertedBlock
		&& w.getBlock(x-2, y, z-1) == BlocksCore.invertedBlock
		&& w.getBlock(x-2, y, z+1) == BlocksCore.invertedBlock
		&& w.getBlock(x-1, y, z+2) == BlocksCore.invertedBlock
		&& w.getBlock(x+1, y, z+2) == BlocksCore.invertedBlock
		&& w.getBlock(x-1, y, z-2) == BlocksCore.invertedBlock
		&& w.getBlock(x+1, y, z-2) == BlocksCore.invertedBlock
		&& w.getBlock(x-1, y+1, z+2) == BlocksCore.mithrilineCrystal
		&& w.getBlock(x+1, y+1, z+2) == BlocksCore.mithrilineCrystal
		&& w.getBlock(x-1, y+1, z-2) == BlocksCore.mithrilineCrystal
		&& w.getBlock(x+1, y+1, z-2) == BlocksCore.mithrilineCrystal
		&& w.getBlock(x+2, y+1, z-1) == BlocksCore.mithrilineCrystal
		&& w.getBlock(x+2, y+1, z+1) == BlocksCore.mithrilineCrystal
		&& w.getBlock(x-2, y+1, z-1) == BlocksCore.mithrilineCrystal
		&& w.getBlock(x-2, y+1, z+1) == BlocksCore.mithrilineCrystal
		&& w.getBlockMetadata(x-1, y+1, z+2) == 0
		&& w.getBlockMetadata(x+1, y+1, z+2) == 0
		&& w.getBlockMetadata(x-1, y+1, z-2) == 0
		&& w.getBlockMetadata(x+1, y+1, z-2) == 0
		&& w.getBlockMetadata(x+2, y+1, z-1) == 0
		&& w.getBlockMetadata(x+2, y+1, z+1) == 0
		&& w.getBlockMetadata(x-2, y+1, z-1) == 0
		&& w.getBlockMetadata(x-2, y+1, z+1) == 0
		)
		{
			tier = 0;
			if(w.getBlock(x+1, y+1, z+1) == BlocksCore.blockPale
			&& w.getBlock(x-1, y+1, z+1) == BlocksCore.blockPale	
			&& w.getBlock(x+1, y+1, z-1) == BlocksCore.blockPale	
			&& w.getBlock(x-1, y+1, z-1) == BlocksCore.blockPale	
			&& w.getBlock(x+1, y+2, z+1) == BlocksCore.mithrilineCrystal
			&& w.getBlock(x-1, y+2, z+1) == BlocksCore.mithrilineCrystal	
			&& w.getBlock(x+1, y+2, z-1) == BlocksCore.mithrilineCrystal	
			&& w.getBlock(x-1, y+2, z-1) == BlocksCore.mithrilineCrystal	
			&& w.getBlockMetadata(x+1, y+2, z+1) == 3
			&& w.getBlockMetadata(x-1, y+2, z+1) == 3	
			&& w.getBlockMetadata(x+1, y+2, z-1) == 3	
			&& w.getBlockMetadata(x-1, y+2, z-1) == 3
			)
			{
				tier = 1;
				if(w.getBlock(x+2, y+1, z+2) == BlocksCore.voidStone
				&& w.getBlock(x-2, y+1, z+2) == BlocksCore.voidStone	
				&& w.getBlock(x+2, y+1, z-2) == BlocksCore.voidStone	
				&& w.getBlock(x-2, y+1, z-2) == BlocksCore.voidStone	
				&& w.getBlock(x+2, y+2, z+2) == BlocksCore.voidStone
				&& w.getBlock(x-2, y+2, z+2) == BlocksCore.voidStone	
				&& w.getBlock(x+2, y+2, z-2) == BlocksCore.voidStone	
				&& w.getBlock(x-2, y+2, z-2) == BlocksCore.voidStone	
				&& w.getBlock(x+2, y+3, z+2) == BlocksCore.mithrilineCrystal
				&& w.getBlock(x-2, y+3, z+2) == BlocksCore.mithrilineCrystal	
				&& w.getBlock(x+2, y+3, z-2) == BlocksCore.mithrilineCrystal	
				&& w.getBlock(x-2, y+3, z-2) == BlocksCore.mithrilineCrystal	
				&& w.getBlockMetadata(x+2, y+3, z+2) == 6
				&& w.getBlockMetadata(x-2, y+3, z+2) == 6	
				&& w.getBlockMetadata(x+2, y+3, z-2) == 6	
				&& w.getBlockMetadata(x-2, y+3, z-2) == 6
				)
				{
					tier = 2;
					if(w.getBlock(x+2, y, z) == BlocksCore.demonicPlating
					&& w.getBlock(x-2, y, z) == BlocksCore.demonicPlating	
					&& w.getBlock(x, y, z+2) == BlocksCore.demonicPlating
					&& w.getBlock(x, y, z-2) == BlocksCore.demonicPlating
					&& w.getBlock(x+2, y+4, z) == BlocksCore.mithrilineCrystal
					&& w.getBlock(x-2, y+4, z) == BlocksCore.mithrilineCrystal	
					&& w.getBlock(x, y+4, z+2) == BlocksCore.mithrilineCrystal
					&& w.getBlock(x, y+4, z-2) == BlocksCore.mithrilineCrystal
					&& w.getBlockMetadata(x+2, y+4, z) == 9
					&& w.getBlockMetadata(x-2, y+4, z) == 9
					&& w.getBlockMetadata(x, y+4, z+2) == 9
					&& w.getBlockMetadata(x, y+4, z-2) == 9
					)
					{
						tier = 3;
					}
				}
			}
		}
	}

}
