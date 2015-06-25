package ec3.network.proxy;

import io.netty.channel.ChannelHandler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.server.MinecraftServer;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import ec3.common.mod.EssentialCraftCore;
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
		}
		return null;
	}

}
