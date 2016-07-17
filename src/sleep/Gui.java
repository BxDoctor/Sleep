package sleep;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.text.NumberFormat;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class Gui {

	private JFrame frame;
	private JButton btnUp;
	private JTextField time;
	private JButton btnDown;
	private JPanel buttonPanel;
	private JPanel timePanel;
	private JButton startBtn;
	private Sleeper sleeper;
	public Integer now;
	public boolean changed;
	private JPanel fixPanel;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Gui window = new Gui();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Gui() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 250);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		time = new JFormattedTextField(NumberFormat.getIntegerInstance());
		time.setFont(new Font("Serif", 1, 82));
		now = 30*60;
		time.setText(now.toString());
		time.setBounds(0, 0, 20, 20);
		btnUp = new JButton("+ 10 Minuten");
		btnUp.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				change(600);
			}
			
		});
		btnDown = new JButton("- 10 Minuten");
		btnDown.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				change(-600);
				
			}
			
		});
			
		
		buttonPanel = new JPanel();
		buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
		buttonPanel.add(btnUp);
		buttonPanel.add(btnDown);
		
		timePanel = new JPanel();
		timePanel.setLayout(new BorderLayout());
		timePanel.add(time,BorderLayout.CENTER);
		JFrame kiosk = new JFrame();
		timePanel.add(new ControlButton(kiosk),BorderLayout.SOUTH);
		
		
		fixPanel = new JPanel();
		fixPanel.setLayout(new BoxLayout(fixPanel,BoxLayout.Y_AXIS));
		for (int x=15; x<91; x+=15){
			fixPanel.add(btnFix(x));
		}
		
		
		Container cPane = frame.getContentPane();
		cPane.setLayout(new BoxLayout(cPane, BoxLayout.X_AXIS));
		
		cPane.add(timePanel);
		cPane.add(buttonPanel);
		cPane.add(fixPanel);
		sleeper = new Sleeper();
		sleeper.start();
		
		
	}
	
	protected JButton btnFix(int i){
		JButton b = new JButton(i + " Minuten");
		b.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				time.setText((new Integer(i*60)).toString());
				change(0);
			}
			
		});
		return b;
	}
	protected void change(int i) {
		now = max(Integer.parseInt(time.getText()) + i,0);
		time.setText(now.toString());
		changed = true;
		
	}

	public class Sleeper extends Thread{
		public void run(){
			Integer seconds = now;
			
			while (seconds-- > 0){
				try {
					Sleeper.sleep(1000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				if (changed){
					changed = false;
					seconds = now;
				}
				time.setText(seconds.toString());
				
			}
			try {
				Runtime.getRuntime().exec("shutdown -s");
				java.lang.System.exit(0) ;
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	protected Integer max(int i, int j) {
		// TODO Auto-generated method stub
		return -min(-i,-j);
	}

	protected Integer min(int i, int j) {
		// TODO Auto-generated method stub
		if (i < j){
			return i;
		}else{
			return j;
		}
	}

}
