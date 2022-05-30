package admin;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import oracledb.OracleDB;
import user.User;
import util.Util;

public class Admin{
	
	public static int loginAdmNo;
	public static String loginAdmPwd;
	public static boolean isLogin;
	
	AdminAccess aa = new AdminAccess();
	
	//도서관 관리자
	
	//관리자 기능 
	
	//책 추가하기
	//회원 삭제?..
	//도서 상태 갱신?
	
	 //if ADMIN / 나관리 이라면
	 
	 //if LIBRARIAN / 김사서 라면
	 
	 //if PARTTIMER / 원용직 이라면

	
	public void admLogin() {
//		AdminAccess aa = new AdminAccess();
		System.out.println("=====관리자 로그인=====");
		System.out.print("아이디 : ");
		String aid = Util.sc.nextLine();
		System.out.print("패스워드 : ");
		String apwd = Util.sc.nextLine();
		
		// 데이터베이스 연결 
    	Connection conn = OracleDB.getOracleConnection();
    	// ADMIN 계정 확인
    	String sql = "SELECT ANO, APWD FROM ADMIN WHERE AID =  ? ";
    					
	    // 전송
		PreparedStatement pstmt = null;
		// 결과
		ResultSet rs = null;
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, aid);
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				String dbPwd = rs.getString("APWD");
				int no = rs.getInt("ANO");
				if(dbPwd.equalsIgnoreCase(apwd)) {
					loginAdmNo = no;
					loginAdmPwd = dbPwd;
					System.out.println(" ****접속 완료**** ");
					//isLogin = true;
					
					adminMenu();
					
				}
			}
		} catch (SQLException e) {
			System.out.println(" SQL 예외 발생 ");
			e.printStackTrace();
			System.out.println(" ----접속 실패---- ");
		}finally {
			OracleDB.close(conn);
			OracleDB.close(pstmt);
			OracleDB.close(rs);
		}
	


		
}
	
	
	public void adminMenu() {	
		
		boolean isTrue = true;
		
		// 1. 관리자 로그인 하기 while + switch
		
		while(isTrue){
			 System.out.println("--------------------------------------------------------");
		     System.out.println("\t 관리자 시스템 시작");
		     System.out.println("--------------------------------------------------------");
		     System.out.println("원하는 번호를 입력해주세요. : \n");
		     System.out.println(" 1 -  도서 추가");
		     System.out.println(" 2 -  회원 삭제");
//		     System.out.println(" 3 -  뒤로 기기"); //어드민한테만 액세스 권한 가짐 일반회원 X  
		     System.out.println("-----------------------------------------\n");
		     
		     int n = Util.scInt();
				
				//케이스 추가할 거 있으면 case 4,5,6..... 클래스명().메소명(); break; 추가
				
				switch(n) {
				case 1 : bookInsert(); break;
				case 2 : deleteMember(); break;
//				case 3 : break; 
				default : System.out.println("시스템종료");
				
				}
				
		}	     
}	     


 
    
    //회원 정보 조회 User().myInfo().
    // 회원 삭제
	public void deleteMember() {
		
		//삭제할 회원의 아이디 입력받기
		System.out.println("삭제할 회원의 아이디를 입력해주세요.");
		String id = Util.sc.nextLine();
		
		Connection conn = OracleDB.getOracleConnection();
		
		String sql = "SELECT MNO FROM MEMBER WHERE MID = " + id;
		
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		PreparedStatement pstmt2 = null;
		
		try {
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				
				boolean istrue = true;
				
				while(istrue) {
					System.out.println("=====회원 삭제=====");
					System.out.print("정말 삭제하시겠습니까?");
					System.out.print("1. 예 ");
					System.out.print("2. 아니오 ");
					
					int n = Util.scInt();
					
					if(n == 1) {
						
						int mno = rs.getInt("MNO");
						
						String dsql = "DELETE FROM MEMBER WHERE MNO = " + mno;
						
						pstmt2 = conn.prepareStatement(sql);
						int i = pstmt.executeUpdate();
						
						if(i == 1) {
							System.out.println(id + " 회원이 삭제되었습니다.");
						}
				 		else {
						System.out.println("회원 삭제를 취소하셨습니다.");
				 		}	
					}		
				
				}		
			}
			
				}catch (SQLException e) {
				System.out.println(" SQL 예외 발생 ");
				e.printStackTrace();
				System.out.println(" ----접속 실패---- ");
				}finally {
				OracleDB.close(conn);
				OracleDB.close(pstmt);
				OracleDB.close(rs);
				}
		
}		
           	
       
         //도서 상태 INSERT DELETE UPDATE
        // 1. 도서 추가
      	public void bookInsert() {
      		
       		Connection conn = OracleDB.getOracleConnection();
       		
       		// 추가할 도서 입력
       		System.out.println("추가할 도서의 제목을 입력하세요.");
       		String bname = Util.sc.nextLine();
       		
       		System.out.println("도서의 장르 번호를 입력하세요.");
       		int cno = Util.scInt();
       		
       		
        	// INSERT 
//       		String sql = "INSERT INTO BOOK(BNO, CNO, BNAME) VALUES(999 ,"+ cno +"," + bname + ")";
       		String sql = "INSERT INTO BOOK(BNO, CNO, BNAME) VALUES(BOOK_PK_SEQ.NEXTVAL,"+ cno +"," + bname + ")";
    		PreparedStatement pstmt = null;
    		
    		try {
    			pstmt= conn.prepareStatement(sql);
    			int i = pstmt.executeUpdate();
    			
    			//출력하기
    			if(i == 1) {
					System.out.print(cno);
					System.out.print(" | ");
					System.out.print(bname);
					System.out.print(" | ");
					System.out.println();
					System.out.println("장르번호 : "+ cno + "책제목 : " +bname + "추가가 완료 되었습니다.");
    			} else {
    				System.out.println("도서 추가를 실패하셨습니다.");
    			}
				
    		} catch (SQLException e) {
    			e.printStackTrace();
    		} finally {
    			OracleDB.close(pstmt);
    			OracleDB.close(conn);
    		}
    		
    		
    	}

	}

	
	
