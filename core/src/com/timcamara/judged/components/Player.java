package com.timcamara.judged.components;

import com.artemis.Component;

public class Player extends Component {
	public int score = 0;
	
	public void change_score(int points) {
		score += points;
		
		if(score < 0) {
			score = 0;
		}
	}
}

