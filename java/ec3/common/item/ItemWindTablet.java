package ec3.common.item;

import java.util.List;

import DummyCore.Utils.MathUtils;
import ec3.common.registry.PotionRegistry;
import ec3.utils.common.ECUtils;
import ec3.utils.common.WindRelations;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.EnumAction;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemDye;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.server.MinecraftServer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;

public class ItemWindTablet extends ItemStoresMRUInNBT {
	//24
	public static String[] windMessages = new String[]{
		"A wind blow rotates around you...",//0
		"The wind howls...",//1
		"The wind ruffles your hair...",//2
		"The wind blows around your fingers...",//3
		"The wind says something...",//4
		"The wind makes you sneeze!",//5
		"You hear something similar to laugh...",//6
		"The wind rotates around your legs...",//7
		"The wind tickles you...",//8
		"The wind stops all other sounds...",//9
		"§lThe wind whispers 'Owethanna'...",//10
		"The wind whispers your name...",//11
		"The wind brings a very nostalgic smell...",//12
		"The wind creates a miniature tornado...",//13
		"The wind thows some leaves around...",//14
		"§lThe wind says 'Owethanna Else '...",//15
		"The wind pushes you upwards...",//16
		"The wind rotates very fast around you...",//17
		"The wind goes into your lungs...",//18
		"You feel very powerfull!",//19
		"You fly up using the wind!",//20
		"You start seeing other worlds!",//21
		"You and the wind laugh...",//22
		"The wind and you shout:"//23
	};

	public ItemWindTablet() {
		super();	
		this.setMaxMRU(5000);
		this.maxStackSize = 1;
		this.bFull3D = true;
	}

    /**
     * Called whenever this item is equipped and the right mouse button is pressed. Args: itemStack, world, entityPlayer
     */
    public ItemStack onItemRightClick(ItemStack p_77659_1_, World p_77659_2_, EntityPlayer p_77659_3_)
    {
        if((ECUtils.tryToDecreaseMRUInStorage(p_77659_3_, -500) || this.setMRU(p_77659_1_, -500)))
        {
        	boolean hasEffect = p_77659_3_.getActivePotionEffect(PotionRegistry.windTouch)!=null;
        	if(!hasEffect)
        	{
        		p_77659_3_.addPotionEffect(new PotionEffect(PotionRegistry.windTouch.id,20*60,0));
        		if(!p_77659_3_.worldObj.isRemote)
        		{
        			p_77659_3_.addChatMessage(new ChatComponentText(EnumChatFormatting.DARK_AQUA+""+EnumChatFormatting.ITALIC+windMessages[0]));
        		}
        	}else
        	{
        		boolean readd = true;
        		int currentWindRev = WindRelations.getPlayerWindRelations(p_77659_3_);
        		int maxWindRev = 3500;
        		String windName = "Owethanna Else Hugaida";
        		int revPos = MathUtils.pixelatedTextureSize(currentWindRev, maxWindRev, windName.length());
        		int amplifier = p_77659_3_.getActivePotionEffect(PotionRegistry.windTouch).getAmplifier();
        		if(amplifier < revPos)
        			amplifier+=1;
        		if(!p_77659_3_.worldObj.isRemote)
        		{
        			if(amplifier+1 < 23)
        				p_77659_3_.addChatMessage(new ChatComponentText(EnumChatFormatting.DARK_AQUA+""+EnumChatFormatting.ITALIC+windMessages[amplifier+1]));
        			if(amplifier+1 == 23 && p_77659_3_.isInsideOfMaterial(Material.portal))
        			{
        				readd = false;
        				p_77659_3_.addChatMessage(new ChatComponentText(EnumChatFormatting.DARK_AQUA+""+EnumChatFormatting.ITALIC+windMessages[amplifier+1]));
        				p_77659_3_.addChatMessage(new ChatComponentText(EnumChatFormatting.DARK_AQUA+""+EnumChatFormatting.ITALIC+"§lOwethanna Else Hugaida!"));
        				p_77659_3_.addChatMessage(new ChatComponentText(EnumChatFormatting.DARK_AQUA+""+EnumChatFormatting.ITALIC+"§lOwethanna Else Hugaida!!"));
        				p_77659_3_.addChatMessage(new ChatComponentText(EnumChatFormatting.DARK_AQUA+""+EnumChatFormatting.ITALIC+"§lOwethanna Else Hugaida!!!!"));
        				MinecraftServer server = MinecraftServer.getServer();
        				if(p_77659_3_.capabilities.isCreativeMode)
        					p_77659_3_.capabilities.isCreativeMode = false;
        	            server.getConfigurationManager().transferPlayerToDimension((EntityPlayerMP) p_77659_3_, 53);
        	            p_77659_3_.worldObj.playSoundAtEntity(p_77659_3_, "portal.trigger", 10, 0.01F);
        			}
        		}
        		{
	        		p_77659_3_.removePotionEffect(PotionRegistry.windTouch.id);
	        		if(readd)
	        		p_77659_3_.addPotionEffect(new PotionEffect(PotionRegistry.windTouch.id,20*60,amplifier));
        		}
        	}
        }
        return p_77659_1_;
    }
    
	@Override
    public void addInformation(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, List par3List, boolean par4) 
    {
		super.addInformation(par1ItemStack, par2EntityPlayer, par3List, par4);
		int currentWindRev = WindRelations.getPlayerWindRelations(par2EntityPlayer);
		int maxWindRev = 3500;
		String windName = "Owethanna Else Hugaida";
		String hidden = "??????????????????????";
		int revPos = MathUtils.pixelatedTextureSize(currentWindRev, maxWindRev, windName.length());
		par3List.add("Wind name:");
		par3List.add(windName.substring(0, revPos)+hidden.substring(revPos));
    }

}
