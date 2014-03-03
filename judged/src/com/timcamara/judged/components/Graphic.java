package com.timcamara.judged.components;

import com.artemis.Component;
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
		SPRITE,
		EFFECT
	}
	
	// Sprite Constructor
	public Graphic(TextureAtlas.AtlasRegion region) {
		type = types.SPRITE;
		
		sprite  = new Sprite(region);
	}
	
	// Effect Constructor
	public Graphic(PooledEffect pe) {
		type = types.EFFECT;
		
		effect = pe;
	}
	
	public void setPosition(float x, float y) {
		if(type == types.SPRITE) {
			this.sprite.setPosition(x, y);
		}
		else if(type == types.EFFECT) {
			effect.setPosition(x, y);
		}
		else {
			throw new IllegalArgumentException("Graphic.setPosition called with invalid type.");
		}
	}
	
	public void draw(SpriteBatch batch) {
		if(type == types.SPRITE) {
			this.sprite.draw(batch);
		}
		else if(type == types.EFFECT) {
			effect.draw(batch);
			
			if(effect.isComplete()) {
				effect.free();
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
