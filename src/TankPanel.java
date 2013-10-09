import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.JPanel;

public class TankPanel extends JPanel{
	public TankPanel() {
		setBackground(Color.GREEN);
		new Thread(new PaintThread()).start();
		
		this.setFocusable(true);
		this.addKeyListener(new KeyMonitor());
	}
	
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawString("arraysize: " + misArrayList.size(), 20, 30);
		
		// 画坦克
		myTank.draw(g);
		enemyTank.draw(g);
		
		// 画炮弹
		for(int i=0; i<misArrayList.size(); i++) {
			Missile m = misArrayList.get(i);
			m.draw(g);
		}
		
	}

	public ArrayList<Missile> misArrayList = new ArrayList<Missile>();
	private Tank myTank = new Tank(50, 50, true, this);
	private Tank enemyTank = new Tank(60, 70, false, this);
	
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
