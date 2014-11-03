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

public class WorldEvent_Darkness implements IWorldEvent{

	@Override
	public void onEventBeginning(World w) {
		ECUtils.sendChatMessageToAllPlayersInDim(53, EnumChatFormatting.RED+"Why is it so dark?!");
		((WorldProviderFirstWorld)w.provider).generateLightBrightnessTable();
	}

	@Override
	public void worldTick(World w, int leftoverTime) {
		if(w.provider.dimensionId == 53)
			((WorldProviderFirstWorld)w.provider).generateLightBrightnessTable();
	}

	@Override
	public void playerTick(EntityPlayer p, int leftoverTime) {
		
	}

	@Override
	public void onEventEnd(World w) {
		ECUtils.sendChatMessageToAllPlayersInDim(53, EnumChatFormatting.GREEN+"The lights are back!");
		if(w.provider.dimensionId == 53)
			((WorldProviderFirstWorld)w.provider).generateLightBrightnessTable();
	}

	@Override
	public int getEventDuration(World w) {
		// TODO Auto-generated method stub
		return 24000*(w.rand.nextInt(3)+1);
	}

	@Override
	public boolean possibleToApply(World w) {
		// TODO Auto-generated method stub
		return w.provider.dimensionId == 53;
	}

	@Override
	public float getEventProbability(World w) {
		// TODO Auto-generated method stub
		return 0.00005F;
	}

	@Override
	public String getEventID() {
		// TODO Auto-generated method stub
		return "ec3.event.darkness";
	}

}
