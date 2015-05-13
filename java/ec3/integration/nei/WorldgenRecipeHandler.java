package ec3.integration.nei;

import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;

import org.lwjgl.opengl.GL11;

import DummyCore.Utils.MiscUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import codechicken.nei.PositionedStack;
import codechicken.nei.recipe.TemplateRecipeHandler;
import ec3.common.block.BlocksCore;
import ec3.common.item.ItemDrop;
import ec3.common.item.ItemGenericEC3;
import ec3.common.item.ItemsCore;

public class WorldgenRecipeHandler extends TemplateRecipeHandler{

	public class WorldgenCraftingPair extends CachedRecipe
	{
        public ArrayList<PositionedStack> ingredients;
        PositionedStack ingred;
        PositionedStack result;
        public int minDrop, maxDrop;
        public boolean fortune;

        public WorldgenCraftingPair(ItemStack recipe, ItemStack value, int i, int j, boolean b)
        {
        	minDrop = i;
        	maxDrop = j;
        	fortune = b;
        	
            this.result = new PositionedStack(value, 76, 72);
            this.ingred = new PositionedStack(recipe, 76, 19);
            
            ingredients = new ArrayList<PositionedStack>();
            
            ingredients.add(ingred);
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
		return "EssentialCraft Worldgen";
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
    		if(results[0] instanceof ItemStack)
    		{
    			ItemStack stk = ItemStack.class.cast(results[0]);
    			if(stk.getItem() != null)
    			{
    				Item itm = stk.getItem();
    				int meta = stk.getItemDamage();
    				
    				if(itm instanceof ItemGenericEC3)
    				{
    					if(meta == 51)
    					{
    						this.arecipes.add(new WorldgenCraftingPair(new ItemStack(Blocks.lapis_ore),stk,12,21,true));
    					}
    				}
    				
    				if(itm instanceof ItemDrop)
    				{
    					this.arecipes.add(new WorldgenCraftingPair(new ItemStack(BlocksCore.oreDrops,1,meta == 4 ? 0 : meta+1),stk,1,2,false));
    				}
    			}
    		}
    	}else
    	{
    		if(outputId.equalsIgnoreCase(getOverlayIdentifier()))
    		{
    			this.arecipes.add(new WorldgenCraftingPair(new ItemStack(Blocks.lapis_ore),new ItemStack(ItemsCore.genericItem,1,51),12,21,true));
    			for(int meta = 0; meta < 5; ++meta)
    				this.arecipes.add(new WorldgenCraftingPair(new ItemStack(BlocksCore.oreDrops,1,meta == 4 ? 0 : meta+1),new ItemStack(ItemsCore.drops,1,meta),1,2,false));
    		}
    	}
    }
    
    public void loadTransferRects() 
    {
    	super.transferRects.add(new RecipeTransferRect(new Rectangle(78,38,18,28), this.getOverlayIdentifier(), new Object[0]));
    }
    
    public String getOverlayIdentifier()
    {
        return "ec3Worldgen";
    }
    
    public void drawExtras(int recipe) 
    {
    	super.drawExtras(recipe);
    	
    	GL11.glPushMatrix();
    	
    	WorldgenCraftingPair pair = WorldgenCraftingPair.class.cast(arecipes.get(recipe));
    	
    	Minecraft.getMinecraft().fontRenderer.drawString("Drop ", 72, 90, 0x000000);
    	Minecraft.getMinecraft().fontRenderer.drawStringWithShadow(""+pair.minDrop, 64, 100, 0xff0000);
    	Minecraft.getMinecraft().fontRenderer.drawString("->", 79, 100, 0x000000);
    	Minecraft.getMinecraft().fontRenderer.drawStringWithShadow(""+pair.maxDrop, 92, 100, 0x007700);
    	
    	Minecraft.getMinecraft().fontRenderer.drawString("Fortune ", 64, 110, 0x000000);
    	Minecraft.getMinecraft().fontRenderer.drawStringWithShadow(pair.fortune ? "Yes" : "No", 76, 120, pair.fortune ? 0x007700 : 0xff0000);
    	
    	GL11.glPopMatrix();
    }
    
    @Override
    public void drawBackground(int recipe) {
    	super.drawBackground(recipe);
    	
    	MiscUtils.bindTexture("essentialcraft", "textures/gui/slot_common.png");
    	MiscUtils.drawTexturedModalRect(75, 18, 0, 0, 18, 18, 0);
    	MiscUtils.drawTexturedModalRect(75, 71, 0, 0, 18, 18, 0);
    	
    	MiscUtils.bindTexture("minecraft", "textures/gui/container/brewing_stand.png");
    	MiscUtils.drawTexturedModalRect(78, 38, 96, 15, 18, 28, 0);
    }
    
    @Override
    public int recipiesPerPage() {
        return 1;
    }

}
