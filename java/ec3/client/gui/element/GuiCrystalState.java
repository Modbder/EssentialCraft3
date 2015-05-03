package ec3.client.gui.element;

import org.lwjgl.opengl.GL11;

import DummyCore.Utils.MathUtils;
import DummyCore.Utils.MiscUtils;
import ec3.api.ITEHasMRU;
import ec3.common.item.ItemBoundGem;
import ec3.common.item.ItemEssence;
import ec3.common.mod.EssentialCraftCore;
import ec3.common.tile.TileCrystalController;
import ec3.common.tile.TileElementalCrystal;
import ec3.common.tile.TilePotionSpreader;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.init.Items;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.util.ResourceLocation;

public class GuiCrystalState extends GuiTextField{
	public TileCrystalController tile;
	
	public GuiCrystalState(int i, int j, TileEntity t)
	{
		super(i,j);
		tile = (TileCrystalController) t;
	}

	@Override
	public ResourceLocation getElementTexture() {
		// TODO Auto-generated method stub
		return super.getElementTexture();
	}

	@Override
	public void draw(int posX, int posY) {
		if(tile.getMRU() > 0)
		{
			this.drawTexturedModalRect(posX, posY, 0, 0, 17, 17);
			//this.drawTexturedModalRect(posX+19, posY, 1, 0, 17, 17);
			this.drawTexturedModalRect(posX, posY+19, 0, 1, 17, 17);
			//this.drawTexturedModalRect(posX+19, posY+19, 1, 1, 17, 17);
			this.drawTexturedModalRect(posX, posY+17, 0, 1, 17, 2);
			//this.drawTexturedModalRect(posX+19, posY+17, 1, 1, 17, 2);
			
			
			for(int x = 0; x < 5; ++x)
			{
				this.drawTexturedModalRect(posX+16+16*x, posY, 1, 0, 16, 17);
				this.drawTexturedModalRect(posX+16+16*x, posY+19, 1, 1, 16, 17);
				this.drawTexturedModalRect(posX+16+16*x, posY+17, 1, 1, 16, 2);
			}
			
			this.drawTexturedModalRect(posX+3+16*5, posY, 1, 0, 17, 17);
			this.drawTexturedModalRect(posX+3+16*5, posY+19, 1, 1, 17, 17);
			this.drawTexturedModalRect(posX+3+16*5, posY+17, 1, 1, 17, 2);
			
			GL11.glPushMatrix();
			
			GL11.glPopMatrix();
			drawText(posX,posY);
		}
	}

	@Override
	public int getX() {
		// TODO Auto-generated method stub
		return super.getX();
	}

	@Override
	public int getY() {
		// TODO Auto-generated method stub
		return super.getY();
	}

	@Override
	public void drawText(int posX, int posY) {
		TileElementalCrystal crystal = tile.getCrystal();
		FontRenderer fontRenderer = Minecraft.getMinecraft().fontRenderer;
		if(crystal != null)
		{
			fontRenderer.drawStringWithShadow("Fire: "+(int)crystal.fire+"%", posX+2, posY+4, 0xffffff);
			fontRenderer.drawStringWithShadow("Water: "+(int)crystal.water+"%", posX+2, posY+14, 0xffffff);
			fontRenderer.drawStringWithShadow("Earth: "+(int)crystal.earth+"%", posX+2, posY+24, 0xffffff);
			fontRenderer.drawStringWithShadow("Air: "+(int)crystal.air+"%", posX+50, posY+4, 0xffffff);
			fontRenderer.drawStringWithShadow("Size: "+(int)crystal.size+"%", posX+50, posY+14, 0xffffff);
    		ItemStack e = tile.getStackInSlot(1);
    		if(e != null)
    		{
	    		int rarity = (int)((float)e.getItemDamage()/4);
	    		float chance = (float) (2*(rarity+1));
	    		fontRenderer.drawStringWithShadow("Chance: "+(int)chance+"%", posX+50, posY+24, 0xffffff);
    		}
		}
	}

}
