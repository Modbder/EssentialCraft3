package ec3.integration.nei;

import codechicken.nei.api.API;
import codechicken.nei.api.IConfigureNEI;
import ec3.common.mod.EssentialCraftCore;

public class NEIEssentialCraftConfig implements IConfigureNEI{

	@Override
	public void loadConfig() {
		API.registerRecipeHandler(new MagicianTableRecipeHandler());
		API.registerUsageHandler(new MagicianTableRecipeHandler());
		API.registerRecipeHandler(new RadiatingChamberRecipeHandler());
		API.registerUsageHandler(new RadiatingChamberRecipeHandler());
		API.registerRecipeHandler(new MagicalAssemblerRecipeHandler());
		API.registerUsageHandler(new MagicalAssemblerRecipeHandler());
		API.registerRecipeHandler(new MithrilineFurnaceRecipeHandler());
		API.registerUsageHandler(new MithrilineFurnaceRecipeHandler());
		
		API.registerRecipeHandler(new WorldgenRecipeHandler());
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "EssentialCraft";
	}

	@Override
	public String getVersion() {
		// TODO Auto-generated method stub
		return EssentialCraftCore.version;
	}

}
