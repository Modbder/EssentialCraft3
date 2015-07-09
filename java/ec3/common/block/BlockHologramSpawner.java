package ec3.common.block;

import ec3.common.entity.EntityHologram;
import ec3.utils.cfg.Config;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

public class BlockHologramSpawner extends Block{

	public BlockHologramSpawner() 
	{
		super(Material.rock);
		this.setBlockBounds(0, 0, 0, 1, 0.5F, 1);
	}
	
    public boolean onBlockActivated(World w, int x, int y, int z, EntityPlayer p, int p_149727_6_, float p_149727_7_, float p_149727_8_, float p_149727_9_)
    {
    	if(!w.isRemote && w.provider != null && (w.provider.dimensionId == Config.dimensionID || Config.allowHologramInOtherDimensions))
    	{
    		w.setBlockToAir(x, y, z);
    		EntityHologram hg = new EntityHologram(w);
    		hg.setPositionAndRotation(x+0.5D, y+5, z+0.5D, 0, 0);
    		w.spawnEntityInWorld(hg);
    	}
        return true;
    }
	
}
