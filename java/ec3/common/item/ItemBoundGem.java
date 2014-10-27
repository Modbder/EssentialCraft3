package ec3.common.item;

import java.util.List;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
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
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

public class ItemBoundGem extends Item {

	public IIcon icon;
	public IIcon active_icon;
	public ItemBoundGem() {
		super();
		this.maxStackSize = 1;
	}
    
	@Override
    public boolean onItemUseFirst(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ)
    {
    	if(world.getBlock(x, y, z) == BlocksCore.rayTower && world.getBlockMetadata(x, y, z) == 1)
    	{
    		y-=1;
    	}
    	TileEntity t = world.getTileEntity(x, y, z);
    	if(t != null && !world.isRemote)
    	{
    		if(t instanceof ITERequiresMRU || t instanceof ITETransfersMRU || t instanceof ITEStoresMRU && !world.isRemote)
    		{
    			ITEHasMRU t_1 = (ITEHasMRU) t;
    			createTag(stack);
    			MiscUtils.getStackTag(stack).setIntArray("pos", new int[]{x,y,z});
    			MiscUtils.getStackTag(stack).setInteger("dim", player.dimension);
    			world.playSoundAtEntity(player, "random.levelup", 1.0F, 2.0F);
    			return true;
    		}
    	}
        return false;
    }
    
	@Override
    public ItemStack onItemRightClick(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer)
    {
    	if(par1ItemStack.getTagCompound() != null && !par2World.isRemote && par3EntityPlayer.isSneaking())
    	{
    		par1ItemStack.setTagCompound(null);
    		par2World.playSoundAtEntity(par3EntityPlayer, "random.breath", 1.0F, 0.01F);
    	}
        return par1ItemStack;
    }
    
    public void addInformation(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, List par3List, boolean par4) 
    {
    	if(par1ItemStack.getTagCompound() != null)
    	{
    		int[] coord = MiscUtils.getStackTag(par1ItemStack).getIntArray("pos");
    		par3List.add("Currently Bound To TileEntity At:");
    		par3List.add("x: "+coord[0]);
    		par3List.add("y: "+coord[1]);
    		par3List.add("z: "+coord[2]);
    		par3List.add("dimension: "+MiscUtils.getStackTag(par1ItemStack).getInteger("dim"));
    	}
    }
    
    public static int[] getCoords(ItemStack stack)
    {
    	return MiscUtils.getStackTag(stack).getIntArray("pos");
    }
    
    public boolean hasEffect(ItemStack par1ItemStack)
    {
        return par1ItemStack.getTagCompound() != null;
    }
    
    public EnumRarity getRarity(ItemStack par1ItemStack)
    {
        return par1ItemStack.getTagCompound() != null ? EnumRarity.epic : EnumRarity.common;
    }

    
    public boolean createTag(ItemStack stack)
    {
    	if(stack.getTagCompound() == null)
    	{
    		NBTTagCompound tag = new NBTTagCompound();
    		tag.setIntArray("pos", new int[]{0,0,0});
    		return true;
    	}
    	return false;
    }
    
    @Override
    public void registerIcons(IIconRegister par1IconRegister)
    {
        this.icon = par1IconRegister.registerIcon("essentialcraft:gem_bound");
        this.active_icon = par1IconRegister.registerIcon("essentialcraft:gem_bound_active");
    }
    
    @SideOnly(Side.CLIENT)
    public IIcon getIconIndex(ItemStack i)
    {
        if(i.hasTagCompound())
        {
        	return this.active_icon;
        }else
        {
        	return this.icon;
        }
    }
}
