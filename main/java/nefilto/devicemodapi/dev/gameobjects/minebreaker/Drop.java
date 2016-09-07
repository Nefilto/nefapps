package nefilto.devicemodapi.dev.gameobjects.minebreaker;

import nefilto.devicemodapi.dev.gameobjects.GameObject;

public class Drop extends GameObject {
	public int type;

	public Drop(int x, int y, int width, int height, int type) {
		super(x, y, width, height);
		this.speed = 4;
		this.velY = this.speed;
		this.type = type;
	}
	
	@Override
	public void update(int x, int y, int width, int height){
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
			life--;
		}
	}
}
