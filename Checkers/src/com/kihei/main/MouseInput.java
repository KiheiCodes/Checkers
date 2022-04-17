package com.kihei.main;

import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class MouseInput extends MouseAdapter {
	
	public static int sR = -1;
	public static int sC = -1;
	
	public void mousePressed(MouseEvent e) {
		int mx = e.getX();
		int my = e.getY();
		
		// the misery begins.. only if there isn't a winner yet
		if (Display.winner == 0) {
			int cX = ((984 - 800) / 2);
			int cY = ((961 - 800) / 2);
			
			// there is too much.. in this nested loop (have fun)
			for(int r = 0; r < 8; r++) {
				for(int c = 0; c < 8; c++) {
					if (mouseOver(mx, my, cX, cY, 100, 100)) { // it found the piece based on mouse position
						System.out.println("(" + r + ", " + c + "): " + Display.board[r][c]);
						if (sR == -1) { // there is nothing selected
							if (Display.board[r][c] != 0) { // it is a piece
								sR = r;
								sC = c;
							}
						} else { // there is a piece selected
							/* check if the clicked pos (r and c) is a legal move based on the selected piece pos */
							if (Display.ct == 1 && Display.board[sR][sC] == 1) { // black piece
								if (r == sR - 1 && ((c == sC + 1) || (c == sC - 1))) { // is it up and diagonal to the selected?
									if (Display.board[r][c] == 0) {
										Display.board[sR][sC] = 0;
										kingB(r, c);
										swapTurn();
									}
								} else if (r == sR - 2) { // is it 2 spaces up? (checking for captures)
									if (c == sC + 2 && isW(Display.board[sR - 1][sC + 1]) && Display.board[r][c] == 0) { // is it 2 spaces right and opponent piece is in between and ending piece is available?
										Display.board[sR][sC] = 0;
										Display.board[sR - 1][sC + 1] = 0;
										kingB(r, c);
										swapTurn();
									} else if (c == sC - 2 && isW(Display.board[sR - 1][sC - 1]) && Display.board[r][c] == 0) { // is it 2 spaces left and opponent piece is in between and ending piece is available?
										Display.board[sR][sC] = 0;
										Display.board[sR - 1][sC - 1] = 0;
										kingB(r, c);
										swapTurn();
									}
								}
								
							} else if (Display.ct == 2 && Display.board[sR][sC] == 2) { // white piece
								if (r == sR + 1 && ((c == sC + 1) || (c == sC - 1))) { // is it down and diagonal to the selected?
									if (Display.board[r][c] == 0) {
										Display.board[sR][sC] = 0;
										Display.board[r][c] = 2;
										kingW(r, c);
										swapTurn();
									}
								} else if (r == sR + 2) { // is it 2 spaces down? (checking for captures)
									if (c == sC + 2 && isB(Display.board[sR + 1][sC + 1]) && Display.board[r][c] == 0) { // is it 2 spaces right, opponent piece is in between, and ending piece is available?
										Display.board[sR][sC] = 0;
										Display.board[sR + 1][sC + 1] = 0;
										Display.board[r][c] = 2;
										kingW(r, c);
										swapTurn();
									} else if (c == sC - 2 && isB(Display.board[sR + 1][sC - 1]) && Display.board[r][c] == 0) { // is it 2 spaces left, opponent piece is in between, and ending piece is available?
										Display.board[sR][sC] = 0;
										Display.board[sR + 1][sC - 1] = 0;
										Display.board[r][c] = 2;
										kingW(r, c);
										swapTurn();
									}
								}
							} else if (Display.ct == 1 && Display.board[sR][sC] == 3) { // black king
								if ((r == sR + 1 || r == sR - 1) && ((c == sC + 1) || (c == sC - 1))) { // is it diagonal to the selected?
									if (Display.board[r][c] == 0) {
										Display.board[sR][sC] = 0;
										Display.board[r][c] = 3;
										swapTurn();
									}
								} else if (r == sR - 2) { // is it 2 spaces up? (checking for captures)
									if (c == sC + 2 && isW(Display.board[sR - 1][sC + 1]) && Display.board[r][c] == 0) { // is it 2 spaces right, opponent piece is in between, and ending piece is available?
										Display.board[sR][sC] = 0;
										Display.board[sR - 1][sC + 1] = 0;
										Display.board[r][c] = 3;
										swapTurn();
									} else if (c == sC - 2 && isW(Display.board[sR - 1][sC - 1]) && Display.board[r][c] == 0) { // is it 2 spaces left, opponent piece is in between, and ending piece is available?
										Display.board[sR][sC] = 0;
										Display.board[sR - 1][sC - 1] = 0;
										Display.board[r][c] = 3;
										swapTurn();
									}
								} else if (r == sR + 2) { // is it 2 spaces down? (checking for captures)
									if (c == sC + 2 && isW(Display.board[sR + 1][sC + 1]) && Display.board[r][c] == 0) { // is it 2 spaces right, opponent piece is in between, and ending piece is available?
										Display.board[sR][sC] = 0;
										Display.board[sR + 1][sC + 1] = 0;
										Display.board[r][c] = 3;
										swapTurn();
									} else if (c == sC - 2 && isW(Display.board[sR + 1][sC - 1]) && Display.board[r][c] == 0) { // is it 2 spaces left, opponent piece is in between, and ending piece is available?
										Display.board[sR][sC] = 0;
										Display.board[sR + 1][sC - 1] = 0;
										Display.board[r][c] = 3;
										swapTurn();
									}
								}
								
							} else if (Display.ct == 2 && Display.board[sR][sC] == 4) { // white king
								if ((r == sR + 1 || r == sR - 1) && ((c == sC + 1) || (c == sC - 1))) { // is it diagonal to the selected?
									if (Display.board[r][c] == 0) {
										Display.board[sR][sC] = 0;
										Display.board[r][c] = 4;
										swapTurn();
									}
								} else if (r == sR + 2) { // is it 2 spaces down? (checking for captures)
									if (c == sC + 2 && isB(Display.board[sR + 1][sC + 1]) && Display.board[r][c] == 0) { // is it 2 spaces right and opponent piece is in between and ending piece is available?
										Display.board[sR][sC] = 0;
										Display.board[sR + 1][sC + 1] = 0;
										Display.board[r][c] = 4;
										swapTurn();
									} else if (c == sC - 2 && isB(Display.board[sR + 1][sC - 1]) && Display.board[r][c] == 0) { // is it 2 spaces left and opponent piece is in between and ending piece is available?
										Display.board[sR][sC] = 0;
										Display.board[sR + 1][sC - 1] = 0;
										Display.board[r][c] = 4;
										swapTurn();
									}
								} else if (r == sR - 2) { // is it 2 spaces up? (checking for captures)
									if (c == sC + 2 && isB(Display.board[sR - 1][sC + 1]) && Display.board[r][c] == 0) { // is it 2 spaces right and opponent piece is in between and ending piece is available?
										Display.board[sR][sC] = 0;
										Display.board[sR - 1][sC + 1] = 0;
										Display.board[r][c] = 4;
										swapTurn();
									} else if (c == sC - 2 && isB(Display.board[sR - 1][sC - 1]) && Display.board[r][c] == 0) { // is it 2 spaces left and opponent piece is in between and ending piece is available?
										Display.board[sR][sC] = 0;
										Display.board[sR - 1][sC - 1] = 0;
										Display.board[r][c] = 4;
										swapTurn();
									}
								}
							}
							
							sR = -1; sC = -1; // unselect piece
						}
					}
					cX += 100;
				}
				
				cY += 100;
				cX -= 800;
			}
			
			
		} else {
			if (mouseOver(mx, my, (984 - 200) / 2, (961 - 75) / 2 + 413, 200, 75)) {
				Display.board = new int[8][8];
				//Game.setup();
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
				Display.winner = 0;
				Display.ct = 1;
				System.out.println("yes");
			}
		}
	}
	
	public static boolean isW(int p) {
		if (p == 2 || p == 4)
			return true;
		else
			return false;
	}
	
	public static boolean isB(int p) {
		if (p == 1 || p == 3)
			return true;
		else
			return false;
	}
	
	private void kingB(int r, int c) {
		if (r == 0)
			Display.board[r][c] = 3;
		else
			Display.board[r][c] = 1;
	}
	
	private void kingW(int r, int c) {
		if (r == 7)
			Display.board[r][c] = 4;
		else
			Display.board[r][c] = 2;
	}
	
	private boolean mouseOver(int mx, int my, int x, int y, int width, int height) {
		if (mx > x && mx < x + width && my > y && my < y + height)
			return true;
		else
			return false;
	}
	
	private void swapTurn() {
		if (Display.ct == 1)
			Display.ct = 2;
		else
			Display.ct = 1;
	}
	
	public void tick() {
		
	}
	
	public void render(Graphics g) {
		
	}
	
}
