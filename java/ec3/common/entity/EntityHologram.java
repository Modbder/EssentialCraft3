package ec3.common.entity;

import java.util.ArrayList;
import java.util.List;

import ec3.common.item.ItemBaublesWearable;
import ec3.common.item.ItemsCore;
import ec3.common.mod.EssentialCraftCore;
import ec3.common.registry.AchievementRegistry;
import ec3.common.world.WorldGenOldCatacombs;
import ec3.utils.common.ECUtils;
import DummyCore.Utils.DataStorage;
import DummyCore.Utils.DummyData;
import DummyCore.Utils.MathUtils;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.boss.IBossDisplayData;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.management.ServerConfigurationManager;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.util.WeightedRandomChestContent;
import net.minecraft.world.World;
import net.minecraftforge.common.util.FakePlayer;

public class EntityHologram extends EntityLiving implements IBossDisplayData
{

	public static final double RANGE = 24;
	public int attackID = -1;
	public int attackTimer;
	public int restingTime;
	public int prevAttackID = -1;
	public int damage = 1;
	public double basePosX, basePosY, basePosZ;
	public List<String> players = new ArrayList<String>();
	
	@Override
    protected void fall(float distance)
    {
    	
    }
	
    protected void dropFewItems(boolean playerkill, int fortune)
    {
    	
    	if(!this.worldObj.isRemote)
    	{
    		for(int i = 0; i < this.players.size(); ++i)
    		{
    			EntityPlayer p = MinecraftServer.getServer().getConfigurationManager().func_152612_a(this.players.get(i));
    			if(p != null)
    				p.triggerAchievement(AchievementRegistry.achievementList.get("hologram"));
    			
    			boolean addBig = true;
    			
    			for(int j = 0; j < 4; ++j)
    			{
    				if(p.inventory.armorInventory[j] != null)
    					addBig = false;
    			}
    			
    			if(addBig)
    				p.triggerAchievement(AchievementRegistry.achievementList.get("hologramBig"));
    		}
    	}
    	
    	EssentialCraftCore.proxy.stopSound("hologram");
    	
        int meta = 76;
        
        if(prevAttackID == -1)
        	prevAttackID = this.worldObj.rand.nextInt(4);
        
        if(prevAttackID == 0)
        	meta = 70;
        
        if(prevAttackID == 1)
        	meta = 73;
        
        if(prevAttackID == 2)
        	meta = 72;
        
        if(prevAttackID == 3)
        	meta = 71;
        
    	this.entityDropItem(new ItemStack(ItemsCore.genericItem,1,meta), 0);
    	
    	if(this.worldObj.rand.nextDouble() < 0.1D)
    		this.entityDropItem(new ItemStack(ItemsCore.orbitalRemote,1,0), 0);
    	
    	if(this.worldObj.rand.nextDouble() < 0.1D)
    		this.entityDropItem(new ItemStack(ItemsCore.dividingGun,1,0), 0);
    	
    	if(this.worldObj.rand.nextDouble() < 0.3D)
    		this.entityDropItem(new ItemStack(ItemsCore.record_robocalypse,1,0), 0);
    	
    	World w = this.worldObj;
    	int x = MathHelper.floor_double(posX);
    	int y = MathHelper.floor_double(posY);
    	int z = MathHelper.floor_double(posZ);
    	
		w.setBlock(x, y, z, Blocks.chest);
		TileEntityChest chest = (TileEntityChest) w.getTileEntity(x, y, z);
        if (chest != null)
        {
            WeightedRandomChestContent.generateChestContents(w.rand, WorldGenOldCatacombs.generatedItems, chest, w.rand.nextInt(26)+18);
            IInventory inv = chest;
            for(int i = 0; i < inv.getSizeInventory(); ++i)
            {
            	ItemStack stk = inv.getStackInSlot(i);
            	if(stk != null && stk.getItem() instanceof ItemBaublesWearable)
            	{
            		ItemBaublesWearable.initRandomTag(stk, w.rand);
            	}
            }
        }
    }
	
	public EntityHologram(World w) 
	{
		super(w);
	}
	
    protected void applyEntityAttributes()
    {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.0D);
        this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(400.0D);
        this.getEntityAttribute(SharedMonsterAttributes.knockbackResistance).setBaseValue(1.0D);
    }
	
	@Override
	protected void entityInit() 
	{
		super.entityInit();
		this.getDataWatcher().addObject(12, "||null:null");
	}
	
    protected String getLivingSound()
    {
        return null;
    }

    protected String getHurtSound()
    {
        return null;
    }

    protected String getDeathSound()
    {
        return "essentialcraft:sound.mob.hologram.shutdown";
    }
	
	public void dwWrite()
	{
		if(!this.worldObj.isRemote)
			this.getDataWatcher().updateObject(12, "||aID:"+attackID+"||aTi:"+attackTimer+"||rTi:"+restingTime);
	}
	
	public void dwRead()
	{
		if(this.worldObj.isRemote)
		{
			String str = this.getDataWatcher().getWatchableObjectString(12);
			if(str != null && !str.isEmpty() && !str.equals("||null:null"))
			{
				try
				{
					DummyData[] genDat = DataStorage.parseData(str);
					attackID = Integer.parseInt(genDat[0].fieldValue);
					attackTimer = Integer.parseInt(genDat[1].fieldValue);
					restingTime = Integer.parseInt(genDat[2].fieldValue);
					
				}catch(Exception e)
				{
					e.printStackTrace();
				}
			}
		}
	}
	
	@Override
	public void onUpdate()
	{
		if(this.ticksExisted == 1)
		{
			this.basePosX = posX;
			this.basePosY = posY;
			this.basePosZ = posZ;
		}
		
		if(this.posX != this.basePosX || this.posY != this.basePosY || this.posZ != this.basePosZ)
			this.setPositionAndRotation(basePosX, basePosY, basePosZ, rotationYaw, rotationPitch);
		
		//EssentialCraftCore.proxy.stopSound("hologram");
		if(this.deathTime == 0)
			EssentialCraftCore.proxy.startSound("hologram", "essentialcraft:records.hologram");
		else
			EssentialCraftCore.proxy.stopSound("hologram");
		
		dwWrite();
		
		if(this.motionY < 0.002)
			this.motionY = 0.002;
		
		super.onUpdate();
		
		dwRead();
		
		if(this.isBurning())
			this.extinguish();
		
		if(!this.getActivePotionEffects().isEmpty())
			this.clearActivePotions();
		
		if(!this.worldObj.isRemote)
		{
			if(restingTime == 0 && attackID == -1)
			{
				int rndID = this.worldObj.rand.nextInt(4);
				attackID = rndID;
				attackTimer = 100;
				ECUtils.playSoundToAllNearby(posX, posY, posZ, "essentialcraft:sound.mob.hologram.stop", 5, 2F, 16, this.dimension);
				damage = 1;
			}
			if(attackTimer != 0 && attackID != -1)
			{
				--attackTimer;
				if(attackID == 0)
				{
					if(attackTimer == 20)
					{
						int hMax = 3 - MathHelper.floor_float(getHealth()/getMaxHealth() * 3);
						for(int i = 0; i < players.size(); ++i)
						{
							EntityPlayer p = MinecraftServer.getServer().getConfigurationManager().func_152612_a(players.get(i));
							if(p != null)
							{
								for(int j = 0; j < 1 + hMax; ++j)
								{
									EntityPlayerClone clone = new EntityPlayerClone(p.worldObj);
									clone.setPositionAndRotation(p.posX+MathUtils.randomDouble(rand)*6, p.posY, p.posZ+MathUtils.randomDouble(rand)*6, p.rotationYaw, p.rotationPitch);
									clone.setCurrentItemOrArmor(0, p.getCurrentEquippedItem() != null ? p.getCurrentEquippedItem().copy() : null);
									
									clone.setCurrentItemOrArmor(1, p.inventory.armorInventory[3] != null ? p.inventory.armorInventory[3].copy() : null);
									clone.setCurrentItemOrArmor(2, p.inventory.armorInventory[2] != null ? p.inventory.armorInventory[2].copy() : null);
									clone.setCurrentItemOrArmor(3, p.inventory.armorInventory[1] != null ? p.inventory.armorInventory[1].copy() : null);
									clone.setCurrentItemOrArmor(4, p.inventory.armorInventory[0] != null ? p.inventory.armorInventory[0].copy() : null);
									
									
									this.worldObj.spawnEntityInWorld(clone);
								}
							}
						}
					}
				}
				if(attackID == 1)
				{
					for(int i = 0; i < players.size(); ++i)
					{
						if(players.get(i) == null)
							continue;
						
						this.faceEntity(MinecraftServer.getServer().getConfigurationManager().func_152612_a(players.get(i)), 360F, 180F);
						EntityArmorDestroyer destr = new EntityArmorDestroyer(this.worldObj,this);
						destr.setAngles(this.worldObj.rand.nextFloat()*360, this.worldObj.rand.nextFloat()*360);
						
						this.rotationYaw = this.worldObj.rand.nextFloat()*360;
						this.rotationPitch = 90-this.worldObj.rand.nextFloat()*180;
						this.worldObj.spawnEntityInWorld(destr);
					}
				}
				if(attackID == 2)
				{
					
					
					if(this.attackTimer % 10 == 0)
					{
						if(this.players.size() > 0)
						{
							int i = this.worldObj.rand.nextInt(this.players.size());
							EntityPlayer p = MinecraftServer.getServer().getConfigurationManager().func_152612_a(players.get(i));
							if(p != null)
							{
								EntityOrbitalStrike strike = new EntityOrbitalStrike(worldObj, p.posX, p.posY, p.posZ, damage, 3 - (2 - this.getHealth()/this.getMaxHealth()*2), this);
								this.worldObj.spawnEntityInWorld(strike);
							}
							damage *= 2;
						}
					}
				}
				if(attackID == 3)
				{
					
					if(this.attackTimer % 20 == 0)
					{
						for(int i = 0; i < 1 + (5 - MathHelper.floor_double(this.getHealth()/this.getMaxHealth()*5)); ++i)
							if(this.players.size() > 0)
							{
								int i1 = this.worldObj.rand.nextInt(this.players.size());
								EntityPlayer p = MinecraftServer.getServer().getConfigurationManager().func_152612_a(players.get(i1));
								if(p != null)
								{
									EntityDivider d = new EntityDivider(worldObj, p.posX, p.posY, p.posZ, damage, 2, this);
									this.worldObj.spawnEntityInWorld(d);
								}
							}
					}
				}
			}
			if(attackTimer == 0 && attackID != -1)
			{
				prevAttackID = attackID;
				attackID = -1;
				restingTime = 100 - MathHelper.floor_double(80-(this.getHealth()/this.getMaxHealth()*80));
				ECUtils.playSoundToAllNearby(posX, posY, posZ, "essentialcraft:sound.mob.hologram.stop", 5, 0.01F, 16, this.dimension);
			}
			if(restingTime > 0)
				--restingTime;
		}else
		{
			EntityPlayer p = EssentialCraftCore.proxy.getClientPlayer();
			if(p != null && p.capabilities.isFlying && p.getDistanceToEntity(this) <= RANGE && p.dimension == this.dimension)
			{
				p.capabilities.isFlying = false;
			}
		}
		
		if(!this.worldObj.isRemote && this.ticksExisted % 10 == 0)
		{
			MinecraftServer server = MinecraftServer.getServer();
			ServerConfigurationManager manager = server.getConfigurationManager();
			for(int i = 0; i < players.size(); ++i)
			{
				EntityPlayer p = MinecraftServer.getServer().getConfigurationManager().func_152612_a(players.get(i));
				if(p == null || p.isDead)
					players.remove(i);
			}
			for(int i = 0; i < manager.getCurrentPlayerCount(); ++i)
			{
				EntityPlayerMP player = EntityPlayerMP.class.cast(manager.playerEntityList.get(i));
				
				if(player == null)
					continue;
				
				if(this.players.contains(player.getCommandSenderName()))
				{
					if(player.isDead)
					{
						players.remove(i);
						continue;
					}
					
					if(this.dimension != player.dimension)
						manager.transferPlayerToDimension(player, this.dimension);
					
					double distance = player.getDistanceToEntity(this);
					if(distance > RANGE)
					{
						
						player.setPositionAndRotation(posX, posY, posZ, player.rotationYaw, player.rotationPitch);
						ECUtils.changePlayerPositionOnClient(player);
						player.attackEntityFrom(DamageSource.causeMobDamage(this), 5);
						ECUtils.playSoundToAllNearby(posX, posY, posZ, "random.anvil_break", 1, 0.01F, 8, this.dimension);
					}
					
					if(player.capabilities.isFlying)
						player.capabilities.isFlying = false;
					
				}else
				{
					if(this.dimension != player.dimension)
						continue;
					
					double distance = player.getDistanceToEntity(this);
					if(distance <= RANGE)
					{
						this.players.add(player.getCommandSenderName());
					}
				}
			}
		}
	}
	
	@Override
    public void writeEntityToNBT(NBTTagCompound tag)
    {
        super.writeEntityToNBT(tag);
        tag.setInteger("attackID", attackID);
        tag.setInteger("attackTimer", attackTimer);
        tag.setInteger("restingTime", restingTime);
        tag.setInteger("prevAttackID", prevAttackID);
        tag.setInteger("listSize", players.size());
        tag.setInteger("damage", damage);
        tag.setDouble("basePosX", basePosX);
        tag.setDouble("basePosY", basePosY);
        tag.setDouble("basePosZ", basePosZ);
        for(int i = 0; i < players.size(); ++i)
        	tag.setString("player_"+i, players.get(i));
    }
	
    public void readEntityFromNBT(NBTTagCompound tag)
    {
        super.readEntityFromNBT(tag);
        attackID = tag.getInteger("attackID");
        attackTimer = tag.getInteger("attackTimer");
        restingTime = tag.getInteger("restingTime");
        prevAttackID = tag.getInteger("prevAttackID");
        damage = tag.getInteger("damage");
        basePosX = tag.getDouble("basePosX");
        basePosY = tag.getDouble("basePosY");
        basePosZ = tag.getDouble("basePosZ");
        for(int i = 0; i < tag.getInteger("listSize"); ++i)
        	players.add(tag.getString("player_"+i));
    }
    
    @Override
    public void applyEntityCollision(Entity e)
    {
    	
    }
    
	@Override
    protected void dropEquipment(boolean p_82160_1_, int p_82160_2_)
    {
    	
    }
    
	@Override
    protected void collideWithEntity(Entity e)
    {
    	
    }
	
    public boolean attackEntityFrom(DamageSource src, float f)
    {
    	if(this.attackID != -1)
    		return false;
    	
    	if(src == null)
    		return false;
    	
    	if(src.getSourceOfDamage() == null)
    		return false;
    	
    	if(!(src.getSourceOfDamage() instanceof EntityPlayer))
    		return false;
    	
    	if(src.getSourceOfDamage() instanceof FakePlayer)
    		return false;
    	
    	damage += f;
    	
    	if(f > 40 || damage > 40)
    		this.restingTime = 1;
    	
    	if(src.isProjectile())
    		f /= 4;
    	
    	if(src.isProjectile())
    		ECUtils.playSoundToAllNearby(posX, posY, posZ, "essentialcraft:sound.mob.hologram.damage.projectile", 5, this.worldObj.rand.nextFloat()*2, 16, this.dimension);
    	else
    		ECUtils.playSoundToAllNearby(posX, posY, posZ, "essentialcraft:sound.mob.hologram.damage.melee", 0.3F, this.worldObj.rand.nextFloat()*2, 16, this.dimension);
    	
    	return super.attackEntityFrom(src, f);
    }

}
