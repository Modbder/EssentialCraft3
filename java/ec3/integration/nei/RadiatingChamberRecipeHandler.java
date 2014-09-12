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
import ec3.api.RadiatingChamberRecipe;
import ec3.api.RadiatingChamberRecipes;
import ec3.client.gui.GuiRadiatingChamber;
import ec3.common.mod.EssentialCraftCore;

public class RadiatingChamberRecipeHandler extends TemplateRecipeHandler{
	
	public class RadiatingChamberCraftingPair extends CachedRecipe
	{
        public ArrayList<PositionedStack> ingredients;
        PositionedStack[] ingred;
        PositionedStack result;
        public int mruRequired;
        public float[] balanceBounds = new float[2];
        
        public RadiatingChamberCraftingPair(RadiatingChamberRecipe recipe)
        {
        	balanceBounds[0] = recipe.upperBalanceLine;
        	balanceBounds[1] = recipe.lowerBalanceLine;
        	this.ingred = new PositionedStack[2];
        	ItemStack[] craftMatrix = recipe.recipeItems;
        	for(int t = 0; t < craftMatrix.length; ++t)
        	{
        		if(craftMatrix[t]!=null)
        		{
        			this.ingred[t] = new PositionedStack(craftMatrix[t], 51+t*18, 6);
        		}
        	}
            this.result = new PositionedStack(recipe.result, 37, 19);
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
	             		x = 0;
	             		y = 0;
	             		break;
	             	}
	             	case 1:
	             	{
	             		x = 0;
	             		y = 2;
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
		return "Radiating Chamber";
	}

	@Override
	public String getGuiTexture() {
		// TODO Auto-generated method stub
		return "essentialcraft:textures/gui/gui_generic.png";
	}
	
    @Override
    public Class<? extends GuiContainer> getGuiClass()
    {
        return GuiRadiatingChamber.class;
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
    			RadiatingChamberRecipe rec = RadiatingChamberRecipes.getRecipeByResult(stk);
    			if(rec != null)
    			{
    				RadiatingChamberCraftingPair pair = new RadiatingChamberCraftingPair(rec);
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
    			for(int i = 0; i < RadiatingChamberRecipes.craftMatrixByID.size(); ++i)
    			{
    				String searchPairSecond = RadiatingChamberRecipes.craftMatrixByID.get(i);
    				if(searchPairSecond != null && !searchPairSecond.isEmpty())
    				{
    					if(searchPairSecond.contains(searchPair))
    					{
    						List<RadiatingChamberRecipe> lst = RadiatingChamberRecipes.recipes.get(searchPairSecond);
    						if(lst != null && !lst.isEmpty())
    						{
    							for(int i1 = 0; i1 < lst.size(); ++i1)
    							{
    								RadiatingChamberRecipe rec = lst.get(i1);
    		   		    			if(rec != null)
    	    		    			{
    	    		    				RadiatingChamberCraftingPair pair = new RadiatingChamberCraftingPair(rec);
    	    		    				arecipes.add(pair);
    	    		    			}
    							}
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
    	RadiatingChamberCraftingPair rec = (RadiatingChamberCraftingPair) arecipes.get(recipe);
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
		
		MiscUtils.drawTexturedModalRect(posX+36, 0, 0, 0, 17, 17,1);
		MiscUtils.drawTexturedModalRect(posX+36, 17, 0, 1, 17, 16,1);
		MiscUtils.drawTexturedModalRect(posX+36, 26, 0, 1, 17, 16,1);
		MiscUtils.drawTexturedModalRect(posX+36, 37, 0, 1, 17, 17,1);
		
		for(int x = 1; x < 6; ++x)
		{
			MiscUtils.drawTexturedModalRect(posX+36+16*x, 0, 1, 0, 16, 17,1);
			MiscUtils.drawTexturedModalRect(posX+36+16*x, 17, 1, 1, 16, 16,1);
			MiscUtils.drawTexturedModalRect(posX+36+16*x, 26, 1, 1, 16, 16,1);
			MiscUtils.drawTexturedModalRect(posX+36+16*x, 37, 1, 1, 16, 17,1);
		}
		
		MiscUtils.drawTexturedModalRect(posX+32+16*6, 0, 1, 0, 17, 17,1);
		MiscUtils.drawTexturedModalRect(posX+32+16*6, 17, 1, 1, 17, 16,1);
		MiscUtils.drawTexturedModalRect(posX+32+16*6, 26, 1, 1, 17, 16,1);
		MiscUtils.drawTexturedModalRect(posX+32+16*6, 37, 1, 1, 17, 17,1);
		
		Minecraft.getMinecraft().fontRenderer.drawStringWithShadow(rec.mruRequired+" MRU", posX+2, posY+5, 0xffffff);
		
		Minecraft.getMinecraft().fontRenderer.drawStringWithShadow(""+rec.mruRequired/20/60+"Min "+(rec.mruRequired/20-((rec.mruRequired/20/60)*60))+"Sec", posX+2+60, posY+5, 0xffffff);
    	
		float upperBalance = rec.balanceBounds[0];
		if(upperBalance > 2.0F)upperBalance = 2.0F;
		
		float lowerBalance = rec.balanceBounds[1];
		if(lowerBalance < 0.1F)lowerBalance = 0.0F;
		
		Minecraft.getMinecraft().fontRenderer.drawStringWithShadow("Upper Balance: "+upperBalance, posX+38, 5, 0xffffff);
		
		Minecraft.getMinecraft().fontRenderer.drawStringWithShadow("Lower Balance: "+lowerBalance, posX+38, 40, 0xffffff);
		
    	GL11.glPopMatrix();
    }
    
    @Override
    public void drawBackground(int recipe) {
    	super.drawBackground(recipe);
    	MiscUtils.bindTexture("essentialcraft", "textures/gui/slot_common.png");
    	MiscUtils.drawTexturedModalRect(18, 0, 0, 0, 18, 18, 0);
    	MiscUtils.drawTexturedModalRect(36, 18, 0, 0, 18, 18, 0);
    	MiscUtils.drawTexturedModalRect(18, 36, 0, 0, 18, 18, 0);
    }
    
    @Override
    public int recipiesPerPage() {
        return 1;
    }

}
