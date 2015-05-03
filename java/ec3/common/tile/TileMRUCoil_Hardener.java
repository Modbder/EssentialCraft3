package ec3.common.tile;

import DummyCore.Utils.Coord3D;
import DummyCore.Utils.Lightning;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;
import ec3.common.block.BlocksCore;

public class TileMRUCoil_Hardener extends TileEntity{
	
	@SideOnly(Side.CLIENT)
	public Lightning localLightning;
	
	@Override
	public void updateEntity()
	{
		if(this.worldObj.isRemote)
		{
			if(localLightning == null && this.worldObj.rand.nextFloat() <= 0.01F)
			{
				int dy = this.yCoord;
				while(this.worldObj.getBlock(xCoord, dy, zCoord) == BlocksCore.mruCoilHardener)
				{
					++dy;
				}
				if(this.worldObj.getBlock(xCoord, dy, zCoord) == BlocksCore.mruCoil)
				{
					TileMRUCoil tile = (TileMRUCoil) this.worldObj.getTileEntity(xCoord, dy, zCoord);
					if(tile.isStructureCorrect())
					{
						ForgeDirection fDir = ForgeDirection.VALID_DIRECTIONS[2+this.worldObj.rand.nextInt(4)];
						localLightning = new Lightning(this.worldObj.rand, new Coord3D(0.5F+(float)fDir.offsetX/2.3F,0,0.5F+(float)fDir.offsetZ/2.3F), new Coord3D(0.5F+(float)fDir.offsetX/2.3F,1,0.5F+(float)fDir.offsetZ/2.3F), 0.03F, 1.0F, 0.1F, 0.8F);
						this.worldObj.playSound(xCoord+0.5F+(float)fDir.offsetX/2.3F,yCoord+0.5F,zCoord+0.5F+(float)fDir.offsetZ/2.3F, "essentialcraft:sound.gen_electricity", 0.1F, 1F, false);
					}
				}
			}else
			{
				if(localLightning != null && localLightning.renderTicksExisted >= 40)
					localLightning = null;
			}
		}
		super.updateEntity();
	}

}
