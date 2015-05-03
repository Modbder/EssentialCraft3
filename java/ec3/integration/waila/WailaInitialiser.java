package ec3.integration.waila;

import cpw.mods.fml.common.event.FMLInterModComms;

public class WailaInitialiser {
	
	public static void sendIMC()
	{
		FMLInterModComms.sendMessage("Waila", "register", "ec3.integration.waila.WailaDataProvider.callbackRegister");	
	}
	
	

}
