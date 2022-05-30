package book;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import oracledb.OracleDB;
import user.User;
import util.Util;

public class Reserve {
	
	public void showBookList() {
			
			Connection conn = null;
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			
			//책 목록
			System.out.println("======== 예약 가능 도서 목록 ========");
			String sql = "SELECT * FROM BOOK WHERE BCOUNT > 0";
			
			try {
				conn = OracleDB.getOracleConnection();
				pstmt = conn.prepareStatement(sql);
				rs = pstmt.executeQuery();

				System.out.print("글번호");
				System.out.print(" | ");
				System.out.print("분류번호");
				System.out.print(" | ");
				System.out.print("책이름");
				System.out.print(" | ");
				System.out.print("보유권수");
				System.out.print(" | ");
				System.out.println("\n===============================");
				
				while(rs.next()) {
					
					
					int bno = rs.getInt("BNO");
					int cno = rs.getInt("CNO");
					String bname = rs.getString("BNAME"); 
					int bcount = rs.getInt("BCOUNT");
					
					System.out.print(bno);
					System.out.print(" | ");
					System.out.print(cno);
					System.out.print(" | ");
					System.out.print(bname);
					System.out.print(" | ");
					System.out.print(bcount +"권");
					System.out.println();
					
				}
				
			} catch (SQLException e) {
				System.out.println("도서 목록 조회 오류");
			} finally {
				OracleDB.close(conn);
				OracleDB.close(pstmt);
				OracleDB.close(rs);
			}
			}

		
			//도서 검색-> 예약하기
			public boolean searchBook() {
				
				showBookList();
				
				Connection conn = null;
				PreparedStatement pstmt1 = null;
				PreparedStatement pstmt2 = null;
				ResultSet rs1 = null;
				String sql = null;
				
				System.out.print("책 제목을 입력하시오 : ");
				String bname = Util.sc.nextLine();
				
				try {
					sql = "SELECT BNO FROM BOOK WHERE BNAME = ?";
					
					conn = OracleDB.getOracleConnection();
					pstmt1 = conn.prepareStatement(sql);
					pstmt1.setNString(1,bname);
					rs1 = pstmt1.executeQuery();
					
					
					if(rs1.next()) {
						int bno = rs1.getInt("BNO");
						System.out.println("검색하신 책을 찾았습니다!");
						System.out.println(bname+ " 을(를) 예약하시겠습니까? 1. 예");
						
						
						
						int mno = User.loginNo;
						
						int anser = Util.scInt();
						
						if (anser == 1) {
							
							String reserve = "INSERT INTO RESERVE(RESERVENO,MNO,BNO,RESERVEDATE) VALUES (RESERVE_NO_SEQ.NEXTVAL," + mno +" , "+ bno +", SYSDATE)"; 
							pstmt2 = conn.prepareStatement(reserve);
							int rs2 = pstmt2.executeUpdate();
							
							if (rs2 == 1) { 
							System.out.println("예약이 완료되었습니다.");
							return true;
							}
							
					} else {
						System.out.println("예약이 취소되었습니다.");
						return false;  
					}
					}
					} catch (SQLException e) {
					e.printStackTrace();
				}finally{
					OracleDB.close(conn);
					OracleDB.close(pstmt1);
					OracleDB.close(pstmt2);
					OracleDB.close(rs1);
				}
				
				return false;

			}
} 
