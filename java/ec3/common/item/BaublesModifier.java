package ec3.common.item;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ec3.api.ApiCore;
import ec3.api.IMRUStorage;
import ec3.api.IUBMRUGainModifier;
import ec3.api.IWindModifier;
import ec3.api.IWindResistance;
import ec3.utils.cfg.Config;
import ec3.utils.common.RadiationManager;
import DummyCore.Utils.MathUtils;
import DummyCore.Utils.MiscUtils;
import baubles.api.BaubleType;
import baubles.api.IBauble;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.util.StatCollector;

public class BaublesModifier extends Item implements IBauble, IUBMRUGainModifier, IMRUStorage, IWindResistance, IWindModifier{
	
	public static String[] names = new String[]{
			"portableMD",//0
			"ringOfStability",//1
			"ringOfExperience",//2
			"ringOfResistance",//3
			"lifePendant",//4
			"windCatcher",//5
			"windMagesTalisman",//6
			"doctorsBelt",//7
			"alphaRing",//8
			"betaRing",//9
			"gammaRing",//10
			"deltaRing",//11
			"epsilonBelt",//12
			"dzetaAmulet",//13
			"etaAmulet",//14
			"thetaBelt",//15
			"iotaRing",//16
			"kappaRing",//17
			"lambdaRing",//18
			"muRing",//19
			"nuAmulet",//20
			"xiBelt",//21
			"omicronAmulet",//22
			"piBelt",//23
			"rhoRing",//24
			"sigmaRing",//25
			"tauRing",//26
			"upsilonRing",//27
			"phiRing",//28
			"chiRing",//29
			"psiRing",//30
			"omegaRing",//31
	};
	
	public static IIcon[] itemIcons = new IIcon[128];
	
	public BaubleType[] btALST = new BaubleType[]{
			BaubleType.AMULET,
			BaubleType.RING,
			BaubleType.RING,
			BaubleType.RING,
			BaubleType.AMULET,
			BaubleType.AMULET,
			BaubleType.AMULET,
			BaubleType.BELT,
			BaubleType.RING,
			BaubleType.RING,
			BaubleType.RING,
			BaubleType.RING,
			BaubleType.BELT,
			BaubleType.AMULET,
			BaubleType.AMULET,
			BaubleType.BELT,
			BaubleType.RING,
			BaubleType.RING,
			BaubleType.RING,
			BaubleType.RING,
			BaubleType.AMULET,
			BaubleType.BELT,
			BaubleType.AMULET,
			BaubleType.BELT,
			BaubleType.RING,
			BaubleType.RING,
			BaubleType.RING,
			BaubleType.RING,
			BaubleType.RING,
			BaubleType.RING,
			BaubleType.RING,
			BaubleType.RING,
			BaubleType.RING,
	};

	public BaublesModifier()
	{
		this.setMaxDamage(0);
		this.setHasSubtypes(true);
		
		this.setMaxStackSize(1);
	}
	
	@Override
	public BaubleType getBaubleType(ItemStack itemstack) {
		return btALST.length > itemstack.getItemDamage() ? btALST[itemstack.getItemDamage()] : BaubleType.RING;
	}

	@SuppressWarnings("unchecked")
	@Override
	public void onWornTick(ItemStack itemstack, EntityLivingBase player) {
		if(itemstack.getItemDamage() == 0 && player instanceof EntityPlayer)
		{
			int ubmrucu = ApiCore.getPlayerData((EntityPlayer) player).getPlayerUBMRU();
			if(ubmrucu - 1 >= 0 && this.setMRU(itemstack, 10))
			{
				--ubmrucu;
				ApiCore.getPlayerData((EntityPlayer) player).modifyUBMRU(ubmrucu);
			}
		}	
		if(itemstack.getItemDamage() == 17 && player instanceof EntityPlayer)
		{
			EntityPlayer p = (EntityPlayer) player;
			p.addExhaustion(0.01F);
			if(p.ticksExisted % 10 == 0)
				ApiCore.getPlayerData((EntityPlayer) player).modifyUBMRU(ApiCore.getPlayerData((EntityPlayer) player).getPlayerUBMRU()+1);
		}
		if(itemstack.getItemDamage() == 18 && player instanceof EntityPlayer)
		{
			EntityPlayer p = (EntityPlayer) player;
			if(p.worldObj.provider != null && p.worldObj.provider.dimensionId!=Config.dimensionID)
				RadiationManager.increasePlayerRadiation(p, -3);
		}
		if(itemstack.getItemDamage() == 19 && player instanceof EntityPlayer)
		{
			EntityPlayer p = (EntityPlayer) player;
			if(p.worldObj.provider != null && p.worldObj.provider.dimensionId==Config.dimensionID)
				RadiationManager.increasePlayerRadiation(p, 1);
		}
		if(itemstack.getItemDamage() == 7 && player instanceof EntityPlayer)
		{
			EntityPlayer p = (EntityPlayer) player;
			PotionEffect[] peArray = new PotionEffect[p.getActivePotionEffects().size()];
			peArray = (PotionEffect[]) p.getActivePotionEffects().toArray(peArray);
			for(int i = 0; i < peArray.length; ++i)
			{
				PotionEffect effect = p.getActivePotionEffect(Potion.potionTypes[peArray[i].getPotionID()]);
				if(Potion.potionTypes[peArray[i].getPotionID()].isBadEffect())
				{
					try
					{
						Class<PotionEffect> cz = PotionEffect.class;
						Field duration = cz.getDeclaredFields()[1];
						duration.setAccessible(true);
						int d = duration.getInt(effect);
						duration.setInt(effect, --d);
						duration.setAccessible(false);
						continue;
					}
					catch(Exception e)
					{
						continue;
					}
				}
			}
		}
	}

	@Override
	public void onEquipped(ItemStack itemstack, EntityLivingBase player) {
		
	}

	@Override
	public void onUnequipped(ItemStack itemstack, EntityLivingBase player) {
	}

	@Override
	public boolean canEquip(ItemStack itemstack, EntityLivingBase player) {
		return true;
	}

	@Override
	public boolean canUnequip(ItemStack itemstack, EntityLivingBase player) {
		return true;
	}
	
    public void registerIcons(IIconRegister p_94581_1_)
    {
        super.registerIcons(p_94581_1_);
        for(int i = 0; i < names.length; ++i)
        {
        	itemIcons[i] = p_94581_1_.registerIcon("essentialcraft:baubleModifiers/"+names[i]);
        }
    }
    
    @SideOnly(Side.CLIENT)
    public IIcon getIconFromDamage(int i)
    {
        return itemIcons[i];
    }
	
    @SuppressWarnings({ "rawtypes", "unchecked" })
	@SideOnly(Side.CLIENT)
    public void getSubItems(Item p_150895_1_, CreativeTabs p_150895_2_, List p_150895_3_)
    {
        for(int i = 0; i < names.length; ++i)
        {
        	p_150895_3_.add(new ItemStack(p_150895_1_,1,i));
        }
    }
    
    public String getUnlocalizedName(ItemStack p_77667_1_)
    {
        return this.getUnlocalizedName()+"."+names[p_77667_1_.getItemDamage()];
    }

	@SuppressWarnings("unchecked")
	@Override
	public float getModifiedValue(float original, ItemStack mod, Random rng, EntityPlayer p) {
		if(mod.getItemDamage() == 31)
		{
			return original*2;
		}
		if(mod.getItemDamage() == 29)
		{
			return original/2;
		}
		if(mod.getItemDamage() == 20)
		{
			float divide = original/100*25;
			original -= divide;
			
			double x = p.posX;
			double y = p.posY;
			double z = p.posZ;
			
			List<EntityMob> mobs = p.worldObj.getEntitiesWithinAABB(EntityMob.class, AxisAlignedBB.getBoundingBox(x-0.5D, y-0.5D, z-0.5D, x+0.5D, y+0.5D, z+0.5D).expand(6, 3, 6));
			for(int i = 0; i < mobs.size(); ++i)
			{
				EntityMob mob = mobs.get(i);
				mob.attackEntityFrom(DamageSource.causePlayerDamage(p), 3);
			}
			
			return original;
		}
		if(mod.getItemDamage() == 1)
		{
			return original + rng.nextInt(100);
		}
		if(mod.getItemDamage() == 2)
		{
			float divide10 = original/10;
			original -= divide10;
			EntityXPOrb orb = new EntityXPOrb(p.worldObj, p.posX+MathUtils.randomFloat(rng), p.posY+MathUtils.randomFloat(rng), p.posZ+MathUtils.randomFloat(rng), MathHelper.floor_float(divide10/4));
			orb.field_70532_c = 100;
			if(!p.worldObj.isRemote)
				p.worldObj.spawnEntityInWorld(orb);
			
			return original;
		}
		if(mod.getItemDamage() == 4)
		{
			float divide30 = original/100*30;
			original -= divide30;
			
			p.heal(divide30/30);
			
			return original;
		}
		if(mod.getItemDamage() == 7)
		{
			return 0;
		}
		return original;
	}

    @SuppressWarnings({ "rawtypes", "unchecked" })
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean par4)
    {
        super.addInformation(stack, player, list, par4);
        if(stack.getItemDamage() == 0)
        {
        	list.add(this.getMRU(stack) + "/" + this.getMaxMRU(stack) + " MRU");
        }	
       
        list.addAll(buildHelpList(StatCollector.translateToLocal("ec3.txt.help.baubles."+stack.getItemDamage())));
    }
	
	@Override
	public boolean setMRU(ItemStack stack, int amount) {
		if(getMRU(stack)+amount >= 0 && getMRU(stack)+amount<=getMaxMRU(stack))
		{
			MiscUtils.getStackTag(stack).setInteger("mru", MiscUtils.getStackTag(stack).getInteger("mru")+amount);
			return true;
		}
		return false;
	}

	@Override
	public int getMRU(ItemStack stack) {
		return stack.getItemDamage() == 0 ? MiscUtils.getStackTag(stack).getInteger("mru") : 0;
	}

	@Override
	public int getMaxMRU(ItemStack stack) {
		return stack.getItemDamage() == 0 ? 5000 : 0;
	}

	@Override
	public boolean resistWind(EntityPlayer p, ItemStack stk) {
		return stk.getItemDamage() == 3;
	}

	@Override
	public float getModifier(ItemStack stk, EntityPlayer p) {
		if(stk.getItemDamage() == 5)
		{
			if(!p.worldObj.isRemote && p.worldObj.rand.nextFloat() <= 0.15F)
			{
				ItemStack windKeeper = new ItemStack(ItemsCore.windKeeper);
				if(!p.inventory.addItemStackToInventory(windKeeper))
					p.dropPlayerItemWithRandomChoice(windKeeper, true);
				p.addChatMessage(new ChatComponentText(EnumChatFormatting.DARK_RED+""+EnumChatFormatting.ITALIC+"The wind catcher catches some wind..."));
			}
			return -0.1F;
		}	
		if(stk.getItemDamage() == 6)
		{
			return 0.3F;
		}
		return 0;
	}
    
	public List<String> buildHelpList(String s)
	{
		List<String> ret = new ArrayList<String>();
		String addedString = "";
		while(s.indexOf("|") != -1)
		{
			int index = s.indexOf("|");
			String charType = s.substring(index+1, index+2);
			s = s.substring(0,index)+s.substring(index+2);
			if(charType.equals("n"))
			{
				int nextIndex = s.indexOf("|n");
				if(nextIndex != -1)
				{
					addedString = s.substring(0,index);
					ret.add(addedString);
					addedString = "";
					s = s.substring(index);
				}else
				{
					addedString = s.substring(0,index);
					ret.add(addedString);
					addedString = "";
					s = s.substring(index);
					
				}
			}else
				if(charType.equals("r"))
				{
					s = s.substring(0,index)+EnumChatFormatting.RESET+s.substring(index);
				}
				else
				{
					s = s.substring(0,index)+findByChar(charType.charAt(0))+s.substring(index);
				}
		}
		addedString += s;
		ret.add(addedString);
		return ret;
	}
	
	public EnumChatFormatting findByChar(char c)
	{
		for(int i = 0; i < EnumChatFormatting.values().length; ++i)
		{
			EnumChatFormatting ecf = EnumChatFormatting.values()[i];
			if(ecf.getFormattingCode() == c)
			{
				return ecf;
			}
		}
		return EnumChatFormatting.RESET;
	}
}
