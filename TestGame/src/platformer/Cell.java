package platformer;

import java.awt.Graphics;
import java.awt.Rectangle;

public class Cell extends Rectangle
{
	public int[] id = {0,0};
	
	public Cell(Rectangle size, int[] id)
	{
		setBounds(size);
		this.id = id;
	}
	
	public void render(Graphics g, boolean isSelected)
	{
		g.drawImage(Tile.tile_cell, x - 1, y - 1, width + 2, height + 2, null);
		
		if(id != Tile.air)
		{
			g.drawImage(Tile.tileset_terrian, x + Tile.invItemBorder, y + Tile.invItemBorder, x + width - Tile.invItemBorder, y + height - Tile.invItemBorder, 
					id[0]*Tile.tileSize, id[1]*Tile.tileSize, id[0]*Tile.tileSize + Tile.tileSize, id[1]*Tile.tileSize + Tile.tileSize, null);
		}
		
		if(isSelected)
			g.drawImage(Tile.tile_select, x, y, width, height, null);
		
		//g.setColor(new Color(0, 200, 255));
		//g.fillRect(x,y,width,height);
		
		
	}
}
