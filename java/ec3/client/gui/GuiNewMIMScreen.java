package ec3.client.gui;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

import DummyCore.Client.GuiElement;
import DummyCore.Utils.MathUtils;
import DummyCore.Utils.MiscUtils;
import ec3.client.gui.element.GuiMRUStorage;
import ec3.common.inventory.ContainerNewMIMScreen;
import ec3.common.mod.EssentialCraftCore;
import ec3.common.tile.TileNewMIMCraftingManager.CraftingPattern;
import ec3.common.tile.TileNewMIMScreen;
import ec3.network.PacketNBT;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;

public class GuiNewMIMScreen extends GuiContainer{

	TileNewMIMScreen screen;
	EntityPlayer player;
	int scrollIndex;
	int maxScrollIndex;
	ItemStack selectedStack;
	List<GuiElement> elementList = new ArrayList<GuiElement>();
	boolean secondPress;
	int recipeSelected;
	int lastPressedTime;
	GuiTextField search;
	GuiTextField stackSize;
	int[] btnActions = new int[]{1,-1,2,-2,4,-4,5,-5,8,-8,10,-10,16,-16,32,-32,50,-50,64,-64,100,-100,128,-128,Integer.MAX_VALUE,-Integer.MAX_VALUE};
	Hashtable<ItemStack,CraftingPattern> craftsByItemStack = new Hashtable<ItemStack,CraftingPattern>();
	
	public static boolean packetArrived = true;
	final ResourceLocation textures = new ResourceLocation("essentialcraft","textures/gui/mimScreen.png");
	final ResourceLocation stextures = new ResourceLocation("essentialcraft","textures/gui/mimScreenSlider.png");
	boolean isLeftMouseButtonPressed;
	
	public GuiNewMIMScreen(TileNewMIMScreen par1, EntityPlayer par2)
	{
		super(new ContainerNewMIMScreen(par2.inventory, par1));
		this.elementList.add(new GuiMRUStorage(7, 59, par1));
		screen = par1;
		player = par2;
		this.xSize = 256;
		this.ySize = 256;
		packetArrived = true;
	}
	
    public void updateScreen()
    {
    	if(lastPressedTime > 0)
    		--lastPressedTime;
    	else
    		secondPress = false;
        super.updateScreen();
    }
	
    public void onGuiClosed()
    {
        super.onGuiClosed();
        Keyboard.enableRepeatEvents(false);
    }
    
    public boolean isValidInt(char c, int keyID)
    {
    	return keyID == 1 || keyID == 3 || keyID == 22 || keyID == 24 || (keyID == 14) || (keyID == 199) || (keyID == 203) || (keyID == 205) || (keyID == 207) || (keyID == 211) || c == '0' || c == '1' || c == '2' || c == '3' || c == '4' || c == '5' || c == '6' || c == '7' || c == '8' || c == '9';
    }
    
    protected void keyTyped(char c, int keyID)
    {
        if (this.search.textboxKeyTyped(c, keyID) || (isValidInt(c,keyID) && stackSize.textboxKeyTyped(c, keyID)))
        {
        	setupMaxInt();
        }
        else
        {
        	if(keyID == 28 && selectedStack != null)
        	{
        		this.actionPerformed(GuiButton.class.cast(buttonList.get(26)));
        	}
            super.keyTyped(c, keyID);
        }
    }
    
    protected void mouseClicked(int p_73864_1_, int p_73864_2_, int p_73864_3_)
    {
        super.mouseClicked(p_73864_1_, p_73864_2_, p_73864_3_);
        this.search.mouseClicked(p_73864_1_, p_73864_2_, p_73864_3_);
        this.stackSize.mouseClicked(p_73864_1_, p_73864_2_, p_73864_3_);
    }
    
    public void drawScreen(int p_73863_1_, int p_73863_2_, float p_73863_3_)
    {
        super.drawScreen(p_73863_1_, p_73863_2_, p_73863_3_);
        GL11.glDisable(GL11.GL_LIGHTING);
        GL11.glDisable(GL11.GL_BLEND);
        this.search.drawTextBox();
        this.stackSize.drawTextBox();
    }
    
    public static class GuiRequestButton extends GuiButton
    {

		public GuiRequestButton(int id, int x, int y,int sizeX, int sizeY, String string) {
			super(id, x, y, sizeX, sizeY, string);
		}
		
		@Override
	    public void drawButton(Minecraft p_146112_1_, int p_146112_2_, int p_146112_3_)
	    {
	        if (this.visible)
	        {
	            FontRenderer fontrenderer = p_146112_1_.fontRenderer;
	            p_146112_1_.getTextureManager().bindTexture(buttonTextures);
	            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
	            this.field_146123_n = p_146112_2_ >= this.xPosition && p_146112_3_ >= this.yPosition && p_146112_2_ < this.xPosition + this.width && p_146112_3_ < this.yPosition + this.height;
	            int k = this.getHoverState(this.field_146123_n);
	            GL11.glEnable(GL11.GL_BLEND);
	            OpenGlHelper.glBlendFunc(770, 771, 1, 0);
	            GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
	            this.drawTexturedModalRect(this.xPosition, this.yPosition, 0, 46 + k * 20, this.width / 2, this.height);
	            this.drawTexturedModalRect(this.xPosition + this.width / 2, this.yPosition, 200 - this.width / 2, 46 + k * 20, this.width / 2, this.height);
	            this.mouseDragged(p_146112_1_, p_146112_2_, p_146112_3_);
	            int l = 14737632;

	            if (packedFGColour != 0)
	            {
	                l = packedFGColour;
	            }
	            else if (!this.enabled)
	            {
	                l = 10526880;
	            }
	            else if (this.field_146123_n)
	            {
	                l = 16777120;
	            }
	            
	            boolean unicode = Minecraft.getMinecraft().gameSettings.forceUnicodeFont || Minecraft.getMinecraft().fontRenderer.getUnicodeFlag();

	            if(!unicode)
	            	GL11.glScalef(0.5F, 0.5F, 0.5F);
	            else
	            	GL11.glScalef(0.95F, 0.95F, 0.95F);
	            
	            	this.drawCenteredString(fontrenderer, this.displayString, !unicode ? (this.xPosition + this.width / 2)*2 : MathHelper.floor_float((this.xPosition + this.width / 2)/0.95F), !unicode ? (this.yPosition + (this.height - 8) / 2)*2+4 : MathHelper.floor_float((this.yPosition + (this.height - 8) / 2)/0.95F), l);
	            if(!unicode)
	            	GL11.glScalef(2, 2, 2);
	            else
	            	GL11.glScalef(1F/0.95F, 1F/0.95F, 1F/0.95F);
	        }
	    }
    	
    }
	
    @SuppressWarnings("unchecked")
	public void initGui()
    {
    	buttonList.clear();
        super.initGui();
        Keyboard.enableRepeatEvents(true);
		int k = (this.width - this.xSize)/2;
		int l = (this.height - this.ySize)/2;
        search = new GuiTextField(fontRendererObj, k+4, l+4, 248, 20);
        stackSize = new GuiTextField(fontRendererObj, k+95, l+156, 64, 12);
        setupMaxInt();
        for(int i = 0; i < btnActions.length; ++i)
        {
        	boolean neg = i%2 == 1;
        	if(i < 8)
        	{
	        	GuiButton btn = new GuiButton(i, neg ? k-i/2*16 + 103 - 32 : k+i/2*16 + 103 + 64, l+154, 16, 16, neg ? String.valueOf(btnActions[i]) : "+"+btnActions[i]);
	        	this.buttonList.add(btn);
        	}else
        	{
        		int xIndex = i % 4 == 0 ? k + 212 : i % 4 == 1 ? k + 26 : i % 4 == 2 ? k + 235 : k + 4;
        		int yIndex = l + 140 + i/4*16;
        		if(btnActions[i] != Integer.MAX_VALUE && btnActions[i] != -Integer.MAX_VALUE)
        		{
		        	GuiButton btn = new GuiButton(i, xIndex, yIndex, 16, 16, neg ? ""+btnActions[i] : "+"+btnActions[i]);
		        	this.buttonList.add(btn);
        		}else
        		{
        			String s = neg ? "<<<" : ">>>";
		        	GuiButton btn = new GuiButton(i, neg ? k+4 : k+235,l+154, 16, 16, s);
		        	this.buttonList.add(btn);
        		}
        	}
        }
        GuiRequestButton request = new GuiRequestButton(26,k+3,l+46,27,10,"Request");
        this.buttonList.add(request);
    }
    
    @Override
	protected void actionPerformed(GuiButton b) 
	{
		super.actionPerformed(b);
		if(b.id < 26)
		{
			int i = btnActions[b.id];
			setupMaxInt();
			int currentInt = Integer.parseInt(this.stackSize.getText());
			if(i != Integer.MAX_VALUE && i != -Integer.MAX_VALUE)
			{
				currentInt += i;
				if(currentInt <= 0)
					currentInt = 1;
				this.stackSize.setText(String.valueOf(currentInt));
				setupMaxInt();
			}else
			{
				currentInt = i;
				if(currentInt < 0)
				{
					this.stackSize.setText("1");
					setupMaxInt();
					return;
				}
				if(selectedStack != null)
				{
					if(currentInt > selectedStack.stackSize)
					{
						this.stackSize.setText(String.valueOf(selectedStack.stackSize));
						setupMaxInt();
						return;
					}
				}
				setupMaxInt();
			}
		}else
		{
			if(b.id == 26)
			{
				if(screen != null && screen.parent != null && selectedStack != null)
				{
					setupMaxInt();
					
					NBTTagCompound carriedToServer = new NBTTagCompound();
					NBTTagCompound itemTag = new NBTTagCompound();
					selectedStack.writeToNBT(itemTag);
					carriedToServer.setInteger("x", screen.xCoord);
					carriedToServer.setInteger("y", screen.yCoord);
					carriedToServer.setInteger("z", screen.zCoord);
					carriedToServer.setInteger("px", screen.parent.xCoord);
					carriedToServer.setInteger("py", screen.parent.yCoord);
					carriedToServer.setInteger("pz", screen.parent.zCoord);
					carriedToServer.setString("requester", Minecraft.getMinecraft().thePlayer.getCommandSenderName());
					carriedToServer.setInteger("size", Integer.parseInt(stackSize.getText()));
					carriedToServer.setBoolean("craft", recipeSelected > -1);
					carriedToServer.setTag("requestedItem", itemTag);
					PacketNBT sent = new PacketNBT(carriedToServer).setID(5);
					EssentialCraftCore.network.sendToServer(sent);
					packetArrived = false;
				}
			}
		}
	}
	
    @Override
    public boolean doesGuiPauseGame()
    {
        return false;
    }

	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks,int mX, int mY) {
		int k = (this.width - this.xSize)/2;
		int l = (this.height - this.ySize)/2;
		Minecraft.getMinecraft().renderEngine.bindTexture(textures);
		this.drawTexturedModalRect(k, l, 0, 0, xSize, ySize);
		
		GuiButton.class.cast(this.buttonList.get(26)).enabled = selectedStack != null && packetArrived;
		
		for(int i = 0; i < this.elementList.size(); ++i)
		{
			GuiElement element = elementList.get(i);
			Minecraft.getMinecraft().renderEngine.bindTexture(element.getElementTexture());
			element.draw(k+element.getX(),l+element.getY());
			GL11.glColor3f(1, 1, 1);
		}
		
		MiscUtils.bindTexture("essentialcraft", "textures/gui/mimScreenSlider.png");
		this.drawTexturedModalRect(k+228, l+25, 0, 0, 12, 126);
		if(Mouse.isButtonDown(0))
		{
			if(mX >= k+228 && mX <= k+228+12)
			{
				if(mY >= l+25 && mY < l+25+126)
				{
					int yIndex = mY - (l+25);
					int percentage = MathHelper.floor_double((double)yIndex/126*100);
					double dIndex = (double)percentage/100D*maxScrollIndex;
					int iIndex = (int)dIndex;
					double lIndex = dIndex-(double)iIndex;
					int index = lIndex >= 0.5D ? iIndex+1 : iIndex;
					scrollIndex = Math.min(index, maxScrollIndex);
				}
			}
		}
		this.drawTexturedModalRect(k+229, l+26+(maxScrollIndex == 0 ? 0 : MathUtils.pixelatedTextureSize(scrollIndex, maxScrollIndex, 113)), 12, 0, 10, 11);
		
		if(!Mouse.isButtonDown(0) && isLeftMouseButtonPressed)
			isLeftMouseButtonPressed = false;
		
		if(screen!=null&&screen.parent!=null)
		{
			ArrayList<ItemStack> drawnItems;
			
			
			if(search.getText() != null && !search.getText().isEmpty())
				drawnItems = screen.parent.getItemsByName(search.getText());
			else
				drawnItems = screen.parent.getAllItems();
			
			ArrayList<CraftingPattern> crafts;
			
			if(search.getText() != null && !search.getText().isEmpty())
				crafts = screen.parent.getCraftsByName(search.getText());
			else
				crafts = screen.parent.getAllCrafts();
			
			this.craftsByItemStack.clear();
			
			int regSize = drawnItems.size();
			
			for(CraftingPattern p : crafts)
			{
				craftsByItemStack.put(p.result, p);
				drawnItems.add(p.result);
			}
			
			if(drawnItems == null || drawnItems.isEmpty())
			{
				this.selectedStack = null;
				secondPress = false;
				recipeSelected = -1;
			}
			if(selectedStack != null)
			{
				boolean hasStack = false;
				
				if(recipeSelected > -1)
				{
					for(ItemStack is : craftsByItemStack.keySet())
					{
						if(is != null && is.isItemEqual(selectedStack) && ItemStack.areItemStackTagsEqual(is, selectedStack) && is.stackSize == selectedStack.stackSize)
						{
							hasStack = true;
							break;
						}
					}
				}else
				{
					for(ItemStack is : drawnItems)
					{
						if(is != null && is.isItemEqual(selectedStack) && ItemStack.areItemStackTagsEqual(is, selectedStack))
						{
							hasStack = true;
							break;
						}
					}
				}
				
				if(!hasStack)
				{
					selectedStack = null;
					secondPress = false;
					recipeSelected = -1;
				}
			}
				
			RenderHelper.disableStandardItemLighting();
			RenderHelper.enableGUIStandardItemLighting();
			maxScrollIndex = Math.max(0, drawnItems.size() <= 60 ? 0 : (drawnItems.size()-60)/10+1);
			
			int dWheel = Mouse.getDWheel();
			
			if(dWheel > 0)
				scrollIndex = Math.max(0, --scrollIndex);
			
			if(dWheel < 0)
				scrollIndex = Math.min(maxScrollIndex, ++scrollIndex);
			
			if(scrollIndex > maxScrollIndex)
				scrollIndex = 0;
			
			for(int i = scrollIndex*10; i < drawnItems.size() && i < 60+scrollIndex*10; ++i)
			{
				int x = k+32+i%10*19;
				int y = l+28+i/10*19-scrollIndex*19;
				if(selectedStack != null)
				{
					if(drawnItems.get(i) != null && drawnItems.get(i).isItemEqual(selectedStack) && ItemStack.areItemStackTagsEqual(drawnItems.get(i), selectedStack) && ((recipeSelected == -1 && i < regSize)|| (recipeSelected > -1 && i == recipeSelected)))
					{
						x -= 2;
						y -= 2;
						this.drawGradientRect(x, y, x+18, y+18, 0x88ffaaff, 0x88886688);
						this.drawGradientRect(x, y, x+1, y+18, 0xff660066, 0xff330033);
						this.drawGradientRect(x, y+17, x+18, y+18, 0xff330033, 0xff110011);
						this.drawGradientRect(x+17, y, x+18, y+18, 0xff990099, 0xff110011);
						this.drawGradientRect(x, y, x+18, y+1, 0xff660066, 0xff990099);
						y += 1;
						x += 1; 
					}
				}
				
				GuiResearchBook.itemRender.renderItemAndEffectIntoGUI(fontRendererObj,Minecraft.getMinecraft().renderEngine, drawnItems.get(i), x, y);
				int alterSize = drawnItems.get(i).stackSize;
				boolean greaterThan1K = alterSize >= 1000;
				boolean greaterThan1M = alterSize >= 1000000;
				if(alterSize >= 1000000)
					alterSize/=1000000;
				if(alterSize >= 1000)
					alterSize/=1000;
				String s = greaterThan1M ? alterSize+"kk" : greaterThan1K ? alterSize+"k" : alterSize+"";
				if(i >= regSize)
					s = "Craft";
				if(i >= regSize)
				{
					boolean unicode = Minecraft.getMinecraft().gameSettings.forceUnicodeFont || Minecraft.getMinecraft().fontRenderer.getUnicodeFlag();
					if(!unicode)
					{
						GL11.glScaled(0.5F, 0.5F, 1);
						GL11.glTranslated(0, 0, 100);
						GL11.glTranslated(1D, 1D, 0);
						fontRendererObj.drawString(s, x*2, (y+8)*2+6, 0x000000);
						GL11.glTranslated(-1D, -1D, 0);
						fontRendererObj.drawString(s, x*2, (y+8)*2+6, 0xffffff);
						GL11.glTranslated(0, 0, -100);
						GL11.glScaled(2F, 2F, 1);
					}else
					{
						GL11.glTranslated(0, 0, 100);
						GL11.glTranslated(0.5D, 0.5D, 0);
						fontRendererObj.drawString(s, x, y+8, 0x000000);
						GL11.glTranslated(-0.5D, -0.5D, 0);
						fontRendererObj.drawString(s, x, y+8, 0xffffff);
						GL11.glTranslated(0, 0, -100);
					}
				}else
				{
					GL11.glTranslated(0, 0, 100);
					GL11.glTranslated(0.5D, 0.5D, 0);
					fontRendererObj.drawString(s, x, y+8, 0x000000);
					GL11.glTranslated(-0.5D, -0.5D, 0);
					fontRendererObj.drawString(s, x, y+8, 0xffffff);
					GL11.glTranslated(0, 0, -100);
				}
				
				if(mX >= x && mX <= x + 16)
				{
					if(mY >= y && mY <= y + 16)
					{
						this.renderToolTip(drawnItems.get(i), mX, mY);
						if(i >= regSize)
						{
							GL11.glTranslated(0, 0, 200);
							int j2 = mX+12;
							int k2 = mY+drawnItems.get(i).getTooltip(player, false).size()*10-8;
							int i1 = 50;
							int sX = 50;
				            this.drawGradientRect(j2 - 3, k2 - 4, j2 + sX + 3, k2 - 3, 0xff000000, 0xff000000);
				            this.drawGradientRect(j2 - 3, k2 + i1 + 3, j2 + sX + 3, k2 + i1 + 4, 0xff000000, 0xff000000);
				            this.drawGradientRect(j2 - 3, k2 - 3, j2 + sX + 3, k2 + i1 + 3, 0xff223366, 0xff000611);
				            this.drawGradientRect(j2 - 4, k2 - 3, j2 - 3, k2 + i1 + 3, 0xff000000, 0xff000000);
				            this.drawGradientRect(j2 + sX + 3, k2 - 3, j2 + sX + 4, k2 + i1 + 3, 0xff000000, 0xff000000);
				            GL11.glTranslated(0, 0, -200);
				            
				            if(drawnItems.get(i) != null && this.craftsByItemStack.containsKey(drawnItems.get(i)))
				            {
				            	for(int j = 0; j < this.craftsByItemStack.get(drawnItems.get(i)).input.length; ++j)
				            	{
			            			int pX = mX+j/3*17+10 - 10;
			            			int pY = mY+j%3*17+4 - 17;
			            			GL11.glTranslated(0, 0, 300);
			            			this.drawGradientRect(pX+12, pY+16, pX+12+16, pY+16+16, 0x88ffaaff, 0x88886688);
			            			this.drawGradientRect(pX+12, pY+16, pX+12+1, pY+16+16, 0xff660066, 0xff330033);
			            			this.drawGradientRect(pX+12, pY+16+15, pX+12+16, pY+16+16, 0xff330033, 0xff110011);
			            			this.drawGradientRect(pX+12+15, pY+16, pX+12+16, pY+16+16, 0xff990099, 0xff110011);
			            			this.drawGradientRect(pX+12, pY+16, pX+12+16, pY+16+1, 0xff660066, 0xff990099);
			            			
				            		if(this.craftsByItemStack.get(drawnItems.get(i)).input[j] != null)
				            		{
				            			
				            			GL11.glTranslated(0, 0, 300);
				            			GuiResearchBook.itemRender.renderItemAndEffectIntoGUI(fontRendererObj,Minecraft.getMinecraft().renderEngine, this.craftsByItemStack.get(drawnItems.get(i)).input[j], mX+j/3*17+12, mY+j%3*17+3);
				            			GL11.glTranslated(0, 0, -300);
				            			
				            		}
				            		GL11.glTranslated(0, 0, -300);
				            	}
				            }
				            
						}
						if(Mouse.isButtonDown(0) && !isLeftMouseButtonPressed)
						{
							isLeftMouseButtonPressed = true;
							if(selectedStack == null)
							{
								selectedStack = drawnItems.get(i).copy();
								secondPress = true;
								lastPressedTime = 20;
								if(i >= regSize)
									recipeSelected = i;
								else
									recipeSelected = -1;
							}
							else
							{
								if(isShiftKeyDown())
								{
									secondPress = true;
									lastPressedTime = 20;
								}
								
								if(drawnItems.get(i) != null && (!drawnItems.get(i).isItemEqual(selectedStack) || !ItemStack.areItemStackTagsEqual(drawnItems.get(i), selectedStack)))
								{
									selectedStack = drawnItems.get(i).copy();
									if(i >= regSize)
										recipeSelected = i;
									else
										recipeSelected = -1;
									
									if(isShiftKeyDown())
									{
										secondPress = false;
										this.actionPerformed(GuiButton.class.cast(buttonList.get(26)));
									}
									
									setupMaxInt();
								}else
								{
									if(i >= regSize)
									{
										if(i == recipeSelected)
										{
											if(!secondPress)
											{
												secondPress = true;
												lastPressedTime = 20;
											}
											else
											{
												secondPress = false;
												this.actionPerformed(GuiButton.class.cast(buttonList.get(26)));
											}
											setupMaxInt();
										}else
										{
											selectedStack = drawnItems.get(i).copy();
											recipeSelected = i;
										}
									}
									else
									{
										if(i < regSize)
										{
											if(recipeSelected != -1)
											{
												recipeSelected = -1;
												selectedStack = drawnItems.get(i).copy();
											}else
											{
												if(!secondPress)
												{
													secondPress = true;
													lastPressedTime = 20;
												}
												else
												{
													secondPress = false;
													this.actionPerformed(GuiButton.class.cast(buttonList.get(26)));
												}
											}
											setupMaxInt();
										}
										
									}
								}
							}
						}
					}
				}
			}
			
			RenderHelper.enableStandardItemLighting();
			drawnItems.clear();
			drawnItems = null;
			
		}
	}
	
	@SuppressWarnings("rawtypes")
	@Override
    protected void drawHoveringText(List p_146283_1_, int p_146283_2_, int p_146283_3_, FontRenderer font)
    {
        if (!p_146283_1_.isEmpty())
        {
            GL11.glTranslated(0, 0, 100);
            int k = 0;
            Iterator iterator = p_146283_1_.iterator();

            while (iterator.hasNext())
            {
                String s = (String)iterator.next();
                int l = font.getStringWidth(s);

                if (l > k)
                {
                    k = l;
                }
            }

            int j2 = p_146283_2_ + 12;
            int k2 = p_146283_3_ - 12;
            int i1 = 8;

            if (p_146283_1_.size() > 1)
            {
                i1 += 2 + (p_146283_1_.size() - 1) * 10;
            }

            if (j2 + k > this.width)
            {
                j2 -= 28 + k;
            }

            if (k2 + i1 + 6 > this.height)
            {
                k2 = this.height - i1 - 6;
            }

            this.zLevel = 300.0F;
            //itemRender.zLevel = 300.0F;
            this.drawGradientRect(j2 - 3, k2 - 4, j2 + k + 3, k2 - 3, 0xff000000, 0xff000000);
            this.drawGradientRect(j2 - 3, k2 + i1 + 3, j2 + k + 3, k2 + i1 + 4, 0xff000000, 0xff000000);
            this.drawGradientRect(j2 - 3, k2 - 3, j2 + k + 3, k2 + i1 + 3, 0xff223366, 0xff000611);
            this.drawGradientRect(j2 - 4, k2 - 3, j2 - 3, k2 + i1 + 3, 0xff000000, 0xff000000);
            this.drawGradientRect(j2 + k + 3, k2 - 3, j2 + k + 4, k2 + i1 + 3, 0xff000000, 0xff000000);
            
            for (int i2 = 0; i2 < p_146283_1_.size(); ++i2)
            {
                String s1 = (String)p_146283_1_.get(i2);
                GL11.glTranslated(0, 0, 300);
                font.drawString(s1, j2, k2, 0xffffff);
                GL11.glTranslated(0, 0, -300);
				
				
                if (i2 == 0)
                {
                    k2 += 2;
                }

                k2 += 10;
            }

            this.zLevel = 0.0F;
            //itemRender.zLevel = 0.0F;
            GL11.glTranslated(0, 0, -100);
        }
    }
	
	public void setupMaxInt()
	{
		if(selectedStack == null)
		{		
			if(!isCorrectInteger())
				this.stackSize.setText("0");
			
			return;
		}
		if(!isCorrectInteger())
			this.stackSize.setText("1");
		else
		{
			int i = Integer.parseInt(this.stackSize.getText());
			if(i <= 0)
				this.stackSize.setText("1");
			
			if(i > selectedStack.stackSize && recipeSelected == -1)
				this.stackSize.setText(String.valueOf(selectedStack.stackSize));
		}
		
		
	}
	
	public boolean isCorrectInteger()
	{
		try
		{
			int i = Integer.parseInt(this.stackSize.getText());
			return i >= 0;
		}catch(NumberFormatException e)
		{
			return false;
		}
	}
}
