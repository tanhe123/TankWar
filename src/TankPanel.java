import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.rmi.server.ExportException;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.JPanel;

public class TankPanel extends JPanel{
	public TankPanel() {
		setBackground(Color.GREEN);
		new Thread(new PaintThread()).start();
		
		this.setFocusable(true);
		this.addKeyListener(new KeyMonitor());
		
		// 添加坦克
		for(int i=0; i<20; i++) {
			tanks.add(new Tank(30+40*i, 50, false, this));
		}
		
		// 添加墙
		walls.add(new Wall(200, 300, 40, 230, this));
		walls.add(new Wall(100, 200, 140, 30, this));
	}
	
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawString("missile arraysize: " + misArrayList.size(), 20, 30);
		g.drawString("explode arraysize: " + explodes.size(), 20, 50);
		g.drawString("tanks size : " + tanks.size(), 20, 70);
		g.drawString("my life: " + myTank.getLife(), 20, 90);
		// 画坦克
		myTank.collidesWithWall(walls);
		myTank.draw(g);
		
		// 画敌人
		Iterator<Tank> tita = tanks.iterator();
		while(tita.hasNext()) {
			Tank t = tita.next();
			if(!t.isLive()) tita.remove();
			else {
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

	public ArrayList<Tank> tanks = new ArrayList<Tank>();
	public ArrayList<Missile> misArrayList = new ArrayList<Missile>();
	public ArrayList<Wall> walls = new ArrayList<Wall>();
	public Tank myTank = new Tank(250, 300, true, this);
	public ArrayList<Explode> explodes = new ArrayList<Explode>(); 
	
	private class PaintThread implements Runnable {
		public void run() {
			while(true) {
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
