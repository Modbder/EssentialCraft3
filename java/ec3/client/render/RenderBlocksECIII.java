package ec3.client.render;

import org.lwjgl.opengl.GL11;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.IBlockAccess;
import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;
import ec3.common.block.BlockChargingChamber;
import ec3.common.block.BlockColdDistillator;
import ec3.common.block.BlockCorruptionCleaner;
import ec3.common.block.BlockCorruption_Light;
import ec3.common.block.BlockCrystalController;
import ec3.common.block.BlockCrystalExtractor;
import ec3.common.block.BlockCrystalFormer;
import ec3.common.block.BlockDarknessObelisk;
import ec3.common.block.BlockDrops;
import ec3.common.block.BlockDropsOre;
import ec3.common.block.BlockEnderGenerator;
import ec3.common.block.BlockFancy;
import ec3.common.block.BlockFlowerBurner;
import ec3.common.block.BlockHeatGenerator;
import ec3.common.block.BlockMIM;
import ec3.common.block.BlockMINEjector;
import ec3.common.block.BlockMINInjector;
import ec3.common.block.BlockMRUCoil_Coil;
import ec3.common.block.BlockMRUCoil_Hardener;
import ec3.common.block.BlockMRULevitator;
import ec3.common.block.BlockMRUReactor;
import ec3.common.block.BlockMRUSpreader;
import ec3.common.block.BlockMagicalAssemblerCore;
import ec3.common.block.BlockMagicalDisplay;
import ec3.common.block.BlockMagicalEnchanter;
import ec3.common.block.BlockMagicalJukebox;
import ec3.common.block.BlockMagicalMirror;
import ec3.common.block.BlockMagicalQuarry;
import ec3.common.block.BlockMagicalRepairer;
import ec3.common.block.BlockMagicianTable;
import ec3.common.block.BlockMagmaticSmeltery;
import ec3.common.block.BlockMatrixAbsorber;
import ec3.common.block.BlockMithrilineCrystal;
import ec3.common.block.BlockMithrilineFurnace;
import ec3.common.block.BlockMonsterHarvester;
import ec3.common.block.BlockMonsterHolder;
import ec3.common.block.BlockMoonWell;
import ec3.common.block.BlockPotionSpreader;
import ec3.common.block.BlockRadiatingChamber;
import ec3.common.block.BlockRayTower;
import ec3.common.block.BlockReactorSupport;
import ec3.common.block.BlockRedstoneTransmitter;
import ec3.common.block.BlockSunRayAbsorber;
import ec3.common.block.BlockUltraFlowerBurner;
import ec3.common.block.BlockUltraHeatGenerator;
import ec3.common.block.BlocksCore;
import ec3.common.tile.TileMRUReactor;

public class RenderBlocksECIII implements ISimpleBlockRenderingHandler{

	@Override
	public void renderInventoryBlock(Block block, int metadata, int modelId,
			RenderBlocks renderer) {
		if(block instanceof BlockCrystalExtractor)
		{
	        GL11.glPushMatrix();
	        GL11.glTranslatef(0F,-0.5F,0F);
	        Minecraft.getMinecraft().renderEngine.bindTexture(RenderCrystalExtractor.textures);
	        RenderCrystalExtractor.model.renderAll();
	        GL11.glPopMatrix();
		}
		if(block instanceof BlockMithrilineFurnace)
		{
	        GL11.glPushMatrix();
	        GL11.glTranslatef(0F,-0.5F,0F);
	        Minecraft.getMinecraft().renderEngine.bindTexture(RenderMithrilineFurnace.textures);
	        RenderMithrilineFurnace.model.renderAll();
	        GL11.glPopMatrix();
		}
		if(block instanceof BlockMithrilineCrystal)
		{
	        GL11.glPushMatrix();
	        GL11.glTranslatef(0F,-1F,0F);
	        GL11.glScalef(2, 2, 2);
	        Minecraft.getMinecraft().renderEngine.bindTexture(metadata == 0 ? RenderMithrilineCrystal.textures_mithriline : metadata == 3 ? RenderMithrilineCrystal.textures_pale : metadata == 6 ? RenderMithrilineCrystal.textures_void : metadata == 9 ? RenderMithrilineCrystal.textures_demonic : RenderMithrilineCrystal.textures_shade);
	        RenderMithrilineCrystal.model.renderAll();
	        GL11.glPopMatrix();
		}
		if(block instanceof BlockChargingChamber)
		{
	        GL11.glPushMatrix();
	        GL11.glTranslatef(0F,-0.5F,0F);
	        Minecraft.getMinecraft().renderEngine.bindTexture(RenderChargingChamber.textures);
	        RenderChargingChamber.model.renderAll();
	        GL11.glPopMatrix();
		}
		if(block instanceof BlockMagicalMirror)
		{
	        GL11.glPushMatrix();
	        GL11.glTranslatef(0.5F,-0.5F,0.5F);
	        Minecraft.getMinecraft().renderEngine.bindTexture(RenderMagicalMirror.textures);
	        RenderMagicalMirror.model.renderPart("pCube2");
	        Minecraft.getMinecraft().renderEngine.bindTexture(RenderMagicalMirror.glass);
	        RenderMagicalMirror.model.renderPart("pPlane1");
	        GL11.glPopMatrix();
		}
		if(block instanceof BlockMagicalAssemblerCore)
		{
	        GL11.glPushMatrix();
	        GL11.glTranslatef(0F,-0.5F,0F);
	        Minecraft.getMinecraft().renderEngine.bindTexture(RenderMagicalAssembler.textures);
	        RenderMagicalAssembler.model.renderPart("pCube1");
	        Minecraft.getMinecraft().renderEngine.bindTexture(RenderMagicalAssembler.texturesMRU);
	        RenderMagicalAssembler.model.renderPart("pCube2");
	        RenderMagicalAssembler.model.renderPart("pCube3");
	        RenderMagicalAssembler.model.renderPart("pCube4");
	        RenderMagicalAssembler.model.renderPart("pCylinder1");
	        RenderMagicalAssembler.model.renderPart("pCylinder2");
	        RenderMagicalAssembler.model.renderPart("pCylinder3");
	        RenderMagicalAssembler.model.renderPart("pCylinder4");
	        RenderMagicalAssembler.model.renderPart("pCylinder5");
	        RenderMagicalAssembler.model.renderPart("pCylinder6");
	        GL11.glPopMatrix();
		}
		if(block instanceof BlockMINEjector)
		{
			int x = 0;
			int y = 0;
			int z = 0;
			float scaleindex = 1.0F;
			boolean advanced = metadata > 5;
			TextureManager render = Minecraft.getMinecraft().renderEngine;
			GL11.glPushMatrix();
			RenderHelper.disableStandardItemLighting();
			GL11.glEnable(GL11.GL_BLEND);
	        GL11.glDisable(GL11.GL_LIGHTING);
	        GL11.glDisable(GL11.GL_CULL_FACE);
	        OpenGlHelper.glBlendFunc(770, 771, 1, 0);
			
			GL11.glTranslated(x, y-0.5F, z);
			GL11.glScalef(scaleindex, scaleindex, scaleindex);
			render.bindTexture(RenderMINEjector.glass);
			RenderMINEjector.model.renderPart("pPipe1");
			if(!advanced)
				render.bindTexture(RenderMINEjector.overlay);
			else
				render.bindTexture(RenderMINEjector.overlayA);
			RenderMINEjector.model.renderPart("pCube2");
			if(!advanced)
				render.bindTexture(RenderMINEjector.rings);
			else
				render.bindTexture(RenderMINEjector.ringsA);
			RenderMINEjector.model.renderPart("pTorus1");
			RenderMINEjector.model.renderPart("pTorus2");
			RenderMINEjector.model.renderPart("pTorus3");
			RenderMINEjector.model.renderPart("pTorus4");
			
			GL11.glEnable(GL11.GL_LIGHTING);
			GL11.glDisable(GL11.GL_BLEND);
			RenderHelper.enableStandardItemLighting();
			GL11.glPopMatrix();
		}
		if(block instanceof BlockMINInjector)
		{
			int x = 0;
			int y = 0;
			int z = 0;
			float scaleindex = 1.0F;
			boolean advanced = metadata > 5;
			TextureManager render = Minecraft.getMinecraft().renderEngine;
			GL11.glPushMatrix();
			RenderHelper.disableStandardItemLighting();
			GL11.glEnable(GL11.GL_BLEND);
	        GL11.glDisable(GL11.GL_LIGHTING);
	        GL11.glDisable(GL11.GL_CULL_FACE);
	        OpenGlHelper.glBlendFunc(770, 771, 1, 0);
			
			GL11.glTranslated(x, y-0.5F, z);
			GL11.glScalef(scaleindex, scaleindex, scaleindex);
			render.bindTexture(RenderMINInjector.glass);
			RenderMINInjector.model.renderPart("pPipe1");
			if(!advanced)
				render.bindTexture(RenderMINInjector.glass);
			else
				render.bindTexture(RenderMINInjector.glass);
			RenderMINInjector.model.renderPart("pSphere1");
			if(!advanced)
				render.bindTexture(RenderMINInjector.rings);
			else
				render.bindTexture(RenderMINInjector.ringsA);
			RenderMINInjector.model.renderPart("pTorus1");
			RenderMINInjector.model.renderPart("pTorus2");
			RenderMINInjector.model.renderPart("pTorus3");
			RenderMINInjector.model.renderPart("pTorus4");
			
			GL11.glEnable(GL11.GL_LIGHTING);
			GL11.glDisable(GL11.GL_BLEND);
			RenderHelper.enableStandardItemLighting();
			GL11.glPopMatrix();
		}
		if(block instanceof BlockCrystalController)
		{
	        GL11.glPushMatrix();
	        GL11.glTranslatef(0F,-0.5F,0F);
	        Minecraft.getMinecraft().renderEngine.bindTexture(RenderCrystalController.textures);
	        RenderCrystalController.model.renderAll();
	        GL11.glPopMatrix();
		}
		if(block instanceof BlockDarknessObelisk)
		{
	        GL11.glPushMatrix();
	        GL11.glTranslatef(0F,-0.5F,0F);
	        float f = 0.5F;
	        GL11.glScalef(f, f, f);
	        Minecraft.getMinecraft().renderEngine.bindTexture(RenderDarknessObelisk.obelisk);
	        RenderDarknessObelisk.model.renderPart("pCube1");
	        GL11.glPopMatrix();
		}
		if(block instanceof BlockCrystalFormer)
		{
	        GL11.glPushMatrix();
	        GL11.glTranslatef(0F,-0.5F,0F);
	        Minecraft.getMinecraft().renderEngine.bindTexture(RenderCrystalFormer.textures);
	        RenderCrystalFormer.model.renderAll();
	        GL11.glPopMatrix();
		}
		if(block instanceof BlockMagicalJukebox)
		{
	        GL11.glPushMatrix();
	        GL11.glTranslatef(0F,-0.5F,0F);
	        Minecraft.getMinecraft().renderEngine.bindTexture(RenderMagicalJukebox.textures);
	        RenderMagicalJukebox.model.renderAll();
	        GL11.glPopMatrix();
		}
		if(block instanceof BlockMagmaticSmeltery)
		{
	        GL11.glPushMatrix();
	        GL11.glTranslatef(0F,-0.5F,0F);
	        Minecraft.getMinecraft().renderEngine.bindTexture(RenderMagmaticSmelter.textures);
	        RenderMagmaticSmelter.model.renderAll();
	        GL11.glPopMatrix();
		}
		if(block instanceof BlockRadiatingChamber)
		{
	        GL11.glPushMatrix();
	        GL11.glTranslatef(0F,-0.5F,0F);
	        Minecraft.getMinecraft().renderEngine.bindTexture(RenderRadiatingChamber.textures);
	        RenderRadiatingChamber.model.renderAll();
	        GL11.glPopMatrix();
		}
		if(block instanceof BlockMatrixAbsorber)
		{
	        GL11.glPushMatrix();
	        GL11.glTranslatef(0F,-0.5F,0F);
	        float scale = 1F;
	        GL11.glScalef(scale, scale, scale);
	        Minecraft.getMinecraft().renderEngine.bindTexture(RenderMatrixAbsorber.textures);
	        RenderMatrixAbsorber.model.renderAll();
	        GL11.glPopMatrix();
		}
		if(block instanceof BlockMagicalRepairer)
		{
	        GL11.glPushMatrix();
	        GL11.glTranslatef(0F,-0.5F,0F);
	        Minecraft.getMinecraft().renderEngine.bindTexture(RenderMagicalRepairer.textures);
	        RenderMagicalRepairer.model.renderAll();
	        GL11.glPopMatrix();
		}
		if(block instanceof BlockMonsterHarvester)
		{
	        GL11.glPushMatrix();
	        GL11.glTranslatef(0F,-0.5F,0F);
	        Minecraft.getMinecraft().renderEngine.bindTexture(RenderMonsterHarvester.textures);
	        RenderMonsterHarvester.model.renderAll();
	        GL11.glPopMatrix();
		}
		if(block instanceof BlockMagicalEnchanter)
		{
	        GL11.glPushMatrix();
	        GL11.glTranslatef(0F,-0.5F,0F);
	        Minecraft.getMinecraft().renderEngine.bindTexture(RenderMagicalEnchanter.textures);
	        RenderMagicalEnchanter.model.renderAll();
	        GL11.glPopMatrix();
		}
		if(block instanceof BlockPotionSpreader)
		{
	        GL11.glPushMatrix();
	        GL11.glTranslatef(0F,-0.5F,0F);
	        Minecraft.getMinecraft().renderEngine.bindTexture(RenderPotionSpreader.textures);
	        RenderPotionSpreader.model.renderAll();
	        GL11.glPopMatrix();
		}
		if(block instanceof BlockFlowerBurner)
		{
	        GL11.glPushMatrix();
	        GL11.glTranslatef(0F,-0.5F,0F);
	        Minecraft.getMinecraft().renderEngine.bindTexture(RenderFlowerBurner.textures);
	        RenderFlowerBurner.model.renderAll();
	        GL11.glPopMatrix();
		}
		if(block instanceof BlockUltraFlowerBurner)
		{
	        GL11.glPushMatrix();
	        GL11.glTranslatef(0F,-0.5F,0F);
	        Minecraft.getMinecraft().renderEngine.bindTexture(RenderUltraFlowerBurner.textures);
	        RenderUltraFlowerBurner.model.renderAll();
	        GL11.glPopMatrix();
		}
		if(block instanceof BlockMagicianTable)
		{
	        GL11.glPushMatrix();
	        GL11.glTranslatef(0F,-0.5F,0F);
	        Minecraft.getMinecraft().renderEngine.bindTexture(RenderMagicianTable.textures);
	        RenderMagicianTable.model.renderAll();
	        GL11.glPopMatrix();
		}
		if(block instanceof BlockEnderGenerator)
		{
	        GL11.glPushMatrix();
	        GL11.glTranslatef(0F,-0.5F,0F);
	        Minecraft.getMinecraft().renderEngine.bindTexture(RenderEnderGenerator.textures);
	        RenderEnderGenerator.model.renderAll();
	        GL11.glPopMatrix();
		}
		if(block instanceof BlockDropsOre)
		{
	        GL11.glPushMatrix();
	        BlockDropsOre dr = (BlockDropsOre) block;
	        
	        renderer.setOverrideBlockTexture(metadata < 5 ? dr.icons[0] : metadata >= 5 && metadata < 10 ? dr.icons[1] : dr.icons[2]);
	        renderer.renderBlockAsItem(Blocks.glass, 1, 1);
	        renderer.useInventoryTint = false;
	        float s = 0.999F;
	        GL11.glScalef(s, s, s);
	        switch(metadata % 5)
	        {
	        	case 0:
	        	{
	        		GL11.glColor3f(1, 0, 1);
	        		break;
	        	}
	        	case 1:
	        	{
	        		GL11.glColor3f(1, 0, 0);
	        		break;
	        	}
	        	case 2:
	        	{
	        		GL11.glColor3f(0, 0, 1);
	        		break;
	        	}
	        	case 3:
	        	{
	        		GL11.glColor3f(0.4F, 0.3F, 0.3F);
	        		break;
	        	}
	        	case 4:
	        	{
	        		GL11.glColor3f(1, 1F, 1);
	        		break;
	        	}
	        }
	       
	        renderer.setOverrideBlockTexture(dr.icons[3]);
	        renderer.renderBlockAsItem(Blocks.glass, 1, 1);
	        renderer.useInventoryTint = true;
	        GL11.glPopMatrix();
		}
		if(block instanceof BlockHeatGenerator)
		{
	        GL11.glPushMatrix();
	        GL11.glTranslatef(0F,-0.5F,0F);
	        Minecraft.getMinecraft().renderEngine.bindTexture(RenderHeatGenerator.textures);
	        RenderHeatGenerator.model.renderAll();
	        GL11.glPopMatrix();
		}
		if(block instanceof BlockUltraHeatGenerator)
		{
	        GL11.glPushMatrix();
	        GL11.glTranslatef(0F,-0.5F,0F);
	        Minecraft.getMinecraft().renderEngine.bindTexture(RenderUltraHeatGenerator.textures);
	        RenderUltraHeatGenerator.model.renderAll();
	        GL11.glPopMatrix();
		}
		if(block instanceof BlockSunRayAbsorber)
		{
	        GL11.glPushMatrix();
	        GL11.glTranslatef(0F,-0.6F,0F);
	        Minecraft.getMinecraft().renderEngine.bindTexture(RenderSunRayAbsorber.textures);
	        //GL11.glScalef(0.01F, 0.01F, 0.01F);
	        RenderSunRayAbsorber.model.renderAll();
	        GL11.glPopMatrix();
		}
		if(block instanceof BlockColdDistillator)
		{
	        GL11.glPushMatrix();
	        GL11.glTranslatef(0F,-0.5F,0F);
	        Minecraft.getMinecraft().renderEngine.bindTexture(RenderColdDistillator.textures);
	        RenderColdDistillator.model.renderAll();
	        GL11.glPopMatrix();
		}
		if(block instanceof BlockMRUCoil_Hardener)
		{
	        GL11.glPushMatrix();
	        GL11.glTranslatef(0F,-0.5F,0F);
	        Minecraft.getMinecraft().renderEngine.bindTexture(RenderMRUCoilHardener.textures);
	        RenderMRUCoilHardener.model.renderAll();
	        GL11.glPopMatrix();
		}
		if(block instanceof BlockMRUReactor)
		{
	        GL11.glPushMatrix();
	        GL11.glTranslatef(0F,-0.5F,0F);
	        Minecraft.getMinecraft().renderEngine.bindTexture(RenderMRUReactor.textures);
	        RenderMRUReactor.model.renderAll();
	        GL11.glPopMatrix();
		}
		if(block instanceof BlockDrops)
		{
			switch(metadata)
			{
				case 0:
				{
					renderer.setOverrideBlockTexture(Block.getBlockFromName("minecraft:lava").getBlockTextureFromSide(0));
					break;
				}
				case 1:
				{
					renderer.setOverrideBlockTexture(Block.getBlockFromName("minecraft:water").getBlockTextureFromSide(0));
					break;
				}
				case 2:
				{
					renderer.setOverrideBlockTexture(Blocks.dirt.getIcon(1, 2));
					break;
				}
				case 3:
				{
					renderer.setOverrideBlockTexture(Blocks.quartz_block.getIcon(1, 0));
					break;
				}
			}
			GL11.glScalef(0.2F, 0.2F, 0.2F);
			renderer.renderBlockAsItem(Block.getBlockFromName("minecraft:glass"), 1, 1);
		}
		if(block instanceof BlockCorruption_Light)
		{
			GL11.glPushMatrix();
			renderer.setOverrideBlockTexture(block.getIcon(0, metadata));
			GL11.glScalef(1F, 1F, 0.01F);
			renderer.renderBlockAsItem(Blocks.ice, 1, 1);
			GL11.glPopMatrix();
		}
		if(block instanceof BlockRayTower)
		{
			if(metadata == 0)
			{
				renderer.renderAllFaces = true;
				renderInventoryBlockWithSize(renderer,Blocks.glass,BlocksCore.fortifiedGlass.getBlockTextureFromSide(0),0.81F,1.1F,0.81F,0,0,0);
				renderInventoryBlockWithSize(renderer,Blocks.glass,Blocks.anvil.getIcon(1, 2),0.8F,0.1F,0.8F,0,-0.5F,0);
				renderInventoryBlockWithSize(renderer,Blocks.glass,Blocks.anvil.getIcon(1, 2),0.1F,1.2F,0.1F,0.35F,0.1F,0.35F);
				renderInventoryBlockWithSize(renderer,Blocks.glass,Blocks.anvil.getIcon(1, 2),0.1F,1.2F,0.1F,-0.35F,0.1F,0.35F);
				renderInventoryBlockWithSize(renderer,Blocks.glass,Blocks.anvil.getIcon(1, 2),0.1F,1.2F,0.1F,0.35F,0.1F,-0.35F);
				renderInventoryBlockWithSize(renderer,Blocks.glass,Blocks.anvil.getIcon(1, 2),0.1F,1.2F,0.1F,-0.35F,0.1F,-0.35F);
				renderInventoryBlockWithSize(renderer,Blocks.glass,Blocks.anvil.getIcon(1, 2),0.2F,1.6F,0.2F,0,0.3F,0);
				renderInventoryBlockWithSize(renderer,Blocks.glass,Blocks.anvil.getIcon(1, 2),0.7F,0.1F,0.7F,0,0.5F,0);
				renderer.renderAllFaces = false;
			}
		}
		if(block instanceof BlockMoonWell)
		{
			renderInventoryBlockWithSize(renderer,Blocks.glass,BlocksCore.moonWell.getIcon(0, 0),1F,0.1F,1F,0,-0.5F,0);
			renderInventoryBlockWithSize(renderer,Blocks.glass,BlocksCore.moonWell.getIcon(0, 0),0.1F,0.9F,1F,0.45F,0F,0);
			renderInventoryBlockWithSize(renderer,Blocks.glass,BlocksCore.moonWell.getIcon(0, 0),0.1F,0.9F,1F,-0.45F,0F,0);
			renderInventoryBlockWithSize(renderer,Blocks.glass,BlocksCore.moonWell.getIcon(0, 0),0.9F,0.9F,0.1F,0,0F,0.45F);
			renderInventoryBlockWithSize(renderer,Blocks.glass,BlocksCore.moonWell.getIcon(0, 0),0.9F,0.9F,0.1F,0,0F,-0.45F);
			renderInventoryBlockWithSize(renderer,Blocks.glass,Blocks.stained_glass.getIcon(0, 2),0.9F,0.7F,0.9F,0,-0.17F,0F);
		}
		if(block instanceof BlockMIM)
		{
			renderInventoryBlockWithSize(renderer,Blocks.glass,BlocksCore.mim.getIcon(0, 0),1F,0.1F,1F,0,-0.5F,0);
			renderInventoryBlockWithSize(renderer,Blocks.glass,BlocksCore.mim.getIcon(0, 0),0.1F,1F,0.1F,0,0F,0);
		}
		if(block instanceof BlockMagicalDisplay)
		{
			renderInventoryBlockWithSize(renderer,Blocks.glass,BlocksCore.magicalDisplay.getIcon(0, 0),1F,1F,0.1F,0,0F,0);
		}
		
		if(block instanceof BlockMagicalQuarry)
		{
			renderInventoryBlockWithSize(renderer,Blocks.glass,BlocksCore.fortifiedGlass.getIcon(0, 0),1F,1F,1F,0,0,0);
			renderInventoryBlockWithSize(renderer,Blocks.glass,BlocksCore.fortifiedStone.getIcon(0, 0),0.99F,0.2F,0.99F,0,0.4F,0);
			renderInventoryBlockWithSize(renderer,Blocks.glass,BlocksCore.fortifiedStone.getIcon(0, 0),0.2F,0.8F,0.2F,0.39F,0,0.39F);
			renderInventoryBlockWithSize(renderer,Blocks.glass,BlocksCore.fortifiedStone.getIcon(0, 0),0.2F,0.8F,0.2F,-0.39F,0,0.39F);
			renderInventoryBlockWithSize(renderer,Blocks.glass,BlocksCore.fortifiedStone.getIcon(0, 0),0.2F,0.8F,0.2F,0.39F,0,-0.39F);
			renderInventoryBlockWithSize(renderer,Blocks.glass,BlocksCore.fortifiedStone.getIcon(0, 0),0.2F,0.8F,0.2F,-0.39F,0,-0.39F);
			renderInventoryBlockWithSize(renderer,Blocks.glass,BlocksCore.magicalQuarry.getIcon(0, 0),0.2F,0.5F,0.2F,0,0.1F,0);
			renderInventoryBlockWithSize(renderer,Blocks.glass,Blocks.beacon.getIcon(0, 0),0.3F,0.3F,0.3F,0,-0.1F,0);
		}
		if(block instanceof BlockMonsterHolder)
		{
			renderInventoryBlockWithSize(renderer,Blocks.glass,BlocksCore.fortifiedGlass.getIcon(0, 0),1F,1F,1F,0,0,0);
			renderInventoryBlockWithSize(renderer,Blocks.glass,BlocksCore.fortifiedStone.getIcon(0, 0),0.99F,0.2F,0.99F,0,-0.4F,0);
			renderInventoryBlockWithSize(renderer,Blocks.glass,BlocksCore.magicalQuarry.getIcon(0, 0),0.2F,0.5F,0.2F,0,-0.1F,0);
			renderInventoryBlockWithSize(renderer,Blocks.glass,Blocks.beacon.getIcon(0, 0),0.3F,0.3F,0.3F,0,0.1F,0);
		}
		if(block instanceof BlockMRULevitator)
		{
			renderInventoryBlockWithSize(renderer,Blocks.glass,BlocksCore.fortifiedStone.getIcon(0, 0),0.99F,0.2F,0.99F,0,0.4F,0);
			renderInventoryBlockWithSize(renderer,Blocks.glass,BlocksCore.fortifiedStone.getIcon(0, 0),0.2F,0.8F,0.2F,0.39F,0,0.39F);
			renderInventoryBlockWithSize(renderer,Blocks.glass,BlocksCore.fortifiedStone.getIcon(0, 0),0.2F,0.8F,0.2F,-0.39F,0,0.39F);
			renderInventoryBlockWithSize(renderer,Blocks.glass,BlocksCore.fortifiedStone.getIcon(0, 0),0.2F,0.8F,0.2F,0.39F,0,-0.39F);
			renderInventoryBlockWithSize(renderer,Blocks.glass,BlocksCore.fortifiedStone.getIcon(0, 0),0.2F,0.8F,0.2F,-0.39F,0,-0.39F);
			renderInventoryBlockWithSize(renderer,Blocks.glass,Blocks.beacon.getIcon(0, 0),0.2F,0.5F,0.2F,0,0.1F,0);
			renderInventoryBlockWithSize(renderer,Blocks.glass,BlocksCore.magicalQuarry.getIcon(0, 0),0.3F,0.3F,0.3F,0,-0.1F,0);
		}
		if(block instanceof BlockMRUSpreader)
		{
			renderInventoryBlockWithSize(renderer,Blocks.glass,BlocksCore.magicalQuarry.getIcon(0, 0),0.4F,0.5F,0.4F,0,0.1F,0);
			renderInventoryBlockWithSize(renderer,Blocks.glass,BlocksCore.magicalQuarry.getIcon(0, 0),0.2F,1F,0.2F,0,-0.3F,0);
		}
		if(block instanceof BlockRedstoneTransmitter)
		{
			renderInventoryBlockWithSize(renderer,Blocks.glass,BlocksCore.fortifiedStone.getIcon(0, 0),0.8F,0.2F,0.8F,0,-0.3F,0);
			GL11.glPushMatrix();
				float offsetY = 0.1F;
				renderInventoryBlockWithSize(renderer,Blocks.redstone_torch,Blocks.redstone_torch.getIcon(0, 0),0.0001F,1F,1F,0.05F,offsetY,0);
				renderInventoryBlockWithSize(renderer,Blocks.redstone_torch,Blocks.redstone_torch.getIcon(0, 0),0.0001F,1F,1F,-0.05F,offsetY,0);
				renderInventoryBlockWithSize(renderer,Blocks.redstone_torch,Blocks.redstone_torch.getIcon(0, 0),1F,1F,0F,0,offsetY,0.05F);
				renderInventoryBlockWithSize(renderer,Blocks.redstone_torch,Blocks.redstone_torch.getIcon(0, 0),1F,1F,0F,0,offsetY,-0.05F);
			GL11.glPopMatrix();
		}
		if(block instanceof BlockMRUCoil_Coil)
		{
			renderInventoryBlockWithSize(renderer,Blocks.glass,Blocks.end_portal_frame.getIcon(1, 0),0.4F,0.2F,0.4F,0,-0.4F,0);
			renderInventoryBlockWithSize(renderer,Blocks.glass,BlocksCore.magicPlating.getIcon(1, 0),0.1F,0.7F,0.1F,0,-0.0F,0);
			renderInventoryBlockWithSize(renderer,Blocks.glass,BlocksCore.ecEjector.getIcon(1, 0),0.3F,0.3F,0.3F,0,0.5F,0);
			
			renderInventoryBlockWithSize(renderer,Blocks.glass,BlocksCore.magicPlating.getIcon(1, 0),0.1F,0.1F,1F,0.5F,-0.4F,0);
			renderInventoryBlockWithSize(renderer,Blocks.glass,BlocksCore.magicPlating.getIcon(1, 0),0.1F,0.1F,1F,-0.5F,-0.4F,0);
			renderInventoryBlockWithSize(renderer,Blocks.glass,BlocksCore.magicPlating.getIcon(1, 0),1F,0.1F,0.1F,0F,-0.4F,0.5F);
			renderInventoryBlockWithSize(renderer,Blocks.glass,BlocksCore.magicPlating.getIcon(1, 0),1F,0.1F,0.1F,0F,-0.4F,-0.5F);
			
			renderInventoryBlockWithSize(renderer,Blocks.glass,BlocksCore.magicPlating.getIcon(1, 0),0.1F,0.1F,1F,0.5F,-0.2F,0);
			renderInventoryBlockWithSize(renderer,Blocks.glass,BlocksCore.magicPlating.getIcon(1, 0),0.1F,0.1F,1F,-0.5F,-0.2F,0);
			renderInventoryBlockWithSize(renderer,Blocks.glass,BlocksCore.magicPlating.getIcon(1, 0),1F,0.1F,0.1F,0F,-0.2F,0.5F);
			renderInventoryBlockWithSize(renderer,Blocks.glass,BlocksCore.magicPlating.getIcon(1, 0),1F,0.1F,0.1F,0F,-0.2F,-0.5F);
			
			renderInventoryBlockWithSize(renderer,Blocks.glass,BlocksCore.magicPlating.getIcon(1, 0),0.1F,0.1F,0.9F,0.4F,-0F,0);
			renderInventoryBlockWithSize(renderer,Blocks.glass,BlocksCore.magicPlating.getIcon(1, 0),0.1F,0.1F,0.9F,-0.4F,-0F,0);
			renderInventoryBlockWithSize(renderer,Blocks.glass,BlocksCore.magicPlating.getIcon(1, 0),0.9F,0.1F,0.1F,0F,-0F,0.4F);
			renderInventoryBlockWithSize(renderer,Blocks.glass,BlocksCore.magicPlating.getIcon(1, 0),0.9F,0.1F,0.1F,0F,-0F,-0.4F);
			
			renderInventoryBlockWithSize(renderer,Blocks.glass,BlocksCore.magicPlating.getIcon(1, 0),0.1F,0.1F,0.6F,0.3F,0.2F,0);
			renderInventoryBlockWithSize(renderer,Blocks.glass,BlocksCore.magicPlating.getIcon(1, 0),0.1F,0.1F,0.6F,-0.3F,0.2F,0);
			renderInventoryBlockWithSize(renderer,Blocks.glass,BlocksCore.magicPlating.getIcon(1, 0),0.6F,0.1F,0.1F,0F,0.2F,0.3F);
			renderInventoryBlockWithSize(renderer,Blocks.glass,BlocksCore.magicPlating.getIcon(1, 0),0.6F,0.1F,0.1F,0F,0.2F,-0.3F);
			
			renderInventoryBlockWithSize(renderer,Blocks.glass,BlocksCore.magicPlating.getIcon(1, 0),0.1F,0.1F,0.3F,0.2F,0.4F,0);
			renderInventoryBlockWithSize(renderer,Blocks.glass,BlocksCore.magicPlating.getIcon(1, 0),0.1F,0.1F,0.3F,-0.2F,0.4F,0);
			renderInventoryBlockWithSize(renderer,Blocks.glass,BlocksCore.magicPlating.getIcon(1, 0),0.3F,0.1F,0.1F,0F,0.4F,0.2F);
			renderInventoryBlockWithSize(renderer,Blocks.glass,BlocksCore.magicPlating.getIcon(1, 0),0.3F,0.1F,0.1F,0F,0.4F,-0.2F);
		}
		
		if(block instanceof BlockCorruptionCleaner)
		{
			renderInventoryBlockWithSize(renderer,Blocks.glass,BlocksCore.fortifiedGlass.getIcon(0, 0),1F,1F,1F,0F,0F,0);
			renderInventoryBlockWithSize(renderer,Blocks.glass,BlocksCore.fortifiedStone.getIcon(0, 0),0.999F,0.2F,0.999F,0F,-0.37F,0);
			renderInventoryBlockWithSize(renderer,Blocks.glass,BlocksCore.fortifiedStone.getIcon(0, 0),0.999F,0.2F,0.999F,0F,0.39F,0);
			renderInventoryBlockWithSize(renderer,Blocks.glass,BlocksCore.fortifiedStone.getIcon(0, 0),0.3F,0.2F,0.3F,0F,-0.19F,0);
			renderInventoryBlockWithSize(renderer,Blocks.glass,BlocksCore.fortifiedStone.getIcon(0, 0),0.3F,0.2F,0.3F,0F,0.19F,0);
			renderInventoryBlockWithSize(renderer,Blocks.glass,Blocks.diamond_block.getIcon(0, 0),0.4F,0.2F,0.4F,0F,0F,0);
		}
		
		if(block instanceof BlockReactorSupport)
		{
			renderInventoryBlockWithSize(renderer,Blocks.glass,BlocksCore.magicPlating.getIcon(1, 0),1F,0.2F,1F,0,-0.4F,0);
			renderInventoryBlockWithSize(renderer,Blocks.glass,BlocksCore.magicPlating.getIcon(1, 0),0.7F,0.6F,0.7F,0,-0.1F,0);
			renderInventoryBlockWithSize(renderer,Blocks.glass,BlocksCore.magicPlating.getIcon(1, 0),0.5F,0.5F,0.5F,0,0.4F,0);
		
			renderInventoryBlockWithSize(renderer,Blocks.glass,BlocksCore.magicPlating.getIcon(1, 0),0.1F,0.1F,0.9F,0.4F,0.2F,0);
			renderInventoryBlockWithSize(renderer,Blocks.glass,BlocksCore.magicPlating.getIcon(1, 0),0.1F,0.1F,0.9F,-0.4F,0.2F,0);
			renderInventoryBlockWithSize(renderer,Blocks.glass,BlocksCore.magicPlating.getIcon(1, 0),0.9F,0.1F,0.1F,0F,0.2F,0.4F);
			renderInventoryBlockWithSize(renderer,Blocks.glass,BlocksCore.magicPlating.getIcon(1, 0),0.9F,0.1F,0.1F,0F,0.2F,-0.4F);
			
			renderInventoryBlockWithSize(renderer,Blocks.glass,BlocksCore.magicPlating.getIcon(1, 0),0.1F,0.1F,0.9F,0.4F,0.4F,0);
			renderInventoryBlockWithSize(renderer,Blocks.glass,BlocksCore.magicPlating.getIcon(1, 0),0.1F,0.1F,0.9F,-0.4F,0.4F,0);
			renderInventoryBlockWithSize(renderer,Blocks.glass,BlocksCore.magicPlating.getIcon(1, 0),0.9F,0.1F,0.1F,0F,0.4F,0.4F);
			renderInventoryBlockWithSize(renderer,Blocks.glass,BlocksCore.magicPlating.getIcon(1, 0),0.9F,0.1F,0.1F,0F,0.4F,-0.4F);
			
			renderInventoryBlockWithSize(renderer,Blocks.glass,BlocksCore.magicPlating.getIcon(1, 0),0.1F,0.1F,0.9F,0.4F,0.6F,0);
			renderInventoryBlockWithSize(renderer,Blocks.glass,BlocksCore.magicPlating.getIcon(1, 0),0.1F,0.1F,0.9F,-0.4F,0.6F,0);
			renderInventoryBlockWithSize(renderer,Blocks.glass,BlocksCore.magicPlating.getIcon(1, 0),0.9F,0.1F,0.1F,0F,0.6F,0.4F);
			renderInventoryBlockWithSize(renderer,Blocks.glass,BlocksCore.magicPlating.getIcon(1, 0),0.9F,0.1F,0.1F,0F,0.6F,-0.4F);
		}
		
		if(block instanceof BlockFancy)
		{
			renderInventoryBlockWithSize(renderer,Blocks.glass,block.getIcon(1, 0),0.99F,0.99F,0.99F,0.0F,0.0F,0);
			renderInventoryBlockWithSize(renderer,Blocks.glass,((BlockFancy)block).overlayIcons[metadata],1,1,1,0.0F,0.0F,0);
		}
		renderer.clearOverrideBlockTexture();
	}
	
	public void renderInventoryBlockWithSize(RenderBlocks renderer, Block b, IIcon icon, float scaleX, float scaleY,float scaleZ, float offsetX, float offsetY, float offsetZ)
	{
		renderer.setOverrideBlockTexture(icon);
		GL11.glPushMatrix();
		GL11.glTranslatef(offsetX, offsetY, offsetZ);
		GL11.glScalef(scaleX, scaleY, scaleZ);
		renderer.renderBlockAsItem(Blocks.glass, 1, 1);
		GL11.glPopMatrix();
	}

	@Override
	public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z,
			Block block, int modelId, RenderBlocks renderer) {
		int metadata = world.getBlockMetadata(x, y, z);
		if(block instanceof BlockRedstoneTransmitter)
		{
			renderer.setOverrideBlockTexture(BlocksCore.fortifiedStone.getBlockTextureFromSide(0));
			IIcon redstoneIcon = BlocksCore.demonicPlating.getBlockTextureFromSide(0);
			switch(metadata)
			{
				case 0:
				{
					renderer.setRenderBounds(0.2D, 0, 0.2D, 0.8D, 0.2D, 0.8D);
					renderer.renderStandardBlock(Blocks.glass, x, y, z);
					renderer.setOverrideBlockTexture(Blocks.planks.getBlockTextureFromSide(0));
					renderer.setRenderBounds(0.42D, 0.2D, 0.42D, 0.58D, 0.4D, 0.58D);
					renderer.renderStandardBlock(Blocks.planks, x, y, z);
					renderer.setOverrideBlockTexture(redstoneIcon);
					renderer.setRenderBounds(0.4D, 0.4D, 0.4D, 0.6D, 0.6D, 0.6D);
					renderer.renderStandardBlock(Blocks.planks, x, y, z);
					break;
				}
				case 1:
				{
					renderer.setRenderBounds(0.2D, 0.8D, 0.2D, 0.8D, 1D, 0.8D);
					renderer.renderStandardBlock(Blocks.glass, x, y, z);
					renderer.setOverrideBlockTexture(Blocks.planks.getBlockTextureFromSide(0));
					renderer.setRenderBounds(0.42D, 0.6D, 0.42D, 0.58D, 0.8D, 0.58D);
					renderer.renderStandardBlock(Blocks.planks, x, y, z);
					renderer.setOverrideBlockTexture(redstoneIcon);
					renderer.setRenderBounds(0.4D, 0.4D, 0.4D, 0.6D, 0.6D, 0.6D);
					renderer.renderStandardBlock(Blocks.planks, x, y, z);
					break;
				}
				case 5:
				{
					renderer.setRenderBounds(0.8D, 0.2D, 0.2D, 1D, 0.8D, 0.8D);
					renderer.renderStandardBlock(Blocks.glass, x, y, z);
					renderer.setOverrideBlockTexture(Blocks.planks.getBlockTextureFromSide(0));
					renderer.setRenderBounds(0.6D, 0.42D, 0.42D, 0.8D, 0.58D, 0.58D);
					renderer.renderStandardBlock(Blocks.planks, x, y, z);
					renderer.setOverrideBlockTexture(redstoneIcon);
					renderer.setRenderBounds(0.4D, 0.4D, 0.4D, 0.6D, 0.6D, 0.6D);
					renderer.renderStandardBlock(Blocks.planks, x, y, z);
					break;
				}
				case 4:
				{
					renderer.setRenderBounds(0D, 0.2D, 0.2D, 0.2D, 0.8D, 0.8D);
					renderer.renderStandardBlock(Blocks.glass, x, y, z);
					renderer.setOverrideBlockTexture(Blocks.planks.getBlockTextureFromSide(0));
					renderer.setRenderBounds(0.2D, 0.42D, 0.42D, 0.4D, 0.58D, 0.58D);
					renderer.renderStandardBlock(Blocks.planks, x, y, z);
					renderer.setOverrideBlockTexture(redstoneIcon);
					renderer.setRenderBounds(0.4D, 0.4D, 0.4D, 0.6D, 0.6D, 0.6D);
					renderer.renderStandardBlock(Blocks.planks, x, y, z);
					break;
				}
				case 3:
				{
					renderer.setRenderBounds(0.2D, 0.2D, 0.8D, 0.8D, 0.8D, 1D);
					renderer.renderStandardBlock(Blocks.glass, x, y, z);
					renderer.setOverrideBlockTexture(Blocks.planks.getBlockTextureFromSide(0));
					renderer.setRenderBounds(0.42D, 0.42D, 0.6D, 0.58D, 0.58D, 0.8D);
					renderer.renderStandardBlock(Blocks.planks, x, y, z);
					renderer.setOverrideBlockTexture(redstoneIcon);
					renderer.setRenderBounds(0.4D, 0.4D, 0.4D, 0.6D, 0.6D, 0.6D);
					renderer.renderStandardBlock(Blocks.planks, x, y, z);
					break;
				}
				case 2:
				{
					renderer.setRenderBounds(0.2D, 0.2D, 0D, 0.8D, 0.8D, 0.2D);
					renderer.renderStandardBlock(Blocks.glass, x, y, z);
					renderer.setOverrideBlockTexture(Blocks.planks.getBlockTextureFromSide(0));
					renderer.setRenderBounds(0.42D, 0.42D, 0.2D, 0.58D, 0.58D, 0.4D);
					renderer.renderStandardBlock(Blocks.planks, x, y, z);
					renderer.setOverrideBlockTexture(redstoneIcon);
					renderer.setRenderBounds(0.4D, 0.4D, 0.4D, 0.6D, 0.6D, 0.6D);
					renderer.renderStandardBlock(Blocks.planks, x, y, z);
					break;
				}
			}
		}
		if(block instanceof BlockDrops)
		{
			switch(metadata)
			{
				case 0:
				{
					renderer.setOverrideBlockTexture(Block.getBlockFromName("minecraft:lava").getBlockTextureFromSide(0));
					break;
				}
				case 1:
				{
					renderer.setOverrideBlockTexture(Block.getBlockFromName("minecraft:water").getBlockTextureFromSide(0));
					break;
				}
				case 2:
				{
					renderer.setOverrideBlockTexture(Blocks.dirt.getIcon(1, 2));
					break;
				}
				case 3:
				{
					renderer.setOverrideBlockTexture(Blocks.quartz_block.getIcon(1, 0));
					break;
				}
			}
			renderer.setRenderBounds(0.8D, 0, 0.1D, 0.9D, 0.1D, 0.3D);
			renderer.renderStandardBlockWithColorMultiplier(Block.getBlockFromName("minecraft:glass"), x, y, z, 1.0F, 1.0F, 1.0F);
			renderer.setRenderBounds(0.5D, 0, 0.3D, 0.7D, 0.1D, 0.4D);
			renderer.renderStandardBlockWithColorMultiplier(Block.getBlockFromName("minecraft:glass"), x, y, z, 1.0F, 1.0F, 1.0F);
			renderer.setRenderBounds(0.1D, 0, 0.2D, 0.2D, 0.1D, 0.4D);
			renderer.renderStandardBlockWithColorMultiplier(Block.getBlockFromName("minecraft:glass"), x, y, z, 1.0F, 1.0F, 1.0F);
			renderer.setRenderBounds(0.8D, 0, 0.8D, 0.9D, 0.1D, 0.9D);
			renderer.renderStandardBlockWithColorMultiplier(Block.getBlockFromName("minecraft:glass"), x, y, z, 1.0F, 1.0F, 1.0F);
			renderer.setRenderBounds(0.7D, 0, 0.6D, 0.9D, 0.1D, 0.7D);
			renderer.renderStandardBlockWithColorMultiplier(Block.getBlockFromName("minecraft:glass"), x, y, z, 1.0F, 1.0F, 1.0F);
			renderer.setRenderBounds(0.2D, 0, 0.8D, 0.3D, 0.1D, 0.9D);
			renderer.renderStandardBlockWithColorMultiplier(Block.getBlockFromName("minecraft:glass"), x, y, z, 1.0F, 1.0F, 1.0F);
			renderer.setRenderBounds(0.3D, 0, 0.5D, 0.5D, 0.1D, 0.7D);
			renderer.renderStandardBlockWithColorMultiplier(Block.getBlockFromName("minecraft:glass"), x, y, z, 1.0F, 1.0F, 1.0F);
		}
		
		if(block instanceof BlockDropsOre)
		{
	        GL11.glPushMatrix();
	        BlockDropsOre dr = (BlockDropsOre) block;
	        renderer.setOverrideBlockTexture(metadata < 5 ? dr.icons[0] : metadata >= 5 && metadata < 10 ? dr.icons[1] : dr.icons[2]);
	        float s = 1.001F;
	        renderer.setRenderBounds(1-s, 1-s, 1-s, s, s, s);
	        renderer.renderStandardBlock(Blocks.stone, x, y, z);
	       
	        float r = 1, g = 1, b = 1;
	        switch(metadata % 5)
	        {
	        	case 0:
	        	{
	        		r = 1;
	        		g = 0;
	        		b = 1;
	        		break;
	        	}
	        	case 1:
	        	{
	        		r = 1;
	        		g = 0;
	        		b = 0;
	        		break;
	        	}
	        	case 2:
	        	{
	        		r = 0;
	        		g = 0;
	        		b = 1;
	        		break;
	        	}
	        	case 3:
	        	{
	        		r = 0.4F;
	        		g = 0.3F;
	        		b = 0.3F;
	        		break;
	        	}
	        	case 4:
	        	{
	        		r = 1;
	        		g = 1;
	        		b = 1;
	        		break;
	        	}
	        }
	       
	        renderer.setOverrideBlockTexture(dr.icons[3]);
	        
	       
	        renderer.renderAllFaces = false;
			renderTesselatedTextureByPoints(world,block,renderer,x,y,z,r,g,b,1D,127,dr.icons[3],
					1D,1D,1D, //SOUTH-TOP-EAST
					0D,1D,1D, //SOUTH-TOP-WEST
					1D,1D,0D, //SOUTH-TOP-EAST
					0D,1D,0D, //NORTH-TOP-WEST
					1D,0D,1D, //SOUTH-BOT-EAST
					0D,0D,1D, //SOUTH-BOT-WEST
					1D,0D,0D, //SOUTH-BOT-EAST
					0D,0D,0D  //NORTH-BOT-WEST
					);
	        //renderer.renderStandardBlockWithColorMultiplier(Blocks.stone, x, y, z, r, g, b);
	        GL11.glPopMatrix();
		}
		
		if(block instanceof BlockMagicalDisplay)
		{
			renderer.setOverrideBlockTexture(block.getBlockTextureFromSide(0));
			switch(metadata)
			{
				case 0:
				{
					renderer.setRenderBounds(0, 0.95D, 0, 1, 1D, 1);
					break;
				}
				case 1:
				{
					renderer.setRenderBounds(0, 0D, 0, 1, 0.05D, 1);
					break;
				}
				case 2:
				{
					renderer.setRenderBounds(0, 0D, 0.95D, 1, 1D, 1);
					break;
				}
				case 3:
				{
					renderer.setRenderBounds(0, 0D, 0D, 1, 1D, 0.05D);
					break;
				}
				case 4:
				{
					renderer.setRenderBounds(0.95D, 0D, 0D, 1, 1D, 1);
					break;
				}
				case 5:
				{
					renderer.setRenderBounds(0D, 0D, 0D, 0.05D, 1D, 1);
					break;
				}
			}
			renderer.renderStandardBlock(Blocks.glass, x, y, z);
		}
		if(block instanceof BlockRayTower)
		{
			if(world.getBlockMetadata(x, y, z) == 0)
			{
				renderer.renderAllFaces = true;
				renderer.setOverrideBlockTexture(BlocksCore.fortifiedGlass.getBlockTextureFromSide(0));
				renderer.setRenderBounds(0.19D, 0, 0.19D, 0.81D, 1.2D, 0.81D);
				renderer.renderStandardBlock(Blocks.glass, x, y, z);
				renderer.setOverrideBlockTexture(Blocks.anvil.getIcon(1, 2));
				renderer.setRenderBounds(0.2D, 0, 0.2D, 0.8D, 0.1D, 0.8D);
				renderer.renderStandardBlock(Blocks.glass, x, y, z);
				renderer.setRenderBounds(0.1D, 0, 0.1D, 0.2D, 1.3D, 0.2D);
				renderer.renderStandardBlock(Blocks.glass, x, y, z);
				renderer.setRenderBounds(0.8D, 0, 0.1D, 0.9D, 1.3D, 0.2D);
				renderer.renderStandardBlock(Blocks.glass, x, y, z);
				renderer.setRenderBounds(0.1D, 0, 0.8D, 0.2D, 1.3D, 0.9D);
				renderer.renderStandardBlock(Blocks.glass, x, y, z);
				renderer.setRenderBounds(0.8D, 0, 0.8D, 0.9D, 1.3D, 0.9D);
				renderer.renderStandardBlock(Blocks.stone, x, y, z);
				renderer.setRenderBounds(0.4D, 0.1D, 0.4D, 0.6D, 1.7D, 0.6D);
				renderer.renderStandardBlock(Blocks.stone, x, y, z);
				renderer.setRenderBounds(0.2D, 1D, 0.2D, 0.8D, 1.2D, 0.8D);
				renderer.renderStandardBlock(Blocks.glass, x, y, z);
				renderer.renderAllFaces = false;
			}
		}
		if(block instanceof BlockCorruption_Light)
		{
			renderer.setOverrideBlockTexture(block.getIcon(0, metadata));
			Block b  = world.getBlock(x, y-1, z);
			if(b.isBlockNormalCube())
			{
				renderer.setRenderBounds(-0.01D, -0.01D, -0.01D, 1.01D, 1.01D, 1.01D);
				renderer.renderStandardBlock(Blocks.glass, x, y-1, z);
			}
			b  = world.getBlock(x, y+1, z);
			if(b.isBlockNormalCube())
			{
				renderer.setRenderBounds(-0.01D, -0.01D, -0.01D, 1.01D, 1.01D, 1.01D);
				renderer.renderStandardBlock(Blocks.glass, x, y+1, z);
			}
			b  = world.getBlock(x+1, y, z);
			if(b.isBlockNormalCube())
			{
				renderer.setRenderBounds(-0.01D, -0.01D, -0.01D, 1.01D, 1.01D, 1.01D);
				renderer.renderStandardBlock(Blocks.glass, x+1, y, z);
			}
			b  = world.getBlock(x-1, y, z);
			if(b.isBlockNormalCube())
			{
				renderer.setRenderBounds(-0.01D, -0.01D, -0.01D, 1.01D, 1.01D, 1.01D);
				renderer.renderStandardBlock(Blocks.glass, x-1, y, z);
			}
			b  = world.getBlock(x, y, z-1);
			if(b.isBlockNormalCube())
			{
				renderer.setRenderBounds(-0.01D, -0.01D, -0.01D, 1.01D, 1.01D, 1.01D);
				renderer.renderStandardBlock(Blocks.glass, x, y, z-1);
			}
			b  = world.getBlock(x, y, z+1);
			if(b.isBlockNormalCube())
			{
				renderer.setRenderBounds(-0.01D, -0.01D, -0.01D, 1.01D, 1.01D, 1.01D);
				renderer.renderStandardBlock(Blocks.glass, x, y, z+1);
			}
			renderer.setRenderBounds(0.999D, 0.999D, 0.999D, 1.0D, 1.0D, 1.0D);
			renderer.renderStandardBlock(Blocks.glass, x, y, z);
		}
		if(block instanceof BlockMoonWell)
		{
			renderer.renderAllFaces = true;
			renderer.setOverrideBlockTexture(block.getIcon(0, metadata));
			renderer.setRenderBounds(0.9D,0,0,1,1,1);
			renderer.renderStandardBlock(Blocks.glass, x, y, z);
			renderer.setRenderBounds(0.0D,0,0,0.1D,1,1);
			renderer.renderStandardBlock(Blocks.glass, x, y, z);
			renderer.setRenderBounds(0.1D,0,0.9D,0.9D,1,1);
			renderer.renderStandardBlock(Blocks.glass, x, y, z);
			renderer.setRenderBounds(0.1D,0,0.0D,0.9D,1,0.1D);
			renderer.renderStandardBlock(Blocks.glass, x, y, z);
			renderer.setRenderBounds(0.1D,0,0.1D,0.9D,0.1D,0.9D);
			renderer.renderStandardBlock(Blocks.glass, x, y, z);
			renderer.setRenderBounds(0.1D,0,0.1D,0.9D,0.1D,0.9D);
			renderer.renderStandardBlock(Blocks.glass, x, y, z);
			renderer.setOverrideBlockTexture(Blocks.beacon.getIcon(1, 2));
			renderer.setRenderBounds(0.3D,0.1D,0.3D,0.4D,0.7D,0.4D);
			renderer.renderStandardBlock(Blocks.glass, x, y, z);
			renderer.setRenderBounds(0.3D,0.1D,0.6D,0.4D,0.7D,0.7D);
			renderer.renderStandardBlock(Blocks.glass, x, y, z);
			renderer.setRenderBounds(0.6D,0.1D,0.3D,0.7D,0.7D,0.4D);
			renderer.renderStandardBlock(Blocks.glass, x, y, z);
			renderer.setRenderBounds(0.6D,0.1D,0.6D,0.7D,0.7D,0.7D);
			renderer.renderStandardBlock(Blocks.glass, x, y, z);
			renderer.setOverrideBlockTexture(Blocks.stained_glass.getIcon(1, 2));
			renderer.setRenderBounds(0.1D,0.1D,0.1D,0.9D,0.8D,0.9D);
			renderer.renderStandardBlock(Blocks.glass, x, y, z);
			renderer.renderAllFaces = false;
		}
		if(block instanceof BlockMagicalQuarry)
		{
			renderer.renderAllFaces = true;
			renderer.setOverrideBlockTexture(BlocksCore.fortifiedGlass.getIcon(0, 0));
			renderer.setRenderBounds(0,0,0,1,1,1);
			renderer.renderStandardBlock(Blocks.glass, x, y, z);
			renderer.setOverrideBlockTexture(BlocksCore.fortifiedStone.getIcon(0, 0));
			renderer.setRenderBounds(0,0.8D,0,1,1,1);
			renderer.renderStandardBlock(Blocks.glass, x, y, z);
			renderer.setRenderBounds(0.8D,0.2D,0.8D,0.99D,0.8D,0.99D);
			renderer.renderStandardBlock(Blocks.glass, x, y, z);
			renderer.setRenderBounds(0.01D,0.2D,0.8D,0.2D,0.8D,0.99D);
			renderer.renderStandardBlock(Blocks.glass, x, y, z);
			renderer.setRenderBounds(0.8D,0.2D,0.01D,0.99D,0.8D,0.2D);
			renderer.renderStandardBlock(Blocks.glass, x, y, z);
			renderer.setRenderBounds(0.01D,0.2D,0.01D,0.2D,0.8D,0.2D);
			renderer.renderStandardBlock(Blocks.glass, x, y, z);
			renderer.setOverrideBlockTexture(BlocksCore.magicalQuarry.getIcon(0, 0));
			renderer.setRenderBounds(0.4D,0.4D,0.4D,0.6D,0.8D,0.6D);
			renderer.renderStandardBlock(Blocks.glass, x, y, z);
			renderer.setOverrideBlockTexture(Blocks.beacon.getIcon(0, 0));
			renderer.setRenderBounds(0.3D,0.2D,0.3D,0.7D,0.6D,0.7D);
			renderer.renderStandardBlock(Blocks.glass, x, y, z);
			renderer.setOverrideBlockTexture(BlocksCore.magicalQuarry.getIcon(0, 0));
			renderer.setRenderBounds(0.8D,0.0D,0.8D,0.99D,0.2D,0.99D);
			renderer.renderStandardBlock(Blocks.glass, x, y, z);
			renderer.setRenderBounds(0.01D,0.0D,0.8D,0.2D,0.2D,0.99D);
			renderer.renderStandardBlock(Blocks.glass, x, y, z);
			renderer.setRenderBounds(0.8D,0.0D,0.01D,0.99D,0.2D,0.2D);
			renderer.renderStandardBlock(Blocks.glass, x, y, z);
			renderer.setRenderBounds(0.01D,0.0D,0.01D,0.2D,0.2D,0.2D);
			renderer.renderStandardBlock(Blocks.glass, x, y, z);
			renderer.renderAllFaces = false;
		}
		if(block instanceof BlockMonsterHolder)
		{
			renderer.renderAllFaces = true;
			renderer.setOverrideBlockTexture(BlocksCore.fortifiedGlass.getIcon(0, 0));
			renderer.setRenderBounds(0,0,0,1,1,1);
			renderer.renderStandardBlock(Blocks.glass, x, y, z);
			renderer.setOverrideBlockTexture(BlocksCore.fortifiedStone.getIcon(0, 0));
			renderer.setRenderBounds(0,0.0D,0,1,0.2D,1);
			renderer.renderStandardBlock(Blocks.glass, x, y, z);
			renderer.setOverrideBlockTexture(BlocksCore.magicalQuarry.getIcon(0, 0));
			renderer.setRenderBounds(0.4D,0.2D,0.4D,0.6D,0.6D,0.6D);
			renderer.renderStandardBlock(Blocks.glass, x, y, z);
			renderer.setOverrideBlockTexture(Blocks.beacon.getIcon(0, 0));
			renderer.setRenderBounds(0.3D,0.4D,0.3D,0.7D,0.8D,0.7D);
			renderer.renderStandardBlock(Blocks.glass, x, y, z);
			renderer.renderAllFaces = false;
		}
		if(block instanceof BlockMRULevitator)
		{
			renderer.renderAllFaces = true;
			renderer.setOverrideBlockTexture(BlocksCore.fortifiedStone.getIcon(0, 0));
			renderer.setRenderBounds(0,0.8D,0,1,1,1);
			renderer.renderStandardBlock(Blocks.glass, x, y, z);
			renderer.setRenderBounds(0.8D,0.2D,0.8D,0.99D,0.8D,0.99D);
			renderer.renderStandardBlock(Blocks.glass, x, y, z);
			renderer.setRenderBounds(0.01D,0.2D,0.8D,0.2D,0.8D,0.99D);
			renderer.renderStandardBlock(Blocks.glass, x, y, z);
			renderer.setRenderBounds(0.8D,0.2D,0.01D,0.99D,0.8D,0.2D);
			renderer.renderStandardBlock(Blocks.glass, x, y, z);
			renderer.setRenderBounds(0.01D,0.2D,0.01D,0.2D,0.8D,0.2D);
			renderer.renderStandardBlock(Blocks.glass, x, y, z);
			renderer.setOverrideBlockTexture(Blocks.beacon.getIcon(0, 0));
			renderer.setRenderBounds(0.4D,0.4D,0.4D,0.6D,0.8D,0.6D);
			renderer.renderStandardBlock(Blocks.glass, x, y, z);
			renderer.setOverrideBlockTexture(BlocksCore.magicalQuarry.getIcon(0, 0));
			renderer.setRenderBounds(0.3D,0.2D,0.3D,0.7D,0.6D,0.7D);
			renderer.renderStandardBlock(Blocks.glass, x, y, z);
			renderer.renderAllFaces = false;
		}
		if(block instanceof BlockMRUSpreader)
		{
			renderer.renderAllFaces = true;
			renderer.setOverrideBlockTexture(BlocksCore.magicalQuarry.getIcon(0, 0));
			renderer.setRenderBounds(0.4D,0.0D,0.4D,0.6D,0.6D,0.6D);
			renderer.renderStandardBlock(Blocks.glass, x, y, z);
			renderer.setOverrideBlockTexture(BlocksCore.magicalQuarry.getIcon(0, 0));
			renderer.setRenderBounds(0.3D,0.4D,0.3D,0.7D,0.8D,0.7D);
			renderer.renderStandardBlock(Blocks.glass, x, y, z);
			renderer.renderAllFaces = false;
		}
		
		if(block instanceof BlockMRUCoil_Coil)
		{
			renderer.renderAllFaces = true;
			renderer.setOverrideBlockTexture(Blocks.end_portal_frame.getIcon(1, 0));
			renderer.setRenderBounds(0.3D,0.0D,0.3D,0.7D,0.2D,0.7D);
			renderer.renderStandardBlock(Blocks.glass, x, y, z);
			renderer.setOverrideBlockTexture(BlocksCore.magicPlating.getIcon(0, 0));
			renderer.setRenderBounds(0.45D,0.2D,0.45D,0.55D,0.8D,0.55D);
			renderer.renderStandardBlock(Blocks.glass, x, y, z);
			
			renderer.setRenderBounds(0.0D,0.0D,0.9D,1D,0.1D,1D);
			renderer.renderStandardBlock(Blocks.glass, x, y, z);
			renderer.setRenderBounds(0.9D,0.0D,0.0D,1D,0.1D, 1D);
			renderer.renderStandardBlock(Blocks.glass, x, y, z);
			renderer.setRenderBounds(0.0D,0.0D,0.0D,0.1D,0.1D,1D);
			renderer.renderStandardBlock(Blocks.glass, x, y, z);
			renderer.setRenderBounds(0.0D,0.0D,0.0D,1D,0.1D,0.1D);
			renderer.renderStandardBlock(Blocks.glass, x, y, z);
			
			renderer.setRenderBounds(0.1D,0.2D,0.8D,0.9D,0.3D,0.9D);
			renderer.renderStandardBlock(Blocks.glass, x, y, z);
			renderer.setRenderBounds(0.8D,0.2D,0.1D,0.9D,0.3D,0.9D);
			renderer.renderStandardBlock(Blocks.glass, x, y, z);
			renderer.setRenderBounds(0.1D,0.2D,0.1D,0.2D,0.3D,0.9D);
			renderer.renderStandardBlock(Blocks.glass, x, y, z);
			renderer.setRenderBounds(0.1D,0.2D,0.1D,0.9D,0.3D,0.2D);
			renderer.renderStandardBlock(Blocks.glass, x, y, z);
			
			renderer.setRenderBounds(0.2D,0.4D,0.7D,0.8D,0.5D,0.8D);
			renderer.renderStandardBlock(Blocks.glass, x, y, z);
			renderer.setRenderBounds(0.7D,0.4D,0.2D,0.8D,0.5D,0.8D);
			renderer.renderStandardBlock(Blocks.glass, x, y, z);
			renderer.setRenderBounds(0.2D,0.4D,0.2D,0.3D,0.5D,0.8D);
			renderer.renderStandardBlock(Blocks.glass, x, y, z);
			renderer.setRenderBounds(0.2D,0.4D,0.2D,0.8D,0.5D,0.3D);
			renderer.renderStandardBlock(Blocks.glass, x, y, z);
			
			renderer.setRenderBounds(0.3D,0.6D,0.6D,0.7D,0.7D,0.7D);
			renderer.renderStandardBlock(Blocks.glass, x, y, z);
			renderer.setRenderBounds(0.6D,0.6D,0.3D,0.7D,0.7D,0.7D);
			renderer.renderStandardBlock(Blocks.glass, x, y, z);
			renderer.setRenderBounds(0.3D,0.6D,0.3D,0.4D,0.7D,0.7D);
			renderer.renderStandardBlock(Blocks.glass, x, y, z);
			renderer.setRenderBounds(0.3D,0.6D,0.3D,0.7D,0.7D,0.4D);
			renderer.renderStandardBlock(Blocks.glass, x, y, z);
			
			renderer.renderAllFaces = false;
		}
		
		if(block instanceof BlockCorruptionCleaner)
		{
			renderer.renderAllFaces = true;
			
			renderer.setOverrideBlockTexture(BlocksCore.fortifiedStone.getIcon(0, 0));
			renderer.setRenderBounds(0,0.8D,0,1,1,1);
			renderer.renderStandardBlock(Blocks.glass, x, y, z);
			renderer.setOverrideBlockTexture(BlocksCore.fortifiedStone.getIcon(0, 0));
			renderer.setRenderBounds(0,0.0D,0,1,0.2D,1);
			renderer.renderStandardBlock(Blocks.glass, x, y, z);
			
			renderer.setOverrideBlockTexture(BlocksCore.fortifiedStone.getIcon(0, 0));
			renderer.setRenderBounds(0.4,0.6D,0.4,0.6,0.8,0.6);
			renderer.renderStandardBlock(Blocks.glass, x, y, z);
			
			renderer.setOverrideBlockTexture(BlocksCore.fortifiedStone.getIcon(0, 0));
			renderer.setRenderBounds(0.4,0.2D,0.4,0.6,0.4,0.6);
			renderer.renderStandardBlock(Blocks.glass, x, y, z);
			
			renderer.setOverrideBlockTexture(Blocks.diamond_block.getIcon(0, 0));
			renderer.setRenderBounds(0.3,0.4D,0.3,0.7,0.6,0.7);
			renderer.renderStandardBlock(Blocks.glass, x, y, z);
			
			renderer.setOverrideBlockTexture(BlocksCore.fortifiedGlass.getIcon(0, 0));
			renderer.setRenderBounds(0,0,0,1,1,1);
			renderer.renderStandardBlock(Blocks.glass, x, y, z);
			
			renderer.renderAllFaces = false;
		}
		
		if(block instanceof BlockReactorSupport)
		{
			Block b_0 = world.getBlock(x, y-1, z);
			Block b_1 = world.getBlock(x, y-2, z);
			
			renderer.renderAllFaces = true;
			
			if(!(b_0 instanceof BlockReactorSupport))
			{
				renderer.setOverrideBlockTexture(BlocksCore.magicPlating.getIcon(0, 0));
				renderer.setRenderBounds(0D,0D,0D,1D,0.2D,1D);
				renderer.renderStandardBlock(Blocks.glass, x, y, z);
				renderer.setRenderBounds(0.2D,0.2D,0.2D,0.8D,1D,0.8D);
				renderer.renderStandardBlock(Blocks.glass, x, y, z);
			}
			else
			if(!(b_1 instanceof BlockReactorSupport) && b_0 instanceof BlockReactorSupport)
			{
				renderer.setOverrideBlockTexture(BlocksCore.magicPlating.getIcon(0, 0));
				renderer.setRenderBounds(0.2D,0.0D,0.2D,0.8D,0.4D,0.8D);
				renderer.renderStandardBlock(Blocks.glass, x, y, z);
				renderer.setRenderBounds(0.3D,0.4D,0.3D,0.7D,1D,0.7D);
				renderer.renderStandardBlock(Blocks.glass, x, y, z);
				
				renderer.setRenderBounds(0.1D,0.8D,0.8D,0.9D,0.9D,0.9D);
				renderer.renderStandardBlock(Blocks.glass, x, y, z);
				renderer.setRenderBounds(0.8D,0.8D,0.1D,0.9D,0.9D,0.9D);
				renderer.renderStandardBlock(Blocks.glass, x, y, z);
				renderer.setRenderBounds(0.1D,0.8D,0.1D,0.2D,0.9D,0.9D);
				renderer.renderStandardBlock(Blocks.glass, x, y, z);
				renderer.setRenderBounds(0.1D,0.8D,0.1D,0.9D,0.9D,0.2D);
				renderer.renderStandardBlock(Blocks.glass, x, y, z);
				
				renderer.setRenderBounds(0.1D,0.6D,0.8D,0.9D,0.7D,0.9D);
				renderer.renderStandardBlock(Blocks.glass, x, y, z);
				renderer.setRenderBounds(0.8D,0.6D,0.1D,0.9D,0.7D,0.9D);
				renderer.renderStandardBlock(Blocks.glass, x, y, z);
				renderer.setRenderBounds(0.1D,0.6D,0.1D,0.2D,0.7D,0.9D);
				renderer.renderStandardBlock(Blocks.glass, x, y, z);
				renderer.setRenderBounds(0.1D,0.6D,0.1D,0.9D,0.7D,0.2D);
				renderer.renderStandardBlock(Blocks.glass, x, y, z);
				
				renderer.setRenderBounds(0.1D,0.4D,0.8D,0.9D,0.5D,0.9D);
				renderer.renderStandardBlock(Blocks.glass, x, y, z);
				renderer.setRenderBounds(0.8D,0.4D,0.1D,0.9D,0.5D,0.9D);
				renderer.renderStandardBlock(Blocks.glass, x, y, z);
				renderer.setRenderBounds(0.1D,0.4D,0.1D,0.2D,0.5D,0.9D);
				renderer.renderStandardBlock(Blocks.glass, x, y, z);
				renderer.setRenderBounds(0.1D,0.4D,0.1D,0.9D,0.5D,0.2D);
				renderer.renderStandardBlock(Blocks.glass, x, y, z);
			}
			else
			{
				renderer.setOverrideBlockTexture(BlocksCore.magicPlating.getIcon(0, 0));
				renderer.renderStandardBlock(Blocks.glass, x, y, z);
			}
			
			TileEntity tile = world.getTileEntity(x-1, y, z);
			if(tile instanceof TileMRUReactor)
			{
				TileMRUReactor tmr = (TileMRUReactor) tile;
				if(tmr.isStructureCorrect)
				{
					renderer.setOverrideBlockTexture(BlocksCore.fortifiedStone.getIcon(0, 0));
					renderer.setRenderBounds(-0.4D,0.6D,0.45D,0.6D,0.7D,0.55D);
					renderer.renderStandardBlock(Blocks.glass, x, y, z);
					renderer.setRenderBounds(-0.4D,0.95D,0.45D,0.6D,1.05D,0.55D);
					renderer.renderStandardBlock(Blocks.glass, x, y, z);
					renderer.setRenderBounds(-0.4D,1.3D,0.45D,0.6D,1.4D,0.55D);
					renderer.renderStandardBlock(Blocks.glass, x, y, z);
				}
			}
			
			tile = world.getTileEntity(x+1, y, z);
			if(tile instanceof TileMRUReactor)
			{
				TileMRUReactor tmr = (TileMRUReactor) tile;
				if(tmr.isStructureCorrect)
				{
					renderer.setOverrideBlockTexture(BlocksCore.fortifiedStone.getIcon(0, 0));
					renderer.setRenderBounds(0.4D,0.6D,0.45D,1.6D,0.7D,0.55D);
					renderer.renderStandardBlock(Blocks.glass, x, y, z);
					renderer.setRenderBounds(0.4D,0.95D,0.45D,1.6D,1.05D,0.55D);
					renderer.renderStandardBlock(Blocks.glass, x, y, z);
					renderer.setRenderBounds(0.4D,1.3D,0.45D,1.6D,1.4D,0.55D);
					renderer.renderStandardBlock(Blocks.glass, x, y, z);
				}
			}
			
			tile = world.getTileEntity(x, y, z+1);
			if(tile instanceof TileMRUReactor)
			{
				TileMRUReactor tmr = (TileMRUReactor) tile;
				if(tmr.isStructureCorrect)
				{
					renderer.setOverrideBlockTexture(BlocksCore.fortifiedStone.getIcon(0, 0));
					renderer.setRenderBounds(0.45D,0.6D,0.4D,0.55D,0.7D,1.55D);
					renderer.renderStandardBlock(Blocks.glass, x, y, z);
					renderer.setRenderBounds(0.45D,0.95D,0.4D,0.55D,1.05D,1.55D);
					renderer.renderStandardBlock(Blocks.glass, x, y, z);
					renderer.setRenderBounds(0.45D,1.3D,0.4D, 0.55D,1.4D,1.55D);
					renderer.renderStandardBlock(Blocks.glass, x, y, z);
				}
			}
			
			tile = world.getTileEntity(x, y, z-1);
			if(tile instanceof TileMRUReactor)
			{
				TileMRUReactor tmr = (TileMRUReactor) tile;
				if(tmr.isStructureCorrect)
				{
					renderer.setOverrideBlockTexture(BlocksCore.fortifiedStone.getIcon(0, 0));
					renderer.setRenderBounds(0.45D,0.6D,-0.4D,0.55D,0.7D,0.6D);
					renderer.renderStandardBlock(Blocks.glass, x, y, z);
					renderer.setRenderBounds(0.45D,0.95D,-0.4D,0.55D,1.05D,0.6D);
					renderer.renderStandardBlock(Blocks.glass, x, y, z);
					renderer.setRenderBounds(0.45D,1.3D,-0.4D, 0.55D,1.4D,0.5D);
					renderer.renderStandardBlock(Blocks.glass, x, y, z);
				}
			}
			renderer.renderAllFaces = false;
		}
		
		if(block instanceof BlockFancy)
		{
			renderer.setOverrideBlockTexture(block.getIcon(0, metadata));
			renderer.setRenderBounds(0.00D,0.00D,0.0D, 1D,1D,1D);
			renderer.renderStandardBlock(Blocks.glass, x, y, z);
			renderer.setOverrideBlockTexture(((BlockFancy)block).overlayIcons[metadata]);
			renderer.setRenderBounds(-0.001D,-0.001D,-0.001D, 1.001D,1.001D,1.001D);
			renderer.renderStandardBlock(Blocks.glass, x, y, z);
		}
		
		if(block instanceof BlockMIM)
		{
			renderer.setOverrideBlockTexture(block.getIcon(0, metadata));
			renderer.setRenderBounds(0, 0, 0, 1, 0.1, 1);
			renderer.renderStandardBlock(Blocks.glass, x, y, z);
			renderer.setRenderBounds(0.45, 0, 0.45, 0.55, 1, 0.55);
			renderer.renderStandardBlock(Blocks.glass, x, y, z);
		}
		renderer.clearOverrideBlockTexture();
		return true;
	}

	@Override
	public boolean shouldRender3DInInventory(int modelId) {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public int getRenderId() {
		// TODO Auto-generated method stub
		return 2634;
	}
	
	public void renderTesselatedTextureByPoints(IBlockAccess world, Block b, RenderBlocks renderer, double x, double y, double z, double red, double green, double blue, double alpha, int brightness, IIcon renderWith, double... points)
	{
		if(renderer.renderAllFaces || b.shouldSideBeRendered(world, MathHelper.floor_double(x-1), MathHelper.floor_double(y), MathHelper.floor_double(z), 6))
		//SIDE WEST
		renderFace(renderer, Blocks.glass, x, y, z, red, green, blue, alpha, brightness, renderWith,
				points[3],points[4],points[5], //topright
				points[9],points[10],points[11], //botright
				points[21],points[22],points[23], //botleft
				points[15],points[16],points[17]  //topleft
				);
		
		if(renderer.renderAllFaces || b.shouldSideBeRendered(world, MathHelper.floor_double(x), MathHelper.floor_double(y+1), MathHelper.floor_double(z), 6))
		//SIDE UP
		renderFace(renderer, Blocks.glass, x, y, z, red, green, blue, alpha, brightness, renderWith,
				points[0],points[1],points[2], //topright
				points[6],points[7],points[8], //botright
				points[9],points[10],points[11], //botleft
				points[3],points[4],points[5] //topleft
				);
		
		if(renderer.renderAllFaces || b.shouldSideBeRendered(world, MathHelper.floor_double(x), MathHelper.floor_double(y), MathHelper.floor_double(z+1), 6))
		//SIDE SOUTH
		renderFace(renderer, Blocks.glass, x, y, z, red, green, blue, alpha, brightness, renderWith,
				points[0],points[1],points[2], //topright
				points[3],points[4],points[5], //botright
				points[15],points[16],points[17], //botleft
				points[12],points[13],points[14]  //topleft
				);
		
		if(renderer.renderAllFaces || b.shouldSideBeRendered(world, MathHelper.floor_double(x), MathHelper.floor_double(y), MathHelper.floor_double(z-1), 6))
		//SIDE NORTH
		renderFace(renderer, Blocks.glass, x, y, z, red, green, blue, alpha, brightness, renderWith,
				points[9],points[10],points[11], //topright
				points[6],points[7],points[8], //botright
				points[18],points[19],points[20], //botleft
				points[21],points[22],points[23]  //topleft
				);
		
		if(renderer.renderAllFaces || b.shouldSideBeRendered(world, MathHelper.floor_double(x+1), MathHelper.floor_double(y), MathHelper.floor_double(z), 6))
		//SIDE EAST
		renderFace(renderer, Blocks.glass, x, y, z, red, green, blue, alpha, brightness, renderWith,
				points[6],points[7],points[8], //topright
				points[0],points[1],points[2], //botright
				points[12],points[13],points[14], //botleft
				points[18],points[19],points[20]  //topleft
				);
		
		if(renderer.renderAllFaces || b.shouldSideBeRendered(world, MathHelper.floor_double(x), MathHelper.floor_double(y-1), MathHelper.floor_double(z), 6))
		//SIDE DOWN
		renderFace(renderer, Blocks.glass, x, y, z, red, green, blue, alpha, brightness, renderWith,
				points[21],points[22],points[23], //topright
				points[18],points[19],points[20], //botright
				points[12],points[13],points[14], //botleft
				points[15],points[16],points[17]  //topleft
				);
	}
	
    public void renderFace(RenderBlocks renderer, Block b, double x, double y, double z,double red, double green, double blue, double alpha, int brightness, IIcon icon, double... renderPts)
    {
        Tessellator tessellator = Tessellator.instance;
        
        double d3 = (double)icon.getInterpolatedU(renderer.renderMinX * 16.0D);
        double d4 = (double)icon.getInterpolatedU(renderer.renderMaxX * 16.0D);
        double d5 = (double)icon.getInterpolatedV(renderer.renderMinZ * 16.0D);
        double d6 = (double)icon.getInterpolatedV(renderer.renderMaxZ * 16.0D);
        
        

        double d7 = d4;
        double d8 = d3;
        double d9 = d5;
        double d10 = d6;
        tessellator.setColorRGBA_F((float)red, (float)green, (float)blue, (float)alpha);
        tessellator.setBrightness(brightness);
        {
            tessellator.addVertexWithUV(x+renderPts[0], y+renderPts[1], z+renderPts[2], d4, d6);
            tessellator.addVertexWithUV(x+renderPts[3], y+renderPts[4], z+renderPts[5], d7, d9);
            tessellator.addVertexWithUV(x+renderPts[6], y+renderPts[7], z+renderPts[8], d3, d5);
            tessellator.addVertexWithUV(x+renderPts[9], y+renderPts[10], z+renderPts[11], d8, d10);
        }
    }

}
