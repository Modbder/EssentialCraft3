package ec3.common.entity;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.entity.EnumCreatureType;
import net.minecraft.world.biome.BiomeGenBase;
import cpw.mods.fml.common.registry.EntityRegistry;
import ec3.common.mod.EssentialCraftCore;
import ec3.common.registry.BiomeRegistry;
import ec3.utils.cfg.Config;

public class EntitiesCore {
	public static EntitiesCore instance;
	
	public static void registerEntities()
	{
		int[] id = Config.instance.mobID;
		EntityRegistry.registerModEntity(EntityMRUPresence.class, "EssentialCraftII.entities.MRU", id[0], EssentialCraftCore.core, 64, 1, true);
		EntityRegistry.registerModEntity(EntityMRUArrow.class, "EssentialCraftII.entities.MRUArrow", id[1], EssentialCraftCore.core, 64, 1, true);
		EntityRegistry.registerModEntity(EntitySolarBeam.class, "EssentialCraftII.entities.SolarBeam", id[2], EssentialCraftCore.core, 64, 1, true);
		
		EntityRegistry.registerGlobalEntityID(EntityWindMage.class, "EssentialCraftII.entities.WindMage", id[3], 0x997755, 0xffffff);
		EntityRegistry.registerModEntity(EntityWindMage.class, "EssentialCraftII.entities.WindMage", id[3], EssentialCraftCore.core, 64, 1, true);
		EntityRegistry.registerGlobalEntityID(EntityPoisonFume.class, "EssentialCraftII.entities.poisonFume", id[4], 0x00ff00, 0xff00ff);
		EntityRegistry.registerModEntity(EntityPoisonFume.class, "EssentialCraftII.entities.poisonFume", id[4], EssentialCraftCore.core, 64, 1, true);
		
		EntityRegistry.addSpawn(EntityWindMage.class, 2, 1, 6, EnumCreatureType.monster, biomesToSpawn());
		EntityRegistry.addSpawn(EntityPoisonFume.class, 100, 8, 16, EnumCreatureType.monster, biomesToSpawn());
	}
	
	public static BiomeGenBase[] biomesToSpawn()
	{
		
		List<BiomeGenBase> spawnLst = new ArrayList<BiomeGenBase>();
		for(int i = 0; i < BiomeGenBase.getBiomeGenArray().length; ++i)
		{
			if(BiomeGenBase.getBiomeGenArray()[i] != null && BiomeGenBase.getBiomeGenArray()[i] != BiomeGenBase.hell && BiomeGenBase.getBiomeGenArray()[i] != BiomeGenBase.sky)
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
