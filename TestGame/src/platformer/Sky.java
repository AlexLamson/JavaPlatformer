package platformer;

import java.awt.Color;
import java.awt.Graphics;

public class Sky {
	public boolean isNight = false;
	
	public int r1 = 0, g1 = 150, b1 = 255;		//day
	public int r2 = 20, g2 = 20, b2 = 100;		//night
	public double r = r1, g = g1, b = b1;
	
	public int increment = 1;
	
	public int dayFrame = 0, dayTime = 1000;
	
	public Sky()
	{
		if(isNight)
		{
			r = r1;
			g = g1;
			b = b1;
		}
		else
		{
			r = r2;
			g = g2;
			b = b2;
		}
	}
	
	public void tick()
	{
		dayFrame += increment;
		if(dayFrame > dayTime)
		{
			increment = -1;
			dayFrame = dayTime;
		}
		else if(dayFrame < 0)
		{
			increment = 1;
			dayFrame = 0;
		}
			
		
//		if(dayFrame >= dayTime)
//		{
//			isNight = !isNight;
//			dayFrame = 0;
//		}
//		else
//		{
//			dayFrame++;
//		}
		
//		System.out.println(Math.abs(b2-b1)*dayFrame/dayTime);
		
		r = r1 + Math.abs(r2-r1)*dayFrame/dayTime;
		g = g2 + Math.abs(g2-g1)*dayFrame/dayTime;
		b = b2 + Math.abs(b2-b1)*dayFrame/dayTime;
	}
	
	public void render(Graphics gr)
	{
		gr.setColor(new Color((int)r, (int)g, (int)b));
		gr.fillRect(0, 0, Main.pixel.width, Main.pixel.height);
	}
}
