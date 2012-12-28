package platformer;

import java.awt.*;
import java.util.*;		//will use later for the randomness in level generation

public class Level
{
	public static int worldW = 50, worldH = 20;
	public static Block[][] block = new Block[worldW][worldH];

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
		for(int y = 0; y < block[0].length; y++)
		{
			for(int x = 0; x < block.length; x++)
			{
				
				if(y > worldH/4 + 2)
				{
					if(new Random().nextInt(100) > 20)
					{
						try
						{
							if(block[x-1][y-1].id == Tile.earth)
							{
								block[x][y].id = Tile.earth;
							}
						}catch(Exception e){}
					}
					
					if(new Random().nextInt(100) > 30)
					{
						try
						{
							if(block[x+1][y-1].id == Tile.earth)
							{
								block[x][y].id = Tile.earth;
							}
						}catch(Exception e){}
					}
					
						try
						{
							if(block[x][y-1].id == Tile.earth)
							{
								block[x][y].id = Tile.earth;
							}
						}catch(Exception e){}
					
					
					if(new Random().nextInt(100) < 10)
					{
						block[x][y].id = Tile.earth;
					}
				}
				
				//place the outside edge
				if(x == 0 || y == 0 || x == block.length-1 || y == block[0].length-1)
				{
					block[x][y].id = Tile.sand;
				}
			}
		}
		
		for(int y = 0; y < block[0].length; y++)
		{
			for(int x = 0; x < block.length; x++)
			{
				if(block[x][y].id == Tile.earth && block[x][y-1].id == Tile.air)
					block[x][y].id = Tile.grass;
			}
		}
	}
	
	public void building()
	{
		int camX = (int)Main.sX / Tile.tileSize;
		int camY = (int)Main.sY / Tile.tileSize;
		int extraTiles = 2;								//tiles to render that not visible
		
		if(Main.isMouseLeft || Main.isMouseRight)
		{
			for(int x = camX; x < camX + (Main.pixel.width / Tile.tileSize) + extraTiles; x++)
			{
				for(int y = camY; y < camY + (Main.pixel.height / Tile.tileSize) + extraTiles; y++)
				{
					if(x >= 0 && x < block.length && y >= 0 && y < block[0].length)
						if(block[x][y].contains(new Point((int)Main.sX + Main.mse.x/Main.pixelSize, (int)Main.sY + Main.mse.y/Main.pixelSize)))
						{
							int[] sid = Main.inventory.invBar[Inventory.selected].id;
							
							if(Main.isMouseLeft)
							{
									block[x][y].id = Tile.air;
							}
							else if(Main.isMouseRight)
							{
								if(sid != Tile.air)
								{
									block[x][y].id = sid;
									
//									if(block[x][y].id == Tile.earth && block[x][y-1].id == Tile.air)
//										block[x][y].id = Tile.grass;
//									
//									if(block[x][y].id == Tile.grass && block[x][y-1].id != Tile.air)
//										block[x][y].id = Tile.earth;
//									
//									if(block[x][y+1].id == Tile.grass)
//										block[x][y+1].id = Tile.earth;
								}
							}
							
							
							
						}
				}
			}
		}
	}
	
	public void tick()
	{
		building();
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
