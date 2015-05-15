package ec3.common.item;

import java.util.List;

import DummyCore.Utils.Coord3D;
import DummyCore.Utils.DummyDistance;
import DummyCore.Utils.MiscUtils;
import ec3.common.tile.TileMagicalAssembler;
import ec3.common.tile.TileMagicalMirror;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatStyle;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.World;

public class ItemControlRod extends Item {

	public ItemControlRod() {
		super();
		this.maxStackSize = 1;
	}
    
	@Override
    public boolean onItemUseFirst(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ)
    {
		if(world.isRemote) return false;
		if(stack.getTagCompound() == null)
		{
			TileEntity tile = world.getTileEntity(x, y, z);
			if(tile != null)
			{
				if(tile instanceof TileMagicalMirror)
				{
					MiscUtils.getStackTag(stack).setIntArray("pos", new int[]{x,y,z});
					player.addChatMessage(new ChatComponentText("Mirror linked to the wand!").setChatStyle(new ChatStyle().setColor(EnumChatFormatting.GREEN)));
					return true;
				}
			}
		}else
		{
			TileEntity tile = world.getTileEntity(x, y, z);
			if(tile != null)
			{
				if(tile instanceof TileMagicalAssembler)
				{
					int[] o = MiscUtils.getStackTag(stack).getIntArray("pos");
					float distance = new DummyDistance(new Coord3D(x,y,z),new Coord3D(o[0],o[1],o[2])).getDistance();
					if(distance <= TileMagicalMirror.cfgMaxDistance)
					{
						TileEntity tile1 = world.getTileEntity(o[0],o[1],o[2]);
						if(tile1 != null && tile1 instanceof TileMagicalMirror)
						{
							((TileMagicalMirror)tile1).inventoryPos = new Coord3D(x+0.5F,y+0.5F,z+0.5F);
							player.addChatMessage(new ChatComponentText("Mirror linked to the assembler!").setChatStyle(new ChatStyle().setColor(EnumChatFormatting.GREEN)));
							stack.setTagCompound(null);
							return true;
						}
					}
				}else
				{
					if(tile instanceof IInventory)
					{
						int[] o = MiscUtils.getStackTag(stack).getIntArray("pos");
						float distance = new DummyDistance(new Coord3D(x,y,z),new Coord3D(o[0],o[1],o[2])).getDistance();
						if(distance <= TileMagicalMirror.cfgMaxDistance)
						{
							TileEntity tile1 = world.getTileEntity(o[0],o[1],o[2]);
							if(tile1 != null && tile1 instanceof TileMagicalMirror)
							{
								((TileMagicalMirror)tile1).inventoryPos = new Coord3D(x+0.5F,y+0.5F,z+0.5F);
								player.addChatMessage(new ChatComponentText("Mirror linked to the inventory!").setChatStyle(new ChatStyle().setColor(EnumChatFormatting.GREEN)));
								stack.setTagCompound(null);
								return true;
							}
						}
					}
				}
			}
		}
        return false;
    }
    
	 @SuppressWarnings({ "unchecked", "rawtypes" })
    public void addInformation(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, List par3List, boolean par4) 
    {
    	if(par1ItemStack.getTagCompound() != null)
    	{
    		int[] coord = MiscUtils.getStackTag(par1ItemStack).getIntArray("pos");
    		par3List.add("Currently linked to Mirror At:");
    		par3List.add("x: "+coord[0]);
    		par3List.add("y: "+coord[1]);
    		par3List.add("z: "+coord[2]);
    		par3List.add("dimension: "+MiscUtils.getStackTag(par1ItemStack).getInteger("dim"));
    	}
    }
    
    public static int[] getCoords(ItemStack stack)
    {
    	return MiscUtils.getStackTag(stack).getIntArray("pos");
    }
    
    
    public boolean createTag(ItemStack stack)
    {
    	if(stack.getTagCompound() == null)
    	{
    		NBTTagCompound tag = new NBTTagCompound();
    		tag.setIntArray("pos", new int[]{0,0,0});
    		return true;
    	}
    	return false;
    }
}
