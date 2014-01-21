package com.timcamara.judged.components;

import com.artemis.Component;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Rectangle;

public class Graphic extends Component {
	public Sprite sprite;
	
	public Graphic(TextureAtlas.AtlasRegion region) {
		this(region, 0);
	}
	
	public Graphic(TextureAtlas.AtlasRegion region, float rotation) {
		sprite  = new Sprite(region);
		sprite.rotate(rotation);
	}
	
	public Rectangle getBounds() {
		return new Rectangle(sprite.getX(), sprite.getY(), sprite.getWidth(), sprite.getHeight());
	}
}
