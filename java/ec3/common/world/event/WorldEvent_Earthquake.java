package ec3.common.world.event;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.World;
import ec3.api.IWorldEvent;
import ec3.utils.cfg.Config;
import ec3.utils.common.ECUtils;

public class WorldEvent_Earthquake implements IWorldEvent{

	@Override
	public void onEventBeginning(World w) {
		ECUtils.sendChatMessageToAllPlayersInDim(Config.dimensionID, EnumChatFormatting.RED+"The ground is shaking...");
	}

	@Override
	public void worldTick(World w, int leftoverTime) {
	}

	@Override
	public void playerTick(EntityPlayer p, int leftoverTime) {
		
	}

	@Override
	public void onEventEnd(World w) {
		ECUtils.sendChatMessageToAllPlayersInDim(Config.dimensionID, EnumChatFormatting.GREEN+"The ground is solid again!");
	}

	@Override
	public int getEventDuration(World w) {
		return 5000;
	}

	@Override
	public boolean possibleToApply(World w) {
		return w.provider.dimensionId == Config.dimensionID;
	}

	@Override
	public float getEventProbability(World w) {
		return 0.0001F;
	}

	@Override
	public String getEventID() {
		return "ec3.event.earthquake";
	}

}
