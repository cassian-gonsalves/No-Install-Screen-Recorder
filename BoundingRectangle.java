package demo;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.*;
import javax.swing.event.*;
public class BoundingRectangle{
	
//	public static void main(String[] args) {
//		new BoundingRectangle(new Rectangle());
//	}
//	
	public BoundingRectangle(Rectangle doReturn){
		this.finalScreenSize = doReturn;
		EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
                }
                JFrame frame = new JFrame("Test");
                frame.setUndecorated(true);
                frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setLayout(new BorderLayout());
                frame.add(new ImagePanel());
                frame.pack();
                frame.setLocationRelativeTo(null);
                frame.setVisible(true);
            }

        });
	}
	public void show(){
		frame.setVisible(true);
	}
	public static Rectangle getScreenViewableBounds() {
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice gd = ge.getDefaultScreenDevice();

        return getScreenViewableBounds(gd);
    }

    public static Rectangle getScreenViewableBounds(GraphicsDevice gd) {
        Rectangle bounds = new Rectangle(0, 0, 0, 0);
        if (gd != null) {
            GraphicsConfiguration gc = gd.getDefaultConfiguration();
            bounds = gc.getBounds();

            Insets insets = Toolkit.getDefaultToolkit().getScreenInsets(gc);

            bounds.x += insets.left;
            bounds.y += insets.top;
            bounds.width -= (insets.left + insets.right);
            bounds.height -= (insets.top + insets.bottom);
        }
        return bounds;
    }
    
    
    
	protected class ImagePanel extends JPanel{
		private BufferedImage image;
		private Point mouseAnchor;
		private Point dragPoint;
		private BackgroundPanel background;
		
		public ImagePanel(){
			background = new BackgroundPanel();
			try{
				Robot bot = new Robot();
				image = bot.createScreenCapture(getScreenViewableBounds());
				
			}catch(AWTException ex){
				Logger.getLogger(BoundingRectangle.class.getName()).log(Level.SEVERE, null, ex);
			}
			this.setLayout(null);
			this.add(background);
			
			MouseAdapter adapter = new MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent e) {
                    mouseAnchor = e.getPoint();
                    dragPoint = null;
                    background.setLocation(mouseAnchor);
                    background.setSize(0, 0);
                }

                @Override
                public void mouseDragged(MouseEvent e) {
                    dragPoint = e.getPoint();
                    int width = dragPoint.x - mouseAnchor.x;
                    int height = dragPoint.y - mouseAnchor.y;

                    int x = mouseAnchor.x;
                    int y = mouseAnchor.y;

                    if (width < 0) {
                        x = dragPoint.x;
                        width *= -1;
                    }
                    if (height < 0) {
                        y = dragPoint.y;
                        height *= -1;
                    }
                    background.setBounds(x, y, width, height);
                    background.revalidate();
                    repaint();
                }

            };
            addMouseListener(adapter);
            addMouseMotionListener(adapter);
		}
		@Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g.create();
            g2d.drawImage(image, 0, 0, this);
            g2d.dispose();
        }
	}
	
	
	
	
	protected class BackgroundPanel extends JPanel{
		private JLabel label;
		private JButton button;
		
		public BackgroundPanel(){
			button = new JButton("Close");
			button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    SwingUtilities.getWindowAncestor(BackgroundPanel.this).dispose();
                }
            });
			
			label = new JLabel("Rectangle ");
			label.setOpaque(true);
			label.setBorder(BorderFactory.createEmptyBorder(4, 4, 4, 4));
			label.setBackground(Color.gray);
			label.setForeground(Color.white);
			
			this.setOpaque(false);
			this.setLayout(new GridBagLayout());
			GridBagConstraints gbc = new GridBagConstraints();
			gbc.gridx = 0;
			gbc.gridy = 0;
			this.add(label,gbc);
			gbc.gridy++;
			this.add(button,gbc);
			
			/* This is used to update the JLabel to show what area is being recorded */
			this.addComponentListener(new ComponentAdapter() {
                @Override
                public void componentResized(ComponentEvent e) {
                    finalScreenSize.x = getX();
                    finalScreenSize.y = getY();
                    finalScreenSize.width = getWidth();
                    finalScreenSize.height = getHeight();
                	label.setText("Rectangle " + getX() + "x" + getY() + "x" + getWidth() + "x" + getHeight());
                }
            });
		}
		@Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g.create();
            g2d.setColor(new Color(128, 128, 128, 64));
            g2d.fillRect(0, 0, getWidth(), getHeight());

            float dash1[] = {10.0f};
            BasicStroke dashed =
                            new BasicStroke(3.0f,
                            BasicStroke.CAP_BUTT,
                            BasicStroke.JOIN_MITER,
                            10.0f, dash1, 0.0f);
            g2d.setColor(Color.BLACK);
            g2d.setStroke(dashed);
            g2d.drawRect(0, 0, getWidth() - 3, getHeight() - 3);
            g2d.dispose();
        }
	}
	JFrame frame = new JFrame("Test");
	Rectangle finalScreenSize = new Rectangle();
}
