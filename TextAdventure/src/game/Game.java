package game;

import java.util.HashMap;
import java.util.Optional;
import java.util.Scanner;

import game.entities.Player;
import game.entities.GameEntity;
import game.entities.LivingGameEntity;

public class Game {

	private Player player = new Player("Player");
	private boolean quitGame = false;
	private Scanner scanner = new Scanner(System.in);

	private HashMap<String, Command> commands = new HashMap<>();
	private World world;
	
	public Game() {
		setUpCommands();
		world = new World(player);
	}
	
	private void setUpCommands() {
		commands.put("help", this::help);
		commands.put("quit", this::quit);
		commands.put("stats", () -> player.showStats());
		commands.put("north", () -> world.movePlayerNorth());
		commands.put("south", () -> world.movePlayerSouth());
		commands.put("east", () -> world.movePlayerEast());
		commands.put("west", () -> world.movePlayerWest());
		commands.put("map", () -> System.out.println(world));
		commands.put("playeritems", () -> System.out.println(player.items));
	}
	

	
	private void help() {
		System.out.println("The valid commands are: ");
		System.out.println(commands.keySet());
	}
	
	public void play() {
		showIntroduction();
		//game loop
		while(!quitGame) {
			System.out.print("> ");
			String userInput = scanner.nextLine();
			runCommand(userInput);
			runPlayerInteractions();
		}
	}
	
	private void showIntroduction() {
		System.out.println("Grey foggy clouds float oppressively close to you,");
		System.out.println("reflected in the murky grey water which reaches up your shins.");
		System.out.println("Some black plants barely poke out of the shallow water.");
		System.out.println("Try \"north\",\"south\",\"east\", \"west\" or use \"help\" for help.");
		System.out.println("You notice a small watch-like device in your left hand. It has hands like a watch, but the hands don't seem to tell time.");
		System.out.println();
	}
	
	private void runCommand(String command) {
		if(isCommandValid(command)) {
			commands.get(command).execute();
			if(!quitGame)
				System.out.println("Dial reads '" + world.getPlayerDistanceToTreasure() + "'m");
		} else {
			System.out.println("Sorry, action not recognised! Use \"help\" to see a list of valid commands.");
		}
	}
	
	private void runPlayerInteractions() {
		if (world.hasPlayerLandedOnTreasure()) {
			gameWon();
		}
		Optional<GameEntity> entityPlayerLandedOn = world.getEntityPlayerHasLandedOn();
		Optional<LivingGameEntity> creaturePlayerIsAt = world.getCreatureAtPlayer();
		if (entityPlayerLandedOn.isPresent()) {
			GameEntity entity = entityPlayerLandedOn.get();
			//item that can be picked up
			if (entity.getID() > 1000 && entity.getID() < 2000) {
				yesNoUserInteration("You have found " + entity.getName() + "! Add to bag? (y/n)", () -> {
					world.addCurrentItemToBag();
				});
				
			}
		}
		if (creaturePlayerIsAt.isPresent()) {
			LivingGameEntity creature = creaturePlayerIsAt.get();
			yesNoUserInteration("A " + creature.getName() + " has appeard, do you want to fight it? (y/n)", () -> world.fightCurrentCreature());
		}
	}
	
	private boolean isCommandValid(String command) {
		return commands.keySet().contains(command);
	}
	
	private void quit() {
		yesNoUserInteration("Are you sure you want to quit? (y/n)", () -> {
			System.out.println("Thanks for playing! :)");
			quitGame = true;
		});
	}
	
	private void yesNoUserInteration(String question, Command onYes) {
		String input = "";
		boolean isInputValid = false;
		while(!isInputValid) {
			System.out.println(question);
			System.out.print("> ");
			input = scanner.nextLine().toLowerCase();
			if(input.equals("y")) {
				onYes.execute();
				isInputValid = true;
			} else if (input.equals("n")) {
				isInputValid = true;
			}
		}
	}
	

	private void gameWon() {
		System.out.println("You managed to get to the treasure!");
		System.out.println("Well done you have won! Thanks for playing :)");
		System.exit(0);
	}
	
	public static void gameOver(String message) {
		System.out.println("Game over! " + message);
		System.exit(0);
	}
}


interface Command {
	public void execute();
}
