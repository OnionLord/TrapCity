package TrapCity;

import java.awt.Image;
import java.awt.Toolkit;

public class Traps 
{
	public int trap_x;
	public int trap_y;
	public boolean trap_on;
	public int trap_type;
	public int trap_state;
	public int trap_num;
	public int trap_w;
	public int trap_h;
	public int trap_limit;
	public boolean activated;
	
	public Hole type_hole = new Hole();
	public Thorn type_thorn = new Thorn();
	public MakeRandom rand = new MakeRandom();
	
	Traps(int n)
	{
		
		trap_limit = n;
		trap_type = rand.type_rand(trap_limit);
		
		if ( trap_type == 1)
		{
			trap_w = type_hole.trap_w;
			trap_h = type_hole.trap_h;
			trap_y = type_hole.trap_y;
		}
		else if ( trap_type == 2)
		{
			trap_w = type_thorn.trap_w;
			trap_h = type_thorn.trap_h;
			trap_y = type_thorn.trap_y;
		}
		trap_on = false;
		trap_state = 0;
		activated = false;

		//trap_y = 610;
	}
	public void appear()
	{
		trap_on = true;
	}
	public void disappear()
	{
		trap_on = false;
	}
	public void set_trap_num(int n)
	{
		trap_num = n;
		trap_x = rand.locate_rand(trap_w, (trap_num)*200);
	}
	

	
	public Image draw_trap()
	{
		return Toolkit.getDefaultToolkit().getImage("gamedata\\pictures\\traps\\trap_" + trap_type + "_" + trap_state + ".gif");
	}
}
