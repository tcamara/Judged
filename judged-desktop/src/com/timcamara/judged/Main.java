package com.timcamara.judged;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;

public class Main {
	public static void main(String[] args) {
		new LwjglApplication(new JudgedGame(), "Judged", JudgedGame.screen_width, JudgedGame.screen_height, false);
	}
}
