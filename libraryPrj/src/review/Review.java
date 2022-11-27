package review;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import book.BookApply;
import book.BookBorrow;
import book.BookReturn;
import book.BookSearch;
import book.Extend;
import book.Reserve;
import user.User;
import util.Util;


public class Review {

	public void reviewList() {
		System.out.println("============ 후기==============");
		System.out.println("1. 후기 작성");
		System.out.println("2. 후기 보기");
		
		int n = Util.scInt();
		
		switch(n) {
		case 0 : break;
		case 1 : reviewWrite(); break;
		case 2 :  revieList(); break;
		
		
		default : System.out.println("다시 선택하세요");
	}
	}
	
public void bestBook() {
		
		Connection conn = oracledb.OracleDB.getOracleConnection();
		String sql2 ="SELECT BNAME,BNO,RENT_COUNT,	DENSE_RANK() OVER (ORDER BY RENT_COUNT DESC) DENSE_RANK FROM BOOK";
	//	SELECT BNAME,RENT_COUNT,	DENSE_RANK() OVER (ORDER BY RENT_COUNT DESC) DENSE_RANK FROM BOOK
		System.out.print("책이름");
		System.out.print("  |  ");
		System.out.print("읽힌수");
		System.out.print("  |  ");
		System.out.println("순위");
		
		
		try {
			PreparedStatement pstmt = conn.prepareStatement(sql2);
			
			
			ResultSet rs = pstmt.executeQuery();

			while(rs.next()) {
				
				String bookName = rs.getString("BNAME");
				int rentCount = rs.getInt("RENT_COUNT");
				int rank = rs.getInt("DENSE_RANK");
			  
			 
				System.out.print(bookName);
				System.out.print("  |  ");
				System.out.print(rentCount);
				System.out.print("  |  ");
				System.out.println(rank);
				
				
			
				
				
			}
		} catch (SQLException e) {
		
		}
	}
	public void reviewWrite() {
		System.out.println("후기 제목을 입력해주세요");
	String title = util.Util.sc.nextLine();
	System.out.println("후기 내용을 입력 해주세요");
	String content = util.Util.sc.nextLine();
	Connection conn = oracledb.OracleDB.getOracleConnection();
	String sql = " INSERT INTO REVIEW(RVNO,BNO,MNO,R_TITLE,REVIEWTEXT) VALUES(REVIEW_NO_SEQ.NEXTVAL,1,?,?,?)";
	
	try {
		PreparedStatement pstmt =conn.prepareStatement(sql);
		
		pstmt.setInt(1, User.loginNo);
		pstmt.setString(2, title);
		pstmt.setString(3, content);
		int result = pstmt.executeUpdate();
		if(result == 1) {
			System.out.println("후기가 작성 되었습니다");
		}else {
			 System.out.println("후기작성이 실패 되었습니다");
		}
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
}
	public void revieList() {
		System.out.println("=========리뷰 목록 =========");
		
		Connection conn = oracledb.OracleDB.getOracleConnection();
		String sql = "SELECT RVNO, R_TITLE FROM REVIEW";
		try {
			PreparedStatement pstmt = conn.prepareStatement(sql);
			
		ResultSet	rs = pstmt.executeQuery();
		System.out.print("리뷰 번호");
		System.out.print("  |  ");
		System.out.print("리뷰 제목");
		System.out.println("  |  ");
		
		while(rs.next()) {
			int rno = rs.getInt("RVNO");
			String title = rs.getString("R_TITLE");
			System.out.print(rno);
			System.out.print("        |");
			System.out.print(title);
			System.out.println("  | ");
		}
		reviewShow();
		
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public void reviewShow() {
		System.out.println("조회하실 리뷰 번호를 입력해주세요");
		int no = util.Util.scInt();
		Connection conn = oracledb.OracleDB.getOracleConnection();
		String sql = "SELECT * FROM REVIEW WHERE  RVNO = ?";
		try {
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1,no);
			ResultSet rs = pstmt.executeQuery();
			
			if(rs.next()) {
				String title = rs.getString("R_TITLE");
				String content = rs.getString("REVIEWTEXT");
				
				System.out.println(" 리뷰제목 : " + title);
				System.out.println(" 리뷰 내용 : " + content);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
public void bestKing() {
		
		Connection conn = oracledb.OracleDB.getOracleConnection();
		String sql2 ="SELECT MNAME,MCOUNT,	DENSE_RANK() OVER (ORDER BY MCOUNT DESC) DENSE_RANK FROM MEMBER";
	//	SELECT BNAME,RENT_COUNT,	DENSE_RANK() OVER (ORDER BY RENT_COUNT DESC) DENSE_RANK FROM BOOK
		System.out.print("이름");
		System.out.print("  |  ");
		System.out.print("독서수");
		System.out.print("  |  ");
		System.out.println("순위");
		
		
		try {
			PreparedStatement pstmt = conn.prepareStatement(sql2);
			
			
			ResultSet rs = pstmt.executeQuery();

			while(rs.next()) {
				
				String MName = rs.getString("MNAME");
				int MCount = rs.getInt("MCOUNT");
				int rank = rs.getInt("DENSE_RANK");
			  
			 
				System.out.print(MName);
				System.out.print("  |  ");
				System.out.print(MCount);
				System.out.print("  |  ");
				System.out.println(rank);
				
				
			
				
				
			}
		} catch (SQLException e) {
		
		}
	}
	
	
}