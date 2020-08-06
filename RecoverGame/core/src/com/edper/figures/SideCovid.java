package com.edper.figures;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.edper.handlers.Animation;

public class SideCovid {
	private static final int BLUE = 1;
	private static final int YELLOW = 2;
	private static final int RED = 3;
	private static final int LENGHT = 70;
	
	private Animation animation;
	private Texture texture;
	private Rectangle bounds;
	
	public SideCovid(int color) {
		if(color == BLUE)
			this.texture = new Texture("Sprites/SideAnimation/sideBlue.png");
		if(color == YELLOW)
			this.texture = new Texture("Sprites/SideAnimation/sideYellow.png");
		if(color == RED)
			this.texture = new Texture("Sprites/SideAnimation/sideRed.png");
		this.animation = new Animation(new TextureRegion(texture), 2, 0.5f);
		this.bounds = new Rectangle(0, 0, texture.getWidth()/2, texture.getHeight());
	} 
	
	public void update(float dt) {
		animation.update(dt);
	}
	
	public TextureRegion getTexture() {
		return animation.getFrame();
	}
	
	public int getLength() {
		return LENGHT;
	}
}
