package ec3.integration.projecte;

import net.minecraft.item.ItemStack;
import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.event.FMLServerStartingEvent;

@Mod(modid="ec3toprojecte")
public class ProjectEIntegration {
	
	@EventHandler
	public void serverStartup(FMLServerStartingEvent event)
	{
		FMLLog.info("Loading EC3 to ProjectE integration...", new Object[]{});
		boolean ec3Loaded = Loader.isModLoaded("essentialcraft");
		boolean projectELoaded = Loader.isModLoaded("ProjectE");
		if(!ec3Loaded)
			FMLLog.warning("EC3 not found! Can I ask you: HOW?", new Object[]{});
		if(!projectELoaded)
			FMLLog.warning("Project E not found! Interrupting integration", new Object[]{});
		
		if(ec3Loaded && projectELoaded)
		{
			
			//Some code here
		}
		else
		{
			FMLLog.info("EC3 to ProjectE integration falilure! EC3 Loaded: %s, Project E Loaded: %s", ec3Loaded, projectELoaded);
			return;
		}
	}
	
	public int findEMCForItemStack(ItemStack is, int attemptNum)
	{
		return 0;
	}

}
