package states;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseEvent;

import general.Orbis;
import objects.LetalisOrb;
import states.GameOver;
import tools.OButton;
import tools.PopUp;


public class Menu extends GameState
{
	private OButton setButton;
	private OButton infoButton;
    private OButton playButton;
    private OButton helpButton;
    private OButton splitButton;
    private OButton scoresButton;
    private OButton statsButton;
    private OButton quitButton;
    private LetalisOrb bullet;
    
    private PopUp pop;
    private boolean popQuit;

    public Menu()
    {
    	infoButton = new OButton("", -375, Orbis.GAME_SIZE/2, 800, Settings.themes.magnusColor);
    	playButton = new OButton("Play",     (Orbis.GAME_SIZE)/2, Orbis.GAME_SIZE/2,  200, Settings.themes.optimusColor);
    	
        setButton = new OButton("Settings", (Orbis.GAME_SIZE) + 25,  -25, 120, Settings.themes.amoveoColor);        
        
        splitButton = new OButton("Scores", (Orbis.GAME_SIZE/4), - 100,  150, Settings.themes.scindoColor);       
        
        helpButton = new OButton("Help",     (Orbis.GAME_SIZE/2) - 75, Orbis.GAME_SIZE + 50, 120, Settings.themes.praedoColor);
        quitButton = new OButton("Quit",     (Orbis.GAME_SIZE/2) + 200, Orbis.GAME_SIZE + 75, 120, Settings.themes.parvusColor);
    }
    
    public void transition()
    {
        Orbis.sounds.playPop();        
        Orbis.command = false;
        Orbis.keyStroke.clear();
    }
    
    public void step()
    {
    	if(playButton.getX() < (Orbis.GAME_SIZE) - 200)
    	playButton.move((Orbis.GAME_SIZE) - 200, Orbis.GAME_SIZE/2);
    	
    	if(infoButton.getX() < -250)
    		infoButton.move(-250, Orbis.GAME_SIZE/2);
    	
    	if(quitButton.getY() > Orbis.GAME_SIZE - 75)
    		quitButton.move(playButton.getX(), playButton.getY());
    	
    	if(helpButton.getY() > Orbis.GAME_SIZE - 150)
    		helpButton.move(playButton.getX(), playButton.getY());
    	
    	if(setButton.getY() < Orbis.GAME_SIZE/2 - 225)
    		setButton.move(playButton.getX(), playButton.getY());

    	if(splitButton != null && splitButton.getY() < Orbis.GAME_SIZE/2 - 235)
    	{
    		splitButton.move(playButton.getX(), playButton.getY());
    		if (splitButton.getY() > Orbis.GAME_SIZE/2 - 300 && bullet == null)
    		{
    		    Orbis.sounds.playShoot();
    			bullet = new LetalisOrb(playButton.getX(), playButton.getY(), Game.pointDirection(playButton.getX(), playButton.getY(), splitButton.getX(),splitButton.getY()));
    			Game.bulletsFired--;
    			bullet.setSize(10);
    			bullet.setSpeed(8);
    		}
    		else if (bullet != null)
    			bullet.step();
    	}
    	else if (scoresButton == null)
    	{
       		bullet = null;
    		Orbis.sounds.playHit();
    		splitButton = null;
    		scoresButton = new OButton("Scores", (Orbis.GAME_SIZE/2) - 100, Orbis.GAME_SIZE/2 - 175,  100, Settings.themes.scindoColor);
    		statsButton = new OButton("Stats", (Orbis.GAME_SIZE/2) + 15, Orbis.GAME_SIZE/2 - 275,  100, Settings.themes.scindoColor);
    	}


    }
    
    public void paintComponent(Graphics g)
    {
        ((Graphics2D) g).setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        ((Graphics2D) g).setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON); 
        ((Graphics2D) g).setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS, RenderingHints.VALUE_FRACTIONALMETRICS_ON);
        
        if (Orbis.devMode)
        {
        	 g.setColor(Color.BLACK);
             g.setFont(new Font("Courier New", Font.BOLD, 15));
             g.drawString("Dev Mode Active", 15, 20);
             g.drawString("Hello, " + System.getProperty("user.name") + ".", 15, 40);
        }
        	
        
        infoButton.draw(g);
        setButton.draw(g);
        
        helpButton.draw(g);
        if (splitButton != null)
        	splitButton.draw(g);
        
        if (scoresButton != null)
        	scoresButton.draw(g);
        
        if (statsButton != null)
        	statsButton.draw(g);
        
        if (bullet != null)
        	bullet.paint(g);
        
        quitButton.draw(g);
        
        /*
        if (playButton.checkClicked(MouseInfo.getPointerInfo().getLocation().x, MouseInfo.getPointerInfo().getLocation().y))
        {
            g.drawOval(playButton.getX()-playButton.getX()/2, playButton.getY()-playButton.getY()/2, playButton.getRadius()+10, playButton.getRadius()+10);
        }
        */
        playButton.draw(g);
    }

    @Override
    public void mouseClicked(MouseEvent arg0){}

    @Override
    public void mouseEntered(MouseEvent e){}

    @Override
    public void mouseExited(MouseEvent arg0){}

    @Override
    public void mousePressed(MouseEvent e)
    {
    	playButton.checkClicked(e.getX(), e.getY());
    	helpButton.checkClicked(e.getX(), e.getY());
    	infoButton.checkClicked(e.getX(), e.getY());
    	setButton.checkClicked(e.getX(), e.getY());
    	quitButton.checkClicked(e.getX(), e.getY());    	
    	if(statsButton != null)
    	{
            scoresButton.checkClicked(e.getX(), e.getY());
            statsButton.checkClicked(e.getX(), e.getY());
    	}
    }

    @Override
    public void mouseReleased(MouseEvent e)
    {
    	if (playButton.checkClicked(e.getX(), e.getY()))
    	{
    	    Orbis.sounds.playPop();
    	    if (Game.score > 0)
    	    {
    	        pop = new PopUp("Resume?", "Would you like to resume?",
                        "(Or you can start anew!)");
    	        pop.setLabels("Resume", "Restart");
                pop.addMouseListener(this); 
    	    }
    	    else
    	    {
    	        Orbis.changeState("GAME");
    	        Stats.gamesPlayed++;
    	    }
    	}
    	if (helpButton.checkClicked(e.getX(), e.getY()))
    	{
    		Orbis.changeState("HELP");
    	}
    	if (scoresButton != null && scoresButton.checkClicked(e.getX(), e.getY()))
    	{
    		Orbis.changeState("HIGHSCORE");
    	}
    	if (statsButton != null && statsButton.checkClicked(e.getX(), e.getY()))
    	{
    		Orbis.changeState("STATS");
    	}
    	if (infoButton.checkClicked(e.getX(), e.getY()))
    	{
    		Orbis.changeState("INFO");
    	}
    	if (setButton.checkClicked(e.getX(), e.getY()))
    	{
    		Orbis.changeState("SETTINGS");
    	}
    	if(quitButton.checkClicked(e.getX(), e.getY()))
    	{
        	Orbis.sounds.playPop();
        	popQuit = true;
        	if(Game.score > 0)
        	{
        	    pop = new PopUp("You Quitter", "Are you sure you want to quit?",
        	            "(All progress will be lost!)");
        	} else {
        	    pop = new PopUp("You Quitter", "Are you sure you want to quit?",
        			"(We'll miss you!)");
        	}
        	pop.addMouseListener(this);       	           
        }
        
        if (pop != null)
        {
        	if (PopUp.accept.checkClicked(e.getX(), e.getY()))
        	{
        	    if (popQuit)
        	    {
        	        Orbis.sounds.playPop();
        	        Orbis.frame.dispose();
        	        System.exit(0);
        	    }
        	    else
        	    {
        	        Orbis.sounds.playPop();
        	        Orbis.keyStroke.clear(); 
                    Game.pauseTime += (System.currentTimeMillis() - Upgrade.startTime); 
        	        Orbis.changeState("GAME");
        	        PopUp.frame.dispose();
        	    }
        	}
        	if (PopUp.reject.checkClicked(e.getX(), e.getY()))
        	{
        	    if (popQuit)
        	    {
        	        Orbis.sounds.playPop();
        	        PopUp.frame.dispose();
        	        pop = null;
        	        popQuit = false;
        	    }
        	    else
        	    {
        	        Orbis.sounds.playPop();
                    GameOver.updateStats(); 
                    Game.resetAll();
                    PopUp.frame.dispose(); 
                    Game.pauseTime += (System.currentTimeMillis() - Upgrade.startTime); 
                    Orbis.changeState("GAME");                   
        	    }
        	}
        }
    }
}
