package ec3.integration.rotarycraft;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import cpw.mods.fml.common.Loader;

public class RCLoadingHandler {
	
	public static boolean RCDetected;
	public static final Logger logger = LogManager.getLogger();
	
	public static void runPreInitChecks()
	{
		if(Loader.isModLoaded("RotaryCraft"))
		{
			RCDetected = true;
			logger.info("RotaryCraft detected, enabling compathability tweaks...");
		}
	}
	
	public static void postInit()
	{
		if(RCDetected)
		{
			try
			{
				logger.info("This is still to be implemented...");
				//Class<?> CompactorAPI = Class.forName("Reika.RotaryCraft.API.CompactorAPI");
				//Method addRecipe = CompactorAPI.getMethod("addCompactorRecipe", ItemStack.class,ItemStack.class,int.class,int.class);
				
			}catch(Exception e)
			{
				e.printStackTrace();
				return;
			}
		}
	}

}
