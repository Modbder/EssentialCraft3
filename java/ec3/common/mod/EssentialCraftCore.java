package ec3.common.mod;


import net.minecraft.block.Block;
import net.minecraft.command.CommandHandler;
import net.minecraft.server.MinecraftServer;
import DummyCore.Core.Core;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;
import ec3.common.block.BlocksCore;
import ec3.common.entity.EntitiesCore;
import ec3.common.item.ItemsCore;
import ec3.common.registry.AchievementRegistry;
import ec3.common.registry.BiomeRegistry;
import ec3.common.registry.CoreRegistry;
import ec3.common.registry.DimensionRegistry;
import ec3.common.registry.EnchantRegistry;
import ec3.common.registry.RecipeRegistry;
import ec3.common.registry.SpellRegistry;
import ec3.common.registry.StructureRegistry;
import ec3.common.registry.VillagersRegistry;
import ec3.network.proxy.CommonProxy;
import ec3.utils.cfg.Config;
import ec3.utils.common.CommandCreateMRUCU;
import ec3.utils.common.CommandSetBalance;
import ec3.utils.common.CommandSetMRU;

@Mod(modid = "essentialcraft", name = "EssentialCraftIII", version = "4.0.1710.421")
public class EssentialCraftCore {

//============================================CORE START=================================================//
	
//============================================CORE VARS==================================================//
	//TODO Variables
	public static EssentialCraftCore core;
	@SidedProxy(clientSide = "ec3.network.proxy.ClientProxy", serverSide = "ec3.network.proxy.CommonProxy")
	public static CommonProxy proxy;
	public static Config cfg = new Config();
	public static String version = "4.0.1710.421";
	public static int globalECVersion;
	public static int modVersion;
	public static int mcVersion;
	public static int buildVersion;
//============================================CORE FUNCTIONS=============================================//
	//TODO Functions
	public boolean renderMRUPresenceWithoutMonocle() {
		// TODO Auto-generated method stub
		return true;
	}
//============================================CORE MOD===================================================//
	//TODO Mod
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
		core = this;
		try
		{
			Core.registerModAbsolute(getClass(), "Essential Craft 3", event.getModConfigurationDirectory().getAbsolutePath(), cfg);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return;
		}
		CoreRegistry.register();
	}
	
	@EventHandler
	public void onMinecraftLoading(FMLInitializationEvent event)
	{
		DimensionRegistry.core.registerDimensionMagic();
		BlocksCore.instance.loadBlocks();
		ItemsCore.instance.loadItems();
		RecipeRegistry.instance.main();
		EntitiesCore.registerEntities();
		EnchantRegistry.register();
		VillagersRegistry.instance.register();
		BiomeRegistry.core.register();
		StructureRegistry.register();
		proxy.registerRenderInformation();
		proxy.registerTileEntitySpecialRenderer();
		
		
	}
	
	@EventHandler
	public void onMinecraftLoadingFinished(FMLPostInitializationEvent event)
	{
		SpellRegistry.register();
		AchievementRegistry.register();
	}
}
