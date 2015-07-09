package ec3.client.gui;

import DummyCore.Utils.MiscUtils;
import ec3.common.inventory.ContainerMagicalChest;
import ec3.common.tile.TileMagicalChest;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;

public class GuiMagicalChest extends GuiContainer{
	
	TileMagicalChest tile;
	
    public GuiMagicalChest(InventoryPlayer inventoryPlayer, TileMagicalChest chest)
    {
        super(new ContainerMagicalChest(inventoryPlayer, chest));
        tile = chest;

        if (this.tile.getBlockMetadata() == 0)
        {
            xSize = 176;
            ySize = 222;
        }
        else if (this.tile.getBlockMetadata() == 1)
        {
            xSize = 256;
            ySize = 256;
        }
    }

	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks,int mX, int mY) {
		int k = (this.width - this.xSize)/2;
		int l = (this.height - this.ySize)/2;
        if (this.tile.getBlockMetadata() == 0)
        	MiscUtils.bindTexture("essentialcraft", "textures/gui/magical_chest.png");
        else
        	MiscUtils.bindTexture("essentialcraft", "textures/gui/void_chest.png");
        
        this.drawTexturedModalRect(k, l, 0, 0, xSize, ySize);
	}
}
