package com.missile;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.ArrayList;

import com.main.TankClient;
import com.main.TankPanel;
import com.ornament.Explode;
import com.ornament.Wall;
import com.tank.Tank;

public class Missile {
	
	public Missile(int x, int y, Tank.Direction dir) {
		this.x = x;
		this.y = y;
		this.dir = dir;
	}
	
	public Missile(int x, int y, boolean good, Tank.Direction dir, TankPanel tp) {
		this(x, y, dir);
		this.tp = tp;
		this.good = good;
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
		
		// 飞出边界
		if(this.x < 0 || this.y < 0 || this.x > TankClient.WIDTH || this.y > TankClient.HEIGHT) {
			live = false;
		}
		
	}
	
	public boolean isLive() {
		return live;
	}
	
	public void setLive(boolean b) {
		live = b;
	}
	
	public Rectangle getRect() {
		return new Rectangle(x, y, MISSLE_SIZE, MISSLE_SIZE);
	}
	
	public boolean hitTank(Tank t) {
		if(t.isLive() && this.getRect().intersects(t.getRect())
				&& this.good != t.isGood()) {
			
			if(t.isGood()) {	// 自己
				if(t.getLife() - 20 > 0) {
					t.setLife(t.getLife()-20);
				} else {
					t.setLife(0);
					t.setLive(false);
				}
			} else { 			// 敌人
				t.setLive(false);
			}
			
			Explode e = new Explode(t.getX(), t.getY(), tp);
			tp.explodes.add(e);
			return true;
		}
		return false;
	}
	
	public boolean hitTank(ArrayList<Tank> tanks) {
		for(int i=0; i<tanks.size(); i++) {
			if(this.hitTank(tanks.get(i))) {
				return true;
			}
		}
		return false;
	}
	
	public boolean hitWall(Wall wall) {
		if(this.live && this.getRect().intersects(wall.getRect())) {
			this.live = false;
			return true;
		}
		return false;
	}
	
	public boolean hitWall(ArrayList<Wall> walls) {
		for(Wall e : walls) {
			if(this.hitWall(e))
				return true;
		}
		return false;
	}
	
	private boolean good;
	private boolean live = true;
	private int x, y;
	private Tank.Direction dir;
	private TankPanel tp;
	
	public static final int MISSLE_SIZE = 10;
	public static final int MISSILE_SPEED = 12;

}
