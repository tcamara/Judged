package com.timcamara.judged.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.timcamara.judged.EntityFactory;
import com.timcamara.judged.JudgedGame;

public class MenuScreen implements Screen {
	private Stage      stage;
	private JudgedGame game;
	
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
		
		stage = new Stage(JudgedGame.screen_width, JudgedGame.screen_height, true);
		Gdx.input.setInputProcessor(stage);
		
		stage.addActor(EntityFactory.createTable());
		
		if(menu == JudgedGame.menus.TITLE) {
			stage.addActor(EntityFactory.createLabel("Judged", JudgedGame.screen_width / 2, JudgedGame.screen_height / 2 + 100));
			stage.addActor(EntityFactory.createButton("Start", JudgedGame.screen_width / 2, JudgedGame.screen_height / 2, new InputListener() {
					public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
						if(JudgedGame.dev_mode) {
							System.out.println("start button pressed");
						}
						
						startGame();
						return true;
					}
				}
			));
		}
		else if(menu == JudgedGame.menus.PAUSE) {
			stage.addActor(EntityFactory.createLabel("Paused", JudgedGame.screen_width / 2, JudgedGame.screen_height / 2 + 100));
			stage.addActor(EntityFactory.createButton("Continue", JudgedGame.screen_width / 2, JudgedGame.screen_height / 2 - 100, new InputListener() {
					public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
						if(JudgedGame.dev_mode) {
							System.out.println("continue button pressed");
						}
						
						// TODO: stuff
						return true;
					}
				}
			));
			stage.addActor(EntityFactory.createButton("Quit", JudgedGame.screen_width / 2, JudgedGame.screen_height / 2 - 200, new InputListener() {
					public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
						if(JudgedGame.dev_mode) {
							System.out.println("quit button pressed");
						}
						
						exitGame();
						return true;
					}
				}
			));
		}
		else if(menu == JudgedGame.menus.LEVEL_LOSS) {
			stage.addActor(EntityFactory.createLabel("Level Failed", JudgedGame.screen_width / 2, JudgedGame.screen_height / 2 + 100));
			stage.addActor(EntityFactory.createLabel("Your Score: " + JudgedGame.score, JudgedGame.screen_width / 2, JudgedGame.screen_height / 2));
			stage.addActor(EntityFactory.createButton("Retry", JudgedGame.screen_width / 2, JudgedGame.screen_height / 2 - 100, new InputListener() {
					public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
						if(JudgedGame.dev_mode) {
							System.out.println("retry button pressed");
						}
						
						startGame();
						return true;
					}
				}
			));
			stage.addActor(EntityFactory.createButton("Quit", JudgedGame.screen_width / 2, JudgedGame.screen_height / 2 - 200, new InputListener() {
					public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
						if(JudgedGame.dev_mode) {
							System.out.println("quit button pressed");
						}
						
						exitGame();
						return true;
					}
				}
			));
		}
		else if(menu == JudgedGame.menus.LEVEL_WIN) {
			stage.addActor(EntityFactory.createLabel("Level Complete", JudgedGame.screen_width / 2, JudgedGame.screen_height / 2 + 100));
			stage.addActor(EntityFactory.createLabel("Your Score: " + JudgedGame.score, JudgedGame.screen_width / 2, JudgedGame.screen_height / 2));
			stage.addActor(EntityFactory.createButton("Continue", JudgedGame.screen_width / 2, JudgedGame.screen_height / 2 - 100, new InputListener() {
					public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
						if(JudgedGame.dev_mode) {
							System.out.println("continue button pressed");
						}
						
						continueGame();
						return true;
					}
				}
			));
			stage.addActor(EntityFactory.createButton("Quit", JudgedGame.screen_width / 2, JudgedGame.screen_height / 2 - 200, new InputListener() {
					public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
						if(JudgedGame.dev_mode) {
							System.out.println("quit button pressed");
						}
						
						exitGame();
						return true;
					}
				}
			));
		}
		else if(menu == JudgedGame.menus.GAME_WIN) {
			stage.addActor(EntityFactory.createLabel("You Win", JudgedGame.screen_width / 2, JudgedGame.screen_height / 2 + 100));
			stage.addActor(EntityFactory.createLabel("Your Score: " + JudgedGame.score, JudgedGame.screen_width / 2, JudgedGame.screen_height / 2));
			stage.addActor(EntityFactory.createButton("Play Again", JudgedGame.screen_width / 2, JudgedGame.screen_height / 2 - 100, new InputListener() {
					public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
						if(JudgedGame.dev_mode) {
							System.out.println("play again button pressed");
						}
						
						startGame();
						return true;
					}
				}
			));
			stage.addActor(EntityFactory.createButton("Quit", JudgedGame.screen_width / 2, JudgedGame.screen_height / 2 - 200, new InputListener() {
					public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
						if(JudgedGame.dev_mode) {
							System.out.println("quit button pressed");
						}
						
						exitGame();
						return true;
					}
				}
			));
		}
		else {
			System.out.println("Incorrect value passed to MenuScreen: " + menu.toString());
			exitGame();
		}
	}
	
	public void startGame() {
		JudgedGame.level = 0;
		game.setScreen(new GameScreen(game));
	}
	
	public void continueGame() {
		game.setScreen(new GameScreen(game));
	}
	
	public void exitGame() {
		Gdx.app.exit();
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
