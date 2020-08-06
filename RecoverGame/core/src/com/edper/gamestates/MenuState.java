package com.edper.gamestates;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.edper.main.RecoverGame;
import com.badlogic.gdx.Input.Keys;

public class MenuState extends State {
	private Texture background;
	private String[] menuItems;
	private BitmapFont font;
	private GlyphLayout layout;
	private int currentItem = 0;
	
	public MenuState(GameStateManager gsm) {
		super(gsm);
		background = new Texture("Screens/mainMenu.png");
		menuItems = new String[] {
				"Jogar",
				"Recordes",
				"Ajuda",
				"Sair"
			};
		font = new BitmapFont(Gdx.files.internal("Fonts/PixelFont32.fnt"));
		layout = new GlyphLayout();
		for(String menuItem : menuItems)
			layout.setText(font, menuItem);
	}

	@Override
	public void handleInput() {
		if(Gdx.input.isKeyJustPressed(Input.Keys.UP)) {
			if(currentItem == 0)
				currentItem=menuItems.length-1;
			else
				currentItem-=1;
		}
		if(Gdx.input.isKeyJustPressed(Input.Keys.DOWN)) {
			if(currentItem == menuItems.length-1)
				currentItem=0;
			else
				currentItem+=1;
		}
		
		if(Gdx.input.isKeyJustPressed(Input.Keys.SPACE) || Gdx.input.isKeyJustPressed(Input.Keys.ENTER) || Gdx.input.isKeyJustPressed(Input.Keys.Z) || Gdx.input.isKeyJustPressed(Input.Keys.X)){
			if(currentItem == 0)
				gsm.set(new PlayState(gsm));
			if(currentItem == 1)
				gsm.set(new HighScoresState(gsm));
			if(currentItem == 2)
				gsm.set(new HelpState(gsm));
			if(currentItem == 3)
				Gdx.app.exit();
        }
		if(Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE))
			Gdx.app.exit();
	}

	@Override
	public void update(float dt) {
		handleInput();
	}

	@Override
	public void render(SpriteBatch sb) {
		sb.begin();
        sb.draw(background, 0, 0, RecoverGame.WIDTH, RecoverGame.HEIGHT);
        for(int index = 0; index < menuItems.length; index++) {
        	if(currentItem == index) 
        		font.setColor(Color.YELLOW);
        	else
        		font.setColor(Color.WHITE);
        	font.draw(sb, menuItems[index], Gdx.graphics.getWidth()/2 - layout.width, Gdx.graphics.getHeight()/2 - index*layout.height - 20*index - 50);
        }
        sb.end();
	}
	
}










