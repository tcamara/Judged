package com.timcamara.judged.components;

import com.artemis.Component;

public class Position extends Component {
	public float x;
	public float y;
	public float rotation;
	
	public Position() {
		this(0, 0, 0);
	}
	
	public Position(float x, float y) {
		this(x, y, 0);
	}
	
	public Position(float x, float y, float rotation) {
		this.x = x;
		this.y = y;
		this.rotation = rotation;
	}
	
	public void addRotation(float r) {
		rotation = (rotation + r) % 360;
	}
	
	public void add(float x, float y) {
		this.x += x;
		this.y += y;
	}
	
	public void set(float x, float y) {
		this.x = x;
		this.y = y;
	}
	
	public void set(float x, float y, float r) {
		this.x = x;
		this.y = y;
		this.rotation = r;
	}
}
