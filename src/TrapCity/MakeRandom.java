package TrapCity;
import java.util.Random;
import java.util.Date;
import java.text.SimpleDateFormat;

//���� ��ġ�� ���� ���� �Լ��̴�.

public class MakeRandom {
	
	public int locate_rand(int trap_w, int start_x)
	{
		Random r= new Random();
	
		return start_x + 20 + (r.nextInt(180 - trap_w))+1;
		
		//���� ���� ���� ��ġ + �� ���� ���� ������... �⺻ 200���� �ϸ� ���� ������ ��ġ�� ������ ����
		//���� �Լ� ������ Ʈ�� �ʺ�ŭ ����� ��.
		
		//������ ȭ�鿡�� ���̴� ��ġ
		//200 : ������ �ʹ� ������ �ִ� ���� ����
		//�����Լ� �߻�
	}
	
	public int type_rand( int n )
	{
		Random r = new Random();
		
		return r.nextInt(n) + 1;
	}
}
