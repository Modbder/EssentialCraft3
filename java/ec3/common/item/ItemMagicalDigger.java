package ec3.common.item;

import java.util.List;

import ec3.api.IItemRequiresMRU;
import ec3.utils.common.ECUtils;
import DummyCore.Utils.Coord3D;
import DummyCore.Utils.MiscUtils;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemPickaxe;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.stats.StatList;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeHooks;

public class ItemMagicalDigger extends ItemPickaxe implements IItemRequiresMRU {
	
	public ItemMagicalDigger() {
		super(ItemsCore.elemental);
		this.setMaxMRU(5000);
		this.maxStackSize = 1;
        this.bFull3D = false;
        this.setMaxDamage(0);
	}
	
	int maxMRU = 5000;
	
	public Item setMaxMRU(int max)
	{
		maxMRU = max;
		return this;
	}
	
	@Override
	public boolean setMRU(ItemStack stack, int amount) {
		if(MiscUtils.getStackTag(stack).getInteger("mru")+amount >= 0 && MiscUtils.getStackTag(stack).getInteger("mru")+amount<=MiscUtils.getStackTag(stack).getInteger("maxMRU"))
		{
			MiscUtils.getStackTag(stack).setInteger("mru", MiscUtils.getStackTag(stack).getInteger("mru")+amount);
			return true;
		}
		return false;
	}
	
	@Override
	public int getMRU(ItemStack stack) {
		// TODO Auto-generated method stub
		return MiscUtils.getStackTag(stack).getInteger("mru");
	}
	
    public boolean isItemTool(ItemStack p_77616_1_)
    {
    	return true;
    }
    
    public void addInformation(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, List par3List, boolean par4) 
    {
    	super.addInformation(par1ItemStack, par2EntityPlayer, par3List, par4);
    	par3List.add(ECUtils.getStackTag(par1ItemStack).getInteger("mru") + "/" + ECUtils.getStackTag(par1ItemStack).getInteger("maxMRU") + " MRU");
    }
	
    @Override
    public void getSubItems(Item par1, CreativeTabs par2CreativeTabs, List par3List)
    {
        for (int var4 = 0; var4 < 1; ++var4)
        {
        	ItemStack min = new ItemStack(par1, 1, 0);
        	ECUtils.initMRUTag(min, maxMRU);
        	ItemStack max = new ItemStack(par1, 1, 0);
        	ECUtils.initMRUTag(max, maxMRU);
        	ECUtils.getStackTag(max).setInteger("mru", ECUtils.getStackTag(max).getInteger("maxMRU"));
            par3List.add(min);
            par3List.add(max);
        }
    }
    
	@Override
	public int getMaxMRU(ItemStack stack) {
		// TODO Auto-generated method stub
		return this.maxMRU;
	}
	
	@Override
    public ItemStack onItemRightClick(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer)
    {
    	par3EntityPlayer.swingItem();
    	par3EntityPlayer.swingProgress = 0.3F;
        return par1ItemStack;
    }
	
	@Override
    public boolean isFull3D()
    {
        return true;
    }
	
    @Override
    public float getDigSpeed(ItemStack stack, Block block, int meta) 
    {
        return getStrVsBlock(stack, block);
    }
    
    public float getStrVsBlock(ItemStack par1ItemStack, Block par2Block)
    {
    	if(this.getMRU(par1ItemStack) > 9)
    	{
    		return 32.0F;
    	}
        return 1.0F;
    }
    
    public boolean canBreak(ItemStack s)
    {
    	return this.getMRU(s)>9;
    }
    
    @Override
    public boolean onBlockDestroyed(ItemStack par1ItemStack, World par2World, Block par3, int par4, int par5, int par6, EntityLivingBase par7EntityLivingBase)
    {
    	if(par7EntityLivingBase instanceof EntityPlayer && !par7EntityLivingBase.isSneaking() && this.canBreak(par1ItemStack))
    	{
    		this.break3x3x3Blocks((EntityPlayer) par7EntityLivingBase, new Coord3D(par4,par5,par6),par1ItemStack,par2World.getBlock(par4, par5, par6));
    	}
    	return true;
    }
   
    public void break3x3x3Blocks(EntityPlayer e, Coord3D c, ItemStack s, Block id)
    {
    	for(int x = -1; x <= 1; ++x)
    	{
        	for(int y = -1; y <= 1; ++y)
        	{
            	for(int z = -1; z <= 1; ++z)
            	{
            		Coord3D c00rd = new Coord3D(c.x+x,c.y+y,c.z+z);
            		for(int v = 0; v < 10; ++v)
        			e.worldObj.spawnParticle("reddust", c.x+x+e.worldObj.rand.nextFloat(),c.y+y+e.worldObj.rand.nextFloat(),c.z+z+e.worldObj.rand.nextFloat(), 1.0D, 0.0D, 1.0D);
        			e.worldObj.playSoundAtEntity(e, "random.fizz", 0.2F, 6.0F);
            		Block b = e.worldObj.getBlock((int)c.x+x,(int)c.y+y,(int)c.z+z);
            		if(b != null && b == id)
            		{
            			if(!e.worldObj.isRemote &&(ECUtils.tryToDecreaseMRUInStorage(e, -9) || this.setMRU(s, -9)))
            			{
            				this.breakBlock(e, c00rd, s);
            			}
            		}
            	}
        	}
    	}
    }
    
    public void breakBlock(EntityPlayer e, Coord3D coord, ItemStack s)
    {
    	int x = (int) coord.x;
    	int y = (int) coord.y;
    	int z = (int) coord.z;
    	if(this.canBreak(s))
    	{
    		Block b = e.worldObj.getBlock(x, y, z);
    		b.harvestBlock(e.worldObj, e, x, y, z, e.worldObj.getBlockMetadata(x, y, z));
    		//b.breakBlock(e.worldObj,  x, y, z, b.blockID, e.worldObj.getBlockMetadata(x, y, z));
    		e.worldObj.setBlock(x, y, z, Blocks.air, 0, 3);
    	}
    }
    
    @Override
    public boolean canHarvestBlock(Block par1Block, ItemStack itemStack)
    {
        return true;
    }
    
}
