package platformer;

import java.applet.*;
import javax.swing.*;
import java.awt.*;
import java.util.*;

public class Main extends Applet implements Runnable
{
	public static int pixelSize = 2;
	public static double sX = 0, sY = 0;
	public static double dir = 0;
	
	public static Dimension realSize;
	public static Dimension size = new Dimension(700,560);		//will hold the window dimensions
	public static Dimension pixel = new Dimension(size.width/pixelSize, size.height/pixelSize);
	
	public static Point mse = new Point(0, 0);
	
	public static String windowName = "Alex's 2D platformer";
	
	public static boolean isRunning = false;
	public static boolean isMoving = false;
	public static boolean isSprinting = false;
	public static boolean isJumping = false;
	public static boolean isMouseLeft = false;
	public static boolean isMouseRight = false;
	
	private Image screen;
	
	public static Level level;
	public static Character character;
	public static Inventory inventory;
	public static Sky sky;
	public static ArrayList<Mob> mob = new ArrayList<Mob>();
	
	public Main()
	{
		setPreferredSize(size);
		
		addKeyListener(new Listening());
		addMouseListener(new Listening());
		addMouseMotionListener(new Listening());
		addMouseWheelListener(new Listening());
	}
	
	public void start()
	{
		requestFocus();
		
		//defining objects
		new Tile();		//loading images
		level = new Level();
		sky = new Sky();
		character = new Character(Tile.tileSize, Tile.tileSize*2);
		inventory = new Inventory();
		mob.add(new Iggy(50,10,Tile.tileSize,Tile.tileSize*2,Tile.mobIggy));
		
		//start the game loop
		isRunning = true;
		new Thread(this).start();
	}
	
	public void stop()
	{
		isRunning = false;
	}
	
	public void tick()
	{
		if(frame.getWidth() != realSize.width || frame.getHeight() != realSize.height)
		{
			frame.pack();
		}
		
		level.tick();
		character.tick();
		sky.tick();
		for(int i = 0; i < mob.toArray().length; i++)
		{
			mob.get(i).tick();
		}
		
	}
	
	public void render()
	{
		Graphics g = screen.getGraphics();
		
//		g.setColor(new Color(0, 150, 255));
//		g.fillRect(0, 0, pixel.width, pixel.height);
		
		sky.render(g);
		level.render(g);
		character.render(g);
		inventory.render(g);
		
		for(int i = 0; i < mob.toArray().length; i++)
		{
			mob.get(i).render(g);
		}
		
		//new Iggy(10,10,Tile.tileSize,Tile.tileSize*2,Tile.mobIggy).render(g);
		
		g = getGraphics();
		
		g.drawImage(screen, 0, 0, size.width, size.height, 0, 0, pixel.width, pixel.height, null);
		g.dispose();		//throw it away to avoid lag from too many graphics objects
	}
	
	public void run()
	{
		screen = createVolatileImage(pixel.width, pixel.height);	//actually use the graphics card (less lag)
		
		while(isRunning)
		{
			tick();
			render();
			
			try
			{
				Thread.sleep(5);
			}catch(Exception e){ }
		}
	}
	
	private static JFrame frame;
	public static void main(String[] args) {
		Main main = new Main();
		
		frame = new JFrame();
		frame.add(main);
		frame.pack();
		
		realSize = new Dimension(frame.getWidth(), frame.getHeight());
		
		frame.setTitle(windowName);
		frame.setResizable(true);
		frame.setLocationRelativeTo(null);		//null makes it go to the center
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		
		main.start();
	}

}
