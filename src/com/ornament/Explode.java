package com.ornament;

import java.awt.Color;
import java.awt.Graphics;

import com.main.TankPanel;


public class Explode {
	public Explode(int x, int y, TankPanel tp) {
		super();
		this.x = x;
		this.y = y;
		this.tp = tp;
	}
	
	public void draw(Graphics g) {
		if(!live) return ;
		
		if(step == diameter.length) {
			live = false;
			return ;
		}
		
		Color c = g.getColor();
		g.setColor(Color.GRAY);
		g.fillOval(x, y, diameter[step], diameter[step]);
		g.setColor(c);
		step++;
	}
	
	public boolean isLive() {
		return live;
	}
	
	private static int[] diameter = {4, 7, 12, 18, 16, 26, 32, 49, 30, 14, 6};
	private int step = 0;
	private boolean live = true;
	private int x, y;
	private TankPanel tp;
}
