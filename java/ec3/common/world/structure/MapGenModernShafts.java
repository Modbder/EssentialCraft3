package ec3.common.world.structure;

import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import net.minecraft.util.MathHelper;
import net.minecraft.world.gen.structure.MapGenStructure;
import net.minecraft.world.gen.structure.StructureMineshaftStart;
import net.minecraft.world.gen.structure.StructureStart;

public class MapGenModernShafts extends MapGenStructure{

	 private double field_82673_e = 0.004D;
	 
	@Override
	public String func_143025_a() {
		// TODO Auto-generated method stub
		return "ModernMineshaft";
	}
	
	public MapGenModernShafts()
	{
		
	}

    public MapGenModernShafts(Map p_i2034_1_)
    {
        Iterator iterator = p_i2034_1_.entrySet().iterator();

        while (iterator.hasNext())
        {
            Entry entry = (Entry)iterator.next();

            if (((String)entry.getKey()).equals("chance"))
            {
                this.field_82673_e = MathHelper.parseDoubleWithDefault((String)entry.getValue(), this.field_82673_e);
            }
        }
    }
    
    
    protected boolean canSpawnStructureAtCoords(int p_75047_1_, int p_75047_2_)
    {
        return this.rand.nextDouble() < this.field_82673_e && this.rand.nextInt(80) < Math.max(Math.abs(p_75047_1_), Math.abs(p_75047_2_)) && this.worldObj.provider.dimensionId == 53;
    }

    protected StructureStart getStructureStart(int p_75049_1_, int p_75049_2_)
    {
        return new StructureModernShaftStart(this.worldObj, this.rand, p_75049_1_, p_75049_2_);
    }

}
