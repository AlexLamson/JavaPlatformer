package platformer;

import java.awt.image.*;
import javax.imageio.*;
import java.io.*;

import javax.imageio.ImageIO;

public class Tile {
	
	public static int tileSize = 20;
	
	public static int[] air = {-1, -1};
	public static int[] earth = {0, 0};
	
	public static int[] character = {0, 18};
	
	public static BufferedImage tileset_terrian;
	
	public Tile()
	{
		try
		{
			Tile.tileset_terrian = ImageIO.read(new File("res/tileset_terrian.png"));
		}catch(Exception e){ }
	}
	
}

//https://www.youtube.com/watch?v=jM7-5ScztpQ
//0:49:25