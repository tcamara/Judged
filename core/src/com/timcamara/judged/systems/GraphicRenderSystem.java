package com.timcamara.judged.systems;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.EntitySystem;
import com.artemis.annotations.Mapper;
import com.artemis.utils.ImmutableBag;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.timcamara.judged.components.Graphic;
import com.timcamara.judged.components.Position;

public class GraphicRenderSystem extends EntitySystem {
	@Mapper ComponentMapper<Position> pm;
	@Mapper ComponentMapper<Graphic> sm;
	
	private OrthographicCamera screen_camera;
	private OrthographicCamera world_camera;
	private SpriteBatch batch;
	
	@SuppressWarnings("unchecked")
	public GraphicRenderSystem(OrthographicCamera screen_camera, OrthographicCamera world_camera) {
		super(Aspect.getAspectForAll(Position.class, Graphic.class));
		this.screen_camera = screen_camera;
		this.world_camera = world_camera;
	}
	
	// TODO: this should really be built into Artemis
	public void dispose() {
		batch.dispose();
	}
	
	@Override
	protected void initialize() {
		batch = new SpriteBatch();
	}
	
	@Override
	protected boolean checkProcessing() {
		return true;
	}

	@Override
	protected void processEntities(ImmutableBag<Entity> entities) {
		for(int i = 0; i < entities.size(); i++) {
			process(entities.get(i));
		}
	}
	
	@Override
	protected void begin() {
		batch.setProjectionMatrix(screen_camera.combined);
		batch.begin();
	}
	
	protected void process(Entity e) {
		Position position = pm.get(e);
		Graphic graphic = sm.get(e);
		
		// Translate from world coordinates to screen coordinates
		Vector3 unprojected = new Vector3(position.x, position.y, 0);
		world_camera.project(unprojected);
		
		// Set position, then call the draw method in the graphic component
		graphic.setPosition(unprojected.x, unprojected.y, Gdx.graphics.getDeltaTime());
		graphic.draw(batch, e);
	}
	
	@Override
	protected void end() {
		batch.end();
	}
}
