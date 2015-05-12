package ec3.common.registry;

import ec3.common.world.WorldProviderFirstWorld;
import ec3.utils.cfg.Config;
import net.minecraftforge.common.DimensionManager;

public class DimensionRegistry {
	public static DimensionRegistry core;
	public void registerDimensionMagic()
	{
		DimensionManager.registerProviderType(Config.dimensionID, WorldProviderFirstWorld.class, false);
		DimensionManager.registerDimension(Config.dimensionID,Config.dimensionID);
	}

}
