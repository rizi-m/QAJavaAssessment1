package game.entities;

public enum HealthPotionStrength {

	WEAK,
	MILD,
	STRONG;
	@Override
	public String toString() {
		if (this == WEAK) {
			return "Weak Potion";
		} else if (this == MILD) {
			return "Mild Potion";
		}
		else {
			return "Strong Potion";
		}
	}
	
	
}
