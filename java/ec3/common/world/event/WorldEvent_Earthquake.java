package ec3.common.world.event;

import DummyCore.Utils.MathUtils;
import net.minecraft.entity.item.EntityFallingBlock;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import ec3.api.IWorldEvent;
import ec3.common.world.WorldProviderFirstWorld;
import ec3.utils.common.ECUtils;

public class WorldEvent_Earthquake implements IWorldEvent{

	@Override
	public void onEventBeginning(World w) {
		ECUtils.sendChatMessageToAllPlayersInDim(53, EnumChatFormatting.RED+"The ground is shaking...");
	}

	@Override
	public void worldTick(World w, int leftoverTime) {
	}

	@Override
	public void playerTick(EntityPlayer p, int leftoverTime) {
		
	}

	@Override
	public void onEventEnd(World w) {
		ECUtils.sendChatMessageToAllPlayersInDim(53, EnumChatFormatting.GREEN+"The ground is solid again!");
	}

	@Override
	public int getEventDuration(World w) {
		// TODO Auto-generated method stub
		return 5000;
	}

	@Override
	public boolean possibleToApply(World w) {
		// TODO Auto-generated method stub
		return w.provider.dimensionId == 53;
	}

	@Override
	public float getEventProbability(World w) {
		// TODO Auto-generated method stub
		return 0.0001F;
	}

	@Override
	public String getEventID() {
		// TODO Auto-generated method stub
		return "ec3.event.earthquake";
	}

}
