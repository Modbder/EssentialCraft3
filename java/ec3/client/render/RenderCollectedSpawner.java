package ec3.client.render;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import ec3.common.item.ItemCollectedMonsterSpawner;
import DummyCore.Utils.MiscUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.client.IItemRenderer;

public class RenderCollectedSpawner implements IItemRenderer{

	@Override
	public boolean handleRenderType(ItemStack item, ItemRenderType type) {
		return true;
	}

	@Override
	public boolean shouldUseRenderHelper(ItemRenderType type, ItemStack item,ItemRendererHelper helper) {
		return helper != ItemRendererHelper.BLOCK_3D;
	}

	@Override
	public void renderItem(ItemRenderType type, ItemStack item, Object... data)
	{
		if(item == null || item.getItem() == null || !(item.getItem() instanceof ItemCollectedMonsterSpawner))
			return;
		
		RenderBlocks rb = (RenderBlocks) data[0];
		
		if(type == ItemRenderType.EQUIPPED_FIRST_PERSON)
			GL11.glTranslated(0D, 0.75D, 0.5D);
		
		Minecraft.getMinecraft().renderEngine.bindTexture(TextureMap.locationBlocksTexture);
		rb.setOverrideBlockTexture(Blocks.mob_spawner.getIcon(0, 0));
		rb.renderBlockAsItem(Blocks.glass, 0, 1);
		rb.clearOverrideBlockTexture();
		
		NBTTagCompound tag = MiscUtils.getStackTag(item);
		
		if(tag.hasKey("monsterSpawner"))
		{
			NBTTagCompound spawnerTag = tag.getCompoundTag("monsterSpawner");
			String id = spawnerTag.getString("EntityId");
			try
			{
				Entity e = EntityList.createEntityByName(id, Minecraft.getMinecraft().theWorld);
				e.setWorld(Minecraft.getMinecraft().theWorld);
				
	            float scale = 0.4375F;
	            if(e.getShadowSize() > 1.5)
	            	scale = 0.1F;
	            
				GL11.glPushMatrix();
				
				GL11.glRotated(Minecraft.getMinecraft().thePlayer.ticksExisted * 10, 0,1,0);
	            GL11.glRotatef(-20F, 1.0F, 0.0F, 0.0F);
	            GL11.glTranslatef(0.0F, -0.4F, 0.0F);
	            GL11.glScalef(scale, scale, scale);
	            e.setLocationAndAngles(0, 0, 0, 0.0F, 0.0F);
	            
				RenderManager.instance.renderEntityWithPosYaw(e, 0.0D, 0.0D, 0.0D, 0.0F, 0);
				
				GL11.glPopMatrix();
				
	            GL11.glEnable(GL12.GL_RESCALE_NORMAL);
	            OpenGlHelper.setActiveTexture(OpenGlHelper.lightmapTexUnit);
	            GL11.glDisable(GL11.GL_TEXTURE_2D);
	            OpenGlHelper.setActiveTexture(OpenGlHelper.defaultTexUnit);
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
		}
	}

}
