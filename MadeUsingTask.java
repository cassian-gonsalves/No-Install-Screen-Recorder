package demo;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.awt.geom.*;
import java.util.TimerTask;

import javax.swing.*;
import common.ResourcesToAccess;

public class MadeUsingTask extends TimerTask{
	int x = 0;
	int y = 0;
	JFrame frame = new JFrame();
	java.util.Timer timer;
	Icon xuggle = common.ResourcesToAccess.xuggle;
	JLabel label = new JLabel(xuggle);
	Rectangle2D.Double visiblePortion = new Rectangle2D.Double(x,y,xuggle.getIconWidth()/4,
			xuggle.getIconHeight());
	int passCount = 1;
	
	public MadeUsingTask(java.util.Timer t){
		timer = t;
		frame.add(label);
		frame.setUndecorated(true);
		frame.setSize(xuggle.getIconWidth(),xuggle.getIconHeight());
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		frame.setOpacity(0.5f);
		frame.setIconImage(((ImageIcon)common.ResourcesToAccess.minimizedIcon).getImage());
	}
	
	public void setTimer(java.util.Timer t){
		this.timer = t;
	}
	
	@Override
	public void run(){
		if(passCount <=4 ){
			frame.setShape(visiblePortion);
			visiblePortion.width += xuggle.getIconWidth()/4;
			visiblePortion.x = x;
		}
		if(passCount == 5){
//			System.out.println("frame count 4");
			frame.dispose();
			timer.cancel();
		}
		passCount++;
		
	}
}
