package ec3.network.proxy;

import io.netty.channel.ChannelHandler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.server.MinecraftServer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.WorldServer;
import net.minecraftforge.common.DimensionManager;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import ec3.client.gui.GuiNewMIMScreen;
import ec3.common.mod.EssentialCraftCore;
import ec3.common.tile.TileMagicalQuarry;
import ec3.common.tile.TileNewMIM;
import ec3.common.tile.TileNewMIMInventoryStorage;
import ec3.common.tile.TileNewMIMScreen;
import ec3.network.PacketNBT;
import ec3.utils.common.ECUtils;

@ChannelHandler.Sharable
public class EC3PacketDispatcher implements IMessageHandler<PacketNBT,IMessage>{

	@Override
	public IMessage onMessage(PacketNBT message, MessageContext ctx) 
	{
		switch(message.packetID)
		{
			case 0:
			{
				//Generic data SYNC.
				ECUtils.readOrCreatePlayerData(message.theTag.hasKey("syncplayer") ? MinecraftServer.getServer().getConfigurationManager().func_152612_a(message.theTag.getString("syncplayer")) : EssentialCraftCore.proxy.getClientPlayer(),message.theTag);
				break;
			}
			case 1:
			{
				//Server-side to client Sync request
				EntityPlayer target = MinecraftServer.getServer().getConfigurationManager().func_152612_a(message.theTag.getString("syncplayer"));
				EntityPlayer clientPlayer = MinecraftServer.getServer().getConfigurationManager().func_152612_a(message.theTag.getString("sender"));
				if(target != null)
				{
					NBTTagCompound theTag = new NBTTagCompound();
					theTag.setString("syncplayer", target.getCommandSenderName());
					ECUtils.getData(target).writeToNBTTagCompound(theTag);
					PacketNBT pkt = new PacketNBT(theTag).setID(0);
					EssentialCraftCore.network.sendTo(pkt, (EntityPlayerMP) clientPlayer);
				}
				break;
			}
			case 2:
			{
				ECUtils.ec3WorldTag = message.theTag;
				break;
			}
			case 3:
			{
				TileEntity tile = EssentialCraftCore.proxy.getClientPlayer().worldObj.getTileEntity(message.theTag.getInteger("x"), message.theTag.getInteger("y"), message.theTag.getInteger("z"));
				if(tile != null && tile instanceof TileNewMIMInventoryStorage)
				{
					TileNewMIMInventoryStorage.class.cast(tile).items.clear();
					for(int i = 0; i < message.theTag.getTagList("items", 10).tagCount(); ++i)
					{
						ItemStack s = ItemStack.loadItemStackFromNBT(message.theTag.getTagList("items", 10).getCompoundTagAt(i));
						s.stackSize = message.theTag.getTagList("items", 10).getCompoundTagAt(i).getInteger("stackSize");
						TileNewMIMInventoryStorage.class.cast(tile).items.add(s);
					}
				}
				break;
			}
			case 4:
			{
				TileEntity tile = EssentialCraftCore.proxy.getClientPlayer().worldObj.getTileEntity(message.theTag.getInteger("x"), message.theTag.getInteger("y"), message.theTag.getInteger("z"));
				if(tile != null && tile instanceof TileMagicalQuarry)
				{
					TileMagicalQuarry.class.cast(tile).miningX = message.theTag.getInteger("mx");
					TileMagicalQuarry.class.cast(tile).miningY = message.theTag.getInteger("my");
					TileMagicalQuarry.class.cast(tile).miningZ = message.theTag.getInteger("mz");
				}
				break;
			}case 5:
			{
				ItemStack retrieved = ItemStack.loadItemStackFromNBT(message.theTag.getCompoundTag("requestedItem"));
				
				EntityPlayerMP requester = MinecraftServer.getServer().getConfigurationManager().func_152612_a(message.theTag.getString("requester"));
				
				if(!message.theTag.getBoolean("craft"))
					retrieved.stackSize = message.theTag.getInteger("size");
				
				int size = message.theTag.getInteger("size");
				TileEntity tile = requester.worldObj.getTileEntity(message.theTag.getInteger("px"), message.theTag.getInteger("py"), message.theTag.getInteger("pz"));
				if(tile != null && tile instanceof TileNewMIM)
				{
					TileNewMIM mim = TileNewMIM.class.cast(tile);
					
					if(!message.theTag.getBoolean("craft"))
					{
						int left = mim.retrieveItemStackFromSystem(retrieved,false,true);
						if(left == 0)
						{
							
						}else
						{
							size -= left;
						}
						TileEntity t = requester.worldObj.getTileEntity(message.theTag.getInteger("x"), message.theTag.getInteger("y"), message.theTag.getInteger("z"));
						if(t != null && t instanceof TileNewMIMScreen)
						{
							TileNewMIMScreen sc = TileNewMIMScreen.class.cast(t);
							int left64 = size % 64;
							int times64 = size/64;
							for(int i = 0; i < size; ++i)
							{
								sc.setMRU(sc.getMRU()-TileNewMIMScreen.mruForOut);
							}
							for(int i = 0; i < times64; ++i)
							{
								ItemStack added = retrieved.copy();
								added.stackSize = 64;
								if(!requester.inventory.addItemStackToInventory(added))
									requester.dropPlayerItemWithRandomChoice(added, false);
							}
							ItemStack added = retrieved.copy();
							added.stackSize = left64;
							if(!requester.inventory.addItemStackToInventory(added))
								requester.dropPlayerItemWithRandomChoice(added, false);
							
							NBTTagCompound canDo = new NBTTagCompound();
							PacketNBT packet = new PacketNBT(canDo).setID(6);
							EssentialCraftCore.network.sendTo(packet, requester);
						}
					}else
					{
						mim.craftFromTheSystem(retrieved, size);
						NBTTagCompound canDo = new NBTTagCompound();
						PacketNBT packet = new PacketNBT(canDo).setID(6);
						EssentialCraftCore.network.sendTo(packet, requester);
					}
				}
				break;
			}case 6:
			{
				GuiNewMIMScreen.packetArrived = true;
				break;
			}
			case 7:
			{
				NBTTagCompound tag = message.theTag;
				if(tag != null && !tag.hasNoTags())
				{
					String playername = tag.getString("playername");
					EntityPlayer requester = MinecraftServer.getServer().getConfigurationManager().func_152612_a(playername);
					if(requester != null)
					{
						int dimID = tag.getInteger("dim");
						WorldServer world = DimensionManager.getWorld(dimID);
						if(world != null)
						{
							int x = tag.getInteger("x");
							int y = tag.getInteger("y");
							int z = tag.getInteger("z");
							if(world.blockExists(x, y, z))
							{
								if(world.getTileEntity(x, y, z) != null)
								{
									ECUtils.requestScheduledTileSync(world.getTileEntity(x, y, z), requester);
								}
							}
						}
					}
				}
				break;
			}
			
		}
		return null;
	}

}
