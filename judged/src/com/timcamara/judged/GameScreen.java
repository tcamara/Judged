package com.timcamara.judged;

import com.artemis.World;
import com.artemis.managers.GroupManager;
import com.artemis.managers.TagManager;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.FPSLogger;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.timcamara.judged.systems.CollisionSystem;
import com.timcamara.judged.systems.GraphicRenderSystem;
import com.timcamara.judged.systems.HereticMovementSystem;
import com.timcamara.judged.systems.HereticSpawnerSystem;
import com.timcamara.judged.systems.InputSystem;

public class GameScreen implements Screen {
	private OrthographicCamera    screen_camera;
	private OrthographicCamera    world_camera;
	private TextureAtlas          atlas;
	private World                 world;
	private GraphicRenderSystem   graphicRenderSystem;
	private HereticMovementSystem hereticMovementSystem;
	private HereticSpawnerSystem  hereticSpawnerSystem;
	private InputSystem           inputSystem;
	private CollisionSystem       collisionSystem;
	private FPSLogger             fps;
	
	public GameScreen(JudgedGame game) {
		// Load texture atlas
		if(JudgedGame.dev_mode) {
			atlas = new TextureAtlas(Gdx.files.internal("images/pack_dev.txt"));
		}
		else {
			atlas = new TextureAtlas(Gdx.files.internal("images/pack.txt"));
		}
		
		// Set up the cameras
		screen_camera = new OrthographicCamera(JudgedGame.screen_width, JudgedGame.screen_height);
		world_camera  = new OrthographicCamera(JudgedGame.world_width, JudgedGame.world_height);
		
		screen_camera.setToOrtho(false, JudgedGame.screen_width, JudgedGame.screen_height);
		world_camera.setToOrtho(false, JudgedGame.world_width, JudgedGame.world_height);
		
		screen_camera.position.set(JudgedGame.screen_width / 2, JudgedGame.screen_height / 2, 0);
		world_camera.position.set(JudgedGame.world_width / 2, JudgedGame.world_height / 2, 0);
		
		// Set up the world
		world = new World();
		graphicRenderSystem   = world.setSystem(new GraphicRenderSystem(screen_camera, world_camera), true);
		hereticMovementSystem = world.setSystem(new HereticMovementSystem(), true);
		hereticSpawnerSystem  = world.setSystem(new HereticSpawnerSystem(atlas), true);
		inputSystem           = world.setSystem(new InputSystem(screen_camera), true);
		collisionSystem       = world.setSystem(new CollisionSystem(world, game), true);
		world.initialize();
		world.setManager(new GroupManager());
		world.setManager(new TagManager());
		
		EntityFactory.createPlayer(world);
//		EntityFactory.createBackground(world, atlas, "game");
		EntityFactory.createTemple(world, atlas);
		
		if(JudgedGame.dev_mode) {
			fps = new FPSLogger();
		}
	}
	
	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0,0,0.2f,1);
	    Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
	    
	    screen_camera.update();
	    world_camera.update();
	    
	    world.setDelta(delta);
	    world.process();
	    graphicRenderSystem.process();
	    hereticMovementSystem.process();
	    hereticSpawnerSystem.process();
	    inputSystem.process();
	    collisionSystem.process();
//	    fps.log();
	}
	
	@Override
	public void resize(int width, int height) {
		screen_camera.viewportWidth = width;
		screen_camera.viewportHeight = height;
		screen_camera.update();
		
		JudgedGame.screen_width = width;
		JudgedGame.screen_height = height;
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
