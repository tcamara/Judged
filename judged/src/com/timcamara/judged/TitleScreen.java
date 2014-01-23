package com.timcamara.judged;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;

public class TitleScreen implements Screen {
	private Stage stage;
	private JudgedGame game;
	
	public TitleScreen(JudgedGame game) {
		this.game = game;
		
		stage = new Stage(JudgedGame.screen_width, JudgedGame.screen_height, true);
		Gdx.input.setInputProcessor(stage);
		
		Table table = new Table();
		table.setFillParent(true);
		stage.addActor(table);
		
		Skin button_skin = new Skin(Gdx.files.internal("ui/uiskin.json"));
		
		Label title = new Label("Judged", button_skin);
		title.setPosition(JudgedGame.screen_width / 2, JudgedGame.screen_height / 2);
		stage.addActor(title);
		
		TextButton button = new TextButton("Start", button_skin);
		button.setPosition(JudgedGame.screen_width / 2, JudgedGame.screen_height / 2 - 100);
		button.addListener(new InputListener() {
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				if(JudgedGame.dev_mode) {
					System.out.println("start button pressed");
				}
				startGame();
				return true;
			}
		});
		stage.addActor(button);
	}
	
	public void startGame() {
		game.setScreen(new GameScreen(game));
	}
	
	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0,0,0.2f,1);
	    Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
	    
	    stage.act(Gdx.graphics.getDeltaTime());
	    stage.draw();
	}
	
	@Override
	public void resize(int width, int height) {
		stage.setViewport(width, height, true);
	}
	
	@Override
	public void show() {
		
	}
	
	@Override
	public void hide() {
		
	}
	
	@Override
	public void pause() {
		
	}
	
	@Override
	public void resume() {
		
	}
	
	@Override
	public void dispose() {
		stage.dispose();
	}
}
