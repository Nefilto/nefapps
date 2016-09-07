package nefilto.devicemodapi.dev.gameobjects.minebreaker;

import java.util.Random;

import nefilto.devicemodapi.dev.gameobjects.GameObject;

public class Brick extends GameObject {
	public int type;
	public int point;
	public boolean hasPowerUp;

	public Brick(int x, int y, int width, int height, int life, int type) {
		super(x, y, width, height);
		this.life = life;
		this.type = type;
		this.point = 5;
		this.hasPowerUp = false;
	}
	public Brick(int x, int y, int width, int height, int life, int type, int point) {
		this(x, y, width, height, life, type);
		this.point = point;
	}
	public Brick(int x, int y, int width, int height, int life, int type, int point, boolean hasPowerUp) {
		this(x, y, width, height, life, type, point);
		this.hasPowerUp = hasPowerUp;
	}
	
	public PowerUp dropPowerUp(Random rand){
		int num = 4;
		int powerType = rand.nextInt(num - 1) + 1;
		return new PowerUp(x + 2, y, 7, 7, powerType);
	}
}
