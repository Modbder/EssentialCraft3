package ec3.common.world;

import java.lang.reflect.Field;
import java.util.Random;

import ec3.common.world.structure.MapGenTown;
import net.minecraft.block.Block;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.ChunkProviderGenerate;
import net.minecraft.world.gen.feature.WorldGenDungeons;

public class ChunkProviderFirstWorld extends ChunkProviderGenerate{
	
	public Random rand;
	public World worldObj;
	public MapGenTown town = new MapGenTown();
	public static ChunkProviderFirstWorld instance;

	public static MapGenTown getGen()
	{
		return instance.town;
	}
	
	public ChunkProviderFirstWorld(World p_i2006_1_, long p_i2006_2_,
			boolean p_i2006_4_) {
		super(p_i2006_1_, p_i2006_2_, false);
		instance = this;
		try
		{
		Class mainClazz = ChunkProviderGenerate.class;
		Object obj = this;
		Field rngFld = mainClazz.getDeclaredFields()[0];
		rngFld.setAccessible(true);
		rand = (Random) rngFld.get(obj);
		}catch(Exception e)
		{
			e.printStackTrace();return;
		}
		this.worldObj = p_i2006_1_;
	}
	
    public void populate(IChunkProvider p_73153_1_, int p_73153_2_, int p_73153_3_)
    {
    	instance = this;
    	super.populate(p_73153_1_, p_73153_2_, p_73153_3_);
    	try
    	{
    		this.town.generateStructuresInChunk(this.worldObj, this.rand, p_73153_2_, p_73153_3_);
    	}catch(Exception e)
    	{
    		return;
    	}
        int k = p_73153_2_ * 16;
        int l = p_73153_3_ * 16;
        int y = this.rand.nextInt(256);
        if(y < 32 && this.rand.nextFloat() < 0.1F)
        {
            int l1 = k + this.rand.nextInt(16) + 8;
            int j2 = l + this.rand.nextInt(16) + 8;
            (new WorldGenOldCatacombs()).generate(this.worldObj, this.rand, l1, y, j2);
        }
        
        if(this.rand.nextInt(32) == 0)
        {
            int l1 = k + this.rand.nextInt(16) + 8;
            int y1 = 6;
            int j2 = l + this.rand.nextInt(16) + 8;
    		int maxY = y1;
    		while(maxY < 256)
    		{
    			++maxY;
    			boolean isAir = true;
    			for(int i = 0; i < 10; ++i)
    			{
    				if(!worldObj.isAirBlock(l1, maxY+i, j2))
    					isAir = false;
    			}
    			if(isAir)
    				break;
    		}
          //(new WorldGenDestroyedHouse(worldObj.rand.nextInt(10))).generate(worldObj, worldObj.rand, l1, maxY, j2);
        }
    }
    
    public Chunk provideChunk(int p_73154_1_, int p_73154_2_)
    {
    	instance = this;
    	this.town.func_151539_a(this, this.worldObj, p_73154_1_, p_73154_2_, (Block[])null);
        return super.provideChunk(p_73154_1_, p_73154_2_);
    }
    
    public void recreateStructures(int p_82695_1_, int p_82695_2_)
    {
    	this.town.func_151539_a(this, this.worldObj, p_82695_1_, p_82695_2_, (Block[])null);
    }

}
