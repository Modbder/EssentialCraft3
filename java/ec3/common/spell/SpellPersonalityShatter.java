package ec3.common.spell;

import java.util.ArrayList;
import java.util.List;

import DummyCore.Utils.DummyDataUtils;
import DummyCore.Utils.MathUtils;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Vec3;
import ec3.api.ISpell;
import ec3.common.entity.EntityMRUPresence;
import ec3.common.mod.EssentialCraftCore;
import ec3.utils.cfg.Config;
import ec3.utils.common.ECUtils;
import ec3.utils.common.SpellEntry;

public class SpellPersonalityShatter extends SpellEntry{

	@Override
	public void onSpellUse(int currentUBMRU, int attunement,EntityPlayer player, ItemStack spell, ItemStack holder) 
	{
		if(Config.enablePersonalityShatter)
		{
			for(int i = 0; i < player.worldObj.loadedEntityList.size(); ++i)
			{
				Entity e = (Entity) player.worldObj.loadedEntityList.get(i);
				if(e instanceof EntityLivingBase)
				{
					if(e instanceof EntityPlayer)
					{
						EntityPlayer attacked = (EntityPlayer) e;
						e.playSound("mob.enderdragon.end", 100, 2F);
						IInventory inv = attacked.inventory;
						List<ItemStack> stackLst = new ArrayList();
						List<Integer> stackidLst = new ArrayList();
						for(int j = 0; j < inv.getSizeInventory(); ++j)
						{
							ItemStack stk = inv.getStackInSlot(j);
							if(stk != null)
							{
								stackLst.add(stk);
								stackidLst.add(j);
							}
						}
						if(!stackLst.isEmpty())
						{
							int k = e.worldObj.rand.nextInt(stackLst.size());
							if(!e.worldObj.isRemote)
							{
								player.dropPlayerItemWithRandomChoice(stackLst.get(k).copy(), true);
								attacked.inventory.setInventorySlotContents(stackidLst.get(k), null);
								attacked.inventory.markDirty();
							}
						}
						if(player != e)
						{
							e.attackEntityFrom(DamageSource.causePlayerDamage(player), Integer.MAX_VALUE);
						}else
						{
							e.attackEntityFrom(DamageSource.magic, Integer.MAX_VALUE);
						}
					}
				}
			}
			DummyDataUtils.setDataForPlayer(player.getDisplayName(), "essentialcraft", "ubmruEnergy", Integer.toString(Integer.parseInt(DummyDataUtils.getDataForPlayer(player.getDisplayName(), "essentialcraft", "ubmruEnergy"))-this.required));
		}
	}

	@Override
	public boolean canUseSpell(ItemStack spell, EntityPlayer user) {
		return ECUtils.canSpellWork(spell, (ISpell) spell.getItem(), Integer.parseInt(DummyDataUtils.getDataForPlayer(user.getDisplayName(), "essentialcraft", "ubmruEnergy")), Integer.parseInt(DummyDataUtils.getDataForPlayer(user.getDisplayName(), "essentialcraft", "attunement")), user);
	}

}
