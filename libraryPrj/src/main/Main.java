package main;


import admin.Admin;
import user.UserMenu;
import util.Util;

public class Main {
	
	public static void main(String[] args) {
		
		System.out.println(" 원용의 서재 "); //여기도... 좋은 의견 부탁드립니다.. 

		
		boolean isTrue = true;
		
		while(isTrue) {
		System.out.println("==================="); //간편하게 == ** ## 등 특수문자 이용가능, 의견있으면 말씀해주세요
		System.out.println("1. 일반사용자");
		System.out.println("2. 관리자포털");
		System.out.println("3. 시스템종료");
		
		int n = Util.scInt();
		
		//케이스 추가할 거 있으면 case 4,5,6..... 클래스명().메소명(); break; 추가
		
		switch(n) {
		case 1 :  new UserMenu().userLoginMenu(); break;
		case 2 :  new Admin().admLogin();break;
		case 3 : isTrue = false; break; 
		default : System.out.println("시스템종료");
		}

		  
		}
		
		
		

	}

}
