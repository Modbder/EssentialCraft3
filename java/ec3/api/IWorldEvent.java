package ec3.api;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

public interface IWorldEvent {
	
	public abstract void onEventBeginning(World w);
	
	public abstract void worldTick(World w, int leftoverTime);
	
	public abstract void playerTick(EntityPlayer p, int leftoverTime);
	
	public abstract void onEventEnd(World w);
	
	public abstract int getEventDuration(World w);
	
	public abstract boolean possibleToApply(World w);
	
	public abstract float getEventProbability(World w);
	
	public abstract String getEventID();

}
