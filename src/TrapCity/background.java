package TrapCity;

import java.awt.Image;
import java.awt.Toolkit;

public class background {

	//����� ĳ���Ͱ� �����̷��� ����� �ݴ�� �����δ�.
	//ĳ���Ͱ� ���������� �̵��Ѵٸ�
	//����� �������� �̵��ϴ� ������ ó���Ѵ�.
	
	private int bg_w; //��� �ʺ�
	private int bg_h; //��� ����
	private int bg_x = 0; //��� x��ǥ
	private int bg_y = 20; //��� y��ǥ
	public int stage_num; //�������� ��ȣ
	
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
