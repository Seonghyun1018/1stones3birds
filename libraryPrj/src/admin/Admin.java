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
	//도서 정보 조회
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
	

		adminMenu();
		
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
		     System.out.println(" 3 -  뒤로 기기"); //어드민한테만 액세스 권한 가짐 일반회원 X  
		     System.out.println("-----------------------------------------\n");
		     
		     int n = Util.scInt();
				
				//케이스 추가할 거 있으면 case 4,5,6..... 클래스명().메소명(); break; 추가
				
				switch(n) {
				case 1 :  break;
				case 2 :  break;
				case 3 :  break; 
				default : System.out.println("시스템종료");
				}
		     
				bookInsert();
}	     
}	     


 
    
    //회원 정보 조회 User().myInfo().
    // 회원 삭제
	public void deleteMember() {
		
		boolean istrue = true;
		
		while(istrue) {
			System.out.println("=====회원 삭제=====");
			System.out.print("정말 삭제하시겠습니까?");
			System.out.print("1. 예 ");
			System.out.print("2. 아니오 ");
			
			int n = Util.scInt();
			
			switch(n) {
			case 1 : new User().myInfo(); break;
			case 2 : istrue = false; break;
			default : System.out.println("잘못 입력하셨습니다. 다시 입력해주세요.");
			
			
		}
	}
}           	
       
         //도서 상태 INSERT DELETE UPDATE
        // 1. 도서 추가
      	public void bookInsert() {
       		Connection conn = OracleDB.getOracleConnection();
        	// INSERT 
       		String sql
    		= "INSERT INTO BOOK(BNO, CNO, BNAME, BCOUNT,BUYDATE,RENT_COUNT) "
    				     + "VALUES(BOOK_PK_SEQ.NEXTVAL, ?, ?, ?, BUYDATE(?, 'RRRR/MM/DD'), ?)";
    		
    		PreparedStatement pstmt = null;
    		ResultSet rs = null;
    		
    		try {
    			PreparedStatement pstmt2 = conn.prepareStatement(sql);
    			pstmt2.setString(1, "BNO");
    			pstmt2.setString(2, "BCOUNT");
    			pstmt2.setString(3, "BUYDATE");
    			pstmt2.setString(4, "RENT_COUNT");
    			int result = pstmt2.executeUpdate();
    			
    			
    		} catch (SQLException e) {
    			e.printStackTrace();
    		} finally {
    			close(pstmt);
    			close(rs);
    			close(conn);
    		}
    		
    		
    	}



       	
       	



		private void close(Connection conn) {
			
		}



		private void close(ResultSet rs) {
			
			
		}



		private void close(PreparedStatement pstmt) {
			
			
		}



		// 3. 도서 정보 삭제
       	public void bookDelete() {
       		Connection conn = OracleDB.getOracleConnection();
        	// DELETE
        	String sql = "INSERT ANO, APWD FROM ADMIN WHERE AID = ?";
       		
       		
       		
       		
       	}

     
//       	
//       	// 4. 도서 조회
//       	public void bookInfo() {
//       		Connection conn = getConnection();
//       		
//       		Book book = bd.bookSelectId(conn, bookId);
//       		
//       		close(conn);
//       		
//       		return book;
//       	}
//       	
      
    	
  
	
	}
	
      
	
  
//class

 
 


           
            

            
                            



	
	
	
	
