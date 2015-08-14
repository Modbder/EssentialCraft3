package ec3.common.block;

import ec3.common.entity.EntityDemon;
import ec3.common.tile.TileDemonicPentacle;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatStyle;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;

public class BlockDemonicPentacle extends BlockContainer{
	
	public BlockDemonicPentacle() {
		super(Material.rock);
		this.setBlockBounds(0, 0, 0, 1, 0.0625F, 1);
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
        return -1;
    }

	@Override
	public TileEntity createNewTileEntity(World var1, int var2) {
		// TODO Auto-generated method stub
		return new TileDemonicPentacle();
	}
	
	@Override
	public boolean onBlockActivated(World par1World, int par2, int par3, int par4, EntityPlayer par5EntityPlayer, int par6, float par7, float par8, float par9)
	{
		TileDemonicPentacle pentacle = (TileDemonicPentacle) par1World.getTileEntity(par2, par3, par4);
		if(pentacle.consumeEnderstarEnergy(666))
		{
			EntityDemon demon = new EntityDemon(par1World);
			demon.setPositionAndRotation(par2+0.5D, par3+0.1D, par4+0.5D, 0, 0);
			demon.playLivingSound();
			if(!par1World.isRemote)
				par1World.spawnEntityInWorld(demon);
		}else
		{
			if(par1World.isRemote)
				par5EntityPlayer.addChatMessage(new ChatComponentText(StatCollector.translateToLocal("ec3.txt.noEnergy")).setChatStyle(new ChatStyle().setColor(EnumChatFormatting.RED)));
		}
		return true;
	}
	
	@Override
    public void breakBlock(World par1World, int par2, int par3, int par4, Block par5, int par6)
    {
		super.breakBlock(par1World, par2, par3, par4, par5, par6);
    }
}
