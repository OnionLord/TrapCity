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
		f.anishow();//�� �������� �����带 ȣ���Ѵ�.
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

	
	public BufferStrategy strategy; //������۸��� ���� BufferStrategyŬ����
	
	
	public frame3()
	{
		
		super("TrapCity Ver 0.01");
		trap = new Traps[19];
		for ( int i = 0 ; i < 19 ; i ++ ) //�ı� ������ ������ ����
		{
			trap[i] = new Traps(2);
			trap[i].set_trap_num(i + 1);
			if ( trap[i].trap_type != 1 )
				able_kill++;
		}
		if ( able_kill <=2 )
		{
			this.setVisible(false);
			new Stage3().main(null);//��������1�� �ҷ��´�
		}
		aaa = new Thread(this);
		setSize(995, 700);
		bgm1.start();
		this.setLocation(50, 50); //ó�� â�ߴ� ��ġ
		this.setVisible(true); //â�� ���̴����� ����
		this.setResizable(false); // âũ�� ����
		this.addMouseListener(this);
		this.addMouseMotionListener(this);
		this.addKeyListener(this);
		this.addWindowListener(
			new WindowAdapter(){
			public void windowClosing(WindowEvent ev){
			System.exit(0);
			}
		});//XŬ���� ����
		
		
		createBufferStrategy(3);
		strategy = getBufferStrategy();
	}

	public void paint(Graphics g)//�׸� �׸���
	{
		
		g = strategy.getDrawGraphics();
		
		g.drawImage(Toolkit.getDefaultToolkit().getImage("gamedata\\pictures\\stage1\\stage3.jpg"), Back_Ground.getbg_x(), Back_Ground.getbg_y(), Back_Ground.getbg_w(), Back_Ground.getbg_h(), this );
		//��� �׸���
		
		//drawImage�Լ��� �Ʒ��� ������ �� ���� ���̾�ó��...

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
		paint(g); //������ �� �𸣰ڴ�. �ٸ� �׸� �����̴� ������ �ణ�̳��� ���� �� �ִ�.
		//���� ���۸��� ���ϰھ�...
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
				Hans.pressed_key_left = true;//����Ű���� ����
				Hans.stare_num = 2;
				
	            move_left();
	            judge_trap_appear();
	            judge_death();
	        }
			
			
	        else if (e.getKeyCode()==e.VK_RIGHT) 
	        {
	        	Hans.pressed_key_right = true; //������Ű ���� ����
	           	Hans.stare_num = 1;
	        	
	        	move_right();
	        	judge_trap_appear();
	        	judge_death();
	        	
	            
	        }
	        else if ( e.getKeyCode() == e.VK_SPACE)//�� ����Ű
			{
	        	if ( Hans.pressed_key_jump == false && Hans.jumptrue == false )
	        	{
		        	SoundPlay jump1 = new SoundPlay("gamedata\\sound\\jump1.wav");
	        		jump1.start(); 
		        	
		        	Hans.pressed_key_jump = true;	//����Ű ���� ����		
		      	
		        	Hans.jumptrue = true;	//���� ���� ����
					jumping();//�����ϴ� �Լ��� �θ����ν� ���� �����带 ���� ��ȯ�Ѵ�.
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
					new Stage3().main(null);//��������1�� �ҷ��´�
	
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
			//new GameMain().main(null);//��������1�� �ҷ��´�
			//this.setVisible(false);
			victory = true;
			able_use_key = false;
		}
	}
	
	public void judge_trap_appear()
	{
		for ( int i = 0 ; i < 19 ; i ++ )
		{
		
			//���� ��ǥ ����
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
			if (e.getKeyCode()==e.VK_LEFT) //�� Ű�� �� �� ���� Ű ���� ����ġ�� ����.
			{
				//Ư������ ���� �� ��Ȳ�� Ű�� �������� ��� ���� ������ ������� ���� ����
				//�����̴� ���´� ��ȿ��.
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
	
	public void anishow()//�������� ������ ȣ��
	{
		aaa.start();
	}
	
	public void run() //�� �������� ������. ���� ������ ���� �״� �͸� ����Ѵ�.
	{
		 Thread.currentThread().setPriority(Thread.MIN_PRIORITY);
         
         // ���� �������� ��ü�� �����´�.
         Thread curThread = Thread.currentThread();
         
         // ���� �����尡 ī���� ��������...
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
       			//���� ��ǥ ����
       			if ( Hans.char_x + Hans.char_w - 40 >= trap[i].trap_x + Back_Ground.getbg_x() && Hans.char_x <= trap[i].trap_x + Back_Ground.getbg_x() + trap[i].trap_w - 40)
       			{
       				
       				if ( Hans.char_y >= 540-65 && trap[i].trap_type == 2 && trap[i].trap_state >= 4)
       				{
       					
       					if ( trap[i].trap_state != 9 && trap[i].trap_state >=4 ) //���������� ������
       					{
       						bgm1.stop();
       						able_use_key = false;
       						//System.out.printf("Game Over\n");
       						//die.start();
       						//Hans.state_num = 14;
       						game_over = true;					
       						//this.setVisible(false);
       						//new GameOver().main(null);//��������1�� �ҷ��´�
       						
       					}
       				}	
       				
       				if ( Hans.char_y == 560-65 && trap[i].trap_type == 1)//�����̿� ������
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
       						//new GameOver().main(null);//��������1�� �ҷ��´�
       						
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
                
                
                
             repaint();//�ٽ� �׷���� 
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
			if ( Hans.char_x <= 1000-Hans.char_w)//������ Keypressed�� �̵���İ� ����.
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
			if ( Hans.char_x >= 0 )//ȭ���� ���ʺκ��� �ѱ��� �ʴ´�.
			{
				
				if ( Back_Ground.getbg_x() <= -5 && Hans.char_x <= 500) 
				//���ȭ���� ���� ���κб��� �������� ������ && ȭ�� ����� �Ѿ ��
				{
					for ( int i = 0 ; i < (Hans.speed) ; i ++)
					{
					//ȭ���� ���ݿ� �����Ҷ��� ���� �Բ� �����δ�. �׸��� ����� 1/4 ������ �����ϸ� ĳ���ʹ� ���� ���.
						Back_Ground.set_bgx(Back_Ground.getbg_x()+ 1);
					//��游 �����̸� ĳ���͵� ���� �����̴� �� ���� ȿ���� ����.
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
	
	public void jumping() //���� �����带 ��ȯ�ϴ� �Լ��̴�.
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
		  // TODO �ڵ� ������ �޼ҵ� ����
	}
	
	public void Sapjil_of_jump() //x�� ��ǥ�� �����ϱ� ���� �����̴�.
	{
		if (Hans.stoptrue == false) //������ ������
		{
			if ( Hans.pressed_key_right )//������ ����Ű�� ������
			{
				Hans.speed *= 4;//���� �� x�� �ӵ��� 2���� �ӵ��� ���� �Ѵ�.
				move_right();
				Hans.speed /= 4;
			}
			else if (Hans.pressed_key_left) //���� ����Ű�� ������
			{
				Hans.speed *= 4;
				move_left();
				Hans.speed /= 4;
			}
		}
	
	}
	
	class Ani_Jump_Up extends Thread //������ ������
	{
		public void run()
		{
			try
			{
				for ( int i = 0 ; i < 14 ; i ++ )
				{
					Hans.jumpcnt++;//���� �����Ӽ� ����
					switch (Hans.jumpcnt)//y���� ������Ų��. x���� ������...
					{//���� ������ ���� ���� �󸶳� ��ġ�� ���ϴ��� �����Ѵ�.
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
					Hans.jumptrue = false;//���� ��
					Hans.jumpcnt = 0;//���� �ʱ�ȭ
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

		if (Hans.stare_num == 1)//������ ��Ÿ��.
		{
			b.bomb_x = Hans.char_x + 42 + b.bomb_w; //��ź�� ������ġ ����
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
	
	class Attack_Motion extends Thread //���ݿ� ������
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
					{//���������Ӽ��� ���� ����� �ٲ��.
					
					case 1:  Hans.state_num = 11;  break;
					case 5: atkwav.start();  Hans.state_num = 12; break;
					
					case 9:  Hans.state_num = 13; Bomb_Start(); break;
					case 14: 
					Hans.attackcnt = 0;
					Hans.attack_true = false;//���� ��� ��.
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
					b.bombcnt++;//��ź �� �����Ӽ� ����
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
					
					b.bomb_on = false;//��ź �� ��
					b.bombcnt = 0;//��ź �� �ʱ�ȭ
					b.bomb_y = 600-65;
					
					break;
					}
					Thread.sleep(35);
					repaint();
					
					
					int j;
					for ( j = 0 ; j < 19 ; j ++ )
					{
						//��ź�� ������ ������
						if ( b.bomb_x + b.bomb_w  >= trap[j].trap_x + trap[j].trap_w/2 + Back_Ground.getbg_x() - 10 && b.bomb_x <= trap[j].trap_x + Back_Ground.getbg_x() + trap[j].trap_w/2 + 10 && trap[j].trap_type!=1)
						{
							
							if ( trap[j].trap_state != 9 && trap[j].trap_on == true)
							{
								
								b.bomb_on = false;
								//b.bombcnt = 0;//���� �ʱ�ȭ
								b.bomb_y = 600-65;
								bef.bomb_on=true;
								bef.bomb_x = b.bomb_x;
								bef.bomb_y = b.bomb_y;
								
								System.out.printf("Attack\n");
								if ( true )
								{
									
									trap[j].trap_state = 9;//�ı���
									able_kill--;
									
								}
								be.start();//���� ����Ʈ ����
								judge_vic();//�¸� ����
								
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
					bef.bombcnt++;//���� �����Ӽ� ����
					switch (bef.bombcnt)//y���� ������Ų��. x���� ������...
					{//���� ������ ���� ���� �󸶳� ��ġ�� ���ϴ��� �����Ѵ�.
					case 1:  atkwav.start(); break;
					case 2:  bef.bomb_state = 1; break;
					case 3:   break;
					case 4:  bef.bomb_state = 2;break;
					case 5:  bef.bomb_state = 3;break;
					case 6:   break;
					case 7:  bef.bomb_state = 4;
					//Hans.state_num = 0;
					bef.bomb_on = false;//���� ��
					bef.bombcnt = 0;//���� �ʱ�ȭ
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

	//��������
	//������ �������� ������ ���� �߰�. ex)������ �������°�
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
							switch (j)//y���� ������Ų��. x���� ������...
							{//���� ������ ���� ���� �󸶳� ��ġ�� ���ϴ��� �����Ѵ�.
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
		//new GameOver().main(null);//��������1�� �ҷ��´�
	}
	
	class stonerolling extends Thread // ��������
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
						{//���� ĳ���Ͱ� �´� �����̴�.
	
							System.out.printf("ang!\n");
							game_over = true; //���� Ȯ��
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
						{//���� ĳ���Ͱ� �´� �����̴�.
	
							System.out.printf("ang!\n");
							game_over = true; //���� Ȯ��
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
	
	class stonerolling1 extends Thread // ��������
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
						{//���� ĳ���Ͱ� �´� �����̴�.
	
							System.out.printf("ang!\n");
							game_over = true; //���� Ȯ��
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
	

	class stonerolling2 extends Thread // ��������
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
						{//���� ĳ���Ͱ� �´� �����̴�.
	
							System.out.printf("ang!\n");
							game_over = true; //���� Ȯ��
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