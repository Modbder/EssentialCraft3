package ec3.common.item;

import java.util.List;

import DummyCore.Utils.DataStorage;
import DummyCore.Utils.DummyData;
import DummyCore.Utils.MiscUtils;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

public class ItemPlayerList extends Item {

	public ItemPlayerList() {
		super();
		this.maxStackSize = 1;
	}
    
    
	@Override
    public ItemStack onItemRightClick(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer)
    {
		NBTTagCompound itemTag = MiscUtils.getStackTag(par1ItemStack);
		if(!itemTag.hasKey("usernames"))
			itemTag.setString("usernames", "||username:null");
		String str = itemTag.getString("usernames");
		DummyData[] dt = DataStorage.parseData(str);
		boolean canAddUsername = true;
		for(int i = 0; i < dt.length; ++i)
		{
			if(dt[i].fieldValue.equals(par3EntityPlayer.getCommandSenderName()))
				canAddUsername = false;
		}
		if(canAddUsername)
		{
			str+="||username:"+par3EntityPlayer.getCommandSenderName();
		}
		itemTag.setString("usernames", str);
        return par1ItemStack;
    }
    
    @SuppressWarnings({ "unchecked", "rawtypes" })
	public void addInformation(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, List par3List, boolean par4) 
    {
    	if(par1ItemStack.getTagCompound() != null)
    	{
    		par3List.add("Allowed Players:");
    		NBTTagCompound itemTag = MiscUtils.getStackTag(par1ItemStack);
    		if(!itemTag.hasKey("usernames"))
    			itemTag.setString("usernames", "||username:null");
    		String str = itemTag.getString("usernames");
    		DummyData[] dt = DataStorage.parseData(str);
    		for(int i = 0; i < dt.length; ++i)
    		{
    			String name = dt[i].fieldValue;
    			if(!name.equals("null"))
    			{
    				par3List.add(" -"+name);
    			}
    		}
    	}
    }
}
