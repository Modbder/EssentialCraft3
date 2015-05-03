package ec3.common.potion;

import DummyCore.Utils.MiscUtils;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ec3.common.registry.PotionRegistry;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;

public class PotionMindfoldParadox extends Potion{

	public PotionMindfoldParadox(int p_i1573_1_, boolean p_i1573_2_,int p_i1573_3_) 
	{
		super(p_i1573_1_, p_i1573_2_, p_i1573_3_);
		this.setIconIndex(7, 2);
		this.setPotionName("potion.paradox");
	}
	
    public boolean isUsable()
    {
        return true;
    }
	
    public void performEffect(EntityLivingBase p_76394_1_, int p_76394_2_)
    {
    	int duration = p_76394_1_.getActivePotionEffect(PotionRegistry.paradox).getDuration();
    	if(duration == 2000)
    	{
    		if(p_76394_1_.worldObj.isRemote)
    			p_76394_1_.worldObj.playSound(p_76394_1_.posX,p_76394_1_.posY,p_76394_1_.posZ, "essentialcraft:sound.tinnitus", 100, 1, true);
    		MiscUtils.setShaders(2);
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
        return 7 + 2 * 8;
    }
    
    public boolean shouldRenderInvText(PotionEffect effect)
    {
        return false;
    }
    
    /**
     * Called to draw the this Potion onto the player's inventory when it's active.
     * This can be used to e.g. render Potion icons from your own texture.
     * @param x the x coordinate
     * @param y the y coordinate
     * @param effect the active PotionEffect
     * @param mc the Minecraft instance, for convenience
     */
    @SideOnly(Side.CLIENT)
    public void renderInventoryEffect(int x, int y, PotionEffect effect, net.minecraft.client.Minecraft mc)
    { 
    	String s1 = I18n.format(effect.getEffectName(), new Object[0]);
    	mc.fontRenderer.drawStringWithShadow(s1, x + 10 + 18, y + 6, 16777215);
    	mc.fontRenderer.drawStringWithShadow(StatCollector.translateToLocal("potion.paradox.txt"), x + 10 + 18, y + 16, 16777215);
    }
    
    static final ResourceLocation rl = new ResourceLocation("essentialcraft", "textures/special/potions.png");

}
