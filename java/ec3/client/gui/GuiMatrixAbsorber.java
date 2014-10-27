package ec3.client.gui;

import DummyCore.Client.GuiCommon;
import ec3.api.ITEHasMRU;
import ec3.client.gui.element.GuiBalanceState;
import ec3.client.gui.element.GuiHeightState;
import ec3.client.gui.element.GuiMRUGenerated;
import ec3.client.gui.element.GuiMRUState;
import ec3.client.gui.element.GuiMRUStorage;
import ec3.client.gui.element.GuiMoonState;
import net.minecraft.inventory.Container;
import net.minecraft.tileentity.TileEntity;

public class GuiMatrixAbsorber extends GuiCommon{

	public GuiMatrixAbsorber(Container c, TileEntity tile) {
		super(c,tile);
		this.elementList.add(new GuiMRUStorage(7, 4, (ITEHasMRU) tile));
		this.elementList.add(new GuiMRUState(25, 58, (ITEHasMRU) tile, 0));
		this.elementList.add(new GuiBalanceState(25, 22, tile));
		this.elementList.add(new GuiMRUGenerated(25, 40,tile,"matrixAbsorber"));
	}
	
	

}
