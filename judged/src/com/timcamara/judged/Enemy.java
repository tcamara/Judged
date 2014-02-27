package com.timcamara.judged;

public class Enemy {
	public int health;
	public int damage;
	public int worth;
	public float speed;
	public int spawn_chance;
	public String image;
	
	public Enemy() {
		
	}
	
	public Enemy(int health, int damage, int worth, float speed, int spawn_chance, String image) {
		this.health       = health;
		this.damage       = damage;
		this.worth        = worth;
		this.speed        = speed;
		this.spawn_chance = spawn_chance;
		this.image        = image;
	}
}
