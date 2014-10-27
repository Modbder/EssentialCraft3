package ec3.utils.common;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.UUID;

import org.lwjgl.opengl.GL11;

import baubles.api.BaublesApi;

import com.mojang.authlib.GameProfile;

import cpw.mods.fml.common.ObfuscationReflectionHelper;
import cpw.mods.fml.relauncher.ReflectionHelper;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import DummyCore.Utils.Coord3D;
import DummyCore.Utils.DummyData;
import DummyCore.Utils.DummyDistance;
import DummyCore.Utils.MathUtils;
import DummyCore.Utils.MiscUtils;
import ec3.api.ApiCore;
import ec3.api.EnumStructureType;
import ec3.api.IItemAllowsSeeingMRUCU;
import ec3.api.IMRUPressence;
import ec3.api.IMRUStorage;
import ec3.api.ISpell;
import ec3.api.ITEHasMRU;
import ec3.common.entity.EntityMRUPresence;
import ec3.common.item.ItemBaublesWearable;
import ec3.common.item.ItemBoundGem;
import ec3.common.mod.EssentialCraftCore;
import ec3.common.registry.PotionRegistry;
import ec3.common.tile.TileRayTower;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class ECUtils {
	public static Hashtable<EnumStructureType,List<Block>> allowedBlocks = new Hashtable(); 
	public static Hashtable<String, Float> mruResistance = new Hashtable();
	public static Hashtable<String, Boolean> ignoreMeta = new Hashtable();
	public static List<SpellEntry> spells = new ArrayList();
	
	public static void createNBTTag(ItemStack stack)
	{
		if(stack.hasTagCompound())
		{
			return;
		}
		NBTTagCompound itemTag = new NBTTagCompound();
		stack.setTagCompound(itemTag);
	}
	
	public static NBTTagCompound getStackTag(ItemStack stack)
	{
		createNBTTag(stack);
		return stack.getTagCompound();
	}
	
    public static NBTTagCompound getOrCreateNbtData(ItemStack itemStack)
    {
        NBTTagCompound ret = itemStack.getTagCompound();
        if(ret == null)
        {
            ret = new NBTTagCompound();
            itemStack.setTagCompound(ret);
        }
        return ret;
    }
	
	public static void initMRUTag(ItemStack stack, int maxMRU)
	{
		if(!getStackTag(stack).hasKey("maxMRU"))
		getStackTag(stack).setInteger("maxMRU", maxMRU);
		if(!getStackTag(stack).hasKey("mru"))
		getStackTag(stack).setInteger("mru", 0);
	}
	
	public static void registerBlockResistance(Block blk, int meta, float resistance)
	{
		DummyData dt = new DummyData(blk.getUnlocalizedName(),meta);
		ignoreMeta.put(blk.getUnlocalizedName(), meta == -1);
		mruResistance.put(dt.toString(), resistance);
	}
	
	public static boolean canPlayerSeeMRU(EntityPlayer player)
	{
		ItemStack currentItem = player.getCurrentEquippedItem();
		
		ItemStack headwear = player.getEquipmentInSlot(4);
		
		return (currentItem != null && ApiCore.allowsSeeingMRU.contains(currentItem.getItem())) || (headwear != null && ApiCore.allowsSeeingMRU.contains(headwear.getItem())) || (currentItem != null && currentItem.getItem() instanceof IItemAllowsSeeingMRUCU) || (headwear != null && headwear.getItem() instanceof IItemAllowsSeeingMRUCU);
	}
	 
	public static boolean canSpellWork(ItemStack spell, ISpell spell_2, int ubmru, int attune,EntityPlayer player)
	{
		NBTTagCompound tag = MiscUtils.getStackTag(spell);
		if(tag.hasKey("cooldown"))
		{
			if(tag.getInteger("cooldown") <= 0)
			{
				if(ubmru >= spell_2.getUBMRURequired(spell))
				{
					if(spell_2.getAttunementRequired(spell) == -1 || spell_2.getAttunementRequired(spell) == attune)
					{
						return true;
					}
				}
			}
		}else
		{
			if(ubmru >= spell_2.getUBMRURequired(spell))
			{
				if(spell_2.getAttunementRequired(spell) == -1 || spell_2.getAttunementRequired(spell) == attune)
				{
					return true;
				}
			}
		}
		return false;
	}
	
	public static boolean tryToDecreaseMRUInStorage(EntityPlayer player, int amount)
	{
		if(player.capabilities.isCreativeMode)
		{
			return true;
		}
		InventoryPlayer _gen_var_0 = player.inventory;
		for(int _gen_int_0 = 0; _gen_int_0 < _gen_var_0.mainInventory.length; ++_gen_int_0)
		{
			ItemStack _gen_var_1 = _gen_var_0.mainInventory[_gen_int_0];
			if(_gen_var_1 != null)
			{
				if(_gen_var_1.getItem() instanceof IMRUStorage)
				{
					if(((IMRUStorage)(_gen_var_1.getItem())).setMRU(_gen_var_1, amount))
					{
						return true;
					}
				}
			}
		}
		
		return false;
		
	}

	
	public static void saveMRUState(ITEHasMRU tile, NBTTagCompound saveTag)
	{
		
		saveTag.setFloat("mru", tile.getMRU());
		saveTag.setFloat("maxMRU", tile.getMaxMRU());
		saveTag.setFloat("balance", tile.getBalance());
		saveTag.setString("uuid", tile.getUUID().toString());
	}
	
	public static void loadMRUState(ITEHasMRU tile, NBTTagCompound loadTag)
	{
		tile.setBalance(loadTag.getFloat("balance"));
		tile.setMRU((int) loadTag.getFloat("mru"));
		tile.setMaxMRU(loadTag.getFloat("maxMRU"));
	}
	
	public static void createMRUCUAt(World w, Coord3D c, int MRU, float balance, boolean flag, boolean canAlwaysStay)
	{
		EntityMRUPresence mru = new EntityMRUPresence(w);
		mru.setPositionAndRotation(c.x, c.y, c.z, 0, 0);
		if(!w.isRemote)
		{
			mru.setMRU(MRU);
			mru.setBalance(balance);
			mru.setFlag(flag);
			mru.setAlwaysStay(canAlwaysStay);
			w.spawnEntityInWorld(mru);
		}
	}
	
	public static void mruIn(TileEntity tile, int slotNum)
	{
		if(tile instanceof IInventory && tile instanceof ITEHasMRU)
		{
			IInventory inv = (IInventory) tile;
			ITEHasMRU mrut = (ITEHasMRU) tile;
			if(inv.getStackInSlot(slotNum) != null && inv.getStackInSlot(slotNum).getItem() instanceof ItemBoundGem && inv.getStackInSlot(slotNum).getTagCompound() != null)
			{
				ItemStack s = inv.getStackInSlot(slotNum);
				int[] o = ItemBoundGem.getCoords(s);
				if(MathUtils.getDifference(tile.xCoord, o[0]) <= 16 && MathUtils.getDifference(tile.yCoord, o[1]) <= 16 && MathUtils.getDifference(tile.zCoord, o[2]) <= 16)
				{
	    			if(tile.getWorldObj().getTileEntity(o[0], o[1], o[2]) != null && tile.getWorldObj().getTileEntity(o[0], o[1], o[2]) instanceof ITEHasMRU)
	    			{
		    			ITEHasMRU t = (ITEHasMRU) tile.getWorldObj().getTileEntity(o[0], o[1], o[2]);
		    			if(t != tile && t != null && !tile.getWorldObj().isRemote)
		    			{
		    				if(mrut.getMRU() < mrut.getMaxMRU())
		    				{
		    					int mru = t.getMRU();
		    					if(mru > mrut.getMaxMRU() - mrut.getMRU())
		    					{
		    						t.setMRU(mru-(mrut.getMaxMRU() - mrut.getMRU()));
		    						mrut.setMRU(mrut.getMaxMRU());
		    					}else
		    					{
		    						t.setMRU(0);
		    						mrut.setMRU(mrut.getMRU()+mru);
		    					}
		    					mrut.setBalance((mrut.getBalance()+t.getBalance())/2);
		    				}
		    			}
	    			}
				}
			}
		}
	}
	
	public static boolean increaseCorruptionAt(World w, float x, float y, float z, int amount)
	{
		Coord3D c = new Coord3D(x,y,z);
		try
		{
			EntityMRUPresence mru = (EntityMRUPresence) getClosestMRUCU(w, c, 32);
			if(mru != null)
			{
				if(!w.isRemote)
				{
					mru.setMRU(mru.getMRU()+amount);
				}
			}else
			{
				createMRUCUAt(w, c, amount, 1.0F+MathUtils.randomFloat(w.rand), true, false);
			}
			List<EntityPlayer> players = w.getEntitiesWithinAABB(EntityPlayer.class, AxisAlignedBB.getBoundingBox(x, y, z, x+1, y+1, z+1).expand(6D, 3D, 6D));
			for(int i = 0; i < players.size(); ++i)
			{
				if(!w.isRemote)
					calculateAndAddMRUCorruptionPE(players.get(i));
			}
		}catch(Exception e)
		{
			return false;
		}
		return false;
	}
	
	public static void calculateAndAddMRUCorruptionPE(EntityPlayer player)
	{
		boolean hasEffect = player.getActivePotionEffect(PotionRegistry.mruCorruptionPotion) != null;
		if(hasEffect)
		{
			int currentDuration = player.getActivePotionEffect(PotionRegistry.mruCorruptionPotion).getDuration();
			int currentModifer = player.getActivePotionEffect(PotionRegistry.mruCorruptionPotion).getAmplifier();
			int newDuration = currentDuration+2;
			int newModifier = currentDuration/2000;
			player.removePotionEffect(PotionRegistry.mruCorruptionPotion.id);
			player.addPotionEffect(new PotionEffect(PotionRegistry.mruCorruptionPotion.id,newDuration,newModifier));
		}else
		{
			player.addPotionEffect(new PotionEffect(PotionRegistry.mruCorruptionPotion.id,200,0));
		}
	}
	
	public static void calculateAndAddPE(EntityPlayer player, Potion potion, int index, int index2)
	{
		boolean hasEffect = player.getActivePotionEffect(potion) != null;
		if(hasEffect)
		{
			int currentDuration = player.getActivePotionEffect(potion).getDuration();
			int currentModifer = player.getActivePotionEffect(potion).getAmplifier();
			int newDuration = currentDuration+index2;
			int newModifier = currentDuration/index;
			player.removePotionEffect(potion.id);
			player.addPotionEffect(new PotionEffect(potion.id,newDuration,newModifier));
		}else
		{
			player.addPotionEffect(new PotionEffect(potion.id,200,0));
		}
	}
	
	public static IMRUPressence getClosestMRUCU(World w, DummyCore.Utils.Coord3D c, int radius)
	{
		List l = w.getEntitiesWithinAABB(IMRUPressence.class, AxisAlignedBB.getBoundingBox(c.x-0.5, c.y-0.5, c.z-0.5, c.x+0.5, c.y+0.5, c.z+0.5).expand(radius, radius/2, radius));
		IMRUPressence ret = null;
		if(!l.isEmpty())
		{
			if(!(l.get(0) instanceof EntityMRUPresence))
			{
				ret = (IMRUPressence) l.get(0);
			}else
			{
				List<EntityMRUPresence> actualList = l;
				double currentDistance = 0;
				double dominatingDistance = 0;
				int dominatingIndex = 0;
				DummyCore.Utils.Coord3D main = new DummyCore.Utils.Coord3D(c.x,c.y,c.z);
				for(int i = 0; i < actualList.size(); ++i)
				{
					EntityMRUPresence pressence = actualList.get(i);
					DummyCore.Utils.Coord3D current = new DummyCore.Utils.Coord3D(pressence.posX,pressence.posY,pressence.posZ);
					DummyDistance dist = new DummyDistance(main,current);
					if(i == 0)
					{
						dominatingIndex = i;
						dominatingDistance = dist.getDistance();
					}else
					{
						currentDistance = dist.getDistance();
						if(currentDistance < dominatingDistance)
						{
							dominatingIndex = i;
							dominatingDistance = dist.getDistance();
						}
					}
				}
				ret = actualList.get(dominatingIndex);
			}
		}
		return ret;
	}
	
	public static void spawnMRUParticles(TileEntity tile, int slotNum)
	{
		IInventory inv = (IInventory) tile;
		if(tile.getWorldObj().isRemote)
		{
			if(inv.getStackInSlot(slotNum) != null && inv.getStackInSlot(slotNum).getItem() instanceof ItemBoundGem && inv.getStackInSlot(slotNum).getTagCompound() != null)
			{
				ItemStack s = inv.getStackInSlot(slotNum);
				int[] o = ItemBoundGem.getCoords(s);
				if(MathUtils.getDifference(tile.xCoord, o[0]) <= 16 && MathUtils.getDifference(tile.yCoord, o[1]) <= 16 && MathUtils.getDifference(tile.zCoord, o[2]) <= 16)
				{
	    			if(tile.getWorldObj().getTileEntity(o[0], o[1], o[2]) != null && tile.getWorldObj().getTileEntity(o[0], o[1], o[2]) instanceof ITEHasMRU)
	    			{
	    				if(tile instanceof TileRayTower)
	    				{
		    				if(tile.getWorldObj().getTileEntity(o[0], o[1], o[2]) instanceof TileRayTower)
		    					EssentialCraftCore.proxy.spawnParticle("mruFX", (float) (o[0]+0.5D+MathUtils.randomDouble(tile.getWorldObj().rand)/5), (float) (o[1]+1.85D+MathUtils.randomDouble(tile.getWorldObj().rand)/5), (float) (o[2]+0.5D+MathUtils.randomDouble(tile.getWorldObj().rand)/5), tile.xCoord-o[0]+MathUtils.randomDouble(tile.getWorldObj().rand)/5, tile.yCoord-o[1]+0.25D+MathUtils.randomDouble(tile.getWorldObj().rand)/5, tile.zCoord-o[2]+MathUtils.randomDouble(tile.getWorldObj().rand)/5);
		    				else
		    					EssentialCraftCore.proxy.spawnParticle("mruFX", (float) (o[0]+0.5D+MathUtils.randomDouble(tile.getWorldObj().rand)/5), (float) (o[1]+0.5D+MathUtils.randomDouble(tile.getWorldObj().rand)/5), (float) (o[2]+0.5D+MathUtils.randomDouble(tile.getWorldObj().rand)/5), tile.xCoord-o[0]+MathUtils.randomDouble(tile.getWorldObj().rand)/5, tile.yCoord-o[1]+1.5D+MathUtils.randomDouble(tile.getWorldObj().rand)/5, tile.zCoord-o[2]+MathUtils.randomDouble(tile.getWorldObj().rand)/5);
	    				}else
	    				{
	    					if(tile.getWorldObj().getTileEntity(o[0], o[1], o[2]) instanceof TileRayTower)
		    					EssentialCraftCore.proxy.spawnParticle("mruFX", (float) (o[0]+0.5D+MathUtils.randomDouble(tile.getWorldObj().rand)/5), (float) (o[1]+1.85D+MathUtils.randomDouble(tile.getWorldObj().rand)/5), (float) (o[2]+0.5D+MathUtils.randomDouble(tile.getWorldObj().rand)/5), tile.xCoord-o[0]+MathUtils.randomDouble(tile.getWorldObj().rand)/5, tile.yCoord-o[1]-1.5D+MathUtils.randomDouble(tile.getWorldObj().rand)/5, tile.zCoord-o[2]+MathUtils.randomDouble(tile.getWorldObj().rand)/5);
		    				else
		    					EssentialCraftCore.proxy.spawnParticle("mruFX", (float) (o[0]+0.5D+MathUtils.randomDouble(tile.getWorldObj().rand)/5), (float) (o[1]+0.5D+MathUtils.randomDouble(tile.getWorldObj().rand)/5), (float) (o[2]+0.5D+MathUtils.randomDouble(tile.getWorldObj().rand)/5), tile.xCoord-o[0]+MathUtils.randomDouble(tile.getWorldObj().rand)/5, tile.yCoord-o[1]+MathUtils.randomDouble(tile.getWorldObj().rand)/5, tile.zCoord-o[2]+MathUtils.randomDouble(tile.getWorldObj().rand)/5);
	    				}
	    			}
				}
			}
		}
	}
	
	@SideOnly(Side.CLIENT)
	public static void renderMRUBeam(TileEntity p_76986_1_, int slotNum, double p_76986_2_, double p_76986_4_, double p_76986_6_, float p_76986_8_, float p_76986_9_)
	{
	    	if(canPlayerSeeMRU(Minecraft.getMinecraft().thePlayer))
	    	{
		        float stability = ((ITEHasMRU)p_76986_1_).getBalance();
		        int color = 0x00ffff;
		        
		        float colorRRender = 0.0F;
		        float colorGRender = 1.0F;
		        float colorBRender = 1.0F;
		        
		        float colorRNormal = 0.0F;
		        float colorGNormal = 1.0F;
		        float colorBNormal = 1.0F;
		        
		        float colorRChaos = 1.0F;
		        float colorGChaos = 0.0F;
		        float colorBChaos = 0.0F;
		        
		        float colorRFrozen = 0.0F;
		        float colorGFrozen = 0.0F;
		        float colorBFrozen = 1.0F;
		        if(stability!=1.0F)
		        {
		        	if(stability<1.0F)
		        	{
		            	float diff = stability;
		            	if(diff < 0.01F)
		            		diff = 0.0F;
		            	colorRRender = (colorRNormal*diff) + (colorRFrozen*(1.0F-diff));
		            	colorGRender = (colorGNormal*diff) + (colorGFrozen*(1.0F-diff));
		            	colorBRender = (colorBNormal*diff) + (colorBFrozen*(1.0F-diff));
		        	}
		        	if(stability>1.0F)
		        	{
		            	float diff = 2.0F-stability;
		            	if(diff < 0.01F)
		            		diff = 0.0F;
		            	colorRRender = (colorRNormal*diff) + (colorRChaos*(1.0F-diff));
		            	colorGRender = (colorGNormal*diff) + (colorGChaos*(1.0F-diff));
		            	colorBRender = (colorBNormal*diff) + (colorBChaos*(1.0F-diff));
		        	}
		        }
				IInventory inv = (IInventory) p_76986_1_;
				if(inv.getStackInSlot(0) != null && inv.getStackInSlot(0).getItem() instanceof ItemBoundGem && inv.getStackInSlot(0).getTagCompound() != null)
				{
					ItemStack s = inv.getStackInSlot(0);
					int[] o = ItemBoundGem.getCoords(s);
					if(MathUtils.getDifference(p_76986_1_.xCoord, o[0]) <= 16 && MathUtils.getDifference(p_76986_1_.yCoord, o[1]) <= 16 && MathUtils.getDifference(p_76986_1_.zCoord, o[2]) <= 16)
					{
		    			if(p_76986_1_.getWorldObj().getTileEntity(o[0], o[1], o[2]) != null && p_76986_1_.getWorldObj().getTileEntity(o[0], o[1], o[2]) instanceof ITEHasMRU)
		    			{
		    		        float f21 = (float)0 + p_76986_9_;
		    		        float f31 = MathHelper.sin(f21 * 0.2F) / 2.0F + 0.5F;
		    		        f31 = (f31 * f31 + f31) * 0.2F;
		    		        float f4;
		    		        float f5;
		    		        float f6;
		    		        GL11.glPushMatrix();
		    		        //Is the main tile a RayTower
		    		        if(p_76986_1_.getWorldObj().getTileEntity(p_76986_1_.xCoord, p_76986_1_.yCoord, p_76986_1_.zCoord) instanceof TileRayTower)
		    		        {
		    		        	//Is the transfering tile a RayTower
			    		        if(p_76986_1_.getWorldObj().getTileEntity(o[0], o[1], o[2]) instanceof TileRayTower)
			    		        {
				    		        f4 = (float)(o[0] - p_76986_1_.xCoord);
						            f5 = (float)(o[1] - (double)(f31 + p_76986_1_.yCoord-0.15F));
				    		        f6 = (float)(o[2] - p_76986_1_.zCoord);
			    		        }else
			    		        {
				    		        f4 = (float)(o[0] - p_76986_1_.xCoord);
						            f5 = (float)(o[1] - (double)(f31 + p_76986_1_.yCoord+1.3F));
				    		        f6 = (float)(o[2] - p_76986_1_.zCoord);
			    		        }
		    		        	GL11.glTranslatef((float)p_76986_2_+0.5F, (float)p_76986_4_ + 1.9F, (float)p_76986_6_+0.5F);
		    		        }else
		    		        {
		    		        	//Is the transfering tile a RayTower
			    		        if(p_76986_1_.getWorldObj().getTileEntity(o[0], o[1], o[2]) instanceof TileRayTower)
			    		        {
				    		        f4 = (float)(o[0] - p_76986_1_.xCoord);
						            f5 = (float)(o[1] - (double)(f31 + p_76986_1_.yCoord-1.55F));
				    		        f6 = (float)(o[2] - p_76986_1_.zCoord);
				    		        GL11.glTranslatef((float)p_76986_2_+0.5F, (float)p_76986_4_ + 0.5F, (float)p_76986_6_+0.5F);
			    		        }else
			    		        {
				    		        f4 = (float)(o[0] - p_76986_1_.xCoord);
						            f5 = (float)(o[1] - (double)(f31 + p_76986_1_.yCoord-0.15F));
				    		        f6 = (float)(o[2] - p_76986_1_.zCoord);
				    		        GL11.glTranslatef((float)p_76986_2_+0.5F, (float)p_76986_4_ + 0.5F, (float)p_76986_6_+0.5F);
			    		        }
		    		        	
		    		        }
		    		        float f7 = MathHelper.sqrt_float(f4 * f4 + f6 * f6);
		    		        float f8 = MathHelper.sqrt_float(f4 * f4 + f5 * f5 + f6 * f6);
		    		        GL11.glRotatef((float)(-Math.atan2((double)f6, (double)f4)) * 180.0F / (float)Math.PI - 90.0F, 0.0F, 1.0F, 0.0F);
		    		        GL11.glRotatef((float)(-Math.atan2((double)f7, (double)f5)) * 180.0F / (float)Math.PI - 90.0F, 1.0F, 0.0F, 0.0F);
		    		        Tessellator tessellator = Tessellator.instance;
		    		        RenderHelper.disableStandardItemLighting();
		    		        GL11.glAlphaFunc(GL11.GL_GREATER, 0.1F);
		    		        GL11.glDisable(GL11.GL_CULL_FACE);
		    		        MiscUtils.bindTexture("essentialcraft","textures/special/mru_beam.png");
		    		        GL11.glShadeModel(GL11.GL_SMOOTH);
		    		        GL11.glEnable(GL11.GL_BLEND);
		    		        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE);
		    		        GL11.glDisable(GL11.GL_ALPHA_TEST);
		    	            float f9 = 1;
		    	            float f10 = MathHelper.sqrt_float(f4 * f4 + f5 * f5 + f6 * f6) / 32.0F - (PlayerTickHandler.tickAmount + p_76986_9_) * 0.01F;
		    		        tessellator.startDrawing(5);
		    		        byte b0 = 8;
		
		    		        for (int i = 0; i <= b0; ++i)
		    		        {
		    		            float f11 = MathHelper.sin((float)(i % b0) * (float)Math.PI * 2.0F / (float)b0) * 0.75F * 0.2F;
		    		            float f12 = MathHelper.cos((float)(i % b0) * (float)Math.PI * 2.0F / (float)b0) * 0.75F * 0.2F;
		    		            float f13 = (float)(i % b0) * 1.0F / (float)b0;
		    		            tessellator.setColorRGBA_F(colorRRender, colorGRender, colorBRender, 10F);
		    		            tessellator.addVertexWithUV((double)(f11), (double)(f12), 0.0D, (double)f13, (double)f10);
		    		            tessellator.setColorRGBA_F(colorRRender, colorGRender, colorBRender, 10F);
		    		            tessellator.addVertexWithUV((double)f11, (double)f12, (double)f8, (double)f13, (double)f9);
		    		        }
		
		    		        tessellator.draw();
		    		        GL11.glEnable(GL11.GL_CULL_FACE);
		    		        GL11.glDisable(GL11.GL_BLEND);
		    		        GL11.glShadeModel(GL11.GL_FLAT);
		    		        GL11.glEnable(GL11.GL_ALPHA_TEST);
		    		        RenderHelper.enableStandardItemLighting();
		    		        GL11.glPopMatrix();
		    			}
					}
				}
			}
    	}
	
	public static float getGenResistance(int index, EntityPlayer p)
	{
		float resistance = 0F;
		for(int i = 0; i < p.inventory.armorInventory.length; ++i)
		{
			ItemStack armorStk = p.inventory.armorInventory[i];
			if(armorStk != null)
			{
				Item itm = armorStk.getItem();
				if(ApiCore.reductionsTable.containsKey(itm))
				{
					ArrayList<Float> lst = ApiCore.reductionsTable.get(itm);
					resistance += lst.get(index);
				}
			}
		}
		resistance /= 4;
		IInventory baublesInventory = BaublesApi.getBaubles(p);
		if(baublesInventory != null)
		{
			for(int i = 0; i < baublesInventory.getSizeInventory(); ++i)
			{
				ItemStack stk = baublesInventory.getStackInSlot(i);
				if(stk != null && stk.getItem() instanceof ItemBaublesWearable && MiscUtils.getStackTag(stk).hasKey("type"))
				{
					NBTTagCompound bTag = MiscUtils.getStackTag(stk);
					ArrayList<Float> fltLst = new ArrayList();
					fltLst.add(bTag.getFloat("mrucr"));
					fltLst.add(bTag.getFloat("mrurr"));
					fltLst.add(bTag.getFloat("car"));
					resistance += fltLst.get(index);
				}
			}
		}
		
		float retFlt = 1.0F - resistance;
		if(retFlt < 0)retFlt = 0;
		return retFlt;
	}
	
	public static GameProfile EC3FakePlayerProfile = new GameProfile(UUID.fromString("5cd89d0b-e9ba-0000-89f4-b5dbb05963da"), "[EC3]");
}
