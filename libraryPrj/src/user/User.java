package user;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.regex.Pattern;

import oracledb.OracleDB;
import util.Util;

public class User {
	
	public static int loginNo;
	public static String loginPwd;
	public static boolean isLogin;
	
	//로그인==============================================================================================
	public boolean login() {
		System.out.println("=====로그인=====");
		System.out.print("아이디 : ");
		String id = Util.sc.nextLine();
		System.out.print("패스워드 : ");
		String pwd = Util.sc.nextLine();
		
		//디비 연결 
		Connection conn = OracleDB.getOracleConnection();
		//아이디에 맞는 패스워드 디비에서 조회
		
		//관리자 화면 로그인도 할 수도 있음 컬럼명을 if로 나누기 처리하기
		
		String sql = "SELECT MNO, MPWD FROM MEMBER WHERE MID = ? AND QUIT != 'Y'";
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, id);
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				String dbPwd = rs.getString("MPWD"); 
				int no = rs.getInt("MNO");			
				if(dbPwd.equalsIgnoreCase(pwd)) {
					//로그인 성공
					loginNo = no;
					loginPwd = dbPwd;
					System.out.println("로그인 성공 !!!");
					isLogin = true;
					return true;
				}
			}
		} catch (SQLException e) {
			System.out.println("SQL 예외 발생 !!!");
		}finally {
			OracleDB.close(conn);
			OracleDB.close(pstmt);
			OracleDB.close(rs);
		}
		
		System.out.println("로그인 실패 ...");
		return false;
	}//로그인==============================================================================================
	
	
	//회원가입==============================================================================================
	/*
	 * 아이디, 비번, 닉네임 입력받기
	 * 아이디 중복 검사
	 * 비밀번호 유효성 검사 (대소문자구분되는 영문 숫자 혼합 6~16문자)
	 * 디비에 저장
	 */
	public boolean join() {
		System.out.println("=====회원가입=====");
		String id = null;
		String pwd = null;
		String name = null;
		String phone = null;
		
		System.out.println("_를 사용가능한 영문 숫자 혼합 4~10문자");
		System.out.print("아이디 : ");
		id = Util.sc.nextLine();
		
		System.out.println("대소문자구분되는 영문 숫자 혼합 6~16문자");
		System.out.print("비밀번호 : ");
		pwd = Util.sc.nextLine();
		
		System.out.print("이름 : ");
		name = Util.sc.nextLine();
		
		System.out.print("전화번호 : ");
		phone = Util.sc.nextLine();
		
		String patternId = "^\\w{4,10}$";
		String patternPwd = "^[0-9a-zA-Z]{6,16}$";
		String patternName = "^[a-zA-Z가-힣]+$";
		String patternPhone = "^\\d{2,3}\\d{3,4}\\d{4}$";
		
		//아이디 유효성 검사
		if(!Pattern.matches(patternId, id)) {
			// 회원가입 실패
			System.out.println("아이디는 _를 포함한 영문 숫자 혼합 4~10문자 입니다.");
			return false;
		}
		
		//비번 유효성 검사
		if(!Pattern.matches(patternPwd, pwd)) {
			// 회원가입 실패
			System.out.println("비밀번호는 4글자 이상 이어야 합니다.");
			return false;
		}
		
		//이름 유효성 검사
		if(!Pattern.matches(patternName, name)) {
			// 회원가입 실패
			System.out.println("이름을 제대로 입력해 주세요(영어, 한글만 가능)");
			return false;
		}
		
		if(!Pattern.matches(patternPhone, phone)) {
			// 회원가입 실패
			System.out.println("올바른 전화번호 형식이 아닙니다.");
			return false;
		}
		
		//아이디 중복 검사
		//1. 디비 접속
		Connection conn = OracleDB.getOracleConnection();
		//2. 디비에서 현재 아이디와 일치하는 아이디 조회
		try {
			String sql = "SELECT * FROM MEMBER WHERE MID = ?";
//			Statement stmtDup = conn.createStatement();
//			ResultSet rs = stmtDup.executeQuery(sql);
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, id);
			ResultSet rs = pstmt.executeQuery();
			
			if(rs.next()) {
				//중복된 아이디.. 회원가입 실패
				System.out.println("아이디 중복됨");
				return false;
			}
			
			//디비에 데이터 저장
			//3. 회원가입성공? 디비 저장
			String sqlInsert 
				= "INSERT INTO MEMBER(MNO,MNAME,MID,MPWD,PHONE) "
						+ "VALUES(MEMBER_PK_SEQ.NEXTVAL,?,?,?,?)";
//			Statement stmtInsert = conn.createStatement();
//			int result = stmtInsert.executeUpdate(sqlInsert);
			PreparedStatement pstmt2 = conn.prepareStatement(sqlInsert);
			pstmt2.setString(1, name);
			pstmt2.setString(2, id);
			pstmt2.setString(3, pwd);
			pstmt2.setString(4, phone);
			int result = pstmt2.executeUpdate();
			
			if(result == 1) {
				System.out.println("회원 가입 성공");
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		System.out.println("회원가입 실패");
		return false;
	}//회원가입==============================================================================================
	
	
	//개인정보 조회==============================================================================================
	public void myInfo() {
		System.out.println("=====개인정보=====");
			
		//디비 연결 
		Connection conn = OracleDB.getOracleConnection();
		//아이디에 맞는 패스워드 디비에서 조회
		
		//관리자 화면 로그인도 할 수도 있음 컬럼명을 if로 나누기 처리하기
		
		String sql = "SELECT * FROM MEMBER WHERE MNO = ?";
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, loginNo);
			rs = pstmt.executeQuery();
			rs.next();
		
			String dbName = rs.getString("MNAME");
			String dbId = rs.getString("MID");
			String dbPwd = "********";
			Timestamp dbInDate = rs.getTimestamp("MDATE");
			String dbPhone = rs.getString("PHONE");
			String dbCantBorrow = rs.getString("CANTBORROW");
			int dbBorrowable = rs.getInt("BORROWABLE");
			int dbReadCount = rs.getInt("MCOUNT");
			
			System.out.print("회원명 : ");
			System.out.println(dbName);
			System.out.print("아이디 : ");
			System.out.println(dbId);
			System.out.print("비밀번호 : ");
			System.out.println(dbPwd);
			System.out.print("회원가입일자 : ");
			System.out.println(dbInDate);
			System.out.print("전화번호 : ");
			System.out.println(dbPhone);
			System.out.print("대출제한일자 : ");
			if(dbCantBorrow==null) {
				System.out.println("없음");
			}else {
				System.out.println(dbCantBorrow);	
			}
			System.out.print("대출가능한 책 권수 : ");
			System.out.println(dbBorrowable);
			System.out.print("지금까지 읽은 책 수 : ");
			System.out.println(dbReadCount);
			
			
			
		} catch (SQLException e) {
			System.out.println("SQL 예외 발생 !!!");
			e.printStackTrace();
		}finally {
			OracleDB.close(conn);
			OracleDB.close(pstmt);
			OracleDB.close(rs);
		}
		
	}//개인 정보 조회=============================================================================================
	
	//탈퇴 확인=============================================================================================
	public boolean askUserQuit() {
		
		boolean istrue = true;
		
		while(istrue) {
			System.out.println("=====회원 탈퇴=====");
			System.out.print("정말 탈퇴하시겠습니까?");
			System.out.print("1. 예");
			System.out.print("9. 아니오");
			int n = Util.scInt();
			
			switch(n) {
			case 1 : 
				userQuit(); istrue=false; break;
			case 2 : istrue = false;  break;
			default : System.out.println("다시 선택하세요");
			}
			
			if(n==1) {
				return true;
			}
			
		}
		
		return false;
	}//탈퇴 확인=============================================================================================
	
	
	/*
	 * 
	 * 
	 * 여기부터
	 */
	
	//탈퇴=============================================================================================
	public void userQuit() {
		System.out.println("=====탈퇴하였습니다=====");
		
		
		//디비 연결 
		Connection conn = OracleDB.getOracleConnection();
		//아이디에 맞는 패스워드 디비에서 조회
		
		//관리자 화면 로그인도 할 수도 있음 컬럼명을 if로 나누기 처리하기
		
		String sql = "UPDATE MEMBER SET QUIT = 'Y' WHERE MNO = ?";
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, loginNo);
			rs = pstmt.executeQuery();
			
			isLogin = false;
		} catch (SQLException e) {
			System.out.println("SQL 예외 발생 !!!");
		}finally {
			OracleDB.close(conn);
			OracleDB.close(pstmt);
			OracleDB.close(rs);
		}
		
	}//탈퇴=============================================================================================

}//class


















