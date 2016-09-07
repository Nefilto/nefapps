package nefilto.devicemodapi.dev.gameobjects.minebreaker;

import nefilto.devicemodapi.dev.gameobjects.GameObject;

public class Ball extends GameObject {
	public boolean canLaunch;
	public boolean isSticky;
	public boolean canDie;
	public boolean isNewBall;

	public Ball(int x, int y, int width, int height) {
		super(x, y, width, height);
		this.canLaunch = true;
		this.isSticky = true;
		this.canDie = true;
		this.isNewBall = true;
		this.speed = 2;
	}
	
	@Override
	public void update(int x, int y, int width, int height) {
		this.x += velX;
		this.y += velY;
		//check out of bound
		if(this.x <= x+110){
			velX*=-1;
		}
		if(this.x >= x+width - this.width){
			velX*=-1;
		}
		if(this.y <= y){
			velY*=-1;
		}
		if(this.y >= y + height - this.height){
			if(canDie){
				life--;
			}else{
				this.y = y + height - this.height;
				velY*=-1;
			}
		}
	}
}
