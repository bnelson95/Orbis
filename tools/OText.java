package tools;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

public class OText
{
	private int x;
	private int y;
	private int width;

	private String text;

	private String font;
	private int fontSize;
	private double lineSpacing;
	private char alignment;
	private Color color;


	public OText(int x, int y, int width, String text)
	{
		this.x = x;
		this.y = y;
		this.width = width;
		this.text = text;

		this.font = "Arial";
		this.fontSize = 12;
		this.lineSpacing = 1.0;
		this.alignment = 'L';
		this.color = HTMLColor.Black;
	}


	public void setFont(String font, int fontSize, double lineSpacing, char alignment, Color color)
	{
		this.font = font;
		this.fontSize = fontSize;
		this.alignment = alignment;
		this.lineSpacing = lineSpacing;
		this.color = color;
	}


	public void draw(Graphics g)
	{
		//Initialize offsets to 0
		int offsetX = 0;
		int offsetY = 0;
		
		//Set the font
		g.setFont(new Font(font, Font.PLAIN, fontSize));
		g.setColor(color);
		
		//Split the string into an array on the spaces
		String[] words = text.split("\\s+");
		
		//Until there are no words left in the array
		for (int i = 0; i < words.length;)
		{
			String line = "";
			
			//While there is another word in the string AND there is enough room for the word in the line
			while (i < words.length && g.getFontMetrics().stringWidth(line+words[i]) < this.width)
			{
				//If the current word is a break tag
				if (words[i].equals("<br>"))
				{
					//Move index to the next word and Break out of the while loop to stop adding words to the line
					i++; break;
				}
				
				//If the current word is not a tag 
				else
				{
					//Add the word to the line and move index to the next word
					line += words[i]; i++;
					
					if (i < words.length && !words[i].equals("<br>") && g.getFontMetrics().stringWidth(line+words[i]) < this.width)
					{
						line += " ";
					}
				}
			}
			
			switch (alignment)
			{
				case 'L' : offsetX = 0; break;
				case 'C' : offsetX = width/2 - g.getFontMetrics().stringWidth(line)/2; break;
				case 'R' : offsetX = width - g.getFontMetrics().stringWidth(line); break;
				default : offsetX = 0; break;
			}
			
			//Draw the line ofwords
			g.drawString(line, x+offsetX, y+offsetY);
			
			//Start new line of words
			offsetY += newLine();
		}
	}
	
	
	private int newLine()
	{
		return (int)((double)fontSize * lineSpacing);
	}
	
	
	public void setX(int x) {this.x = x;}
	public void setY(int y) {this.y = y;}
}
