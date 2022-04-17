package com.kihei.main;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;

public class Game extends Canvas implements Runnable {

	private static final long serialVersionUID = -4995321819197236202L;

	public static final int WIDTH = 1000, HEIGHT = 1000;
	
	private Thread thread;
	private boolean running = false;
	
	private Display display;
	private final Window w;
	
	public static BufferedImage crown;
	
	public Game() {
		BufferedImageLoader loader = new BufferedImageLoader();
		crown = loader.loadImage("/crown.png");
		
		this.addMouseListener(new MouseInput());
		
		display = new Display();
		w = new Window(WIDTH, HEIGHT, "Tictactoe", this);
		
		setup();
	}
	
	public synchronized void start() {
		thread = new Thread(this);
		thread.start();
		running = true;
	}
	
	public static int framesDisplay = 0;
	
	public void run() {
		this.requestFocus();
		long lastTime = System.nanoTime();
		double amountOfTicks = 60.00;
		double ns = 1000000000 / amountOfTicks;
		double delta = 0;
		long timer = System.currentTimeMillis();
		int frames = 0;
		
		while (running) {
			long now = System.nanoTime();
			delta += (now - lastTime) / ns;
			lastTime = now;
			while (delta >= 1) {
				tick();
				delta--;
			}
			if (running)
				render();
			frames++;
			
			if (System.currentTimeMillis() - timer > 1000) {
				timer += 1000;
				framesDisplay = frames;
				frames = 0;
			}
		}
	}
	
	private void tick() {
		
	}
	
	private void render() {
		BufferStrategy bs = this.getBufferStrategy();
		if (bs == null) {
			this.createBufferStrategy(3);
			return;
		}
		
		Graphics g = bs.getDrawGraphics();
		
		g.setColor(new Color(255, 252, 236));
		g.fillRect(0, 0, WIDTH, HEIGHT);
		
		int width = w.frame.getContentPane().getWidth();
		int height = w.frame.getContentPane().getHeight();
		
		display.render(g, width, height);
		
		g.dispose();
		bs.show();
	}
	
	public static void setup() {
		for (int r = 0; r < 8; r+=2) {
			for (int c = 1; c < 8; c+=2) {
				if (r == 0 || r == 2)
					Display.board[r][c] = 2;
				if (r == 6)
					Display.board[r][c] = 1;
			}
		}
		for (int r = 1; r < 8; r+=2) {
			for (int c = 0; c < 8; c+=2) {
				if (r == 1)
					Display.board[r][c] = 2;
				if (r == 5 || r == 7)
					Display.board[r][c] = 1;
			}
		}
	}
	
	public static void main(String args[]) {
		new Game();
	}
	
}