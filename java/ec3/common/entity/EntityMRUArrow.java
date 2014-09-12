package ec3.common.entity;

import DummyCore.Utils.MathUtils;
import ec3.common.mod.EssentialCraftCore;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.world.World;

public class EntityMRUArrow extends EntityArrow{
	
    public EntityMRUArrow(World p_i1753_1_)
    {
        super(p_i1753_1_);
    }


	public EntityMRUArrow(World p_i1756_1_, EntityLivingBase p_i1756_2_,float p_i1756_3_) 
	{
		super(p_i1756_1_, p_i1756_2_, p_i1756_3_);
		this.canBePickedUp = 0;
	}
	
    public void onUpdate()
    {
        super.onUpdate();
    }

}
