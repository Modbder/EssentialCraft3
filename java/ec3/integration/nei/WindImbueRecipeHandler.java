package ec3.integration.nei;

import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;

import org.lwjgl.opengl.GL11;

import DummyCore.Utils.MiscUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;
import codechicken.nei.PositionedStack;
import codechicken.nei.recipe.TemplateRecipeHandler;
import ec3.api.WindImbueRecipe;
import ec3.common.block.BlocksCore;
import ec3.common.item.ItemSoulStone;

public class WindImbueRecipeHandler extends TemplateRecipeHandler{
	
	public class WindImbueCraftingPair extends CachedRecipe
	{
        public ArrayList<PositionedStack> ingredients;
        PositionedStack[] ingred;
        PositionedStack result;
        public float energy;
        public int size;
        
        public WindImbueCraftingPair(WindImbueRecipe recipe)
        {
        	this.ingred = new PositionedStack[2];
        	ItemStack craftMatrix = recipe.transforming;
        	
        	this.ingred[0] = new PositionedStack(craftMatrix, 92, 6);
            this.result = new PositionedStack(recipe.result, 92, 1);
            ingredients = new ArrayList<PositionedStack>();
            setIngredients(craftMatrix,1);
            this.energy = recipe.enderEnergy;
        } 
        
        public void setIngredients(ItemStack items, int size)
        {
        	List<ItemStack> possibleStacks = new ArrayList<ItemStack>();
        	possibleStacks.add(items);
        	
        	for(int i = 0; i < possibleStacks.size(); ++i)
        	{
        		ItemStack is = possibleStacks.get(i);
        		is.stackSize = size;
        	}
        	
        	PositionedStack stack = new PositionedStack(possibleStacks, 92-36, 1, false);
        	PositionedStack rune = new PositionedStack(new ItemStack(BlocksCore.windRune), 92-18, 19, false);
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
	
    public void loadTransferRects() 
    {
    	super.transferRects.add(new RecipeTransferRect(new Rectangle(72,0,18,18), this.getOverlayIdentifier(), new Object[0]));
    }
    
    public String getOverlayIdentifier()
    {
        return "windRecipes";
    }

	@Override
	public String getRecipeName() {
		return "Wind Imbue";
	}
	
	@Override
	public String getGuiTexture() {
		return "essentialcraft:textures/gui/gui_generic.png";
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
    			WindImbueRecipe rec = WindImbueRecipe.findRecipeByResult(stk);
    			if(rec != null)
    			{
    				arecipes.add(new WindImbueCraftingPair(rec));
    			}
    		}
    	}else
    	{
    		if(outputId.equalsIgnoreCase(getOverlayIdentifier()))
    		{
    			for(int i = 0; i < WindImbueRecipe.recipes.size(); ++i)
    				arecipes.add(new WindImbueCraftingPair(WindImbueRecipe.recipes.get(i)));
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
    			WindImbueRecipe rec = WindImbueRecipe.findRecipeByComponent(stk);
    			if(rec != null)
    			{
    				arecipes.add(new WindImbueCraftingPair(rec));
    			}
    		}
    	}else
    	{
    		if(inputId.equalsIgnoreCase(getOverlayIdentifier()))
    		{
    			for(int i = 0; i < WindImbueRecipe.recipes.size(); ++i)
    				arecipes.add(new WindImbueCraftingPair(WindImbueRecipe.recipes.get(i)));
    		}
    	}
    }
    
    public void drawExtras(int recipe) 
    {
    	super.drawExtras(recipe);
    	GL11.glPushMatrix();
    	
    	WindImbueCraftingPair pair = WindImbueCraftingPair.class.cast(arecipes.get(recipe));
    	
    	Minecraft.getMinecraft().fontRenderer.drawString(MathHelper.floor_double(pair.energy)+" ESPE", 53, 36, 0x000000, false);
    	if(pair.result.item!=null&&pair.result.item.getItem() instanceof ItemSoulStone)
    		Minecraft.getMinecraft().fontRenderer.drawString("+Wind Relations", 53, 46, 0x81d17d, true);
    	
    	GL11.glPopMatrix();
    }
    
    @Override
    public void drawBackground(int recipe) {
    	super.drawBackground(recipe);
    	GL11.glPushMatrix();
    	
    	MiscUtils.bindTexture("essentialcraft", "textures/gui/slot_common.png");
    	MiscUtils.drawTexturedModalRect(91-36, 0, 0, 0, 18, 18, 0);
    	MiscUtils.drawTexturedModalRect(91, 0, 0, 0, 18, 18, 0);
    	
    	MiscUtils.bindTexture("essentialcraft", "textures/special/wind_icon.png");
    	Tessellator tec = Tessellator.instance;
    	tec.startDrawingQuads();
    	
    	tec.addVertexWithUV(74, 0, 50, 0, 0);
    	tec.addVertexWithUV(74, 16, 50, 0, 1);
    	tec.addVertexWithUV(74+16, 16, 50, 1, 1);
    	tec.addVertexWithUV(74+16, 0, 50, 1, 0);
    	
    	tec.draw();
    	
    
		GL11.glPopMatrix();
    }
    
    @Override
    public int recipiesPerPage() {
        return 2;
    }

}
