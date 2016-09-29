package objects;
import java.awt.Color;
import java.awt.Graphics;

import general.Orbis;
import states.Game;
import states.Upgrade;


public abstract class Drop extends GameObject
{
    public Drop(double x, double y, int size, Color color)
    {
        this.x = x;
        this.y = y;
        this.size = size;
        this.color = color;
    }
    
    public void step()
    {
        move();
    }
    
    public void move()
    {
        if (Upgrade.player_attract > 0)
        {
            double direction = Game.pointDirection(x, y, Game.optimus.getX(), Game.optimus.getY());
            double distance = Game.pointDistance(x, y, Game.optimus.getX(), Game.optimus.getY());
            
            setMovement(direction, (double)Orbis.GAME_SIZE/(distance*2) * (double)Upgrade.player_attract);
            
            x += dx;
            y += dy;
        }
    }
    
    public double getRadius()
    {
        return (size / 2);
    }
    
    public abstract void hit(GameObject obj);
    
    public void paint(Graphics g)
    {
        g.setColor(color);
        g.fillOval((int)(x - size/2), (int)(y - size/2), size, size);
    }
    
    public boolean checkCollision(OptimusOrb o)
    {
        double combinedRadius = this.getRadius() + o.getRadius();
        
        double xx = Math.abs(x - o.getX());
        double yy = Math.abs(y - o.getY());
        double distance = Math.sqrt(xx*xx + yy*yy);
        
        return (distance < combinedRadius);
    }
    
    public boolean isDead()
    {
        return (size == 0);
    }
    
    public void setMovement(double angle, double magnitude)
    {
        dx = magnitude * Math.cos(Math.toRadians(angle));
        dy = -magnitude * Math.sin(Math.toRadians(angle));
        //0- 90, x+, y-
        //90-180, x-, y-
        //180-270, x-, y+
        //270-360, x+, y+
    }
    
    // ACCESSORS
    public double getX() {return x;}
    public double getY() {return y;}
    public int getSize() {return size;}
}
