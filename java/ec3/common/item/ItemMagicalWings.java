package ec3.common.item;

import java.util.List;

import DummyCore.Utils.MathUtils;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.relauncher.Side;
import ec3.utils.common.ECUtils;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.stats.StatList;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeHooks;

public class ItemMagicalWings extends ItemStoresMRUInNBT {

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
			int dam = s.getItemDamage();
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
}
