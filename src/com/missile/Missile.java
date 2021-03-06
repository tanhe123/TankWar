package com.missile;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.main.TankClient;
import com.main.TankPanel;
import com.ornament.Explode;
import com.ornament.Wall;
import com.tank.*;

public class Missile {
	private static Toolkit tk;
	private static Image[] imgs;
	public static Map<String, Image> hashImgs = new HashMap<String, Image>();
	
	private Image imga = tk.getImage(Explode.class.getClassLoader().getResource("images/missileU.gif"));
	
	static {
		tk = Toolkit.getDefaultToolkit();
		imgs = new Image[] {
			tk.getImage(Explode.class.getClassLoader().getResource("images/missileL.gif")),
			tk.getImage(Explode.class.getClassLoader().getResource("images/missileLU.gif")),
			tk.getImage(Explode.class.getClassLoader().getResource("images/missileU.gif")),
			tk.getImage(Explode.class.getClassLoader().getResource("images/missileRU.gif")),
			tk.getImage(Explode.class.getClassLoader().getResource("images/missileR.gif")),
			tk.getImage(Explode.class.getClassLoader().getResource("images/missileRD.gif")),
			tk.getImage(Explode.class.getClassLoader().getResource("images/missileD.gif")),
			tk.getImage(Explode.class.getClassLoader().getResource("images/missileLD.gif"))
		};
				
		hashImgs.put("L", imgs[0]);
		hashImgs.put("LU", imgs[1]);
		hashImgs.put("U", imgs[2]);
		hashImgs.put("RU", imgs[3]);
		hashImgs.put("R", imgs[4]);
		hashImgs.put("RD", imgs[5]);
		hashImgs.put("D", imgs[6]);
		hashImgs.put("LD", imgs[7]);
	}
	
	public Missile(int x, int y, Direction dir) {
		this.x = x;
		this.y = y;
		this.dir = dir;
	}
	
	public Missile(int x, int y, boolean good, Direction dir, TankPanel tp) {
		this(x, y, dir);
		this.tp = tp;
		this.good = good;
	}
	
	public void draw(Graphics g) {

		switch (dir) {
		case L:
			g.drawImage(hashImgs.get("L"), x, y, null);
			break;
		case LU:
			g.drawImage(hashImgs.get("LU"), x, y, null);
			break;
		case U: 
			g.drawImage(hashImgs.get("U"), x, y, null);
			break;
		case RU:
			g.drawImage(hashImgs.get("RU"), x, y, null);
			break;
		case R:
			g.drawImage(hashImgs.get("R"), x, y, null);
			break;
		case RD:
			g.drawImage(hashImgs.get("RD"), x, y, null);
			break;
		case D:
			g.drawImage(hashImgs.get("D"), x, y, null);
			break;
		case LD:
			g.drawImage(hashImgs.get("LD"), x, y, null);
			break;
		default :
			break;
		}

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
		if(this.isLive() && t.isLive() && this.getRect().intersects(t.getRect())
				&& this.good != t.isGood()) {
			if(t.isGood()) {	// 自己
				t.setLife(t.getLife()-20);
				if(t.getLife() <= 0) {
					t.setLife(0);
					t.setLive(false);
				}
			} else { 			// 敌人
				t.setLive(false);
			}
			
			this.live = false;			
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
	private Direction dir;
	private TankPanel tp;
	
	public static final int MISSLE_SIZE = 10;
	public static final int MISSILE_SPEED = 12;

}
