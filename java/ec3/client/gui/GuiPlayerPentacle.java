package ec3.client.gui;

import java.util.ArrayList;

import org.lwjgl.opengl.GL11;

import DummyCore.Utils.MathUtils;
import DummyCore.Utils.MiscUtils;
import ec3.api.ICorruptionEffect;
import ec3.client.render.RenderHandlerEC3;
import ec3.client.render.RenderPlayerPentacle;
import ec3.common.tile.TilePlayerPentacle;
import ec3.utils.common.ECUtils;
import ec3.utils.common.PlayerGenericData;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.ResourceLocation;

public class GuiPlayerPentacle extends GuiScreen{

	public static class EffectButton extends GuiButton
	{
		public int listIndex;
		
		public EffectButton(int id, int x, int y,int sX, int sY, String name) 
		{
			super(id,x,y,sX,sY,name);
		}
		
		public EffectButton(int id, int x, int y,int sX, int sY, String name, int index) 
		{
			super(id,x,y,sX,sY,name);
			listIndex = index;
	    	PlayerGenericData data = ECUtils.getData(Minecraft.getMinecraft().thePlayer);
	    	ArrayList<ICorruptionEffect> effects = (ArrayList<ICorruptionEffect>) data.getEffects();
	    	if(effects.size() <= listIndex)
	    		this.enabled = false;
	    	else
	    	{
	    		if(effects.get(listIndex) == null)
	    			this.enabled = false;
	    	}
		}
		
	    public void drawButton(Minecraft mc, int mX, int mY)
	    {	    	
	    	
	    	if(!this.enabled)
	    		return;
	    	
	    	super.drawButton(mc, mX, mY);
	    	PlayerGenericData data = ECUtils.getData(mc.thePlayer);
	    	ArrayList<ICorruptionEffect> effects = (ArrayList<ICorruptionEffect>) data.getEffects();
	    	if(effects.size() <= listIndex)
	    		this.enabled = false;
	    	else
	    	{
	    		if(effects.get(listIndex) == null)
	    			this.enabled = false;
	    		else
	    		{
	    			
	    			ICorruptionEffect effect = effects.get(listIndex);
    	        	FontRenderer renderer = Minecraft.getMinecraft().fontRenderer;
    	        	GuiPlayerPentacle gui = (GuiPlayerPentacle) Minecraft.getMinecraft().currentScreen;
  
	    			ResourceLocation loc = effects.get(listIndex).getEffectIcon();
	  	        	if(gui.pentacle.tier < effect.getType().ordinal())
	  	        	{
    	        		renderer = Minecraft.getMinecraft().standardGalacticFontRenderer;
    	        		loc = GuiPlayerPentacle.field_147526_d;
	  	        	}
    	        	if(GuiScreen.isShiftKeyDown() && mc.thePlayer.capabilities.isCreativeMode)
    	        	{
    	        		renderer = Minecraft.getMinecraft().fontRenderer;
    	        		loc = effects.get(listIndex).getEffectIcon();
    	        	}
	    			mc.renderEngine.bindTexture(loc);
	    			int p_73729_1_ = this.xPosition+2;
	    			int p_73729_2_ = this.yPosition+2;
	    			int p_73729_5_ = 16;
	    			int p_73729_6_ = 16;
	    			
	    	        Tessellator tessellator = Tessellator.instance;
	    	        tessellator.startDrawingQuads();
	    	        tessellator.addVertexWithUV((double)(p_73729_1_ + 0), (double)(p_73729_2_ + p_73729_6_), (double)this.zLevel, 0, 1);
	    	        tessellator.addVertexWithUV((double)(p_73729_1_ + p_73729_5_), (double)(p_73729_2_ + p_73729_6_), (double)this.zLevel, 1, 1);
	    	        tessellator.addVertexWithUV((double)(p_73729_1_ + p_73729_5_), (double)(p_73729_2_ + 0), (double)this.zLevel, 1, 0);
	    	        tessellator.addVertexWithUV((double)(p_73729_1_ + 0), (double)(p_73729_2_ + 0), (double)this.zLevel, 0, 0);
	    	        tessellator.draw();
	    	        
	    	        if(mX >= this.xPosition && mX <= this.xPosition+20)
	    	        {
		    	        if(mY >= this.yPosition && mY <= this.yPosition+20)
		    	        {
		    	        	GL11.glTranslatef(0, 0, 100);
		    	        	String name = effect.getLocalizedName();
		    	        	String desc = effect.getLocalizedDesc();
		    	        	int length = name.length();
		    	        	if(desc.length()/2 >= name.length())
		    	        		length = desc.length()/2;
		    	        	this.drawGradientRect(mX+5, mY+5, mX+length*6, mY+25, 0x22ffffff, 0x22ffffff);

		    	        	this.drawString(renderer, name, mX+5, mY+5, 0xffffff);
		    	        	boolean enable = effect.getStickiness() <= gui.energy;
		    	        	String additional = "";
		    	        	if(GuiScreen.isShiftKeyDown() && mc.thePlayer.capabilities.isCreativeMode)
		    	        	{
		    	        		enable = true;
		    	        		additional = " [Creative]";
		    	        	}
		    	        	GL11.glScalef(1F/2F, 1F/2F, 1);
		    	        	//GL11.glTranslatef(0, 0, 200);
		    	        	this.drawString(renderer, EnumChatFormatting.ITALIC+desc, (mX+5)*2, (mY+15)*2, 0xffffff);
		    	        	this.drawString(renderer, EnumChatFormatting.ITALIC+""+(enable ? EnumChatFormatting.DARK_GREEN : EnumChatFormatting.RED)+""+effect.getStickiness()+" ESPE"+additional, (mX+5)*2, (mY+20)*2, 0xffffff);
		    	        	//GL11.glTranslatef(0, 0, -200);
		    	        	GL11.glScalef(2, 2, 1);
		    	        	GL11.glTranslatef(0, 0, -100);
		    	        }
	    	        }
	    		}
	    	}
	    }
		
	}
	
	public int energy;
    protected int xSize = 176;
    protected int ySize = 166;
    protected int renderTime;
    protected long timeOpened;
	public TilePlayerPentacle pentacle;
    private static final ResourceLocation field_147529_c = new ResourceLocation("textures/environment/end_sky.png");
    private static final ResourceLocation field_147526_d = new ResourceLocation("textures/entity/end_portal.png");
	
	public GuiPlayerPentacle(TileEntity tile)
	{
		super();
		pentacle = (TilePlayerPentacle) tile;
		timeOpened = tile.getWorldObj().getTotalWorldTime();
	}
	
    public boolean doesGuiPauseGame()
    {
        return false;
    }
	
	public GuiPlayerPentacle()
	{
		super();
	}
	
	protected void actionPerformed(GuiButton p_146284_1_) 
	{
		EffectButton eb = (EffectButton) p_146284_1_;
		if(eb.enabled)
		{
			ArrayList<ICorruptionEffect> effects = (ArrayList<ICorruptionEffect>) ECUtils.getData(Minecraft.getMinecraft().thePlayer).getEffects();
			if(effects.size() > p_146284_1_.id)
			{
				ICorruptionEffect effect = effects.get(eb.listIndex);
				if(effect.getType().ordinal() <= this.pentacle.tier || (GuiScreen.isShiftKeyDown() && Minecraft.getMinecraft().thePlayer.capabilities.isCreativeMode))
				{
					if(GuiScreen.isShiftKeyDown() && Minecraft.getMinecraft().thePlayer.capabilities.isCreativeMode)
						MiscUtils.handleButtonPress(eb.listIndex, getClass(), eb.getClass(), Minecraft.getMinecraft().thePlayer, this.pentacle.xCoord, this.pentacle.yCoord, this.pentacle.zCoord,"||isCreative:true");
					else
						MiscUtils.handleButtonPress(eb.listIndex, getClass(), eb.getClass(), Minecraft.getMinecraft().thePlayer, this.pentacle.xCoord, this.pentacle.yCoord, this.pentacle.zCoord);
				}
			}
		}
		
	}
	
	@SuppressWarnings("unchecked")
	public void initGui() 
	{
        int k = (this.width - this.xSize) / 2;
        int l = (this.height - this.ySize) / 2;
        this.buttonList.add(new EffectButton(0,k + 76,l - 40,20,20,"0",0));
        this.buttonList.add(new EffectButton(1,k - 36 ,l + 40,20,20,"1",1));
        this.buttonList.add(new EffectButton(2,k + 186 ,l + 40,20,20,"2",2));
        this.buttonList.add(new EffectButton(3,k + 76 ,l + 16,20,20,"3",3));
        this.buttonList.add(new EffectButton(4,k + 30 ,l + 10,20,20,"4",4));
        this.buttonList.add(new EffectButton(5,k + 126 ,l + 10,20,20,"5",5));
        this.buttonList.add(new EffectButton(6,k + 20 ,l + 57,20,20,"6",6));
        this.buttonList.add(new EffectButton(7,k + 132 ,l + 57,20,20,"7",7));
        this.buttonList.add(new EffectButton(8,k + 0 ,l + 101,20,20,"8",8));
        this.buttonList.add(new EffectButton(9,k + 150 ,l + 101,20,20,"9",9));
        this.buttonList.add(new EffectButton(10,k + 42 ,l + 126,20,20,"10",10));
        this.buttonList.add(new EffectButton(11,k + 110 ,l + 126,20,20,"11",11));
        this.buttonList.add(new EffectButton(12,k + 76 ,l + 156,20,20,"12",12));
        this.buttonList.add(new EffectButton(13,k + 0 ,l + 174,20,20,"13",13));
        this.buttonList.add(new EffectButton(14,k + 150 ,l + 174,20,20,"14",14));
	}
	
    public void drawScreen(int p_73863_1_, int p_73863_2_, float p_73863_3_)
    {
    	renderTime = (int)((Minecraft.getMinecraft().theWorld.getTotalWorldTime() - this.timeOpened) % 24000L);
        int k = (this.width - this.xSize) / 2;
        int l = (this.height - this.ySize) / 2;
        
        float opacityIndex = (float)renderTime / 100F;
        if(opacityIndex > 1)opacityIndex = 1;
        
        GL11.glPushMatrix();
        GL11.glColor4f(0.0F, 0.2F, 0.05F, opacityIndex);
        
        ScaledResolution res = new ScaledResolution(Minecraft.getMinecraft(),Minecraft.getMinecraft().displayWidth,Minecraft.getMinecraft().displayHeight);
        
        Minecraft.getMinecraft().renderEngine.bindTexture(field_147529_c);
        drawTexturedModalRect(0, 0, p_73863_1_, p_73863_2_, res.getScaledWidth(), res.getScaledHeight());
        
        GL11.glPopMatrix();
        
        GL11.glPushMatrix();
        GL11.glColor4f(0F, 0.7F, 1F, 0.6F*opacityIndex);
        GL11.glScalef(2, 2, 2);
        Minecraft.getMinecraft().renderEngine.bindTexture(field_147526_d);
        drawTexturedModalRect(0, 0, (int)(Minecraft.getMinecraft().thePlayer.ticksExisted%24000L), (int)(Minecraft.getMinecraft().thePlayer.ticksExisted%24000L), res.getScaledWidth(), res.getScaledHeight());
        GL11.glScalef(0.5F, 0.5F, 0.5F);
        GL11.glPopMatrix();
        
        TilePlayerPentacle p = this.pentacle;
    	if(p.tier == -1)
    	{
    		GL11.glColor4f(0.2F, 0.2F, 0.2F,opacityIndex);
    	}
    	if(p.tier == 0)
    	{
    		GL11.glColor4f(0F, 1F, 0F,opacityIndex);
    	}
    	if(p.tier == 1)
    	{
    		GL11.glColor4f(0F, 0F, 1F,opacityIndex);
    	}
    	if(p.tier == 2)
    	{
    		GL11.glColor4f(0.5F, 0F, 0.5F,opacityIndex);
    	}
    	if(p.tier == 3)
    	{
    		GL11.glColor4f(1F, 0F, 0F,opacityIndex);
    	}
        
        int uv = 256;
        int uu = 256;
        
        Minecraft.getMinecraft().renderEngine.bindTexture(RenderPlayerPentacle.rune);
        this.drawTexturedModalRect(k-uv/6, l-uu/6, 0, 0, uv, uu);
        
        if(opacityIndex==1)
        {
        	GuiInventory.func_147046_a((int) (k+uv/2.9F), (int) (l+uu/2.5F), 20, 0, 0, Minecraft.getMinecraft().thePlayer);
        	Minecraft.getMinecraft().renderEngine.bindTexture(RenderHandlerEC3.whitebox);
        	GL11.glColor3f(0.058F, 0.058F, 0.058F);
        	this.drawTexturedModalRect((int) (k+uv/2.9F) - 32, (int) (l+uu/2.5F) + 2, 0, 0, 64, 8);
        	GL11.glColor3f(0.2F, 0.2F, 0.1F);
        	this.drawTexturedModalRect((int) (k+uv/2.9F) - 32, (int) (l+uu/2.5F) + 2, 0, 0, 61, 5);
        	if(ECUtils.getData(Minecraft.getMinecraft().thePlayer) != null)
        	{
        		PlayerGenericData data = ECUtils.getData(Minecraft.getMinecraft().thePlayer);
        		int index = MathUtils.pixelatedTextureSize(data.getOverhaulDamage(), 72000, 61);
        		GL11.glColor3f(0.73F, 0F, 0F);
        		this.drawTexturedModalRect((int) (k+uv/2.9F) - 32, (int) (l+uu/2.5F) + 2, 0, 0, index, 5);
        		this.energy = this.pentacle.getEnderstarEnergy();
        		String displayEnergy = energy+"";
        		while(5-displayEnergy.length() > 0)
        			displayEnergy = " "+displayEnergy;
        		displayEnergy += " ESPE";
        		this.drawString(fontRendererObj, displayEnergy, (int) (k+uv/2.9F) - 32, (int) (l+uu/2.5F) + 22, 0xffffff);
        	}
        }
        if(opacityIndex==1)
        	super.drawScreen(p_73863_1_, p_73863_2_, p_73863_3_);
    }

}
