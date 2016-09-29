package tools;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;


public class OButton
{
    private String text;
    private int x;
    private int y;
    private double xx;
    private double yy;
    private int speed;
    private int size;
    private Color color;

    public OButton(String text, int x, int y, int size, Color color)
    {
        this.text = text;
        this.x = x;
        this.y = y;
        this.size = size;
        this.color = color;
        this.xx = 0;
        this.yy = 0;
        this.speed = 5;
    }
    
    public boolean checkClicked(int x, int y)
    {
        double combinedRadius = this.getRadius();
        
        double xDis = Math.abs(this.x - x);
        double yDis = Math.abs(this.y - y);
        double distance = Math.sqrt(xDis*xDis + yDis*yDis);
        
        return (distance < combinedRadius);
    }
    
    public void draw(Graphics g2)
    {
    	Color initColor = g2.getColor();
    	Font initFont = g2.getFont();
    	
    	g2.setColor(color);
		g2.fillOval(x - size/2, y - size/2, size, size);
		
		g2.setColor(Color.WHITE);
		g2.setFont(new Font("Courier New", Font.BOLD, size /5));
		
		int textX = x - (g2.getFontMetrics().stringWidth(text) / 2);
		int textY = y + (g2.getFontMetrics().getHeight() / 4);
    
		g2.drawString(text, textX, textY);
        
        
        g2.setColor(initColor);
        g2.setFont(initFont);
    }
    
    public void move(int playerX, int playerY)
    { 
        double xDif = playerX - x;
        double yDif = playerY - y;
        double hyp = Math.sqrt(Math.pow(xDif, 2) + Math.pow(yDif, 2));        
        this.xx = (speed * xDif) / hyp;
        this.yy = (speed * yDif) / hyp;
        x += xx;
        y += yy;
    }
    
    public void setLabel(String text)
    {
        this.text = text;
    }
    
    public int getX() 
    {
		return x;
	}

	public int getY() 
	{
		return y;
	}
	
	public int getSize()
	{
		return size;
	}

	public int getRadius()
    {
        return size / 2;
    }
	
	public void setSize(int size) { this.size = size; }


}
