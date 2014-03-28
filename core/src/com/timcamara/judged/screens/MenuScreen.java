package com.timcamara.judged.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.timcamara.judged.EntityFactory;
import com.timcamara.judged.JudgedGame;

public class MenuScreen extends ScreenAdapter {
	private Stage      stage;
	private JudgedGame game;
	private Skin       button_skin;
	
	public MenuScreen(JudgedGame game, JudgedGame.menus menu) {
		this.game  = game;
		
		// If we won a level and there are no more levels left, we won the game
		// This is kinda hacky, consider making a ScoringSystem or something
		if(menu == JudgedGame.menus.LEVEL_WIN) {
			try {
				JudgedGame.levels.get(++JudgedGame.level);
			}
			catch(IndexOutOfBoundsException e) {
				menu = JudgedGame.menus.GAME_WIN;
			}
		}
		
		button_skin = new Skin(Gdx.files.internal("ui/uiskin.json"));
		stage = new Stage(new FitViewport(JudgedGame.screen_width, JudgedGame.screen_height));
		Gdx.input.setInputProcessor(stage);
		
		Table table = EntityFactory.createTable();
		stage.addActor(table);
		
		if(menu == JudgedGame.menus.TITLE) {
			EntityFactory.createLabel("Judged", button_skin, table);
			table.row();
			EntityFactory.createButton("Start", button_skin, table, new InputListener() {
					public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
						if(JudgedGame.dev_mode) {
							System.out.println("start button pressed");
						}
						
						startGame();
						return true;
					}
				}
			);
		}
		else if(menu == JudgedGame.menus.PAUSE) {
			EntityFactory.createLabel("Paused", button_skin, table);
			table.row();
			EntityFactory.createButton("Continue", button_skin, table, new InputListener() {
					public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
						if(JudgedGame.dev_mode) {
							System.out.println("continue button pressed");
						}
						
						// TODO: stuff
						return true;
					}
				}
			);
			table.row();
			EntityFactory.createButton("Quit", button_skin, table, new InputListener() {
					public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
						if(JudgedGame.dev_mode) {
							System.out.println("quit button pressed");
						}
						
						exitGame();
						return true;
					}
				}
			);
		}
		else if(menu == JudgedGame.menus.LEVEL_LOSS) {
			EntityFactory.createLabel("Level Failed", button_skin, table);
			table.row();
			EntityFactory.createLabel("Your Score: " + JudgedGame.score, button_skin, table);
			table.row();
			EntityFactory.createButton("Retry", button_skin, table, new InputListener() {
					public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
						if(JudgedGame.dev_mode) {
							System.out.println("retry button pressed");
						}
						
						startGame();
						return true;
					}
				}
			);
			table.row();
			EntityFactory.createButton("Quit", button_skin, table, new InputListener() {
					public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
						if(JudgedGame.dev_mode) {
							System.out.println("quit button pressed");
						}
						
						exitGame();
						return true;
					}
				}
			);
		}
		else if(menu == JudgedGame.menus.LEVEL_WIN) {
			EntityFactory.createLabel("Level Complete", button_skin, table);
			table.row();
			EntityFactory.createLabel("Your Score: " + JudgedGame.score, button_skin, table);
			table.row();
			EntityFactory.createButton("Continue", button_skin, table, new InputListener() {
					public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
						if(JudgedGame.dev_mode) {
							System.out.println("continue button pressed");
						}
						
						continueGame();
						return true;
					}
				}
			);
			table.row();
			EntityFactory.createButton("Quit", button_skin, table, new InputListener() {
					public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
						if(JudgedGame.dev_mode) {
							System.out.println("quit button pressed");
						}
						
						exitGame();
						return true;
					}
				}
			);
		}
		else if(menu == JudgedGame.menus.GAME_WIN) {
			EntityFactory.createLabel("You Win", button_skin, table);
			table.row();
			EntityFactory.createLabel("Your Score: " + JudgedGame.score, button_skin, table);
			table.row();
			EntityFactory.createButton("Play Again", button_skin, table, new InputListener() {
					public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
						if(JudgedGame.dev_mode) {
							System.out.println("play again button pressed");
						}
						
						startGame();
						return true;
					}
				}
			);
			table.row();
			EntityFactory.createButton("Quit", button_skin, table, new InputListener() {
					public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
						if(JudgedGame.dev_mode) {
							System.out.println("quit button pressed");
						}
						
						exitGame();
						return true;
					}
				}
			);
		}
		else {
			throw new IllegalArgumentException("Incorrect value passed to MenuScreen: " + menu.toString());
		}
	}
	
	public void startGame() {
		JudgedGame.level = 0;
		game.setScreen(new GameScreen(game));
		dispose();
	}
	
	public void continueGame() {
		game.setScreen(new GameScreen(game));
		dispose();
	}
	
	public void exitGame() {
		dispose();
		Gdx.app.exit();
	}
	
	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0,0,0,1);
	    Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
	    
	    stage.act(delta);
	    stage.draw();
	    
	    if(JudgedGame.dev_mode) {
	    	Table.drawDebug(stage);
		}
	}
	
	@Override
	public void resize(int width, int height) {
		stage.getViewport().update(width, height, true);
	}
	
	@Override
	public void dispose() {
		button_skin.dispose();
		stage.dispose();
	}
}
