package ec3.common.block;

import java.util.Random;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ec3.common.mod.EssentialCraftCore;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

public class BlockMRUSpreader extends Block{

	public BlockMRUSpreader(Material p_i45394_1_) {
		super(p_i45394_1_);
		this.setTickRandomly(true);
		this.setLightLevel(1.0F);
		// TODO Auto-generated constructor stub
	}
	
    @SideOnly(Side.CLIENT)
    public void randomDisplayTick(World p_149734_1_, int p_149734_2_, int p_149734_3_, int p_149734_4_, Random p_149734_5_) 
    {
    	for(int i = 0; i < 5; ++i)
    	{
    		Vec3 rotateVec = Vec3.createVectorHelper(1, 1, 1);
    		rotateVec.rotateAroundX(p_149734_5_.nextFloat()*360F);
    		rotateVec.rotateAroundY(p_149734_5_.nextFloat()*360F);
    		rotateVec.rotateAroundZ(p_149734_5_.nextFloat()*360F);
    		for(int i1 = 0; i1 < 10; ++i1)
    			EssentialCraftCore.proxy.spawnParticle("mruFX", p_149734_2_+0.5F, p_149734_3_+1F, p_149734_4_+0.5F, rotateVec.xCoord*10, rotateVec.yCoord*10, rotateVec.zCoord*10);
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
