package Person;

import java.sql.Connection;
import oracledb.OracleDB;

public class Admin{
	
//    Scanner admin = new Scanner(System.in);
    
//    	ex) Library lib = Library.get~~~();
    
    	// 도서관 이름, 벌금, 기한 및 보류 요청 등 기본 정보 설정
    	
//    	lib.setRequestExpiry(7);
//    	lib.setReturnDeadline(5);
//    	lib.setName("도서관 이름");
    
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

        choice = takeInput(0,4);
        
        if (choice == 3){
            System.out.println("\n비밀번호를 입력하세요 : ");
            String aPass = admin.next();
            
            if(aPass.equals("lib")){
            	
                while(true){    //관리 포털로 이동
                
                    

                    System.out.println("--------------------------------------------------------");
                    System.out.println("\t관리 프로그램을 실행합니다. ");
                    System.out.println("--------------------------------------------------------");
                    System.out.println("다음 기능들을 사용할 수 있습니다. : \n");

                    System.out.println("1- Add Clerk");
                    System.out.println("2- Add Librarian"); 
                    System.out.println("3- View Issued Books History");  
                    System.out.println("4- View All Books in Library"); 
                    System.out.println("5- Logout"); 

                    System.out.println("---------------------------------------------");

                    choice = takeInput(0,6);

                    if (choice == 5)
                        break;

                    if (choice == 1)
                        lib.createPerson('c');
                    else if (choice == 2)
                        lib.createPerson('l');

                    else if (choice == 3)
                        lib.viewHistory();

                    else if (choice == 4)
                        lib.viewAllBooks();
                    
                    System.out.println("\nPress any key to continue..\n");
                    admin.next();                        
                }
            }
            else
                System.out.println("\nSorry! Wrong Password.");
        }

        else if (choice == 1)
        {
            Person person = lib.login();

            if (person == null){}
            
            else if (person.getClass().getSimpleName().equals("Borrower"))
            {                    
                while (true)    // Way to Borrower's Portal
                {
                    clrscr();
                                    
                    System.out.println("--------------------------------------------------------");
                    System.out.println("\tWelcome to Borrower's Portal");
                    System.out.println("--------------------------------------------------------");
                    System.out.println("Following Functionalities are available: \n");
                    System.out.println("1- Search a Book");
                    System.out.println("2- Place a Book on hold");
                    System.out.println("3- Check Personal Info of Borrower");
                    System.out.println("4- Check Total Fine of Borrower"); 
                    System.out.println("5- Check Hold Requests Queue of a Book");                         
                    System.out.println("6- Logout");
                    System.out.println("--------------------------------------------------------");
                    
                    choice = takeInput(0,7);

                    if (choice == 6)
                        break;
                    
                    allFunctionalities(person,choice);
                }
            }
            
            else if (person.getClass().getSimpleName().equals("Clerk"))
            {
                while(true) // Way to Clerk's Portal
                {
                    clrscr();
                                    
                    System.out.println("--------------------------------------------------------");
                    System.out.println("\tWelcome to Clerk's Portal");
                    System.out.println("--------------------------------------------------------");
                    System.out.println("Following Functionalities are available: \n");
                    System.out.println("1- Search a Book");
                    System.out.println("2- Place a Book on hold");
                    System.out.println("3- Check Personal Info of Borrower");
                    System.out.println("4- Check Total Fine of Borrower");               
                    System.out.println("5- Check Hold Requests Queue of a Book");                        
                    System.out.println("6- Check out a Book");
                    System.out.println("7- Check in a Book");                        
                    System.out.println("8- Renew a Book");
                    System.out.println("9- Add a new Borrower");
                    System.out.println("10- Update a Borrower's Info");
                    System.out.println("11- Logout");
                    System.out.println("--------------------------------------------------------");                    
                    
                    choice = takeInput(0,12);

                    if (choice == 11)
                        break;
                                        
                    allFunctionalities(person,choice);                        
                }                    
            }
            
            else if (person.getClass().getSimpleName().equals("Librarian"))
            {
        while(true) // Way to Librarian Portal
        {
            clrscr();
                            
            System.out.println("--------------------------------------------------------");
            System.out.println("\tWelcome to Librarian's Portal");
            System.out.println("--------------------------------------------------------");
            System.out.println("Following Functionalities are available: \n");
            System.out.println("1- Search a Book");
            System.out.println("2- Place a Book on hold");
            System.out.println("3- Check Personal Info of Borrower");
            System.out.println("4- Check Total Fine of Borrower");      
            System.out.println("5- Check Hold Requests Queue of a Book");                        
            System.out.println("6- Check out a Book");
            System.out.println("7- Check in a Book");                        
            System.out.println("8- Renew a Book");
            System.out.println("9- Add a new Borrower");
            System.out.println("10- Update a Borrower's Info");
            System.out.println("11- Add new Book");
            System.out.println("12- Remove a Book");
            System.out.println("13- Change a Book's Info");
            System.out.println("14- Check Personal Info of Clerk");                        
            System.out.println("15- Logout");
            System.out.println("--------------------------------------------------------");
            
            choice = takeInput(0,16);

            if (choice == 15)
                break;
                                   
            allFunctionalities(person,choice);                        
          }                    
      }
            
  }
        else {
        	stop = true;
        }

        System.out.println("\nPress any key to continue..\n");
        Scanner sc = new Scanner(System.in);
        sc.next();            
    }
    
    //자원반납
    lib.fillItBack(conn);
    }
    catch(Exception e){
        System.out.println("\nExiting...\n");
    }   
    
   


}//class

