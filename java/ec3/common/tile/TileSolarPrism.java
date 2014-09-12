package ec3.common.tile;

import java.util.UUID;

import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntity;
import ec3.api.EnumStructureType;
import ec3.api.IStructurePiece;
import ec3.api.ITERequiresMRU;
import ec3.common.entity.EntitySolarBeam;

public class TileSolarPrism extends TileEntity{
	
	@Override
	public void updateEntity()
	{
		super.updateEntity();
		if(!this.worldObj.isRemote)
		{
			if(this.worldObj.rand.nextFloat() <= 0.025F && this.worldObj.canBlockSeeTheSky(xCoord, yCoord, zCoord) && (worldObj.getWorldTime() % 24000 >= 5000 && worldObj.getWorldTime() % 24000 <= 7000) && !this.worldObj.isRaining())
			{
				int y = this.yCoord-1;
				while(y > 0 && this.worldObj.isAirBlock(xCoord, y, zCoord))
				{
					--y;
					if(!this.worldObj.isAirBlock(xCoord, y, zCoord))
					{
						EntitySolarBeam beam = new EntitySolarBeam(this.worldObj,xCoord,y,zCoord);
						this.worldObj.spawnEntityInWorld(beam);
					}
				}
			}
			if(this.worldObj.isAirBlock(xCoord+2, yCoord, zCoord) || this.worldObj.isAirBlock(xCoord-2, yCoord, zCoord) || this.worldObj.isAirBlock(xCoord, yCoord, zCoord-2) || this.worldObj.isAirBlock(xCoord, yCoord, zCoord+2))
			{
				this.worldObj.getBlock(xCoord, yCoord, zCoord).dropBlockAsItem(worldObj, xCoord, yCoord, zCoord, this.getBlockMetadata(), 0);
				this.worldObj.setBlock(xCoord, yCoord, zCoord, Blocks.air, 0, 3);
			}
			if(!this.worldObj.isAirBlock(xCoord+1, yCoord, zCoord) || !this.worldObj.isAirBlock(xCoord-1, yCoord, zCoord) || !this.worldObj.isAirBlock(xCoord, yCoord, zCoord-1) || !this.worldObj.isAirBlock(xCoord, yCoord, zCoord+1))
			{
				this.worldObj.getBlock(xCoord, yCoord, zCoord).dropBlockAsItem(worldObj, xCoord, yCoord, zCoord, this.getBlockMetadata(), 0);
				this.worldObj.setBlock(xCoord, yCoord, zCoord, Blocks.air, 0, 3);
			}
			if(!this.worldObj.isAirBlock(xCoord+1, yCoord, zCoord+1) || !this.worldObj.isAirBlock(xCoord+1, yCoord, zCoord-1) || !this.worldObj.isAirBlock(xCoord-1, yCoord, zCoord-1) || !this.worldObj.isAirBlock(xCoord-1, yCoord, zCoord+1))
			{
				this.worldObj.getBlock(xCoord, yCoord, zCoord).dropBlockAsItem(worldObj, xCoord, yCoord, zCoord, this.getBlockMetadata(), 0);
				this.worldObj.setBlock(xCoord, yCoord, zCoord, Blocks.air, 0, 3);
			}
		}
	}

}
