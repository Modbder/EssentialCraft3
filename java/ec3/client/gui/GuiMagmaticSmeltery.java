package ec3.client.gui;

import ec3.api.ITEHasMRU;
import ec3.client.gui.element.GuiBalanceState;
import ec3.client.gui.element.GuiBoundGemState;
import ec3.client.gui.element.GuiEnchantmentState;
import ec3.client.gui.element.GuiFluidTank;
import ec3.client.gui.element.GuiMRUState;
import ec3.client.gui.element.GuiMRUStorage;
import ec3.client.gui.element.GuiPotionState;
import ec3.client.gui.element.GuiRepairState;
import net.minecraft.inventory.Container;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.fluids.IFluidTank;

public class GuiMagmaticSmeltery extends GuiCommon{

	public GuiMagmaticSmeltery(Container c, TileEntity tile) {
		super(c,tile);
		this.elementList.add(new GuiMRUStorage(7, 4, (ITEHasMRU) tile));
		this.elementList.add(new GuiBalanceState(43, 40, tile));
		this.elementList.add(new GuiBoundGemState(43, 4, tile, 0));
		this.elementList.add(new GuiMRUState(25, 58, (ITEHasMRU) tile, 0));
		this.elementList.add(new GuiFluidTank(25, 4, (IFluidTank)tile));
	}
	
	

}
