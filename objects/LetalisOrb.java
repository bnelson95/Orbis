package objects;
import java.awt.Color;
import java.awt.Graphics;

import states.Game;
import states.Settings;
import states.Upgrade;

public class LetalisOrb extends GameObject
{
	//Constants
	public static final int SIZE = 5;
	public static final int SPEED = 10;
	public static final int DAMAGE = 10 + (Upgrade.bullet_damage * 5);
	public static final Color COLOR = Settings.themes.letalisColor;
	
	//Fields
    private double direction;
    private int damage;
    
    //Constructer
    public LetalisOrb(double x, double y, double direction)
    {
        setX(x);
        setY(y);
        setDirection(direction);
        
        setSize(SIZE);
        setSpeed(SPEED);
        setDamage(DAMAGE);
        setColor(new Color(COLOR.getRed(), COLOR.getGreen(), COLOR.getBlue(), 192));
        
        setMovement(direction, speed);
        
        Game.bulletsFired++;
    }
    
    
    public void step()
    {
        super.move();
        move();
    }
    
    
    public void move()
    {
        if (Upgrade.bullet_tracking > 0)
        {
            if (Game.objects.items(Inimicus.class) > 0)
            {
            	double closestEnemyX = 0;
            	double closestEnemyY = 0;
            	int enemyPos = -1;
                
            	for (int i = 0; i < Game.objects.size() && enemyPos >= 0; i++)
            	{
            		if(!Game.objects.isEmpty(i) && Game.objects.get(i) instanceof Inimicus)
            		{
            			enemyPos = i;    
            			break;
            		}
            	}

            	if (enemyPos >= 0)
            	{
            		closestEnemyX = Game.objects.get(enemyPos).getX();
            		closestEnemyY = Game.objects.get(enemyPos).getY();
            	}

            	for (int i = 1; i < Game.objects.size(); i++)
                {
                    if (!Game.objects.isEmpty(i) && Game.objects.get(i) instanceof Inimicus)
                    {
                    	if (pointDistance(x, y, Game.objects.get(i).getX(), Game.objects.get(i).getY()) < pointDistance(x, y, closestEnemyX, closestEnemyY))
                    	{
                    		closestEnemyX = Game.objects.get(i).getX();
                    		closestEnemyY = Game.objects.get(i).getY();
                    	}
                    }
                }
                
                
                double currentDir = pointDirection(x, y, x+dx, y+dy);
                double enemyDir = pointDirection(x, y, closestEnemyX, closestEnemyY);
                
                double dif = (enemyDir - currentDir) % 360;
                if (currentDir > enemyDir) {dif += 360;}
                if (dif >= 180) {dif -= 360;}

                double change = 0;
                double range = 60 + 15 * (Upgrade.bullet_tracking-1); // Level 1:60, 2:120, 3:180
                
                if (dif >= -range/2 && dif <= range/2)
                    change = (dif * Upgrade.bullet_tracking / 33.3)*2;
                
                setMovement(currentDir+change, speed);
            }
        }            
    }
    
    
    public void hit(GameObject obj)
    {
    	if (obj instanceof Inimicus)
        {
        	Game.bulletsHit++;
            size = 0;
        }
    }
    
    
    public boolean isDead()
    {
        return (size == 0 || isOutsideFrame());
    }
    
    
    public void paint(Graphics g)
    {
        g.setColor(color);
        g.fillOval((int) x - size/2, (int) y - size/2, size, size);
    }
    
    
    // ACCESSORS
    public double getDirection() {return direction;}
    public int getDamage() {return damage;}
    
    
    // MUTATORS
    public void setDirection(double direction) {this.direction = direction;}
    public void setDamage(int damage) {this.damage = damage;}
}
