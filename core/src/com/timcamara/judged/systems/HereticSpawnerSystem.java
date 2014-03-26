package com.timcamara.judged.systems;

import java.util.Random;

import com.artemis.Aspect;
import com.artemis.Entity;
import com.artemis.managers.GroupManager;
import com.artemis.systems.EntityProcessingSystem;
import com.artemis.utils.ImmutableBag;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.timcamara.judged.Enemy;
import com.timcamara.judged.EntityFactory;
import com.timcamara.judged.JudgedGame;
import com.timcamara.judged.Level;
import com.timcamara.judged.components.Player;

public class HereticSpawnerSystem extends EntityProcessingSystem {	
	private TextureAtlas         atlas;
	private ImmutableBag<Entity> heretics;
	private Random rand;
	private float offset = 1f;
	private float iterations = 0;
	private Level level;
	
	@SuppressWarnings("unchecked")
	public HereticSpawnerSystem(TextureAtlas atlas, Level level) {
		super(Aspect.getAspectForAll(Player.class));
		
		this.atlas = atlas;
		this.level = level;
		rand = new Random();
	}
	
	@Override
	protected void begin() {
		heretics = world.getManager(GroupManager.class).getEntities("HERETICS");
		iterations += .1;
	}
	
	@Override
	protected void process(Entity e) {
		double amount = Math.pow(level.spawn_rate, iterations) + offset;
		double difference = amount - heretics.size();
		
		// Determine which enemy to spawn
		int collected = 0;
		int roll = rand.nextInt(100);
		Enemy chosen = level.enemies.get(0);
		for(Enemy enemy : level.enemies) {
			collected += enemy.spawn_chance;
			if(roll <= collected) {
				chosen = enemy;
				break;
			}
		}
		
		while(difference > 1) {
			int choice = rand.nextInt(4);
			int x = -1;
			int y = -1;
			
			if(choice == 0) { // left side
				y = rand.nextInt(JudgedGame.world_height);
			}
			else if(choice == 1) { // top side
				x = rand.nextInt(JudgedGame.world_width);
				y = JudgedGame.world_height + 1;
			}
			else if(choice == 2) { // right side
				x = JudgedGame.world_width + 1;
				y = rand.nextInt(JudgedGame.world_height);
			}
			else { // bottom side
				x = rand.nextInt(JudgedGame.world_width);
			}
			
			EntityFactory.createHeretic(world, atlas, chosen.image, x, y, chosen.speed, chosen.health, chosen.damage, chosen.worth);
			
			difference--;
		}
	}
}
