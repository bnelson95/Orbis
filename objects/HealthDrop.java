package objects;
import java.awt.Color;

import states.Settings;


public class HealthDrop extends Drop
{
    private static final int SIZE = 8;
    private static final Color COLOR = Settings.themes.healthColor;
    
    public HealthDrop(double x, double y)
    {
        super(x, y, SIZE, new Color(COLOR.getRed(), COLOR.getGreen(), COLOR.getBlue(), 192));
    }
    
    @Override
    public void hit(GameObject obj)
    {
        if(obj instanceof OptimusOrb || obj instanceof Praedo)
        {
            new ParticleSystem(this, obj, 1, 3, 4);
            size = 0;
        }
    }
}
