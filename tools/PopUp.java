package tools;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JFrame;
import javax.swing.JPanel;

import states.Settings;

public class PopUp extends JPanel implements MouseListener
{	
	private static final int POP_SIZE = 400;
	
	public static JFrame frame;
	private String mainText;
	private String subText;
	
	public static OButton accept;
	public static OButton reject;
	
	private String acceptLabel;
	private String rejectLabel;
	
	/**
	 * Constructor.  Creates pop up frame.
	 * @param header of the window.
	 * @param mainText of what the popup will say/ask.
	 * @param subText of what will go under the mainText
	 */
	public PopUp(String header, String mainText, String subText) 
	{
		this.mainText = mainText;
		this.subText = subText;
		
		//Create basic frame.
        frame = new JFrame(header);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        
        //Align the frame based on the insets (with a throw-away frame).
        JFrame blah = new JFrame();
        blah.pack();
        Insets insets = blah.getInsets();
        blah = null;
        frame.setSize(new Dimension(POP_SIZE + insets.right, POP_SIZE/2 + insets.top));
        
        //Sets the location of the frame to the middle of the screen.
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int width = (int) screenSize.getWidth();
        int height = (int) screenSize.getHeight();
        frame.setLocation(width/2 - POP_SIZE/2, height/2 - POP_SIZE/2);        
        frame.setResizable(false);
        
        acceptLabel = "Yes";
        rejectLabel = "No";
        initializeButtons();
        
        frame.setBackground(Settings.themes.background);
        frame.add(this);
        frame.setVisible(true);
	}
	
	 public void initializeButtons()
	 {
		 accept = new OButton(acceptLabel, POP_SIZE/2 - 75, 135, 75, Settings.themes.optimusColor);
		 reject = new OButton(rejectLabel, POP_SIZE/2 + 75, 135, 75, Settings.themes.parvusColor);
	 }
	 
	 public void setLabels(String acceptLabel, String rejectLabel)
	 {
	     this.acceptLabel = acceptLabel;
	     this.rejectLabel = rejectLabel;
	     initializeButtons();
	 }
	
	 public void paintComponent(Graphics g)
	 {
		 ((Graphics2D) g).setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		 ((Graphics2D) g).setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON); 
		 ((Graphics2D) g).setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS, RenderingHints.VALUE_FRACTIONALMETRICS_ON);

		 g.setFont(new Font("Courier New", Font.BOLD, 20));
		 g.setColor(Settings.themes.textColor);
		 int x = (POP_SIZE / 2) - (g.getFontMetrics().stringWidth(mainText) / 2);
		 int y = 40;
		 g.drawString(mainText, x, y);
		 
		 g.setFont(new Font("Courier New", Font.PLAIN, 20));;
		 int sx = (POP_SIZE / 2) - (g.getFontMetrics().stringWidth(subText) / 2);
		 int sy = 70;
		 g.drawString(subText, sx, sy);
		 
		 accept.draw(g);
		 reject.draw(g);
	 }

	@Override
	public void mouseClicked(MouseEvent e) {}

	@Override
	public void mouseEntered(MouseEvent e) {}

	@Override
	public void mouseExited(MouseEvent e) {}

	@Override
	public void mousePressed(MouseEvent e) {}

	@Override
	public void mouseReleased(MouseEvent e) {}

}
