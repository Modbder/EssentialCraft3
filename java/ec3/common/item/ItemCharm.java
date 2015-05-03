package ec3.common.item;

import java.util.List;

import baubles.api.BaubleType;
import baubles.api.IBauble;
import ec3.utils.common.ECUtils;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.stats.StatList;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeHooks;

public class ItemCharm extends ItemStoresMRUInNBT implements IBauble{

	public IIcon[] icon = new IIcon[10];
	public String[] name = new String[]{"Fire","Water","Earth","Air","Steam","Magma","Lightning","Life","Rain","Dust"};
	public ItemCharm() {
		super();
		this.setMaxMRU(5000);
		this.setMaxDamage(0);
		this.maxStackSize = 1;
        this.bFull3D = false;
        this.setHasSubtypes(true);
	}
	
	@Override
	public void onWornTick(ItemStack s, EntityLivingBase entity)
	{
		if(entity instanceof EntityPlayer)
		{
			EntityPlayer e = (EntityPlayer) entity;
			int dam = s.getItemDamage();
			switch(dam)
			{
				case 0:
					this.updateFire(e, s);
					break;
				case 1:
					this.updateWater(e, s);
					break;
				case 2:
					this.updateEarth(e, s);
					break;
				case 3:
					this.updateAir(e, s);
					break;
				case 4:
					this.updateSteam(e, s);
					break;
				case 5:
					this.updateMagma(e, s);
					break;
				case 6:
					this.updateLightning(e, s);
					break;
				case 7:
					this.updateLife(e, s);
					break;
				case 8:
					this.updateRain(e, s);
					break;
				case 9:
					this.updateDust(e, s);
					break;
			}
		}
		
	}
    
    public IIcon getIconFromDamage(int par1)
    {
    	return icon[par1];
    }
    
    @Override
    public void registerIcons(IIconRegister par1IconRegister)
    {
    	for(int i = 0; i < 10; ++i)
    	{
    		this.icon[i] = par1IconRegister.registerIcon("essentialcraft:tools/charm"+name[i]);
    	}
    }
    
    public void getSubItems(Item par1, CreativeTabs par2CreativeTabs, List par3List)
    {
        for (int var4 = 0; var4 < 10; ++var4)
        {
        	ItemStack min = new ItemStack(par1, 1, var4);
        	ECUtils.initMRUTag(min, maxMRU);
        	ItemStack max = new ItemStack(par1, 1, var4);
        	ECUtils.initMRUTag(max, maxMRU);
        	ECUtils.getStackTag(max).setInteger("mru", ECUtils.getStackTag(max).getInteger("maxMRU"));
            par3List.add(min);
            par3List.add(max);
        }
    }
    
    public void updateFire(EntityPlayer e, ItemStack s)
    {
    	if(e.isBurning() && !e.isPotionActive(Potion.fireResistance.id) && (ECUtils.tryToDecreaseMRUInStorage(e, -50) || this.setMRU(s, -50)))
    	{
    		e.extinguish();
    		e.worldObj.playSoundAtEntity(e, "random.fizz", 1, 1);
    	}
    }
    
    public void updateWater(EntityPlayer e, ItemStack s)
    {
    	if(e.getAir() < 10 && e.isInWater() && (ECUtils.tryToDecreaseMRUInStorage(e, -100) || this.setMRU(s, -100)))
    	{
    		e.setAir(100);
    		e.worldObj.playSoundAtEntity(e, "random.breath", 1, 1);
    	}
    }
    
    public void updateEarth(EntityPlayer e, ItemStack s)
    {
    	if(e.hurtTime > 0 && !e.isPotionActive(Potion.resistance.id) && (ECUtils.tryToDecreaseMRUInStorage(e, -200) || this.setMRU(s, -200)))
    	{
    		e.addPotionEffect(new PotionEffect(Potion.resistance.id,100,0));
    	}
    }
    
    public void updateAir(EntityPlayer e, ItemStack s)
    {
    	if(e.isSprinting() && !e.isPotionActive(Potion.moveSpeed.id) && (ECUtils.tryToDecreaseMRUInStorage(e, -10) || this.setMRU(s, -10)))
    	{
    		e.addPotionEffect(new PotionEffect(Potion.moveSpeed.id,20,1));
    		e.worldObj.playSoundAtEntity(e, "random.breath", 1, .01F);
    	}
    }
    
    public void updateSteam(EntityPlayer e, ItemStack s)
    {
    	if(e.getHealth() < 5 && !e.isPotionActive(Potion.moveSpeed.id) && (ECUtils.tryToDecreaseMRUInStorage(e, -200) || this.setMRU(s, -200)))
    	{
    		e.addPotionEffect(new PotionEffect(Potion.moveSpeed.id,100,5));
    		e.worldObj.playSoundAtEntity(e, "random.breath", 1, .01F);
    		e.worldObj.playSoundAtEntity(e, "random.fizz", 1, .01F);
    	}
    }
    
    public void updateMagma(EntityPlayer e, ItemStack s)
    {
    	Material m = e.worldObj.getBlock((int)e.posX-1, (int)e.posY-1, (int)e.posZ).getMaterial();
    	Material m1 = e.worldObj.getBlock((int)e.posX-1, (int)e.posY, (int)e.posZ).getMaterial();
    	if((m == Material.lava || m1 == Material.lava) && !e.isPotionActive(Potion.fireResistance.id) && (ECUtils.tryToDecreaseMRUInStorage(e, -100) || this.setMRU(s, -100)))
    	{
    		e.addPotionEffect(new PotionEffect(Potion.fireResistance.id,100,0));
    		e.worldObj.playSoundAtEntity(e, "liquid.lava", 1,  10F);
    	}
    }
    
    public void updateLightning(EntityPlayer e, ItemStack s)
    {
    	if(e.worldObj.isThundering()&& !e.isPotionActive(Potion.damageBoost.id) && (ECUtils.tryToDecreaseMRUInStorage(e, -100) || this.setMRU(s, -100)))
    	{
    		e.addPotionEffect(new PotionEffect(Potion.fireResistance.id,100,0));
    		e.addPotionEffect(new PotionEffect(Potion.nightVision.id,600,0));
    		e.addPotionEffect(new PotionEffect(Potion.damageBoost.id,100,0));
    		e.worldObj.playSoundAtEntity(e, "ambient.weather.thunder1", 1,  1F);
    	}
    }
    
    public void updateLife(EntityPlayer e, ItemStack s)
    {
    	if(e.getHealth() < 5 && !e.isPotionActive(Potion.regeneration.id) && (ECUtils.tryToDecreaseMRUInStorage(e, -200) || this.setMRU(s, -200)))
    	{
    		e.addPotionEffect(new PotionEffect(Potion.regeneration.id,50,1));
    		e.worldObj.playSoundAtEntity(e, "random.orb", 1,  10F);
    	}
    	if(e.getHealth() < 20 && !e.isPotionActive(Potion.regeneration.id) && (ECUtils.tryToDecreaseMRUInStorage(e, -50) || this.setMRU(s, -50)))
    	{
    		e.addPotionEffect(new PotionEffect(Potion.regeneration.id,50,0));
    		e.worldObj.playSoundAtEntity(e, "random.orb", 1, 10F);
    	}

    }
    
    public void updateRain(EntityPlayer e, ItemStack s)
    {
    	if(e.worldObj.isRaining() && !e.isPotionActive(Potion.digSpeed.id) && (ECUtils.tryToDecreaseMRUInStorage(e, -50) || this.setMRU(s, -50)))
    	{
    		e.addPotionEffect(new PotionEffect(Potion.digSpeed.id,100,0));
    		e.addPotionEffect(new PotionEffect(Potion.jump.id,100,2));
    		e.addPotionEffect(new PotionEffect(Potion.moveSpeed.id,100,0));
    		e.worldObj.playSoundAtEntity(e, "liquid.splash", 1,  1F);
    	}
    }
    
    public void updateDust(EntityPlayer e, ItemStack s)
    {
    	Material m = e.worldObj.getBlock((int)e.posX-1, (int)e.posY-1, (int)e.posZ).getMaterial();
    	if(m == Material.sand && !e.isPotionActive(Potion.resistance.id) && (ECUtils.tryToDecreaseMRUInStorage(e, -100) || this.setMRU(s, -100)))
    	{
    		e.addPotionEffect(new PotionEffect(Potion.resistance.id,100,0));
    		e.addPotionEffect(new PotionEffect(Potion.regeneration.id,100,0));
    	}
    }
    
    public String getItemDisplayName(ItemStack par1ItemStack)
    {
    	return "Charm Of "+name[par1ItemStack.getItemDamage()];
    }

	@Override
	public BaubleType getBaubleType(ItemStack itemstack) {
		// TODO Auto-generated method stub
		return BaubleType.AMULET;
	}

	@Override
	public void onEquipped(ItemStack itemstack, EntityLivingBase player) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onUnequipped(ItemStack itemstack, EntityLivingBase player) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean canEquip(ItemStack itemstack, EntityLivingBase player) {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean canUnequip(ItemStack itemstack, EntityLivingBase player) {
		// TODO Auto-generated method stub
		return true;
	}
}
