package platformer;

import java.awt.image.*;
import java.io.*;
import javax.imageio.ImageIO;

public class Tile {
	
	public static int tileSize = 20;
	public static int invCellLength = 8;
	public static int invCellSize = 25;
	public static int invCellSpace = 4;
	public static int invBorderSpace = 4;
	public static int invItemBorder = 3;
	public static int invHeight = 4;
	
	public static final int[] air = {-1, -1};
	public static final int[] earth = {0, 0};
	public static final int[] grass = {1, 0};
	public static final int[] sand = {2, 0};
	public static final int[] solidAir = {3, 0};
	public static final int[] bedrock = {4, 0};
	public static final int[] wood = {5, 0};
	public static final int[] leaves = {6, 0};
	
	public static int[] mobIggy = {0, 16};
	public static int[] character = {0, 18};
	
	public static BufferedImage tileset_terrian;
	public static BufferedImage tile_cell;
	public static BufferedImage tile_select;
	
	public Tile()
	{
		try
		{
			Tile.tileset_terrian = ImageIO.read(new File("res/tileset_terrian.png"));
		}catch(Exception e){ }
		
		try
		{
			Tile.tile_cell = ImageIO.read(new File("res/tile_cell.png"));
		}catch(Exception e){ }
		
		try
		{
			Tile.tile_select = ImageIO.read(new File("res/tile_select.png"));
		}catch(Exception e){ }
	}
	
}