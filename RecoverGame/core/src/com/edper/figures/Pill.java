package com.edper.figures;

import java.util.ArrayList;
import java.util.Random;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.edper.handlers.Animation;

public class Pill {
	private static final int BLUE = 1;
	private static final int YELLOW = 2;
	private static final int RED = 3;
	
	private static final int WIDTH = 56;
	private static final int HEIGHT = 28;
	
	private int initialX1 = 3;
	private int initialX2 = 4;
	private int initialY1 = 0;
	private int initialY2 = 0;
	
	private int x1;
	private int y1;
	private int x2;
	private int y2;
	
	private int color1;
	private int color2;
	private int tempColor;
	
	private boolean isFalling;
	private boolean isHorizontal;
	private boolean isHalfPill;
	
	private Texture texture;
	private Rectangle bounds;
	
	private int rotation;
	
	Random rand = new Random();
	
	public Pill() {
		this.isHorizontal = true;
		this.isHalfPill = false;
		this.isFalling = true;
		this.x1 = initialX1;
		this.x2 = initialX2;
		this.y1 = initialY1;
		this.y2 = initialY2;
		this.color1 = genPillColor();
		this.color2 = genPillColor();
		
		if(this.color1 == BLUE && this.color2 == BLUE)
			this.texture = new Texture("Sprites/Pills/bluePill.png");
		if(this.color1 == BLUE && this.color2 == YELLOW)
			this.texture = new Texture("Sprites/Pills/blueYellowPill.png");
		if(this.color1 == BLUE && this.color2 == RED)
			this.texture = new Texture("Sprites/Pills/blueRedPill.png");
		if(this.color1 == YELLOW && this.color2 == YELLOW)
			this.texture = new Texture("Sprites/Pills/yellowPill.png");
		if(this.color1 == YELLOW && this.color2 == BLUE)
			this.texture = new Texture("Sprites/Pills/yellowBluePill.png");
		if(this.color1 == YELLOW && this.color2 == RED)
			this.texture = new Texture("Sprites/Pills/yellowRedPill.png");
		if(this.color1 == RED && this.color2 == RED)
			this.texture = new Texture("Sprites/Pills/redPill.png");
		if(this.color1 == RED && this.color2 == BLUE)
			this.texture = new Texture("Sprites/Pills/redBluePill.png");
		if(this.color1 == RED && this.color2 == YELLOW)
			this.texture = new Texture("Sprites/Pills/redYellowPill.png");
		
		this.bounds = new Rectangle(0, 0, texture.getWidth()/2, texture.getHeight());
		
		this.rotation = 0;
	}

	public int genPillColor() {
		return rand.nextInt(3) + 1;
	}
	
	public void dropPill(int[][] matrix) {
		this.isFalling = true; 
		if(this.isHorizontal) {
			if(this.y1 + 1 < 16 && this.y2 + 1 < 16 && matrix[this.y1 + 1][this.x1] == 0 && matrix[this.y2 + 1][this.x2] == 0) {
				this.isFalling = true;
				this.y1 = this.y1 + 1;
				this.y2 = this.y2 + 1;
			}
			else {
				this.isFalling = false;
			}
		} else {
			if(this.y2 + 1 < 16 && matrix[this.y2 + 1][this.x2] == 0) {
				this.isFalling = true;
				this.y1 = this.y1 + 1;
				this.y2 = this.y2 + 1;
			}
			else {
				this.isFalling = false;
			}
		}
	}
	
	public void rotateRight(int[][] matrix) {	
		if(this.isHorizontal) {
			if(this.x2-1 >= 0 && this.y1-1 >= 0 && matrix[this.y1 - 1][this.x1] == 0) {
				this.x2 = this.x2-1;
				this.y1 = this.y1-1;
				this.isHorizontal = false;
				this.rotation += 1;
				if(this.rotation > 3)
					this.rotation = 0;
			}
		} else {
			if(this.x2+1 < 8 && this.y1+1 < 16 && matrix[this.y2][this.x2 + 1] == 0) {
				this.x2 = this.x2+1;
				this.y1 = this.y1+1;
				tempColor = this.color1;
				this.color1 = this.color2;
				this.color2 = tempColor;
				this.isHorizontal = true;
				this.rotation += 1;
				if(this.rotation > 3)
					this.rotation = 0;
			}
		}
	}
	
	public void rotateLeft(int[][] matrix) {
		if(this.isHorizontal) {
			if(this.x2-1 >= 0 && this.y1-1 >= 0 && matrix[this.y1 - 1][this.x1] == 0) {
				this.x2 = this.x2-1;
				this.y1 = this.y1-1;
				tempColor = this.color1;
				this.color1 = this.color2;
				this.color2 = tempColor;
				this.isHorizontal = false;
				this.rotation -= 1;
				if(this.rotation < 0)
					this.rotation = 3;
			}
		} else {
			if(this.x2+1 < 8 && this.y1+1 < 16 && matrix[this.y2][this.x2 + 1] == 0) {
				this.x2 = this.x2+1;
				this.y1 = this.y1+1;
				this.isHorizontal = true;
				this.rotation -= 1;
				if(this.rotation < 0)
					this.rotation = 3;
			}
		}
	}
	
	public void moveRight(int[][] matrix) {
		if(this.isHorizontal) {
			if(this.x1+1 < 8 && this.x2+1 < 8 && matrix[this.y2][this.x2 + 1] == 0) {
				this.x1 = this.x1+1;
				this.x2 = this.x2+1;
			}
		}
		else {
			if(this.x1+1 < 8 && this.x2+1 < 8 && matrix[this.y2][this.x2 + 1] == 0 && matrix[this.y1][this.x1+ 1] == 0) {
				this.x1 = this.x1+1;
				this.x2 = this.x2+1;
			}
		}
	}
	
	public void moveLeft(int[][] matrix) {
		if(this.isHorizontal) {
			if(this.x1-1 >= 0 && this.x2-1 >= 0 && matrix[this.y1][this.x1 - 1] == 0) {
				this.x1 = this.x1-1;
				this.x2 = this.x2-1;
			}
		}
		else {
			if(this.x1-1 >= 0 && this.x2-1 >= 0 && matrix[this.y1][this.x1 - 1] == 0 && matrix[this.y2][this.x2 - 1] == 0) {
				this.x1 = this.x1-1;
				this.x2 = this.x2-1;
			}
		}
	}
	
	public boolean colorsAreEqual() {
		return this.color1 == this.color2;
	}
	

	public void setX1(int x) {
		this.x1 = x;
	}
	
	public void setX2(int x) {
		this.x2 = x;
	}
	
	public void setY1(int y) {
		this.y1 = y;
	}
	
	public void setY2(int y) {
		this.y2 = y;
	}
	
	public void setColor1(int color) {
		this.color1 = color;
	}
	
	public void setColor2(int color) {
		this.color2 = color;
	}
	
	public int getX1() {
		return this.x1;
	}
	
	public int getX2() {
		return this.x2;
	}
	
	public int getY1() {
		return this.y1;
	}
	
	public int getY2() {
		return this.y2;
	}
	
	public int getColor1() {
		return this.color1;
	}
	
	public int getColor2() {
		return this.color2;
	}
	
	public boolean getIsHorizontal() {
		return this.isHorizontal;
	}
	
	public static int getWidth() {
		return WIDTH;
	}

	public static int getHeight() {
		return HEIGHT;
	}
	
	public boolean getIsFalling() {
		return this.isFalling;
	}
	
	public int getRotation() {
		return this.rotation;
	}
	
	public Texture getTexture() {
		return this.texture;
	}
}
