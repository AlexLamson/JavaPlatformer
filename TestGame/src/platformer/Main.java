package platformer;

import java.applet.*;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.util.ArrayList;
import java.util.Arrays;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class Main extends Applet implements Runnable
{
	public static int pixelSize = 4;
	
	public static boolean degbugMode = false;
	
	public double minQuality = 10;
	public static double computerSpeed = 10;						//bigger # = slower computer
	public long currentTime = System.currentTimeMillis();
	public int tickDelay = 5;
	public double lagTime = 0;
	public int fpsBufferSize = 8;
	public double[] lagArray = new double[fpsBufferSize];
	public int lagArrayPos = 0;
	public double averageFPS = 0;
	
	public static boolean showMenu = false;
	public static int currentMenu = 0;			//0 - main menu, 1 - pause menu, 2 - dead menu
	
	public static Dimension realSize;
	public static Dimension size = new Dimension(700, 560);		//will hold the window dimensions
	public static Dimension pixel = new Dimension(size.width/pixelSize, size.height/pixelSize);
	
	public static Point mse = new Point(0, 0);
	
	public static String windowName = "Alex's 2D platformer";
	
	public static boolean isRunning = false;
	public static boolean isMouseLeft = false;
	public static boolean isMouseMiddle = false;
	public static boolean isMouseRight = false;
	public static boolean isMoving = false;
	public static boolean isSprinting = false;
	public static boolean isJumping = false;

	public static JFrame frame;
	private Image screen;
	
	public static Menu menu;
	public static Level level;
	public static Character character;
	public static Inventory inventory;
	public static Sky sky;
	public static Spawner spawner;
	public static ArrayList<Mob> mob = new ArrayList<Mob>();

	private GameClient socketClient;
	private GameServer socketServer;
	
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
		
		for(int i = 0; i < fpsBufferSize; i++){ lagArray[i] = 0; }
		
		//defining objects
		menu = new Menu();
		new Tile();
		level = new Level();
		sky = new Sky();
		character = new Character(Tile.tileSize, Tile.tileSize*2, , JOptionPane.showInputDialog(this, "Enter a username: "));
		inventory = new Inventory();
		spawner = new Spawner();
		
		//start the game loop
		isRunning = true;
		new Thread(this).start();
		
		if(JOptionPane.showConfirmDialog(this, "Want to run the server?") == 0)
		{
			socketServer = new GameServer(this);
			socketServer.start();
		}
		
		socketClient = new GameClient(this, "localhost");
		socketClient.start();
		
		socketClient.sendData("ping".getBytes());
	}
	
	public void stop()
	{
		isRunning = false;
	}
	
	public void tick()
	{
//		if(frame.getWidth() != realSize.width || frame.getHeight() != realSize.height)
//		{
//			frame.pack();
//		}
		
		level.tick();
		character.tick();
		sky.tick();
		for(int i = 0; i < mob.size(); i++)
		{
			if(mob.get(i) != null)
			{
				mob.get(i).tick();
			}
			else
			{
				Spawner.killMob(i);		//why? because errors!
			}
		}
	}
	
	public void render()
	{
		Graphics g = screen.getGraphics();
		
		if(showMenu)
			menu.render(g);
		else
		{
			sky.render(g);
			level.render(g);
			character.render(g);
			//inventory.render(g);
			
			for(int i = 0; i < mob.toArray().length; i++)
			{
				mob.get(i).render(g);
			}
		}
		
		double average = 0;
		for(int i = 0; i < fpsBufferSize; i++){ average += lagArray[i]; }
		averageFPS = average/fpsBufferSize;
		
		if(degbugMode)
		{
			g.setColor(new Color(255, 0, 0));
			g.drawString("fps: "+Arrays.toString(lagArray), 0, 10);
			g.drawString(""+(int)(System.currentTimeMillis()-currentTime-tickDelay), 0, 25);
		}
		
//		System.out.println(Arrays.toString(lagArray));
		
		g = getGraphics();
		
		g.drawImage(screen, 0, 0, size.width, size.height, 0, 0, pixel.width, pixel.height, null);
		g.dispose();		//throw it away to avoid lag from too many graphics objects
	}
	
	public void run()
	{
		screen = createVolatileImage(pixel.width, pixel.height);	//actually use the graphics card (less lag)
		
		while(isRunning)
		{
			if(showMenu)
				menu.tick();
			else
				tick();
			render();
			
			//System.out.println(System.currentTimeMillis());
			
			lagTime = (System.currentTimeMillis() - currentTime - tickDelay);		//lag in milliseconds
			currentTime = System.currentTimeMillis();
			
			lagArray[lagArrayPos] = lagTime;
			
			lagArrayPos++;
			if(lagArrayPos >= fpsBufferSize)
				lagArrayPos = 0;
			
			if(degbugMode)
			{
				System.out.println(lagTime);
				
				System.out.println("tickDelay: "+tickDelay+" computerSpeed: "+computerSpeed);
				
				System.out.println("eh: "+computerSpeed);
				if(computerSpeed <= 0)
					computerSpeed = 1;
				else if(computerSpeed > minQuality)
					computerSpeed = minQuality;
			}
				
			try
			{
				Thread.sleep((int)(tickDelay*computerSpeed));
			}catch(Exception e){ }
		}
	}
	
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
		
		if(computerSpeed <= 0)
			computerSpeed = 1;
		
		main.start();
	}
}
