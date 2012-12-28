package platformer;

import java.awt.Graphics;
import java.awt.Rectangle;

public class Inventory
{
	public Cell[] invBar = new Cell[Tile.invCellLength];
	
	public static int selected = 0;
	
	public Inventory()
	{
		int centerAlign = (Main.pixel.width/2) - (Tile.invCellSize + Tile.invCellSpace) * Tile.invCellLength / 2;
		
		for(int i = 0; i < invBar.length; i++)
		{
			invBar[i] = new Cell(new Rectangle(centerAlign + (i * (Tile.invCellSize + Tile.invCellSpace)), Main.pixel.height - (Tile.invCellSize + Tile.invBorderSpace), Tile.invCellSize, Tile.invCellSize), Tile.air);
		}
		
		invBar[0].id = Tile.grass;
		invBar[1].id = Tile.earth;
		invBar[2].id = Tile.sand;
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
	}
}
