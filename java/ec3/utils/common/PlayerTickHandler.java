package ec3.utils.common;

import io.netty.buffer.ByteBuf;

import java.nio.ByteBuffer;
import java.util.List;

import DummyCore.Blocks.BlocksRegistry;
import DummyCore.Utils.DummyDataUtils;
import DummyCore.Utils.DummyPacketDispatcher;
import DummyCore.Utils.MathUtils;
import DummyCore.Utils.MiscUtils;
import DummyCore.Utils.Notifier;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent;
import cpw.mods.fml.common.gameevent.TickEvent.Phase;
import cpw.mods.fml.common.gameevent.TickEvent.PlayerTickEvent;
import cpw.mods.fml.common.gameevent.TickEvent.WorldTickEvent;
import cpw.mods.fml.common.network.ByteBufUtils;
import cpw.mods.fml.common.network.internal.FMLProxyPacket;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ec3.api.WorldEventLibrary;
import ec3.common.block.BlocksCore;
import ec3.common.item.ItemsCore;
import ec3.common.world.WorldProviderFirstWorld;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.monster.EntityEnderman;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.play.server.S3FPacketCustomPayload;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MathHelper;
import net.minecraft.world.EnumSkyBlock;
import net.minecraft.world.World;
import net.minecraftforge.client.event.sound.SoundLoadEvent;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;

public class PlayerTickHandler{
	public static int tickAmount;
	
	@SubscribeEvent
	public void tickEvent(WorldTickEvent event)
	{
		++tickAmount;
	}
	
	@SubscribeEvent
	public void tickEvent(LivingUpdateEvent event)
	{
		if(event.entityLiving != null) //Should never be null actually, but still leaving this check here
		{
			if(event.entityLiving instanceof EntityPlayer) //Checking, if the ticking entity is the actual player
			{
				EntityPlayer player = (EntityPlayer) event.entityLiving;
				if(player.worldObj.isRemote)
				{
					if(player.dimension == 53)
					{
						if(player.worldObj.provider.dimensionId == 53)
						{
							((WorldProviderFirstWorld)(player.worldObj.provider)).generateLightBrightnessTable();
							if(ECUtils.isEventActive("ec3.event.darkness"))
							{
								if(player.worldObj.rand.nextFloat() < 0.01F)
									player.worldObj.playSound(player.posX,player.posY,player.posZ, "ambient.cave.cave", 1, player.worldObj.rand.nextFloat()*2, true);
								if(player.worldObj.rand.nextFloat() < 0.001F)
								{
									String[] sound = {"mob.zombie.death","mob.zombie.say","mob.blaze.death","mob.skeleton.step","mob.endermen.stare","mob.spider.step","mob.spider.death","mob.spider.say","mob.creeper.death"};
									
									player.worldObj.playSound(player.posX+MathUtils.randomDouble(player.worldObj.rand)*16,player.posY,player.posZ+MathUtils.randomDouble(player.worldObj.rand)*16, sound[player.worldObj.rand.nextInt(sound.length)], 1, 0.01F, true);
								}
									
							}
							
							if(ECUtils.isEventActive("ec3.event.earthquake"))
							{
								player.cameraPitch += MathUtils.randomFloat(player.worldObj.rand);
								player.rotationYaw += MathUtils.randomFloat(player.worldObj.rand);
								player.motionX += MathUtils.randomFloat(player.worldObj.rand)/30;
								player.motionY += MathUtils.randomFloat(player.worldObj.rand)/30;
								player.motionZ += MathUtils.randomFloat(player.worldObj.rand)/30;
								if(player.worldObj.rand.nextFloat() < 0.01F)
								{
									player.cameraPitch += MathUtils.randomFloat(player.worldObj.rand)*90;
									player.rotationYaw += MathUtils.randomFloat(player.worldObj.rand)*90;
									player.motionX += MathUtils.randomFloat(player.worldObj.rand)*3;
									player.motionY += MathUtils.randomFloat(player.worldObj.rand)*3;
									player.motionZ += MathUtils.randomFloat(player.worldObj.rand)*3;
									player.worldObj.playSound(player.posX,player.posY,player.posZ, "random.explode", 0.1F, 0.1F, true);
								}
							}
						}
					}
				}
				if(!event.entityLiving.worldObj.isRemote) //We should only run block changes on server-side
				{		
					if(WorldEventLibrary.currentEvent != null)
						WorldEventLibrary.currentEvent.playerTick(player, WorldEventLibrary.currentEventDuration);
					WindRelations.playerTick(player);
					if(player.ticksExisted % 20 == 0)
					{
						String str = DummyDataUtils.getDataForPlayer(player.getDisplayName(), "essentialcraft", "ubmruEnergy");
						String attunement = DummyDataUtils.getDataForPlayer(player.getDisplayName(), "essentialcraft", "attunement");
						if(attunement == null || attunement.isEmpty() || attunement.equals("no data") || attunement.equals("empty string") || attunement.equals("empty"))
						{
							DummyDataUtils.setDataForPlayer(player.getDisplayName(), "essentialcraft", "attunement", Integer.toString(0));
						}
						World wrd = player.worldObj;
						List itemList = wrd.getEntitiesWithinAABB(EntityItem.class,AxisAlignedBB.getBoundingBox(player.posX-0.5D, player.posY-0.5D, player.posZ-0.5D, player.posX+0.5D, player.posY+0.5D, player.posZ+0.5D).expand(4, 2, 4));
						for(int i = 0; i < itemList.size(); ++i)
						{
							doGroundItemChecks((EntityItem) itemList.get(i));
						}
					}
				}
			}
		}
	}
	
	public void doGroundItemChecks(EntityItem item)
	{
		if(item.getEntityItem().getItem() == Items.blaze_powder)
		{
			forLoop:for(int x = -1; x <= 1; ++x)
			{
				for(int z = -1; z <= 1; ++z)
				{
					Block b = item.worldObj.getBlock((int)item.posX+x, (int)item.posY-1, (int)item.posZ+z);
					if(b == Blocks.netherrack)
					{
						Block b_check_b1 = item.worldObj.getBlock((int)item.posX+x+1, (int)item.posY-1, (int)item.posZ+z);
						Block b_check_b2 = item.worldObj.getBlock((int)item.posX+x-1, (int)item.posY-1, (int)item.posZ+z);
						Block b_check_b3 = item.worldObj.getBlock((int)item.posX+x, (int)item.posY-1, (int)item.posZ+z+1);
						Block b_check_b4 = item.worldObj.getBlock((int)item.posX+x, (int)item.posY-1, (int)item.posZ+z-1);
						Block b_check_air = item.worldObj.getBlock((int)item.posX+x, (int)item.posY, (int)item.posZ+z);
						if(b_check_air == Blocks.air && b_check_b1.getMaterial() == Material.lava && b_check_b4.getMaterial() == Material.lava && b_check_b2.getMaterial() == Material.lava && b_check_b3.getMaterial() == Material.lava)
						{
							if(item.getEntityItem().stackSize == 1)
							item.lifespan = 0;
							else
							{
								item.getEntityItem().stackSize -= 1;
							}
							MiscUtils.spawnParticlesOnServer("explode", (float)item.posX, (float)item.posY, (float)item.posZ, 0D, 0D, 0D);
							if(item.worldObj.rand.nextFloat() <= 0.8F)
							{
								item.worldObj.setBlock((int)item.posX+x, (int)item.posY, (int)item.posZ+z, BlocksCore.drops, 0, 3);
							}
							break forLoop;
						}
					}
				}
			}
		}else
			if(item.getEntityItem().getItem() == Items.clay_ball)
			{
				forLoop:for(int x = -1; x <= 1; ++x)
				{
					for(int z = -1; z <= 1; ++z)
					{
						Block b = item.worldObj.getBlock((int)item.posX+x, (int)item.posY-1, (int)item.posZ+z);
						if(b == Blocks.ice)
						{
							Block b_check_b1 = item.worldObj.getBlock((int)item.posX+x+1, (int)item.posY-1, (int)item.posZ+z);
							Block b_check_b2 = item.worldObj.getBlock((int)item.posX+x-1, (int)item.posY-1, (int)item.posZ+z);
							Block b_check_b3 = item.worldObj.getBlock((int)item.posX+x, (int)item.posY-1, (int)item.posZ+z+1);
							Block b_check_b4 = item.worldObj.getBlock((int)item.posX+x, (int)item.posY-1, (int)item.posZ+z-1);
							Block b_check_air = item.worldObj.getBlock((int)item.posX+x, (int)item.posY, (int)item.posZ+z);
							if(b_check_air == Blocks.air && b_check_b1.getMaterial() == Material.water && b_check_b4.getMaterial() == Material.water && b_check_b2.getMaterial() == Material.water && b_check_b3.getMaterial() == Material.water)
							{
								if(item.getEntityItem().stackSize == 1)
								item.lifespan = 0;
								else
								{
									item.getEntityItem().stackSize -= 1;
								}
								MiscUtils.spawnParticlesOnServer("explode", (float)item.posX, (float)item.posY, (float)item.posZ, 0D, 0D, 0D);
								if(item.worldObj.rand.nextFloat() <= 0.8F)
								{
									item.worldObj.setBlock((int)item.posX+x, (int)item.posY, (int)item.posZ+z, BlocksCore.drops, 1, 3);
								}
								break forLoop;
							}
						}
					}
				}
		}else
			if(item.getEntityItem().getItem() == Items.slime_ball)
			{
				forLoop:for(int x = -1; x <= 1; ++x)
				{
					for(int z = -1; z <= 1; ++z)
					{
						Block b = item.worldObj.getBlock((int)item.posX+x, (int)item.posY-1, (int)item.posZ+z);
						if(b == Blocks.grass)
						{
							Block b_check_b1 = item.worldObj.getBlock((int)item.posX+x+1, (int)item.posY-1, (int)item.posZ+z);
							Block b_check_b2 = item.worldObj.getBlock((int)item.posX+x-1, (int)item.posY-1, (int)item.posZ+z);
							Block b_check_b3 = item.worldObj.getBlock((int)item.posX+x, (int)item.posY-1, (int)item.posZ+z+1);
							Block b_check_b4 = item.worldObj.getBlock((int)item.posX+x, (int)item.posY-1, (int)item.posZ+z-1);
							Block b_check_air = item.worldObj.getBlock((int)item.posX+x, (int)item.posY, (int)item.posZ+z);
							if(b_check_air == Blocks.air && b_check_b1 == Blocks.mossy_cobblestone && b_check_b4 == Blocks.mossy_cobblestone && b_check_b2 == Blocks.mossy_cobblestone && b_check_b3 == Blocks.mossy_cobblestone)
							{
								if(item.getEntityItem().stackSize == 1)
								item.lifespan = 0;
								else
								{
									item.getEntityItem().stackSize -= 1;
								}
								MiscUtils.spawnParticlesOnServer("explode", (float)item.posX, (float)item.posY, (float)item.posZ, 0D, 0D, 0D);
								if(item.worldObj.rand.nextFloat() <= 0.8F)
								{
									item.worldObj.setBlock((int)item.posX+x, (int)item.posY, (int)item.posZ+z, BlocksCore.drops, 2, 3);
								}
								break forLoop;
							}
						}
					}
				}
			}else
				if(item.getEntityItem().getItem() == Items.gunpowder)
				{
					forLoop:for(int x = -1; x <= 1; ++x)
					{
						for(int z = -1; z <= 1; ++z)
						{
							Block b = item.worldObj.getBlock((int)item.posX+x, (int)item.posY-1, (int)item.posZ+z);
							if(b == Blocks.quartz_block)
							{
								Block b_check_b1 = item.worldObj.getBlock((int)item.posX+x+1, (int)item.posY-1, (int)item.posZ+z);
								Block b_check_b2 = item.worldObj.getBlock((int)item.posX+x-1, (int)item.posY-1, (int)item.posZ+z);
								Block b_check_b3 = item.worldObj.getBlock((int)item.posX+x, (int)item.posY-1, (int)item.posZ+z+1);
								Block b_check_b4 = item.worldObj.getBlock((int)item.posX+x, (int)item.posY-1, (int)item.posZ+z-1);
								Block b_check_air = item.worldObj.getBlock((int)item.posX+x, (int)item.posY, (int)item.posZ+z);
								if(b_check_air == Blocks.air && b_check_b1 == Blocks.sand && b_check_b4 == Blocks.sand && b_check_b2 == Blocks.sand && b_check_b3 == Blocks.sand)
								{
									if(item.getEntityItem().stackSize == 1)
									item.lifespan = 0;
									else
									{
										item.getEntityItem().stackSize -= 1;
									}
									MiscUtils.spawnParticlesOnServer("explode", (float)item.posX, (float)item.posY, (float)item.posZ, 0D, 0D, 0D);
									if(item.worldObj.rand.nextFloat() <= 0.8F)
									{
										item.worldObj.setBlock((int)item.posX+x, (int)item.posY, (int)item.posZ+z, BlocksCore.drops, 3, 3);
									}
									break forLoop;
								}
							}
						}
					}
				}else
				{
					if(item.getEntityItem().getItem() == Items.diamond)
					{
						forLoop:for(int x = -1; x <= 1; ++x)
						{
							for(int z = -1; z <= 1; ++z)
							{
								Block b = item.worldObj.getBlock((int)item.posX+x, (int)item.posY-1, (int)item.posZ+z);
								if(b == Blocks.emerald_block)
								{
									Block b_check_air = item.worldObj.getBlock((int)item.posX+x, (int)item.posY, (int)item.posZ+z);
									if(b_check_air == Blocks.air)
									{
										if(item.getEntityItem().stackSize == 1)
										item.lifespan = 0;
										else
										{
											item.getEntityItem().stackSize -= 1;
										}
										MiscUtils.spawnParticlesOnServer("explode", (float)item.posX, (float)item.posY, (float)item.posZ, 0D, 0D, 0D);
										if(item.worldObj.rand.nextFloat() <= 0.6F)
										{
											ECUtils.increaseCorruptionAt(item.worldObj, (float)item.posX, (float)item.posY, (float)item.posZ, 5000);
										}
										break forLoop;
									}
								}
							}
						}
					}else
					{
						if(item.getEntityItem().getItem() == Items.emerald)
						{
							forLoop:for(int x = -1; x <= 1; ++x)
							{
								for(int z = -1; z <= 1; ++z)
								{
									Block b = item.worldObj.getBlock((int)item.posX+x, (int)item.posY-1, (int)item.posZ+z);
									if(b == Blocks.emerald_block)
									{
										Block b_check_air = item.worldObj.getBlock((int)item.posX+x, (int)item.posY, (int)item.posZ+z);
										if(b_check_air == Blocks.air)
										{
											if(item.getEntityItem().stackSize == 1)
											item.lifespan = 0;
											else
											{
												item.getEntityItem().stackSize -= 1;
											}
											MiscUtils.spawnParticlesOnServer("explode", (float)item.posX, (float)item.posY, (float)item.posZ, 0D, 0D, 0D);
											if(item.worldObj.rand.nextFloat() <= 0.5F)
											{
												EntityItem soulStone = new EntityItem(item.worldObj,(float)item.posX, (float)item.posY, (float)item.posZ,new ItemStack(ItemsCore.soulStone,1,0));
												item.worldObj.spawnEntityInWorld(soulStone);
											}
											break forLoop;
										}
									}
								}
							}
						}
					}
				}
	}

}
