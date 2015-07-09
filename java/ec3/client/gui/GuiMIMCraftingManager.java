package ec3.client.gui;

import DummyCore.Utils.MiscUtils;
import ec3.common.inventory.ContainerMIMCraftingManager;
import ec3.common.tile.TileNewMIMCraftingManager;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;

public class GuiMIMCraftingManager extends GuiContainer{
	
	TileNewMIMCraftingManager tile;
	
    public GuiMIMCraftingManager(InventoryPlayer inventoryPlayer, TileNewMIMCraftingManager t)
    {
        super(new ContainerMIMCraftingManager(inventoryPlayer, t));
        tile = t;

        xSize = 176;
        ySize = 222;
    }

	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks,int mX, int mY) {
		int k = (this.width - this.xSize)/2;
		int l = (this.height - this.ySize)/2;
		
		MiscUtils.bindTexture("essentialcraft", "textures/gui/magical_chest.png");
        
        this.drawTexturedModalRect(k, l, 0, 0, xSize, ySize);
	}
}
