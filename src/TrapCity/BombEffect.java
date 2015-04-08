package TrapCity;

import java.awt.Image;
import java.awt.Toolkit;

public class BombEffect 
{
	public int bomb_h = 50;
	public int bomb_w = 50;
	public int bomb_x;
	public int bomb_y;
	public int bombcnt = 1;
	public int bomb_state;
	public boolean bomb_on = false;
	
	public Image draw_bomb()
	{
		return Toolkit.getDefaultToolkit().getImage("gamedata\\pictures\\bomb\\effect_" + bomb_state + ".gif");
	}
}
