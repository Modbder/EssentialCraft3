package ec3.common.item;

import java.util.List;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import DummyCore.Utils.DataStorage;
import DummyCore.Utils.DummyData;
import DummyCore.Utils.MiscUtils;
import ec3.api.ITEHasMRU;
import ec3.api.ITERequiresMRU;
import ec3.api.ITEStoresMRU;
import ec3.api.ITETransfersMRU;
import ec3.common.block.BlocksCore;
import ec3.utils.common.ECUtils;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.IIcon;
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
