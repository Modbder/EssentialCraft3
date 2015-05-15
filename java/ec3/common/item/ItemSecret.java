package ec3.common.item;

import java.util.List;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ec3.common.mod.EssentialCraftCore;
import ec3.common.registry.AchievementRegistry;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.biome.BiomeGenBase.TempCategory;

public class ItemSecret extends Item{
	public static String[] dropNames = new String[]{"410_ticket","d6","ironwood_branch","mysterious_stick","smoothandsilkystone","strange_figure","strange_symbol"};
	public static IIcon[] itemIcons = new IIcon[7];
	
	public ItemSecret()
	{
		this.setMaxDamage(0);
		this.setHasSubtypes(true);
	}
	
    @SuppressWarnings({ "rawtypes", "unchecked" })
	public void addInformation(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, List par3List, boolean par4) 
    {
    	super.addInformation(par1ItemStack, par2EntityPlayer, par3List, par4);
    	int metadata = par1ItemStack.getItemDamage();
    	switch(metadata)
    	{
	    	case 0:
	    	{
	    		for(int i = 0; i < 5; ++i)
	    			par3List.add(StatCollector.translateToLocal("ec3.text.desc.secret_"+metadata+"_"+i));
	    		break;
	    	}
	    	case 1:
	    	{
	    		for(int i = 0; i < 4; ++i)
	    			par3List.add(StatCollector.translateToLocal("ec3.text.desc.secret_"+metadata+"_"+i));
	    		break;
	    	}
	    	case 2:
	    	{
	    		par3List.add("The branch seems to be made of "+ EnumChatFormatting.WHITE+"iron");
	    		par3List.add("However it is clearly a branch of a tree");
	    		par3List.add("You feel better while holding it");
	    		par3List.add("Maybe it can improve your "+EnumChatFormatting.AQUA+"spells?");
	    		break;
	    	}
	    	case 3:
	    	{
	    		par3List.add("This seems to be a regular stick");
	    		par3List.add("But it gives you a strange feel of power");
	    		par3List.add("You only know one thing");
	    		par3List.add("Whoever controls the stick controls the "+EnumChatFormatting.DARK_AQUA+"universe...");
	    		break;
	    	}
	    	case 4:
	    	{
	    		par3List.add("This stone is too smooth");
	    		par3List.add("It makes you feel better");
	    		par3List.add("It is also very silky");
	    		par3List.add("Maybe some kind of "+EnumChatFormatting.DARK_GREEN+"bird"+EnumChatFormatting.GRAY+" would like it?");
	    		break;
	    	}
	    	case 5:
	    	{
	    		par3List.add("This is a very strange figure");
	    		par3List.add("It seems to be from the future");
	    		par3List.add("You can't do anything with it");
	    		par3List.add("But it seems a bit "+EnumChatFormatting.DARK_GRAY+"damaged...");
	    		break;
	    	}
	    	case 6:
	    	{
	    		par3List.add("This is a very strange symbol");
	    		par3List.add("It seems to be an image of something");
	    		par3List.add("When you look at it you want to glory something");
	    		par3List.add("There are letters on the bottom that say "+EnumChatFormatting.LIGHT_PURPLE+"EZIC");
	    		break;
	    	}
    	}
    }
    
	@SuppressWarnings("rawtypes")
	@Override
    public ItemStack onItemRightClick(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer)
    {
    	int metadata = par1ItemStack.getItemDamage();
    	switch(metadata)
    	{
    		case 0:
    		{
				EntityPlayer player = par3EntityPlayer;
				World wrld = par3EntityPlayer.worldObj;
				List playerLst = wrld.getEntitiesWithinAABB(EntityPlayer.class, AxisAlignedBB.getBoundingBox(player.posX-10, player.posY-10, player.posZ-10, player.posX+10, player.posY+10, player.posZ+10));
				BiomeGenBase biome = wrld.getBiomeGenForCoords(MathHelper.floor_double(player.posX), MathHelper.floor_double(player.posZ));
				boolean canWork = (wrld.getWorldTime() % 24000 >= 14000 && wrld.getWorldTime() % 24000 <= 16000) && (player.rotationPitch <= -42 && player.rotationPitch >= -65) && (playerLst.size() == 1) && (!wrld.isRaining() && (biome.getTempCategory() == TempCategory.WARM || biome.getTempCategory() == TempCategory.MEDIUM));
				if(canWork)
				{
					player.inventory.setInventorySlotContents(player.inventory.currentItem, new ItemStack(ItemsCore.record_everlastingSummer,1,0));
					if(wrld.isRemote)
					{
						player.addChatMessage(new ChatComponentText(EnumChatFormatting.WHITE+"You gase into the stars holding the ticket."));
						player.addChatMessage(new ChatComponentText(EnumChatFormatting.WHITE+"Suddenly a gust of wind swoops upon you."));
						player.addChatMessage(new ChatComponentText(EnumChatFormatting.WHITE+"You are immediately beeing attacked by lots of feels."));
						player.addChatMessage(new ChatComponentText(EnumChatFormatting.WHITE+"Strange, warm feels fall upon you."));
						player.addChatMessage(new ChatComponentText(EnumChatFormatting.WHITE+"You feel calm and relaxed."));
						player.addChatMessage(new ChatComponentText(EnumChatFormatting.WHITE+"A feeling falls upon you. You feel like you've just lived a whole another life."));
						player.addChatMessage(new ChatComponentText(EnumChatFormatting.WHITE+"You try to remember what happened, but memory lets you down."));
						player.addChatMessage(new ChatComponentText(EnumChatFormatting.WHITE+"You suddenly realise, that you no longer keep the ticket in your hand."));
						player.addChatMessage(new ChatComponentText(EnumChatFormatting.WHITE+"Instead a music disk is in your hand."));
						player.addChatMessage(new ChatComponentText(EnumChatFormatting.WHITE+"When you gase to the disk, you begin to hear the song, written on it."));
						player.addChatMessage(new ChatComponentText(EnumChatFormatting.WHITE+"You start feeling really sad, like you've missed something very important to you."));
						player.addChatMessage(new ChatComponentText(EnumChatFormatting.WHITE+"You feel lonely, like missing another half of you."));
						player.addChatMessage(new ChatComponentText(EnumChatFormatting.WHITE+"After some time you calm down."));
					}
					player.triggerAchievement(AchievementRegistry.achievementList.get("theEndlessSummer"));
				}
    		}
    	}
		return par1ItemStack;
    }
	
    public String getUnlocalizedName(ItemStack p_77667_1_)
    {
        return this.getUnlocalizedName()+dropNames[p_77667_1_.getItemDamage()];
    }
    
    public void registerIcons(IIconRegister p_94581_1_)
    {
        super.registerIcons(p_94581_1_);
        for(int i = 0; i < 7; ++i)
        	itemIcons[i] = p_94581_1_.registerIcon("essentialcraft:secrets/"+dropNames[i]);
    }
    
    @SideOnly(Side.CLIENT)
    public IIcon getIconFromDamage(int i)
    {
        return itemIcons[i];
    }
    
    @SuppressWarnings({ "unchecked", "rawtypes" })
	@SideOnly(Side.CLIENT)
    public void getSubItems(Item p_150895_1_, CreativeTabs p_150895_2_, List p_150895_3_)
    {
        for(int i = 0; i < 7; ++i)
        {
        	p_150895_3_.add(new ItemStack(p_150895_1_,1,i));
        }
    }
    
    @SideOnly(Side.CLIENT)
    public boolean hasEffect(ItemStack par1ItemStack, int pass)
    {
    	int metadata = par1ItemStack.getItemDamage();
    	switch(metadata)
    	{
	    	case 0:
	    	{
	    		return EssentialCraftCore.proxy.itemHasEffect(par1ItemStack);
	    	}
    	}
    	return false;
    }
    
    public EnumRarity getRarity(ItemStack p_77613_1_)
    {
        return EssentialCraftCore.proxy.itemHasEffect(p_77613_1_) ? EnumRarity.rare : EnumRarity.common;
    }

}
