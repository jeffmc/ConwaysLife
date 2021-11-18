package mcmillan.jeff.conwayslife;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Random;

import javax.swing.JPanel;

@SuppressWarnings("serial")
public class Grid extends JPanel {
	public boolean[][] field;
	private int fieldWidth, fieldHeight;
	
	public Grid(int w, int h) {
		super();
		setupGUI();
		fieldWidth = w;
		fieldHeight = h;
		restartGrid(GenerationType.Random);
	}
	
	public void restartGrid(GenerationType mode) { // Generate grid and repaint
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
	
	private void generateRandomField() {
		Random rand = new Random();
		field = new boolean[fieldWidth][fieldHeight];
		for (int y=0;y<fieldHeight;y++) {
			for (int x=0;x<fieldWidth;x++) {
				field[x][y] = rand.nextBoolean(); // TODO: Add threshold.
			}
		}
	}
	
	private void generateClearField() {
		field = new boolean[fieldWidth][fieldHeight];
		for (int y=0;y<fieldHeight;y++) {
			for (int x=0;x<fieldWidth;x++) {
				field[x][y] = false;
			}
		}
	}
	
	private void setupGUI() {
		this.setPreferredSize(new Dimension(400, 400));
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
	
	public void stepField() { // Steps each cell and repaints panel
		boolean[][] nextField = new boolean[fieldWidth][fieldHeight];
		for (int y=0;y<fieldHeight;y++) {
			for (int x=0;x<fieldWidth;x++) {
				nextField[x][y] = stepCell(x,y);
			}
		}
		field = nextField;
		repaint();
	}
	
	 private boolean stepCell(int x, int y) { // Returns boolean for state of this cell in next frame.
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
	
    public void toggleCell(int x, int y) { // Toggles a cell and repaints panel
    	if (x >= fieldWidth || y >= fieldHeight || x < 0 || y < 0) throw new IllegalArgumentException("Cell out of field bounds: [" + x + ", " + y + "]");
    	field[x][y] = !field[x][y];
    	repaint();
    }
    
    public void toggleCellFromMouse(int mx, int my) { // Converts mouse coords to field coords and calls toggleCell
    	toggleCell(Math.floorDiv(mx, Math.floorDiv(getWidth(), fieldWidth)),
    			Math.floorDiv(my, Math.floorDiv(getHeight(), fieldHeight)));
    }
    
    @Deprecated
	public void print() { // Only looks correct with 25x25
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
	
	public void paintComponent(Graphics g) {
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, getWidth(), getHeight());
		int width = getWidth();
		int height = getHeight();
		drawCells(g,width,height);
		drawLines(g,width,height);
//		print();
	}

	private void drawCells(Graphics g, int w, int h) {
		g.setColor(Color.RED);
		int colPx = Math.floorDiv(w, fieldWidth);
		int rowPx = Math.floorDiv(w, fieldHeight);
		for (int y=0;y<fieldHeight;y++) {
			for (int x=0;x<fieldWidth;x++) {
				if (field[x][y]) {
					g.fillRect(colPx*x, rowPx*y, colPx, rowPx);
				}
			}
		}
	}
	
	private void drawLines(Graphics g, int w, int h) {
		g.setColor(Color.BLACK);
		int colPx = Math.floorDiv(w, fieldWidth);
		for (int x=1;x<fieldWidth;x++) {
			g.drawLine(x*colPx, 0, x*colPx, h);
		}
		int rowPx = Math.floorDiv(w, fieldHeight);
		for (int y=1;y<fieldHeight;y++) {
			g.drawLine(0, y*rowPx, h, y*rowPx);
		}
	}
	
	public enum GenerationType {
		Random, Clear;
	}
	
}
