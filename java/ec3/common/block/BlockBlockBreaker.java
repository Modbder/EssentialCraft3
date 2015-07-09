package ec3.common.block;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.init.Blocks;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class BlockBlockBreaker extends Block{
	
	public IIcon[] blockIcons = new IIcon[2];
	
	public BlockBlockBreaker() {
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
    	this.blockIcons[1] = p_149651_1_.registerIcon("essentialcraft:blockBreaker");
    }
	
    public boolean canProvidePower()
    {
        return true;
    }
    
    public void onNeighborBlockChange(World w, int x, int y, int z, Block n) 
    {
    	if(w.isBlockIndirectlyGettingPowered(x, y, z))
    	{
    		ForgeDirection d = ForgeDirection.values()[w.getBlockMetadata(x, y, z)];
    		Block broken = w.getBlock(x+d.offsetX, y+d.offsetY, z+d.offsetZ);
    		if(!broken.isAir(w, x+d.offsetX, y+d.offsetY, z+d.offsetZ))
    		{
    			float hardness = broken.getBlockHardness(w, x+d.offsetX, y+d.offsetY, z+d.offsetZ);
    			if(hardness >= 0 && hardness <= 10)
    			{
    				for(int i = 1; i < 13; ++i)
    				{
    					int dX = x+d.offsetX*i;
    					int dY = y+d.offsetY*i;
    					int dZ = z+d.offsetZ*i;
    					Block b = w.getBlock(dX, dY, dZ);
    					if(b.getBlockHardness(w, dX, dY, dZ) == hardness)
    					{
    	    				b.breakBlock(w, dX, dY, dZ, b, w.getBlockMetadata(dX, dY, dZ));
    	    				b.onBlockDestroyedByPlayer(w, dX, dY, dZ, w.getBlockMetadata(dX, dY, dZ));
    	    				b.dropBlockAsItem(w, dX, dY, dZ, w.getBlockMetadata(dX, dY, dZ), 0);
    	    				w.setBlock(dX, dY, dZ, Blocks.air, 0, 2);
    					}else
    						break;
    				}
    			}
    		}
    	}
    }
    
    @Override
    public int onBlockPlaced(World w, int x, int y, int z, int side, float hitX, float hitY, float hitZ, int meta)
    {
        return ForgeDirection.values()[side].ordinal();
    }
}
