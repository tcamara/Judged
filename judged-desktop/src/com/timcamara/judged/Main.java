package com.timcamara.judged;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

public class Main {
	public static void main(String[] args) {
		LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
		cfg.title = "Judged";
		cfg.width = JudgedGame.screen_width;
		cfg.height = JudgedGame.screen_height;
		
		new LwjglApplication(new JudgedGame(), cfg);
	}
}
