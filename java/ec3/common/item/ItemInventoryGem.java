package ec3.common.item;

import java.util.List;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import DummyCore.Utils.Coord3D;
import DummyCore.Utils.MiscUtils;
import ec3.common.block.BlocksCore;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

public class ItemInventoryGem extends Item{
	
	public static Coord3D currentlyClicked;
	public static int clickTicks;
	
	public IIcon icon;
	public IIcon active_icon;
	
	@Override
    public boolean onItemUseFirst(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ)
    {
		if(stack.getTagCompound() != null && MiscUtils.getStackTag(stack).hasKey("pos"))
			return false;
		
    	if(world.getBlock(x, y, z) == BlocksCore.rayTower && world.getBlockMetadata(x, y, z) == 1)
    	{
    		y-=1;
    	}
    	TileEntity t = world.getTileEntity(x, y, z);
    	if(t != null && !world.isRemote)
    	{
    		if(t instanceof IInventory && !world.isRemote)
    		{
    			ItemStack is = createTag(stack);
    			MiscUtils.getStackTag(is).setIntArray("pos", new int[]{x,y,z});
    			MiscUtils.getStackTag(is).setInteger("dim", player.dimension);
    			world.playSoundAtEntity(player, "random.levelup", 1.0F, 2.0F);
    			if(stack.stackSize <= 0)
    				player.inventory.setInventorySlotContents(player.inventory.currentItem, null);
    			
    			if(!player.inventory.addItemStackToInventory(is))
    				player.dropPlayerItemWithRandomChoice(is, false);
    			
    			if(player.openContainer != null)
    				player.openContainer.detectAndSendChanges();
    			
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
    	if(par1ItemStack.getTagCompound() != null && par2World.isRemote && !par3EntityPlayer.isSneaking())
    	{
    		int[] c = MiscUtils.getStackTag(par1ItemStack).getIntArray("pos");
    		currentlyClicked = new Coord3D(c[0],c[1],c[2]);
    		clickTicks = 100;
    	}
        return par1ItemStack;
    }
	
	 @SuppressWarnings({ "unchecked", "rawtypes" })
	 public void addInformation(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, List par3List, boolean par4) 
	 {
	     if(par1ItemStack.getTagCompound() != null)
	     {
	    	 int[] coord = MiscUtils.getStackTag(par1ItemStack).getIntArray("pos");
	    	 par3List.add("Currently Bound To Inventory At:");
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
    
    public EnumRarity getRarity(ItemStack par1ItemStack)
    {
        return par1ItemStack.getTagCompound() != null ? EnumRarity.epic : EnumRarity.common;
    }	 
    
    public ItemStack createTag(ItemStack stack)
    {
    	ItemStack retStk = stack.copy();
    	retStk.stackSize = 1;
    	stack.stackSize -= 1;
    	
    	if(retStk.getTagCompound() == null)
    	{
    		NBTTagCompound tag = new NBTTagCompound();
    		tag.setIntArray("pos", new int[]{0,0,0});
    		return retStk;
    	}
    	return retStk;
    }    

    @Override
    public void registerIcons(IIconRegister par1IconRegister)
    {
        this.icon = par1IconRegister.registerIcon("essentialcraft:gem_bound");
        this.active_icon = par1IconRegister.registerIcon("essentialcraft:gem_bound_active");
        this.itemIcon = par1IconRegister.registerIcon("essentialcraft:gem_bound");
    }
    
    @Override
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(ItemStack i, int pass)
    {
        if(i.hasTagCompound())
        {
        	return this.active_icon;
        }else
        {
        	return this.icon;
        }
    }
    
    @Override
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
    
    @SideOnly(Side.CLIENT)
    public int getColorFromItemStack(ItemStack stk, int pass)
    {
        return 0xff66ff;
    }   
}
