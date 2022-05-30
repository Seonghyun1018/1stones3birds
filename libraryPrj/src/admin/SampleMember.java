package Person;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import oracledb.OracleDB;
import util.Util;

public class SampleMember {
   
   public static int loginUserNo;

//�α���
   public boolean login() {
      System.out.println("=====�α���=====");
      System.out.print("���̵� : ");
      String id = Util.sc.nextLine();
      System.out.print("�н����� : ");
      String pwd = Util.sc.nextLine();
      
      
      //��� ���� ���
      Connection conn = OracleDB.getOracleConnection();
      //�ش� ���̵� �´� �н����� ��񿡼� ��ȸ�ϱ�
      String sql = "SELECT PWD FROM MEMBER WHERE ID = ?";
      PreparedStatement pstmt = null;
      ResultSet rs = null;
      try {
         pstmt = conn.prepareStatement(sql);
         pstmt.setString(1, id);
         rs = pstmt.executeQuery();
         
         if(rs.next()) {
            String dbPwd = rs.getString("pwd"); 
            if(dbPwd.equalsIgnoreCase(pwd)) {
               
               System.out.println("�α��� ���� !!!");
               return true;
            }
         }
         
      } catch (SQLException e) {
         System.out.println("SQL ���� �߻� !!!");
      }finally {
         OracleDB.close(conn);
         OracleDB.close(pstmt);
         OracleDB.close(rs);
      }
      
      //��񿡼� ��ȸ�� �н����尡 ��ġ�ϴ��� Ȯ��
      
      System.out.println("�α��� ���� ...");
      return false;
   }
   
   //ȸ������
   /*
    * ���̵�, ���, �г��� �Է¹ޱ�
    * ���̵� �ߺ� �˻�
    * ��й�ȣ ��ȿ�� �˻� (8���� �̻����� ,,, )
    * ��� ����
    */
   public boolean join() {
      System.out.println("=====ȸ������=====");
      System.out.print("���̵� : ");
      String id = Util.sc.nextLine();
      System.out.print("��й�ȣ : ");
      String pwd = Util.sc.nextLine();
      System.out.print("�г��� : ");
      String nick = Util.sc.nextLine();
      
      //��� ��ȿ�� �˻�
      if(pwd.length() < 4) {
         // ȸ������ ����
         System.out.println("��й�ȣ�� 4���� �̻� �̾�� �մϴ�.");
         return false;
      }
      
      //���̵� �ߺ� �˻�
      //1. ��� ����
      Connection conn = OracleDB.getOracleConnection();
      //2. ��񿡼� ���� ���̵�� ��ġ�ϴ� ���̵� ��ȸ
      try {
         String sql = "SELECT * FROM MEMBER WHERE ID = ?";
//         Statement stmtDup = conn.createStatement();
//         ResultSet rs = stmtDup.executeQuery(sql);
         PreparedStatement pstmt = conn.prepareStatement(sql);
         pstmt.setString(1, id);
         ResultSet rs = pstmt.executeQuery();
         
         if(rs.next()) {
            //�ߺ��� ���̵�.. ȸ������ ����
            System.out.println("���̵� �ߺ�!!!");
            return false;
         }
         
         //��� ������ ����
         //3. ȸ�����Լ���? ��� ����
         String sqlInsert 
            = "INSERT INTO MEMBER(NO,ID,PWD,NICK) "
                  + "VALUES(MEMBER_NO_SEQ.NEXTVAL,?,?,?)";
//         Statement stmtInsert = conn.createStatement();
//         int result = stmtInsert.executeUpdate(sqlInsert);
         PreparedStatement pstmt2 = conn.prepareStatement(sqlInsert);
         pstmt2.setString(1, id);
         pstmt2.setString(2, pwd);
         pstmt2.setString(3, nick);
         int result = pstmt2.executeUpdate();
         
         
         if(result == 1) {
            System.out.println("ȸ�� ���� ���� !!!");
            return true;
         }
         
      } catch (SQLException e) {
         e.printStackTrace();
      }
      
      System.out.println("ȸ������ ����...");
      return false;
   }
   

}//class