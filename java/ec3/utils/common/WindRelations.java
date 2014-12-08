package ec3.utils.common;

import java.util.Hashtable;
import java.util.Random;

import ec3.common.item.ItemsCore;
import ec3.common.registry.BiomeRegistry;
import ec3.common.registry.PotionRegistry;
import DummyCore.Utils.DummyDataUtils;
import DummyCore.Utils.MathUtils;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.biome.BiomeGenBase;

public class WindRelations {
	
	public static int getPlayerWindRelations(EntityPlayer player)
	{
		String str = DummyDataUtils.getDataForPlayer(player.getDisplayName(), "essentialcraft", "windRelations");
		if(str == null || str.isEmpty() || str.equals("no data") || str.equals("empty string") || str.equals("empty"))
		{
			DummyDataUtils.setDataForPlayer(player.getDisplayName(), "essentialcraft", "windRelations", Integer.toString(0));
		}
		str = DummyDataUtils.getDataForPlayer(player.getDisplayName(), "essentialcraft", "windRelations");
		int retInt = Integer.parseInt(str);
		return retInt;
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
			}
		}
		return retFlt;
	}
	
	public static int getPlayerRadiation(EntityPlayer player)
	{
		String str = DummyDataUtils.getDataForPlayer(player.getDisplayName(), "essentialcraft", "radiation");
		if(str == null || str.isEmpty() || str.equals("no data") || str.equals("empty string") || str.equals("empty"))
		{
			DummyDataUtils.setDataForPlayer(player.getDisplayName(), "essentialcraft", "radiation", Integer.toString(0));
		}
		str = DummyDataUtils.getDataForPlayer(player.getDisplayName(), "essentialcraft", "radiation");
		int retInt = Integer.parseInt(str);
		return retInt;
	}
	
	public static void setPlayerWindRelations(EntityPlayer player, int amount)
	{
		DummyDataUtils.setDataForPlayer(player.getDisplayName(), "essentialcraft", "windRelations", Integer.toString(amount));
	}
	
	public static void setPlayerRadiation(EntityPlayer player, int amount)
	{
		DummyDataUtils.setDataForPlayer(player.getDisplayName(), "essentialcraft", "radiation", Integer.toString(amount));
	}
	
	public static void increasePlayerWindRelations(EntityPlayer e, int amount)
	{
		int current = getPlayerWindRelations(e);
		amount *= getPlayerWindRevModifier(e);
		setPlayerWindRelations(e, current+amount);
		e.addPotionEffect(new PotionEffect(PotionRegistry.windTouch.id,1000,0));
	}
	
	public static void increasePlayerRadiation(EntityPlayer e, int amount)
	{
		int current = getPlayerRadiation(e);
		setPlayerRadiation(e, current+amount);
	}
	
	public static void playerTick(EntityPlayer player)
	{
		int dimID = player.dimension;
		if(dimID == 53)
		{
			int chunkX = player.chunkCoordX;
			int chunkZ = player.chunkCoordZ;
			Random rnd = new Random(Long.parseLong((int)MathUtils.module(chunkX)*128+""+(int)MathUtils.module(chunkZ)*128));
			BiomeGenBase biome = player.worldObj.getBiomeGenForCoords((int)player.posX, (int)player.posZ);
			int rndRad = rnd.nextInt(6);
			if(biome == BiomeRegistry.firstWorldBiomeArray[8])
				rndRad += 2;
			if(biome == BiomeRegistry.firstWorldBiomeArray[4])
				rndRad += 6;
			rndRad = (int) ((float)rndRad * ECUtils.getGenResistance(2, player));
			increasePlayerRadiation(player,rndRad);
			rnd = null;
		}
		String str1 = DummyDataUtils.getDataForPlayer(player.getDisplayName(), "essentialcraft", "radiation");
		if(str1 == null || str1.isEmpty() || str1.equals("no data") || str1.equals("empty string") || str1.equals("empty"))
		{
			DummyDataUtils.setDataForPlayer(player.getDisplayName(), "essentialcraft", "radiation", Integer.toString(0));
		}else
		{
			int amount = getPlayerRadiation(player);
			if(amount > 0)
				increasePlayerRadiation(player,-1);
			if(amount > 0)
			{
				boolean hasEffect = player.getActivePotionEffect(PotionRegistry.radiation) != null;
				if(hasEffect)
				{
					int currentDuration = amount;
					int newModifier = currentDuration/2000;
					player.removePotionEffect(PotionRegistry.radiation.id);
					player.addPotionEffect(new PotionEffect(PotionRegistry.radiation.id,currentDuration,newModifier));
				}else
				{
					player.addPotionEffect(new PotionEffect(PotionRegistry.radiation.id,200,0));
				}
			}
		}
		String str = DummyDataUtils.getDataForPlayer(player.getDisplayName(), "essentialcraft", "windRelations");
		if(str == null || str.isEmpty() || str.equals("no data") || str.equals("empty string") || str.equals("empty"))
		{
			DummyDataUtils.setDataForPlayer(player.getDisplayName(), "essentialcraft", "windRelations", Integer.toString(0));
		}else
		{
			int current = getPlayerWindRelations(player);
			int mod = 1;
			if(player.getActivePotionEffect(PotionRegistry.paranormalLightness) != null) mod = player.getActivePotionEffect(PotionRegistry.paranormalLightness).getAmplifier()+1;
			if(!player.worldObj.isRemote && player.worldObj.rand.nextDouble() < (0.00001F)*mod)
			{
				player.addChatMessage(new ChatComponentText(EnumChatFormatting.DARK_AQUA+""+EnumChatFormatting.ITALIC+"The wind timidly touches your hair..."));
				increasePlayerWindRelations(player,1000);
			}
			if(!player.worldObj.isRemote && player.worldObj.rand.nextDouble() < 0.00005F*mod)
			{
				if(player.isSprinting())
				{
					player.addChatMessage(new ChatComponentText(EnumChatFormatting.DARK_AQUA+""+EnumChatFormatting.ITALIC+"The wind pushes you in the back..."));
					increasePlayerWindRelations(player,1000);
					player.addPotionEffect(new PotionEffect(Potion.moveSpeed.id,20,31));
				}
			}
		}
	}

}
