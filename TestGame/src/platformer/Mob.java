package platformer;

import java.awt.Graphics;
import java.awt.Point;
import java.util.Random;

public class Mob extends DoubleRectangle
{
	public int[] id;
	
	public boolean isJumping = false;
	public boolean isMoving = false;
	public boolean isFalling = false;
	
	//default attributes
	public double movementSpeed = 0.4;
	public double fallingSpeed = 1;
	public double jumpingSpeed = 1;
	public double dir = movementSpeed;
	
	public int age = 0, lifeSpan = 2000;
	public int jumpingHeight = 60, jumpingCount = 0;
	public int animation = 0, totalFrames = 3;							//totalFrames is the number of running frames
	public int animationFrame = 0, animationTime = 30;
	
	public Mob(int x, int y, int width, int height, int[] id)
	{
		setBounds(x, y, width, height);
		this.id = id;
	}
	
	public void tick()
	{
		age++;
		
		if(!isJumping && !isCollidingWithBlock(new Point((int)x + 2, (int)(y + height)), new Point((int)(x + width - 2), (int)(y + height)) ))
		{
			y += fallingSpeed;
			isFalling = true;
		}
		else
		{
			isFalling = false;
			isJumping = false;
			if(new Random().nextInt(100) < 1)
			{
				isMoving = true;
				
				if(new Random().nextInt(100) < 50)
					dir = -movementSpeed;
				else
					dir = movementSpeed;
			}
		}
		
		if(isMoving)
		{
			boolean canMove = false;
			
			if(dir > 0)
			{
				canMove = isCollidingWithBlock(new Point((int)(x + width), (int)y), new Point((int)(x + width), (int)(y + height - 2)) );
			}
			else if(dir < 0)
			{
				canMove = isCollidingWithBlock(new Point((int)x, (int)y),  new Point((int)x, (int)(y + height - 2)) );	// -2 is to not get stuck in the ground
			}
			
			if(!canMove && !isFalling)
			{
				isJumping = true;
			}
			
			if(animationFrame >= animationTime)
			{
				if(animation > totalFrames-1)
				{
					animation = 1;
				}
				else
				{
					animation++;
				}
				
				animationFrame = 0;
			}
			else
			{
				animationFrame++;
			}
			
			if(!canMove)
			{
				x += dir;
			}
		}
		else	//is not moving
		{
			animation = 0;
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
				if(x >= 0 && y >= 0 & x < Level.block.length && y < Level.block[0].length)
				{
					if(Level.block[x][y].id != Tile.air)
					{
						if(Level.block[x][y].contains(pt1) || Level.block[x][y].contains(pt2))
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
		if(dir >= 0)
		{
			g.drawImage(Tile.tileset_terrian, (int)(x - Main.sX), (int)(y - Main.sY), (int)(x + width - Main.sX), (int)(y + height - Main.sY),
					(id[0] + animation)*Tile.tileSize, id[1]*Tile.tileSize,
					(id[0] + animation)*Tile.tileSize + (int)width, id[1]*Tile.tileSize + (int)height, null);
		}
		else
		{
			g.drawImage(Tile.tileset_terrian, (int)(x - Main.sX), (int)(y - Main.sY), (int)(x + width - Main.sX), (int)(y + height - Main.sY),
					(id[0] + animation)*Tile.tileSize + (int)width, id[1]*Tile.tileSize,
					(id[0] + animation)*Tile.tileSize, id[1]*Tile.tileSize + (int)height, null);
		}
	}
}
