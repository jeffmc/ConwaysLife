package mcmillan.jeff.conwayslife;

import java.awt.BorderLayout;

import javax.swing.JFrame;

public class Main {
	
	public static JFrame window;
	
	public static void main(String args[]) {
		beginWindow();
		
		Grid grid = new Grid(25,25);
		window.add(grid, BorderLayout.CENTER);
		ControlPanel controlPanel = new ControlPanel(grid);
		window.add(controlPanel, BorderLayout.PAGE_END);

		finishWindow();
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
