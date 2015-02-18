package ec3.integration.nei;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Set;

import org.lwjgl.opengl.GL11;

import DummyCore.Utils.MathUtils;
import DummyCore.Utils.MiscUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import codechicken.nei.PositionedStack;
import codechicken.nei.recipe.TemplateRecipeHandler;
import ec3.api.MagicalAssemblerRecipes;
import ec3.api.RadiatingChamberRecipe;
import ec3.api.RadiatingChamberRecipes;
import ec3.api.ShapedAssemblerRecipe;
import ec3.client.gui.GuiRadiatingChamber;
import ec3.common.mod.EssentialCraftCore;

public class MagicalAssemblerRecipeHandler extends TemplateRecipeHandler{
	
	public class MagicalAssemblerCraftingPair extends CachedRecipe
	{
        public ArrayList<PositionedStack> ingredients;
        PositionedStack[] ingred;
        PositionedStack result;
        public int mruRequired;
        
        public MagicalAssemblerCraftingPair(ShapedAssemblerRecipe recipe)
        {
        	this.ingred = new PositionedStack[9];
        	Object[] craftMatrix = recipe.getInput();
        	for(int t = 0; t < craftMatrix.length; ++t)
        	{
        		if(craftMatrix[t]!=null)
        		{
        			this.ingred[t] = new PositionedStack(craftMatrix[t], 25 + t%3*18, 6 + t/3*18);
        		}
        	}
            this.result = new PositionedStack(recipe.getRecipeOutput(), 119, 23);
            ingredients = new ArrayList<PositionedStack>();
            setIngredients(craftMatrix);
            this.mruRequired = recipe.mruRequired;
        } 
        
        public void setIngredients(Object[] items)
        {
        	for(int i = 0; i < items.length; ++i)
        	{
        		if(items[i] != null)
        		{
	        		PositionedStack ps = new PositionedStack(items[i], 25 + i%3*18, 6 + i/3*18);
	        		ps.setMaxSize(1);
	        		this.ingredients.add(ps);
        		}
        	}
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
		// TODO Auto-generated method stub
		return "Magical Assembler";
	}

	@Override
	public String getGuiTexture() {
		// TODO Auto-generated method stub
		return "minecraft:textures/gui/container/crafting_table.png";
	}
	
    @Override
    public Class<? extends GuiContainer> getGuiClass()
    {
        return ec3.client.gui.GuiMagicalAssembler.class;
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
    			List<ShapedAssemblerRecipe> lst = MagicalAssemblerRecipes.findRecipes(stk);
    			for(int i = 0; i < lst.size(); ++i)
    			{
    				ShapedAssemblerRecipe rec = lst.get(i);
    				if(rec != null)
    				{
    					MagicalAssemblerCraftingPair pair = new MagicalAssemblerCraftingPair(rec);
    					this.arecipes.add(pair);
    				}
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
    			List<ShapedAssemblerRecipe> lst = MagicalAssemblerRecipes.findUsageRecipes(stk);
    			for(int i = 0; i < lst.size(); ++i)
    			{
    				ShapedAssemblerRecipe rec = lst.get(i);
    				if(rec != null)
    				{
    					MagicalAssemblerCraftingPair pair = new MagicalAssemblerCraftingPair(rec);
    					this.arecipes.add(pair);
    				}
    			}
    		}
    	}
    }
    
    public void drawExtras(int recipe) 
    {
    	GL11.glPushMatrix();
    	int posX = 0;
    	int posY = 0;
    	MagicalAssemblerCraftingPair rec = (MagicalAssemblerCraftingPair) arecipes.get(recipe);
    	MiscUtils.bindTexture("essentialcraft", "textures/gui/mruStorage.png");
    	MiscUtils.drawTexturedModalRect(0, 0, 0, 0, 18, 72,1);
		int percentageScaled = MathUtils.pixelatedTextureSize((int) (rec.mruRequired), 10000, 72);
		IIcon icon = (IIcon) EssentialCraftCore.proxy.getClientIcon("mru");
		MiscUtils.drawTexture(1, -1+(74-percentageScaled), icon, 16, percentageScaled-2, 0);
		
    	MiscUtils.bindTexture("essentialcraft", "textures/gui/slot_common.png");
    	
    	posX = 18;
    	posY = 54;

		Minecraft.getMinecraft().fontRenderer.drawStringWithShadow((int)(rec.mruRequired)+" MRU", posX+2, posY+7, 0xffffff);
    	GL11.glPopMatrix();
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
