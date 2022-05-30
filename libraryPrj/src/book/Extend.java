package book;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import oracledb.OracleDB;
import user.User;
import util.Util;

public class Extend {

	//대출 연장
	public void isExtend() {
		
		System.out.println("대출 기한을 연장할 책의 번호를 입력해주세요.");
		
		int bno = Util.sc.nextInt();
		int mno = User.loginNo; /////회원번호 수정!!!!!!!
				
		Connection conn = null; 
		PreparedStatement pstmt = null;
		ResultSet rs = null;//  -> 쿼리 오류뜨면 주석처리
		PreparedStatement pstmt2 = null;
		ResultSet brs = null;
		
	try {
		conn = OracleDB.getOracleConnection();
		
		String bsql = "SELECT BWNO FROM BORROW WHERE BNO = " + bno + " AND MNO = " + mno;
		
        pstmt2 = conn.prepareStatement(bsql);
        brs = pstmt2.executeQuery();
        
       if(brs.next()) {
		
		System.out.println("대출 기한을 연장하시겠습니까? (Y / N)"); 
		String anser = Util.sc.nextLine();
        
		
		if (anser.equalsIgnoreCase("Y")) {
			
			int bwno = brs.getInt("BWNO");
			
			//연장 유무 칼럼 (ISEXTEND) 'Y'로 바꿔주기 (UPDATE)
			//대출 테이블 기한(DEADLINE)에 TIMESTAMP +7
			// !!!!!  테이블명수정
	        String sql = "UPDATE BORROW SET ISEXTEND = 'Y' , DEADLINE = DEADLINE+7 WHERE BWNO = " + bwno; 
	        pstmt = conn.prepareStatement(sql);
	        rs = pstmt.executeQuery();
	       //int i = pstmt.executeUpdate();
        
		System.out.println("도서 대출 기한이 연장되었습니다.");
		
		}else if(anser.equalsIgnoreCase("N")){
			
			System.out.println("대출 기한 연장을 취소하였습니다");
			
		}else {
			
			System.out.println("잘못 입력하셨습니다.");
		}
       } else {
    	   System.out.println("연장할 책이 없습니다.");
       }
		
		} catch (SQLException e) {
			System.out.println("SQL 오류 발생");
		} finally {
			OracleDB.close(conn);
			OracleDB.close(pstmt);
			OracleDB.close(rs);
		}
		}
		
	
}
