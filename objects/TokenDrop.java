package objects;

import java.awt.Color;

import general.Orbis;
import states.Game;
import states.Settings;
import states.Upgrade;

public class TokenDrop extends Drop
{
    private static final int SIZE = 5;
    private static final Color COLOR = Settings.themes.tokenColor;

    public TokenDrop(double x, double y)
    {
        super(x, y, SIZE, COLOR);
    }
    
    public void move()
    {
    	double distance = Game.pointDistance(x, y, Game.optimus.getX(), Game.optimus.getY());
        
    	double direction = Game.pointDirection(x, y, Game.optimus.getX(), Game.optimus.getY());
        double magnitude = (double)Orbis.GAME_SIZE/(distance*2) * (double)(Upgrade.player_attract + 1);

        if (magnitude < 12) magnitude = 12;
        
        setMovement(direction, magnitude);

        x += dx;
        y += dy;
    }

    @Override
    public void hit(GameObject obj)
    {
        if (obj instanceof OptimusOrb)
        {
            new ParticleSystem(this, obj, 1, 3, 4);
            size = 0;
        }
        else if (obj instanceof TokenDrop)
        {
        	//Move away from other enemies
    		double dir = pointDirection(obj.getX(), obj.getY(), x, y);
    		//double dis = (this.getRadius()+obj.getRadius())-pointDistance(obj.getX(), obj.getY(), x, y);
    		double dis = SIZE * 4;
    		setMovement(dir, dis);
    		move();
        }
        
    }
    
}
