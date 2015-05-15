package ec3.client.gui;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Random;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.client.GuiScrollingList;
import DummyCore.Client.GuiCommon;
import DummyCore.Client.GuiElement;
import DummyCore.Utils.MiscUtils;
import DummyCore.Utils.UnformedItemStack;
import ec3.api.ITEHasMRU;
import ec3.api.MagicianTableRecipe;
import ec3.api.RadiatingChamberRecipe;
import ec3.api.ShapedAssemblerRecipe;
import ec3.client.gui.element.GuiMRUState;
import ec3.client.gui.element.GuiMRUStorage;
import ec3.common.tile.TileMagicalAssembler;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.inventory.Container;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.ShapedRecipes;
import net.minecraft.item.crafting.ShapelessRecipes;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.StatCollector;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.oredict.ShapelessOreRecipe;

public class GuiMagicalAssembler extends GuiCommon{
	
	public int recipiesAmount = 0;
	public int selectedRecipe;
	private GuiCraftsList modList;
	 protected static RenderItem itemRender = new RenderItem();
	 
	 public int mouseX, mouseY;

	public GuiMagicalAssembler(Container c, TileEntity tile) {
		super(c,tile);
		this.elementList.add(new GuiMRUStorage(7, 4, (ITEHasMRU) tile));
		this.elementList.add(new GuiMRUState(25, 58, (ITEHasMRU) tile, 0));
	}
	
    @SuppressWarnings("unchecked")
	public void initGui()
    {
        super.initGui();
	    int k = (this.width - this.xSize) / 2;
	    int l = (this.height - this.ySize) / 2;
	    TileMagicalAssembler assembler = (TileMagicalAssembler) this.genericTile;
        this.modList=new GuiCraftsList(this, 126, getRecipiesSize(), l+4, l+58, k+26, 28);
        this.modList.registerScrollButtons(this.buttonList, 7, 8);
        recipiesAmount = this.getRecipiesSize();
        if(assembler.currentRecipe == -1)
        	this.buttonList.add(new GuiButton(0, k+152, l+21, 20, 20, "Set"));
        else
        {
        	this.buttonList.add(new GuiButton(1, k+120, l+37, 30, 20, "Clear"));
        }
    }
    
    public void drawScreen(int p_73863_1_, int p_73863_2_, float p_73863_3_)
    {
    	mouseX = p_73863_1_;
    	mouseY = p_73863_2_;
    	super.drawScreen(p_73863_1_, p_73863_2_, p_73863_3_);
	    int k = (this.width - this.xSize) / 2;
	    int l = (this.height - this.ySize) / 2;
	    
	    TileMagicalAssembler assembler = (TileMagicalAssembler) this.genericTile;
	    
	    if(recipiesAmount != this.getRecipiesSize())
	    {
	    	this.buttonList.clear();
	    	this.initGui();
	    }
	    if(assembler.currentRecipe == -1)
	    {
	    	if(this.getRecipiesSize() <= 0)
	    	{
	    		Object gb = this.buttonList.get(0);
	    		if(gb != null)
	    		{
	    			GuiButton b = (GuiButton) gb;
	    			b.enabled = false;
	    		}
	    	}else
	    	{
	    		Object gb = this.buttonList.get(0);
	    		if(gb != null)
	    		{
	    			GuiButton b = (GuiButton) gb;
	    			b.enabled = true;
	    		}
	    	}
	    }
	    
	    if(assembler.currentRecipe == -1)
	    	this.modList.drawScreen(p_73863_1_, p_73863_2_, p_73863_3_);
	    else
	    {
	    	if(assembler.currentCraft != null)
	    	{
	    		GuiElement element = this.elementList.get(1);
	    		if(assembler.getMRU() - assembler.getRequiredMRUToCraft() >= 0)
	    		{
	    			this.fontRendererObj.drawString("V", element.getX()+k+136, element.getY()+l+5, 0x00ff00);
	    		}else
	    		{
	    			this.fontRendererObj.drawString("X", element.getX()+k+136, element.getY()+l+5, 0xff0000);
	    		}
	    		RenderHelper.disableStandardItemLighting();
	    		RenderHelper.enableGUIStandardItemLighting();
	    		itemRender.renderItemAndEffectIntoGUI(fontRendererObj, Minecraft.getMinecraft().renderEngine, assembler.currentCraft, k+30, l+1);
	    		
	    		
	    		RenderHelper.enableStandardItemLighting();
	    		GL11.glColor3f(1, 1, 1);
	    		GL11.glDisable(GL11.GL_LIGHTING);
	    		this.fontRendererObj.drawStringWithShadow(assembler.currentCraft.getDisplayName(), k+48, l+3, 0x00ff00);
	    		GL11.glEnable(GL11.GL_LIGHTING);
	    		List<UnformedItemStack> hasItems = new ArrayList<UnformedItemStack>(assembler.requiredItemsToCraft.size());
        	    List<UnformedItemStack> hasItemsCopy = new ArrayList<UnformedItemStack>(assembler.requiredItemsToCraft.size());
        	    hasItemsCopy.addAll(assembler.requiredItemsToCraft);
				for(int i = 0; i < assembler.requiredItemsToCraft.size(); ++i)
				{
					hasItems.add(null);
				}
				for(int i = 2; i < 17; ++i)
				{
					ItemStack is = assembler.getStackInSlot(i);
					if(is != null)
					{
						forCycle:for(int j = 0; j < hasItemsCopy.size(); ++j)
						{
							if(hasItemsCopy.get(j) != null && hasItemsCopy.get(j).itemStackMatches(is))
							{
								hasItemsCopy.set(j, null);
								hasItems.set(j,assembler.requiredItemsToCraft.get(j));
								break forCycle;
							}
						}
					}
				}
				for(int i = 0; i < hasItems.size(); ++i)
				{
					if(hasItems.get(i) == null && assembler.requiredItemsToCraft.get(i).possibleStacks.isEmpty())
						hasItems.set(i, assembler.requiredItemsToCraft.get(i));
				}
				
        	    for(int i = 0; i < assembler.requiredItemsToCraft.size(); ++i)
        	    {
        	    	UnformedItemStack is = assembler.requiredItemsToCraft.get(i);
        	    	if(is != null)
        	    	{
        	    		ItemStack ss = is.getISToDraw(this.genericTile.getWorldObj().getTotalWorldTime());
        	    		if(ss != null)
        	    		{
        	    			ItemStack drawed = ss.copy();
	        	    		if(drawed != null)
	        	    		{
		        	    		Random rnd = new Random(this.genericTile.getWorldObj().getTotalWorldTime()/20%20);
			        	    	if(drawed.getItemDamage() == -1 || drawed.getItemDamage() == OreDictionary.WILDCARD_VALUE)
			        	    	{
			        	    		List<ItemStack> sub = new ArrayList<ItemStack>();
			        	    		drawed.getItem().getSubItems(drawed.getItem(), drawed.getItem().getCreativeTab(), sub);
			        	    		drawed = sub.get(rnd.nextInt(sub.size()));
			        	    	}
			        	    	int x = k + 32 + i%5*20;
			        	    	int y = l + 16 + i/5*20;

			        	    	itemRender.renderItemAndEffectIntoGUI(fontRendererObj, Minecraft.getMinecraft().renderEngine, drawed, x, y);
			        	    	f:for(int n = 0; n < hasItems.size(); ++n)
			        	    	{
			        	    		if(hasItems.get(n) != null)
			        	    		{
			        	    			if(is.equals(hasItems.get(n)))
			        	    			{
			        	    				hasItems.set(n, null);
				        	    			this.fontRendererObj.drawString("V", x+12, y+12, 0x00ff00);
				        	    			break f;
			        	    			}
			        	    		}
			        	    	}
			        	    	GL11.glColor3f(1, 1, 1);
	        	    		}
		        	    	drawed = null;
	        	    	}
        	    	}
        	    }
	    	}
	    }
	    
    }
    
    public int getRecipiesSize()
    {
    	TileMagicalAssembler assembler = (TileMagicalAssembler) this.genericTile;
    	return assembler.allRecipes.size();
    }
	
    protected void actionPerformed(GuiButton par1GuiButton) 
    {
    	TileMagicalAssembler assembler = (TileMagicalAssembler) this.genericTile;
    	if(par1GuiButton.id == 0)
    	{
    		assembler.currentRecipe = this.selectedRecipe;
    		assembler.formRequiredComponents();
    		MiscUtils.handleButtonPress(0, this.getClass(), GuiButton.class, Minecraft.getMinecraft().thePlayer, this.genericTile.xCoord, this.genericTile.yCoord, this.genericTile.zCoord, "||data:"+this.selectedRecipe);
	    	this.buttonList.clear();
	    	this.initGui();
    	}
    	if(par1GuiButton.id == 1)
    	{
    		assembler.actualRecipes.clear();
    		assembler.allRecipes.clear();
    		assembler.currentCraft = null;
    		assembler.currentRecipe = -1;
    		assembler.requiredItemsToCraft.clear();
    		this.selectedRecipe = -1;
    		if(assembler.getStackInSlot(1) != null)
    			assembler.formCraftList(assembler.getStackInSlot(1));
    		MiscUtils.handleButtonPress(1, this.getClass(), GuiButton.class, Minecraft.getMinecraft().thePlayer, this.genericTile.xCoord, this.genericTile.yCoord, this.genericTile.zCoord, "||data:-1");
	    	this.buttonList.clear();
	    	this.initGui();
    	}
    	super.actionPerformed(par1GuiButton);
    }
    
    public void elementClicked(int id)
    {
    	selectedRecipe = id;
    }
    
    public boolean indexSelected(int id)
    {
    	return id == selectedRecipe;
    }
    
    public class GuiCraftsList extends GuiScrollingList 
    {
    	public GuiMagicalAssembler parent;
    	
        public GuiCraftsList(GuiMagicalAssembler parent, int listWidth, int listHeight, int posY, int maxPosY, int posX, int maxPosX)
        {
            super(Minecraft.getMinecraft(), listWidth, listHeight, posY, maxPosY, posX, maxPosX);
            this.parent=parent;
        }
        
        @Override
        protected int getSize()
        {
            return parent.getRecipiesSize();
        }
        
        @Override
        protected void elementClicked(int var1, boolean var2)
        {
            this.parent.elementClicked(var1);
        }
        
        @Override
        protected boolean isSelected(int var1)
        {
            return false;
        }
        
        @Override
        protected void drawBackground()
        {
            //this.parent.drawDefaultBackground();
        }
        
        @Override
        protected int getContentHeight()
        {
            return (this.getSize()) * 28 + 1;
        }
        
        @SuppressWarnings("unchecked")
		@Override
        protected void drawSlot(int listIndex, int var2, int var3, int var4, Tessellator var5)
        {
        	TileMagicalAssembler assembler = (TileMagicalAssembler) this.parent.genericTile;
    	    int k = (this.parent.width - this.parent.xSize) / 2;
    	    int l = (this.parent.height - this.parent.ySize) / 2;

    	    
        	if(var3 > l+5 && var3 < l+38+5 && listIndex < assembler.allRecipes.size())
        	{
        		ItemStack rendered = assembler.allRecipes.get(listIndex);
        		IRecipe recipe = assembler.actualRecipes.get(listIndex);
        		if(rendered != null)
        		{
	        	    int defaultX = 28-13;
	        	    int defaultY = var3-l-17;
	        	    if(rendered.getItem() != null)
	        	    {
	        	    	if(!this.parent.indexSelected(listIndex))
	        	    		this.parent.fontRendererObj.drawStringWithShadow(rendered.getDisplayName(), k+50, var3, 0xffffff);
	        	    	else
	        	    		this.parent.fontRendererObj.drawStringWithShadow(rendered.getDisplayName(), k+50, var3, 0x00ff00);
	        	    }
	        	    this.parent.drawGradientRect(k+12+defaultX, l+16+defaultY, k+12+18+defaultX, l+16+18+defaultY, 0x88ffaaff, 0x88886688);
	       			this.parent.drawGradientRect(k+12+defaultX, l+16+defaultY, k+12+1+defaultX, l+16+18+defaultY, 0xff660066, 0xff330033);
	       			this.parent.drawGradientRect(k+12+defaultX, l+16+17+defaultY, k+12+18+defaultX, l+16+18+defaultY, 0xff330033, 0xff110011);
	       			this.parent.drawGradientRect(k+12+17+defaultX, l+16+defaultY, k+12+18+defaultX, l+16+18+defaultY, 0xff990099, 0xff110011);
	       			this.parent.drawGradientRect(k+12+defaultX, l+16+defaultY, k+12+18+defaultX, l+16+1+defaultY, 0xff660066, 0xff990099);
	       			if(rendered.getItem() != null)
	       			{
	       				itemRender.renderItemAndEffectIntoGUI(fontRendererObj, Minecraft.getMinecraft().renderEngine, rendered, k+28, var3);
	       			}
        		}
        		
        		if(parent.mouseX > k+28 && parent.mouseX < var2)
        		{
            		if(parent.mouseY > var3 && parent.mouseY < var3+28)
            		{
            			int sizeX = 112;
            			int sizeY = 64;
    	        	    this.parent.drawGradientRect(mouseX, mouseY, sizeX+mouseX, sizeY+mouseY, 0xff000000, 0xff333333);
    	        	    this.parent.drawGradientRect(mouseX, mouseY, sizeX+mouseX, 1+mouseY, 0xff000000, 0xff000000);
    	        	    this.parent.drawGradientRect(mouseX, mouseY+sizeY-1, sizeX+mouseX, sizeY+mouseY, 0xff333333, 0xff333333);
    	        	    this.parent.drawGradientRect(mouseX, mouseY, 1+mouseX, sizeY+mouseY, 0xf000000, 0xff333333);
    	        	    this.parent.drawGradientRect(sizeX+mouseX-1, mouseY, sizeX+mouseX, sizeY+mouseY, 0xff000000, 0xff333333);
    	        	    
    	        	    Random rnd = new Random(this.parent.genericTile.getWorldObj().getTotalWorldTime()/20%20);
    	        	    String recipeString = "";
    	        	    Hashtable<String, Integer> isLst = new Hashtable<String, Integer>();
    	        	    List<ItemStack> items = new ArrayList<ItemStack>();
    	        	    for(int i = 0; i < recipe.getRecipeSize(); ++i)
    	        	    {
    	        	    	if(recipe instanceof ShapedRecipes)
    	        	    	{
    	        	    		ShapedRecipes rec = (ShapedRecipes) recipe;
    	        	    		ItemStack is = rec.recipeItems[i];
    	        	    		if(is != null)
    	        	    		{
    	        	    			if(isLst.containsKey(is.toString()))
    	        	    			{
    	        	    				isLst.put(is.toString(), isLst.get(is.toString())+1);
    	        	    			}else
    	        	    			{
    	        	    				items.add(is);
    	        	    				isLst.put(is.toString(), 1);
    	        	    			}
    	        	    		}
    	        	    	}
    	        	    	if(recipe instanceof ShapedOreRecipe)
    	        	    	{
    	        	    		ShapedOreRecipe rec = (ShapedOreRecipe) recipe;
    	        	    		Object drawable = rec.getInput()[i];
    	        	    		ItemStack needToDraw = null;
    	        	    		 if(drawable instanceof ItemStack)
    	        					 needToDraw = (ItemStack) drawable;
    	        				 if(drawable instanceof Item)
    	        					 needToDraw = new ItemStack((Item)drawable);
    	        				 if(drawable instanceof Block)
    	        					 needToDraw = new ItemStack((Block)drawable);
    	        				 if(drawable instanceof ItemStack[])
    	        				 {
    	        					 needToDraw = ((ItemStack[])drawable)[rnd.nextInt(((ItemStack[])drawable).length)];
    	        				 }
    	        				 if(drawable instanceof String)
    	        				 {
    	        					 List<ItemStack> oreStacks = OreDictionary.getOres((String) drawable);
    	        					 int drawingID = rnd.nextInt(oreStacks.size());
    	        					 needToDraw = oreStacks.get(drawingID);
    	        				 }
    	        				 if(drawable instanceof List)
    	        				 {
    	        					 List<ItemStack> oreStacks = (List<ItemStack>) drawable;
    	        					 int drawingID = rnd.nextInt(oreStacks.size());
    	        					 needToDraw = oreStacks.get(drawingID);
    	        				 }
    	        				 
    	        				 if(needToDraw != null)
    	        				 {
     	        	    			if(isLst.containsKey(needToDraw.toString()))
     	        	    			{
     	        	    				isLst.put(needToDraw.toString(), isLst.get(needToDraw.toString())+1);
     	        	    			}else
     	        	    			{
     	        	    				items.add(needToDraw);
     	        	    				isLst.put(needToDraw.toString(), 1);
     	        	    			}
    	        				 }
    	        	    	}
       	        	    	if(recipe instanceof ShapedAssemblerRecipe)
    	        	    	{
       	        	    		ShapedAssemblerRecipe rec = (ShapedAssemblerRecipe) recipe;
    	        	    		Object drawable = rec.getInput()[i];
    	        	    		ItemStack needToDraw = null;
    	        	    		 if(drawable instanceof ItemStack)
    	        					 needToDraw = (ItemStack) drawable;
    	        				 if(drawable instanceof Item)
    	        					 needToDraw = new ItemStack((Item)drawable);
    	        				 if(drawable instanceof Block)
    	        					 needToDraw = new ItemStack((Block)drawable);
    	        				 if(drawable instanceof ItemStack[])
    	        				 {
    	        					 needToDraw = ((ItemStack[])drawable)[rnd.nextInt(((ItemStack[])drawable).length)];
    	        				 }
    	        				 if(drawable instanceof String)
    	        				 {
    	        					 List<ItemStack> oreStacks = OreDictionary.getOres((String) drawable);
    	        					 int drawingID = rnd.nextInt(oreStacks.size());
    	        					 needToDraw = oreStacks.get(drawingID);
    	        				 }
    	        				 if(drawable instanceof List)
    	        				 {
    	        					 List<ItemStack> oreStacks = (List<ItemStack>) drawable;
    	        					 int drawingID = rnd.nextInt(oreStacks.size());
    	        					 needToDraw = oreStacks.get(drawingID);
    	        				 }
    	        				 
    	        				 if(needToDraw != null)
    	        				 {
     	        	    			if(isLst.containsKey(needToDraw.toString()))
     	        	    			{
     	        	    				isLst.put(needToDraw.toString(), isLst.get(needToDraw.toString())+1);
     	        	    			}else
     	        	    			{
     	        	    				items.add(needToDraw);
     	        	    				isLst.put(needToDraw.toString(), 1);
     	        	    			}
    	        				 }
    	        	    	}
    	        	    	if(recipe instanceof ShapelessOreRecipe)
    	        	    	{
    	        	    		ShapelessOreRecipe rec = (ShapelessOreRecipe) recipe;
    	        	    		Object drawable = rec.getInput().get(i);
    	        	    		ItemStack needToDraw = null;
    	        	    		 if(drawable instanceof ItemStack)
    	        					 needToDraw = (ItemStack) drawable;
    	        				 if(drawable instanceof Item)
    	        					 needToDraw = new ItemStack((Item)drawable);
    	        				 if(drawable instanceof Block)
    	        					 needToDraw = new ItemStack((Block)drawable);
    	        				 if(drawable instanceof ItemStack[])
    	        				 {
    	        					 needToDraw = ((ItemStack[])drawable)[rnd.nextInt(((ItemStack[])drawable).length)];
    	        				 }
    	        				 if(drawable instanceof String)
    	        				 {
    	        					 List<ItemStack> oreStacks = OreDictionary.getOres((String) drawable);
    	        					 int drawingID = rnd.nextInt(oreStacks.size());
    	        					 needToDraw = oreStacks.get(drawingID);
    	        				 }
    	        				 if(drawable instanceof List)
    	        				 {
    	        					 List<ItemStack> oreStacks = (List<ItemStack>) drawable;
    	        					 int drawingID = rnd.nextInt(oreStacks.size());
    	        					 needToDraw = oreStacks.get(drawingID);
    	        				 }
    	        				 
    	        				 if(needToDraw != null)
    	        				 {
     	        	    			if(isLst.containsKey(needToDraw.toString()))
     	        	    			{
     	        	    				isLst.put(needToDraw.toString(), isLst.get(needToDraw.toString())+1);
     	        	    			}else
     	        	    			{
     	        	    				items.add(needToDraw);
     	        	    				isLst.put(needToDraw.toString(), 1);
     	        	    			}
    	        				 }
    	        	    	}
    	        	    	
    	        	    	if(recipe instanceof MagicianTableRecipe)
    	        	    	{
    	        	    		MagicianTableRecipe mtr = (MagicianTableRecipe) recipe;
    	        	    		UnformedItemStack is = mtr.requiredItems[i];
    	        	    		ItemStack needToDraw = is.getISToDraw(this.parent.genericTile.getWorldObj().getTotalWorldTime());
     	        				 if(needToDraw != null)
    	        				 {
     	        	    			if(isLst.containsKey(needToDraw.toString()))
     	        	    			{
     	        	    				isLst.put(needToDraw.toString(), isLst.get(needToDraw.toString())+1);
     	        	    			}else
     	        	    			{
     	        	    				items.add(needToDraw);
     	        	    				isLst.put(needToDraw.toString(), 1);
     	        	    			}
    	        				 }
    	        	    	}
    	        	    	
    	        	    	if(recipe instanceof RadiatingChamberRecipe)
    	        	    	{
    	        	    		RadiatingChamberRecipe rcr = (RadiatingChamberRecipe) recipe;
    	        	    		ItemStack needToDraw = rcr.recipeItems[i];
    	        				if(needToDraw != null)
    	        				{
	    	        	    		if(isLst.containsKey(needToDraw.toString()))
    	        	    			{
    	        	    				isLst.put(needToDraw.toString(), isLst.get(needToDraw.toString())+1);
    	        	    			}else
    	        	    			{
    	        	    				items.add(needToDraw);
    	        	    				isLst.put(needToDraw.toString(), 1);
    	        	    			}
    	        				}
    	        	    	}
    	        	    }
    	        	    
    	        	    if(recipe instanceof ShapedRecipes || recipe instanceof ShapedOreRecipe)
    	        	    {
    	        	    	recipeString = StatCollector.translateToLocal("ec3.txt.shapedRecipe");
    	        	    	
    	        	    }
    	        	    if(recipe instanceof ShapelessRecipes || recipe instanceof ShapelessOreRecipe)
    	        	    {
    	        	    	recipeString = StatCollector.translateToLocal("ec3.txt.shapelessRecipe");
    	        	    	
    	        	    }
    	        	    if(recipe instanceof MagicianTableRecipe)
    	        	    {
    	        	    	recipeString = StatCollector.translateToLocal("ec3.txt.magicianRecipe");
    	        	    	
    	        	    	this.parent.fontRendererObj.drawStringWithShadow("Required MRU: "+((MagicianTableRecipe)recipe).mruRequired, mouseX+2, mouseY+54, 0xffffff);
    	        	    }
    	        	    if(recipe instanceof ShapedAssemblerRecipe)
    	        	    {
    	        	    	recipeString = StatCollector.translateToLocal("ec3.txt.assemblerRecipe");
    	        	    	
    	        	    	this.parent.fontRendererObj.drawStringWithShadow("Required MRU: "+((ShapedAssemblerRecipe)recipe).mruRequired, mouseX+2, mouseY+54, 0xffffff);
    	        	    }
    	        	    if(recipe instanceof RadiatingChamberRecipe)
    	        	    {
    	        	    	recipeString = StatCollector.translateToLocal("ec3.txt.radiatingRecipe");
    	        	    	
    	        	    	this.parent.fontRendererObj.drawStringWithShadow("Required MRU: "+(int)(((RadiatingChamberRecipe)recipe).mruRequired*((RadiatingChamberRecipe)recipe).costModifier), mouseX+2, mouseY+54, 0xffffff);
    	        	    }
    	        	    
    	        	    
    	        	    for(int i = 0; i < items.size(); ++i)
    	        	    {
    	        	    	ItemStack drawed = items.get(i).copy();
    	        	    	if(drawed.getItemDamage() == -1 || drawed.getItemDamage() == OreDictionary.WILDCARD_VALUE)
    	        	    	{
    	        	    		List<ItemStack> sub = new ArrayList<ItemStack>();
    	        	    		drawed.getItem().getSubItems(drawed.getItem(), drawed.getItem().getCreativeTab(), sub);
    	        	    		drawed = sub.get(rnd.nextInt(sub.size()));
    	        	    	}
    	        	    	if(drawed != null)
    	        	    	{
    	        	    		int amount = 1;
    	        	    		if(isLst.containsKey(drawed.toString()))
    	        	    			amount = isLst.get(drawed.toString());
	    	        	    	int x = mouseX + 4 + i%5*20;
	    	        	    	int y = mouseY + 12 + i/5*20;
	    	        	    	itemRender.renderItemAndEffectIntoGUI(fontRendererObj, Minecraft.getMinecraft().renderEngine, drawed, x, y);
	    	        	    	this.parent.fontRendererObj.drawStringWithShadow("x"+amount+"", x+10, y+10, 0x0000ff);
    	        	    	}
    	        	    	drawed = null;
    	        	    }
    	        	    
    	        	    this.parent.fontRendererObj.drawStringWithShadow(recipeString, mouseX+12, mouseY+2, 0x0000ff);
            		}
        		}
        		
        	}
        }
    }

}
