package com.timcamara.judged;

import java.util.ArrayList;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.timcamara.judged.screens.GameScreen;
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
	public MenuScreen title_screen;
	public MenuScreen pause_screen;
	public MenuScreen level_loss_screen;
	public MenuScreen level_win_screen;
	public MenuScreen game_win_screen;
	public GameScreen game_screen;
	public OrthographicCamera camera;
	public FitViewport viewport;
	
	@Override
	public void create () {
		Json json = new Json();
//		levels = json.fromJson(LevelList.class, Gdx.files.internal("json/endless.json")).levels;
		levels = json.fromJson(LevelList.class, Gdx.files.internal("json/levels.json")).levels;
//		levels = json.fromJson(LevelList.class, Gdx.files.internal("json/endgame.json")).levels;
		
		// Initialize the camera and viewport
		camera = new OrthographicCamera(JudgedGame.screen_width, JudgedGame.screen_height);
		camera.setToOrtho(false, JudgedGame.screen_width, JudgedGame.screen_height);
		viewport = new FitViewport(JudgedGame.screen_width, JudgedGame.screen_height, camera);
		
		// Initialize screens
		title_screen      = new MenuScreen(this, viewport, menus.TITLE);
		pause_screen      = new MenuScreen(this, viewport, menus.PAUSE);
		level_loss_screen = new MenuScreen(this, viewport, menus.LEVEL_LOSS);
		level_win_screen  = new MenuScreen(this, viewport, menus.LEVEL_WIN);
		game_win_screen   = new MenuScreen(this, viewport, menus.GAME_WIN);
		game_screen       = new GameScreen(this);
		
		setScreen(title_screen);
	}
}
