package objects;
import java.awt.Color;

import states.Settings;


public class Parvus extends Inimicus
{
    public static final int   SIZE   = 30;
    public static final int   SPEED  =  8;
    public static final int   DAMAGE = 10;
    public static final int   VALUE  =  5;
    public static final Color COLOR  = Settings.themes.parvusColor;
      
    /**
     * Constructor.  
     * 
     * @param x coordinate
     * @param y coordinate
     */
    public Parvus(int x, int y)
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
    public void hit(GameObject obj)
    {
    	super.hit(obj);
    }
    
    public void death()
    {
        super.death();
        createTokens();
    }
    
}
