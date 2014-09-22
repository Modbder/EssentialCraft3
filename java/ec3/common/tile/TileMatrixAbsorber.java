package ec3.common.tile;

import java.util.List;

import DummyCore.Utils.Coord3D;
import DummyCore.Utils.DataStorage;
import DummyCore.Utils.DummyData;
import DummyCore.Utils.DummyDataUtils;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import ec3.api.ApiCore;
import ec3.api.IColdBlock;
import ec3.api.IHotBlock;
import ec3.common.entity.EntitySolarBeam;
import ec3.common.item.ItemSoulStone;
import ec3.common.item.ItemsCore;

public class TileMatrixAbsorber extends TileMRUGeneric{
	
	public TileMatrixAbsorber()
	{
		this.balance = 1;
		this.maxMRU = (int) ApiCore.GENERATOR_MAX_MRU_GENERIC/10;
		this.setSlotsNum(1);
	}
	
	public boolean canGenerateMRU()
	{
		return false;
	}
	
	@Override
	public void updateEntity()
	{
		this.balance = 1;
		super.updateEntity();
		ItemStack stk = this.getStackInSlot(0);
		if(stk != null && stk.getItem() instanceof ItemSoulStone)
		{
	    	if(stk.getTagCompound() != null)
	    	{
	    		String username = stk.getTagCompound().getString("playerName");
	    		String currentEnergy = DummyDataUtils.getDataForPlayer(username, "essentialcraft", "ubmruEnergy");
				if(currentEnergy != null && !currentEnergy.isEmpty() && !currentEnergy.equals("no data") && !currentEnergy.equals("empty string") && !currentEnergy.equals("empty"))
				{
					if(this.getMRU() + 1 <= this.getMaxMRU())
					{
						int current = Integer.parseInt(currentEnergy);
						if(current - 10 >= 0)
						{
							DummyDataUtils.setDataForPlayer(username, "essentialcraft", "ubmruEnergy", Integer.toString(current-10));
							this.setMRU(this.getMRU()+1);
				    		for(int o = 0; o < 10; ++o)
				    		{
				    			this.worldObj.spawnParticle("reddust", xCoord+0.25D+this.worldObj.rand.nextDouble()/2.2D, yCoord+0.25D+((float)o/20), zCoord+0.25D+this.worldObj.rand.nextDouble()/2.2D, 1.0D, 0.0D, 1.0D);
				    		}
						}
					}
				}
	    	}
		}
	}
	
	@Override
    public void readFromNBT(NBTTagCompound i)
    {
		super.readFromNBT(i);
    }
	
	@Override
    public void writeToNBT(NBTTagCompound i)
    {
    	super.writeToNBT(i);
    }

}
