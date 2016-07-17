package sleep;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFrame;

@SuppressWarnings("serial")
public class ControlButton extends JButton {

	private JFrame frame;
	private int cur = 500;
	
	public ControlButton(JFrame frame) {
		super("GOOD NIGHT");
	this.frame = frame;
	this.addActionListener(this.generateActionListener());
	}

	private ActionListener generateActionListener() {
		ActionListener AL = new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				frame.setVisible(true);
				frame.setExtendedState(JFrame.MAXIMIZED_BOTH); //maximise window

				//frame.setUndecorated(true); //remove decorations e.g. x in top right

				 frame.setAlwaysOnTop(true);
				 frame.addMouseWheelListener(new MouseWheelListener(){


					@Override
					public void mouseWheelMoved(MouseWheelEvent arg0) {
						double val =  arg0.getPreciseWheelRotation();
						if (val > 0){
							val=66;
						}else{
							val =-66;
						}
						System.out.println(arg0.getPreciseWheelRotation());
						cur = cur - (int) val;
						if (cur > 1000){
							cur = 1000;
						}
						if ( cur < 0 ){
							cur= 0;
						}
						setSystemVolume(cur);
					}
					 
				 });
				
			}
	};
	return AL;
	}
	public void setSystemVolume(int volume)
	{
	    if(volume < 0 || volume > 1000)
	    {
	        throw new RuntimeException("Error: " + volume + " is not a valid number. Choose a number between 0 and 100");
	    }

	    else
	    {
	        double endVolume = 65.535 * volume;

	        Runtime rt = Runtime.getRuntime();
	        Process pr;
	        try 
	        {
	            String nircmdFilePath = "./nircmd.exe";
				pr = rt.exec(nircmdFilePath  + " setsysvolume " + endVolume);
	            pr = rt.exec(nircmdFilePath + " mutesysvolume 0");
	            System.out.println("current vol: "+ volume);

	        } 
	        catch (IOException e) 
	        {
	            e.printStackTrace();
	        }
	    }
	}
}
