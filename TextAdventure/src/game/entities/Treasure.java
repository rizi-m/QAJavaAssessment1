package game.entities;

import game.Coordinate;

public class Treasure extends GameEntity {

	public Treasure(String name, Coordinate position) {
		super(name, 1000, 1, position);
	}
	
	@Override
	public String toString() {
		return "T";
	}
	
}
