package states;

//General imports
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.net.URL;

//Local imports
import general.*;


public class Splash extends GameState
{
	private Image logo;
	
	public Splash()
	{
		URL url = Orbis.class.getResource("/images/Orbis.png");
        logo = Toolkit.getDefaultToolkit().getImage(url);
        Orbis.sounds.playMenuSong();
	}
	public void transition(){}
	
	public void step()
	{
		if (checkDevMode())
			Orbis.devMode = true;
	}
	
	public void paintComponent(Graphics g)	
	{
		Graphics2D g2 = (Graphics2D) g;
		((Graphics2D) g).setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        ((Graphics2D) g).setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON); 
        ((Graphics2D) g).setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS, RenderingHints.VALUE_FRACTIONALMETRICS_ON);
        
        
        int width = Orbis.GAME_SIZE-50;
        int height = width * logo.getHeight(this) / logo.getWidth(this);
        g2.drawImage(logo, 25, 25, width, height, this);
        
        String click = "Click to Continue";       
        String intro = Orbis.Version;
        String intro1 = "Samuel Barr & Blake Nelson";
        
        g.setColor(Settings.themes.textColor);
        g.setFont(new Font("Courier New", Font.BOLD, 36));
        int clickx = (Orbis.GAME_SIZE / 2) - (g.getFontMetrics().stringWidth(click) / 2);
        g.drawString(click, clickx, Orbis.GAME_SIZE / 2 + 100); 
        
        g.setFont(new Font("Courier New", Font.BOLD, 18));
        int introx = (Orbis.GAME_SIZE / 2) - (g.getFontMetrics().stringWidth(intro) / 2);
        int intro1x = (Orbis.GAME_SIZE / 2) - (g.getFontMetrics().stringWidth(intro1) / 2);
              
        g.drawString(intro, introx, Orbis.GAME_SIZE / 2 + 310);
        g.drawString(intro1, intro1x, Orbis.GAME_SIZE / 2 + 335);
	}
	/**
	 * Method to check code for developer's mode.
	 * @return true if code is present.
	 */
	public boolean checkDevMode()
	{
		if (Orbis.command && Orbis.keyStroke.size() > 7)
		{
			//clears all
			if (Orbis.keyStroke.contains(KeyEvent.VK_BACK_SPACE))
			{
				Orbis.keyStroke.clear();
				return false;
			}
			//RECLUDAM for unlock.
			if (Orbis.keyStroke.get(0) == (KeyEvent.VK_R))
				if (Orbis.keyStroke.get(1) == (KeyEvent.VK_E))
					if (Orbis.keyStroke.get(2) == (KeyEvent.VK_C))
						if (Orbis.keyStroke.get(3) == (KeyEvent.VK_L))
							if (Orbis.keyStroke.get(4) == (KeyEvent.VK_U))
								if (Orbis.keyStroke.get(5) == (KeyEvent.VK_D))
									if (Orbis.keyStroke.get(6) == (KeyEvent.VK_A))
										if (Orbis.keyStroke.get(7) == (KeyEvent.VK_M))	
										{
											Orbis.keyStroke.clear();
										    Orbis.sounds.playGrow();
										    return true;
										}
											
													
		}
		return false;
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {}
	@Override
	public void mouseEntered(MouseEvent arg0) {}
	@Override
	public void mouseExited(MouseEvent arg0) {}
	@Override
	public void mousePressed(MouseEvent arg0) {}
	@Override
	public void mouseReleased(MouseEvent arg0)
	{
		Orbis.changeState("MENU");
	}
	
}
