package book;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import oracledb.OracleDB;
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
			public void searchBook() {
				
				showBookList();
				
				Connection conn = null;
				PreparedStatement pstmt1 = null;
				PreparedStatement pstmt2 = null;
				ResultSet rs1 = null;
				ResultSet rs2 = null;
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
						System.out.println(bname+ " 을(를) 예약하시겠습니까? (Y / N)");
						String anser = Util.sc.nextLine();
						
						if (anser.equalsIgnoreCase("Y")) {
							
							String reserve = "INSERT INTO RESERVE(RESERVENO,MNO,BNO,RESERVEDATE) VALUES (RESERVE_NO_SEQ.NEXTVAL, 1, "+ bno +", SYSDATE)"; 
							pstmt2 = conn.prepareStatement(reserve);
							rs2 = pstmt2.executeQuery();
							System.out.println("예약이 완료되었습니다.");
							
					} else if (anser.equalsIgnoreCase("N")) {
						System.out.println("예약이 취소되었습니다.");
					return; 
					}
					}
					} catch (SQLException e) {
					e.printStackTrace();
				}finally{
					OracleDB.close(conn);
					OracleDB.close(pstmt1);
					OracleDB.close(pstmt2);
					OracleDB.close(rs1);
					OracleDB.close(rs2);
				}

			}
} 
