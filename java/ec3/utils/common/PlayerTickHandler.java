package ec3.utils.common;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import org.apache.logging.log4j.Level;
import org.lwjgl.input.Keyboard;

import baubles.api.BaublesApi;
import DummyCore.Utils.MathUtils;
import DummyCore.Utils.MiscUtils;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent.Phase;
import cpw.mods.fml.common.gameevent.TickEvent.PlayerTickEvent;
import cpw.mods.fml.common.gameevent.TickEvent.WorldTickEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ec3.api.ApiCore;
import ec3.api.CorruptionEffectLibrary;
import ec3.api.DiscoveryEntry;
import ec3.api.ICorruptionEffect;
import ec3.api.PageEntry;
import ec3.api.WorldEventLibrary;
import ec3.client.gui.GuiResearchBook;
import ec3.common.block.BlocksCore;
import ec3.common.item.BaublesModifier;
import ec3.common.item.ItemComputerArmor;
import ec3.common.item.ItemComputerBoard;
import ec3.common.item.ItemGenericArmor;
import ec3.common.item.ItemsCore;
import ec3.common.mod.EssentialCraftCore;
import ec3.common.registry.ResearchRegistry;
import ec3.common.world.WorldProviderFirstWorld;
import ec3.utils.cfg.Config;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatStyle;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.MathHelper;
import net.minecraft.util.StatCollector;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.player.PlayerEvent;

public class PlayerTickHandler{
	public Hashtable<EntityPlayer, Integer> ticks = new Hashtable<EntityPlayer, Integer>();
	public Hashtable<EntityPlayer, Integer> wticks = new Hashtable<EntityPlayer, Integer>();
	
	public Hashtable<EntityPlayer, Boolean> isWearingBoots = new Hashtable<EntityPlayer, Boolean>();
	
	public Hashtable<EntityPlayer, Boolean> isFlightAllowed = new Hashtable<EntityPlayer, Boolean>();
	
	public boolean client_flightAllowed;
	
	public static int tickAmount;
	
	public boolean isRKeyPressed = false;
	
	public void manageWorldSync(EntityPlayer e)
	{
		if(!wticks.containsKey(e))
		{
			wticks.put(e, 10);
			ECUtils.requestCurrentEventSyncForPlayer((EntityPlayerMP) e);
		}else
		{
			int i = wticks.get(e).intValue();
			
			if(i <= 0)
			{
				i = 10;
				ECUtils.requestCurrentEventSyncForPlayer((EntityPlayerMP) e);
			}else
				--i;
			wticks.put(e, i);
		}
	}
	
	@SubscribeEvent
	public void tickEvent(WorldTickEvent event)
	{
		++tickAmount;
	}
	
	public void manageSync(EntityPlayer e)
	{
		if(!ticks.containsKey(e))
		{
			ticks.put(e, 10);
			ECUtils.requestSync(e);
		}else
		{
			int i = ticks.get(e).intValue();
			
			if(i <= 0)
			{
				i = 10;
				ECUtils.requestSync(e);
			}else
				--i;
			ticks.put(e, i);
		}
	}
	
	@SideOnly(Side.CLIENT)
	@SuppressWarnings("unchecked")
	public void client_manageBook(EntityPlayer e)
	{
		if(Keyboard.isKeyDown(Keyboard.KEY_R) && !isRKeyPressed)
		{
			isRKeyPressed = true;
			ItemStack currentPlayerItem = e.getCurrentEquippedItem();
			if(currentPlayerItem != null && currentPlayerItem.getItem() == ItemsCore.research_book)
			{
				boolean foundItem = false;
				Vec3 playerLookVec = e.getLookVec();
				Vec3 itemSearchVec = Vec3.createVectorHelper(playerLookVec.xCoord, playerLookVec.yCoord, playerLookVec.zCoord);
				//Searching for EntityItem
				s:for(int o = 0; o < 4; ++o)
				{
					itemSearchVec.xCoord *= o+1;
					itemSearchVec.yCoord *= o+1;
					itemSearchVec.zCoord *= o+1;
					List<EntityItem> lst = e.worldObj.getEntitiesWithinAABB(EntityItem.class, AxisAlignedBB.getBoundingBox(e.posX+itemSearchVec.xCoord-0.5D, e.posY+itemSearchVec.yCoord-0.5D, e.posZ+itemSearchVec.zCoord-0.5D, e.posX+itemSearchVec.xCoord+0.5D, e.posY+itemSearchVec.yCoord+0.5D, e.posZ+itemSearchVec.zCoord+0.5D));
					if(!lst.isEmpty())
					{
						for(EntityItem itm : lst)
						{
							if(itm != null && !itm.isDead)
							{
								ItemStack heldIS = itm.getEntityItem().copy();
				    			if(ApiCore.findDiscoveryByIS(heldIS) != null)
				    			{
				    				DiscoveryEntry switchTo = ApiCore.findDiscoveryByIS(heldIS);
				    				GuiResearchBook.currentPage = 0;
				    				if(GuiResearchBook.currentCategory == null)
				    					GuiResearchBook.currentCategory = ResearchRegistry.basic;
				    				GuiResearchBook.currentPage_discovery = 0;
				    				GuiResearchBook.currentDiscovery = switchTo;
				    				if(switchTo != null)
				    				{
				    					f:for(int i = 0; i < switchTo.pages.size(); ++i)
				    					{
				    						PageEntry entry = switchTo.pages.get(i);
				    						if(entry != null)
				    						{
				    							if(entry.displayedItems != null && entry.displayedItems.length > 0)
				    							{
				    								for(ItemStack is : entry.displayedItems)
				    								{
				    									if(heldIS.isItemEqual(is))
				    									{
				    										GuiResearchBook.currentPage = i - i%2;
				    										break f;
				    									}
				    								}
				    							}
				    							if(entry.pageRecipe != null)
				    							{
				    								ItemStack result = entry.pageRecipe.getRecipeOutput();
				    								if(result.isItemEqual(heldIS))
				    								{
				    									GuiResearchBook.currentPage = i - i%2;
														break f;
				    								}
				    							}
				    						}
				    					}
				    				}
				    				EssentialCraftCore.proxy.openBookGUIForPlayer();
				    				GuiResearchBook book = (GuiResearchBook) Minecraft.getMinecraft().currentScreen;
				    				book.initGui();
				    				foundItem = true;
				    				break s;
				    			}
							}
						}
					}
				}
				if(!foundItem)
				{
					Vec3 blockSearchVec = Vec3.createVectorHelper(playerLookVec.xCoord, playerLookVec.yCoord, playerLookVec.zCoord);
					//Searching for Block
					s:for(int o = 0; o < 4; ++o)
					{
						blockSearchVec.xCoord *= o+1;
						blockSearchVec.yCoord *= o+1;
						blockSearchVec.zCoord *= o+1;
						Block blk = e.worldObj.getBlock(MathHelper.floor_double(blockSearchVec.xCoord+e.posX), MathHelper.floor_double(blockSearchVec.yCoord+e.posY), MathHelper.floor_double(blockSearchVec.zCoord+e.posZ));
						if(blk != null && blk != Blocks.air)
						{
							ItemStack heldIS = new ItemStack(blk,1,e.worldObj.getBlockMetadata(MathHelper.floor_double(blockSearchVec.xCoord+e.posX), MathHelper.floor_double(blockSearchVec.yCoord+e.posY), MathHelper.floor_double(blockSearchVec.zCoord+e.posZ)));
			    			if(ApiCore.findDiscoveryByIS(heldIS) != null)
			    			{
			    				DiscoveryEntry switchTo = ApiCore.findDiscoveryByIS(heldIS);
			    				GuiResearchBook.currentPage = 0;
			    				if(GuiResearchBook.currentCategory == null)
			    					GuiResearchBook.currentCategory = ResearchRegistry.basic;
			    				GuiResearchBook.currentPage_discovery = 0;
			    				GuiResearchBook.currentDiscovery = switchTo;
			    				if(switchTo != null)
			    				{
			    					f:for(int i = 0; i < switchTo.pages.size(); ++i)
			    					{
			    						PageEntry entry = switchTo.pages.get(i);
			    						if(entry != null)
			    						{
			    							if(entry.displayedItems != null && entry.displayedItems.length > 0)
			    							{
			    								for(ItemStack is : entry.displayedItems)
			    								{
			    									if(heldIS.isItemEqual(is))
			    									{
			    										GuiResearchBook.currentPage = i - i%2;
			    										break f;
			    									}
			    								}
			    							}
			    							if(entry.pageRecipe != null)
			    							{
			    								ItemStack result = entry.pageRecipe.getRecipeOutput();
			    								if(result.isItemEqual(heldIS))
			    								{
			    									GuiResearchBook.currentPage = i - i%2;
													break f;
			    								}
			    							}
			    						}
			    					}
			    				}
			    				EssentialCraftCore.proxy.openBookGUIForPlayer();
			    				GuiResearchBook book = (GuiResearchBook) Minecraft.getMinecraft().currentScreen;
			    				book.initGui();
			    				break s;
			    			}
						}
					}
				}
			}
		}
		if(!Keyboard.isKeyDown(Keyboard.KEY_R) && isRKeyPressed)
		{
			isRKeyPressed = false;
		}
	}
	
	@SideOnly(Side.CLIENT)
	public void client_manageWorldEvents(EntityPlayer e)
	{
		if(e.dimension == Config.dimensionID)
		{
			if(e.worldObj.provider.dimensionId == Config.dimensionID)
			{
				((WorldProviderFirstWorld)(e.worldObj.provider)).generateLightBrightnessTable();
				if(ECUtils.isEventActive("ec3.event.darkness"))
				{
					if(e.worldObj.rand.nextFloat() < 0.01F)
						e.worldObj.playSound(e.posX,e.posY,e.posZ, "ambient.cave.cave", 1, e.worldObj.rand.nextFloat()*2, true);
					if(e.worldObj.rand.nextFloat() < 0.001F)
					{
						String[] sound = {"mob.zombie.death","mob.zombie.say","mob.blaze.death","mob.skeleton.step","mob.endermen.stare","mob.spider.step","mob.spider.death","mob.spider.say","mob.creeper.death"};
						
						e.worldObj.playSound(e.posX+MathUtils.randomDouble(e.worldObj.rand)*16,e.posY,e.posZ+MathUtils.randomDouble(e.worldObj.rand)*16, sound[e.worldObj.rand.nextInt(sound.length)], 1, 0.01F, true);
					}
						
				}
				boolean ignoreEarthquake = false;
		    	IInventory b = BaublesApi.getBaubles(e);
		    	if(b != null)
		    	{
		    		for(int i = 0; i < b.getSizeInventory(); ++i)
		    		{
		    			ItemStack is = b.getStackInSlot(i);
		    			if(is != null && is.getItem() != null && is.getItem() instanceof BaublesModifier && is.getItemDamage() == 19)
		    				ignoreEarthquake = true;
		    		}
		    	}
				if(ECUtils.isEventActive("ec3.event.earthquake"))
				{
					e.cameraPitch += MathUtils.randomFloat(e.worldObj.rand);
					e.rotationYaw += MathUtils.randomFloat(e.worldObj.rand);
					e.motionX += MathUtils.randomFloat(e.worldObj.rand)/30;
					e.motionY += MathUtils.randomFloat(e.worldObj.rand)/30;
					e.motionZ += MathUtils.randomFloat(e.worldObj.rand)/30;
					if(e.worldObj.rand.nextFloat() < 0.01F)
					{
						if(!ignoreEarthquake)
						{
							e.cameraPitch += MathUtils.randomFloat(e.worldObj.rand)*90;
							e.rotationYaw += MathUtils.randomFloat(e.worldObj.rand)*90;
							e.motionX += MathUtils.randomFloat(e.worldObj.rand)*3;
							e.motionY += MathUtils.randomFloat(e.worldObj.rand)*3;
							e.motionZ += MathUtils.randomFloat(e.worldObj.rand)*3;
						}
						e.worldObj.playSound(e.posX,e.posY,e.posZ, "random.explode", 0.1F, 0.1F, true);
					}
				}
			}
		}
	}
	
	@SubscribeEvent
	public void save(PlayerEvent.SaveToFile event)
	{
		EntityPlayer e = event.entityPlayer;
		if(!e.worldObj.isRemote)
		{
			File f = event.playerDirectory;
			if(f != null)
			{
				String fPath = f.getAbsolutePath();
				File saveFile = new File(fPath+"//"+e.getCommandSenderName()+".ecdat");
				if(saveFile.isDirectory())
				{
					//???
					saveFile.delete();
					try{saveFile.createNewFile();}catch(IOException Ex){Ex.printStackTrace();}
				}
				if(!saveFile.exists())
				{
					try{saveFile.createNewFile();}catch(IOException Ex){Ex.printStackTrace();}
				}
				
				try
				{
					FileOutputStream oStream = new FileOutputStream(saveFile);
					try
					{
						NBTTagCompound tag = new NBTTagCompound();
						ECUtils.getData(e).writeToNBTTagCompound(tag);
						CompressedStreamTools.writeCompressed(tag, oStream);
					}
					catch(Exception Ex)
					{
						FMLCommonHandler.instance().raiseException(Ex, "EssentialCraft3 Encountered an exception whlist saving playerdata NBT of player "+e.getCommandSenderName()+"!Report the error to the forum - http://www.minecraftforum.net/forums/mapping-and-modding/minecraft-mods/2286105", false);
					}
					finally
					{
						oStream.close();
					}
				}
				catch(Exception Exx)
				{
					FMLCommonHandler.instance().raiseException(Exx, "EssentialCraft3 Encountered an exception whlist wrighting playerdata file of player "+e.getCommandSenderName()+"! Make sure, that the file is not being accessed by other applications and is not threated as a virus by your anti-virus software! Also make sure, that you have some harddrive space. If everything above is correct - report the error to the forum - http://www.minecraftforum.net/forums/mapping-and-modding/minecraft-mods/2286105", true);
				}
			}
		}
	}
	
	@SubscribeEvent
	public void load(PlayerEvent.LoadFromFile event)
	{
		EntityPlayer e = event.entityPlayer;

		if(!e.worldObj.isRemote)
		{
			File f = event.playerDirectory;
			if(f != null)
			{
				String fPath = f.getAbsolutePath();
				File saveFile = new File(fPath+"//"+e.getCommandSenderName()+".ecdat");
				if(saveFile.isDirectory())
				{
					//???
					saveFile.delete();
					try{saveFile.createNewFile();}catch(IOException Ex){Ex.printStackTrace();}
				}
				if(!saveFile.exists())
				{
					try{saveFile.createNewFile();}catch(IOException Ex){Ex.printStackTrace();}
				}
				
				try
				{
					FileInputStream iStream = new FileInputStream(saveFile);
					try
					{
						NBTTagCompound tag = null;
						try
						{
							tag = CompressedStreamTools.readCompressed(iStream);
						}
						catch(java.io.EOFException EOFE)
						{
							//NBT could not be read, probably a first login.
							FMLLog.log(Level.WARN, "[EC3]Player data for player "+e.getCommandSenderName()+" could not be read. If it is the first time of the player to log in - it is fine. Otherwise, report the error to the author!");
						}
						
						if(tag != null)
							ECUtils.readOrCreatePlayerData(e, tag);
						else 
							ECUtils.createPlayerData(e);
					}
					catch(Exception Ex)
					{
						FMLCommonHandler.instance().raiseException(Ex, "EssentialCraft3 Encountered an exception whlist reading playerdata NBT of player "+e.getCommandSenderName()+"! It is totally fine if this is your first time opening the save. If it is not - report the error to the forum - http://www.minecraftforum.net/forums/mapping-and-modding/minecraft-mods/2286105", false);
						ECUtils.readOrCreatePlayerData(e, new NBTTagCompound());
					}
					finally
					{
						iStream.close();
					}
				}
				catch(Exception Exx)
				{
					FMLCommonHandler.instance().raiseException(Exx, "EssentialCraft3 Encountered an exception whlist opening playerdata file of player "+e.getCommandSenderName()+"! Make sure, that the file is not being accessed by other applications and is not threated as a virus by your anti-virus software! Also make sure, that you have some harddrive space. If everything above is correct - report the error to the forum - http://www.minecraftforum.net/forums/mapping-and-modding/minecraft-mods/2286105", true);
				}
			}
		}
	}
	
	@SuppressWarnings("unchecked")
	@SubscribeEvent
	public void tickEvent(PlayerTickEvent event)
	{
		if(event.phase == Phase.END)
		{
			EntityPlayer e = event.player;
			
			if(e == null)
				return;
				
			//TODO lagFix
			
			//Client overview
			if(e.worldObj.isRemote)
			{
				client_manageBook(e);
				client_manageWorldEvents(e);
			}
			
			if(!isFlightAllowed.containsKey(e))
				isFlightAllowed.put(e, false);
			
			//Step assistant, should 100% work with TC's system, not sure about Vazkii's one... If she calls it once per tick, it is fine. If it is only onEquiped(), then his baubles need to be re-equipped in order to work.
			if(!isWearingBoots.containsKey(e))
				isWearingBoots.put(e, false);
			
			if(e.inventory.armorInventory[0] != null && e.inventory.armorInventory[0].getItem() instanceof ItemComputerArmor)
			{
				e.fallDistance = 0;
			}
			
			if(e.inventory.armorInventory[3] != null && e.inventory.armorInventory[3].getItem() instanceof ItemComputerArmor)
			{
				e.setAir(300);
				e.getFoodStats().addStats(1, 1);
			}
			
			if(e.inventory.armorInventory[1] != null && e.inventory.armorInventory[1].getItem() instanceof ItemComputerArmor)
			{
				if(e.isBurning() && !e.worldObj.isRemote && e.ticksExisted%20 == 0)
					e.addPotionEffect(new PotionEffect(Potion.fireResistance.id,100,0,true));
			}
			
			if(e.inventory.armorInventory[0] != null && (e.inventory.armorInventory[0].getItem() instanceof ItemGenericArmor || e.inventory.armorInventory[0].getItem() instanceof ItemComputerArmor || e.inventory.armorInventory[0].getItem() == ItemsCore.magicArmorItems[7]) && (!isWearingBoots.get(e) || e.stepHeight < 1F))
			{
				isWearingBoots.put(e, true);
				e.stepHeight = 1F;
			}
			
			if((e.inventory.armorInventory[0] == null || !(e.inventory.armorInventory[0].getItem() instanceof ItemGenericArmor || e.inventory.armorInventory[0].getItem() instanceof ItemComputerArmor || e.inventory.armorInventory[0].getItem() == ItemsCore.magicArmorItems[7])) && isWearingBoots.get(e))
			{
				isWearingBoots.put(e, false);
				e.stepHeight = 0.5F;
			}
			
			if(BaublesApi.getBaubles(e) != null)
			{
				ItemStack belt = BaublesApi.getBaubles(e).getStackInSlot(3);
				if(belt != null)
				{
					if(belt.getItem() instanceof ItemComputerBoard)
					{
						Side s = FMLCommonHandler.instance().getEffectiveSide();
						if(s == Side.CLIENT)
						{
							this.client_flightAllowed = true;
							if(!e.capabilities.allowFlying && !e.capabilities.isCreativeMode)
							{
								e.capabilities.allowFlying = true;
								e.capabilities.setFlySpeed(0.2F);
							}
							
						}else
						{
							isFlightAllowed.put(e, true);
							if(!e.capabilities.allowFlying && !e.capabilities.isCreativeMode)
							{
								e.capabilities.allowFlying = true;
							}
						}
					}else
					{
						Side s = FMLCommonHandler.instance().getEffectiveSide();
						if(s == Side.CLIENT)
						{
							if(client_flightAllowed)
							{
								client_flightAllowed = false;
								if(e.capabilities.allowFlying && !e.capabilities.isCreativeMode)
								{
									e.capabilities.allowFlying = false;
									if(e.capabilities.isFlying)
										e.capabilities.isFlying = false;
									e.capabilities.setFlySpeed(0.05F);
								}
								
							}
						}else
						{
							if(isFlightAllowed.get(e))
							{
								isFlightAllowed.put(e, false);
								if(e.capabilities.allowFlying && !e.capabilities.isCreativeMode)
								{
									e.capabilities.allowFlying = false;
									if(e.capabilities.isFlying)
										e.capabilities.isFlying = false;
								}
							}
						}
					}
				}else
				{
					Side s = FMLCommonHandler.instance().getEffectiveSide();
					if(s == Side.CLIENT)
					{
						if(client_flightAllowed)
						{
							client_flightAllowed = false;
							if(e.capabilities.allowFlying && !e.capabilities.isCreativeMode)
							{
								e.capabilities.allowFlying = false;
								if(e.capabilities.isFlying)
									e.capabilities.isFlying = false;
								e.capabilities.setFlySpeed(0.05F);
							}
							
						}
					}else
					{
						if(isFlightAllowed.get(e))
						{
							isFlightAllowed.put(e, false);
							if(e.capabilities.allowFlying && !e.capabilities.isCreativeMode)
							{
								e.capabilities.allowFlying = false;
								if(e.capabilities.isFlying)
									e.capabilities.isFlying = false;
							}
						}
					}
				}
			}
			
			if(e.ticksExisted % 20 != 0)
				return;
			
			//Server overview
			if(!e.worldObj.isRemote)
			{
				manageSync(e);
				manageWorldSync(e);
				WindRelations.playerTick(e);
				RadiationManager.playerTick(e);
				//System.out.println(ECUtils.getData(e).damage);
				if(e.ticksExisted % 200 == 0)
					ECUtils.requestSync(e);
				if(e.ticksExisted % 1200 == 0)
				{
					PlayerGenericData data = ECUtils.getData(e);
					if(e.worldObj.rand.nextInt(36000) <= data.getOverhaulDamage())
					{
						ArrayList<ICorruptionEffect> possibleEffects = new ArrayList<ICorruptionEffect>();
						ArrayList<ICorruptionEffect> playerEffects = ArrayList.class.cast(data.getEffects());
						ArrayList<ICorruptionEffect> costSelected = CorruptionEffectLibrary.findSutableEffects(data.getOverhaulDamage());
						for(int i = 0; i < costSelected.size(); ++i)
						{
							ICorruptionEffect selected = costSelected.get(i);
							boolean canAdd = selected != null;
							if(selected != null)
							{
								if(!playerEffects.isEmpty())
									J:for(int j = 0; j < playerEffects.size(); ++j)
									{
										ICorruptionEffect playerEffect = playerEffects.get(j);
										if(playerEffect == null)
											continue J; 
										if(selected.effectEquals(playerEffect))
										{
											if(!selected.canMultiply())
											{
												canAdd = false;
												break J;
											}
										}
									}
									if(canAdd)
										possibleEffects.add(selected);
							}
						}
						if(!possibleEffects.isEmpty())
						{
							ICorruptionEffect added = possibleEffects.get(e.worldObj.rand.nextInt(possibleEffects.size())).copy();
							data.modifyOverhaulDamage(data.getOverhaulDamage() - added.getStickiness());
							data.getEffects().add(added);
							e.addChatMessage(new ChatComponentText(StatCollector.translateToLocal("ec3.txt.corruption")).setChatStyle(new ChatStyle().setColor(EnumChatFormatting.RED)));
							ECUtils.requestSync(e);
						}
					}
				}
				for(int i = 0; i < ECUtils.getData(e).effects.size(); ++i)
				{
					ICorruptionEffect effect = ECUtils.getData(e).effects.get(i);
					effect.onPlayerTick(e);
				}
				
				if(WorldEventLibrary.currentEvent != null)
					WorldEventLibrary.currentEvent.playerTick(e, WorldEventLibrary.currentEventDuration);
				
					World wrd = e.worldObj;
					List<EntityItem> itemList = wrd.getEntitiesWithinAABB(EntityItem.class,AxisAlignedBB.getBoundingBox(e.posX-0.5D, e.posY-0.5D, e.posZ-0.5D, e.posX+0.5D, e.posY+0.5D, e.posZ+0.5D).expand(2, 1, 2));
					for(int i = 0; i < itemList.size(); ++i)
					{
						doGroundItemChecks((EntityItem) itemList.get(i));
					}
			}
		}
	}
	
	public void doGroundItemChecks(EntityItem item)
	{
		if(item.getEntityItem().getItem() == Items.blaze_powder)
		{
			Block b = item.worldObj.getBlock(MathHelper.floor_double(item.posX), MathHelper.floor_double(item.posY)-1, MathHelper.floor_double(item.posZ));
			if(b == Blocks.netherrack)
			{
				Block b_check_b1 = item.worldObj.getBlock(MathHelper.floor_double(item.posX)+1, MathHelper.floor_double(item.posY)-1, MathHelper.floor_double(item.posZ));
				Block b_check_b2 = item.worldObj.getBlock(MathHelper.floor_double(item.posX)-1, MathHelper.floor_double(item.posY)-1, MathHelper.floor_double(item.posZ));
				Block b_check_b3 = item.worldObj.getBlock(MathHelper.floor_double(item.posX), MathHelper.floor_double(item.posY)-1, MathHelper.floor_double(item.posZ)+1);
				Block b_check_b4 = item.worldObj.getBlock(MathHelper.floor_double(item.posX), MathHelper.floor_double(item.posY)-1, MathHelper.floor_double(item.posZ)-1);
				Block b_check_air = item.worldObj.getBlock(MathHelper.floor_double(item.posX), (int)item.posY, MathHelper.floor_double(item.posZ));
				if(b_check_air.isAir(item.worldObj,MathHelper.floor_double(item.posX), (int)item.posY, MathHelper.floor_double(item.posZ)) && b_check_b1.getMaterial() == Material.lava && b_check_b4.getMaterial() == Material.lava && b_check_b2.getMaterial() == Material.lava && b_check_b3.getMaterial() == Material.lava)
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
						item.worldObj.setBlock(MathHelper.floor_double(item.posX), (int)item.posY, MathHelper.floor_double(item.posZ), BlocksCore.drops, 0, 3);
					}
				}
			}
		}else
		if(item.getEntityItem().getItem() == Items.clay_ball)
		{
			Block b = item.worldObj.getBlock(MathHelper.floor_double(item.posX), MathHelper.floor_double(item.posY)-1, MathHelper.floor_double(item.posZ));
			if(b == Blocks.ice)
			{
				Block b_check_b1 = item.worldObj.getBlock(MathHelper.floor_double(item.posX)+1, MathHelper.floor_double(item.posY)-1, MathHelper.floor_double(item.posZ));
				Block b_check_b2 = item.worldObj.getBlock(MathHelper.floor_double(item.posX)-1, MathHelper.floor_double(item.posY)-1, MathHelper.floor_double(item.posZ));
				Block b_check_b3 = item.worldObj.getBlock(MathHelper.floor_double(item.posX), MathHelper.floor_double(item.posY)-1, MathHelper.floor_double(item.posZ)+1);
				Block b_check_b4 = item.worldObj.getBlock(MathHelper.floor_double(item.posX), MathHelper.floor_double(item.posY)-1, MathHelper.floor_double(item.posZ)-1);
				Block b_check_air = item.worldObj.getBlock(MathHelper.floor_double(item.posX), (int)item.posY, MathHelper.floor_double(item.posZ));
				if(b_check_air.isAir(item.worldObj,MathHelper.floor_double(item.posX), (int)item.posY, MathHelper.floor_double(item.posZ)) && b_check_b1.getMaterial() == Material.water && b_check_b4.getMaterial() == Material.water && b_check_b2.getMaterial() == Material.water && b_check_b3.getMaterial() == Material.water)
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
						item.worldObj.setBlock(MathHelper.floor_double(item.posX), (int)item.posY, MathHelper.floor_double(item.posZ), BlocksCore.drops, 1, 3);
					}
				}
			}
		}else
		if(item.getEntityItem().getItem() == Items.slime_ball)
		{
			Block b = item.worldObj.getBlock(MathHelper.floor_double(item.posX), MathHelper.floor_double(item.posY)-1, MathHelper.floor_double(item.posZ));
			if(b == Blocks.grass)
			{
				Block b_check_b1 = item.worldObj.getBlock(MathHelper.floor_double(item.posX)+1, MathHelper.floor_double(item.posY)-1, MathHelper.floor_double(item.posZ));
				Block b_check_b2 = item.worldObj.getBlock(MathHelper.floor_double(item.posX)-1, MathHelper.floor_double(item.posY)-1, MathHelper.floor_double(item.posZ));
				Block b_check_b3 = item.worldObj.getBlock(MathHelper.floor_double(item.posX), MathHelper.floor_double(item.posY)-1, MathHelper.floor_double(item.posZ)+1);
				Block b_check_b4 = item.worldObj.getBlock(MathHelper.floor_double(item.posX), MathHelper.floor_double(item.posY)-1, MathHelper.floor_double(item.posZ)-1);
				Block b_check_air = item.worldObj.getBlock(MathHelper.floor_double(item.posX), (int)item.posY, MathHelper.floor_double(item.posZ));
				if(b_check_air.isAir(item.worldObj,MathHelper.floor_double(item.posX), (int)item.posY, MathHelper.floor_double(item.posZ)) && b_check_b1 == Blocks.mossy_cobblestone && b_check_b4 == Blocks.mossy_cobblestone && b_check_b2 == Blocks.mossy_cobblestone && b_check_b3 == Blocks.mossy_cobblestone)
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
						item.worldObj.setBlock(MathHelper.floor_double(item.posX), (int)item.posY, MathHelper.floor_double(item.posZ), BlocksCore.drops, 2, 3);
					}
				}
			}
		}else
		if(item.getEntityItem().getItem() == Items.gunpowder)
		{
			Block b = item.worldObj.getBlock(MathHelper.floor_double(item.posX), MathHelper.floor_double(item.posY)-1, MathHelper.floor_double(item.posZ));
			if(b == Blocks.quartz_block)
			{
				Block b_check_b1 = item.worldObj.getBlock(MathHelper.floor_double(item.posX)+1, MathHelper.floor_double(item.posY)-1, MathHelper.floor_double(item.posZ));
				Block b_check_b2 = item.worldObj.getBlock(MathHelper.floor_double(item.posX)-1, MathHelper.floor_double(item.posY)-1, MathHelper.floor_double(item.posZ));
				Block b_check_b3 = item.worldObj.getBlock(MathHelper.floor_double(item.posX), MathHelper.floor_double(item.posY)-1, MathHelper.floor_double(item.posZ)+1);
				Block b_check_b4 = item.worldObj.getBlock(MathHelper.floor_double(item.posX), MathHelper.floor_double(item.posY)-1, MathHelper.floor_double(item.posZ)-1);
				Block b_check_air = item.worldObj.getBlock(MathHelper.floor_double(item.posX), (int)item.posY, MathHelper.floor_double(item.posZ));
				if(b_check_air.isAir(item.worldObj,MathHelper.floor_double(item.posX), (int)item.posY, MathHelper.floor_double(item.posZ)) && b_check_b1 == Blocks.sand && b_check_b4 == Blocks.sand && b_check_b2 == Blocks.sand && b_check_b3 == Blocks.sand)
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
						item.worldObj.setBlock(MathHelper.floor_double(item.posX), (int)item.posY, MathHelper.floor_double(item.posZ), BlocksCore.drops, 3, 3);
					}
				}
			}
		}else
		{
			if(item.getEntityItem().getItem() == Items.diamond)
			{
				Block b = item.worldObj.getBlock(MathHelper.floor_double(item.posX), MathHelper.floor_double(item.posY)-1, MathHelper.floor_double(item.posZ));
				if(b == Blocks.emerald_block)
				{
					Block b_check_air = item.worldObj.getBlock(MathHelper.floor_double(item.posX), (int)item.posY, MathHelper.floor_double(item.posZ));
					if(b_check_air.isAir(item.worldObj,MathHelper.floor_double(item.posX), (int)item.posY, MathHelper.floor_double(item.posZ)))
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
					}
				}
			}else
			{
				if(item.getEntityItem().getItem() == Items.emerald)
				{
					Block b = item.worldObj.getBlock(MathHelper.floor_double(item.posX), MathHelper.floor_double(item.posY)-1, MathHelper.floor_double(item.posZ));
					if(b == Blocks.emerald_block)
					{
						Block b_check_air = item.worldObj.getBlock(MathHelper.floor_double(item.posX), (int)item.posY, MathHelper.floor_double(item.posZ));
						if(b_check_air.isAir(item.worldObj, MathHelper.floor_double(item.posX), (int)item.posY, MathHelper.floor_double(item.posZ)))
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
						}
					}
				}
			}
		}
	}

}
