package ec3.api;

import net.minecraft.tileentity.TileEntity;

/**
 * 
 * @author Modbder
 * @Description implement this in your tileEntities so they can be counted as pieces of structure AND you can reference to the main part of the structure
 */
public interface IStructurePiece {
	
	/**
	 * Use this to determine in which structure your tile is used
	 * @return the corresponding Enum
	 */
	public abstract EnumStructureType getStructure();
	
	/**
	 * You can use this any time to get the actual controller of the structure(block that holds all data)
	 * @return the corresponding TileEntity
	 */
	public abstract TileEntity structureController();
	
	/**
	 * This will be used by the structure central block if the structure is correct to give you the data needed. Do any variable setups here.
	 * @param tile - the Tile that is the core block
	 * @param structure - the structure type itself
	 */
	public abstract void setStructureController(TileEntity tile, EnumStructureType structure);

}
