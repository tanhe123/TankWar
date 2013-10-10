package com.tank;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Random;

import com.main.*;
import com.missile.*;
import com.ornament.*;


public class Tank {
	public Tank(int x, int y, boolean good) {
		this.x = x;
		this.y = y;
		this.oldX = x;
		this.oldY = y;
		this.good = good;
	}
	
	public Tank(int x, int y, boolean good, TankPanel tankPanel) {
		this(x, y, good);
		this.tp = tankPanel;
	}
	
	public void draw(Graphics g) {
		if(!live) return;
						
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
		case KeyEvent.VK_O:
			superFire();
			break;
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
		this.oldX = this.x;
		this.oldY = this.y;
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
		
		//触碰到边缘
		if(x < 0) stay();
		if(y < 0) stay();
		if(x + TANK_SIZE > TankClient.WIDTH) stay();
		// 去掉标题栏的高度
		if(y + TANK_SIZE > TankClient.HEIGHT-30) stay();
		
		if(!good) {
			if(step == 0) {
				step = r.nextInt(10);
				Direction[] dirs = Direction.values();
				int rn = r.nextInt(dirs.length);
				dir = dirs[rn];
			}
			
			step--;
			
			if(r.nextInt(40) > 38) this.fire();
		}
	}
	
	private void stay() {
		x = oldX;
		y = oldY;
		dir = Direction.STOP;
		step = 0;
	}
	
	public boolean collidesWithWall(Wall wall) {
		if(this.live && this.getRect().intersects(wall.getRect())) {
			stay();
			return true;
		}
		return false;
	}
	
	public boolean collidesWithWall(ArrayList<Wall> walls) {
		for(Wall e : walls) {
			if(this.collidesWithWall(e)) 
				return true;
		}
		return false;
	}
	
	public boolean collidesWithTank(Tank t) {
		if(this != t && this.live && t.live && this.getRect().intersects(t.getRect())) {
			this.stay();
			return true;
		}
		return false;
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
		return this.fire(ptdir);
	}
	
	public Missile fire(Direction dir) {
		if(!live) return null;
		
		int x = this.x+TANK_SIZE/2-Missile.MISSLE_SIZE/2;
		int y = this.y+TANK_SIZE/2-Missile.MISSLE_SIZE/2;
		Missile m = new Missile(x, y, this.good, dir, this.tp);
		tp.misArrayList.add(m);
		return m;
	}
	
	public void superFire() {
		Direction[] dirs = Direction.values();
		for(Direction d : dirs) {
			if(d == Direction.STOP) continue;
			fire(d);
		}
	}
	
	public Rectangle getRect() {
		return new Rectangle(x, y, TANK_SIZE, TANK_SIZE);
	}
	
	public boolean isLive() { 
		return live;
	}

	public void setLive(boolean live) {
		this.live = live;
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	public boolean isGood() {
		return good;
	}
	
	public int getLife() {
		return life;
	}

	public void setLife(int life) {
		this.life = life;
	}
	
	private int oldX, oldY;	//保存的前一坐标
	private int step = 0;	
	private boolean good;	//是自己，还是敌人
	private static Random r = new Random();
	private boolean live = true;	//是否还或者
	private int life = 40;
	private TankPanel tp;
	private int x, y;
	private Direction dir = Direction.STOP;
	private Direction ptdir = Direction.R;
	private boolean bleft = false, bright = false, bup = false, bdown = false; 
	public enum Direction {L, LU, U, RU, R, RD, D, LD, STOP};
	
	public static final int TANK_SIZE = 30;
	public static final int TANK_SPEED = 5;
}
