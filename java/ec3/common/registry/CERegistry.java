package ec3.common.registry;

import static ec3.api.EnumCorruptionEffect.*;
import ec3.api.EnumCorruptionEffect;
import ec3.utils.common.CorruptionEffectEC3NBTBased;

public class CERegistry {
	
	public static void register()
	{
		effect(BODY,6000);
		effect(BODY,6000);
		effect(BODY,6000);
		effect(BODY,6000);
		effect(BODY,6000);
		effect(MIND,36000);
		effect(MIND,36000);
		effect(MIND,14400);
		effect(MIND,18000);
		effect(MIND,8000);
		effect(MIND,8000);
		effect(MATRIX,20000);
		effect(MATRIX,72000);
		effect(MATRIX,20000);
		effect(MATRIX,4000);
		effect(MATRIX,8000);
		effect(MATRIX,2000);
		effect(MATRIX,36000);
		effect(MATRIX,14400);
		effect(MATRIX,72000);
		effect(MATRIX,72000);
		effect(MATRIX,36000);
		effect(MATRIX,36000);
		effect(MATRIX,8000);
		effect(MATRIX,4000);
	}
	
	public static CorruptionEffectEC3NBTBased effect(EnumCorruptionEffect effect, int cost)
	{
		return new CorruptionEffectEC3NBTBased().setMeta(gMeta()).setType(effect).setCost(cost).done();
	}
	
	public static int meta = -1;
	
	public static int gMeta()
	{
		return ++meta;
	}

}
