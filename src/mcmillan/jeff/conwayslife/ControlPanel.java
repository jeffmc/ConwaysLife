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
	
	private static final String START = "Start";
	private static final String STOP = "Stop";
	
	private static final long MS_PER_FRAME = 250;
	
	private Grid grid;
	
	public ControlPanel(Grid g) {
		super();
		grid = g;
		stepBtn = new JButton("Step");
		this.add(stepBtn);
		stepBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				grid.stepField();
			}
		});
		setupLoopingBtn();
		
		randomizeBtn = new JButton("Randomize");
		randomizeBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				grid.restartGrid(GenerationType.Random);
			}
		});
		add(randomizeBtn);
		clearBtn = new JButton("Clear");
		clearBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				grid.restartGrid(GenerationType.Clear);
			}
		});
		add(clearBtn);
	}
	
	private void setupLoopingBtn() {
		autoStepping = new AtomicBoolean(false);
		loopBtn = new JButton(START);
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
				loopBtn.setText(autoStepping.get()?STOP:START);
			}
		});
	}
}
