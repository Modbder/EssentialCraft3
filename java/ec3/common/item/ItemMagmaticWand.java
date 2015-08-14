package ec3.common.item;

import ec3.utils.common.ECUtils;
import ec3.utils.common.EnumOreColoring;
import DummyCore.Utils.MathUtils;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraft.world.WorldSettings.GameType;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.event.world.BlockEvent.BreakEvent;
import net.minecraftforge.oredict.OreDictionary;

public class ItemMagmaticWand extends ItemStoresMRUInNBT {

	public ItemMagmaticWand() {
		super();	
		this.setMaxMRU(5000);
		this.maxStackSize = 1;
		this.bFull3D = true;
	}
	
    
    public boolean onItemUseFirst(ItemStack stack, EntityPlayer player, World world, int px, int y, int pz, int side, float hitX, float hitY, float hitZ)
    {
		ItemStack ore = new ItemStack(world.getBlock(px, y, pz).getItem(world, px, y, pz),1,world.getBlockMetadata(px, y, pz));
		if(ore != null && !world.isRemote)
		{
			int[] oreIds = OreDictionary.getOreIDs(ore);
			
			String oreName = "Unknown";
			if(oreIds.length > 0)
				oreName = OreDictionary.getOreName(oreIds[0]);
			int metadata = -1;
			for(int i = 0; i < EnumOreColoring.values().length; ++i)
			{
				EnumOreColoring oreColor = EnumOreColoring.values()[i];
				if(oreName.equalsIgnoreCase(oreColor.oreName))
				{
					metadata = i;
					break;
				}
			}
	        if(!player.worldObj.isRemote && metadata != -1 && (ECUtils.tryToDecreaseMRUInStorage(player, -500) || this.setMRU(stack, -500)))
	        {
				int suggestedStackSize = EnumOreColoring.values()[metadata].dropAmount;
				if(world.rand.nextFloat() <= 0.33F)
					suggestedStackSize = EnumOreColoring.values()[metadata].dropAmount*2;
				ItemStack sugStk = new ItemStack(ItemsCore.magicalAlloy,suggestedStackSize,metadata);
				
				GameType type = GameType.SURVIVAL;
				if(player.capabilities.isCreativeMode)
					type = GameType.CREATIVE;
				if(!player.capabilities.allowEdit)
					type = GameType.ADVENTURE;
				
				BreakEvent be = ForgeHooks.onBlockBreakEvent(player.worldObj, type, (EntityPlayerMP) player, px, y, pz);
				if(!be.isCanceled())
				{
					world.setBlockToAir(px, y, pz);
					EntityItem drop = new EntityItem(world, px, y, pz, sugStk);
					drop.motionX = MathUtils.randomDouble(world.rand)/10;
					drop.motionY = MathUtils.randomDouble(world.rand)/10;
					drop.motionZ = MathUtils.randomDouble(world.rand)/10;
					world.spawnEntityInWorld(drop);
				}
				
				player.swingItem();
	        }
		}
        return false;
    }
    
}
