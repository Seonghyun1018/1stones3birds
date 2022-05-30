package book;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import oracledb.OracleDB;
import util.Util;


public class BookSearch {
	
	// 도서 검색
	
	//1. 검색할 단어 또는 책제목 받기 
	//2. 책 테이블에서 대조해서 검색 (%입력값%)
	//3. 정보 받아서 출력
	
	public void bSearch() {
		//입력받기
		String bookInput;
		System.out.println();
		System.out.print("검색할 단어 또는 책제목을 입력하세요. : ");
		bookInput = Util.sc.nextLine().trim();
		
		//책 찾기
		Connection conn = OracleDB.getOracleConnection();
		
		//책번호, 책이름, 현재권수 받아오기
		String sql = "SELECT BNO, BNAME, BCOUNT FROM BOOK WHERE BNAME LIKE '%" + bookInput + "%'";
		
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			
				System.out.println("\n==================================");
				System.out.print("책번호");
				System.out.print("|");
				System.out.print("책이름      ");
				System.out.print("|");
				System.out.print("재고");
				System.out.print("|");
				System.out.println("\n==================================");
				
				//출력
				while(rs.next()) {
					int bno = rs.getInt("BNO");
					String bname = rs.getString("BNAME");
					int bcount = rs.getInt("BCOUNT");
					
					System.out.print(bno);
					System.out.print(" | ");
					System.out.print(bname);
					System.out.print(" | ");
					System.out.print(bcount + "권");
					System.out.print(" | ");
					System.out.println();
				}
				
				System.out.println("검색을 완료하였습니다.");
				
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			OracleDB.close(conn);
			OracleDB.close(pstmt);
			OracleDB.close(rs);
		}
		
	}
}
