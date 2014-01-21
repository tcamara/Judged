package com.timcamara.judged.client;

import com.timcamara.judged.JudgedGame;
import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.backends.gwt.GwtApplication;
import com.badlogic.gdx.backends.gwt.GwtApplicationConfiguration;

public class GwtLauncher extends GwtApplication {
	@Override
	public GwtApplicationConfiguration getConfig () {
		GwtApplicationConfiguration cfg = new GwtApplicationConfiguration(JudgedGame.screen_width, JudgedGame.screen_height);
		return cfg;
	}

	@Override
	public ApplicationListener getApplicationListener () {
		return new JudgedGame();
	}
}