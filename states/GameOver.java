package states;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

import javax.sound.sampled.Clip;
import javax.swing.JOptionPane;

import general.Orbis;
import tools.OButton;
import tools.PopUp;


@SuppressWarnings("serial")
public class GameOver extends GameState
{
    public static final String NAME = "GAMEOVER";
    
	private OButton menuButton;
	private OButton quitButton;
	private OButton restartButton;
	
	private boolean restart;
	private boolean menu;
	private boolean quit;
	
	PopUp pop;
	
	public GameOver()
	{
		restartButton = new OButton("Restart", Orbis.GAME_SIZE / 2, 625, 100, Settings.themes.optimusColor);
        menuButton =    new OButton("Menu", Orbis.GAME_SIZE / 2 - 113, 625, 75, Settings.themes.parvusColor);
        quitButton =    new OButton("Quit", Orbis.GAME_SIZE / 2 + 113, 625, 75, Settings.themes.parvusColor);
	}
	
	public void transition()
	{
	    updateStats();
        updateScore();
        Orbis.sounds.playPop();
        Orbis.sounds.getGameSong().stop();
        Orbis.sounds.playSad();
	}
	
	public void step()
	{    
        if (Orbis.keyStroke.contains(KeyEvent.VK_R) || restart)
        {
        	Orbis.sounds.playPop();
        	if (!Orbis.sounds.muteSong)
        	{
        		Orbis.sounds.getGameSong().start();
        		Orbis.sounds.getGameSong().loop(Clip.LOOP_CONTINUOUSLY);
        	}
        	Game.resetAll();
        	Orbis.changeState("GAME");
        	restart = false;
        }
        if (Orbis.keyStroke.contains(KeyEvent.VK_M) || menu)
        {
        	Orbis.sounds.playPop();
        	Orbis.changeState("MENU");

        	Game.resetAll();
        	menu = false;
        }
        if (Orbis.keyStroke.contains(KeyEvent.VK_Q) || quit)
        {
        	Game.resetAll();
        	Orbis.frame.dispose();
        	System.exit(0);
        }             
	}
	
	public static void updateScore()
	{		
        if (HighScore.checkScore(Game.score))
        {
            String name;
            String error = "";
            do
            {
                name = "Player";
                if (name.length() > 10) {error = "NAME TOO LONG";}
                
                name = JOptionPane.showInputDialog("High Score: " + Game.score + "\n" + error + "\nEnter your name: ", "Player");
                if (name == null || name.equals("") || name.equalsIgnoreCase("null")) name = "Player";
            } while (name.length() > 10);
            
            
                       
            HighScore.updateScore(name, Game.killCount, Game.score);
        }
	}
	
    public static void updateStats()
    {
    	Stats.updateStats(Game.killCount, Game.score, Game.totalTime, Game.bulletsFired);
    }

	public void paintComponent(Graphics g)
    {
        ((Graphics2D) g).setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        ((Graphics2D) g).setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON); 
        ((Graphics2D) g).setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS, RenderingHints.VALUE_FRACTIONALMETRICS_ON);        
        Game.paintObjects(g);
        
        g.setColor(Settings.themes.textColor);  
        g.setFont(new Font("Courier New", Font.PLAIN, 50));
        String header = "GAME OVER";
        int headerX = (Orbis.GAME_SIZE / 2) - (g.getFontMetrics().stringWidth(header) / 2);
        int headerY = 50;
        g.drawString(header, headerX, headerY);
        
        g.drawLine(25, 75, 675, 75);
        g.drawLine(Orbis.GAME_SIZE/2, 100, Orbis.GAME_SIZE/2, 525);
        g.drawLine(25, 550, 675, 550);  

        //Draw Labels
        String stat1 = "SCORE";
        String stat2 = "KILLCOUNT";
        String stat3 = "BULLETS FIRED";
        String stat4 = "ACCURACY";
        String stat5 = "TIME";
        g.setFont(new Font("Courier New", Font.BOLD, 30));
        g.drawString(stat1, Orbis.GAME_SIZE/2 - 25 - g.getFontMetrics().stringWidth(stat1), 125);
        g.drawString(stat2, Orbis.GAME_SIZE/2 - 25 - g.getFontMetrics().stringWidth(stat2), 200);
        g.drawString(stat3, Orbis.GAME_SIZE/2 - 25 - g.getFontMetrics().stringWidth(stat3), 275);
        g.drawString(stat4, Orbis.GAME_SIZE/2 - 25 - g.getFontMetrics().stringWidth(stat4), 350);
        g.drawString(stat5, Orbis.GAME_SIZE/2 - 25 - g.getFontMetrics().stringWidth(stat5), 425);
        
        //Draw values
        g.setFont(new Font("Courier New", Font.PLAIN, 30));
        g.drawString(String.format("%,d", Game.score), Orbis.GAME_SIZE/2 + 25, 125);
        g.drawString(String.format("%,d", Game.killCount), Orbis.GAME_SIZE/2 + 25, 200);
        g.drawString(String.format("%,d", Game.bulletsFired), Orbis.GAME_SIZE/2 + 25, 275);
        g.drawString("" + String.format("%.1f%%", ((1 - ((Game.bulletsFired - Game.bulletsHit) / Game.bulletsFired))) * 100), 
                Orbis.GAME_SIZE/2 + 25, 350);
        g.drawString("" + Stats.convertTime(Game.totalTime), Orbis.GAME_SIZE/2 + 25, 425);
        
        
        menuButton.draw(g);
        restartButton.draw(g);
        quitButton.draw(g);
    }

	
	public void mouseClicked(MouseEvent arg0) { }
	public void mouseEntered(MouseEvent arg0) { }
	public void mouseExited(MouseEvent arg0) { }
	public void mousePressed(MouseEvent arg0) { }

	
	public void mouseReleased(MouseEvent e) 
	{
		if (menuButton.checkClicked(e.getX(), e.getY())){menu = true;}
		if (restartButton.checkClicked(e.getX(), e.getY())){restart = true;}
		if (quitButton.checkClicked(e.getX(), e.getY()))
		{
        	Orbis.sounds.playPop();
        	pop = new PopUp("You Quitter", "Are you sure you want to quit?",
        			"(We'll miss you!)");
        	pop.addMouseListener(this);       	           
        }
        
        if (pop != null)
        {
        	if (PopUp.accept.checkClicked(e.getX(), e.getY()))
        	{
        		Orbis.sounds.playPop();
                Orbis.frame.dispose();
                System.exit(0);
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

