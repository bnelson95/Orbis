package objects;

//Local imports
import general.Orbis;
import states.Game;
import states.Upgrade;


/**
 * 
 * @author Blake
 * @version
 */
public abstract class Inimicus extends GameObject
{
    private int damage;
    private int value;
      
    /**
     * 
     */
    public void step()
    { 
    	double speed = this.speed - ((double)Upgrade.player_speed*10/this.speed);
    	if (speed < 2) speed = 2;
    	
        setMovement(pointDirection((int)x, (int)y, Game.optimus.getX(), Game.optimus.getY()), speed);
        
        move();
    }

    
    /**
     * 
     */
    public void hit(GameObject obj)
    {
    	//Enemy (this) HIT (obj) Optimus
    	if (obj instanceof OptimusOrb)
    	{
    		new ParticleSystem(this, obj, 3, 1, 4);  		
    		this.size = 0;
    	}
    	
    	//Enemy (this) HIT (obj) Bullet
    	else if (obj instanceof LetalisOrb)
    	{
    		new ParticleSystem(this, obj, 2, 2, 4);
    		
    		if (shield > 0)
            {
                shield--;
            }
            else 
            {
                size -= ((LetalisOrb) obj).getDamage();
            }
            if (this.isDead()) this.death();
            
            Orbis.sounds.playHit();
    	}
    	
    	//Enemy (this) HIT (obj) Enemy
    	else if (obj instanceof Inimicus)
    	{
    		//Move away from other enemies
    		double dir = pointDirection(obj.getX(), obj.getY(), x, y);
    		double dis = (this.getRadius()+obj.getRadius())-pointDistance(obj.getX(), obj.getY(), x, y);
    		setMovement(dir, dis);
    		move();
    	}
    }
    
    
    /**
     * 
     */
    public boolean isDead()
    {
        return(size < 10);
    }
    
    
    /**
     * 
     */
    public void death()
    {
        Game.spawnDrop(x, y);
        Game.killCount++;
    }
    
    public void createTokens()
    {
        for (int i = 0; i < value; i++)
        {
            double xx = x + (Math.random() * value * 6 - value * 3);
            double yy = y + (Math.random() * value * 6 - value * 3);
            Game.objects.add(new TokenDrop(xx, yy));
        }
    }
    
    
    //Accessors
    public int getDamage() {return damage;}
    
    
    //Mutators
    public void setDamage(int damage) {this.damage = damage;}
    public void setValue(int value) {this.value = value;}
}
