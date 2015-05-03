package ec3.utils.common;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import ec3.api.ICorruptionEffect;
import ec3.api.IPlayerData;

public class PlayerGenericData implements IPlayerData{
	
	int damage,radiation,wind,ubmru,matrixid;
	boolean windbound;
	final List<ICorruptionEffect> effects = new ArrayList<ICorruptionEffect>();
	EntityPlayer thePlayer;
	
	public PlayerGenericData(EntityPlayer p)
	{
		thePlayer = p;
	}

	@Override
	public int getOverhaulDamage() {
		return damage;
	}

	@Override
	public void modifyOverhaulDamage(int newDamage) {
		damage = newDamage;
	}

	@Override
	public int getPlayerRadiation() {
		return radiation;
	}

	@Override
	public void modifyRadiation(int newRad) {
		radiation = newRad;
	}

	@Override
	public int getPlayerWindPoints() {
		return wind;
	}

	@Override
	public void modifyWindpoints(int newWind) {
		wind = newWind;
	}

	@Override
	public boolean isWindbound() {
		return windbound;
	}

	@Override
	public void modifyWindbound(boolean newValue) {
		windbound = newValue;
	}

	@Override
	public int getPlayerUBMRU() {
		return ubmru;
	}

	@Override
	public void modifyUBMRU(int newubmru) {
		ubmru = newubmru;
	}

	@Override
	public int getMatrixTypeID() {
		return matrixid;
	}

	@Override
	public void modifyMatrixType(int newType) {
		matrixid = newType;
	}

	@Override
	public List<ICorruptionEffect> getEffects() {
		return effects;
	}

	@Override
	public void readFromNBTTagCompound(NBTTagCompound tag) {
		damage = tag.getInteger("damage");
		radiation = tag.getInteger("radiation");
		wind = tag.getInteger("wind");
		ubmru = tag.getInteger("ubmru");
		matrixid = tag.getInteger("matrixid");
		windbound = tag.getBoolean("windbound");
		effects.clear();
		
		for(int i = 0; i < tag.getInteger("effectsSize"); ++i)
		{
			CorruptionEffectEC3NBTBased effect = new CorruptionEffectEC3NBTBased();
			effect.readFromNBTTagCompound(tag, i);
			effects.add(effect);
		}
		
	}

	@Override
	public void writeToNBTTagCompound(NBTTagCompound tag) {
		
		tag.setInteger("damage", damage);
		tag.setInteger("radiation", radiation);
		tag.setInteger("wind", wind);
		tag.setInteger("ubmru", ubmru);
		tag.setInteger("matrixid", matrixid);
		tag.setBoolean("windbound", windbound);
		tag.setInteger("effectsSize", effects.size());
		for(int i = 0; i < effects.size(); ++i)
			effects.get(i).writeToNBTTagCompound(tag,i);
	}

	@Override
	public EntityPlayer carrier() {
		return thePlayer;
	}

}
