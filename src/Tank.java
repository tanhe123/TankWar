import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;


public class Tank {
	public Tank(int x, int y, boolean good) {
		this.x = x;
		this.y = y;
		this.good = good;
	}
	
	public Tank(int x, int y, boolean good, TankPanel tp) {
		this(x, y, good);
		this.tp = tp;
	}
	
	public void draw(Graphics g) {
		Color c = g.getColor();
		// 设置坦克颜色
		if(this.good) g.setColor(Color.RED);
		else g.setColor(Color.GRAY);
		
		g.fillOval(x, y, TANK_SIZE, TANK_SIZE);
		g.setColor(Color.BLACK);
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
		g.setColor(c);
		move();
	}
	
	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();
		switch(key) {
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
		case KeyEvent.VK_CONTROL:
			fire();
			break;
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
		case STOP:
			break;
		}
		
		// 炮筒方向
		if(this.dir != Direction.STOP) {
			ptdir = dir;
		}
		
		if(x < 0) x = 0;
		if(y < 0) y = 0;
		if(x + TANK_SIZE > TankClient.WIDTH) x = TankClient.WIDTH-TANK_SIZE;
		// 去掉标题栏的高度
		if(y + TANK_SIZE > TankClient.HEIGHT-30) y = TankClient.HEIGHT-TANK_SIZE-30;
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
		else if(!bleft && !bright && !bup && !bdown) dir = Direction.STOP;
	}
	
	public Missile fire() {
		int x = this.x+TANK_SIZE/2-Missile.MISSLE_SIZE/2;
		int y = this.y+TANK_SIZE/2-Missile.MISSLE_SIZE/2;
		Missile m = new Missile(x, y, ptdir, this.tp);
		tp.misArrayList.add(m);
		return m;
	}
	
	private boolean good;
	private TankPanel tp;
	private int x, y;
	private Direction dir = Direction.STOP;
	private Direction ptdir = Direction.R;
	private boolean bleft = false, bright = false, bup = false, bdown = false; 
	enum Direction {L, LU, U, RU, R, RD, D, LD, STOP};
	
	public static final int TANK_SIZE = 30;
	public static final int TANK_SPEED = 5;
}
