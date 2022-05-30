package book;

import review.Review;
import util.Util;

public class LibraryMenu {
	
	public void Library() {
		
		boolean istrue = true;
		
		while(istrue) {
			System.out.println("=====원하시는 서비스를 입력해주세요=====");
			System.out.println("0. 돌아가기");
			System.out.println("1. 도서 검색");
			System.out.println("2. 도서 대출");
			System.out.println("3. 도서 반납");
			System.out.println("4. 대출 연장");
			System.out.println("5. 도서 예약");
			System.out.println("6. 도서 신청");
			System.out.println("7.이달의 독서순위");
			System.out.println("8. 도서 후기 작성");
			
			
			boolean isLogout = false;
			
			int n = Util.scInt();
			
			switch(n) {
			case 0 : break;
			case 1 : new BookSearch().bSearch(); break;
			case 2 : new BookBorrow().bBorrow(); break;
			case 3 : new BookReturn().bReturn(); break;
			case 4 : new Extend().isExtend(); break;
			case 5 : new Reserve().searchBook(); break;
			case 6 : new BookApply().bApply(); break;
			case 7 : new Review().bestBook(); break;
			case 8 : new Review().reviewList(); break;
			
			default : System.out.println("다시 선택하세요");
			
			}
			
			if(isLogout) {
				istrue = false;
			}
		
		
	}
	}
}
