package platformer;

import java.awt.*;

public class Character extends DoubleRectangle
{
	
	public double fallingSpeed = 1;
	public double movingSpeed = 0.5;
	public double jumpingSpeed = 1;
	
	public int jumpingHeight = 60, jumpingCount = 0;
	
	public boolean isJumping = false;
	
	public Character(int width, int height)
	{
		setBounds((Main.pixel.width/2) - (width/2), (Main.pixel.height/2) - (height/2), width, height);
	}
	
	public void tick()
	{
		if(!isJumping && !isCollidingWithBlock(new Point((int)x + 2, (int)(y + height)), new Point((int)(x + width - 2), (int)(y + height)) ))
		{
			y += fallingSpeed;
			Main.sY += fallingSpeed;
		}
		else
		{
			if(Main.isJumping)
			{
				isJumping = true;
			}
		}
		
		if(Main.isMoving == true)
		{
			boolean canMove = false;
			
			if(Main.dir == movingSpeed)
			{
				canMove = isCollidingWithBlock(new Point((int)(x + width), (int)y), new Point((int)(x + width), (int)(y + height - 2)) );
			}
			else if(Main.dir == -movingSpeed)
			{
				canMove = isCollidingWithBlock(new Point((int)x, (int)y),  new Point((int)x, (int)(y + height - 2)) );	// -2 is to not get stuck in the ground
			}
			
			if(!canMove)
			{
				x += Main.dir;
				Main.sX += Main.dir;
			}
		}
		
		if(isJumping)
		{
			if(!isCollidingWithBlock(new Point((int)(x + 2), (int)y), new Point((int)(x + width - 2), (int)y) ))
			{
				if(jumpingCount >= jumpingHeight)
				{
					isJumping = false;
					jumpingCount = 0;
				}
				else
				{
					y -= jumpingSpeed;
					Main.sY -= jumpingSpeed;
					
					jumpingCount++;
				}
			}
			else
			{
				isJumping = false;
				jumpingCount = 0;
			}
		}
	}
	
	public boolean isCollidingWithBlock(Point pt1, Point pt2)
	{
		boolean isColliding = false;
		
		for(int x = (int)(this.x/Tile.tileSize); x < (int)(this.x/Tile.tileSize) + 3; x++)
		{
			for(int y = (int)(this.y/Tile.tileSize); y < (int)(this.y/Tile.tileSize) + 3; y++)
			{
				if(x >= 0 && y >= 0 & x < Main.level.block.length && y < Main.level.block[0].length)
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
