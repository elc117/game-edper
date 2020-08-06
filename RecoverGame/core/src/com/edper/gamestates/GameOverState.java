package com.edper.gamestates;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
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
	private String[] returnMessages;
	private String score;
	
	public GameOverState(GameStateManager gsm) {
		super(gsm);
		background = new Texture("Screens/gameOverScreen.png");
		title = "Fim de Jogo!";
		returnMessages = new String[]{
				"ENTER / ESC - Confirmar"
		};
		scoreMessage = "Pontuacao:";
		font = new BitmapFont(Gdx.files.internal("Fonts/PixelFont32.fnt"));
		font.setColor(Color.WHITE);
		layout = new GlyphLayout();
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
    	font.draw(sb, title, RecoverGame.WIDTH/2 - layout.width/2, RecoverGame.HEIGHT/2 - layout.height/2 + 90);
    	
    	layout.setText(font, scoreMessage);
    	font.draw(sb, scoreMessage, RecoverGame.WIDTH/2 - layout.width/2, RecoverGame.HEIGHT/2 - layout.height/2 + 30);
    	
    	BufferedReader buffRead = null;
    	score = "";
    	ArrayList<String> scoreArray = new ArrayList<String>();
    	int scoresIndex = 0;
		try {
			buffRead = new BufferedReader(new FileReader("scores.txt"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
    	try {
    		while (true) {
    			if (score != null) {
    				scoreArray.add(score);
    				scoresIndex++;

    			} else
    				break;
    			score = buffRead.readLine();
    		}
    		
		} catch (IOException e) {
			e.printStackTrace();
		}
    	try {
			buffRead.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
    	layout.setText(font, scoreArray.get(scoresIndex-1));
    	font.draw(sb, scoreArray.get(scoresIndex-1), RecoverGame.WIDTH/2 - layout.width/2, RecoverGame.HEIGHT/2 - layout.height/2 - 35);
    	
    	for(int index = 0; index < returnMessages.length; index++) {
        	layout.setText(font, returnMessages[index]);
        	font.draw(sb, returnMessages[index], 10, index*layout.height + 20*index + 20);	
        }
        sb.end();
		
	}

}
