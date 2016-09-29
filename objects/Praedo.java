package objects;

//General imports
import java.awt.Color;

import states.Game;
import states.Settings;
import states.Upgrade;


/**
 * 
 * @author Blake
 * @version
 */
public class Praedo extends Inimicus
{
	
	public static final int   SIZE   = 50;
	public static final int   SPEED  =  8;
	public static final int   DAMAGE = 20;
	public static final int   VALUE  = 10;
	public static final Color COLOR  = Settings.themes.praedoColor;
	
	
	/**
	 * 
	 * Constructor.
	 * @param x
	 * @param y
	 */
	public Praedo(int x, int y)
	{
		setX(x); setY(y);

		setSize  (SIZE  );
		setSpeed (SPEED );
		setDamage(DAMAGE);
		setValue (VALUE );
		setColor (COLOR );
	}
	
	
	/**
	 * 
	 */
	public void step()
	{
		//System.out.println(Game.objects.items(HealthDrop.class));
	    if (Game.objects.items(HealthDrop.class) > 0)
	    {
	        double closestX = 0;
            double closestY = 0;
            int dropPos = -1;
            
            //Finds first occurance of a drop in objects
            for (int i = 0; i < Game.objects.size(); i++)
            {
                if(!Game.objects.isEmpty(i) && Game.objects.get(i) instanceof HealthDrop)
                {
                    dropPos = i; break;
                }
            }
            //If one is found, store coordinates
            if (dropPos >= 0)
            {
                closestX = Game.objects.get(dropPos).getX();
                closestY = Game.objects.get(dropPos).getY();
            }
            //Check stored coordinates against the other drops
            for (int i = 1; i < Game.objects.size(); i++)
            {
                if (!Game.objects.isEmpty(i) && Game.objects.get(i) instanceof HealthDrop)
                {
                    if (pointDistance(x, y, Game.objects.get(i).getX(), Game.objects.get(i).getY()) < pointDistance(x, y, closestX, closestY))
                    {
                        closestX = Game.objects.get(i).getX();
                        closestY = Game.objects.get(i).getY();
                    }
                }
            }
            //Move
            double speed = this.speed - ((double)Upgrade.player_speed*10/this.speed);
            if (speed < 2) speed = 2;           
            setMovement(pointDirection((int)x, (int)y, closestX, closestY), speed/2);
            move();
	    }
	    else
	    {
	    	super.step();
	    }
	}
	
	
	/**
	 * 
	 */
	public void hit(GameObject obj)
	{
		super.hit(obj);
		
		if (obj instanceof HealthDrop)
		{
			if (size < 100)
			{
				size += 10;
			}
		}
		if (obj instanceof ShieldDrop)
        {
            if (shield < 10)
            {
                shield ++;
            }
        }
	}
	
	public void death()
    {
        super.death();
        createTokens();
    }
	
}
