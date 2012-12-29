package platformer;

import java.util.*;

public class Spawner implements Runnable
{
	public static int maxMobs = 5;
	
	public boolean isRunning = false;
	
	public Spawner()
	{
		isRunning = true;
		new Thread(this).start();
	}
	
	public static void spawnMob(Mob mob)
	{
		Main.mob.add(mob);
	}
	
	public void checkForOldMobs()		//if a mob is too old, kill it
	{
		for(int i = 0; i < Main.mob.size(); i++)
		{
			if(Main.mob.get(i).age >= Main.mob.get(i).lifeSpan)
			{
				Main.spawner.killMob(i);
			}
		}
	}
	
	public static void killMob(int i)
	{
		Main.mob.remove(i);
	}
	
	public void run()
	{
		while(isRunning)
		{
			checkForOldMobs();
			if(Main.mob.size() < maxMobs && new Random().nextInt(1000) <= 10)
			{
				spawnMob(new Iggy((new Random().nextInt(Level.worldW)+1) * Tile.tileSize, 50, Tile.tileSize, Tile.tileSize*2));
			}
			
			try
			{
				Thread.sleep(20);
			}catch(Exception e){ }
		}
	}
}
