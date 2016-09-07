package nefilto.devicemodapi.dev.main;

import com.mrcrayfish.device.api.ApplicationManager;

import nefilto.devicemodapi.dev.apps.ApplicationMineBreaker;
import nefilto.devicemodapi.dev.apps.ApplicationOracle;
import nefilto.devicemodapi.dev.apps.ApplicationSlipper;
import nefilto.devicemodapi.dev.apps.ApplicationTest;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;

@Mod(modid = Dmod.MODID, version = Dmod.VERSION, acceptedMinecraftVersions = "[1.8.9,)", dependencies = "required-after:cdm@[1.0,)")
public class Dmod{
    public static final String MODID = "nefapps";
    public static final String VERSION = "1.0";
    public static final ResourceLocation icons = new ResourceLocation(MODID, "textures/gui/icons.png");
    
    @EventHandler
    public void postInit(FMLPostInitializationEvent event){
		
    	ApplicationManager.registerApplication(new ApplicationTest(MODID+"apptest", "App Test"));
    	ApplicationManager.registerApplication(new ApplicationSlipper(MODID+"mineslipper", "Mine Slipper"));
    	ApplicationManager.registerApplication(new ApplicationOracle(MODID+"oracle", "Oracle", icons, 0,0));
    	ApplicationManager.registerApplication(new ApplicationMineBreaker(MODID+"minebraker", "MineBreaker", icons, 14, 0));
    }
}
