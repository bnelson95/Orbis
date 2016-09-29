package objects;
import java.awt.Color;

import general.Orbis;
import states.Game;
import states.Settings;

/**
 * 
 * @author Blake
 * @version
 */
public class Amoveo extends Inimicus
{
	
    public static final int   SIZE   = 50;
    public static final int   SPEED  = 8;
    public static final int   DAMAGE = 10;
    public static final int   VALUE  = 15;
    public static final Color COLOR  = Settings.themes.amoveoColor;
    
    
    /**
     * Constructor.
     * @param x Initial x position
     * @param y Initial y position
     */
    public Amoveo(int x, int y)
    {
    	setX(x);
        setY(y);
        
        setSize  (SIZE  );
        setSpeed (SPEED );
        setDamage(DAMAGE);
        setValue (VALUE );
        setColor (COLOR );
    }
    
    
    /**
     * Overrides Inimicus hit method.
     * @param obj The GameObject colliding with this object.
     */
    public void hit(GameObject obj)
    {
      //If there is no shield, call super first.
        if (shield == 0)
            super.hit(obj);
    	
    	if (obj instanceof LetalisOrb)
    	{
    	    if (shield == 0) teleport();
    	}
    	//If there is a shield, call super last to avoid teleporting with a shield.
    	if (shield > 0)
    	    super.hit(obj);
    }
    
    public void death()
    {
        super.death();
        createTokens();
    }


    /**
     * Sets the amoveo to a random location the same distance or further than it was before.
     */
	private void teleport()
	{
		//Initialize value of the original distance.
		double oldDis = pointDistance(Game.optimus.getX(), Game.optimus.getY(), getX(), getY());
		
		//Initialize empty variables for the new location and its distance.
		double newX, newY, newDis; 
		
		//Set the new location to a random position until the new 
		//distance is greater than or equal to the original distance.
        do 
        {
            newX = (int)(Math.random()*Orbis.GAME_SIZE);
            newY = (int)(Math.random()*Orbis.GAME_SIZE);
            newDis = pointDistance(Game.optimus.getX(), Game.optimus.getY(), newX, newY);
        } 
        while (newDis < oldDis);
        
        //Move the object to the new location.
        setX(newX); setY(newY);	
	}
	
}
