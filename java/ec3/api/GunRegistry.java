package ec3.api;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import net.minecraft.item.ItemStack;
import DummyCore.Utils.DummyData;

public class GunRegistry {
	
	public static final List<GunMaterial> gunMaterials = new ArrayList<GunMaterial>();
	public static final List<LenseMaterial> lenseMaterials = new ArrayList<LenseMaterial>();
	public static final List<ScopeMaterial> scopeMaterials = new ArrayList<ScopeMaterial>();
	public static final List<ScopeMaterial> scopeMaterialsSniper = new ArrayList<ScopeMaterial>();
	
	public static class ScopeMaterial
	{
		public String id;
		public ItemStack recipe;
		public Hashtable<GunType,ArrayList<DummyData>> materialData = new Hashtable<GunType,ArrayList<DummyData>>();
		
		public ScopeMaterial(String s, boolean sniper)
		{
			id = s;
			if(!sniper)
				scopeMaterials.add(this);
			else
				scopeMaterialsSniper.add(this);
				
		}
		
		public ScopeMaterial setRecipe(ItemStack is)
		{
			recipe = is;
			return this;
		}
		
		public ScopeMaterial appendData(String s, float value, GunType gun)
		{
			if(!materialData.containsKey(gun))
			{
				materialData.put(gun, new ArrayList<DummyData>());
			}
			ArrayList<DummyData> d = materialData.get(gun);
			d.add(new DummyData(s,value));
			materialData.put(gun, d);
			
			return this;
		}
		
		public ScopeMaterial appendData(String s, float value)
		{
			for(int i = 0; i < GunType.values().length; ++i)
			{
				GunType gun = GunType.values()[i];
				if(!materialData.containsKey(gun))
				{
					materialData.put(gun, new ArrayList<DummyData>());
				}
				ArrayList<DummyData> d = materialData.get(gun);
				d.add(new DummyData(s,value));
				materialData.put(gun, d);
			}
			return this;
		}
	}
	
	public static class LenseMaterial
	{
		public Hashtable<GunType,ArrayList<DummyData>> materialData = new Hashtable<GunType,ArrayList<DummyData>>();
		public String id;
		public ItemStack recipe;
		
		public LenseMaterial(String s)
		{
			id = s;
			lenseMaterials.add(this);
		}
		
		public LenseMaterial setRecipe(ItemStack is)
		{
			recipe = is;
			return this;
		}
		
		public LenseMaterial appendData(String s, float value, GunType gun)
		{
			if(!materialData.containsKey(gun))
			{
				materialData.put(gun, new ArrayList<DummyData>());
			}
			ArrayList<DummyData> d = materialData.get(gun);
			d.add(new DummyData(s,value));
			materialData.put(gun, d);
			
			return this;
		}
		
		public LenseMaterial appendData(String s, float value)
		{
			for(int i = 0; i < GunType.values().length; ++i)
			{
				GunType gun = GunType.values()[i];
				if(!materialData.containsKey(gun))
				{
					materialData.put(gun, new ArrayList<DummyData>());
				}
				ArrayList<DummyData> d = materialData.get(gun);
				d.add(new DummyData(s,value));
				materialData.put(gun, d);
			}
			return this;
		}
	}
	
	public static class GunMaterial
	{
		public Hashtable<GunType,ArrayList<DummyData>> materialData = new Hashtable<GunType,ArrayList<DummyData>>();
		public String id;
		public ItemStack recipe;
		
		public GunMaterial(String s)
		{
			id = s;
			gunMaterials.add(this);
		}
		
		public GunMaterial setRecipe(ItemStack is)
		{
			recipe = is;
			return this;
		}
		
		public GunMaterial appendData(String s, float value, GunType gun)
		{
			if(!materialData.containsKey(gun))
			{
				materialData.put(gun, new ArrayList<DummyData>());
			}
			ArrayList<DummyData> d = materialData.get(gun);
			d.add(new DummyData(s,value));
			materialData.put(gun, d);
			
			return this;
		}
		
		public GunMaterial appendData(String s, float value)
		{
			for(int i = 0; i < GunType.values().length; ++i)
			{
				GunType gun = GunType.values()[i];
				if(!materialData.containsKey(gun))
				{
					materialData.put(gun, new ArrayList<DummyData>());
				}
				ArrayList<DummyData> d = materialData.get(gun);
				d.add(new DummyData(s,value));
				materialData.put(gun, d);
			}
			return this;
		}
	}
	
	public static enum GunType
	{
		PISTOL,
		RIFLE,
		SNIPER,
		GATLING
	}

}
