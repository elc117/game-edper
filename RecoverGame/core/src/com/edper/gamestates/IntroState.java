package com.edper.gamestates;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.edper.main.RecoverGame;

public class IntroState extends State{
	private Texture background;
	private String[] introMessages;
	private String[] skipMessages;
	
	private BitmapFont font;
	private GlyphLayout layout;
	private int currentMessage = 0;
	
	public IntroState(GameStateManager gsm) {
		super(gsm);
		background = new Texture("Screens/introScreen.png");
		introMessages = new String[] {
				"No ano de 20XX...",
				"A cura para o Covid-19\nfoi descoberta!",
				"Mas agora o desafio\ne' outro:",
				"Garantir que toda populacao\ndo planeta receba essa cura!"
			};
		skipMessages = new String[] {
				"ENTER - Pular Tudo",
				"Z - Avancar"
		};
		font = new BitmapFont(Gdx.files.internal("Fonts/PixelFont32.fnt"));
		layout = new GlyphLayout();
		font.setColor(Color.WHITE);
	}

	@Override
	public void handleInput() {
		if(Gdx.input.isKeyJustPressed(Input.Keys.Z))
			currentMessage+=1;
		
		if(currentMessage == introMessages.length || Gdx.input.isKeyJustPressed(Input.Keys.ENTER))
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
        for(int index = 0; index < introMessages.length; index++) {
        	layout.setText(font, introMessages[index]);
        	if(currentMessage == index) 
        		font.draw(sb, introMessages[index], Gdx.graphics.getWidth()/2 - layout.width/2, Gdx.graphics.getHeight()/2 + layout.height/2 + 15);	
        }
        for(int index = 0; index < skipMessages.length; index++) {
        	layout.setText(font, skipMessages[index]);
        	font.draw(sb, skipMessages[index], 10, index*layout.height + 20*index + 20);	
        }
        sb.end();
	}

}
