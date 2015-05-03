package ec3.common.tile;

import java.util.List;

import ec3.common.block.BlocksCore;
import ec3.common.entity.EntityDemon;
import ec3.common.mod.EssentialCraftCore;
import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class TileDemonicPentacle extends TileEntity
{
	public int tier = -1;
	public int sCheckTick = 0;
	public int energy;
	public int energyCheck = 0;
	
	
	public boolean consumeEnderstarEnergy(int consumed)
	{
		int[][] coords = new int[][]{{2,1,2},{2,1,-2},{-2,1,2},{-2,1,-2},
				{3,0,2},{3,0,-2},{-3,0,2},{-3,0,-2},{2,0,3},{2,0,-3},{-2,0,3},{-2,0,-3},
				{3,1,0},{-3,1,0},{0,1,3},{0,1,-3}
		};
		int tierCheck = coords.length;

		double aconsumed = 0;
		double consumeModifier = 1D;
		for(int i = 0; i < tierCheck; ++i)
		{
			TileEntity tile = this.worldObj.getTileEntity(xCoord+coords[i][0], yCoord+coords[i][1], zCoord+coords[i][2]);
			if(tile instanceof TileMithrilineCrystal)
			{
				TileMithrilineCrystal tmc = TileMithrilineCrystal.class.cast(tile);
				if(tmc.energy>TileMithrilineCrystal.maxEnergy)
					tmc.energy = TileMithrilineCrystal.maxEnergy;
					aconsumed += tmc.energy;
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
				
				aconsumed += tmc.energy;
				tmc.energy = 0;
				if(aconsumed*consumeModifier >= consumed)
				{
					double diff = aconsumed*consumeModifier - consumed;
					if(diff > 0)
						tmc.energy += MathHelper.floor_double(diff);
					return true;
				}
			}
		}
		return false;
	}
	
	public int getEnderstarEnergy()
	{
		if(tier == -1)
			return 0;
		
		int[][] coords = new int[][]{{2,1,2},{2,1,-2},{-2,1,2},{-2,1,-2},
				{3,0,2},{3,0,-2},{-3,0,2},{-3,0,-2},{2,0,3},{2,0,-3},{-2,0,3},{-2,0,-3},
				{3,1,0},{-3,1,0},{0,1,3},{0,1,-3}
		};
		
		int energy = 0;
		
		for(int i = 0; i < coords.length; ++i)
		{
			TileEntity tile = this.worldObj.getTileEntity(xCoord+coords[i][0], yCoord+coords[i][1], zCoord+coords[i][2]);
			if(tile instanceof TileMithrilineCrystal)
			{
				energy += MathHelper.floor_float(TileMithrilineCrystal.class.cast(tile).energy);
			}
		}
		
		return energy;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public void updateEntity()
	{
		List<EntityDemon> demons = this.worldObj.getEntitiesWithinAABB(EntityDemon.class, AxisAlignedBB.getBoundingBox(xCoord, yCoord, zCoord, xCoord+1, yCoord+1, zCoord+1));
		if(!demons.isEmpty())
		{
			for(int i = 0; i < 4; ++i)
			{
				double time = (this.worldObj.getWorldTime()+9*i)%36 * 10;
				double timeSin = Math.sin(Math.toRadians(time)) * 1.1D;
				double timeCos = Math.cos(Math.toRadians(time)) * 1.1D;
				double x = xCoord + 0.5D + timeSin;
				double y = yCoord;
				double z = zCoord + 0.5D + timeCos;
				EssentialCraftCore.proxy.SmokeFX(x,y,z,0,0.1D,0,1,1,0,0);
			}
		}
		if(energyCheck == 0)
		{
			energyCheck = 100;
			energy = this.getEnderstarEnergy();
		}else
			--energyCheck;
		
		if(sCheckTick == 0)
		{
			checkStructureAndTier();
			sCheckTick = 200;
		}else
			--sCheckTick;
		
		float movement = this.worldObj.getWorldTime() % 60;
		if(movement > 30)movement = 30 - movement+30F;
		int[][] coords = new int[][]{{2,1,2},{2,1,-2},{-2,1,2},{-2,1,-2},
				{3,0,2},{3,0,-2},{-3,0,2},{-3,0,-2},{2,0,3},{2,0,-3},{-2,0,3},{-2,0,-3},
				{3,1,0},{-3,1,0},{0,1,3},{0,1,-3}
		};
		if(tier >= 0)
		{
			for(int i = 0; i < 4; ++i)
			{
				this.worldObj.spawnParticle("reddust", xCoord+coords[i][0]+0.5D, yCoord+coords[i][1]+movement/30, zCoord+coords[i][2]+0.5D, -1, 1, 0);
				this.worldObj.spawnParticle("reddust", xCoord+coords[i][0]+0.5D, yCoord+coords[i][1]+2+movement/30, zCoord+coords[i][2]+0.5D, -1, 1, 0);
			}
			for(int i = 4; i < 12; ++i)
			{
				this.worldObj.spawnParticle("reddust", xCoord+coords[i][0]+0.5D, yCoord+coords[i][1]+movement/30, zCoord+coords[i][2]+0.5D, -1, 0, 1);
				this.worldObj.spawnParticle("reddust", xCoord+coords[i][0]+0.5D, yCoord+coords[i][1]+2+movement/30, zCoord+coords[i][2]+0.5D, -1, 0, 1);
			}
			for(int i = 12; i < 16; ++i)
			{
				this.worldObj.spawnParticle("reddust", xCoord+coords[i][0]+0.5D, yCoord+coords[i][1]+movement/30, zCoord+coords[i][2]+0.5D, 0.1D, 0, 0.1D);
				this.worldObj.spawnParticle("reddust", xCoord+coords[i][0]+0.5D, yCoord+coords[i][1]+2+movement/30, zCoord+coords[i][2]+0.5D, 0.1D, 0, 0.1D);
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
		
		if(w.getBlock(x, y, z) == BlocksCore.voidStone &&
		w.getBlock(x+2, y, z) == BlocksCore.voidStone &&	
		w.getBlock(x-2, y, z) == BlocksCore.voidStone &&
		w.getBlock(x, y, z-2) == BlocksCore.voidStone &&	
		w.getBlock(x, y, z+2) == BlocksCore.voidStone &&	
		w.getBlock(x+3, y, z+1) == BlocksCore.voidStone &&	
		w.getBlock(x+3, y, z-1) == BlocksCore.voidStone &&	
		w.getBlock(x-3, y, z+1) == BlocksCore.voidStone &&	
		w.getBlock(x-3, y, z-1) == BlocksCore.voidStone &&	
		w.getBlock(x+1, y, z+3) == BlocksCore.voidStone &&	
		w.getBlock(x-1, y, z+3) == BlocksCore.voidStone &&	
		w.getBlock(x+1, y, z-3) == BlocksCore.voidStone &&	
		w.getBlock(x-1, y, z-3) == BlocksCore.voidStone &&	
		w.getBlock(x+3, y+1, z) == Blocks.glowstone &&	
		w.getBlock(x-3, y+1, z) == Blocks.glowstone &&	
		w.getBlock(x, y+1, z+3) == Blocks.glowstone &&	
		w.getBlock(x, y+1, z-3) == Blocks.glowstone &&	
		w.getBlock(x+2, y+1, z+2) == BlocksCore.invertedBlock &&	
		w.getBlock(x-2, y+1, z+2) == BlocksCore.invertedBlock &&	
		w.getBlock(x+2, y+1, z-2) == BlocksCore.invertedBlock &&	
		w.getBlock(x-2, y+1, z-2) == BlocksCore.invertedBlock &&	
		w.getBlock(x+1, y, z) == BlocksCore.platingPale &&	
		w.getBlock(x-1, y, z) == BlocksCore.platingPale &&	
		w.getBlock(x, y, z+1) == BlocksCore.platingPale &&	
		w.getBlock(x, y, z-1) == BlocksCore.platingPale &&	
		w.getBlock(x+2, y, z+1) == BlocksCore.platingPale &&	
		w.getBlock(x+2, y, z-1) == BlocksCore.platingPale &&	
		w.getBlock(x-2, y, z+1) == BlocksCore.platingPale &&	
		w.getBlock(x-2, y, z-1) == BlocksCore.platingPale &&	
		w.getBlock(x+1, y, z+2) == BlocksCore.platingPale &&	
		w.getBlock(x+1, y, z-2) == BlocksCore.platingPale &&	
		w.getBlock(x-1, y, z+2) == BlocksCore.platingPale &&	
		w.getBlock(x-1, y, z-2) == BlocksCore.platingPale &&	
		w.getBlock(x+3, y, z+2) == BlocksCore.platingPale &&	
		w.getBlock(x+3, y, z-2) == BlocksCore.platingPale &&	
		w.getBlock(x-3, y, z+2) == BlocksCore.platingPale &&	
		w.getBlock(x-3, y, z-2) == BlocksCore.platingPale &&	
		w.getBlock(x+2, y, z+3) == BlocksCore.platingPale &&	
		w.getBlock(x+2, y, z-3) == BlocksCore.platingPale &&	
		w.getBlock(x-2, y, z+3) == BlocksCore.platingPale &&	
		w.getBlock(x-2, y, z-3) == BlocksCore.platingPale &&
		
		w.getBlock(x+3, y+1, z+2) == BlocksCore.mithrilineCrystal &&	
		w.getBlock(x+3, y+1, z-2) == BlocksCore.mithrilineCrystal &&	
		w.getBlock(x-3, y+1, z+2) == BlocksCore.mithrilineCrystal &&	
		w.getBlock(x-3, y+1, z-2) == BlocksCore.mithrilineCrystal &&	
		w.getBlock(x+2, y+1, z+3) == BlocksCore.mithrilineCrystal &&	
		w.getBlock(x+2, y+1, z-3) == BlocksCore.mithrilineCrystal &&	
		w.getBlock(x-2, y+1, z+3) == BlocksCore.mithrilineCrystal &&	
		w.getBlock(x-2, y+1, z-3) == BlocksCore.mithrilineCrystal &&
		
		w.getBlockMetadata(x+3, y+1, z+2) == 3 &&	
		w.getBlockMetadata(x+3, y+1, z-2) == 3 &&	
		w.getBlockMetadata(x-3, y+1, z+2) == 3 &&	
		w.getBlockMetadata(x-3, y+1, z-2) == 3 &&	
		w.getBlockMetadata(x+2, y+1, z+3) == 3 &&	
		w.getBlockMetadata(x+2, y+1, z-3) == 3 &&	
		w.getBlockMetadata(x-2, y+1, z+3) == 3 &&	
		w.getBlockMetadata(x-2, y+1, z-3) == 3 &&
		
		w.getBlock(x+2, y+2, z+2) == BlocksCore.mithrilineCrystal &&	
		w.getBlock(x-2, y+2, z+2) == BlocksCore.mithrilineCrystal &&	
		w.getBlock(x+2, y+2, z-2) == BlocksCore.mithrilineCrystal &&	
		w.getBlock(x-2, y+2, z-2) == BlocksCore.mithrilineCrystal &&	
		w.getBlockMetadata(x+2, y+2, z+2) == 0 &&	
		w.getBlockMetadata(x-2, y+2, z+2) == 0 &&	
		w.getBlockMetadata(x+2, y+2, z-2) == 0 &&	
		w.getBlockMetadata(x-2, y+2, z-2) == 0 &&	
		
		w.getBlock(x+3, y+2, z) == BlocksCore.mithrilineCrystal &&	
		w.getBlock(x-3, y+2, z) == BlocksCore.mithrilineCrystal &&	
		w.getBlock(x, y+2, z+3) == BlocksCore.mithrilineCrystal &&	
		w.getBlock(x, y+2, z-3) == BlocksCore.mithrilineCrystal &&	
		w.getBlockMetadata(x+3, y+2, z) == 6 &&	
		w.getBlockMetadata(x-3, y+2, z) == 6 &&	
		w.getBlockMetadata(x, y+2, z+3) == 6 &&	
		w.getBlockMetadata(x, y+2, z-3) == 6	
		
		
		)
		{
			tier = 0;
		}
	}

}
