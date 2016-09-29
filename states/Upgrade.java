package states;

//General imports
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

//Local imports
import general.Orbis;
import objects.LetalisOrb;
import states.GameOver;
import tools.OButton;
import tools.PopUp;

/**
 * Class to give Optimus various upgrades.
 * Doubles as pause screen.
 * 
 * @author Samuel Barr & Blake Nelson
 */
@SuppressWarnings("serial")
public class Upgrade extends GameState
{
    public static final int INIT_DMG  = 400;
    public static final int INIT_MULT = 800;
    public static final int INIT_TRK  = 200;
    public static final int INIT_SPD  = 400;
    public static final int INIT_SHLD = 200;
    public static final int INIT_ATTR = 400;
    
    public static int bullet_damage = 0;
    public static int bullet_multi = 0;
    public static int bullet_tracking = 0;
   
    public static int player_speed = 0;
    public static int player_shield = 0;
    public static int player_attract = 0;
    
    private static int damageCost;
    private static int multiCost;
    private static int trackingCost;    
    private static int speedCost;
    private static int shieldCost;
    private static int attractCost;
    
    public static long startTime;
    
    private OButton damageUp;
    private OButton trackingUp;
    private OButton multiUp;
    
    private OButton speedUp;
    private OButton shieldUp;
    private OButton attractUp;
    
    //Pop-up for menu and quit.
    private PopUp pop;
    private boolean popQuit;
    
    private OButton backButton;
    private OButton menuButton;
    private OButton quitButton;

    private boolean resume;
    private boolean menu;
    private boolean quit;

    public Upgrade()
    {   
        damageCost =   INIT_DMG ;
        multiCost =    INIT_MULT;
        trackingCost = INIT_TRK ;
        speedCost =    INIT_SPD ;
        shieldCost =   INIT_SHLD;
        attractCost =  INIT_ATTR;
        
        damageUp =   new OButton(damageCost+"", 50, 225, 50, Settings.themes.parvusColor);
        trackingUp = new OButton(trackingCost+"", 50, 475, 50, Settings.themes.parvusColor);
        multiUp =    new OButton(multiCost+"", 50, 350, 50, Settings.themes.parvusColor);
        
        speedUp =   new OButton(speedCost+"", 400, 225, 50, Settings.themes.optimusColor);
        shieldUp =  new OButton(shieldCost+"", 400, 350, 50, Settings.themes.optimusColor);
        attractUp = new OButton(attractCost+"", 400, 475, 50, Settings.themes.optimusColor);
        
        backButton = new OButton("Resume", Orbis.GAME_SIZE / 2, 625, 100, Settings.themes.optimusColor);
        menuButton = new OButton("Menu", Orbis.GAME_SIZE / 2 - 113, 625, 75, Settings.themes.parvusColor);
        quitButton = new OButton("Quit", Orbis.GAME_SIZE / 2 + 113, 625, 75, Settings.themes.parvusColor);        
    }
    
    public void initButtons()
    {
        damageUp.setLabel  (damageCost+""  );
        trackingUp.setLabel(trackingCost+"");
        multiUp.setLabel   (multiCost+""   );
        speedUp.setLabel   (speedCost+""   );
        shieldUp.setLabel  (shieldCost+""  );
        attractUp.setLabel (attractCost+"" );
    }
    
    public void transition()
    {
        Orbis.sounds.playPop();
        Orbis.sounds.playMenuSong();
    }
            

    /**
     * Checks for keystrokes.
     */
	public void step()
    {
    	if (Game.running)
    		startTime = System.currentTimeMillis();
    	Game.running = false;
    	
        if (Orbis.keyStroke.contains(KeyEvent.VK_SPACE) || resume)
        { 
            resume = false;
            Orbis.sounds.playPop();
            Orbis.keyStroke.clear(); 
            Game.pauseTime += (System.currentTimeMillis() - startTime);            
            Orbis.changeState("GAME");
        }
        if (menu)
        {
            menu = false;
            Orbis.sounds.getGameSong().stop();
            Orbis.sounds.playMenuSong();
            Orbis.changeState("MENU");
        }
        if (quit)
        {
            quit = false;
            Orbis.sounds.playPop();
            GameOver.updateStats();
            Orbis.frame.dispose();
            System.exit(0);
        }
        Orbis.sounds.muteSong();
    }

    public void paintComponent(Graphics g)
    {
        ((Graphics2D) g).setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        ((Graphics2D) g).setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON); 
        ((Graphics2D) g).setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS, RenderingHints.VALUE_FRACTIONALMETRICS_ON);

        g.setFont(new Font("Courier New", Font.PLAIN, 50));
        g.setColor(Settings.themes.textColor);
        String paused = "PAUSED";
        int pausedX = (Orbis.GAME_SIZE / 2) - (g.getFontMetrics().stringWidth(paused) / 2);
        int pausedY = 50;
        g.drawString(paused, pausedX, pausedY);

        // Draw lines
        g.drawLine(25, 75, 675, 75);
        g.drawLine(25, 150, 675, 150);
        g.drawLine(350, 175, 350, 525);
        g.drawLine(25, 550, 675, 550);
        
        
        g.setFont(new Font("Courier New", Font.BOLD, 25));
        
        //Draw Tokens.
        String tokens;
        if(Orbis.devMode)
            tokens = "Tokens: --";
        else
            tokens = "Tokens: " + Game.tokens;
        int tokenX = (Orbis.GAME_SIZE / 2) - (g.getFontMetrics().stringWidth(tokens) / 2);
        int tokenY = 125 - (g.getFontMetrics().getHeight() / 4);
        g.drawString(tokens, tokenX, tokenY);
        
        // Draw upgrade labels and buttons
        g.drawString("Damage:    " + bullet_damage, 100, 225);
        g.drawString("Multi:     " + bullet_multi, 100, 350);
        g.drawString("Tracking:  " + bullet_tracking, 100, 475);
        
        g.drawString("Speed:     " + player_speed, 450, 225);
        g.drawString("Shield:    " + player_shield, 450, 350);
        g.drawString("Attract:   " + player_attract, 450, 475);
        
        //Draw Descriptions
        g.setFont(new Font("Courier New", Font.BOLD, 15));
        g.drawString("Boosts Bullet Damage +5", 100, 250);
        g.drawString("(Current Damage: " + LetalisOrb.DAMAGE + ")", 100, 265);
        
        g.drawString("Increases Bullets per Shot", 100, 375);
        
        g.drawString("Bullets Track Enemies", 100, 500);
        
        g.drawString("Increases Your Speed", 450, 250);
        g.drawString("(Decreases Enemy Speed)", 450, 265);
        
        g.drawString("Activates Shield Drops", 450, 375);
        g.drawString("(Gives +1 Max Shield)", 450, 390);
        
        g.drawString("All Drops Attract to You", 450, 500);
        
        damageUp.draw(g);
        trackingUp.draw(g);
        multiUp.draw(g);
        
        speedUp.draw(g);
        shieldUp.draw(g);
        attractUp.draw(g);
        
        
        backButton.draw(g);
        menuButton.draw(g);
        quitButton.draw(g);
    }
    
    public static void reset()
    {
        bullet_damage = 0;    
        bullet_tracking = 0;
        bullet_multi = 0;   
                            
        player_speed = 0;   
        player_shield = 0;  
        player_attract = 0;
        
        damageCost =   INIT_DMG ;
        multiCost =    INIT_MULT;
        trackingCost = INIT_TRK ;
        speedCost =    INIT_SPD ;
        shieldCost =   INIT_SHLD;
        attractCost =  INIT_ATTR;
    }



    @Override
    public void mouseClicked(MouseEvent arg0){}

    @Override
    public void mouseEntered(MouseEvent arg0){}

    @Override
    public void mouseExited(MouseEvent arg0){}

    @Override
    public void mousePressed(MouseEvent arg0){}

    @Override
    public void mouseReleased(MouseEvent e)
    {
        if (damageUp.checkClicked(e.getX(), e.getY()) && (Game.tokens >= damageCost || Orbis.devMode))
        { bullet_damage++;  Game.tokens -= damageCost; Orbis.sounds.playGrow(); damageCost += INIT_DMG/2;}
        
        if (multiUp.checkClicked(e.getX(), e.getY()) && (Game.tokens >= multiCost || Orbis.devMode))
        { bullet_multi++;  Game.tokens -= multiCost; Orbis.sounds.playGrow(); multiCost += INIT_MULT/2;}
        
        if (trackingUp.checkClicked(e.getX(), e.getY()) && (Game.tokens >= trackingCost || Orbis.devMode))
        { bullet_tracking++; Game.tokens -= trackingCost; Orbis.sounds.playGrow();  trackingCost += INIT_TRK/2;}
        
        if (speedUp.checkClicked(e.getX(), e.getY()) && (Game.tokens >= speedCost || Orbis.devMode))
        { player_speed++;   Game.tokens -= speedCost; Orbis.sounds.playGrow(); speedCost += INIT_SPD/2;}
        
        if (shieldUp.checkClicked(e.getX(), e.getY()) && (Game.tokens >= shieldCost || Orbis.devMode))
        { player_shield++;  Game.tokens -= shieldCost; Game.optimus.setShield(Game.optimus.getShield() + 1);
            Orbis.sounds.playGrow(); shieldCost += INIT_SHLD/2;
        }
        
        if (attractUp.checkClicked(e.getX(), e.getY()) && (Game.tokens >= attractCost || Orbis.devMode))
        { player_attract++; Game.tokens -= attractCost; Orbis.sounds.playGrow(); attractCost += INIT_ATTR/2;}
    	
        initButtons();
        
    	if (backButton.checkClicked(e.getX(), e.getY())) { resume = true; }
    	if (menuButton.checkClicked(e.getX(), e.getY())) { menu   = true; }
    	if (quitButton.checkClicked(e.getX(), e.getY())) 
    	{
    		Orbis.sounds.playPop();
    		pop = new PopUp("You Quitter", "Are you sure you want to quit?",
    				"(All progress will be lost!)");
    		pop.addMouseListener(this);
    		popQuit = true;
    	}

    	if (pop != null)
    	{
    		if (PopUp.accept.checkClicked(e.getX(), e.getY()))
    		{   			
    			if (popQuit){
    				Orbis.sounds.playPop();
    				Orbis.frame.dispose();
        			System.exit(0);
    			} else {
    				PopUp.frame.dispose();
    				menu = true;   				
    			}
    			
    		}
    		if (PopUp.reject.checkClicked(e.getX(), e.getY()))
    		{
    			Orbis.sounds.playPop();
    			PopUp.frame.dispose();
    			pop = null;
    		}
    	}
    }

}
