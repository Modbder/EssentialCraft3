package ec3.client.gui;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.common.config.ConfigElement;
import cpw.mods.fml.client.config.GuiConfig;
import cpw.mods.fml.client.config.IConfigElement;
import ec3.utils.cfg.Config;

public class GuiModConfiguration extends GuiConfig{

	public GuiModConfiguration(GuiScreen parentScreen) {
		super(parentScreen, getConfigElements(), "essentialcraft", false,false, GuiConfig.getAbridgedConfigPath(Config.config.toString()));
		// TODO Auto-generated constructor stub
	}


    private static List getConfigElements()
    {
        List list = new ArrayList();
        list.addAll((new ConfigElement(Config.config.getCategory("misc"))).getChildElements());
        list.addAll((new ConfigElement(Config.config.getCategory("general"))).getChildElements());
        list.addAll((new ConfigElement(Config.config.getCategory("worldgen"))).getChildElements());
        list.addAll((new ConfigElement(Config.config.getCategory("difficulty"))).getChildElements());
        list.addAll((new ConfigElement(Config.config.getCategory("tileentities"))).getChildElements());
        return list;
    }
}
