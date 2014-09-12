package ec3.client.gui;

import ec3.api.ITEHasMRU;
import ec3.client.gui.element.GuiBalanceState;
import ec3.client.gui.element.GuiBoundGemState;
import ec3.client.gui.element.GuiMRUState;
import ec3.client.gui.element.GuiMRUStorage;
import net.minecraft.inventory.Container;
import net.minecraft.tileentity.TileEntity;

public class GuiMonsterHolder extends GuiCommon{

	public GuiMonsterHolder(Container c, TileEntity tile) {
		super(c,tile);
		this.elementList.add(new GuiMRUStorage(7, 4, (ITEHasMRU) tile));
		this.elementList.add(new GuiBoundGemState(25, 58-18-18, tile, 0));
		this.elementList.add(new GuiBalanceState(25, 58-18, tile));
		this.elementList.add(new GuiMRUState(25, 58, (ITEHasMRU) tile, 0));
	}
	
	

}
