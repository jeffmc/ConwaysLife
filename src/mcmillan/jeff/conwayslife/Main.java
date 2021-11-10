package mcmillan.jeff.conwayslife;

import java.util.Scanner;

public class Main {
	public static void main(String args[]) {
		Grid game = new Grid(25,25);
		game.print();
		Scanner console = new Scanner(System.in);
		while (true) { // TODO: Add GUI functionality.
			if (console.nextLine().trim().toLowerCase().length()>3) break;
			game.stepField();
			game.print();
		}
		System.out.println("Exited!");
		console.close();
		System.exit(0);
	}
}
