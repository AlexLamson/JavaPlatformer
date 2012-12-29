package platformer;

import java.awt.Graphics;
import java.awt.Point;

public class Character extends DoubleRectangle
{
	public double fallingSpeed = 1;
	public double movingSpeed = 0.5;
	public double jumpingSpeed = 1;
	public double sprintingSpeed = 2;
	
	public int jumpingHeight = 60, jumpingCount = 0;
	public int walkAnimation = 30, sprintAnimation = 20;
	public int animation = 0, totalFrames = 3;							//totalFrames is the number of running frames
	public int animationFrame = 0, animationTime = walkAnimation;
	
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
			if(Main.isJumping && !Inventory.isOpen)
			{
				isJumping = true;
			}
		}
		
		if(Main.isMoving && !Inventory.isOpen)
		{
			boolean canMove = false;
			
			if(Main.dir > 0)
			{
				canMove = isCollidingWithBlock(new Point((int)(x + width), (int)y), new Point((int)(x + width), (int)(y + height - 2)) );
				
			}
			else if(Main.dir < 0)
			{
				canMove = isCollidingWithBlock(new Point((int)x, (int)y),  new Point((int)x, (int)(y + height - 2)) );	// -2 is to not get stuck in the ground
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
				x += Main.dir;
				Main.sX += Main.dir;
			}
		}
		else	//is not moving
		{
			animation = 0;
		}
		
		if(Main.isSprinting)
		{
			animationTime = sprintAnimation;
		}
		else
		{
			animationTime = walkAnimation;
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
		
		if(Main.facingLeft && Main.dir > 0)
		{
			Main.facingLeft = false;
		}
		else if(!Main.facingLeft && Main.dir < 0)
		{
			Main.facingLeft = true;
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
		if(Main.facingLeft)
		{
			g.drawImage(Tile.tileset_terrian, (int)(x - Main.sX), (int)(y - Main.sY), (int)(x + width - Main.sX), (int)(y + height - Main.sY),
					(Tile.character[0] + animation)*Tile.tileSize + (int)width, Tile.character[1]*Tile.tileSize,
					(Tile.character[0] + animation)*Tile.tileSize, Tile.character[1]*Tile.tileSize + (int)height, null);
		}
		else if(!Main.facingLeft)
		{
			g.drawImage(Tile.tileset_terrian, (int)(x - Main.sX), (int)(y - Main.sY), (int)(x + width - Main.sX), (int)(y + height - Main.sY),
					(Tile.character[0] + animation)*Tile.tileSize, Tile.character[1]*Tile.tileSize,
					(Tile.character[0] + animation)*Tile.tileSize + (int)width, Tile.character[1]*Tile.tileSize + (int)height, null);
		}
		
//		g.setColor(new Color(255,0,0));
//		g.drawRect((int)(x - Main.sX), (int)(y - Main.sY), (int)width, (int)height);
	}
}
