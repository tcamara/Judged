package com.timcamara.judged.components;

import com.artemis.Component;
import com.artemis.Entity;
import com.badlogic.gdx.graphics.g2d.ParticleEffectPool.PooledEffect;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Rectangle;

public class Graphic extends Component {
	public Sprite       sprite;
	public PooledEffect effect;
	public types        type;
	public static enum  types {
		EFFECT,
		SPRITE
	}
	
	// Sprite Constructor
	public Graphic(TextureAtlas.AtlasRegion region) {
		type = types.SPRITE;
		
		this.sprite  = new Sprite(region);
	}
	
	// Effect Constructor
	public Graphic(PooledEffect pe) {
		type = types.EFFECT;
		
		this.effect = pe;
	}
	
	public void setPosition(float x, float y, float delta) {
		if(type == types.SPRITE) {
			this.sprite.setPosition(x, y);
		}
		else if(type == types.EFFECT) {
			effect.setPosition(x, y);
			effect.update(delta);
		}
		else {
			throw new IllegalArgumentException("Graphic.setPosition called with invalid type.");
		}
	}
	
	public void draw(SpriteBatch batch, Entity e) {
		if(type == types.SPRITE) {
			this.sprite.draw(batch);
		}
		else if(type == types.EFFECT) {
			effect.draw(batch);
			
			if(effect.isComplete()) {
				effect.free();
				e.deleteFromWorld();
			}
		}
		else {
			throw new IllegalArgumentException("Graphic.draw called with invalid type.");
		}
	}
	
	public Rectangle getBounds() {
		if(type == types.SPRITE) {
			return new Rectangle(sprite.getX(), sprite.getY(), sprite.getWidth(), sprite.getHeight());
		}
		else {
			throw new IllegalArgumentException("Don't call Graphic.getBounds for anything other than a sprite.");
		}
		
	}
}
