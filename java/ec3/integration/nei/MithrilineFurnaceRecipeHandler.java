package ec3.integration.nei;

import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import DummyCore.Utils.MiscUtils;
import DummyCore.Utils.UnformedItemStack;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;
import codechicken.nei.PositionedStack;
import codechicken.nei.recipe.TemplateRecipeHandler;
import ec3.api.MithrilineFurnaceRecipe;
import ec3.api.MithrilineFurnaceRecipes;
import ec3.client.gui.GuiMagicianTable;
import ec3.common.tile.TileMithrilineFurnace;

public class MithrilineFurnaceRecipeHandler extends TemplateRecipeHandler{
	
	public class MithrilineFurnaceCraftingPair extends CachedRecipe
	{
        public ArrayList<PositionedStack> ingredients;
        PositionedStack[] ingred;
        PositionedStack result;
        public float energy;
        public int size;
        
        public MithrilineFurnaceCraftingPair(MithrilineFurnaceRecipe recipe)
        {
        	this.ingred = new PositionedStack[2];
        	UnformedItemStack craftMatrix = recipe.smelted;
        	this.ingred[0] = new PositionedStack(craftMatrix.possibleStacks, 51, 6);
            this.result = new PositionedStack(recipe.result, 55, 1);
            ingredients = new ArrayList<PositionedStack>();
            setIngredients(craftMatrix,recipe.requiredRecipeSize);
            this.energy = recipe.enderStarPulsesRequired;
            this.size = recipe.requiredRecipeSize;
        } 
        
        public void setIngredients(UnformedItemStack items, int size)
        {
        	List<ItemStack> possibleStacks = new ArrayList<ItemStack>();
        	possibleStacks.addAll(items.possibleStacks);
        	for(int i = 0; i < possibleStacks.size(); ++i)
        	{
        		ItemStack is = possibleStacks.get(i);
        		is.stackSize = size;
        	}
        	
        	PositionedStack stack = new PositionedStack(possibleStacks, 19, 1, false);
        	//stack.setMaxSize(1);
        	ingredients.add(stack);
        	
        	possibleStacks.clear();
        	possibleStacks = null;
        }
        
        @Override
        public List<PositionedStack> getIngredients()
        {
            return ingredients;
        }
        
        public PositionedStack getResult()
        {
            return result;
        }
		
	}
	
    public void loadTransferRects() 
    {
    	super.transferRects.add(new RecipeTransferRect(new Rectangle(36,0,18,18), this.getOverlayIdentifier(), new Object[0]));
    }
    
    public String getOverlayIdentifier()
    {
        return "mithrilineFurnace";
    }

	@Override
	public String getRecipeName() {
		return "Mithriline Furnace";
	}

	@Override
	public String getGuiTexture() {
		return "essentialcraft:textures/gui/gui_generic.png";
	}
	
    @Override
    public Class<? extends GuiContainer> getGuiClass()
    {
        return GuiMagicianTable.class;
    }
    
    @Override
    public void loadCraftingRecipes(String outputId, Object... results)
    {
    	if(results.length > 0)
    	{
    		Object obj = results[0];
    		if(obj instanceof ItemStack)
    		{
    			ItemStack stk = (ItemStack) obj;
    			MithrilineFurnaceRecipe rec = MithrilineFurnaceRecipes.findRecipeByResult(stk);
    			if(rec != null)
    			{
    				MithrilineFurnaceCraftingPair pair = new MithrilineFurnaceCraftingPair(rec);
    				arecipes.add(pair);
    			}
    		}
    	}else
    	{
    		if(outputId.equalsIgnoreCase(getOverlayIdentifier()))
	    		for(int i = 0; i < MithrilineFurnaceRecipes.allRegisteredRecipes.size(); ++i)
	    		{
	    			MithrilineFurnaceRecipe rec = MithrilineFurnaceRecipes.allRegisteredRecipes.get(i);
	    			if(rec != null)
	    			{
	    				MithrilineFurnaceCraftingPair pair = new MithrilineFurnaceCraftingPair(rec);
	    				arecipes.add(pair);
	    			}
	    		}
    	}
    }
    
    public void loadUsageRecipes(String inputId, Object... ingredients) 
    {
    	
    	if(ingredients.length > 0)
    	{
    		Object obj = ingredients[0];
    		if(obj instanceof ItemStack)
    		{
    			ItemStack stk = (ItemStack) obj;
    			
    			MithrilineFurnaceRecipe recs = MithrilineFurnaceRecipes.findRecipeByComponent(stk);
    			if(recs != null)
    			{
    				MithrilineFurnaceCraftingPair pair = new MithrilineFurnaceCraftingPair(recs);
    				arecipes.add(pair);
    			}
    		}
    	}else
    	{
    		if(inputId.equalsIgnoreCase(getOverlayIdentifier()))
    			loadCraftingRecipes(inputId,ingredients);
    	}
    }
    
    public void drawExtras(int recipe) 
    {
    	if(this.cycleticks%30 == 0)
    	{
    		Random rng = new Random();
    		MithrilineFurnaceCraftingPair cp = (MithrilineFurnaceCraftingPair) this.arecipes.get(recipe);
	    	for(int i = 0; i < cp.getIngredients().size(); ++i)
	    	{
	    		PositionedStack stack = cp.getIngredients().get(i);
	    		stack.setPermutationToRender(rng.nextInt(stack.items.length));
	    		for(int j = 0; j < stack.items.length; ++j)
	    			if(stack.items[j] != null)
	    				stack.items[j].stackSize = cp.size;
	    	}
    	}
    }
    
    @Override
    public void drawBackground(int recipe) {
    	super.drawBackground(recipe);
    	MiscUtils.bindTexture("essentialcraft", "textures/gui/slot_common.png");
    	MiscUtils.drawTexturedModalRect(18, 0, 0, 0, 18, 18, 0);
    	MiscUtils.drawTexturedModalRect(54, 0, 0, 0, 18, 18, 0);
    	MiscUtils.bindTexture("essentialcraft", "textures/gui/mithrilineFurnaceElements.png");
    	MithrilineFurnaceCraftingPair rec = (MithrilineFurnaceCraftingPair) arecipes.get(recipe);
		float current = rec.energy;
		float max = TileMithrilineFurnace.maxEnergy;
		
    	float m = current/max*100;
    	int n = MathHelper.floor_float(m/100*18);
    	int posX = 36;
    	int posY = 0;
    	MiscUtils.drawTexturedModalRect(posX, posY, 0, 14, 18, 18, 0);
		MiscUtils.drawTexturedModalRect(posX, posY+18-n, 18, 14+(18-n), 18, n, 0);
    }
    
    @Override
    public int recipiesPerPage() {
        return 2;
    }

}
