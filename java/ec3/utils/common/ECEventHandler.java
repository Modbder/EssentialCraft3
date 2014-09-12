package ec3.utils.common;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import DummyCore.Utils.DummyDataUtils;
import DummyCore.Utils.MathUtils;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.DamageSource;
import net.minecraftforge.common.util.FakePlayer;
import net.minecraftforge.event.entity.living.LivingDeathEvent;

public class ECEventHandler {
	
	@SubscribeEvent
	public void onKillEntity(LivingDeathEvent event)
	{
		EntityLivingBase base = event.entityLiving;
		DamageSource src = event.source;
		if(src != null && src.getSourceOfDamage() != null)
		{
			Entity e = src.getSourceOfDamage();
			if(e instanceof EntityPlayer && !(e instanceof FakePlayer))
			{
				EntityPlayer player = (EntityPlayer) e;
				int addedEnergy = 0;
				if(base instanceof EntityPlayer && !(base instanceof FakePlayer))
				{
					String currentEnergy = DummyDataUtils.getDataForPlayer(((EntityPlayer) base).getDisplayName(),"essentialcraft", "ubmruEnergy");
					if(currentEnergy != null && !currentEnergy.isEmpty() && !currentEnergy.equals("no data") && !currentEnergy.equals("empty string") && !currentEnergy.equals("empty"))
					{
						int currentEnergy_int = Integer.parseInt(currentEnergy);
						addedEnergy += currentEnergy_int;
					}
				}else
				{
					float maxHp = base.getMaxHealth();
					addedEnergy += (20+(MathUtils.randomFloat(e.worldObj.rand)*10))*maxHp;
				}
				String currentEnergy = DummyDataUtils.getDataForPlayer(player.getDisplayName(),"essentialcraft", "ubmruEnergy");
				if(currentEnergy != null && !currentEnergy.isEmpty() && !currentEnergy.equals("no data") && !currentEnergy.equals("empty string") && !currentEnergy.equals("empty"))
				{
					int currentEnergy_int = Integer.parseInt(currentEnergy);
					currentEnergy_int += addedEnergy;
					DummyDataUtils.setDataForPlayer(player.getDisplayName(), "essentialcraft", "ubmruEnergy", Integer.toString(currentEnergy_int));
				}else
				{
					DummyDataUtils.setDataForPlayer(player.getDisplayName(), "essentialcraft", "ubmruEnergy", Integer.toString(addedEnergy));
				}
			}
		}
		if(base instanceof EntityPlayer)
		{
			DummyDataUtils.setDataForPlayer(((EntityPlayer) base).getDisplayName(), "essentialcraft", "ubmruEnergy", "0");
		}
	}

}
