package states;

//General imports
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.text.DecimalFormat;
import javax.sound.sampled.Clip;

//Local imports
import general.Orbis;
import objects.*;
import tools.CircuArray;


@SuppressWarnings("serial")
public class Game extends GameState
{
    public static final String NAME = "GAME";
    
	public static CircuArray<GameObject> objects;
    public static OptimusOrb optimus;
    
    public static int score;
    public static int killCount;
    
    public static double spawnFunction;
    public static int initSpawnRate;  //In milliseconds
    public static int finalSpawnRate; //In milliseconds
    
    //Temp values to keep complexity low
    public int enemyCount;
    public int bulletCount;
    public static int bulletsFired;
    public static float bulletsHit;
    public static long pauseTime;
    public static long totalTime;
    
    public static int tokens;
    public static long spawnTime;
    public static long startTime;
    //boolean to control pause features.
    public static boolean running;
    //count for initialization and countdown.
    private static int count;

   /**
    * Constructor.  
    * Initializes fields.
    */
    public Game()
    {
        running = true;
        
        objects = new CircuArray<GameObject>(500);
        optimus = new OptimusOrb();
        objects.add(optimus);
        
        initSpawnRate = 2000; //2.0 seconds
        finalSpawnRate = 500; //0.5 seconds
        
        killCount = 0;
        enemyCount = 0;
        bulletCount = 0;
        score = 0;
        bulletsFired = 0;
        tokens = 0;
        count = 0;
    }
    
    public void transition()
    {
        Orbis.sounds.playPop();
        Orbis.sounds.getMenuSong().stop();
        if (!Orbis.sounds.muteSong && Game.running) 
            Orbis.sounds.playGameSong();
    }
    
    /**
     * Updates all game components. 
     */
    public void step()
    {
    	if (!running)
    	{
    		countdown();
    		running = true;
    		count = 3;
    	}
    	else
    	{
    		update();
    		Orbis.checkScreen();
    		pause();
            Orbis.sounds.muteSong();
    		if (count == 0)
    		{
    			startTime = System.currentTimeMillis();
    			spawnTime = System.currentTimeMillis();
    			count = 3;
    		}
    	}
    }
    
    
    /**
     * Spawns inimicus based on spawnTime.
     * Updates rest of gameObjects by movement, 
     * and eliminates dead objects.
     */
    public void update()
    {        
        //Spawns inimicus orb (faster spawns depend on killCount)
    	spawnFunction = (initSpawnRate - finalSpawnRate) * Math.pow(Math.E, (-killCount/150.0)) + finalSpawnRate;
        if (System.currentTimeMillis() > spawnTime + spawnFunction)
        {
            if (!Orbis.noSpawn)
                spawnInimicus();
            spawnTime = System.currentTimeMillis();
        }
        
        //GameObjects loop
        for(int i = 0; i < objects.size(); i++)
        {
            if(!objects.isEmpty(i))
            {
            	//STEP
                objects.get(i).step();
                
                //HIT
                for(int j = i+1; j < objects.size(); j++)
                {
                    if(!objects.isEmpty(j))
                    {
                        if (objectCollision(objects.get(i), objects.get(j)))
                        {
                            objects.get(i).hit(objects.get(j));
                            objects.get(j).hit(objects.get(i));
                        }
                    }
                }

                //DEAD
                if(objects.get(i).isDead()) objects.remove(i);
            }
                
        }
        
    }
    
    /**
     * Spawns all enemies in a random location.
     * 10% chance to spawn a Magnus.
     * 10% chance to spawn a Scindo.
     * 10% chance to spawn an Amoveo.
     * 10% chance to spawn a Praedo.
     */
    public void spawnInimicus()
    {
        int spawnX = (int)(Math.random()*Orbis.GAME_SIZE);
        int spawnY = (int)(Math.random()*Orbis.GAME_SIZE);
        
        int side = (int)(Math.random()*4);
        if      (side == 0) spawnX = 0 - 50;
        else if (side == 1) spawnY = 0 - 50;
        else if (side == 2) spawnX = Orbis.GAME_SIZE + 50;
        else if (side == 3) spawnY = Orbis.GAME_SIZE + 50;
        
        int num;
        if (killCount >= 10) num = (int)(Math.random() * 10);
        else                 num = 10;
        
        Inimicus enemy;
        switch (num)
    	{
    		case 0  : enemy = (new Magnus(spawnX, spawnY)); break;
    		case 1  : enemy = (new Scindo(spawnX, spawnY)); break;
    		case 2  : enemy = (new Amoveo(spawnX, spawnY)); break;
    		case 3  : enemy = (new Praedo(spawnX, spawnY)); break;
    		default : enemy = (new Parvus(spawnX, spawnY)); break;
    	}
        int shieldChance = killCount / 5;
        if (Math.random() * 100 < shieldChance)
        {
            enemy.setShield((int)Math.ceil(Math.random() * (killCount / 100.0)));
        }
        objects.add(enemy);      
    }
    
    /**
     * Spawns a drop at a set location.
     * (10% chance)
     * 
     * @param d coordinate
     * @param e coordinate
     */
    public static void spawnDrop(double x, double y)
    {
        int chance = (int)(Math.random() * 10);
        if (chance == 0 && optimus.getSize() < 50)
        {
            objects.add(new HealthDrop(x, y));
        }
        else if (chance == 1 && optimus.getShield() < (2*Upgrade.player_shield-1))
        {
            objects.add(new ShieldDrop(x, y));
        }
    }
    
    
    public boolean objectCollision(GameObject obj1, GameObject obj2)
    {
        double combinedRadius = obj1.getRadius() + obj2.getRadius();
        double distance = pointDistance(obj1.getX(), obj1.getY(), obj2.getX(), obj2.getY());
        
        return (distance < combinedRadius + 2);
    }


    /**
     * Pauses the game.
     * Listens for other commands. 
     */
    public void pause()
    {
        if (Orbis.keyStroke.contains(KeyEvent.VK_SPACE))
        {
            Orbis.keyStroke.clear();
            Orbis.sounds.getGameSong().stop();
            Orbis.changeState("UPGRADE");            
        }
        
        if (!Orbis.sounds.muteSong && !Orbis.sounds.getGameSong().isActive() && Orbis.state == 2)
        {
            Orbis.sounds.getGameSong().start();
            Orbis.sounds.getGameSong().loop(Clip.LOOP_CONTINUOUSLY);
        }
    }
    
    /**
     * Method to reset every game component back to initial values.
     */
    public static void resetAll()
    {
        Upgrade.reset();
        killCount = 0;
        score = 0;
        bulletsFired = 0;      
        tokens = 0;        
        count = 0;
        
        Orbis.keyStroke.clear();
        objects.clear();
        
        pauseTime = 0;
        totalTime = 0;
        pauseTime = 0;
        
        Orbis.sounds.resetGameSong();
        
        Stats.gamesPlayed++;
        optimus.setPos(Orbis.GAME_SIZE / 2, Orbis.GAME_SIZE / 2);
        optimus.setSize(50);
        optimus.setShield(0);
        objects.add(optimus);
        running = true;
    }
    
    /**
     * Method to count off before game continues.
     */
    public void countdown()
    {
        for (count = 3; count > 0; count--)
        {
            Orbis.frame.repaint();
            Orbis.sounds.playHit();  
            Orbis.delay(900);
        }
    	
    	if (!Orbis.sounds.muteSong) Orbis.sounds.playGameSong();
    }
    
    /**
     * Overridden PaintComponent
     * Paints all game objects and text to the screen.
     */
    public void paintComponent(Graphics g2)
    {
        ((Graphics2D) g2).setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        ((Graphics2D) g2).setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON); 
        ((Graphics2D) g2).setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS, RenderingHints.VALUE_FRACTIONALMETRICS_ON);
                       
        paintObjects(g2);
        paintInfoText(g2);
        paintCountdown(g2);
    }
    
    public void paintCountdown(Graphics g2)
    {
    	if (!running)
    	{
    		
    		g2.setColor(Settings.themes.textColor);
    		g2.setFont(new Font("Courier New", Font.PLAIN, 50));
    		g2.drawString(""+ count,(Orbis.GAME_SIZE / 2), (Orbis.GAME_SIZE / 2));

    		Orbis.frame.invalidate();
    		Orbis.frame.validate();
    	}
    }
    
    public void paintInfoText(Graphics g2)
    {
        g2.setColor(Settings.themes.textColor);
        g2.setFont(new Font("Courier New", Font.PLAIN, 15));
        g2.drawString("Kill Count: " + killCount, 15, 20);
        if (!Orbis.devMode)
        {
        	g2.drawString("Score: " + score, (Orbis.GAME_SIZE / 4) + 50, 20);
            g2.drawString("Tokens: " + tokens, Orbis.GAME_SIZE - (Orbis.GAME_SIZE/4) - 125, 20);
        }else{
        	int scoreWidth = g2.getFontMetrics().stringWidth("DEV MODE");
        	g2.drawString("DEV MODE", Orbis.GAME_SIZE / 2 - scoreWidth / 2, 20);
        }
        //Version
        int versionWidth = g2.getFontMetrics().stringWidth(Orbis.Version);
        g2.drawString(Orbis.Version, Orbis.GAME_SIZE - versionWidth - 10, 20);
        
        g2.drawString("Enemies: " + objects.items(Inimicus.class), Orbis.GAME_SIZE - 110, Orbis.GAME_SIZE - 10);
        
        //Spawn times
        DecimalFormat fmt = new DecimalFormat("#.#");
        if(Orbis.devMode)
            g2.drawString("Sec/Spawn:" + (int)(spawnFunction), 15, Orbis.GAME_SIZE - 10);
        else
            g2.drawString("Sec/Spawn: " + fmt.format(spawnFunction/1000), 15, Orbis.GAME_SIZE - 10);
        
    }
    
    public static void paintObjects(Graphics g2)
    {
        for (int i = 1; i < objects.size(); i++)
            if (!objects.isEmpty(i))
                objects.get(i).paint(g2);
        optimus.paint(g2);
    }
    
    public static double pointDirection(double x1, double y1, double x2, double y2)
    {
        return (((Math.atan2(x1-x2, y1-y2) * (180/Math.PI))) + 450) % 360;
    }
    
    public static double pointDistance(double x1, double y1, double x2, double y2)
    {
        return Math.sqrt(Math.pow(x2 - x1, 2) + Math.pow(y2 - y1, 2));
    }

    @Override
    public void mousePressed(MouseEvent e)
    {
        double direction = pointDirection(optimus.getX(), optimus.getY(), e.getX(), e.getY());
        
        if(running)
        {
            Orbis.sounds.playShoot();
            objects.add(new LetalisOrb(optimus.getX(), optimus.getY(), direction));

            for (int i = 0; i < Upgrade.bullet_multi; i++)
            {
                double change = (Math.random()*Upgrade.bullet_multi*30) - (Upgrade.bullet_multi*30)/2;
                objects.add(new LetalisOrb(optimus.getX(), optimus.getY(), direction+change));
            }
        }
    }

    @Override
    public void mouseReleased(MouseEvent arg0){}

    @Override
    public void mouseClicked(MouseEvent e){}

    @Override
    public void mouseEntered(MouseEvent arg0){}

    @Override
    public void mouseExited(MouseEvent arg0){}   
}
