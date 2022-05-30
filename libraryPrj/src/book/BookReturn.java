package book;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import oracledb.OracleDB;
import user.User;
import util.Util;


public class BookReturn {

	//도서 반납
	
	//1. 반납할 책번호 받기
	//1-1. 대출 현황 조회
	//2. 책번호, 회원번호가 일치하고, RDATE가 NULL인 데이터가 대출 테이블에 있는지 확인
	//3. 없으면 실패
	//4. 있을때 반납 기한을 넘겼을시 페널티클래스 소환!!!
	//5. 대출 테이블에서 RDATE 갱신
	//6. 책 현재권수, 회원 대출가능권수 + 1
	
	public boolean bReturn() {
		//책번호 입력받기
		int bookInput;
		System.out.println();
		System.out.println("반납할 책의 책번호를 입력하세요. 0 : 대출현황조회");
		bookInput = Util.scInt();
		
		//대출현황조회로 이동
		if (bookInput == 0) {
			MyBook mb = new MyBook();
			mb.MBook();
			
			//다시입력받기
			System.out.println("반납할 책의 책번호를 입력하세요. 0 : 뒤로가기");
			bookInput = Util.scInt();
			
			if(bookInput == 0) {
				return false;
			}
		}
		
		// DB접속
		Connection conn = OracleDB.getOracleConnection();
		
		// 회원번호 받기 !!!!!!!!!!!!!!!!!!!!!!!!!!!
		int mno = User.loginNo;
		
		// 책번호,회원번호가 일치하고 RDATE가 null인 데이터 가져오기
		String sql = "SELECT BWNO FROM BORROW WHERE RDATE IS NULL AND BNO = " + bookInput + "AND MNO = " + mno;
		
		PreparedStatement pstmt1 = null;
		ResultSet rs = null;
		PreparedStatement pstmt2 = null;
		ResultSet drs = null;
		PreparedStatement pstmt3 = null;
		PreparedStatement pstmt4 = null;
		PreparedStatement pstmt5 = null;
		
		try {
			pstmt1 = conn.prepareStatement(sql);
			rs = pstmt1.executeQuery();
			
			if(rs.next()) {

				//반납기한 넘길경우 페널티 호출
				String dsql = "SELECT DEADLINE FROM BORROW WHERE BNO = " + bookInput + "AND MNO = " + mno;
				
				pstmt2 = conn.prepareStatement(dsql);
				drs = pstmt2.executeQuery();
				
				drs.next();
				
				Date deadline = drs.getTimestamp("DEADLINE");
				Date today = new Date();
				
				if (today.after(deadline)) {
					
					//페널티 클래스 호출 !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
					new Penalty().penalty();
								
				} else {
					System.out.println("반납이 가능합니다.");
				}
				
				System.out.println("반납하시겠습니까? 1.예");
				int input = Util.scInt();
				
				if(input != 1) {
					System.out.println("반납을 종료합니다.");
					return false;
				}
				
				//반납진행 - 대출테이블 데이터 수정
				int bwno = rs.getInt("BWNO");
				
				String sqlUpdate = "UPDATE BORROW SET RDATE = SYSDATE WHERE BWNO = " + bwno;
			
				pstmt3 = conn.prepareStatement(sqlUpdate);
				int result = pstmt3.executeUpdate();
			
				if (result == 1) {
					System.out.println("도서 반납 성공!");

					//책 테이블에 현재권수 +1
					String sqlUpdate2 = "UPDATE BOOK SET BCOUNT=BCOUNT+1 WHERE BNO = " + bookInput;
					
					pstmt4 = conn.prepareStatement(sqlUpdate2);
					pstmt4.executeUpdate();
					
					//회원 테이블에 대출가능권수 +1
					String sqlUpdate3 = "UPDATE MEMBER SET BORROWABLE=BORROWABLE+1 WHERE MNO = " + mno;
					
					pstmt5 = conn.prepareStatement(sqlUpdate3);
					pstmt5.executeUpdate();
				}
				
			} else {
					System.out.println();
					System.out.println("반납할 책이 없습니다.");
					return false;
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			OracleDB.close(conn);
			OracleDB.close(rs);
			OracleDB.close(pstmt1);
			OracleDB.close(drs);
			OracleDB.close(pstmt2);
			OracleDB.close(pstmt3);
			OracleDB.close(pstmt4);
			OracleDB.close(pstmt5);
		}
		
		
		return false;
	}
}
