package ec3.common.spell;

import java.util.List;

import DummyCore.Utils.DummyDataUtils;
import DummyCore.Utils.MathUtils;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Vec3;
import ec3.api.ISpell;
import ec3.common.entity.EntityMRUPresence;
import ec3.common.mod.EssentialCraftCore;
import ec3.utils.common.ECUtils;
import ec3.utils.common.SpellEntry;

public class SpellMRURelease extends SpellEntry{
	
	public double damage;
	
	public SpellMRURelease setDamage(double d)
	{
		damage = d;
		return this;
	}

	@Override
	public void onSpellUse(int currentUBMRU, int attunement,EntityPlayer player, ItemStack spell, ItemStack holder) 
	{
		for(int i1 = 0; i1 < 600; ++i1)
			if(player.worldObj.isRemote)
				EssentialCraftCore.proxy.spawnParticle("cSpellFX", (float)player.posX+MathUtils.randomFloat(player.worldObj.rand)/6, (float)player.posY+MathUtils.randomFloat(player.worldObj.rand)/6, (float)player.posZ+MathUtils.randomFloat(player.worldObj.rand)/6, MathUtils.randomFloat(player.worldObj.rand)*6, MathUtils.randomFloat(player.worldObj.rand)*6, MathUtils.randomFloat(player.worldObj.rand)*6);
		List<EntityLivingBase> lst = player.worldObj.getEntitiesWithinAABB(EntityLivingBase.class, AxisAlignedBB.getBoundingBox(player.posX-6, player.posY-6, player.posZ-6, player.posX+6, player.posY+6, player.posZ+6));
		if(!lst.isEmpty())
		{
			for(int i = 0; i < lst.size();++i)
			{
				EntityLivingBase b = lst.get(i);
				if(b != player)
					b.attackEntityFrom(DamageSource.causePlayerDamage(player), (float) damage);
			}
		}
		DummyDataUtils.setDataForPlayer(player.getDisplayName(), "essentialcraft", "ubmruEnergy", Integer.toString(Integer.parseInt(DummyDataUtils.getDataForPlayer(player.getDisplayName(), "essentialcraft", "ubmruEnergy"))-this.required));
	}

	@Override
	public boolean canUseSpell(ItemStack spell, EntityPlayer user) {
		return ECUtils.canSpellWork(spell, (ISpell) spell.getItem(), Integer.parseInt(DummyDataUtils.getDataForPlayer(user.getDisplayName(), "essentialcraft", "ubmruEnergy")), Integer.parseInt(DummyDataUtils.getDataForPlayer(user.getDisplayName(), "essentialcraft", "attunement")), user);
	}

}
