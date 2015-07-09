package ec3.network.proxy;

import java.util.List;

import DummyCore.Utils.DummyData;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;
import cpw.mods.fml.common.network.IGuiHandler;
import ec3.common.entity.EntityDemon;
import ec3.common.inventory.ContainerAMINEjector;
import ec3.common.inventory.ContainerAMINInjector;
import ec3.common.inventory.ContainerChargingChamber;
import ec3.common.inventory.ContainerColdDistillator;
import ec3.common.inventory.ContainerCorruptionCleaner;
import ec3.common.inventory.ContainerCrafter;
import ec3.common.inventory.ContainerCraftingFrame;
import ec3.common.inventory.ContainerCrystalController;
import ec3.common.inventory.ContainerCrystalExtractor;
import ec3.common.inventory.ContainerCrystalFormer;
import ec3.common.inventory.ContainerDarknessObelisk;
import ec3.common.inventory.ContainerDemon;
import ec3.common.inventory.ContainerEnderGenerator;
import ec3.common.inventory.ContainerFilter;
import ec3.common.inventory.ContainerFlowerBurner;
import ec3.common.inventory.ContainerFurnaceMagic;
import ec3.common.inventory.ContainerHeatGenerator;
import ec3.common.inventory.ContainerMIM;
import ec3.common.inventory.ContainerMIMCraftingManager;
import ec3.common.inventory.ContainerMIMInventoryStorage;
import ec3.common.inventory.ContainerMINEjector;
import ec3.common.inventory.ContainerMINInjector;
import ec3.common.inventory.ContainerMRUAcceptor;
import ec3.common.inventory.ContainerMRUCoil;
import ec3.common.inventory.ContainerMRUInfo;
import ec3.common.inventory.ContainerMagicalAssembler;
import ec3.common.inventory.ContainerMagicalChest;
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
import ec3.common.inventory.ContainerNewMIM;
import ec3.common.inventory.ContainerNewMIMScreen;
import ec3.common.inventory.ContainerNewMIMSimpleNode;
import ec3.common.inventory.ContainerPotionSpreader;
import ec3.common.inventory.ContainerRadiatingChamber;
import ec3.common.inventory.ContainerRayTower;
import ec3.common.inventory.ContainerRedstoneTransmitter;
import ec3.common.inventory.ContainerRightClicker;
import ec3.common.inventory.ContainerSunRayAbsorber;
import ec3.common.inventory.ContainerUltraFlowerBurner;
import ec3.common.inventory.ContainerUltraHeatGenerator;
import ec3.common.inventory.ContainerWeaponBench;
import ec3.common.inventory.InventoryCraftingFrame;
import ec3.common.inventory.InventoryMagicFilter;
import ec3.common.tile.TileAMINEjector;
import ec3.common.tile.TileAMINInjector;
import ec3.common.tile.TileAdvancedBlockBreaker;
import ec3.common.tile.TileAnimalSeparator;
import ec3.common.tile.TileChargingChamber;
import ec3.common.tile.TileColdDistillator;
import ec3.common.tile.TileCorruptionCleaner;
import ec3.common.tile.TileCrafter;
import ec3.common.tile.TileCrystalController;
import ec3.common.tile.TileCrystalExtractor;
import ec3.common.tile.TileCrystalFormer;
import ec3.common.tile.TileDarknessObelisk;
import ec3.common.tile.TileEnderGenerator;
import ec3.common.tile.TileFlowerBurner;
import ec3.common.tile.TileFurnaceMagic;
import ec3.common.tile.TileHeatGenerator;
import ec3.common.tile.TileMIM;
import ec3.common.tile.TileMINEjector;
import ec3.common.tile.TileMINInjector;
import ec3.common.tile.TileMRUCoil;
import ec3.common.tile.TileMagicalAssembler;
import ec3.common.tile.TileMagicalChest;
import ec3.common.tile.TileMagicalEnchanter;
import ec3.common.tile.TileMagicalFurnace;
import ec3.common.tile.TileMagicalHopper;
import ec3.common.tile.TileMagicalJukebox;
import ec3.common.tile.TileMagicalQuarry;
import ec3.common.tile.TileMagicalRepairer;
import ec3.common.tile.TileMagicalTeleporter;
import ec3.common.tile.TileMagicianTable;
import ec3.common.tile.TileMagmaticSmelter;
import ec3.common.tile.TileMatrixAbsorber;
import ec3.common.tile.TileMithrilineFurnace;
import ec3.common.tile.TileMonsterHarvester;
import ec3.common.tile.TileMonsterHolder;
import ec3.common.tile.TileMoonWell;
import ec3.common.tile.TileNewMIM;
import ec3.common.tile.TileNewMIMCraftingManager;
import ec3.common.tile.TileNewMIMExportNode;
import ec3.common.tile.TileNewMIMImportNode;
import ec3.common.tile.TileNewMIMInventoryStorage;
import ec3.common.tile.TileNewMIMScreen;
import ec3.common.tile.TilePotionSpreader;
import ec3.common.tile.TileRadiatingChamber;
import ec3.common.tile.TileRayTower;
import ec3.common.tile.TileRedstoneTransmitter;
import ec3.common.tile.TileRightClicker;
import ec3.common.tile.TileSunRayAbsorber;
import ec3.common.tile.TileUltraFlowerBurner;
import ec3.common.tile.TileUltraHeatGenerator;
import ec3.common.tile.TileWeaponMaker;
import ec3.common.tile.TileecAcceptor;
import ec3.common.tile.TileecStateChecker;
import ec3.utils.cfg.Config;

public class CommonProxy implements IGuiHandler{

	@SuppressWarnings("unchecked")
	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world,
			int x, int y, int z) {
		if(ID == Config.guiID[0])
		{
			TileEntity tile = world.getTileEntity(x, y, z);
			if(tile == null)
			{
				//Item:filter
				if(x == 0 && y == -1 && z == 0)
				{
					InventoryMagicFilter inventory = new InventoryMagicFilter(player.getCurrentEquippedItem());
					return new ContainerFilter(player, inventory);
				}
				//Item: Crafting Frame
				if(x == 0 && y == -2 && z == 0)
				{
					InventoryCraftingFrame inventory = new InventoryCraftingFrame(player.getCurrentEquippedItem());
					return new ContainerCraftingFrame(player, inventory);
				}
			}
			if(tile instanceof TileRayTower)
			{
				return new ContainerRayTower(player.inventory, tile);
			}
			if(tile instanceof TileecAcceptor)
			{
				return new ContainerMRUAcceptor(player.inventory, tile);
			}
			if(tile instanceof TileecStateChecker)
			{
				return new ContainerMRUInfo(player.inventory, tile);
			}
			if(tile instanceof TileMoonWell)
			{
				return new ContainerMoonWell(player.inventory, tile);
			}
			if(tile instanceof TileSunRayAbsorber)
			{
				return new ContainerSunRayAbsorber(player.inventory, tile);
			}
			if(tile instanceof TileColdDistillator)
			{
				return new ContainerColdDistillator(player.inventory, tile);
			}
			if(tile instanceof TileFlowerBurner)
			{
				return new ContainerFlowerBurner(player.inventory, tile);
			}
			if(tile instanceof TileHeatGenerator)
			{
				return new ContainerHeatGenerator(player.inventory, tile);
			}
			if(tile instanceof TileEnderGenerator)
			{
				return new ContainerEnderGenerator(player.inventory, tile);
			}
			if(tile instanceof TileMagicianTable)
			{
				return new ContainerMagicianTable(player.inventory, tile);
			}
			if(tile instanceof TileMagicalQuarry)
			{
				return new ContainerMagicalQuarry(player.inventory, tile);
			}
			if(tile instanceof TileMonsterHolder)
			{
				return new ContainerMonsterHolder(player.inventory, tile);
			}
			if(tile instanceof TilePotionSpreader)
			{
				return new ContainerPotionSpreader(player.inventory, tile);
			}
			if(tile instanceof TileMagicalEnchanter)
			{
				return new ContainerMagicalEnchanter(player.inventory, tile);
			}
			if(tile instanceof TileMonsterHarvester)
			{
				return new ContainerMonsterHarvester(player.inventory, tile);
			}
			if(tile instanceof TileMagicalRepairer)
			{
				return new ContainerMagicalRepairer(player.inventory, tile);
			}
			if(tile instanceof TileMatrixAbsorber)
			{
				return new ContainerMatrixAbsorber(player.inventory, tile);
			}
			if(tile instanceof TileRadiatingChamber)
			{
				return new ContainerRadiatingChamber(player.inventory, tile);
			}
			if(tile instanceof TileMagmaticSmelter)
			{
				return new ContainerMagmaticSmeltery(player.inventory, tile);
			}
			if(tile instanceof TileMagicalJukebox)
			{
				return new ContainerMagicalJukebox(player.inventory, tile);
			}
			if(tile instanceof TileCrystalFormer)
			{
				return new ContainerCrystalFormer(player.inventory, tile);
			}
			if(tile instanceof TileCrystalController)
			{
				return new ContainerCrystalController(player.inventory, tile);
			}
			if(tile instanceof TileCrystalExtractor)
			{
				return new ContainerCrystalExtractor(player.inventory, tile);
			}
			if(tile instanceof TileChargingChamber)
			{
				return new ContainerChargingChamber(player.inventory, tile);
			}
			if(tile instanceof TileMagicalTeleporter)
			{
				return new ContainerMagicalTeleporter(player.inventory, tile);
			}
			if(tile instanceof TileMagicalFurnace)
			{
				return new ContainerMagicalFurnace(player.inventory, tile);
			}
			if(tile instanceof TileMRUCoil)
			{
				return new ContainerMRUCoil(player.inventory, tile);
			}
			if(tile instanceof TileCorruptionCleaner)
			{
				return new ContainerCorruptionCleaner(player.inventory, tile);
			}
			if(tile instanceof TileAMINEjector)
			{
				return new ContainerAMINEjector(player.inventory, tile);
			}
			if(tile instanceof TileMINEjector)
			{
				return new ContainerMINEjector(player.inventory, tile);
			}
			if(tile instanceof TileAMINInjector)
			{
				return new ContainerAMINInjector(player.inventory, tile);
			}
			if(tile instanceof TileMINInjector)
			{
				return new ContainerMINInjector(player.inventory, tile);
			}
			if(tile instanceof TileMIM)
			{
				return new ContainerMIM(player.inventory, tile);
			}
			if(tile instanceof TileDarknessObelisk)
			{
				return new ContainerDarknessObelisk(player.inventory, tile);
			}
			if(tile instanceof TileUltraHeatGenerator)
			{
				return new ContainerUltraHeatGenerator(player.inventory, tile);
			}
			if(tile instanceof TileUltraFlowerBurner)
			{
				return new ContainerUltraFlowerBurner(player.inventory, tile);
			}
			if(tile instanceof TileMagicalAssembler)
			{
				return new ContainerMagicalAssembler(player.inventory, tile);
			}
			if(tile instanceof TileMithrilineFurnace)
			{
				return new ContainerMithrilineFurnace(player.inventory, tile);
			}
			if(tile instanceof TileRightClicker)
			{
				return new ContainerRightClicker(player.inventory, tile);
			}
			if(tile instanceof TileRedstoneTransmitter)
			{
				return new ContainerRedstoneTransmitter(player.inventory, tile);
			}
			if(tile instanceof TileMagicalHopper)
			{
				return new ContainerMagicalHopper(player.inventory, tile);
			}
			if(tile instanceof TileWeaponMaker)
			{
				return new ContainerWeaponBench(player.inventory, tile);
			}
			if(tile instanceof TileFurnaceMagic)
			{
				return new ContainerFurnaceMagic(player.inventory, tile);
			}
			if(tile instanceof TileMagicalChest)
			{
				return new ContainerMagicalChest(player.inventory, (TileMagicalChest) tile);
			}
			if(tile instanceof TileNewMIMInventoryStorage)
			{
				return new ContainerMIMInventoryStorage(player.inventory, (TileNewMIMInventoryStorage) tile);
			}
			if(tile instanceof TileNewMIM)
			{
				return new ContainerNewMIM(player.inventory, tile);
			}
			if(tile instanceof TileNewMIMScreen)
			{
				return new ContainerNewMIMScreen(player.inventory, tile);
			}
			if(tile instanceof TileNewMIMCraftingManager)
			{
				return new ContainerMIMCraftingManager(player.inventory, (TileNewMIMCraftingManager) tile);
			}
			if(tile instanceof TileNewMIMExportNode || tile instanceof TileNewMIMImportNode || tile instanceof TileAdvancedBlockBreaker)
			{
				return new ContainerNewMIMSimpleNode(player.inventory, tile);
			}
			if(tile instanceof TileCrafter)
			{
				return new ContainerCrafter(player.inventory, (TileCrafter) tile);
			}
			if(tile instanceof TileAnimalSeparator)
			{
				return new ContainerRayTower(player.inventory, tile);
			}
		}
		if(ID == Config.guiID[1])
		{
			List<EntityDemon> demons = world.getEntitiesWithinAABB(EntityDemon.class, AxisAlignedBB.getBoundingBox(x-1, y-1, z-1, x+1, y+1, z+1));
			if(!demons.isEmpty())
			{
				return new ContainerDemon(player, demons.get(0));
			}
		}
		return null;
	}
	
	public void openBookGUIForPlayer()
	{
		
	}
	
	public void openPentacleGUIForPlayer(TileEntity tile)
	{
		
	}
	
	public Object getClientVoidChestGUI(EntityPlayer player, World world, int x, int y, int z, int page)
	{
		return null;
	}
	
	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world,
			int x, int y, int z) {
		// TODO Auto-generated method stub
		return null;
	}

	public void registerRenderInformation() 
	{
		
	}
	
	public void registerTileEntitySpecialRenderer()
	{
		
	}
	
	public World getClientWorld()
	{
		return null;
	}
	
	public Object getClientIcon(String iconName)
	{
		return null;
	}
	
	public void spawnParticle(String name, float x, float y, float z, double i, double j, double k)
	{
		
	}
	
	public boolean itemHasEffect(ItemStack stk)
	{
		return false;
	}
	
	public Object getClientModel(int id)
	{
		return null;
	}
	
	public Object getRenderer(int index)
	{
		return null;
	}
	
	public EntityPlayer getClientPlayer()
	{
		return null;
	}
	
	public void ItemFX(double... ds)
	{
		
	}
	
	public void FlameFX(double... ds)
	{
		
	}
	
	public void SmokeFX(double... ds)
	{
		
	}
	
	public void MRUFX(double... ds)
	{
		
	}
	
	public void wingsAction(EntityPlayer e, ItemStack s)
	{
		
	}
	
	public void handlePositionChangePacket(DummyData[] packetData)
	{
		
	}
	
	public void handleSoundPlay(DummyData[] packetData)
	{
		
	}

	public void stopSound(String soundID) {
		
	}

	public void startSound(String soundID, String soundName) {
	}
}
