package objects;
import java.awt.Color;

import states.Settings;


public class ShieldDrop extends Drop
{
    private static final int SIZE = 8;
    private static final Color COLOR = Settings.themes.shieldColor;
    
    public ShieldDrop(double d, double e)
    {
        super(d, e, SIZE, new Color(COLOR.getRed(), COLOR.getGreen(), COLOR.getBlue(), 192));
    }
    
    @Override
    public void hit(GameObject obj)
    {
        if(obj instanceof OptimusOrb)
        {
        	new ParticleSystem(this, obj, 1, 3, 4);
            size = 0;
        }
    }
}
