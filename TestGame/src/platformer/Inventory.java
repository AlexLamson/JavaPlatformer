package platformer;

import java.awt.Graphics;
import java.awt.Rectangle;

public class Inventory
{
	public Cell[] invBar = new Cell[Tile.invCellLength];
	
	public Inventory()
	{
		int centerAlign = (Main.pixel.width/2) - (Tile.invCellSize + Tile.invCellSpace) * Tile.invCellLength / 2;
		
		for(int i = 0; i < invBar.length; i++)
		{
			invBar[i] = new Cell(new Rectangle(centerAlign + (i * (Tile.invCellSize + Tile.invCellSpace)), Main.pixel.height - (Tile.invCellSize + Tile.invBorderSpace), Tile.invCellSize, Tile.invCellSize), Tile.air);
		}
	}
	
	public void render(Graphics g)
	{
		for(int i = 0; i < invBar.length; i++)
		{
			invBar[i].render(g);
		}
	}
}
