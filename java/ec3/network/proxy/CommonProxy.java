package ec3.network.proxy;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.util.List;

import DummyCore.Utils.DataStorage;
import DummyCore.Utils.DummyData;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.server.MinecraftServer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;
import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.common.network.IGuiHandler;
import ec3.client.gui.GuiChargingChamber;
import ec3.client.gui.GuiColdDistillator;
import ec3.client.gui.GuiCrystalExtractor;
import ec3.client.gui.GuiCrystalFormer;
import ec3.client.gui.GuiEnderGenerator;
import ec3.client.gui.GuiFlowerBurner;
import ec3.client.gui.GuiHeatGenerator;
import ec3.client.gui.GuiMagicalEnchanter;
import ec3.client.gui.GuiMagicalJukebox;
import ec3.client.gui.GuiMagicalQuarry;
import ec3.client.gui.GuiMagicalRepairer;
import ec3.client.gui.GuiMagicianTable;
import ec3.client.gui.GuiMagmaticSmeltery;
import ec3.client.gui.GuiMatrixAbsorber;
import ec3.client.gui.GuiMonsterHarvester;
import ec3.client.gui.GuiMonsterHolder;
import ec3.client.gui.GuiPotionSpreader;
import ec3.client.gui.GuiRadiatingChamber;
import ec3.client.gui.GuiSunRayAbsorber;
import ec3.client.render.RenderMRUPresence;
import ec3.common.entity.EntityMRUPresence;
import ec3.common.inventory.ContainerChargingChamber;
import ec3.common.inventory.ContainerColdDistillator;
import ec3.common.inventory.ContainerCrystalController;
import ec3.common.inventory.ContainerCrystalExtractor;
import ec3.common.inventory.ContainerCrystalFormer;
import ec3.common.inventory.ContainerEnderGenerator;
import ec3.common.inventory.ContainerFlowerBurner;
import ec3.common.inventory.ContainerHeatGenerator;
import ec3.common.inventory.ContainerMRUAcceptor;
import ec3.common.inventory.ContainerMRUInfo;
import ec3.common.inventory.ContainerMagicalEnchanter;
import ec3.common.inventory.ContainerMagicalJukebox;
import ec3.common.inventory.ContainerMagicalQuarry;
import ec3.common.inventory.ContainerMagicalRepairer;
import ec3.common.inventory.ContainerMagicianTable;
import ec3.common.inventory.ContainerMagmaticSmeltery;
import ec3.common.inventory.ContainerMatrixAbsorber;
import ec3.common.inventory.ContainerMonsterHarvester;
import ec3.common.inventory.ContainerMonsterHolder;
import ec3.common.inventory.ContainerMoonWell;
import ec3.common.inventory.ContainerPotionSpreader;
import ec3.common.inventory.ContainerRadiatingChamber;
import ec3.common.inventory.ContainerRayTower;
import ec3.common.inventory.ContainerSunRayAbsorber;
import ec3.common.tile.TileChargingChamber;
import ec3.common.tile.TileColdDistillator;
import ec3.common.tile.TileCrystalController;
import ec3.common.tile.TileCrystalExtractor;
import ec3.common.tile.TileCrystalFormer;
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
import ec3.common.tile.TileSunRayAbsorber;
import ec3.common.tile.TileecAcceptor;
import ec3.common.tile.TileecStateChecker;
import ec3.utils.cfg.Config;

public class CommonProxy implements IGuiHandler{

	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world,
			int x, int y, int z) {
		if(ID == Config.guiID[0])
		{
			TileEntity tile = world.getTileEntity(x, y, z);
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
		}
		return null;
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
}
