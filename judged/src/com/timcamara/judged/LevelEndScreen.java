package com.timcamara.judged;

import com.artemis.World;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.timcamara.judged.systems.MenuInputSystem;
import com.timcamara.judged.systems.MenuRenderSystem;

public class LevelEndScreen implements Screen {
	private OrthographicCamera    camera;
	private World                 world;
	private MenuRenderSystem      menuRenderSystem;
	private MenuInputSystem       menuInputSystem;
	
	public LevelEndScreen(JudgedGame game, boolean end_of_game, int score) {
		// Set up the camera
		camera = new OrthographicCamera();
		camera.setToOrtho(false, JudgedGame.screen_width, JudgedGame.screen_height);
		
		// Set up the world
		world = new World();
		menuRenderSystem   = world.setSystem(new MenuRenderSystem(camera), true);
		menuInputSystem    = world.setSystem(new MenuInputSystem(game, camera), true);
		world.initialize();
		
		// Add Entities
		EntityFactory.createText(world, "Your Score: " + score, (JudgedGame.screen_width / 2) - 100, (JudgedGame.screen_height / 2) + 30);
//		EntityFactory.createButton(world, "Retry", (JudgedGame.screen_width / 2) - 100, (JudgedGame.screen_height / 2) + -100);
	}
	
	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0,0,0.2f,1);
	    Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
	    
	    camera.update();
	    
	    world.setDelta(delta);
	    world.process();
	    menuRenderSystem.process();
	    menuInputSystem.process();
	}
	
	@Override
	public void resize(int width, int height) {
		
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
		
	}
}
