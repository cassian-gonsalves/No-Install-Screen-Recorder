package common;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;
import javax.swing.Icon;
import javax.swing.ImageIcon;

public class ResourcesToAccess {
	public static Icon downIcon;
	public static Icon whiteCursorIcon;
	public static Icon blackCursorIcon;
	public static Icon playIcon;
	public static Icon pauseIcon;
	public static Icon xuggle;
	public static Icon reel;
	public static Icon minimizedIcon;
	
	
	static{
		try{
			InputStream is = ResourcesToAccess.class.getClassLoader().getResourceAsStream("images/down.png");
			BufferedImage bi = ImageIO.read(is);
			downIcon = (Icon) new ImageIcon(bi);
			
			is = ResourcesToAccess.class.getClassLoader().getResourceAsStream("images/pause.png");
			bi = ImageIO.read(is);
			pauseIcon = (Icon) new ImageIcon(bi);
			
			 is = ResourcesToAccess.class.getClassLoader().getResourceAsStream("images/play.png");
			 bi = ImageIO.read(is);
			 playIcon = (Icon) new ImageIcon(bi);
			 
			 is = ResourcesToAccess.class.getClassLoader().getResourceAsStream("images/blackCursor.png");
			 bi = ImageIO.read(is);
			 blackCursorIcon = (Icon) new ImageIcon(bi);
			 
			 is = ResourcesToAccess.class.getClassLoader().getResourceAsStream("images/whiteCursor.png");
			 bi = ImageIO.read(is);
			 whiteCursorIcon = (Icon) new ImageIcon(bi);
			 
			 is = ResourcesToAccess.class.getClassLoader().getResourceAsStream("images/xuggle.png");
			 bi = ImageIO.read(is);
			 xuggle = (Icon) new ImageIcon(bi);
			 
			 is = ResourcesToAccess.class.getClassLoader().getResourceAsStream("images/reel.gif");
			 bi = ImageIO.read(is);
			 reel = (Icon) new ImageIcon(bi);
			 
			 
			 
			 InputStream bigIS = ResourcesToAccess.class.getClassLoader().getResourceAsStream("images/red-dot.png");
			 bi = ImageIO.read(bigIS);
			 minimizedIcon = (Icon) new ImageIcon(bi);
			 
			is.close();
			bigIS.close();
		}catch(IOException e){
			
		}
	}
	
}
