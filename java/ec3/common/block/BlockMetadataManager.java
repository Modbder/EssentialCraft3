package ec3.common.block;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class BlockMetadataManager extends Block{
	
	public IIcon[] blockIcons = new IIcon[2];
	
	public BlockMetadataManager() {
		super(Material.rock);
	}
	
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(IBlockAccess w, int x, int y, int z, int side)
    {
    	int meta = w.getBlockMetadata(x, y, z);
    	return side == meta ? blockIcons[1] : blockIcons[0];
    }
	
    public IIcon getIcon(int side, int meta)
    {
        return side == 3 ? blockIcons[1] : blockIcons[0];
    }
    
	@Override
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister p_149651_1_)
    {
    	this.blockIcons[0] = p_149651_1_.registerIcon("essentialcraft:fortifiedStone");
    	this.blockIcons[1] = p_149651_1_.registerIcon("essentialcraft:metadataManager");
    }
	
    public boolean canProvidePower()
    {
        return true;
    }
    
    public void onNeighborBlockChange(World w, int x, int y, int z, Block n) 
    {
    	for(int i = 0; i < 6; ++i)
    	{
    		ForgeDirection d = ForgeDirection.values()[i];
    		Block b = w.getBlock(x+d.offsetX, y+d.offsetY, z+d.offsetZ);
    		if(b != n && b != this && n != this)
    		{
    			b.onNeighborBlockChange(w, x+d.offsetX, y+d.offsetY, z+d.offsetZ, this);
    		}
    	}
    }
    
    public boolean canConnectRedstone(IBlockAccess world, int x, int y, int z, int side)
    {
    	return side != world.getBlockMetadata(x, y, z);
    }
	
    public int isProvidingWeakPower(IBlockAccess w, int x, int y, int z, int side)
    {
    	int metadata = w.getBlockMetadata(x, y, z);
    	ForgeDirection d = ForgeDirection.values()[metadata];
    	return w.getBlockMetadata(x+d.offsetX, y+d.offsetY, z+d.offsetZ);
    }
    
    @Override
    public int onBlockPlaced(World w, int x, int y, int z, int side, float hitX, float hitY, float hitZ, int meta)
    {
        return ForgeDirection.values()[side].ordinal();
    }
}
