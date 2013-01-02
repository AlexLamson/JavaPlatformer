package platformer;

public abstract class Entity extends DoubleRectangle
{
	
	public int x, y;
	
	public abstract void tick();

	public abstract void render();
	
}
