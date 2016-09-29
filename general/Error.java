package general;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.PrintWriter;


public class Error 
{
	public static String filePath;
	private File directory;
	private static File error;
	
	public Error() 
	{
		if (System.getProperty("os.name").contains("Windows"))
        {
        	filePath = "C:/Users/" + System.getProperty("user.name") + "/AppData/Local";
        }
        else if (System.getProperty("os.name").contains("Mac"))
    	{
    		filePath = "/Users/"+ System.getProperty("user.name") +"/Library/Application Support";
    	}
        
        directory = new File(filePath + "/Orbis/");
    	error = new File(filePath + "/Orbis/ErrorLog.txt");
    	if(!directory.exists())	
    		directory.mkdir();  
    	
    	//Overwrites the data every time this constuctor is called.
    	//(The log will only have exception data for that current session)
    	try
		{
		    PrintWriter pw = new PrintWriter(error);
		    pw.println("*Error Log*");
		    pw.println();
		    pw.close();
		} catch (FileNotFoundException e) {e.printStackTrace();}
    	  	
	}
	
	/**
	 * Method to append to the error log.
	 * @param err
	 */
	public void writeError(String err)
	{
		try
		{
		    FileWriter fw = new FileWriter(error, true);
		    PrintWriter pw = new PrintWriter(fw);
		    pw.println(err);
		    pw.println();
		    pw.close();
		} catch (Exception e) {e.printStackTrace();}
	}

}
