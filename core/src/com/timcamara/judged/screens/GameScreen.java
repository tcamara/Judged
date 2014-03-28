package com.timcamara.judged.screens;

import com.artemis.World;
import com.artemis.managers.GroupManager;
import com.artemis.managers.TagManager;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.FPSLogger;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.ParticleEffectPool;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.FitViewport;
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

public class GameScreen extends ScreenAdapter {
	private OrthographicCamera    camera;
	private FitViewport           viewport;
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
		// Grab the level
		level = JudgedGame.levels.get(JudgedGame.level);
		
		// Load texture atlas
		if(JudgedGame.dev_mode) {
			atlas = new TextureAtlas(Gdx.files.internal("images/pack_dev.txt"));
		}
		else {
			atlas = new TextureAtlas(Gdx.files.internal("images/pack.txt"));
		}
		
		// Set up the camera and viewport
		camera = new OrthographicCamera(JudgedGame.screen_width, JudgedGame.screen_height);
		camera.setToOrtho(false, JudgedGame.screen_width, JudgedGame.screen_height);
		viewport = new FitViewport(JudgedGame.screen_width, JudgedGame.screen_height, camera);
		
		// Prepare for effects
		particle_effect = EntityFactory.createParticleEffect("effects/first.p");
		particle_effect_pool = EntityFactory.createParticleEffectPool(particle_effect, 0, 70);
		
		// Prepare for UI
		stage = new Stage(viewport);
		Gdx.input.setInputProcessor(stage);
		ui_skin = new Skin(Gdx.files.internal("ui/uiskin.json"));
		
		// Set up the world and the systems
		world = new World();
		graphicRenderSystem   = world.setSystem(new GraphicRenderSystem(camera), true);
		hereticMovementSystem = world.setSystem(new HereticMovementSystem(), true);
		hereticSpawnerSystem  = world.setSystem(new HereticSpawnerSystem(atlas, level), true);
		inputSystem           = world.setSystem(new InputSystem(viewport), true);
		collisionSystem       = world.setSystem(new CollisionSystem(world, game, particle_effect_pool), true);
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
		Gdx.gl.glClearColor(0,0,0,1);
	    Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
	    
	    camera.update();
	    
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
		camera.viewportWidth = width;
		camera.viewportHeight = height;
		camera.update();
		
		viewport.update(width, height);
		stage.getViewport().update(width, height, true);
		
//		JudgedGame.screen_width = width;
//		JudgedGame.screen_height = height;
	}
	
	@Override
	public void dispose() {
		atlas.dispose();
		particle_effect.dispose();
		graphicRenderSystem.dispose();
		stage.dispose();
		ui_skin.dispose();
		world.dispose();
	}
}
