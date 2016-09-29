package states;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

import general.*;
import tools.*;


public class Settings extends GameState
{
	private OButton classicTheme;
    private OButton randomTheme;
    private OButton chillTheme;
    private OButton warmTheme;
    private OButton BWTheme;
    private OButton holidayTheme;
    
    private OButton songVolLabel;
    private OButton songVolUp;
    private OButton songVolDown;
    private OButton effectsVolLabel;
    private OButton effectsVolUp;
    private OButton effectsVolDown;
    
    private OButton particlesButton;
     
    public static Themes themes;
    
    public static String filePath;
    private File directory;
    private static File settings;
    public static File file;
    public static Scanner inFile;
    
    public static String currentTheme;
    public static int currentSongVol;
    public static int currentEffectsVol;
    public static String currentParticles;
    public static int currentFrameRate;
    
    private OButton backButton;
    private OButton resetButton;
    private OButton restartButton;
    
    private PopUp pop;

    public Settings()
    {
        themes = new Themes();       
        
        if (System.getProperty("os.name").contains("Windows"))
            filePath = "C:/Users/" + System.getProperty("user.name") + "/AppData/Local";
        else if (System.getProperty("os.name").contains("Mac"))
            filePath = "/Users/"+ System.getProperty("user.name") +"/Library/Application Support";
        
        directory = new File(filePath + "/Orbis/");
        settings = new File(filePath + "/Orbis/Settings.txt");
        if(!directory.exists())
        {
            try{directory.mkdir();} 
            catch (Exception e)
                {Orbis.error.writeError("Error in Settings/Constructor: New Directory Failed");}
        }
        if (!settings.exists())
        {
            PrintWriter pw;
            try
            {
                pw = new PrintWriter(settings);
                pw.println("Classic");
                pw.println("8");
                pw.println("8");
                pw.println("On");
                pw.println("30");
                pw.close();
            } catch (FileNotFoundException e){Orbis.error.writeError("Error in Settings/Constructor: File not found");
            e.printStackTrace();}
            
        }

        readFile();
        updateTheme();
        
        String tempTheme = currentTheme;
        Settings.themes.classic();
        classicTheme = new OButton("Classic", Orbis.GAME_SIZE/2 - 250, 175, 75, themes.optimusColor);
        Settings.themes.random();
        randomTheme =  new OButton("Random",  Orbis.GAME_SIZE/2 - 150, 175, 75, themes.optimusColor);
        Settings.themes.chill();
        chillTheme =   new OButton("Chill",   Orbis.GAME_SIZE/2 - 50,  175, 75, themes.amoveoColor);
        Settings.themes.warm();
        warmTheme =    new OButton("Warm",    Orbis.GAME_SIZE/2 + 50,  175, 75, themes.magnusColor);
        Settings.themes.blackAndWhite();
        BWTheme =      new OButton("B & W",   Orbis.GAME_SIZE/2 + 150, 175, 75, themes.optimusColor);
        Settings.themes.holiday();
        holidayTheme = new OButton("Holiday", Orbis.GAME_SIZE/2 + 250, 175, 75, themes.optimusColor);
        
        currentTheme = tempTheme;
        updateTheme();
        
        
        //initVolLabels();
        songVolUp = new OButton(">", Orbis.GAME_SIZE/4 + 75, 338, 50, themes.magnusColor);
        songVolDown = new OButton("<", Orbis.GAME_SIZE/4 - 75, 338, 50, themes.magnusColor);
        
        effectsVolUp = new OButton(">", Orbis.GAME_SIZE/2  + Orbis.GAME_SIZE/4 + 75, 338, 50, themes.magnusColor);
        effectsVolDown = new OButton("<", Orbis.GAME_SIZE/2  + Orbis.GAME_SIZE/4 - 75, 338, 50, themes.magnusColor);
                
        initParticleLabel();
        
        backButton =    new OButton("Back", Orbis.GAME_SIZE / 2, 625, 100, themes.parvusColor);
        restartButton = new OButton("Restart", Orbis.GAME_SIZE - 100, 625, 100, themes.parvusColor);
        resetButton =   new OButton("Reset", 100, 625, 100, themes.praedoColor);
    }
    
    public void transition()
    {
        Orbis.sounds.playPop();
    }
    
    public void step() {}
    
    public void initVolLabels()
    {
        songVolLabel = null;
        songVolLabel = new OButton(currentSongVol + "", Orbis.GAME_SIZE/4, 338, 75, themes.amoveoColor);
        effectsVolLabel = null;
        effectsVolLabel = new OButton(currentEffectsVol + "", Orbis.GAME_SIZE/2 + Orbis.GAME_SIZE/4, 338, 75, themes.amoveoColor);
        if(Orbis.frame != null)
        Orbis.frame.repaint();
    }
    
    public void initParticleLabel()
    {
    	particlesButton = null;
    	particlesButton = new OButton(currentParticles, Orbis.GAME_SIZE / 2, 465, 75, themes.optimusColor);
    	if(Orbis.frame != null)
    	Orbis.frame.repaint();
    }
    
    public void paintComponent(Graphics g)
    {
        ((Graphics2D) g).setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        ((Graphics2D) g).setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON); 
        ((Graphics2D) g).setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS, RenderingHints.VALUE_FRACTIONALMETRICS_ON);
        
        //Draws heading
        g.setFont(new Font("Courier New", Font.PLAIN, 50));
        g.setColor(Settings.themes.textColor);
        String paused = "SETTINGS";
        int pausedX = (Orbis.GAME_SIZE / 2) - (g.getFontMetrics().stringWidth(paused) / 2);
        int pausedY = 50;
        g.drawString(paused, pausedX, pausedY);
        
        //Draws lines
        g.drawLine(25, 75, 675, 75);
        g.drawLine(25, 238, 675, 238);
        g.drawLine(25, 238, 675, 238);
        g.drawLine(25, 550, 675, 550);
        g.drawLine(350, 263, 350, 388);
        g.drawLine(25, 413, 675, 413);
        
        //"Themes"
        g.setFont(new Font("Courier New", Font.BOLD, 25));      
        String themes = "Themes";
        int themesX = (Orbis.GAME_SIZE / 2) - (g.getFontMetrics().stringWidth(themes) / 2);
        int themesY = 125 - (g.getFontMetrics().getHeight() / 4);
        g.drawString(themes, themesX, themesY);
        
        //"Song Volume"
        g.setFont(new Font("Courier New", Font.BOLD, 25));      
        String vol = "Song Volume";
        int volX = (Orbis.GAME_SIZE / 4) - (g.getFontMetrics().stringWidth(vol) / 2);
        int volY = 288 - (g.getFontMetrics().getHeight() / 4);
        g.drawString(vol, volX, volY);
        
        //VolLabel
        g.setFont(new Font("Courier New", Font.PLAIN, 50));
        int sVolLX = (Orbis.GAME_SIZE / 4) - (g.getFontMetrics().stringWidth(currentSongVol+"") / 2);
        int sVolLY = 365 - (g.getFontMetrics().getHeight() / 4);
        g.drawString(currentSongVol+"", sVolLX, sVolLY);
        
        //"Effects Volume"
        g.setFont(new Font("Courier New", Font.BOLD, 25));      
        String vol1 = "Effects Volume";
        int vol1X = (Orbis.GAME_SIZE / 2 + Orbis.GAME_SIZE/4) - (g.getFontMetrics().stringWidth(vol1) / 2);
        int vol1Y = 288 - (g.getFontMetrics().getHeight() / 4);
        g.drawString(vol1, vol1X, vol1Y);
        
        //VolLabel
        g.setFont(new Font("Courier New", Font.PLAIN, 50));
        int eVolLX = (Orbis.GAME_SIZE / 2 + Orbis.GAME_SIZE / 4) - (g.getFontMetrics().stringWidth(currentEffectsVol+"") / 2);
        int eVolLY = 365 - (g.getFontMetrics().getHeight() / 4);
        g.drawString(currentEffectsVol+"", eVolLX, eVolLY);
        
        //Particles option
        g.setFont(new Font("Courier New", Font.BOLD, 25));      
        String par = "Particles:";
        int parX = (Orbis.GAME_SIZE / 4) - (g.getFontMetrics().stringWidth(par) / 2);
        int parY = 460 - (g.getFontMetrics().getHeight() / 4);
        g.drawString(par, parX, parY);
        
        //"Restart" Notice
        g.setFont(new Font("Courier New", Font.PLAIN, 20));       
        String info = "*Restart game for theme changes to take effect*";
        int infoX = (Orbis.GAME_SIZE / 2) - (g.getFontMetrics().stringWidth(info) / 2);
        int infoY = 525;
        g.drawString(info, infoX, infoY);
        
        
        //Draws buttons
        classicTheme.draw(g);
        randomTheme.draw(g);
        chillTheme.draw(g); 
        warmTheme.draw(g);
        BWTheme.draw(g);
        holidayTheme.draw(g);
        
        //Volume controls
        //songVolLabel.draw(g);
        songVolUp.draw(g);
        songVolDown.draw(g);
        
        //effectsVolLabel.draw(g);
        effectsVolUp.draw(g);
        effectsVolDown.draw(g);
        
        particlesButton.draw(g);
        
        backButton.draw(g);
        resetButton.draw(g);
        restartButton.draw(g);
    }
    
    public static void updateTheme()
    {
        switch(currentTheme)
        {
        case "Classic":
            themes.classic();
            break;
        case "Random":
            themes.random();
            break;
        case "Chill":
            themes.chill();
            break;
        case "Warm":
        	themes.warm();
        	break;
        case "B&W":
            themes.blackAndWhite();
            break;
        case "Holiday":
        	themes.holiday();
            break;
        }
    }
    
    /**
     * Overwrites file with new credentials.
     * DO NOT call this method without reading the file first.
     */
    public static void updateFile()
    {
        PrintWriter outFile = null;
        try
        {
            outFile = new PrintWriter(settings.getPath());
        }
        catch (FileNotFoundException e)
        {
        	Orbis.error.writeError("Error in Settings/updateFile: PrintWriter Failed");
        }

        outFile.println(currentTheme);
        outFile.println(currentSongVol);
        outFile.println(currentEffectsVol);
        outFile.println(currentParticles);
        outFile.close();    
    }
    
    /**
     * Reads the Settings file.
     * Initial values are placed if the file is missing data.
     */
    public void readFile()
    {
    	try {inFile = new Scanner(settings);}
        catch(Exception e){
        	Orbis.error.writeError("Error in Settings/Constructor: Scanner for file failed");
            e.printStackTrace();
        }
    	if (inFile.hasNext())
        currentTheme = inFile.nextLine();
    	else currentTheme = "Classic";
    	
        if (inFile.hasNext())
        	currentSongVol = inFile.nextInt();
        else currentSongVol = 8;
        
        if (inFile.hasNext())
        	currentEffectsVol = inFile.nextInt();
        else currentEffectsVol = 8;
        
        if (inFile.hasNextLine())
        {
            inFile.nextLine();
        	currentParticles = inFile.nextLine();
        }         
        	
    }
    
    /**
     * Scraps the Sound object for Orbis, and recreates it.
     * Used to reinitialize game volumes.
     */
    public void updateSounds()
    {
        Orbis.sounds.closeSongs();
        Orbis.sounds = null;
        Orbis.sounds = new Sound(currentSongVol, currentEffectsVol);
    }

    @Override
    public void mouseClicked(MouseEvent arg0) {}

    @Override
    public void mouseEntered(MouseEvent arg0){}

    @Override
    public void mouseExited(MouseEvent arg0){}

    @Override
    public void mousePressed(MouseEvent arg0){}

    @SuppressWarnings("static-access")
	@Override
    public void mouseReleased(MouseEvent e)
    {
        //Theme Selections
        if (classicTheme.checkClicked(e.getX(), e.getY()))
        {
            Orbis.sounds.playGrow();
            currentTheme = "Classic";
            updateTheme();
        }
        if (randomTheme.checkClicked(e.getX(), e.getY()))
        {
            Orbis.sounds.playGrow();
            currentTheme = "Random";
            updateTheme();
        }
        if (chillTheme.checkClicked(e.getX(), e.getY()))
        {
            Orbis.sounds.playGrow();
            currentTheme = "Chill";
            updateTheme();
        }
        if (warmTheme.checkClicked(e.getX(), e.getY()))
        {
        	Orbis.sounds.playGrow();
            currentTheme = "Warm";
            updateTheme();
        }
        if (BWTheme.checkClicked(e.getX(), e.getY()))
        {
            Orbis.sounds.playGrow();
            currentTheme = "B&W";
            updateTheme();
        }
        if (holidayTheme.checkClicked(e.getX(), e.getY()))
        {
            Orbis.sounds.playGrow();
            currentTheme = "Holiday";
            updateTheme();
        }

        //Volume Controls
        if(songVolUp.checkClicked(e.getX(), e.getY()))
        {          
            if (currentSongVol < 10)
            {
                Orbis.sounds.playHit();
                currentSongVol++;
                initVolLabels();
                updateSounds();
            }
        }
        if(songVolDown.checkClicked(e.getX(), e.getY()))
        {            
            if (currentSongVol > 0)
            {
                Orbis.sounds.playHit();
                currentSongVol--;
                initVolLabels();
                updateSounds();
            }
        }
        if(effectsVolUp.checkClicked(e.getX(), e.getY()))
        {           
            if (currentEffectsVol < 10)
            {
                Orbis.sounds.playHit();
                currentEffectsVol++;
                initVolLabels();
                updateSounds();
            }
        }
        if(effectsVolDown.checkClicked(e.getX(), e.getY()))
        {
            if (currentEffectsVol > 0)
            {
                Orbis.sounds.playHit();
                currentEffectsVol--;
                initVolLabels();
                updateSounds();
            }
        }
        
        if (particlesButton.checkClicked(e.getX(), e.getY()))
        {
        	Orbis.sounds.playHit();
        	if (currentParticles.equals("On"))
        		currentParticles = "Extra";
        	else if (currentParticles.equals("Extra"))
        		currentParticles = "Off";
        	else if (currentParticles.equals("Off"))
        	    currentParticles = "On";
        	initParticleLabel();
        }
        
        

        if (backButton.checkClicked(e.getX(), e.getY()))
        {
        	Orbis.changeState("MENU");
        }

        if (restartButton.checkClicked(e.getX(), e.getY()))
        {
            try
            {
                Runtime.getRuntime().exec("java -jar Orbis.jar");
            } catch (IOException i)
            {
                Orbis.error.writeError("Error in Settings/Restart: Restart Failed");
            }
            System.exit(0);
        }

        if (resetButton.checkClicked(e.getX(), e.getY()))
        {

        	Orbis.sounds.playPop();
        	pop = new PopUp("Reset", "Reset your stats and scores?",
        			"(This cannot be undone)");
        	pop.setLabels("Reset", "No");
        	pop.addMouseListener(this);       	           
        }

        if (pop != null)
        {
        	if (pop.accept.checkClicked(e.getX(), e.getY()))
        	{
        	    Orbis.sounds.playPop();
        		HighScore.reset();
        		Stats.reset();
        		pop.frame.dispose();
                pop = null;
        	}
        	if (pop.reject.checkClicked(e.getX(), e.getY()))
        	{
        		Orbis.sounds.playPop();
        		pop.frame.dispose();
        		pop = null;
        	}          
        }

        updateFile();
    }

}
