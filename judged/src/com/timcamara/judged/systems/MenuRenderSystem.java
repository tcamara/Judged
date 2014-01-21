package com.timcamara.judged.systems;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.EntitySystem;
import com.artemis.annotations.Mapper;
import com.artemis.utils.ImmutableBag;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.timcamara.judged.components.Position;
import com.timcamara.judged.components.Text;

public class MenuRenderSystem extends EntitySystem {
	@Mapper ComponentMapper<Position> pm;
	@Mapper ComponentMapper<Text> tm;
	
	private OrthographicCamera camera;
	private SpriteBatch batch;
	private BitmapFont font;
	
	@SuppressWarnings("unchecked")
	public MenuRenderSystem(OrthographicCamera camera) {
		super(Aspect.getAspectForAll(Position.class, Text.class));
		this.camera = camera;
		
		font = new BitmapFont();
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
		font.setColor(1.0f, 1.0f, 1.0f, 1.0f);
		font.setScale(2);
		batch.setProjectionMatrix(camera.combined);
		batch.begin();
	}
	
	protected void process(Entity e) {
		Position position = pm.get(e);
		Text text = tm.get(e);
		
		font.draw(batch, text.str, position.x, position.y);
	}
	
	@Override
	protected void end() {
		batch.end();
	}
}
