package game;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.Scanner;
import java.util.function.Function;

import game.entities.Player;
import game.entities.SwampCreature;
import game.entities.SwampCreatureSize;
import game.entities.Treasure;
import game.entities.GameEntity;
import game.entities.HealthPotion;
import game.entities.HealthPotionStrength;
import game.entities.LivingGameEntity;

public class World {
	
	private Player player;
	private Cell[][] worldCells = new Cell[31][31];
	private Treasure treasure;
	private List<GameEntity> worldItems = new ArrayList<>();
	private List<LivingGameEntity> creatures = new ArrayList<>();
	
	public World(Player player) {
		for(int i = 0; i < worldCells.length; ++ i) {
			for(int j = 0; j < worldCells[0].length; ++ j) {
				worldCells[j][i] = new Cell(i, j);
			}
		}
		add(player);
		setUpTreasure();
	}
	
	
	private void add(Player player) {
		this.player = player;
		movePlayertoCentre();
	}
	
	private void setUpTreasure() {
		Coordinate treasurePositon;
		Random random = new Random();
		do {
			int treasureXPosition = random.nextInt(worldCells.length);
			int treasureYPosition = random.nextInt(worldCells.length);
			treasurePositon = new Coordinate(treasureXPosition, treasureYPosition);
		} while(player.getPosition().equals(treasurePositon));
		
		treasure = new Treasure("Treasure", treasurePositon);
		worldItems.add(treasure);
	}
	
	private Cell getCentreCell() {
		int x = worldCells.length / 2;
		return worldCells[x][x];
	}
	
	private void movePlayertoCentre() {
		Cell centreCell = getCentreCell();
		this.player.setPosition(centreCell.getCellCoordinate());
	}
	
	private void moveCells(Function<Cell, Cell> shifter) {
		for(int i = 0; i < worldCells.length; ++ i) {
			worldCells[i] = Arrays.asList(worldCells[i])
					.stream()
					.map(shifter)
					.toArray(Cell[]::new);
		}
		movePlayertoCentre();
		spawnRandomCreature();
		randomlyAddRandomItemAroundPlayer();
	}
	
	public void spawnRandomCreature() {
		Random random = new Random();
		int randomNumber = random.nextInt(10);
		if (randomNumber < 1) {
			randomNumber = random.nextInt(10);
			if (randomNumber < 6) {
				SwampCreature creature = new SwampCreature(player.getPosition(), SwampCreatureSize.SMALL);
				creatures.add(creature);
			} else if (randomNumber < 8) {
				SwampCreature creature = new SwampCreature(player.getPosition(), SwampCreatureSize.MEDIUM);
				creatures.add(creature);
			} else {
				SwampCreature creature = new SwampCreature(player.getPosition(), SwampCreatureSize.LARGE);
				creatures.add(creature);
			}

		}
	}
	
	public boolean hasPlayerLandedOnTreasure() {
		return getPlayerDistanceToTreasure() == 0;
	}
	
	public Optional<GameEntity> getEntityPlayerHasLandedOn() {
		Coordinate playerPosition = player.getPosition();
		return getItemAt(playerPosition);
	}
	
	public Optional<LivingGameEntity> getCreatureAtPlayer() {
		Coordinate playerPosition = player.getPosition();
		return getCreatureAt(playerPosition);
	}
	
	public void fightCurrentCreature() {
		Optional<LivingGameEntity> creatureAtPlayer = getCreatureAtPlayer();
		if (creatureAtPlayer.isPresent()) {
			LivingGameEntity creature = creatureAtPlayer.get();
			boolean fight = true;
			while(fight) {
				if(creature.isDead()) {
					fight = false;
					System.out.println("You have killed the creature! Continue searching for treasure!");
					creatures.remove(creature);
					break;
				}
				if (player.isDead()) {
					Game.gameOver("You died");
				}
				int damageFromCreature = creature.getDamageDealt();
				player.takeDamage(damageFromCreature);
				System.out.println("Taken " + damageFromCreature + " damage from creature!");
				
				String input;
				Scanner scanner = new Scanner(System.in);
				boolean validInput = false;
				while(!validInput) {
					System.out.println("What would you like to do? (run/attack/stats)");
					System.out.print("> ");
					input = scanner.nextLine();
					if (input.equals("run")) {
						runFromFight();
						System.out.println("You ran away!");
						creatures.remove(creature);
						validInput = true;
						fight = false;
					} else if (input.equals("attack")) {
						creature.takeDamage(player.getDamageDealt());
						validInput = true;
					} else if (input.equals("stats")) {
						player.showStats();
					}
				}
			}
		}
	}
	
	public void runFromFight() {
		Random random = new Random();
		int randomNumber = random.nextInt(12);
		if (randomNumber < 3) {
			movePlayerEast(); movePlayerEast(); movePlayerEast(); movePlayerEast();
		} else if (randomNumber < 6) {
			movePlayerWest(); movePlayerWest(); movePlayerWest(); movePlayerWest();
		}
		else if (randomNumber < 9) {
			movePlayerNorth(); movePlayerNorth();movePlayerNorth();movePlayerNorth();
		}
		else {
			movePlayerSouth(); movePlayerSouth();movePlayerSouth();movePlayerSouth();
		}
	}
	
	//randomly drops a random item around the player
	private void randomlyAddRandomItemAroundPlayer() {
		Random random = new Random();
		int randomNumber = random.nextInt(10);
		if (randomNumber < 3) {
			List<Coordinate> coordinatesAroundPlayer = player.getCoordinatesAroundSelf();
			int randomIndex = random.nextInt(coordinatesAroundPlayer.size());
			Coordinate randomCoordinate = coordinatesAroundPlayer.get(randomIndex);
			if (!isCellOccupied(randomCoordinate)) {
				GameEntity randomItem = generateRandomItemToDrop(randomCoordinate);
				worldItems.add(randomItem);
			}
		}
	}
	
	private Optional<GameEntity> getItemAt(Coordinate cellCoordinate) {
		return worldItems.stream()
				.filter(item -> cellCoordinate.equals(item.getPosition()))
				.findFirst();
	}
	
	private Optional<LivingGameEntity> getCreatureAt(Coordinate cellCoordinate) {
		return creatures.stream()
				.filter(item -> cellCoordinate.equals(item.getPosition()))
				.findFirst();
	}
	
	
	private boolean isCellOccupied(Coordinate cellCoordinate) {
		return worldItems.stream()
			.anyMatch(item -> cellCoordinate.equals(item.getPosition()));
	}

	private GameEntity generateRandomItemToDrop(Coordinate position) {
		Random random = new Random();
		int randomNumber = random.nextInt(100);
		if (randomNumber < 50) {
			return new HealthPotion(HealthPotionStrength.WEAK, position);
		} else if (randomNumber < 90) {
			return new HealthPotion(HealthPotionStrength.MILD, position);
		}
		return new HealthPotion(HealthPotionStrength.STRONG, position);
	}

	public void movePlayerNorth() {
		moveCells(Cell::shiftSouth);
	}
	
	public void movePlayerSouth() {
		moveCells(Cell::shiftNorth);
	}
	
	public void movePlayerEast() {
		moveCells(Cell::shiftWest);
	}
	
	public void movePlayerWest() {
		moveCells(Cell::shiftEast);
	}
	
	public double getPlayerDistanceToTreasure() {
		int xDistance = player.getPosition().getX() - treasure.getPosition().getX();
		int yDistance = player.getPosition().getY() - treasure.getPosition().getY();
		return ((double) ((int) ((Math.sqrt((xDistance * xDistance) + (yDistance * yDistance))) * 1000))) / 1000;
	}
	
	public void addCurrentItemToBag() {
		Optional<GameEntity> item = getItemAt(player.getPosition());
		if (item.isPresent()) {
			player.add(item.get());
			worldItems.remove(item.get());
		}
	}
	
	@Override
	public String toString() {
		StringBuilder stringBuilder = new StringBuilder();
		for(int i = 0; i < worldCells.length; ++ i) {
			stringBuilder.append("|");
			for(int j = 0; j < worldCells.length; ++ j) {
				Coordinate currentCoordinate = worldCells[i][j].getCellCoordinate();
				Optional<GameEntity> itemAtCurrentCell = getItemAt(currentCoordinate);
				Optional<LivingGameEntity> creatureAtCurrentCell = getCreatureAt(currentCoordinate);
				if(itemAtCurrentCell.isPresent()) {
					stringBuilder.append(itemAtCurrentCell.get()+"|");
				}
				else if (creatureAtCurrentCell.isPresent()) {
					stringBuilder.append(creatureAtCurrentCell.get()+"|");
				}
				else if (player.getPosition().equals(currentCoordinate)) {
					stringBuilder.append(player+"|");
				}
				else {
				
					stringBuilder.append(" "+"|");
				}
//				stringBuilder.append(worldCells[i][j]+"|");
//				System.out.println(treasure.getPosition());
			}
			stringBuilder.append("\n");
		}
		return stringBuilder.toString();
	}
}
