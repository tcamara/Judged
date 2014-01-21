package com.timcamara.judged.components;

import com.artemis.Component;
import com.artemis.utils.TrigLUT;
import com.badlogic.gdx.math.Vector2;

public class Velocity extends Component {
	public Vector2 speed;
	public float   speed_max;
	public float   accel;
	public float   friction;
	
	public Velocity(float speed, float angle) {
		this(speed, angle, speed, speed, 1);
	}
	
	public Velocity(float speed, float angle, float speed_max, float accel, float friction) {
		float x = speed * TrigLUT.cosDeg(angle + 90);
		float y = speed * TrigLUT.sinDeg(angle + 90);
		
		this.speed = new Vector2(x, y);
		this.speed_max = speed_max;
		this.accel = accel;
		this.friction = friction;
		
		// Make sure we don't exceed limit
		limiter();
	}
	
	public void add(float angle) {
		this.speed.x += accel * TrigLUT.cosDeg(angle + 90);
		this.speed.y += accel * TrigLUT.sinDeg(angle + 90);
		
		// Make sure we don't exceed limit
		limiter();
	}
	
	public void set(float speed, float angle) {
		this.speed.x = speed * TrigLUT.cosDeg(angle + 90);
		this.speed.y = speed * TrigLUT.sinDeg(angle + 90);
		
		// Make sure we don't exceed limit
		limiter();
	}
	
	public float angle() {
		return speed.angle();
	}
	
	private void limiter() {
		if(speed.len() > speed_max) {
			speed.limit(speed_max);
		}
	}
}
