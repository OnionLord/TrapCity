package TrapCity;

import java.awt.Image;
import java.awt.Toolkit;

public class fallingstone {

	public int rs_w = 100;
	public int rs_h = 100;
	public int rs_x;
	public int rs_y = -100;
	public boolean rs_on = false;
	public boolean make_death = false;
	
	fallingstone( int x )
	{
		rs_x = x;
	}
	
	public Image draw_trap()
	{
		return Toolkit.getDefaultToolkit().getImage("gamedata\\pictures\\traps\\rstone.gif");
	}
}
