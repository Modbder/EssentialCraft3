package ec3.common.registry;

import DummyCore.Client.MainMenuRegistry;
import net.minecraftforge.common.MinecraftForge;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import ec3.api.WorldEventLibrary;
import ec3.client.gui.GuiMainMenuEC3;
import ec3.common.block.BlocksCore;
import ec3.common.item.ItemsCore;
import ec3.common.mod.EssentialCraftCore;
import ec3.common.tile.TileColdDistillator;
import ec3.common.tile.TileCorruption;
import ec3.common.tile.TileEnderGenerator;
import ec3.common.tile.TileFlowerBurner;
import ec3.common.tile.TileHeatGenerator;
import ec3.common.tile.TileMoonWell;
import ec3.common.tile.TileRayTower;
import ec3.common.tile.TileSolarPrism;
import ec3.common.tile.TileSunRayAbsorber;
import ec3.common.tile.TileecAcceptor;
import ec3.common.tile.TileecBalancer;
import ec3.common.tile.TileecController;
import ec3.common.tile.TileecEjector;
import ec3.common.tile.TileecHoldingChamber;
import ec3.common.tile.TileecRedstoneController;
import ec3.common.tile.TileecStateChecker;
import ec3.common.world.event.WorldEvent_Darkness;
import ec3.common.world.event.WorldEvent_Earthquake;
import ec3.common.world.event.WorldEvent_Fumes;
import ec3.common.world.event.WorldEvent_SunArray;
import ec3.utils.cfg.Config;
import ec3.utils.common.ECEventHandler;
import ec3.utils.common.PlayerTickHandler;
import ec3.utils.common.PlayerTracker;

public class CoreRegistry {
	
	public static CoreRegistry instance;
	
	public static void register()
	{
		Config.instance = new Config();
		BlocksCore.instance = new BlocksCore();
		ItemsCore.instance = new ItemsCore();
		TileRegistry.register();
		DimensionRegistry.core = new DimensionRegistry();
		VillagersRegistry.instance = new VillagersRegistry();
		NetworkRegistry.INSTANCE.registerGuiHandler(EssentialCraftCore.core, EssentialCraftCore.core.proxy);
		RecipeRegistry.instance = new RecipeRegistry();
		BiomeRegistry.core = new BiomeRegistry();
		//MagicalEnergiserRecipes.smeltingBase = new MagicalEnergiserRecipes();
		//MagicianTableRecipes.smeltingBase = new MagicianTableRecipes();
		//TickRegistry.registerTickHandler(new PlayerTick(), Side.SERVER);
		//MinecraftForge.EVENT_BUS.register(new ForgeEventHandlerECII());
		MinecraftForge.EVENT_BUS.register(new PlayerTickHandler());
		MinecraftForge.EVENT_BUS.register(new ECEventHandler());
		FMLCommonHandler.instance().bus().register(new ECEventHandler());
		MinecraftForge.EVENT_BUS.register(new PlayerTracker());
		//GameRegistry.registerPlayerTracker(new PlayerTrackerECII());
		
		
		WorldEventLibrary.registerWorldEvent(new WorldEvent_SunArray());
		WorldEventLibrary.registerWorldEvent(new WorldEvent_Fumes());
		WorldEventLibrary.registerWorldEvent(new WorldEvent_Darkness());
		WorldEventLibrary.registerWorldEvent(new WorldEvent_Earthquake());
	}

}
