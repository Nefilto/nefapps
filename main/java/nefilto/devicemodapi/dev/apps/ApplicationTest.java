package nefilto.devicemodapi.dev.apps;

import nefilto.devicemodapi.dev.components.MineBreaker;
import net.minecraft.nbt.NBTTagCompound;

import com.mrcrayfish.device.api.app.Application;
import com.mrcrayfish.device.api.app.Layout;

public class ApplicationTest extends Application{
	
	Layout layoutTest;

	public ApplicationTest(String appId, String displayName) {
		super(appId, displayName);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public void init() {
		super.init();
		layoutTest = new Layout();
		
		setCurrentLayout(layoutTest);
	}

	@Override
	public void load(NBTTagCompound arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void save(NBTTagCompound arg0) {
		// TODO Auto-generated method stub
		
	}

}
