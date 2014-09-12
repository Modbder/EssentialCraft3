package ec3.common.item;

import java.util.List;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ec3.api.EnumSpellType;
import ec3.api.ISpell;
import ec3.network.proxy.ClientProxy;
import ec3.utils.common.ECUtils;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IIcon;

public class ItemSpell extends Item implements ISpell{
	
	public IIcon[] displayIcons;
	public ItemSpell()
	{
		super();
		this.setHasSubtypes(true);
		this.setMaxDamage(0);
	}

	@Override
	public EnumSpellType getSpellType(ItemStack stk) {
		int meta = stk.getItemDamage();
		return ECUtils.spells.get(meta).type;
	}

	@Override
	public int getUBMRURequired(ItemStack stk) {
		int meta = stk.getItemDamage();
		return ECUtils.spells.get(meta).required;
	}

	@Override
	public void onSpellUse(int currentUBMRU, int attunement,EntityPlayer player, ItemStack spell, ItemStack holder) {
		int meta = spell.getItemDamage();
		ECUtils.spells.get(meta).onSpellUse(currentUBMRU, attunement, player, spell, holder);
	}

	@Override
	public int getRequiredContainerTier(ItemStack stk) {
		int meta = stk.getItemDamage();
		return ECUtils.spells.get(meta).reqTier;
	}

	@Override
	public int getAttunementRequired(ItemStack stk) {
		int meta = stk.getItemDamage();
		return ECUtils.spells.get(meta).attuneReq;
	}

	@Override
	public boolean requiresSpecificAttunement(ItemStack stk) {
		int meta = stk.getItemDamage();
		return ECUtils.spells.get(meta).attuneReq != -1;
	}

	@Override
	public boolean canUseSpell(ItemStack spell, EntityPlayer user) {
		int meta = spell.getItemDamage();
		return ECUtils.spells.get(meta).canUseSpell(spell, user);
	}
	
    public String getUnlocalizedName(ItemStack p_77667_1_)
    {
        return this.getUnlocalizedName()+ECUtils.spells.get(p_77667_1_.getItemDamage()).name;
    }
    
    public void registerIcons(IIconRegister p_94581_1_)
    {
    	displayIcons = new IIcon[ECUtils.spells.size()];
        super.registerIcons(p_94581_1_);
        for(int i = 0; i < ECUtils.spells.size(); ++i)
        	displayIcons[i] = p_94581_1_.registerIcon("essentialcraft:"+ECUtils.spells.get(i).icon);
    }
    
    @SideOnly(Side.CLIENT)
    public IIcon getIconFromDamage(int i)
    {
        return this.displayIcons[i];
    }
    
    @SideOnly(Side.CLIENT)
    public void getSubItems(Item p_150895_1_, CreativeTabs p_150895_2_, List p_150895_3_)
    {
        for(int i = 0; i < ECUtils.spells.size(); ++i)
        {
        	p_150895_3_.add(new ItemStack(p_150895_1_,1,i));
        }
    }
    
    public void addInformation(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, List par3List, boolean par4) 
    {
    	par3List.add(ECUtils.spells.get(par1ItemStack.getItemDamage()).description);
    	EnumSpellType etype = ECUtils.spells.get(par1ItemStack.getItemDamage()).type;
    	String type = "";
    	String tool = "";
    	if(etype == EnumSpellType.CONSUMING)
    	{
    		type = EnumChatFormatting.GREEN+"Consuming";
    		tool = EnumChatFormatting.WHITE+"Staff";
    	}
    	if(etype == EnumSpellType.MIRACLE)
    	{
    		type = EnumChatFormatting.YELLOW+"Miracle";
    		tool = EnumChatFormatting.WHITE+"Bell";
    	}
    	if(etype == EnumSpellType.SORCERY)
    	{
    		type = EnumChatFormatting.AQUA+"Sorcery";
    		tool = EnumChatFormatting.WHITE+"Staff";
    	}
    	int att = ECUtils.spells.get(par1ItemStack.getItemDamage()).attuneReq;
    	String at = "";
			switch(att)
			{
				case 0:
				{
					at = EnumChatFormatting.GREEN+"Neutral";
					break;
				}
				case 1:
				{
					at = EnumChatFormatting.RED+"Chaos";
					break;
				}
				case 2:
				{
					at = EnumChatFormatting.BLUE+"Frozen";
					break;
				}
				case 3:
				{
					at = EnumChatFormatting.LIGHT_PURPLE+"Magic";
					break;
				}
				case 4:
				{
					at = EnumChatFormatting.GRAY+"Shade";
					break;
				}
				default:
				{
					at = EnumChatFormatting.GREEN+"Neutral";
					break;
				}
			}
	    int tier = ECUtils.spells.get(par1ItemStack.getItemDamage()).reqTier;
	    String stier = "";
		switch(tier)
		{
			case 0:
			{
				stier = EnumChatFormatting.WHITE+"Metalic";
				break;
			}
			case 1:
			{
				stier = EnumChatFormatting.GREEN+"Void";
				break;
			}
			case 2:
			{
				stier = EnumChatFormatting.BLUE+"Aincient";
				break;
			}
		}
    	par3List.add("Spell type is: "+type);
    	par3List.add("Required Tool: "+tool);
    	par3List.add("Required UBMRU: "+EnumChatFormatting.GREEN+ECUtils.spells.get(par1ItemStack.getItemDamage()).required);
    	par3List.add("Required Attunement: "+at);
    	par3List.add("Required Tool Tier: "+stier);
    }

}
