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
import com.badlogic.gdx.graphics.g2d.ParticleEffectPool;
import com.badlogic.gdx.math.Vector2;
import com.timcamara.judged.EntityFactory;
import com.timcamara.judged.JudgedGame;
import com.timcamara.judged.Level;
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
	
	private JudgedGame         game;
	private World              world;
	private InputSystem        inputSystem;
	private Player             player;
	private Level		       level;
	private ParticleEffectPool particle_effect_pool;
	
	@SuppressWarnings("unchecked")
	public CollisionSystem(World world, JudgedGame game, ParticleEffectPool particle_effect_pool) {
		super(Aspect.getAspectForAll(Heretic.class, Position.class, Graphic.class, Velocity.class));
		
		this.game                 = game;
		this.level                = JudgedGame.levels.get(JudgedGame.level);
		this.particle_effect_pool = particle_effect_pool;
		
		// Get InputSystem
		this.world = world;
		inputSystem = world.getSystem(InputSystem.class);
	}
	
	@Override
	protected void process(Entity e) {
		player = pm.get(world.getManager(TagManager.class).getEntity("PLAYER"));
		
		// Check for collisions with touch event (e.g., hit by player)
		if(inputSystem.is_touched && pointCollisionExists(gm.get(e), inputSystem.touch)) {
			inputSystem.is_touched = false;
			hereticPlayerCollision(e, inputSystem.touch);
		}
		
		// Get temples
		ImmutableBag<Entity> bag = world.getManager(GroupManager.class).getEntities("TEMPLES");
		
		// If no more temples left, level is lost
		if(bag.isEmpty()) {
			JudgedGame.score = player.score;
			game.setScreen(game.level_loss_screen);
		}
		
		// Check for collisions with temples
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
	
	private boolean pointCollisionExists(Graphic g, Vector2 v) {
		return g.getBounds().contains(v.x, v.y);
	}
	
	private boolean collisionExists(Graphic g1, Graphic g2) {
		return g1.getBounds().overlaps(g2.getBounds());
	}
	
	////////////////////////
	// Collision Handling //
	////////////////////////
	
	private void hereticPlayerCollision(Entity heretic, Vector2 touch) {
		Health heretic_health  = hm.get(heretic);
		Worth worth            = wm.get(heretic);
		
		// Deal damage to the heretic
		if(!heretic_health.hit()) {
			// Play death effect
			EntityFactory.createPooledEffect(world, particle_effect_pool, touch.x, touch.y);
			
			// Heretic is dead
			heretic.deleteFromWorld();
			
			// Add heretic's worth to player's score
			player.change_score(worth.value);
			
			// If we've reached the goal score, end the level
			if(player.score >= level.goal_score) {
				JudgedGame.score = player.score;
				game.setScreen(game.level_win_screen);
			}
		}
		else {
			// Play hit effect
			EntityFactory.createPooledEffect(world, particle_effect_pool, touch.x, touch.y);
		}
	}
	
	private void hereticTempleCollision(Entity heretic, Entity temple) {
		Damage heretic_damage = dm.get(heretic);
		Health temple_health  = hm.get(temple);
		
		// Deal damage to the temple
		if(!temple_health.hit(heretic_damage.amount)) {
			// Temple is destroyed
			temple.deleteFromWorld();
		}
		
		// Heretic is dead
		heretic.deleteFromWorld();
	}
	
}
