package states;

//General imports
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;

//Local Imports
import general.*;
import tools.*;


public class Stats extends GameState
{
	private OButton backButton;
	public static int killCount;
	public static long score;
	public static long bulletsFired;
	public static long timePlayed;
	public static int gamesPlayed;
	public static float accuracy;
	
	private String filePath;	
	public static Scanner inFile;
	private File directory;
	public static File stats;
	
	/**
	 * Constructor.
	 * Creates directories/files if needed.
	 * Reads from necessary file.
	 */
	public Stats()
	{
		//Gets file directory.
		if (System.getProperty("os.name").contains("Windows"))
        {
        	filePath = "C:/Users/" + System.getProperty("user.name") + "/AppData/Local";
        }
        else if (System.getProperty("os.name").contains("Mac"))
    	{
    		filePath = "/Users/"+ System.getProperty("user.name") +"/Library/Application Support";
    	}
        
        directory = new File(filePath + "/Orbis/");
    	stats = new File(filePath + "/Orbis/Stats.txt");
    	
    	//If directory does not already exist, create it.
    	if(!directory.exists())
    	{
    		try
    		{
    			directory.mkdir();   			   			
    		} catch (Exception e){ System.out.println("New Directory Failure"); }
    	}
    	
    	//If the stats file does not already exist, create one with null values.
    	if (!stats.exists())
    	{
    		PrintWriter pw;
    		pw = null;
			try {
				pw = new PrintWriter(stats);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
    		pw.println("0");
			pw.println("0");
			pw.println("0");
			pw.println("0");
			pw.println("0");
			pw.println("0");

			pw.close();
    	}
    	
    	//Creates file scanner.
    	try{ inFile = new Scanner(stats);}
        catch(Exception e){e.printStackTrace();}
    	
    	//Reads file.
    	killCount = inFile.nextInt();
    	score = inFile.nextLong();
    	bulletsFired = inFile.nextLong();
    	timePlayed = inFile.nextLong();
    	gamesPlayed = inFile.nextInt();
    	
    	if(inFile.hasNext())
    	    accuracy = inFile.nextFloat();
    	else
    	    accuracy = 100;
    	
    	//Creates back button and closes file.
    	backButton = new OButton("Back", Orbis.GAME_SIZE / 2, 625, 100, Settings.themes.parvusColor);
        inFile.close();
        repaint();
	}
	
	public void transition()
	{
	    Orbis.sounds.playPop();
	}
	
	public void step() {}
	
	public void paintComponent(Graphics g)
    {
        ((Graphics2D) g).setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        ((Graphics2D) g).setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON); 
        ((Graphics2D) g).setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS, RenderingHints.VALUE_FRACTIONALMETRICS_ON);        
        
        g.setColor(Settings.themes.textColor);       
        g.setFont(new Font("Courier New", Font.PLAIN, 50));
        
        String header = "STATS";
        int headerX = (Orbis.GAME_SIZE / 2) - (g.getFontMetrics().stringWidth(header) / 2);
        int headerY = 50;
        g.drawString(header, headerX, headerY);
        
        g.drawLine(25, 75, 675, 75);
        g.drawLine(Orbis.GAME_SIZE/2, 100, Orbis.GAME_SIZE/2, 525);
        g.drawLine(25, 550, 675, 550);
        
        //Draw Labels
        String stat1 = "TOTAL SCORE";
        String stat2 = "TOTAL KILLCOUNT";
        String stat3 = "BULLETS FIRED";
        String stat4 = "ACCURACY";
        String stat5 = "GAMES PLAYED";
        String stat6 = "TOTAL TIME";
        g.setFont(new Font("Courier New", Font.BOLD, 30));
        g.drawString(stat1, Orbis.GAME_SIZE/2 - 25 - g.getFontMetrics().stringWidth(stat1), 125);
        g.drawString(stat2, Orbis.GAME_SIZE/2 - 25 - g.getFontMetrics().stringWidth(stat2), 200);
        g.drawString(stat3, Orbis.GAME_SIZE/2 - 25 - g.getFontMetrics().stringWidth(stat3), 275);
        g.drawString(stat4, Orbis.GAME_SIZE/2 - 25 - g.getFontMetrics().stringWidth(stat4), 350);
        g.drawString(stat5, Orbis.GAME_SIZE/2 - 25 - g.getFontMetrics().stringWidth(stat5), 425);
        g.drawString(stat6, Orbis.GAME_SIZE/2 - 25 - g.getFontMetrics().stringWidth(stat6), 500);
        
        //Draw values
        g.setFont(new Font("Courier New", Font.PLAIN, 30));
        g.drawString(String.format("%,d", score), Orbis.GAME_SIZE/2 + 25, 125);
        g.drawString(String.format("%,d", killCount), Orbis.GAME_SIZE/2 + 25, 200);
        g.drawString(String.format("%,d", bulletsFired), Orbis.GAME_SIZE/2 + 25, 275);
        g.drawString(String.format("%.1f%%", accuracy), Orbis.GAME_SIZE/2 + 25, 350);
        g.drawString(String.format("%,d", gamesPlayed), Orbis.GAME_SIZE/2 + 25, 425);
        g.drawString("" + convertTime(timePlayed), Orbis.GAME_SIZE/2 + 25, 500);             
        
        //Draw Button
        backButton.draw(g);
    }
	
	/**
	 * Adds new stats to the current stats.
	 * 
	 * @param newKillCount
	 * @param newScore
	 * @param newBulletsFired
	 * @param newTimePlayed
	 */
	public static void updateStats(int newKillCount, long newScore, long newTimePlayed, int newBulletsFired)
	{
		killCount += newKillCount;
		score += newScore;
		timePlayed += newTimePlayed;
		bulletsFired += newBulletsFired;
		accuracy = ((accuracy + (1 - ((bulletsFired - Game.bulletsHit) / bulletsFired)) * 100) / 2);
		updateFile();
	}
	
	/**
	 * Overwrites file with stored information.
	 * 
	 * DO NOT call this method without reading the file first.
	 */
	public static void updateFile()
	{
		PrintWriter outFile = null;
        try
        {
        	outFile = new PrintWriter(stats.getPath());
        }
        catch (FileNotFoundException e)
        {
        	Orbis.error.writeError("Error in Stats/updateFile: PrintWriter failed.");
        }
        
        outFile.println(killCount);
		outFile.println(score);
		outFile.println(bulletsFired);
		outFile.println(timePlayed);
		outFile.println(gamesPlayed);
		outFile.println(accuracy);
        
        outFile.close();
	}
	
	public static String convertTime(long time)
	{
		long second = (time / 1000) % 60;
		long minute = (time / (1000 * 60)) % 60;
		long hour = (time / (1000 * 60 * 60)) % 24;

		String contime = String.format("%02d:%02d:%02d", hour, minute, second);
		
		return contime;
	}
	
	/**
     * Method to reset the contents of the file to null values.
     */
    public static void reset()
    {
        PrintWriter outFile = null;
        try
        {
            outFile = new PrintWriter(stats.getPath());
        }
        catch (FileNotFoundException e)
        {
        	Orbis.error.writeError("Error in Stats/Reset: Failed to obtain file.");
        }

        outFile.println("0");
        outFile.println("0");
        outFile.println("0");
        outFile.println("0");
        outFile.println("0");
        outFile.println("0");

        outFile.close();
    }
	
	@Override
	public void mouseClicked(MouseEvent arg0) {}

	@Override
	public void mouseEntered(MouseEvent e) {}

	@Override
	public void mouseExited(MouseEvent e) {}

	@Override
	public void mousePressed(MouseEvent e) {}

	@Override
	public void mouseReleased(MouseEvent e) {
		if (backButton.checkClicked(e.getX(), e.getY()))
		{
			Orbis.changeState("MENU");
		}

	}

}
