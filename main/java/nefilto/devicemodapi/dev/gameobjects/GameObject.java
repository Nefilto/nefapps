package nefilto.devicemodapi.dev.gameobjects;

import java.awt.Rectangle;

public class GameObject {
	public int x;
	public int y;
	public int width;
	public int height;
	public int velX;
	public int velY;
	public int speed;
	public int life;
	
	public GameObject(int x, int y, int width, int height){
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.velX = 0;
		this.velY = 0;
		this.speed = 2;
		this.life = 1;
	}
	
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
			velY*=-1;
		}
	}
	public Rectangle getBounds() {
        return new Rectangle(x, y, width, height);
    }
}
