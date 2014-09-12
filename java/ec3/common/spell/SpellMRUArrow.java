package ec3.common.spell;

import java.util.List;

import DummyCore.Utils.DummyDataUtils;
import DummyCore.Utils.MathUtils;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.Vec3;
import ec3.api.ISpell;
import ec3.common.entity.EntityMRUArrow;
import ec3.common.entity.EntityMRUPresence;
import ec3.common.mod.EssentialCraftCore;
import ec3.utils.common.ECUtils;
import ec3.utils.common.SpellEntry;

public class SpellMRUArrow extends SpellEntry{
	
	public double damage;
	public boolean isHoming;
	
	public SpellMRUArrow setDamage(double dam)
	{
		damage = dam;
		return this;
	}
	
	public SpellMRUArrow setHoming(boolean bool)
	{
		isHoming = bool;
		return this;
	}

	@Override
	public void onSpellUse(int currentUBMRU, int attunement,EntityPlayer player, ItemStack spell, ItemStack holder) {

		Vec3 lookVec = player.getLookVec();
		Vec3 mainLookVec = player.getLookVec();
	    int mDistance = 0;
		Ford:for(int i = 0; i < 20; ++i)
		{
			Vec3 additionalVec = mainLookVec.addVector(mainLookVec.xCoord*i, mainLookVec.yCoord*i, mainLookVec.zCoord*i);
			List<EntityLivingBase> lst = player.worldObj.getEntitiesWithinAABB(EntityLivingBase.class, AxisAlignedBB.getBoundingBox(player.posX+additionalVec.xCoord-1, player.posY+additionalVec.yCoord-2, player.posZ+additionalVec.zCoord-1, player.posX+additionalVec.xCoord+1, player.posY+additionalVec.yCoord+2, player.posZ+additionalVec.zCoord+1));
			if(!lst.isEmpty())
			{
				EntityLivingBase base = lst.get(0);
				if(base != player)
				{
					EntityMRUArrow arrow = new EntityMRUArrow(player.worldObj, player, 3);
					if(!player.worldObj.isRemote)
						player.worldObj.spawnEntityInWorld(arrow);
					arrow.setDamage(2.0D+damage);
					for(int i1 = 0; i1 < 600; ++i1)
						if(player.worldObj.isRemote)
							EssentialCraftCore.proxy.spawnParticle("cSpellFX", (float)player.posX+MathUtils.randomFloat(player.worldObj.rand)/6, (float)player.posY+MathUtils.randomFloat(player.worldObj.rand)/6, (float)player.posZ+MathUtils.randomFloat(player.worldObj.rand)/6, base.posX-player.posX, base.posY-player.posY, base.posZ-player.posZ);
					DummyDataUtils.setDataForPlayer(player.getDisplayName(), "essentialcraft", "ubmruEnergy", Integer.toString(Integer.parseInt(DummyDataUtils.getDataForPlayer(player.getDisplayName(), "essentialcraft", "ubmruEnergy"))-this.required));
					break Ford;
				}
			}
		}
	}

	@Override
	public boolean canUseSpell(ItemStack spell, EntityPlayer user) {
		return ECUtils.canSpellWork(spell, (ISpell) spell.getItem(), Integer.parseInt(DummyDataUtils.getDataForPlayer(user.getDisplayName(), "essentialcraft", "ubmruEnergy")), Integer.parseInt(DummyDataUtils.getDataForPlayer(user.getDisplayName(), "essentialcraft", "attunement")), user);
	}

}
