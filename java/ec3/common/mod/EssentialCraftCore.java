package ec3.common.mod;


import java.util.Arrays;

import net.minecraft.command.CommandHandler;
import net.minecraft.server.MinecraftServer;
import DummyCore.Core.Core;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.ModMetadata;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;
import cpw.mods.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import cpw.mods.fml.common.registry.GameRegistry;
import ec3.common.block.BlocksCore;
import ec3.common.entity.EntitiesCore;
import ec3.common.item.ItemsCore;
import ec3.common.registry.AchievementRegistry;
import ec3.common.registry.BiomeRegistry;
import ec3.common.registry.BloodMagicRegistry;
import ec3.common.registry.CERegistry;
import ec3.common.registry.CoreRegistry;
import ec3.common.registry.DimensionRegistry;
import ec3.common.registry.EnchantRegistry;
import ec3.common.registry.GunInitialization;
import ec3.common.registry.PotionRegistry;
import ec3.common.registry.RecipeRegistry;
import ec3.common.registry.ResearchRegistry;
import ec3.common.registry.StructureRegistry;
import ec3.common.registry.VillagersRegistry;
import ec3.common.world.WorldGenManager;
import ec3.integration.rotarycraft.RCLoadingHandler;
import ec3.integration.versionChecker.Check;
import ec3.integration.waila.WailaInitialiser;
import ec3.network.proxy.CommonProxy;
import ec3.utils.cfg.Config;
import ec3.utils.common.CommandCreateMRUCU;
import ec3.utils.common.CommandSetBalance;
import ec3.utils.common.CommandSetMRU;

@Mod(
		modid = EssentialCraftCore.modid,
		name = "EssentialCraftIII",
		version = EssentialCraftCore.version,
		dependencies = "required-after:DummyCore@[1.12,);",
		guiFactory = "ec3.client.regular.ModConfigGuiHandler"
	)
public class EssentialCraftCore {

//============================================CORE START=================================================//
	
//============================================CORE VARS==================================================//
	@Instance(EssentialCraftCore.modid)
	public static EssentialCraftCore core;
	@SidedProxy(clientSide = "ec3.network.proxy.ClientProxy", serverSide = "ec3.network.proxy.CommonProxy", modId = EssentialCraftCore.modid)
	public static CommonProxy proxy;
	public static Config cfg = new Config();
	//TODO Do not forget to change the version number every git commit.
	public static final String version = "4.6.1710.42";
	public static final String modid = "essentialcraft";
	public static ModMetadata metadata;
	public static SimpleNetworkWrapper network;
//============================================CORE FUNCTIONS=============================================//

	
//============================================CORE MOD===================================================//
	@EventHandler
	public void serverStart(FMLServerStartingEvent event)
    {
        MinecraftServer mcserver = event.getServer();
        ((CommandHandler)mcserver.getCommandManager()).registerCommand(new CommandSetMRU());
        ((CommandHandler)mcserver.getCommandManager()).registerCommand(new CommandSetBalance());
        ((CommandHandler)mcserver.getCommandManager()).registerCommand(new CommandCreateMRUCU());
        
    }
	
	@EventHandler
	public void beforeMinecraftLoaded(FMLPreInitializationEvent event)
	{
		metadata = event.getModMetadata();
		
		core = this;
		try
		{
			Core.registerModAbsolute(getClass(), "Essential Craft 3", event.getModConfigurationDirectory().getAbsolutePath(), cfg);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		Check.checkerCommit();
		WailaInitialiser.sendIMC();
		RCLoadingHandler.runPreInitChecks();
	}
	
	@EventHandler
	public void onMinecraftLoading(FMLInitializationEvent event)
	{
		if(core == null)
			core = this;
		CoreRegistry.register();
		
		if(DimensionRegistry.core != null)
			DimensionRegistry.core.registerDimensionMagic();
		else
		{
			DimensionRegistry.core = new DimensionRegistry();
			DimensionRegistry.core.registerDimensionMagic();
		}
		if(BlocksCore.instance != null)
			BlocksCore.instance.loadBlocks();
		else
		{
			BlocksCore.instance = new BlocksCore();
			BlocksCore.instance.loadBlocks();
		}
		if(ItemsCore.instance != null)
			ItemsCore.instance.loadItems();
		else
		{
			ItemsCore.instance = new ItemsCore();
			ItemsCore.instance.loadItems();
		}
		if(RecipeRegistry.instance != null)
			RecipeRegistry.instance.main();
		else
		{
			RecipeRegistry.instance = new RecipeRegistry();
			RecipeRegistry.instance.main();
		}
		
		EnchantRegistry.register();
		if(VillagersRegistry.instance != null)
			VillagersRegistry.instance.register();
		
		if(BiomeRegistry.core != null)
			BiomeRegistry.core.register();
		else
		{
			BiomeRegistry.core = new BiomeRegistry();
			BiomeRegistry.core.register();
		}
		BlocksCore.postInitLoad();
		StructureRegistry.register();
		if(proxy != null)
		{
			proxy.registerRenderInformation();
			proxy.registerTileEntitySpecialRenderer();
		}else
		{
			
		}
		GunInitialization.register();
		CERegistry.register();
	}
	
	public static boolean clazzExists(String clazzName)
	{
		try
		{
			Class<?> clazz = Class.forName(clazzName);
			return clazz != null;
		}
		catch(Exception e)
		{
			return false;
		}
	}
	
	@EventHandler
	public void onMinecraftLoadingFinished(FMLPostInitializationEvent event)
	{
		EntitiesCore.registerEntities();
		BloodMagicRegistry.register();
		AchievementRegistry.register();
		PotionRegistry.registerPotions();
		GameRegistry.registerWorldGenerator(new WorldGenManager(), 16);
		cfg.postInitParseDecorativeBlocks();
		ResearchRegistry.init();
		
		metadata.autogenerated=false;
		metadata.modId=modid;
		metadata.version=version;
		metadata.name="Essential Craft 3";
		metadata.credits="Author: Modbder";
		metadata.authorList=Arrays.asList(new String[]{"Modbder"});
		metadata.description="Essential Craft 3 is a huge magic-themed mod, that adds lots of end-game content.";
		metadata.url="http://www.minecraftforum.net/forums/mapping-and-modding/minecraft-mods/2286105-1-7-10-forge-open-source-dummythinking-mods";
		metadata.updateUrl="http://www.minecraftforum.net/forums/mapping-and-modding/minecraft-mods/2286105-1-7-10-forge-open-source-dummythinking-mods";
		metadata.logoFile="assets/essentialcraft/textures/special/logo.png";
	}
}
