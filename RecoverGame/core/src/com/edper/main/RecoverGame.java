package com.edper.main;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.edper.gamestates.GameStateManager;
import com.edper.gamestates.IntroState;
import com.edper.gamestates.MenuState;
import com.edper.gamestates.PlayState;

public class RecoverGame extends ApplicationAdapter {
	public static final int WIDTH = 1000;
    public static final int HEIGHT = 600;
    public static final float SCALE = 0.5f;
    public static final String TITLE = "Recover";

	private SpriteBatch spriteBatch;
    private GameStateManager gameStateManager;
	
	@Override
	public void create () {
		spriteBatch = new SpriteBatch();
        gameStateManager = new GameStateManager();
        gameStateManager.push(new IntroState(gameStateManager));

        Gdx.gl.glClearColor(1, 0, 0, 1);
	}

	@Override
	public void render () {
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		gameStateManager.update(Gdx.graphics.getDeltaTime());
        gameStateManager.render(spriteBatch);
	}
}
