package nefilto.devicemodapi.dev.components;

import ibxm.Player;

import java.awt.Color;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Random;

import nefilto.devicemodapi.dev.gameobjects.minebreaker.Ball;
import nefilto.devicemodapi.dev.gameobjects.minebreaker.Bar;
import nefilto.devicemodapi.dev.gameobjects.minebreaker.Brick;
import nefilto.devicemodapi.dev.gameobjects.minebreaker.Drop;
import nefilto.devicemodapi.dev.gameobjects.minebreaker.PowerUp;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;

import com.mrcrayfish.device.api.app.Component;
import com.mrcrayfish.device.api.utils.RenderUtil;
import com.mrcrayfish.device.core.Laptop;
public class MineBreaker extends Component{
	
	boolean hasJustStarted;
	
	ResourceLocation mainSp;
	
	int width;
	int height;
	
	//Game Stats
	int score;
	int highscore;
	int time;
	int level;
	int life;
	int powerTimer;
	Random rand;
	
	boolean goNextLevel;
	ArrayList<String[][]> levels;
	
	Bar bar;
	
	String[][] level1 = {
			{"1","1","X","X","X","X","X","X","X","X","X","X","X","X","X","X","X","X"},
			{"X","X","X","X","X","X","X","X","X","X","X","X","X","X","X","X","X","X"},
			{"X","X","X","X","X","X","X","X","X","X","X","X","X","X","X","X","X","X"},
			{"X","X","X","X","X","X","X","X","X","X","X","X","X","X","X","X","X","X"},
			{"1","1","W","W","W","W","1","1","1","1","W","W","W","W","1","1","1","X"},
			{"2","2","2","2","W","2","2","2","2","2","2","W","W","2","2","2","2","X"},
			{"3","L","L","L","3","2","2","2","2","2","2","2","2","L","L","L","3","X"},
			{"3","3","3","L","3","2","2","2","2","2","2","2","2","2","3","L","2","X"},
			{"2","2","2","3","2","X","X","5","6","5","X","X","2","4","3","4","2","X"},
			{"2","2","2","2","2","X","X","G","D","R","X","X","2","2","2","2","2","X"},
			{"X","X","X","X","X","X","X","X","B","X","X","X","X","X","X","X","X","X"},
			{"X","X","X","X","X","X","X","X","X","X","X","X","X","X","X","X","X","X"},
			{"X","X","X","X","X","X","X","X","X","X","X","X","X","X","X","X","X","X"},
			{"X","X","X","X","X","X","X","X","X","X","X","X","X","X","X","X","X","X"},
			{"X","X","X","X","X","X","X","X","X","X","X","X","X","X","X","X","X","X"}
			};
	
	String[][] level2 = {
			{"X","X","X","X","X","X","X","X","X","X","X","X","X","X","X","X","X","X"},
			{"X","X","X","X","X","X","X","X","X","X","X","X","X","X","X","X","X","X"},
			{"1","1","1","1","1","1","1","2","6","2","1","1","1","1","1","1","1","X"},
			{"2","2","2","2","2","2","2","2","2","2","2","2","2","2","2","2","2","X"},
			{"X","X","X","X","X","X","X","2","2","2","X","X","X","X","X","X","X","X"},
			{"X","X","X","X","X","X","X","2","2","2","X","X","X","X","X","X","X","X"},
			{"X","1","4","6","4","1","X","2","2","2","X","1","4","6","4","1","X","X"},
			{"X","X","X","X","X","X","X","2","2","2","X","X","X","X","X","X","X","X"},
			{"X","X","X","X","X","X","X","2","2","2","X","X","X","X","X","X","X","X"},
			{"1","1","1","1","1","1","1","2","2","2","1","1","1","1","1","1","1","X"},
			{"2","2","2","2","2","2","2","2","2","2","2","2","2","2","2","2","2","X"},
			{"3","3","3","3","3","3","3","3","3","3","3","3","3","3","3","3","3","X"},
			{"X","X","X","X","X","X","X","X","X","X","X","X","X","X","X","X","X","X"},
			{"X","X","X","X","X","X","X","X","X","X","X","X","X","X","X","X","X","X"},
			{"X","X","X","X","X","X","X","X","X","X","X","X","X","X","X","X","X","X"}
			};
	
	ArrayList<Ball> balls;
	ArrayList<Brick> bricks;
	ArrayList<PowerUp> powerUps;
	ArrayList<Drop> drops;

	public MineBreaker(int left, int top, int width, int height) {
		super(left, top);
		
		hasJustStarted = true;
		
		this.width = width;
		this.height = height;
		
		mainSp = new ResourceLocation("nefapps:textures/gui/minebraker.png");
		
		score = 0;
		highscore = 0;
		time = 0;
		level = 1;
		life = 3;
		
		rand = new Random();
		
		goNextLevel = false;
		
		powerUps = new ArrayList<PowerUp>();
		drops = new ArrayList<Drop>();
		
		levels = new ArrayList<String[][]>();
		levels.add(level1);
		levels.add(level2);
	}

	@Override
	public void render(Laptop laptop, Minecraft mc, int mouseX, int mouseY,
			boolean windowActive, float partialTicks) {
		
		drawTexture(xPosition, yPosition, 0.0f, 146.0f, 362, 165, 512.0f, 512.0f, mainSp, mc);
		//-----------------
		//Draw Left Side
		//-----------------
		drawRect(xPosition+9, yPosition+9, xPosition+100-9, yPosition + height -9, Color.BLACK.getRGB());
		drawRect(xPosition+10, yPosition+10, xPosition+100-10, yPosition + height -10, Color.GRAY.getRGB());
		
		//draw Stats
		drawString(mc.fontRendererObj, "HIGHSCORE", xPosition+28, yPosition+20, Color.ORANGE.getRGB());
		drawString(mc.fontRendererObj, scoreToString(highscore), xPosition+20, yPosition+30, Color.WHITE.getRGB());
		
		drawString(mc.fontRendererObj, "SCORE", xPosition+50, yPosition+45, Color.ORANGE.getRGB());
		drawString(mc.fontRendererObj, scoreToString(score), xPosition+20, yPosition+55, Color.WHITE.getRGB());
		
		drawString(mc.fontRendererObj, "LEVEL", xPosition+20, yPosition+75, Color.ORANGE.getRGB());
		drawString(mc.fontRendererObj, ""+scoreToString(level,3), xPosition+62, yPosition+75, Color.WHITE.getRGB());
		
		drawString(mc.fontRendererObj, "TIME", xPosition+20, yPosition+90, Color.ORANGE.getRGB());
		drawString(mc.fontRendererObj, timeToString(time), xPosition+60, yPosition+90, Color.WHITE.getRGB());
		
		drawRect(xPosition+19, yPosition+90+19, xPosition+100-19, yPosition+155-9, Color.BLACK.getRGB());
		drawRect(xPosition+20, yPosition+90+20, xPosition+100-20, yPosition+155-10, Color.DARK_GRAY.getRGB());
		
		//----------
		drawTexture(xPosition+35, yPosition+95+20, 63.0f, 0.0f, 9, 9, 512.0f, 512.0f, mainSp, mc);
		drawString(mc.fontRendererObj, "x "+life, xPosition+48, yPosition+95+20, Color.WHITE.getRGB());
		//----------
		//-----------------
		
		//-----------------
		//Draw Game Objects
		//-----------------
		
		//draw Bar
		if(bar != null)
			drawTexture(bar.x, bar.y, 14.0f, 0.0f, 39, 6, 512.0f, 512.0f, mainSp, mc);
		
		//draw Bricks
		if(bricks != null && bricks.size() > 0){
			for(int i = 0; i < bricks.size(); i++){
				Brick current = bricks.get(i);
				
				//Temp
				if(current.hasPowerUp)
					drawRect(current.x, current.y, current.x + current.width, current.y+current.height, Color.RED.getRGB());
				
				if(current.type == 1){

					drawTexture(current.x, current.y, 0.0f, 0.0f, 14, 7, 512.0f, 512.0f, mainSp, mc);

				}else if(current.type == 2){

					drawTexture(current.x, current.y, 0.0f, 7.0f, 14, 7, 512.0f, 512.0f, mainSp, mc);
					
				}else if(current.type == 3){
					
					if(current.life == 4){
						
						drawTexture(current.x, current.y, 0.0f, 7.0f * 2, 14, 7, 512.0f, 512.0f, mainSp, mc);

						
					}else if(current.life == 3){
						
						drawTexture(current.x, current.y, 0.0f, 7.0f * 3, 14, 7, 512.0f, 512.0f, mainSp, mc);
						
					}else if(current.life == 2){

						drawTexture(current.x, current.y, 0.0f, 7.0f * 4, 14, 7, 512.0f, 512.0f, mainSp, mc);
						
					}else if(current.life == 1){
						
						drawTexture(current.x, current.y, 0.0f, 7.0f * 5, 14, 7, 512.0f, 512.0f, mainSp, mc);

					}
					
				}else if(current.type == 4){

					drawTexture(current.x, current.y, 0.0f, 7.0f * 6, 14, 7, 512.0f, 512.0f, mainSp, mc);
					
				}else if(current.type == 5){
					
					drawTexture(current.x, current.y, 0.0f, 7.0f * 7, 14, 7, 512.0f, 512.0f, mainSp, mc);
					
				}else if(current.type == 6){
					
					drawTexture(current.x, current.y, 0.0f, 7.0f * 8, 14, 7, 512.0f, 512.0f, mainSp, mc);
					
				}else if(current.type == 7){
					
					drawTexture(current.x, current.y, 0.0f, 7.0f * 9, 14, 7, 512.0f, 512.0f, mainSp, mc);
					
				}else if(current.type == 8){
					
					drawTexture(current.x, current.y, 0.0f, 7.0f * 10, 14, 7, 512.0f, 512.0f, mainSp, mc);
					
				}else if(current.type == 9){
					
					drawTexture(current.x, current.y, 0.0f, 7.0f * 11, 14, 7, 512.0f, 512.0f, mainSp, mc);
					
				}
		    }
		}
		
		
		//draw balls
		if(balls != null && balls.size() > 0){
			for(int i = 0; i < balls.size(); i++){
				Ball current = balls.get(i);
				
				if(current.isSticky){
					drawTexture(current.x, current.y, 58.0f, 0.0f, 5, 5, 512.0f, 512.0f, mainSp, mc);
				}else{
					drawTexture(current.x, current.y, 53.0f, 0.0f, 5, 5, 512.0f, 512.0f, mainSp, mc);
				}
		    }
		}
		
		//draw powerups
		if(powerUps != null && powerUps.size() > 0){
			for(int i = 0; i < powerUps.size(); i++){
				PowerUp current = powerUps.get(i);

				switch(current.type){
					case 1:
						drawTexture(current.x, current.y, 0.0f, 312.0f, 7, 7, 512.0f, 512.0f, mainSp, mc);
						break;
					case 2:
						drawTexture(current.x, current.y, 0.0f, 312.0f + current.height, 7, 7, 512.0f, 512.0f, mainSp, mc);
						break;
					case 3:
						drawTexture(current.x, current.y, 0.0f, 312.0f + current.height * 2, 7, 7, 512.0f, 512.0f, mainSp, mc);
						break;
					case 4:
						drawTexture(current.x, current.y, 0.0f, 312.0f + current.height * 3, 7, 7, 512.0f, 512.0f, mainSp, mc);
						break;
						
					default:
						drawRect(current.x, current.y, current.x + current.width, current.y+current.height, Color.BLACK.getRGB());
				}
			
		    }
		}

		//draw drops
		if(drops != null && drops.size() > 0){
			for(int i = 0; i < drops.size(); i++){
				Drop current = drops.get(i);
				
				switch(current.type){
					case 1:
						drawTexture(current.x, current.y, 7.0f, 312.0f, 7, 7, 512.0f, 512.0f, mainSp, mc);
						break;
					case 2:
						drawTexture(current.x, current.y, 7.0f, 312.0f + current.height, 7, 7, 512.0f, 512.0f, mainSp, mc);
						break;
					case 3:
						drawTexture(current.x, current.y, 7.0f, 312.0f + current.height * 2, 7, 7, 512.0f, 512.0f, mainSp, mc);
						break;
					case 4:
						drawTexture(current.x, current.y, 7.0f, 312.0f + current.height * 3, 7, 7, 512.0f, 512.0f, mainSp, mc);
						break;
						
					default:
						drawRect(current.x, current.y, current.x + current.width, current.y+current.height, Color.BLACK.getRGB());
				}
				
		    }
		}
		
		//-----------------

		
	}
	
	@Override
	public void handleTick() {
		
		//Initialize variables if null
		//Bar
		if(bar == null){
			bar = new Bar(xPosition +40 + width / 2 - 14, yPosition + height - 10, 39, 6);
		}
		bar.update(xPosition, yPosition, width, height);
		
		//Bricks
		if(bricks == null){
			bricks = new ArrayList<Brick>();
			loadLevel(xPosition, yPosition , width, height);
		}
		for (int i = 0; i < bricks.size(); i++) {
			Brick current = bricks.get(i);
			current.update(xPosition, yPosition, width, height);
			
			if(current.life <= 0){
				bricks.remove(i);
			}
		}
		
		//Balls
		if(balls == null){
			balls = new ArrayList<Ball>();
			balls.add(new Ball(bar.x + bar.width / 2 + 2, bar.y - 4, 5, 5));
		}
		for (int i = 0; i < balls.size(); i++) {
			Ball current = balls.get(i);
			current.update(xPosition, yPosition, width, height);
			
			if(current.life <= 0){
				balls.remove(i);
				
				if(balls.size() <= 0) life--;
				if(life >= 0 && balls.size() == 0) balls.add(new Ball(bar.x + bar.width / 2 + 2, bar.y - 4, 5, 5));
			}
		}
		
		//powerUps
		for (int i = 0; i < powerUps.size(); i++) {
			PowerUp current = powerUps.get(i);
			current.update(xPosition, yPosition, width, height);
			
			if(current.life <= 0){
				powerUps.remove(i);
			}
		}
		
		//drops
		for (int i = 0; i < drops.size(); i++) {
			Drop current = drops.get(i);
			current.update(xPosition, yPosition, width, height);
			
			if(current.life <= 0){
				drops.remove(i);
			}
		}
		
		checkCollisions();
		
		//redraw the level
		if(goNextLevel){
			goNextLevel = false;
			loadLevel(xPosition, yPosition , width, height);
		}
		
		//check for end of level
		if(life < 0){
			if(score > highscore){
				highscore = score;
				// TODO save high score
			}
			resetStats();
		}else if(bricks.size() == 0){
			level++;
			if(levels.size() > level-1){
				goNextLevel =  true;
				balls = null;
				bar = null;
				bricks.clear();
			}else{
				goNextLevel = false;
				bricks = null;
				balls = null;
				bar = null;
				level = 1;
			}
			
		}
		
		//Rely don't know about this it fix a weird bug where the xPosition and yPosition pointed outside the laptop texture like in absolute top left
		if(hasJustStarted){
			goNextLevel = false;
			bricks = null;
			balls = null;
			bar = null;
			level = 1;
			time = 0;
			hasJustStarted = false;
		}
		
		//increment the time
		time++;
		
		super.handleTick();
	}
	
	@Override
	public void handleKeyTyped(char character, int code) {
		if(character == 'd'){
			bar.velX = bar.speed;
		}else if(character == 'q'){
			bar.velX = -bar.speed;
		}else if(character == 's'){
			bar.velX = 0;
		}else if(character == 'n'){
			bricks.clear();
		}else if(character == 'f'){
			for(int i = 0; i < balls.size(); i++){
				Ball current = balls.get(i);
				if(current.canLaunch){
					current.canLaunch = false;
					current.y -= 1;
					if(rand.nextBoolean()){
						current.velX = current.speed;
						current.velY = -current.speed;
					}else{
						current.velX = -current.speed;
						current.velY = -current.speed;
					}
					if(current.isNewBall){
						current.isNewBall = false;
						current.isSticky = false;
					}
				}
		    }
		}
		super.handleKeyTyped(character, code);
	}
	
	@Override
	public void handleKeyReleased(char character, int code) {
		if(character == 'd'){
			bar.velX = 0;
		}else if(character == 'q'){
			bar.velX = 0;
		}
		super.handleKeyReleased(character, code);
	}
	
	private void drawTexture(int x, int y, float u, float v, int width, int height, float textureWidth, float textureHeight, ResourceLocation texture, Minecraft mc){
		GlStateManager.disableDepth();
		GlStateManager.disableLighting();
		
		GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
		mc.renderEngine.bindTexture(texture);
		drawModalRectWithCustomSizedTexture(x, y, u, v, width, height, textureWidth, textureHeight);
		
		GlStateManager.enableDepth();
		GlStateManager.enableDepth();
	}
	
	private void loadLevel(int x, int y, int width, int height){
		int xpos = x + 110;
		int ypos = y;

		for(int j = 0; j < 15; j++){
			for(int k = 0; k < 18; k++){
				
				//chance of powerup in brick
				int num = 5;
				boolean haspowerup = (rand.nextInt(num - 1) + 1 == 1);
				
				if(levels.get(level-1)[j][k] == "1"){
					bricks.add(new Brick(xpos+k*14, ypos+j*7, 14, 7, 1, 1));
				}else if(levels.get(level-1)[j][k] == "2"){
					bricks.add(new Brick(xpos+k*14, ypos+j*7, 14, 7, 1, 2));
				}else if(levels.get(level-1)[j][k] == "3"){
					bricks.add(new Brick(xpos+k*14, ypos+j*7, 14, 7, 1, 3, 10, haspowerup));
				}else if(levels.get(level-1)[j][k] == "4"){
					bricks.add(new Brick(xpos+k*14, ypos+j*7, 14, 7, 2, 3, 15, haspowerup));
				}else if(levels.get(level-1)[j][k] == "5"){
					bricks.add(new Brick(xpos+k*14, ypos+j*7, 14, 7, 3, 3, 20, haspowerup));
				}else if(levels.get(level-1)[j][k] == "6"){
					bricks.add(new Brick(xpos+k*14, ypos+j*7, 14, 7, 4, 3, 25, haspowerup));
				}else if(levels.get(level-1)[j][k] == "D"){
					bricks.add(new Brick(xpos+k*14, ypos+j*7, 14, 7, 6, 4, 10));
				}else if(levels.get(level-1)[j][k] == "G"){
					bricks.add(new Brick(xpos+k*14, ypos+j*7, 14, 7, 3, 5, 10));
				}else if(levels.get(level-1)[j][k] == "R"){
					bricks.add(new Brick(xpos+k*14, ypos+j*7, 14, 7, 1, 6, 10));
				}else if(levels.get(level-1)[j][k] == "B"){
					bricks.add(new Brick(xpos+k*14, ypos+j*7, 14, 7, 2, 7, 10));
				}else if(levels.get(level-1)[j][k] == "W"){
					bricks.add(new Brick(xpos+k*14, ypos+j*7, 14, 7, 1, 8, 5));
				}else if(levels.get(level-1)[j][k] == "L"){
					bricks.add(new Brick(xpos+k*14, ypos+j*7, 14, 7, 1, 9, 5));
				}

			}
		}
	}
	
	private void checkCollisions() {

        Rectangle r3 = bar.getBounds();

        for(int i = 0; i < balls.size(); i++){
			Ball current = balls.get(i);
			Rectangle r2 = current.getBounds();
			//ball collide with bar
			if (r3.intersects(r2)) {
				if(current.isSticky){
					current.canLaunch = true;
					current.velY = 0;
					current.velX = bar.velX;
					current.x = bar.x + (current.x - bar.x);
					current.y = bar.y - current.height + 2;
				}else{
					if(bar.velX < 0){
						current.velX = -current.speed;
					}else if(bar.velX > 0){
						current.velX = current.speed;
					}
					current.velY*=-1;
				}
            }
			for(int j = 0; j < bricks.size(); j++){
				Brick b = bricks.get(j);
				Rectangle r1 = b.getBounds();
				//ball collide with brick
				if (r2.intersects(r1)) {
					current.velY*=-1;
					b.life--;
					score+=b.point;
					//drop bonus if brick can
					if(b.life == 0){
						switch(b.type){
						
						case 4:
							//drop diamond(1)
							drops.add(new Drop(b.x + 2, b.y, 7, 7, 1));
							break;
						case 5:
							//drop gold(2)
							drops.add(new Drop(b.x + 2, b.y, 7, 7, 2));
							break;
						case 6:
							//drop redstone(3)
							drops.add(new Drop(b.x + 2, b.y, 7, 7, 3));
							break;
						case 7:
							//drop lapis(4)
							drops.add(new Drop(b.x + 2, b.y, 7, 7, 4));
							break;
							
						default:
							break;
						}
						if(b.hasPowerUp){
							powerUps.add(b.dropPowerUp(rand));
						}
					}
					
	            }
			}
	    }
        for(int i = 0; i < powerUps.size(); i++){
        	PowerUp current = powerUps.get(i);
			Rectangle r4 = current.getBounds();
			//powerup collide with bar
			if (r3.intersects(r4)) {
				//apply the power up to bar or balls
				current.life--;
				//TODO apply powerups
				switch(current.type){
				case 1:
					//add life
					life++;
					break;
				case 2:
					//TODO
					//double the size of the bar maybe can quadruple it too who knows 
					//Accommodate in the render method for that
					//Make Sprite for a larger Bar
					break;
				case 3:
					//add a ball
					balls.add(new Ball(bar.x + bar.width / 2 + 2, bar.y - 4, 5, 5));
					break;
				case 4:
					//add movement speed to the bar
					bar.speed *= 2;
					break;
				}
			}
        }
        for(int i = 0; i < drops.size(); i++){
        	Drop current = drops.get(i);
			Rectangle r6 = current.getBounds();
			//drops collide with bar
			if (r3.intersects(r6)) {
				current.life--;
				//give the drop bonus
				switch(current.type){
					case 1:
						//Diamond = 100 point
						score+=100;
						break;
					case 2:
						//Gold = 100 point
						score+=75;
						break;
					case 3:
						//Redstone = 100 point
						score+=50;
						break;
					case 4:
						//Lapis = 100 point
						score+=25;
						break;
				}
			}
        }
	}
	
	private String scoreToString(int score){
		String scoreString = String.valueOf(score);
		String zeros = "";
		int length = scoreString.length();
		while(length < 10){
			zeros+="0";
			length++;
		}
		return zeros+scoreString;
	}
	private String scoreToString(int score, int zeroBefor){
		String scoreString = String.valueOf(score);
		String zeros = "";
		int length = scoreString.length();
		while(length < zeroBefor){
			zeros+="0";
			length++;
		}
		return zeros+scoreString;
	}
	
	private void resetStats(){
		score = 0;
		time = 0;
		life = 3;
		goNextLevel = false;
		bricks = null;
		balls = null;
		bar = null;
		level = 1;
		time = 0;
	}
	
	private String timeToString(int time){

		int timeToSec = time /20;
		int sec = timeToSec % 60;
		int min = timeToSec / 60;
		
		String t = "";
		t+=min;
		t+=":";
		t+=scoreToString(sec,2);
		return t;
	}

}
