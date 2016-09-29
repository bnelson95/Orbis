package objects;
import java.awt.Color;

import states.Game;
import states.Settings;


public class Scindo extends Inimicus
{
    public static final int   SIZE   = 40;
    public static final int   SPEED  =  8;
    public static final int   DAMAGE = 10;
    public static final int   VALUE  =  3;
    public static final Color COLOR  = Settings.themes.scindoColor;

    public Scindo(double x, double y)
    {
        setX(x); setY(y);
        
        setSize  (SIZE  );
        setSpeed (SPEED );
        setDamage(DAMAGE);
        setValue (VALUE );
        setColor (COLOR );
    }
    
    public Scindo(double x, double y, int size)
    {
        this(x, y);
        setSize  (size  );
    }


    public void hit(GameObject obj)
    {
        //If there is no shield, call super first.
        if(shield == 0)
            super.hit(obj);
        
        if (obj instanceof LetalisOrb)
        {
            if (shield == 0)
    		{
    			LetalisOrb l = (LetalisOrb) obj;
    			size += l.getDamage();
    			split(l);
    		}
    	}
        //If there is a shield, call super last to avoid splitting with a shield.
        if(shield > 0)
            super.hit(obj);
    }
    
    public void death()
    {
        super.death();
        createTokens();
    }
    
    public void split(LetalisOrb bullet)
    {
        if (getSize() != 0)
        {
            double angle = pointDirection(bullet.getX(), bullet.getY(), this.getX(), this.getY());
            
            Scindo s1 = new Scindo(getX(), getY(), this.getSize()/2);
            s1.setPosition(angle - 90, getSize()*2);
            
            Scindo s2 = new Scindo(getX(), getY(), this.getSize()/2);
            s2.setPosition(angle + 90, getSize()*2);
            size = 0;
            Game.objects.add(s1);
            Game.objects.add(s2);
        }
    }
        
    public void setPosition(double angle, double magnitude)
    {
        setX((int) (getX() + magnitude * Math.cos(Math.toRadians(angle))));
        setY((int) (getY() - magnitude * Math.sin(Math.toRadians(angle))));
    }
}
