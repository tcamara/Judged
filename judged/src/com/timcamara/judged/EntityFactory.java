package com.timcamara.judged;

import com.artemis.Entity;
import com.artemis.World;
import com.artemis.managers.GroupManager;
import com.artemis.managers.TagManager;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.timcamara.judged.components.Believer;
import com.timcamara.judged.components.Damage;
import com.timcamara.judged.components.Graphic;
import com.timcamara.judged.components.Health;
import com.timcamara.judged.components.Heretic;
import com.timcamara.judged.components.Player;
import com.timcamara.judged.components.Position;
import com.timcamara.judged.components.Velocity;
import com.timcamara.judged.components.Worth;

public class EntityFactory {
	
	public static Entity createPlayer(World world) {
		Entity player = world.createEntity();
		player.addComponent(new Player());
		world.getManager(TagManager.class).register("PLAYER", player);
		player.addToWorld();
		
		return player;
	}
	
	public static Entity createTemple(World world, TextureAtlas atlas) {		
		Graphic g = new Graphic(atlas.findRegion("shrine_large"));
		
		Entity temple = world.createEntity();
		temple.addComponent(new Believer());
		temple.addComponent(g);
		temple.addComponent(new Position(((JudgedGame.world_width / 2) - 1), ((JudgedGame.world_height / 2) - 1)));
		temple.addComponent(new Health(3));
		temple.addComponent(new Worth(-1));
		world.getManager(GroupManager.class).add(temple, "TEMPLES");
		temple.addToWorld();
		
		return temple;
	}
	
	public static Entity createHeretic(World world, TextureAtlas atlas, float x, float y) {
		Entity heretic = world.createEntity();
		heretic.addComponent(new Heretic());
		heretic.addComponent(new Graphic(atlas.findRegion("skeleton_priest_large")));
		heretic.addComponent(new Position(x, y));
		heretic.addComponent(new Velocity(Heretic.speed, 0));
		heretic.addComponent(new Health(1));
		heretic.addComponent(new Damage(1));
		heretic.addComponent(new Worth(1));
		world.getManager(GroupManager.class).add(heretic, "HERETICS");
		heretic.addToWorld();
		
		return heretic;
	}
	
	public static Entity createBackground(World world, TextureAtlas atlas, String mode) {
		Entity bg = world.createEntity();
		bg.addComponent(new Graphic(atlas.findRegion("gradient"), 0));
		bg.addComponent(new Position());
		bg.addToWorld();
		
		return bg;
	}
}
