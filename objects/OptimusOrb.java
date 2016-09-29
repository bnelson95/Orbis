package objects;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;

import general.Orbis;
import states.Game;
import states.Settings;
import states.Upgrade;


public class OptimusOrb extends GameObject
{
	
	public static final int   SIZE  = 50;
	public static final int   SPEED = 10;
	public static final Color COLOR = Settings.themes.optimusColor;

    
    
    /**
     * Constructor.  
     * Sets fields to recommended values.
     */
    public OptimusOrb()
    {
        setX(Orbis.GAME_SIZE/2);
        setY(Orbis.GAME_SIZE/2);
        
        setSize(SIZE); setRenderSize(SIZE);
        setSpeed(SPEED);
        setColor(COLOR);
        
        shield = 0;
    }
    
    
    public void step()
    {
    	int speed = this.speed + (Upgrade.player_speed*2);
    	
    	// Keyboard movement
        if (Orbis.keyStroke.contains(KeyEvent.VK_D) && x < Orbis.GAME_SIZE - size/2) x += speed;
        if (Orbis.keyStroke.contains(KeyEvent.VK_A) && x >               0 + size/2) x -= speed;
        if (Orbis.keyStroke.contains(KeyEvent.VK_S) && y < Orbis.GAME_SIZE - size/2) y += speed;
        if (Orbis.keyStroke.contains(KeyEvent.VK_W) && y >               0 + size/2) y -= speed;
    }
    
    
    /**
     * 
     */
    public void hit(GameObject obj)
    {
    	//Optimus (this) HIT (obj) Enemy
        if (obj instanceof Inimicus)
        {
        	Inimicus i = (Inimicus) obj;       	
            if (!Orbis.invincibility)
            {
                new ParticleSystem(this, obj, 2, 2, 4);
                if (shield > 0)
                {
                	shield--;
                }
                else
                {
                	size -= i.getDamage();               	
                }             
            }

            Orbis.sounds.playPunch();
        }
        
        //Optimus (this) HIT (obj) HealthDrop
        if (obj instanceof HealthDrop)
        {
            Orbis.sounds.playGrow();
        	size += 10;           
        }
        
        //Optimus (this) HIT (obj) ShieldDrop
        if (obj instanceof ShieldDrop)
        {
            Orbis.sounds.playGrow();
            shield++;
        }
        
        if (obj instanceof TokenDrop && !Orbis.devMode)
        {
            Orbis.sounds.playHit();
            Game.tokens++;
            Game.score++;
        }
    }
    
    
    /**
     * 
     */
    public boolean isDead()
    {
        if (size <= 0)
        {
            Game.totalTime = ((System.currentTimeMillis() - Game.startTime) - Game.pauseTime);
            Orbis.changeState("GAMEOVER");
            return true;
        }
        return false;
    }
    
    
    /**
     * 
     */
    public void updateRenderSize()
    {
    	renderSize = (renderSize < size) ? renderSize+1 : (renderSize > size) ? renderSize-1 : renderSize;
    	
    	/*
        if (renderSize < size)
        {
            renderSize++;
        }
        else if (renderSize > size)
        {
            renderSize--;
        }
        */
    }
    
    
    /**
     * 
     */
    public void paint(Graphics g)
    {
    	Graphics2D g2 = (Graphics2D) g;
    	
        updateRenderSize();
        
        g2.setColor(color);
        g2.fillOval((int)x - renderSize/2, (int)y - renderSize/2, renderSize, renderSize);
        
        g2.setColor(Settings.themes.shieldColor);
        g2.setStroke(new BasicStroke(shield*2));
        g2.drawOval((int)x - renderSize/2, (int)y - renderSize/2, renderSize, renderSize);
    }    
    
    // MUTATORS
    public void setSize(int size) {this.size = size;}
    public void setRenderSize(int renderSize) {this.renderSize = renderSize;}
    public void setPos(double x, double y) {this.x = x; this.y = y;}
}
