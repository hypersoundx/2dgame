package rpgGame;

import javax.swing.JPanel;

import entity.Player;
import tile.TileManager;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;


public class GamePanel extends JPanel implements Runnable{
	
	private static final long serialVersionUID = 1L;
	
	// settings
	final int ogTileSize = 16;
	final int scale = 3;
	
	public final int tileSize = ogTileSize * scale; // 48x48
	
	public final int maxScreenCol = 24;
	public final int maxScreenRow = 16;
	
	public final int screenWidth = tileSize * maxScreenCol;
	public final int screenHeight = tileSize * maxScreenRow;
	
	int FPS = 60;
	KeyHandler keyH = new KeyHandler();
	Thread gameThread;
	Player player = new Player(this, keyH);
	TileManager tileM = new TileManager(this);
	
	
	// constructor
	public GamePanel() {
		
		this.setPreferredSize(new Dimension(screenWidth, screenHeight));
		this.setBackground(Color.black);
		this.setDoubleBuffered(true);
		this.addKeyListener(keyH);
		this.setFocusable(true);
		this.requestFocusInWindow();
	}
	
	public void startGameThread() {
		
		gameThread = new Thread(this);
		gameThread.start();
		this.requestFocusInWindow();
	}
	@Override
	public void run() {
		
		double drawInterval = 1000000000/FPS;
		double delta = 0; // game loop delta method 
		long lastTime = System.nanoTime();
		long currentTime;
		long timer = 0;
		int drawCount = 0;
		
		// game loop
		while (gameThread != null) {
			
			currentTime = System.nanoTime(); 
			delta += (currentTime - lastTime) / drawInterval;
			timer += (currentTime - lastTime);
			lastTime = currentTime;
			
			if (delta >= 1) {
				update();
				repaint();	
				delta--;
				drawCount++;
			}
			if (timer >= 1000000000) {
				System.out.println("FPS:" + drawCount);
				drawCount = 0;
				timer = 0;
			}

		}
		
	}
	public void update() {
		
		player.update();
		
	}
	public void paintComponent(Graphics g) {
		
		super.paintComponent(g);
		
		// change to graphics2d with more functions
		Graphics2D g2 = (Graphics2D)g;
		
		tileM.draw(g2); // background drawn first
		
		player.draw(g2);
		
		g2.dispose();
	}
}







