package ec3.common.world;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ec3.client.render.RenderCloudsFirstWorld;
import ec3.client.render.RenderSkyFirstWorld;
import ec3.common.mod.EssentialCraftCore;
import net.minecraft.world.WorldProvider;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.ChunkProviderEnd;
import net.minecraftforge.client.IRenderHandler;

public class WorldProviderFirstWorld extends WorldProvider{

	@Override
	public String getDimensionName() {
		// TODO Auto-generated method stub
		return "First World";
	}
	
    protected void registerWorldChunkManager()
    {
        this.worldChunkMgr = new WorldChunkManager_FirstWorld(this.worldObj.rand.nextLong(),this.terrainType);
        this.isHellWorld = false;
        this.hasNoSky = false;
        this.dimensionId = 53;
    }
    
    @SideOnly(Side.CLIENT)
    public IRenderHandler getSkyRenderer()
    {
        return (IRenderHandler) EssentialCraftCore.proxy.getRenderer(0);
    }
    
    @SideOnly(Side.CLIENT)
    public IRenderHandler getCloudRenderer()
    {
        return (IRenderHandler) EssentialCraftCore.proxy.getRenderer(1);
    }
    
    public boolean isSurfaceWorld()
    {
        return true;
    }
    
    public IChunkProvider createChunkGenerator()
    {
        return new ChunkProviderFirstWorld(this.worldObj, this.worldObj.getSeed(), true);
    }


}
