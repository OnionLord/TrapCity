package TrapCity;

import java.awt.Image;
import java.awt.Toolkit;

public class character {

	public int char_w;
	public int char_h;//캐릭터의 크기. 바꾸지 않으므로 get함수만 만든다.
	public int equiped;//get함수로 주고 set함수로 받는다.
	public int char_x; //캐릭터 위치
	public int char_y;
	public int moved;//캐릭터 모션 관련 -1, 1 두가지로 구분된다.
	public int speed;//캐릭터의 속도
	public boolean jumptrue; //점프합니다
	public int jumpcnt; //점프프레임 표현(점프 쓰레드)
	public boolean stoptrue; //멈춥니다.
	public boolean lookatright; //캐릭터가 바라보는 방향
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
		lookatright = true;//true 오른쪽 , false 왼쪽
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
	
