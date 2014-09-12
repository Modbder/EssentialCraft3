package ec3.common.tile;

import ec3.common.block.BlocksCore;
import ec3.common.registry.BiomeRegistry;
import DummyCore.Utils.MiscUtils;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.INetHandlerPlayClient;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.potion.PotionEffect;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.biome.BiomeGenBase;

public class TileCorruption extends TileEntity
{
	@Override
    public void onDataPacket(NetworkManager net, S35PacketUpdateTileEntity pkt)
    {
		if(net.getNetHandler() instanceof INetHandlerPlayClient)
			if(pkt.func_148853_f() == -11)
			{
				NBTTagCompound packetTag = pkt.func_148857_g();
				int biomeID = packetTag.getInteger("biomeID");
				MiscUtils.changeBiome(worldObj, BiomeGenBase.getBiomeGenArray()[biomeID], xCoord, zCoord);
			}
    }
	
	public void changeBiomeAtPos(int biomeID)
	{
		MiscUtils.changeBiome(worldObj, BiomeGenBase.getBiomeGenArray()[biomeID], xCoord, zCoord);
        NBTTagCompound nbttagcompound = new NBTTagCompound();
        nbttagcompound.setInteger("biomeID", biomeID);
        S35PacketUpdateTileEntity pkt = new S35PacketUpdateTileEntity(this.xCoord, this.yCoord, this.zCoord, -11, nbttagcompound);
        MiscUtils.sendPacketToAllAround(worldObj, pkt, xCoord, yCoord, zCoord, worldObj.provider.dimensionId, 32);
	}
	
    public void addPotionEffectAtEntity(EntityLivingBase e, PotionEffect p)
    {
    	if(!e.isPotionActive(p.getPotionID()))
    	{
    		e.addPotionEffect(p);
    	}
    }
    
    public void updateEntity()
    {
    	super.updateEntity();
    	if(this.worldObj.rand.nextFloat() <= 0.0001F)
    	{
    		int biomeId = 0;
    		Block blk = this.worldObj.getBlock(xCoord, yCoord, zCoord);
    		if(blk == BlocksCore.lightCorruption[0])
    		{
    			biomeId = BiomeRegistry.chaosCorruption.biomeID;
    		}
    		if(blk == BlocksCore.lightCorruption[1])
    		{
    			biomeId = BiomeRegistry.frozenCorruption.biomeID;
    		}
    		if(blk == BlocksCore.lightCorruption[2])
    		{
    			biomeId = BiomeRegistry.shadowCorruption.biomeID;
    		}
    		if(blk == BlocksCore.lightCorruption[3])
    		{
    			biomeId = BiomeRegistry.magicCorruption.biomeID;
    		}
    		if(!this.worldObj.isRemote)
    			changeBiomeAtPos(biomeId);
    	}
    	int metadata = this.worldObj.getBlockMetadata(xCoord, yCoord, zCoord);
    	if(metadata >= 7)
    	{
    		Block blk = this.worldObj.getBlock(xCoord, yCoord, zCoord);
    		if(this.worldObj.getBlock(xCoord+1, yCoord, zCoord).isBlockNormalCube())
    		{
    			this.worldObj.setBlock(xCoord+1, yCoord, zCoord, blk, 0, 3);
    		}
    		if(this.worldObj.getBlock(xCoord-1, yCoord, zCoord).isBlockNormalCube())
    		{
    			this.worldObj.setBlock(xCoord-1, yCoord, zCoord, blk, 0, 3);
    		}
    		if(this.worldObj.getBlock(xCoord, yCoord+1, zCoord).isBlockNormalCube())
    		{
    			this.worldObj.setBlock(xCoord, yCoord+1, zCoord, blk, 0, 3);
    		}
    		if(this.worldObj.getBlock(xCoord, yCoord-1, zCoord).isBlockNormalCube())
    		{
    			this.worldObj.setBlock(xCoord, yCoord-1, zCoord, blk, 0, 3);
    		}
    		if(this.worldObj.getBlock(xCoord, yCoord, zCoord+1).isBlockNormalCube())
    		{
    			this.worldObj.setBlock(xCoord, yCoord, zCoord+1, blk, 0, 3);
    		}
    		if(this.worldObj.getBlock(xCoord, yCoord, zCoord-1).isBlockNormalCube())
    		{
    			this.worldObj.setBlock(xCoord, yCoord, zCoord-1, blk, 0, 3);
    		}
    		this.worldObj.setBlock(xCoord, yCoord, zCoord, Blocks.air, 0, 3);
    	}
    }
}
