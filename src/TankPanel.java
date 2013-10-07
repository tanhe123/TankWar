import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

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
		g.setColor(Color.RED);
		myTank.draw(g);
		if(mis != null) {
			mis.draw(g);
		}
	}

	public Missile mis;
	private Tank myTank = new Tank(50, 50, this);
	
	private class PaintThread implements Runnable {
		public void run() {
			while(true) {
				repaint();
				try {
					Thread.sleep(40);
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
