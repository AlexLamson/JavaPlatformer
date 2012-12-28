package platformer;

import java.awt.Color;
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
	
	public void render(Graphics g)
	{
		//g.setColor(new Color(0, 200, 255));
		//g.fillRect(x,y,width,height);
		
		g.drawImage(Tile.tile_cell, x, y, width, height, null);
	}
}
