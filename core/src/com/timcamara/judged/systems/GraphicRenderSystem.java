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
import com.timcamara.judged.components.Graphic;
import com.timcamara.judged.components.Position;

public class GraphicRenderSystem extends EntitySystem {
	@Mapper ComponentMapper<Position> pm;
	@Mapper ComponentMapper<Graphic> sm;
	
	private OrthographicCamera camera;
	private SpriteBatch batch;
	
	@SuppressWarnings("unchecked")
	public GraphicRenderSystem(OrthographicCamera camera) {
		super(Aspect.getAspectForAll(Position.class, Graphic.class));
		this.camera = camera;
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
		batch.setProjectionMatrix(camera.combined);
		batch.begin();
	}
	
	protected void process(Entity e) {
		Position position = pm.get(e);
		Graphic graphic = sm.get(e);
		
		// Set position, then call the draw method in the graphic component
		graphic.setPosition(position.x, position.y, Gdx.graphics.getDeltaTime());
		graphic.draw(batch, e);
	}
	
	@Override
	protected void end() {
		batch.end();
	}
	
	@Override
	public void dispose() {
		batch.dispose();
	}
}
