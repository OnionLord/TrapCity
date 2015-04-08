package TrapCity;
import java.util.Random;
import java.util.Date;
import java.text.SimpleDateFormat;

//함정 배치를 위한 랜덤 함수이다.

public class MakeRandom {
	
	public int locate_rand(int trap_w, int start_x)
	{
		Random r= new Random();
	
		return start_x + 20 + (r.nextInt(180 - trap_w))+1;
		
		//함정 구간 시작 위치 + 그 구간 범위 내에서... 기본 200으로 하며 다음 구간과 겹치기 방지를 위해
		//랜덤 함수 범위를 트랩 너비만큼 빼기로 함.
		
		//함정이 화면에서 보이는 위치
		//200 : 함정이 너무 가까이 있는 것을 방지
		//랜덤함수 발생
	}
	
	public int type_rand( int n )
	{
		Random r = new Random();
		
		return r.nextInt(n) + 1;
	}
}
