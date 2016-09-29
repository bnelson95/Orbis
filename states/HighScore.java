package states;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;


import general.*;
import tools.*;


@SuppressWarnings("serial")
public class HighScore extends GameState
{
    private OButton backButton;
    public static File file;
    public static Scanner inFile;
    public static Score[] scores;
    public static Score recent;
    
    public static String filePath;
    private File directory;
    private static File highscores;

    public HighScore()
    {
        //Create path directory based on operating system.
        if (System.getProperty("os.name").contains("Windows"))
        {
        	filePath = "C:/Users/" + System.getProperty("user.name") + "/AppData/Local";
        }
        else if (System.getProperty("os.name").contains("Mac"))
    	{
    		filePath = "/Users/"+ System.getProperty("user.name") +"/Library/Application Support";
    	}
        
        //Open (Or create) path directory.
        directory = new File(filePath + "/Orbis/");
    	highscores = new File(filePath + "/Orbis/Scores.txt");
    	if(!directory.exists())
    	{
    		try
    		{
    			directory.mkdir();   
    		} catch (Exception e) {
    			Orbis.error.writeError("New Directory For Scores Failed.");
    		}
    	}
    	
    	if(!highscores.exists())
    	{
    		try
    		{
        		PrintWriter pw = new PrintWriter(highscores);
        		for (int i = 0; i < 7; i++)
        		{
        			pw.println("null");
        			pw.println("0");
        			pw.println("0");
        		}
        		pw.close();
    		} catch (Exception e){
    		    Orbis.error.writeError("File Creation for Scores Failed.");
    		}
    	}
    	
        scores = new Score[6];

        
        backButton = new OButton("Back", Orbis.GAME_SIZE / 2, 625, 100, Settings.themes.parvusColor);
        
        try
        {       	
            inFile = new Scanner(highscores);
        }
        catch(Exception e)
        {
        	Orbis.error.writeError("Scanner Object for Scores Failed.");
        }
        
        //Reads file, and puts names, killCounts, and scores into separate arrays.
        for (int i = 0; i < scores.length && inFile.hasNext(); i++)
        {
            scores[i] = new Score(inFile.nextLine(), inFile.nextInt(), inFile.nextInt());
            if (inFile.hasNext())
                inFile.nextLine();
        }
        inFile.close();
        recent = new Score("Recent:", 0, 0);
        repaint();
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
        
        
        
        g.setColor(Settings.themes.textColor);
        
        g.setFont(new Font("Courier New", Font.PLAIN, 50));
        String intro0 = "HIGH SCORES";
        int intro0x = (Orbis.GAME_SIZE / 2) - (g.getFontMetrics().stringWidth(intro0) / 2);
        g.drawString(intro0, intro0x, 50);
        
        g.setFont(new Font("Courier New", Font.BOLD, 30));
        String intro1 = "NAME        SCORE        KILLCOUNT";
        int intro1x = (Orbis.GAME_SIZE / 2) - (g.getFontMetrics().stringWidth(intro1) / 2);
        g.drawString(intro1, intro1x, 120);
        
        g.drawLine(25, 75, 675, 75);
        g.drawLine(25, 550, 675, 550);

        g.setFont(new Font("Courier New", Font.PLAIN, 30));
        for(int i = 0; i < scores.length && scores[i] != null; i++)
        {
            scores[i].paintComponent(g, 170 + 60 * i);
        }
        g.drawLine(25, 495, 675, 495);
        recent.paintComponent(g, 530);
        

        backButton.draw(g);
    }
    
    public static boolean checkScore(int score)
    {
        boolean check = false;
        if (scores.length > 0)
        {
            for (int i = 0; i < scores.length - 1; i++)
            {
                check = (score > scores[i].getScore());
            }
        }
        return check;
    }
    
    /**
     * Updates high score listing depending on arguments.
     * 
     * @param killCount
     * @param score
     * @return the index if the scores were changed.
     *         else returns -1
     */
    public static void updateScore(String name, int killCount, int score)
    {
    	boolean sorted = false;
        //Checks passed in score against scores array.
        for (int i = 0; i < scores.length - 1; i++)
        {
            //If the score is bigger, replace all elements at "i" with new elements. 
            if (score > scores[i].getScore() && !sorted)
            {
                scores[scores.length - 1] = new Score(name, killCount, score);
                int startScan, index, maxIndex;
                Score maxValue;

                for (startScan = 0; startScan < scores.length; startScan++)
                {
                    maxValue = scores[startScan];  
                    maxIndex = startScan;
                    for (index = startScan + 1; index < scores.length; index++)
                    {
                        if (scores[index].getScore() > maxValue.getScore())
                        {
                            maxValue = scores[index];
                            maxIndex = index;
                        }
                    }
                    scores[maxIndex] = scores[startScan];
                    scores[startScan] = maxValue;
                }

                sorted = true;
            }

        }     
        recent = new Score("Recent:", killCount, score);               
        updateFile();
    }
    
    /**
     * Overwrites file with new credentials.
     * DO NOT call this method without reading/recording the file first.
     */
    public static void updateFile()
    {
        PrintWriter outFile = null;
        try
        {
        	outFile = new PrintWriter(highscores.getPath());
        }
        catch (FileNotFoundException e)
        {
        	Orbis.error.writeError("Failed to get Highscore Path.");
        }
        
        for (int i = 0; i < scores.length; i++)
        {
            outFile.println(scores[i].getName());
            outFile.println(scores[i].getKillCount());
            outFile.println(scores[i].getScore());
            
        }
        outFile.close();
    
    }
    
    /**
     * Method to reset the contents of the file to null values.
     */
    public static void reset()
    {
        PrintWriter outFile = null;
        try
        {
            outFile = new PrintWriter(highscores.getPath());
        }
        catch (FileNotFoundException e)
        {
        	Orbis.error.writeError("Error in HighScore/Reset: Failed to obtain file.");
        }
        
        for (int i = 0; i < 7; i++)
        {
            outFile.println("null");
            outFile.println("0");
            outFile.println("0");
        }
        outFile.close();
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
    	{
    		Orbis.changeState("MENU");
    	}
    }
}
