package com.timcamara.judged;

import com.badlogic.gdx.math.Vector2;

public class Temple {
	public int     health;
	public Vector2 position;
	public String  image;
	
	public Temple() {
		
	}
	
	public Temple(int health, int x, int y, String image) {
		this.health = health;
		position    = new Vector2(x, y);
		this.image  = image;
	}
}
