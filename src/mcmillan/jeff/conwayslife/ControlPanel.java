package mcmillan.jeff.conwayslife;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.concurrent.atomic.AtomicBoolean;

import javax.swing.JButton;
import javax.swing.JPanel;

import mcmillan.jeff.conwayslife.Grid.GenerationType;

@SuppressWarnings("serial")
public class ControlPanel extends JPanel {
	private JButton stepBtn;
	
	private JButton loopBtn;
	private AtomicBoolean autoStepping;
	private Thread loopThread;
	
	private JButton randomizeBtn;
	private JButton clearBtn;
	
	private static final String START_STR = "Start";
	private static final String STOP_STR = "Stop";
	
	private static final long MS_PER_FRAME = 250;
	
	private Grid grid;
	
	// ControlPanel contains the step, start/stop, randomize, clear buttons and manages the loop.
	public ControlPanel(Grid g) {
		super();
		grid = g;
		
		// Single-step button
		stepBtn = new JButton("Step");
		this.add(stepBtn);
		stepBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				grid.stepField();
			}
		});
		
		setupLoopingBtn();
		
		// Randomize button
		randomizeBtn = makeRestartGridBtn("Randomize", GenerationType.Random);
		add(randomizeBtn);
		
		// Clear button
		clearBtn = makeRestartGridBtn("Clear", GenerationType.Clear);
		add(clearBtn);
	}
	
	private void setupLoopingBtn() {
		autoStepping = new AtomicBoolean(false);
		loopBtn = new JButton(START_STR);
		add(loopBtn);
		loopBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				autoStepping.set(!autoStepping.get());;
				if (autoStepping.get()) {
					loopThread = new Thread(new Runnable() {
						@Override
						public void run() {
							autoStepping.set(true);
							long lastStep = System.currentTimeMillis();
							while (autoStepping.get()) {
								if (System.currentTimeMillis()-lastStep > MS_PER_FRAME) {
									lastStep = System.currentTimeMillis();
									grid.stepField();
								}
							}
						}
					});
					loopThread.start();
				} else {
					autoStepping.set(false);
				}
				loopBtn.setText(autoStepping.get()?STOP_STR:START_STR);
			}
		});
	}
	
	private JButton makeRestartGridBtn(String label, Grid.GenerationType mode) {
		JButton btn = new JButton(label);
		btn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				grid.restartGrid(mode);
			}
		});
		return btn;
	}
}
