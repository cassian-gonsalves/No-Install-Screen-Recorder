package demo;
import java.awt.Color;

import javax.swing.*;
public class TimeUpdateThread extends Thread{
	public TimeUpdateThread(JLabel timeLabel){
		this.timeLabel = timeLabel;
		seconds = 0;
	}
	@Override
	public void run(){
		timeLabel.setForeground(Color.white);
		while(keepUpdating==true){
			try{
				seconds++;
				timeLabel.setText(Long.toString(seconds));
				timeLabel.repaint();
				sleep(1000);
			}catch(InterruptedException e){
				System.out.println("Thread Interrupted");
			}
		}
	}
	public void startIt(){
		keepUpdating = true;
	}
	public void stopIt(){
		keepUpdating = false;
		timeLabel.setForeground(Color.red);
		timeLabel.repaint();
	}
	JLabel timeLabel;
	long seconds;
	boolean keepUpdating = true;
}
