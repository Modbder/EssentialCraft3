package ec3.utils.common;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.List;
import java.util.Set;

import org.lwjgl.opengl.GL11;

import baubles.api.BaublesApi;
import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.client.event.ConfigChangedEvent;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.eventhandler.Event.Result;
import cpw.mods.fml.common.eventhandler.EventPriority;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent;
import cpw.mods.fml.common.gameevent.TickEvent.Phase;
import cpw.mods.fml.common.gameevent.TickEvent.RenderTickEvent;
import cpw.mods.fml.common.gameevent.TickEvent.WorldTickEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ec3.api.WorldEventLibrary;
import ec3.client.gui.GuiResearchBook;
import ec3.common.block.BlocksCore;
import ec3.common.item.ItemMagicalWings;
import ec3.common.item.ItemsCore;
import ec3.common.mod.EssentialCraftCore;
import ec3.common.registry.ResearchRegistry;
import ec3.common.tile.TileAMINEjector;
import ec3.common.tile.TileAMINInjector;
import ec3.common.tile.TileMagicalAssembler;
import ec3.common.tile.TileMagicalMirror;
import ec3.common.world.BiomeGenCorruption_Chaos;
import ec3.common.world.BiomeGenCorruption_Frozen;
import ec3.common.world.BiomeGenCorruption_Magic;
import ec3.utils.cfg.Config;
import DummyCore.Events.DummyEvent_OnPacketRecieved;
import DummyCore.Utils.Coord3D;
import DummyCore.Utils.DataStorage;
import DummyCore.Utils.DummyData;
import DummyCore.Utils.DummyDataUtils;
import DummyCore.Utils.MathUtils;
import DummyCore.Utils.MiscUtils;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.inventory.InventoryBasic;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.Potion;
import net.minecraft.server.MinecraftServer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.DamageSource;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import net.minecraftforge.client.event.GuiScreenEvent;
import net.minecraftforge.common.util.FakePlayer;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.terraingen.BiomeEvent;
import net.minecraftforge.event.terraingen.DecorateBiomeEvent;

public class ECEventHandler {
	
	public String lastTickLanguage;
	
	@SubscribeEvent
	public void configSync(ConfigChangedEvent.OnConfigChangedEvent event)
	{
		if(event.modID.equals("essentialcraft"))
		{
			Config.config.save();
			Config.instance.load(Config.config);
		}
	}
	
	@SubscribeEvent
	public void guiButtonPressed(DummyCore.Events.DummyEvent_OnClientGUIButtonPress event)
	{
		if(event.client_ParentClassPath.equalsIgnoreCase("ec3.client.gui.GuiFilter"))
		{
			EntityPlayer p = event.presser;
			ItemStack is = p.getCurrentEquippedItem();
			NBTTagCompound itemTag = MiscUtils.getStackTag(is);
			if(event.buttonID == 0)
			{
				if(itemTag.getBoolean("ignoreMeta"))
					itemTag.setBoolean("ignoreMeta", false);
				else
					itemTag.setBoolean("ignoreMeta", true);
			}
			if(event.buttonID == 1)
			{
				if(itemTag.getBoolean("ignoreNBT"))
					itemTag.setBoolean("ignoreNBT", false);
				else
					itemTag.setBoolean("ignoreNBT", true);
			}
			if(event.buttonID == 2)
			{
				if(itemTag.getBoolean("ignoreOreDict"))
					itemTag.setBoolean("ignoreOreDict", false);
				else
					itemTag.setBoolean("ignoreOreDict", true);
			}
			is.setTagCompound(itemTag);
		}
		if(event.client_ParentClassPath.equalsIgnoreCase("ec3.client.gui.GuiAMINEjector"))
		{
			TileAMINEjector tile = (TileAMINEjector) event.presser.worldObj.getTileEntity(event.x, event.y, event.z);
			if(event.buttonID == 0)
				tile.slotnum += 1;
			else
				tile.slotnum -= 1;
			event.presser.openGui(EssentialCraftCore.core, Config.guiID[0], event.presser.worldObj, event.x, event.y, event.z);
		}
		if(event.client_ParentClassPath.equalsIgnoreCase("ec3.client.gui.GuiAMINInjector"))
		{
			TileAMINInjector tile = (TileAMINInjector) event.presser.worldObj.getTileEntity(event.x, event.y, event.z);
			if(event.buttonID == 0)
				tile.slotnum += 1;
			else
				tile.slotnum -= 1;
			event.presser.openGui(EssentialCraftCore.core, Config.guiID[0], event.presser.worldObj, event.x, event.y, event.z);
		}
		if(event.client_ParentClassPath.equalsIgnoreCase("ec3.client.gui.GuiMagicalAssembler") && event.buttonID == 0)
		{
			TileMagicalAssembler assembler = (TileMagicalAssembler) event.presser.worldObj.getTileEntity(event.x, event.y, event.z);
			int id = Integer.parseInt(event.additionalData[0].fieldValue);
			assembler.currentRecipe = id;
			assembler.formRequiredComponents();
		}
		if(event.client_ParentClassPath.equalsIgnoreCase("ec3.client.gui.GuiMagicalAssembler") && event.buttonID == 1)
		{
			TileMagicalAssembler assembler = (TileMagicalAssembler) event.presser.worldObj.getTileEntity(event.x, event.y, event.z);
    		assembler.actualRecipes.clear();
    		assembler.allRecipes.clear();
    		assembler.currentCraft = null;
    		assembler.currentRecipe = -1;
    		assembler.requiredItemsToCraft.clear();
    		assembler.currentSelectedOne = null;
			for(int i = 0; i < assembler.mirrors.size(); ++i)
			{
				Coord3D coord = assembler.mirrors.get(i);
				TileEntity tile = assembler.getWorldObj().getTileEntity((int)coord.x, (int)coord.y, (int)coord.z);
				if(tile == null || !(tile instanceof TileMagicalMirror))
					assembler.mirrors.remove(i);
				else
					((TileMagicalMirror) tile).end(assembler);
			}
    		if(assembler.getStackInSlot(1) != null)
    			assembler.formCraftList(assembler.getStackInSlot(1));
		}
	}
	
	@SubscribeEvent
	public void worldTick(WorldTickEvent event)
	{

		if(event.phase == Phase.END && event.world != null && event.world.provider.dimensionId == 0)
		{
			List<Object> hashTableIteratorLst = new ArrayList();
			
			Enumeration e = ECUtils.inPortalTime.keys();
			while(e.hasMoreElements())
			{
				Object o = e.nextElement();
				hashTableIteratorLst.add(o);
			}
			
			for(int i = 0; i < hashTableIteratorLst.size(); ++i)
			{
				int k = ECUtils.inPortalTime.get(hashTableIteratorLst.get(i));
				if(hashTableIteratorLst.get(i) instanceof Entity)
				{
					Entity en = (Entity) hashTableIteratorLst.get(i);
					if(!en.isInsideOfMaterial(Material.portal))
						k -= 4;
				}
				--k;
				if(k <= 0)
				{
					ECUtils.inPortalTime.remove(hashTableIteratorLst.get(i));
				}else
				{
					ECUtils.inPortalTime.put(hashTableIteratorLst.get(i), k);
				}
			}
			
			hashTableIteratorLst = null;
		}
		
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
					if(!(player instanceof FakePlayer))
						DummyDataUtils.setDataForPlayer(player.getDisplayName(), "essentialcraft", "ubmruEnergy", Integer.toString(currentEnergy_int));
				}else
				{
					if(!(player instanceof FakePlayer))
						DummyDataUtils.setDataForPlayer(player.getDisplayName(), "essentialcraft", "ubmruEnergy", Integer.toString(addedEnergy));
				}
			}
		}
		if(base instanceof EntityPlayer)
		{
			if(!(base instanceof FakePlayer))
				DummyDataUtils.setDataForPlayer(((EntityPlayer) base).getDisplayName(), "essentialcraft", "ubmruEnergy", "0");
		}
	}

	@SubscribeEvent
	public void onPacketRecieved(DummyEvent_OnPacketRecieved event)
	{
		
		DummyData[] packetData = DataStorage.parseData(event.recievedData);
		if(packetData != null && packetData.length > 0)
		{
			try 
			{
				DummyData modData = packetData[0];
				if(modData.fieldName.equalsIgnoreCase("mod") && modData.fieldValue.equalsIgnoreCase("ec3.particle.item"))
				{
					double sX = Double.parseDouble(packetData[1].fieldValue);
					double sY = Double.parseDouble(packetData[2].fieldValue);
					double sZ = Double.parseDouble(packetData[3].fieldValue);
					double mX = Double.parseDouble(packetData[4].fieldValue);
					double mY = Double.parseDouble(packetData[5].fieldValue);
					double mZ = Double.parseDouble(packetData[6].fieldValue);
					EssentialCraftCore.proxy.ItemFX(sX,sY,sZ,mX,mY,mZ);
				}else
				{
					if(modData.fieldName.equalsIgnoreCase("mod") && modData.fieldValue.equalsIgnoreCase("ec3.item.wings"))
					{
						double sX = Double.parseDouble(packetData[1].fieldValue);
						double sY = Double.parseDouble(packetData[2].fieldValue);
						double sZ = Double.parseDouble(packetData[3].fieldValue);
						EntityPlayer player = MinecraftServer.getServer().getConfigurationManager().func_152612_a(packetData[4].fieldValue);
						player.setPosition(sX, sY, sZ);
						ItemStack wings = BaublesApi.getBaubles(player).getStackInSlot(3);
						if(wings != null)
						{
							ItemMagicalWings w = (ItemMagicalWings) wings.getItem();
							if(ECUtils.tryToDecreaseMRUInStorage(player, -1) || w.setMRU(wings, -1));
						}
					}
				}
			}catch(Exception e)
			{
				e.printStackTrace();
				return;
			}
		}
	}
}
