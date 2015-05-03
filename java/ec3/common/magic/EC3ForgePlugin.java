package ec3.common.magic;

import java.util.Map;

import cpw.mods.fml.relauncher.IFMLLoadingPlugin;
import ec3.common.mod.EssentialCraftCore;

/**
 * 
 * @author Modbder
 * @Description ASM features with TConstruct.
 */
public class EC3ForgePlugin implements IFMLLoadingPlugin{

	@Override
	public String[] getASMTransformerClass() {
		// TODO Auto-generated method stub
		return new String[]{ASMHandler.class.getName()};
	}

	@Override
	public String getModContainerClass() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getSetupClass() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void injectData(Map<String, Object> data) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getAccessTransformerClass() {
		// TODO Auto-generated method stub
		return null;
	}

}
