package main;


//import Person.Member;
import util.Util;

public class Main {
	
	public static void main(String[] args) {
		
		System.out.println(" ANONYMOUS의 서재 "); //여기도... 좋은 의견 부탁드립니다.. 
		
		boolean isTrue = true;
		
		while(isTrue) {
		System.out.println("=================="); // 간편하게 == ** ## 등 특수문자 이용해도 되고, 좋은의견 있으면 말씀해주세요~
		System.out.println("1. 일반사용자"); // 순서 바꿔도됨
		System.out.println("2. 관리자포털");
		System.out.println("3. 사용자종료");
		
		int n = Util.scInt();
		
		switch(n) {
		case 1 :  new UserMenu().userLoginMenu(); break;
		case 2 :  break;
		case 3 : isTrue = false; break; 
		default : System.out.println(" 잘못 입력하셨습니다. 다시 입력해 주세요. ");
		}

		
		}
		
		
		

	}

}
