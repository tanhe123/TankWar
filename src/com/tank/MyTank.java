package com.tank;

import java.awt.Color;
import java.awt.event.KeyEvent;

import com.main.TankPanel;
import com.tank.Tank;

public class MyTank extends Tank {
	public MyTank(int x, int y) {
		super(x, y);
	}
		
	public MyTank(int x, int y, boolean good, TankPanel tankPanel) {
		super(x, y, tankPanel);
	}

	// 初始化
	{
		this.setColor(Color.BLUE);
		this.setGood(true);
	}
	
	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();
		switch(key) {
		case KeyEvent.VK_LEFT: case KeyEvent.VK_A:
			bleft = true;
			break;
		case KeyEvent.VK_RIGHT: case KeyEvent.VK_D:
			bright = true;
			break;
		case KeyEvent.VK_UP: case KeyEvent.VK_W:
			bup = true;
			break;
		case KeyEvent.VK_DOWN: case KeyEvent.VK_S:
			bdown = true;
			break;
		}
		locateDirection();
	}

	public void keyReleased(KeyEvent e) {
		int key = e.getKeyCode();
		switch(key) {
		case KeyEvent.VK_O:
			superFire();
			break;
		case KeyEvent.VK_CONTROL:
			fire();
			break;
		case KeyEvent.VK_LEFT: case KeyEvent.VK_A:
			bleft = false;
			break;
		case KeyEvent.VK_RIGHT: case KeyEvent.VK_D:
			bright = false;
			break;
		case KeyEvent.VK_UP: case KeyEvent.VK_W:
			bup = false;
			break;
		case KeyEvent.VK_DOWN: case KeyEvent.VK_S:
			bdown = false;
			break;
		}
		locateDirection();
	}
	
	public void superFire() {
		Direction[] dirs = Direction.values();
		for(Direction d : dirs) {
			if(d == Direction.STOP) continue;
			fire(d);
		}
	}
}


