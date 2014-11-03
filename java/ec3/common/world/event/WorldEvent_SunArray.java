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
import ec3.utils.common.ECUtils;

public class WorldEvent_SunArray implements IWorldEvent{

	@Override
	public void onEventBeginning(World w) {
		ECUtils.sendChatMessageToAllPlayersInDim(53, EnumChatFormatting.RED+"This is going to be a hot day...");
	}

	@Override
	public void worldTick(World w, int leftoverTime) {
		
	}

	@Override
	public void playerTick(EntityPlayer p, int leftoverTime) {
		if(!p.capabilities.isCreativeMode && p.dimension == 53 && p.worldObj.canBlockSeeTheSky(MathHelper.floor_double(p.posX), MathHelper.floor_double(p.posY+2), MathHelper.floor_double(p.posZ)))
		{
			p.attackEntityFrom(DamageSource.onFire, 1);
			p.setFire(10);
		}
		if(p.dimension == 53)
		{
			EntityFallingBlock sand = new EntityFallingBlock(p.worldObj, p.posX+MathUtils.randomDouble(p.worldObj.rand)*128, 255, p.posZ+MathUtils.randomDouble(p.worldObj.rand)*128, Blocks.fire);
			sand.field_145812_b = 3;
			sand.field_145813_c = false;
			if(!p.worldObj.isRemote)
				p.worldObj.spawnEntityInWorld(sand);
		}
		
	}

	@Override
	public void onEventEnd(World w) {
		ECUtils.sendChatMessageToAllPlayersInDim(53, EnumChatFormatting.GREEN+"The suns are back to normal!");
	}

	@Override
	public int getEventDuration(World w) {
		// TODO Auto-generated method stub
		return 12000;
	}

	@Override
	public boolean possibleToApply(World w) {
		// TODO Auto-generated method stub
		return w.provider.dimensionId == 53 && w.getWorldTime() % 24000L == 0;
	}

	@Override
	public float getEventProbability(World w) {
		// TODO Auto-generated method stub
		return 0.3F;
	}

	@Override
	public String getEventID() {
		// TODO Auto-generated method stub
		return "ec3.event.sunArray";
	}

}
