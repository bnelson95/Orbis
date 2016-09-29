package states;


import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseEvent;

import general.Orbis;
import objects.*;
import tools.OButton;
import tools.OText;



public class Help extends GameState
{
    public static final String NAME = "HELP";
	private static final int NUMBER_PAGES = 4;
	
	private OButton pageLeftButton;
	private OButton pageRightButton;
	private String[] pages = {"Optimus", "Controls", "Enemies", "Upgrades"};
	private int page;
	
	
	private OText keyTitleText;
	private OText keyMoveText;
	private OText keyPauseText;
	private OText keyMuteText;
	
	private OText mouseTitleText;
	private OText mouseText;
	
	
    private OButton backButton;

    
    public Help()
    {
    	pageLeftButton = new OButton("<", 50, 125, 50, Settings.themes.optimusColor);
    	pageRightButton = new OButton(">", 650, 125, 50, Settings.themes.optimusColor);
    	page = 0;
    	
    	
    	initControlsPage();
    	
    	
        backButton = new OButton("Back", Orbis.GAME_SIZE / 2, 625, 100, Settings.themes.parvusColor);
    }
    
    
    private void initControlsPage()
    {
    	//Initialize the keyboard title text
    	String keyTitleString = "KEYBOARD";
    	keyTitleText = new OText(25, 200, 300, keyTitleString);
    	keyTitleText.setFont("Courier New", 20, 1.0, 'C', Settings.themes.textColor);
    	
    	//Initialize the keyboard movement text
    	String keyMoveString = "Move Optimus with the WASD keys "
    			             + " <br> "
    			             + "as if they were the arrow keys.";
    	keyMoveText = new OText(25, 225, 300, keyMoveString);
    	keyMoveText.setFont("Courier New", 14, 1.0, 'C', Settings.themes.textColor);
    	
    	
    	//Initialize the mouse title text
    	String mouseTitleString = "MOUSE";
    	mouseTitleText = new OText(375, 200, 300, mouseTitleString);
    	mouseTitleText.setFont("Courier New", 20, 1.0, 'C', Settings.themes.textColor);
    	
    	//Initialize the mouse text
    	String mouseMoveString = "Aim by moving the mouse and shoot bullets from Optimus in the direction of the cursor using both clicks. ";
    	mouseText = new OText(375, 225, 300, mouseMoveString);
    	mouseText.setFont("Courier New", 14, 1.0, 'C', Settings.themes.textColor);
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
        
        //Draw heading
        g.setFont(new Font("Courier New", Font.PLAIN, 50));
        g.setColor(Color.BLACK);
        String help = "HELP";
        int helpX = (Orbis.GAME_SIZE / 2) - (g.getFontMetrics().stringWidth(help) / 2);
        int helpY = 50;
        g.drawString(help, helpX, helpY);
        
        //Draw top horizontal line
        g.drawLine(25, 75, 675, 75);
        
        //Draw page title
        g.setFont(new Font("Courier New", Font.BOLD, 25));
        g.drawString(pages[page], Orbis.GAME_SIZE/2 - g.getFontMetrics().stringWidth(pages[page])/2, 125+g.getFontMetrics().getHeight()/4);
        
        //Draw page buttons and their titles
        g.setFont(new Font("Courier New", Font.PLAIN, 25));
        if (page != 0){
        	pageLeftButton.draw(g);
        	g.drawString(pages[page-1], 85, 125+g.getFontMetrics().getHeight()/4);
        }
        
        if (page != NUMBER_PAGES-1){
        	pageRightButton.draw(g);
        	g.drawString(pages[page+1], 625-10-g.getFontMetrics().stringWidth(pages[page+1]), 125+g.getFontMetrics().getHeight()/4);
        }
        
        //Draw page
        switch (pages[page])
        {      	
        	case "Optimus"  : paintOptimusPage (g); break;
        	case "Controls" : paintControlsPage(g); break;
        	case "Enemies"  : paintEnemiesPage (g); break;
        	case "Upgrades" : paintUpgradesPage(g); break;
        }
        
        //Draw bottom horizontal line
        g.drawLine(25, 550, 675, 550);
        
        //Draww the back button
        backButton.draw(g);
    }   
    
    private void paintOptimusPage(Graphics g)
    {
        g.setColor(OptimusOrb.COLOR);
        g.fillOval((Orbis.GAME_SIZE/2) - 25, 160, OptimusOrb.SIZE, OptimusOrb.SIZE);
        
        g.setFont(new Font("Courier New", Font.BOLD, 18));
        g.setColor(Settings.themes.textColor); 
        
        String obj0 = "        This is you: Optimus Orbis (Good Circle)           ";       
        String obj1 = "The only one of your kind.  You're different from the rest.";        
        String obj2 = "   You're faster, smarter, and you can shoot bullets.      ";
        String obj3 = "      But the other Orbi, the Inimicus, are jealous.       ";
        String obj4 = " They'll do anything they can to crush you, and they will. ";        
        String obj5 = " But that doesn't mean you have to go out without a fight. ";
        String obj6 = "      The Inimicus will try to beat you to death.          ";
        String obj7 = "   Pick up health( ) and shield( ) drops to stay alive!    ";
        
        g.drawString(obj0, 25, 200+50 - g.getFontMetrics().getHeight()/4);
        g.setFont(new Font("Courier New", Font.PLAIN, 18));
        g.drawString(obj1, 25, 225+50 - g.getFontMetrics().getHeight()/4);
        g.drawString(obj2, 25, 250+50 - g.getFontMetrics().getHeight()/4);
        g.setColor(Parvus.COLOR);
        g.drawString(obj3, 25, 275+50 - g.getFontMetrics().getHeight()/4);
        g.setColor(Settings.themes.textColor); 
        g.drawString(obj4, 25, 300+50 - g.getFontMetrics().getHeight()/4);
        g.drawString(obj5, 25, 325+50 - g.getFontMetrics().getHeight()/4);
        g.drawString(obj6, 25, 375+50 - g.getFontMetrics().getHeight()/4);
        g.drawString(obj7, 25, 400+50 - g.getFontMetrics().getHeight()/4);
        
        //Draws Health Drops
        g.setColor(Settings.themes.healthColor);
        g.fillOval(221, 436, 8, 8);       
        g.setColor(Settings.themes.shieldColor);
        g.fillOval(372, 436, 8, 8);
        
        g.setColor(Settings.themes.textColor);
    }
    
    
    private void paintControlsPage(Graphics g)
    {
    	//Draw the middle vertical line
        g.drawLine(Orbis.GAME_SIZE/2, 175, Orbis.GAME_SIZE/2, 525);
        
        keyTitleText.draw(g);
        keyMoveText.draw(g);
        
        mouseTitleText.draw(g);
        mouseText.draw(g);
        
        
        //Draw keys
        int xOffset = 105;
        int yOffset = 250;        
        g.drawRoundRect(xOffset + 50, yOffset, 40, 40, 10, 10); //W
        g.drawRoundRect(xOffset, yOffset + 50, 40, 40, 10, 10); //A
        g.drawRoundRect(xOffset + 50, yOffset + 50, 40, 40, 10, 10); //S
        g.drawRoundRect(xOffset + 100, yOffset + 50, 40, 40, 10, 10); //D
        
        //Draw key labels
        g.drawString("W", xOffset + 70, yOffset + 20);
        g.drawString("A", xOffset + 20, yOffset + 70);
        g.drawString("S", xOffset + 70, yOffset + 70);
        g.drawString("D", xOffset + 120, yOffset + 70);
        
        
    }
    
    
    private void paintEnemiesPage(Graphics g)
    {
        g.drawLine(Orbis.GAME_SIZE/2, 175, Orbis.GAME_SIZE/2, 525);
    	paintEnemies(g);
    }
    
    
    public void paintEnemies(Graphics g)
    { 	      
        int col1X = 50;
        int col2X = 375;
    	
        //******************Parvus********************
        String par1 = "Parvus";
        String par2 = "\"The Small\"";
        String par3 = "The original enemy";
        
    	int parX = col1X + Parvus.SIZE/2;
    	int parY = 200;
    	
    	//Paint circle
    	g.setColor(Parvus.COLOR);
    	g.fillOval(parX - Parvus.SIZE/2, parY - Parvus.SIZE/2, Parvus.SIZE, Parvus.SIZE);
    	
    	//Paint Latin name
    	g.setFont(new Font("Courier New", Font.BOLD, 18));
        g.setColor(Color.BLACK);
        int par1Xoffset = Parvus.SIZE/2 + 20;
        int par1Yoffset = g.getFontMetrics().getHeight()/4 - 10;
        g.drawString(par1, parX+par1Xoffset, parY+par1Yoffset);
        
        //Paint English translation
        g.setFont(new Font("Courier New", Font.PLAIN, 16));
        g.setColor(Parvus.COLOR);
        int par2Xoffset = Parvus.SIZE/2 + 20;
        int par2Yoffset = g.getFontMetrics().getHeight()/4 + 10;
        g.drawString(par2, parX+par2Xoffset, parY+par2Yoffset);
        
        //Paint description
        g.setFont(new Font("Courier New", Font.PLAIN, 16));
        g.setColor(Color.BLACK);
        //int par3Xoffset = Parvus.SIZE/2 + 10;
        int par3Yoffset = g.getFontMetrics().getHeight()/4 + Parvus.SIZE/2 + 20;
        g.drawString(par3, col1X, parY+par3Yoffset);
        
        
        //******************Magnus********************
        String mag1 = "Magnus";
        String mag2 = "\"The Great\"";
        String mag3 = "The original boss";
        
        int magX = col1X + Magnus.SIZE/2;
        int magY = 375;
        
        //Paint circle
        g.setColor(Magnus.COLOR);
        g.fillOval(magX - Magnus.SIZE/2, magY - Magnus.SIZE/2, Magnus.SIZE, Magnus.SIZE);
        
        //Paint Latin name
        g.setFont(new Font("Courier New", Font.BOLD, 18));
        g.setColor(Color.BLACK);
        int mag1Xoffset = Magnus.SIZE/2 + 20;
        int mag1Yoffset = g.getFontMetrics().getHeight()/4 - 10;
        g.drawString(mag1, magX+mag1Xoffset, magY+mag1Yoffset);
        
        //Paint English translation
        g.setFont(new Font("Courier New", Font.PLAIN, 16));
        g.setColor(Magnus.COLOR);
        int mag2Xoffset = Magnus.SIZE/2 + 20;
        int mag2Yoffset = g.getFontMetrics().getHeight()/4 + 10;
        g.drawString(mag2, magX+mag2Xoffset, magY+mag2Yoffset);
        
        //Paint description
        g.setFont(new Font("Courier New", Font.PLAIN, 16));
        g.setColor(Color.BLACK);
        //int mag3Xoffset = Magnus.SIZE/2 + 10;
        int mag3Yoffset = g.getFontMetrics().getHeight()/4 + Magnus.SIZE/2 + 20;
        g.drawString(mag3, col1X, magY+mag3Yoffset);
        
        
        //******************Scindo********************
        String sci1 = "Scindo";
        String sci2 = "\"The Divider\"";
        String sci3 = "Splits on bullet impact";
        
        int sciX = col2X + Scindo.SIZE/2;
        int sciY = 200;
        
        //Paint circle
        g.setColor(Scindo.COLOR);
        g.fillOval(sciX - Scindo.SIZE/2, sciY - Scindo.SIZE/2, Scindo.SIZE, Scindo.SIZE);
        
        //Paint Latin name
        g.setFont(new Font("Courier New", Font.BOLD, 18));
        g.setColor(Color.BLACK);
        int sci1Xoffset = Scindo.SIZE/2 + 20;
        int sci1Yoffset = g.getFontMetrics().getHeight()/4 - 10;
        g.drawString(sci1, sciX+sci1Xoffset, sciY+sci1Yoffset);
        
        //Paint English translation
        g.setFont(new Font("Courier New", Font.PLAIN, 16));
        g.setColor(Scindo.COLOR);
        int sci2Xoffset = Scindo.SIZE/2 + 20;
        int sci2Yoffset = g.getFontMetrics().getHeight()/4 + 10;
        g.drawString(sci2, sciX+sci2Xoffset, sciY+sci2Yoffset);
        
        //Paint description
        g.setFont(new Font("Courier New", Font.PLAIN, 16));
        g.setColor(Color.BLACK);
        //int sci3Xoffset = Magnus.SIZE/2 + 10;
        int sci3Yoffset = g.getFontMetrics().getHeight()/4 + Scindo.SIZE/2 + 20;
        g.drawString(sci3, col2X, sciY+sci3Yoffset);
        
        
        //******************Amoveo********************
        String amo1 = "Amoveo";
        String amo2 = "\"The Shifter\"";
        String amo3 = "Teleports on bullet impact";
        
        int amoX = col2X + Amoveo.SIZE/2;
        int amoY = 325;
        
        //Paint circle
        g.setColor(Amoveo.COLOR);
        g.fillOval(amoX - Amoveo.SIZE/2, amoY - Amoveo.SIZE/2, Amoveo.SIZE, Amoveo.SIZE);
        
        //Paint Latin name
        g.setFont(new Font("Courier New", Font.BOLD, 18));
        g.setColor(Color.BLACK);
        int amo1Xoffset = Amoveo.SIZE/2 + 20;
        int amo1Yoffset = g.getFontMetrics().getHeight()/4 - 10;
        g.drawString(amo1, amoX+amo1Xoffset, amoY+amo1Yoffset);
        
        //Paint English translation
        g.setFont(new Font("Courier New", Font.PLAIN, 16));
        g.setColor(Amoveo.COLOR);
        int amo2Xoffset = Amoveo.SIZE/2 + 20;
        int amo2Yoffset = g.getFontMetrics().getHeight()/4 + 10;
        g.drawString(amo2, amoX+amo2Xoffset, amoY+amo2Yoffset);
        
        //Paint description
        g.setFont(new Font("Courier New", Font.PLAIN, 16));
        g.setColor(Color.BLACK);
        //int amo3Xoffset = Amoveo.SIZE/2 + 10;
        int amo3Yoffset = g.getFontMetrics().getHeight()/4 + Amoveo.SIZE/2 + 20;
        g.drawString(amo3, col2X, amoY+amo3Yoffset);
        
        
        //******************Praedo********************
        String pra1 = "Praedo";
        String pra2 = "\"The Robber\"";
        String pra3 = "Stealer of health drops";
        
        int praX = col2X + Praedo.SIZE/2;
        int praY = 450;
        
        //Paint circle
        g.setColor(Praedo.COLOR);
        g.fillOval(praX - Praedo.SIZE/2, praY - Praedo.SIZE/2, Praedo.SIZE, Praedo.SIZE);
        
        //Paint Latin name
        g.setFont(new Font("Courier New", Font.BOLD, 18));
        g.setColor(Color.BLACK);
        int pra1Xoffset = Praedo.SIZE/2 + 20;
        int pra1Yoffset = g.getFontMetrics().getHeight()/4 - 10;
        g.drawString(pra1, praX+pra1Xoffset, praY+pra1Yoffset);
        
        //Paint English translation
        g.setFont(new Font("Courier New", Font.PLAIN, 16));
        g.setColor(Praedo.COLOR);
        int pra2Xoffset = Praedo.SIZE/2 + 20;
        int pra2Yoffset = g.getFontMetrics().getHeight()/4 + 10;
        g.drawString(pra2, praX+pra2Xoffset, praY+pra2Yoffset);
        
        //Paint description
        g.setFont(new Font("Courier New", Font.PLAIN, 16));
        g.setColor(Color.BLACK);
        //int sci3Xoffset = Praedo.SIZE/2 + 10;
        int pra3Yoffset = g.getFontMetrics().getHeight()/4 + Praedo.SIZE/2 + 20;
        g.drawString(pra3, col2X, praY+pra3Yoffset);
        
        
    }
    
    private void paintUpgradesPage(Graphics g)
    {
        g.setFont(new Font("Courier New", Font.PLAIN, 18));
        g.setColor(Settings.themes.textColor);
               
        String obj0 = " Your score depends on how many tokens you collect overall.";
        String obj1 = " Every time you kill an enemy, tokens will attract to you.";
        String obj2 = "   In turn, tokens can be used to purchase upgrades.";
                        
        String obj3 = "     Press the spacebar in game to access upgrades.";
                        
        String obj4 = "   Each upgrade ability can be upgraded more than once,";       
        String obj5 = "    and there is no limit to how much you can upgrade.";
                        
        String obj6 = "  Tokens and upgrades do not transfer between games, so";
        String obj7 = "                   use them wisely!";
        
        g.drawString(obj0, 30, 125+75 - g.getFontMetrics().getHeight()/4);
        g.drawString(obj1, 30, 175+75 - g.getFontMetrics().getHeight()/4);
        g.drawString(obj2, 30, 200+75 - g.getFontMetrics().getHeight()/4);
        g.setColor(Parvus.COLOR);
        g.drawString(obj3, 30, 250+75 - g.getFontMetrics().getHeight()/4);
        g.setColor(Settings.themes.textColor);
        g.drawString(obj4, 30, 300+75 - g.getFontMetrics().getHeight()/4);
        g.drawString(obj5, 30, 325+75 - g.getFontMetrics().getHeight()/4);
        
        g.drawString(obj6, 30, 375+75 - g.getFontMetrics().getHeight()/4);
        g.setColor(Parvus.COLOR);
        g.drawString(obj7, 30, 400+75 - g.getFontMetrics().getHeight()/4);
        g.setColor(Settings.themes.textColor);
    }


    @Override
    public void mouseClicked(MouseEvent arg0){}

    @Override
    public void mouseEntered(MouseEvent arg0){}

    @Override
    public void mouseExited(MouseEvent arg0){}

    @Override
    public void mousePressed(MouseEvent e){}

    @Override
    public void mouseReleased(MouseEvent e)
    {
    	if (backButton.checkClicked(e.getX(), e.getY())) 
    		Orbis.changeState("MENU");
    	
    	if (pageLeftButton.checkClicked(e.getX(), e.getY()) && page != 0)
    	{
    		page--;
    		Orbis.sounds.playHit();
    	}
    	
    	if (pageRightButton.checkClicked(e.getX(), e.getY()) && page != NUMBER_PAGES-1)
    	{
    		page++;
    		Orbis.sounds.playHit();
    	}
    }
}
