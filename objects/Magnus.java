package objects;

//General imports
import java.awt.Color;

import states.Settings;


/**
 * 
 * @author Blake
 * @version
 */
public class Magnus extends Inimicus
{
	
    public static final int   SIZE   = 100; //Diameter in pixels
    public static final int   SPEED  =   6;
    public static final int   DAMAGE =  20;
    public static final int   VALUE  =  10;
    public static final Color COLOR  = Settings.themes.magnusColor;
    
    
    /**
     * 
     * Constructor.
     * @param x
     * @param y
     */
    public Magnus(double x, double y)
    {
        setX(x); setY(y);
        
        setSize  (SIZE  );
        setSpeed (SPEED );
        setDamage(DAMAGE);
        setValue (VALUE );
        setColor (COLOR );
    }
  	
  	public void death()
    {
        super.death();
        createTokens();
    }
  	
}
