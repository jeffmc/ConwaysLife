package mcmillan.jeff.conwayslife;

import java.awt.BorderLayout;

import javax.swing.JFrame;

import mcmillan.jeff.conwayslife.Grid.GenerationMode;

// Entry point into game. 
// Combines ControlPanel and Grid instances into a window and presents to user.

public class Main {
	
	public static JFrame window;
	
	public static void main(String args[]) {
		beginWindow();
		
		// grid manages all game logic.
		Grid grid = new Grid(30, 30, 600, GenerationMode.Clear); // JFrame size will max out at display height
		window.add(grid, BorderLayout.CENTER); // Add grid to center of JFrame
		
		// controlPanel manages all game loop logic and regenerates field.
		ControlPanel controlPanel = new ControlPanel(grid);
		window.add(controlPanel, BorderLayout.PAGE_END); // Add grid to bottom of JFrame

		finishWindow();
	}
	
	// Construct JFrame and set properties.
	private static void beginWindow() {
		window = new JFrame("Conway's Game of Life");
		window.setResizable(false);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	// Pack components and show the JFrame.
	private static void finishWindow() {
		window.pack();
		window.setVisible(true);
	}
}
