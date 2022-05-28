package main;


//import Person.Member;
import util.Util;

public class Main {
	
	public static void main(String[] args) {
		
		System.out.println(" ANONYMOUS의 서재 "); //여기도... 좋은 의견 부탁드립니다.. 

		
		boolean isTrue = true;
		
		while(isTrue) {
		System.out.println("==================="); //간편하게 == ** ## 등 특수문자 이용가능, 의견있으면 말씀해주세요
		System.out.println("1. 일반사용자");
		System.out.println("2. 관리자포털");
		System.out.println("3. 시스템종료");
		
		int n = Util.scInt();
		
		switch(n) {
		case 1 :  new UserMenu().userLoginMenu(); break;
		case 2 :  break;
		case 3 : isTrue = false; break; 
		default : System.out.println("시스템종료");
		}

		  
		}
		
		
		

	}

}
