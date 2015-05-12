package ec3.client.gui;

import org.lwjgl.opengl.GL11;

import ec3.common.tile.TileWeaponMaker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;
import DummyCore.Client.GuiCommon;
import DummyCore.Utils.MiscUtils;

public class GuiWeaponBench extends GuiCommon{

	public GuiWeaponBench(Container c, TileEntity tile)
	{
		super(c, tile);
	}
	
    @SuppressWarnings({ "unchecked" })
	public void initGui()
    {
        super.initGui();
	    int k = (this.width - this.xSize) / 2;
	    int l = (this.height - this.ySize) / 2;
	    this.buttonList.add(new GuiButton(0, k+145, l+20, 28,12, "Done"));
	    
	    this.buttonList.add(new GuiButton(1, k+150, l+60, 20,20, ">>>"));
    }
    
    @Override
    protected void actionPerformed(GuiButton par1GuiButton) 
    {
    	if(par1GuiButton.id == 1)
    	{
    		TileWeaponMaker w = TileWeaponMaker.class.cast(this.genericTile);
    		
    		++w.index;
    		if(w.index > 3)
    			w.index = 0;
    		
    		w.getWorldObj().setBlockMetadataWithNotify(w.xCoord, w.yCoord, w.zCoord, w.index, 4);
    		
    		Minecraft.getMinecraft().thePlayer.openContainer = null;
    		Minecraft.getMinecraft().thePlayer.closeScreenNoPacket();
    		Minecraft.getMinecraft().setIngameFocus();
    	}
    	MiscUtils.handleButtonPress(par1GuiButton.id, this.getClass(), GuiButton.class, Minecraft.getMinecraft().thePlayer, this.genericTile.xCoord, this.genericTile.yCoord, this.genericTile.zCoord);
    }

	public ResourceLocation guiGenLocation_0 = new ResourceLocation("essentialcraft","textures/gui/pistol_maker.png");
	public ResourceLocation guiGenLocation_1 = new ResourceLocation("essentialcraft","textures/gui/rifle_maker.png");
	public ResourceLocation guiGenLocation_2 = new ResourceLocation("essentialcraft","textures/gui/sniper_maker.png");
	public ResourceLocation guiGenLocation_3 = new ResourceLocation("essentialcraft","textures/gui/gatling_maker.png");
	
    private void drawItemStack(ItemStack p_146982_1_, int p_146982_2_, int p_146982_3_, String p_146982_4_)
    {
        FontRenderer font = null;
        if (p_146982_1_ != null) font = p_146982_1_.getItem().getFontRenderer(p_146982_1_);
        if (font == null) font = fontRendererObj;
        itemRender.renderWithColor = false;
        itemRender.renderItemAndEffectIntoGUI(font, this.mc.getTextureManager(), p_146982_1_, p_146982_2_, p_146982_3_);
        itemRender.renderItemOverlayIntoGUI(font, this.mc.getTextureManager(), p_146982_1_, p_146982_2_, p_146982_3_ - 0, p_146982_4_);
        itemRender.renderWithColor = true;
    }
	
	@Override
	protected void drawGuiContainerBackgroundLayer(float f1,int i1, int i2) 
	{
		GL11.glColor3f(1, 1, 1);
        int k = (this.width - this.xSize) / 2;
        int l = (this.height - this.ySize) / 2;
        TileWeaponMaker w = TileWeaponMaker.class.cast(this.genericTile);
        String t = "item.ec3.gun.pistol.name";
        if(w.index == 1)
        	t = "item.ec3.gun.rifle.name";
        if(w.index == 2)
        	t = "item.ec3.gun.sniper.name";
        if(w.index == 3)
        	t = "item.ec3.gun.gatling.name";
        
        if(w.index == 0)
        	this.mc.renderEngine.bindTexture(guiGenLocation_0);
        if(w.index == 1)
        	this.mc.renderEngine.bindTexture(guiGenLocation_1);
        if(w.index == 2)
        	this.mc.renderEngine.bindTexture(guiGenLocation_2);
        if(w.index == 3)
        	this.mc.renderEngine.bindTexture(guiGenLocation_3);
        
        this.drawTexturedModalRect(k, l, 0, 0, this.xSize, this.ySize);
        
        this.fontRendererObj.drawString(StatCollector.translateToLocal(t), k+60, l+5, 0x000000);
        
        if(w.previewStack != null)
        {
        	this.drawItemStack(w.previewStack, k+153, l+5, "");
        }
        if(!w.areIngridientsCorrect())
        {
        	GuiButton.class.cast(this.buttonList.get(0)).enabled = false;
        }else
        {
        	GuiButton.class.cast(this.buttonList.get(0)).enabled = true;
        }
	}
}
