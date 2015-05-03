package ec3.network.proxy;

import java.util.List;

import DummyCore.Client.GuiCommon;
import DummyCore.Client.MainMenuRegistry;
import DummyCore.Utils.DummyData;
import DummyCore.Utils.DummyPacketHandler;
import DummyCore.Utils.DummyPacketIMSG;
import DummyCore.Utils.MathUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.RenderSnowball;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.IIcon;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.biome.BiomeGenBase.TempCategory;
import net.minecraftforge.client.IRenderHandler;
import net.minecraftforge.client.MinecraftForgeClient;
import net.minecraftforge.common.MinecraftForge;
import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ec3.client.gui.GuiAMINEjector;
import ec3.client.gui.GuiAMINInjector;
import ec3.client.gui.GuiChargingChamber;
import ec3.client.gui.GuiColdDistillator;
import ec3.client.gui.GuiCorruptionCleaner;
import ec3.client.gui.GuiCrystalController;
import ec3.client.gui.GuiCrystalExtractor;
import ec3.client.gui.GuiCrystalFormer;
import ec3.client.gui.GuiDarknessObelisk;
import ec3.client.gui.GuiDemon;
import ec3.client.gui.GuiEnderGenerator;
import ec3.client.gui.GuiFilter;
import ec3.client.gui.GuiFlowerBurner;
import ec3.client.gui.GuiHeatGenerator;
import ec3.client.gui.GuiMIM;
import ec3.client.gui.GuiMINEjector;
import ec3.client.gui.GuiMINInjector;
import ec3.client.gui.GuiMRUAcceptor;
import ec3.client.gui.GuiMRUCoil;
import ec3.client.gui.GuiMRUInfo;
import ec3.client.gui.GuiMagicalAssembler;
import ec3.client.gui.GuiMagicalEnchanter;
import ec3.client.gui.GuiMagicalFurnace;
import ec3.client.gui.GuiMagicalJukebox;
import ec3.client.gui.GuiMagicalQuarry;
import ec3.client.gui.GuiMagicalRepairer;
import ec3.client.gui.GuiMagicalTeleporter;
import ec3.client.gui.GuiMagicianTable;
import ec3.client.gui.GuiMagmaticSmeltery;
import ec3.client.gui.GuiMainMenuEC3;
import ec3.client.gui.GuiMatrixAbsorber;
import ec3.client.gui.GuiMithrilineFurnace;
import ec3.client.gui.GuiMonsterHarvester;
import ec3.client.gui.GuiMonsterHolder;
import ec3.client.gui.GuiMoonWell;
import ec3.client.gui.GuiPlayerPentacle;
import ec3.client.gui.GuiPotionSpreader;
import ec3.client.gui.GuiRadiatingChamber;
import ec3.client.gui.GuiRayTower;
import ec3.client.gui.GuiResearchBook;
import ec3.client.gui.GuiRightClicker;
import ec3.client.gui.GuiSunRayAbsorber;
import ec3.client.gui.GuiUltraFlowerBurner;
import ec3.client.gui.GuiUltraHeatGenerator;
import ec3.client.gui.GuiWeaponBench;
import ec3.client.model.ModelArmorEC3;
import ec3.client.regular.EntityCSpellFX;
import ec3.client.regular.EntityColoredFlameFX;
import ec3.client.regular.EntityFogFX;
import ec3.client.regular.EntityItemFX;
import ec3.client.regular.EntityMRUFX;
import ec3.client.regular.RenderMRUArrow;
import ec3.client.render.ArmorRenderer;
import ec3.client.render.ClientRenderHandler;
import ec3.client.render.GunItemRenderHelper;
import ec3.client.render.RenderBlocksECIII;
import ec3.client.render.RenderChargingChamber;
import ec3.client.render.RenderCloudsFirstWorld;
import ec3.client.render.RenderColdDistillator;
import ec3.client.render.RenderCorruptionCleaner;
import ec3.client.render.RenderCrystalController;
import ec3.client.render.RenderCrystalExtractor;
import ec3.client.render.RenderCrystalFormer;
import ec3.client.render.RenderDarknessObelisk;
import ec3.client.render.RenderDemon;
import ec3.client.render.RenderDemonicPentacle;
import ec3.client.render.RenderElementalCrystal;
import ec3.client.render.RenderElementalCrystalAsItem;
import ec3.client.render.RenderEnderGenerator;
import ec3.client.render.RenderFlowerBurner;
import ec3.client.render.RenderHandlerEC3;
import ec3.client.render.RenderHeatGenerator;
import ec3.client.render.RenderMIM;
import ec3.client.render.RenderMINEjector;
import ec3.client.render.RenderMINInjector;
import ec3.client.render.RenderMRUCoil;
import ec3.client.render.RenderMRUCoilHardener;
import ec3.client.render.RenderMRULink;
import ec3.client.render.RenderMRUPresence;
import ec3.client.render.RenderMRURay;
import ec3.client.render.RenderMRUReactor;
import ec3.client.render.RenderMagicalAssembler;
import ec3.client.render.RenderMagicalDisplay;
import ec3.client.render.RenderMagicalEnchanter;
import ec3.client.render.RenderMagicalJukebox;
import ec3.client.render.RenderMagicalMirror;
import ec3.client.render.RenderMagicalQuarry;
import ec3.client.render.RenderMagicalRepairer;
import ec3.client.render.RenderMagicianTable;
import ec3.client.render.RenderMagmaticSmelter;
import ec3.client.render.RenderMatrixAbsorber;
import ec3.client.render.RenderMithrilineCrystal;
import ec3.client.render.RenderMithrilineFurnace;
import ec3.client.render.RenderMonsterHarvester;
import ec3.client.render.RenderMonsterHolder;
import ec3.client.render.RenderPlayerPentacle;
import ec3.client.render.RenderPoisonFume;
import ec3.client.render.RenderPotionSpreader;
import ec3.client.render.RenderRadiatingChamber;
import ec3.client.render.RenderRayTower;
import ec3.client.render.RenderSkyFirstWorld;
import ec3.client.render.RenderSolarBeam;
import ec3.client.render.RenderSolarPrism;
import ec3.client.render.RenderSolarPrismAsItem;
import ec3.client.render.RenderSunRayAbsorber;
import ec3.client.render.RenderUltraFlowerBurner;
import ec3.client.render.RenderUltraHeatGenerator;
import ec3.client.render.RenderWindMage;
import ec3.client.render.RenderWindRune;
import ec3.common.block.BlocksCore;
import ec3.common.entity.EntityDemon;
import ec3.common.entity.EntityMRUArrow;
import ec3.common.entity.EntityMRUPresence;
import ec3.common.entity.EntityMRURay;
import ec3.common.entity.EntityPoisonFume;
import ec3.common.entity.EntityShadowKnife;
import ec3.common.entity.EntitySolarBeam;
import ec3.common.entity.EntityWindMage;
import ec3.common.inventory.ContainerAMINEjector;
import ec3.common.inventory.ContainerAMINInjector;
import ec3.common.inventory.ContainerChargingChamber;
import ec3.common.inventory.ContainerColdDistillator;
import ec3.common.inventory.ContainerCorruptionCleaner;
import ec3.common.inventory.ContainerCrystalController;
import ec3.common.inventory.ContainerCrystalExtractor;
import ec3.common.inventory.ContainerCrystalFormer;
import ec3.common.inventory.ContainerDarknessObelisk;
import ec3.common.inventory.ContainerDemon;
import ec3.common.inventory.ContainerEnderGenerator;
import ec3.common.inventory.ContainerFilter;
import ec3.common.inventory.ContainerFlowerBurner;
import ec3.common.inventory.ContainerHeatGenerator;
import ec3.common.inventory.ContainerMIM;
import ec3.common.inventory.ContainerMINEjector;
import ec3.common.inventory.ContainerMINInjector;
import ec3.common.inventory.ContainerMRUAcceptor;
import ec3.common.inventory.ContainerMRUCoil;
import ec3.common.inventory.ContainerMRUInfo;
import ec3.common.inventory.ContainerMagicalAssembler;
import ec3.common.inventory.ContainerMagicalEnchanter;
import ec3.common.inventory.ContainerMagicalFurnace;
import ec3.common.inventory.ContainerMagicalHopper;
import ec3.common.inventory.ContainerMagicalJukebox;
import ec3.common.inventory.ContainerMagicalQuarry;
import ec3.common.inventory.ContainerMagicalRepairer;
import ec3.common.inventory.ContainerMagicalTeleporter;
import ec3.common.inventory.ContainerMagicianTable;
import ec3.common.inventory.ContainerMagmaticSmeltery;
import ec3.common.inventory.ContainerMatrixAbsorber;
import ec3.common.inventory.ContainerMithrilineFurnace;
import ec3.common.inventory.ContainerMonsterHarvester;
import ec3.common.inventory.ContainerMonsterHolder;
import ec3.common.inventory.ContainerMoonWell;
import ec3.common.inventory.ContainerPotionSpreader;
import ec3.common.inventory.ContainerRadiatingChamber;
import ec3.common.inventory.ContainerRayTower;
import ec3.common.inventory.ContainerRedstoneTransmitter;
import ec3.common.inventory.ContainerRightClicker;
import ec3.common.inventory.ContainerSunRayAbsorber;
import ec3.common.inventory.ContainerUltraFlowerBurner;
import ec3.common.inventory.ContainerUltraHeatGenerator;
import ec3.common.inventory.ContainerWeaponBench;
import ec3.common.inventory.InventoryMagicFilter;
import ec3.common.item.ItemSecret;
import ec3.common.item.ItemsCore;
import ec3.common.tile.TileAMINEjector;
import ec3.common.tile.TileAMINInjector;
import ec3.common.tile.TileChargingChamber;
import ec3.common.tile.TileColdDistillator;
import ec3.common.tile.TileCorruptionCleaner;
import ec3.common.tile.TileCrystalController;
import ec3.common.tile.TileCrystalExtractor;
import ec3.common.tile.TileCrystalFormer;
import ec3.common.tile.TileDarknessObelisk;
import ec3.common.tile.TileDemonicPentacle;
import ec3.common.tile.TileElementalCrystal;
import ec3.common.tile.TileEnderGenerator;
import ec3.common.tile.TileFlowerBurner;
import ec3.common.tile.TileHeatGenerator;
import ec3.common.tile.TileMIM;
import ec3.common.tile.TileMINEjector;
import ec3.common.tile.TileMINInjector;
import ec3.common.tile.TileMRUCoil;
import ec3.common.tile.TileMRUCoil_Hardener;
import ec3.common.tile.TileMRUReactor;
import ec3.common.tile.TileMagicalAssembler;
import ec3.common.tile.TileMagicalDisplay;
import ec3.common.tile.TileMagicalEnchanter;
import ec3.common.tile.TileMagicalFurnace;
import ec3.common.tile.TileMagicalHopper;
import ec3.common.tile.TileMagicalJukebox;
import ec3.common.tile.TileMagicalMirror;
import ec3.common.tile.TileMagicalQuarry;
import ec3.common.tile.TileMagicalRepairer;
import ec3.common.tile.TileMagicalTeleporter;
import ec3.common.tile.TileMagicianTable;
import ec3.common.tile.TileMagmaticSmelter;
import ec3.common.tile.TileMatrixAbsorber;
import ec3.common.tile.TileMithrilineCrystal;
import ec3.common.tile.TileMithrilineFurnace;
import ec3.common.tile.TileMonsterHarvester;
import ec3.common.tile.TileMonsterHolder;
import ec3.common.tile.TileMoonWell;
import ec3.common.tile.TilePlayerPentacle;
import ec3.common.tile.TilePotionSpreader;
import ec3.common.tile.TileRadiatingChamber;
import ec3.common.tile.TileRayTower;
import ec3.common.tile.TileRedstoneTransmitter;
import ec3.common.tile.TileRightClicker;
import ec3.common.tile.TileSolarPrism;
import ec3.common.tile.TileSunRayAbsorber;
import ec3.common.tile.TileUltraFlowerBurner;
import ec3.common.tile.TileUltraHeatGenerator;
import ec3.common.tile.TileWeaponMaker;
import ec3.common.tile.TileWindRune;
import ec3.common.tile.TileecAcceptor;
import ec3.common.tile.TileecStateChecker;
import ec3.utils.cfg.Config;

public class ClientProxy extends CommonProxy{
ResourceLocation villagerSkin = new ResourceLocation("essentialcraft","textures/entities/magician.png");
	@SuppressWarnings("unchecked")
	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world,int x, int y, int z) 
	{
		if(ID == Config.guiID[0])
		{
			TileEntity tile = world.getTileEntity(x, y, z);
			if(tile == null)
			{
				//Item:filter
				if(x == 0 && y == -1 && z == 0)
				{
					InventoryMagicFilter inventory = new InventoryMagicFilter(player.getCurrentEquippedItem());
					return new GuiFilter(new ContainerFilter(player, inventory), inventory);
				}
			}
			if(tile instanceof TileRayTower)
			{
				return new GuiRayTower(new ContainerRayTower(player.inventory, tile), tile);
			}
			if(tile instanceof TileecAcceptor)
			{
				return new GuiMRUAcceptor(new ContainerMRUAcceptor(player.inventory,tile), tile);
			}
			if(tile instanceof TileecStateChecker)
			{
				return new GuiMRUInfo(new ContainerMRUInfo(player.inventory,tile), tile);
			}
			if(tile instanceof TileMoonWell)
			{
				return new GuiMoonWell(new ContainerMoonWell(player.inventory, tile),tile);
			}
			if(tile instanceof TileSunRayAbsorber)
			{
				return new GuiSunRayAbsorber(new ContainerSunRayAbsorber(player.inventory, tile),tile);
			}
			if(tile instanceof TileColdDistillator)
			{
				return new GuiColdDistillator(new ContainerColdDistillator(player.inventory, tile),tile);
			}
			if(tile instanceof TileFlowerBurner)
			{
				return new GuiFlowerBurner(new ContainerFlowerBurner(player.inventory, tile),tile);
			}
			if(tile instanceof TileHeatGenerator)
			{
				return new GuiHeatGenerator(new ContainerHeatGenerator(player.inventory, tile),tile);
			}
			if(tile instanceof TileEnderGenerator)
			{
				return new GuiEnderGenerator(new ContainerEnderGenerator(player.inventory, tile),tile);
			}
			if(tile instanceof TileMagicianTable)
			{
				return new GuiMagicianTable(new ContainerMagicianTable(player.inventory, tile),tile);
			}
			if(tile instanceof TileMagicalQuarry)
			{
				return new GuiMagicalQuarry(new ContainerMagicalQuarry(player.inventory, tile),tile);
			}
			if(tile instanceof TileMonsterHolder)
			{
				return new GuiMonsterHolder(new ContainerMonsterHolder(player.inventory, tile),tile);
			}
			if(tile instanceof TilePotionSpreader)
			{
				return new GuiPotionSpreader(new ContainerPotionSpreader(player.inventory, tile),tile);
			}
			if(tile instanceof TileMagicalEnchanter)
			{
				return new GuiMagicalEnchanter(new ContainerMagicalEnchanter(player.inventory, tile),tile);
			}
			if(tile instanceof TileMonsterHarvester)
			{
				return new GuiMonsterHarvester(new ContainerMonsterHarvester(player.inventory, tile),tile);
			}
			if(tile instanceof TileMagicalRepairer)
			{
				return new GuiMagicalRepairer(new ContainerMagicalRepairer(player.inventory, tile),tile);
			}
			if(tile instanceof TileMatrixAbsorber)
			{
				return new GuiMatrixAbsorber(new ContainerMatrixAbsorber(player.inventory, tile),tile);
			}
			if(tile instanceof TileRadiatingChamber)
			{
				return new GuiRadiatingChamber(new ContainerRadiatingChamber(player.inventory, tile),tile);
			}
			if(tile instanceof TileMagmaticSmelter)
			{
				return new GuiMagmaticSmeltery(new ContainerMagmaticSmeltery(player.inventory, tile),tile);
			}
			if(tile instanceof TileMagicalJukebox)
			{
				return new GuiMagicalJukebox(new ContainerMagicalJukebox(player.inventory, tile),tile);
			}
			if(tile instanceof TileCrystalFormer)
			{
				return new GuiCrystalFormer(new ContainerCrystalFormer(player.inventory, tile),tile);
			}
			if(tile instanceof TileCrystalController)
			{
				return new GuiCrystalController(new ContainerCrystalController(player.inventory, tile),tile);
			}
			if(tile instanceof TileCrystalExtractor)
			{
				return new GuiCrystalExtractor(new ContainerCrystalExtractor(player.inventory, tile),tile);
			}
			if(tile instanceof TileChargingChamber)
			{
				return new GuiChargingChamber(new ContainerChargingChamber(player.inventory, tile),tile);
			}
			if(tile instanceof TileMagicalTeleporter)
			{
				return new GuiMagicalTeleporter(new ContainerMagicalTeleporter(player.inventory, tile),tile);
			}
			if(tile instanceof TileMagicalFurnace)
			{
				return new GuiMagicalFurnace(new ContainerMagicalFurnace(player.inventory, tile),tile);
			}
			if(tile instanceof TileMRUCoil)
			{
				return new GuiMRUCoil(new ContainerMRUCoil(player.inventory, tile),tile);
			}
			if(tile instanceof TileCorruptionCleaner)
			{
				return new GuiCorruptionCleaner(new ContainerCorruptionCleaner(player.inventory, tile),tile);
			}
			if(tile instanceof TileAMINEjector)
			{
				return new GuiAMINEjector(new ContainerAMINEjector(player.inventory, tile),tile);
			}
			if(tile instanceof TileMINEjector)
			{
				return new GuiMINEjector(new ContainerMINEjector(player.inventory, tile),tile);
			}
			if(tile instanceof TileAMINInjector)
			{
				return new GuiAMINInjector(new ContainerAMINInjector(player.inventory, tile),tile);
			}
			if(tile instanceof TileMINInjector)
			{
				return new GuiMINInjector(new ContainerMINInjector(player.inventory, tile),tile);
			}
			if(tile instanceof TileMIM)
			{
				return new GuiMIM(new ContainerMIM(player.inventory, tile),tile);
			}
			if(tile instanceof TileDarknessObelisk)
			{
				return new GuiDarknessObelisk(new ContainerDarknessObelisk(player.inventory, tile),tile);
			}
			if(tile instanceof TileUltraHeatGenerator)
			{
				return new GuiUltraHeatGenerator(new ContainerUltraHeatGenerator(player.inventory, tile),tile);
			}
			if(tile instanceof TileUltraFlowerBurner)
			{
				return new GuiUltraFlowerBurner(new ContainerUltraFlowerBurner(player.inventory, tile),tile);
			}
			if(tile instanceof TileMagicalAssembler)
			{
				return new GuiMagicalAssembler(new ContainerMagicalAssembler(player.inventory, tile),tile);
			}
			if(tile instanceof TileMithrilineFurnace)
			{
				return new GuiMithrilineFurnace(new ContainerMithrilineFurnace(player.inventory, tile),tile);
			}
			if(tile instanceof TileRightClicker)
			{
				return new GuiRightClicker(new ContainerRightClicker(player.inventory, tile),tile);
			}
			if(tile instanceof TileRedstoneTransmitter)
			{
				return new GuiCommon(new ContainerRedstoneTransmitter(player.inventory, tile),tile);
			}
			if(tile instanceof TileMagicalHopper)
			{
				return new GuiCommon(new ContainerMagicalHopper(player.inventory, tile),tile);
			}
			if(tile instanceof TileWeaponMaker)
			{
				return new GuiWeaponBench(new ContainerWeaponBench(player.inventory, tile),tile);
			}
			if(tile instanceof TilePlayerPentacle)
			{
				
				return new GuiPlayerPentacle(tile);
			}
		}
		if(ID == Config.guiID[1])
		{
			List<EntityDemon> demons = world.getEntitiesWithinAABB(EntityDemon.class, AxisAlignedBB.getBoundingBox(x-1, y-1, z-1, x+1, y+1, z+1));
			if(!demons.isEmpty())
			{
				return new GuiDemon(new ContainerDemon(player, demons.get(0)));
			}
		}
		return null;
	}
	
	@Override
	public void openBookGUIForPlayer()
	{
		Minecraft.getMinecraft().displayGuiScreen(new GuiResearchBook());
	}
	
	@Override
	public void openPentacleGUIForPlayer(TileEntity tile)
	{
		Minecraft.getMinecraft().displayGuiScreen(new GuiPlayerPentacle(tile));
	}

	@Override
	public void registerRenderInformation()
	{
		MainMenuRegistry.registerNewGui(GuiMainMenuEC3.class, "[EC3] Magical Menu", "For EC3 fans ;)");
		RenderingRegistry.registerEntityRenderingHandler(EntityMRUPresence.class, new RenderMRUPresence());
		RenderingRegistry.registerEntityRenderingHandler(EntityMRUArrow.class, new RenderMRUArrow());
		RenderingRegistry.registerEntityRenderingHandler(EntitySolarBeam.class, new RenderSolarBeam());
		RenderingRegistry.registerEntityRenderingHandler(EntityWindMage.class, new RenderWindMage());
		RenderingRegistry.registerEntityRenderingHandler(EntityPoisonFume.class, new RenderPoisonFume());
		RenderingRegistry.registerEntityRenderingHandler(EntityShadowKnife.class, new RenderSnowball(ItemsCore.shadeKnife));
		RenderingRegistry.registerEntityRenderingHandler(EntityMRURay.class, new RenderMRURay());
		RenderingRegistry.registerEntityRenderingHandler(EntityDemon.class, new RenderDemon());
		RenderingRegistry.registerBlockHandler(new RenderBlocksECIII());
		MinecraftForge.EVENT_BUS.register(new ClientRenderHandler());
		FMLCommonHandler.instance().bus().register(new RenderHandlerEC3());
		MinecraftForge.EVENT_BUS.register(new RenderHandlerEC3());
		MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(BlocksCore.elementalCrystal), new RenderElementalCrystalAsItem());
		MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(BlocksCore.solarPrism), new RenderSolarPrismAsItem());
		MinecraftForgeClient.registerItemRenderer(ItemsCore.pistol, new GunItemRenderHelper());
		MinecraftForgeClient.registerItemRenderer(ItemsCore.rifle, new GunItemRenderHelper());
		MinecraftForgeClient.registerItemRenderer(ItemsCore.sniper, new GunItemRenderHelper());
		MinecraftForgeClient.registerItemRenderer(ItemsCore.gatling, new GunItemRenderHelper());
		for(int i = 0; i < ItemsCore.magicArmorItems.length; ++i)
		{
			if(ItemsCore.magicArmorItems[i] != null)
				MinecraftForgeClient.registerItemRenderer(ItemsCore.magicArmorItems[i], new ArmorRenderer());
		}
		
	}
	
	@Override
	public void registerTileEntitySpecialRenderer()
	{
		ClientRegistry.bindTileEntitySpecialRenderer(TileRayTower.class, new RenderRayTower());
		ClientRegistry.bindTileEntitySpecialRenderer(TileecAcceptor.class, new RenderMRULink());
		ClientRegistry.bindTileEntitySpecialRenderer(TileSolarPrism.class, new RenderSolarPrism());
		ClientRegistry.bindTileEntitySpecialRenderer(TileSunRayAbsorber.class, new RenderSunRayAbsorber());
		ClientRegistry.bindTileEntitySpecialRenderer(TileColdDistillator.class, new RenderColdDistillator());
		ClientRegistry.bindTileEntitySpecialRenderer(TileFlowerBurner.class, new RenderFlowerBurner());
		ClientRegistry.bindTileEntitySpecialRenderer(TileHeatGenerator.class, new RenderHeatGenerator());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEnderGenerator.class, new RenderEnderGenerator());
		ClientRegistry.bindTileEntitySpecialRenderer(TileMagicianTable.class, new RenderMagicianTable());
		ClientRegistry.bindTileEntitySpecialRenderer(TileMagicalQuarry.class, new RenderMagicalQuarry());
		ClientRegistry.bindTileEntitySpecialRenderer(TileMonsterHolder.class, new RenderMonsterHolder());
		ClientRegistry.bindTileEntitySpecialRenderer(TilePotionSpreader.class, new RenderPotionSpreader());
		ClientRegistry.bindTileEntitySpecialRenderer(TileMagicalEnchanter.class, new RenderMagicalEnchanter());
		ClientRegistry.bindTileEntitySpecialRenderer(TileMonsterHarvester.class, new RenderMonsterHarvester());
		ClientRegistry.bindTileEntitySpecialRenderer(TileMagicalRepairer.class, new RenderMagicalRepairer());
		ClientRegistry.bindTileEntitySpecialRenderer(TileMatrixAbsorber.class, new RenderMatrixAbsorber());
		ClientRegistry.bindTileEntitySpecialRenderer(TileRadiatingChamber.class, new RenderRadiatingChamber());
		ClientRegistry.bindTileEntitySpecialRenderer(TileMagmaticSmelter.class, new RenderMagmaticSmelter());
		ClientRegistry.bindTileEntitySpecialRenderer(TileMagicalJukebox.class, new RenderMagicalJukebox());
		ClientRegistry.bindTileEntitySpecialRenderer(TileElementalCrystal.class, new RenderElementalCrystal());
		ClientRegistry.bindTileEntitySpecialRenderer(TileCrystalFormer.class, new RenderCrystalFormer());
		ClientRegistry.bindTileEntitySpecialRenderer(TileCrystalController.class, new RenderCrystalController());
		ClientRegistry.bindTileEntitySpecialRenderer(TileCrystalExtractor.class, new RenderCrystalExtractor());
		ClientRegistry.bindTileEntitySpecialRenderer(TileChargingChamber.class, new RenderChargingChamber());
		ClientRegistry.bindTileEntitySpecialRenderer(TileMRUCoil_Hardener.class, new RenderMRUCoilHardener());
		ClientRegistry.bindTileEntitySpecialRenderer(TileMRUCoil.class, new RenderMRUCoil());
		ClientRegistry.bindTileEntitySpecialRenderer(TileCorruptionCleaner.class, new RenderCorruptionCleaner());
		ClientRegistry.bindTileEntitySpecialRenderer(TileMRUReactor.class, new RenderMRUReactor());
		ClientRegistry.bindTileEntitySpecialRenderer(TileMINEjector.class, new RenderMINEjector());
		ClientRegistry.bindTileEntitySpecialRenderer(TileAMINEjector.class, new RenderMINEjector());
		ClientRegistry.bindTileEntitySpecialRenderer(TileMINInjector.class, new RenderMINInjector());
		ClientRegistry.bindTileEntitySpecialRenderer(TileAMINInjector.class, new RenderMINInjector());
		ClientRegistry.bindTileEntitySpecialRenderer(TileMIM.class, new RenderMIM());
		ClientRegistry.bindTileEntitySpecialRenderer(TileDarknessObelisk.class, new RenderDarknessObelisk());
		ClientRegistry.bindTileEntitySpecialRenderer(TileUltraHeatGenerator.class, new RenderUltraHeatGenerator());
		ClientRegistry.bindTileEntitySpecialRenderer(TileUltraFlowerBurner.class, new RenderUltraFlowerBurner());
		ClientRegistry.bindTileEntitySpecialRenderer(TileMagicalAssembler.class, new RenderMagicalAssembler());
		ClientRegistry.bindTileEntitySpecialRenderer(TileMagicalMirror.class, new RenderMagicalMirror());
		ClientRegistry.bindTileEntitySpecialRenderer(TileMagicalDisplay.class, new RenderMagicalDisplay());
		ClientRegistry.bindTileEntitySpecialRenderer(TileMithrilineCrystal.class, new RenderMithrilineCrystal());
		ClientRegistry.bindTileEntitySpecialRenderer(TileMithrilineFurnace.class, new RenderMithrilineFurnace());
		ClientRegistry.bindTileEntitySpecialRenderer(TilePlayerPentacle.class, new RenderPlayerPentacle());
		ClientRegistry.bindTileEntitySpecialRenderer(TileWindRune.class, new RenderWindRune());
		ClientRegistry.bindTileEntitySpecialRenderer(TileDemonicPentacle.class, new RenderDemonicPentacle());
	}
	
	@Override
	public World getClientWorld()
	{
		return FMLClientHandler.instance().getClient().theWorld;
	}
	
	@Override
	public Object getClientIcon(String str)
	{
		if(str.equals("mru"))
			return mruIcon;
		if(str.equals("chaosIcon"))
			return chaosIcon;
		if(str.equals("frozenIcon"))
			return frozenIcon;
		if(str.equals("mruParticleIcon"))
			return mruParticleIcon;
		if(str.equals("particle_fogFX"))
			return fogIcon;
		if(str.contains("consSpellParticle"))
		{
			int index = str.indexOf('_');
			if(index != -1)
			{
				int arrayNum = Integer.parseInt(str.substring(index+1));
				return c_spell_particle_array[arrayNum];
			}
		}
		return null;
	}
	
	@Override
	public void spawnParticle(String name, float x, float y, float z, double i, double j, double k)
	{
		if(name.equals("mruFX"))
			Minecraft.getMinecraft().effectRenderer.addEffect(new EntityMRUFX(getClientWorld(), x, y, z, i, j, k));
		if(name.equals("cSpellFX"))
			Minecraft.getMinecraft().effectRenderer.addEffect(new EntityCSpellFX(getClientWorld(), x, y, z, i, j, k));
		if(name.equals("fogFX"))
			Minecraft.getMinecraft().effectRenderer.addEffect(new EntityFogFX(getClientWorld(), x, y, z, i, j, k));
	}
	
	@SuppressWarnings("rawtypes")
	@Override
	public boolean itemHasEffect(ItemStack stk)
	{
		if(stk.getItem() instanceof ItemSecret)
		{
			int metadata = stk.getItemDamage();
			switch(metadata)
			{
				case 0:
				{
					EntityPlayer player = Minecraft.getMinecraft().thePlayer;
					World wrld = Minecraft.getMinecraft().theWorld;
					List playerLst = wrld.getEntitiesWithinAABB(EntityPlayer.class, AxisAlignedBB.getBoundingBox(player.posX-10, player.posY-10, player.posZ-10, player.posX+10, player.posY+10, player.posZ+10));
					BiomeGenBase biome = wrld.getBiomeGenForCoords((int)player.posX, (int)player.posY);
					return (wrld.getWorldTime() % 24000 >= 14000 && wrld.getWorldTime() % 24000 <= 16000) && (player.rotationPitch <= -42 && player.rotationPitch >= -65) && (playerLst.size() == 1) && (!wrld.isRaining() && (biome.getTempCategory() == TempCategory.WARM || biome.getTempCategory() == TempCategory.MEDIUM));
				}
			}
		}
		return false;
	}
	
	@Override
	public Object getClientModel(int id)
	{
		switch (id) 
		{
			case 0:
				return chest;
			case 1: 
				return legs;
			case 2:
				return chest1;
			default: break; 
		} 
		return chest; 
	}
	
	@Override
	public Object getRenderer(int index)
	{
		if(index == 0)
			return skyedRenderer;
		else
			return cloudedRenderer;
	}
	
	@Override
	public EntityPlayer getClientPlayer()
	{
		return Minecraft.getMinecraft().thePlayer;
	}
	
	@Override
	public void ItemFX(double... ds)
	{
		Minecraft.getMinecraft().effectRenderer.addEffect(new EntityItemFX(
				Minecraft.getMinecraft().theWorld, ds[0], ds[1], ds[2], 1, 0, 1, ds[3], ds[4], ds[5]
				));
	}
	
	@Override
	public void FlameFX(double... ds)
	{
		Minecraft.getMinecraft().effectRenderer.addEffect(new EntityColoredFlameFX(
				Minecraft.getMinecraft().theWorld, ds[0], ds[1], ds[2], ds[3], ds[4], ds[5], ds[6], ds[7], ds[8], ds[9]
				));
	}
	
	public void SmokeFX(double... ds)
	{
		if(ds.length == 7)
		{
			Minecraft.getMinecraft().effectRenderer.addEffect(new ec3.client.regular.SmokeFX(
					Minecraft.getMinecraft().theWorld, ds[0], ds[1], ds[2], ds[3], ds[4], ds[5], (float) ds[6]
					));
		}
		if(ds.length == 10)
		{
			Minecraft.getMinecraft().effectRenderer.addEffect(new ec3.client.regular.SmokeFX(
					Minecraft.getMinecraft().theWorld, ds[0], ds[1], ds[2], ds[3], ds[4], ds[5], (float) ds[6], ds[7], ds[8], ds[9]
					));
		}
	}
	
	@Override
	public void MRUFX(double... ds)
	{
		if(ds.length <= 6)
		{
			Minecraft.getMinecraft().effectRenderer.addEffect(new EntityMRUFX(getClientWorld(), ds[0], ds[1], ds[2], ds[3], ds[4], ds[5]));
		}else
			Minecraft.getMinecraft().effectRenderer.addEffect(new EntityMRUFX(getClientWorld(), ds[0], ds[1], ds[2], ds[3], ds[4], ds[5], ds[6], ds[7], ds[8]));
	}
	
	@Override
	public void wingsAction(EntityPlayer e, ItemStack s)
	{
		if(GameSettings.isKeyDown(Minecraft.getMinecraft().gameSettings.keyBindJump) && Minecraft.getMinecraft().inGameHasFocus)
		{
			e.worldObj.spawnParticle("reddust", e.posX+MathUtils.randomDouble(e.worldObj.rand)/2, e.posY-1+MathUtils.randomDouble(e.worldObj.rand), e.posZ+MathUtils.randomDouble(e.worldObj.rand)/2, 0, 1, 1);
			e.motionY += 0.1F;
			e.fallDistance = 0F;
			double pX = e.posX;
			double pY = e.posY;
			double pZ = e.posZ;
			String dataString = new String();
			dataString += "||mod:EC3.Item.Wings";
			dataString += "||x:"+pX+"||y:"+pY+"||z:"+pZ;
			dataString += "||playername:"+e.getCommandSenderName();
			DummyPacketIMSG pkt = new DummyPacketIMSG(dataString);
			DummyPacketHandler.sendToServer(pkt);
		}
	}
	
	@Override
	public void handlePositionChangePacket(DummyData[] packetData)
	{
		double sX = Double.parseDouble(packetData[1].fieldValue);
		double sY = Double.parseDouble(packetData[2].fieldValue);
		double sZ = Double.parseDouble(packetData[3].fieldValue);
		float yaw = Float.parseFloat(packetData[4].fieldValue);
		float pitch = Float.parseFloat(packetData[5].fieldValue);
		EntityPlayer player = Minecraft.getMinecraft().thePlayer;
		player.setPositionAndRotation(sX, sY, sZ,yaw,pitch);
		player.rotationYawHead = player.rotationYaw;
	}
	
	public void handleSoundPlay(DummyData[] packetData)
	{
		double sX = Double.parseDouble(packetData[1].fieldValue);
		double sY = Double.parseDouble(packetData[2].fieldValue);
		double sZ = Double.parseDouble(packetData[3].fieldValue);
		float volume = Float.parseFloat(packetData[4].fieldValue);
		float pitch = Float.parseFloat(packetData[5].fieldValue);
		String sound = packetData[6].fieldValue;
		EntityPlayer player = Minecraft.getMinecraft().thePlayer;
		player.worldObj.playSound(sX, sY, sZ, sound, volume, pitch, false);
	}
	
	public static IIcon mruIcon;
	public static IIcon mruParticleIcon;
	public static IIcon[] c_spell_particle_array = new IIcon[4];
	public static IIcon chaosIcon;
	public static IIcon frozenIcon;
	

	@SideOnly(Side.CLIENT)
	private static IRenderHandler skyedRenderer = new RenderSkyFirstWorld();
	
	@SideOnly(Side.CLIENT)
	private static IRenderHandler cloudedRenderer = new RenderCloudsFirstWorld();
	public static IIcon fogIcon;
	
	private static final ModelArmorEC3 chest = new ModelArmorEC3(1.0f);
	private static final ModelArmorEC3 chest1 = new ModelArmorEC3(0.75f);
	private static final ModelArmorEC3 legs = new ModelArmorEC3(0.5f);
}
