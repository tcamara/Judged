package com.timcamara.judged.components;

import com.artemis.Component;

public class Believer extends Component {
	public static float speed = 30f;
	public int last_angle_change = 0;
	public int angle_changes_every = 70;
	
	public Believer() {
		
	}
	
	public Boolean can_change() {
		if(last_angle_change >= angle_changes_every) {
			last_angle_change = 0;
			return true;
		}
		else {
			last_angle_change++;
			return false;
		}
	}
	
}

