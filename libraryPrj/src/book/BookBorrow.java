package book;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;

import count.Count;
import oracledb.OracleDB;
import user.User;
import util.Util;


public class BookBorrow {
	public static int loginNo;
	Count ct = new Count();
	
	public void bookCheck() {
		Connection conn = OracleDB.getOracleConnection();
		String sql = "SELECT CANTBORROW, BORROWABLE FROM MEMBER WHERE MNO = ?";
		
		try {
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, User.loginNo);
			ResultSet rs = pstmt.executeQuery();
			
			if(rs.next()) {
				Timestamp cantborrow = rs.getTimestamp("CANTBORROW");
				int borrowable =rs.getInt("BORROWABLE");
				if(cantborrow!=null || borrowable == 0) {
					System.out.println("현재 대출이 불가능한 상태 입니다");
				}else {
				System.out.println("현재 대출 가능한 상태 입니다");
				bBorrow();
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	// 도서 대출 -- 대출 테이블, 대출 시퀀스 테이블 작성
	
	//0. 대출가능여부 클래스 소환해서 대출 가능한지 확인!!!
	
	//1. 대출할 책 번호 받기 ----- 책번호 모르면 뒤로 돌아가서 도서검색하기
	//2. 책테이블에서 해당 책 찾기
	//3. 없으면 실패
	//3-1. 같은 책을 대출 중일 경우 실패
	//4. 책의 현재권수 확인
	//5. 0개일 경우 대출 불가
	//6. 아닐시 대출
		//대출 테이블에 데이터 추가
		//(1 대출번호부여 시퀀스, 2 회원번호 받아오기, 3 책번호 받아오기, 
		// 4 대출일자 5 반납일자 6 기한 7 연장유무 기본값)
		// 책 현재 권수, 회원 대출가능권수 -1
	
	public boolean bBorrow() {
		
		//대출할 책 번호 입력받기
		int bookInput;
		System.out.println();
		System.out.println("대출할 책의 책번호를 입력하세요. 0 : 뒤로가기");
		bookInput = Util.scInt();
		
		if (bookInput == 0) {
			return false;
		}
		
		//DB접속
		Connection conn = OracleDB.getOracleConnection();
		
		//책번호, 책이름, 현재권수 받아오기
		String sql = "SELECT BNO, BNAME, BCOUNT FROM BOOK WHERE BNO = " + bookInput;
		
		PreparedStatement pstmt1 = null;
		ResultSet rs = null;
		PreparedStatement pstmt2 = null;
		ResultSet brs = null;
		PreparedStatement pstmt3 = null;
		PreparedStatement pstmt4 = null;
		PreparedStatement pstmt5 = null;
		
		try {
			pstmt1 = conn.prepareStatement(sql);
			rs = pstmt1.executeQuery();
			
			boolean check = rs.next();
			
			// 책 없으면 실패
			if(!check) {
				System.out.println();
				System.out.println("해당하는 책이 없습니다.");
				return false;
				
			} else {
				//대출 테이블에 중복 책 조회
				int mno = User.loginNo; 				//회원번호 수정!!!!!!!!!!!!!!!!!!!!!!!!!
				
				String bsql = "SELECT RDATE FROM BORROW WHERE BNO = " + bookInput + "AND MNO = " + mno;
				
				pstmt2 = conn.prepareStatement(bsql);
				brs = pstmt2.executeQuery();
				
				while (brs.next()) {
				
					Date rdate = brs.getTimestamp("RDATE");
				
					if (rdate == null) {
						System.out.println("이미 대출한 책입니다.");
						return false;
					}
				}
				
				//책 출력
				int bcount = rs.getInt("BCOUNT");
				int bno = rs.getInt("BNO");
				String bname = rs.getString("BNAME");
				
				System.out.println("==================================");
				System.out.println("책제목 : " + bname);
				System.out.println("==================================");
				
				// 현재 권수 확인 - 0개일경우 대출불가
				if (bcount == 0) {
					System.out.println("모든 책이 대출중입니다.");
					System.out.println();
					
				} else {
					System.out.println("대출가능한 책이 있습니다.");
					System.out.println();
					System.out.println("책을 빌리시겠습니까? 1.예");
					
				int input = Util.scInt();
				
				if (input != 1) {
					return false;
				}
				
				// 대출 진행 - 대출 테이블에 데이터 추가

				//회원 번호 확인하기!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
				
				String sqlInsert = "INSERT INTO BORROW(BWNO, MNO, BNO) VALUES(BORROW_NO_SEQ.NEXTVAL,?,?)";
				
				pstmt3 = conn.prepareStatement(sqlInsert);
				pstmt3.setInt(1, mno);
				pstmt3.setInt(2, bno);
				int Iresult = pstmt3.executeUpdate();
				
				if(Iresult == 1) {
					System.out.println("도서 대출 성공!");
					
					//책 테이블에 현재권수 -1
					String sqlUpdate = "UPDATE BOOK SET BCOUNT=BCOUNT-1 WHERE BNO = " + bno;
					
					pstmt4 = conn.prepareStatement(sqlUpdate);
					pstmt4.executeUpdate();
									
					//회원 테이블에 대출가능권수 -1
					String sqlUpdate2 = "UPDATE MEMBER SET BORROWABLE=BORROWABLE-1 WHERE MNO = " + mno;
					
					pstmt5 = conn.prepareStatement(sqlUpdate2);
					pstmt5.executeUpdate();
					
					ct.countTest(bno);
					ct.memberCount(User.loginNo);
					//
					//대출 시 읽은수 추가
					
					} 
				}	
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			OracleDB.close(conn);
			OracleDB.close(rs);
			OracleDB.close(pstmt1);
			OracleDB.close(brs);
			OracleDB.close(pstmt2);
			OracleDB.close(pstmt3);
			OracleDB.close(pstmt4);
			OracleDB.close(pstmt5);
		}
		
		return false;
	}

}
