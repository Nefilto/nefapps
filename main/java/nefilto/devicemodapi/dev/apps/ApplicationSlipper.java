package nefilto.devicemodapi.dev.apps;

import java.awt.Color;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Random;

import org.lwjgl.opengl.GL11;

import javafx.scene.input.KeyCode;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagString;
import net.minecraft.util.ResourceLocation;

import com.mrcrayfish.device.api.app.Application;
import com.mrcrayfish.device.api.app.Component;
import com.mrcrayfish.device.api.app.Layout;
import com.mrcrayfish.device.api.app.Layout.Background;
import com.mrcrayfish.device.api.app.component.Button;
import com.mrcrayfish.device.api.app.component.Image;
import com.mrcrayfish.device.api.app.component.Text;
import com.mrcrayfish.device.api.app.listener.ClickListener;
import com.mrcrayfish.device.api.utils.RenderUtil;

public class ApplicationSlipper extends Application{
	
	int appWidth = 362;
	int appHeight = 165;
	
	int score = 0;
	int highscore = 0;
	int level = 1;
	int life = 3;
	boolean playing = false;
	int ghettoTimer = 0;

	Random rand = new Random();
	
	Player player;
	ArrayList<Enemy> enemys;
	ArrayList<Lifepack> lifepack;
	
	//Layout strings
	String newgame = "New Game";
	//Layouts
	Layout main;
	Layout gameover;
	Layout start;
	
	//Images
	Image starBg;
	Image gameoverBg;
	
	//Text
	Text gameoverScore;
	Text gameoverhighscore;
	
	//Buttons
	Button btnNewGame;
	Button btnBackToMain;

	public ApplicationSlipper(String appId, String displayName) {
		super(appId, displayName);
		
		enemys = new ArrayList<ApplicationSlipper.Enemy>();
		lifepack = new ArrayList<Lifepack>();
	}
	
	@Override
	public void init() {
		//initialize layouts
		start = new Layout(200,appHeight);
		gameover = new Layout(200,appHeight);
		main = new Layout(appWidth,appHeight);
		
		//initialize Buttons
		btnNewGame = new Button(newgame, 200 / 2 - 30, appHeight / 2 + 40, 60, 20);
		btnBackToMain = new Button("Back", 200 / 2 - 30, appHeight / 2 + 40, 60, 20);
		
		//init images
		starBg = new Image(0, 0, 0, 60, 200, 165, new ResourceLocation("nefapps:textures/gui/start_bg.png"));
		gameoverBg = new Image(0, 0, 25, 50, 200, appHeight, new ResourceLocation("nefapps:textures/gui/gameover_bg.png"));
		
		//Text
		gameoverScore = new Text("Your score is : " +score, 40, 40, 150);
		gameoverScore.setTextColour(new Color(0, 0, 0));
		gameoverScore.setShadow(false);
		
		gameoverhighscore = new Text("The Highscore is : " +highscore, 40, 60, 150);
		gameoverhighscore.setTextColour(new Color(0, 0, 0));
		gameoverhighscore.setShadow(false);
		
		//add to Layouts
		start.addComponent(starBg);
		start.addComponent(btnNewGame);
		
		gameover.addComponent(gameoverBg);
		gameover.addComponent(btnBackToMain);
		gameover.addComponent(gameoverScore);
		gameover.addComponent(gameoverhighscore);
		
		
		//Customize the Layouts
		main.setBackground(new Background() {	
			@Override
			public void render(Gui gui, Minecraft mc, int x, int y, int width,
					int height) {
				
				//header
				//gui.drawRect(x, y, x+width, y+height, Color.GREEN.getRGB());
				mc.renderEngine.bindTexture(new ResourceLocation("nefapps:textures/gui/bg.png"));
				
				gui.drawTexturedModalRect(x, y, 0, 0, appWidth, appHeight);
				gui.drawRect(x, y, x+width, y+20, Color.GRAY.getRGB());
				gui.drawRect(x, y+20, x+width, y+21, Color.DARK_GRAY.getRGB());
				
				gui.drawString(mc.fontRendererObj, "SCORE : "+score, x+15, y+6, Color.WHITE.getRGB());
				gui.drawString(mc.fontRendererObj, "LIFE : "+life, x+160, y+6, Color.WHITE.getRGB());
				gui.drawString(mc.fontRendererObj, "LEVEL : "+level, x+300, y+6, Color.WHITE.getRGB());
				
				//Player
				if(player == null){
					int playerx = x + appWidth / 2 - 5;
					int playery = y + appHeight / 2 - 5;
					player = new Player(playerx, playery, 10, 10, 2, mc.thePlayer.getDisplayNameString());
				}
				
				
				if(enemys.size() <= 0){
					enemys.add(new Enemy(rand.nextInt((x+width - 10 - (x)) + 1) + x, 
							rand.nextInt(((y+height - 10) - (y+21)) + 1) + (y+21), 10, 10,500, 1));
				}
				
				//Enemy
				if(level == 1){
				if(ghettoTimer == 300){
						enemys.add(new Enemy(rand.nextInt((x+width - 10 - (x)) + 1) + x, 
								rand.nextInt(((y+height - 10) - (y+21)) + 1) + (y+21), 10, 10,1500, 1));
						ghettoTimer = 0;
						score  += 5;
					}
				}else if(level == 2){
					if(ghettoTimer == 400){
						enemys.add(new Enemy(rand.nextInt((x+width - 10 - (x)) + 1) + x, 
								rand.nextInt(((y+height - 10) - (y+21)) + 1) + (y+21), 10, 10,1000, 2, "MHF_Creeper"));
						//drop life pack with 50% chance
						if(rand.nextBoolean()){
							lifepack.add(new Lifepack(rand.nextInt((x+width - 10 - (x)) + 1) + x, 
									rand.nextInt(((y+height - 10) - (y+21)) + 1) + (y+21), 5, 5, 500));
						}	
						//life pack bonus at 25
						if(score == 25){
							lifepack.add(new Lifepack(rand.nextInt((x+width - 10 - (x)) + 1) + x, 
									rand.nextInt(((y+height - 10) - (y+21)) + 1) + (y+21), 5, 5, 500));
						}
						ghettoTimer = 0;
						score  += 5;
					}
				}else if(level == 3){
					if(ghettoTimer == 500){
						enemys.add(new Enemy(rand.nextInt((x+width - 10 - (x)) + 1) + x, 
								rand.nextInt(((y+height - 10) - (y+21)) + 1) + (y+21), 10, 10,1000, 3, "MHF_Enderman"));
						enemys.add(new Enemy(rand.nextInt((x+width - 10 - (x)) + 1) + x, 
								rand.nextInt(((y+height - 10) - (y+21)) + 1) + (y+21), 10, 10,1000, 2, "MHF_Creeper"));
						ghettoTimer = 0;
						player.speed = 3;
						score  += 5;
					}
				}
				else if(level == 4){
					if(ghettoTimer == 600){
						enemys.add(new Enemy(rand.nextInt((x+width - 10 - (x)) + 1) + x, 
								rand.nextInt(((y+height - 10) - (y+21)) + 1) + (y+21), 10, 10,500, 3, "MHF_Enderman"));
						enemys.add(new Enemy(rand.nextInt((x+width - 10 - (x)) + 1) + x, 
								rand.nextInt(((y+height - 10) - (y+21)) + 1) + (y+21), 10, 10,500, 3, "MHF_Enderman"));
						enemys.add(new Enemy(rand.nextInt((x+width - 10 - (x)) + 1) + x, 
								rand.nextInt(((y+height - 10) - (y+21)) + 1) + (y+21), 10, 10,1000, 2, "MHF_Creeper"));
						//drop life pack with 50% chance
						if(rand.nextBoolean()){
							lifepack.add(new Lifepack(rand.nextInt((x+width - 10 - (x)) + 1) + x, 
									rand.nextInt(((y+height - 10) - (y+21)) + 1) + (y+21), 5, 5, 500));
						}
						ghettoTimer = 0;
						score  += 5;
					}
				}
				
				//GlStateManager.disableDepth();
				//GlStateManager.disableLighting();
				GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
				//draw player
				//gui.drawRect(player.x, player.y, player.x + player.width, player.y+player.height, Color.BLUE.getRGB());
				RenderUtil.renderItem(player.x-3, player.y-3, player.sprite, false);
				//mc.getRenderItem().renderItemIntoGUI(player.sprite,player.x-3, player.y-3);
				player.update(x, y, width, height);
				//draw enemys
				for(int i = 0; i < enemys.size(); i++){
					Enemy current = enemys.get(i);
					//gui.drawRect(current.x, current.y, current.x + current.width, current.y+current.height, Color.RED.getRGB());
					RenderUtil.renderItem(current.x-3, current.y-3, current.sprite, false);
					//mc.getRenderItem().renderItemIntoGUI(current.sprite, current.x-3, current.y-3);
					current.update(x, y, width, height);
					if(current.life <= 0){
						enemys.remove(i);
					}
			    }
				//draw lifepacks
				for(int i = 0; i < lifepack.size(); i++){
					Lifepack current = lifepack.get(i);
					gui.drawRect(current.x, current.y, current.x + current.width, current.y+current.height, Color.WHITE.getRGB());
					current.update(x, y, width, height);
					if(current.life <= 0){
						lifepack.remove(i);
					}
			    }
				//GlStateManager.enableLighting();
				//GlStateManager.enableDepth();
				
				checkCollisions();
				//Logic
				if(life <= 0){
					
					if(score > highscore) highscore = score;
					//switch to gameover layout
					gameoverScore.setText("Your Score is : "+ score);
					gameoverhighscore.setText("The HighScore is : "+ highscore);
					setCurrentLayout(gameover);
					
				}
				
				if(score == 20) level = 2;
				else if (score == 50) level = 3;
				else if (score == 80) level = 4;
				
				ghettoTimer++;
			}
		});
		
		//handle clicking
		btnNewGame.setClickListener(new ClickListener() {
			@Override
			public void onClick(Component arg0, int arg1) {
				newgame = "Resume";
				btnNewGame.setText("Resume");
				playing = true;
				setCurrentLayout(main);
			}
		});
		btnBackToMain.setClickListener(new ClickListener() {
			@Override
			public void onClick(Component arg0, int arg1) {
				score = 0;
				level = 1;
				life = 3;
				btnNewGame.setText("New Game");
				enemys.clear();
				player = null;
				playing = false;
				
				//TODO save the highscore
				
				setCurrentLayout(start);
			}
		});
		
		
		// set the layout to start
		if(getCurrentLayout() == null && !playing){
			setCurrentLayout(start);
		}else{
			setCurrentLayout(main);
		}
			
		
		
	}
	
	@Override
	public void handleKeyTyped(char character, int code) {
		super.handleKeyTyped(character, code);
		
		if(character == 'd'){
			player.velY = 0;
			player.velX = player.speed;
		}else if(character == 'q'){
			player.velY = 0;
			player.velX = -player.speed;
		}else if(character == 'z'){
			player.velX = 0;
			player.velY = -player.speed;
		}else if(character == 's'){
			player.velX = 0;
			player.velY = player.speed;
		}
		
	}

	@Override
	public void load(NBTTagCompound arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void save(NBTTagCompound arg0) {
		// TODO Auto-generated method stub
		
	}
	
	public void checkCollisions() {

        Rectangle r3 = player.getBounds();

        for(int i = 0; i < enemys.size(); i++){
			Enemy current = enemys.get(i);
			Rectangle r2 = current.getBounds();
			if (r3.intersects(r2)) {
                enemys.remove(i);
                life--;
            }
	    }
        for(int i = 0; i < lifepack.size(); i++){
			Lifepack current = lifepack.get(i);
			Rectangle r2 = current.getBounds();
			if (r3.intersects(r2)) {
				lifepack.remove(i);
				score+=5;
                life++;
            }
	    }
	}
	
	class Player{
		public int x;
		public int y;
		public int width;
		public int height;
		public int velX;
		public int velY;
		public int speed;
		public ItemStack sprite;
		
		public Player(int x, int y, int width, int height, int speed){
			this.x = x;
			this.y = y;
			this.width = width;
			this.height = height;
			this.speed = speed;
			//----------------
			String playerName = "Nefilto";
			sprite = new ItemStack(Items.skull, 1, 3);
			sprite.setTagCompound(new NBTTagCompound());
			sprite.getTagCompound().setTag("SkullOwner", new NBTTagString(playerName));

		}
		public Player(int x, int y, int width, int height, int speed, String playerName){
			this(x,y,width,height,speed);
			//----------------
			sprite = new ItemStack(Items.skull, 1, 3);
			sprite.setTagCompound(new NBTTagCompound());
			sprite.getTagCompound().setTag("SkullOwner", new NBTTagString(playerName));

		}
		public void update(int x, int y, int width, int height){
			this.x += velX;
			this.y += velY;
			//check out of bound
			if(this.x <= x){
				velX = 0;
				this.x = x;
			}
			if(this.x >= x+width - this.width){
				velX = 0;
				this.x = x+width - this.width;
			}
			if(this.y <= y + 21){
				velY = 0;
				player.y = y + 21;
			}
			if(this.y >= y + height - this.height){
				velY = 0;
				this.y = y + height - this.height;
			}
		}
		
		public Rectangle getBounds() {
	        return new Rectangle(x, y, width, height);
	    }
		
	}
	class Enemy{
		public int x;
		public int y;
		public int width;
		public int height;
		public int speed;
		public int velX = 0;
		public int velY = 0;
		Random rand = new Random();
		int life;
		public ItemStack sprite;
		
		public Enemy(int x, int y, int width, int height, int life, int speed){
			this.x = x;
			this.y = y;
			this.width = width;
			this.height = height;
			this.speed = speed;
			if(rand.nextBoolean()){
				velX = speed;
				velY = speed;
			}else{
				velX = -speed;
				velY = -speed;
			}
			this.life = life;
			
			//--------------
			String playerName = "MHF_Zombie";
			sprite = new ItemStack(Items.skull, 1, 3);
			sprite.setTagCompound(new NBTTagCompound());
			sprite.getTagCompound().setTag("SkullOwner", new NBTTagString(playerName));
		}
		public Enemy(int x, int y, int width, int height, int life, int speed, String mobName){
			this(x,y,width,height,life,speed);
			
			//--------------
			sprite = new ItemStack(Items.skull, 1, 3);
			sprite.setTagCompound(new NBTTagCompound());
			sprite.getTagCompound().setTag("SkullOwner", new NBTTagString(mobName));
		}
		public void update(int x, int y, int width, int height){
			this.x += velX;
			this.y += velY;
			//check out of bound
			if(this.x <= x){
				velX*=-1;
			}
			if(this.x >= x+width - this.width){
				velX*=-1;
			}
			if(this.y <= y + 21){
				velY*=-1;
			}
			if(this.y >= y + height - this.height){
				velY*=-1;
			}
			life--;
		}
		public Rectangle getBounds() {
	        return new Rectangle(x, y, width, height);
	    }
		
	}
	
	class Lifepack{
		public int x;
		public int y;
		public int width;
		public int height;
		int life;
		public Lifepack(int x, int y, int width, int height, int life){
			this.x = x;
			this.y = y;
			this.width = width;
			this.height = height;
			this.life = life;
		}
		public void update(int x, int y, int width, int height){
			life--;
		}
		public Rectangle getBounds() {
	        return new Rectangle(x, y, width, height);
	    }
	}

}
