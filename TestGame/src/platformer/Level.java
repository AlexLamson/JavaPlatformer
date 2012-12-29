package platformer;

import java.awt.*;
import java.util.*;		//will use later for the randomness in level generation

public class Level
{
	public static int worldW = 50, worldH = 50;
	public static Block[][] block = new Block[worldW][worldH];
	
	public boolean makeTrees = true;
	
	public int treeTrunkHeight = 4;
	public int leavesHeight = 3;
	public int leavesWidth = 3;

	public Level()
	{
		for(int x = 0; x < block.length; x++)
		{
			for(int y = 0; y < block[0].length; y++)
			{
				//fill up the array with air blocks
				block[x][y] = new Block(new Rectangle(x*Tile.tileSize, y*Tile.tileSize, Tile.tileSize, Tile.tileSize), Tile.air);
			}
		}
		
		generateLevel();
	}
	
	public void generateLevel()
	{
		for(int y = 0; y < block[0].length; y++)
		{
			for(int x = 0; x < block.length; x++)		//read the blocks row by row
			{
				
				if(y > worldH/4 + 2)
				{
					//place right side of hill
					if(new Random().nextInt(100) > 20)
					{
						if(check(x-1, y-1, Tile.earth))
						{
							place(x, y, Tile.earth);
						}
					}
					
					//place left side of hill
					if(new Random().nextInt(100) > 30)
					{
						if(check(x+1, y-1, Tile.earth))
						{
							place(x, y, Tile.earth);
						}
					}
					
					//place peak of hill
					if(check(x, y-1, Tile.earth))
					{
						place(x, y, Tile.earth);
					}
					
					//create base for hills
					if(new Random().nextInt(100) < 10)
					{
						block[x][y].id = Tile.earth;
					}
				}
//				if(y > worldH/2)
//				{
//					if(check(x, y, Tile.air))
//					{
//						block[x][y].id = Tile.earth;
//					}
//					
//					int test = new Random().nextInt(100);
//					if(test <= 5)
//					{
//						place(x,y,Tile.leaves);
//						
//						place(x-1, y, Tile.sand);
//						place(x+1, y, Tile.sand);
//						place(x-1, y-1, Tile.sand);
//						place(x-1, y+1, Tile.sand);
//
//						place(x-1, y-1, Tile.sand);
//						place(x-1, y+1, Tile.sand);
//						place(x+1, y-1, Tile.sand);
//						place(x+1, y+1, Tile.sand);
//					}
//				}
				
				//place the solid air on the sides
				if(x == 0 || x == block.length-1)
				{
					block[x][y].id = Tile.solidAir;
				}
				
				//place the bedrock on the bottom
				if(x > 0 && x < block.length-1 && y == block.length-1)
				{
					block[x][y].id = Tile.bedrock;
				}
			}
		}
		
		for(int y = 0; y < block[0].length; y++)
		{
			for(int x = 0; x < block.length; x++)
			{
				//turn the top layer of dirt into grass
				if(check(x, y, Tile.earth) && check(x, y-1, Tile.air))
					place(x, y, Tile.grass);
				
				//place trees
				if(makeTrees)
				{
					if(check(x, y, Tile.grass))
					{
						if(new Random().nextInt(100) < 10)
						{
							place(x, y, Tile.earth);
							for(int i = 0; i < treeTrunkHeight; i++)
							{
								place(x, y - i - 1, Tile.wood);
							}
							
							for(int y2 = 0; y2 < leavesHeight; y2++)
							{
								for(int x2 = 0; x2 < leavesWidth; x2++)
								{
									place(x + x2 - leavesWidth/2, y - y2 - treeTrunkHeight - 1, Tile.leaves);
								}
							}
						}
					}
				}
			}
		}
	}
	
	public static boolean check(int x, int y, int[] id)
	{
		if(x >= 0 && x < block.length && y >= 0 && y < block[0].length)
		{
			if(block[x][y].id == id)
				return true;
		}
		return false;
	}
	
	public static void place(int x, int y, int[] id)
	{
		if(x >= 0 && x < block.length && y >= 0 && y < block[0].length)
		{
			if(block[x][y].id != Tile.bedrock && block[x][y].id != Tile.solidAir)
			{
				block[x][y].id = id;
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
							int[] sid = Inventory.invBar[Inventory.selected].id;
							
							if(Main.isMouseLeft)
							{
								if(block[x][y].id != Tile.solidAir && block[x][y].id != Tile.bedrock)
									block[x][y].id = Tile.air;
							}
							else if(Main.isMouseRight)
							{
								if(sid != Tile.air)
								{
									place(x, y, sid);
									
//									if(check(x, y, Tile.earth) && check(x, y-1, Tile.air))
//										place(x, y, Tile.grass);
//
//									if(check(x, y, Tile.grass) && !check(x, y-1, Tile.air))
//										place(x, y, Tile.earth);
//
//									if(check(x, y+1, Tile.grass))
//										place(x, y+1, Tile.earth);
								}
							}
							
							
							
						}
				}
			}
		}
	}
	
	public void tick()
	{
		if(!Inventory.isOpen)
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
				{
					block[x][y].render(g);
					
					if(block[x][y].id != Tile.air && block[x][y].id != Tile.solidAir && !Inventory.isOpen)
					{
						if(block[x][y].contains(new Point((int)Main.sX + Main.mse.x/Main.pixelSize, (int)Main.sY + Main.mse.y/Main.pixelSize)))
						{
							g.setColor(new Color(255, 255, 255, 100));
							g.fillRect(block[x][y].x - (int)Main.sX, block[x][y].y - (int)Main.sY, block[x][y].width, block[x][y].height);
						}
					}
				}
			}
		}
	}
}
