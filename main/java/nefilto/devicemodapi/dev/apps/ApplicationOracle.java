package nefilto.devicemodapi.dev.apps;

import java.awt.Color;
import java.util.Random;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;

import com.mrcrayfish.device.api.app.Application;
import com.mrcrayfish.device.api.app.Component;
import com.mrcrayfish.device.api.app.Layout;
import com.mrcrayfish.device.api.app.Layout.Background;
import com.mrcrayfish.device.api.app.component.Button;
import com.mrcrayfish.device.api.app.component.ItemList;
import com.mrcrayfish.device.api.app.component.Label;
import com.mrcrayfish.device.api.app.listener.ClickListener;

public class ApplicationOracle extends Application{
	
	private boolean choice;
	private boolean needReset = false;
	private Random rand;
	
	//layouts
	Layout layoutMain;
	
	//buttons
	Button btnAsk;

	public ApplicationOracle(String appId, String displayName, ResourceLocation icon, int iconU, int iconV) {
		super(appId, displayName, icon, iconU, iconV);
		// TODO Auto-generated constructor stub
		rand = new Random();
		choice = rand.nextBoolean();
	}
	
	@Override
	public void init() {
		
		
		//layouts
		layoutMain = new Layout(80,80);
		
		//set the backgrounds
		layoutMain.setBackground(new Background() {
			
			@Override
			public void render(Gui gui, Minecraft mc, int x, int y, int width,
					int height) {
				
				if(!needReset){
					gui.drawRect(x, y, x+width, y+height, Color.GRAY.getRGB());
					gui.drawString(mc.fontRendererObj, "ASK", x+30, y+15, Color.WHITE.getRGB());
				}else{
					if(choice){
						gui.drawRect(x, y, x+width, y+height, Color.GREEN.getRGB());
						gui.drawString(mc.fontRendererObj, "YES", x+30, y+15, Color.WHITE.getRGB());
					}else{
						gui.drawRect(x, y, x+width, y+height, Color.RED.getRGB());
						gui.drawString(mc.fontRendererObj, "NO", x+34, y+15, Color.WHITE.getRGB());
					}
				}
			}
		});
		
		//buttons
		btnAsk = new Button("", 0, 40, 80, 40);
		if(needReset){
			btnAsk.setText("Reset");
		}else{
			btnAsk.setText("Shall I?");
		}
		btnAsk.setClickListener(new ClickListener() {
			
			@Override
			public void onClick(Component arg0, int arg1) {
				if(!needReset){
					choice = rand.nextBoolean();
					needReset = true;
					btnAsk.setText("Reset");
				}else{
					needReset = false;
					btnAsk.setText("Shall I?");
				}
				
			}
		});
		
		//add to layout
		layoutMain.addComponent(btnAsk);
		
		setCurrentLayout(layoutMain);
		
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
