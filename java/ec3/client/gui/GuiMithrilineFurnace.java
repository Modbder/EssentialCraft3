package ec3.client.gui;

import DummyCore.Client.GuiCommon;
import ec3.client.gui.element.GuiEnderPulseStorage;
import net.minecraft.inventory.Container;
import net.minecraft.tileentity.TileEntity;

public class GuiMithrilineFurnace extends GuiCommon{

	public GuiMithrilineFurnace(Container c, TileEntity tile) {
		super(c,tile);
		this.elementList.add(new GuiEnderPulseStorage(4, 64, tile));
	}
	
	

}
