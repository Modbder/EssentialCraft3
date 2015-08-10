package ec3.common.tile;

import java.lang.ref.WeakReference;
import java.util.List;

import ec3.api.IItemRequiresMRU;
import ec3.utils.common.ECUtils;
import DummyCore.Utils.MathUtils;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.init.Items;
import net.minecraft.item.ItemRedstone;
import net.minecraft.item.ItemReed;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.INetHandlerPlayClient;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.WorldServer;
import net.minecraftforge.common.util.FakePlayer;
import net.minecraftforge.common.util.FakePlayerFactory;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.event.ForgeEventFactory;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent.Action;

public class TileRightClicker extends TileMRUGeneric
{
	public boolean wasPowered = false;
	public int rotation = 0;
	public FakePlayer fakePlayer;
	
	public ForgeDirection getRotation()
	{
		return ForgeDirection.getOrientation(rotation);
	}
	
	public void finishClick(int slot, boolean setupAll)
	{
		int cycle = setupAll ? 9 : 1;
		for(int i = 0; i < cycle; ++i)
		{
			
			if(fakePlayer.getCurrentEquippedItem() != null)
			{
				ItemStack setted = fakePlayer.inventory.getCurrentItem().copy();
				if(setted != null && setted.getItem() instanceof IItemRequiresMRU)
				{
					IItemRequiresMRU iReq = (IItemRequiresMRU) setted.getItem();
					int current = iReq.getMRU(setted);
					int thM = this.getMRU();
					int thMM = this.getMaxMRU();
					if(thM < thMM)
					{
						int diff = thM - thMM;
						if(diff <= current)
						{
							iReq.setMRU(setted, -diff);
							this.setMRU(this.getMRU()+diff);
							if(this.getMRU() < 0)
								this.setMRU(0);
						}else
						{
							iReq.setMRU(setted, -current);
							this.setMRU(this.getMRU()+current);
							if(this.getMRU() < 0)
								this.setMRU(0);
						}
					}
				}
				this.setInventorySlotContents(1+i, setted);
				if(setted.stackSize <= 0)
					this.setInventorySlotContents(1+i, null);
			}else
				this.setInventorySlotContents(1+i, null);
			
			fakePlayer.inventory.currentItem += 1;
		}
		fakePlayer.inventory.setInventorySlotContents(fakePlayer.inventory.currentItem, null);
		fakePlayer = null;
	}
	
	public boolean canAct()
	{
		return true;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void setupFake(int slot, boolean setupAll)
	{
		ForgeDirection r = this.getRotation();
		fakePlayer = (FakePlayer) new WeakReference(FakePlayerFactory.get((WorldServer) this.worldObj, ECUtils.EC3FakePlayerProfile)).get();
		fakePlayer.inventory.mainInventory = new ItemStack[9];
		fakePlayer.inventory.currentItem = 0;
		if(setupAll)
		{
			for(int i = 0; i < 9; ++i)
			{
				fakePlayer.inventory.setInventorySlotContents(fakePlayer.inventory.currentItem+i, this.getStackInSlot(1+i) == null ? null : this.getStackInSlot(1+i).copy());
				if(i+1 != slot)
					this.setInventorySlotContents(i+1, null);
			}
		}
		
		if(this.getStackInSlot(slot) != null)
		{
		ItemStack setted = this.getStackInSlot(slot).copy();
		if(setted != null && setted.getItem() instanceof IItemRequiresMRU)
		{
			IItemRequiresMRU iReq = (IItemRequiresMRU) setted.getItem();
			int current = iReq.getMRU(setted);
			int max = iReq.getMaxMRU(setted);
			int thM = this.getMRU();
			if(current < max)
			{
				int diff = current - max;
				if(diff <= thM)
				{
					iReq.setMRU(setted, diff);
					this.setMRU(this.getMRU()-diff);
					if(this.getMRU() < 0)
						this.setMRU(0);
				}else
				{
					iReq.setMRU(setted, thM);
					this.setMRU(0);
				}
			}
		}
		fakePlayer.inventory.setInventorySlotContents(fakePlayer.inventory.currentItem, setted);
		this.setInventorySlotContents(slot, null);
		float rotation = 0F;
		
		if(this.rotation == 2)
			rotation = 0;
		if(this.rotation == 3)
			rotation = 90;
		if(this.rotation == 4)
			rotation = 180;
		if(this.rotation == 5)
			rotation = 270;
		fakePlayer.setPositionAndRotation(xCoord+0.5D+r.offsetX, yCoord+0.5D+r.offsetY, zCoord+0.5D+r.offsetZ, rotation, this.rotation == 0 ? -90 : this.rotation == 1 ? 90 : 0);
		}
	}
	
	@SuppressWarnings("unchecked")
	public boolean rightClick(boolean sneak)
	{
		ForgeDirection faceDir = this.getRotation();
	    int dx = faceDir.offsetX;
	    int dy = faceDir.offsetY;
	    int dz = faceDir.offsetZ;
	    int x = xCoord + dx;
	    int y = yCoord + dy;
	    int z = zCoord + dz;
	    fakePlayer.setPosition(x + 0.5, y + 0.5 - fakePlayer.eyeHeight, z + 0.5);
	    fakePlayer.rotationPitch = faceDir.offsetY * -90;
	    fakePlayer.setSneaking(sneak);
	    
	    switch (faceDir) 
	    {
            case NORTH:
            	fakePlayer.rotationYaw = 180;
                break;
            case SOUTH:
            	fakePlayer.rotationYaw = 0;
                break;
            case WEST:
            	fakePlayer.rotationYaw = 90;
                break;
            case EAST:
            	fakePlayer.rotationYaw = -90;
            default:
            	break;
        }
	    
        try 
        {
            PlayerInteractEvent event = ForgeEventFactory.onPlayerInteract(fakePlayer, Action.RIGHT_CLICK_AIR, x, y, z, faceDir.ordinal(), worldObj);
            if (event.isCanceled()) return false;
            
            Block block = worldObj.getBlock(x, y, z);
            List<EntityLivingBase> detectedEntities = worldObj.getEntitiesWithinAABB(EntityLivingBase.class, AxisAlignedBB.getBoundingBox(x, y, z, x + 1, y + 1, z + 1));
            
            Entity entity = detectedEntities.isEmpty() ? null : detectedEntities.get(worldObj.rand.nextInt(detectedEntities.size()));
            
            ItemStack stack = fakePlayer.getCurrentEquippedItem();
            if(stack == null) stack = new ItemStack(Items.stick,0,0);
	        if (stack.getItem().itemInteractionForEntity(stack, fakePlayer, (EntityLivingBase) entity)) return true;
	        if (entity instanceof EntityAnimal && ((EntityAnimal) entity).interact(fakePlayer)) return true;
            
            if (stack.getItem().onItemUseFirst(stack, fakePlayer, worldObj, x, y, z, faceDir.ordinal(), dx, dy, dz)) return true;
            if (!worldObj.isAirBlock(x, y, z) && block.onBlockActivated(worldObj, x, y, z, fakePlayer, faceDir.ordinal(), dx, dy, dz)) return true;
            
            boolean isGoingToShift = false;              
            if(stack != null)
            {
	            if(stack.getItem() instanceof ItemReed || stack.getItem() instanceof ItemRedstone)
	            {
	                isGoingToShift = true;
	            }
	                int useX = isGoingToShift ? xCoord : x;
	                int useY = isGoingToShift ? yCoord : y;
	                int useZ = isGoingToShift ? zCoord : z;
	                if (stack.getItem().onItemUse(stack, fakePlayer, worldObj, useX, useY, useZ, faceDir.ordinal(), dx, dy, dz)) return true;
            }
            
            ItemStack copy = stack.copy();
            fakePlayer.setCurrentItemOrArmor(0, stack.getItem().onItemRightClick(stack, worldObj, fakePlayer));
            if (!copy.isItemEqual(stack)) return true;
            
            return false;
        } 
        catch (Throwable e)
        {
	        return true;
        }
	}

	public TileRightClicker()
	{
		super();
		this.setSlotsNum(11);
		this.setMaxMRU(5000);
	}
	 
	@Override
    public void readFromNBT(NBTTagCompound par1NBTTagCompound)
    {
    	super.readFromNBT(par1NBTTagCompound);
    	rotation = par1NBTTagCompound.getInteger("rotation");
    	wasPowered = par1NBTTagCompound.getBoolean("powered");
    }
    
    @Override
    public void writeToNBT(NBTTagCompound par1NBTTagCompound)
    {
    	super.writeToNBT(par1NBTTagCompound);
    	par1NBTTagCompound.setInteger("rotation", rotation);
    	par1NBTTagCompound.setBoolean("powered", wasPowered);
    }
    
    @Override
    public void updateEntity() 
    {
    	super.updateEntity();
    	
    	ECUtils.manage(this, 0);
    	
    	if(this.worldObj.isBlockIndirectlyGettingPowered(xCoord, yCoord, zCoord) && !wasPowered && canAct())
    	{
    		if(this.getBlockMetadata() <= 1)
    		{
	    		if(!this.worldObj.isRemote)
	    		{
		    		setupFake(1,false);
		    		rightClick(this.getBlockMetadata()%2 == 1);
		    		finishClick(1,false);
	    		}
    		}
    		if(this.getBlockMetadata() == 2 || this.getBlockMetadata() == 3)
    		{
	    		if(!this.worldObj.isRemote)
	    		{
	    			for(int i = 0; i < 9; ++i)
	    			{
			    		setupFake(1+i,false);
			    		rightClick(this.getBlockMetadata()%2 == 1);
			    		finishClick(1+i,false);
	    			}
	    		}
    		}
    		if(this.getBlockMetadata() == 4 || this.getBlockMetadata() == 5)
    		{
	    		if(!this.worldObj.isRemote)
	    		{
	    			for(int i = 0; i < 9; ++i)
	    			{
			    		setupFake(1+i,true);
			    		rightClick(this.getBlockMetadata()%2 == 1);
			    		finishClick(1+i,true);
	    			}
	    		}
    		}
    		wasPowered = true;
    	}
    	if(wasPowered && !this.worldObj.isBlockIndirectlyGettingPowered(xCoord, yCoord, zCoord))
    		wasPowered = false;
    	
    	if(wasPowered)
    	{
    		this.worldObj.spawnParticle("reddust", xCoord+0.5D+MathUtils.randomDouble(this.worldObj.rand)/1.5D, yCoord+0.5D+MathUtils.randomDouble(this.worldObj.rand)/1.5D, zCoord+0.5D+MathUtils.randomDouble(this.worldObj.rand)/1.5D, 1, 0, 0);
    	}
    }

	@Override
    public void onDataPacket(NetworkManager net, S35PacketUpdateTileEntity pkt)
    {
		if(net.getNetHandler() instanceof INetHandlerPlayClient)
			if(pkt.func_148853_f() == -10)
			{
				boolean reRender = pkt.func_148857_g().getInteger("rotation") != this.rotation || ItemStack.loadItemStackFromNBT(NBTTagList.class.cast(pkt.func_148857_g().getTagList("Items", 10)).getCompoundTagAt(10)) != this.getStackInSlot(10);
				this.readFromNBT(pkt.func_148857_g());
				if(reRender)
					this.worldObj.markBlockRangeForRenderUpdate(xCoord-1, yCoord-1, zCoord-1, xCoord+1, yCoord+1, zCoord+1);
			}
    }

	@Override
	public int[] getOutputSlots() {
		return new int[]{0};
	}
	
}
