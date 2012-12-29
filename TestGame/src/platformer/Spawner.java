package platformer;

import java.util.*;

public class Spawner implements Runnable
{
	public boolean isRunning = false;
	
	public Spawner()
	{
		isRunning = true;
		new Thread(this).start();
	}
	
	public void spawnMob(Mob mob)
	{
		Main.mob.add(mob);
	}
	
	public void run()
	{
		while(isRunning)
		{
			spawnMob(new Iggy((new Random().nextInt(Level.worldW)+1) * Tile.tileSize, 50, Tile.tileSize, Tile.tileSize*2));
			
			try
			{
				Thread.sleep(new Random().nextInt(5000) + 5000);
			}catch(Exception e){ }
		}
	}
}
