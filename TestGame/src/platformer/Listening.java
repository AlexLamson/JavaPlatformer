package platformer;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;


public class Listening implements KeyListener, MouseListener, MouseMotionListener, MouseWheelListener
{

	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();
		
		double sprintSave = 1;
		if(Main.isSprinting)
			sprintSave = Main.character.sprintingSpeed;
		
		switch(key)
		{
		case KeyEvent.VK_D:
			Main.isMoving = true;
			Main.dir = Main.character.movingSpeed * sprintSave;
			break;
		case KeyEvent.VK_A:
			Main.isMoving = true;
			Main.dir = -Main.character.movingSpeed * sprintSave;
			break;
		case KeyEvent.VK_W:
			Main.isJumping = true;
			break;
		case KeyEvent.VK_SHIFT:
			Main.isSprinting = true;
			if(Math.abs(Main.dir) < Main.character.sprintingSpeed * Main.character.movingSpeed)
				Main.dir *= Main.character.sprintingSpeed;
			break;
			
		case KeyEvent.VK_F:
			Inventory.isOpen = !Inventory.isOpen; 
			break;
		
		//basic digging controls
		case KeyEvent.VK_DOWN:
			Level.place((int)((Main.character.x+Tile.tileSize/2)/Tile.tileSize), (int)(Main.character.y/Tile.tileSize)+2, Tile.air);
			break;
		case KeyEvent.VK_UP:
			Level.place((int)((Main.character.x+Tile.tileSize/2)/Tile.tileSize), (int)(Main.character.y/Tile.tileSize)-1, Tile.air);
			break;
		case KeyEvent.VK_LEFT:
			Level.place((int)((Main.character.x-Tile.tileSize/2)/Tile.tileSize), (int)(Main.character.y/Tile.tileSize), Tile.air);
			Level.place((int)((Main.character.x-Tile.tileSize/2)/Tile.tileSize), (int)((Main.character.y+Tile.tileSize)/Tile.tileSize), Tile.air);
			break;
		case KeyEvent.VK_RIGHT:
			Level.place((int)((Main.character.x+Tile.tileSize*1.5)/Tile.tileSize), (int)(Main.character.y/Tile.tileSize), Tile.air);
			Level.place((int)((Main.character.x+Tile.tileSize*1.5)/Tile.tileSize), (int)((Main.character.y+Tile.tileSize)/Tile.tileSize), Tile.air);
			break;
		}
	}

	public void keyReleased(KeyEvent e) {
		int key = e.getKeyCode();
		
		switch(key)
		{
		case KeyEvent.VK_D:
			if(Main.dir > 0)
			{
				Main.isMoving = false;
				Main.dir = 0;
			}
			break;
		case KeyEvent.VK_A:
			if(Main.dir < 0)
			{
				Main.isMoving = false;
				Main.dir = 0;
			}
			break;
		case KeyEvent.VK_W:
			Main.isJumping = false;
			break;
		case KeyEvent.VK_SHIFT:
			Main.isSprinting = false;
			Main.dir /= Main.character.sprintingSpeed;
			break;
		}
	}

	public void keyTyped(KeyEvent e) {
		
	}

	public void mouseClicked(MouseEvent e) {
		
	}

	public void mouseEntered(MouseEvent e) {
		
	}

	public void mouseExited(MouseEvent e) {
		
	}
	
	public void mousePressed(MouseEvent e) {
		if(e.getButton() == MouseEvent.BUTTON1)			//left click
		{
			Main.isMouseLeft = true;
		}
		else if(e.getButton() == MouseEvent.BUTTON3)	//right click
		{
			Main.isMouseRight = true;
		}
		
		Inventory.click(e);
	}

	public void mouseReleased(MouseEvent e) {
		if(e.getButton() == MouseEvent.BUTTON1)			//left click
		{
			Main.isMouseLeft = false;
		}
		else if(e.getButton() == MouseEvent.BUTTON3)	//right click
		{
			Main.isMouseRight = false;
		}
	}

	public void mouseDragged(MouseEvent e) {
		Main.mse.setLocation(e.getX(), e.getY());
	}

	public void mouseMoved(MouseEvent e) {
		Main.mse.setLocation(e.getX(), e.getY());
	}
	
	public void mouseWheelMoved(MouseWheelEvent e) {
		if(e.getWheelRotation() < 0)		//scrolled up
		{
			if(Inventory.selected < Tile.invCellLength-1)
			{
				Inventory.selected++;
			}
			else
			{
				Inventory.selected = 0;
			}
		}
		else if(e.getWheelRotation() > 0)		//scrolled down
		{
			if(Inventory.selected > 0)
			{
				Inventory.selected--;
			}
			else
			{
				Inventory.selected = Tile.invCellLength-1;
			}
		}
	}

}
