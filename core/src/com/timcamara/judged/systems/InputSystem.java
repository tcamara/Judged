package com.timcamara.judged.systems;

import com.artemis.Aspect;
import com.artemis.Entity;
import com.artemis.systems.EntityProcessingSystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.timcamara.judged.JudgedGame;
import com.timcamara.judged.components.Player;
import com.timcamara.judged.components.Position;

public class InputSystem extends EntityProcessingSystem implements InputProcessor {	
	public Vector2 touch      = new Vector2();
	public boolean is_touched = false;
	public Viewport viewport;
	
	@SuppressWarnings("unchecked")
	public InputSystem(Viewport viewport) {
		super(Aspect.getAspectForAll(Player.class, Position.class));
		
		// Set viewport so we can unproject
		this.viewport = viewport;
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
		if(JudgedGame.dev_mode) {
			System.out.println("TouchDown on: (" + screenX + ", " + screenY + ")");
		}
		
		touch.set(screenX, screenY);
		is_touched = true;
		
		// Unprojecting gets us the coordinates in the proper coordinate system
		viewport.unproject(touch);
		
		return false;
	}
	
	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		is_touched = false;
		return false;
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
