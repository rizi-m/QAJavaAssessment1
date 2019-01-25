package game.entities;

import game.Coordinate;

public class HealthPotion extends GameEntity {

	private HealthPotionStrength strength;
	
	public HealthPotion(HealthPotionStrength strength, Coordinate position) {
		super(strength.toString(), 1001, 1, position);
		this.strength = strength;
	}
	
	public int getHealthPointsHealed() {
		switch (strength) {
		case WEAK: return 5;
		case MILD: return 20;
		case STRONG: return 75;
		}
		return 0;
	}
	
	@Override
	public String toString() {
		return "H";
	}

}

 