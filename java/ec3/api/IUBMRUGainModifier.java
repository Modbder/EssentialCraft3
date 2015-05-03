package ec3.api;

import java.util.Random;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

public interface IUBMRUGainModifier {
	
	public float getModifiedValue(float original, ItemStack mod, Random rng, EntityPlayer p);

}
