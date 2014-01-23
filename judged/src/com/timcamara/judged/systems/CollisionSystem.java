package com.timcamara.judged.systems;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.World;
import com.artemis.annotations.Mapper;
import com.artemis.managers.GroupManager;
import com.artemis.managers.TagManager;
import com.artemis.systems.EntityProcessingSystem;
import com.artemis.utils.ImmutableBag;
import com.badlogic.gdx.math.Vector3;
import com.timcamara.judged.GameEndScreen;
import com.timcamara.judged.JudgedGame;
import com.timcamara.judged.components.Damage;
import com.timcamara.judged.components.Graphic;
import com.timcamara.judged.components.Health;
import com.timcamara.judged.components.Heretic;
import com.timcamara.judged.components.Player;
import com.timcamara.judged.components.Position;
import com.timcamara.judged.components.Velocity;
import com.timcamara.judged.components.Worth;

public class CollisionSystem extends EntityProcessingSystem {
	@Mapper ComponentMapper<Damage>  dm;
	@Mapper ComponentMapper<Health>  hm;
	@Mapper ComponentMapper<Graphic> gm;
	@Mapper ComponentMapper<Player>  pm;
	@Mapper ComponentMapper<Worth>   wm;
	
	private JudgedGame  game;
	private World       world;
	private InputSystem is;
	private Player      player;
	
	@SuppressWarnings("unchecked")
	public CollisionSystem(World world, JudgedGame game) {
		super(Aspect.getAspectForAll(Heretic.class, Position.class, Graphic.class, Velocity.class));
		
		this.game = game;
		
		// Get InputSystem
		this.world = world;
		is = world.getSystem(InputSystem.class);
	}
	
	@Override
	protected void process(Entity e) {
		player = pm.get(world.getManager(TagManager.class).getEntity("PLAYER"));
		
		// Check for collisions with touch event (e.g., hit by player)
		if(is.is_touched && pointCollisionExists(gm.get(e), is.touch)) {
			hereticPlayerCollision(e);
		}
		
		// Check for collisions with temples
		ImmutableBag<Entity> bag = world.getManager(GroupManager.class).getEntities("TEMPLES");
		for(int i = 0; i < bag.size(); i++) {
			Entity temple = bag.get(i);
			if(collisionExists(gm.get(e), gm.get(temple))) {
				hereticTempleCollision(e, temple);
			}
		}
	}
	
	////////////////////////
	// Collision Checking //
	////////////////////////
	
	private boolean pointCollisionExists(Graphic g, Vector3 v) {
		return g.getBounds().contains(v.x, v.y);
	}
	
	private boolean collisionExists(Graphic g1, Graphic g2) {
		return g1.getBounds().overlaps(g2.getBounds());
	}
	
	////////////////////////
	// Collision Handling //
	////////////////////////
	
	private void hereticPlayerCollision(Entity heretic) {
		Health heretic_health  = hm.get(heretic);
		Worth worth = wm.get(heretic);
		
		// Deal damage to the heretic
		if(heretic_health.hit()) {
			// Heretic is dead
			heretic.deleteFromWorld();
			player.change_score(worth.value);
			if(JudgedGame.dev_mode) {
				System.out.println("Score: " + player.score);
			}
		}
	}
	
	private void hereticTempleCollision(Entity heretic, Entity temple) {
		Damage heretic_damage = dm.get(heretic);
		Health temple_health  = hm.get(temple);
		Worth worth = wm.get(temple);
		
		// Deal damage to the temple
		if(temple_health.hit(heretic_damage.amount)) {
			// Temple is destroyed
			temple.deleteFromWorld();
			
			if(JudgedGame.dev_mode) {
				System.out.println("Score: " + player.score);
			}
			
			game.setScreen(new GameEndScreen(game, player.score));
		}
		
		// Heretic is dead
		heretic.deleteFromWorld();
	}
	
}
