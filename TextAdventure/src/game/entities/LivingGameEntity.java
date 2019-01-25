package game.entities;

import java.util.Random;

import game.Coordinate;

public abstract class LivingGameEntity extends GameEntity {

	private int health;
	
	public LivingGameEntity(String name, int ID, int weight, Coordinate position, int health) {
		super(name, ID, weight, position);
		this.health = health;
	}
	
	public void takeDamage(int damage) {
		health -= damage;
	}
	
	public int getDamageDealt() {
		if (isDead()) {
			return 0;
		}
		Random random = new Random();
		return 1 + random.nextInt(5);
	}
	
	public boolean isDead() {
		return health < 1;
	}
	
	public void healUsing(HealthPotion potion) {
		health += potion.getHealthPointsHealed();
	}
	
	public int getHealth() {
		return health;
	}
	
	

}
