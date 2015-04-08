package TrapCity;

//스테이지 메인 메뉴다.
//본 게임에 비해서 너무 너무 단순하므로
//객체화는 하지 않고 더러운 채로 냅두었다.

import java.awt.*;
import java.awt.event.*;
import java.applet.*;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.awt.image.BufferStrategy;

public class GameMain{
	public static void main(String ar[])
	{
		frame f = new frame();
	}
	
}

class frame extends Frame implements MouseListener,MouseMotionListener,KeyListener
{

	private Image Back_Ground;
	private Image start;
	private Image howto;
	private Image end;
	private Image gui;
	
	public BufferStrategy strategy;
	public boolean onst = true;
	public boolean oncr = false;
	public boolean onen = false;
	public boolean viewcre = false;
	


	public frame()
	{
		
		super("TrapCity Ver 0.01");
		open();
			
		setSize(995, 700);

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
		createBufferStrategy(2);
		strategy = getBufferStrategy();
		
	}
	
	public void open(){
	
		Back_Ground = Toolkit.getDefaultToolkit().getImage("gamedata\\pictures\\maintitle\\title1.jpg");
		howto = Toolkit.getDefaultToolkit().getImage("gamedata\\pictures\\maintitle\\title2.jpg");
		end = Toolkit.getDefaultToolkit().getImage("gamedata\\pictures\\maintitle\\title3.jpg");
		gui = Toolkit.getDefaultToolkit().getImage("gamedata\\pictures\\maintitle\\guide.jpg");


	}

	public void update ( Graphics g )
	{
		paint(g);
	}
	
	public void paint(Graphics g)
	{
		g = strategy.getDrawGraphics();
		g.drawImage(Back_Ground, 0, 20, 1000, 700, this );
		if ( oncr )
		{
			g.drawImage(howto, 0, 20, 1000, 700, this );
		}
		if ( onen )
		{
			g.drawImage(end, 0, 20, 1000, 700, this );
		}
		if(viewcre)
		{
			g.drawImage(gui, 0, 20, 1000, 700, this );
		}
		strategy.show();

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
		JPanel panel = new JPanel();
				SoundPlay soundeq1= new SoundPlay("gamedata\\sound\\start.wav");
				SoundPlay soundeq2= new SoundPlay("gamedata\\sound\\end.wav");
				
		if ( e.getKeyCode() == e.VK_DOWN && viewcre == false)
		{
			if ( onst)
			{
				onst = false;
				oncr = true;
				onen = false;
			}
			else if ( oncr)
			{
				oncr = false;
				onen = true;
				onst = false;
			}
		}
		if ( e.getKeyCode() == e.VK_UP && viewcre == false)
		{
			if ( oncr)
			{
				onst = true;
				oncr = false;
				onen = false;
			}
			else if ( onen)
			{
				oncr = true;
				onen = false;
				onst = false;
			}
		}
		if ( e.getKeyCode() == e.VK_SPACE && viewcre == false)
		{
			if ( onst)
			{
				soundeq1.start();
				this.setVisible(false);
				new Stage1().main(null);//스테이지1을 불러온다
			}
			else if (oncr)
			{
				viewcre = true;
			}
			else if (onen)
			{
				soundeq2.start();
				JOptionPane.showMessageDialog(panel, "종료합니다", "END", JOptionPane.WARNING_MESSAGE);
				System.exit(0);
			}
		}
		if (e.getKeyCode() == e.VK_ENTER  && viewcre == true)
		{
			soundeq1.start();
			this.setVisible(false);
			new Stage1().main(null);//스테이지1을 불러온다
		}
	
		repaint();
	
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
