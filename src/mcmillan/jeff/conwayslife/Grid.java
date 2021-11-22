package mcmillan.jeff.conwayslife;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Random;

import javax.swing.JPanel;

// The Grid class manages all data and logic between cells/neighbors in the Game of Life field,
// stepField(), toggleCell(), and restartGrid() are all the most needed methods in this class.

@SuppressWarnings("serial")
public class Grid extends JPanel {
	public boolean[][] field; // 2-dimensional boolean array for holding the state of each cell.
	private int fieldWidth, fieldHeight; // Dimensions of the field
	
	// fieldWidth, fieldHeight, panel's preferred height in pixels, and initial generation mode.
	public Grid(int w, int h, int preferredHeightPx, GenerationMode mode) {
		super(); // Call default JPanel constructor
		
		// Set fields using parameters
		fieldWidth = w;
		fieldHeight = h;
		
		setupGUI(preferredHeightPx);
		restartGrid(mode);
	}
	
	// Generate grid based on mode and repaint
	public void restartGrid(GenerationMode mode) {
		switch (mode) {
		default:
		case Clear:
			generateClearField();
			break;
		case Random:
			generateRandomField();
			break;
		}
		repaint();
	}

	// Generates a randomized (50/50) array in field
	private void generateRandomField() {
		Random rand = new Random();
		field = new boolean[fieldWidth][fieldHeight];
		for (int y=0;y<fieldHeight;y++) {
			for (int x=0;x<fieldWidth;x++) {
				field[x][y] = rand.nextBoolean(); // TODO: Add threshold.
			}
		}
	}
	
	// Generates an empty (false) array in field
	private void generateClearField() {
		field = new boolean[fieldWidth][fieldHeight];
		for (int y=0;y<fieldHeight;y++) {
			for (int x=0;x<fieldWidth;x++) {
				field[x][y] = false;
			}
		}
	}
	
	// Determines dimensions of JPanel in pixels to maintain correct aspect ratio and adds listener for mouse events.
	private void setupGUI(int preferredHeightPx) {
		float ar = fieldWidth / fieldHeight;
		this.setPreferredSize(new Dimension((int)Math.ceil(preferredHeightPx*ar), preferredHeightPx));
		this.addMouseListener(new MouseListener() {
			@Override
			public void mouseClicked(MouseEvent e) {
				// With the functionality here, it seemed to miss some clicks.
			}
			@Override
			public void mousePressed(MouseEvent e) {
			}
			@Override
			public void mouseReleased(MouseEvent e) {
				toggleCellFromMouse(e.getX(), e.getY());
			}
			@Override
			public void mouseEntered(MouseEvent e) {
			}
			@Override
			public void mouseExited(MouseEvent e) {
			}
		});
	}
	
	// Steps all cells once and repaints panel once finished
	public void stepField() {
		boolean[][] nextField = new boolean[fieldWidth][fieldHeight];
		for (int y=0;y<fieldHeight;y++) {
			for (int x=0;x<fieldWidth;x++) {
				nextField[x][y] = stepCell(x,y);
			}
		}
		field = nextField;
		repaint();
	}
	
	// Returns future state of this cell to be set in stepField, non-destructive method.
	private boolean stepCell(int x, int y) {
	    // If alive
	    //   0 or 1 neighbors - dies of loneliness
	    //   2 or 3 neighbors - survives to next round
	    //   4 or more neighbors - dies of overcrowding
	    // If dead
	    //   3 neighbors - becomes populated
	 
	    boolean xp = x < fieldWidth - 1,
	      xn = x > 0; // x +/- available
	    boolean yp = y < fieldHeight - 1,
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
	
    // Toggles a cell at field coordinates and repaints panel
    public void toggleCell(int x, int y) {
    	if (x >= fieldWidth || y >= fieldHeight || x < 0 || y < 0) throw new IllegalArgumentException("Cell out of field bounds: [" + x + ", " + y + "]");
    	field[x][y] = !field[x][y];
    	repaint();
    }
    
    // Converts mouse coordinates to field coordinates and calls toggleCell
    public void toggleCellFromMouse(int mx, int my) {
    	toggleCell(Math.floorDiv(mx, Math.floorDiv(getWidth(), fieldWidth)),
    			Math.floorDiv(my, Math.floorDiv(getHeight(), fieldHeight)));
    }
    
    // Prints the state of field into console. (Borders only look correct with 25x? field and monospaced font. )
    @Deprecated
	public void print() {
		System.out.print("\n/-------------------------\\\n");
		for (int y=0;y<fieldHeight;y++) {
			System.out.print("|");
			for (int x=0;x<fieldWidth;x++) {
				System.out.print(field[x][y]?"#":" ");;
			}
			System.out.print("|\n");
		}
		System.out.println("\\-------------------------/\n");
		
	}
	
    // Override the default JPanel paintComponent method to draw our grid lines and cell states.
    @Override
	public void paintComponent(Graphics g) {
		
		// Get panel width and height in pixels.
		int width = getWidth();
		int height = getHeight();
		
		// Draw background
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, width, height);
		
		// Draw game components
		drawCells(g,width,height);
		drawLines(g,width,height);
//		print(); // Used in early-stages and debugging to print field state to console.
	}

    // Draw the state of each cell in field.
	private void drawCells(Graphics g, int w, int h) {
		g.setColor(Color.RED);
		int colPx = Math.floorDiv(w, fieldWidth);
		int rowPx = Math.floorDiv(h, fieldHeight);
		for (int y=0;y<fieldHeight;y++) {
			for (int x=0;x<fieldWidth;x++) {
				if (field[x][y]) {
					g.fillRect(colPx*x, rowPx*y, colPx, rowPx);
				}
			}
		}
	}

    // Draw the grid lines.
	private void drawLines(Graphics g, int w, int h) {
		g.setColor(Color.BLACK);
		int colPx = Math.floorDiv(w, fieldWidth);
		for (int x=1;x<fieldWidth;x++) {
			g.drawLine(x*colPx, 0, x*colPx, h);
		}
		int rowPx = Math.floorDiv(h, fieldHeight);
		for (int y=1;y<fieldHeight;y++) {
			g.drawLine(0, y*rowPx, w, y*rowPx);
		}
	}
	
	// Field generation modes.
	public enum GenerationMode {
		Random, Clear; // TODO: Add a threshold value to random.
	}
	
}
