package ec3.integration.minetweaker;

import minetweaker.MineTweakerAPI;

public class MTRegistry {

	public static void init()
	{
		MineTweakerAPI.registerClass(MagicianTable.class);
		MineTweakerAPI.registerClass(WindImbue.class);
		MineTweakerAPI.registerClass(MithrilineFurnace.class);
		MineTweakerAPI.registerClass(RadiatingChamber.class);
	}

}
