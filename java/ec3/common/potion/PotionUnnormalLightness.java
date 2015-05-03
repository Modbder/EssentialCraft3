package ec3.common.potion;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityClientPlayerMP;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;

public class PotionUnnormalLightness extends Potion{

	public PotionUnnormalLightness(int p_i1573_1_, boolean p_i1573_2_,int p_i1573_3_) 
	{
		super(p_i1573_1_, p_i1573_2_, p_i1573_3_);
		this.setIconIndex(3, 2);
		this.setEffectiveness(0.25D);
		this.setPotionName("potion.paranormalLightness");
		this.func_111184_a(SharedMonsterAttributes.movementSpeed, "91AEAA56-376B-4498-935B-2F7F68070636", 0.4D, 2);
	}
	
    public boolean isUsable()
    {
        return true;
    }
	
    public void performEffect(EntityLivingBase p_76394_1_, int p_76394_2_)
    {
    	int duration = p_76394_1_.getActivePotionEffect(this).getDuration();
    	p_76394_1_.addPotionEffect(new PotionEffect(Potion.jump.id,duration,p_76394_2_));
    }
    
    public boolean isReady(int p_76397_1_, int p_76397_2_)
    {
    	return true;
    }
    
    @SideOnly(Side.CLIENT)
    public boolean hasStatusIcon()
    {
        return true;
    }
    
    @SideOnly(Side.CLIENT)
    public int getStatusIconIndex()
    {
    	Minecraft.getMinecraft().renderEngine.bindTexture(rl);
        return super.getStatusIconIndex();
    }
    
    
    private int statusIconIndex;
    static final ResourceLocation rl = new ResourceLocation("essentialcraft", "textures/special/potions.png");

}
