package objects;
import java.awt.Color;
import java.awt.Graphics;

/**
 * Class to create a single particle.
 * 
 * @author Sam Barr & Blake Nelson
 * @version 1.0
 */
public class Particle extends GameObject
{
	private double direction;
	private double decay;
	
	/**
	 * Constructor.
	 * 
	 * @param size
	 * @param speed
	 * @param direction
	 * @param decay
	 * @param color
	 */
	public Particle(double x, double y, int size, int speed, double direction, double decay, Color color)
	{
		this.x = x;
		this.y = y;
		
		this.size = size;
		this.speed = speed;
		this.direction = direction;
		this.decay = decay;
		this.color = color;		
	}
	
	/**
	 * Updates the particle by movement, and by speed and direction depending on decay rate.
	 */
	public void step()
	{
	    this.dx = speed * Math.cos(Math.toRadians(direction));
	    this.dy = speed * Math.sin(Math.toRadians(direction));

	    x += dx;
	    y += dy;

	    size -= decay/2;
	}

	public void hit(GameObject obj)
	{
	    
	}
	
	public boolean isDead()
	{
	    return(size <= 0);
	}
	

	public void paint(Graphics g)
	{
		g.setColor(color);
		g.fillOval((int)Math.round(x) - size/2, (int)Math.round(y) - size/2, size, size);
	}
	
	public double getDirection() {return direction;}
	public void setDirection(double direction) {this.direction = direction;}
	public double getDecay() {return decay;}
	public void setDecay(double decay) {this.decay = decay;}
	
}
