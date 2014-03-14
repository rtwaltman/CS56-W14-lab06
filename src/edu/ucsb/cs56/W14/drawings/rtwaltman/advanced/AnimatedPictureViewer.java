package edu.ucsb.cs56.w14.drawings.rtwaltman.advanced;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class AnimatedPictureViewer {
	private DrawPanel panel = new DrawPanel();
	private TandemBike tBike = new TandemBike(100,100, 15);

	Thread anim;

	private int x = 100;
	private int y = 100;
	private int dx = 10;
	private int dy = 10;

	public static void main(String[] args) {
		new AnimatedPictureViewer().go();
	}

	public void go(){
		JFrame frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		frame.getContentPane().add(panel);
		frame.setSize(700, 500);
		frame.setVisible(true);

		frame.getContentPane().addMouseListener(new MouseAdapter() {
			public void mouseEntered(MouseEvent e){
				System.out.println("Mouse entered");
				anim = new Animation();
				anim.start();
			}

			public void mouseExited(MouseEvent e){
				System.out.println("Mouse exited");
				//kill the animation thread
				anim.interrupt();
				while(anim.isAlive()){}
				anim = null;
				panel.repaint();
			}
		});
	} //go()

	class DrawPanel extends JPanel {
		public void paintComponent(Graphics g) {

			Graphics2D g2 = (Graphics2D) g;

			//clear the panel 1st
			g2.setColor(Color.white);
			g2.fillRect(0,0,this.getWidth(), this.getHeight());

			//draw the bike
			g2.setColor(Color.BLACK);
			TandemBike test = new TandemBike(x, y, 15);
			g2.draw(test);
		}
	}

	class Animation extends Thread {
		public void run() {
			try {
				while(true) {
					//bounce off all walls
					if(y>=450) {dy = -10;}
					if(y<=20) {dy = 10;}
					if(x>=590) {dx = -10;}
					if(x<=10) {dx = 10;}

					x += dx;
					y += dy;
					panel.repaint();
					Thread.sleep(50);
				}
			}catch (Exception ex) {
				if(ex instanceof InterruptedException) {
					//Do nothing - expected on mouseExited
				}else {
					ex.printStackTrace();
					System.exit(1);
				}
			}
		}
	}
}

