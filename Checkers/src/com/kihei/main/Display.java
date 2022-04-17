package com.kihei.main;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class Display {
	
	public static int[][] board = new int[8][8];
	public static int ct = 1;
	public static int winner = 0;
	
	public void render(Graphics g, int width, int height) {
		int cX = ((width - 800) / 2);
		int cY = ((height - 800) / 2);
		
		setupBoard(g, cX, cY);
		
		Img crown = new Img(Game.crown);
		BufferedImage crown_image = crown.grabImage();
		
		// update circles on board based on array
		for (int r = 0; r < 8; r++) {
			for (int c = 0; c < 8; c++) {
				if (board[r][c] != 0) {
					if (MouseInput.sR == r && MouseInput.sC == c) {
						g.setColor(new Color(255, 255, 255));
						g.fillOval(cX+2, cY+2, 96, 96); // adds white outline if the piece is selected
					}
					
					if (board[r][c] == 1 || board[r][c] == 3) {
						g.setColor(new Color(0, 0, 0));
					} else if (board[r][c] == 2 || board[r][c] == 4) {
						g.setColor(new Color(226, 39, 45));
					}
					g.fillOval(cX+5, cY+5, 90, 90);
					
					if (board[r][c] == 3 || board[r][c] == 4) { // is a king
						g.setColor(new Color(230, 166, 18));
						g.drawImage(crown_image, cX+5, cY+5, null);
					}
				}
				cX += 100;
			}
			cY += 100;
			cX -= 800;
		}
		
		// checking for win (if there aren't any black or white pieces)
		boolean containsB = false;
		boolean containsW = false;
		for (int r = 0; r < 8; r++) {
			for (int c = 0; c < 8; c++) {
				if (MouseInput.isB(board[r][c])) {
					containsB = true;
					break;
				}
			}
		}
		for (int r = 0; r < 8; r++) {
			for (int c = 0; c < 8; c++) {
				if (MouseInput.isW(board[r][c])) {
					containsW = true;
					break;
				}
			}
		}
		if (containsB == false) {
			winner = 2;
		} else if (containsW == false) {
			winner = 1;
		}
		
		if (winner == 1 || winner == 2) {
			g.setColor(Color.black);
			cStr(g, tts(winner) + " won!", 0, -440, width, height, 40);
		}
		
		if (winner != 0) {
			g.setColor(Color.black);
			g.fillRect((width - 200) / 2, (height - 75) / 2 + 440, 200, 70);
			g.setColor(Color.white);
			cStr(g, "Play Again", (width - 200) / 2, (height - 75) / 2 + 435, 200, 75, 35);
		}
		
		g.setColor(new Color(0, 0, 0));
		g.setFont(new Font(g.getFont().getFontName(), Font.PLAIN, 40));
		g.drawString("Current turn:", 20, 50);
		if(Display.ct == 1) {
			g.setColor(new Color(0, 0, 0));
		} else {
			g.setColor(new Color(226, 39, 45));
		}
		g.fillOval(250, 14, 50, 50);
	}
	
	// sets up actual board squares (WHY IS THIS SO COMPLICATED)
	private void setupBoard(Graphics g, int cX, int cY) {
		// creates entire back of board as brown
		g.setColor(new Color(212, 190, 159));
		g.fillRect(cX, cY, 800, 800);
				
		// for loop adds white squares every other row
		cX += 100;
		for(int r = 0; r < 4; r++) {
			for(int c = 0; c < 4; c++) {
				g.setColor(new Color(145, 100, 69));
				g.fillRect(cX, cY, 100, 100);
				cX += 200;
			}
			cY += 200;
			cX -= 800;
		}
		// shift it slightly to do the other rows
		cX -= 100;
		cY -= 700;
		// add rest of white squares
		for(int r = 0; r < 4; r++) {
			for(int c = 0; c < 4; c++) {
				g.setColor(new Color(145, 100, 69));
				g.fillRect(cX, cY, 100, 100);
				cX += 200;
			}
			cY += 200;
			cX -= 800;
		}
	}
	
	// current turn (integer) to string
	private String tts(int i) {
		if (i == 1)
			return "Black";
		else if (i == 2)
			return "White";
		else
			return "";
	}
	
	// center string
	private void cStr(Graphics g, String text, int rectX, int rectY, int rectW, int rectH, int fontSize) {
	    FontMetrics metrics = g.getFontMetrics(new Font(g.getFont().getFontName(), Font.PLAIN, fontSize));
	    // Determine the X coordinate for the text
	    int x = rectX + (rectW - metrics.stringWidth(text)) / 2;
	    // Determine the Y coordinate for the text (note we add the ascent, as in java 2d 0 is top of the screen)
	    int y = rectY + ((rectH - metrics.getHeight()) / 2) + metrics.getAscent();
	    g.setFont(new Font(g.getFont().getFontName(), Font.PLAIN, fontSize));
	    g.drawString(text, x, y);
	}
}
