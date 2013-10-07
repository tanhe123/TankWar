import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;


public class Tank {
	public Tank(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public Tank(int x, int y, TankPanel tp) {
		this(x, y);
		this.tp = tp;
	}
	
	public void draw(Graphics g) {
		g.fillOval(x, y, TANK_SIZE, TANK_SIZE);
		ptdir = Direction.R;
		g.setColor(Color.BLACK);
	//	System.out.println(ptdir);
		switch (ptdir) {
		case L:
			g.drawLine(x+TANK_SIZE/2, y+TANK_SIZE/2, x, y+TANK_SIZE/2);
			break;
		case LU:
			g.drawLine(x+TANK_SIZE/2, y+TANK_SIZE/2, x, y);
			break;
		case U:
			g.drawLine(x+TANK_SIZE/2, y+TANK_SIZE/2, x+TANK_SIZE/2, y);
			break;
		case RU:
			g.drawLine(x+TANK_SIZE/2, y+TANK_SIZE/2, x+TANK_SIZE, y);
			break;
		case R:
			g.drawLine(x+TANK_SIZE/2, y+TANK_SIZE/2, x+TANK_SIZE, y+TANK_SIZE/2);
			break;
		case RD:
			g.drawLine(x+TANK_SIZE/2, y+TANK_SIZE/2, x+TANK_SIZE, y+TANK_SIZE);
			break;
		case D:
			g.drawLine(x+TANK_SIZE/2, y+TANK_SIZE/2, x+TANK_SIZE/2, y+TANK_SIZE);
			break;
		case LD:
			g.drawLine(x+TANK_SIZE/2, y+TANK_SIZE/2, x, y+TANK_SIZE);
			break;
		}
		
		move();
	}
	
	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();
		switch(key) {
		case KeyEvent.VK_CONTROL:
			tp.mis = new Missile(x+TANK_SIZE/2-Missile.MISSLE_SIZE/2, y+TANK_SIZE/2-Missile.MISSLE_SIZE/2, dir);
			break;
		case KeyEvent.VK_LEFT: case KeyEvent.VK_A:
			bleft = true;
			break;
		case KeyEvent.VK_RIGHT: case KeyEvent.VK_D:
			bright = true;
			break;
		case KeyEvent.VK_UP: case KeyEvent.VK_W:
			bup = true;
			break;
		case KeyEvent.VK_DOWN: case KeyEvent.VK_S:
			bdown = true;
			break;
		}
		locateDirection();
	}

	public void keyReleased(KeyEvent e) {
		int key = e.getKeyCode();
		switch(key) {
		case KeyEvent.VK_LEFT: case KeyEvent.VK_A:
			bleft = false;
			break;
		case KeyEvent.VK_RIGHT: case KeyEvent.VK_D:
			bright = false;
			break;
		case KeyEvent.VK_UP: case KeyEvent.VK_W:
			bup = false;
			break;
		case KeyEvent.VK_DOWN: case KeyEvent.VK_S:
			bdown = false;
			break;
		}
		locateDirection();
		move();
	}

	private void move() {
		switch(dir) {
		case L:
			x -= TANK_SPEED;
			break;
		case LU:
			x -= TANK_SPEED;
			y -= TANK_SPEED;
			break;
		case U:
			y -= TANK_SPEED;
			break;
		case RU:
			x += TANK_SPEED;
			y -= TANK_SPEED;
			break;
		case R:
			x += TANK_SPEED;
			break;
		case RD:
			x += TANK_SPEED;
			y += TANK_SPEED;
			break;
		case D:
			y += TANK_SPEED;
			break;
		case LD:
			x -= TANK_SPEED;
			y += TANK_SPEED;
			break;
		}
		
		if(this.dir != Direction.STOP) {
			System.out.println("ha");
			ptdir = dir;
		}
	}
	
	private void locateDirection() {
		if(bleft && !bright && !bup && !bdown) dir = Direction.L;
		else if(bleft && !bright && bup && !bdown) dir = Direction.LU;
		else if(!bleft && !bright && bup && !bdown) dir = Direction.U;
		else if(!bleft && bright && bup && !bdown) dir = Direction.RU;
		else if(!bleft && bright && !bup && !bdown) dir = Direction.R;
		else if(!bleft && bright && !bup && bdown) dir = Direction.RD;
		else if(!bleft && !bright && !bup && bdown) dir = Direction.D;
		else if(bleft && !bright && !bup && bdown) dir = Direction.LD;
	}
	
	private TankPanel tp;
	private int x, y;
	private Direction dir = Direction.STOP;
	private Direction ptdir = Direction.R;
	private boolean bleft = false, bright = false, bup = false, bdown = false; 
	enum Direction {L, LU, U, RU, R, RD, D, LD, STOP};
	
	public static final int TANK_SIZE = 30;
	private static final int TANK_SPEED = 5;
}
