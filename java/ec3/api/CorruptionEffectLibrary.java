package ec3.api;

import java.util.ArrayList;
import java.util.List;

public class CorruptionEffectLibrary {
	
	public static final List<ICorruptionEffect> allPossibleEffects = new ArrayList<ICorruptionEffect>();

	public static void addEffect(ICorruptionEffect effect)
	{
		if(effect != null)
			allPossibleEffects.add(effect);
	}
	
	public static ArrayList<ICorruptionEffect> findSutableEffects(int maxCost)
	{
		ArrayList<ICorruptionEffect> retLst = new ArrayList<ICorruptionEffect>();
		
		for(int i = 0; i < allPossibleEffects.size(); ++i)
		{
			ICorruptionEffect effect = allPossibleEffects.get(i);
			if(effect.getStickiness() <= maxCost)
			{
				retLst.add(effect);
				if(effect.getType().equals(EnumCorruptionEffect.BODY))
				{
					retLst.add(effect);
					retLst.add(effect);
				}
			}
		}
		
		return retLst;
	}
}
