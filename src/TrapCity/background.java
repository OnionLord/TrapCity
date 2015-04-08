package TrapCity;

import java.awt.Image;
import java.awt.Toolkit;

public class background {

	//배경은 캐릭터가 움직이려는 방향과 반대로 움직인다.
	//캐릭터가 오른쪽으로 이동한다면
	//배경은 왼쪽으로 이동하는 것으로 처리한다.
	
	private int bg_w; //배경 너비
	private int bg_h; //배경 높이
	private int bg_x = 0; //배경 x좌표
	private int bg_y = 20; //배경 y좌표
	public int stage_num; //스테이지 번호
	
	background( int w, int h, int n )
	{
		bg_w = w;
		bg_h = h;
		stage_num = n;
	}
	
	public int getbg_x()
	{
		return bg_x;
	}
	
	public int getbg_y()
	{
		return bg_y;
	}
	
	public int getbg_h()
	{
		return bg_h;
	}
	
	public int getbg_w()
	{
		return bg_w;
	}
	
	public void set_bgx( int n )
	{
		bg_x = n;
	}
	
	public Image draw_bg()
	{
		return Toolkit.getDefaultToolkit().getImage("gamedata\\pictures\\stage"+stage_num+"\\BackGround.jpg");
	}
}
