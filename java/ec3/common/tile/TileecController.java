package ec3.common.tile;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import DummyCore.Utils.BlockPosition;
import DummyCore.Utils.Coord3D;
import DummyCore.Utils.DataStorage;
import DummyCore.Utils.DummyData;
import DummyCore.Utils.MiscUtils;
import ec3.api.EnumStructureType;
import ec3.api.IMRUPressence;
import ec3.api.IStructurePiece;
import ec3.api.ITEHasMRU;
import ec3.utils.common.ECUtils;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.INetHandlerPlayClient;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraftforge.common.config.Configuration;

public class TileecController extends TileEntity implements ITEHasMRU{
	
	//============================Variables================================//
	public int syncTick;
	public int structureCheckTick;
	public int mru;
	public int maxMRU = 60000;
	public float resistance;
	public Coord3D upperCoord;
	public Coord3D lowerCoord;
	
	public boolean isCorrect;
	
	public float balance;
	
	public UUID uuid = UUID.randomUUID();
	
	public List<BlockPosition> blocksInStructure = new ArrayList<BlockPosition>();
	
	public static float cfgMaxMRU = 60000;
	public static float cfgMRUPerStorage = 100000;
	//===========================Functions=================================//
	
	@Override
	public void updateEntity() 
	{
		//Retrying structure checks. Basically, every 10 seconds the structure will re-initialize//
		if(structureCheckTick == 0)
		{
			isCorrect = this.checkStructure();
			structureCheckTick = 200;
		}else
			--this.structureCheckTick;
		
		//Sending the sync packets to the CLIENT. 
		if(syncTick == 0)
		{
			if(!this.worldObj.isRemote)
				MiscUtils.sendPacketToAllAround(worldObj, getDescriptionPacket(), xCoord, yCoord, zCoord, this.worldObj.provider.dimensionId, 16);
			syncTick = 30;
		}else
			--this.syncTick;
	}
	
	@SuppressWarnings("unchecked")
	public IMRUPressence getMRUCU()
	{
		if(isCorrect)
		{
			List<IMRUPressence> pList = this.worldObj.getEntitiesWithinAABB(IMRUPressence.class, AxisAlignedBB.getBoundingBox(lowerCoord.x, lowerCoord.y, lowerCoord.z, upperCoord.x, upperCoord.y, upperCoord.z));
			if(pList != null && !pList.isEmpty())
				return pList.get(0);
		}
		return null;
	}
	
	@Override
    public Packet getDescriptionPacket()
    {
        NBTTagCompound nbttagcompound = new NBTTagCompound();
        this.writeToNBT(nbttagcompound);
        return new S35PacketUpdateTileEntity(this.xCoord, this.yCoord, this.zCoord, -10, nbttagcompound);
    }
	
	@Override
    public void onDataPacket(NetworkManager net, S35PacketUpdateTileEntity pkt)
    {
		if(net.getNetHandler() instanceof INetHandlerPlayClient)
			if(pkt.func_148853_f() == -10)
				this.readFromNBT(pkt.func_148857_g());
    }

	
	@Override
    public void readFromNBT(NBTTagCompound i)
    {
		super.readFromNBT(i);
		ECUtils.loadMRUState(this, i);
    }
	
	@Override
    public void writeToNBT(NBTTagCompound i)
    {
    	super.writeToNBT(i);
    	ECUtils.saveMRUState(this, i);
    }
	
	/**
	 * Checking the shape of the structure;
	 * @return - false, if the structure is incorrect, true otherwise
	 */
	public boolean checkStructure()
	{
		resistance = 0F;
		maxMRU = (int) cfgMaxMRU;
		blocksInStructure.clear(); //Clearing the list of blocks to reinitialise it
		//Base variables setup//
		int minX = 0;
		int minY = 0;
		int minZ = 0;
		int maxX = 0;
		int maxY = 0;
		int maxZ = 0;
		int checkInt0 = 0;
		List<?> allowedBlocks = ECUtils.allowedBlocks.get(EnumStructureType.MRUCUContaigementChamber); //Getting the list of allowed blocks in the structure
		//Trying to find the whole shape of a structure//
		while(allowedBlocks.contains(this.worldObj.getBlock(xCoord+checkInt0, yCoord, zCoord)))
		{
			++checkInt0;
		}
		--checkInt0;
		if(checkInt0 > 0)
		{
			maxX = checkInt0;
		}
		checkInt0 = 0;
		while(allowedBlocks.contains(this.worldObj.getBlock(xCoord+checkInt0, yCoord, zCoord)))
		{
			--checkInt0;
		}
		++checkInt0;
		if(checkInt0 < 0)
		{
			minX = checkInt0;
		}
		if(maxX == 0)
		{
			checkInt0 = 0;
			while(allowedBlocks.contains(this.worldObj.getBlock(xCoord, yCoord, zCoord+checkInt0)))
			{
				++checkInt0;
			}
			--checkInt0;
			if(checkInt0 > 0)
			{
				maxZ = checkInt0;
			}
		}
		if(minX == 0)
		{
			checkInt0 = 0;
			while(allowedBlocks.contains(this.worldObj.getBlock(xCoord, yCoord, zCoord+checkInt0)))
			{
				--checkInt0;
			}
			++checkInt0;
			if(checkInt0 < 0)
			{
				minZ = checkInt0;
			}
		}
		if(maxX == 0 && maxZ != 0)
		{
			checkInt0 = 0;
			while(allowedBlocks.contains(this.worldObj.getBlock(xCoord+checkInt0, yCoord, zCoord+maxZ)))
			{
				++checkInt0;
			}
			--checkInt0;
			if(checkInt0 > 0)
			{
				maxX = checkInt0;
			}
		}
		if(minX == 0 && maxZ != 0)
		{
			checkInt0 = 0;
			while(allowedBlocks.contains(this.worldObj.getBlock(xCoord+checkInt0, yCoord, zCoord+maxZ)))
			{
				--checkInt0;
			}
			++checkInt0;
			if(checkInt0 < 0)
			{
				minX = checkInt0;
			}
		}
		if(maxX == 0 && minZ != 0)
		{
			checkInt0 = 0;
			while(allowedBlocks.contains(this.worldObj.getBlock(xCoord+checkInt0, yCoord, zCoord+minZ)))
			{
				++checkInt0;
			}
			--checkInt0;
			if(checkInt0 > 0)
			{
				maxX = checkInt0;
			}
		}
		if(minX == 0 && minZ != 0)
		{
			checkInt0 = 0;
			while(allowedBlocks.contains(this.worldObj.getBlock(xCoord+checkInt0, yCoord, zCoord+minZ)))
			{
				--checkInt0;
			}
			++checkInt0;
			if(checkInt0 < 0)
			{
				minX = checkInt0;
			}
		}
		if(maxZ == 0 && maxX != 0)
		{
			checkInt0 = 0;
			while(allowedBlocks.contains(this.worldObj.getBlock(xCoord+maxX, yCoord, zCoord+checkInt0)))
			{
				++checkInt0;
			}
			--checkInt0;
			if(checkInt0 > 0)
			{
				maxZ = checkInt0;
			}
		}
		if(minZ == 0 && maxX != 0)
		{
			checkInt0 = 0;
			while(allowedBlocks.contains(this.worldObj.getBlock(xCoord+maxX, yCoord, zCoord+checkInt0)))
			{
				--checkInt0;
			}
			++checkInt0;
			if(checkInt0 < 0)
			{
				minZ = checkInt0;
			}
		}
		if(maxZ == 0 && minX != 0)
		{
			checkInt0 = 0;
			while(allowedBlocks.contains(this.worldObj.getBlock(xCoord+minX, yCoord, zCoord+checkInt0)))
			{
				++checkInt0;
			}
			--checkInt0;
			if(checkInt0 > 0)
			{
				maxZ = checkInt0;
			}
		}
		if(minZ == 0 && minX != 0)
		{
			checkInt0 = 0;
			while(allowedBlocks.contains(this.worldObj.getBlock(xCoord+minX, yCoord, zCoord+checkInt0)))
			{
				--checkInt0;
			}
			++checkInt0;
			if(checkInt0 < 0)
			{
				minZ = checkInt0;
			}
		}
		if(maxY == 0 && maxX != 0 && maxZ != 0)
		{
			checkInt0 = 0;
			while(allowedBlocks.contains(this.worldObj.getBlock(xCoord+maxX, yCoord+checkInt0, zCoord+maxZ)))
			{
				++checkInt0;
			}
			--checkInt0;
			if(checkInt0 > 0)
			{
				maxY = checkInt0;
			}
		}
		if(maxY == 0 && minX != 0 && maxZ != 0)
		{
			checkInt0 = 0;
			while(allowedBlocks.contains(this.worldObj.getBlock(xCoord+minX, yCoord+checkInt0, zCoord+maxZ)))
			{
				++checkInt0;
			}
			--checkInt0;
			if(checkInt0 > 0)
			{
				maxY = checkInt0;
			}
		}
		if(maxY == 0 && maxX != 0 && minZ != 0)
		{
			checkInt0 = 0;
			while(allowedBlocks.contains(this.worldObj.getBlock(xCoord+maxX, yCoord+checkInt0, zCoord+minZ)))
			{
				++checkInt0;
			}
			--checkInt0;
			if(checkInt0 > 0)
			{
				maxY = checkInt0;
			}
		}
		if(maxY == 0 && minX != 0 && minZ != 0)
		{
			checkInt0 = 0;
			while(allowedBlocks.contains(this.worldObj.getBlock(xCoord+minX, yCoord+checkInt0, zCoord+minZ)))
			{
				++checkInt0;
			}
			--checkInt0;
			if(checkInt0 > 0)
			{
				maxY = checkInt0;
			}
		}
		if(minY == 0 && maxX != 0 && minZ != 0)
		{
			checkInt0 = 0;
			while(allowedBlocks.contains(this.worldObj.getBlock(xCoord+maxX, yCoord+checkInt0, zCoord+minZ)))
			{
				--checkInt0;
			}
			++checkInt0;
			if(checkInt0 < 0)
			{
				minY = checkInt0;
			}
		}
		if(minY == 0 && minX != 0 && minZ != 0)
		{
			checkInt0 = 0;
			while(allowedBlocks.contains(this.worldObj.getBlock(xCoord+minX, yCoord+checkInt0, zCoord+minZ)))
			{
				--checkInt0;
			}
			++checkInt0;
			if(checkInt0 < 0)
			{
				minY = checkInt0;
			}
		}
		if(minY == 0 && minX != 0 && maxZ != 0)
		{
			checkInt0 = 0;
			while(allowedBlocks.contains(this.worldObj.getBlock(xCoord+maxX, yCoord+checkInt0, zCoord+minZ)))
			{
				--checkInt0;
			}
			++checkInt0;
			if(checkInt0 < 0)
			{
				minY = checkInt0;
			}
		}
		if(minY == 0 && maxX != 0 && maxZ != 0)
		{
			checkInt0 = 0;
			while(allowedBlocks.contains(this.worldObj.getBlock(xCoord+minX, yCoord+checkInt0, zCoord+minZ)))
			{
				--checkInt0;
			}
			++checkInt0;
			if(checkInt0 < 0)
			{
				minY = checkInt0;
			}
		}
		//Checking for the cuboid shape//
		if((minX == 0 && maxX == 0) || (minY == 0 && maxY == 0) || (minZ == 0 && maxZ == 0))
		{
			return false;
		}else
		{
			this.lowerCoord = new Coord3D(this.xCoord+minX, this.yCoord+minY, this.zCoord+minZ);
			this.upperCoord = new Coord3D(this.xCoord+maxX, this.yCoord+maxY, this.zCoord+maxZ);
			for(int x = minX; x <= maxX; ++x)
			{
				for(int y = minY; y <= maxY; ++y)
				{
					for(int z = minZ; z <= maxZ; ++z)
					{
						if(z == minZ || z == maxZ || x == minX || x == maxX || y == minY || y == maxY)
						{
							if(allowedBlocks.contains(this.worldObj.getBlock(xCoord+x, yCoord+y, zCoord+z)))
							{
								this.blocksInStructure.add(new BlockPosition(worldObj, xCoord+x, yCoord+y, zCoord+z));
								int meta = this.worldObj.getBlockMetadata(xCoord+x, yCoord+y, zCoord+z);
								if(ECUtils.ignoreMeta.containsKey(this.worldObj.getBlock(xCoord+x, yCoord+y, zCoord+z).getUnlocalizedName()) && ECUtils.ignoreMeta.get(this.worldObj.getBlock(xCoord+x, yCoord+y, zCoord+z).getUnlocalizedName()))
								{
									meta = -1;
								}
								DummyData dt = new DummyData(this.worldObj.getBlock(xCoord+x, yCoord+y, zCoord+z).getUnlocalizedName(),meta);
								if(ECUtils.mruResistance.containsKey(dt.toString()))
								{
									resistance += ECUtils.mruResistance.get(dt.toString());
								}else
								{
									resistance += 1F;
								}
								dt = null;
								if(this.worldObj.getTileEntity(xCoord+x, yCoord+y, zCoord+z) != null && this.worldObj.getTileEntity(xCoord+x, yCoord+y, zCoord+z) instanceof IStructurePiece)
								{
									IStructurePiece piece = (IStructurePiece) this.worldObj.getTileEntity(xCoord+x, yCoord+y, zCoord+z);
									piece.setStructureController(this, EnumStructureType.MRUCUContaigementChamber);
									if(this.worldObj.getTileEntity(xCoord+x, yCoord+y, zCoord+z) instanceof TileecHoldingChamber)
									{
										maxMRU += cfgMRUPerStorage;
									}
								}
							}else
							{
								return false;
							}
						}
					}
				}
			}
		}
		return true;
	}

	@Override
	public int getMRU() {
		IMRUPressence pressence =  getMRUCU();
		if(pressence != null)
			return this.mru;
		return 0;
	}

	@Override
	public int getMaxMRU() {
		return maxMRU;
	}

	@Override
	public boolean setMRU(int i) {
			this.mru = i;
		return true;
	}

	@Override
	public float getBalance() {
		IMRUPressence pressence =  getMRUCU();
		if(pressence != null)
			return pressence.getBalance();
		return this.balance;
	}

	@Override
	public boolean setBalance(float f) {
		this.balance = f;
		return true;
	}

	@Override
	public UUID getUUID() {
		// TODO Auto-generated method stub
		return uuid;
	}

	@Override
	public boolean setMaxMRU(float f) {
		maxMRU = (int) f;
		return true;
	}
	
	
    public static void setupConfig(Configuration cfg)
    {
    	try
    	{
	    	cfg.load();
	    	String[] cfgArrayString = cfg.getStringList("EnrichmentChamberSettings", "tileentities", new String[]{
	    			"Default Max MRU:60000",
	    			"MRU Increasement per Storage:100000"
	    			},"");
	    	String dataString="";
	    	
	    	for(int i = 0; i < cfgArrayString.length; ++i)
	    		dataString+="||"+cfgArrayString[i];
	    	
	    	DummyData[] data = DataStorage.parseData(dataString);
	    	
	    	cfgMaxMRU = Float.parseFloat(data[0].fieldValue);
	    	cfgMRUPerStorage = Float.parseFloat(data[1].fieldValue);
	    	
	    	cfg.save();
    	}catch(Exception e)
    	{
    		return;
    	}
    }

}
