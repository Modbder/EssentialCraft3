package ec3.common.item;

import java.util.List;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import DummyCore.Utils.DummyDataUtils;
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
import net.minecraft.item.EnumAction;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IChatComponent;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

public class ItemMRUMatrixProjection extends Item {

	public String[] names = new String[]{"empty","chaos","frozen","magic","shade"};
	public IIcon[] icon = new IIcon[5];
	public ItemMRUMatrixProjection() {
		super();
		this.setHasSubtypes(true);
		this.setMaxDamage(0);
	}
	
    public String getUnlocalizedName(ItemStack p_77667_1_)
    {
        return super.getUnlocalizedName(p_77667_1_)+"_"+names[p_77667_1_.getItemDamage()];
    }
    
	@Override
    public ItemStack onItemRightClick(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer)
    {
		if(par1ItemStack.getTagCompound() != null)
		{
			String username = par1ItemStack.getTagCompound().getString("playerName");
			if(username.equals(par3EntityPlayer.getDisplayName()))
				par3EntityPlayer.setItemInUse(par1ItemStack, this.getMaxItemUseDuration(par1ItemStack));
		}
    	if(par1ItemStack.getTagCompound() == null && !par2World.isRemote && !par3EntityPlayer.isSneaking())
    	{
    		NBTTagCompound playerTag = new NBTTagCompound();
    		playerTag.setString("playerName", par3EntityPlayer.getDisplayName());
    		par1ItemStack.setTagCompound(playerTag);
    	}
        return par1ItemStack;
    }
    
    public void addInformation(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, List par3List, boolean par4) 
    {
    	if(par1ItemStack.getTagCompound() != null)
    	{
    		String username = par1ItemStack.getTagCompound().getString("playerName");
			par3List.add(EnumChatFormatting.DARK_GRAY+"Thus is a projection of "+EnumChatFormatting.GOLD+username+EnumChatFormatting.DARK_GRAY+"'s MRU Matrix");
    	}
    }
    
    @Override
    public void registerIcons(IIconRegister par1IconRegister)
    {
    	super.registerIcons(par1IconRegister);
    	for(int i = 0; i < 5; ++i)
    		this.icon[i] = par1IconRegister.registerIcon("essentialcraft:mruMatrix_"+names[i]);
    }
    
    @SideOnly(Side.CLIENT)
    public IIcon getIconFromDamage(int i)
    {
        return this.icon[i];
    }
    
    @SideOnly(Side.CLIENT)
    public void getSubItems(Item p_150895_1_, CreativeTabs p_150895_2_, List p_150895_3_)
    {
        for(int i = 0; i < 5; ++i)
        {
        	p_150895_3_.add(new ItemStack(p_150895_1_,1,i));
        }
    }
    
    public EnumAction getItemUseAction(ItemStack p_77661_1_)
    {
        return EnumAction.block;
    }
    
    public int getMaxItemUseDuration(ItemStack p_77626_1_)
    {
        return 200;
    }
    
    public void onUsingTick(ItemStack stack, EntityPlayer player, int count)
    {
    	if(count % 40 == 0)
    	{
    		player.playSound("portal.travel", 0.3F, 2);
    		player.playSound("portal.trigger", 0.3F, 2);
    	}
    	if(count == 100)
    		player.addPotionEffect(new PotionEffect(Potion.confusion.id,200,0,true));
    	if(count <= 50)
    	{
    		player.addPotionEffect(new PotionEffect(Potion.blindness.id,2000,0,true));
    	}
    	
    }
    
    public ItemStack onEaten(ItemStack p_77654_1_, World p_77654_2_, EntityPlayer p_77654_3_)
    {
    	p_77654_3_.playSound("portal.travel", 0.3F, 0.01F);
    	p_77654_3_.addPotionEffect(new PotionEffect(Potion.wither.id,40*19,0,true));
    	p_77654_3_.addPotionEffect(new PotionEffect(Potion.weakness.id,40*19,4,true));
    	p_77654_3_.addPotionEffect(new PotionEffect(Potion.moveSlowdown.id,40*19,4,true));
    	p_77654_3_.addPotionEffect(new PotionEffect(Potion.digSlowdown.id,40*19,4,true));
    	p_77654_3_.addPotionEffect(new PotionEffect(Potion.hunger.id,40*19,0,true));
    	if(!p_77654_3_.worldObj.isRemote)
    	{
    		p_77654_3_.addChatMessage(new ChatComponentText(EnumChatFormatting.AQUA+"Your MRU Matrix twists with new colors!"));
    		DummyDataUtils.setDataForPlayer(p_77654_3_.getDisplayName(), "essentialcraft", "attunement", Integer.toString(p_77654_1_.getItemDamage()));
    	}
    	p_77654_3_.inventory.decrStackSize(p_77654_3_.inventory.currentItem, 1);
        return p_77654_1_;
    }


}
