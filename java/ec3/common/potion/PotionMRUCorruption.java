package ec3.common.potion;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ec3.utils.common.ECUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.Potion;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;

public class PotionMRUCorruption extends Potion{

	public PotionMRUCorruption(int p_i1573_1_, boolean p_i1573_2_,int p_i1573_3_) 
	{
		super(p_i1573_1_, p_i1573_2_, p_i1573_3_);
		this.setIconIndex(3, 1);
		this.setEffectiveness(0.25D);
		this.setPotionName("potion.mruCorruption");
	}
	
    public void performEffect(EntityLivingBase p_76394_1_, int p_76394_2_)
    {
    	if(!p_76394_1_.worldObj.isRemote && p_76394_1_.worldObj.rand.nextInt(16) < p_76394_2_)
    	{
    		float damIndex = 5;
    		if(p_76394_1_ instanceof EntityPlayer)
    		{
    			damIndex *= ECUtils.getGenResistance(0, (EntityPlayer) p_76394_1_);
    		}
    		
    		p_76394_1_.attackEntityFrom(DamageSource.magic, damIndex);
    	}
    }
    
    public boolean isReady(int p_76397_1_, int p_76397_2_)
    {
    	return p_76397_1_ % 20 == 0;
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
