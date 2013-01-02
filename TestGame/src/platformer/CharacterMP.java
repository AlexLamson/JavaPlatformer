package platformer;

import java.net.InetAddress;

public class CharacterMP extends Character
{
	public InetAddress ipAddress;
	public int port;
	
	public CharacterMP(int width, int height, String username, InetAddress ipAddress, int port) {
		super(width, height, username);
		this.ipAddress = ipAddress;
		this.port = port;
	}
	
	public CharacterMP(int width, int height, String username, Listening listener, InetAddress ipAddress, int port) {
		super(width, height, username);
		this.ipAddress = ipAddress;
		this.port = port;
	}
	
	public void tick()
	{
		super.tick();
	}
}
