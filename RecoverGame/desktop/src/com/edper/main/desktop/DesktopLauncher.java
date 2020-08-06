package com.edper.main.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.edper.main.RecoverGame;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width = RecoverGame.WIDTH;
        config.height = RecoverGame.HEIGHT;
        config.title = RecoverGame.TITLE;
        config.resizable = false;
		new LwjglApplication(new RecoverGame(), config);	
	}
}
