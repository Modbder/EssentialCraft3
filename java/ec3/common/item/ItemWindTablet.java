package ec3.common.item;

import java.util.List;

import DummyCore.Utils.MathUtils;
import ec3.common.block.BlocksCore;
import ec3.utils.common.ECUtils;
import ec3.utils.common.WindRelations;
import net.minecraft.block.Block;
import net.minecraft.block.BlockPortal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class ItemWindTablet extends ItemStoresMRUInNBT {
	//24
	public static String[] windMessages = new String[]{
		"A wind blow rotates around you...",//0
		"The wind howls...",//1
		"The wind ruffles your hair...",//2
		"The wind blows around your fingers...",//3
		"The wind says something...",//4
		"The wind makes you sneeze!",//5
		"You hear something similar to laugh...",//6
		"The wind rotates around your legs...",//7
		"The wind tickles you...",//8
		"The wind stops all other sounds...",//9
		"The wind whispers 'Owethanna'...",//10
		"The wind whispers your name...",//11
		"The wind brings a very nostalgic smell...",//12
		"The wind creates a miniature tornado...",//13
		"The wind thows some leaves around...",//14
		"The wind says 'Owethanna Else '...",//15
		"The wind pushes you upwards...",//16
		"The wind rotates very fast around you...",//17
		"The wind goes into your lungs...",//18
		"You feel very powerfull!",//19
		"You fly up using the wind!",//20
		"You start seeing other worlds!",//21
		"You and the wind laugh...",//22
		"The wind and you shout:"//23
	};

	public ItemWindTablet() {
		super();	
		this.setMaxMRU(5000);
		this.maxStackSize = 1;
		this.bFull3D = false;
	}

    /**
     * Called whenever this item is equipped and the right mouse button is pressed. Args: itemStack, world, entityPlayer
     */
    public ItemStack onItemRightClick(ItemStack p_77659_1_, World p_77659_2_, EntityPlayer p_77659_3_)
    {
        if((ECUtils.tryToDecreaseMRUInStorage(p_77659_3_, -500) || this.setMRU(p_77659_1_, -500)))
        {
        		int currentWindRev = WindRelations.getPlayerWindRelations(p_77659_3_);
        		int maxWindRev = 3500;
        		String windName = "Owethanna Else Hugaida";
        		int revPos = MathUtils.pixelatedTextureSize(currentWindRev, maxWindRev, windName.length());
        		if(!p_77659_3_.worldObj.isRemote && revPos >= 22)
        		{
        				if(p_77659_3_.capabilities.isCreativeMode)
        					p_77659_3_.capabilities.isCreativeMode = false;
        				Block b = p_77659_3_.worldObj.getBlock(MathHelper.floor_double(p_77659_3_.posX), MathHelper.floor_double(p_77659_3_.posY), MathHelper.floor_double(p_77659_3_.posZ));
        				
        				if(b instanceof BlockPortal)
        				{
        					int x = MathHelper.floor_double(p_77659_3_.posX);
        					int y = MathHelper.floor_double(p_77659_3_.posY);
        					int z = MathHelper.floor_double(p_77659_3_.posZ);
        					int i = 0;
        					
        					while(p_77659_3_.worldObj.getBlock(x+i, y, z) instanceof BlockPortal)
        					{
        						int j = 0;
            					while(p_77659_3_.worldObj.getBlock(x+i, y+j, z) instanceof BlockPortal)
            					{
            						int k = 0;
                					while(p_77659_3_.worldObj.getBlock(x+i, y+j, z+k) instanceof BlockPortal)
                					{
                						int metadata = p_77659_3_.worldObj.getBlockMetadata(x+i, y+j, z+k);
                						p_77659_3_.worldObj.setBlock(x+i, y+j, z+k, BlocksCore.portal, metadata, 2);
                						++k;
                					}
                					k = 0;
                					while(p_77659_3_.worldObj.getBlock(x+i, y+j, z-k) instanceof BlockPortal)
                					{
                						int metadata = p_77659_3_.worldObj.getBlockMetadata(x+i, y+j, z-k);
                						p_77659_3_.worldObj.setBlock(x+i, y+j, z-k, BlocksCore.portal, metadata, 2);
                						++k;
                					}
                					++j;
            					}
            					j = 0;
            					while(p_77659_3_.worldObj.getBlock(x+i, y-j, z) instanceof BlockPortal)
            					{
            						int k = 0;
                					while(p_77659_3_.worldObj.getBlock(x+i, y-j, z+k) == Blocks.portal)
                					{
                						int metadata = p_77659_3_.worldObj.getBlockMetadata(x+i, y-j, z+k);
                						p_77659_3_.worldObj.setBlock(x+i, y-j, z+k, BlocksCore.portal, metadata, 2);
                						++k;
                					}
                					k = 0;
                					while(p_77659_3_.worldObj.getBlock(x+i, y-j, z-k) == Blocks.portal)
                					{
                						int metadata = p_77659_3_.worldObj.getBlockMetadata(x+i, y+j, z-k);
                						p_77659_3_.worldObj.setBlock(x+i, y-j, z-k, BlocksCore.portal, metadata, 2);
                						++k;
                					}
                					++j;
            					}
            					++i;
        					}
        					i = 0;
        					while(p_77659_3_.worldObj.getBlock(x-i, y, z) instanceof BlockPortal)
        					{
        						int j = 0;
            					while(p_77659_3_.worldObj.getBlock(x-i, y+j, z) instanceof BlockPortal)
            					{
            						int k = 0;
                					while(p_77659_3_.worldObj.getBlock(x-i, y+j, z+k) instanceof BlockPortal)
                					{
                						int metadata = p_77659_3_.worldObj.getBlockMetadata(x-i, y+j, z+k);
                						p_77659_3_.worldObj.setBlock(x-i, y+j, z+k, BlocksCore.portal, metadata, 2);
                						++k;
                					}
                					k = 0;
                					while(p_77659_3_.worldObj.getBlock(x-i, y+j, z-k) instanceof BlockPortal)
                					{
                						int metadata = p_77659_3_.worldObj.getBlockMetadata(x-i, y+j, z-k);
                						p_77659_3_.worldObj.setBlock(x-i, y+j, z-k, BlocksCore.portal, metadata, 2);
                						++k;
                					}
                					++j;
            					}
            					j = 0;
            					while(p_77659_3_.worldObj.getBlock(x-i, y-j, z) instanceof BlockPortal)
            					{
            						int k = 0;
                					while(p_77659_3_.worldObj.getBlock(x-i, y-j, z+k) instanceof BlockPortal)
                					{
                						int metadata = p_77659_3_.worldObj.getBlockMetadata(x-i, y-j, z+k);
                						p_77659_3_.worldObj.setBlock(x-i, y-j, z+k, BlocksCore.portal, metadata, 2);
                						++k;
                					}
                					k = 0;
                					while(p_77659_3_.worldObj.getBlock(x-i, y-j, z-k) instanceof BlockPortal)
                					{
                						int metadata = p_77659_3_.worldObj.getBlockMetadata(x-i, y-j, z-k);
                						p_77659_3_.worldObj.setBlock(x-i, y-j, z-k, BlocksCore.portal, metadata, 2);
                						++k;
                					}
                					++j;
            					}
            					++i;
        					}
        				}
        	            //server.getConfigurationManager().transferPlayerToDimension((EntityPlayerMP) p_77659_3_, 53);
        	            p_77659_3_.worldObj.playSoundAtEntity(p_77659_3_, "portal.trigger", 10, 0.01F);
        			}
        	}
        return p_77659_1_;
    }
    
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
    public void addInformation(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, List par3List, boolean par4) 
    {
		super.addInformation(par1ItemStack, par2EntityPlayer, par3List, par4);
		int currentWindRev = WindRelations.getPlayerWindRelations(par2EntityPlayer);
		int maxWindRev = 3500;
		String windName = "Owethanna Else Hugaida";
		String hidden = "??????????????????????";
		int revPos = MathUtils.pixelatedTextureSize(currentWindRev, maxWindRev, windName.length());
		par3List.add("Wind name:");
		par3List.add(windName.substring(0, revPos)+hidden.substring(revPos));
    }

}
