package TrapCity;

import java.awt.Image;
import java.awt.Toolkit;

public class Bomb 
{
	public int bomb_h = 16;
	public int bomb_w = 16;
	public int bomb_x;
	public int bomb_y = 595-65;
	public boolean bomb_on = false;
	public int bombcnt = 0;
	public int bombspeed = 20;
	
	public Image draw_bomb()
	{
		return Toolkit.getDefaultToolkit().getImage("gamedata\\pictures\\bomb\\bomb.gif");
	}
}
