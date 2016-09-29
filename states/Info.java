package states;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseEvent;

import javax.swing.JLabel;

import general.Orbis;
import tools.OButton;
import tools.OText;


@SuppressWarnings("serial")
public class Info extends GameState
{
	private OText titleText;
	private OText infoText;
	private OButton backButton;
	
	
	public Info()
	{
		
		//Initialize the title text
		String titleString = "INFO";
		titleText = new OText(25, 50, 650, titleString);
		titleText.setFont("Courier New", 50, 1.0, 'C', Settings.themes.textColor);
		
		
		//Initialize the info text
		String infoString = " <br> "
    		    + "This game has been created as a personal journey for us. "
    		    + "To break down the barriers of our own programming world and to get a taste of the world of game development. "
    		    + "This game is meant purely for entertainment purposes only, and we have no intent to sell or make profit from Orbis. "
    		    + " <br> <br> "
    		    + "While this game is still in Beta, we encourage all of our players to give us their input to further improve the well-being of Orbis. "
    		    + "You can do this via the Facebook page at: facebook.com/TheOrbisGame "
    		    + " <br> <br> "
    		    + "Most of all, we hope that you, the players, have just as much fun playing the game as we do writing it. <3";
		infoText = new OText(25, 75, 650, infoString);
		infoText.setFont("Courier New", 18, 1.5, 'C', Settings.themes.textColor);
		
		
		 // String yeah = "Is That You or Are You You by Chris Zabriskie is licensed under a Creative Commons Attribution license (https://creativecommons.org/licenses/by/4.0/)
	     //         	   Source: http://chriszabriskie.com/reappear/
	     //                Artist: http://chriszabriskie.com/";
		
		
		//Initialize the back button
		backButton = new OButton("Back", Orbis.GAME_SIZE / 2, 620, 100, Settings.themes.parvusColor);
	}
	
	
	public void transition()
	{
	    Orbis.sounds.playPop();
	}
	
	
	public void step(){}

	
	public void paintComponent(Graphics g)
    {
        ((Graphics2D) g).setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        ((Graphics2D) g).setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON); 
        ((Graphics2D) g).setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS, RenderingHints.VALUE_FRACTIONALMETRICS_ON);        
        
        //Draw the title text
        titleText.draw(g);
        
        //Draw the top horizontal line
        g.setColor(Settings.themes.textColor);
        g.drawLine(25, 75, 675, 75);
        
        //Draw the info text
        infoText.draw(g);
        
        //Draw the bottom horizontal line
        g.setColor(Settings.themes.textColor);
        g.drawLine(25, 550, 675, 550);
        
        //Draw the back button
        backButton.draw(g);
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
	public void mouseReleased(MouseEvent e) 
	{
		if (backButton.checkClicked(e.getX(), e.getY()))
		{
			Orbis.changeState("MENU");			
		}

	}
}

