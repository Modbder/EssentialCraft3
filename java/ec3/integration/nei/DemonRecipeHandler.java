package ec3.integration.nei;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import net.minecraft.item.ItemStack;
import codechicken.nei.PositionedStack;
import codechicken.nei.recipe.TemplateRecipeHandler;
import ec3.api.DemonTrade;
import ec3.common.block.BlocksCore;
import ec3.common.item.ItemGenericEC3;
import ec3.common.item.ItemsCore;

public class DemonRecipeHandler extends TemplateRecipeHandler{
	
	public static final Random rnd = new Random();
	
	public class DemonCraftingPair extends CachedRecipe
	{
        public ArrayList<PositionedStack> ingredients;
        PositionedStack[] ingred;
        PositionedStack result;
        
        public DemonCraftingPair(DemonTrade recipe)
        {
        	this.ingred = new PositionedStack[2];
        	ItemStack craftMatrix = recipe.desiredItem;
        	if(craftMatrix == null)
        		craftMatrix = new ItemStack(ItemsCore.soul,1,DemonTrade.allMobs.indexOf(recipe.entityType));
        		
        	this.ingred[0] = new PositionedStack(craftMatrix, 92, 6);
            this.result = new PositionedStack(new ItemStack(ItemsCore.genericItem,1,52), 93-18, 1);
            ingredients = new ArrayList<PositionedStack>();
            setIngredients(craftMatrix,1);
        } 
        
        public void setIngredients(ItemStack items, int size)
        {
        	List<ItemStack> possibleStacks = new ArrayList<ItemStack>();
        	possibleStacks.add(items);
        	
        	PositionedStack stack = new PositionedStack(possibleStacks, 93-18, 19, false);
        	PositionedStack rune = new PositionedStack(new ItemStack(BlocksCore.demonicPentacle), 93-18, 37, false);
        	ingredients.add(stack);
        	ingredients.add(rune);
        	
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

	@Override
	public String getRecipeName() {
		return "Demon Trade";
	}
	
	@Override
	public String getGuiTexture() {
		return "essentialcraft:textures/gui/demon.png";
	}
	
    @Override
    public void loadCraftingRecipes(String outputId, Object... results)
    {
    	if(results.length > 0 && results != null && results[0] instanceof ItemStack)
    	{
    		ItemStack stk = ItemStack.class.cast(results[0]);
    		if(stk != null && stk.getItem() instanceof ItemGenericEC3 && stk.getItemDamage() == 52)
    		{
    			arecipes.add(new DemonCraftingPair(DemonTrade.trades.get(rnd.nextInt(DemonTrade.trades.size()))));
    		}
    	}
    }
    
    public void drawExtras(int recipe) 
    {
    	if(this.cycleticks%20==0)
    	{
    		arecipes.remove(0);
    		arecipes.add(new DemonCraftingPair(DemonTrade.trades.get(rnd.nextInt(DemonTrade.trades.size()))));
    	}
    	super.drawExtras(recipe);
    }
    
    @Override
    public void drawBackground(int recipe) {
    	super.drawBackground(recipe);
    }
    
    @Override
    public int recipiesPerPage() {
        return 1;
    }
}
