package TrapCity;

import java.awt.Image;
import java.awt.Toolkit;

public class rollingstone 
{
	public int rs_w = 100;
	public int rs_h = 100;
	public int rs_x;
	public int rs_y = 550-65;
	public boolean rs_on = false;
	public boolean make_death = false;
	public boolean killed = false;
	
	rollingstone( int x )
	{
		rs_x = x;
	}
	
	public Image draw_trap()
	{
		if ( killed == false)
			return Toolkit.getDefaultToolkit().getImage("gamedata\\pictures\\traps\\rstone.gif");
		else
			return Toolkit.getDefaultToolkit().getImage("gamedata\\pictures\\traps\\rstone1.gif");
	}
}
