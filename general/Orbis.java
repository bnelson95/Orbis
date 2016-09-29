package general;

//General imports
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;

//Local imports
import states.*;


/**
 * Main traffic controller for Orbis.  
 * Initializes all components, maintains state machine, 
 * and the main loop.  Dev commands are also included.
 *  
 * @author Samuel Barr & Blake Nelson
 * @version Unis 2.0
 */

public class Orbis extends JPanel implements MouseListener, KeyListener
{
    public static final String Version = "Unis 2.0";
    
    public static final int GAME_SIZE = 700;
    public static final int FRAME_RATE = 30;
    
    public static JFrame frame;   
    public static ArrayList<GameState> states;
    public static int state;
    
    public static Error error;
    public static Sound sounds;
    
    public static ArrayList<Integer> keyStroke;
    
    //Dev controls
    public static boolean command;
    public static boolean devMode;
    public static boolean invincibility;
    public static boolean noSpawn;
    public static boolean freeze;
    
    
    /**
     * Constructor.  Creates JFrame, and sets it in place.
     */
    public Orbis()
    {
        initialize();
        
    	//Create basic frame.
        frame = new JFrame("Orbis");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        //Align the frame based on the insets (with a throw-away frame).
        JFrame blah = new JFrame();
        blah.pack();
        Insets insets = blah.getInsets();
        blah = null;
        frame.setSize(new Dimension(GAME_SIZE + insets.right, GAME_SIZE + insets.top));
        
        //Sets the location of the frame to the middle of the screen.
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int width = (int) screenSize.getWidth();
        int height = (int) screenSize.getHeight();
        frame.setLocation(width/2 - GAME_SIZE/2, height/2 - GAME_SIZE/2);
        
        frame.setResizable(false);       
        frame.setBackground(Settings.themes.background);
        frame.add(states.get(state));
        frame.setVisible(true);
        frame.addKeyListener(this);
    }
    
    
    /**
     * Initializes and creates all main game components.
     */
    public void initialize()
    {   
        states = new ArrayList<GameState>();
        command = false;
        devMode = false;
        error = new Error();
        
        states.add(new Settings());  //0
        sounds = new Sound(Settings.currentSongVol, Settings.currentEffectsVol);
        states.add(new Splash());    //1
        states.add(new Menu());      //2
        states.add(new Game());      //3
        states.add(new Upgrade());   //4
        states.add(new GameOver());  //5
        states.add(new Help());      //6
        states.add(new HighScore()); //7   
        states.add(new Stats());     //8
        states.add(new Info());      //9
                       
        
        
        for (int i = 0; i < states.size(); i++)
            states.get(i).addMouseListener(this);
        
        state = 1;
        keyStroke = new ArrayList<Integer>();   
        
        
    }
    
    /**
     * Main game loop. 
     * Checks gameState and updates accordingly @ 30 frames/sec. 
     */
    public void runOrbis()
    {
    	
        long startMs;
        long endMs;
        long delayMs;
        int frameMs = 1000 / FRAME_RATE;
        while (true)
        {
        	startMs = System.currentTimeMillis();   
        	
        	states.get(state).step();
        	frame.repaint();
            
            if (devMode) devCommands();
            
            //Stabilizes frame rate.
            endMs = System.currentTimeMillis();
            delayMs = frameMs - (endMs - startMs);
            Orbis.delay(delayMs);
        }
        
        
        
        /*
    	final int TICKS_PER_SECOND = 60;
        final int SKIP_TICKS = 1000 / TICKS_PER_SECOND;
        final int MAX_FRAMESKIP = 10;

        long next_game_tick = System.currentTimeMillis();
        int loops;

        boolean game_is_running = true;
        while( game_is_running ) {

            loops = 0;
            while( System.currentTimeMillis() > next_game_tick && loops < MAX_FRAMESKIP) {
            	states.get(state).step();

                next_game_tick += SKIP_TICKS;
                loops++;
            }

            frame.repaint();
        }
    	*/
    }
    
    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        states.get(state).paintComponent(g2);      
    }
    
    public static void main(String[] args)
    {
        Orbis orbis = new Orbis();
        orbis.runOrbis();
    }
    
    /**
     * Method to change the state of the game.
     * 
     * @param s String to change.
     */
    public static void changeState(String s)
    {
        frame.remove(states.get(state));
        switch (s){
        case "SETTINGS":  state = 0; break;
        case "SPLASH":    state = 1; break;
        case "MENU":      state = 2; break;
        case "GAME":      state = 3; break;
        case "UPGRADE":   state = 4; break;
        case "GAMEOVER":  state = 5; break;
        case "HELP":      state = 6; break;
        case "HIGHSCORE": state = 7; break;
        case "STATS":     state = 8; break;
        case "INFO":      state = 9; break;           
        }
    	
    	frame.add(states.get(state));
    	states.get(state).transition();
        Orbis.frame.invalidate();
        Orbis.frame.validate();
    }
    
    /**
     * Method to make sure the screen is in focus.
     */
    public static void checkScreen()
    {
    	if(!frame.isFocused())
    	{
    		Orbis.sounds.getGameSong().stop(); 		
    		changeState("UPGRADE");            
    	}
    }
    
    /**
     * Delay method.
     * @param delay amount (in milliseconds) to delay.
     */
    public static void delay(long delay)
    {
        long endTime;
        endTime = System.currentTimeMillis() + delay;
        while (System.currentTimeMillis() < endTime);
    }
    
    /**
     * Method to detect and carry out various developer's commands.
     */
    public static void devCommands()
    {
    	if(keyStroke.contains(KeyEvent.VK_CONTROL))
    	{
    		if (keyStroke.contains(KeyEvent.VK_I))
    			if(!invincibility)
                {
                    invincibility = true;
                    keyStroke.clear();
                }
                else if (invincibility)
                {
                	invincibility = false;
                	keyStroke.clear();
                } 
    		
    		if (keyStroke.contains(KeyEvent.VK_N))
    			if(!noSpawn){
                    noSpawn = true;
                    keyStroke.clear();
                } else if (noSpawn){
                	noSpawn = false;
                	keyStroke.clear();
                }
    		
    		if (keyStroke.contains(KeyEvent.VK_F))
                if(!freeze){
                    freeze = true;
                    keyStroke.clear();
                } else {
                    freeze = false;
                    keyStroke.clear();
                } 

    		if (keyStroke.contains(KeyEvent.VK_G))
    		{
    			Game.optimus.setSize(Game.optimus.getSize() + 10);
    			keyStroke.clear();
    		}

    		if (keyStroke.contains(KeyEvent.VK_S))
    		{
    			Game.optimus.setSpeed(Game.optimus.getSpeed() + 2);
    			keyStroke.clear();
    		}
    		
    		if (keyStroke.contains(KeyEvent.VK_C))
    		{
    			Upgrade.reset();
    			keyStroke.clear();
    		}
    		if (keyStroke.contains(KeyEvent.VK_K))
    		{
    		    Game.killCount += 50;
    		    keyStroke.clear();
    		}
    	}
    }
    
    @Override
    public void keyPressed(KeyEvent e)
    {
        if (!keyStroke.contains(e.getKeyCode()))
        {  
            keyStroke.add(new Integer(e.getKeyCode()));
            
            if (e.getKeyCode() == KeyEvent.VK_CONTROL && state == 1)
            {
            	command = true;
            	sounds.playHit();
            	keyStroke.clear();
            }
        }
    }
    @Override
    public void keyReleased(KeyEvent e)
    {
    	if (!command)
        keyStroke.remove(new Integer(e.getKeyCode()));
    }
    @Override
    public void keyTyped(KeyEvent arg0){}
    
    @Override
    public void mouseClicked(MouseEvent arg0){}
    
    @Override
    public void mouseEntered(MouseEvent arg0){}
    
    @Override
    public void mouseExited(MouseEvent arg0){}

    @Override
    public void mousePressed(MouseEvent e)
    {
    	states.get(state).mousePressed(e);
    }

    @Override
    public void mouseReleased(MouseEvent e)
    {
    	states.get(state).mouseReleased(e);
    }
}
