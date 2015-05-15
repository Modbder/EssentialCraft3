package ec3.common.item;

import java.lang.reflect.Method;
import java.util.List;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import DummyCore.Utils.MiscUtils;
import ec3.common.mod.EssentialCraftCore;
import ec3.network.PacketNBT;
import ec3.utils.common.ECUtils;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

public class ItemSoulStone extends Item {

	int clientTimer = 0;
	public IIcon icon;
	public IIcon active_icon;
	public ItemSoulStone() {
		super();
		this.maxStackSize = 1;
	}

    
	@Override
    public ItemStack onItemRightClick(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer)
    {
    	if(!par2World.isRemote && !par3EntityPlayer.isSneaking())
    	{
    		NBTTagCompound playerTag = MiscUtils.getStackTag(par1ItemStack);
    		playerTag.setString("playerName", par3EntityPlayer.getDisplayName());
    		par1ItemStack.setTagCompound(playerTag);
    	}
    	if(par1ItemStack.getTagCompound() != null && !par2World.isRemote && par3EntityPlayer.isSneaking())
    	{
    		MiscUtils.getStackTag(par1ItemStack).removeTag("playerName");
    	}
        return par1ItemStack;
    }
    
    @SuppressWarnings({ "rawtypes", "unchecked" })
	public void addInformation(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, List par3List, boolean par4) 
    {
    	if(par1ItemStack.getTagCompound() != null)
    	{
    		String username = par1ItemStack.getTagCompound().getString("playerName");
    		EntityPlayer player = par2EntityPlayer;
    		if(player != null)
    		{
	    		if(ECUtils.playerDataExists(username))
	    		{
	    			int currentEnergy = ECUtils.getData(player).getPlayerUBMRU();
	    			int att = ECUtils.getData(player).getMatrixTypeID();
					par3List.add(EnumChatFormatting.DARK_GRAY+"Tracking MRU Matrix of "+EnumChatFormatting.GOLD+username);
					par3List.add(EnumChatFormatting.DARK_GRAY+"Detected "+EnumChatFormatting.GREEN+currentEnergy+EnumChatFormatting.DARK_GRAY+" UBMRU Energy");
					
					String at = "Neutral";
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
							at = EnumChatFormatting.GREEN+"Unknown";
							break;
						}
					}
					par3List.add(EnumChatFormatting.DARK_GRAY+"MRU Matrix twists with "+at+EnumChatFormatting.DARK_GRAY+" energies.");
					}
				}else
				{
					par3List.add(EnumChatFormatting.DARK_GRAY+"The MRU Matrix of the owner is too pale to track...");
					if(clientTimer == 0)
					{
						NBTTagCompound sTag = new NBTTagCompound();
						sTag.setString("syncplayer", username);
						sTag.setString("sender", par2EntityPlayer.getCommandSenderName());
						EssentialCraftCore.network.sendToServer(new PacketNBT(sTag).setID(1));
						clientTimer = 100;
						
					}else
					{
						--clientTimer;
					}
				}
			this.addBloodMagicDescription(par1ItemStack, par2EntityPlayer, par3List, par4);
    	}
    }
    
    @SuppressWarnings({ "rawtypes", "unchecked" })
	public void addBloodMagicDescription(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, List par3List, boolean par4)
    {
    	if(par1ItemStack.getTagCompound() != null && MiscUtils.getStackTag(par1ItemStack).getBoolean("bloodInfused"))
    	{
    		String username = par1ItemStack.getTagCompound().getString("playerName");
			
			try
			{
				Class energyItems = Class.forName("WayofTime.alchemicalWizardry.common.items.EnergyItems");
				Method getCurrentEssence = energyItems.getMethod("getCurrentEssence", String.class);
				int currentEssence = Integer.parseInt(getCurrentEssence.invoke(null, username).toString());
				par3List.add(EnumChatFormatting.DARK_GRAY+"Detected "+EnumChatFormatting.DARK_RED+currentEssence+EnumChatFormatting.DARK_GRAY+" Life Essence.");
			}catch(Exception e)
			{
				par3List.add(EnumChatFormatting.DARK_GRAY+"The owner's life network is pure and untouched...");
			}
    	}else if(par1ItemStack.getTagCompound() != null)
    	{
    		par3List.add(EnumChatFormatting.DARK_GRAY+"The owner's life network is pure and untouched...");
    	}
    }
    
    @SideOnly(Side.CLIENT)
    public int getColorFromItemStack(ItemStack p_82790_1_, int p_82790_2_)
    {
    	if(p_82790_1_.getTagCompound() != null && MiscUtils.getStackTag(p_82790_1_).getBoolean("bloodInfused"))
    	{
    		return 0xff0000;
    	}
        return 16777215;
        
    }
    
    @Override
    public void registerIcons(IIconRegister par1IconRegister)
    {
    	super.registerIcons(par1IconRegister);
        this.icon = par1IconRegister.registerIcon("essentialcraft:matrix/soulStone");
    }
    
    @SideOnly(Side.CLIENT)
    public IIcon getIconIndex(ItemStack i)
    {
        return this.icon;
    }
}
