package ec3.common.registry;

import static ec3.utils.common.ECUtils.allowedBlocks;

import java.util.ArrayList;
import java.util.List;

import ec3.api.EnumStructureType;
import ec3.common.block.BlocksCore;
import ec3.common.world.structure.MapGenTown;
import ec3.common.world.structure.StructureTownPieces;
import ec3.utils.common.ECUtils;
import net.minecraft.block.Block;
import net.minecraft.world.gen.structure.MapGenStructureIO;

public class StructureRegistry {
	
	public static void register()
	{
		MapGenStructureIO.registerStructure(MapGenTown.Start.class, "ec3.Town");
		StructureTownPieces.registerVillagePieces();
		List<Block> structureBlocks_mrucucc = new ArrayList();
		structureBlocks_mrucucc.add(BlocksCore.fortifiedGlass);
		structureBlocks_mrucucc.add(BlocksCore.magicPlating);
		structureBlocks_mrucucc.add(BlocksCore.ecController);
		structureBlocks_mrucucc.add(BlocksCore.ecAcceptor);
		structureBlocks_mrucucc.add(BlocksCore.ecBalancer);
		structureBlocks_mrucucc.add(BlocksCore.ecEjector);
		structureBlocks_mrucucc.add(BlocksCore.ecHoldingChamber);
		structureBlocks_mrucucc.add(BlocksCore.ecRedstoneController);
		structureBlocks_mrucucc.add(BlocksCore.ecStateChecker);
		structureBlocks_mrucucc.add(BlocksCore.fortifiedStone);
		structureBlocks_mrucucc.add(BlocksCore.voidGlass);
		structureBlocks_mrucucc.add(BlocksCore.voidStone);
		allowedBlocks.put(EnumStructureType.MRUCUContaigementChamber, structureBlocks_mrucucc);
		
		ECUtils.registerBlockResistance(BlocksCore.fortifiedGlass, 0, 3F);
		ECUtils.registerBlockResistance(BlocksCore.magicPlating, 0, 5F);
		ECUtils.registerBlockResistance(BlocksCore.ecController, 0, 100F);
		ECUtils.registerBlockResistance(BlocksCore.ecAcceptor, 0, 100F);
		ECUtils.registerBlockResistance(BlocksCore.ecBalancer, 0, 100F);
		ECUtils.registerBlockResistance(BlocksCore.ecEjector, 0, 100F);
		ECUtils.registerBlockResistance(BlocksCore.ecHoldingChamber, 0, 100F);
		ECUtils.registerBlockResistance(BlocksCore.ecRedstoneController, 0, 100F);
		ECUtils.registerBlockResistance(BlocksCore.ecStateChecker, 0, 100F);
		ECUtils.registerBlockResistance(BlocksCore.fortifiedStone, -1, 2F);
		ECUtils.registerBlockResistance(BlocksCore.voidGlass, -1, 15F);
		ECUtils.registerBlockResistance(BlocksCore.voidStone, -1, 18F);
	}

}
