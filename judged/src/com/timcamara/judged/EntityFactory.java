package com.timcamara.judged;

import com.artemis.Entity;
import com.artemis.World;
import com.artemis.managers.GroupManager;
import com.artemis.managers.TagManager;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.timcamara.judged.components.Damage;
import com.timcamara.judged.components.Graphic;
import com.timcamara.judged.components.Health;
import com.timcamara.judged.components.Heretic;
import com.timcamara.judged.components.Player;
import com.timcamara.judged.components.Position;
import com.timcamara.judged.components.Velocity;
import com.timcamara.judged.components.Worth;

public class EntityFactory {
	private static Skin button_skin = new Skin(Gdx.files.internal("ui/uiskin.json"));
	
	public static Actor createTable() {
		Table table = new Table();
		table.setFillParent(true);
		
		return table;
	}
	
	public static Actor createLabel(String text, float x, float y) {
		Label label = new Label(text, button_skin);
		label.setPosition(x, y);
		
		return label;
	}
	
	public static Actor createButton(String text, float x, float y, InputListener inputListener) {
		TextButton button = new TextButton(text, button_skin);
		button.setPosition(x, y);
		button.addListener(inputListener);
		
		return button;
	}
	
	public static Entity createPlayer(World world) {
		Entity player = world.createEntity();
		player.addComponent(new Player());
		world.getManager(TagManager.class).register("PLAYER", player);
		player.addToWorld();
		
		return player;
	}
	
	public static Entity createTemple(World world, TextureAtlas atlas, String image, float x, float y, int health) {		
		Entity temple = world.createEntity();
		temple.addComponent(new Graphic(atlas.findRegion(image)));
		temple.addComponent(new Position(x, y));
		temple.addComponent(new Health(health));
		world.getManager(GroupManager.class).add(temple, "TEMPLES");
		temple.addToWorld();
		
		return temple;
	}
	
	public static Entity createHeretic(World world, TextureAtlas atlas, String image, float x, float y, float speed, int health, int damage, int worth) {
		Entity heretic = world.createEntity();
		heretic.addComponent(new Heretic());
		heretic.addComponent(new Graphic(atlas.findRegion(image)));
		heretic.addComponent(new Position(x, y));
		heretic.addComponent(new Velocity(speed, 0));
		heretic.addComponent(new Health(health));
		heretic.addComponent(new Damage(damage));
		heretic.addComponent(new Worth(worth));
		world.getManager(GroupManager.class).add(heretic, "HERETICS");
		heretic.addToWorld();
		
		return heretic;
	}
	
	public static Entity createBackground(World world, TextureAtlas atlas, String region_name) {
		Entity bg = world.createEntity();
		bg.addComponent(new Graphic(atlas.findRegion(region_name), 0));
		bg.addComponent(new Position(0, 0));
		bg.addToWorld();
		
		return bg;
	}
}
