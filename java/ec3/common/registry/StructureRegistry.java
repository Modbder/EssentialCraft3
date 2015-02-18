package ec3.common.registry;

import static ec3.utils.common.ECUtils.allowedBlocks;

import java.util.ArrayList;
import java.util.List;

import ec3.api.EnumStructureType;
import ec3.common.block.BlocksCore;
import ec3.common.world.structure.MapGenModernShafts;
import ec3.common.world.structure.MapGenTown;
import ec3.common.world.structure.StructureModernShaftPieces;
import ec3.common.world.structure.StructureModernShaftStart;
import ec3.common.world.structure.StructureTownPieces;
import ec3.utils.common.ECUtils;
import net.minecraft.block.Block;
import net.minecraft.world.gen.structure.MapGenStructureIO;

public class StructureRegistry {
	
	public static void register()
	{
		MapGenStructureIO.registerStructure(MapGenTown.Start.class, "ec3.Town");
		MapGenStructureIO.registerStructure(StructureModernShaftStart.class, "ec3.ModernShafts");
		StructureTownPieces.registerVillagePieces();
		StructureModernShaftPieces.registerStructurePieces();
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
		structureBlocks_mrucucc.add(BlocksCore.platingPale);
		structureBlocks_mrucucc.add(BlocksCore.fancyBlocks.get(0));
		structureBlocks_mrucucc.add(BlocksCore.fancyBlocks.get(2));
		structureBlocks_mrucucc.add(BlocksCore.fancyBlocks.get(4));
		structureBlocks_mrucucc.add(BlocksCore.fancyBlocks.get(5));
		structureBlocks_mrucucc.add(BlocksCore.fancyBlocks.get(6));
		allowedBlocks.put(EnumStructureType.MRUCUContaigementChamber, structureBlocks_mrucucc);
		
		List<Block> structureBlocks_mrucoil = new ArrayList();
		structureBlocks_mrucoil.add(BlocksCore.platingPale);
		structureBlocks_mrucoil.add(BlocksCore.magicPlating);
		structureBlocks_mrucoil.add(BlocksCore.voidStone);
		structureBlocks_mrucoil.add(BlocksCore.fancyBlocks.get(4));
		structureBlocks_mrucoil.add(BlocksCore.fancyBlocks.get(5));
		structureBlocks_mrucoil.add(BlocksCore.fancyBlocks.get(6));
		
		allowedBlocks.put(EnumStructureType.MRUCoil, structureBlocks_mrucoil);
		
		ECUtils.registerBlockResistance(BlocksCore.fortifiedGlass, 0, 3F);
		ECUtils.registerBlockResistance(BlocksCore.fancyBlocks.get(0), -1, 8F);
		ECUtils.registerBlockResistance(BlocksCore.fancyBlocks.get(2), -1, 3F);
		ECUtils.registerBlockResistance(BlocksCore.fancyBlocks.get(4), -1, 5F);
		ECUtils.registerBlockResistance(BlocksCore.fancyBlocks.get(5), -1, 10F);
		ECUtils.registerBlockResistance(BlocksCore.fancyBlocks.get(6), -1, 18F);
		
		ECUtils.registerBlockResistance(BlocksCore.magicPlating, 0, 5F);
		ECUtils.registerBlockResistance(BlocksCore.platingPale, 0, 10F);
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
