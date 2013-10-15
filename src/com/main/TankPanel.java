package com.main;

import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.*;

import javax.swing.JOptionPane;
import javax.swing.JPanel;

import com.tank.*;
import com.ornament.*;
import com.missile.*;

public class TankPanel extends JPanel{
	public TankPanel() {
		setBackground(Color.GREEN);
		new Thread(new PaintThread()).start();
		
		this.setFocusable(true);
		this.addKeyListener(new KeyMonitor());

		// 添加墙, 墙要先于坦克添加
		walls.add(new Wall(200, 300, 40, 230, this));
		walls.add(new Wall(100, 200, 140, 30, this));

		// 把自身加入
		tanks.add(myTank);
			
		// 添加敌人
		for(int i=0; i<2; i++) {
			this.addRandomTank();
		}
	}
	
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		g.drawString("missile arraysize: " + misArrayList.size(), 20, 30);
		g.drawString("explode arraysize: " + explodes.size(), 20, 50);
		g.drawString("tanks size : " + tanks.size(), 20, 70);
		g.drawString("my life: " + myTank.getLife(), 20, 90);
		
		// 画坦克
		Iterator<Tank> tita = tanks.iterator();
		while(tita.hasNext()) {
			Tank t = tita.next();
			if(!t.isLive()) tita.remove();
			else {
				t.collidesWithTank(tanks);
				t.collidesWithWall(walls);
				t.draw(g);
			}
		}
		
		// 画子弹
		Iterator<Missile> mita = misArrayList.iterator();
		while(mita.hasNext()) {
			Missile m = mita.next();

			if(m.isLive()) {
				// 是否击到其他坦克
				m.hitTank(tanks);
				m.hitTank(myTank);
				m.hitWall(walls);
				m.draw(g);
			}
			else mita.remove();
		}
		
		// 画爆炸
		Iterator<Explode> eita = explodes.iterator();
		while(eita.hasNext()) {
			Explode e = eita.next();
			if(!e.isLive()) eita.remove();
			else e.draw(g);
		}
		
		// 画墙 
		for(Wall e : walls) {
			e.draw(g);
		}
	}
	
	public void addRandomTank() {
		while(true) {
			Tank t = new EnemyTank(Tank.r.nextInt(TankClient.WIDTH-100), 
					Tank.r.nextInt(TankClient.HEIGHT-100), this);
			if(!t.collidesWithTank(tanks) && !t.collidesWithWall(walls)) {
				tanks.add(t);
				break;
			}
		}
	}

	public ArrayList<Tank> tanks = new ArrayList<Tank>();
	public ArrayList<Missile> misArrayList = new ArrayList<Missile>();
	public ArrayList<Wall> walls = new ArrayList<Wall>();
	public MyTank myTank = new MyTank(250, 300, true, this);
	public ArrayList<Explode> explodes = new ArrayList<Explode>(); 
	
	
	private class PaintThread implements Runnable {
		public void run() {
			while(true) {
				if(tanks.size() == 1) {
					int r = JOptionPane.showConfirmDialog(null, "恭喜您胜利了\n");
					if(r == JOptionPane.OK_OPTION) {
						break;
					}
				}
				else if(!myTank.isLive()) {
					int r = JOptionPane.showConfirmDialog(null, "您失败了");
					if(r == JOptionPane.OK_OPTION) {
						break;
					}
				}
				
				repaint();
				try {
					Thread.sleep(50);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	private class KeyMonitor extends KeyAdapter {
		public void keyPressed(KeyEvent e) {
			myTank.keyPressed(e);
		}

		public void keyReleased(KeyEvent e) {
			myTank.keyReleased(e);
		}
	}
}
