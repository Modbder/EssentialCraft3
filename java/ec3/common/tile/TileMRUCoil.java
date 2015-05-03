package ec3.common.tile;

import java.util.ArrayList;
import java.util.List;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraftforge.common.config.Configuration;
import DummyCore.Utils.Coord3D;
import DummyCore.Utils.DataStorage;
import DummyCore.Utils.DummyData;
import DummyCore.Utils.Lightning;
import DummyCore.Utils.MathUtils;
import DummyCore.Utils.MiscUtils;
import ec3.api.ApiCore;
import ec3.api.EnumStructureType;
import ec3.api.ITEHasMRU;
import ec3.common.block.BlocksCore;
import ec3.common.item.ItemBoundGem;
import ec3.common.item.ItemPlayerList;
import ec3.utils.common.ECUtils;

public class TileMRUCoil extends TileMRUGeneric{
	public float rad = 1F;
	
	public Lightning localLightning;
	
	public Lightning monsterLightning;
	
	public int height;
	
	public int ticksBeforeStructureCheck, lightningTicks;
	
	public boolean isStructureCorrect;
	
	public static float cfgMaxMRU = ApiCore.DEVICE_MAX_MRU_GENERIC*10;
	public static boolean generatesCorruption = false;
	public static int genCorruption = 50;
	public static int mruUsage = 200;
	public static boolean hurtPlayers = true;
	public static boolean hurtPassive = true;
	public static float damage = 18F;
	public static float radiusModifier = 1F;
	
	public TileMRUCoil()
	{
		 super();
		this.maxMRU = (int) cfgMaxMRU;
		this.setSlotsNum(2);
	}
	
	public boolean isStructureCorrect()
	{
		return isStructureCorrect && this.getMRU() > mruUsage;
	}
	
	public void initStructure()
	{
		this.height = 0;
		this.rad = 0;
		this.isStructureCorrect = false;
		int dy = this.yCoord-1;
		while(this.worldObj.getBlock(xCoord, dy, zCoord) == BlocksCore.mruCoilHardener)
		{
			--dy;
			++this.height;
		}
		List<Block> allowed = ECUtils.allowedBlocks.get(EnumStructureType.MRUCoil);
		Block b_0 = this.worldObj.getBlock(xCoord, dy, zCoord);
		Block b_1 = this.worldObj.getBlock(xCoord+1, dy, zCoord);
		Block b_2 = this.worldObj.getBlock(xCoord-1, dy, zCoord);
		Block b_3 = this.worldObj.getBlock(xCoord+1, dy, zCoord+1);
		Block b_4 = this.worldObj.getBlock(xCoord-1, dy, zCoord+1);
		Block b_5 = this.worldObj.getBlock(xCoord+1, dy, zCoord-1);
		Block b_6 = this.worldObj.getBlock(xCoord-1, dy, zCoord-1);
		Block b_7 = this.worldObj.getBlock(xCoord, dy, zCoord+1);
		Block b_8 = this.worldObj.getBlock(xCoord, dy, zCoord-1);
		
		Block b_0_0 = this.worldObj.getBlock(xCoord, dy, zCoord-2);
		Block b_0_1 = this.worldObj.getBlock(xCoord, dy, zCoord+2);
		Block b_0_2 = this.worldObj.getBlock(xCoord+2, dy, zCoord);
		Block b_0_3 = this.worldObj.getBlock(xCoord-2, dy, zCoord);
		
		Block b_1_0 = this.worldObj.getBlock(xCoord, dy, zCoord-3);
		Block b_1_1 = this.worldObj.getBlock(xCoord+1, dy, zCoord-3);
		Block b_1_2 = this.worldObj.getBlock(xCoord-1, dy, zCoord-3);
		Block b_1_3 = this.worldObj.getBlock(xCoord, dy, zCoord+3);
		Block b_1_4 = this.worldObj.getBlock(xCoord+1, dy, zCoord+3);
		Block b_1_5 = this.worldObj.getBlock(xCoord-1, dy, zCoord+3);
		Block b_1_6 = this.worldObj.getBlock(xCoord+3, dy, zCoord);
		Block b_1_7 = this.worldObj.getBlock(xCoord+3, dy, zCoord-1);
		Block b_1_8 = this.worldObj.getBlock(xCoord+3, dy, zCoord+1);
		Block b_1_9 = this.worldObj.getBlock(xCoord-3, dy, zCoord);
		Block b_1_10 = this.worldObj.getBlock(xCoord-3, dy, zCoord-1);
		Block b_1_11 = this.worldObj.getBlock(xCoord-3, dy, zCoord+1);
		List<Block> cBl = new ArrayList<Block>();
		cBl.add(b_0);
		cBl.add(b_1);
		cBl.add(b_2);
		cBl.add(b_3);
		cBl.add(b_4);
		cBl.add(b_5);
		cBl.add(b_6);
		cBl.add(b_7);
		cBl.add(b_8);
		cBl.add(b_0_0);
		cBl.add(b_0_1);
		cBl.add(b_0_2);
		cBl.add(b_0_3);
		cBl.add(b_1_0);
		cBl.add(b_1_1);
		cBl.add(b_1_2);
		cBl.add(b_1_3);
		cBl.add(b_1_4);
		cBl.add(b_1_5);
		cBl.add(b_1_6);
		cBl.add(b_1_7);
		cBl.add(b_1_8);
		cBl.add(b_1_9);
		cBl.add(b_1_10);
		cBl.add(b_1_11);
		
		if(allowed.containsAll(cBl))
		{
			isStructureCorrect = true;
			rad = height+3;
		}
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public void updateEntity()
	{
		if(!this.worldObj.isBlockIndirectlyGettingPowered(xCoord, yCoord, zCoord))
		{
			if(this.worldObj.isRemote)
			{
				if(this.isStructureCorrect())
				{
					if(this.localLightning == null)
					{
						{
							this.localLightning = new Lightning(this.worldObj.rand, new Coord3D(0.5F,0.9F,0.5F), new Coord3D(0.5F+MathUtils.randomDouble(this.worldObj.rand),0.9F+MathUtils.randomDouble(this.worldObj.rand),0.5F+MathUtils.randomDouble(this.worldObj.rand)), 0.1F, 1.0F, 0.2F, 1.0F);
						}
					}else
					{
						if(this.localLightning.renderTicksExisted >= 20)
							this.localLightning = null;
					}
				}else
					this.localLightning = null;
			}
			if(this.monsterLightning != null)
			{
				if(!this.worldObj.isRemote)
					++lightningTicks;
				if(this.monsterLightning.renderTicksExisted >= 20 && this.worldObj.isRemote)
					this.monsterLightning = null;
				if(this.lightningTicks >= 20 && !this.worldObj.isRemote)
				{
					this.lightningTicks = 0;
					this.monsterLightning = null;
				}
			}
			if(this.isStructureCorrect)
			{
				List<EntityLivingBase> entities = this.worldObj.getEntitiesWithinAABB(EntityLivingBase.class, AxisAlignedBB.getBoundingBox(xCoord, yCoord, zCoord, xCoord+1, yCoord+1, zCoord+1).expand(rad*radiusModifier, rad*radiusModifier, rad*radiusModifier));
				if(entities != null && !entities.isEmpty() && this.monsterLightning == null)
				{
					Ford:for(EntityLivingBase b : entities)
					{
						
						if(b instanceof EntityPlayer)
						{
							if(!((EntityPlayer)b).capabilities.isCreativeMode && hurtPlayers)
							{
								ItemStack is = this.getStackInSlot(1);
								if(is != null && is.getItem() instanceof ItemPlayerList)
								{
						    		NBTTagCompound itemTag = MiscUtils.getStackTag(is);
						    		if(!itemTag.hasKey("usernames"))
						    			itemTag.setString("usernames", "||username:null");
						    		String str = itemTag.getString("usernames");
						    		DummyData[] dt = DataStorage.parseData(str);
						    		for(int i = 0; i < dt.length; ++i)
						    		{
						    			String username = dt[i].fieldValue;
						    			String playerName = b.getCommandSenderName();
						    			if(username.equals(playerName))
						    			{
						    				continue Ford;
						    			}
						    		}
						    		attack(b);
						    		
								}else
								{
									attack(b);
								}
							}
						}else
						{
							if(!(b instanceof IMob) && !hurtPassive)
								continue Ford;
							attack(b);
							break Ford;
						}
					}
				}
			}
			if(ticksBeforeStructureCheck <= 0)
			{
				ticksBeforeStructureCheck = 20;
				this.initStructure();
			}else
			{
				--ticksBeforeStructureCheck;
			}
		}
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
	}
	
	public void attack(EntityLivingBase b)
	{
		if(this.getMRU() > mruUsage && !b.isDead && b.hurtTime <= 0 && b.hurtResistantTime <= 0)
		{
			if(generatesCorruption)
				ECUtils.increaseCorruptionAt(worldObj, xCoord, yCoord, zCoord, this.worldObj.rand.nextInt(genCorruption));
			b.attackEntityFrom(DamageSource.magic, damage);
			if(this.worldObj.isRemote && this.monsterLightning == null)
				this.worldObj.playSound(xCoord+0.5F,yCoord+0.5F,zCoord+0.5F, "essentialcraft:sound.lightning_hit", 2F, 2F, false);
			this.monsterLightning = new Lightning(this.worldObj.rand, new Coord3D(0.5F,0.8F,0.5F), new Coord3D(b.posX-this.xCoord+0.5D,b.posY-this.yCoord+0.8D,b.posZ-this.zCoord+0.5D), 0.1F, 1F,0.0F,0.7F);
			if(!this.worldObj.isRemote)
				this.setMRU(this.getMRU()-mruUsage);

		}
	}
	
    @SideOnly(Side.CLIENT)
    @Override
    public AxisAlignedBB getRenderBoundingBox()
    {
    	AxisAlignedBB bb = INFINITE_EXTENT_AABB;
    	return bb;
    }
    
    public static void setupConfig(Configuration cfg)
    {
    	try
    	{
	    	cfg.load();
	    	String[] cfgArrayString = cfg.getStringList("MRUCoilSettings", "tileentities", new String[]{
	    			"Max MRU:"+ApiCore.DEVICE_MAX_MRU_GENERIC*10,
	    			"MRU Usage per hit:1000",
	    			"Can this device actually generate corruption:false",
	    			"The amount of corruption generated each tick(do not set to 0!):20",
	    			"Can the coil attack players, and, therefore, requires a Whitelist:true",
	    			"Can the coil attack passive entities, like sheep:true",
	    			"Damage per hit:18.0",
	    			"Radius multiplier:1.0"
	    			},"");
	    	String dataString="";
	    	
	    	for(int i = 0; i < cfgArrayString.length; ++i)
	    		dataString+="||"+cfgArrayString[i];
	    	
	    	DummyData[] data = DataStorage.parseData(dataString);
	    	
	    	mruUsage = Integer.parseInt(data[1].fieldValue);
	    	cfgMaxMRU = Float.parseFloat(data[0].fieldValue);
	    	generatesCorruption = Boolean.parseBoolean(data[2].fieldValue);
	    	genCorruption = Integer.parseInt(data[3].fieldValue);
	    	hurtPlayers = Boolean.parseBoolean(data[4].fieldValue);
	    	hurtPassive = Boolean.parseBoolean(data[5].fieldValue);
	    	damage = Float.parseFloat(data[6].fieldValue);
	    	radiusModifier = Float.parseFloat(data[7].fieldValue);
	    	
	    	cfg.save();
    	}catch(Exception e)
    	{
    		return;
    	}
    }
}
