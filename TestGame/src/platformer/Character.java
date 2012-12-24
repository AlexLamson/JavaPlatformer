package platformer;

import java.awt.*;

public class Character extends DoubleRectangle
{
	
	public double fallingSpeed = 1.2;
	
	public Character(int width, int height)
	{
		setBounds((Main.pixel.width/2) - (width/2), (Main.pixel.height/2) - (height/2), width, height);
	}
	
	public void tick()
	{
		if(!isCollidingWithBlock(new Point((int)x, (int)(y + height)), new Point((int)(x + width), (int)(y + height)) ))
		{
			y += fallingSpeed;
			Main.sY += fallingSpeed;
		}
	}
	
	public boolean isCollidingWithBlock(Point pt1, Point pt2)
	{
		boolean isColliding = false;
		
		for(int x = (int)(this.x/Tile.tileSize); x < (int)(this.x/Tile.tileSize) + 3; x++)
		{
			for(int y = (int)(this.y/Tile.tileSize); y < (int)(this.y/Tile.tileSize) + 3; y++)
			{
				if(Main.level.block[x][y].id != Tile.air)
				{
					if(Main.level.block[x][y].contains(pt1) || Main.level.block[x][y].contains(pt2))
					{
						isColliding = true;
						break;
					}
				}
			}
		}
		
		return isColliding;
	}
	
	public void render(Graphics g)
	{
		g.drawImage(Tile.tileset_terrian, (int)(x - Main.sX), (int)(y - Main.sY), (int)(x + width - Main.sX), (int)(y + height - Main.sY), Tile.character[0]*Tile.tileSize, Tile.character[1]*Tile.tileSize, Tile.character[0]*Tile.tileSize + (int)width, Tile.character[1]*Tile.tileSize + (int)height, null);
//		g.setColor(new Color(255,0,0));
//		g.drawRect((int)(x - Main.sX), (int)(y - Main.sY), (int)width, (int)height);
	}
}
