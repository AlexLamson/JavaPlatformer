package platformer;

import java.awt.*;

public class Level
{
	public Block[][] block = new Block[50][50];

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
		for(int x = 0; x < block.length; x++)
		{
			for(int y = 0; y < block[0].length; y++)
			{
				block[x][y].render(g);
			}
		}
	}
}
