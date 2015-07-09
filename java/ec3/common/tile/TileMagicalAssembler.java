package ec3.common.tile;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import DummyCore.Utils.Coord3D;
import DummyCore.Utils.UnformedItemStack;
import ec3.api.MagicalAssemblerRecipes;
import ec3.api.MagicianTableRecipe;
import ec3.api.MagicianTableRecipes;
import ec3.api.RadiatingChamberRecipe;
import ec3.api.RadiatingChamberRecipes;
import ec3.api.ShapedAssemblerRecipe;
import ec3.utils.common.ECUtils;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.ShapedRecipes;
import net.minecraft.item.crafting.ShapelessRecipes;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.oredict.ShapelessOreRecipe;

public class TileMagicalAssembler extends TileMRUGeneric{
	
	public List<UnformedItemStack> requiredItemsToCraft = new ArrayList<UnformedItemStack>();
	public List<Integer> requiredStackSizeToCraft = new ArrayList<Integer>();
	
	public ItemStack currentCraft;
	
	public List<ItemStack> allRecipes = new ArrayList<ItemStack>();
	public List<IRecipe> actualRecipes = new ArrayList<IRecipe>();
	
	public List<Coord3D> mirrors = new ArrayList<Coord3D>();
	public List<TileMagicalMirror> mirrorsTiles = new ArrayList<TileMagicalMirror>();
	
	
	public IRecipe currentSelectedOne;
	
	public int currentRecipe = -1;
	
	public int recipeType = -1;
	
	public boolean isWorking = false;
	
	@Override
	public int[] getAccessibleSlotsFromSide(int side) {
		return new int[]{0,17};
	}
	
	@Override
    public void readFromNBT(NBTTagCompound i)
    {
		isWorking = i.getBoolean("working");
		currentRecipe = i.getInteger("recipeID");
		recipeType = i.getInteger("recipeType");
		NBTTagList unformedItems = i.getTagList("unformedItems", 10);
		this.requiredItemsToCraft.clear();
		if(unformedItems != null)
			for(int j = 0; j < unformedItems.tagCount(); ++j)
			{
				NBTTagCompound tag = unformedItems.getCompoundTagAt(j);
				UnformedItemStack unformedIS = new UnformedItemStack();
				unformedIS.readFromNBTTagCompound(tag);
				this.requiredItemsToCraft.add(unformedIS);
			}
		if(i.hasKey("crafted"))
		{
			NBTTagCompound craftedTag = i.getCompoundTag("crafted");
			currentCraft = ItemStack.loadItemStackFromNBT(craftedTag);
		}
		super.readFromNBT(i);
		if(this.getStackInSlot(1) != null)
		{
			this.formCraftList(this.getStackInSlot(1));
			if(this.currentRecipe != -1)
				this.formRequiredComponents();
		}
    }
	
	
	@Override
    public void writeToNBT(NBTTagCompound i)
    {
		i.setBoolean("working", isWorking);
		i.setInteger("recipeID", this.currentRecipe);
		i.setInteger("recipeType", this.recipeType);
		NBTTagList unformedItems = new NBTTagList();
		for(int j = 0; j < this.requiredItemsToCraft.size(); ++j)
		{
			NBTTagCompound tag = new NBTTagCompound();
			this.requiredItemsToCraft.get(j).writeToNBTTagCompound(tag);
			unformedItems.appendTag(tag);
		}
		i.setTag("unformedItems", unformedItems);
		if(this.currentCraft != null)
		{
			NBTTagCompound craftTag = new NBTTagCompound();
			this.currentCraft.writeToNBT(craftTag);
			i.setTag("crafted", craftTag);
		}
		
		super.writeToNBT(i);
		
    }
	
	public int getRequiredMRUToCraft()
	{
		if(this.currentSelectedOne == null)
		{
			return 0;
		}
		if(currentSelectedOne instanceof MagicianTableRecipe) return ((MagicianTableRecipe) currentSelectedOne).mruRequired;
		if(currentSelectedOne instanceof RadiatingChamberRecipe) return (int) (((RadiatingChamberRecipe) currentSelectedOne).mruRequired* ((RadiatingChamberRecipe) currentSelectedOne).costModifier);
		if(currentSelectedOne instanceof ShapedAssemblerRecipe) return ((ShapedAssemblerRecipe) currentSelectedOne).mruRequired;
		return 100;
	}
	
	public TileMagicalAssembler()
	{
		 super();
		this.setSlotsNum(18);
		this.setMaxMRU(10000F);
	}
	
	public void updateEntity() 
	{
		for(int i = 0; i < mirrors.size(); ++i)
		{
			Coord3D coord = mirrors.get(i);
			TileEntity tile = worldObj.getTileEntity((int)coord.x, (int)coord.y, (int)coord.z);
			if(tile == null || !(tile instanceof TileMagicalMirror))
				mirrors.remove(i);
		}
		super.updateEntity();
		ECUtils.manage(this, 0);
		TileEntity tile;
		if(this.getStackInSlot(1) != null)
		{
			if(this.allRecipes.isEmpty() && this.currentCraft == null)
			{
				this.formCraftList(this.getStackInSlot(1));
			}
			if(this.currentCraft != null && !this.isWorking && this.worldObj.isBlockIndirectlyGettingPowered(xCoord, yCoord, zCoord))
			{
				this.isWorking = true;
				for(int i = 0; i < mirrors.size(); ++i)
				{
					Coord3D coord = mirrors.get(i);
					tile = worldObj.getTileEntity((int)coord.x, (int)coord.y, (int)coord.z);
					if(tile == null || !(tile instanceof TileMagicalMirror))
						mirrors.remove(i);
					else
						((TileMagicalMirror) tile).begin(this);
				}
			}else
				if(this.currentCraft == null || !this.worldObj.isBlockIndirectlyGettingPowered(xCoord, yCoord, zCoord))
				{
					this.isWorking = false;
					for(int i = 0; i < mirrors.size(); ++i)
					{
						Coord3D coord = mirrors.get(i);
						tile = worldObj.getTileEntity((int)coord.x, (int)coord.y, (int)coord.z);
						if(tile == null || !(tile instanceof TileMagicalMirror))
							mirrors.remove(i);
						else
							((TileMagicalMirror) tile).end(this);
					}
				}
			if(this.currentCraft != null && this.isWorking)
			{
				for(int i = 0; i < mirrors.size(); ++i)
				{
					Coord3D coord = mirrors.get(i);
					tile = worldObj.getTileEntity((int)coord.x, (int)coord.y, (int)coord.z);
					if(tile == null || !(tile instanceof TileMagicalMirror))
						mirrors.remove(i);
					else
						((TileMagicalMirror) tile).pulse(this);
				}
			}
			if(this.isWorking && this.currentCraft != null && this.worldObj.getWorldTime()%20 == 0 && !this.requiredItemsToCraft.isEmpty() && (this.getStackInSlot(17) == null || (this.getStackInSlot(17).isItemEqual(currentCraft) && this.getStackInSlot(17).stackSize+1 < this.getStackInSlot(17).getMaxStackSize()+1)) && this.getMRU() - getRequiredMRUToCraft() >= 0 && this.currentSelectedOne != null)
			{
				List<UnformedItemStack> hasItems = new ArrayList<UnformedItemStack>(this.requiredItemsToCraft.size());
				List<UnformedItemStack> hasItemsCopy = new ArrayList<UnformedItemStack>(this.requiredItemsToCraft.size());
				hasItemsCopy.addAll(requiredItemsToCraft);
				
				for(int i = 0; i < this.requiredItemsToCraft.size(); ++i)
				{
					hasItems.add(null);
				}
				for(int i = 2; i < 17; ++i)
				{
					ItemStack is = this.getStackInSlot(i);
					if(is != null)
					{
						forCycle:for(int j = 0; j < hasItemsCopy.size(); ++j)
						{
							if(hasItemsCopy.get(j) != null && hasItemsCopy.get(j).itemStackMatches(is))
							{
								hasItemsCopy.set(j, null);
								hasItems.set(j,this.requiredItemsToCraft.get(j));
								break forCycle;
							}
						}
					}
				}
				for(int i = 0; i < hasItems.size(); ++i)
				{
					if(hasItems.get(i) == null && this.requiredItemsToCraft.get(i).possibleStacks.isEmpty())
						hasItems.set(i, this.requiredItemsToCraft.get(i));
				}
				
				if(hasItems.equals(requiredItemsToCraft))
				{
					for(int i = 2; i < 17; ++i)
					{
						this.decrStackSize(i, 1);
					}
					if(this.getStackInSlot(17) == null)
						this.setInventorySlotContents(17, this.currentCraft.copy());
					else
						this.getStackInSlot(17).stackSize += this.currentCraft.stackSize;
					this.isWorking = false;
					this.setMRU(this.getMRU()-this.getRequiredMRUToCraft());
					for(int i = 0; i < mirrors.size(); ++i)
					{
						Coord3D coord = mirrors.get(i);
						tile = worldObj.getTileEntity((int)coord.x, (int)coord.y, (int)coord.z);
						if(tile == null || !(tile instanceof TileMagicalMirror))
							mirrors.remove(i);
						else
							((TileMagicalMirror) tile).end(this);
					}
				}
			}
		}else
		{
				allRecipes.clear();
				actualRecipes.clear();
				requiredItemsToCraft.clear();
				requiredStackSizeToCraft.clear();
				currentCraft = null;
				this.currentSelectedOne = null;
				this.isWorking = false;
		}
	}
	
	/**
	 * Forms this.requiredItemsToCraft and this.requiredStackSizeToCraft with the corresponding ItemStacks.
	 */
	public void formRequiredComponents()
	{
		this.requiredItemsToCraft.clear();
		if(this.currentRecipe != -1)
		{
			IRecipe rec = this.actualRecipes.get(currentRecipe);
			this.currentSelectedOne = rec;
			this.currentCraft = rec.getRecipeOutput();
			for(int i = 0; i < rec.getRecipeSize(); ++i)
			{
				if(rec instanceof ShapedRecipes)
				{
					ShapedRecipes sr = (ShapedRecipes) rec;
					UnformedItemStack uis = new UnformedItemStack(sr.recipeItems[i]);
					this.requiredItemsToCraft.add(uis);
				}
				if(rec instanceof ShapelessRecipes)
				{
					ShapelessRecipes sr = (ShapelessRecipes) rec;
					UnformedItemStack uis = new UnformedItemStack(sr.recipeItems.get(i));
					this.requiredItemsToCraft.add(uis);
				}
				if(rec instanceof MagicianTableRecipe)
				{
					MagicianTableRecipe mtr = (MagicianTableRecipe) rec;
					UnformedItemStack uis = mtr.requiredItems[i];
					this.requiredItemsToCraft.add(uis);
				}
				if(rec instanceof RadiatingChamberRecipe)
				{
					RadiatingChamberRecipe rcr = (RadiatingChamberRecipe) rec;
					UnformedItemStack uis = new UnformedItemStack(rcr.recipeItems[i]);
					this.requiredItemsToCraft.add(uis);
				}
				if(rec instanceof ShapedOreRecipe)
				{
					ShapedOreRecipe sor = (ShapedOreRecipe) rec;
					UnformedItemStack uis = new UnformedItemStack(sor.getInput()[i]);
					this.requiredItemsToCraft.add(uis);
				}
				if(rec instanceof ShapedAssemblerRecipe)
				{
					ShapedAssemblerRecipe sor = (ShapedAssemblerRecipe) rec;
					UnformedItemStack uis = new UnformedItemStack(sor.getInput()[i]);
					this.requiredItemsToCraft.add(uis);
				}
				if(rec instanceof ShapelessOreRecipe)
				{
					ShapelessOreRecipe sor = (ShapelessOreRecipe) rec;
					UnformedItemStack uis = new UnformedItemStack(sor.getInput().get(i));
					this.requiredItemsToCraft.add(uis);
				}
			}
		}
	}
	
	/**
	 * Forms 3 lists of recipes, corresponding to the given ItemStack. 
	 * Then, adds all their components to this.actualRecipes; Also puts their results into this.allRecipes;
	 * @param is - part of a recipe
	 */
	public void formCraftList(ItemStack is)
	{
		allRecipes.clear();
		actualRecipes.clear();
		requiredItemsToCraft.clear();
		requiredStackSizeToCraft.clear();
		currentCraft = null;
		this.currentSelectedOne = null;
		if(is != null)
		{
			@SuppressWarnings("unchecked")
			List<IRecipe> rec = CraftingManager.getInstance().getRecipeList();
			List<IRecipe> lstCR = new ArrayList<IRecipe>();
			List<RadiatingChamberRecipe> lstRC = new ArrayList<RadiatingChamberRecipe>();
			List<MagicianTableRecipe> lstMT = new ArrayList<MagicianTableRecipe>();
			List<ShapedAssemblerRecipe> lstMA = new ArrayList<ShapedAssemblerRecipe>();
			ItemStack genStk = is.copy();
			genStk.stackSize = 0;
			String searchPair = genStk.toString();
			genStk = null;
			
			//Radiating Chamber Forming
			for(int i = 0; i < RadiatingChamberRecipes.craftMatrixByID.size(); ++i)
			{
				String searchPairSecond = RadiatingChamberRecipes.craftMatrixByID.get(i);
				if(searchPairSecond != null && !searchPairSecond.isEmpty())
				{
					if(searchPairSecond.contains(searchPair))
					{
						lstRC.addAll(RadiatingChamberRecipes.recipes.get(searchPairSecond));
					}
				}
			}
			
			//Magical Assembler Forming
			lstMA.addAll(MagicalAssemblerRecipes.findUsageRecipes(is));
			
			//Magician Table Forming
			lstMT.addAll(MagicianTableRecipes.getRecipiesByComponent(is));
			
			//Shaped||shapeless regular||ore recipes(will it crash? :o)
			for(IRecipe scr : rec)
			{
				if(scr instanceof ShapedRecipes)
				{
					ShapedRecipes sscr = (ShapedRecipes) scr;
					if(Arrays.asList(sscr.recipeItems).contains(is))
						lstCR.add(sscr);
				}
				if(scr instanceof ShapelessRecipes)
				{
					ShapelessRecipes sscr = (ShapelessRecipes) scr;
					for(int i = 0; i < sscr.recipeItems.size(); ++i)
					{
						ItemStack s = (ItemStack) sscr.recipeItems.get(i);
						if(s != null && s.isItemEqual(is))
							lstCR.add(sscr);
					}
				}
				if(scr instanceof ShapedOreRecipe)
				{
					ShapedOreRecipe sor = (ShapedOreRecipe) scr;
					for(Object o : sor.getInput())
					{
						ItemStack compared = null;
						if(o instanceof ItemStack)
						{
							compared = (ItemStack) o;
							if(compared.isItemEqual(is))
								lstCR.add(sor);
						}
						if(o instanceof Item)
						{
							compared = new ItemStack((Item)o,1,0);
							if(compared.isItemEqual(is))
								lstCR.add(sor);
						}
						if(o instanceof Block)
						{
							compared = new ItemStack((Block)o,1,0);
							if(compared.isItemEqual(is))
								lstCR.add(sor);
						}
						if(o instanceof ItemStack[])
						{
							for(ItemStack s : (ItemStack[])o)
							{
								if(s.isItemEqual(is))
									lstCR.add(sor);
							}
						}
						if(o instanceof String)
						{
							List<ItemStack> isLst = OreDictionary.getOres((String) o);
							for(ItemStack s : isLst)
							{
								if(s.isItemEqual(is))
									lstCR.add(sor);
							}
						}
						if(o instanceof List)
						{
							@SuppressWarnings("unchecked")
							List<ItemStack> isLst = (List<ItemStack>) o;
							for(ItemStack s : isLst)
							{
								if(s.isItemEqual(is))
									lstCR.add(sor);
							}
						}
					}
				}
				if(scr instanceof ShapelessOreRecipe)
				{
					ShapelessOreRecipe sor = (ShapelessOreRecipe) scr;
					for(Object o : sor.getInput())
					{
						ItemStack compared = null;
						if(o instanceof ItemStack)
						{
							compared = (ItemStack) o;
							if(compared.isItemEqual(is))
								lstCR.add(sor);
						}
						if(o instanceof Item)
						{
							compared = new ItemStack((Item)o,1,0);
							if(compared.isItemEqual(is))
								lstCR.add(sor);
						}
						if(o instanceof Block)
						{
							compared = new ItemStack((Block)o,1,0);
							if(compared.isItemEqual(is))
								lstCR.add(sor);
						}
						if(o instanceof ItemStack[])
						{
							for(ItemStack s : (ItemStack[])o)
							{
								if(s.isItemEqual(is))
									lstCR.add(sor);
							}
						}
						if(o instanceof String)
						{
							List<ItemStack> isLst = OreDictionary.getOres((String) o);
							for(ItemStack s : isLst)
							{
								if(s.isItemEqual(is))
									lstCR.add(sor);
							}
						}
						if(o instanceof List)
						{
							@SuppressWarnings("unchecked")
							List<ItemStack> isLst = (List<ItemStack>) o;
							for(ItemStack s : isLst)
							{
								if(s.isItemEqual(is))
									lstCR.add(sor);
							}
						}
					}
				}
			}
			
			for(IRecipe cr : lstMA)
			{
				if(!this.actualRecipes.contains(cr))
				{
					this.actualRecipes.add(cr);
					this.allRecipes.add(cr.getRecipeOutput());
				}
			}
			
			for(IRecipe cr : lstCR)
			{
				if(!this.actualRecipes.contains(cr))
				{
					this.actualRecipes.add(cr);
					this.allRecipes.add(cr.getRecipeOutput());
				}
			}
			
			for(RadiatingChamberRecipe cr : lstRC)
			{
				this.actualRecipes.add(cr);
				this.allRecipes.add(cr.result);
			}
			
			for(MagicianTableRecipe cr : lstMT)
			{
				this.actualRecipes.add(cr);
				this.allRecipes.add(cr.result);
			}
			
		}
	}

	@Override
	public int[] getOutputSlots() {
		return new int[]{0};
	}

}
