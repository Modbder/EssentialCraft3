package ec3.common.world.event;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.World;
import ec3.api.IWorldEvent;
import ec3.common.world.WorldProviderFirstWorld;
import ec3.utils.cfg.Config;
import ec3.utils.common.ECUtils;

public class WorldEvent_Darkness implements IWorldEvent{

	@Override
	public void onEventBeginning(World w) {
		ECUtils.sendChatMessageToAllPlayersInDim(Config.dimensionID, EnumChatFormatting.RED+"Why is it so dark?!");
		((WorldProviderFirstWorld)w.provider).generateLightBrightnessTable();
	}

	@Override
	public void worldTick(World w, int leftoverTime) {
		if(w.provider.dimensionId == Config.dimensionID)
			((WorldProviderFirstWorld)w.provider).generateLightBrightnessTable();
	}

	@Override
	public void playerTick(EntityPlayer p, int leftoverTime) {
		
	}

	@Override
	public void onEventEnd(World w) {
		ECUtils.sendChatMessageToAllPlayersInDim(Config.dimensionID, EnumChatFormatting.GREEN+"The lights are back!");
		if(w.provider.dimensionId == Config.dimensionID)
			((WorldProviderFirstWorld)w.provider).generateLightBrightnessTable();
	}

	@Override
	public int getEventDuration(World w) {
		return 24000*(w.rand.nextInt(3)+1);
	}

	@Override
	public boolean possibleToApply(World w) {
		return w.provider.dimensionId == Config.dimensionID;
	}

	@Override
	public float getEventProbability(World w) {
		return 0.00005F;
	}

	@Override
	public String getEventID() {
		return "ec3.event.darkness";
	}

}
