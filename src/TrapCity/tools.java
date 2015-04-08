package TrapCity;

import java.awt.Image;
import java.awt.Toolkit;

public class tools {

	private int tool_w = 58;//도구 그림 크기
	private int tool_h = 64;
	
	private int tool_x;//도구 그림 배치 위치
	private int tool_y;
	private boolean equiped;//도구 장착여부
	
	private int tool_num;//도구 번호
	
	tools( int x, int y, int n, boolean eq )
	{
		tool_x = x;
		tool_y = y;
		tool_num = n;
		equiped = eq;
	}
	
	public int gettool_w() 
	{
		return tool_w;
	}
	public int gettool_h()
	{
		return tool_h;
	}
	public int gettool_x()
	{
		return tool_x;
	}
	public int gettool_y()
	{
		return tool_y;
	}
	public int gettool_num()
	{
		return tool_num;
	}
	
	public boolean getequip()
	{
		return equiped;
	}
	
	public void setequip(boolean n)
	{
		equiped = n;
	}
	
	public Image Draw_tool()
	{
		if (equiped)
		{
			return Toolkit.getDefaultToolkit().getImage("gamedata\\pictures\\tools\\wp" + tool_num + "_equip.jpg");
		}
		else
		{
			return Toolkit.getDefaultToolkit().getImage("gamedata\\pictures\\tools\\wp" + tool_num + "_normal.jpg");
		}
	}
}
