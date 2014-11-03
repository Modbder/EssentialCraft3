package ec3.common.block;

import java.util.Random;

import DummyCore.Utils.MathUtils;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ec3.common.mod.EssentialCraftCore;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

public class BlockMRULevitator extends Block{

	public BlockMRULevitator(Material p_i45394_1_) {
		super(p_i45394_1_);
		this.setTickRandomly(true);
		// TODO Auto-generated constructor stub
	}
	
    @SideOnly(Side.CLIENT)
    public void randomDisplayTick(World p_149734_1_, int p_149734_2_, int p_149734_3_, int p_149734_4_, Random p_149734_5_) 
    {
    	for(int i = 0; i < 10; ++i)
    	{
    		EssentialCraftCore.proxy.spawnParticle("mruFX", p_149734_2_+0.5F+MathUtils.randomFloat(p_149734_5_)/5, p_149734_3_+0.5F, p_149734_4_+0.5F+MathUtils.randomFloat(p_149734_5_)/5, 0, -5-MathUtils.randomFloat(p_149734_5_)*5, 0);
    		Vec3 rotateVec = Vec3.createVectorHelper(1, 0, 1);
    		rotateVec.rotateAroundY(i*36);
    		for(int i1 = 0; i1 < 3; ++i1)
    			EssentialCraftCore.proxy.spawnParticle("mruFX", p_149734_2_+0.5F+MathUtils.randomFloat(p_149734_5_)/5, p_149734_3_+0.25F, p_149734_4_+0.5F+MathUtils.randomFloat(p_149734_5_)/5, rotateVec.xCoord, 0, rotateVec.zCoord);
    		rotateVec = null;
    	}
    		
    }
    
    public boolean isOpaqueCube()
    {
        return false;
    }
    
	@Override
    public int getRenderBlockPass()
    {
        return 0;
    }
    
    public boolean renderAsNormalBlock()
    {
        return false;
    }
    
    public int getRenderType()
    {
        return 2634;
    }


}
