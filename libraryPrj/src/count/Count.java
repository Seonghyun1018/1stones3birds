package count;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import oracledb.OracleDB;
import util.Util;

public class Count {
	public void countTest(int bookinput) {
		Connection	conn = OracleDB.getOracleConnection();
		
		
		String sql = "UPDATE BOOK SET RENT_COUNT = RENT_COUNT+1 WHERE BNO = ?";
		try {
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1,bookinput);
			pstmt.executeUpdate();
			
		} catch (SQLException e) {
			System.out.println("sql 쿼리 이상");
		}
}
	public void memberCount(int loginNo) {
		Connection	conn = OracleDB.getOracleConnection();
		
		String sql = "UPDATE MEMBER SET MCOUNT = MCOUNT+1 WHERE MNO = ? ";
		try {
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1,loginNo );
			pstmt.executeUpdate();
		} catch (SQLException e) {
			System.out.println("sql 쿼리 이상");
		}
		
	}
	
}
