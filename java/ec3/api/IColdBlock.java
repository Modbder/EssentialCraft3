package ec3.api;

import net.minecraft.world.World;

/**
 * 
 * @author Modbder
 * @Description use this interface to create new blocks for the cold distillator to operate with
 * 
 */
public interface IColdBlock {
	
	/**
	 * This is used to check how many mru per tick will this block add
	 * @param meta - the metadata of the block 
	 * @return amount of mru per tick will this block add
	 */
	public float getColdModifier(World w, int x, int y, int z, int meta);
}
