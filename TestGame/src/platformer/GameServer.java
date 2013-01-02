package platformer;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

public class GameServer extends Thread
{
	private DatagramSocket socket;
	private Main main;
	
	public GameServer(Main main)
	{
		this.main = main;
		try {
			this.socket = new DatagramSocket(1331);		//put the listening port here
		} catch (SocketException e) {
			e.printStackTrace();
		}
		
	}
	
	public void run()
	{
		while(true)
		{
			byte[] data = new byte[1024];
			DatagramPacket packet = new DatagramPacket(data, data.length);
			
			try {
				socket.receive(packet);
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			String message = new String(packet.getData());
			System.out.println("CLIENT > ["+packet.getAddress().getHostAddress()+":"+packet.getPort()+"] " + message );
			if(message.trim().equalsIgnoreCase("ping"))
			{
				sendData("pong".getBytes(), packet.getAddress(), packet.getPort());
			}
		}
	}
	
	public void sendData(byte[] data, InetAddress ipAddress, int port)
	{
		DatagramPacket packet = new DatagramPacket(data, data.length, ipAddress, port);
		try {
			this.socket.send(packet);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}