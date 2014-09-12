package ec3.common.tile;

import java.util.List;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemPotion;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.Vec3;
import DummyCore.Utils.Coord3D;
import DummyCore.Utils.DummyDistance;
import DummyCore.Utils.MathUtils;
import DummyCore.Utils.MiscUtils;
import ec3.api.ApiCore;
import ec3.api.ITEHasMRU;
import ec3.common.item.ItemBoundGem;
import ec3.utils.common.ECUtils;

public class TilePotionSpreader extends TileMRUGeneric{
	public int potionID = -1;
	public int potionDuration = -1;
	public int potionAmplifier = -1;
	public int potionUseTime = -1;
	
	public TilePotionSpreader()
	{
		this.maxMRU = (int) ApiCore.DEVICE_MAX_MRU_GENERIC;
		this.setSlotsNum(9);
	}
	
	@Override
	public void updateEntity()
	{
		super.updateEntity();
		ECUtils.mruIn(this, 0);
		ECUtils.spawnMRUParticles(this, 0);
		IInventory inv = this;
		int slotNum = 0;
		TileEntity tile = this;
		if(inv.getStackInSlot(slotNum) != null && inv.getStackInSlot(slotNum).getItem() instanceof ItemBoundGem && inv.getStackInSlot(slotNum).getTagCompound() != null)
		{
			ItemStack s = inv.getStackInSlot(slotNum);
			int[] o = ItemBoundGem.getCoords(s);
			if(MathUtils.getDifference(tile.xCoord, o[0]) <= 16 && MathUtils.getDifference(tile.yCoord, o[1]) <= 16 && MathUtils.getDifference(tile.zCoord, o[2]) <= 16)
			{
    			if(tile.getWorldObj().getTileEntity(o[0], o[1], o[2]) != null && tile.getWorldObj().getTileEntity(o[0], o[1], o[2]) instanceof ITEHasMRU)
    			{
    				this.setBalance(((ITEHasMRU) tile.getWorldObj().getTileEntity(o[0], o[1], o[2])).getBalance());
    			}
    		}
		}
		if(this.potionID == -1)
			for(int i = 1; i < 9; ++i)
			{
				ItemStack stk = this.getStackInSlot(i);
				if(stk != null && stk.getItem() instanceof ItemPotion)
				{
					ItemPotion potion = (ItemPotion) stk.getItem();
					List<PotionEffect> lst = potion.getEffects(stk);
					if(!lst.isEmpty())
					{
						PotionEffect effect = lst.get(0);
						this.potionID = effect.getPotionID();
						this.potionAmplifier = effect.getAmplifier();
						this.potionDuration = effect.getDuration();
						this.potionUseTime = 16;
						this.setInventorySlotContents(i, null);
						break;
					}
				}
			}
		else
		{
			List<EntityLivingBase> lst = this.worldObj.getEntitiesWithinAABB(EntityLivingBase.class, AxisAlignedBB.getBoundingBox(xCoord-8, yCoord-8, zCoord-8, xCoord+9, yCoord+9, zCoord+9));
			if(!lst.isEmpty() && !this.worldObj.isRemote)
			{
				boolean haveUsedPotion = false;
				for(int i = 0; i < lst.size(); ++i)
				{
					EntityLivingBase base = lst.get(i);
					boolean shouldUsePotion = false;
					PotionEffect effect = new PotionEffect(this.potionID,this.potionDuration,this.potionAmplifier);
					if(potionID == Potion.heal.id)
					{
						float healAmount = (float)Math.max(4 << effect.getAmplifier(), 0);
						shouldUsePotion = (!base.isEntityUndead() && base.getHealth()+healAmount<=base.getMaxHealth()) || (base.isEntityUndead() && base.hurtResistantTime == 0 && base.hurtTime == 0);
					}else if(potionID == Potion.harm.id)
					{
						float damageAmount = (float)(6 << effect.getAmplifier());
						shouldUsePotion = (base.isEntityUndead() && base.getHealth()+damageAmount<=base.getMaxHealth()) || (!base.isEntityUndead()  && base.hurtResistantTime == 0 && base.hurtTime == 0);
					}else
					{
						shouldUsePotion = !base.isPotionActive(potionID);
					}
					if(shouldUsePotion && this.getMRU() >= 250)	
					{
						this.setMRU(this.getMRU()-250);
						haveUsedPotion = true;
						base.addPotionEffect(effect);
						int j = Potion.potionTypes[potionID].getLiquidColor();
						float f = 0F;
						float f1 = 0F;
						float f2 = 0F;
				        f += (float)(j >> 16 & 255) / 255.0F;
				        f1 += (float)(j >> 8 & 255) / 255.0F;
				        f2 += (float)(j >> 0 & 255) / 255.0F;
				        for(int i1 = 0; i1 < 100; ++i1)
				        	MiscUtils.spawnParticlesOnServer("mobSpell", (float) (base.posX+MathUtils.randomFloat(this.worldObj.rand)), (float) (base.posY+1+MathUtils.randomFloat(this.worldObj.rand)), (float) (base.posZ+MathUtils.randomFloat(this.worldObj.rand)), f, f1, f2);
					}
				}
				if(haveUsedPotion)
					--this.potionUseTime;
			}
			if(this.potionUseTime <= 0)
			{
				this.potionID = -1;
				this.potionAmplifier = -1;
				this.potionDuration = -1;
			}
			if(potionID != -1)
			{
				int j = Potion.potionTypes[potionID].getLiquidColor();
				float f = 0F;
				float f1 = 0F;
				float f2 = 0F;
		        f += (float)(j >> 16 & 255) / 255.0F;
		        f1 += (float)(j >> 8 & 255) / 255.0F;
		        f2 += (float)(j >> 0 & 255) / 255.0F;
		       this.worldObj.spawnParticle("mobSpell", xCoord+0.5F, yCoord+0.5F, zCoord+0.5F, f, f1, f2);
			}
			
		}
	}
	
    @SideOnly(Side.CLIENT)
    @Override
    public AxisAlignedBB getRenderBoundingBox()
    {
    	AxisAlignedBB bb = INFINITE_EXTENT_AABB;
    	return bb;
    }
    
	@Override
    public void readFromNBT(NBTTagCompound i)
    {
		potionID = i.getInteger("potionID");
		potionDuration = i.getInteger("potionDuration");
		potionAmplifier = i.getInteger("potionAmplifier");
		potionUseTime = i.getInteger("potionUseTime");
		super.readFromNBT(i);
    }
	
	@Override
    public void writeToNBT(NBTTagCompound i)
    {
		i.setInteger("potionID", potionID);
		i.setInteger("potionDuration", potionDuration);
		i.setInteger("potionAmplifier", potionAmplifier);
		i.setInteger("potionUseTime", potionUseTime);
    	super.writeToNBT(i);
    }

}
