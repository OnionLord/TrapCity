package TrapCity;

import java.awt.*;
import java.awt.event.*;
import java.applet.*;
import javax.swing.*;

import java.awt.image.BufferedImage;
import java.awt.image.BufferStrategy;

public class GameOver{
	public static void main(String ar[])
	{
		frame6 f = new frame6();
	}
	
}

class frame6 extends Frame implements MouseListener,MouseMotionListener,KeyListener
{

	private Image Back_Ground;

	public int mouse_x;
	public int mouse_y;
	public int start_on = 1;
	public int end_on = 0;
	public int stage = 0;

	private SoundPlay bgm1 = new SoundPlay("gamedata\\bgm\\gameover.wav");


	public frame6()
	{
		
		super("G.A.M.E.O.V.E.R.");
		open();
			
		setSize(1000, 700);
		//bgm1.start();
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

		
		
	}
	
	public void open(){
	
		Back_Ground = Toolkit.getDefaultToolkit().getImage("gamedata\\pictures\\maintitle\\gameover.jpg");
		
	}

	public void update ( Graphics g )
	{
		paint(g);
	}
	
	public void paint(Graphics g)
	{

		g.drawImage(Back_Ground, 0, 0, 1000, 700, this );

	}


	public void mouseClicked(MouseEvent e) 
	{
	}
	public void mouseEntered(MouseEvent e) {}
	public void mouseExited(MouseEvent e)  {}
	public void mousePressed(MouseEvent e)  {}
	public void mouseReleased(MouseEvent e) {}
	
	public void mouseDragged(MouseEvent e) {} 
	public void mouseMoved(MouseEvent e) 
	{
		
		
	}

	@Override
	public void keyPressed(KeyEvent e) 
	{
		if (e.getKeyCode() == e.VK_SPACE || e.getKeyCode() == e.VK_ENTER)
		{
			bgm1.stop();
			this.setVisible(false);	
			new Stage1().main(null);//스테이지1을 불러온다

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

	@Override
	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	
	

}