package platformer;

import java.applet.*;
import javax.swing.*;
import java.awt.*;

public class Main extends Applet implements Runnable
{
	private static int pixelSize = 2;
	
	public static Dimension size = new Dimension(700,560);		//will hold the window dimensions
	public static Dimension pixel = new Dimension(size.width/pixelSize, size.height/pixelSize);
	public static String windowName = "Alex's 2D platformer";
	public static boolean isRunning = false;
	
	private Image screen;
	
	public Main()
	{
//		setSize(size);			//technically don't need this
		setPreferredSize(size);
	}
	
	public void start()
	{
		//defining objects
		
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
		
	}
	
	public void render()
	{
		Graphics g = screen.getGraphics();
		
		g.setColor(new Color(0,100,255));
		g.fillRect(0, 0, 100, 100);
		
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
	
	public static void main(String[] args) {
		Main main = new Main();
		
		JFrame frame = new JFrame();
		frame.add(main);
		frame.pack();
		frame.setTitle(windowName);
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);		//null makes it go to the center
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		
		main.start();
	}

}
