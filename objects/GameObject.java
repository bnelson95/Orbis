package objects;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import general.Orbis;
import states.Settings;


public abstract class GameObject
{
    protected double x;
    protected double y;
    protected double dx; //Change in x
    protected double dy; //Change in y
    protected int size;
    protected int shield;
    protected int renderSize;
    protected int speed;
    protected Color color;

    public GameObject(){}
    
    
    public abstract void step();
    public abstract void hit(GameObject obj);
    public abstract boolean isDead();
    
    
    
    public void move()
    {
        x += dx;
        y += dy;
    }
    
    public void setMovement(double angle, double magnitude)
    {
        dx = magnitude * Math.cos(Math.toRadians(angle));
        dy = -magnitude * Math.sin(Math.toRadians(angle));
    }

    public void addMovement(double angle, double magnitude)
    {
        dx += magnitude * Math.cos(Math.toRadians(angle));
        dy += magnitude * Math.sin(Math.toRadians(angle));
    }
    
    public static double pointDirection(double x1, double y1, double x2, double y2)
    {
        return (((Math.atan2(x1-x2, y1-y2) * (180/Math.PI))) + 450) % 360;
    }
    
    public static double pointDistance(double x1, double y1, double x2, double y2)
    {
        return Math.sqrt(Math.pow(x2 - x1, 2) + Math.pow(y2 - y1, 2));
    }
    
    public void paint(Graphics g)
    {
        Graphics2D g2 = (Graphics2D) g;
        
        g2.setColor(color);
        g2.fillOval((int)x - size /2, (int)y - size /2, size, size);
        
        g2.setColor(Settings.themes.shieldColor);
        g2.setStroke(new BasicStroke(shield*2));
        g2.drawOval((int)x - size/2, (int)y - size/2, size, size);
    }
    
    public double getRadius()
    {
        return (size / 2);
    }
    
    public boolean isOutsideFrame()
    {
    	return (x + getRadius() < 0
    		 || x - getRadius() > Orbis.GAME_SIZE
    		 || y + getRadius() < 0
    		 || y - getRadius() > Orbis.GAME_SIZE);
    			
    }
    
    // ACCESSORS
    public double getX() {return x;}
    public double getY() {return y;}
    public int getSize() {return size;}
    public int getShield() {return shield;}
    public int getRenderSize() {return renderSize;}
    public int getSpeed() {return speed;}
    public Color getColor(){return color;}
    public Color getOuterColor()
    {
        if (shield > 0)
            return Settings.themes.shieldColor;
        return getColor();
    }
    
    
    // MUTATORS
    public void setX(double x2) {this.x = x2;}
    public void setY(double y2) {this.y = y2;}
    public void setSize(int size) {this.size = size;}
    public void setShield(int shield) {this.shield = shield;}
    public void setSpeed(int speed) {this.speed = speed;}
    public void setColor(Color color) {this.color = color;}
    
}
