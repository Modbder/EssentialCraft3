package ec3.common.block;

import DummyCore.Utils.DataStorage;
import DummyCore.Utils.DummyData;
import DummyCore.Utils.MiscUtils;
import ec3.common.item.ItemPlayerList;
import ec3.common.mod.EssentialCraftCore;
import ec3.common.tile.TileMRUCoil;
import ec3.common.tile.TileMRUCoil_Hardener;
import ec3.common.tile.TileecController;
import ec3.utils.cfg.Config;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;

public class BlockMRUCoil_Coil extends BlockContainer{

	protected BlockMRUCoil_Coil(Material p_i45386_1_) {
		super(p_i45386_1_);
		// TODO Auto-generated constructor stub
	}
	
	public BlockMRUCoil_Coil() {
		super(Material.rock);
		// TODO Auto-generated constructor stub
	}
	
    public boolean isOpaqueCube()
    {
        return false;
    }
    
	@Override
    public int getRenderBlockPass()
    {
        return 0;
    }
    
    public boolean renderAsNormalBlock()
    {
        return false;
    }
    
    public int getRenderType()
    {
        return 2634;
    }

	@Override
	public TileEntity createNewTileEntity(World var1, int var2) {
		// TODO Auto-generated method stub
		return new TileMRUCoil();
	}
	
	@Override
	 public boolean onBlockActivated(World par1World, int par2, int par3, int par4, EntityPlayer par5EntityPlayer, int par6, float par7, float par8, float par9)
	    {
	        if (par1World.isRemote)
	        {
	            return true;
	        }else
	        {
	        	if(!par5EntityPlayer.isSneaking())
	        	{
	        		if(par5EntityPlayer.capabilities.isCreativeMode)
	        		{
		        		par5EntityPlayer.openGui(EssentialCraftCore.core, Config.instance.guiID[0], par1World, par2, par3, par4);
		            	return true;
	        		}
	        		TileMRUCoil tile = (TileMRUCoil) par1World.getTileEntity(par2, par3, par4);
					ItemStack is = tile.getStackInSlot(1);
					if(is != null && is.getItem() instanceof ItemPlayerList)
					{
			    		NBTTagCompound itemTag = MiscUtils.getStackTag(is);
			    		if(!itemTag.hasKey("usernames"))
			    			itemTag.setString("usernames", "||username:null");
			    		String str = itemTag.getString("usernames");
			    		DummyData[] dt = DataStorage.parseData(str);
			    		for(int i = 0; i < dt.length; ++i)
			    		{
			    			String username = dt[i].fieldValue;
			    			String playerName = par5EntityPlayer.getCommandSenderName();
			    			if(username.equals(playerName))
			    			{
				        		par5EntityPlayer.openGui(EssentialCraftCore.core, Config.instance.guiID[0], par1World, par2, par3, par4);
				            	return true;
			    			}
			    		}
			    		par5EntityPlayer.addChatMessage(new ChatComponentText(EnumChatFormatting.RED+StatCollector.translateToLocal("ec3.txt.noPermission")));
			    		
					}else
					{
		        		par5EntityPlayer.openGui(EssentialCraftCore.core, Config.instance.guiID[0], par1World, par2, par3, par4);
		            	return true;
					}

	        	}
	        	else
	        	{
	        		return false;
	        	}
	        }
			return false;
	    }
}
