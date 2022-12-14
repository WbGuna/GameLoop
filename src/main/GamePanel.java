package main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

public class GamePanel extends JPanel implements Runnable{

	private static final long serialVersionUID = 6644375181764124582L;
	
	final int originalTilesSize = 16; // 16x16 tile
	final int scale = 3;
	
	final int tileSize = originalTilesSize * scale; // 48x48 tile
	final int maxScreenCol = 16;
	final int maxScreenRow = 12;
	final int screenWidth = tileSize * maxScreenCol; // 768 pixels - Largura
	final int screenHeight = tileSize * maxScreenRow; // 576 pixels - Altura
	
	int FPS = 60;
	
	KeyHandler keyH = new KeyHandler();
	Thread gameThread;
	
	int playerX = 100;
	int playerY = 100;
	int playerSpeed = 4;
	
	public GamePanel() {
		
		this.setPreferredSize(new Dimension(screenWidth, screenHeight));
		this.setBackground(Color.black);
		this.setDoubleBuffered(true);
		this.addKeyListener(keyH);
		this.setFocusable(true);
	}
	
	public void startGameThread() {
		
		gameThread = new Thread(this);
		gameThread.start();
	}

//	@Override
//	public void run() {
//		
//		double drawInterval = 1000000000/FPS; // por secundos
//		double nexDrawTime = System.nanoTime() + drawInterval;
//
//		while(gameThread != null) {
//
//			update();			
//			repaint();
//						
//			try {
//				double remainingTime = nexDrawTime - System.nanoTime();
//				remainingTime =  remainingTime/1000000;
//				
//				if(remainingTime < 0) {
//					remainingTime = 0;
//				}
//				Thread.sleep((long) remainingTime);
//				
//				nexDrawTime += drawInterval;
//				
//			} catch (InterruptedException e) {
//				e.printStackTrace();
//			}
//			
//		}
//		
//	}
	
	@Override
	public void run() {
		
		double drawInterval = 1000000000/FPS; // por secundos
		double delta = 0;
		long lastTime = System.nanoTime();
		long currentTime;
		long timer = 0;
		int drawCont = 0;

		while(gameThread != null) {

			currentTime = System.nanoTime();
			delta += (currentTime - lastTime) / drawInterval;
			timer += (currentTime - lastTime);
			lastTime = currentTime;
			if(delta >= 1) {
				update();			
				repaint();
				delta --;
				drawCont++;
				
			}
			
			if(timer >= 1000000000) {
				System.out.println("FPS: " + drawCont);
				drawCont = 0;
				timer = 0;
			}
			
		}
		
	}
	
	public void update() {
		
		if(keyH.upPressed == true) {
			playerY -= playerSpeed;			
		} else if(keyH.downPressed == true) {
			playerY += playerSpeed;
		} else if(keyH.leftPressed == true) {
			playerX -= playerSpeed;
		} else if(keyH.rightPressed == true) {
			playerX += playerSpeed;
		}
	}
	
	public void paintComponent(Graphics g) {
		
		super.paintComponent(g);		
		Graphics2D g2 = (Graphics2D)g;		
		g2.setColor(Color.white);		
		g2.fillRect(playerX, playerY, tileSize, tileSize);		
		g2.dispose();
		
	}

}
