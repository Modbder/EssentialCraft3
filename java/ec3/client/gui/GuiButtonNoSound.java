package ec3.client.gui;

import DummyCore.Utils.MathUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.SoundHandler;
import net.minecraft.client.gui.GuiButton;

public class GuiButtonNoSound extends GuiButton{

	public GuiButtonNoSound(int p_i1021_1_, int p_i1021_2_, int p_i1021_3_,
			int p_i1021_4_, int p_i1021_5_, String p_i1021_6_) {
		super(p_i1021_1_, p_i1021_2_, p_i1021_3_, p_i1021_4_, p_i1021_5_, p_i1021_6_);
		// TODO Auto-generated constructor stub
	}

	@Override
    public void func_146113_a(SoundHandler p_146113_1_)
    {
		Minecraft mc = Minecraft.getMinecraft();
		 mc.theWorld.playSound(mc.thePlayer.posX,mc.thePlayer.posY,mc.thePlayer.posZ, "essentialcraft:sound.pageturn", 1, 1+MathUtils.randomFloat(mc.theWorld.rand)/4, false);
    }
}
