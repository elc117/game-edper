package com.edper.figures;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;

public class HalfPill {
	private static final int BLUE = 1;
	private static final int YELLOW = 2;
	private static final int RED = 3;
	private static final int LENGTH= 28;
	
	private int x;
	private int y;
	private int color;

	private Texture texture;
	private Rectangle bounds;
	
	private boolean isFalling;
	
	public HalfPill(int initialX, int initialY, int color) {
		this.x = initialX;
		this.y = initialY;
		this.color = color;
		this.isFalling = true;
		
		if(this.color == BLUE) 
			this.texture = new Texture("Sprites/HalfPills/blueHalfPill.png");
		if(this.color == YELLOW) 
			this.texture = new Texture("Sprites/HalfPills/yellowHalfPill.png");
		if(this.color == RED) 
			this.texture = new Texture("Sprites/HalfPills/redHalfPill.png");
		this.bounds = new Rectangle(0, 0, texture.getWidth()/2, texture.getHeight());
	}
	
	public void dropHalfPill(int[][] matrix) {
		if(this.y + 1 < 16 && matrix[this.y + 1][this.x] == 0) {
			this.y = this.y + 1;
		} else {
			this.isFalling = false;
		}
	}

	public int getX() {
		return this.x;
	}
	
	public int getY() {
		return this.y;
	}
	
	public boolean getIsFalling() {
		return this.isFalling;
	}
	
	public int getColor() {
		return this.color;
	}
	
	public void setX(int n) {
		this.x = n;
	}
	
	public void setY(int n) {
		this.y = n;
	}
	
	public void setColor(int n) {
		this.color = n;
	}
	
	public Texture getTexture() {
		return this.texture;
	}
	
	public int getLength() {
		return this.LENGTH;
	}
	
}
