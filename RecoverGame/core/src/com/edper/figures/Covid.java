package com.edper.figures;

import java.util.Random;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.edper.handlers.Animation;

public class Covid {
	private static final int BLUE = 1;
	private static final int YELLOW = 2;
	private static final int RED = 3;
	private static final int LENGTH= 28;
	
	private int color;
	private int x;
	private int y;
	
	private int min = 6;
	private int max = 16;
	
	private Animation animation;
	private Texture texture;
	private Rectangle bounds;
	
	Random rand = new Random();
	
	public Covid(int[][] matrix, int listSize) {
		this.x = genX();
		this.y = genY();
		if(matrix[this.y][this.x] != 0) {
			this.x = genX();
			this.y = genY();
		}
		this.color = genColor(listSize);
		
		if(this.color == BLUE) 
			this.texture = new Texture("Sprites/Covid/blueCovid.png");
		if(this.color == YELLOW) 
			this.texture = new Texture("Sprites/Covid/yellowCovid.png");
		if(this.color == RED) 
			this.texture = new Texture("Sprites/Covid/redCovid.png");
		this.animation = new Animation(new TextureRegion(texture), 2, 0.5f);
		this.bounds = new Rectangle(0, 0, texture.getWidth()/2, texture.getHeight());
	}
	
	private int genColor(int listSize) {
		if(listSize == 0) return 1;
		if(listSize == 1) return 2;
		if(listSize == 2) return 3;
		return rand.nextInt(3) + 1;
	}

	private int genX() {
		return rand.nextInt(8);
	}
	
	private int genY() {
		return rand.nextInt(max - min) + min;
	}

	public int getX() {
		return this.x;
	}
	
	public int getY() {
		return this.y;
	}
	
	public int getColor() {
		return this.color;
	}
	
	public int getLength() {
		return LENGTH;
	}
	
	public void update(float dt) {
		animation.update(dt);
	}
	
	public TextureRegion getTexture() {
		return animation.getFrame();
	}
	
}
