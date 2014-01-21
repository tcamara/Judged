package com.timcamara.judged.systems;

import java.util.Random;

import com.artemis.Aspect;
import com.artemis.Entity;
import com.artemis.managers.GroupManager;
import com.artemis.systems.EntityProcessingSystem;
import com.artemis.utils.ImmutableBag;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.timcamara.judged.EntityFactory;
import com.timcamara.judged.JudgedGame;
import com.timcamara.judged.components.Player;

public class HereticSpawnerSystem extends EntityProcessingSystem {	
	private TextureAtlas         atlas;
	private ImmutableBag<Entity> heretics;
	private Random rand;
	private float base = 1.008f;
	private float offset = 1f;
	private float iterations = 0;
	
	@SuppressWarnings("unchecked")
	public HereticSpawnerSystem(TextureAtlas atlas) {
		super(Aspect.getAspectForAll(Player.class));
		
		this.atlas = atlas;
		rand = new Random();
	}
	
	@Override
	protected void begin() {
		heretics = world.getManager(GroupManager.class).getEntities("HERETICS");
		iterations += .1;
	}
	
	@Override
	protected void process(Entity e) {
		double amount = Math.pow(base, iterations) + offset;
		double difference = amount - heretics.size();
		
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
//			System.out.println(x + ", " + y);
			EntityFactory.createHeretic(world, atlas, x, y);
			
			difference--;
		}
	}
}
