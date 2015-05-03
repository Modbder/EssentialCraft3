package ec3.common.block;

import java.util.Hashtable;
import java.util.Random;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ec3.utils.common.ECUtils;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

public class BlockPortal extends net.minecraft.block.BlockPortal{

	
		
	public BlockPortal()
	{
		super();
	}
	
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(int p_149691_1_, int p_149691_2_)
    {
        return this.blockIcon;
    }
	
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister p_149651_1_)
    {
        this.blockIcon = p_149651_1_.registerIcon(this.getTextureName());
    }
	
    public void updateTick(World p_149674_1_, int p_149674_2_, int p_149674_3_, int p_149674_4_, Random p_149674_5_)
    {

    }
    
    public void onEntityCollidedWithBlock(World p_149670_1_, int p_149670_2_, int p_149670_3_, int p_149670_4_, Entity p_149670_5_)
    {
    	if(!p_149670_1_.isRemote)
	    {
	    	if(ECUtils.inPortalTime.containsKey(p_149670_5_))
	    	{
	    		int i = ECUtils.inPortalTime.get(p_149670_5_);
	    		if(i >= 200 && !p_149670_1_.isRemote)
	    		{
	    			i = 0;
	    			p_149670_5_.travelToDimension(53);
	    		}
	    		ECUtils.inPortalTime.put(p_149670_5_, i+=1);
	    	}else
	    	{
	    		ECUtils.inPortalTime.put(p_149670_5_, 2);
	    	}
    	}
    }
    
    @SideOnly(Side.CLIENT)
    public void randomDisplayTick(World p_149734_1_, int p_149734_2_, int p_149734_3_, int p_149734_4_, Random p_149734_5_)
    {
    	
    }
}
