package ec3.common.registry;

import cpw.mods.fml.common.registry.GameRegistry;
import ec3.common.tile.TileChargingChamber;
import ec3.common.tile.TileColdDistillator;
import ec3.common.tile.TileCorruption;
import ec3.common.tile.TileCrystalController;
import ec3.common.tile.TileCrystalExtractor;
import ec3.common.tile.TileCrystalFormer;
import ec3.common.tile.TileElementalCrystal;
import ec3.common.tile.TileEnderGenerator;
import ec3.common.tile.TileFlowerBurner;
import ec3.common.tile.TileHeatGenerator;
import ec3.common.tile.TileMagicalEnchanter;
import ec3.common.tile.TileMagicalJukebox;
import ec3.common.tile.TileMagicalQuarry;
import ec3.common.tile.TileMagicalRepairer;
import ec3.common.tile.TileMagicianTable;
import ec3.common.tile.TileMagmaticSmelter;
import ec3.common.tile.TileMatrixAbsorber;
import ec3.common.tile.TileMonsterHarvester;
import ec3.common.tile.TileMonsterHolder;
import ec3.common.tile.TileMoonWell;
import ec3.common.tile.TilePotionSpreader;
import ec3.common.tile.TileRadiatingChamber;
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
import net.minecraft.tileentity.TileEntity;

public class TileRegistry {
	
	public static void register()
	{
		addTileToMapping(TileecController.class);
		addTileToMapping(TileecAcceptor.class);
		addTileToMapping(TileecBalancer.class);
		addTileToMapping(TileecEjector.class);
		addTileToMapping(TileecHoldingChamber.class);
		addTileToMapping(TileecRedstoneController.class);
		addTileToMapping(TileecStateChecker.class);
		addTileToMapping(TileRayTower.class);
		addTileToMapping(TileCorruption.class);
		addTileToMapping(TileMoonWell.class);
		addTileToMapping(TileSolarPrism.class);
		addTileToMapping(TileSunRayAbsorber.class);
		addTileToMapping(TileColdDistillator.class);
		addTileToMapping(TileFlowerBurner.class);
		addTileToMapping(TileHeatGenerator.class);
		addTileToMapping(TileEnderGenerator.class);
		addTileToMapping(TileMagicianTable.class);
		addTileToMapping(TileMagicalQuarry.class);
		addTileToMapping(TileMonsterHolder.class);
		addTileToMapping(TilePotionSpreader.class);
		addTileToMapping(TileMagicalEnchanter.class);
		addTileToMapping(TileMonsterHarvester.class);
		addTileToMapping(TileMagicalRepairer.class);
		addTileToMapping(TileMatrixAbsorber.class);
		addTileToMapping(TileRadiatingChamber.class);
		addTileToMapping(TileMagmaticSmelter.class);
		addTileToMapping(TileMagicalJukebox.class);
		addTileToMapping(TileElementalCrystal.class);
		addTileToMapping(TileCrystalFormer.class);
		addTileToMapping(TileCrystalController.class);
		addTileToMapping(TileCrystalExtractor.class);
		addTileToMapping(TileChargingChamber.class);
	}
	
	public static void addTileToMapping(Class<? extends TileEntity> tile)
	{
		GameRegistry.registerTileEntity(tile, "ec3:"+tile.getCanonicalName());
	}

}
