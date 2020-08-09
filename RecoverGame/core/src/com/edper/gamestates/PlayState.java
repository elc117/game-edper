package com.edper.gamestates;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import com.edper.figures.Counter;
import com.edper.figures.Covid;
import com.edper.figures.Grid;
import com.edper.figures.Pill;
import com.edper.figures.SideCovid;
import com.edper.main.RecoverGame;

public class PlayState extends State {
	
	private Texture background;
	
	private SideCovid blueSideCovid;
	private SideCovid yellowSideCovid;
	private SideCovid redSideCovid;

	private Grid grid;
	private Counter counter;
	
	private float combo;
	
	private float fallSpeed = 0.3f;
	private float dropFaster = 0f;
	private long currentTimeMillis;
	
	private Sprite sprite;
	
	private BitmapFont font;
	private BitmapFont font26;
	private GlyphLayout layout;
	private String[] counterText = new String[] {
			"Proximo",
			"Pontos",
			"Nivel",
			"Covid"
	};
	private String currentLevel;
	private String score;
	private String covidCount;
	
	private int currentPill;
	
	public PlayState(GameStateManager gsm) {
		super(gsm);
		background = new Texture("Screens/gameScreen.png");
		
		blueSideCovid = new SideCovid(1);
		yellowSideCovid = new SideCovid(2);
		redSideCovid = new SideCovid(3);
		
		grid = new Grid();
		
		sprite = new Sprite();
		
		counter = new Counter();
		
		font = new BitmapFont(Gdx.files.internal("Fonts/PixelFont32.fnt"));
		font.setColor(Color.BLACK);
		font26 = new BitmapFont(Gdx.files.internal("Fonts/PixelFont26.fnt"));
		font26.setColor(Color.BLACK);
		layout = new GlyphLayout();
	}

	@Override
	public void handleInput() {
		if(grid.getPillList().size() >= 2) {
			if(Gdx.input.isKeyJustPressed(Input.Keys.X) && !grid.hasHalfPillFalling() && !grid.hasOtherPillsFalling())
				grid.getPillList().get(currentPill).rotateRight(grid.getMatrix());
			if(Gdx.input.isKeyJustPressed(Input.Keys.Z) && !grid.hasHalfPillFalling() && !grid.hasOtherPillsFalling())
				grid.getPillList().get(currentPill).rotateLeft(grid.getMatrix());
			if(Gdx.input.isKeyJustPressed(Input.Keys.RIGHT) && !grid.hasHalfPillFalling() && !grid.hasOtherPillsFalling() && grid.getPillList().get(currentPill).getIsFalling())
				grid.getPillList().get(currentPill).moveRight(grid.getMatrix());
			if(Gdx.input.isKeyJustPressed(Input.Keys.LEFT) && !grid.hasHalfPillFalling() && !grid.hasOtherPillsFalling() && grid.getPillList().get(currentPill).getIsFalling())
				grid.getPillList().get(currentPill).moveLeft(grid.getMatrix());
			if(Gdx.input.isKeyPressed(Input.Keys.DOWN) && !grid.hasHalfPillFalling() && !grid.hasOtherPillsFalling())
				dropFaster = 0.2f;
			else
				dropFaster = 0f;
		}
		
		if(Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE))
			gsm.set(new MenuState(gsm));
	}

	@Override
	public void update(float dt) {
		handleInput();
		
		blueSideCovid.update(dt);
		yellowSideCovid.update(dt);
		redSideCovid.update(dt);
		
		for(int i=0; i < grid.getCovidList().size(); i++)
			grid.getCovidList().get(i).update(dt);
		
		if(counter.getCurrentLevel() == 0 || grid.getCovidCount() == 0) {
			counter.addLevel();
			counter.setInitializeLevel(true);
		}
		
		
		if(counter.getInitializeLevel()) {
			grid.resetGrid(counter.getCurrentLevel());
			grid.createPill();
			counter.setInitializeLevel(false);
		}
		
		currentPill = grid.getPillList().size() - 2;
		
		
		
		if(grid.getGameOver()) {
			gsm.set(new GameOverState(gsm, (int)counter.getScore()));
		}
			
		
		long newTimeMillis = System.currentTimeMillis();
		float frameTimeSeconds = (newTimeMillis - currentTimeMillis) / 1000f;
		
		if(frameTimeSeconds >= fallSpeed - dropFaster && !grid.hasHalfPillFalling() && !grid.hasOtherPillsFalling()) {
			currentTimeMillis = newTimeMillis;
			grid.getPillList().get(currentPill).dropPill(grid.getMatrix());
			grid.updateGrid();
		}
		
		if(!grid.hasPillFalling() && !grid.hasHalfPillFalling()) {
			combo = grid.removeLine();
			counter.addScore(combo);
		}
		
		grid.detectGameOver();
		
		if(!grid.hasPillFalling() && !grid.hasHalfPillFalling())
			grid.createPill();
		
		if(frameTimeSeconds >= fallSpeed){
			currentTimeMillis = newTimeMillis;
			for(int i=0; i < grid.getHalfPillList().size(); i++) 
				grid.getHalfPillList().get(i).dropHalfPill(grid.getMatrix());
			
		}
		
		grid.updateGrid();
		
		if(frameTimeSeconds >= fallSpeed){
			for(int i = 0; i < grid.getPillList().size()-2; i++)
				grid.getPillList().get(i).dropPill(grid.getMatrix());
		}
		
		
		grid.updateGrid();
		counter.updateCounter(grid.getCovidCount(), grid.getPillList());
	}

	@Override
	public void render(SpriteBatch sb) {
		sb.begin();
        sb.draw(background, 0, 0, RecoverGame.WIDTH, RecoverGame.HEIGHT);
        
        if(grid.hasBlueCovid())
        	sb.draw(blueSideCovid.getTexture(), 840 - blueSideCovid.getLength()/2, 210 - blueSideCovid.getLength()/2,blueSideCovid.getLength(),blueSideCovid.getLength());
        if(grid.hasYellowCovid())
        	sb.draw(yellowSideCovid.getTexture(), 735 - yellowSideCovid.getLength()/2, 180 - yellowSideCovid.getLength()/2,yellowSideCovid.getLength(),yellowSideCovid.getLength());
        if(grid.hasRedCovid())
        	sb.draw(redSideCovid.getTexture(), 800 - redSideCovid.getLength()/2, 110 - redSideCovid.getLength()/2,redSideCovid.getLength(),redSideCovid.getLength());
        
        for(int i = 0; i < grid.getCovidList().size(); i++) {
        	int textureLenght = grid.getCovidList().get(i).getLength();
        	sb.draw(grid.getCovidList().get(i).getTexture(),
        			394 - textureLenght/2 + (grid.getCovidList().get(i).getX())*textureLenght,
        			72 - textureLenght/2 + (15 - grid.getCovidList().get(i).getY())*textureLenght,
        			textureLenght,textureLenght);
        }
        
        for(int i = 0; i < grid.getHalfPillList().size(); i++) {
        	int textureLenght = grid.getHalfPillList().get(i).getLength();
        	sb.draw(grid.getHalfPillList().get(i).getTexture(),
        			394 - textureLenght/2 + grid.getHalfPillList().get(i).getX()*textureLenght,
        			72 - textureLenght/2 + (15 - grid.getHalfPillList().get(i).getY())*textureLenght,
        			textureLenght,textureLenght);
        }
        
        for(int i = 0; i < grid.getPillList().size()-1; i++) {
        	int textureWidth = grid.getPillList().get(i).getWidth();
        	int textureHeight = grid.getPillList().get(i).getHeight();
        	int rotation = grid.getPillList().get(i).getRotation();
        	sprite = new Sprite(grid.getPillList().get(i).getTexture());
        	sprite.setOrigin(textureWidth/2,textureHeight/2);
        	if(rotation == 0) {
        		sprite.rotate(-90*rotation);
        		sprite.setBounds(394 - textureHeight/2 + (grid.getPillList().get(i).getX1())*textureHeight,
        				         72 - textureHeight/2 + (15-grid.getPillList().get(i).getY1())*textureHeight,
        				         textureWidth,
        				         textureHeight);
        		sprite.draw(sb);
        	}
        	if(rotation == 1) {
        		sprite.rotate(-90*rotation);
        		sprite.setBounds(394 - textureWidth/2 + (grid.getPillList().get(i).getX1())*textureHeight,
        						 72 - textureWidth/2 + (15-grid.getPillList().get(i).getY1())*textureHeight,
        				         textureWidth,
        				         textureHeight);
        		sprite.draw(sb);
        	}
        	if(rotation == 2) {
        		sprite.rotate(-90*rotation);
        		sprite.setBounds(394 - textureHeight/2 + (grid.getPillList().get(i).getX1())*textureHeight,
        				         72 - textureHeight/2 + (15-grid.getPillList().get(i).getY2())*textureHeight,
        				         textureWidth,
        				         textureHeight);
        		sprite.draw(sb);
        	}
        	if(rotation == 3) {
        		sprite.rotate(-90*rotation);
        		sprite.setBounds(394 - textureWidth/2 + (grid.getPillList().get(i).getX2())*textureHeight,
        						 72 - textureWidth/2 + (15-grid.getPillList().get(i).getY1())*textureHeight,
        				         textureWidth,
        				         textureHeight);
        		sprite.draw(sb);
        	}
        }
        
        int textureWidth = counter.getWidth();
    	int textureHeight = counter.getHeight();
    	sb.draw(counter.getTexture(),
    			765 - textureHeight/2,
    			425 - textureHeight/2,
    			textureWidth,textureHeight);
        
        layout.setText(font, counterText[0]);
    	font.draw(sb, counterText[0], 865 - layout.width, 525 - layout.height/2);	
    	font.draw(sb, counterText[1], 190 - layout.width/2, 440 - layout.height/2);
    	font.draw(sb, counterText[2], 210 - layout.width/2, 330 - layout.height/2);	
    	font.draw(sb, counterText[3], 200 - layout.width/2, 210 - layout.height/2);
    	
    	currentLevel = String.valueOf(counter.getCurrentLevel());
    	score = String.valueOf((int)counter.getScore());
    	covidCount = String.valueOf(counter.getCovidCount());
    	
    	layout.setText(font, score);
    	font.draw(sb, score, 180 - layout.width/2, 385 - layout.height/2);	
    	
    	layout.setText(font, currentLevel);
    	font.draw(sb, currentLevel, 180 - layout.width/2, 275 - layout.height/2);	
    	
    	layout.setText(font, covidCount);
    	font.draw(sb, covidCount, 180 - layout.width/2, 155 - layout.height/2);	
         	
        sb.end();
	}

}
