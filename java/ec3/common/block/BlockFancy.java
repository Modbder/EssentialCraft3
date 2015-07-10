package ec3.common.block;

import java.util.List;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ec3.api.IColdBlock;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class BlockFancy extends Block implements IColdBlock{
	
	public static final String[] overlays = new String[]{"ancientTile","bigTile","brick","fancyTile","pressuredTile","smallTiles","temple","tiles","futuristicTile","machine","runic","netherStar","plate","packedPlate","doublePlate","gem"};
	public IIcon[] overlayIcons = new IIcon[overlays.length];
	
	public boolean nativeCreated = true;
	public Block base;
	public int baseMeta;

	public BlockFancy(Material material) {
		super(material);
		if(material == Material.rock)this.setStepSound(soundTypeStone);
		if(material == Material.anvil)this.setStepSound(soundTypeAnvil);
		if(material == Material.clay || material == Material.ground)this.setStepSound(soundTypeGravel);
		if(material == Material.cloth)this.setStepSound(soundTypeCloth);
		if(material == Material.craftedSnow)this.setStepSound(soundTypeSnow);
		if(material == Material.wood)this.setStepSound(soundTypeWood);
		if(material == Material.glass)this.setStepSound(soundTypeGlass);
	}
	
	public BlockFancy(Block base, int meta)
	{
		this(base.getMaterial());
		nativeCreated = false;
		this.base = base;
		this.baseMeta = meta;
		this.setStepSound(base.stepSound);
	}
	
	public boolean isSideSolid(IBlockAccess world, int x, int y, int z, ForgeDirection side)
	{
		return true;
	}
    
    public boolean isOpaqueCube()
    {
        return true;
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
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister r)
    {
		for(int i = 0; i < overlays.length; ++i)
		{
			overlayIcons[i] = r.registerIcon("essentialcraft:fancyBlocks/overlay/overlay_"+overlays[i]);
		}
		super.registerBlockIcons(r);
    }
	
    @SuppressWarnings({ "unchecked", "rawtypes" })
	@SideOnly(Side.CLIENT)
    public void getSubBlocks(Item p_149666_1_, CreativeTabs p_149666_2_, List p_149666_3_)
    {
    	for(int i = 0; i < overlays.length; ++i)
    		p_149666_3_.add(new ItemStack(p_149666_1_, 1, i));
    }
    
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(int side, int meta)
    {
    	if(this.nativeCreated)
    		return this.blockIcon;
    	else
    		return base.getIcon(side, this.baseMeta);
    }

	@Override
	public float getColdModifier(World w, int x, int y, int z,int meta)  {
		// TODO Auto-generated method stub
		return this == BlocksCore.fancyBlocks.get(3) ? 0.5F : 0;
	}

}
