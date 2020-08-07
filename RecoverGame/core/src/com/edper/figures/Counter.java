package com.edper.figures;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;

public class Counter {
	private static final int BLUE = 1;
	private static final int YELLOW = 2;
	private static final int RED = 3;
	
	private static final int WIDTH = 112;
	private static final int HEIGHT = 56;
	
	private int currentLevel;
	private int previousLevel;
	private float score;
	private int covidCount;
	private int nextPillColor1;
	private int nextPillColor2;
	private boolean levelStarted;
	
	private Texture texture;
	private Rectangle bounds;
	
	public Counter() {
		this.previousLevel = -1;
		this.currentLevel = 0;
		this.score = 0f;
		this.levelStarted = false;
		
		this.nextPillColor1 = 1;
		this.nextPillColor2 = 1;
		texture = new Texture("Sprites/NextPillDisplay/blueDisplay.png");
		
		
	}
	
	public void addScore(float combo) {
		this.score += 100f*combo;
	}
	
	public void updateCounter(int newCovidCount, ArrayList<Pill> list) {
		this.covidCount = newCovidCount;
		this.nextPillColor1 = list.get(list.size()-1).getColor1();
		this.nextPillColor2 = list.get(list.size()-1).getColor2();
		
		if(this.nextPillColor1 == BLUE && this.nextPillColor2 == BLUE)
			texture = new Texture("Sprites/NextPillDisplay/blueDisplay.png");
		if(this.nextPillColor1 == BLUE && this.nextPillColor2 == YELLOW)
			texture = new Texture("Sprites/NextPillDisplay/blueYellowDisplay.png");
		if(this.nextPillColor1 == BLUE && this.nextPillColor2 == RED)
			texture = new Texture("Sprites/NextPillDisplay/blueRedDisplay.png");
		if(this.nextPillColor1 == YELLOW && this.nextPillColor2 == YELLOW)
			texture = new Texture("Sprites/NextPillDisplay/yellowDisplay.png");
		if(this.nextPillColor1 == YELLOW && this.nextPillColor2 == BLUE)
			texture = new Texture("Sprites/NextPillDisplay/yellowBlueDisplay.png");
		if(this.nextPillColor1 == YELLOW && this.nextPillColor2 == RED)
			texture = new Texture("Sprites/NextPillDisplay/yellowRedDisplay.png");
		if(this.nextPillColor1 == RED && this.nextPillColor2 == RED)
			texture = new Texture("Sprites/NextPillDisplay/redDisplay.png");
		if(this.nextPillColor1 == RED && this.nextPillColor2 == BLUE)
			texture = new Texture("Sprites/NextPillDisplay/redBlueDisplay.png");
		if(this.nextPillColor1 == RED && this.nextPillColor2 == YELLOW)
			texture = new Texture("Sprites/NextPillDisplay/redYellowDisplay.png");
		
		bounds = new Rectangle(0, 0, texture.getWidth()/2, texture.getHeight());
	}
	
	public boolean levelStarted() {
		if(previousLevel+1 != currentLevel) {
			previousLevel+=1;
			return true;
		}
		return false;
	}

	public int getCurrentLevel() {
		return this.currentLevel;
	}
	
	public int getCovidCount() {
		return this.covidCount;
	}
	
	public float getScore() {
		return this.score;
	}

	public void addLevel() {
		this.currentLevel+=1;
	}

	public void setInitializeLevel(boolean started) {
		this.levelStarted = started;
	}
	
	public boolean getInitializeLevel() {
		return this.levelStarted;
	}
	
	public Texture getTexture() {
		return this.texture;
	}
	
	public int getWidth() {
		return WIDTH;
	}
	
	public int getHeight() {
		return HEIGHT;
	}
	
	public int getNextPillColor1() {
		return nextPillColor1;
	}

	public int getNextPillColor2() {
		return nextPillColor2;
	}
}
