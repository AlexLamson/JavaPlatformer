package platformer;

import java.awt.*;

public class Level
{
	public int worldW = 50, worldH = 20;
	public Block[][] block = new Block[worldW][worldH];

	public Level()
	{
		for(int x = 0; x < block.length; x++)
		{
			for(int y = 0; y < block[0].length; y++)
			{
				block[x][y] = new Block(new Rectangle(x*Tile.tileSize, y*Tile.tileSize, Tile.tileSize, Tile.tileSize), Tile.air);
			}
		}
		
		generateLevel();
	}
	
	public void generateLevel()
	{
		for(int x = 0; x < block.length; x++)
		{
			for(int y = 0; y < block[0].length; y++)
			{
				if(x == 0 || y == 0 || x == block.length-1 || y == block[0].length-1)
				{
					block[x][y].id = Tile.earth;
				}
			}
		}
	}
	
	public void tick()
	{
		
	}
	
	public void render(Graphics g)
	{
		int camX = (int)Main.sX / Tile.tileSize;
		int camY = (int)Main.sY / Tile.tileSize;
		int extraTiles = 2;								//tiles to render that not visible
		
		for(int x = camX; x < camX + (Main.pixel.width / Tile.tileSize) + extraTiles; x++)
		{
			for(int y = camY; y < camY + (Main.pixel.height / Tile.tileSize) + extraTiles; y++)
			{
				if(x >= 0 && x < block.length && y >= 0 && y < block[0].length)
					block[x][y].render(g);
			}
		}
	}
}
