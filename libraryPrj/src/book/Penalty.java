package book;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import oracledb.OracleDB;
import user.User;

public class Penalty {
   
   public void penalty() {
      
	System.out.println("대여하신 도서가 연체되었습니다.");
	   
   Connection conn = OracleDB.getOracleConnection();
   
   int mno = User.loginNo;
   String sql = "UPDATE MEMBER SET CANTBORROW = SYSDATE+7 WHERE =" + mno;
   
   PreparedStatement pstmt = null;
   
   try {
     pstmt = conn.prepareStatement(sql);
      int i = pstmt.executeUpdate();
      
      if(i == 1) {
         System.out.println("도서 연체로인해 7일동안 대출이 불가합니다.");
      }
      
   } catch (SQLException e) {
      e.printStackTrace();
   }finally {
	   OracleDB.close(conn);
	   OracleDB.close(pstmt);
   }
   
   }
   
}