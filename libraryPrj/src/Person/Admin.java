package Person;

import java.sql.Connection;
import java.util.Scanner;

import oracledb.OracleDB;

public class Admin{
	
    Scanner admin = new Scanner(System.in);
    
//    	ex) Library lib = Library.get~~~();
    
    	// 도서관 이름, 벌금, 기한 및 보류 요청 등 기본 정보 설정
    	
//    	lib.setRequestExpiry(7);
//    	lib.setReturnDeadline(5);
//    	lib.setName("이름,,,");
    
    	// 데이터베이스 연결 추후에 따로 클래스 뺴서 작성
    	Connection conn = OracleDB.getOracleConnection();
    
    	if (conn == null){
    		System.out.println("\n데이터베이스에 연결하는 중 오류가 발생했습니다.");
    		return;
    	}
    	
    	
    	try {
    		lib.populateLibrary(conn);
    		
    		boolean stop = false;
    		
    		while(!stop){
    			 System.out.println("--------------------------------------------------------");
    		     System.out.println("\t도서관 관리 시스템 시작");
    		     System.out.println("--------------------------------------------------------");
    		     System.out.println("다음 기능을 사용 할 수 있습니다. : \n");
    		     System.out.println(" 1 -  관리자 로그인");
    		     System.out.println(" 2 -  종료");
    		     System.out.println(" 3 -  관리 기능"); //어드민한테만 액세스 권한 가짐  
    		     System.out.println("-----------------------------------------\n");
    		     
    		     
    		     
    		     int choice = 0;
    		     
    		   //선택한 값 실행시켜주기
    		     choice = takeInput(0,4);
    		     
    		     if (choice == 3){
    		    	 System.out.println("\n비밀번호를 입력하세요 : ");
    		    	 
    		    	 String pwd = admin.next();
    		    	 
    		  
    		    	 if(pwd.equals("pwd")) {
    		    		 
    		    	 }
    		    	 
    		 while(true) {
    		        	  
                System.out.println("--------------------------------------------------------");
                System.out.println("\t관리 프로그램을 실행합니다. ");
                System.out.println("--------------------------------------------------------");
                System.out.println("다음 기능들을 사용할 수 있습니다. : \n");
                System.out.println("1- 직원 관리"); 
                System.out.println("2- 발행된 책 기록 보기");  
                System.out.println("3- 도서관 내의 모든 책 정보"); 
                System.out.println("4- 로그아웃"); 
                System.out.println("---------------------------------------------");
  
                    
                choice = takeInput(0,5);
                
            if (choice == 4)
            	break;
            
            if (choice == 1)
            	lib.createPerson('1');
            
            else if (choice == 2)
            	lib.viewHistory();
                  
            else if (choice == 3)
                lib.viewAllBooks();
   
                    
               System.out.println("\n계속하려면 아무 키나 누르십시오.\n");
                  Admin.next();                        
                }
           }
            else
                System.out.println("\n패스워드 입력을 잘못하셨습니다.");
    		     
    }
    		
    		else if (choice == 1){
    			
    			Person person = lib.login();

            if (person == null){
            }
            
            //회원 아이디,닉네임, 등등 + 패스워드 입력
            else if (person.getName().equals("대출회원")) { 
            	
        	   while (true) {
        		   
        		   System.out.println("--------------------------------------------------------");
                   System.out.println("\t환영합니다. Hello!");
                   System.out.println("--------------------------------------------------------");
                   System.out.println("다음 기능들을 사용 할 수 있습니다. : \n");
                   System.out.println("1- 도서 검색 하기 ");
                   System.out.println("2- 도서 위치 검색하기 ");
                   System.out.println("3- Check Personal Info of Borrower");
                   System.out.println("4- Check Total Fine of Borrower"); 
                   System.out.println("5- Check Hold Requests Queue of a Book");                         
                   System.out.println("6- Logout");
                   System.out.println("--------------------------------------------------------");
               
                   choice = takeInput(0,7);

                   if (choice == 6)
                	   break;
                   	
       }
        	   
}
            else if (person.getName().equals("점원")){ //점원 아이디,닉네임, 등등 + 패스워드 입력
            	
            	while(true) {
            		
            		System.out.println("--------------------------------------------------------");
                    System.out.println("\t점원 포털로 이동합니다.");
                    System.out.println("--------------------------------------------------------");
                    System.out.println("다음 기능들을 사용 할 수 있습니다.: \n");
                    System.out.println("1- 도서 검색하기 ");
                    System.out.println("2- 해당하는 도서의 책자 위치 찾기 ");
                    System.out.println("3- 회원의 개인 정보 확인하기 ");
                    System.out.println("4- 대출 가능 여부 확인하기 ");               
                    System.out.println("5- 대출 연장 확인하기 ");                        
                    System.out.println("6- 반납한 도서 확인하기 ");
                    System.out.println("7- 대출한 도서 확인하기 ");                        
                    System.out.println("8- 도서 상태 갱신하기 ");
                    System.out.println("9- 대출회원 가입 ");
                    System.out.println("10- 회원정보 수정하기 ");
                    System.out.println("11- 로그아웃 ");
                    System.out.println("--------------------------------------------------------");                    
                    
                    choice = takeInput(0,12);

                    if (choice == 11)
                        break;
                                      
   }                    
}
            
            else if (person.getName().equals("사서")){ //사서 아이디,닉네임, 등등 + 패스워드 입력
            	
            	//사서 관리 포털이동
            	while(true) {
            		
            		System.out.println("--------------------------------------------------------");
                    System.out.println("\t사서 포털로 이동합니다. ");
                    System.out.println("--------------------------------------------------------");
                    System.out.println("다음 기능들을 이용 할 수 있습니다. : \n");
                    System.out.println("1- 도서 검색 ");
                    System.out.println("2- 해당하는 도서의 책자 위치 찾기 ");
                    System.out.println("3- 회원의 개인 정보 확인 ");
                    System.out.println("4- 대출 가능 여부 확인 ");      
                    System.out.println("5- 대출 연장 확인 ");                        
                    System.out.println("6- 반납된 도서 확인 ");
                    System.out.println("7- 대출된 도서 확인 ");                        
                    System.out.println("8- 도서 상태 갱신 ");
                    System.out.println("9- 대출회원 가입 ");
                    System.out.println("10- 회원정보 수정 ");
                    System.out.println("11- 새로 들어온 책 추가 ");
                    System.out.println("12- 책 폐기처리 ");
                    System.out.println("13- 책 정보 수정 ");
                    System.out.println("14- 로그아웃 ");                        
                    System.out.println("--------------------------------------------------------");
                    
                    choice = takeInput(0,15);

                    if (choice == 14)
                        break;
                    
                }                    
            }
                  
        }
    		else {
    			stop = true;
              }
    		
    		
    		System.out.println("\n아무키 입력\n"); //ex 계속하려면 아무 키나 누르십시오.

    		Scanner sc = new Scanner(System.in);

    		sc.next(); 
              
   }
          
          //자원반납
          //(conn);
          
     }

          catch(Exception e){
              System.out.println("\n에러 발생\n");
             
     }   
          
         

    }
                    
                    

                    
                                    


}                    
}
   
}//class

