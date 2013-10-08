import java.awt.Color;
import java.awt.Graphics;

public class Missile {
	
	public Missile(int x, int y, Tank.Direction dir) {
		this.x = x;
		this.y = y;
		this.dir = dir;
	}
	
	public void draw(Graphics g) {
		Color c = g.getColor();
		g.setColor(Color.BLACK);
		g.fillOval(x, y, MISSLE_SIZE, MISSLE_SIZE);
		g.setColor(c);
		
		move();
	}
	
	private void move() {
		switch(dir) {
		case L:
			x -= MISSILE_SPEED;
			break;
		case LU:
			x -= MISSILE_SPEED;
			y -= MISSILE_SPEED;
			break;
		case U:
			y -= MISSILE_SPEED;
			break;
		case RU:
			x += MISSILE_SPEED;
			y -= MISSILE_SPEED;
			break;
		case R:
			x += MISSILE_SPEED;
			break;
		case RD:
			x += MISSILE_SPEED;
			y += MISSILE_SPEED;
			break;
		case D:
			y += MISSILE_SPEED;
			break;
		case LD:
			x -= MISSILE_SPEED;
			y += MISSILE_SPEED;
			break;
		}
	}

	private int x, y;
	private Tank.Direction dir;
	
	public static final int MISSLE_SIZE = 10;
	public static final int MISSILE_SPEED = 15;

}
