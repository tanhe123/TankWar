package com.main;

import javax.swing.JFrame;

public class TankClient extends JFrame{
	public static void main(String[] args) {
		new TankClient();
	}
	
	public TankClient() {
		setTitle("TankWar");
		setLocation(200, 200);
		setSize(WIDTH, HEIGHT);
			
		getContentPane().add(new TankPanel());
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);				
		setResizable(false);
	}
	
	public final static int HEIGHT = 500;
	public final static int WIDTH = 600;
}
