package ec3.common.entity;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.BiomeDictionary.Type;
import cpw.mods.fml.common.registry.EntityRegistry;
import ec3.common.mod.EssentialCraftCore;
import ec3.utils.cfg.Config;

public class EntitiesCore {
	public static EntitiesCore instance;
	public static final List<Class<? extends Entity>> registeredEntities = new ArrayList<Class<? extends Entity>>(); 
	
	public static void registerEntities()
	{
		registerEntity(EntityMRUPresence.class, 64, 1, true);
		registerEntity(EntityMRUArrow.class, 64, 1, true);
		registerEntity(EntitySolarBeam.class, 64, 1, true);
		registerEntity(EntityWindMage.class, 64, 1, true);
		registerEntity(EntityPoisonFume.class, 64, 1, true);
		registerEntity(EntityShadowKnife.class, 32, 1, true);
		registerEntity(EntityMRURay.class, 128, 1, true);
		registerEntity(EntityDemon.class, 32, 1, true);
		registerEntity(EntityHologram.class, 32, 1, true);
		registerEntity(EntityPlayerClone.class, 32, 1, true);
		registerEntity(EntityOrbitalStrike.class, 32, 1, true);
		registerEntity(EntityDivider.class, 32, 1, true);
		registerEntity(EntityArmorDestroyer.class, 32, 1, true);
		registerEntity(EntityDividerProjectile.class, 32, 1, true);
		
		EntityRegistry.addSpawn(EntityWindMage.class, 2, 1, 6, EnumCreatureType.monster, biomesToSpawn());
		EntityRegistry.addSpawn(EntityPoisonFume.class, 100, 8, 16, EnumCreatureType.monster, biomesToSpawn());
	}
	
	public static void registerEntity(Class<? extends Entity> entityClass, int trackingRange, int tickDelay, boolean trackRotation)
	{
		EntityRegistry.registerModEntity(entityClass, entityClass.getName(), nextID(), EssentialCraftCore.core, trackingRange, tickDelay, trackRotation);
		registeredEntities.add(entityClass);
	}
	
	public static int id = -1;
	
	private static int nextID()
	{
		return ++id;
	}
	
	public static int nextEntityID(int defaultID)
	{
		if(Config.autoFindEID)return defaultID;
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
