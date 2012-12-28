package platformer;

import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.*;

public class Inventory
{
	public static Cell[] invBar = new Cell[Tile.invCellLength];
	public static Cell[] invBag = new Cell[Tile.invCellLength * Tile.invHeight];
	
	public static boolean isOpen = false;
	public static boolean isHolding = false;
	
	public static int selected = 0;
	public static int holdingCount = 0;
	public static int[] holdingID = Tile.air;
	
	public Inventory()
	{
		int centerAlign = (Main.pixel.width/2) - (Tile.invCellSize + Tile.invCellSpace) * Tile.invCellLength / 2;
		
		for(int i = 0; i < invBar.length; i++)
		{
			invBar[i] = new Cell(new Rectangle(centerAlign + (i * (Tile.invCellSize + Tile.invCellSpace)), Main.pixel.height - (Tile.invCellSize + Tile.invBorderSpace), Tile.invCellSize, Tile.invCellSize), Tile.air);
		}
		
		int x = 0, y = 0;
		for(int i = 0; i < invBag.length; i++)
		{
			invBag[i] = new Cell(new Rectangle(centerAlign + (x * (Tile.invCellSize + Tile.invCellSpace)), Main.pixel.height - (Tile.invCellSize + Tile.invBorderSpace) - (Tile.invHeight * (Tile.invCellSize + Tile.invCellSpace)) + (y * (Tile.invCellSize + Tile.invCellSpace)), Tile.invCellSize, Tile.invCellSize), Tile.air);
			
			x++;
			
			if(x == Tile.invCellLength)
			{
				x = 0;
				y++;
			}
		}
		
		invBar[0].id = Tile.grass;
		invBar[1].id = Tile.earth;
		invBar[2].id = Tile.sand;
		invBar[3].id = Tile.wood;
		invBar[4].id = Tile.leaves;
	}
	
	public static void click(MouseEvent e)
	{
		if(e.getButton() == 1)
		{
			if(isOpen)
			{
				for(int i = 0; i < invBar.length; i++)
				{
					if(invBar[i].contains(new Point(Main.mse.x / Main.pixelSize, Main.mse.y / Main.pixelSize)))
					{
						if(invBar[i].id != Tile.air && !isHolding)
						{
							holdingID = invBar[i].id;
							invBar[i].id = Tile.air;
							
							isHolding = true;
						}
						else if(isHolding && invBar[i].id == Tile.air)
						{
							invBar[i].id = holdingID;
							isHolding = false;
						}
						else if(isHolding && invBar[i].id != Tile.air)
						{
							int[] save = invBar[i].id;
							
							invBar[i].id = holdingID;
							holdingID = save;
						}
					}
				}
				
				for(int i = 0; i < invBag.length; i++)
				{
					if(invBag[i].contains(new Point(Main.mse.x / Main.pixelSize, Main.mse.y / Main.pixelSize)))
					{
						if(invBag[i].id != Tile.air && !isHolding)
						{
							holdingID = invBag[i].id;
							invBag[i].id = Tile.air;
							
							isHolding = true;
						}
						else if(isHolding && invBag[i].id == Tile.air)
						{
							invBag[i].id = holdingID;
							isHolding = false;
						}
						else if(isHolding && invBag[i].id != Tile.air)
						{
							int[] save = invBag[i].id;
							
							invBag[i].id = holdingID;
							holdingID = save;
						}
					}
				}
			}
		}
	}
	
	public void render(Graphics g)
	{
		for(int i = 0; i < invBar.length; i++)
		{
			if(i == selected)
				invBar[i].render(g, true);
			else
				invBar[i].render(g, false);
		}
		
		if(isOpen)
		{
			for(int i = 0; i < invBag.length; i++)
			{
				invBag[i].render(g, false);
			}
		}
		
		if(isHolding)
		{
				g.drawImage(Tile.tileset_terrian, Main.mse.x / Main.pixelSize - Tile.invCellSize/2 + Tile.invItemBorder, Main.mse.y / Main.pixelSize - Tile.invCellSize/2 + Tile.invItemBorder, Main.mse.x / Main.pixelSize - Tile.invCellSize/2 + Tile.invCellSize - Tile.invItemBorder, Main.mse.y / Main.pixelSize - Tile.invCellSize/2 + Tile.invCellSize - Tile.invItemBorder, 
						holdingID[0]*Tile.tileSize, holdingID[1]*Tile.tileSize, holdingID[0]*Tile.tileSize + Tile.tileSize, holdingID[1]*Tile.tileSize + Tile.tileSize, null);
		}
	}
}
