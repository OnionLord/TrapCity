package TrapCity;

import java.awt.Image;
import java.awt.Toolkit;

public class character {

	public int char_w;
	public int char_h;//ĳ������ ũ��. �ٲ��� �����Ƿ� get�Լ��� �����.
	public int equiped;//get�Լ��� �ְ� set�Լ��� �޴´�.
	public int char_x; //ĳ���� ��ġ
	public int char_y;
	public int moved;//ĳ���� ��� ���� -1, 1 �ΰ����� ���еȴ�.
	public int speed;//ĳ������ �ӵ�
	public boolean jumptrue; //�����մϴ�
	public int jumpcnt; //���������� ǥ��(���� ������)
	public boolean stoptrue; //����ϴ�.
	public boolean lookatright; //ĳ���Ͱ� �ٶ󺸴� ����
	public boolean pressed_key_left=false;
	public boolean pressed_key_right=false;
	public boolean pressed_key_jump=false;
	public boolean pressed_key_attack=false;
	public int life;
	public int movecnt;
	public int stare_num;
	public int state_num;
	public boolean attack_true;
	public int attackcnt;
	
	
	character()
	{
		char_w = 80;
		char_h = 80;
		char_x = 0;
		char_y = 560-64;
		moved = 1;
		speed = 3;
		jumpcnt = 0;
		jumptrue = false;
		stoptrue = false;
		lookatright = true;//true ������ , false ����
		life = 3;
		pressed_key_left=false;
		pressed_key_right=false;
		pressed_key_jump=false;
		pressed_key_attack=false;
		movecnt = 0;
		attackcnt = 0;

		stare_num = 1;
		state_num = 0;
	}
	
	
	public Image Drawchar()
	{
		return Toolkit.getDefaultToolkit().getImage("gamedata\\pictures\\char\\char_" + stare_num + "_" + state_num + ".gif");
	}

}
	
