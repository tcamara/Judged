package com.timcamara.judged.components;

import com.artemis.Component;

public class Health extends Component {
	public int amount;
	
	public Health() {
		amount = 0;
	}
	
	public Health(int amount) {
		this.amount = amount;
	}
	
	public boolean is_alive() {
		return amount > 0;
	}
	
	public boolean hit() {
		amount -= 1;
		
		return is_alive();
	}
	
	public boolean hit(int damage) {
		amount -= damage;
		
		return is_alive();
	}
}
