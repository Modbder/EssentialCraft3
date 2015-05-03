package ec3.common.item;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import DummyCore.Utils.MiscUtils;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ec3.api.IShadeCreature;
import ec3.common.entity.EntityShadowKnife;
import ec3.utils.common.ECUtils;
import ec3.utils.common.ShadeUtils;

public class ItemShadowKnife extends ItemSword_Mod{

	public IIcon[] swordIcons = new IIcon[2];
	
    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister r)
    {
    	this.itemIcon = r.registerIcon(this.iconString+"_gen");
    	swordIcons[0] = r.registerIcon(this.iconString+"_gen");
    	swordIcons[1] = r.registerIcon(this.iconString+"_player");
    }
    
    public boolean onEntitySwing(EntityLivingBase entityLiving, ItemStack stack)
    {
    	if(stack.stackSize >= 2 && (entityLiving instanceof IShadeCreature || (entityLiving instanceof EntityPlayer && ECUtils.getData(EntityPlayer.class.cast(entityLiving)).getMatrixTypeID() == 4)))
    	{
    		if(!(entityLiving instanceof EntityPlayer && EntityPlayer.class.cast(entityLiving).capabilities.isCreativeMode))
    			--stack.stackSize;
	    	if(stack.stackSize <= 0)
	    	{
	    		entityLiving.setCurrentItemOrArmor(0, null);
	    	}
	    	EntityShadowKnife knife = new EntityShadowKnife(entityLiving.worldObj, entityLiving);
	    	if(!entityLiving.worldObj.isRemote)
	    		entityLiving.worldObj.spawnEntityInWorld(knife);
    	}
        return false;
    }
    
    @SideOnly(Side.CLIENT)
    public IIcon getIconIndex(ItemStack stack)
    {
    	return getIcon(stack,0);
    }
    
    public IIcon getIcon(ItemStack stack, int pass)
    {
    	return MiscUtils.getStackTag(stack).getBoolean("active") ? swordIcons[0] : swordIcons[1];
    }
	
	public ItemShadowKnife() {
		super(ItemsCore.shade);
	}
	
	public void toggleActivity(ItemStack is, boolean b)
	{
		if(is != null)
		{
			NBTTagCompound tag = MiscUtils.getStackTag(is);
			if(tag.getBoolean("active") != b)
			{
				tag.setBoolean("active", b);
			}
		}
	}
	
    public boolean onEntityItemUpdate(EntityItem entityItem)
    {
    	toggleActivity(entityItem.getEntityItem(),false);
    	return super.onEntityItemUpdate(entityItem);
    }
	
	public void onUpdate(ItemStack sword, World w, Entity e, int slotNum, boolean held) 
	{
		if(e.ticksExisted % 20 == 0 && !w.isRemote && held)
		{
			sword.stackSize += 1;
			if(sword.stackSize >= 32)
				sword.stackSize = 32;
		}
		if(e instanceof IShadeCreature)
			toggleActivity(sword,true);
		
		if(e instanceof EntityPlayer)
		{
    		EntityPlayer p = EntityPlayer.class.cast(e);
    		if(ECUtils.getData(p).getMatrixTypeID() == 4)
    			toggleActivity(sword,true);
    		else
    			toggleActivity(sword,false);
		}
	}
	
	@Override
    @SuppressWarnings({ "rawtypes", "unchecked" })
	public Multimap getAttributeModifiers(ItemStack stack)
    {
    	Multimap mp = HashMultimap.create();
    	if(MiscUtils.getStackTag(stack).getBoolean("active"))
    		mp.put(SharedMonsterAttributes.attackDamage.getAttributeUnlocalizedName(), new AttributeModifier(field_111210_e, "Weapon modifier", 12, 0));
    	return mp;
    }
	
	
    public boolean isItemTool(ItemStack p_77616_1_)
    {
    	return true;
    }

    public boolean hitEntity(ItemStack weapon, EntityLivingBase attacked, EntityLivingBase attacker)
    {
    	if(attacker instanceof IShadeCreature)
    	{
    		if(attacked instanceof EntityPlayer)
    		{
    			EntityPlayer p = EntityPlayer.class.cast(attacked);
    			ShadeUtils.attackPlayerWithShade(p, attacker, weapon);
    		}
    	}
    	return false;
    }

}
