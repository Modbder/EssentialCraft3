package ec3.common.inventory;

import cpw.mods.fml.common.registry.GameRegistry;
import ec3.common.item.ItemBoundGem;
import ec3.common.item.ItemDrop;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.stats.AchievementList;
import net.minecraft.util.MathHelper;

public class SlotBoundEssence extends Slot
{
    /** The player that is using the GUI where this slot resides. */
    private int field_75228_b;

    public SlotBoundEssence(IInventory par2IInventory, int par3, int par4, int par5)
    {
        super(par2IInventory, par3, par4, par5);
        this.setBackgroundIcon(ItemDrop.itemIcons[3]);
    }

    @Override
    public boolean isItemValid(ItemStack par1ItemStack)
    {
        return par1ItemStack.getItem() instanceof ItemBoundGem;
    }


}
