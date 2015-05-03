package ec3.common.tile;

import DummyCore.Utils.MathUtils;
import ec3.api.WindImbueRecipe;
import ec3.common.block.BlocksCore;
import ec3.common.item.ItemSoulStone;
import ec3.common.mod.EssentialCraftCore;
import ec3.utils.common.ECUtils;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatStyle;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.MathHelper;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;

public class TileWindRune extends TileEntity
{
	public int tier = -1;
	public int sCheckTick = 0;
	public int energy;
	public int energyCheck = 0;
	
	public int getEnderstarEnergy()
	{
		if(tier == -1)
			return 0;
		
		int[][] coords = new int[][]{{2,0,2},{2,0,-2},{-2,0,2},{-2,0,-2},{1,2,1},{-1,2,1},{1,2,-1},{-1,2,-1}};
		int energy = 0;
		
		for(int i = 0; i < 8; ++i)
		{
			TileEntity tile = this.worldObj.getTileEntity(xCoord+coords[i][0], yCoord+coords[i][1], zCoord+coords[i][2]);
			if(tile instanceof TileMithrilineCrystal)
			{
				energy += MathHelper.floor_float(TileMithrilineCrystal.class.cast(tile).energy);
			}
		}
		
		return energy;
	}
	
	public boolean action(EntityPlayer player)
	{
		if(player.getCurrentEquippedItem() != null)
		{
			ItemStack item = player.getCurrentEquippedItem();
			WindImbueRecipe rec = WindImbueRecipe.findRecipeByComponent(item);
			if(rec != null)
			{
				int energy = this.energy;
				boolean hasEnergy = energy >= rec.enderEnergy;
				boolean creative = player.capabilities.isCreativeMode;
				if(hasEnergy || creative)
				{
					int[][] coords = new int[][]{{2,0,2},{2,0,-2},{-2,0,2},{-2,0,-2},{1,2,1},{-1,2,1},{1,2,-1},{-1,2,-1}};
					int cenergy = 0;
					
					for(int i = 0; i < 8; ++i)
					{
						TileEntity tile = this.worldObj.getTileEntity(xCoord+coords[i][0], yCoord+coords[i][1], zCoord+coords[i][2]);
						if(tile instanceof TileMithrilineCrystal)
						{
							cenergy += MathHelper.floor_float(TileMithrilineCrystal.class.cast(tile).energy);
							if(hasEnergy)
								TileMithrilineCrystal.class.cast(tile).energy = 0;
							
							if(cenergy >= energy)
							{
								int left = cenergy - energy;
								if(hasEnergy)
									TileMithrilineCrystal.class.cast(tile).energy = left;
								
								if(rec.transforming.getItem() instanceof ItemSoulStone && !ECUtils.getData(player).isWindbound())
								{
									ECUtils.getData(player).modifyWindbound(true);
									if(!player.worldObj.isRemote)
									{
										player.addChatMessage(new ChatComponentText(StatCollector.translateToLocal("ec3.txt.windImbue")).setChatStyle(new ChatStyle().setColor(EnumChatFormatting.AQUA)));
										ECUtils.requestSync(player);
									}
								}
								
								player.inventory.decrStackSize(player.inventory.currentItem, 1);
								if(player.inventory.getStackInSlot(player.inventory.currentItem) == null)
									player.inventory.setInventorySlotContents(player.inventory.currentItem, rec.result.copy());
								else
									if(!player.inventory.addItemStackToInventory(rec.result.copy()))
										player.dropPlayerItemWithRandomChoice(rec.result.copy(), true);
								
								for(int j = 0; j < 300; ++j)
								{
									EssentialCraftCore.proxy.SmokeFX(
											xCoord+0.5D+MathUtils.randomDouble(this.worldObj.rand)*1.6D,
											yCoord,
											zCoord+0.5D+MathUtils.randomDouble(this.worldObj.rand)*1.6D,
											
											0,
											this.worldObj.rand.nextDouble()*0.3D,
											0,
											1,
											0.7D,
											1.0D,
											0.85D
									);
								}
								
								return true;
							}
						}
					}
				}
			}
		}
		return false;
	}
	
	@Override
	public void updateEntity()
	{
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
		int[][] coords = new int[][]{{2,0,2},{2,0,-2},{-2,0,2},{-2,0,-2},{1,2,1},{-1,2,1},{1,2,-1},{-1,2,-1}
		};
		if(tier >= 0)
		{
				EssentialCraftCore.proxy.SmokeFX(
						xCoord+0.5D+MathUtils.randomDouble(this.worldObj.rand)*1.6D,
						yCoord,
						zCoord+0.5D+MathUtils.randomDouble(this.worldObj.rand)*1.6D,
						
						0,
						this.worldObj.rand.nextDouble()*0.3D,
						0,
						1,
						0.2D,
						1.0D,
						0.45D
				);
			
			for(int i = 0; i < 8; ++i)
			{
				this.worldObj.spawnParticle("reddust", xCoord+coords[i][0]+0.5D, yCoord+coords[i][1]+movement/30, zCoord+coords[i][2]+0.5D, -1, 1, 0);
				this.worldObj.spawnParticle("reddust", xCoord+coords[i][0]+0.5D, yCoord+coords[i][1]+2+movement/30, zCoord+coords[i][2]+0.5D, -1, 1, 0);
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
		&& w.getBlock(x+1, y+1, z-1) == BlocksCore.invertedBlock
		&& w.getBlock(x+1, y+1, z+1) == BlocksCore.invertedBlock
		&& w.getBlock(x-1, y+1, z-1) == BlocksCore.invertedBlock
		&& w.getBlock(x-1, y+1, z+1) == BlocksCore.invertedBlock
		&& w.getBlock(x+1, y+2, z-1) == BlocksCore.invertedBlock
		&& w.getBlock(x+1, y+2, z+1) == BlocksCore.invertedBlock
		&& w.getBlock(x-1, y+2, z-1) == BlocksCore.invertedBlock
		&& w.getBlock(x-1, y+2, z+1) == BlocksCore.invertedBlock
		&& w.getBlock(x-1, y+3, z+1) == BlocksCore.mithrilineCrystal
		&& w.getBlock(x+1, y+3, z+1) == BlocksCore.mithrilineCrystal
		&& w.getBlock(x-1, y+3, z-1) == BlocksCore.mithrilineCrystal
		&& w.getBlock(x+1, y+3, z-1) == BlocksCore.mithrilineCrystal
		&& w.getBlock(x-2, y+1, z+2) == BlocksCore.mithrilineCrystal
		&& w.getBlock(x+2, y+1, z+2) == BlocksCore.mithrilineCrystal
		&& w.getBlock(x-2, y+1, z-2) == BlocksCore.mithrilineCrystal
		&& w.getBlock(x+2, y+1, z-2) == BlocksCore.mithrilineCrystal
		
		&& w.getBlock(x+1, y, z-1) == BlocksCore.platingPale
		&& w.getBlock(x+2, y, z-1) == BlocksCore.platingPale
		&& w.getBlock(x-2, y, z-1) == BlocksCore.platingPale
		&& w.getBlock(x+2, y, z+1) == BlocksCore.platingPale
		&& w.getBlock(x-2, y, z+1) == BlocksCore.platingPale
		&& w.getBlock(x+1, y, z-2) == BlocksCore.platingPale
		&& w.getBlock(x-1, y, z-2) == BlocksCore.platingPale
		&& w.getBlock(x+1, y, z+2) == BlocksCore.platingPale
		&& w.getBlock(x-1, y, z+2) == BlocksCore.platingPale	
		
		&& w.getBlock(x-2, y, z) == BlocksCore.magicPlating
		&& w.getBlock(x-2, y+1, z) == BlocksCore.magicPlating
		&& w.getBlock(x+2, y, z) == BlocksCore.magicPlating
		&& w.getBlock(x+2, y+1, z) == BlocksCore.magicPlating
		&& w.getBlock(x, y, z-2) == BlocksCore.magicPlating
		&& w.getBlock(x, y+1, z-2) == BlocksCore.magicPlating
		&& w.getBlock(x, y, z+2) == BlocksCore.magicPlating
		&& w.getBlock(x, y+1, z+2) == BlocksCore.magicPlating
		
		&& w.getBlock(x+1, y, z+1) == BlocksCore.platingPale
		&& w.getBlock(x-1, y, z-1) == BlocksCore.platingPale
		&& w.getBlock(x-1, y, z+1) == BlocksCore.platingPale
		
		
		&& w.getBlockMetadata(x-2, y+1, z+2) == 0
		&& w.getBlockMetadata(x+2, y+1, z+2) == 0
		&& w.getBlockMetadata(x-2, y+1, z-2) == 0
		&& w.getBlockMetadata(x+2, y+1, z-2) == 0
		
		&& w.getBlockMetadata(x+1, y+3, z-1) == 0
		&& w.getBlockMetadata(x+1, y+3, z+1) == 0
		&& w.getBlockMetadata(x-1, y+3, z-1) == 0
		&& w.getBlockMetadata(x-1, y+3, z+1) == 0
		)
		{
			tier = 0;
		}
	}

}
