package ec3.common.block;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.oredict.OreDictionary;

public class BlockConnectedTextures extends Block{

    public static final String[] field_150921_b = new String[] {"white", "red", "green", "brown", "blue", "purple", "cyan", "lightgray", "gray", "pink", "lime", "yellow", "lightblue", "magenta", "orange", "black"};
    public static final int[] field_150922_c = new int[] {15790320, 11743532, 3887386, 5320730, 2437522, 8073150, 2651799, 11250603, 4408131, 14188952, 4312372, 14602026, 6719955, 12801229, 15435844,1973019};
    
    @SideOnly(Side.CLIENT)
    public int getRenderColor(int metadata)
    {
    	if(metadata == OreDictionary.WILDCARD_VALUE)
    		metadata = 0;
        return field_150922_c[metadata];
    }

    /**
     * Returns a integer with hex for 0xrrggbb with this color multiplied against the blocks color. Note only called
     * when first determining what to render.
     */
    @SideOnly(Side.CLIENT)
    public int colorMultiplier(IBlockAccess world, int x, int y, int z)
    {
    	int metadata = world.getBlockMetadata(x, y, z);
    	  return field_150922_c[metadata];
    }
    
	public IIcon[] blockIcons = new IIcon[16];
	public String iconPath;
	protected BlockConnectedTextures(Material p_i45394_1_) {
		super(p_i45394_1_);
		// TODO Auto-generated constructor stub
	}
	
    public boolean isOpaqueCube()
    {
        return this.blockMaterial != Material.glass;
    }
    
    public int getRenderBlockPass()
    {
        return this.blockMaterial == Material.glass ? 1 : 0;
    }
    
    public boolean shouldSideBeRendered(IBlockAccess par1IBlockAccess, int par2, int par3, int par4, int par5)
    {
        Block i1 = par1IBlockAccess.getBlock(par2, par3, par4);
        return  i1 == this ? false : super.shouldSideBeRendered(par1IBlockAccess, par2, par3, par4, par5);
    }
	
	public Block setTexturePath(String p)
	{
		iconPath = p;
		return this;
	}
	
	@Override
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister p_149651_1_)
    {
        this.blockIcon = p_149651_1_.registerIcon(this.getTextureName());
        blockIcons[0] = p_149651_1_.registerIcon(this.getTextureName());
        blockIcons[1] = p_149651_1_.registerIcon("essentialcraft:ct/"+iconPath+"/b");
        blockIcons[2] = p_149651_1_.registerIcon("essentialcraft:ct/"+iconPath+"/r");
        blockIcons[3] = p_149651_1_.registerIcon("essentialcraft:ct/"+iconPath+"/l");
        blockIcons[4] = p_149651_1_.registerIcon("essentialcraft:ct/"+iconPath+"/t");
        blockIcons[5] = p_149651_1_.registerIcon("essentialcraft:ct/"+iconPath+"/br");
        blockIcons[6] = p_149651_1_.registerIcon("essentialcraft:ct/"+iconPath+"/bl");
        blockIcons[7] = p_149651_1_.registerIcon("essentialcraft:ct/"+iconPath+"/tl");
        blockIcons[8] = p_149651_1_.registerIcon("essentialcraft:ct/"+iconPath+"/tr");
        blockIcons[9] = p_149651_1_.registerIcon("essentialcraft:ct/"+iconPath+"/lrb");
        blockIcons[10] = p_149651_1_.registerIcon("essentialcraft:ct/"+iconPath+"/tbr");
        blockIcons[11] = p_149651_1_.registerIcon("essentialcraft:ct/"+iconPath+"/tbl");
        blockIcons[12] = p_149651_1_.registerIcon("essentialcraft:ct/"+iconPath+"/lrt");
        blockIcons[13] = p_149651_1_.registerIcon("essentialcraft:ct/"+iconPath+"/tblr");
        blockIcons[14] = p_149651_1_.registerIcon("essentialcraft:ct/"+iconPath+"/tb");
        blockIcons[15] = p_149651_1_.registerIcon("essentialcraft:ct/"+iconPath+"/lr");
    }
	
	@Override
	public IIcon getIcon(IBlockAccess par1IBlockAccess, int par2, int par3, int par4, int par5)
    {
    	if(par5 == 1)
    	{
    		return this.getIconFromTop(par1IBlockAccess, par2, par3, par4);
    	}
    	if(par5 == 0)
    	{
    		return this.getIconFromBottom(par1IBlockAccess, par2, par3, par4);
    	}
    	if(par5 == 2)
    	{
    		return this.getIconFromFront(par1IBlockAccess, par2, par3, par4);
    	}
    	if(par5 == 3)
    	{
    		return this.getIconFromBack(par1IBlockAccess, par2, par3, par4);
    	}
    	if(par5 == 4)
    	{
    		return this.getIconFromRight(par1IBlockAccess, par2, par3, par4);
    	}
    	if(par5 == 5)
    	{
    		return this.getIconFromLeft(par1IBlockAccess, par2, par3, par4);
    	}
        return this.getIcon(par5, par1IBlockAccess.getBlockMetadata(par2, par3, par4));
    }
    
    public IIcon getIconFromRight(IBlockAccess par1IBlockAccess, int par2, int par3, int par4)
    {
		if(par1IBlockAccess.getBlock(par2, par3+1, par4) == this&&par1IBlockAccess.getBlock(par2, par3-1, par4) == this&&par1IBlockAccess.getBlock(par2, par3, par4-1) == this&&par1IBlockAccess.getBlock(par2, par3, par4+1) == this)
		{
			return this.blockIcons[13];
		}
		if(par1IBlockAccess.getBlock(par2, par3+1, par4) == this&&par1IBlockAccess.getBlock(par2, par3-1, par4) == this&&par1IBlockAccess.getBlock(par2, par3, par4+1) == this)
		{
			return this.blockIcons[11];
		}
		if(par1IBlockAccess.getBlock(par2, par3+1, par4) == this&&par1IBlockAccess.getBlock(par2, par3-1, par4) == this&&par1IBlockAccess.getBlock(par2, par3, par4-1) == this)
		{
			return this.blockIcons[10];
		}
		if(par1IBlockAccess.getBlock(par2, par3, par4+1) == this&&par1IBlockAccess.getBlock(par2, par3, par4-1) == this&&par1IBlockAccess.getBlock(par2, par3-1, par4) == this)
		{
			return this.blockIcons[12];
		}
		if(par1IBlockAccess.getBlock(par2, par3, par4+1) == this&&par1IBlockAccess.getBlock(par2, par3, par4-1) == this&&par1IBlockAccess.getBlock(par2, par3+1, par4) == this)
		{
			return this.blockIcons[9];
		}
		if(par1IBlockAccess.getBlock(par2, par3-1, par4) == this&&par1IBlockAccess.getBlock(par2, par3, par4+1) == this)
		{
			return this.blockIcons[7];
		}
		if(par1IBlockAccess.getBlock(par2, par3+1, par4) == this&&par1IBlockAccess.getBlock(par2, par3, par4+1) == this)
		{
			return this.blockIcons[6];
		}
		if(par1IBlockAccess.getBlock(par2, par3-1, par4) == this&&par1IBlockAccess.getBlock(par2, par3, par4-1) == this)
		{
			return this.blockIcons[8];
		}
		if(par1IBlockAccess.getBlock(par2, par3+1, par4) == this&&par1IBlockAccess.getBlock(par2, par3, par4-1) == this)
		{
			return this.blockIcons[5];
		}
		if(par1IBlockAccess.getBlock(par2, par3-1, par4) == this&&par1IBlockAccess.getBlock(par2, par3+1, par4) == this)
		{
			return this.blockIcons[14];
		}
		if(par1IBlockAccess.getBlock(par2, par3, par4+1) == this&&par1IBlockAccess.getBlock(par2, par3, par4-1) == this)
		{
			return this.blockIcons[15];
		}
		if(par1IBlockAccess.getBlock(par2, par3, par4+1) == this)
		{
			return this.blockIcons[3];
		}
		if(par1IBlockAccess.getBlock(par2, par3, par4-1) == this)
		{
			return this.blockIcons[2];
		}
 		if(par1IBlockAccess.getBlock(par2, par3-1, par4) == this)
 		{
 			return this.blockIcons[4];
 		}
 		if(par1IBlockAccess.getBlock(par2, par3+1, par4) == this)
 		{
 			return this.blockIcons[1];
 		}
    	 return this.getIcon(4, par1IBlockAccess.getBlockMetadata(par2, par3, par4));
    }
    
    public IIcon getIconFromLeft(IBlockAccess par1IBlockAccess, int par2, int par3, int par4)
    {
		if(par1IBlockAccess.getBlock(par2, par3+1, par4) == this&&par1IBlockAccess.getBlock(par2, par3-1, par4) == this&&par1IBlockAccess.getBlock(par2, par3, par4-1) == this&&par1IBlockAccess.getBlock(par2, par3, par4+1) == this)
		{
			return this.blockIcons[13];
		}
		if(par1IBlockAccess.getBlock(par2, par3+1, par4) == this&&par1IBlockAccess.getBlock(par2, par3-1, par4) == this&&par1IBlockAccess.getBlock(par2, par3, par4-1) == this)
		{
			return this.blockIcons[11];
		}
		if(par1IBlockAccess.getBlock(par2, par3+1, par4) == this&&par1IBlockAccess.getBlock(par2, par3-1, par4) == this&&par1IBlockAccess.getBlock(par2, par3, par4+1) == this)
		{
			return this.blockIcons[10];
		}
		if(par1IBlockAccess.getBlock(par2, par3, par4+1) == this&&par1IBlockAccess.getBlock(par2, par3, par4-1) == this&&par1IBlockAccess.getBlock(par2, par3-1, par4) == this)
		{
			return this.blockIcons[12];
		}
		if(par1IBlockAccess.getBlock(par2, par3, par4+1) == this&&par1IBlockAccess.getBlock(par2, par3, par4-1) == this&&par1IBlockAccess.getBlock(par2, par3+1, par4) == this)
		{
			return this.blockIcons[9];
		}
		if(par1IBlockAccess.getBlock(par2, par3-1, par4) == this&&par1IBlockAccess.getBlock(par2, par3, par4-1) == this)
		{
			return this.blockIcons[7];
		}
		if(par1IBlockAccess.getBlock(par2, par3+1, par4) == this&&par1IBlockAccess.getBlock(par2, par3, par4-1) == this)
		{
			return this.blockIcons[6];
		}
		if(par1IBlockAccess.getBlock(par2, par3-1, par4) == this&&par1IBlockAccess.getBlock(par2, par3, par4+1) == this)
		{
			return this.blockIcons[8];
		}
		if(par1IBlockAccess.getBlock(par2, par3+1, par4) == this&&par1IBlockAccess.getBlock(par2, par3, par4+1) == this)
		{
			return this.blockIcons[5];
		}
		if(par1IBlockAccess.getBlock(par2, par3-1, par4) == this&&par1IBlockAccess.getBlock(par2, par3+1, par4) == this)
		{
			return this.blockIcons[14];
		}
		if(par1IBlockAccess.getBlock(par2, par3, par4-1) == this&&par1IBlockAccess.getBlock(par2, par3, par4+1) == this)
		{
			return this.blockIcons[15];
		}
		if(par1IBlockAccess.getBlock(par2, par3, par4-1) == this)
		{
			return this.blockIcons[3];
		}
		if(par1IBlockAccess.getBlock(par2, par3, par4+1) == this)
		{
			return this.blockIcons[2];
		}
 		if(par1IBlockAccess.getBlock(par2, par3-1, par4) == this)
 		{
 			return this.blockIcons[4];
 		}
 		if(par1IBlockAccess.getBlock(par2, par3+1, par4) == this)
 		{
 			return this.blockIcons[1];
 		}
    	 return this.getIcon(3, par1IBlockAccess.getBlockMetadata(par2, par3, par4));
    }
    
    public IIcon getIconFromBack(IBlockAccess par1IBlockAccess, int par2, int par3, int par4)
    {
		if(par1IBlockAccess.getBlock(par2, par3+1, par4) == this&&par1IBlockAccess.getBlock(par2, par3-1, par4) == this&&par1IBlockAccess.getBlock(par2-1, par3, par4) == this&&par1IBlockAccess.getBlock(par2+1, par3, par4) == this)
		{
			return this.blockIcons[13];
		}
		if(par1IBlockAccess.getBlock(par2, par3+1, par4) == this&&par1IBlockAccess.getBlock(par2, par3-1, par4) == this&&par1IBlockAccess.getBlock(par2+1, par3, par4) == this)
		{
			return this.blockIcons[11];
		}
		if(par1IBlockAccess.getBlock(par2, par3+1, par4) == this&&par1IBlockAccess.getBlock(par2, par3-1, par4) == this&&par1IBlockAccess.getBlock(par2-1, par3, par4) == this)
		{
			return this.blockIcons[10];
		}
		if(par1IBlockAccess.getBlock(par2+1, par3, par4) == this&&par1IBlockAccess.getBlock(par2-1, par3, par4) == this&&par1IBlockAccess.getBlock(par2, par3-1, par4) == this)
		{
			return this.blockIcons[12];
		}
		if(par1IBlockAccess.getBlock(par2+1, par3, par4) == this&&par1IBlockAccess.getBlock(par2-1, par3, par4) == this&&par1IBlockAccess.getBlock(par2, par3+1, par4) == this)
		{
			return this.blockIcons[9];
		}
		if(par1IBlockAccess.getBlock(par2, par3-1, par4) == this&&par1IBlockAccess.getBlock(par2+1, par3, par4) == this)
		{
			return this.blockIcons[7];
		}
		if(par1IBlockAccess.getBlock(par2, par3+1, par4) == this&&par1IBlockAccess.getBlock(par2+1, par3, par4) == this)
		{
			return this.blockIcons[6];
		}
		if(par1IBlockAccess.getBlock(par2, par3-1, par4) == this&&par1IBlockAccess.getBlock(par2-1, par3, par4) == this)
		{
			return this.blockIcons[8];
		}
		if(par1IBlockAccess.getBlock(par2, par3+1, par4) == this&&par1IBlockAccess.getBlock(par2-1, par3, par4) == this)
		{
			return this.blockIcons[5];
		}
		if(par1IBlockAccess.getBlock(par2, par3-1, par4) == this&&par1IBlockAccess.getBlock(par2, par3+1, par4) == this)
		{
			return this.blockIcons[14];
		}
		if(par1IBlockAccess.getBlock(par2-1, par3, par4) == this&&par1IBlockAccess.getBlock(par2+1, par3, par4) == this)
		{
			return this.blockIcons[15];
		}
		if(par1IBlockAccess.getBlock(par2-1, par3, par4) == this)
		{
			return this.blockIcons[2];
		}
		if(par1IBlockAccess.getBlock(par2+1, par3, par4) == this)
		{
			return this.blockIcons[3];
		}
 		if(par1IBlockAccess.getBlock(par2, par3-1, par4) == this)
 		{
 			return this.blockIcons[4];
 		}
 		if(par1IBlockAccess.getBlock(par2, par3+1, par4) == this)
 		{
 			return this.blockIcons[1];
 		}
    	 return this.getIcon(3, par1IBlockAccess.getBlockMetadata(par2, par3, par4));
    }
    
    public IIcon getIconFromFront(IBlockAccess par1IBlockAccess, int par2, int par3, int par4)
    {
		if(par1IBlockAccess.getBlock(par2, par3+1, par4) == this&&par1IBlockAccess.getBlock(par2, par3-1, par4) == this&&par1IBlockAccess.getBlock(par2-1, par3, par4) == this&&par1IBlockAccess.getBlock(par2+1, par3, par4) == this)
		{
			return this.blockIcons[13];
		}
		if(par1IBlockAccess.getBlock(par2, par3+1, par4) == this&&par1IBlockAccess.getBlock(par2, par3-1, par4) == this&&par1IBlockAccess.getBlock(par2-1, par3, par4) == this)
		{
			return this.blockIcons[11];
		}
		if(par1IBlockAccess.getBlock(par2, par3+1, par4) == this&&par1IBlockAccess.getBlock(par2, par3-1, par4) == this&&par1IBlockAccess.getBlock(par2+1, par3, par4) == this)
		{
			return this.blockIcons[10];
		}
		if(par1IBlockAccess.getBlock(par2+1, par3, par4) == this&&par1IBlockAccess.getBlock(par2-1, par3, par4) == this&&par1IBlockAccess.getBlock(par2, par3-1, par4) == this)
		{
			return this.blockIcons[12];
		}
		if(par1IBlockAccess.getBlock(par2+1, par3, par4) == this&&par1IBlockAccess.getBlock(par2-1, par3, par4) == this&&par1IBlockAccess.getBlock(par2, par3+1, par4) == this)
		{
			return this.blockIcons[9];
		}
		if(par1IBlockAccess.getBlock(par2, par3-1, par4) == this&&par1IBlockAccess.getBlock(par2-1, par3, par4) == this)
		{
			return this.blockIcons[7];
		}
		if(par1IBlockAccess.getBlock(par2, par3+1, par4) == this&&par1IBlockAccess.getBlock(par2-1, par3, par4) == this)
		{
			return this.blockIcons[6];
		}
		if(par1IBlockAccess.getBlock(par2, par3-1, par4) == this&&par1IBlockAccess.getBlock(par2+1, par3, par4) == this)
		{
			return this.blockIcons[8];
		}
		if(par1IBlockAccess.getBlock(par2, par3+1, par4) == this&&par1IBlockAccess.getBlock(par2+1, par3, par4) == this)
		{
			return this.blockIcons[5];
		}
		if(par1IBlockAccess.getBlock(par2, par3-1, par4) == this&&par1IBlockAccess.getBlock(par2, par3+1, par4) == this)
		{
			return this.blockIcons[14];
		}
		if(par1IBlockAccess.getBlock(par2-1, par3, par4) == this&&par1IBlockAccess.getBlock(par2+1, par3, par4) == this)
		{
			return this.blockIcons[15];
		}
		if(par1IBlockAccess.getBlock(par2-1, par3, par4) == this)
		{
			return this.blockIcons[3];
		}
		if(par1IBlockAccess.getBlock(par2+1, par3, par4) == this)
		{
			return this.blockIcons[2];
		}
 		if(par1IBlockAccess.getBlock(par2, par3-1, par4) == this)
 		{
 			return this.blockIcons[4];
 		}
 		if(par1IBlockAccess.getBlock(par2, par3+1, par4) == this)
 		{
 			return this.blockIcons[1];
 		}
    	 return this.getIcon(1, par1IBlockAccess.getBlockMetadata(par2, par3, par4));
    }
    
    public IIcon getIconFromTop(IBlockAccess par1IBlockAccess, int par2, int par3, int par4)
    {
   		if(par1IBlockAccess.getBlock(par2, par3, par4+1) == this && par1IBlockAccess.getBlock(par2, par3, par4-1) == this && par1IBlockAccess.getBlock(par2-1, par3, par4) == this && par1IBlockAccess.getBlock(par2+1, par3, par4) == this)
		{
			return this.blockIcons[13];
		}
		if(par1IBlockAccess.getBlock(par2, par3, par4+1) == this && par1IBlockAccess.getBlock(par2, par3, par4-1) == this && par1IBlockAccess.getBlock(par2-1, par3, par4) == this)
		{
			return this.blockIcons[10];
		}
		if(par1IBlockAccess.getBlock(par2, par3, par4+1) == this && par1IBlockAccess.getBlock(par2, par3, par4-1) == this && par1IBlockAccess.getBlock(par2+1, par3, par4) == this)
		{
			return this.blockIcons[11];
		}
		if(par1IBlockAccess.getBlock(par2+1, par3, par4) == this && par1IBlockAccess.getBlock(par2-1, par3, par4) == this && par1IBlockAccess.getBlock(par2, par3, par4-1) == this)
		{
			return this.blockIcons[9];
		}
		if(par1IBlockAccess.getBlock(par2+1, par3, par4) == this && par1IBlockAccess.getBlock(par2-1, par3, par4) == this && par1IBlockAccess.getBlock(par2, par3, par4+1) == this)
		{
			return this.blockIcons[12];
		}
		if(par1IBlockAccess.getBlock(par2-1, par3, par4) == this && par1IBlockAccess.getBlock(par2, par3, par4-1) == this)
		{
			return this.blockIcons[5];
		}
		if(par1IBlockAccess.getBlock(par2-1, par3, par4) == this && par1IBlockAccess.getBlock(par2, par3, par4+1) == this)
		{
			return this.blockIcons[8];
		}
		if(par1IBlockAccess.getBlock(par2+1, par3, par4) == this && par1IBlockAccess.getBlock(par2, par3, par4-1) == this)
		{
			return this.blockIcons[6];
		}
		if(par1IBlockAccess.getBlock(par2+1, par3, par4) == this && par1IBlockAccess.getBlock(par2, par3, par4+1) == this)
		{
			return this.blockIcons[7];
		}
		if(par1IBlockAccess.getBlock(par2, par3, par4+1) == this && par1IBlockAccess.getBlock(par2, par3, par4-1) == this)
		{
			return this.blockIcons[14];
		}
		if(par1IBlockAccess.getBlock(par2+1, par3, par4) == this && par1IBlockAccess.getBlock(par2-1, par3, par4) == this)
		{
			return this.blockIcons[15];
		}
		if(par1IBlockAccess.getBlock(par2+1, par3, par4) == this)
		{
			return this.blockIcons[3];
		}
		if(par1IBlockAccess.getBlock(par2-1, par3, par4) == this)
		{
			return this.blockIcons[2];
		}
		if(par1IBlockAccess.getBlock(par2, par3, par4+1) == this)
		{
			return this.blockIcons[4];
		}
		if(par1IBlockAccess.getBlock(par2, par3, par4-1) == this)
		{
			return this.blockIcons[1];
		}
		 return this.getIcon(1, par1IBlockAccess.getBlockMetadata(par2, par3, par4));
    }
    
    public IIcon getIconFromBottom(IBlockAccess par1IBlockAccess, int par2, int par3, int par4)
    {
   		if(par1IBlockAccess.getBlock(par2, par3, par4+1) == this && par1IBlockAccess.getBlock(par2, par3, par4-1) == this && par1IBlockAccess.getBlock(par2-1, par3, par4) == this && par1IBlockAccess.getBlock(par2+1, par3, par4) == this)
		{
			return this.blockIcons[13];
		}
		if(par1IBlockAccess.getBlock(par2, par3, par4+1) == this && par1IBlockAccess.getBlock(par2, par3, par4-1) == this && par1IBlockAccess.getBlock(par2-1, par3, par4) == this)
		{
			return this.blockIcons[10];
		}
		if(par1IBlockAccess.getBlock(par2, par3, par4+1) == this && par1IBlockAccess.getBlock(par2, par3, par4-1) == this && par1IBlockAccess.getBlock(par2+1, par3, par4) == this)
		{
			return this.blockIcons[11];
		}
		if(par1IBlockAccess.getBlock(par2+1, par3, par4) == this && par1IBlockAccess.getBlock(par2-1, par3, par4) == this && par1IBlockAccess.getBlock(par2, par3, par4-1) == this)
		{
			return this.blockIcons[9];
		}
		if(par1IBlockAccess.getBlock(par2+1, par3, par4) == this && par1IBlockAccess.getBlock(par2-1, par3, par4) == this && par1IBlockAccess.getBlock(par2, par3, par4+1) == this)
		{
			return this.blockIcons[12];
		}
		if(par1IBlockAccess.getBlock(par2-1, par3, par4) == this && par1IBlockAccess.getBlock(par2, par3, par4-1) == this)
		{
			return this.blockIcons[5];
		}
		if(par1IBlockAccess.getBlock(par2-1, par3, par4) == this && par1IBlockAccess.getBlock(par2, par3, par4+1) == this)
		{
			return this.blockIcons[8];
		}
		if(par1IBlockAccess.getBlock(par2+1, par3, par4) == this && par1IBlockAccess.getBlock(par2, par3, par4-1) == this)
		{
			return this.blockIcons[6];
		}
		if(par1IBlockAccess.getBlock(par2+1, par3, par4) == this && par1IBlockAccess.getBlock(par2, par3, par4+1) == this)
		{
			return this.blockIcons[7];
		}
		if(par1IBlockAccess.getBlock(par2, par3, par4+1) == this && par1IBlockAccess.getBlock(par2, par3, par4-1) == this)
		{
			return this.blockIcons[14];
		}
		if(par1IBlockAccess.getBlock(par2+1, par3, par4) == this && par1IBlockAccess.getBlock(par2-1, par3, par4) == this)
		{
			return this.blockIcons[15];
		}
		if(par1IBlockAccess.getBlock(par2+1, par3, par4) == this)
		{
			return this.blockIcons[3];
		}
		if(par1IBlockAccess.getBlock(par2-1, par3, par4) == this)
		{
			return this.blockIcons[2];
		}
		if(par1IBlockAccess.getBlock(par2, par3, par4+1) == this)
		{
			return this.blockIcons[4];
		}
		if(par1IBlockAccess.getBlock(par2, par3, par4-1) == this)
		{
			return this.blockIcons[1];
		}
		 return this.getIcon(0, par1IBlockAccess.getBlockMetadata(par2, par3, par4));
    }
    
    public IIcon getIcon(int par1, int par2)
    {
        return this.blockIcons[0];
    }
    
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int sideHit, float vecX, float vecY, float vecZ)
    {
    	ItemStack is = player.getCurrentEquippedItem();
    	if(is != null && OreDictionary.getOreIDs(is).length > 0 && !(is.getItem() instanceof ItemBlock))
    	{
    		for(int i = 0; i < OreDictionary.getOreIDs(is).length; ++i)
    		{
	    		String oreDisctName = OreDictionary.getOreName(OreDictionary.getOreIDs(is)[i]);
	    		if(oreDisctName != null && !oreDisctName.isEmpty() && !oreDisctName.equalsIgnoreCase("unknown"))
	    		{
	    			int color = -1;
	    			for(int i1 = 0; i1 < field_150921_b.length; ++i1)
	    			{
	    				String dyeName = "dye"+field_150921_b[i1];
	    				if(oreDisctName.equalsIgnoreCase(dyeName))
	    				{
	    					color = i1;
	    					break;
	    				}
	    			}
	    			if(color != -1)
	    			{
	    				if(player.isSneaking())
	    				{
	    					for(int dx = -2; dx <= 2; ++dx)
	    					{
	        					for(int dy = -2; dy <= 2; ++dy)
	        					{
	            					for(int dz = -2; dz <= 2; ++dz)
	            					{
	            						Block b = world.getBlock(x+dx, y+dy, z+dz);
	            						if(b == this)
	            						{
	            							b.recolourBlock(world, x+dx, y+dy, z+dz, ForgeDirection.VALID_DIRECTIONS[sideHit], color);
	            							world.markBlocksDirtyVertical(x, z, y-2, y+2);
	            						}
	            					}
	        					}
	    					}
	    				}else
	    				{
	    					recolourBlock(world, x, y, z, ForgeDirection.VALID_DIRECTIONS[sideHit], color);
	    					world.markBlocksDirtyVertical(x, z, y-2, y+2);
	    				}
	    				return true;
	    			}
	    		}
    		}
    	}
        return false;
    }
    
    public boolean recolourBlock(World world, int x, int y, int z, ForgeDirection side, int colour)
    {
        int meta = world.getBlockMetadata(x, y, z);
        //if(colour == 0) colour = 15; else if(colour == 15)colour = 0;
        if (meta != colour)
        {
            	world.setBlockMetadataWithNotify(x, y, z, colour, 3);
                return true;
        }
        return false;
    }
}
