package ec3.common.entity;

import cpw.mods.fml.common.registry.EntityRegistry;
import ec3.common.mod.EssentialCraftCore;
import ec3.utils.cfg.Config;

public class EntitiesCore {
	public static EntitiesCore instance;
	
	public static void registerEntities()
	{
		int[] id = Config.instance.mobID;
		EntityRegistry.registerModEntity(EntityMRUPresence.class, "EssentialCraftII.entities.MRU", id[0], EssentialCraftCore.core, 64, 1, true);
		EntityRegistry.registerModEntity(EntityMRUArrow.class, "EssentialCraftII.entities.MRUArrow", id[1], EssentialCraftCore.core, 64, 1, true);
		EntityRegistry.registerModEntity(EntitySolarBeam.class, "EssentialCraftII.entities.SolarBeam", id[2], EssentialCraftCore.core, 64, 1, true);
	}
}
