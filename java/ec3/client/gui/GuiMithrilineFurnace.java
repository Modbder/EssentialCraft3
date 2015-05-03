package ec3.client.gui;

import DummyCore.Client.GuiCommon;
import ec3.api.ITEHasMRU;
import ec3.client.gui.element.GuiBalanceState;
import ec3.client.gui.element.GuiEnderPulseStorage;
import ec3.client.gui.element.GuiHeightState;
import ec3.client.gui.element.GuiMRUGenerated;
import ec3.client.gui.element.GuiMRUState;
import ec3.client.gui.element.GuiMRUStorage;
import ec3.client.gui.element.GuiMoonState;
import net.minecraft.inventory.Container;
import net.minecraft.tileentity.TileEntity;

public class GuiMithrilineFurnace extends GuiCommon{

	public GuiMithrilineFurnace(Container c, TileEntity tile) {
		super(c,tile);
		this.elementList.add(new GuiEnderPulseStorage(4, 64, tile));
	}
	
	

}
