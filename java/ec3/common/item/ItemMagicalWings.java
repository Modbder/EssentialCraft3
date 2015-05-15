package ec3.common.item;

import baubles.api.BaubleType;
import baubles.api.IBauble;
import DummyCore.Utils.MathUtils;
import DummyCore.Utils.MiscUtils;
import ec3.common.mod.EssentialCraftCore;
import ec3.utils.common.ECUtils;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemMagicalWings extends ItemStoresMRUInNBT implements IBauble{

	public ItemMagicalWings() {
		super();
		this.setMaxMRU(5000);
		this.maxStackSize = 1;
        this.bFull3D = true;
	}
	
	@Override
	public void onUpdate(ItemStack s, World world, Entity entity, int indexInInventory, boolean isCurrentItem)
	{
		super.onUpdate(s, world, entity, indexInInventory, isCurrentItem);
		if(entity instanceof EntityPlayer)
		{
			EntityPlayer e = (EntityPlayer) entity;
			{
				if(e.getCurrentEquippedItem() == s && (ECUtils.tryToDecreaseMRUInStorage(e, -1) || this.setMRU(s, -1)))
				{
					if(!e.isSneaking())
					{
						e.motionY += 0.1F;
						e.fallDistance = 0F;
					}
					else
					{
						e.motionY = -0.2F;
						e.fallDistance = 0F;
					}
					world.spawnParticle("reddust", e.posX+MathUtils.randomDouble(world.rand)/2, e.posY-1+MathUtils.randomDouble(world.rand), e.posZ+MathUtils.randomDouble(world.rand)/2, 0, 1, 1);
				}
				if(e.motionY < -.2F && e.isSneaking() && (ECUtils.tryToDecreaseMRUInStorage(e, -1) || this.setMRU(s, -1)))
				{
					e.motionY = -.2F;
					e.fallDistance = 0F;
					world.spawnParticle("reddust", e.posX+MathUtils.randomDouble(world.rand)/2, e.posY-1+MathUtils.randomDouble(world.rand), e.posZ+MathUtils.randomDouble(world.rand)/2, 0, 1, 1);
					
				}
			}
		}
		
	}
	
	@Override
	public BaubleType getBaubleType(ItemStack itemstack) {
		// TODO Auto-generated method stub
		return BaubleType.BELT;
	}

	@Override
	public void onWornTick(ItemStack itemstack, EntityLivingBase player) {
		if(player instanceof EntityPlayer)
		{
			EntityPlayer e = (EntityPlayer) player;
			if(e.motionY < -.2F && !e.isSneaking() && (ECUtils.tryToDecreaseMRUInStorage(e, -1) || this.setMRU(itemstack, -1)))
			{
				e.motionY = -.2F;
				e.fallDistance = 0F;
				e.worldObj.spawnParticle("reddust", e.posX+MathUtils.randomDouble(e.worldObj.rand)/2, e.posY-1+MathUtils.randomDouble(e.worldObj.rand), e.posZ+MathUtils.randomDouble(e.worldObj.rand)/2, 0, 1, 1);
				
			}
			if(this.getMRU(itemstack) >= 1)
				EssentialCraftCore.proxy.wingsAction(e, itemstack);
			MiscUtils.applyPlayerModifier((EntityPlayer)player, SharedMonsterAttributes.movementSpeed, "EC300", 0.1F, false, 0, "bauble");
		}
	}

	@Override
	public void onEquipped(ItemStack itemstack, EntityLivingBase player) {
	}

	@Override
	public void onUnequipped(ItemStack itemstack, EntityLivingBase player) {
		MiscUtils.applyPlayerModifier((EntityPlayer)player, SharedMonsterAttributes.movementSpeed, "EC300", 0.1F, true, 0, "bauble");
		
	}

	@Override
	public boolean canEquip(ItemStack itemstack, EntityLivingBase player) {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean canUnequip(ItemStack itemstack, EntityLivingBase player) {
		// TODO Auto-generated method stub
		return true;
	}
}
