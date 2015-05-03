package ec3.common.spell;

import java.util.List;

import DummyCore.Utils.DummyDataUtils;
import DummyCore.Utils.MathUtils;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.Vec3;
import ec3.api.ISpell;
import ec3.common.entity.EntityMRUPresence;
import ec3.common.mod.EssentialCraftCore;
import ec3.utils.common.ECUtils;
import ec3.utils.common.SpellEntry;

public class SpellTransform extends SpellEntry{
	
	public ItemStack input;
	public ItemStack output;
	public SpellTransform setTransforming(ItemStack stk)
	{
		input = stk;
		return this;
	}
	
	public SpellTransform setTransformed(ItemStack stk)
	{
		output = stk;
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
			List<EntityItem> itemList = player.worldObj.getEntitiesWithinAABB(EntityItem.class, AxisAlignedBB.getBoundingBox(player.posX+additionalVec.xCoord-1, player.posY+additionalVec.yCoord-2, player.posZ+additionalVec.zCoord-1, player.posX+additionalVec.xCoord+1, player.posY+additionalVec.yCoord+2, player.posZ+additionalVec.zCoord+1));
			if(!itemList.isEmpty())
			{
				for(int h = 0; h < itemList.size(); ++h)
				{
					EntityItem item = itemList.get(0);
					ItemStack stk = item.getEntityItem();
					if(stk.getItem() == this.input.getItem())
					{
							stk.stackSize -= 1;
							if(stk.stackSize <= 0)
								item.setDead();
						if(!player.inventory.addItemStackToInventory(output.copy()))
						{
							player.dropPlayerItemWithRandomChoice(output.copy(), true);
						}
						DummyDataUtils.setDataForPlayer(player.getDisplayName(), "essentialcraft", "ubmruEnergy", Integer.toString(Integer.parseInt(DummyDataUtils.getDataForPlayer(player.getDisplayName(), "essentialcraft", "ubmruEnergy"))-this.required));
						for(int i1 = 0; i1 < 600; ++i1)
							if(player.worldObj.isRemote)
								EssentialCraftCore.proxy.spawnParticle("cSpellFX", (float)player.posX+MathUtils.randomFloat(player.worldObj.rand)/6, (float)player.posY+MathUtils.randomFloat(player.worldObj.rand)/6, (float)player.posZ+MathUtils.randomFloat(player.worldObj.rand)/6, item.posX-player.posX, item.posY-player.posY, item.posZ-player.posZ);
						break Ford;
					}
				}
			}
		}
	}

	@Override
	public boolean canUseSpell(ItemStack spell, EntityPlayer user) {
		return ECUtils.canSpellWork(spell, (ISpell) spell.getItem(), Integer.parseInt(DummyDataUtils.getDataForPlayer(user.getDisplayName(), "essentialcraft", "ubmruEnergy")), Integer.parseInt(DummyDataUtils.getDataForPlayer(user.getDisplayName(), "essentialcraft", "attunement")), user);
	}

}
