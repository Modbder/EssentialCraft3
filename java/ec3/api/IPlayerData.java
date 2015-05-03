package ec3.api;

import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;

public interface IPlayerData {
	
	public abstract int getOverhaulDamage();
	
	public abstract void modifyOverhaulDamage(int newDamage);
	
	public abstract int getPlayerRadiation();
	
	public abstract void modifyRadiation(int newRad);
	
	public abstract int getPlayerWindPoints();
	
	public abstract void modifyWindpoints(int newWind);
	
	public abstract boolean isWindbound();
	
	public abstract void modifyWindbound(boolean newValue);
	
	public abstract int getPlayerUBMRU();
	
	public abstract void modifyUBMRU(int newubmru);
	
	public abstract int getMatrixTypeID();
	
	public abstract void modifyMatrixType(int newType);
	
	public abstract List<ICorruptionEffect> getEffects();
	
	public abstract void readFromNBTTagCompound(NBTTagCompound tag);
	
	public abstract void writeToNBTTagCompound(NBTTagCompound tag);
	
	public abstract EntityPlayer carrier();

}
