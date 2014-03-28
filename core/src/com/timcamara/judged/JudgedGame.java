package com.timcamara.judged;

import java.util.ArrayList;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Json;
import com.timcamara.judged.screens.MenuScreen;

public class JudgedGame extends Game {
	public static int     screen_width  = 1280;
	public static int     screen_height = 720;
	public static boolean dev_mode      = false;
	public static int     score         = 0;
	public static int     level         = 0;
	public static ArrayList<Level> levels;
	public enum menus {
		TITLE,
		PAUSE,
		LEVEL_LOSS,
		LEVEL_WIN,
		GAME_WIN
	}
	
	@Override
	public void create () {
		Json json = new Json();
//		levels = json.fromJson(LevelList.class, Gdx.files.internal("json/endless.json")).levels;
		levels = json.fromJson(LevelList.class, Gdx.files.internal("json/levels.json")).levels;
//		levels = json.fromJson(LevelList.class, Gdx.files.internal("json/endgame.json")).levels;
		
//		ArrayList<Temple> temple_array = new ArrayList<Temple>();
//		ArrayList<Enemy>  enemy_array  = new ArrayList<Enemy>();
//		ArrayList<Level>  level_array  = new ArrayList<Level>();
//		
//		temple_array.add(new Temple(3, ((JudgedGame.world_width / 2) - 1), ((JudgedGame.world_height / 2) - 1), "shrine_large"));
//		enemy_array .add(new Enemy(1, 1, 1, 2, 1, "skeleton_priest_large"));
//		level_array .add(new Level(10, 1.008f, "gradient", temple_array, enemy_array));
//		
//		LevelList levels = new LevelList(level_array);
//		Json json = new Json();
//		System.out.println(json.prettyPrint(levels));
		
		setScreen(new MenuScreen(this, menus.TITLE));
	}
}

//1.777777777777778
// 16 x 9
// 1280 x 720 - gs3, n2
// 640 x 360

// 1.6
// 16 x 10
// 2560 x 1600 - n10
// 1280 x 800
// 640 x 400