package ec3.common.potion;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.potion.Potion;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;

public class PotionRadiation extends Potion{

	public PotionRadiation(int p_i1573_1_, boolean p_i1573_2_,int p_i1573_3_) 
	{
		super(p_i1573_1_, p_i1573_2_, p_i1573_3_);
		this.setIconIndex(4, 2);
		this.setEffectiveness(0.25D);
		this.setPotionName("potion.radiation");
	}
	
    public boolean isUsable()
    {
        return true;
    }
	
    public void performEffect(EntityLivingBase p_76394_1_, int p_76394_2_)
    {
    	if(!p_76394_1_.worldObj.isRemote && p_76394_1_.worldObj.rand.nextInt(16) < p_76394_2_)
    	{
    		int amplifier = p_76394_2_;
    		int maxHealth = (int) p_76394_1_.getMaxHealth()-(amplifier+1);
    		float currentHealth = p_76394_1_.getHealth();
    		if(maxHealth < 1)
    			maxHealth = 1;
    		if(currentHealth > maxHealth)
    		{
    			p_76394_1_.setHealth(maxHealth);
    		}

    	}
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
