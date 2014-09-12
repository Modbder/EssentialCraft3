package ec3.common.item;

import java.util.List;

import DummyCore.Utils.DummyDataUtils;
import DummyCore.Utils.MiscUtils;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ec3.api.EnumSpellType;
import ec3.api.ISpell;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

public class ItemStaff extends Item{
	
	public IIcon[] icon = new IIcon[3];
	
	public String[] names = new String[]{"metalic","void","aincient"};
	
	public ItemStaff()
	{
		super();
		this.setHasSubtypes(true);
		this.setMaxDamage(0);
	}
	
    public String getUnlocalizedName(ItemStack p_77667_1_)
    {
        return super.getUnlocalizedName(p_77667_1_)+"_"+names[p_77667_1_.getItemDamage()];
    }
    

    public void addInformation(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, List par3List, boolean par4) 
    {
    	super.addInformation(par1ItemStack, par2EntityPlayer, par3List, par4);
    	if(!MiscUtils.getStackTag(par1ItemStack).hasNoTags())
    	{
    		if(MiscUtils.getStackTag(par1ItemStack).hasKey("spell"))
    		{
	    		ItemStack stk = ItemStack.loadItemStackFromNBT(MiscUtils.getStackTag(par1ItemStack).getCompoundTag("spell"));
	    		if(stk != null)
	    		{
	    			stk.getItem().addInformation(stk, par2EntityPlayer, par3List, par4);
	    		}
	    		stk = null;
    		}
    	}
    }
    
	@Override
    public ItemStack onItemRightClick(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer)
    {
		if(par3EntityPlayer != null && !MiscUtils.getStackTag(par1ItemStack).hasNoTags() && !par3EntityPlayer.isSneaking())
		{
    		ItemStack stk = ItemStack.loadItemStackFromNBT(MiscUtils.getStackTag(par1ItemStack).getCompoundTag("spell"));
    		if(stk != null)
    		{
				Item item = stk.getItem();
				if(item instanceof ISpell)
				{
					ISpell spell = (ISpell) item;
					String str = DummyDataUtils.getDataForPlayer(par3EntityPlayer.getDisplayName(), "essentialcraft", "ubmruEnergy");
					String attunement = DummyDataUtils.getDataForPlayer(par3EntityPlayer.getDisplayName(), "essentialcraft", "attunement");
					if(str != null && attunement != null)
					{
						int currentUBMRU = Integer.parseInt(str);
						int att = Integer.parseInt(attunement);
						if(!par3EntityPlayer.isSwingInProgress && spell.canUseSpell(stk, par3EntityPlayer))
						{
							spell.onSpellUse(currentUBMRU, att, par3EntityPlayer, stk, par1ItemStack);
							par3EntityPlayer.swingItem();
						}
					}
				}
    		}
		}
		if(par3EntityPlayer != null && MiscUtils.getStackTag(par1ItemStack).hasNoTags() && !par3EntityPlayer.isSneaking())
		{
			ItemStack stk = par3EntityPlayer.inventory.getStackInSlot(par3EntityPlayer.inventory.currentItem+1);
			if(stk != null)
			{
				Item item = stk.getItem();
				if(item instanceof ISpell)
				{
					ISpell spell = (ISpell) item;
					EnumSpellType type = spell.getSpellType(stk);
					if(type == EnumSpellType.CONSUMING || type == EnumSpellType.SORCERY)
					{
						int meta = par1ItemStack.getItemDamage();
						int reqTier = spell.getRequiredContainerTier(stk);
						if(meta >= reqTier)
						{
							NBTTagCompound itemTag = new NBTTagCompound();
							stk.writeToNBT(itemTag);
							NBTTagCompound staffTag = new NBTTagCompound();
							staffTag.setTag("spell", itemTag);
							par1ItemStack.setTagCompound(staffTag);
							par3EntityPlayer.inventory.decrStackSize(par3EntityPlayer.inventory.currentItem+1, 1);
							if(par3EntityPlayer.inventory.getStackInSlot(par3EntityPlayer.inventory.currentItem+1) != null && par3EntityPlayer.inventory.getStackInSlot(par3EntityPlayer.inventory.currentItem+1).stackSize <= 0)
							{
								par3EntityPlayer.inventory.setInventorySlotContents(par3EntityPlayer.inventory.currentItem+1,null); 
							}
							
							if(!par3EntityPlayer.worldObj.isRemote)
							{
								par3EntityPlayer.inventory.markDirty();
								par3EntityPlayer.addChatMessage(new ChatComponentText(EnumChatFormatting.GREEN+"Spell is inserted into the staff!"));
							}
						}else
						{
							if(!par3EntityPlayer.worldObj.isRemote)
								par3EntityPlayer.addChatMessage(new ChatComponentText(EnumChatFormatting.RED+"Your staff is not strong enougth!"));
						}
					}else
					{
						if(!par3EntityPlayer.worldObj.isRemote)
							par3EntityPlayer.addChatMessage(new ChatComponentText(EnumChatFormatting.RED+"This spell cannot be casted by staffs!"));
					}
				}
			}
		}
		if(!MiscUtils.getStackTag(par1ItemStack).hasNoTags() && par3EntityPlayer.isSneaking())
		{
    		ItemStack stk = ItemStack.loadItemStackFromNBT(MiscUtils.getStackTag(par1ItemStack).getCompoundTag("spell"));
    		if(stk != null)
    		{
    			par1ItemStack.setTagCompound(null);
    			if(!par3EntityPlayer.inventory.addItemStackToInventory(stk))
    			{
    				par3EntityPlayer.dropPlayerItemWithRandomChoice(stk, false);
    				par3EntityPlayer.inventory.markDirty();
    			}
    		}
		}
		return par1ItemStack;
    }
	
    @Override
    public void registerIcons(IIconRegister par1IconRegister)
    {
    	super.registerIcons(par1IconRegister);
    	for(int i = 0; i < 3; ++i)
    		this.icon[i] = par1IconRegister.registerIcon("essentialcraft:staff_"+names[i]);
    }
    
    @SideOnly(Side.CLIENT)
    public IIcon getIconFromDamage(int i)
    {
        return this.icon[i];
    }
    
    @SideOnly(Side.CLIENT)
    public void getSubItems(Item p_150895_1_, CreativeTabs p_150895_2_, List p_150895_3_)
    {
        for(int i = 0; i < 3; ++i)
        {
        	p_150895_3_.add(new ItemStack(p_150895_1_,1,i));
        }
    }
}
