package com.edper.gamestates;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.edper.main.RecoverGame;

public class GameOverState extends State{
	private Texture background;
	
	private BitmapFont font;
	private GlyphLayout layout;
	
	private String title;
	private String scoreMessage;
	private String scoreString;
	private String highscoreMessage;
	private int highscore;
	private String highscoreString;
	private String returnMessage;
	
	private boolean isNewRecord;
	
	private Preferences prefs;
	
	public GameOverState(GameStateManager gsm, int score) {
		super(gsm);
		background = new Texture("Screens/gameOverScreen2.png");
		title = "Fim de Jogo!";
		returnMessage = "ENTER / ESC - Confirmar";
		scoreMessage = "Pontuacao:";
		
		isNewRecord = false;
		highscoreMessage = "Recorde:";
		prefs = Gdx.app.getPreferences("recovergame");
		highscore = prefs.getInteger("highscore", 0);
		if (score > highscore) {
			isNewRecord = true;
			prefs.putInteger("highscore", score);
			highscore = prefs.getInteger("highscore", 0);
			prefs.flush();
		}
		
		font = new BitmapFont(Gdx.files.internal("Fonts/PixelFont32.fnt"));
		font.setColor(Color.WHITE);
		layout = new GlyphLayout();
		
		scoreString = String.valueOf(score);
		highscoreString = String.valueOf(highscore);
	}

	@Override
	public void handleInput() {
		if(Gdx.input.isKeyJustPressed(Input.Keys.ENTER) || Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE))
			gsm.set(new MenuState(gsm));
	}

	@Override
	public void update(float dt) {
		handleInput();
	}

	@Override
	public void render(SpriteBatch sb) {
		sb.begin();
        sb.draw(background, 0, 0, RecoverGame.WIDTH, RecoverGame.HEIGHT);
        
        layout.setText(font, title);
        font.draw(sb, title, RecoverGame.WIDTH/2 - layout.width/2, RecoverGame.HEIGHT/2 - layout.height/2 + 165);
    	
    	layout.setText(font, scoreMessage);
    	font.draw(sb, scoreMessage, RecoverGame.WIDTH/2 - layout.width/2, RecoverGame.HEIGHT/2 - layout.height/2 + 65);
    	layout.setText(font, scoreString);
    	font.draw(sb, scoreString, RecoverGame.WIDTH/2 - layout.width/2, RecoverGame.HEIGHT/2 - layout.height/2 + 15);
    	
    	if(isNewRecord) {
    		highscoreMessage = "Novo Recorde!";
    		font.setColor(Color.YELLOW);
    	}
    	layout.setText(font, highscoreMessage);
		font.draw(sb, highscoreMessage, RecoverGame.WIDTH/2 - layout.width/2, RecoverGame.HEIGHT/2 - layout.height/2 - 70);
    	
    	layout.setText(font, highscoreString);
    	font.draw(sb, highscoreString, RecoverGame.WIDTH/2 - layout.width/2, RecoverGame.HEIGHT/2 - layout.height/2 - 120);
    	
    	font.setColor(Color.WHITE);
    	
        layout.setText(font, returnMessage);
        font.draw(sb, returnMessage, 10, 20);	
        
        sb.end();
		
	}

}
