package com.timcamara.judged;

import com.badlogic.gdx.Game;

public class JudgedGame extends Game {
	public static int     screen_width  = 800; //2560;
	public static int     screen_height = 480; //1600;
	public static boolean dev_mode      = false;
	public static int     world_width   = 10;
	public static int     world_height  = 6;
		
	@Override
	public void create() {
		setScreen(new GameScreen(this));
	}
}