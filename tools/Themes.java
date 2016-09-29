package tools;

import java.awt.Color;
import java.net.URL;
import java.util.Scanner;

import tools.HTMLColor;


public class Themes
{
    public Color optimusColor;
    public Color letalisColor;
    public Color parvusColor;
    public Color magnusColor;
    public Color scindoColor;
    public Color amoveoColor;
    public Color praedoColor;
    public Color healthColor;
    public Color shieldColor;
    public Color tokenColor;
    public Color textColor;
    public Color background;
    
    
    
    public void classic()
    {
        optimusColor = HTMLColor.Blue;
        letalisColor = HTMLColor.Black;
        
        parvusColor = HTMLColor.Red;
        magnusColor = HTMLColor.Gray;
        scindoColor = HTMLColor.Cyan;
        amoveoColor = HTMLColor.Gold;
        praedoColor = HTMLColor.Indigo;
        
        healthColor = HTMLColor.Green;
        shieldColor = HTMLColor.Magenta;
        tokenColor  = HTMLColor.DodgerBlue;
        
        textColor  = HTMLColor.Black;
        background = HTMLColor.White;
    }
    
    
    public void random()
    {
        optimusColor = HTMLColor.random();
        letalisColor = HTMLColor.random();
        parvusColor  = HTMLColor.random();
        magnusColor  = HTMLColor.random();
        scindoColor  = HTMLColor.random();
        amoveoColor  = HTMLColor.random();
        healthColor  = HTMLColor.random();
        shieldColor  = HTMLColor.random();
        tokenColor   = HTMLColor.random();
        textColor    = HTMLColor.random();
        background   = HTMLColor.random();
    }
    
    
    public void chill()
    {
        optimusColor = HTMLColor.Blue;
        letalisColor = HTMLColor.MidnightBlue;
        
        parvusColor = HTMLColor.DodgerBlue;
        magnusColor = HTMLColor.SteelBlue;
        scindoColor = HTMLColor.DarkTurquoise;
        amoveoColor = HTMLColor.DeepSkyBlue;
        praedoColor = HTMLColor.MediumSlateBlue;
        
        healthColor = HTMLColor.SkyBlue;
        shieldColor = HTMLColor.CadetBlue;
        tokenColor  = HTMLColor.Aqua;
        
        textColor  = HTMLColor.DimGray;
        background = HTMLColor.AliceBlue;
    }
    
    
    public void warm()
    {
    	optimusColor = HTMLColor.Gray;
        letalisColor = HTMLColor.DarkRed;
        
        parvusColor = HTMLColor.Red;
        magnusColor = HTMLColor.Brown;
        scindoColor = HTMLColor.Tomato;
        amoveoColor = HTMLColor.DarkOrange;
        praedoColor = HTMLColor.IndianRed;
        
        healthColor = HTMLColor.GoldenRod;
        shieldColor = HTMLColor.RosyBrown;
        tokenColor  = HTMLColor.Crimson;
        
        textColor  = HTMLColor.SaddleBrown;
        background = HTMLColor.AntiqueWhite;
    }
    
    
    public void blackAndWhite()
    {
    	optimusColor = HTMLColor.Black;
        letalisColor = HTMLColor.Black;
        
        parvusColor = HTMLColor.Black;
        magnusColor = HTMLColor.Black;
        scindoColor = HTMLColor.Black;
        amoveoColor = HTMLColor.Black;
        praedoColor = HTMLColor.Black;
        
        healthColor = HTMLColor.Gray;
        shieldColor = HTMLColor.Gray;
        tokenColor  = HTMLColor.Gray;
        
        textColor  = HTMLColor.Black;
        background = HTMLColor.White;
    }
    
    
    public void holiday()
    {
    	try
    	{
    		URL url = new URL("https://drive.google.com/uc?export=download&id=0B2kAGeixMJa0NDNqNk1KVm1IYVE");
    		Scanner in = new Scanner(url.openStream());

    		optimusColor = new Color(in.nextInt(), in.nextInt(), in.nextInt());
    		letalisColor = new Color(in.nextInt(), in.nextInt(), in.nextInt());

    		parvusColor = new Color(in.nextInt(), in.nextInt(), in.nextInt());
    		magnusColor = new Color(in.nextInt(), in.nextInt(), in.nextInt());
    		scindoColor = new Color(in.nextInt(), in.nextInt(), in.nextInt());
    		amoveoColor = new Color(in.nextInt(), in.nextInt(), in.nextInt());
    		praedoColor = new Color(in.nextInt(), in.nextInt(), in.nextInt());

    		healthColor = new Color(in.nextInt(), in.nextInt(), in.nextInt());
    		shieldColor = new Color(in.nextInt(), in.nextInt(), in.nextInt());
    		tokenColor  = new Color(in.nextInt(), in.nextInt(), in.nextInt());

    		textColor  = new Color(in.nextInt(), in.nextInt(), in.nextInt());
    		background = new Color(in.nextInt(), in.nextInt(), in.nextInt());

    		in.close();
    	}
    	catch(Exception e) { classic(); }
    }
}
