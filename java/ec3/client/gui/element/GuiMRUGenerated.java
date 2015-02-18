package ec3.client.gui.element;

import org.lwjgl.opengl.GL11;

import DummyCore.Utils.MathUtils;
import DummyCore.Utils.MiscUtils;
import ec3.api.IHotBlock;
import ec3.api.ITEHasMRU;
import ec3.common.item.ItemBoundGem;
import ec3.common.mod.EssentialCraftCore;
import ec3.common.tile.TileColdDistillator;
import ec3.common.tile.TileEnderGenerator;
import ec3.common.tile.TileFlowerBurner;
import ec3.common.tile.TileHeatGenerator;
import ec3.common.tile.TileMatrixAbsorber;
import ec3.common.tile.TileMoonWell;
import ec3.common.tile.TileSunRayAbsorber;
import ec3.common.tile.TileUltraFlowerBurner;
import ec3.common.tile.TileUltraHeatGenerator;
import ec3.common.tile.TileecController;
import ec3.common.tile.TileecStateChecker;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.IInventory;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.util.ResourceLocation;

public class GuiMRUGenerated extends GuiTextField{
	
	public TileEntity tile;
	public String tileValue;
	
	public GuiMRUGenerated(int i, int j, TileEntity t, String tileType)
	{
		super(i,j);
		tile = t;
		tileValue = tileType;
	}

	@Override
	public ResourceLocation getElementTexture() {
		// TODO Auto-generated method stub
		return super.getElementTexture();
	}

	@Override
	public void draw(int posX, int posY) {
		super.draw(posX, posY);
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
		if(tileValue.equals("matrixAbsorber"))
		{
			Minecraft.getMinecraft().fontRenderer.drawStringWithShadow(TileMatrixAbsorber.mruUsage+"UBMRU > "+TileMatrixAbsorber.mruGenerated+"MRU", posX+2, posY+5, 0xffffff);
		}
		if(tileValue.equals("heatGenerator"))
		{
			if(tile instanceof TileHeatGenerator)
			{
				TileHeatGenerator furnace = (TileHeatGenerator) tile;
				MiscUtils.bindTexture("minecraft", "textures/gui/container/furnace.png");
				this.drawTexturedModalRect(posX+100, posY+2, 55, 36, 15, 15);
				if(furnace.currentBurnTime > 0)
				{
					int scaledSize = MathUtils.pixelatedTextureSize(furnace.currentBurnTime, furnace.currentMaxBurnTime, 14)+1;
					this.drawTexturedModalRect(posX+101, posY+2+15-scaledSize, 176, 15-scaledSize, 15, scaledSize);
				}
            	float mruGenerated = furnace.mruGenerated;
            	float mruFactor = 1.0F;
            	Block[] b = new Block[4];
            	b[0] = furnace.getWorldObj().getBlock(furnace.xCoord+2, furnace.yCoord, furnace.zCoord);
            	b[1] = furnace.getWorldObj().getBlock(furnace.xCoord-2, furnace.yCoord, furnace.zCoord);
            	b[2] = furnace.getWorldObj().getBlock(furnace.xCoord, furnace.yCoord, furnace.zCoord+2);
            	b[3] = furnace.getWorldObj().getBlock(furnace.xCoord, furnace.yCoord, furnace.zCoord-2);
            	int[] ox = new int[]{2,-2,0,0};
            	int[] oz = new int[]{0,0,2,-2};
            	for(int i = 0; i < 4; ++i)
            	{
            		if(b[i] == Blocks.air)
            		{
            			mruFactor*=0;
            		}else if(b[i] == Blocks.netherrack)
            		{
            			mruFactor*=0.75F;
            		}else if(b[i] == Blocks.lava)
            		{
            			mruFactor*=0.95F;
            		}else if(b[i] == Blocks.fire)
            		{
            			mruFactor*=0.7F;
            		}else if(b[i] instanceof IHotBlock)
            		{
            			mruFactor*=(((IHotBlock)b[i]).getHeatModifier(tile.getWorldObj(), tile.xCoord+ox[i], tile.yCoord, tile.zCoord+oz[i]));
            		}else
            		{
            			mruFactor*=0.5F;
            		}
            		
            	}
            	mruGenerated*=mruFactor;
				Minecraft.getMinecraft().fontRenderer.drawStringWithShadow((int)mruGenerated+" MRU/t", posX+2, posY+5, 0xffffff);
			}
		}
		if(tileValue.equals("ultraHeatGenerator"))
		{
			if(tile instanceof TileUltraHeatGenerator)
			{
				TileUltraHeatGenerator furnace = (TileUltraHeatGenerator) tile;
				MiscUtils.bindTexture("minecraft", "textures/gui/container/furnace.png");
				this.drawTexturedModalRect(posX+100, posY+2, 55, 36, 15, 15);
				if(furnace.currentBurnTime > 0)
				{
					int scaledSize = MathUtils.pixelatedTextureSize(furnace.currentBurnTime, furnace.currentMaxBurnTime, 14)+1;
					this.drawTexturedModalRect(posX+101, posY+2+15-scaledSize, 176, 15-scaledSize, 15, scaledSize);
				}
            	float mruGenerated = 20;
            	float mruFactor = 1.0F;
            	Block[] b = new Block[4];
            	b[0] = furnace.getWorldObj().getBlock(furnace.xCoord+2, furnace.yCoord, furnace.zCoord);
            	b[1] = furnace.getWorldObj().getBlock(furnace.xCoord-2, furnace.yCoord, furnace.zCoord);
            	b[2] = furnace.getWorldObj().getBlock(furnace.xCoord, furnace.yCoord, furnace.zCoord+2);
            	b[3] = furnace.getWorldObj().getBlock(furnace.xCoord, furnace.yCoord, furnace.zCoord-2);
            	int[] ox = new int[]{2,-2,0,0};
            	int[] oz = new int[]{0,0,2,-2};
            	for(int i = 0; i < 4; ++i)
            	{
            		if(b[i] == Blocks.air)
            		{
            			mruFactor*=0;
            		}else if(b[i] == Blocks.netherrack)
            		{
            			mruFactor*=0.75F;
            		}else if(b[i] == Blocks.lava)
            		{
            			mruFactor*=0.95F;
            		}else if(b[i] == Blocks.fire)
            		{
            			mruFactor*=0.7F;
            		}else if(b[i] instanceof IHotBlock)
            		{
            			mruFactor*=(((IHotBlock)b[i]).getHeatModifier(tile.getWorldObj(), tile.xCoord+ox[i], tile.yCoord, tile.zCoord+oz[i]));
            		}else
            		{
            			mruFactor*=0.5F;
            		}
            		
            	}
            	float heat = furnace.heat;
            	if(heat < 1000)
            		mruGenerated = heat/100;
            	else
            		if(heat > 10000)
            			mruGenerated = 80+heat/1000;
            		else
            			mruGenerated = heat/124;
				Minecraft.getMinecraft().fontRenderer.drawStringWithShadow((int)mruGenerated+" MRU/t", posX+2, posY+5, 0xffffff);
				Minecraft.getMinecraft().fontRenderer.drawStringWithShadow("Heat: "+(int)furnace.heat+"C", posX+82, posY-10, 0xffffff);
			}
		}
		if(tileValue.equals("naturalFurnace"))
		{
			if(tile instanceof TileFlowerBurner)
			{
				TileFlowerBurner furnace = (TileFlowerBurner) tile;
				Minecraft.getMinecraft().fontRenderer.drawStringWithShadow((int)furnace.mruGenerated+" MRU/t", posX+2, posY+5, 0xffffff);
				MiscUtils.bindTexture("essentialcraft", "textures/gui/slot_common.png");
				this.drawTexturedModalRect(posX+82, posY, 0, 0, 18, 18);
				if(furnace.burnedFlower != null)
				{
					Block b = furnace.getWorldObj().getBlock((int)furnace.burnedFlower.x,(int)furnace.burnedFlower.y, (int)furnace.burnedFlower.z);
					if(b != null)
					{
						MiscUtils.drawTexture(posX+83, posY+1, b.getIcon(2, furnace.getWorldObj().getBlockMetadata((int)furnace.burnedFlower.x,(int)furnace.burnedFlower.y, (int)furnace.burnedFlower.z)), 16, 16, 1);
					}
				}
				MiscUtils.bindTexture("minecraft", "textures/gui/container/furnace.png");
				this.drawTexturedModalRect(posX+82, posY-15, 55, 36, 15, 15);
				if(furnace.burnTime > 0)
				{
					int scaledSize = MathUtils.pixelatedTextureSize(furnace.burnTime, 30*20, 14)+1;
					this.drawTexturedModalRect(posX+83, posY-scaledSize, 176, 15-scaledSize, 15, scaledSize);
				}
			}
		}
		if(tileValue.equals("ultraNaturalFurnace"))
		{
			if(tile instanceof TileUltraFlowerBurner)
			{
				TileUltraFlowerBurner furnace = (TileUltraFlowerBurner) tile;
				Minecraft.getMinecraft().fontRenderer.drawStringWithShadow((int)furnace.mruProduced+" MRU/t", posX+2, posY+5, 0xffffff);
				MiscUtils.bindTexture("essentialcraft", "textures/gui/slot_common.png");
				this.drawTexturedModalRect(posX+82, posY, 0, 0, 18, 18);
				if(furnace.burnedFlower != null)
				{
					Block b = furnace.getWorldObj().getBlock((int)furnace.burnedFlower.x,(int)furnace.burnedFlower.y, (int)furnace.burnedFlower.z);
					if(b != null)
					{
						int color = b.colorMultiplier(furnace.getWorldObj(), (int)furnace.burnedFlower.x,(int)furnace.burnedFlower.y, (int)furnace.burnedFlower.z);
						GL11.glColor3f((float)(color >> 16 & 255) / 255.0F, (float)(color >> 8 & 255) / 255.0F, (float)(color & 255) / 255.0F);
						MiscUtils.drawTexture(posX+83, posY+1, b.getIcon(2, furnace.getWorldObj().getBlockMetadata((int)furnace.burnedFlower.x,(int)furnace.burnedFlower.y, (int)furnace.burnedFlower.z)), 16, 16, 1);
					}
				}
				MiscUtils.bindTexture("minecraft", "textures/gui/container/furnace.png");
				this.drawTexturedModalRect(posX+82, posY-15, 55, 36, 15, 15);
				if(furnace.burnTime > 0)
				{
					int scaledSize = MathUtils.pixelatedTextureSize(furnace.burnTime, 30*20, 14)+1;
					this.drawTexturedModalRect(posX+83, posY-scaledSize, 176, 15-scaledSize, 15, scaledSize);
				}
			}
		}
		if(tileValue.equals("cold"))
		{
			if(tile instanceof TileColdDistillator)
			{
				TileColdDistillator cold = (TileColdDistillator) tile;
				Minecraft.getMinecraft().fontRenderer.drawStringWithShadow((int)cold.CgetMru()+" MRU/t", posX+2, posY+5, 0xffffff);
			}
		}
		if(tileValue.equals("enderGenerator"))
		{
			Minecraft.getMinecraft().fontRenderer.drawStringWithShadow((int)TileEnderGenerator.mruGenerated+" MRU/hit", posX+2, posY+5, 0xffffff);
		}
		if(tileValue.equals("sunray"))
		{
			Minecraft.getMinecraft().fontRenderer.drawStringWithShadow((int)TileSunRayAbsorber.mruGenerated+" MRU/t", posX+2, posY+5, 0xffffff);
		}
		if(tileValue.equals("moonwell"))
		{
			float mruGenerated = TileMoonWell.mruGenerated;
			int moonPhase = tile.getWorldObj().provider.getMoonPhase(tile.getWorldObj().getWorldTime());
			float moonFactor = 1.0F;
			switch(moonPhase)
			{
				case 0:
				{
					moonFactor = 1.0F;
					break;
				}
				case 1:
				{
					moonFactor = 0.75F;
					break;
				}
				case 7:
				{
					moonFactor = 0.75F;
					break;
				}
				case 2:
				{
					moonFactor = 0.5F;
					break;
				}
				case 6:
				{
					moonFactor = 0.5F;
					break;
				}
				case 3:
				{
					moonFactor = 0.25F;
					break;
				}
				case 5:
				{
					moonFactor = 0.25F;
					break;
				}
				case 4:
				{
					moonFactor = 0.0F;
					break;
				}
			}
			mruGenerated *= moonFactor;
			float heightFactor = 1.0F;
			if(tile.yCoord > 80)
				heightFactor = 0F;
			else
			{
				heightFactor = 1.0F - (float)((float)tile.yCoord/80F);
				mruGenerated *= heightFactor;
			}
			Minecraft.getMinecraft().fontRenderer.drawStringWithShadow((int)mruGenerated+" MRU/t", posX+2, posY+5, 0xffffff);
		}
	}

}
