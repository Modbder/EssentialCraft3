package ec3.api;

import java.util.ArrayList;

import DummyCore.Utils.UnformedItemStack;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;

public interface ICorruptionEffect {
	
	public abstract void readFromNBTTagCompound(NBTTagCompound tag, int tagID);
	
	public abstract void writeToNBTTagCompound(NBTTagCompound tag, int tagID);
	
	public abstract EnumCorruptionEffect getType();
	
	public abstract void onPlayerTick(EntityPlayer player);
	
	public abstract ResourceLocation getEffectIcon();
	
	public abstract int getStickiness();
	
	public abstract ICorruptionEffect copy();
	
	public abstract boolean canMultiply();
	
	public abstract ArrayList<UnformedItemStack> cureItems();
	
	public abstract boolean effectEquals(ICorruptionEffect effect);
	
	public abstract String getLocalizedName();
	
	public abstract String getLocalizedDesc();

}
