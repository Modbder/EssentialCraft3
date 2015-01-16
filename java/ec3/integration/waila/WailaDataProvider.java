package ec3.integration.waila;

import java.util.List;

import ec3.api.ITEHasMRU;
import ec3.common.item.ItemBoundGem;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.World;
import mcp.mobius.waila.api.IWailaConfigHandler;
import mcp.mobius.waila.api.IWailaDataAccessor;
import mcp.mobius.waila.api.IWailaDataProvider;
import mcp.mobius.waila.api.IWailaRegistrar;

public class WailaDataProvider implements IWailaDataProvider{

	@Override
	public ItemStack getWailaStack(IWailaDataAccessor accessor,
			IWailaConfigHandler config) {
		return null;
	}

	@Override
	public List<String> getWailaHead(ItemStack itemStack,
			List<String> currenttip, IWailaDataAccessor accessor,
			IWailaConfigHandler config) {
		return currenttip;
	}

	@Override
	public List<String> getWailaBody(ItemStack itemStack,
			List<String> currenttip, IWailaDataAccessor accessor,
			IWailaConfigHandler config) {
		
		if(accessor.getTileEntity() != null)
		{
			if(accessor.getTileEntity() instanceof ITEHasMRU)
			{
				ITEHasMRU tile = (ITEHasMRU) accessor.getTileEntity();
				currenttip.add("MRU: "+tile.getMRU()+"/"+tile.getMaxMRU());
				float balance = tile.getBalance();
				String str = Float.toString( ((ITEHasMRU)tile).getBalance());
				if(str.length() > 6)
					str = str.substring(0, 6);
				for(int i = str.length()-1; i > 0; --i)
				{
					if(i > 2)
					{
						char c = str.charAt(i);
						if(c == '0')
						{
							str = str.substring(0, i);
						}
					}
				}
				String balanceType = "Pure";
				EnumChatFormatting color = EnumChatFormatting.AQUA;
				if(balance < 1)
				{
					balanceType = "Frozen";
					color = EnumChatFormatting.BLUE;
				}
				if(balance > 1)
				{
					balanceType = "Chaos";
					color = EnumChatFormatting.RED;
				}
				currenttip.add("Balance: "+color+str);
				if(accessor.getTileEntity() instanceof IInventory)
				{
					IInventory tInv = (IInventory) accessor.getTileEntity();
					ItemStack tryBoundGem = tInv.getStackInSlot(0);
					if(tryBoundGem != null)
					{
						if(tryBoundGem.getItem() instanceof ItemBoundGem)
						{
							ItemBoundGem itm = (ItemBoundGem) tryBoundGem.getItem();
							itm.addInformation(tryBoundGem, null, currenttip, true);
						}
					}
				}
			}
			
		}
		
		return currenttip;
	}

	@Override
	public List<String> getWailaTail(ItemStack itemStack,
			List<String> currenttip, IWailaDataAccessor accessor,
			IWailaConfigHandler config) {
		return currenttip;
	}

	@Override
	public NBTTagCompound getNBTData(EntityPlayerMP player, TileEntity te,
			NBTTagCompound tag, World world, int x, int y, int z) {
		return tag;
	}
	
	public static void callbackRegister(IWailaRegistrar registrar)
	{
		registrar.registerBodyProvider(new WailaDataProvider(), Block.class);
	}

}
