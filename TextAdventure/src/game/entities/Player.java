package game.entities;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import game.Coordinate;

public class Player extends LivingGameEntity {
	
	public List<GameEntity> items = new ArrayList<>();
	
	public List<GameEntity> getPlayerItems() {
		return items;
	}
	
	private static int baseWeight = 40;
	
	public Player(String name, Coordinate position) {
		//base player weight is 40
		super(name, 0, baseWeight, position, 100);
	}
	
	public Player(String name) {
		super(name, 0, baseWeight, new Coordinate(0,0), 100);
	}
	
	public void add(GameEntity item) {
		items.add(item);
	}
	
	@Override
	public String toString() {
		return "P";
	}
	
	
	@Override
	public int getWeight() {
		//player weight is his weight + weight of all items carried
		return super.getWeight() + items.stream()
			.mapToInt(GameEntity::getWeight)
			.sum();
	}
	
	@Override
	public int getDamageDealt() {
		int baseDamage = super.getDamageDealt();
		Random random = new Random();
		return baseDamage + random.nextInt(50);
	}
	
	public void showStats() {
		System.out.println(getName() + " has:");
		System.out.println("Health: " + getHealth());
		System.out.println("Items: " + getPlayerItems());
	}
	
	
}
