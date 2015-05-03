package ec3.common.item;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import DummyCore.Utils.MiscUtils;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemAxe;
import net.minecraft.item.ItemHoe;
import net.minecraft.item.ItemPickaxe;
import net.minecraft.item.ItemSpade;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.item.ItemTool;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

public class ItemWindAxe extends ItemAxe_Mod{

	public ItemWindAxe(ToolMaterial m) 
	{
		super(m);
	}
	
    public boolean onItemUse(ItemStack p_77648_1_, EntityPlayer p_77648_2_, World p_77648_3_, int p_77648_4_, int p_77648_5_, int p_77648_6_, int p_77648_7_, float p_77648_8_, float p_77648_9_, float p_77648_10_)
    {
    	return ItemsCore.wind_elemental_hoe.onItemUse(p_77648_1_, p_77648_2_, p_77648_3_, p_77648_4_, p_77648_5_, p_77648_6_, p_77648_7_, p_77648_8_, p_77648_9_, p_77648_10_);
    }
	
    @SuppressWarnings({ "rawtypes", "unchecked" })
	public boolean hitEntity(ItemStack weapon, EntityLivingBase attacked, EntityLivingBase attacker)
    {

    	if(attacker instanceof EntityPlayer)
    	{
	    	//Totally the same code as in
	    	//#link ec3.utils.common.ECEventHandler
			EntityPlayer p = EntityPlayer.class.cast(attacker);

			ItemStack currentTool = p.getCurrentEquippedItem();
			
			if(!p.worldObj.isRemote && currentTool != null && currentTool.getItem() instanceof ItemTool && ItemTool.class.cast(currentTool.getItem()).func_150913_i() == ItemsCore.windElemental)
			{
		        if (true) // <--- Never write code like that. Well, I guess javac gets rid of that anyways...
		        {
		        	String clazz = "sword";
		        	
		        	if(clazz != null && !clazz.isEmpty())// <--- also, copy/paste is never good, kids!
		        	{
			        	String currentToolClass = "";
			        	if(currentTool.getItem() instanceof ItemPickaxe)
			        		currentToolClass = "pickaxe";
			        	if(currentTool.getItem() instanceof ItemAxe)
			        		currentToolClass = "axe";
			        	if(currentTool.getItem() instanceof ItemSpade)
			        		currentToolClass = "shovel";
			        	if(currentTool.getItem() instanceof ItemHoe)
			        		currentToolClass = "hoe";
			        	if(currentTool.getItem() instanceof ItemSword)//<--- yet again, copy/paste is bad!
			        		currentToolClass = "sword";
			        	
			        	NBTTagCompound toolTag = new NBTTagCompound();
			        	NBTTagCompound genericTag = new NBTTagCompound();

			        	currentTool.writeToNBT(toolTag);

			        	if(toolTag.hasKey("tag"))
			        	{
			        		genericTag = (NBTTagCompound) toolTag.getCompoundTag("tag").copy();
			        		toolTag.getCompoundTag("tag").removeTag("pickaxe");
			        		toolTag.getCompoundTag("tag").removeTag("axe");
			        		toolTag.getCompoundTag("tag").removeTag("shovel");
			        		toolTag.getCompoundTag("tag").removeTag("hoe");
			        		toolTag.getCompoundTag("tag").removeTag("sword");
			        		
			        		toolTag.getCompoundTag("tag").removeTag("tag");
			        	}
			        	
			        	Set tags = genericTag.func_150296_c();
			        	List tagKeyLst = new ArrayList();
			        	Iterator $i = tags.iterator();
			        	
			        	while($i.hasNext())
			        	{
			        		tagKeyLst.add($i.next());
			        	}
			        	
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
			        	
			        	for(int i = 0; i < tagKeyLst.size(); ++i)
			        	{
			        		if(tagKeyLst.get(i) instanceof String)
			        			genericTag.removeTag((String) tagKeyLst.get(i));
			        	}
			        	
			        	ItemStack efficent = null;
			        	
			        	if(genericTag.hasKey(clazz))
			        	{
			        		NBTTagCompound loadFrom = (NBTTagCompound) genericTag.getCompoundTag(clazz).copy();
			        		genericTag.removeTag(clazz);
			        		efficent = ItemStack.loadItemStackFromNBT(loadFrom);
			        		loadFrom = null;
			        	}else 
			        	{
			        		if(clazz.equalsIgnoreCase("pickaxe"))
			        			efficent = new ItemStack(ItemsCore.wind_elemental_pick,1,currentTool.getItemDamage());
			        		if(clazz.equalsIgnoreCase("shovel"))
			        			efficent = new ItemStack(ItemsCore.wind_elemental_shovel,1,currentTool.getItemDamage());
			        		if(clazz.equalsIgnoreCase("hoe")) 
			        			efficent = new ItemStack(ItemsCore.wind_elemental_hoe,1,currentTool.getItemDamage());
			        		if(clazz.equalsIgnoreCase("sword"))
			        			efficent = new ItemStack(ItemsCore.wind_elemental_sword,1,currentTool.getItemDamage());
			        		if(clazz.equalsIgnoreCase("axe"))
			        			efficent = new ItemStack(ItemsCore.wind_elemental_axe,1,currentTool.getItemDamage());
			        	}
			        	
			        	if(efficent != null && efficent.getItem() != null)
			        	{
				        	NBTTagCompound anotherTag = MiscUtils.getStackTag(efficent);
				        	
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
				        	
				        	if(toolTag != null)
				        		anotherTag.setTag(currentToolClass, toolTag);
				        	
				        	p.inventory.setInventorySlotContents(p.inventory.currentItem, efficent);
			        	}
			        }
		        }
			}
    	}
    	return true;
    }
  

}
