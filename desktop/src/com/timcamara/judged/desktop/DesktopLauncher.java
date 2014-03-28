package com.timcamara.judged.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.timcamara.judged.JudgedGame;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		
		config.width  = JudgedGame.screen_width;
		config.height = JudgedGame.screen_height;
		
		new LwjglApplication(new JudgedGame(), config);
	}
}
