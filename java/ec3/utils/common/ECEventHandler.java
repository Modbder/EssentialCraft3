package ec3.utils.common;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.Set;

import baubles.api.BaublesApi;
import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.client.event.ConfigChangedEvent;
import cpw.mods.fml.common.eventhandler.Event.Result;
import cpw.mods.fml.common.eventhandler.EventPriority;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent;
import cpw.mods.fml.common.gameevent.TickEvent.Phase;
import cpw.mods.fml.common.gameevent.TickEvent.WorldTickEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ec3.api.ApiCore;
import ec3.api.GunRegistry.GunMaterial;
import ec3.api.GunRegistry.GunType;
import ec3.api.GunRegistry.ScopeMaterial;
import ec3.api.GunRegistry;
import ec3.api.IUBMRUGainModifier;
import ec3.api.WorldEventLibrary;
import ec3.client.gui.GuiResearchBook;
import ec3.common.item.BaublesModifier;
import ec3.common.item.ItemGun;
import ec3.common.item.ItemMagicalWings;
import ec3.common.item.ItemShadeSword;
import ec3.common.item.ItemWindAxe;
import ec3.common.item.ItemWindHoe;
import ec3.common.item.ItemWindPickaxe;
import ec3.common.item.ItemWindShovel;
import ec3.common.item.ItemWindSword;
import ec3.common.item.ItemsCore;
import ec3.common.mod.EssentialCraftCore;
import ec3.common.registry.ResearchRegistry;
import ec3.common.tile.TileAMINEjector;
import ec3.common.tile.TileAMINInjector;
import ec3.common.tile.TileMagicalAssembler;
import ec3.common.tile.TileMagicalMirror;
import ec3.common.tile.TilePlayerPentacle;
import ec3.common.tile.TileWeaponMaker;
import ec3.common.world.BiomeGenCorruption_Chaos;
import ec3.common.world.BiomeGenCorruption_Frozen;
import ec3.common.world.BiomeGenCorruption_Magic;
import ec3.utils.cfg.Config;
import DummyCore.Events.DummyEvent_OnPacketRecieved;
import DummyCore.Utils.Coord3D;
import DummyCore.Utils.DataStorage;
import DummyCore.Utils.DummyData;
import DummyCore.Utils.DummyDataUtils;
import DummyCore.Utils.MathUtils;
import DummyCore.Utils.MiscUtils;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.monster.EntityEnderman;
import net.minecraft.entity.monster.EntityMagmaCube;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.entity.monster.EntitySlime;
import net.minecraft.entity.monster.EntitySnowman;
import net.minecraft.entity.monster.EntitySpider;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.passive.EntityChicken;
import net.minecraft.entity.passive.EntityCow;
import net.minecraft.entity.passive.EntitySquid;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemAxe;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemHoe;
import net.minecraft.item.ItemPickaxe;
import net.minecraft.item.ItemShears;
import net.minecraft.item.ItemSpade;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.server.MinecraftServer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraftforge.client.event.FOVUpdateEvent;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.common.util.FakePlayer;
import net.minecraftforge.event.AnvilUpdateEvent;
import net.minecraftforge.event.entity.living.EnderTeleportEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.living.LivingSetAttackTargetEvent;
import net.minecraftforge.event.entity.player.EntityInteractEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.entity.player.PlayerPickupXpEvent;
import net.minecraftforge.event.terraingen.BiomeEvent;
import net.minecraftforge.event.terraingen.DecorateBiomeEvent;
import net.minecraftforge.event.terraingen.DecorateBiomeEvent.Decorate.EventType;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.event.world.BlockEvent.HarvestDropsEvent;
import net.minecraftforge.oredict.OreDictionary;

public class ECEventHandler {
	
	public String lastTickLanguage;

	@SubscribeEvent
	public void anvilEvent(AnvilUpdateEvent event)
	{
		if(event.left != null && event.left.getItem() instanceof ItemGun && event.right != null)
		{
			NBTTagCompound tag = MiscUtils.getStackTag(event.left);
			if(tag.hasKey("base"))
			{
				GunMaterial material = null;
				for(int i = 0; i < GunRegistry.gunMaterials.size(); ++i)
				{
					GunMaterial gm = GunRegistry.gunMaterials.get(i);
					if(gm.recipe.isItemEqual(event.right))
					{
						material = gm;
						break;
					}
				}
				if(material != null)
				{
					ItemStack result = event.left.copy();
					MiscUtils.getStackTag(result).setFloat("gunDamage", 0);
					event.cost = 4;
					event.materialCost = 1;
					event.output = result;
				}
				
			}
		}
	}
	
	public ItemStack findShearItem(Entity e)
	{
		int rN = 1+e.worldObj.rand.nextInt(3);
		if(e instanceof EntityCow)
			return new ItemStack(Items.leather,rN,0);
		if(e instanceof EntityChicken)
			return new ItemStack(Items.feather,rN,0);
		if(e instanceof EntitySquid)
			return new ItemStack(Items.dye,rN,0);
		if(e instanceof EntitySpider)
			return new ItemStack(Items.string,rN,0);
		if(e instanceof EntityCreeper)
			return new ItemStack(Items.gunpowder,rN,0);
		if(e instanceof EntitySkeleton)
			return new ItemStack(Items.dye,rN,15);
		if(e instanceof EntityMagmaCube)
			return new ItemStack(Items.magma_cream,rN,0);
		if(e instanceof EntitySlime)
			return new ItemStack(Items.slime_ball,rN,0);
		if(e instanceof EntityZombie)
			return new ItemStack(Items.rotten_flesh,rN,0);
		if(e instanceof EntitySnowman)
			return new ItemStack(Blocks.snow,rN,0);
		return null;
	}
	
	@SuppressWarnings("unchecked")
	@SubscribeEvent
	public void attckTargetEvent(LivingSetAttackTargetEvent event)
	{
		if(event.target != null)
		{
			if(event.target instanceof EntityPlayer)
			{
				EntityPlayer player = (EntityPlayer) event.target;
				boolean changeTarget = false;
		    	IInventory b = BaublesApi.getBaubles(player);
		    	if(b != null)
		    	{
		    		for(int i = 0; i < b.getSizeInventory(); ++i)
		    		{
		    			ItemStack is = b.getStackInSlot(i);
		    			if(is != null && is.getItem() != null && is.getItem() instanceof BaublesModifier && is.getItemDamage() == 30)
		    				changeTarget = true;
		    		}
		    	}
		    	if(changeTarget)
		    	{
		    		List<EntityLivingBase> entities = event.entityLiving.worldObj.getEntitiesWithinAABB(EntityLivingBase.class, AxisAlignedBB.getBoundingBox(event.entityLiving.posX-0.5D, event.entityLiving.posY-0.5D, event.entityLiving.posZ-0.5D, event.entityLiving.posX+0.5D, event.entityLiving.posY+0.5D, event.entityLiving.posZ+0.5D).expand(6, 3, 6));
		    		for(int i = 0; i < entities.size(); ++i)
		    		{
		    			EntityLivingBase base = entities.get(i);
		    			if(base == event.entityLiving || base == event.target)
		    				entities.remove(i);
		    		}
		    		if(!entities.isEmpty())
		    		{
		    			EntityLivingBase ba = entities.get(event.entityLiving.worldObj.rand.nextInt(entities.size()));
		    			if(ba != event.entityLiving && !(ba instanceof EntityPlayer))
		    			{
			    			event.entityLiving.setRevengeTarget(ba);
			    			event.entityLiving.setLastAttacker(ba);
		    			}
		    		}
		    	}
			}
		}
	}
	
	@SubscribeEvent
	public void xpEvent(PlayerPickupXpEvent event)
	{
		EntityPlayer player = event.entityPlayer;
		boolean edouble = false;
    	IInventory b = BaublesApi.getBaubles(player);
    	if(b != null)
    	{
    		for(int i = 0; i < b.getSizeInventory(); ++i)
    		{
    			ItemStack is = b.getStackInSlot(i);
    			if(is != null && is.getItem() != null && is.getItem() instanceof BaublesModifier && is.getItemDamage() == 29)
    				edouble = true;
    		}
    	}
    	if(edouble)
    		event.orb.xpValue*=2;
	}
	
	@SubscribeEvent
	public void shearEvent(EntityInteractEvent event)
	{
		EntityPlayer player = event.entityPlayer;
		if(player != null)
		{
			boolean shear = false;
        	IInventory b = BaublesApi.getBaubles(player);
        	if(b != null)
        	{
        		for(int i = 0; i < b.getSizeInventory(); ++i)
        		{
        			ItemStack is = b.getStackInSlot(i);
        			if(is != null && is.getItem() != null && is.getItem() instanceof BaublesModifier && is.getItemDamage() == 28)
        				shear = true;
        		}
        	}
        	ItemStack stk = player.getCurrentEquippedItem();
        	int cost = 1000;
        	shear = shear && stk != null && stk.getItem() != null && stk.getItem() instanceof ItemShears && ApiCore.getPlayerData(player).getPlayerUBMRU() >= cost && !player.isSwingInProgress;
        	if(shear)
        	{
        		Entity base = event.target;
        		stk.damageItem(32, player);
        		ItemStack is = this.findShearItem(base);
        		if(is != null)
        		{
	                EntityItem ent = new EntityItem(base.worldObj,base.posX,base.posY,base.posZ,is);
	                Random rand = base.worldObj.rand;
	                ent.motionY += rand.nextFloat() * 0.05F;
	                ent.motionX += (rand.nextFloat() - rand.nextFloat()) * 0.1F;
	                ent.motionZ += (rand.nextFloat() - rand.nextFloat()) * 0.1F;
	                player.worldObj.playSound(player.posX, player.posY, player.posZ, "mob.sheep.shear", 1, 1, false);
	                if(!player.worldObj.isRemote)
	                	player.worldObj.spawnEntityInWorld(ent);
	                player.swingItem();
	        		ApiCore.getPlayerData(player).modifyUBMRU(ApiCore.getPlayerData(player).getPlayerUBMRU()-cost);
        		}
        	}
		}
	}
	
	@SubscribeEvent
	public void enderTeleport(EnderTeleportEvent event)
	{
		if(event.entityLiving != null)
		{
			if(event.entityLiving instanceof EntityEnderman)
			{
				EntityEnderman ender = (EntityEnderman) event.entityLiving;
				EntityPlayer player = ender.worldObj.getClosestPlayerToEntity(ender, 16D);
				if(player != null)
				{
					boolean stopTeleportation = false;
		        	IInventory b = BaublesApi.getBaubles(player);
		        	if(b != null)
		        	{
		        		for(int i = 0; i < b.getSizeInventory(); ++i)
		        		{
		        			ItemStack is = b.getStackInSlot(i);
		        			if(is != null && is.getItem() != null && is.getItem() instanceof BaublesModifier && is.getItemDamage() == 27)
		        				stopTeleportation = true;
		        		}
		        	}
		        	if(stopTeleportation)
		        	{
		        		event.setCanceled(true);
		        	}
				}
			}
		}
	}
	
	@SubscribeEvent
	public void livingDeath(LivingDropsEvent event)
	{
		if(event.source != null)
		{
			if(event.source.getSourceOfDamage() != null && event.source.getSourceOfDamage() instanceof EntityPlayer)
			{
				EntityPlayer player = (EntityPlayer) event.source.getSourceOfDamage();
				boolean increaseDrops = false;
	        	IInventory b = BaublesApi.getBaubles(player);
	        	if(b != null)
	        	{
	        		for(int i = 0; i < b.getSizeInventory(); ++i)
	        		{
	        			ItemStack is = b.getStackInSlot(i);
	        			if(is != null && is.getItem() != null && is.getItem() instanceof BaublesModifier && is.getItemDamage() == 26)
	        				increaseDrops = true;
	        		}
	        	}
	        	if(increaseDrops && ApiCore.getPlayerData(player).getPlayerUBMRU() >= 1000)
	        	{
	        		ApiCore.getPlayerData(player).modifyUBMRU(ApiCore.getPlayerData(player).getPlayerUBMRU()-1000);
	        		for(int i = 0; i < event.drops.size(); ++i)
	        		{
	        			EntityItem ei = event.drops.get(i);
	        			if(ei != null)
	        			{
	        				ItemStack is = ei.getEntityItem();
	        				if(is != null)
	        				{
	        					is.stackSize += player.worldObj.rand.nextInt(3);
	        				}
	        			}
	        		}
	        	}
			}
		}
	}
	
	
	@SubscribeEvent
	public void breakBlock(BlockEvent.BreakEvent event)
	{
		boolean gainXP = false;
		boolean xpToU = false;
		if(event.getPlayer() != null)
		{
        	IInventory b = BaublesApi.getBaubles(event.getPlayer());
        	if(b != null)
        	{
        		for(int i = 0; i < b.getSizeInventory(); ++i)
        		{
        			ItemStack is = b.getStackInSlot(i);
        			if(is != null && is.getItem() != null && is.getItem() instanceof BaublesModifier && is.getItemDamage() == 23)
        				gainXP = true;
        			if(is != null && is.getItem() != null && is.getItem() instanceof BaublesModifier && is.getItemDamage() == 24)
        				xpToU = true;
        		}
        	}
        	if(gainXP)
        	{
        		event.setExpToDrop(event.getExpToDrop()*3);
        		if(event.getExpToDrop() <= 0 && event.world.rand.nextFloat() < 0.05F)
        		{
        			event.setExpToDrop(1);
        		}
        	}
        	if(xpToU)
        	{
        		int xp = event.getExpToDrop();
        		ApiCore.getPlayerData(event.getPlayer()).modifyUBMRU(ApiCore.getPlayerData(event.getPlayer()).getPlayerUBMRU() + xp*1000);
        		event.setExpToDrop(0);
        	}
		}	
	}
	
	
	@SubscribeEvent
	public void harvestDrops(HarvestDropsEvent event)
	{
		if(event.harvester != null)
		{
			boolean increaseFortune = false;
			
        	IInventory b = BaublesApi.getBaubles(event.harvester);
        	if(b != null)
        	{
        		for(int i = 0; i < b.getSizeInventory(); ++i)
        		{
        			ItemStack is = b.getStackInSlot(i);
        			if(is != null && is.getItem() != null && is.getItem() instanceof BaublesModifier && is.getItemDamage() == 22)
        				increaseFortune = true;
        		}
        	}
        	if(increaseFortune && ApiCore.getPlayerData(event.harvester).getPlayerUBMRU() >= 500)
        	{
        		ApiCore.getPlayerData(event.harvester).modifyUBMRU(ApiCore.getPlayerData(event.harvester).getPlayerUBMRU() - 500);
        		for(int i = 0; i < event.drops.size(); ++i)
        		{
        			ItemStack is = event.drops.get(i);
        			if(is != null && is.getItem() != null && !(is.getItem() instanceof ItemBlock))
        			{
        				is.stackSize+=1;
        			}
        		}
        	}
        	String lapisOreDictName = "gemLapis";
        	boolean hasLapis = false;
        	fD:for(ItemStack is : event.drops)
        	{
        		if(OreDictionary.getOreIDs(is).length != 0)
        		{
        			for(int i : OreDictionary.getOreIDs(is))
        			{
        				if(OreDictionary.getOreName(i).equalsIgnoreCase(lapisOreDictName))
        				{
        					hasLapis = true;
        					break fD;
        				}
        			}
        		}
        		
        	}
        	if(hasLapis)
        	{
        		event.drops.add(new ItemStack(ItemsCore.genericItem,12+event.harvester.worldObj.rand.nextInt(8*(event.fortuneLevel+1)),51));
        	}
		}
	}
	
	
	@SubscribeEvent
	public void damageEvent(LivingHurtEvent event)
	{
		if(event.source != null)
		{
			if(event.source.getSourceOfDamage() != null)
			{
				if(event.source.getSourceOfDamage() instanceof EntityPlayer)
				{
					EntityPlayer player = (EntityPlayer) event.source.getSourceOfDamage();
					boolean reset = false;
					boolean dd = false;
					boolean damageScrewup = false;
					boolean radiation = false;
					
		        	IInventory b = BaublesApi.getBaubles(player);
		        	if(b != null)
		        	{
		        		for(int i = 0; i < b.getSizeInventory(); ++i)
		        		{
		        			ItemStack is = b.getStackInSlot(i);
		        			if(is != null && is.getItem() != null && is.getItem() instanceof BaublesModifier && is.getItemDamage() == 13)
		        				reset = true;
		        			if(is != null && is.getItem() != null && is.getItem() instanceof BaublesModifier && is.getItemDamage() == 14)
		        				dd = true;
		        			if(is != null && is.getItem() != null && is.getItem() instanceof BaublesModifier && is.getItemDamage() == 16)
		        				damageScrewup = true;
		        			if(is != null && is.getItem() != null && is.getItem() instanceof BaublesModifier && is.getItemDamage() == 21)
		        				radiation = true;
		        		}
		        	}
		        	if(dd)
		        	{
		        		event.ammount*=2;
		        	}
		        	if(reset)
		        	{
		        		if(ApiCore.getPlayerData(player).getPlayerUBMRU() >= 300)
		        		{
		        			ApiCore.getPlayerData(player).modifyUBMRU(ApiCore.getPlayerData(player).getPlayerUBMRU()-300);
		        			event.entity.hurtResistantTime = 0;
		        			event.entityLiving.hurtTime = 0;
		        		}
		        	}
		        	if(damageScrewup)
		        	{
		        		if(event.entityLiving.hurtResistantTime <= 0 || event.entityLiving.hurtResistantTime <= 0)
		        		{
		        			float percentage = event.ammount/10;
		        			event.ammount -= percentage;
		        			event.entityLiving.attackEntityFrom(DamageSource.magic, percentage);
		        			event.entity.hurtResistantTime = 0;
		        			event.entityLiving.hurtTime = 0;
		        		}
		        	}
		        	if(radiation)
		        	{
		        		if(event.entityLiving instanceof EntityPlayer)
		        		{
		        			RadiationManager.increasePlayerRadiation((EntityPlayer) event.entityLiving, (int) (event.ammount*1000*5));
		        		}else
		        		{
		        			event.entityLiving.getEntityAttribute(SharedMonsterAttributes.maxHealth).applyModifier(new AttributeModifier(SharedMonsterAttributes.maxHealth.getAttributeUnlocalizedName(), -event.ammount, 0));
		        		}	
		        	}
				}
			}
			
		}
		if(event.entityLiving instanceof EntityPlayer)
		{
			EntityPlayer player = (EntityPlayer) event.entityLiving;
			boolean dd = false;
			boolean saveFromDeath = false;
			boolean radiation = false;
			int timesToReflect = 0;
        	IInventory b = BaublesApi.getBaubles(player);
        	if(b != null)
        	{
        		for(int i = 0; i < b.getSizeInventory(); ++i)
        		{
        			ItemStack is = b.getStackInSlot(i);
        			if(is != null && is.getItem() != null && is.getItem() instanceof BaublesModifier && is.getItemDamage() == 14)
        				dd = true;
        			if(is != null && is.getItem() != null && is.getItem() instanceof BaublesModifier && is.getItemDamage() == 15)
        				saveFromDeath = true;
        			if(is != null && is.getItem() != null && is.getItem() instanceof BaublesModifier && is.getItemDamage() == 21)
        				radiation = true;
        			if(is != null && is.getItem() != null && is.getItem() instanceof BaublesModifier && is.getItemDamage() == 25)
        				++timesToReflect;
        		}
        	}
        	if(dd)
        	{
        		event.ammount*=2;
        	}
        	for(int i = 0; i < timesToReflect; ++i)
        	{
        		if(!player.worldObj.isRemote && event.source!=null&&event.source.getSourceOfDamage()!=null&&player.worldObj.rand.nextFloat()<0.1F)
        		{
        			event.source.getSourceOfDamage().attackEntityFrom(DamageSource.causePlayerDamage(player), event.ammount);
        			event.ammount = 0F;
        			break;
        		}
        	}
        	if(saveFromDeath)
        	{
        		if(player.getHealth() - event.ammount <= 0)
        		{
        			if(ApiCore.getPlayerData(player).getPlayerUBMRU() >= event.ammount*5000)
        			{
        				ApiCore.getPlayerData(player).modifyUBMRU((int) (ApiCore.getPlayerData(player).getPlayerUBMRU() - event.ammount*5000));
        				event.ammount = 0;
        			}
        		}
        	}
        	if(radiation)
        	{
        		RadiationManager.increasePlayerRadiation(player, (int) (event.ammount*1000));
        	}
		}else
		{

		}
		if(event.source != null && event.source.getSourceOfDamage() instanceof EntityLivingBase)
		{
			EntityLivingBase elb = EntityLivingBase.class.cast(event.source.getSourceOfDamage());
			if(elb.getHeldItem() != null && elb.getHeldItem().getItem() instanceof ItemShadeSword)
			{
				ItemShadeSword iss = ItemShadeSword.class.cast(elb.getHeldItem().getItem());
				iss.hitEntity(event.entityLiving.getHeldItem(), event.entityLiving, elb);
			}
		}
	}
	
	@SubscribeEvent
	public void configSync(ConfigChangedEvent.OnConfigChangedEvent event)
	{
		if(event.modID.equals("essentialcraft"))
		{
			Config.config.save();
			Config.instance.load(Config.config);
		}
	}
	
	@SubscribeEvent
	public void guiButtonPressed(DummyCore.Events.DummyEvent_OnClientGUIButtonPress event)
	{
		if(event.client_ParentClassPath.equalsIgnoreCase("ec3.client.gui.GuiFilter"))
		{
			EntityPlayer p = event.presser;
			ItemStack is = p.getCurrentEquippedItem();
			NBTTagCompound itemTag = MiscUtils.getStackTag(is);
			if(event.buttonID == 0)
			{
				if(itemTag.getBoolean("ignoreMeta"))
					itemTag.setBoolean("ignoreMeta", false);
				else
					itemTag.setBoolean("ignoreMeta", true);
			}
			if(event.buttonID == 1)
			{
				if(itemTag.getBoolean("ignoreNBT"))
					itemTag.setBoolean("ignoreNBT", false);
				else
					itemTag.setBoolean("ignoreNBT", true);
			}
			if(event.buttonID == 2)
			{
				if(itemTag.getBoolean("ignoreOreDict"))
					itemTag.setBoolean("ignoreOreDict", false);
				else
					itemTag.setBoolean("ignoreOreDict", true);
			}
			is.setTagCompound(itemTag);
		}
		if(event.client_ParentClassPath.equalsIgnoreCase("ec3.client.gui.GuiAMINEjector"))
		{
			TileAMINEjector tile = (TileAMINEjector) event.presser.worldObj.getTileEntity(event.x, event.y, event.z);
			if(event.buttonID == 0)
				tile.slotnum += 1;
			else
				tile.slotnum -= 1;
			event.presser.openGui(EssentialCraftCore.core, Config.guiID[0], event.presser.worldObj, event.x, event.y, event.z);
		}
		if(event.client_ParentClassPath.equalsIgnoreCase("ec3.client.gui.GuiAMINInjector"))
		{
			TileAMINInjector tile = (TileAMINInjector) event.presser.worldObj.getTileEntity(event.x, event.y, event.z);
			if(event.buttonID == 0)
				tile.slotnum += 1;
			else
				tile.slotnum -= 1;
			event.presser.openGui(EssentialCraftCore.core, Config.guiID[0], event.presser.worldObj, event.x, event.y, event.z);
		}
		if(event.client_ParentClassPath.equalsIgnoreCase("ec3.client.gui.GuiMagicalAssembler") && event.buttonID == 0)
		{
			TileMagicalAssembler assembler = (TileMagicalAssembler) event.presser.worldObj.getTileEntity(event.x, event.y, event.z);
			int id = Integer.parseInt(event.additionalData[0].fieldValue);
			assembler.currentRecipe = id;
			assembler.formRequiredComponents();
		}
		if(event.client_ParentClassPath.equalsIgnoreCase("ec3.client.gui.GuiWeaponBench") && event.buttonID == 0)
		{
			TileWeaponMaker maker = (TileWeaponMaker) event.presser.worldObj.getTileEntity(event.x, event.y, event.z);
			maker.makeWeapon();
		}
		if(event.client_ParentClassPath.equalsIgnoreCase("ec3.client.gui.GuiWeaponBench") && event.buttonID == 1)
		{
			TileWeaponMaker maker = (TileWeaponMaker) event.presser.worldObj.getTileEntity(event.x, event.y, event.z);
			
			++maker.index;
			if(maker.index > 3)
				maker.index = 0;
			
			maker.getWorldObj().setBlockMetadataWithNotify(maker.xCoord, maker.yCoord, maker.zCoord, maker.index, 3);
			event.presser.closeScreen();
			event.presser.openGui(EssentialCraftCore.core, Config.guiID[0], event.presser.worldObj, event.x, event.y, event.z);
			
			maker.makeWeapon();
		}
		if(event.client_ParentClassPath.equalsIgnoreCase("ec3.client.gui.GuiMagicalAssembler") && event.buttonID == 1)
		{
			TileMagicalAssembler assembler = (TileMagicalAssembler) event.presser.worldObj.getTileEntity(event.x, event.y, event.z);
    		assembler.actualRecipes.clear();
    		assembler.allRecipes.clear();
    		assembler.currentCraft = null;
    		assembler.currentRecipe = -1;
    		assembler.requiredItemsToCraft.clear();
    		assembler.currentSelectedOne = null;
			for(int i = 0; i < assembler.mirrors.size(); ++i)
			{
				Coord3D coord = assembler.mirrors.get(i);
				TileEntity tile = assembler.getWorldObj().getTileEntity((int)coord.x, (int)coord.y, (int)coord.z);
				if(tile == null || !(tile instanceof TileMagicalMirror))
					assembler.mirrors.remove(i);
				else
					((TileMagicalMirror) tile).end(assembler);
			}
    		if(assembler.getStackInSlot(1) != null)
    			assembler.formCraftList(assembler.getStackInSlot(1));
		}
		if(event.client_ParentClassPath.equalsIgnoreCase("ec3.client.gui.GuiPlayerPentacle"))
		{
			TilePlayerPentacle pentacle = (TilePlayerPentacle) event.presser.worldObj.getTileEntity(event.x, event.y, event.z);
			
			int reqToConsume = ECUtils.getData(event.presser).getEffects().get(event.buttonID).getStickiness();
			if((event.additionalData[0].fieldName.equalsIgnoreCase("isCreative") && event.additionalData[0].fieldValue.equalsIgnoreCase("true")) || pentacle.consumeEnderstarEnergy(reqToConsume))
			{
				ECUtils.getData(event.presser).getEffects().remove(event.buttonID);
				ECUtils.requestSync(event.presser);
			}
		}
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@SubscribeEvent
	public void onBlockSpeedCheck(PlayerEvent.BreakSpeed event)
	{
		//I usually do not comment my code.
		//However I think that this is an exception
		//I do not know why it took me so long to figure all this out
		//But this code took me a good 4-6 hours
		
		//Getting the current player
		EntityPlayer p = event.entityPlayer;
		
		//Getting the player's tool in hand
		ItemStack currentTool = p.getCurrentEquippedItem();
		
		//A usual check - we are on a server, the tool exists and the tool is a Wind type of tool.
		if(!event.entityPlayer.worldObj.isRemote && currentTool != null && (currentTool.getItem() instanceof ItemWindAxe || currentTool.getItem() instanceof ItemWindPickaxe || currentTool.getItem() instanceof ItemWindShovel || currentTool.getItem() instanceof ItemWindHoe || currentTool.getItem() instanceof ItemWindSword))
		{
    		//Setting the current tool type via
    		//Hard-coded tool check. Hmmm....
    		//TODO Change to Item.getToolClasses() later
        	String currentToolClass = "";
        	if(currentTool.getItem() instanceof ItemPickaxe)
        		currentToolClass = "pickaxe";
        	if(currentTool.getItem() instanceof ItemAxe)
        		currentToolClass = "axe";
        	if(currentTool.getItem() instanceof ItemSpade)
        		currentToolClass = "shovel";
        	if(currentTool.getItem() instanceof ItemHoe)
        		currentToolClass = "hoe";
        	if(currentTool.getItem() instanceof ItemSword)
        		currentToolClass = "sword";
        	
			//If the player is using an impropper tool, or the current stack is a sword
	        if (!ForgeHooks.isToolEffective(currentTool, event.block, event.metadata) || currentToolClass.equalsIgnoreCase("sword"))
	        {
	        	//Getting the propper tool type
	        	String clazz = event.block.getHarvestTool(event.metadata);
	        	if(clazz == null || clazz.isEmpty())
	        	{
	        		//If the block and it's materials exist. Who knows, that mod calles this event...
	        		if(event.block!=null && event.block.getMaterial()!=null)
	        		{
	        			//Bunch of material checks to make sure lazy modders like me will still get their blocks recognized properly
	        			if(event.block.getMaterial()==Material.anvil)
	        				clazz = "pickaxe";
	        			if(event.block.getMaterial()==Material.cactus)
	        				clazz = "axe";
	        			if(event.block.getMaterial()==Material.clay)
	        				clazz = "shovel";
	        			if(event.block.getMaterial()==Material.coral)
	        				clazz = "pickaxe";
	        			if(event.block.getMaterial()==Material.craftedSnow)
	        				clazz = "shovel";
	        			if(event.block.getMaterial()==Material.glass)
	        				clazz = "pickaxe";
	        			if(event.block.getMaterial()==Material.gourd)
	        				clazz = "axe";
	        			if(event.block.getMaterial()==Material.grass)
	        				clazz = "shovel";
	        			if(event.block.getMaterial()==Material.ground)
	        				clazz = "shovel";
	        			if(event.block.getMaterial()==Material.ice)
	        				clazz = "pickaxe";
	        			if(event.block.getMaterial()==Material.iron)
	        				clazz = "pickaxe";
	        			if(event.block.getMaterial()==Material.packedIce)
	        				clazz = "pickaxe";
	        			if(event.block.getMaterial()==Material.piston)
	        				clazz = "axe";
	        			if(event.block.getMaterial()==Material.rock)
	        				clazz = "pickaxe";
	        			if(event.block.getMaterial()==Material.sand)
	        				clazz = "shovel";
	        			if(event.block.getMaterial()==Material.snow)
	        				clazz = "shovel";
	        			if(event.block.getMaterial()==Material.sponge)
	        				clazz = "shovel";
	        			if(event.block.getMaterial()==Material.tnt)
	        				clazz = "sword";
	        			if(event.block.getMaterial()==Material.web)
	        				clazz = "sword";
	        			if(event.block.getMaterial()==Material.wood)
	        				clazz = "axe";
	        		}
	        	}
	        	//If the propper tool type exists
	        	//Also a dumb sword check
	        	if(clazz != null && !clazz.isEmpty() && clazz != currentToolClass)
	        	{
		        	//Initializing 2 blank NBTs
		        	
		        	//This one will store all the info about the current tool - ID, Meta, Amount and things like enchantments.
		        	//Should NEVER store other stored items tags!!!
		        	NBTTagCompound toolTag = new NBTTagCompound();
		        	
		        	//And this one will ONLY store tags of other tools
		        	NBTTagCompound genericTag = new NBTTagCompound();
		        	
		        	//Filling in the first tag. At this point it is a huge mess of everyting, so we need to clean it up.
		        	currentTool.writeToNBT(toolTag);
		        	
		        	//If our tag actually stores anything apart from ID, Amount and Meta
		        	if(toolTag.hasKey("tag"))
		        	{
		        		//copy the generic tag
		        		genericTag = (NBTTagCompound) toolTag.getCompoundTag("tag").copy();
		        		
		        		//Nullify all other tools data if any
		        		toolTag.getCompoundTag("tag").removeTag("pickaxe");
		        		toolTag.getCompoundTag("tag").removeTag("axe");
		        		toolTag.getCompoundTag("tag").removeTag("shovel");
		        		toolTag.getCompoundTag("tag").removeTag("hoe");
		        		toolTag.getCompoundTag("tag").removeTag("sword");
		        		
		        		//Safety check, should not happen, but shit happens.
		        		toolTag.getCompoundTag("tag").removeTag("tag");
		        	}
		        	
		        	//Now, since our genericTag is full of every data types, like Enchantments, and we do not want that to happen we need to clean it up.
		        	//I'm doing it by getting every tag there is, wrighting it down into an ArrayList, that way we are escaping the nasty ConurrentModification
		        	//Removing all the necessary data keys from the list, and then removing every element, that maches any of the keys left in the list.
		        	//That way I make sure, that there is nothing, apart from the tool NBT in the genericTag
		        	
		        	//All KEYS of the tags in the genericData
		        	Set tags = genericTag.func_150296_c();
		        	
		        	//Blank list
		        	List tagKeyLst = new ArrayList();
		        	
		        	//Getting the Iterator, since this is a Set, and we do not want the StackOverflow exception
		        	Iterator $i = tags.iterator();
		        	
		        	//Iterating through each KEY and putting it into our blank list
		        	while($i.hasNext())
		        	{
		        		tagKeyLst.add($i.next());
		        	}
		        	
		        	//Removing all the tool tags from the list.
		        	//Before removing making sure, that that element actually exists.
		        	if(tagKeyLst.indexOf("pickaxe") != -1)
		        		tagKeyLst.remove("pickaxe");
		        	if(tagKeyLst.indexOf("axe") != -1)
		        		tagKeyLst.remove("axe");
		        	if(tagKeyLst.indexOf("shovel") != -1)
		        		tagKeyLst.remove("shovel");
		        	if(tagKeyLst.indexOf("hoe") != -1)
		        		tagKeyLst.remove("hoe");
		        	if(tagKeyLst.indexOf("sword") != -1)
		        		tagKeyLst.remove("sword");
		        	
		        	//Iterating through our list, which should have everything, except for our tools in it.
		        	//In other words we worked as a gc - we've collected everything we DO NOT need, and now we are deleting it.
		        	for(int i = 0; i < tagKeyLst.size(); ++i)
		        	{
		        		//This should not be the case, but Mojang does whatever they want with their code, so a safety check might come into play later, if the code gets changed.
		        		if(tagKeyLst.get(i) instanceof String)
		        			genericTag.removeTag((String) tagKeyLst.get(i));
		        	}
		        	
		        	//Initializing our blank ItemStack, that will replace the current one.
		        	ItemStack efficent = null;
		        	
		        	//If the tool we are switching to was found in the tag, and, therefore, was stored
		        	if(genericTag.hasKey(clazz))
		        	{
		        		//Preparing the generic NBT
		        		NBTTagCompound loadFrom = (NBTTagCompound) genericTag.getCompoundTag(clazz).copy();
		        		//And removing that tool from NBT = we do not want NBT duplicates.
		        		genericTag.removeTag(clazz);
		        		//Loading our tool from the NBT
		        		efficent = ItemStack.loadItemStackFromNBT(loadFrom);
		        		//Well... totally unnecessary, but I was tired by that point, and acted like this was a C type of language. Well, saves gc some bytes, I guess.
		        		loadFrom = null;
		        	}else //Or, if the tool was not found, we are creating a new blank one
		        	{
		        		//Another hard-coded tool initialization.
		        		if(clazz.equalsIgnoreCase("pickaxe"))
		        			efficent = new ItemStack(ItemsCore.wind_elemental_pick,1,currentTool.getItemDamage());
		        		if(clazz.equalsIgnoreCase("shovel"))
		        			efficent = new ItemStack(ItemsCore.wind_elemental_shovel,1,currentTool.getItemDamage());
		        		if(clazz.equalsIgnoreCase("hoe")) //Will that ever happen?
		        			efficent = new ItemStack(ItemsCore.wind_elemental_hoe,1,currentTool.getItemDamage());
		        		if(clazz.equalsIgnoreCase("sword"))
		        			efficent = new ItemStack(ItemsCore.wind_elemental_sword,1,currentTool.getItemDamage());
		        		if(clazz.equalsIgnoreCase("axe"))
		        			efficent = new ItemStack(ItemsCore.wind_elemental_axe,1,currentTool.getItemDamage());
		        	}
		        	
		        	//If our tool got replaced. Also, if our tool is a valid item. Should be valid all the times, but...
		        	if(efficent != null && efficent.getItem() != null)
		        	{
		        		//Getting the tag of our new-made item.
		        		//IMPORTANT! This is already a 'tag' tag. I mean, println the efficent's NBT, and you will see
		        		//{id:6666s,Damage:0s,Count:1b,tag:{}}
		        		//That tag{} == this tag!
		        		
		        		//Also, the most creative name ever award goes to me, I guess.
			        	NBTTagCompound anotherTag = MiscUtils.getStackTag(efficent);
			        	
			        	//Setting our tags, if needed
			        	//I mean, saving our tools into the NBT data! Yea, that sounds better!
			        	if(genericTag.hasKey("pickaxe"))
			        		anotherTag.setTag("pickaxe", genericTag.getTag("pickaxe"));
			        	if(genericTag.hasKey("axe"))
			        		anotherTag.setTag("axe", genericTag.getTag("axe"));
			        	if(genericTag.hasKey("shovel"))
			        		anotherTag.setTag("shovel", genericTag.getTag("shovel"));
			        	if(genericTag.hasKey("hoe"))
			        		anotherTag.setTag("hoe", genericTag.getTag("hoe"));
			        	if(genericTag.hasKey("sword"))
			        		anotherTag.setTag("sword", genericTag.getTag("sword"));
			        	
			        	//Also setting our current tool into the NBT.
			        	//I mean saving, sure.
			        	if(toolTag != null)
			        		anotherTag.setTag(currentToolClass, toolTag);
			        	
			        	//And giving our player the new, awesome tool!
			        	event.entityPlayer.inventory.setInventorySlotContents(event.entityPlayer.inventory.currentItem, efficent);
		        	}
		        }
	        }
		}
	}
	
	@SubscribeEvent
	public void worldTick(WorldTickEvent event)
	{

		if(event.phase == Phase.END && event.world != null && event.world.provider.dimensionId == 0)
		{
			List<Object> hashTableIteratorLst = new ArrayList<Object>();
			
			Enumeration<Object> e = ECUtils.inPortalTime.keys();
			while(e.hasMoreElements())
			{
				Object o = e.nextElement();
				hashTableIteratorLst.add(o);
			}
			
			for(int i = 0; i < hashTableIteratorLst.size(); ++i)
			{
				int k = ECUtils.inPortalTime.get(hashTableIteratorLst.get(i));
				if(hashTableIteratorLst.get(i) instanceof Entity)
				{
					Entity en = (Entity) hashTableIteratorLst.get(i);
					if(!en.isInsideOfMaterial(Material.portal))
						k -= 4;
				}
				--k;
				if(k <= 0)
				{
					ECUtils.inPortalTime.remove(hashTableIteratorLst.get(i));
				}else
				{
					ECUtils.inPortalTime.put(hashTableIteratorLst.get(i), k);
				}
			}
			
			hashTableIteratorLst = null;
		}
		
		if(!event.world.isRemote)
		{
			String worldEvent = DummyDataUtils.getCustomDataForMod("essentialcraft", "worldEvent");
			if(worldEvent != null)
			{
				if(event.phase == Phase.END)
				{
					if(WorldEventLibrary.currentEvent == null)
					{
						worldEvent = "no data";
					}else
					{
						worldEvent = new DummyData(WorldEventLibrary.currentEvent.getEventID(),WorldEventLibrary.currentEventDuration).toString();
					}
					ECUtils.endEvent(event.world);
					ECUtils.newWorldEvent(event.world);
					DummyDataUtils.writeCustomDataForMod("essentialcraft", "worldEvent", worldEvent);
				}else
				{
					if(WorldEventLibrary.currentEvent == null && !worldEvent.equals("no data"))
					{
						DummyData[] data = DataStorage.parseData(worldEvent);
						WorldEventLibrary.currentEvent = WorldEventLibrary.gettEffectByID(data[0].fieldName, event.world);
						WorldEventLibrary.currentEventDuration = Integer.parseInt(data[0].fieldValue);
					}
					if(WorldEventLibrary.currentEvent != null)
						WorldEventLibrary.currentEvent.worldTick(event.world, WorldEventLibrary.currentEventDuration);
					DummyDataUtils.writeCustomDataForMod("essentialcraft", "worldEvent", worldEvent);
				}
			}
		}
	}
	
	@SideOnly(Side.CLIENT)
    @SubscribeEvent
    public void onAimZoom(FOVUpdateEvent event)
    {
    	EntityPlayer p = event.entity;
    	if(p.getCurrentEquippedItem() != null && p.getCurrentEquippedItem().getItem() instanceof ItemGun && p.isSneaking() && p.getCurrentEquippedItem().getTagCompound() != null && p.getCurrentEquippedItem().getTagCompound().hasKey("scope"))
    	{
    		String id = p.getCurrentEquippedItem().getTagCompound().getString("scope");
    		ScopeMaterial s = null;
    		if(ItemGun.class.cast(p.getCurrentEquippedItem().getItem()).gunType.equalsIgnoreCase("sniper"))
    		{
	    		for(int i = 0; i < GunRegistry.scopeMaterialsSniper.size(); ++i)
	    		{
	    			if(GunRegistry.scopeMaterialsSniper.get(i).id.equalsIgnoreCase(id))
	    			{
	    				s = GunRegistry.scopeMaterialsSniper.get(i);
	    				break;
	    			}
	    		}
    		}else
    		{
	    		for(int i = 0; i < GunRegistry.scopeMaterials.size(); ++i)
	    		{
	    			if(GunRegistry.scopeMaterials.get(i).id.equalsIgnoreCase(id))
	    			{
	    				s = GunRegistry.scopeMaterials.get(i);
	    				break;
	    			}
	    		}
    		}
    		if(s != null)
    		{
    			GunType g = null;
    			if(ItemGun.class.cast(p.getCurrentEquippedItem().getItem()).gunType.equalsIgnoreCase("sniper"))
    				g = GunType.SNIPER;
    			if(ItemGun.class.cast(p.getCurrentEquippedItem().getItem()).gunType.equalsIgnoreCase("pistol"))
    				g = GunType.PISTOL;
    			if(ItemGun.class.cast(p.getCurrentEquippedItem().getItem()).gunType.equalsIgnoreCase("rifle"))
    				g = GunType.RIFLE;
    			if(ItemGun.class.cast(p.getCurrentEquippedItem().getItem()).gunType.equalsIgnoreCase("gatling"))
    				g = GunType.GATLING;
    			if(g != null)
    			{
    				ArrayList<DummyData> ls = s.materialData.get(g);
    				for(int i = 0; i < ls.size(); ++i)
    				{
    					DummyData dt = ls.get(i);
    					if(dt != null && dt.fieldName.equalsIgnoreCase("scope.zoom"))
    					{
    						float value = Float.parseFloat(dt.fieldValue)*1F;
    						event.newfov = 1/value;
    						return;
    					}
    				}
    			}
    		}
    	}
    }
	
	@SubscribeEvent(priority=EventPriority.HIGHEST)
	public void denyFlowerGen(DecorateBiomeEvent.Decorate event)
	{
		if(event.type == EventType.FLOWERS)
		{
			if(event.world.provider.dimensionId == Config.dimensionID)
			{
				event.setResult(Result.DENY);
			}
		}
	}
	
	@SideOnly(Side.CLIENT)
	@SubscribeEvent
	public void getBiomeWaterColor(BiomeEvent.GetWaterColor event)
	{
		EntityPlayer player = EssentialCraftCore.proxy.getClientPlayer();
		if(player != null)
		{
			int dimID = player.dimension;
			if(dimID == Config.dimensionID)
			{
				event.newColor = 0xff6a58;
			}
		}
	}
	
	@SideOnly(Side.CLIENT)
	@SubscribeEvent
	public void clientTick(TickEvent.ClientTickEvent event)
	{
		if(event.phase == TickEvent.Phase.START)
		{

		}else
		{
			if(lastTickLanguage != null && !lastTickLanguage.isEmpty())
			{
				if(!lastTickLanguage.equalsIgnoreCase(FMLClientHandler.instance().getCurrentLanguage()))
				{
					ResearchRegistry.init();
					if(GuiResearchBook.currentCategory != null && GuiResearchBook.currentDiscovery != null)
					{
						String id = GuiResearchBook.currentDiscovery.id;
						for(int i = 0; i < GuiResearchBook.currentCategory.discoveries.size(); ++i)
						{
							if(GuiResearchBook.currentCategory.discoveries.get(i).id.equals(id))
							{
								GuiResearchBook.currentDiscovery=GuiResearchBook.currentCategory.discoveries.get(i);
								break;
							}
						}
					}
				}
			}
			lastTickLanguage = FMLClientHandler.instance().getCurrentLanguage();
		}
	}
	

	
	@SideOnly(Side.CLIENT)
	@SubscribeEvent
	public void getBiomeFolliageColor(BiomeEvent.GetFoliageColor event)
	{
		EntityPlayer player = EssentialCraftCore.proxy.getClientPlayer();
		if(player != null)
		{
			int dimID = player.dimension;
			if(dimID == Config.dimensionID)
			{
				if(!(event.biome instanceof BiomeGenCorruption_Chaos) && !(event.biome instanceof BiomeGenCorruption_Frozen) && !(event.biome instanceof BiomeGenCorruption_Magic))
				{
					event.newColor = 0x886a58;
				}
			}
		}
	}
	
	@SideOnly(Side.CLIENT)
	@SubscribeEvent
	public void getBiomeGrassColor(BiomeEvent.GetGrassColor event)
	{
		EntityPlayer player = EssentialCraftCore.proxy.getClientPlayer();
		if(player != null)
		{
			int dimID = player.dimension;
			if(dimID == Config.dimensionID)
			{
				if(!(event.biome instanceof BiomeGenCorruption_Chaos) && !(event.biome instanceof BiomeGenCorruption_Frozen) && !(event.biome instanceof BiomeGenCorruption_Magic))
				{
					event.newColor = 0x886a58;
				}
			}
		}
	}
	
	@SubscribeEvent
	public void onKillEntity(LivingDeathEvent event)
	{
		EntityLivingBase base = event.entityLiving;
		DamageSource src = event.source;
		if(src != null && src.getSourceOfDamage() != null)
		{
			Entity e = src.getSourceOfDamage();
			if(e instanceof EntityPlayer && !(e instanceof FakePlayer))
			{
				EntityPlayer player = (EntityPlayer) e;
				int addedEnergy = 0;
				if(base instanceof EntityPlayer && !(base instanceof FakePlayer))
				{
					int currentEnergy_int = ECUtils.getData((EntityPlayer) base).getPlayerUBMRU();
					addedEnergy += currentEnergy_int;
				}else
				{
					float maxHp = base.getMaxHealth();
					ItemStack helmet = player.inventory.armorInventory[3];
					if(helmet != null && helmet.getItem() == ItemsCore.magicArmorItems[4])
					{
						player.getFoodStats().addStats((int) (maxHp/10F), 1F);
					}
					addedEnergy += (20+(MathUtils.randomFloat(e.worldObj.rand)*10))*maxHp;
				}
				if(BaublesApi.getBaubles(player) != null && addedEnergy > 0)
				for(int i = 0; i < 4; ++i)
				{
					ItemStack bStk = BaublesApi.getBaubles(player).getStackInSlot(i);
					if(bStk != null)
					{
						if(bStk.getItem() != null)
						{
							if(bStk.getItem() instanceof IUBMRUGainModifier)
							{
								//G-Mod?
								IUBMRUGainModifier gmod = (IUBMRUGainModifier) bStk.getItem();
								addedEnergy = MathHelper.floor_float(gmod.getModifiedValue(addedEnergy, bStk, player.worldObj.rand,player));
							}
						}
					}
				}
				int currentEnergy_int = ECUtils.getData(player).getPlayerUBMRU();
				currentEnergy_int += addedEnergy;
				ECUtils.getData(player).modifyUBMRU(currentEnergy_int);
				boolean canDropEmber = false;
	        	IInventory b = BaublesApi.getBaubles(player);
	        	if(b != null)
	        	{
	        		for(int i = 0; i < b.getSizeInventory(); ++i)
	        		{
	        			ItemStack is = b.getStackInSlot(i);
	        			if(is != null && is.getItem() != null && is.getItem() instanceof BaublesModifier && is.getItemDamage() == 12)
	        				canDropEmber = true;
	        		}
	        	}
	        	if(canDropEmber)
	        	{
	        		if(player.worldObj.rand.nextFloat() < 0.05F)
	        		{
	        			ItemStack emberStack = new ItemStack(ItemsCore.ember,1,player.worldObj.rand.nextInt(8));
	        			EntityItem emberItem = new EntityItem(base.worldObj, base.posX, base.posY, base.posZ, emberStack);
	        			if(!base.worldObj.isRemote)
	        				base.worldObj.spawnEntityInWorld(emberItem);
	        		}
	        	}
			}
		}
		if(base instanceof EntityPlayer)
		{
			if(!(base instanceof FakePlayer))
				ECUtils.getData((EntityPlayer) base).modifyUBMRU(0);
		}
	}

	@SubscribeEvent
	public void onPacketRecieved(DummyEvent_OnPacketRecieved event)
	{
		
		DummyData[] packetData = DataStorage.parseData(event.recievedData);
		if(packetData != null && packetData.length > 0)
		{
			try 
			{
				DummyData modData = packetData[0];
				if(modData.fieldName.equalsIgnoreCase("mod") && modData.fieldValue.equalsIgnoreCase("ec3.particle.item"))
				{
					double sX = Double.parseDouble(packetData[1].fieldValue);
					double sY = Double.parseDouble(packetData[2].fieldValue);
					double sZ = Double.parseDouble(packetData[3].fieldValue);
					double mX = Double.parseDouble(packetData[4].fieldValue);
					double mY = Double.parseDouble(packetData[5].fieldValue);
					double mZ = Double.parseDouble(packetData[6].fieldValue);
					EssentialCraftCore.proxy.ItemFX(sX,sY,sZ,mX,mY,mZ);
				}else
				{
					if(modData.fieldName.equalsIgnoreCase("mod") && modData.fieldValue.equalsIgnoreCase("ec3.item.wings"))
					{
						double sX = Double.parseDouble(packetData[1].fieldValue);
						double sY = Double.parseDouble(packetData[2].fieldValue);
						double sZ = Double.parseDouble(packetData[3].fieldValue);
						EntityPlayer player = MinecraftServer.getServer().getConfigurationManager().func_152612_a(packetData[4].fieldValue);
						player.setPosition(sX, sY, sZ);
						ItemStack wings = BaublesApi.getBaubles(player).getStackInSlot(3);
						if(wings != null)
						{
							ItemMagicalWings w = (ItemMagicalWings) wings.getItem();
							if(ECUtils.tryToDecreaseMRUInStorage(player, -1) || w.setMRU(wings, -1));
						}
					}
					if(modData.fieldName.equalsIgnoreCase("mod") && modData.fieldValue.equalsIgnoreCase("ec3.player.position"))
					{
						EssentialCraftCore.proxy.handlePositionChangePacket(packetData);
					}else
						if(modData.fieldName.equalsIgnoreCase("mod") && modData.fieldValue.equalsIgnoreCase("ec3.sound"))
						{
							EssentialCraftCore.proxy.handleSoundPlay(packetData);
						}
				}
			}catch(Exception e)
			{
				e.printStackTrace();
				return;
			}
		}
	}
}
