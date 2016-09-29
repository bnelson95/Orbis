package objects;
import java.awt.Color;

import states.Game;
import states.Settings;
import objects.Particle;

public class ParticleSystem
{
	
	private int    amount;
	private double x;
	private double y;
	private int    size;
	private int    speed;
	private double dir;
	private int    spread;
	private double decay;
	private Color  color;

	
	public ParticleSystem(GameObject a, GameObject b, int sizeLevel, int speedLevel, int rangeLevel)
	{
	    if (!Settings.currentParticles.equals("Off"))
	    {
	        setGeneral(a, b    );
	        setSizes  (a, b, sizeLevel );
	        setSpeeds (a, b, speedLevel);
	        setRanges (a, b, rangeLevel);

	        createParticles();
	    }
	}
	
	
	private void setGeneral(GameObject obj1, GameObject obj2)
	{
		setX     (obj1.getX()       );
	    setY     (obj1.getY()       );
	    setColor (obj1.getOuterColor()   );
	    setDir   (Game.pointDirection(obj1.getX(), obj1.getY(), obj2.getX(), obj2.getY())+180);
	}
	
	
	private void setSizes(GameObject obj1, GameObject obj2, int sizeLevel)
	{
		switch (sizeLevel)
		{
			case 1 : setAmount(obj1.getSize() / 6);
			  		 setSize  (obj1.getSize() / 4); break;
			case 2 : setAmount(obj1.getSize() / 5);
		    		 setSize  (obj1.getSize() / 3); break;
			case 3 : setAmount(obj1.getSize() / 4);
			  		 setSize  (obj1.getSize() / 2); break;
		}
	}
	
	
	private void setSpeeds(GameObject obj1, GameObject obj2, int speedLevel)
	{
		switch (speedLevel)
		{
			case 1 : setSpeed (obj2.getSpeed() / 2);
					 setDecay (0.25               ); break;
			case 2 : setSpeed (obj2.getSpeed()    );
					 setDecay (0.5                ); break;
			case 3 : setSpeed (obj2.getSpeed() * 2);
					 setDecay (1.0                ); break;
		}
	}
	
	
	private void setRanges(GameObject obj1, GameObject obj2, int rangeLevel)
	{
		switch (rangeLevel)
		{
			case 1 : setSpread(obj1.getSize() / 2); break;
			case 2 : setSpread(obj1.getSize()    ); break;
			case 3 : setSpread(obj1.getSize() * 2); break;
			case 4 : setSpread(360               ); break;
		}
	}
	
	
	private void createParticles()
	{
		for (int i = 0; i < amount; i++)
        {
			int size = randSize(this.size);
			int speed = randSpeed(this.speed);
			double dir = randDir(this.dir, this.spread);
			
            Game.objects.add(new Particle(x, y, size, speed, dir, decay, color));
        }
	}
	
	
	
	public double randDir(double direction, int spread)
	{
		return spread * 2 * Math.random() + direction - spread; 
	}
	
	public int randSpeed(int speed)
	{
		return (int)(speed * Math.random() + speed/2);
	}
	
	public int randSize(int size)
	{
		if (size < 7) size = 7;
		
		return (int)(size * Math.random() + size/2);
	}

	
	//Mutators
	public void setAmount(int    amount) { this.amount = amount; }
	public void setX     (double x     ) { this.x      = x     ; }
	public void setY     (double y     ) { this.y      = y     ; }
	public void setSize  (int    size  ) { this.size   = size  ; }
	public void setSpeed (int    speed ) { this.speed  = speed ; }
	public void setDir   (double dir   ) { this.dir    = dir   ; }
	public void setSpread(int    spread) { this.spread = spread; }
	public void setDecay (double decay ) { this.decay  = decay ; }
	public void setColor (Color  color ) { this.color  = color ; }
	
	
}
