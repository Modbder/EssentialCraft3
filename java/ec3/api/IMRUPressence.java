package ec3.api;

/**
 * 
 * @author Modbder
 * @Description use this interface to interact with MRU concentration centers. Remember, that MRUPressence is an Entity, so you can get it via List and the world.getEntitiesWithinAABB(IMruPressence.class,AxisAlignedBB)
 * 
 */
public interface IMRUPressence {
	
	/**
	 * A method to know can the MRUPressence increase it's MRU by it's own.
	 * @return true if can, false if can't
	 */
	public boolean getFlag();
	
	/**
	 * You can change will the MRUPressence increase MRU in it by it's own or not
	 * @param b : boolean - true if you want, false if you don't
	 */
	public void setFlag(boolean b);
	
	
	/**
	 * This is a chesk, if can the MRUPressence leave in the world, if it has 0 mru left.
	 * @return true if it stays, false if it will be removed from the world.
	 */
	public boolean canAlwaysStay();
	
	/**
	 * You can change will the MRUPressence stay in the world if it has 0 energy left.
	 * @param b : boolean - true if you want it to stay, false if you want it to dissapear
	 */
	public void setAlwaysStay(boolean b);
	
	
	/**
	 * The MRU balance. The basics of the corruption mechanics. Should return 1.0F if the MRUPressence is not corrupted, and anything else if it is.
	 * @return 1.0F if not corrupted, anything from 0.0F to 2.0F if it is.
	 */
	public float getBalance();
	
	/**
	 * You can change the balance using this. Do not put numbers lower than 0.1F and greater than 1.9F. 
	 * @param f : float - the more difference it has to 1.0F the faster the MRU amount will increase. The more the amount is, the faster the MRUPressence will spin.
	 */
	public void setBalance(float f);
	
	/**
	 * The mru amount currently stored in the MRUPressence. The higher the number is, the more corruption will be affected.
	 * @return integer of the MRU amount.
	 */
	public int getMRU();
	
	/**
	 * Set your mru amount here. If the MRUPressence is corrupted, and MRU is higher than 50000 it will automatically release all corruption in the world and become pure.
	 * @param i : integer. Do not put values lower than 0.
	 */
	public void setMRU(int i);

}
