package platformer;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;


public class Listening implements KeyListener
{

	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();
		
		switch(key)
		{
		case KeyEvent.VK_D:
			Main.isMoving = true;
			Main.dir = Main.character.movingSpeed;
			break;
		case KeyEvent.VK_A:
			Main.isMoving = true;
			Main.dir = -Main.character.movingSpeed;
			break;
		}
	}

	public void keyReleased(KeyEvent e) {
		int key = e.getKeyCode();
		
		switch(key)
		{
		case KeyEvent.VK_D:
			if(Main.dir == Main.character.movingSpeed)
			{
				Main.isMoving = false;
			}
			break;
		case KeyEvent.VK_A:
			if(Main.dir == -Main.character.movingSpeed)
			{
				Main.isMoving = false;
			}
			break;
		}
	}

	public void keyTyped(KeyEvent e) {
		
	}

}
