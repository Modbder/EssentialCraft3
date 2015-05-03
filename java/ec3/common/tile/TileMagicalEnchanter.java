package ec3.common.tile;

import java.util.List;
import java.util.Random;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.enchantment.EnchantmentData;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.common.config.Configuration;
import DummyCore.Utils.DataStorage;
import DummyCore.Utils.DummyData;
import DummyCore.Utils.MathUtils;
import ec3.api.ApiCore;
import ec3.api.ITEHasMRU;
import ec3.common.item.ItemBoundGem;
import ec3.utils.common.ECUtils;

public class TileMagicalEnchanter extends TileMRUGeneric{
	
	List<EnchantmentData> enchants;
	public int progressLevel = -1;
    public int field_145926_a;
    public float field_145933_i;
    public float field_145931_j;
    public float field_145932_k;
    public float field_145929_l;
    public float field_145930_m;
    public float field_145927_n;
    public float field_145928_o;
    public float field_145925_p;
    public float field_145924_q;
    private static Random field_145923_r = new Random();
    
	public static float cfgMaxMRU = ApiCore.DEVICE_MAX_MRU_GENERIC;
	public static boolean generatesCorruption = false;
	public static int genCorruption = 2;
	public static int mruUsage = 100;
	public static int maxEnchantmentLevel = 60;
	
	public TileMagicalEnchanter()
	{
		 super();
		this.maxMRU = (int)cfgMaxMRU;
		this.setSlotsNum(3);
	}
	
	@Override
	public void updateEntity()
	{
		super.updateEntity();
		ECUtils.mruIn(this, 0);
		ECUtils.spawnMRUParticles(this, 0);
		IInventory inv = this;
		int slotNum = 0;
		TileEntity tile = this;
		if(inv.getStackInSlot(slotNum) != null && inv.getStackInSlot(slotNum).getItem() instanceof ItemBoundGem && inv.getStackInSlot(slotNum).getTagCompound() != null)
		{
			ItemStack s = inv.getStackInSlot(slotNum);
			int[] o = ItemBoundGem.getCoords(s);
			if(MathUtils.getDifference(tile.xCoord, o[0]) <= 16 && MathUtils.getDifference(tile.yCoord, o[1]) <= 16 && MathUtils.getDifference(tile.zCoord, o[2]) <= 16)
			{
    			if(tile.getWorldObj().getTileEntity(o[0], o[1], o[2]) != null && tile.getWorldObj().getTileEntity(o[0], o[1], o[2]) instanceof ITEHasMRU)
    			{
    				this.setBalance(((ITEHasMRU) tile.getWorldObj().getTileEntity(o[0], o[1], o[2])).getBalance());
    			}
    		}
		}
		if(!this.worldObj.isBlockIndirectlyGettingPowered(xCoord, yCoord, zCoord))
			this.tryEnchant();
		
        this.field_145927_n = this.field_145930_m;
        this.field_145925_p = this.field_145928_o;
        EntityPlayer entityplayer = this.worldObj.getClosestPlayer((double)((float)this.xCoord + 0.5F), (double)((float)this.yCoord + 0.5F), (double)((float)this.zCoord + 0.5F), 3.0D);

        if (entityplayer != null)
        {
            double d0 = entityplayer.posX - (double)((float)this.xCoord + 0.5F);
            double d1 = entityplayer.posZ - (double)((float)this.zCoord + 0.5F);
            this.field_145924_q = (float)Math.atan2(d1, d0);
            this.field_145930_m += 0.1F;

            if (this.field_145930_m < 0.5F || field_145923_r.nextInt(40) == 0)
            {
                float f1 = this.field_145932_k;

                do
                {
                    this.field_145932_k += (float)(field_145923_r.nextInt(4) - field_145923_r.nextInt(4));
                }
                while (f1 == this.field_145932_k);
            }
        }
        else
        {
            this.field_145924_q += 0.02F;
            this.field_145930_m -= 0.1F;
        }

        while (this.field_145928_o >= (float)Math.PI)
        {
            this.field_145928_o -= ((float)Math.PI * 2F);
        }

        while (this.field_145928_o < -(float)Math.PI)
        {
            this.field_145928_o += ((float)Math.PI * 2F);
        }

        while (this.field_145924_q >= (float)Math.PI)
        {
            this.field_145924_q -= ((float)Math.PI * 2F);
        }

        while (this.field_145924_q < -(float)Math.PI)
        {
            this.field_145924_q += ((float)Math.PI * 2F);
        }

        float f2;

        for (f2 = this.field_145924_q - this.field_145928_o; f2 >= (float)Math.PI; f2 -= ((float)Math.PI * 2F))
        {
            ;
        }

        while (f2 < -(float)Math.PI)
        {
            f2 += ((float)Math.PI * 2F);
        }

        this.field_145928_o += f2 * 0.4F;

        if (this.field_145930_m < 0.0F)
        {
            this.field_145930_m = 0.0F;
        }

        if (this.field_145930_m > 1.0F)
        {
            this.field_145930_m = 1.0F;
        }

        ++this.field_145926_a;
        this.field_145931_j = this.field_145933_i;
        float f = (this.field_145932_k - this.field_145933_i) * 0.4F;
        float f3 = 0.2F;

        if (f < -f3)
        {
            f = -f3;
        }

        if (f > f3)
        {
            f = f3;
        }

        this.field_145929_l += (f - this.field_145929_l) * 0.9F;
        this.field_145933_i += this.field_145929_l;
	}
	
    @SideOnly(Side.CLIENT)
    @Override
    public AxisAlignedBB getRenderBoundingBox()
    {
    	AxisAlignedBB bb = INFINITE_EXTENT_AABB;
    	return bb;
    }
    
	@Override
    public void readFromNBT(NBTTagCompound i)
    {
		this.progressLevel = i.getInteger("work");
		super.readFromNBT(i);
    }
	
	@Override
    public void writeToNBT(NBTTagCompound i)
	{
		i.setInteger("work", progressLevel);
    	super.writeToNBT(i);
    }
	
    public void tryEnchant()
    {
    	if(this.canItemBeEnchanted() && !this.worldObj.isRemote)
    	{
    		if(this.setMRU(this.getMRU()-mruUsage))
    		{
				if(generatesCorruption)
					ECUtils.increaseCorruptionAt(worldObj, xCoord, yCoord, zCoord, this.worldObj.rand.nextInt(genCorruption));
	    		++this.progressLevel;
	    		if(this.progressLevel >= this.getRequiredTimeToAct())
	    		{
	    			this.enchant();
	    			this.progressLevel = -1;
	    		}
    		}
    	}
    	if(!this.canItemBeEnchanted())
    	{
    		this.progressLevel = -1;
    		this.enchants = null;
    	}
    }
    
    public void enchant()
    {
    	List<EnchantmentData> enchants = this.getEnchantmentsForStack(this.getStackInSlot(1));
    	ItemStack enchanted = this.getStackInSlot(1).copy();
    	enchanted.stackSize = 1;
    	this.decrStackSize(1, 1);
    	for(int m = 0; m < enchants.size(); ++m)
    	{
    		EnchantmentData d = enchants.get(m);
    		if(d != null)
    		{
    			if(enchanted.getItem() == Items.book)
    			{
    				enchanted = new ItemStack(Items.enchanted_book,1,0);
    			}
    			enchanted.addEnchantment(d.enchantmentobj, d.enchantmentLevel);
    		}
    	}
    	this.setInventorySlotContents(2, enchanted);
    	this.enchants = null;
    }
    
    @SuppressWarnings("unchecked")
	public List<EnchantmentData> getEnchantmentsForStack(ItemStack stack)
    {
    	if(this.enchants == null)
    		this.enchants = EnchantmentHelper.buildEnchantmentList(this.worldObj.rand, stack, this.getMaxPower());
    	 return enchants;
    }
    
    public int getRequiredTimeToAct()
    {
    	return this.getMaxPower()*10;
    }
    
    public int getRequiredMRU()
    {
    	return this.getMaxPower()*1000;
    }
    
    public int getMaxPower()
    {
    	int l = 0;
    	for(int x = -2; x <= 2; ++x)
    	{
        	for(int y = 0; y <= 2; ++y)
        	{
            	for(int z = -2; z <= 2; ++z)
            	{
            		if(x == 0 && y == 0 && z == 0)
            		{
            			
            		}else
            		{
            			l += ForgeHooks.getEnchantPower(this.worldObj, this.xCoord+x, this.yCoord+y, this.zCoord+z);
            		}
            	}
        	}
    	}
    	if(l > maxEnchantmentLevel)
    		l = maxEnchantmentLevel;
    	return l;
    }
    
    public boolean canItemBeEnchanted()
    {
    	try {
			ItemStack s = this.getStackInSlot(1);
			if(s != null && this.getMaxPower() > 0 && this.getMRU() > mruUsage && this.getStackInSlot(2) == null)
			{
				if(s.isItemEnchantable() && this.getEnchantmentsForStack(s) != null && !this.getEnchantmentsForStack(s).isEmpty())
				{
					return true;
				}
			}
			return false;
		} catch (Exception e) {
			return false;
		}
    }
    
    public static void setupConfig(Configuration cfg)
    {
    	try
    	{
	    	cfg.load();
	    	String[] cfgArrayString = cfg.getStringList("MagicalEnchanterSettings", "tileentities", new String[]{
	    			"Max MRU:"+ApiCore.DEVICE_MAX_MRU_GENERIC,
	    			"MRU Usage:100",
	    			"Max level of enchantment:60",
	    			"Can this device actually generate corruption:false",
	    			"The amount of corruption generated each tick(do not set to 0!):2"
	    			},"");
	    	String dataString="";
	    	
	    	for(int i = 0; i < cfgArrayString.length; ++i)
	    		dataString+="||"+cfgArrayString[i];
	    	
	    	DummyData[] data = DataStorage.parseData(dataString);
	    	
	    	mruUsage = Integer.parseInt(data[1].fieldValue);
	    	maxEnchantmentLevel = Integer.parseInt(data[2].fieldValue);
	    	cfgMaxMRU = Float.parseFloat(data[0].fieldValue);
	    	generatesCorruption = Boolean.parseBoolean(data[3].fieldValue);
	    	genCorruption = Integer.parseInt(data[4].fieldValue);
	    	
	    	cfg.save();
    	}catch(Exception e)
    	{
    		return;
    	}
    }

}
