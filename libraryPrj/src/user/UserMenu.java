package user;

import book.LibraryMenu;
import util.Util;

public class UserMenu {
	
	public UserMenu() {
	}
	
	//로그인및 회원가입 화면========================================================================================
	public void userLoginMenu() {
		boolean istrue = true;
		
		while(istrue) {
			System.out.println("=====사용자 메뉴=====");
			System.out.println("1. 회원가입");
			System.out.println("2. 로그인");
			System.out.println("3. 메인화면으로");
			
			
			int n = Util.scInt();
			
			switch(n) {
			case 1 : 
				new User().join(); break;
			case 2 : 
				boolean loginIsTrue = new User().login(); 
				if(loginIsTrue) userMainMenu();
				break;
			case 3 : istrue = false; break;
			default : System.out.println("다시 선택하세요");
			}
		}//while
	}//userLoginMenu===========================================================================================
	
	//로그인 후 메인화면========================================================================================
	public void userMainMenu() {
		boolean istrue = true;
		
		while(istrue) {
			System.out.println("=====도서관에 오신걸 환영합니다=====");
			System.out.println("1. 개인정보관리");
			System.out.println("2. 도서관이용");
			System.out.println("3. 로그아웃");
			
			boolean isLogout = false;
			
			int n = Util.scInt();
			
			switch(n) {
			case 1 : 
				isLogout = userAlterMenu();  break;
			case 2 : 
				new LibraryMenu().Library(); break;
			case 3 : istrue = false; User.loginNo=0; User.loginPwd=null; User.isLogin = false; break;
			default : System.out.println("다시 선택하세요");
			
			}
			
			if(isLogout) {
				istrue = false;
			}
			
		}//while
	}//userMainMenu==========================================================================================
	
	//개인정보 수정 및 탈퇴화면====================================================================================
	public boolean userAlterMenu() {
		boolean istrue = true;
		
		
		while(istrue) {
			
			boolean islogout = false;
			
			System.out.println("=====개인정보 수정 및 탈퇴=====");
			System.out.println("1. 내정보조회");
			System.out.println("2. 회원탈퇴");
			System.out.println("3. 뒤로");
			
			
			int n = Util.scInt();
			
			switch(n) {
			case 1 : 
				new User().myInfo(); break;
			case 2 : 
				islogout = new User().askUserQuit(); break;
			case 3 : istrue = false; break;
			default : System.out.println("다시 선택하세요");
			
			}
			
			if(islogout) {
				istrue = false;
				return true;
			}
			
		}//while
		
		return false;
		
	}//userAlterMenu============================================================================================
	
	
}//class
