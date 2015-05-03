package ec3.common.world;

import static net.minecraft.world.biome.BiomeGenBase.forest;
import static net.minecraft.world.biome.BiomeGenBase.forestHills;
import static net.minecraft.world.biome.BiomeGenBase.jungle;
import static net.minecraft.world.biome.BiomeGenBase.jungleHills;
import static net.minecraft.world.biome.BiomeGenBase.plains;
import static net.minecraft.world.biome.BiomeGenBase.taiga;
import static net.minecraft.world.biome.BiomeGenBase.taigaHills;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ec3.common.registry.BiomeRegistry;
import net.minecraft.crash.CrashReport;
import net.minecraft.crash.CrashReportCategory;
import net.minecraft.util.ReportedException;
import net.minecraft.world.ChunkPosition;
import net.minecraft.world.World;
import net.minecraft.world.WorldType;
import net.minecraft.world.biome.BiomeCache;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.biome.WorldChunkManager;
import net.minecraft.world.gen.layer.GenLayer;
import net.minecraft.world.gen.layer.IntCache;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.terraingen.WorldTypeEvent;

public class WorldChunkManager_FirstWorld extends WorldChunkManager{
	
    public WorldChunkManager_FirstWorld(long p_i1975_1_, WorldType p_i1975_3_)
    {
    	super(p_i1975_1_,p_i1975_3_);
    }

    public WorldChunkManager_FirstWorld(World p_i1976_1_)
    {
    	super(p_i1976_1_);
    }

    public BiomeGenBase[] getBiomeGenAt(BiomeGenBase[] p_76931_1_, int p_76931_2_, int p_76931_3_, int p_76931_4_, int p_76931_5_, boolean p_76931_6_)
    {
    	BiomeGenBase[] bgb = super.getBiomeGenAt(p_76931_1_, p_76931_2_, p_76931_3_, p_76931_4_, p_76931_5_, p_76931_6_);
    	for(int i = 0; i < bgb.length; ++i)
    	{
    		BiomeGenBase biome = bgb[i];
    		if(biome != null)
    		{
	    		int id = biome.biomeID;
	    		int newId = id%BiomeRegistry.firstWorldBiomeArray.length;
	    		bgb[i] = BiomeRegistry.firstWorldBiomeArray[newId];
    		}
    	}
    	
    	return bgb;
    }
}
