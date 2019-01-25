package game.entities;

import game.Coordinate;

public class SwampCreature extends LivingGameEntity {

	SwampCreatureSize size;
	
	public SwampCreature(Coordinate position, SwampCreatureSize size) {
		super(size.toString(), 2001, size.getWeight(), position, size.getHealth());
		this.size = size;
	}
	
	
	@Override
	public int getDamageDealt() {
		int baseDamage = super.getDamageDealt();
		if (size == SwampCreatureSize.SMALL) {
			return baseDamage + 5;
		} else if (size == SwampCreatureSize.MEDIUM) {
			return baseDamage + 10;
		}
		else {
			return baseDamage + 20;
		}
	}
	
	@Override
	public String toString() {
		return "M";
	}
}
