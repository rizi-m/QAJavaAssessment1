package game.entities;

import java.util.Arrays;
import java.util.List;

import game.Coordinate;

public abstract class GameEntity {
	
	private String name;
	private int ID;
	private int weight;
	private Coordinate position;
	
	public GameEntity(String name, int ID, int weight, Coordinate position) {
		this.name = name;
		this.ID = ID;
		this.weight = weight;
		this.position = new Coordinate(position);
	}
	
	public String getName() {
		return name;
	}
	
	public int getID() {
		return ID;
	}

	public int getWeight() {
		return weight;
	}

	public void setPosition(int x, int y) {
		position.setX(x);
		position.setY(y);
	}
	
	public void setPosition(Coordinate position) {
		this.position.setX(position.getX());
		this.position.setY(position.getY());
	}
	
	public Coordinate getPosition() {
		return position;
	}
	
	@Override
	public boolean equals(Object arg0) {
		if (arg0 == this) {
			return true;
		}
		try {
			GameEntity other = (GameEntity) arg0;
			return other.ID == ID && other.name.equals(name) && other.position.equals(position);
		} catch (Exception e) {}
		return false;
	}
	
	public List<Coordinate> getCoordinatesAroundSelf() {
		int x = position.getX();
		int y = position.getY();
		return Arrays.asList(new Coordinate[] {
			new Coordinate(x-1,y-1),
			new Coordinate(x, y-1),
			new Coordinate(x+1, y-1),
			new Coordinate(x-1, y),
			new Coordinate(x+1, y),
			new Coordinate(x-1, y+1),
			new Coordinate(x, y+1),
			new Coordinate(x+1, y+1)
		});
	}
	
	@Override
	public String toString() {
		return name + " at " + position;
	}
	
}
