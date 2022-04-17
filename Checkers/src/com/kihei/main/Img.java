package com.kihei.main;

import java.awt.image.BufferedImage;

public class Img {
	
	private BufferedImage img;
	
	public Img(BufferedImage crown) {
		this.img = crown;
	}
	
	public BufferedImage grabImage() {
		BufferedImage i = img.getSubimage(0, 0, 90, 90);
		return i;
	}
	
}
