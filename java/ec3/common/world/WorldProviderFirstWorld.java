package ec3.common.world;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ec3.client.render.RenderCloudsFirstWorld;
import ec3.client.render.RenderSkyFirstWorld;
import ec3.common.mod.EssentialCraftCore;
import ec3.utils.common.ECUtils;
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
        this.worldChunkMgr = new WorldChunkManager_FirstWorld(this.worldObj.getSeed(),this.terrainType);
        this.isHellWorld = false;
        this.hasNoSky = false;
        this.dimensionId = 53;
    }

    public void generateLightBrightnessTable()
    {
        float f = 0.0F;

        for (int i = 0; i <= 15; ++i)
        {
        	float f1;
        	if(!ECUtils.isEventActive("ec3.event.darkness"))
        		f1 = 1.0F - (float)i / 15.0F;
        	else
        		f1 = 1.9F - (float)i / 15.0F;
            this.lightBrightnessTable[i] = (1.0F - f1) / (f1 * 3.0F + 1.0F) * (1.0F - f) + f;
        }
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
