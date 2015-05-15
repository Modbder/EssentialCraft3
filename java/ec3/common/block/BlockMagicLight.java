package ec3.common.block;

import java.util.Random;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ec3.common.mod.EssentialCraftCore;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.Item;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

public class BlockMagicLight extends Block{

	public BlockMagicLight() {
		super(Material.circuits);
		this.setTickRandomly(true);
		this.setLightLevel(1.0F);
		this.setBlockBounds(0.2F, 0.2F, 0.2F, 0.8F, 0.8F, 0.8F);
		// TODO Auto-generated constructor stub
	}
	
    public int quantityDropped(Random p_149745_1_)
    {
        return 0;
    }

    public Item getItemDropped(int p_149650_1_, Random p_149650_2_, int p_149650_3_)
    {
        return null;
    }
    
    public void updateTick(World p_149674_1_, int p_149674_2_, int p_149674_3_, int p_149674_4_, Random p_149674_5_) 
    {
    	int meta = p_149674_1_.getBlockMetadata(p_149674_2_, p_149674_3_, p_149674_4_);
    	if(meta == 1)
    		p_149674_1_.setBlockToAir(p_149674_2_, p_149674_3_, p_149674_4_);
    }
	
    @SideOnly(Side.CLIENT)
    public void randomDisplayTick(World p_149734_1_, int p_149734_2_, int p_149734_3_, int p_149734_4_, Random p_149734_5_) 
    {
    	int meta = p_149734_1_.getBlockMetadata(p_149734_2_, p_149734_3_, p_149734_4_);
    	if(meta == 0)
	    	for(int i = 0; i < 5; ++i)
	    	{
	    		Vec3 rotateVec = Vec3.createVectorHelper(1, 1, 1);
	    		rotateVec.rotateAroundX(p_149734_5_.nextFloat()*360F);
	    		rotateVec.rotateAroundY(p_149734_5_.nextFloat()*360F);
	    		rotateVec.rotateAroundZ(p_149734_5_.nextFloat()*360F);
	    			EssentialCraftCore.proxy.spawnParticle("mruFX", p_149734_2_+0.5F, p_149734_3_+0.5F, p_149734_4_+0.5F, rotateVec.xCoord/5, rotateVec.yCoord/5, rotateVec.zCoord/5);
	    		rotateVec = null;
	    	}
    		
    }
    
    public boolean isOpaqueCube()
    {
        return false;
    }
    
    public AxisAlignedBB getCollisionBoundingBoxFromPool(World p_149668_1_, int p_149668_2_, int p_149668_3_, int p_149668_4_)
    {
    	return null;
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
