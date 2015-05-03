package ec3.api;

import net.minecraft.item.ItemStack;

/**
 * 
 * @author Modbder
 * @Description use this interface to create new items, which may require MRU to act with
 * 
 */
public interface IItemRequiresMRU {
	
	/**
	 * This is called so you can use different ways of storing MRU in your items. You can find some examples below
	 * @param stack - ItemStack of your item. 
	 * @param amount - the amount of MRU to add or remove. Use negative numbers to decrease MRU.
	 * @return true, if this operation was successful, false if not
	 */
	public abstract boolean setMRU(ItemStack stack, int amount);
	
	/**
	 * This is called so you can use different ways of storing MRU in your items. You can find some examples below
	 * @param stack - ItemStack of your item. 
	 * @return the amount of MRU in your stack
	 */
	public abstract int getMRU(ItemStack stack);
	
	/**
	 * This is called so you can use different ways of storing MRU in your items. You can find some examples below
	 * @param stack - ItemStack of your item. 
	 * @return the amount of MRU your item can store
	 */
	public abstract int getMaxMRU(ItemStack stack);
	
	//*======================================EXAMPLES=========================================*//
	/**
	 * I used two basic classes to work with my items, but at some development point I decided, that I do not need one of them anymore. Anyway, this way is also suggested here. It is under the @Deprecated mark
	 */
	
	//This is the way to store MRU using damage values. I decided to not use this, because it is not the best way to do so, and it will not allow you to store more than 32000 mru in it. Although this way was used in EC I, and it was perfectly working there.
	/*
	@Deprecated
	public class ItemStoresMRUInDamage extends Item implements IItemRequiresMRU {
		public ItemStoresMRUInDamage(int par1) {
			super(par1);
		}

		@Override
		public boolean setMRU(ItemStack stack, int amount) {
			if(stack.getItemDamage()-amount >= 1 && stack.getItemDamage()-amount<=stack.getMaxDamage()-1)
			{
				stack.setItemDamage(stack.getItemDamage()-amount);
				return true;
			}
			return false;
		}

		@Override
		public int getMRU(ItemStack stack) {
			return stack.getMaxDamage()-stack.getItemDamage();
		}
		
		@Override
		public int getMaxMRU(ItemStack stack) {
			return stack.getMaxDamage();
		}
		
		@Override
	    public void addInformation(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, List par3List, boolean par4) 
	    {
	    	par3List.add(this.getMRU(par1ItemStack) + "/" + par1ItemStack.getMaxDamage() + " MRU");
	    }
	    
	    @Override
	    public void getSubItems(int par1, CreativeTabs par2CreativeTabs, List par3List)
	    {
	        par3List.add(new ItemStack(par1, 1, this.getMaxDamage()-1));
	        par3List.add(new ItemStack(par1, 1, 1));
	    }
	}
	*/
	
	//This is the way to store MRU in item's NBTTagCompound. Notice, that I'm not showing how to create/edit tags, it all is within the ECUtils class. I use lots of methods from there. Just ignore it, you can use your ways to do this
	/*
	public class ItemStoresMRUInNBTTag extends Item implements IItemRequiresMRU {
		int maxMRU;
		public ItemStoresMRUInNBTTag(int par1, int maxMRU) {
			super(par1);
			this.maxMRU = maxMRU;
		}
	
		@Override
		public boolean setMRU(ItemStack stack, int amount) {
			if(ECUtils.getStackTag(stack).getInteger("mru")+amount >= 0 && ECUtils.getStackTag(stack).getInteger("mru")+amount<=ECUtils.getStackTag(stack).getInteger("maxMRU"))
			{
				ECUtils.getStackTag(stack).setInteger("mru", ECUtils.getStackTag(stack).getInteger("mru")+amount);
				return true;
			}
			return false;
		}
	
		@Override
		public int getMRU(ItemStack stack) {
			return ECUtils.getStackTag(stack).getInteger("mru");
		}
		
		@Override
		public int getMaxMRU(ItemStack stack) {
			return this.maxMRU;
		}
		
		@Override
		public void onUpdate(ItemStack itemStack, World world, Entity entity, int indexInInventory, boolean isCurrentItem)
		{
			ECUtils.initMRUTag(itemStack, maxMRU);
		}
		
		@Override
	    public void addInformation(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, List par3List, boolean par4) 
	    {
	    	par3List.add(ECUtils.getStackTag(par1ItemStack).getInteger("mru") + "/" + ECUtils.getStackTag(par1ItemStack).getInteger("maxMRU") + " MRU");
	    }
	    
	    @Override
	    public void getSubItems(int par1, CreativeTabs par2CreativeTabs, List par3List)
	    {
	        ItemStack min = new ItemStack(par1, 1, 0);
	        ItemStack max = new ItemStack(par1, 1, 0);
	        ECUtils.initMRUTag(min, maxMRU);
	        ECUtils.initMRUTag(max, maxMRU);
	       	ECUtils.getStackTag(max).setInteger("mru", ECUtils.getStackTag(max).getInteger("maxMRU"));
	        par3List.add(min);
	        par3List.add(max);
	    }
	 }
	 */

}
