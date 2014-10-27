package ec3.common.block;

import net.minecraft.block.BlockCactus;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;

public class BlockDreadCacti extends BlockCactus{

    public BlockDreadCacti()
    {
    	super();
    	this.setStepSound(soundTypeGrass);
    }
    
    public void onEntityCollidedWithBlock(World p_149670_1_, int p_149670_2_, int p_149670_3_, int p_149670_4_, Entity p_149670_5_)
    {
        super.onEntityCollidedWithBlock(p_149670_1_, p_149670_2_, p_149670_3_, p_149670_4_, p_149670_5_);
        if(p_149670_5_ instanceof EntityLivingBase)
        {
        	EntityLivingBase base = (EntityLivingBase) p_149670_5_;
        	base.addPotionEffect(new PotionEffect(Potion.poison.id,100,0));
        }
    }
}
