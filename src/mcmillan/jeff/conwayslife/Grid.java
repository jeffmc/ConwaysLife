package mcmillan.jeff.conwayslife;

import java.util.Random;

public class Grid {
	public boolean[][] field;
	private int width, height;
	
	public Grid(int w, int h) {
		Random rand = new Random();
		width = w;
		height = h;
		field = new boolean[width][height];
		for (int y=0;y<height;y++) {
			for (int x=0;x<width;x++) {
				field[x][y] = rand.nextBoolean();
			}
		}
	}
	
	public void print() {
		System.out.print("\n/-------------------------\\\n");
		for (int y=0;y<height;y++) {
			System.out.print("|");
			for (int x=0;x<width;x++) {
				System.out.print(field[x][y]?"#":" ");;
			}
			System.out.print("|\n");
		}
		System.out.println("\\-------------------------/\n");
		
	}
	
}
