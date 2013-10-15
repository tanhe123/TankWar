package com.tank;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import com.main.*;
import com.missile.*;
import com.ornament.*;

public class Tank {
	private static Toolkit tk;
	private static Image[] imgs;
	public static Map<String, Image> hashImgs = new HashMap<String, Image>();
	
	static {
		tk = Toolkit.getDefaultToolkit();
		imgs = new Image[] {
			tk.getImage(Explode.class.getClassLoader().getResource("images/tankL.gif")),
			tk.getImage(Explode.class.getClassLoader().getResource("images/tankLU.gif")),
			tk.getImage(Explode.class.getClassLoader().getResource("images/tankU.gif")),
			tk.getImage(Explode.class.getClassLoader().getResource("images/tankRU.gif")),
			tk.getImage(Explode.class.getClassLoader().getResource("images/tankR.gif")),
			tk.getImage(Explode.class.getClassLoader().getResource("images/tankRD.gif")),
			tk.getImage(Explode.class.getClassLoader().getResource("images/tankD.gif")),
			tk.getImage(Explode.class.getClassLoader().getResource("images/tankLD.gif"))
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
	
	public Tank(int x, int y) {
		this.x = x;
		this.y = y;
		this.oldX = x;
		this.oldY = y;
	}
	
	public Tank(int x, int y, TankPanel tankPanel) {
		this(x, y);
		this.tp = tankPanel;
	}
	
	public void draw(Graphics g) {
		if(!live) return;
					
		//显示学条
		if(good) blood.draw(g);

		switch (ptdir) {
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
	
	public boolean collidesWithTank(ArrayList<Tank> tanks) {
		for(Tank e : tanks) {
			if(this != e && this.collidesWithTank(e)) 
				return true;
		}
		return false;
	}
	
	protected void locateDirection() {
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
	
	public void setColor(Color c) {
		tankColor = c;
	}
	
	public void setGood(boolean b) {
		good = b;
	}
	
	private Color tankColor = Color.GRAY;
	private BloodBar blood = new BloodBar();
	private int oldX, oldY;	//保存的前一坐标
	private int step = 0;
	private boolean good = false;	//是自己，还是敌人
	public static Random r = new Random();
	private boolean live = true;	//是否还或者
	private int life = 100;
	private TankPanel tp;
	private int x, y;
	private Direction dir = Direction.STOP;
	private Direction ptdir = Direction.R; 
	public boolean bleft = false, bright = false, bup = false, bdown = false; 
	
	public static final int TANK_SIZE = 30;
	public static final int TANK_SPEED = 5;
	
	
	private class BloodBar {
		public void draw(Graphics g) {
			Color c = g.getColor();
			g.setColor(Color.BLACK);
			g.drawRect(x, y-10, Tank.TANK_SIZE, 10);
			g.setColor(Color.RED);
			g.fillRect(x, y-10, getLife()*TANK_SIZE/100, 10);
			g.setColor(c);
		}
	}
}
