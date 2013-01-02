package platformer;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.ArrayList;

public class Menu
{
	public int currentMenu = 0;			//0 - main menu, 1 - pause menu, 2 - dead menu
	public Color bgColor = new Color(0, 0, 0);
	public static ArrayList<MenuButton> button = new ArrayList<MenuButton>();
//	public static MenuButton[] button = new MenuButton[5];
	
	public Menu()
	{
		currentMenu = Main.currentMenu;
	}
	
	public void tick()
	{
		if(Main.showMenu)
		{
			int w = Main.frame.getWidth()/2;
			int h = Main.frame.getHeight()/2;
			
			if(currentMenu == 0)
			{
				
				button.add(new MenuButton(new Rectangle(w/2-w/2/2, h/9*1, w/2, h/5), "Fun Man!", 0));
				button.add(new MenuButton(new Rectangle(w/2-w/2/2, h/9*4, w/2, h/8), "Play", 1));
				button.add(new MenuButton(new Rectangle(w/2-w/2/2, h/9*6, w/2, h/8), "Quit", 2));
			}
			else if(currentMenu == 1)
			{
				
			}
			else if(currentMenu == 2)
			{
				
			}
		}
	}
	
	public static void click()
	{
		for(int i = 0; i < button.size(); i++)
		{
			System.out.println(Main.mse.x);
			
			if(button.get(i).contains(new Point(Main.mse.x, Main.mse.y)))
			{
				button.get(i).buttonPressed = true;
				
				switch(button.get(i).buttonID)
				{
					case 0:
						System.out.println("case 0!");
						break;
					case 1:
						System.out.println("case 1");
						break;
					case 2:
						System.exit(0);
						break;
				}
				
			}
		}
	}
	
	public void render(Graphics g)
	{
		g.setColor(bgColor);
		g.fillRect(0, 0, Main.pixel.width, Main.pixel.height);
		
		for(int i = 0; i < button.size(); i++)
		{
			button.get(i).render(g);
		}
	}
}
