package mcmillan.jeff.conwayslife;

import java.util.Scanner;

import javax.swing.JFrame;

public class Main {
	
	public static JFrame window;
	
	public static void main(String args[]) {
		beginWindow();
		
		Grid game = new Grid(25,25);
		window.add(game);
		
		finishWindow();
		
		Scanner console = new Scanner(System.in);
		while (true) { // TODO: Add GUI functionality.
			if (console.nextLine().trim().toLowerCase().length()>3) break;
			game.stepField();
		}
		System.out.println("Exited!");
		console.close();
		System.exit(0);
	}
	
	private static void beginWindow() {
		window = new JFrame("Conway's Game of Life");
		window.setResizable(false);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	private static void finishWindow() {
		window.pack();
		window.setVisible(true);
	}
}
