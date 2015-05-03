package ec3.utils.common;

import baubles.api.BaublesApi;
import ec3.api.IWindModifier;
import ec3.api.IWindResistance;
import ec3.common.item.BaublesModifier;
import ec3.common.item.ItemsCore;
import ec3.common.registry.PotionRegistry;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.common.util.FakePlayer;

public class WindRelations {
	
	public static int getPlayerWindRelations(EntityPlayer player)
	{
		if((player instanceof FakePlayer)) return 0;
		return ECUtils.getData(player).getPlayerWindPoints();
	}
	
	public static float getPlayerWindRevModifier(EntityPlayer player)
	{
		float retFlt = 0.1F;
		for(int i = 0; i < player.inventory.armorInventory.length; ++i)
		{
			ItemStack armor = player.inventory.armorInventory[i];
			if(armor != null)
			{
				if(armor.getItem() == ItemsCore.magicArmorItems[12] || armor.getItem() == ItemsCore.magicArmorItems[13] || armor.getItem() == ItemsCore.magicArmorItems[14] || armor.getItem() == ItemsCore.magicArmorItems[15])
				{
					retFlt += 0.23F;
				}
				if(armor.getItem() instanceof IWindModifier)
				{
					retFlt += ((IWindModifier)armor.getItem()).getModifier(armor, player);
				}
			}
		}
		if(BaublesApi.getBaubles(player) != null)
		for(int i = 0; i < BaublesApi.getBaubles(player).getSizeInventory(); ++i)
		{
			ItemStack armor = BaublesApi.getBaubles(player).getStackInSlot(i);
			if(armor != null)
			{
				if(armor.getItem() instanceof IWindModifier)
				{
					retFlt += ((IWindModifier)armor.getItem()).getModifier(armor, player);
				}
			}
		}
		return retFlt;
	}
	
	public static void setPlayerWindRelations(EntityPlayer player, int amount)
	{
		if(!(player instanceof FakePlayer))
			ECUtils.getData(player).modifyWindpoints(amount);
	}
	
	public static void increasePlayerWindRelations(EntityPlayer e, int amount)
	{
		int current = getPlayerWindRelations(e);
		amount *= getPlayerWindRevModifier(e);
		setPlayerWindRelations(e, current+amount);
		e.addPotionEffect(new PotionEffect(PotionRegistry.windTouch.id,1000,0,true));
	}
	
	public static void playerTick(EntityPlayer player)
	{
		if(player instanceof FakePlayer) return;
		if(!ECUtils.getData(player).isWindbound()) return;
		
		int mod = 1;
		if(player.getActivePotionEffect(PotionRegistry.paranormalLightness) != null) 
			mod = player.getActivePotionEffect(PotionRegistry.paranormalLightness).getAmplifier()+1;
		
		if(!player.worldObj.isRemote && player.worldObj.rand.nextDouble() < (0.00001F)*mod)
		{
			
			increasePlayerWindRelations(player,1000);
			boolean elemental = false;
	       	IInventory b = BaublesApi.getBaubles(player);
	       	if(b != null)
	       	{
	       		for(int i = 0; i < b.getSizeInventory(); ++i)
	       		{
	       			ItemStack is = b.getStackInSlot(i);
	       			if(is != null && is.getItem() != null && is.getItem() instanceof BaublesModifier && is.getItemDamage() == 9)
	       				elemental = true;
	       		}
	       	}
	       	if(!elemental)
	       		player.addChatMessage(new ChatComponentText(EnumChatFormatting.DARK_AQUA+""+EnumChatFormatting.ITALIC+"The wind timidly touches your hair..."));
	       	else
	       	{
	       		int rndID = player.worldObj.rand.nextInt(4);
	       		//Fire
	       		if(rndID == 0)
	       		{
	       			player.addChatMessage(new ChatComponentText(EnumChatFormatting.DARK_AQUA+""+EnumChatFormatting.ITALIC+"You feel a warm touch of the wind..."));
	       			player.addPotionEffect(new PotionEffect(Potion.damageBoost.getId(),6*60*20,1,true));
	       		}
	       		//Water
	       		if(rndID == 1)
	       		{
	       			player.addChatMessage(new ChatComponentText(EnumChatFormatting.DARK_AQUA+""+EnumChatFormatting.ITALIC+"You feel a chilling touch of the wind..."));
	       			player.addPotionEffect(new PotionEffect(Potion.regeneration.getId(),2*60*20,0,true));
	       		}
	       		//Earth
	       		if(rndID == 2)
	       		{
	       			player.addChatMessage(new ChatComponentText(EnumChatFormatting.DARK_AQUA+""+EnumChatFormatting.ITALIC+"You feel a strong push of the wind..."));
	       			player.addPotionEffect(new PotionEffect(Potion.field_76443_y.getId(),2*60*20,0,true));
	       		}
	       		//Air
	       		if(rndID == 2)
	       		{
	       			player.addChatMessage(new ChatComponentText(EnumChatFormatting.DARK_AQUA+""+EnumChatFormatting.ITALIC+"You feel a strong blow of the wind..."));
	       			player.addPotionEffect(new PotionEffect(Potion.digSpeed.getId(),6*60*20,1,true));
	       		}
	       	}
		}
		if(!player.worldObj.isRemote && player.worldObj.rand.nextDouble() < 0.00005F*mod)
		{
			if(player.isSprinting())
			{
				player.addChatMessage(new ChatComponentText(EnumChatFormatting.DARK_AQUA+""+EnumChatFormatting.ITALIC+"The wind pushes you in the back..."));
				increasePlayerWindRelations(player,1000);
				boolean addBuff = true;
				
				if(BaublesApi.getBaubles(player) != null)
				{
					for(int i = 0; i < BaublesApi.getBaubles(player).getSizeInventory(); ++i)
					{
						if(BaublesApi.getBaubles(player).getStackInSlot(i) != null)
						{
							if(BaublesApi.getBaubles(player).getStackInSlot(i).getItem() != null)
							{
								if(BaublesApi.getBaubles(player).getStackInSlot(i).getItem() instanceof IWindResistance)
								{
									if(addBuff)
										addBuff = !((IWindResistance)BaublesApi.getBaubles(player).getStackInSlot(i).getItem()).resistWind(player, BaublesApi.getBaubles(player).getStackInSlot(i));
								}
							}
						}
					}
				}
				
				if(player.inventory != null)
				{
					for(int i = 0; i < player.inventory.armorInventory.length; ++i)
					{
						if(player.inventory.armorInventory[i] != null)
						{
							if(player.inventory.armorInventory[i].getItem() != null)
							{
								if(player.inventory.armorInventory[i].getItem() instanceof IWindResistance)
								{
									if(addBuff)
										addBuff = !((IWindResistance)player.inventory.armorInventory[i].getItem()).resistWind(player, BaublesApi.getBaubles(player).getStackInSlot(i));
								}
							}
						}
					}
				}
				
				if(addBuff)
					player.addPotionEffect(new PotionEffect(Potion.moveSpeed.id,20,31));
			}
		}
	}
}
