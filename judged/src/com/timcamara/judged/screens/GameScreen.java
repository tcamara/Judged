package com.timcamara.judged.screens;

import com.artemis.World;
import com.artemis.managers.GroupManager;
import com.artemis.managers.TagManager;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.FPSLogger;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.ParticleEffectPool;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.timcamara.judged.EntityFactory;
import com.timcamara.judged.JudgedGame;
import com.timcamara.judged.Level;
import com.timcamara.judged.Temple;
import com.timcamara.judged.systems.CollisionSystem;
import com.timcamara.judged.systems.GraphicRenderSystem;
import com.timcamara.judged.systems.HereticMovementSystem;
import com.timcamara.judged.systems.HereticSpawnerSystem;
import com.timcamara.judged.systems.InputSystem;
import com.timcamara.judged.systems.UiSystem;

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
	private UiSystem              uiSystem;
	private FPSLogger             fps;
	private Level				  level;
	private ParticleEffect        particle_effect;
	private ParticleEffectPool    particle_effect_pool;
	private Stage                 stage;
	private Skin                  ui_skin;
	
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
		world_camera  = new OrthographicCamera(JudgedGame.world_width,  JudgedGame.world_height);
		screen_camera.setToOrtho(false, JudgedGame.screen_width, JudgedGame.screen_height);
		world_camera .setToOrtho(false, JudgedGame.world_width,  JudgedGame.world_height);
		screen_camera.position.set(JudgedGame.screen_width / 2, JudgedGame.screen_height / 2, 0);
		
		// Set up the world
		level = JudgedGame.levels.get(JudgedGame.level);
		
		// Prepare for effects
		particle_effect = EntityFactory.createParticleEffect("effects/first.p");
		particle_effect_pool = EntityFactory.createParticleEffectPool(particle_effect, 0, 70);
		
		// Prepare for UI
		stage = new Stage(JudgedGame.screen_width, JudgedGame.screen_height, true);
		ui_skin = new Skin(Gdx.files.internal("ui/uiskin.json"));
		
		world = new World();
		graphicRenderSystem   = world.setSystem(new GraphicRenderSystem(screen_camera, world_camera), true);
		hereticMovementSystem = world.setSystem(new HereticMovementSystem(), true);
		hereticSpawnerSystem  = world.setSystem(new HereticSpawnerSystem(atlas, level), true);
		inputSystem           = world.setSystem(new InputSystem(screen_camera), true);
		collisionSystem       = world.setSystem(new CollisionSystem(world, game, world_camera, particle_effect_pool), true);
		uiSystem              = world.setSystem(new UiSystem(world, game, stage, ui_skin));
		world.initialize();
		world.setManager(new GroupManager());
		world.setManager(new TagManager());
		
		// Add level stuff
		EntityFactory.createPlayer(world);
		EntityFactory.createBackground(world, atlas, level.background);
		
		for(Temple t : level.temples) {
			EntityFactory.createTemple(world, atlas, t.image, t.position.x, t.position.y, t.health);
		}
		
		if(JudgedGame.dev_mode) {
			fps = new FPSLogger();
		}
	}
	
	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0,0,0.2f,1);
	    Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
	    
	    screen_camera.update();
	    world_camera.update();
	    
	    world.setDelta(delta);
	    world.process();
	    graphicRenderSystem.process();
	    hereticMovementSystem.process();
	    hereticSpawnerSystem.process();
	    inputSystem.process();
	    collisionSystem.process();
	    uiSystem.process();
	    
	    stage.act();
	    stage.draw();
	    
	    if(JudgedGame.dev_mode) {
	    	fps.log();
	    	Table.drawDebug(stage);
	    }
	}
	
	@Override
	public void resize(int width, int height) {
		screen_camera.viewportWidth = width;
		screen_camera.viewportHeight = height;
		screen_camera.update();
		stage.setViewport(width, height, true);
		
		JudgedGame.screen_width = width;
		JudgedGame.screen_height = height;
	}
	
	@Override
	public void show() {
		
	}
	
	@Override
	public void hide() {
		dispose();
	}
	
	@Override
	public void pause() {
		
	}
	
	@Override
	public void resume() {
		
	}
	
	@Override
	public void dispose() {
		atlas.dispose();
		particle_effect.dispose();
		graphicRenderSystem.dispose();
		stage.dispose();
		ui_skin.dispose();
	}
}
