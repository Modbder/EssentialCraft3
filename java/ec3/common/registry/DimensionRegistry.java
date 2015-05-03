package ec3.common.registry;

import ec3.common.world.WorldProviderFirstWorld;
import net.minecraftforge.common.DimensionManager;

public class DimensionRegistry {
	public static DimensionRegistry core;
	public void registerDimensionMagic()
	{
		DimensionManager.registerProviderType(53, WorldProviderFirstWorld.class, false);
		DimensionManager.registerDimension(53,53);
	}

}
