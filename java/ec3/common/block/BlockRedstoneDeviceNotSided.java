package ec3.common.block;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import DummyCore.Utils.MathUtils;

import com.mojang.authlib.GameProfile;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ec3.common.mod.EssentialCraftCore;
import ec3.common.tile.TileAnimalSeparator;
import ec3.common.tile.TileCrafter;
import ec3.common.tile.TileCreativeMRUSource;
import ec3.utils.cfg.Config;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.common.IPlantable;
import net.minecraftforge.common.IShearable;
import net.minecraftforge.common.util.FakePlayer;
import net.minecraftforge.common.util.ForgeDirection;

public class BlockRedstoneDeviceNotSided extends BlockContainer{
	
	public static final String[] names = new String[]{
		"replanter",
		"itemShuffler",
		"crafter",
		"breeder",
		"creativeMRUStorage",
		"shearingStation",
		"childSeparator",
		"adultSeparator"
	};
	
	public BlockRedstoneDeviceNotSided() {
		super(Material.rock);
	}
	
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(int side, int meta)
    {
    	return icons[meta];
    }
	
	public static IIcon[] icons = new IIcon[names.length];
	
	
	@Override
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister reg)
    {
		for(int i = 0; i < names.length; ++i)
			icons[i] = reg.registerIcon("essentialcraft:"+names[i]);
    }
	
    @SuppressWarnings({ "unchecked", "rawtypes" })
	@SideOnly(Side.CLIENT)
    public void getSubBlocks(Item p_149666_1_, CreativeTabs p_149666_2_, List p_149666_3_)
    {
    	for(int i = 0; i < names.length; ++i)
    		p_149666_3_.add(new ItemStack(p_149666_1_, 1, i));
    }
	
    public int damageDropped(int meta)
    {
    	return meta;
    }
    
    public void shear(Entity en, IShearable e)
    {
    	if(e.isShearable(new ItemStack(Items.shears), en.worldObj, (int)en.posX, (int)en.posY, (int)en.posZ))
    	{
    		ArrayList<ItemStack> items = e.onSheared(new ItemStack(Items.shears), en.worldObj, (int)en.posX, (int)en.posY, (int)en.posZ, 2);
    		for(ItemStack is : items)
    		{
    			if(is != null)
    			{
    				EntityItem itm = new EntityItem(en.worldObj,en.posX,en.posY,en.posZ,is);
    				if(!en.worldObj.isRemote)
    					en.worldObj.spawnEntityInWorld(itm);
    			}
    		}
    	}
    }
    
    @SuppressWarnings("unchecked")
	public void breed(EntityItem e)
    {
    	if(e.getEntityItem() != null)
    	{
    		AxisAlignedBB aabb = AxisAlignedBB.getBoundingBox(e.posX-0.5D, e.posY-0.5D, e.posZ-0.5D, e.posX+0.5D, e.posY+0.5D, e.posZ+0.5D).expand(3, 3, 3);
    		List<EntityAnimal> animals = e.worldObj.getEntitiesWithinAABB(EntityAnimal.class,aabb);
    		for(EntityAnimal animal : animals)
    		{
    			if(animal.isBreedingItem(e.getEntityItem()) && animal.getGrowingAge() == 0 && !animal.isInLove())
    			{
    				FakePlayer fake = new FakePlayer((WorldServer) e.worldObj, breederFakePlayerProfile);
    				
    				animal.func_146082_f(fake);
    				
    				fake = null;
    				--e.getEntityItem().stackSize;
    				if(invalidate(e))
    					break;
    			}
    		}
    	}
    }
    
    public void shuffle(EntityItem e)
    {
    	if(!e.worldObj.isRemote)
    	{
    		e.setPositionAndRotation(e.posX+MathUtils.randomDouble(e.worldObj.rand), e.posY, e.posZ+MathUtils.randomDouble(e.worldObj.rand), 0, 0);
    	}
    }

	public void plant(EntityItem e)
	{
		int x = MathHelper.floor_double(e.posX);
		int y = MathHelper.floor_double(e.posY);
		int z = MathHelper.floor_double(e.posZ);
		if(e.getEntityItem() != null && !e.worldObj.isRemote)
		{
			ItemStack stk = e.getEntityItem();
			//Blocks - tallgrass, bushes, cacti, etc
			if(stk.getItem() instanceof ItemBlock)
			{
				Block b = Block.getBlockFromItem(stk.getItem());
				if(b instanceof IPlantable)
				{
					if(e.worldObj.isAirBlock(x, y, z) && e.worldObj.getBlock(x, y-1, z).canSustainPlant(e.worldObj, x, y, z, ForgeDirection.UP, IPlantable.class.cast(b)))
					{
						FakePlayer user = new FakePlayer((WorldServer) e.worldObj,planterFakePlayerProfile);
						
						stk.getItem().onItemUse(stk, user, e.worldObj, x, y-1, z, ForgeDirection.UP.ordinal(), 0, 0, 0);
						
						invalidate(e);
						
						user = null;
					}
				}
			}else //Items - seeds, reeds, cake?
			{
				if(stk.getItem() instanceof IPlantable)
				{
					if(e.worldObj.isAirBlock(x, y, z) && e.worldObj.getBlock(x, y-1, z).canSustainPlant(e.worldObj, x, y, z, ForgeDirection.UP, IPlantable.class.cast(stk.getItem())))
					{
						FakePlayer user = new FakePlayer((WorldServer) e.worldObj,planterFakePlayerProfile);
						
						stk.getItem().onItemUse(stk, user, e.worldObj, x, y-1, z, ForgeDirection.UP.ordinal(), 0, 0, 0);
						
						invalidate(e);
						
						user = null;
					}
				}
			}
		}
	}
	
	public boolean invalidate(EntityItem e)
	{
		if(e.getEntityItem() == null || e.getEntityItem().stackSize <= 0)
		{
			e.setDead();
			return true;
		}
		
		return false;
	}
	
    public boolean canProvidePower()
    {
        return true;
    }
    
    @SuppressWarnings("unchecked")
	public void onNeighborBlockChange(World w, int x, int y, int z, Block n) 
    {
    	if(w.getBlockMetadata(x, y, z) == 0)
    	{
	    	if(w.isBlockIndirectlyGettingPowered(x, y, z) || w.getBlockPowerInput(x, y, z) > 0)
	    	{
	    		AxisAlignedBB aabb = AxisAlignedBB.getBoundingBox(x, y, z, x+1, y+1, z+1).expand(12, 12, 12);
	    		List<EntityItem> items = w.getEntitiesWithinAABB(EntityItem.class, aabb);
	    		for(EntityItem itm : items)
	    		{
	    			if(!itm.isDead)
	    				plant(itm);
	    		}
	    	}
    	}
    	if(w.getBlockMetadata(x, y, z) == 1)
    	{
	    	if(w.isBlockIndirectlyGettingPowered(x, y, z) || w.getBlockPowerInput(x, y, z) > 0)
	    	{
	    		AxisAlignedBB aabb = AxisAlignedBB.getBoundingBox(x, y, z, x+1, y+1, z+1).expand(12, 12, 12);
	    		List<EntityItem> items = w.getEntitiesWithinAABB(EntityItem.class, aabb);
	    		for(EntityItem itm : items)
	    		{
	    			if(!itm.isDead)
	    				shuffle(itm);
	    		}
	    	}
    	}
    	if(w.getBlockMetadata(x, y, z) == 3)
    	{
	    	if(w.isBlockIndirectlyGettingPowered(x, y, z) || w.getBlockPowerInput(x, y, z) > 0)
	    	{
	    		AxisAlignedBB aabb = AxisAlignedBB.getBoundingBox(x, y, z, x+1, y+1, z+1).expand(12, 12, 12);
	    		List<EntityItem> items = w.getEntitiesWithinAABB(EntityItem.class, aabb);
	    		for(EntityItem itm : items)
	    		{
	    			if(!itm.isDead)
	    				breed(itm);
	    		}
	    	}
    	}
    	if(w.getBlockMetadata(x, y, z) == 5)
    	{
	    	if(w.isBlockIndirectlyGettingPowered(x, y, z) || w.getBlockPowerInput(x, y, z) > 0)
	    	{
	    		AxisAlignedBB aabb = AxisAlignedBB.getBoundingBox(x, y, z, x+1, y+1, z+1).expand(12, 12, 12);
	    		List<IShearable> sheep = w.getEntitiesWithinAABB(IShearable.class, aabb);
	    		for(IShearable sh : sheep)
	    		{
	    			shear((Entity) sh,sh);
	    		}
	    	}
    	}
    	if(w.getBlockMetadata(x, y, z) == 6 || w.getBlockMetadata(x, y, z) == 7)
    	{
    		TileAnimalSeparator.class.cast(w.getTileEntity(x, y, z)).separate(w.getBlockMetadata(x, y, z) == 6);
    	}
    }
	
	public static GameProfile planterFakePlayerProfile = new GameProfile(UUID.fromString("5cd89d0b-e9ba-0000-89f4-badbb05964ac"), "[EC3]Planter");
	public static GameProfile breederFakePlayerProfile = new GameProfile(UUID.fromString("5cd89d0b-e9ba-0000-89f4-badbb05964ad"), "[EC3]Breeder");
	
	@Override
	public TileEntity createNewTileEntity(World p_149915_1_, int meta) {
		return meta == 2 ? new TileCrafter() :  meta == 4 ? new TileCreativeMRUSource() : meta == 6 || meta == 7 ? new TileAnimalSeparator() : null;
	}

	@Override
	 public boolean onBlockActivated(World par1World, int par2, int par3, int par4, EntityPlayer par5EntityPlayer, int par6, float par7, float par8, float par9)
	 {
		if(par1World.getTileEntity(par2, par3, par4) == null)
			return false;
		
	    if (par1World.isRemote)
	    {
	        return true;
	    }else
	    {
	    	if(!par5EntityPlayer.isSneaking())
	    	{
	     		par5EntityPlayer.openGui(EssentialCraftCore.core, Config.guiID[0], par1World, par2, par3, par4);
	         	return true;
	      	}
	      	else
	      	{
	       		return false;
	       	}
	     }
	 }
	
}
