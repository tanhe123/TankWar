package com.ornament;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;

import com.main.TankPanel;


public class Explode {
	public Explode(int x, int y, TankPanel tp) {
		super();
		this.x = x;
		this.y = y;
		this.tp = tp;
	}
	
	public void draw(Graphics g) {
		if(!init) {
			init = true;
			for(int i=0; i<imgs.length; i++) {
				g.drawImage(imgs[i], -100, -100, null);
			}
		}
		if(!live) return ;
		
		if(step == imgs.length) {
			live = false;
			return ;
		}
		
		g.drawImage(imgs[step], x, y, null);
		step++;
	}
	
	public boolean isLive() {
		return live;
	}
	
	private static Toolkit tk = Toolkit.getDefaultToolkit();
	private static Image[] imgs = {
		tk.getImage(Explode.class.getClassLoader().getResource("images/0.gif")),
		tk.getImage(Explode.class.getClassLoader().getResource("images/1.gif")),
		tk.getImage(Explode.class.getClassLoader().getResource("images/2.gif")),
		tk.getImage(Explode.class.getClassLoader().getResource("images/3.gif")),
		tk.getImage(Explode.class.getClassLoader().getResource("images/4.gif")),
		tk.getImage(Explode.class.getClassLoader().getResource("images/5.gif")),
		tk.getImage(Explode.class.getClassLoader().getResource("images/6.gif")),
		tk.getImage(Explode.class.getClassLoader().getResource("images/7.gif")),
		tk.getImage(Explode.class.getClassLoader().getResource("images/8.gif")),
		tk.getImage(Explode.class.getClassLoader().getResource("images/9.gif")),
	};

	private int step = 0;
	private boolean init = false;
	private boolean live = true;
	private int x, y;
	private TankPanel tp;
}
