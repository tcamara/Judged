package com.timcamara.judged;

import java.util.ArrayList;

public class Level {
	public int goal_score;
	public String background;
	public float spawn_rate;
	public ArrayList<Temple> temples;
	public ArrayList<Enemy> enemies;
	
	public Level() {
		
	}
	
	public Level(int goal_score, float spawn_rate, String background, ArrayList<Temple> temples, ArrayList<Enemy> enemies) {
		this.goal_score = goal_score;
		this.spawn_rate = spawn_rate;
		this.background = background;
		this.temples    = temples;
		this.enemies    = enemies;
	}
}
