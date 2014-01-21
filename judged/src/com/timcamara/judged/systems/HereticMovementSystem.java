package com.timcamara.judged.systems;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.annotations.Mapper;
import com.artemis.managers.GroupManager;
import com.artemis.systems.EntityProcessingSystem;
import com.artemis.utils.ImmutableBag;
import com.badlogic.gdx.math.Vector2;
import com.timcamara.judged.components.Heretic;
import com.timcamara.judged.components.Position;
import com.timcamara.judged.components.Velocity;

public class HereticMovementSystem extends EntityProcessingSystem {
	@Mapper ComponentMapper<Position> pm;
	@Mapper ComponentMapper<Velocity> vm;
	
	@SuppressWarnings("unchecked")
	public HereticMovementSystem() {
		super(Aspect.getAspectForAll(Heretic.class, Position.class, Velocity.class));
	}
	
	@Override
	protected void process(Entity e) {
		Position position = pm.get(e);
		Velocity velocity = vm.get(e);
		
		
		// Find closest temple to this point
		ImmutableBag<Entity> bag = world.getManager(GroupManager.class).getEntities("TEMPLES");
		Vector2 closest_temple   = new Vector2();
		Vector2 current_position = new Vector2(position.x, position.y);
		double closest_distance  = -1;
		float temple_distance    = -1;
		Entity temple            = null;
		Position temple_position = null;
		
		for(int i = 0; i < bag.size(); i++) {
			temple = bag.get(i);
			temple_position = pm.get(temple);
			temple_distance = current_position.dst2(temple_position.x, temple_position.y);
			
			if(closest_distance == -1 || (closest_distance > temple_distance)) { 
				closest_temple.set(temple_position.x, temple_position.y);
				closest_distance = temple_distance;
			}
		}
		
		// Set velocity to aim for closest temple
		velocity.set(Heretic.speed, (float)Math.toDegrees(Math.atan2(closest_temple.y - current_position.y, closest_temple.x - current_position.x)) - 90);
		
		// Calculate new position based on velocity
		position.add(velocity.speed.x * world.delta, velocity.speed.y * world.delta);
	}
}
