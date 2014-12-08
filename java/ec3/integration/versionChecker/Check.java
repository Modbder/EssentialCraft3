package ec3.integration.versionChecker;

import cpw.mods.fml.common.event.FMLInterModComms;
import net.minecraft.nbt.NBTTagCompound;

public class Check {
	
	public static void checkerCommit()
	{
		FMLInterModComms.sendMessage("essentialcraft", "VersionChecker", "https://dl.dropbox.com/s/u49qiqb92rprq0v/version.txt?dl=0");
	}

}
