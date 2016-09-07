package nefilto.devicemodapi.dev.apps;

import nefilto.devicemodapi.dev.components.MineBreaker;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;

import com.mrcrayfish.device.api.app.Application;
import com.mrcrayfish.device.api.app.Layout;

public class ApplicationMineBreaker extends Application {
	
	Layout layoutMineBreaker;

	public ApplicationMineBreaker(String appId, String displayName, ResourceLocation icon, int iconU, int iconV) {
		super(appId, displayName, icon, iconU, iconV);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public void init() {
		layoutMineBreaker = new Layout(362,165);
		MineBreaker game = new MineBreaker(0,0,362,165);
		
		layoutMineBreaker.addComponent(game);
		
		setCurrentLayout(layoutMineBreaker);
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
