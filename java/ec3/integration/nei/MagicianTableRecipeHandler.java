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
import ec3.api.MagicianTableRecipe;
import ec3.api.MagicianTableRecipes;
import ec3.client.gui.GuiMagicianTable;
import ec3.common.mod.EssentialCraftCore;

public class MagicianTableRecipeHandler extends TemplateRecipeHandler{
	
	public class MagicianTableCraftingPair extends CachedRecipe
	{
        public ArrayList<PositionedStack> ingredients;
        PositionedStack[] ingred;
        PositionedStack result;
        public int mruRequired;
        
        public MagicianTableCraftingPair(MagicianTableRecipe recipe)
        {
        	this.ingred = new PositionedStack[5];
        	ItemStack[] craftMatrix = recipe.requiredItems;
        	for(int t = 0; t < craftMatrix.length; ++t)
        	{
        		if(craftMatrix[t]!=null)
        		{
        			this.ingred[t] = new PositionedStack(craftMatrix[t], 51+t*18, 6);
        		}
        	}
            this.result = new PositionedStack(recipe.result, 109, 19);
            ingredients = new ArrayList<PositionedStack>();
            setIngredients(craftMatrix);
            this.mruRequired = recipe.mruRequired;
        } 
        
        public void setIngredients(Object[] items)
        {
        	for(int i = 0; i < items.length; ++i)
        	{
	             if(items[i] == null)
	            	 continue;
	             int x = 0;
	             int y = 0;
	             switch(i)
	             {
	             	case 0:
	             	{
	             		x = 1;
	             		y = 1;
	             		break;
	             	}
	             	case 1:
	             	{
	             		x = 0;
	             		y = 0;
	             		break;
	             	}
	             	case 2:
	             	{
	             		x = 2;
	             		y = 0;
	             		break;
	             	}
	             	case 3:
	             	{
	             		x = 0;
	             		y = 2;
	             		break;
	             	}
	             	case 4:
	             	{
	             		x = 2;
	             		y = 2;
	             		break;
	             	}
	             }
	             PositionedStack stack = new PositionedStack(items[i], 19+x*18, 1+y*18, false);
	             stack.setMaxSize(1);
	             ingredients.add(stack);
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
		return "Magician Table";
	}

	@Override
	public String getGuiTexture() {
		// TODO Auto-generated method stub
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
    			MagicianTableRecipe rec = MagicianTableRecipes.getRecipeByResult(stk);
    			if(rec != null)
    			{
    				MagicianTableCraftingPair pair = new MagicianTableCraftingPair(rec);
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
    			ItemStack genStk = stk.copy();
    			genStk.stackSize = 0;
    			String searchPair = genStk.toString();
    			genStk = null;
    			for(int i = 0; i < MagicianTableRecipes.craftMatrixByID.size(); ++i)
    			{
    				String searchPairSecond = MagicianTableRecipes.craftMatrixByID.get(i);
    				if(searchPairSecond != null && !searchPairSecond.isEmpty())
    				{
    					if(searchPairSecond.contains(searchPair))
    					{
    		    			MagicianTableRecipe rec = MagicianTableRecipes.recipes.get(searchPairSecond);
    		    			if(rec != null)
    		    			{
    		    				MagicianTableCraftingPair pair = new MagicianTableCraftingPair(rec);
    		    				arecipes.add(pair);
    		    			}
    					}
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
    	MagicianTableCraftingPair rec = (MagicianTableCraftingPair) arecipes.get(recipe);
    	MiscUtils.bindTexture("essentialcraft", "textures/gui/mruStorage.png");
    	MiscUtils.drawTexturedModalRect(0, 0, 0, 0, 18, 72,1);
		int percentageScaled = MathUtils.pixelatedTextureSize(rec.mruRequired, 5000, 72);
		IIcon icon = (IIcon) EssentialCraftCore.proxy.getClientIcon("mru");
		MiscUtils.drawTexture(1, -1+(74-percentageScaled), icon, 16, percentageScaled-2, 0);
		
    	MiscUtils.bindTexture("essentialcraft", "textures/gui/slot_common.png");
    	
    	posX = 18;
    	posY = 54;
    	
    	MiscUtils.drawTexturedModalRect(posX, posY, 0, 0, 17, 18,1);
    	MiscUtils.drawTexturedModalRect(posX+17, posY, 1, 0, 16, 18,1);
    	MiscUtils.drawTexturedModalRect(posX+17+16, posY, 1, 0, 16, 18,1);
    	MiscUtils.drawTexturedModalRect(posX+17+32, posY, 1, 0, 16, 18,1);
		MiscUtils.drawTexturedModalRect(posX+17+48, posY, 1, 0, 16, 18,1);
		MiscUtils.drawTexturedModalRect(posX+17+64, posY, 1, 0, 16, 18,1);
		MiscUtils.drawTexturedModalRect(posX+17+80, posY, 1, 0, 16, 18,1);
		MiscUtils.drawTexturedModalRect(posX+17+96, posY, 1, 0, 16, 18,1);
		MiscUtils.drawTexturedModalRect(posX+17+111, posY, 1, 0, 17, 18,1);
		
		Minecraft.getMinecraft().fontRenderer.drawStringWithShadow(rec.mruRequired+" MRU", posX+2, posY+5, 0xffffff);
		
		Minecraft.getMinecraft().fontRenderer.drawStringWithShadow(""+rec.mruRequired/20/60+"Min "+(rec.mruRequired/20-((rec.mruRequired/20/60)*60))+"Sec", posX+2+60, posY+5, 0xffffff);
    	
    	GL11.glPopMatrix();
    }
    
    @Override
    public void drawBackground(int recipe) {
    	super.drawBackground(recipe);
    	MiscUtils.bindTexture("essentialcraft", "textures/gui/slot_common.png");
    	MiscUtils.drawTexturedModalRect(18, 0, 0, 0, 18, 18, 0);
    	MiscUtils.drawTexturedModalRect(54, 0, 0, 0, 18, 18, 0);
    	MiscUtils.drawTexturedModalRect(36, 18, 0, 0, 18, 18, 0);
    	MiscUtils.drawTexturedModalRect(18, 36, 0, 0, 18, 18, 0);
    	MiscUtils.drawTexturedModalRect(54, 36, 0, 0, 18, 18, 0);
    	MiscUtils.drawTexturedModalRect(108, 18, 0, 0, 18, 18, 0);
    }
    
    @Override
    public int recipiesPerPage() {
        return 1;
    }

}
