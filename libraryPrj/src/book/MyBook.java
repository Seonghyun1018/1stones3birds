package book;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

import oracledb.OracleDB;


public class MyBook {

	// 대출현황조회목록
	
	// 1. 회원번호 받아오기
	// 2. 대출 테이블 접속해서 회원번호가 일치하고 RDATE가 null인 데이터 확인
	// 3. 출력
	
	public void MBook() {
		
		//회원번호 수정 !!!!!!!!!!!!!!!!
		int mno = 1;
		
		Connection conn = OracleDB.getOracleConnection();
		
		//대출 테이블에서 RDATE IS NULL AND MNO가 일치
		String sql = "SELECT MNO, BNO, DEADLINE, ISEXTEND FROM BORROW WHERE RDATE IS NULL AND MNO = " + mno;
		
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			
			System.out.println("==================================");
			System.out.print("회원번호");
			System.out.print("|");
			System.out.print("책번호");
			System.out.print("|");
			System.out.print("반납기한");
			System.out.print("|");
			System.out.print("연장유무");
			System.out.print("|");
			System.out.println("\n==================================");
			
			while(rs.next()) {
				int memno = rs.getInt("MNO");
				int bno = rs.getInt("BNO");
				Date deadline = rs.getTimestamp("DEADLINE");
				String isextend = rs.getString("ISEXTEND");
				
				SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
				
				System.out.print(memno);
				System.out.print(" | ");
				System.out.print(bno);
				System.out.print(" | ");
				System.out.print(dateFormat.format(deadline));
				System.out.print(" | ");
				System.out.print(isextend);
				System.out.print(" | ");
				System.out.println();
			}
			
			System.out.println("==================================");
			System.out.println("검색을 완료하였습니다.");
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			OracleDB.close(conn);
			OracleDB.close(pstmt);
		}
			
	}
}
