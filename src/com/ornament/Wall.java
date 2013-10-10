package com.ornament;

import java.awt.Graphics;
import java.awt.Rectangle;
import com.main.*;


public class Wall {
	public Wall(int x, int y, int w, int h, TankPanel tp) {
		super();
		this.x = x;
		this.y = y;
		this.w = w;
		this.h = h;
		this.tp = tp;
	}
	
	public void draw(Graphics g) {
		g.fillRect(x, y, w, h);
	}
	
	public Rectangle getRect() {
		return new Rectangle(x, y, w, h);
	}
	
	private int x, y, w, h;
	private TankPanel tp;
}
