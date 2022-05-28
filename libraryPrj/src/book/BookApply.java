package book;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import oracledb.OracleDB;
import util.Util;


public class BookApply {
	
	// 도서 신청 -- 도서신청 테이블, 도서신청 시퀀스
	
	//1. 신청받을 도서 정보 받기 - 책이름
	//2. 도서신청테이블에 추가
	//(신청번호 시퀀스, 책이름)
	
	public void bApply() {
		//입력받기
		String bookInput;
		System.out.println();
		System.out.println("신청할 책의 제목을 입력하세요.");
		System.out.print(": ");
		bookInput = Util.sc.nextLine().trim();
		
		//도서신청테이블에 추가
		Connection conn = OracleDB.getOracleConnection();
		
		String sqlInsert = "INSERT INTO APPLY(APPNO, BNAME) VALUES(APPLY_NO_SEQ.NEXTVAL, '" + bookInput + "')";
		
		PreparedStatement pstmt = null;
		try {
			pstmt = conn.prepareStatement(sqlInsert);
			int Iresult = pstmt.executeUpdate();
			if(Iresult == 1) {
				System.out.println();
				System.out.println("도서 신청 성공!");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			OracleDB.close(conn);
			OracleDB.close(pstmt);
		}
		
	}
}
