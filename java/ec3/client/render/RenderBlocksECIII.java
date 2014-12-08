package ec3.client.render;

import org.lwjgl.opengl.GL11;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;
import ec3.common.block.BlockChargingChamber;
import ec3.common.block.BlockColdDistillator;
import ec3.common.block.BlockCorruptionCleaner;
import ec3.common.block.BlockCorruption_Light;
import ec3.common.block.BlockCrystalController;
import ec3.common.block.BlockCrystalExtractor;
import ec3.common.block.BlockCrystalFormer;
import ec3.common.block.BlockDrops;
import ec3.common.block.BlockEnderGenerator;
import ec3.common.block.BlockFlowerBurner;
import ec3.common.block.BlockHeatGenerator;
import ec3.common.block.BlockMRUCoil_Coil;
import ec3.common.block.BlockMRUCoil_Hardener;
import ec3.common.block.BlockMRULevitator;
import ec3.common.block.BlockMRUReactor;
import ec3.common.block.BlockMRUSpreader;
import ec3.common.block.BlockMagicalEnchanter;
import ec3.common.block.BlockMagicalJukebox;
import ec3.common.block.BlockMagicalQuarry;
import ec3.common.block.BlockMagicalRepairer;
import ec3.common.block.BlockMagicianTable;
import ec3.common.block.BlockMagmaticSmeltery;
import ec3.common.block.BlockMatrixAbsorber;
import ec3.common.block.BlockMonsterHarvester;
import ec3.common.block.BlockMonsterHolder;
import ec3.common.block.BlockMoonWell;
import ec3.common.block.BlockPotionSpreader;
import ec3.common.block.BlockRadiatingChamber;
import ec3.common.block.BlockRayTower;
import ec3.common.block.BlockReactorSupport;
import ec3.common.block.BlockSunRayAbsorber;
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
		if(block instanceof BlockChargingChamber)
		{
	        GL11.glPushMatrix();
	        GL11.glTranslatef(0F,-0.5F,0F);
	        Minecraft.getMinecraft().renderEngine.bindTexture(RenderChargingChamber.textures);
	        RenderChargingChamber.model.renderAll();
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
	        float scale = 0.5F;
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
		if(block instanceof BlockHeatGenerator)
		{
	        GL11.glPushMatrix();
	        GL11.glTranslatef(0F,-0.5F,0F);
	        Minecraft.getMinecraft().renderEngine.bindTexture(RenderHeatGenerator.textures);
	        RenderHeatGenerator.model.renderAll();
	        GL11.glPopMatrix();
		}
		if(block instanceof BlockSunRayAbsorber)
		{
	        GL11.glPushMatrix();
	        GL11.glTranslatef(0F,-0.6F,0F);
	        Minecraft.getMinecraft().renderEngine.bindTexture(RenderSunRayAbsorber.textures);
	        GL11.glScalef(0.01F, 0.01F, 0.01F);
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

}
