package game.entities;

public enum SwampCreatureSize {
	SMALL,
	MEDIUM,
	LARGE;
	
	@Override
	public String toString() {
		if (this == SMALL) {
			return "Small Creature";
		} else if (this == MEDIUM) {
			return "Medium Creature";
		}
		else {
			return "Large Creature";
		}
	}
	
	public int getHealth() {
		if (this == SMALL) {
			return 30;
		} else if (this == MEDIUM) {
			return 50;
		}
		else {
			return 80;
		}
	}
	
	public int getWeight() {
		if (this == SMALL) {
			return 40;
		} else if (this == MEDIUM) {
			return 60;
		}
		else {
			return 70;
		}
	}
	
	
}