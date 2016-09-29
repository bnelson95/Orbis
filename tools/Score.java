package tools;
import java.awt.Graphics;

import general.Orbis;


public class Score
{
    private String name;
    private int killCount;
    private int score;

    public Score(String name, int killCount, int score)
    {
        this.name = name;
        this.killCount = killCount;
        this.score = score;
    }

    @Override
    public String toString()
    {
        return String.format("%-12s%-13s%-9s", name, score, killCount);
    }
    
    public void paintComponent(Graphics g, int y)
    {
        if (!name.equals("null"))
        {
            int x = (Orbis.GAME_SIZE / 2) - (g.getFontMetrics().stringWidth(toString()) / 2);
            g.drawString(toString(), x, y);
        }
    }

    public String getName()
    {
        return name;
    }

    public int getKillCount()
    {
        return killCount;
    }

    public int getScore()
    {
        return score;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public void setKillCount(int killCount)
    {
        this.killCount = killCount;
    }

    public void setScore(int score)
    {
        this.score = score;
    }


}
