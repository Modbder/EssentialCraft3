package ec3.common.block;

import ec3.common.tile.TileecRedstoneController;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.StatCollector;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockecRedstoneController extends BlockContainer{

	protected BlockecRedstoneController(Material p_i45386_1_) {
		super(p_i45386_1_);
		// TODO Auto-generated constructor stub
	}

	@Override
	public TileEntity createNewTileEntity(World var1, int var2) {
		// TODO Auto-generated method stub
		return new TileecRedstoneController();
	}

    public boolean onBlockActivated(World p_149727_1_, int p_149727_2_, int p_149727_3_, int p_149727_4_, EntityPlayer p_149727_5_, int p_149727_6_, float p_149727_7_, float p_149727_8_, float p_149727_9_)
    {
    	if(p_149727_5_.isSneaking())
    	{
	    	TileecRedstoneController rc = (TileecRedstoneController) p_149727_1_.getTileEntity(p_149727_2_, p_149727_3_, p_149727_4_);
	    	rc.setting += 1;
	    	if(rc.setting >= 11)
	    		rc.setting = 0;
	    	if(p_149727_5_.worldObj.isRemote)
	    		p_149727_5_.addChatMessage(new ChatComponentText(StatCollector.translateToLocal("ec3.txt.redstone_"+rc.setting)));
    	}else
    	{
    		TileecRedstoneController rc = (TileecRedstoneController) p_149727_1_.getTileEntity(p_149727_2_, p_149727_3_, p_149727_4_);
    		if(p_149727_5_.worldObj.isRemote)
    			p_149727_5_.addChatMessage(new ChatComponentText(StatCollector.translateToLocal("ec3.txt.redstone_"+rc.setting)));
    	}
        return true;
    }
    
    public boolean canProvidePower()
    {
        return true;
    }

    public int isProvidingWeakPower(IBlockAccess w, int x, int y, int z, int meta)
    {
    	TileecRedstoneController rc = (TileecRedstoneController) w.getTileEntity(x, y, z);
    	
        return rc.outputRedstone() ? 15 : 0;
    }
}
