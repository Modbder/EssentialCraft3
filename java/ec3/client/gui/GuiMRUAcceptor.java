package ec3.client.gui;

import ec3.api.ITEHasMRU;
import ec3.client.gui.element.GuiBoundGemState;
import net.minecraft.inventory.Container;
import net.minecraft.tileentity.TileEntity;

public class GuiMRUAcceptor extends GuiCommon{

	public GuiMRUAcceptor(Container c, TileEntity tile) {
		super(c,tile);
		this.elementList.add(new GuiBoundGemState(48, 50, tile, 0));
	}
	
	

}
