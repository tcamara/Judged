package com.timcamara.judged.components;

import com.artemis.Component;
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
		float x = speed * cosDeg(angle + 90);
		float y = speed * sinDeg(angle + 90);
		
		this.speed = new Vector2(x, y);
		this.speed_max = speed_max;
		this.accel = accel;
		this.friction = friction;
		
		// Make sure we don't exceed limit
		limiter();
	}
	
	public void add(float angle) {
		this.speed.x += accel * cosDeg(angle + 90);
		this.speed.y += accel * sinDeg(angle + 90);
		
		// Make sure we don't exceed limit
		limiter();
	}
	
	public void set(float speed, float angle) {
		this.speed.x = speed * cosDeg(angle + 90);
		this.speed.y = speed * sinDeg(angle + 90);
		
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
	
	
	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// Yeah, this is pretty gross to have here, but I didn't want to look up a new library when Artemis-ODB deprecated it //
	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	@SuppressWarnings("unused")
	private static final float sin(float rad) {
		return sin[(int) (rad * radToIndex) & SIN_MASK];
	}
	
	@SuppressWarnings("unused")
	private static final float cos(float rad) {
		return cos[(int) (rad * radToIndex) & SIN_MASK];
	}

	private static final float sinDeg(float deg) {
		return sin[(int) (deg * degToIndex) & SIN_MASK];
	}

	private static final float cosDeg(float deg) {
		return cos[(int) (deg * degToIndex) & SIN_MASK];
	}

	@SuppressWarnings("unused")
	private static final float RAD, DEG;
	private static final int SIN_BITS, SIN_MASK, SIN_COUNT;
	private static final float radFull, radToIndex;
	private static final float degFull, degToIndex;
	private static final float[] sin, cos;

	static {
		RAD = (float) Math.PI / 180.0f;
		DEG = 180.0f / (float) Math.PI;

		SIN_BITS = 12;
		SIN_MASK = ~(-1 << SIN_BITS);
		SIN_COUNT = SIN_MASK + 1;

		radFull = (float) (Math.PI * 2.0);
		degFull = (float) (360.0);
		radToIndex = SIN_COUNT / radFull;
		degToIndex = SIN_COUNT / degFull;

		sin = new float[SIN_COUNT];
		cos = new float[SIN_COUNT];

		for (int i = 0; i < SIN_COUNT; i++) {
			sin[i] = (float) Math.sin((i + 0.5f) / SIN_COUNT * radFull);
			cos[i] = (float) Math.cos((i + 0.5f) / SIN_COUNT * radFull);
		}
	}
}
