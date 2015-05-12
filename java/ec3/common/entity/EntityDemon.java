package ec3.common.entity;

import java.util.List;

import ec3.api.DemonTrade;
import ec3.common.block.BlockDemonicPentacle;
import ec3.common.item.ItemsCore;
import ec3.common.mod.EssentialCraftCore;
import ec3.utils.cfg.Config;
import DummyCore.Utils.MathUtils;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.oredict.OreDictionary;

public class EntityDemon extends EntityLiving implements IInventory{
	
	public ItemStack inventory;
	public ItemStack desiredItem;

    protected boolean canDespawn()
    {
        return false;
    }
	
	public EntityDemon(World w) 
	{
		super(w);
		if(w.rand.nextBoolean())
		{
			desiredItem = new ItemStack(ItemsCore.soul,1+w.rand.nextInt(7),w.rand.nextInt(DemonTrade.allMobs.size()));
		}else
		{
			desiredItem = DemonTrade.trades.get(DemonTrade.allMobs.size() + w.rand.nextInt(DemonTrade.trades.size() - DemonTrade.allMobs.size())).desiredItem;
		}
	}
	
    protected String getLivingSound()
    {
        return this.worldObj.rand.nextBoolean() ? "essentialcraft:sound.mob.demon.say" : "essentialcraft:sound.mob.demon.summon";
    }
    
    protected String getHurtSound()
    {
        return "essentialcraft:sound.mob.demon.depart";
    }
	
    public boolean attackEntityFrom(DamageSource p_70097_1_, float p_70097_2_)
    {
    	this.playSound(getHurtSound(), 1, 1);
    	this.setDead();
        for (int i = 0; i < 400; ++i)
        {
            double d2 = this.rand.nextGaussian() * 0.02D;
            double d0 = this.rand.nextGaussian() * 0.02D;
            double d1 = this.rand.nextGaussian() * 0.02D;
            this.worldObj.spawnParticle("explode", this.posX + (double)(this.rand.nextFloat() * this.width * 2.0F) - (double)this.width, this.posY + (double)(this.rand.nextFloat() * this.height), this.posZ + (double)(this.rand.nextFloat() * this.width * 2.0F) - (double)this.width, d2, d0, d1);
        }
        return false;
    }
	
	@SuppressWarnings("unchecked")
	@Override
	public void onUpdate()
	{
		if(!this.worldObj.isRemote)
			this.getDataWatcher().updateObject(12, desiredItem);
		
		super.onUpdate();
		
		if(this.getStackInSlot(0) != null && this.desiredItem != null)
		{
			if((this.desiredItem.getItemDamage() != OreDictionary.WILDCARD_VALUE && this.getStackInSlot(0).isItemEqual(desiredItem) && this.getStackInSlot(0).stackSize >= this.desiredItem.stackSize) || (this.desiredItem.getItemDamage() == OreDictionary.WILDCARD_VALUE && this.getStackInSlot(0).getItem() == this.desiredItem.getItem() && this.getStackInSlot(0).stackSize >= this.desiredItem.stackSize))
			{
				this.setDead();
		        for (int i = 0; i < 400; ++i)
		        {
		            double d2 = this.rand.nextGaussian() * 0.02D;
		            double d0 = this.rand.nextGaussian() * 0.02D;
		            double d1 = this.rand.nextGaussian() * 0.02D;
		            this.worldObj.spawnParticle("explode", this.posX + (double)(this.rand.nextFloat() * this.width * 2.0F) - (double)this.width, this.posY + (double)(this.rand.nextFloat() * this.height), this.posZ + (double)(this.rand.nextFloat() * this.width * 2.0F) - (double)this.width, d2, d0, d1);
		        }
		        this.worldObj.playSound(this.posX,this.posY,this.posZ,"essentialcraft:sound.mob.demon.doom", this.getSoundVolume(), this.getSoundPitch(),false);
		        ItemStack result = new ItemStack(ItemsCore.genericItem,3+this.worldObj.rand.nextInt(6),52);
		        EntityItem itm = new EntityItem(this.worldObj,this.posX,this.posY,this.posZ,result);
		        if(!this.worldObj.isRemote)
		        	this.worldObj.spawnEntityInWorld(itm);
			}
		}
		
		if(this.worldObj.isRaining() && this.worldObj.canBlockSeeTheSky(MathHelper.floor_double(posX), MathHelper.floor_double(posY+1), MathHelper.floor_double(posZ)))
		{
			for(int i = 0; i < 20; ++i)
				this.worldObj.spawnParticle("smoke", posX+MathUtils.randomDouble(getRNG()), posY+1.3D+MathUtils.randomDouble(getRNG())*2, posZ+MathUtils.randomDouble(getRNG()), 0, 0.1D, 0);
		}
		if(this.ticksExisted % 40 == 0)
		{
			for(int dx = -1; dx <= 1; ++dx)
			{
				for(int dz = -1; dz <= 1; ++dz)
				{
					Block b = this.worldObj.getBlock(MathHelper.floor_double(posX)+dx, MathHelper.floor_double(posY), MathHelper.floor_double(posZ)+dz);
					if(b instanceof BlockDemonicPentacle)
						return;
				}
			}
			this.attackEntityFrom(DamageSource.outOfWorld, 1);
		}
		if(this.ticksExisted % 20 == 0)
		{
			List<EntityMob> zombies = this.worldObj.getEntitiesWithinAABB(EntityMob.class, AxisAlignedBB.getBoundingBox(posX-0.5D, posY-0.5D, posZ-0.5D, posX+0.5D, posY+0.5D, posZ+0.5D).expand(12, 12, 12));
			if(!zombies.isEmpty())
			{
				EntityMob z = zombies.get(getRNG().nextInt(zombies.size()));
				if(z.isEntityAlive())
				{
					this.swingItem();
					z.attackEntityFrom(DamageSource.causeMobDamage(this), z.getMaxHealth()*1.6F);
					this.worldObj.createExplosion(this, z.posX, z.posY, z.posZ, 2, false);
				}
			}
		}
		if(this.worldObj.isRemote)
			this.desiredItem = this.getDataWatcher().getWatchableObjectItemStack(12);
		//EssentialCraftCore.proxy.SmokeFX(posX,posY+1.5D+MathUtils.randomDouble(getRNG()),posZ,MathUtils.randomDouble(getRNG())/18,-0.09D+MathUtils.randomDouble(getRNG())/18,MathUtils.randomDouble(getRNG())/18,3,1,0.6D-this.worldObj.rand.nextDouble()/3D,0.2D);
	}
	

	@Override
	protected void entityInit() 
	{
		super.entityInit();
		this.getDataWatcher().addObject(12, new ItemStack(Items.apple,1,0));
	}

	@Override
	public ItemStack getHeldItem()
	{
		return null;
	}

	@Override
	public ItemStack getEquipmentInSlot(int p_71124_1_)
	{
		return null;
	}

	@Override
	public void setCurrentItemOrArmor(int p_70062_1_, ItemStack p_70062_2_) {
		
	}

	@Override
	public ItemStack[] getLastActiveItems() 
	{
		return new ItemStack[5];
	}

	@Override
	public int getSizeInventory()
	{
		return 1;
	}

	@Override
	public ItemStack getStackInSlot(int slot)
	{
		return this.inventory;
	}

	@Override
	public ItemStack decrStackSize(int slot, int i) {
		this.inventory.stackSize -= i;
		if(this.inventory.stackSize <= 0)
			this.setInventorySlotContents(0, null);
		return this.inventory;
	}

	@Override
	public ItemStack getStackInSlotOnClosing(int slot)
	{
		return this.inventory;
	}

	@Override
	public void setInventorySlotContents(int slot, ItemStack stk) 
	{
		this.inventory = stk;
	}

	@Override
	public String getInventoryName() 
	{
		return "demon";
	}

	@Override
	public boolean hasCustomInventoryName() 
	{
		return false;
	}

	@Override
	public int getInventoryStackLimit() 
	{
		return 64;
	}

	@Override
	public void markDirty() 
	{
		
	}

	@Override
	public boolean isUseableByPlayer(EntityPlayer p) 
	{
		return !this.isDead;
	}

	@Override
	public void openInventory()
	{
		
	}

	@Override
	public void closeInventory() 
	{
		
	}
	
    public boolean interact(EntityPlayer p)
    {
    	this.playSound("essentialcraft:sound.mob.demon.trade", this.getSoundVolume(), this.getSoundPitch());
    	p.openGui(EssentialCraftCore.core, Config.guiID[1], this.worldObj, MathHelper.floor_double(posX), MathHelper.floor_double(posY), MathHelper.floor_double(posZ));
    	return true;
    }
	
	@Override
    public void writeEntityToNBT(NBTTagCompound tag)
    {
        super.writeEntityToNBT(tag);
        if(this.desiredItem != null)
        {
        	NBTTagCompound itemTag = new NBTTagCompound();
        	this.desiredItem.writeToNBT(itemTag);
        	tag.setTag("desired", itemTag);
        }
        if(this.inventory != null)
        {
        	NBTTagCompound itemTag = new NBTTagCompound();
        	this.inventory.writeToNBT(itemTag);
        	tag.setTag("inventory", itemTag);
        }
    }
	
    public void readEntityFromNBT(NBTTagCompound tag)
    {
        super.readEntityFromNBT(tag);
        if(tag.hasKey("desired"))
        	this.desiredItem = ItemStack.loadItemStackFromNBT(tag.getCompoundTag("desired"));
        if(tag.hasKey("inventory"))
        	this.inventory = ItemStack.loadItemStackFromNBT(tag.getCompoundTag("inventory"));
    }

	@Override
	public boolean isItemValidForSlot(int slot, ItemStack stk) 
	{
		return true;
	}

}
