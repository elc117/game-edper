package com.edper.gamestates;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.edper.main.RecoverGame;

public class HighScoresState extends State {
	private Texture background;
	
	private String score;
	private String title;
	private String position;
	private String returnMessage;
	
	private BitmapFont font;
	private GlyphLayout layout;
	
	public HighScoresState(GameStateManager gsm) {
		super(gsm);
		background = new Texture("Screens/highscoresScreen.png");
		returnMessage = "ESC - Voltar";
		title = "Recordes";
		
		font = new BitmapFont(Gdx.files.internal("Fonts/PixelFont32.fnt"));
		layout = new GlyphLayout();
		font.setColor(Color.WHITE);
	}

	@Override
	public void handleInput() {
		if(Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE))
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
    	font.draw(sb, title, RecoverGame.WIDTH/2 - layout.width/2, RecoverGame.HEIGHT/2 - layout.height/2 + 200);
    	
    	layout.setText(font, returnMessage);
    	font.draw(sb, returnMessage, 10, 20);
        
//        BufferedReader buffRead = null;
//    	score = "";
//    	ArrayList<String> scoreArray = new ArrayList<String>();
//    	ArrayList<Integer> tempScoreArray = new ArrayList<Integer>();
//		try {
//			buffRead = new BufferedReader(new FileReader("scores.txt"));
//		} catch (FileNotFoundException e) {
//			e.printStackTrace();
//		}
//    	try {
//    		while (true) {
//    			if (score != null && score != "") {
//    				scoreArray.add(score);
//
//    			} else
//    				break;
//    			score = buffRead.readLine();
//    		}
//    		
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//    	try {
//			buffRead.close();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//    	
//    	for(String score: scoreArray){
//    		if(score != "")
//    			tempScoreArray.add(Integer.parseInt(score));
//		}
//    	
//    	Collections.sort(tempScoreArray);
//    	
//    	scoreArray.clear();
//    	
//    	for(Integer score: tempScoreArray){
//    		scoreArray.add(score.toString());
//		}
//    	
//    	
//    	int index = 1;
//    	while(index < 9 && index <= scoreArray.size()) {
//    		layout.setText(font, scoreArray.get(scoreArray.size() - index));
//    		font.draw(sb, scoreArray.get(scoreArray.size() - index), RecoverGame.WIDTH/2 -layout.width+ 170, 500 - layout.height/2 - 50*(index));
//    		index++;
//    	}
    	
    	for(int index = 1; index < 9 ; index ++) {
    		position = index + "-" ;
    		layout.setText(font, position);
    		font.draw(sb, position, RecoverGame.WIDTH/2 -layout.width - 120, 500 - layout.height/2 - 50*(index));
    	}
    	
        sb.end();
	}
	
}
