package platformer;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.net.InetAddress;

public class Character extends DoubleRectangle
{
	public String username = "";
	
	public double fallingSpeed = 1*Main.computerSpeed;
	public double movingSpeed = 0.5*Main.computerSpeed;
	public double jumpingSpeed = 1*Main.computerSpeed;
	public double sprintingSpeed = 2;
	
	public boolean isJumping = false;
	
	public int jumpingHeight = (int)(60/Main.computerSpeed), jumpingCount = 0;
	public int walkAnimation = (int)(30/Main.computerSpeed), sprintAnimation = (int)(20/Main.computerSpeed);
	public int animation = 0, totalFrames = 3;							//totalFrames is the number of running frames
	public int animationFrame = 0, animationTime = walkAnimation;

	public static double sX = 0, sY = 0;
	public static double dir = 0;
	public static boolean facingLeft = false;
	
	public int xOffset = 2*Main.pixelSize;
	public int yOffset = 2*Main.pixelSize;
	
	public int xSave1 = 0, xSave2 = 0, ySave1 = 0, ySave2 = 0;
	
	private Listening listener;
	
	public Character(int width, int height, Listening listener, String username)
	{
		this.username = username;
		setBounds((Main.pixel.width/2) - (width/2), (Main.pixel.height/2) - (height/2), width, height);
	}
	
	public void tick()
	{
		xOffset = 0;
		yOffset = 2;
		if(!isJumping && !isCollidingWithBlock(new Point((int)x, (int)(y + height)), new Point((int)(x + width - xOffset), (int)(y + height))) )
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
			
			xOffset = (int)movingSpeed;
			if(Main.isSprinting)
				xOffset = (int)sprintingSpeed;
			
			if(Main.dir > 0)
			{
				canMove = isCollidingWithBlock(new Point((int)(x + width), (int)(y)), new Point((int)(x + width - xOffset), (int)(y + height - yOffset)) );
			}
			else if(Main.dir < 0)
			{
				canMove = isCollidingWithBlock(new Point((int)(x - xOffset), (int)(y)),  new Point((int)(x - xOffset), (int)(y + height - yOffset)) );
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
			if(!isCollidingWithBlock(new Point((int)(x), (int)(y - yOffset)), new Point((int)(x + width - xOffset), (int)(y))) )
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
		//save variables for displaying collision info
		xSave1 = (int)pt1.getX();
		ySave1 = (int)pt1.getY();
		xSave2 = (int)pt2.getX();
		ySave2 = (int)pt2.getY();
		
		
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
		
		if(Main.degbugMode)
		{
			//draw collision info
			g.setColor(new Color(255,0,0));
			g.drawRect((int)(xSave1 - Main.sX), (int)(ySave1 - Main.sY), (int)(xSave2 - xSave1 + xOffset), (int)(ySave2 - ySave1 + yOffset));
			//g.drawLine((int)(xSave1 - Main.sX), (int)(ySave1 - Main.sY), (int)(x + xSave2 - xSave1 + xOffset - Main.sX), (int)(y + ySave2 - ySave1 + yOffset - Main.sY));
			g.setColor(new Color(255,255,0));
			g.drawRect((int)(x - Main.sX), (int)(y - Main.sY), (int)width, (int)height);
		}
	}
}
