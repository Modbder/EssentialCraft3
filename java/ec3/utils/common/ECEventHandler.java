package ec3.utils.common;

import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.common.eventhandler.Event.Result;
import cpw.mods.fml.common.eventhandler.EventPriority;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent;
import cpw.mods.fml.common.gameevent.TickEvent.Phase;
import cpw.mods.fml.common.gameevent.TickEvent.WorldTickEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ec3.api.WorldEventLibrary;
import ec3.client.gui.GuiResearchBook;
import ec3.common.item.ItemsCore;
import ec3.common.mod.EssentialCraftCore;
import ec3.common.registry.ResearchRegistry;
import ec3.common.world.BiomeGenCorruption_Chaos;
import ec3.common.world.BiomeGenCorruption_Frozen;
import ec3.common.world.BiomeGenCorruption_Magic;
import DummyCore.Utils.DataStorage;
import DummyCore.Utils.DummyData;
import DummyCore.Utils.DummyDataUtils;
import DummyCore.Utils.MathUtils;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.util.DamageSource;
import net.minecraft.util.StatCollector;
import net.minecraftforge.client.event.GuiScreenEvent;
import net.minecraftforge.common.util.FakePlayer;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.terraingen.BiomeEvent;
import net.minecraftforge.event.terraingen.DecorateBiomeEvent;

public class ECEventHandler {
	
	public String lastTickLanguage;
	
	@SubscribeEvent
	public void worldTick(WorldTickEvent event)
	{
		if(!event.world.isRemote)
		{
			String worldEvent = DummyDataUtils.getCustomDataForMod("essentialcraft", "worldEvent");
			if(worldEvent != null)
			{
				if(event.phase == Phase.END)
				{
					if(WorldEventLibrary.currentEvent == null)
					{
						worldEvent = "no data";
					}else
					{
						worldEvent = new DummyData(WorldEventLibrary.currentEvent.getEventID(),WorldEventLibrary.currentEventDuration).toString();
					}
					ECUtils.endEvent(event.world);
					ECUtils.newWorldEvent(event.world);
					DummyDataUtils.writeCustomDataForMod("essentialcraft", "worldEvent", worldEvent);
				}else
				{
					if(WorldEventLibrary.currentEvent == null && !worldEvent.equals("no data"))
					{
						DummyData[] data = DataStorage.parseData(worldEvent);
						WorldEventLibrary.currentEvent = WorldEventLibrary.gettEffectByID(data[0].fieldName, event.world);
						WorldEventLibrary.currentEventDuration = Integer.parseInt(data[0].fieldValue);
					}
					if(WorldEventLibrary.currentEvent != null)
						WorldEventLibrary.currentEvent.worldTick(event.world, WorldEventLibrary.currentEventDuration);
					DummyDataUtils.writeCustomDataForMod("essentialcraft", "worldEvent", worldEvent);
				}
			}
		}
	}
	
	@SubscribeEvent(priority=EventPriority.HIGHEST)
	public void denyFlowerGen(DecorateBiomeEvent.Decorate event)
	{
		if(event.type == event.type.FLOWERS)
		{
			if(event.world.provider.dimensionId == 53)
			{
				event.setResult(Result.DENY);
			}
		}
	}
	
	@SideOnly(Side.CLIENT)
	@SubscribeEvent
	public void getBiomeWaterColor(BiomeEvent.GetWaterColor event)
	{
		EntityPlayer player = EssentialCraftCore.proxy.getClientPlayer();
		if(player != null)
		{
			int dimID = player.dimension;
			if(dimID == 53)
			{
				event.newColor = 0xff6a58;
			}
		}
	}
	
	@SideOnly(Side.CLIENT)
	@SubscribeEvent
	public void clientTick(TickEvent.ClientTickEvent event)
	{
		if(event.phase == TickEvent.Phase.START)
		{

		}else
		{
			if(lastTickLanguage != null && !lastTickLanguage.isEmpty())
			{
				if(!lastTickLanguage.equalsIgnoreCase(FMLClientHandler.instance().getCurrentLanguage()))
				{
					ResearchRegistry.init();
					if(GuiResearchBook.currentCategory != null && GuiResearchBook.currentDiscovery != null)
					{
						String id = GuiResearchBook.currentDiscovery.id;
						for(int i = 0; i < GuiResearchBook.currentCategory.discoveries.size(); ++i)
						{
							if(GuiResearchBook.currentCategory.discoveries.get(i).id.equals(id))
							{
								GuiResearchBook.currentDiscovery=GuiResearchBook.currentCategory.discoveries.get(i);
								break;
							}
						}
					}
				}
			}
			lastTickLanguage = FMLClientHandler.instance().getCurrentLanguage();
		}
	}
	
	@SideOnly(Side.CLIENT)
	@SubscribeEvent
	public void getBiomeFolliageColor(BiomeEvent.GetFoliageColor event)
	{
		EntityPlayer player = EssentialCraftCore.proxy.getClientPlayer();
		if(player != null)
		{
			int dimID = player.dimension;
			if(dimID == 53)
			{
				if(!(event.biome instanceof BiomeGenCorruption_Chaos) && !(event.biome instanceof BiomeGenCorruption_Frozen) && !(event.biome instanceof BiomeGenCorruption_Magic))
				{
					event.newColor = 0x886a58;
				}
			}
		}
	}
	
	@SideOnly(Side.CLIENT)
	@SubscribeEvent
	public void getBiomeGrassColor(BiomeEvent.GetGrassColor event)
	{
		EntityPlayer player = EssentialCraftCore.proxy.getClientPlayer();
		if(player != null)
		{
			int dimID = player.dimension;
			if(dimID == 53)
			{
				if(!(event.biome instanceof BiomeGenCorruption_Chaos) && !(event.biome instanceof BiomeGenCorruption_Frozen) && !(event.biome instanceof BiomeGenCorruption_Magic))
				{
					event.newColor = 0x886a58;
				}
			}
		}
	}
	
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
					ItemStack helmet = player.inventory.armorInventory[3];
					if(helmet != null && helmet.getItem() == ItemsCore.magicArmorItems[4])
					{
						player.getFoodStats().addStats((int) (maxHp/10F), 1F);
					}
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
