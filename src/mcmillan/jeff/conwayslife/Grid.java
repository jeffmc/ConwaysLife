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
	
	public void stepField() {
		boolean[][] nextField = new boolean[width][height];
		for (int y=0;y<height;y++) {
			for (int x=0;x<width;x++) {
				nextField[x][y] = stepCell(x,y);
			}
		}
		field = nextField;
	}
	
	 private boolean stepCell(int x, int y) { // Returns boolean for state of this cell in next frame.
		    // If alive
		    //   0 or 1 neighbors - dies of loneliness
		    //   2 or 3 neighbors - survives to next round
		    //   4 or more neighbors - dies of overcrowding
		    // If dead
		    //   3 neighbors - becomes populated
		 
		    boolean xp = x < width - 1,
		      xn = x > 0; // x +/- available
		    boolean yp = y < height - 1,
		      yn = y > 0; // y +/- available
		    int neighbors = 0;
		    if (xp && yp)
		      neighbors += field[x + 1][y + 1] ? 1 : 0;
		    if (xn && yp)
		      neighbors += field[x - 1][y + 1] ? 1 : 0;
		    if (xp && yn)
		      neighbors += field[x + 1][y - 1] ? 1 : 0;
		    if (xn && yn)
		      neighbors += field[x - 1][y - 1] ? 1 : 0;
		    if (xp) neighbors += field[x + 1][y] ? 1 : 0;
		    if (xn) neighbors += field[x - 1][y] ? 1 : 0;
		    if (yp) neighbors += field[x][y + 1] ? 1 : 0;
		    if (yn) neighbors += field[x][y - 1] ? 1 : 0;
		    boolean nextState = false;
		    if (field[x][y]) {
		      if (neighbors >= 2 && neighbors <= 3) {
		        nextState = true;
		      }
		    } else {
		      if (neighbors == 3) nextState = true;
		    }
		    return nextState;
		  };
	
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
