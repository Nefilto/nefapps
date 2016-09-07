package nefilto.devicemodapi.dev.gameobjects.minebreaker;

import nefilto.devicemodapi.dev.gameobjects.GameObject;

public class Bar extends GameObject {
	
	public Bar(int x, int y, int width, int height) {
		super(x, y, width, height);
		this.speed = 4;
	}
	@Override
	public void update(int x, int y, int width, int height) {
		this.x += velX;
		this.y += velY;
		//check out of bound
		if(this.x <= x+110){
			velX = 0;
			this.x = x+110;
		}
		if(this.x >= x+width - this.width){
			velX = 0;
			this.x = x+width - this.width;
		}
		if(this.y <= y){
			velY = 0;
		}
		if(this.y >= y + height - this.height){
			velY = 0;
		}
	}
	
}
