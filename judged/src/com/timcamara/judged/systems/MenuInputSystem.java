package com.timcamara.judged.systems;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.annotations.Mapper;
import com.artemis.systems.EntityProcessingSystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.math.Vector3;
import com.timcamara.judged.JudgedGame;
import com.timcamara.judged.components.Button;
import com.timcamara.judged.components.Position;

public class MenuInputSystem extends EntityProcessingSystem implements InputProcessor {
	private JudgedGame game;
	private Camera     camera;
	private Vector3    click;
	
	@Mapper ComponentMapper<Button>   bm;
	@Mapper ComponentMapper<Position> pm;
	
	@SuppressWarnings("unchecked")
	public MenuInputSystem(JudgedGame game, Camera camera) {
		super(Aspect.getAspectForAll(Button.class, Position.class));
		
		this.game = game;
		this.camera = camera;
		click = new Vector3();
	}
	
	@Override
    protected void initialize() {
            Gdx.input.setInputProcessor(this);
    }
	
	@Override
	protected void process(Entity e) {
		
	}
	
	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		click.set(screenX, screenY, 0);
		camera.unproject(click);
//		game.setScreen(new GameScreen(game));
		return true;
	}
	
	@Override
	public boolean keyDown(int keycode) {
	    return false;
	}
	
	@Override
	public boolean keyUp(int keycode) {
		return false;
	}
		
	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		return false;
	}
	
	@Override
	public boolean keyTyped(char character) {
		return false;
	}
	
	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		return false;
	}
	
	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		return false;
	}
	
	@Override
	public boolean scrolled(int amount) {
		return false;
	}
}
