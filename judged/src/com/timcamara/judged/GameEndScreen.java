package com.timcamara.judged;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;

public class GameEndScreen implements Screen {
	private Stage stage;
	private JudgedGame game;
	
	public GameEndScreen(JudgedGame game, int player_score) {
		this.game = game;
		
		stage = new Stage(JudgedGame.screen_width, JudgedGame.screen_height, true);
		Gdx.input.setInputProcessor(stage);
		
		Table table = new Table();
		table.setFillParent(true);
		stage.addActor(table);
		
		Skin button_skin = new Skin(Gdx.files.internal("ui/uiskin.json"));
		
		Label title = new Label("Your Score: " + player_score, button_skin);
		title.setPosition(JudgedGame.screen_width / 2, JudgedGame.screen_height / 2);
		stage.addActor(title);
		
		TextButton button = new TextButton("Retry", button_skin);
		button.setPosition(JudgedGame.screen_width / 2, JudgedGame.screen_height / 2 - 100);
		button.addListener(new InputListener() {
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				if(JudgedGame.dev_mode) {
					System.out.println("retry button pressed");
				}
				startGame();
				return true;
			}
		});
		stage.addActor(button);
		
		
		
//		EntityFactory.createText(world, "Your Score: " + score, (JudgedGame.screen_width / 2) - 100, (JudgedGame.screen_height / 2) + 30);
	}
	
	public void startGame() {
		game.setScreen(new GameScreen(game));
	}
	
	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0,0,0.2f,1);
	    Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
	    
	    stage.act(Gdx.graphics.getDeltaTime());
	    stage.draw();
	}
	
	@Override
	public void resize(int width, int height) {
		stage.setViewport(width, height, true);
	}
	
	@Override
	public void show() {
		
	}
	
	@Override
	public void hide() {
		
	}
	
	@Override
	public void pause() {
		
	}
	
	@Override
	public void resume() {
		
	}
	
	@Override
	public void dispose() {
		stage.dispose();
	}
}


//package com.timcamara.judged;
//
//import com.artemis.World;
//import com.badlogic.gdx.Gdx;
//import com.badlogic.gdx.Screen;
//import com.badlogic.gdx.graphics.GL10;
//import com.badlogic.gdx.graphics.OrthographicCamera;
//import com.timcamara.judged.systems.MenuInputSystem;
//import com.timcamara.judged.systems.MenuRenderSystem;
//
//public class GameEndScreen implements Screen {
//	private OrthographicCamera    camera;
//	private World                 world;
//	private MenuRenderSystem      menuRenderSystem;
//	private MenuInputSystem       menuInputSystem;
//	
//	public GameEndScreen(JudgedGame game, boolean end_of_game, int score) {
//		// Set up the camera
//		camera = new OrthographicCamera();
//		camera.setToOrtho(false, JudgedGame.screen_width, JudgedGame.screen_height);
//		
//		// Set up the world
//		world = new World();
//		menuRenderSystem   = world.setSystem(new MenuRenderSystem(camera), true);
//		menuInputSystem    = world.setSystem(new MenuInputSystem(game, camera), true);
//		world.initialize();
//		
//		// Add Entities
//		EntityFactory.createText(world, "Your Score: " + score, (JudgedGame.screen_width / 2) - 100, (JudgedGame.screen_height / 2) + 30);
////		EntityFactory.createButton(world, "Retry", (JudgedGame.screen_width / 2) - 100, (JudgedGame.screen_height / 2) + -100);
//	}
//	
//	@Override
//	public void render(float delta) {
//		Gdx.gl.glClearColor(0,0,0.2f,1);
//	    Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
//	    
//	    camera.update();
//	    
//	    world.setDelta(delta);
//	    world.process();
//	    menuRenderSystem.process();
//	    menuInputSystem.process();
//	}
//	
//	@Override
//	public void resize(int width, int height) {
//		
//	}
//	
//	@Override
//	public void show() {
//		
//	}
//	
//	@Override
//	public void hide() {
//		
//	}
//	
//	@Override
//	public void pause() {
//		
//	}
//	
//	@Override
//	public void resume() {
//		
//	}
//	
//	@Override
//	public void dispose() {
//		
//	}
//}
