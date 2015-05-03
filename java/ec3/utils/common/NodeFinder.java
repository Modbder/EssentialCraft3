package ec3.utils.common;

import org.lwjgl.opengl.GL11;

public class NodeFinder {
	
	public void orientCamera(float flt)
	{
		GL11.glRotatef(180F, 0, 0, 1);
	}

}
