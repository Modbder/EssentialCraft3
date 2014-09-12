package ec3.common.spell;

import java.util.List;

import DummyCore.Utils.DummyDataUtils;
import DummyCore.Utils.MathUtils;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.monster.IMob;
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

public class SpellWorldShatter extends SpellEntry{

	@Override
	public void onSpellUse(int currentUBMRU, int attunement,EntityPlayer player, ItemStack spell, ItemStack holder) 
	{
		for(int i = 0; i < player.worldObj.loadedEntityList.size(); ++i)
		{
			Entity e = (Entity) player.worldObj.loadedEntityList.get(i);
			if(e instanceof EntityLivingBase)
			{
				if(e instanceof EntityPlayer)
				{
					e.playSound("mob.enderdragon.end", 100, 0.01F);
				}else
				{
					if(e instanceof IMob)
						e.attackEntityFrom(DamageSource.causePlayerDamage(player), Integer.MAX_VALUE);
				}
			}
		}
		DummyDataUtils.setDataForPlayer(player.getDisplayName(), "essentialcraft", "ubmruEnergy", Integer.toString(Integer.parseInt(DummyDataUtils.getDataForPlayer(player.getDisplayName(), "essentialcraft", "ubmruEnergy"))-this.required));
	}

	@Override
	public boolean canUseSpell(ItemStack spell, EntityPlayer user) {
		return ECUtils.canSpellWork(spell, (ISpell) spell.getItem(), Integer.parseInt(DummyDataUtils.getDataForPlayer(user.getDisplayName(), "essentialcraft", "ubmruEnergy")), Integer.parseInt(DummyDataUtils.getDataForPlayer(user.getDisplayName(), "essentialcraft", "attunement")), user);
	}

}
