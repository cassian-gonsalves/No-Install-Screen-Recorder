package demo;
import javax.swing.*;
import javax.swing.event.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import java.net.*;
import java.util.*;
import java.text.*;
import java.util.concurrent.*;
import java.awt.image.*;
import java.io.*;
public class ScreenRecorder extends JFrame{
	public ScreenRecorder(){
		try{
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		}catch(Exception e){
			
		}
		setSaveLocation();
		setupGUI();
		setupJPopup();
		setVideoParam();
		super.setTitle("Screen Recorder");
		this.getContentPane().add(b);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setVisible(true);
		this.setResizable(false);
		this.addWindowListener(new WindowAdapter(){
			@Override
			public void windowClosing(WindowEvent e){
				printChanges();
			}
		});
		this.pack();
	}
	
	/*This method is used to print all the values that can be changed in the JPopup
	 * This is only to be used for test purposes*/
	public void printChanges(){
		System.out.println("Recording Area: " + recordingArea);
		System.out.println("Encoding: " + encoding);
		System.out.println("Frame Rate: " + frameRate);
		System.out.println("Selected Cursor: " + userCursor);
	}
	
	/* This method is used to setup JPopupMenu*/
	public void setupJPopup(){
		encodingGroup.add(avi);
		encodingGroup.add(quicktime);
		
		popup.add(avi);
		popup.add(quicktime);
		popup.addSeparator();
		
		frameGroup.add(frame10);
		frameGroup.add(frame25);
		
		popup.add(frame10);
		popup.add(frame25);
		popup.addSeparator();
		
		recordingAreaGroup.add(entireScreen);
		recordingAreaGroup.add(custom);
		
		popup.add(entireScreen);
		popup.add(custom);
		popup.addSeparator();
		
		cursorGroup.add(selectBlackCursor);
		cursorGroup.add(selectWhiteCursor);
		cursorGroup.add(selectNoCursor);
		
		selectCursor.add(selectBlackCursor);
		selectCursor.add(selectWhiteCursor);
		selectCursor.add(selectNoCursor);
		
		//TODO
		frame10.addActionListener(popupHandler);
		frame25.addActionListener(popupHandler);
		avi.addActionListener(popupHandler);
		quicktime.addActionListener(popupHandler);
		entireScreen.addActionListener(popupHandler);
		custom.addActionListener(popupHandler);
		selectWhiteCursor.addActionListener(popupHandler);
		selectBlackCursor.addActionListener(popupHandler);
		selectNoCursor.addActionListener(popupHandler);
		
		
		popup.add(selectCursor);
		//popup.addMouseListener(new PopupHandler());
		popup.pack();
	}
	
	//FIXME
	protected class RecordingAreaThread extends Thread{
		@Override
		public void run(){
			new BoundingRectangle(recordingArea);
		}
	}
	
	
	/* Used so setup the default recording area */
	public void setVideoParam(){
		Toolkit t = Toolkit.getDefaultToolkit();
		recordingArea.x = 0;
		recordingArea.y = 0;
		recordingArea.height = t.getScreenSize().height;
		recordingArea.width = t.getScreenSize().width;
	}
	
	/* This method is used to find underlying OS and set the save location */
	public void setSaveLocation(){
		desktop = Desktop.getDesktop();
		OS = System.getProperty("os.name");
		userName = System.getProperty("user.name");
		if(OS.toLowerCase().contains("windows")){
			f = new File("C:/Users/" +userName+ "/Videos");
		}
	}
	
	/*This method is used to add components into each other, setting layout, etc
	 * and adding their Listeners */
	public void setupGUI(){
		play.setPreferredSize(new Dimension(24,24));
		Box controls = Box.createHorizontalBox();
		controls.add(play);
		controls.add(Box.createHorizontalStrut(10));
		timerLabel.setForeground(Color.white);
		controls.add(timerLabel);
		secondsLabel.setForeground(Color.white);
		controls.add(secondsLabel);
		controls.add(Box.createHorizontalStrut(100)); // big separator strut
		intoLabel.setForeground(Color.white);
		controls.add(intoLabel);
		saveLabel.setForeground(Color.white);
		controls.add(saveLabel);
		controls.add(Box.createHorizontalStrut(10));
		controls.add(downLabel);
		b.add(controls);
		
		/* Setting tooltips for various labels */
		saveLabel.setToolTipText("Click to see where your video will be saved");
		downLabel.setToolTipText("Click to see various menus");
		
		saveLabel.addMouseListener(labelHandler);
		downLabel.addMouseListener(labelHandler);
		
	}
	
	
	/* Creating the gradient background */
	protected class Background extends JPanel{
		@Override
		protected void paintComponent(Graphics g){
			Graphics2D g2D = (Graphics2D) g;
			int w = this.getWidth();
			int h = this.getHeight();
			Color color1 = Color.black;
			Color color2 = Color.DARK_GRAY.brighter();
			GradientPaint gr = new GradientPaint(0,0,color1,0,w/2,color2);
			g2D.setPaint(gr); //Color changes to the gradient color
			g2D.fillRect(0, 0, w, h);
			g2D.setPaint(Color.white); //Color changes to white for JLabel, but doesn't actually change
		}
	}
	
	//FIXME
	/* This is a handler for various JPopup menu */
	protected class PopupHandler extends MouseInputAdapter implements ActionListener{
		
		@Override
		public void actionPerformed(ActionEvent e){
			
			Object obj = e.getSource();
			JRadioButtonMenuItem cause;
			cause = (JRadioButtonMenuItem)obj;
				cause = (JRadioButtonMenuItem) obj;
				if(cause == avi){
					System.out.println(encoding);
				}else if(cause == quicktime){
					encoding = "QUICKTIME";
				}else if(cause == frame10){
					frameRate = 10;
				}else if(cause == frame25){
					frameRate = 25;
				}else if(cause == selectWhiteCursor){
					userCursor = whiteCursor;
				}else if(cause == selectBlackCursor){
					userCursor = blackCursor;
				}else if(cause == selectNoCursor){
					userCursor = null;
				}else if(cause == entireScreen){
					setVideoParam();
				}else if(cause == custom){
					recorder.start();
				}
		}
		
		@Override
		public void mouseClicked(MouseEvent e){
			Object obj = e.getSource();
			JRadioButtonMenuItem cause;
			cause = (JRadioButtonMenuItem)obj;
			
				System.out.println("Reached Here!");
				cause = (JRadioButtonMenuItem) obj;
				if(cause == avi){
					System.out.println(encoding);
				}else if(cause == quicktime){
					encoding = "QUICKTIME";
				}else if(cause == frame10){
					frameRate = 10;
				}else if(cause == frame25){
					frameRate = 25;
				}else if(cause == selectWhiteCursor){
					userCursor = whiteCursor;
				}else if(cause == selectBlackCursor){
					userCursor = blackCursor;
				}else if(cause == selectNoCursor){
					userCursor = null;
				}else if(cause == entireScreen){
					setVideoParam();
				}else if(cause == custom){
					System.out.println("Here");
					recorder.start();
				}
		}
	}
	
	/* This is a handler designed for JLabel saveLabel and downLabel */
	protected class LabelHandler extends MouseInputAdapter{
		@Override
		public void mouseClicked(MouseEvent e){
			JLabel cause = (JLabel) e.getSource();
			if(cause == saveLabel&&e.getButton()==MouseEvent.BUTTON1){
				try{
					desktop.open(f);
				}catch(IOException ex){
					ex.printStackTrace();
				}
			}else if(cause == downLabel&&e.getButton()==MouseEvent.BUTTON1){
				popup.show(e.getComponent(), e.getX(),e.getY());
			}
		}
		@Override
		public void mouseEntered(MouseEvent e){
			JLabel cause = (JLabel) e.getComponent();
			if(cause == saveLabel){
				saveLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));
			}else if(cause == downLabel){
				downLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));
			}
		}
		@Override
		public void mouseExited(MouseEvent e){
			JLabel cause = (JLabel) e.getSource();
			if(cause == saveLabel){
				saveLabel.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
			}else if(cause == downLabel){
				downLabel.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
			}
		}
	}
	
	/*Creating the combined Play and Pause button using AbstractAction 
	 * The displayed icon must change from that of play to pause on click and vice versa*/
	protected class PlayAction extends AbstractAction{
		public PlayAction(){
			this.putValue(AbstractAction.LARGE_ICON_KEY, playIcon);
			this.putValue(SHORT_DESCRIPTION, "Click to record video");
		}
		@Override
		public void actionPerformed(ActionEvent e){
			if(isPlay == true){
				isPlay = false;
				play.getAction().putValue(LARGE_ICON_KEY, pauseIcon);
				play.getAction().putValue(SHORT_DESCRIPTION,"Click to pause recording");
				ScreenRecorder.this.setState(JFrame.ICONIFIED);
				
				timeUpdater = new TimeUpdateThread(timerLabel);
				screenCaptureer = new ScreenCapturingThread(screenShots,frameRate,userCursor,recordingArea);
				
				timeUpdater.start();
				screenCaptureer.start();
				
				videoName += fmtTime.format(new Date()); //Preliminary Video Name to be displayed
				videoName = videoName.replace(':', '-');
				System.out.println(videoName);
				
				play.repaint();
			}else{
				
				timeUpdater.stopIt();
				screenCaptureer.stopIt();
				System.out.println(screenShots.size());
				
				isPlay = true;
				play.getAction().putValue(LARGE_ICON_KEY, playIcon);
				play.getAction().putValue(SHORT_DESCRIPTION,"Click to start recording");
				play.repaint();
			}
		}
		boolean isPlay = true; //true represents recorder is ready, false means it is currently recording
		Icon playIcon = (Icon) new ImageIcon("src/images/play.png");
		Icon pauseIcon = (Icon) new ImageIcon("src/images/pause.png");
	}
	
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable(){
			@Override
			public void run(){
				new ScreenRecorder();
			}
		});
	}
	
	Background b = new Background(); // Gradient Background
	JButton play = new JButton(new PlayAction()); //Play-n-Pause Button
	long seconds = 0000; //For how long was the video recorded
	
	/* These are the JLabels used to create the GUI */
	Icon downIcon = (Icon) new ImageIcon("src/images/down.png");
	JLabel timerLabel = new JLabel(Long.toString(seconds),JLabel.CENTER);
	JLabel intoLabel = new JLabel("Into: ",JLabel.LEFT);
	JLabel secondsLabel = new JLabel("Seconds",JLabel.RIGHT);
	JLabel saveLabel = new JLabel("<html><body style='align: center;'><u>Videos</u>");
	JLabel downLabel = new JLabel(downIcon,JLabel.RIGHT);
	
	
	/* At what location the video will be saved depends on underlying OS */
	String OS = null;
	String userName = null;
	File f = null;
	Desktop desktop = null;
	
	/* Vectors to be used for encoding the video  */
	Vector<BufferedImage> screenShots = new Vector<>(1024);
	
	/* All these fields will be used to generate the Video Name */
	Date today = new Date();
	DateFormat fmtDate = DateFormat.getDateInstance();
	DateFormat fmtTime = DateFormat.getTimeInstance();
	
	/* Preliminary Video Name */
	String videoName ="ScreenCast "+ fmtDate.format(today) + " at ";
	
	/* Icons for the cursors */
	Icon blackCursor = (Icon) new ImageIcon("src/images/blackCursor.png");
	Icon whiteCursor = (Icon) new ImageIcon("src/images/whiteCursor.png"); //default cursor
	
	/* Parameters related to the output video */
	long frameRate = 10;
	Rectangle recordingArea = new Rectangle();
	String encoding = "AVI";
	Icon userCursor = whiteCursor; //This is the default cursor
	RecordingAreaThread recorder = new RecordingAreaThread();
	
	
	/* Creating the JPopupMenu containing the various menu items */
	JPopupMenu popup = new JPopupMenu();
	
	ButtonGroup encodingGroup = new ButtonGroup();
	ButtonGroup recordingAreaGroup = new ButtonGroup();
	ButtonGroup cursorGroup = new ButtonGroup();
	ButtonGroup frameGroup = new ButtonGroup();
	
	/* Handler for labels*/
	LabelHandler labelHandler = new LabelHandler();
	PopupHandler popupHandler = new PopupHandler();
	
	/* Various Menus */
	JRadioButtonMenuItem avi = new JRadioButtonMenuItem("AVI",true);
	JRadioButtonMenuItem quicktime = new JRadioButtonMenuItem("QuickTime",false);
	JRadioButtonMenuItem entireScreen = new JRadioButtonMenuItem("Entire Screen",true);
	JRadioButtonMenuItem custom = new JRadioButtonMenuItem("Custom...",false);
	JRadioButtonMenuItem frame10 = new JRadioButtonMenuItem("Frame Rate 10fps",true);
	JRadioButtonMenuItem frame25 = new JRadioButtonMenuItem("Frame Rate 25fps",false);
	JMenuItem selectCursor = new JMenu("Select a cursor");
	JRadioButtonMenuItem selectWhiteCursor = new JRadioButtonMenuItem("White Cursor",true);
	JRadioButtonMenuItem selectBlackCursor = new JRadioButtonMenuItem("Black Cursor",false);
	JRadioButtonMenuItem selectNoCursor = new JRadioButtonMenuItem("No Cursor",false);
	
	TimeUpdateThread timeUpdater;
	ScreenCapturingThread screenCaptureer;
}