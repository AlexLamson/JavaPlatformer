package platformer;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.geom.Rectangle2D;

public class MenuButton extends Rectangle
{
	public String buttonText = "Default";
	public Font buttonFont = new Font("SansSerif", Font.PLAIN, 10);
	public int buttonID = -1;

    public Color buttonTextColor = Color.white;
    public Color buttonReleaseColor = Color.gray;
    public Color buttonPressColor = Color.darkGray;
    
	public boolean buttonPressed = false;
    
	public MenuButton(Rectangle size, String s, int id)
	{
		setBounds(size);
		buttonText = s;
	}
	
	public void drawText(Graphics g)
	{
		FontMetrics fm = g.getFontMetrics(buttonFont);
		Rectangle2D rect = fm.getStringBounds(buttonText,g);
		
		int fX = x + (width - (int)(rect.getWidth()) ) / 2;
		int fY = y + (height - (int)(rect.getHeight()) ) / 2 + fm.getAscent();
		
		g.setColor(buttonTextColor);
		g.drawString(buttonText, fX, fY);		//draw centered text
	}
	
	public void render(Graphics g)
	{
		if(buttonPressed)
			g.setColor(buttonPressColor);
		else
			g.setColor(buttonReleaseColor);
		
		g.fillRect(x, y, width, height);
		
		drawText(g);
	}
}
