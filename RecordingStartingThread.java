package demo;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.geom.*;
import com.sun.awt.AWTUtilities;

import common.ResourcesToAccess;

public class RecordingStartingThread extends Thread{
	@Override
	public void run(){
		JFrame f = new JFrame();
		ImageIcon reel = new ImageIcon("src/images/reel.GIF");
		JLabel label = new JLabel(reel);
		reel.setImageObserver(label);
		f.getContentPane().add(label);
		f.setUndecorated(true);
		f.setSize(300, 300);
		f.setOpacity(0.9f);
		f.setVisible(true);
	}
	public static void main(String[] args) {
		new RecordingStartingThread().start();
	}
}
