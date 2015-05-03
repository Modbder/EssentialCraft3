package ec3.network;

import net.minecraft.nbt.NBTTagCompound;
import io.netty.buffer.ByteBuf;
import cpw.mods.fml.common.network.ByteBufUtils;
import cpw.mods.fml.common.network.simpleimpl.IMessage;

public class PacketNBT implements IMessage{
	
	public NBTTagCompound theTag;
	public int packetID;
	
	public PacketNBT()
	{
		//FML Initialisation
	}
	
	public PacketNBT(NBTTagCompound t)
	{
		theTag = t;
	}
	
	public PacketNBT setID(int i)
	{
		packetID = i;
		return this;
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		theTag = ByteBufUtils.readTag(buf);
		packetID = theTag.getInteger("ec3packetData.id");
	}

	@Override
	public void toBytes(ByteBuf buf) {
		theTag.setInteger("ec3packetData.id", packetID);
		ByteBufUtils.writeTag(buf, theTag);
	}

}
