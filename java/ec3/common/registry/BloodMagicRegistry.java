package ec3.common.registry;

import java.lang.reflect.Method;

import net.minecraft.item.ItemStack;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;

import ec3.common.item.ItemsCore;

public class BloodMagicRegistry {
	
	public static void register()
	{
		try
		{
			Class<?> bmApiRegistry = Class.forName("WayofTime.alchemicalWizardry.api.altarRecipeRegistry.AltarRecipeRegistry");
			Method registerNBTAltarRecipe = bmApiRegistry.getMethod("registerNBTAltarRecipe", ItemStack.class, ItemStack.class, int.class, int.class, int.class, int.class, boolean.class);
			ItemStack emptySoulStone = new ItemStack(ItemsCore.soulStone,1,0);
			ItemStack filledSoulStone = new ItemStack(ItemsCore.soulStone,1,1);
			registerNBTAltarRecipe.invoke(null, filledSoulStone, emptySoulStone, 0, 250, 2, 1, false);
			LogManager.getLogger().log(Level.TRACE, "Successfully registered BloodMagic integration!");
		}
		catch(Exception e)
		{
			LogManager.getLogger().error("Unable to add BloodMagic Integration.");
			return;
		}
	}

}
