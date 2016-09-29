package general;
import java.awt.event.KeyEvent;
import java.net.URL;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;

import states.Settings;

public class Sound 
{
    private Clip gameSong;
    private Clip menuSong;
    private Clip pop;
    private Clip shoot;
    private Clip shoot1;
    private Clip shoot2;
    private Clip hit;
    private Clip hit1;
    private Clip hit2;
    private Clip punch;
    private Clip punch1;
    private Clip punch2;
    private Clip grow;
    private Clip grow1;
    private Clip sad;
    private Clip ping;
    private URL url;
    private AudioInputStream inStream;
    private AudioInputStream popStream;
    private AudioInputStream shootStream;
    private AudioInputStream shoot1Stream;
    private AudioInputStream shoot2Stream;
    private AudioInputStream hitStream;
    private AudioInputStream hit1Stream;
    private AudioInputStream hit2Stream;
    private AudioInputStream punchStream;
    private AudioInputStream punch1Stream;
    private AudioInputStream punch2Stream;
    private AudioInputStream growStream;
    private AudioInputStream grow1Stream;
    private AudioInputStream sadStream;
    private AudioInputStream pingStream;
    private FloatControl gameSongCon;
    private FloatControl menuSongCon;
    private int shootCount;
    private int hitCount;
    private int punchCount;
    private int growCount;
    
    private float gameSongVol;
    private float menuSongVol;
    private float effectsVol;
    
    public boolean muteSong;
    
    public Sound(int songLvl, int effectsLvl)
    {
        try
        {
            gameSong = AudioSystem.getClip();
            menuSong = AudioSystem.getClip();
            pop = AudioSystem.getClip();
            shoot = AudioSystem.getClip();
            shoot1 = AudioSystem.getClip();
            shoot2 = AudioSystem.getClip();
            hit = AudioSystem.getClip();
            hit1 = AudioSystem.getClip();
            hit2 = AudioSystem.getClip();
            punch = AudioSystem.getClip();
            punch1 = AudioSystem.getClip();
            punch2 = AudioSystem.getClip();
            grow = AudioSystem.getClip();
            grow1 = AudioSystem.getClip();
            sad = AudioSystem.getClip();
            ping = AudioSystem.getClip();
            shootCount = 0;
            hitCount = 0;
            punchCount = 0;
            growCount = 0;
            
            updateSongVol(songLvl);
            updateEffectsVol(effectsLvl);
            
            InitializeShoot();
            InitializeHit();
            InitializePunch();
            InitializeGrow();
        }
        catch(Exception e) {Orbis.error.writeError("Sound Failure:  Failed to Initialize Sound Values");
        e.printStackTrace();}
        
        muteSong = false;
    }

    /**
     * Plays pop sound that menu buttons have.
     */
    public void playPop()
    {       
        if (!pop.isOpen())
        {
            try
            {
                url = Sound.class.getResource("/sounds/Pop.wav");           
                popStream = AudioSystem.getAudioInputStream(url);
                pop.open(popStream);
                FloatControl p = (FloatControl)pop.getControl(FloatControl.Type.MASTER_GAIN);
                p.setValue(effectsVol);
                

            }catch(Exception e){
            	Orbis.error.writeError("Sound Failure: Pop failed to play");
                e.printStackTrace();
            }
            pop.start();
        } else {pop.stop(); pop.setFramePosition(0); pop.start();}
    }
    
    /**
     * Initalizes three seperate streams and clips with shoot.
     * Sets volume level for each.
     */
    public void InitializeShoot()
    {       
        if (!shoot.isOpen())
        {
            try
            {
                url = Sound.class.getResource("/sounds/Woosh.wav");           
                shootStream = AudioSystem.getAudioInputStream(url);
                shoot1Stream = AudioSystem.getAudioInputStream(url);
                shoot2Stream = AudioSystem.getAudioInputStream(url);
                shoot.open(shootStream);
                FloatControl s = (FloatControl)shoot.getControl(FloatControl.Type.MASTER_GAIN);
                s.setValue(effectsVol);
                shoot1.open(shoot1Stream);
                FloatControl s1 = (FloatControl)shoot1.getControl(FloatControl.Type.MASTER_GAIN);
                s1.setValue(effectsVol);
                shoot2.open(shoot2Stream);
                FloatControl s2 = (FloatControl)shoot2.getControl(FloatControl.Type.MASTER_GAIN);
                s2.setValue(effectsVol);

            }catch(Exception e){
            	Orbis.error.writeError("Sound Failure: Shoot failed to Initialize");
                e.printStackTrace();
            }
        }
    }
    
    public void playShoot()
    {       
        switch(shootCount)
        {
        case 0:
            shoot1.setFramePosition(0); shoot.start();
            shootCount++;
            break;
        case 1:
            shoot2.setFramePosition(0); shoot1.start();
            shootCount++;
            break;
        case 2:
            shoot.setFramePosition(0); shoot2.start();
            shootCount = 0;
            break;
        }
    }
    
    /**
     * Initializes three separate streams and clips with hit.
     * Sets volume level for each.
     */
    public void InitializeHit()
    {       
        if (!hit.isOpen())
        {
            try
            {
                url = Sound.class.getResource("/sounds/Click.wav");           
                hitStream = AudioSystem.getAudioInputStream(url);
                hit1Stream = AudioSystem.getAudioInputStream(url);
                hit2Stream = AudioSystem.getAudioInputStream(url);
                hit.open(hitStream);
                FloatControl h = (FloatControl)hit.getControl(FloatControl.Type.MASTER_GAIN);
                h.setValue(effectsVol);
                hit1.open(hit1Stream);
                FloatControl h1 = (FloatControl)hit1.getControl(FloatControl.Type.MASTER_GAIN);
                h1.setValue(effectsVol);
                hit2.open(hit2Stream);
                FloatControl h2 = (FloatControl)hit2.getControl(FloatControl.Type.MASTER_GAIN);
                h2.setValue(effectsVol);

            }catch(Exception e){
            	Orbis.error.writeError("Sound Failure: Hit Failed to Initialize");
                e.printStackTrace();
            }
        }
    }
    
    public void playHit()
    {       
        switch(hitCount)
        {
        case 0:
            hit1.setFramePosition(0); hit.start();
            hitCount++;
            break;
        case 1:
            hit2.setFramePosition(0); hit1.start();
            hitCount++;
            break;
        case 2:
            hit.setFramePosition(0); hit2.start();
            hitCount = 0;
            break;
        }
    }
    
    /**
     * Initalizes three seperate streams and clips with punch.
     */
    public void InitializePunch()
    {       
        if (!punch.isOpen())
        {
            try
            {
                url = Sound.class.getResource("/sounds/Jab.wav");           
                punchStream = AudioSystem.getAudioInputStream(url);
                punch1Stream = AudioSystem.getAudioInputStream(url);
                punch2Stream = AudioSystem.getAudioInputStream(url);
                punch.open(punchStream);
                FloatControl p = (FloatControl)punch.getControl(FloatControl.Type.MASTER_GAIN);
                p.setValue(effectsVol);
                punch1.open(punch1Stream);
                FloatControl p1 = (FloatControl)punch1.getControl(FloatControl.Type.MASTER_GAIN);
                p1.setValue(effectsVol);
                punch2.open(punch2Stream);
                FloatControl p2 = (FloatControl)punch2.getControl(FloatControl.Type.MASTER_GAIN);
                p2.setValue(effectsVol);

            }catch(Exception e){
            	Orbis.error.writeError("Sound Failure: Punch Failed to Initialize");
                e.printStackTrace();
            }
        }
    }
    
    public void playPunch()
    {       
        switch(punchCount)
        {
        case 0:
            punch1.setFramePosition(0); punch.start();
            punchCount++;
            break;
        case 1:
            punch2.setFramePosition(0); punch1.start();
            punchCount++;
            break;
        case 2:
            punch.setFramePosition(0); punch2.start();
            punchCount = 0;
            break;
        }
    }
    
    /**
     * Initalizes three seperate streams and clips with grow.
     */
    public void InitializeGrow()
    {       
        if (!grow.isOpen())
        {
            try
            {
                url = Sound.class.getResource("/sounds/Jump.wav");           
                growStream = AudioSystem.getAudioInputStream(url);
                grow1Stream = AudioSystem.getAudioInputStream(url);
                grow.open(growStream);
                FloatControl g = (FloatControl)grow.getControl(FloatControl.Type.MASTER_GAIN);
                grow1.open(grow1Stream);
                FloatControl g1 = (FloatControl)grow1.getControl(FloatControl.Type.MASTER_GAIN);
                
                g.setValue(effectsVol - 6);
                g1.setValue(effectsVol - 6);

            }catch(Exception e){
            	Orbis.error.writeError("Sound Failure: Grow Failed to Initialize");
                e.printStackTrace();
            }
        }
    }
    
    public void playGrow()
    {       
        switch(growCount)
        {
        case 0:
            grow1.setFramePosition(0); grow.start();
            growCount++;
            break;
        case 1:
            grow.setFramePosition(0); grow1.start();
            growCount = 0;
            break;
        }
    }
    
    
    /**
     * Plays gameover cry.
     */
    public void playSad()
    {
        if (!sad.isOpen())
        {
            try
            {
                url = Sound.class.getResource("/sounds/Sad.wav");           
                sadStream = AudioSystem.getAudioInputStream(url);
                sad.open(sadStream);
                FloatControl s = (FloatControl)sad.getControl(FloatControl.Type.MASTER_GAIN);
                s.setValue(effectsVol + 10);

            }catch(Exception e){
            	Orbis.error.writeError("Sound Failure: Sad Failed to Play");
                e.printStackTrace();
            }
            sad.start();
        } else {sad.setFramePosition(0); sad.start();}
    }
    
    /**
     * Plays token ping.
     */
    public void playPing()
    {
        if (!ping.isOpen())
        {
            try
            {
                url = Sound.class.getResource("/sounds/Ping.wav");           
                pingStream = AudioSystem.getAudioInputStream(url);
                ping.open(pingStream);
                FloatControl p = (FloatControl)ping.getControl(FloatControl.Type.MASTER_GAIN);
                p.setValue(effectsVol);

            }catch(Exception e){
            	Orbis.error.writeError("Sound Failure: Ping Failed to Play.");
                e.printStackTrace();
            }
            ping.start();
        } else {ping.setFramePosition(0); ping.start();}
    }
    
    /**
     * Plays main song in loop.
     */
    public void playGameSong()
    {
        if (!gameSong.isOpen())
        {
            try
            {
                url = Sound.class.getResource("/sounds/IronSuit.wav");           
                inStream = AudioSystem.getAudioInputStream(url);
                gameSong.open(inStream);
                
                gameSongCon = (FloatControl)gameSong.getControl(FloatControl.Type.MASTER_GAIN);
                gameSongCon.setValue(gameSongVol);
               
                gameSong.loop(Clip.LOOP_CONTINUOUSLY);
            }
            catch(Exception e)
            {
            	Orbis.error.writeError("Sound Failure: Game Song Failed to Play");
            }
        } else {resetGameSong(); gameSong.loop(Clip.LOOP_CONTINUOUSLY);}
    }
    
    /**
     * Plays main song in loop.
     */
    public void playMenuSong()
    {
        if (!menuSong.isOpen())
        {
            try
            {
                url = Sound.class.getResource("/sounds/MenuSong.wav");
                inStream = AudioSystem.getAudioInputStream(url);
                menuSong.open(inStream);
                
                menuSongCon = (FloatControl)menuSong.getControl(FloatControl.Type.MASTER_GAIN);
                menuSongCon.setValue(menuSongVol);
                menuSong.loop(Clip.LOOP_CONTINUOUSLY);
            }
            catch(Exception e)
            {
                Orbis.error.writeError("Sound Failure: Menu Song Failed to Play");
            }
        } else {resetMenuSong(); menuSong.loop(Clip.LOOP_CONTINUOUSLY);}
    }
    
    /**
     * Method to reset the song back to beginning. 
     */
    public void resetGameSong()
    {
        gameSong.setFramePosition(0);
    }
    /**
     * Method to reset the song back to beginning. 
     */
    public void resetMenuSong()
    {
        menuSong.setFramePosition(0);
    }
    
    
    public Clip getGameSong()
    {
        return gameSong;
    }
    
    public Clip getMenuSong()
    {
        return menuSong;
    }
    
    /**
     * Method to mute both songs.
     */
    public void muteSong()
    {
        if (Orbis.keyStroke.contains(KeyEvent.VK_X))
        {
            if(!muteSong)
            {
                muteSong = true;
                gameSong.stop();
                menuSong.stop();
            }
            else if (muteSong)
            {
                if(Settings.currentSongVol == 0)
                {
                    updateSongVol(5);
                    gameSong.close();
                }
                muteSong = false;
                playGameSong();
            }           
        }
        //If keyStroke still contains X, remove it.
        if (Orbis.keyStroke.contains(KeyEvent.VK_X))
            Orbis.keyStroke.remove(new Integer(KeyEvent.VK_X));
    }
    
    
    /**
     * Updates song volume based on a 1-10 level
     * @param level 1-10
     */
    public void updateSongVol(int level)
    {
        if (level == 0)
        {
            gameSongVol = menuSongVol = -1000.0f;
            muteSong = true;
        }
        else
        {
            muteSong = false;
            level -= 9;
            gameSongVol = -10.0f + level * 5;
            menuSongVol = level * 5;
            
        }
    }

    /**
     * Updates effects volume based on a 1-10 level.
     * @param level 1-10 
     */
    public void updateEffectsVol(int level)
    {
        if (level == 0)
            effectsVol = -1000.0f;
        else
        {
            level-= 9;
            effectsVol = level * 5;
        }
    }
    
    public void closeSongs()
    {
        gameSong.close();
        menuSong.close();
    }

    /**
     * Plays any sound based on String.(Unused)
     * (Must have a .wav file matching 's' in the sound collection)
     */
    @SuppressWarnings("resource")
    public static void playSound(String s)
    {   
        Clip sound = null;
        AudioInputStream stream = null;
        try
        {
            sound = AudioSystem.getClip();
            URL url = Sound.class.getResource("../sounds/" + s + ".wav");           
            stream = AudioSystem.getAudioInputStream(url);
            sound.open(stream);
            sound.start();
        }catch(Exception e){Orbis.error.writeError("Sound Failure: playSound(String s) failed to run");}
    }
}
