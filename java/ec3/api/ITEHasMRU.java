package ec3.api;

import java.util.UUID;

/**
 * 
 * @author Modbder
 * @Description do not use this interface to create new tile entities, this is the base for other 3 interfaces, use them
 */
public interface ITEHasMRU {
	
	/**
	 * this is used to get current MRU of your tile entities
	 * @return current amount of MRU
	 */
	public abstract int getMRU();
	
	/**
	 * this is used to get max MRU of your tile entities
	 * @return max amount of MRU the device can store
	 */
	public abstract int getMaxMRU();

	/**
	 * this is used to set current MRU of your tile entities
	 * @param i - the amount to add. Use negative values to remove MRU
	 * @return true if was successful, false if not
	 */
	public abstract boolean setMRU(int i);
	
	/**
	 * this is used to get the balance in the device.
	 * @return the amount of balanve there is
	 */
	public abstract float getBalance();
	
	/**
	 * this is used to set current balance of your tile entities
	 * @param f - the amount to add. Use negative values to decrease balance
	 * @return true if was successful, false if not
	 */
	public abstract boolean setBalance(float f);
	
	/**
	 * this is used to set maxMRU of your tile entities
	 * @param f - the amount to set.
	 * @return true if was successful, false if not
	 */
	public abstract boolean setMaxMRU(float f);
	
	public abstract UUID getUUID();

}
