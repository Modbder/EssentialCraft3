package ec3.common.item;

import java.lang.reflect.Method;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

public class ItemBlockGeneric extends ItemBlock{
	
	boolean parentHasAddInfo = false;
	boolean hasCheckedParent = false;

	public ItemBlockGeneric(Block p_i45328_1_) {
		super(p_i45328_1_);
		this.setHasSubtypes(true);
		this.setMaxDamage(0);
	}

    public int getMetadata(int par1)
    {
        return par1;
    }
    
    @SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
    public void addInformation(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, List par3List, boolean par4) 
    {
    	if(!hasCheckedParent)
    	{
    		hasCheckedParent = true;
    		try
    		{
    			Class c = this.field_150939_a.getClass();
    			Method addInfo = c.getMethod("addInformation", ItemStack.class,EntityPlayer.class,List.class,boolean.class);
    			parentHasAddInfo = addInfo != null;
    		}catch(Exception e)
    		{
    			parentHasAddInfo = false;
    		}
    	}else
    	{
    		if(parentHasAddInfo)
    		{
    			try
    			{
    				this.field_150939_a.getClass().getMethod("addInformation", ItemStack.class,EntityPlayer.class,List.class,boolean.class).invoke(field_150939_a, par1ItemStack,par2EntityPlayer,par3List,par4);
    			}catch(Exception e)
    			{
    				
    			}
    		}
    	}
    }
    
}
