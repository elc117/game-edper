package com.edper.gamestates;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.edper.main.RecoverGame;

public class ControlsState extends State {
	private Texture background;
	
	private BitmapFont font;
	private BitmapFont font26;
	private GlyphLayout layout;
	private String returnMessage;
	private String title;
	private String[] controlMessages;
	
	public ControlsState(GameStateManager gsm) {
		super(gsm);
		background = new Texture("Screens/controlsScreen.png");
		returnMessage = "ESC - Voltar";
		title = "Controles";
		controlMessages = new String[] {
			"Rotaciona p/ esquerda",
			"Rotaciona p/ direita",
			"Move para os lados",
			"Desce a pilula rapidamente"
		};
		font = new BitmapFont(Gdx.files.internal("Fonts/PixelFont32.fnt"));
		font.setColor(Color.WHITE);
		font26 = new BitmapFont(Gdx.files.internal("Fonts/PixelFont26.fnt"));
		font26.setColor(Color.WHITE);
		layout = new GlyphLayout();
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
        layout.setText(font, returnMessage);
    	font.draw(sb, returnMessage, 10, 20);	
    	layout.setText(font, title);
    	font.draw(sb, title, RecoverGame.WIDTH/2 - layout.width/2, RecoverGame.HEIGHT/2 + 151 - layout.height/2);	
    	
    	for(int index = 0; index < controlMessages.length; index++) {
        	layout.setText(font26, controlMessages[index]); 
        	font26.draw(sb, controlMessages[index], Gdx.graphics.getWidth()/2 - layout.width + 275, Gdx.graphics.getHeight()/2 + 85 - 74*index);	
        }
        sb.end();
	}
	
}
