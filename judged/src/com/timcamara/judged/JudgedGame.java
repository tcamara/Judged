package com.timcamara.judged;

import com.badlogic.gdx.Game;

public class JudgedGame extends Game {
	public static int     screen_width  = 1280;
	public static int     screen_height = 720;
	public static boolean dev_mode      = false;
	public static int     world_width   = 16;
	public static int     world_height  = 9;
	
	// 1.777777777777778
	// 16 x 9
	// 1280 x 720 - gs3
	// 640 x 360
	
	// 1.6
	// 16 x 10
	// 2560 x 1600 - n10
	// 1280 x 800
	// 640 x 400
	
	@Override
	public void create() {
		setScreen(new TitleScreen(this));
	}
}