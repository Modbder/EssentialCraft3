package ec3.common.entity;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.entity.EntityList;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.BiomeDictionary.Type;
import cpw.mods.fml.common.registry.EntityRegistry;
import ec3.common.mod.EssentialCraftCore;
import ec3.common.registry.BiomeRegistry;
import ec3.utils.cfg.Config;

public class EntitiesCore {
	public static EntitiesCore instance;
	
	public static void registerEntities()
	{
		int[] id = Config.instance.mobID;
		EntityRegistry.registerModEntity(EntityMRUPresence.class, "EssentialCraftII.entities.MRU", nextEntityID(id[0]), EssentialCraftCore.core, 64, 1, true);
		EntityRegistry.registerModEntity(EntityMRUArrow.class, "EssentialCraftII.entities.MRUArrow", nextEntityID(id[1]), EssentialCraftCore.core, 64, 1, true);
		EntityRegistry.registerModEntity(EntitySolarBeam.class, "EssentialCraftII.entities.SolarBeam", nextEntityID(id[2]), EssentialCraftCore.core, 64, 1, true);
		int windMageID = nextEntityID(id[3]);
		EntityRegistry.registerGlobalEntityID(EntityWindMage.class, "EssentialCraftII.entities.WindMage", windMageID, 0x997755, 0xffffff);
		EntityRegistry.registerModEntity(EntityWindMage.class, "EssentialCraftII.entities.WindMage", windMageID, EssentialCraftCore.core, 64, 1, true);
		int poisonFumeID = nextEntityID(id[4]);
		EntityRegistry.registerGlobalEntityID(EntityPoisonFume.class, "EssentialCraftII.entities.poisonFume", poisonFumeID, 0x00ff00, 0xff00ff);
		EntityRegistry.registerModEntity(EntityPoisonFume.class, "EssentialCraftII.entities.poisonFume", poisonFumeID, EssentialCraftCore.core, 64, 1, true);
		
		EntityRegistry.addSpawn(EntityWindMage.class, 2, 1, 6, EnumCreatureType.monster, biomesToSpawn());
		EntityRegistry.addSpawn(EntityPoisonFume.class, 100, 8, 16, EnumCreatureType.monster, biomesToSpawn());
	}
	
	public static int nextEntityID(int defaultID)
	{
		for(int i = defaultID; i < 512; ++i)
		{
			if(EntityList.getClassFromID(i) == null)
				return i;
		}
		return defaultID;
	}
	
	public static BiomeGenBase[] biomesToSpawn()
	{
		
		List<BiomeGenBase> spawnLst = new ArrayList<BiomeGenBase>();
		for(int i = 0; i < BiomeGenBase.getBiomeGenArray().length; ++i)
		{
			if(BiomeGenBase.getBiomeGenArray()[i] != null 
					&& BiomeGenBase.getBiomeGenArray()[i] != BiomeGenBase.hell
					&& BiomeGenBase.getBiomeGenArray()[i] != BiomeGenBase.sky 
					&& BiomeGenBase.getBiomeGenArray()[i].getSpawnableList(EnumCreatureType.monster) != null
					&& !BiomeGenBase.getBiomeGenArray()[i].getSpawnableList(EnumCreatureType.monster).isEmpty()
					&& !BiomeDictionary.isBiomeOfType(BiomeGenBase.getBiomeGenArray()[i], Type.END) 
					&& !BiomeDictionary.isBiomeOfType(BiomeGenBase.getBiomeGenArray()[i], Type.NETHER))
			{
				spawnLst.add(BiomeGenBase.getBiomeGenArray()[i]);
			}
				
		}
		BiomeGenBase[] biomesArray = new BiomeGenBase[spawnLst.size()];
		for(int i = 0; i < spawnLst.size(); ++i)
		{
			biomesArray[i] = spawnLst.get(i);
		}
		return biomesArray;
	}
}
