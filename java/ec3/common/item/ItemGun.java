package ec3.common.item;

import java.util.List;
import java.util.Random;

import org.lwjgl.input.Keyboard;

import DummyCore.Utils.DummyData;
import DummyCore.Utils.MathUtils;
import DummyCore.Utils.MiscUtils;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ec3.api.GunRegistry;
import ec3.api.GunRegistry.GunMaterial;
import ec3.api.GunRegistry.GunType;
import ec3.api.GunRegistry.LenseMaterial;
import ec3.api.GunRegistry.ScopeMaterial;
import ec3.common.entity.EntityMRURay;
import ec3.common.mod.EssentialCraftCore;
import ec3.utils.common.ECUtils;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.util.StatCollector;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

public class ItemGun extends ItemStoresMRUInNBT
{
	Random rnd = new Random();
	public IIcon[] baseIcons = new IIcon[GunRegistry.gunMaterials.size()];
	public IIcon[] handleIcons = new IIcon[GunRegistry.gunMaterials.size()];
	public IIcon[] deviceIcons = new IIcon[GunRegistry.gunMaterials.size()];
	public IIcon[] lenseIcons = new IIcon[GunRegistry.lenseMaterials.size()];
	public IIcon[] scopeIcons = new IIcon[GunRegistry.scopeMaterials.size()];
	
	public String gunType;
	
    @SideOnly(Side.CLIENT)
    public boolean requiresMultipleRenderPasses()
    {
        return true;
    }
	
	public ItemGun(String s)
	{
		super();
		this.setHasSubtypes(true);
		this.setMaxStackSize(1);
		this.setFull3D();
		this.setMaxDamage(0);
		gunType = s;
	}
	
	@Override
	public void onUpdate(ItemStack itemStack, World world, Entity entity, int indexInInventory, boolean isCurrentItem)
	{
		if(isCurrentItem && this.gunType.equals("gatling"))
		{
			if(entity instanceof EntityLivingBase)
			{
				EntityLivingBase.class.cast(entity).addPotionEffect(new PotionEffect(Potion.moveSlowdown.id,3,3,true));
			}
		}
 		if(MiscUtils.getStackTag(itemStack).hasKey("cool"))
		{
 			MiscUtils.getStackTag(itemStack).setFloat("cool", MiscUtils.getStackTag(itemStack).getFloat("cool")-1);
 			if(MiscUtils.getStackTag(itemStack).getFloat("cool") <= 0)
 				MiscUtils.getStackTag(itemStack).removeTag("cool");
		}
 		if(MiscUtils.getStackTag(itemStack).hasKey("gunShots") && isCurrentItem)
		{
 			float current = MiscUtils.getStackTag(itemStack).getFloat("gunShots");
 			float max = MiscUtils.getStackTag(itemStack).getCompoundTag("stats").getFloat("shots");
 			if(current+1 >= max)
 			{
 				Vec3 look = entity.getLookVec();
 				look.rotateAroundY(-0.5F);
 				world.spawnParticle("smoke", entity.posX+look.xCoord, entity.posY-0.5D+look.yCoord, entity.posZ+look.zCoord, 0, 0, 0);
 			}
		}
		super.onUpdate(itemStack, world, entity, indexInInventory, isCurrentItem);
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
    public void addInformation(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, List par3List, boolean par4) 
    {
		if(MiscUtils.getStackTag(par1ItemStack).hasKey("stats") && Keyboard.isKeyDown(Keyboard.KEY_LSHIFT))
		{
			NBTTagCompound stats = MiscUtils.getStackTag(par1ItemStack).getCompoundTag("stats");
			par3List.add(StatCollector.translateToLocal("ec3.gun.txt.damage")+" "+MathHelper.floor_float(stats.getFloat("damage")));
			par3List.add(StatCollector.translateToLocal("ec3.gun.txt.durability")+" "+MathHelper.floor_float(stats.getFloat("durability")));
			par3List.add(StatCollector.translateToLocal("ec3.gun.txt.reload")+" "+MathHelper.floor_float(stats.getFloat("reload")));
			par3List.add(StatCollector.translateToLocal("ec3.gun.txt.knockback")+" "+MathHelper.floor_float(stats.getFloat("knockback")));
			par3List.add(StatCollector.translateToLocal("ec3.gun.txt.speed")+" "+MathHelper.floor_float(stats.getFloat("speed")));
			par3List.add(StatCollector.translateToLocal("ec3.gun.txt.spread")+" "+MathHelper.floor_float(stats.getFloat("spread")));
			par3List.add(StatCollector.translateToLocal("ec3.gun.txt.shots")+" "+MathHelper.floor_float(stats.getFloat("shots")));
			par3List.add(StatCollector.translateToLocal("ec3.gun.txt.zoom")+" "+MathHelper.floor_float(stats.getFloat("zoom")));
			par3List.add(StatCollector.translateToLocal("ec3.gun.txt.balance_"+""+MathHelper.floor_float(stats.getFloat("balance"))));
		}else if(MiscUtils.getStackTag(par1ItemStack).hasKey("stats"))
		{
			par3List.add(EnumChatFormatting.BLUE+""+EnumChatFormatting.ITALIC+StatCollector.translateToLocal("ec3.txt.viewInfoHotkey"));
		}
		super.addInformation(par1ItemStack, par2EntityPlayer, par3List, par4);
    }

    @SuppressWarnings("rawtypes")
	@Override
    public void getSubItems(Item par1, CreativeTabs par2CreativeTabs, List par3List)
    {
    	super.getSubItems(par1, par2CreativeTabs, par3List);
    }
    
    public ItemStack onItemRightClick(ItemStack gun, World w, EntityPlayer p)
    {
    	if(!gun.getTagCompound().hasKey("base") && !w.isRemote)
    	{
    		createRandomGun(gun);
    		return gun;
    	}
    	if(p.isUsingItem())
    		return gun;
    	if(this.gunType.equalsIgnoreCase("rifle") || this.gunType.equalsIgnoreCase("gatling"))
    	{
    		p.setItemInUse(gun, this.getMaxItemUseDuration(gun));
    	}
    	else
    	{
	    	if(gun.getTagCompound().hasKey("base"))
	    	{
	    		float balance = 0;
	    		if(gun.getTagCompound().hasKey("lense"))
	    		{
	    			String lenseID = gun.getTagCompound().getString("lense");
	    			if(lenseID.equals("chaos"))
	    				balance = 1;
	    			if(lenseID.equals("frozen"))
	    				balance = 2;
	    			if(lenseID.equals("pure"))
	    				balance = 3;
	    			if(lenseID.equals("shade"))
	    				balance = 4;
	    		}
	    		if(MiscUtils.getStackTag(gun).hasKey("stats"))
	    		{
	    			NBTTagCompound stats = MiscUtils.getStackTag(gun).getCompoundTag("stats");
	    			if(!MiscUtils.getStackTag(gun).hasKey("gunDamage"))
	    			{
	    				MiscUtils.getStackTag(gun).setFloat("gunDamage", 0);
	    			}
	    			if(!MiscUtils.getStackTag(gun).hasKey("gunShots"))
	    			{
	    				MiscUtils.getStackTag(gun).setFloat("gunShots", 0);
	    			}
	    			if(MiscUtils.getStackTag(gun).hasKey("cool"))
	    			{
	    				return gun;
	    			}
	    			if(MiscUtils.getStackTag(gun).getFloat("gunShots")+1 <= stats.getFloat("shots"))
	    				MiscUtils.getStackTag(gun).setFloat("gunShots", MiscUtils.getStackTag(gun).getFloat("gunShots")+1);
	    			else
	    			{
	    				p.setItemInUse(gun, MathHelper.floor_float(stats.getFloat("reload")*20));
	    				return gun;
	    			}
	            	if(ECUtils.tryToDecreaseMRUInStorage(p, (int) -stats.getFloat("damage")*10) || this.setMRU(gun, (int) -stats.getFloat("damage")*10))
	            	{
		    			if(MiscUtils.getStackTag(gun).getFloat("gunDamage")+1 <= stats.getFloat("durability"))
		    				MiscUtils.getStackTag(gun).setFloat("gunDamage", MiscUtils.getStackTag(gun).getFloat("gunDamage")+1);
		    			else
		    			{
		    				if(!w.isRemote && w.rand.nextFloat() <= 0.25F)
		    				{
		    					w.playSound(p.posX, p.posY, p.posZ, "random.break", 1, 1, false);
		    					MiscUtils.getStackTag(gun).setFloat("gunShots", stats.getFloat("shots"));
		    				}
		    			}

		    			
		    			MiscUtils.getStackTag(gun).setFloat("cool", stats.getFloat("speed")*2);
		    			w.playSound(p.posX, p.posY, p.posZ, "essentialcraft:sound.beam", 0.1F+stats.getFloat("damage")/100, 2-stats.getFloat("damage")/50, false);
		    			EntityMRURay ray = new EntityMRURay(w,p,stats.getFloat("damage"),stats.getFloat("spread")/2,balance);
		        		if(!w.isRemote)
		        			w.spawnEntityInWorld(ray);
		        		p.rotationPitch -= stats.getFloat("knockback");
		    		}
	    		}
	    	}
    	}
        return gun;
    }
    
    public static void createRandomGun(ItemStack gun)
    {
    	Random rand = new Random();
    	NBTTagCompound tag = MiscUtils.getStackTag(gun);
    	tag.setString("base", GunRegistry.gunMaterials.get(rand.nextInt(GunRegistry.gunMaterials.size())).id);
    	tag.setString("device", GunRegistry.gunMaterials.get(rand.nextInt(GunRegistry.gunMaterials.size())).id);
    	tag.setString("handle", GunRegistry.gunMaterials.get(rand.nextInt(GunRegistry.gunMaterials.size())).id);
    	tag.setString("lense", GunRegistry.lenseMaterials.get(rand.nextInt(GunRegistry.lenseMaterials.size())).id);
    	ItemGun g = (ItemGun) gun.getItem();
    	if(g.gunType == "sniper")
    	{
    		tag.setString("scope", GunRegistry.scopeMaterialsSniper.get(rand.nextInt(GunRegistry.scopeMaterialsSniper.size())).id);
    	}else
        	if(g.gunType == "gatling")
        	{
        		
        	}else
        	{
        		tag.setString("scope", GunRegistry.scopeMaterials.get(rand.nextInt(GunRegistry.scopeMaterials.size())).id);
        	}
    	gun.setTagCompound(tag);
    	calculateGunStats(gun);
    }
    
    public static void calculateGunStats(ItemStack gun)
    {
    	NBTTagCompound gunTag = MiscUtils.getStackTag(gun);
    	if(gunTag.hasKey("stats"))
    		gunTag.removeTag("stats");
    	
    	NBTTagCompound stats = new NBTTagCompound();
    	GunMaterial base = null;
    	GunMaterial handle = null;
    	GunMaterial device = null;
    	LenseMaterial lense = null;
    	ScopeMaterial scope = null;
    	ItemGun iGun = ItemGun.class.cast(gun.getItem());
    	
    	float damage = 0;
    	float durability = 0;
    	float reload = 0;
    	float knockback = 0;
    	float speed = 0;
    	float spread = 0;
    	float shots = 0;
    	float zoom = 0;
    	float balance = 0;
    	GunType gt = 
    			iGun.gunType.equalsIgnoreCase("pistol") ? GunType.PISTOL :
    			iGun.gunType.equalsIgnoreCase("rifle") ? GunType.RIFLE :
    			iGun.gunType.equalsIgnoreCase("sniper") ? GunType.SNIPER :
    			iGun.gunType.equalsIgnoreCase("gatling") ? GunType.GATLING :
    			GunType.PISTOL;
    	
    	
    	if(gunTag.hasKey("base"))
    	{
    		for(int i = 0; i < GunRegistry.gunMaterials.size(); ++i)
    		{
    			if(GunRegistry.gunMaterials.get(i).id.equalsIgnoreCase(gunTag.getString("base")))
    			{
    				base = GunRegistry.gunMaterials.get(i);
    			}
    		}
    	}
    	if(gunTag.hasKey("device"))
    	{
    		for(int i = 0; i < GunRegistry.gunMaterials.size(); ++i)
    		{
    			if(GunRegistry.gunMaterials.get(i).id.equalsIgnoreCase(gunTag.getString("device")))
    			{
    				device = GunRegistry.gunMaterials.get(i);
    			}
    		}
    	}
    	if(gunTag.hasKey("handle"))
    	{
    		for(int i = 0; i < GunRegistry.gunMaterials.size(); ++i)
    		{
    			if(GunRegistry.gunMaterials.get(i).id.equalsIgnoreCase(gunTag.getString("handle")))
    			{
    				handle = GunRegistry.gunMaterials.get(i);
    			}
    		}
    	}
    	if(gunTag.hasKey("lense"))
    	{
    		for(int i = 0; i < GunRegistry.lenseMaterials.size(); ++i)
    		{
    			if(GunRegistry.lenseMaterials.get(i).id.equalsIgnoreCase(gunTag.getString("lense")))
    			{
    				lense = GunRegistry.lenseMaterials.get(i);
    			}
    		}
    	}
    	if(gunTag.hasKey("scope"))
    	{
    		if(ItemGun.class.cast(gun.getItem()).gunType.equalsIgnoreCase("sniper"))
    		{
	    		for(int i = 0; i < GunRegistry.scopeMaterialsSniper.size(); ++i)
	    		{
	    			if(GunRegistry.scopeMaterialsSniper.get(i).id.equalsIgnoreCase(gunTag.getString("scope")))
	    			{
	    				scope = GunRegistry.scopeMaterialsSniper.get(i);
	    			}
	    		}
    		}else
    		{
    	    	for(int i = 0; i < GunRegistry.scopeMaterials.size(); ++i)
    	    	{
    	    		if(GunRegistry.scopeMaterials.get(i).id.equalsIgnoreCase(gunTag.getString("scope")))
    	    		{
    	    			scope = GunRegistry.scopeMaterials.get(i);
    	    		}
    	    	}
    	    }
    	}
    	if(base != null)
    	{
    		for(DummyData d : base.materialData.get(gt))
    		{
    			if(d.fieldName.equalsIgnoreCase("durability"))
    				durability += Float.parseFloat(d.fieldValue)/3;
    			if(d.fieldName.equalsIgnoreCase("damage"))
    				damage += Float.parseFloat(d.fieldValue)/3;
    			if(d.fieldName.equalsIgnoreCase("reload"))
    				reload += Float.parseFloat(d.fieldValue);  
    			if(d.fieldName.equalsIgnoreCase("knockback"))
    				knockback += Float.parseFloat(d.fieldValue)/3;  
    			if(d.fieldName.equalsIgnoreCase("spread"))
    				spread += Float.parseFloat(d.fieldValue)/3;  
    			if(d.fieldName.equalsIgnoreCase("speed"))
    				speed += Float.parseFloat(d.fieldValue);  
    			if(d.fieldName.equalsIgnoreCase("shots"))
    				shots += Float.parseFloat(d.fieldValue);  
    		}
    	}
    	if(handle != null)
    	{
    		for(DummyData d : handle.materialData.get(gt))
    		{
    			if(d.fieldName.equalsIgnoreCase("durability"))
    				durability += Float.parseFloat(d.fieldValue);
    			if(d.fieldName.equalsIgnoreCase("damage"))
    				damage += Float.parseFloat(d.fieldValue)/3;
    			if(d.fieldName.equalsIgnoreCase("reload"))
    				reload += Float.parseFloat(d.fieldValue)/3;  
    			if(d.fieldName.equalsIgnoreCase("knockback"))
    				knockback += Float.parseFloat(d.fieldValue);  
    			if(d.fieldName.equalsIgnoreCase("spread"))
    				spread += Float.parseFloat(d.fieldValue)/3;  
    			if(d.fieldName.equalsIgnoreCase("speed"))
    				speed += Float.parseFloat(d.fieldValue)/3;  
    			if(d.fieldName.equalsIgnoreCase("shots"))
    				shots += Float.parseFloat(d.fieldValue)/3;   
    		}
    	}
    	if(device != null)
    	{
    		for(DummyData d : device.materialData.get(gt))
    		{
    			if(d.fieldName.equalsIgnoreCase("durability"))
    				durability += Float.parseFloat(d.fieldValue)/3;
    			if(d.fieldName.equalsIgnoreCase("damage"))
    				damage += Float.parseFloat(d.fieldValue);
    			if(d.fieldName.equalsIgnoreCase("reload"))
    				reload += Float.parseFloat(d.fieldValue)/3;  
    			if(d.fieldName.equalsIgnoreCase("knockback"))
    				knockback += Float.parseFloat(d.fieldValue)/3;  
    			if(d.fieldName.equalsIgnoreCase("spread"))
    				spread += Float.parseFloat(d.fieldValue);  
    			if(d.fieldName.equalsIgnoreCase("speed"))
    				speed += Float.parseFloat(d.fieldValue)/3;  
    			if(d.fieldName.equalsIgnoreCase("shots"))
    				shots += Float.parseFloat(d.fieldValue)/3;   
    		}
    	}
    	if(lense != null && lense.materialData.get(gt) != null)
    	{
    		for(DummyData d : lense.materialData.get(gt))
    		{
    			if(d.fieldName.equalsIgnoreCase("durability"))
    				durability += Float.parseFloat(d.fieldValue)/3;
    			if(d.fieldName.equalsIgnoreCase("damage"))
    				damage += Float.parseFloat(d.fieldValue)/3;
    			if(d.fieldName.equalsIgnoreCase("reload"))
    				reload += Float.parseFloat(d.fieldValue)/3;  
    			if(d.fieldName.equalsIgnoreCase("knockback"))
    				knockback += Float.parseFloat(d.fieldValue)/3;  
    			if(d.fieldName.equalsIgnoreCase("spread"))
    				spread += Float.parseFloat(d.fieldValue)/3;  
    			if(d.fieldName.equalsIgnoreCase("speed"))
    				speed += Float.parseFloat(d.fieldValue)/3;  
    			if(d.fieldName.equalsIgnoreCase("shots"))
    				shots += Float.parseFloat(d.fieldValue)/3;   
    		}

    	}
    	if(lense != null)
    	{
    		if(lense.id.equalsIgnoreCase("chaos"))
    			balance = 1;
    		if(lense.id.equalsIgnoreCase("frozen"))
    			balance = 2;
    		if(lense.id.equalsIgnoreCase("pure"))
    			balance = 3;
    		if(lense.id.equalsIgnoreCase("shade"))
    			balance = 4;
    	}
    	
    	if(scope != null)
    	{
    		for(DummyData d : scope.materialData.get(gt))
    		{
    			if(d.fieldName.equalsIgnoreCase("durability"))
    				durability += Float.parseFloat(d.fieldValue)/3;
    			if(d.fieldName.equalsIgnoreCase("damage"))
    				damage += Float.parseFloat(d.fieldValue)/3;
    			if(d.fieldName.equalsIgnoreCase("reload"))
    				reload += Float.parseFloat(d.fieldValue)/3;  
    			if(d.fieldName.equalsIgnoreCase("knockback"))
    				knockback += Float.parseFloat(d.fieldValue)/3;  
    			if(d.fieldName.equalsIgnoreCase("spread"))
    				spread += Float.parseFloat(d.fieldValue)/3;  
    			if(d.fieldName.equalsIgnoreCase("speed"))
    				speed += Float.parseFloat(d.fieldValue)/3;  
    			if(d.fieldName.equalsIgnoreCase("shots"))
    				shots += Float.parseFloat(d.fieldValue)/3;   
    			if(d.fieldName.equalsIgnoreCase("scope.zoom"))
    				zoom += Float.parseFloat(d.fieldValue);   
    		}
    	}
    	
    	if(gt == GunType.GATLING)
    	{
    		speed = 0;
    		spread *= 4.5F;
    		shots *= 20;
    	}
    	
    	if(gt == GunType.RIFLE)
    	{
    		speed = 0;
    		spread *= 1.3F;
    		shots *= 6;
    	}
    	
    	if(gt == GunType.SNIPER)
    	{
    		speed *= 9;
    		spread /= 10;
    		damage *= 2;
    	}
    	
    	if(gt == GunType.PISTOL)
    	{
    		reload /= 2;
    	}
    	
    	stats.setFloat("damage", damage);
    	stats.setFloat("durability", durability);
    	stats.setFloat("reload", reload);
    	stats.setFloat("knockback", knockback);
    	stats.setFloat("speed", speed);
    	stats.setFloat("spread", spread);
    	stats.setFloat("shots", shots);
    	stats.setFloat("zoom", zoom);
    	stats.setFloat("balance", balance);
    	
    	gunTag.setTag("stats", stats);
    }
    
    public ItemStack onEaten(ItemStack gun, World w, EntityPlayer p)
    {
    	if(gun.hasTagCompound())
    	{
    		NBTTagCompound tag = MiscUtils.getStackTag(gun);
    		if(tag.hasKey("stats") && tag.getFloat("gunShots")+1 >= tag.getCompoundTag("stats").getFloat("shots"))
    		{
    			tag.setFloat("gunShots", 0);
    			tag.setFloat("cool", 60);
    		}
    	}
    	return gun;
    }
    
    public void onPlayerStoppedUsing(ItemStack gun, World w, EntityPlayer p, int useTicks) 
    {
    }
    
    public EnumRarity getRarity(ItemStack gun)
    {
    	return super.getRarity(gun);
    }
    
    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister reg)
    {
    	baseIcons = new IIcon[GunRegistry.gunMaterials.size()];
    	handleIcons = new IIcon[GunRegistry.gunMaterials.size()];
    	deviceIcons = new IIcon[GunRegistry.gunMaterials.size()];
    	lenseIcons = new IIcon[GunRegistry.lenseMaterials.size()];
    	if(this.gunType != "sniper")
    		scopeIcons = new IIcon[GunRegistry.scopeMaterials.size()];
    	else
    		scopeIcons = new IIcon[GunRegistry.scopeMaterialsSniper.size()];
    	
    	this.itemIcon = reg.registerIcon("essentialcraft:null");
    	for(int i = 0; i < this.baseIcons.length; ++i)
    		this.baseIcons[i] = reg.registerIcon("essentialcraft:guns/"+this.gunType+"/base/"+GunRegistry.gunMaterials.get(i).id);
    	for(int i = 0; i < this.deviceIcons.length; ++i)
    		this.deviceIcons[i] = reg.registerIcon("essentialcraft:guns/"+this.gunType+"/device/"+GunRegistry.gunMaterials.get(i).id);
    	for(int i = 0; i < this.handleIcons.length; ++i)
    		this.handleIcons[i] = reg.registerIcon("essentialcraft:guns/"+this.gunType+"/handle/"+GunRegistry.gunMaterials.get(i).id);
    	for(int i = 0; i < this.lenseIcons.length; ++i)
    		this.lenseIcons[i] = reg.registerIcon("essentialcraft:guns/"+this.gunType+"/lense/"+GunRegistry.lenseMaterials.get(i).id);
    	for(int i = 0; i < this.scopeIcons.length; ++i)
    		if(this.gunType != "sniper")
    			this.scopeIcons[i] = reg.registerIcon("essentialcraft:guns/"+this.gunType+"/scope/"+GunRegistry.scopeMaterials.get(i).id);
    		else
    			this.scopeIcons[i] = reg.registerIcon("essentialcraft:guns/"+this.gunType+"/scope/"+GunRegistry.scopeMaterialsSniper.get(i).id);
    }
    
    public void onUsingTick(ItemStack stack, EntityPlayer player, int count)
    {
		ItemStack gun = stack;
		EntityPlayer p = player;
		World w = p.worldObj;
    	if(this.getItemUseAction(stack) == EnumAction.block)
    	{
    		if(count % 20 == 0)
    			player.worldObj.playSound(player.posX, player.posY, player.posZ, "random.fizz", 0.4F, 1+MathUtils.randomFloat(player.worldObj.rand), false);
    		return;
    	}else
    	{
    		if(this.gunType.equalsIgnoreCase("rifle") && count % 3 == 0)
    		{

    	    	if(gun.getTagCompound().hasKey("base"))
    	    	{
    	    		float balance = 0;
    	    		if(gun.getTagCompound().hasKey("lense"))
    	    		{
    	    			String lenseID = gun.getTagCompound().getString("lense");
    	    			if(lenseID.equals("chaos"))
    	    				balance = 1;
    	    			if(lenseID.equals("frozen"))
    	    				balance = 2;
    	    			if(lenseID.equals("pure"))
    	    				balance = 3;
    	    			if(lenseID.equals("shade"))
    	    				balance = 4;
    	    		}
    	    		if(MiscUtils.getStackTag(gun).hasKey("stats"))
    	    		{
    	    			NBTTagCompound stats = MiscUtils.getStackTag(gun).getCompoundTag("stats");
    	    			if(!MiscUtils.getStackTag(gun).hasKey("gunDamage"))
    	    			{
    	    				MiscUtils.getStackTag(gun).setFloat("gunDamage", 0);
    	    			}
    	    			if(!MiscUtils.getStackTag(gun).hasKey("gunShots"))
    	    			{
    	    				MiscUtils.getStackTag(gun).setFloat("gunShots", 0);
    	    			}
    	    			if(MiscUtils.getStackTag(gun).getFloat("gunShots")+1 <= stats.getFloat("shots"))
    	    				MiscUtils.getStackTag(gun).setFloat("gunShots", MiscUtils.getStackTag(gun).getFloat("gunShots")+1);
    	    			else
    	    			{
    	    				p.stopUsingItem();
    	    				return;
    	    			}
    	            	if(ECUtils.tryToDecreaseMRUInStorage(p, (int) -stats.getFloat("damage")*3) || this.setMRU(gun, (int) -stats.getFloat("damage")*3))
    	            	{
    	            		if(MiscUtils.getStackTag(gun).getFloat("gunDamage")+1 <= stats.getFloat("durability"))
	    	    				MiscUtils.getStackTag(gun).setFloat("gunDamage", MiscUtils.getStackTag(gun).getFloat("gunDamage")+1);
	    	    			else
	    	    			{
	    	    				if(!w.isRemote && w.rand.nextFloat() <= 0.25F)
	    	    				{
	    	    					w.playSound(p.posX, p.posY, p.posZ, "random.break", 1, 1, false);
	    	    					MiscUtils.getStackTag(gun).setFloat("gunShots", stats.getFloat("shots"));
	    	    				}
	    	    			}

	    	    			MiscUtils.getStackTag(gun).setFloat("cool", stats.getFloat("speed")*2);
	    	    			w.playSound(p.posX, p.posY, p.posZ, "essentialcraft:sound.beam", 0.1F+stats.getFloat("damage")/100, 2-stats.getFloat("damage")/50, false);
	    	    			EntityMRURay ray = new EntityMRURay(w,p,stats.getFloat("damage"),stats.getFloat("spread")/2,balance);
	    	        		if(!w.isRemote)
	    	        			w.spawnEntityInWorld(ray);
    	            	}
    	    		}
    	    	}
    		}
    		if(this.gunType.equalsIgnoreCase("gatling"))
    		{
    			int usingTicks = 10000-count;
    			if(count >= 10000-60 && count % 5 == 0)
    			{
    				w.playSound(p.posX, p.posY, p.posZ, "minecart.inside", 0.1F, 0F+(float)usingTicks/30F, false);
    			}
    			if(usingTicks >= 60)
    			{
    					ECUtils.playSoundToAllNearby(p.posX, p.posY, p.posZ, "essentialcraft:sound.beam", 1F, 2, 16, p.dimension);
        	    	if(gun.getTagCompound().hasKey("base"))
        	    	{
        	    		float balance = 0;
        	    		if(gun.getTagCompound().hasKey("lense"))
        	    		{
        	    			String lenseID = gun.getTagCompound().getString("lense");
        	    			if(lenseID.equals("chaos"))
        	    				balance = 1;
        	    			if(lenseID.equals("frozen"))
        	    				balance = 2;
        	    			if(lenseID.equals("pure"))
        	    				balance = 3;
        	    			if(lenseID.equals("shade"))
        	    				balance = 4;
        	    		}
        	    		if(MiscUtils.getStackTag(gun).hasKey("stats"))
        	    		{
        	    			NBTTagCompound stats = MiscUtils.getStackTag(gun).getCompoundTag("stats");
        	    			if(!MiscUtils.getStackTag(gun).hasKey("gunDamage"))
        	    			{
        	    				MiscUtils.getStackTag(gun).setFloat("gunDamage", 0);
        	    			}
        	    			if(!MiscUtils.getStackTag(gun).hasKey("gunShots") && !w.isRemote)
        	    			{
        	    				MiscUtils.getStackTag(gun).setFloat("gunShots", 0);
        	    			}
        	    			if(MiscUtils.getStackTag(gun).getFloat("gunShots")+1 <= stats.getFloat("shots"))
        	    			{
        	    				if(!w.isRemote)
        	    					MiscUtils.getStackTag(gun).setFloat("gunShots", MiscUtils.getStackTag(gun).getFloat("gunShots")+1);
        	    			}
        	    			else
        	    			{
        	    				p.stopUsingItem();
        	    				return;
        	    			}
        	            	if(ECUtils.tryToDecreaseMRUInStorage(p, (int) -stats.getFloat("damage")*2) || this.setMRU(gun, (int) -stats.getFloat("damage")*2))
        	            	{
	        	    			if(MiscUtils.getStackTag(gun).getFloat("gunDamage")+1 <= stats.getFloat("durability"))
	        	    				MiscUtils.getStackTag(gun).setFloat("gunDamage", MiscUtils.getStackTag(gun).getFloat("gunDamage")+1);
	        	    			else
	        	    			{
	        	    				if(!w.isRemote && w.rand.nextFloat() <= 0.25F)
	        	    				{
	        	    					w.playSound(p.posX, p.posY, p.posZ, "random.break", 1, 1, false);
	        	    					MiscUtils.getStackTag(gun).setFloat("gunShots", stats.getFloat("shots"));
	        	    				}
	        	    			}
	        	    			//w.playSound(p.posX, p.posY, p.posZ, "essentialcraft:sound.beam", 0.1F+stats.getFloat("damage")/100, 2-stats.getFloat("damage")/50, false);
	        	    			EntityMRURay ray = new EntityMRURay(w,p,stats.getFloat("damage"),stats.getFloat("spread")/2,balance);
	        	        		if(!w.isRemote)
	        	        			w.spawnEntityInWorld(ray);
	        	    		}
        	    		}
        	    	}
    			}
    		}
    	}
    }
    
    public IIcon getIcon(ItemStack stack, int renderPass, EntityPlayer player, ItemStack usingItem, int useRemaining)
    {
    	if(!stack.hasTagCompound() || !stack.getTagCompound().hasKey("base"))
    	{
    		Random rnd = new Random((EssentialCraftCore.proxy.getClientWorld().getWorldTime()/20) + (renderPass+1));
    		
    		if(renderPass == 0)
    			return this.baseIcons[rnd.nextInt(baseIcons.length)];
    		if(renderPass == 1)
    			return this.handleIcons[rnd.nextInt(handleIcons.length)];
    		if(renderPass == 2)
    			return this.deviceIcons[rnd.nextInt(deviceIcons.length)];
    		if(renderPass == 3)
    			if(this.gunType != "gatling")
    				return this.scopeIcons[rnd.nextInt(scopeIcons.length)];
    			else
    				return this.itemIcon;
    		if(renderPass == 4)
    			return this.lenseIcons[rnd.nextInt(lenseIcons.length)];
    	}else
    	{

    		NBTTagCompound tag = MiscUtils.getStackTag(stack);
    		if(player != null && player.isSneaking() && tag.hasKey("scope") && this.gunType.equalsIgnoreCase("sniper"))
    		{
    			return this.itemIcon;
    		}
    		if(renderPass == 0)
    		{
    			for(int i = 0; i < GunRegistry.gunMaterials.size(); ++i)
    			{
    				if(GunRegistry.gunMaterials.get(i).id.equalsIgnoreCase(tag.getString("base")))
    					return this.baseIcons[i];
    			}
    		}
    		if(renderPass == 1)
    		{
    			for(int i = 0; i < GunRegistry.gunMaterials.size(); ++i)
    			{
    				if(GunRegistry.gunMaterials.get(i).id.equalsIgnoreCase(tag.getString("handle")))
    					return this.handleIcons[i];
    			}
    		}
    		if(renderPass == 2)
    		{
    			for(int i = 0; i < GunRegistry.gunMaterials.size(); ++i)
    			{
    				if(GunRegistry.gunMaterials.get(i).id.equalsIgnoreCase(tag.getString("device")))
    					return this.deviceIcons[i];
    			}
    		}
    		if(renderPass == 3)
    		{
    			if(this.gunType.equalsIgnoreCase("gatling"))
    				return this.itemIcon;
    			else
    			{
    				if(this.gunType.equalsIgnoreCase("sniper"))
    				{
    	    			for(int i = 0; i < GunRegistry.scopeMaterialsSniper.size(); ++i)
    	    			{
    	    				if(GunRegistry.scopeMaterialsSniper.get(i).id.equalsIgnoreCase(tag.getString("scope")))
    	    				return this.scopeIcons[i];
    	    			}	
    				}else
    				{
    	    			for(int i = 0; i < GunRegistry.scopeMaterials.size(); ++i)
    	    			{
    	    				if(GunRegistry.scopeMaterials.get(i).id.equalsIgnoreCase(tag.getString("scope")))
    	    				return this.scopeIcons[i];
    	    			}
    				}
    			}
    		}
    		if(renderPass == 4)
    		{
    			for(int i = 0; i < GunRegistry.lenseMaterials.size(); ++i)
    			{
    				if(GunRegistry.lenseMaterials.get(i).id.equalsIgnoreCase(tag.getString("lense")))
    					return this.lenseIcons[i];
    			}
    		}
    	}
    	return this.itemIcon;
    }
    
    public int getRenderPasses(int metadata)
    {
    	return 5;
    }
    
    public IIcon getIcon(ItemStack stack, int pass)
    {
    	return this.getIcon(stack, pass, null, stack, 0);
    }

    public boolean showDurabilityBar(ItemStack stack)
    {
        return stack.hasTagCompound() && MiscUtils.getStackTag(stack).hasKey("stats");
    }

    public double getDurabilityForDisplay(ItemStack stack)
    {
    	if(stack.hasTagCompound())
    	{
    		NBTTagCompound gunTag = MiscUtils.getStackTag(stack);
    		if(gunTag.hasKey("stats"))
    		{
    			float currentDamage = gunTag.getFloat("gunDamage");
    			float maxDamage = gunTag.getCompoundTag("stats").getFloat("durability");

    			return currentDamage / maxDamage;
    		}
    	}
        return (double)stack.getItemDamageForDisplay() / (double)stack.getMaxDamage();
    }
    
    public EnumAction getItemUseAction(ItemStack p_77661_1_)
    {
    	if(p_77661_1_.hasTagCompound())
    	{
    		NBTTagCompound tag = MiscUtils.getStackTag(p_77661_1_);
    		if(tag.hasKey("stats"))
    		{
    			float current = tag.getFloat("gunShots")+1;
    			float max = tag.getCompoundTag("stats").getFloat("shots");
    			if(current >= max)
    				return EnumAction.block;
    		}
    		
    	}
        return EnumAction.bow;
    }
    
    public int getMaxItemUseDuration(ItemStack p_77626_1_)
    {
    	if(p_77626_1_.hasTagCompound())
    	{
    		NBTTagCompound tag = MiscUtils.getStackTag(p_77626_1_);
    		if(tag.hasKey("stats"))
    		{
    			float current = tag.getFloat("gunShots")+1;
    			float max = tag.getCompoundTag("stats").getFloat("shots");
    			if(current >= max)
    				return MathHelper.floor_float(tag.getCompoundTag("stats").getFloat("reload")*20);
    		}
    	}
        return 10000;
    }
    
}
