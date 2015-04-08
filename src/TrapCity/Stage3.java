package TrapCity;


import java.awt.*;
import java.awt.event.*;
import java.applet.*;

import javax.sound.sampled.AudioInputStream;
import javax.swing.*;

import sun.audio.*;
import java.awt.image.BufferedImage;
import java.awt.image.BufferStrategy;
import java.io.*;
import java.util.*;
import java.util.Timer;
import java.io.File;
import javax.sound.sampled.*;


public class Stage3{
	public static void main(String ar[])
	{
		frame3 f = new frame3();
		f.anishow();//본 프레임의 쓰레드를 호출한다.
	}
	
}

class frame3 extends Frame implements MouseListener,MouseMotionListener,KeyListener,Runnable
{
	
	private character Hans = new character();
	private background Back_Ground = new background(4000,700,1);
	private MakeRandom rand = new MakeRandom();
	
	private Traps trap[];
	private Bomb b = new Bomb();
	private BombEffect bef = new BombEffect();
	private int able_kill = 0;
	private boolean able_use_key = true;
	private Anitrap at = new Anitrap();
	public rollingstone rs = new rollingstone(1200);
	public rollingstone rs1 = new rollingstone(2200);
	public rollingstone rs2 = new rollingstone(3200);
	public fallingstone fs1 = new fallingstone(600);
	public fallingstone fs2 = new fallingstone(1500);
	public boolean victory = false;
	public boolean oncreadit = false;
	
	
	private Thread aaa;
	private boolean game_over = false;
	//private boolean floated = false;

	private SoundPlay bgm1 = new SoundPlay("gamedata\\bgm\\stage2.wav");
	private SoundPlay ending = new SoundPlay("gamedata\\bgm\\end.wav");

	
	public BufferStrategy strategy; //더블버퍼링을 위한 BufferStrategy클래스
	
	
	public frame3()
	{
		
		super("TrapCity Ver 0.01");
		trap = new Traps[19];
		for ( int i = 0 ; i < 19 ; i ++ ) //파괴 가능한 함정수 조사
		{
			trap[i] = new Traps(2);
			trap[i].set_trap_num(i + 1);
			if ( trap[i].trap_type != 1 )
				able_kill++;
		}
		if ( able_kill <=2 )
		{
			this.setVisible(false);
			new Stage3().main(null);//스테이지1을 불러온다
		}
		aaa = new Thread(this);
		setSize(995, 700);
		bgm1.start();
		this.setLocation(50, 50); //처음 창뜨는 위치
		this.setVisible(true); //창이 보이는지의 여부
		this.setResizable(false); // 창크기 고정
		this.addMouseListener(this);
		this.addMouseMotionListener(this);
		this.addKeyListener(this);
		this.addWindowListener(
			new WindowAdapter(){
			public void windowClosing(WindowEvent ev){
			System.exit(0);
			}
		});//X클릭시 종료
		
		
		createBufferStrategy(3);
		strategy = getBufferStrategy();
	}

	public void paint(Graphics g)//그림 그리기
	{
		
		g = strategy.getDrawGraphics();
		
		g.drawImage(Toolkit.getDefaultToolkit().getImage("gamedata\\pictures\\stage1\\stage3.jpg"), Back_Ground.getbg_x(), Back_Ground.getbg_y(), Back_Ground.getbg_w(), Back_Ground.getbg_h(), this );
		//배경 그리기
		
		//drawImage함수가 아래로 갈수록 맨 위의 레이어처럼...

		for ( int i = 0 ; i < 19 ; i ++ )
		{
			if(trap[i].trap_on == true)
				if ( trap[i].trap_type != 1)
					g.drawImage(trap[i].draw_trap(), trap[i].trap_x + Back_Ground.getbg_x(), trap[i].trap_y, trap[i].trap_w, trap[i].trap_h, this );
		}

		
		if ( b.bomb_on)
		{
			g.drawImage(b.draw_bomb(), b.bomb_x, b.bomb_y, b.bomb_w, b.bomb_h, this);
		}
		if ( bef.bomb_on)
		{
			g.drawImage(bef.draw_bomb(), bef.bomb_x, bef.bomb_y, bef.bomb_w, bef.bomb_h, this);
		}
		
		g.drawImage(fs1.draw_trap(), fs1.rs_x  + Back_Ground.getbg_x(), fs1.rs_y, fs1.rs_w, fs1.rs_h, this);
		
		g.drawImage(fs2.draw_trap(), fs2.rs_x  + Back_Ground.getbg_x(), fs2.rs_y, fs2.rs_w, fs2.rs_h, this);
		//g.drawImage(Toolkit.getDefaultToolkit().getImage("gamedata\\pictures\\stage1\\BackGround.gif"), Back_Ground.getbg_x(), Back_Ground.getbg_y(), Back_Ground.getbg_w(), Back_Ground.getbg_h(), this );
		
		for ( int i = 0 ; i < 19 ; i ++ )
		{
			if(trap[i].trap_on == true)
				if ( trap[i].trap_type == 1)
					g.drawImage(trap[i].draw_trap(), trap[i].trap_x + Back_Ground.getbg_x(), trap[i].trap_y, trap[i].trap_w, trap[i].trap_h, this );
		}
		g.drawImage(rs.draw_trap(), rs.rs_x  + Back_Ground.getbg_x(), rs.rs_y, rs.rs_w, rs.rs_h, this);
		g.drawImage(rs1.draw_trap(), rs1.rs_x  + Back_Ground.getbg_x(), rs1.rs_y, rs1.rs_w, rs1.rs_h, this);
		g.drawImage(rs2.draw_trap(), rs2.rs_x  + Back_Ground.getbg_x(), rs2.rs_y, rs2.rs_w, rs2.rs_h, this);
		g.drawImage(Hans.Drawchar(), Hans.char_x, Hans.char_y, Hans.char_w, Hans.char_h, this);
		if (game_over == true)
		{
			g.drawImage(Toolkit.getDefaultToolkit().getImage("gamedata\\pictures\\maintitle\\gameover.jpg"), 0, 0, Back_Ground.getbg_w()/4, Back_Ground.getbg_h(), this );
		}
		if ( victory == true)
		{
			g.drawImage(Toolkit.getDefaultToolkit().getImage("gamedata\\pictures\\maintitle\\ending.jpg"), 0, 0, Back_Ground.getbg_w()/4, Back_Ground.getbg_h(), this );
		}
		if (oncreadit)
		{
			g.drawImage(Toolkit.getDefaultToolkit().getImage("gamedata\\pictures\\maintitle\\creadit.jpg"), 0, 0, Back_Ground.getbg_w()/4, Back_Ground.getbg_h(), this );
		}
		strategy.show();
		
	}

	
	
	public void update ( Graphics g )
	
	{
		paint(g); //원리는 잘 모르겠다. 다만 그림 깜빡이는 현상을 약간이나마 줄일 수 있다.
		//더블 버퍼링을 못하겠어...
	}
	
	public void mouseClicked(MouseEvent e) {}
	public void mouseEntered(MouseEvent e) {}
	public void mouseExited(MouseEvent e)  {}
	public void mousePressed(MouseEvent e)  {}
	public void mouseReleased(MouseEvent e) {}
	public void mouseDragged(MouseEvent e) {} 
	public void mouseMoved(MouseEvent e) {}
	public void keyPressed(KeyEvent e) 
	{
		if(able_use_key)
		{
			Hans.stoptrue = false;
			if (e.getKeyCode() == e.VK_ESCAPE)
			{
				System.exit(0);
			}
			if (e.getKeyCode()==e.VK_LEFT) 
			{
				Hans.pressed_key_left = true;//왼쪽키누름 인증
				Hans.stare_num = 2;
				
	            move_left();
	            judge_trap_appear();
	            judge_death();
	        }
			
			
	        else if (e.getKeyCode()==e.VK_RIGHT) 
	        {
	        	Hans.pressed_key_right = true; //오른쪽키 누름 인증
	           	Hans.stare_num = 1;
	        	
	        	move_right();
	        	judge_trap_appear();
	        	judge_death();
	        	
	            
	        }
	        else if ( e.getKeyCode() == e.VK_SPACE)//윗 방향키
			{
	        	if ( Hans.pressed_key_jump == false && Hans.jumptrue == false )
	        	{
		        	SoundPlay jump1 = new SoundPlay("gamedata\\sound\\jump1.wav");
	        		jump1.start(); 
		        	
		        	Hans.pressed_key_jump = true;	//위쪽키 누름 인증		
		      	
		        	Hans.jumptrue = true;	//점프 상태 돌입
					jumping();//점프하는 함수를 부름으로써 점프 쓰레드를 간접 소환한다.
					//appear_trap();
	        	}
			
			}
			
			else if (e.getKeyCode() == e.VK_CONTROL)
			{
				if ( Hans.pressed_key_attack == false && Hans.attack_true == false)
				{
					Hans.pressed_key_attack = true;
					Hans.attack_true = true;
					attack();
				}
			}
			
	        repaint();
		}
		else
		{
			if(game_over == true)
			{
				if (e.getKeyCode() == e.VK_SPACE || e.getKeyCode() == e.VK_ENTER)
				{
					bgm1.stop();
					this.setVisible(false);	
					new Stage3().main(null);//스테이지1을 불러온다
	
				}
				if (e.getKeyCode() == e.VK_ESCAPE)
					System.exit(0);
				if (e.getKeyCode() == e.VK_F2)
				{
					bgm1.stop();
					this.setVisible(false);
					new GameMain().main(null);
				}
			}
			else if ( victory == true )
			{
				
				if (oncreadit)
				{
					if (e.getKeyCode() == e.VK_SPACE || e.getKeyCode() == e.VK_ENTER)
					{
						bgm1.stop();
						this.setVisible(false);
						new GameMain().main(null);
		
					}
				}
				else
				{
					if (e.getKeyCode() == e.VK_SPACE || e.getKeyCode() == e.VK_ENTER && oncreadit != true)
					{
						oncreadit = true;

					}
				}

			}
		}
        
	}
	
	public void appear_trap()
	{
		Anitrap at = new Anitrap();
		at.start();

	}
	
	public void judge_death()
	{
		
		SoundPlay die = new SoundPlay("gamedata\\sound\\die.wav");
		
		
		
		

		
		
	}

	public void judge_vic()
	{
		
		if ( able_kill == 0)
		{
			System.out.printf("vic\n");
			
			bgm1.stop();
			//JPanel panel = new JPanel();
			//JOptionPane.showMessageDialog(panel, "All Clear!!!", "Victory!!", JOptionPane.WARNING_MESSAGE);
			//new GameMain().main(null);//스테이지1을 불러온다
			//this.setVisible(false);
			victory = true;
			able_use_key = false;
		}
	}
	
	public void judge_trap_appear()
	{
		for ( int i = 0 ; i < 19 ; i ++ )
		{
		
			//판정 좌표 수정
			if ( Hans.char_x + Hans.char_w >= trap[i].trap_x + Back_Ground.getbg_x() - 10 && Hans.char_x <= trap[i].trap_x + Back_Ground.getbg_x() + trap[i].trap_w - 10)
			{
				if ( trap[i].trap_on == false)
				{
					System.out.printf("dfd");
					trap[i].trap_on = true;
					if ( trap[i].activated == false)
						appear_trap();
				}
				//if (trap[i].trap_type == 2)
				//	at.start();
			}
			else
			{
				trap[i].trap_on =false;

			}
			
		}
		stonerolling sr = new stonerolling();
		stonerolling1 sr1 = new stonerolling1();
		stonerolling2 sr2 = new stonerolling2();
		
		
		if ( Hans.char_x + Hans.char_w >= rs.rs_x - 500 + Back_Ground.getbg_x()  && Hans.char_x <= rs.rs_x - 500 + Back_Ground.getbg_x() + rs.rs_w )
		{
			rs.rs_on = true;
			sr.start();
			
		}
		if ( Hans.char_x + Hans.char_w >= rs1.rs_x - 500 + Back_Ground.getbg_x()  && Hans.char_x <= rs1.rs_x - 500 + Back_Ground.getbg_x() + rs1.rs_w )
		{
			rs1.rs_on = true;
			sr1.start();
			
		}
		if ( Hans.char_x + Hans.char_w >= rs2.rs_x - 500 + Back_Ground.getbg_x()  && Hans.char_x <= rs2.rs_x - 500 + Back_Ground.getbg_x() + rs2.rs_w )
		{
			rs2.rs_on = true;
			sr2.start();
			
		}
	}
	
	public void keyReleased(KeyEvent e) 
	{
		
		if (able_use_key)
		{
			if (e.getKeyCode()==e.VK_LEFT) //각 키를 뗄 데 마다 키 누름 스위치를 끈다.
			{
				//특정방향 점프 후 상황은 키는 프레스를 계속 받지 않지만 릴리즈는 되지 않음
				//움직이는 상태는 유효함.
				//Hans.state_num = 0;
				Hans.pressed_key_left = false;
				
	        }
			
	        if (e.getKeyCode()==e.VK_RIGHT) 
	        {
	        	//Hans.state_num = 0;
	           	Hans.pressed_key_right = false;
	        }
	        if ( e.getKeyCode() == e.VK_SPACE)
			{
	        	Hans.pressed_key_jump = false;
			}
	        if( e.getKeyCode() == e.VK_CONTROL)
	        {
	        	Hans.pressed_key_attack = false;
	        }
			
	        repaint();
		}
	
	}
	
	public void anishow()//본프레임 쓰레드 호출
	{
		aaa.start();
	}
	
	public void run() //본 프레임의 쓰레드. 현재 돌굴러 갈때 죽는 것만 담당한다.
	{
		 Thread.currentThread().setPriority(Thread.MIN_PRIORITY);
         
         // 현재 쓰래드의 객체를 가져온다.
         Thread curThread = Thread.currentThread();
         
         // 현재 쓰레드가 카운터 쓰레드라면...
         if(curThread == aaa) {
           for(;;) 
           {
        	   if ( Hans.char_w + Hans.char_x >= Back_Ground.getbg_x() + 3800)
       		{
       			bgm1.stop();
       			victory = true;
       			able_use_key = false;
       		}
        	   for ( int i = 0 ; i < 19 ; i ++ )
       		{
       			//판정 좌표 수정
       			if ( Hans.char_x + Hans.char_w - 40 >= trap[i].trap_x + Back_Ground.getbg_x() && Hans.char_x <= trap[i].trap_x + Back_Ground.getbg_x() + trap[i].trap_w - 40)
       			{
       				
       				if ( Hans.char_y >= 540-65 && trap[i].trap_type == 2 && trap[i].trap_state >= 4)
       				{
       					
       					if ( trap[i].trap_state != 9 && trap[i].trap_state >=4 ) //가시함정에 죽을때
       					{
       						bgm1.stop();
       						able_use_key = false;
       						//System.out.printf("Game Over\n");
       						//die.start();
       						//Hans.state_num = 14;
       						game_over = true;					
       						//this.setVisible(false);
       						//new GameOver().main(null);//스테이지1을 불러온다
       						
       					}
       				}	
       				
       				if ( Hans.char_y == 560-65 && trap[i].trap_type == 1)//구덩이에 죽을때
       				{
       					
       					if ( trap[i].trap_state != 9)
       					{
       						bgm1.stop();
       						Hans.char_x = trap[i].trap_x + Back_Ground.getbg_x()-10;
       						Hans.char_y += 20;
       						able_use_key = false;
       						//System.out.printf("Game Over\n");
       						//die.start();
       						//Hans.state_num = 14;
       						game_over = true;					
       						//this.setVisible(false);
       						//new GameOver().main(null);//스테이지1을 불러온다
       						
       					}
       					
       				}
       				
       			}
       			
       			
       			if (Hans.char_x + Hans.char_w - 40 >= rs.rs_x + Back_Ground.getbg_x() && Hans.char_x <= rs.rs_x + Back_Ground.getbg_x() + rs.rs_w - 40 && Hans.jumptrue == false)
       			{
       				game_over = true;
       			}
       			if (Hans.char_x + Hans.char_w - 40 >= rs1.rs_x + Back_Ground.getbg_x() && Hans.char_x <= rs1.rs_x + Back_Ground.getbg_x() + rs1.rs_w - 40 && Hans.jumptrue == false)
       			{
       				game_over = true;
       			}
       			if (Hans.char_x + Hans.char_w - 40 >= rs2.rs_x + Back_Ground.getbg_x() && Hans.char_x <= rs2.rs_x + Back_Ground.getbg_x() + rs2.rs_w - 40 && Hans.jumptrue == false)
       			{
       				game_over = true;
       			}
       		}
        	if ( game_over == true)
   			{
   				temp_die();
   				break;
   			}
                
                
                
             repaint();//다시 그려줘라 
             try 
             {
               Thread.sleep(100);
             }
             catch(InterruptedException e) 
             {
               break;
             }
           }
         }


	}
	

	public void move_right()
	{
		//while(Hans.pressed_key_right)
		//{
			if ( Hans.char_x <= 1000-Hans.char_w)//원리는 Keypressed의 이동방식과 같다.
	    	{

	    		if ( Back_Ground.getbg_x() >= -3000 + 5 && Hans.char_x >= 500)
				{
	    			for ( int i = 0 ; i < (Hans.speed) ; i ++)
	    			{
						Back_Ground.set_bgx(Back_Ground.getbg_x()-1);
						//judge_trap_appear();
	    			}
				}
	
	    		else
	    		{
	    			for ( int i = 0 ; i < (Hans.speed) ; i ++)
	    			{
	    				Hans.char_x += 1;
	    				//judge_trap_appear();
	    			}
	    		}
	
					
	    	}
			if ( Hans.jumptrue == false)
			{
				if (Hans.state_num == 8)
					Hans.state_num = 1;
				else
					Hans.state_num++;
			}
		//}

	}
	
	public void move_left()
	{
		//while(Hans.pressed_key_left)
		//{
			if ( Hans.char_x >= 0 )//화면의 왼쪽부분을 넘기지 않는다.
			{
				
				if ( Back_Ground.getbg_x() <= -5 && Hans.char_x <= 500) 
				//배경화면의 왼쪽 끝부분까지 도달하지 않을때 && 화면 가운데를 넘어갈 때
				{
					for ( int i = 0 ; i < (Hans.speed) ; i ++)
					{
					//화면의 절반에 도달할때는 배경과 함께 움직인다. 그리고 배경의 1/4 지점에 도달하면 캐릭터는 따로 논다.
						Back_Ground.set_bgx(Back_Ground.getbg_x()+ 1);
					//배경만 움직이면 캐릭터도 같이 움직이는 것 같은 효과를 낸다.
						//judge_trap_appear();
					}
				}
	
				else 
				{
					for ( int i = 0 ; i < (Hans.speed) ; i ++)
					{
						Hans.char_x -= 1;
						//judge_trap_appear();
					}
				}
			
				
			}
			if ( Hans.jumptrue == false)
			{
				if (Hans.state_num == 8)
					Hans.state_num = 1;
				else
					Hans.state_num++;
			}
		//}

	}
	
	public void jumping() //점프 쓰레드를 소환하는 함수이다.
	{
		Ani_Jump_Up char_jump_up = new Ani_Jump_Up();

		if ( Hans.jumptrue == true)
		{
			char_jump_up.start();
			
		}

	}
	
	public void attack()
	{
		Attack_Motion atk = new Attack_Motion();
		if ( Hans.attack_true == true )
		{
			
			atk.start();
			
		}
	}

	public void keyTyped(KeyEvent e) 
	{
		  // TODO 자동 생성된 메소드 스텁
	}
	
	public void Sapjil_of_jump() //x축 좌표를 변경하기 위한 삽질이다.
	{
		if (Hans.stoptrue == false) //멈추지 않으면
		{
			if ( Hans.pressed_key_right )//오른쪽 방향키를 누르면
			{
				Hans.speed *= 4;//점프 시 x축 속도의 2배의 속도를 내게 한다.
				move_right();
				Hans.speed /= 4;
			}
			else if (Hans.pressed_key_left) //왼쪽 방향키를 누르면
			{
				Hans.speed *= 4;
				move_left();
				Hans.speed /= 4;
			}
		}
	
	}
	
	class Ani_Jump_Up extends Thread //점프용 쓰레드
	{
		public void run()
		{
			try
			{
				for ( int i = 0 ; i < 14 ; i ++ )
				{
					Hans.jumpcnt++;//점프 프레임수 증가
					switch (Hans.jumpcnt)//y축을 변형시킨다. x축은 삽질로...
					{//점프 프레임 수에 따라 얼마나 위치가 변하는지 결정한다.
					case 1:  Hans.char_y -= 40; Hans.state_num = 14; Sapjil_of_jump();  break;
					case 2:  Hans.char_y -= 30; Sapjil_of_jump(); break;
					case 3:  Hans.char_y -= 20; Sapjil_of_jump(); break;
					case 4:  Hans.char_y -= 10; Sapjil_of_jump();break;
					case 5:  Hans.char_y -= 5; Sapjil_of_jump(); break;
					case 6:  Hans.char_y -= 2; Sapjil_of_jump(); break;
					case 7:  Hans.char_y -= 1; Sapjil_of_jump(); break;
					case 8:  Hans.char_y += 1; Sapjil_of_jump(); break;
					case 9:  Hans.char_y += 2; Sapjil_of_jump(); break;
					case 10: Hans.char_y += 5; Sapjil_of_jump(); break;
					case 11: Hans.char_y += 10; Sapjil_of_jump(); break;
					case 12: Hans.char_y += 20; Sapjil_of_jump();  break;
					case 13: Hans.char_y += 30; Sapjil_of_jump(); break;
					case 14:  Hans.char_y += 40; Sapjil_of_jump(); 	
					Hans.state_num = 0;
					Hans.jumptrue = false;//점프 끝
					Hans.jumpcnt = 0;//점프 초기화
					judge_death();

					break;
					}
					judge_trap_appear();
					Thread.sleep(55);
					repaint();
				}
			}
			catch(Exception ex)
			{
			}
		}
	}
	
	public void Bomb_Start()
	{
		Bomb_Motion bm = new Bomb_Motion();

		if (Hans.stare_num == 1)//방향을 나타냄.
		{
			b.bomb_x = Hans.char_x + 42 + b.bomb_w; //폭탄의 시작위치 조정
			b.bombspeed = -6;
		}
		if ( Hans.stare_num == 2)
		{
			b.bomb_x = Hans.char_x + 13;
			b.bombspeed = 6;
		}
		b.bomb_on = true;
		bm.start();
	}
	
	class Attack_Motion extends Thread //공격용 쓰레드
	{
		public void run()
		{
			SoundPlay atkwav = new SoundPlay("gamedata\\sound\\attack.wav");
			
			try
			{
				
				for ( int i = 0 ; i < 14 ; i ++ )
				{
					Hans.attackcnt++;
					switch (Hans.attackcnt)
					{//공격프레임수에 따라서 모션이 바뀐다.
					
					case 1:  Hans.state_num = 11;  break;
					case 5: atkwav.start();  Hans.state_num = 12; break;
					
					case 9:  Hans.state_num = 13; Bomb_Start(); break;
					case 14: 
					Hans.attackcnt = 0;
					Hans.attack_true = false;//공격 모션 끝.
					Hans.state_num = 0;
					break;
					}
					Thread.sleep(65);
					repaint();
				}
	
			}
			catch(Exception ex)
			{
			}
		}
	}
	
	class Bomb_Motion extends Thread 
	{
		public void run()
		{
			Bomb_Effect be = new Bomb_Effect();
			try
			{
				for ( int i = 0 ; i < 7 ; i ++ )
				{
					b.bombcnt++;//폭탄 알 프레임수 증가
					switch (b.bombcnt)
					{
					case 1:  b.bomb_x-=b.bombspeed * 2;  break;
					case 2:  b.bomb_x-=b.bombspeed * 2; break;
					case 3:  b.bomb_x-=b.bombspeed * 2; break;
					case 4:  b.bomb_x-=b.bombspeed * 2;break;
					case 5:  b.bomb_x-=b.bombspeed * 2; break;
					case 6:  b.bomb_x-=b.bombspeed * 2; break;
					case 7:  b.bomb_x-=b.bombspeed * 2;
					//Hans.state_num = 0;
					
					b.bomb_on = false;//폭탄 알 끝
					b.bombcnt = 0;//폭탄 알 초기화
					b.bomb_y = 600-65;
					
					break;
					}
					Thread.sleep(35);
					repaint();
					
					
					int j;
					for ( j = 0 ; j < 19 ; j ++ )
					{
						//폭탄이 함정에 맞으면
						if ( b.bomb_x + b.bomb_w  >= trap[j].trap_x + trap[j].trap_w/2 + Back_Ground.getbg_x() - 10 && b.bomb_x <= trap[j].trap_x + Back_Ground.getbg_x() + trap[j].trap_w/2 + 10 && trap[j].trap_type!=1)
						{
							
							if ( trap[j].trap_state != 9 && trap[j].trap_on == true)
							{
								
								b.bomb_on = false;
								//b.bombcnt = 0;//점프 초기화
								b.bomb_y = 600-65;
								bef.bomb_on=true;
								bef.bomb_x = b.bomb_x;
								bef.bomb_y = b.bomb_y;
								
								System.out.printf("Attack\n");
								if ( true )
								{
									
									trap[j].trap_state = 9;//파괴됨
									able_kill--;
									
								}
								be.start();//폭파 이펙트 실행
								judge_vic();//승리 판정
								
								break;
							}
							else
							{
								break;
							}

							
						}
					}

					repaint();
				}
				
				
			}
			catch(Exception ex)
			{
			}
		}
	}
	
	class Bomb_Effect extends Thread 
	{
		public void run()
		{
			try
			{
				SoundPlay atkwav = new SoundPlay("gamedata\\sound\\killed.wav");
				
				for ( int j = 0 ; j < 7 ; j ++ )
				{
					bef.bombcnt++;//점프 프레임수 증가
					switch (bef.bombcnt)//y축을 변형시킨다. x축은 삽질로...
					{//점프 프레임 수에 따라 얼마나 위치가 변하는지 결정한다.
					case 1:  atkwav.start(); break;
					case 2:  bef.bomb_state = 1; break;
					case 3:   break;
					case 4:  bef.bomb_state = 2;break;
					case 5:  bef.bomb_state = 3;break;
					case 6:   break;
					case 7:  bef.bomb_state = 4;
					//Hans.state_num = 0;
					bef.bomb_on = false;//점프 끝
					bef.bombcnt = 0;//점프 초기화
					break;
					}
					Thread.sleep(55);
					
					repaint();
				}
			}
			catch(Exception ex)
			{
			}
		}
	}

	//판정문제
	//고정적 함정말고 동적인 함정 추가. ex)위에서 떨어지는것
	class Anitrap extends Thread 
	{
		public void run()
		{
			try
			{
				
				for ( int i = 0 ; i < 19 ; i ++ )
				{
					if(trap[i].trap_type == 2 && trap[i].trap_on == true && trap[i].trap_state != 9 && trap[i].activated == false)
					{
						
						for ( int j = 0 ; j < 15 ; j ++ )
						{
							switch (j)//y축을 변형시킨다. x축은 삽질로...
							{//점프 프레임 수에 따라 얼마나 위치가 변하는지 결정한다.
							case 1: trap[i].trap_state = 3; break;
							case 6: trap[i].trap_state = 4;  break;
							case 14: trap[i].trap_state = 5;
							//Hans.state_num = 0;
	
							break;
							}
							
							//if(Hans.char_x + Hans.char_w - 40 >= trap[i].trap_x + Back_Ground.getbg_x() && Hans.char_x <= trap[i].trap_x + Back_Ground.getbg_x() + trap[i].trap_w - 40&& trap[i].trap_state >=4)
							//	rs.make_death = true;
							//judge_death();
							Thread.sleep(55);
							
							repaint();
						}
						trap[i].activated = true;
					}
				}
			}
			catch(Exception ex)
			{
			}
		}
	}
	
	
	public void temp_die()
	{
		SoundPlay die = new SoundPlay("gamedata\\sound\\die.wav");
		bgm1.stop();
		rs.rs_on = false;
		rs1.rs_on = false;
		rs2.rs_on = false;
		able_use_key = false;
		System.out.printf("Game Over\n");
		die.start();
		Hans.state_num = 15;
							
		//this.setVisible(false);
		//new GameOver().main(null);//스테이지1을 불러온다
	}
	
	class stonerolling extends Thread // 돌굴러감
	{
		public void run()
		{
			
			try
			{
				if ( rs.rs_on = true)
				{
					while(game_over != true)
					{
						/*if (Hans.char_x + Hans.char_w - 40 >= rs.rs_x + Back_Ground.getbg_x() && Hans.char_x <= rs.rs_x + Back_Ground.getbg_x() + rs.rs_w - 40 && Hans.jumptrue == false)
						{//돌에 캐릭터가 맞는 범위이다.
	
							System.out.printf("ang!\n");
							game_over = true; //죽음 확인
							//temp_die();
							Thread.interrupted();
							Thread.yield();
							break;
						}*/
						rs.rs_x -= 2;
						Thread.sleep(55);
						repaint();
					}
					
				}
				
				if ( rs1.rs_on == true)
				{
					while(game_over != true)
					{
						/*if (Hans.char_x + Hans.char_w - 40 >= rs.rs_x + Back_Ground.getbg_x() && Hans.char_x <= rs.rs_x + Back_Ground.getbg_x() + rs.rs_w - 40 && Hans.jumptrue == false)
						{//돌에 캐릭터가 맞는 범위이다.
	
							System.out.printf("ang!\n");
							game_over = true; //죽음 확인
							//temp_die();
							Thread.interrupted();
							Thread.yield();
							break;
						}*/
						rs1.rs_x -= 3;
						Thread.sleep(55);
						repaint();
					}
					
				}
				
			}
			catch(Exception ex)
			{
			}
			
		}
	}
	
	class stonerolling1 extends Thread // 돌굴러감
	{
		public void run()
		{
			
			try
			{
				if ( rs1.rs_on == true)
				{
					while(game_over != true)
					{
						/*if (Hans.char_x + Hans.char_w - 40 >= rs.rs_x + Back_Ground.getbg_x() && Hans.char_x <= rs.rs_x + Back_Ground.getbg_x() + rs.rs_w - 40 && Hans.jumptrue == false)
						{//돌에 캐릭터가 맞는 범위이다.
	
							System.out.printf("ang!\n");
							game_over = true; //죽음 확인
							//temp_die();
							Thread.interrupted();
							Thread.yield();
							break;
						}*/
						rs1.rs_x -= 3;
						Thread.sleep(55);
						repaint();
					}
					
				}
				
				
				
			}
			catch(Exception ex)
			{
			}
			
		}
	}
	

	class stonerolling2 extends Thread // 돌굴러감
	{
		public void run()
		{
			
			try
			{
				if ( rs2.rs_on == true)
				{
					while(game_over != true)
					{
						/*if (Hans.char_x + Hans.char_w - 40 >= rs.rs_x + Back_Ground.getbg_x() && Hans.char_x <= rs.rs_x + Back_Ground.getbg_x() + rs.rs_w - 40 && Hans.jumptrue == false)
						{//돌에 캐릭터가 맞는 범위이다.
	
							System.out.printf("ang!\n");
							game_over = true; //죽음 확인
							//temp_die();
							Thread.interrupted();
							Thread.yield();
							break;
						}*/
						rs2.rs_x -= 3;
						Thread.sleep(55);
						repaint();
					}
					
				}
				
				
				
			}
			catch(Exception ex)
			{
			}
			
		}
	}
	
	/*
	class stonefalling extends Thread 
	{
		public void run()
		{
			try
			{
				if ( fs1.rs_on = true)
				{
					while(fs1.make_death != true)
					{
						if (fs1.rs_y == Hans.char_y + 10)
						{
							System.out.printf("ang!\n");
							fs1.make_death = true;
							//temp_die();

							break;
						}
						fs1.rs_y -= 10;
						Thread.sleep(100);
						repaint();
					}
					
				}
				
			}
			catch(Exception ex)
			{
			}
			
		}
	}*/
}