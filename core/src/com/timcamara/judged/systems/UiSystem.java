package com.timcamara.judged.systems; 

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.World;
import com.artemis.annotations.Mapper;
import com.artemis.systems.EntityProcessingSystem;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.timcamara.judged.EntityFactory;
import com.timcamara.judged.JudgedGame;
import com.timcamara.judged.components.Player;

public class UiSystem extends EntityProcessingSystem {
	@Mapper ComponentMapper<Player>  pm;
	
	private Label score_label;
	
	@SuppressWarnings("unchecked")
	public UiSystem(World world, JudgedGame game, Stage stage, Skin skin) {
		super(Aspect.getAspectForAll(Player.class));
		
		// Add Scene2D stuff for UI
		Table table = EntityFactory.createTable();
		table.left().top();
		stage.addActor(table);
		score_label = EntityFactory.createLabel("0", skin, table);
	}
	
	@Override
	protected void process(Entity e) {
		Player player = pm.getSafe(e);
		
		score_label.setText(String.valueOf(player.score));
	}
}