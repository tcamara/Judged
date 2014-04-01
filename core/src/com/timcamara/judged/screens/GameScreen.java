package com.timcamara.judged.screens;

import com.artemis.World;
import com.artemis.managers.GroupManager;
import com.artemis.managers.TagManager;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.FPSLogger;
import com.badlogic.gdx.graphics.GL20;
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

public class GameScreen extends ScreenAdapter {
	private JudgedGame            game;
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
	private AssetManager          manager;
	private String                atlas_string;
	
	public GameScreen(JudgedGame game) {
		this.game = game;
		
		manager = new AssetManager();
		
		if(JudgedGame.dev_mode) {
			fps = new FPSLogger();
		}
	}
	
	@Override
	public void show() {
		// Grab the level
		level = JudgedGame.levels.get(JudgedGame.level);
		
		// Grab the texture atlas that's appropriate (for this level/dev_mode)
		if(JudgedGame.dev_mode) {
			atlas_string = "images/" + level.atlas + "_dev.txt";
		}
		else {
			atlas_string = "images/" + level.atlas + ".txt";
		}
		
		// Load assets
		manager.load("ui/uiskin.json", Skin.class);
		manager.load("effects/first.p", ParticleEffect.class);
		manager.load(atlas_string, TextureAtlas.class);
		manager.finishLoading();
		
		atlas = manager.get(atlas_string, TextureAtlas.class);
		
		// Prepare for effects
		particle_effect = manager.get("effects/first.p", ParticleEffect.class);
		particle_effect_pool = EntityFactory.createParticleEffectPool(particle_effect, 0, 70);
		
		// Prepare for UI
		stage = new Stage(game.viewport);
		Gdx.input.setInputProcessor(stage);
		
		// Set up the world and the systems
		world = new World();
		graphicRenderSystem   = world.setSystem(new GraphicRenderSystem(game.viewport), true);
		hereticMovementSystem = world.setSystem(new HereticMovementSystem(), true);
		hereticSpawnerSystem  = world.setSystem(new HereticSpawnerSystem(atlas, level), true);
		inputSystem           = world.setSystem(new InputSystem(game.viewport), true);
		collisionSystem       = world.setSystem(new CollisionSystem(world, game, particle_effect_pool), true);
		uiSystem              = world.setSystem(new UiSystem(world, game, stage, manager.get("ui/uiskin.json", Skin.class)));
		world.initialize();
		world.setManager(new GroupManager());
		world.setManager(new TagManager());
		
		// Add level stuff
		EntityFactory.createPlayer(world);
		EntityFactory.createBackground(world, atlas, level.background);
		
		for(Temple t : level.temples) {
			EntityFactory.createTemple(world, atlas, t.image, t.position.x, t.position.y, t.health);
		}
	}
	
	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0,0,0,1);
	    Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
	    
	    game.camera.update();
	    
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
		game.camera.update();
		game.viewport.update(width, height);
		stage.getViewport().update(width, height, true);
	}
	
	@Override
	public void dispose() {
		graphicRenderSystem.dispose();
		stage.dispose();
		world.dispose();
		manager.dispose();
	}
}
