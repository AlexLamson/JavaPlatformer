package platformer;

import java.awt.*;

public class Block extends Rectangle
{
	public int[] id = Tile.air;
	
	public Block(Rectangle size, int[] id)
	{
		setBounds(size);
		this.id = id;
	}
	
	public void render(Graphics g)
	{
		if(id != Tile.air)
		{
			g.drawImage(Tile.tileset_terrian, x - (int)Main.sX, y - (int)Main.sY, x + width - (int)Main.sX, y + height - (int)Main.sY, 
					id[0]*Tile.tileSize, id[1]*Tile.tileSize, id[0]*Tile.tileSize + Tile.tileSize, id[1]*Tile.tileSize + Tile.tileSize, null);
		}
	}
	
}
