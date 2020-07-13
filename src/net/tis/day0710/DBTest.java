package net.tis.day0710;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Date;
import java.util.Scanner;


public class DBTest  { 
		Connection CN=null;//DB서버정보및 user/pwd기억, CN참조해서 명령어생성
		Statement ST=null;//정적인명령어 ST=CN.createStatement(X);
		PreparedStatement PST=null; //동적인명령어 PST=CN.prepareStatememt(msg)
		CallableStatement CST=null; //storedprocedure연결
		ResultSet RS=null;//RS=ST.executeQuery("select~") ; 조회결과를 RS기억
		String msg="" ; 
		Scanner sc = new Scanner(System.in);
			
	 public DBTest() {
		 try{
	     Class.forName("oracle.jdbc.driver.OracleDriver"); //드라이브로드
	     String url="jdbc:oracle:thin:@127.0.0.1:1521:XE" ;
	     CN=DriverManager.getConnection("jdbc:oracle:thin:@127.0.0.1:1521:XE","system","1234");
	     System.out.println("오라클연결성공success 월요일");
	     ST=CN.createStatement();
		 }catch (Exception e) {	}
	 }//생성자
	 
	public static void main(String[] args) {
		DBTest test = new DBTest();
				
		Scanner scin = new Scanner(System.in);
		while(true) {
			System.out.print("\n1등록 2출력 3이름조회 4삭제 5갯수조회 6데이터수정 9종료>>> ");
			int sel=scin.nextInt();
			if(sel==1){test.dbInsert();}
			else if(sel==2){ test.dbSelectAll();}
			else if(sel==3){ test.dbSearchName();}
			else if(sel==4){ test.dbDelete(); }
			else if(sel==5) { System.out.println("총 "+test.dbCount()+" 개의 데이터가 있습니다"); }
			else if(sel==6){ test.dbUpdate(); }
			else if(sel==9){ test.myexit(); break; }
		}
		scin.close();
	}//main end
	public void dbUpdate() {//한건 code 수정 PreparedStatement 처리
		try {
			
			System.out.println("업데이트할 code를 입락하시오");
			String updateid = sc.next();
			System.out.println("업데이트할 항목을 입력하시오");
			String updatecol = sc.next();
			System.out.println("업데이트할 내용을 입력하시오");
			String updatedata = sc.next();
			msg="update test set "+updatecol +"='"+updatedata+ "' where code="+updateid;
			int OK = ST.executeUpdate(msg);
			System.out.println(updateid+"의 내용수정 성공했습니다");
			 dbSelectAll();
			
			
		}catch(Exception e) {System.out.println(e);}
	}
	
	public int dbCount() { // 레코드 갯수 Statement
		int mycount=0;
		try {
			msg="select count(*) cnt from test";
			RS = ST.executeQuery(msg);
			while(RS.next()) {
				mycount = RS.getInt("cnt");
			}
		}catch(Exception e) {System.out.println(e);}
		return mycount;
	}
	
	
	public void dbInsert( ) {//한건등록
		try {
		 //PreparedStatement명령어로 대체
		 System.out.print("PST code >> ");
		 int data = Integer.parseInt(sc.nextLine());
		 msg="select count(*) hit from test where code = " + data ;
//		 int ok = ST.executeUpdate(msg);
//		 if(ok==1) {
//			 System.out.println("중복된 데이터 입니다.");
//			 return;
//		 }
		 
		 RS=ST.executeQuery(msg);
		 if(RS.next()) {
			 if(RS.getInt("hit")>0) {
				 System.out.println("중복된 데이터 입니다.");
				 return;
			 }
		 }
		 
		 
		 

		 System.out.print("PST name >> ");
		 String name = sc.nextLine();

		 System.out.print("PST title >> ");
		 String title = sc.nextLine();

		 msg="insert into test values(?, ?, ?, sysdate, 0)";
		 PST=CN.prepareStatement(msg);
		 	   PST.setInt(1, data);
		 	   PST.setString(2, name);
		 	   PST.setString(3, title);
		     int OK=PST.executeUpdate();
		 System.out.println("test테이블 PST명령어저장성공success");
		 dbSelectAll(); 
		}catch (Exception ex) { System.out.println(ex);}
	}//end--------------------
	
	public void dbSelectAll( ) {//전체출력
		try {
		System.out.println("=================================================");
		//msg="select * from test order by code" ;
		msg = "select rownum rn, s.* from test s";
		RS = ST.executeQuery(msg);
		while(RS.next()==true) {
			int rn = RS.getInt("rn");
			int a=RS.getInt("code");
			String b = RS.getString("name");
			String c = RS.getString("title");
			Date dt = RS.getDate("wdate");
			int hit = RS.getInt("cnt");
			System.out.println(rn+"\t"+a+"\t"+b+"\t"+c+"\t"+dt+"\t"+hit);
		}
		System.out.println("조회된 데이터의 총 개수는 "+dbCount()+"");
		System.out.println("=================================================");
		}catch (Exception e) { System.out.println("전체조회에러");}
	}//end--------------------
	
	public void dbSearchName( ) {//name필드조회
		System.out.print("\n조회이름입력>>>");
		String data = sc.nextLine();
		try {		
			msg="select * from test where name like '%" +data+ "%' " ;
			//System.out.println(msg);
			RS = ST.executeQuery(msg);
			while(RS.next()==true) {
				int a=RS.getInt("code");
				String b = RS.getString("name");
				String c = RS.getString("title");
				Date dt = RS.getDate("wdate");
				int hit = RS.getInt("cnt");
				System.out.println(a+"\t"+b+"\t"+c+"\t"+dt+"\t"+hit);
			}	
		}catch (Exception e) { System.out.println("이름조회에러");}
	}//end--------------------
	
	public void dbDelete( ) {//code필드삭제
		 System.out.print("\n삭제할 코드입력>>>");
		 int find = sc.nextInt();
		 try {
		  msg="delete from test where code = " + find ;
		  int OK=ST.executeUpdate(msg);
		  System.out.println(find+" 삭제성공했습니다");
		  dbSelectAll();
		}catch (Exception ex){
			System.out.println(find+" 삭제실패했습니다");
			System.out.println(ex);
		}
	}//end--------------------
	
	public void myexit() {
		System.out.println("프로그램을 종료합니다");
		System.exit(1);
	}//end--------------------
	
	
	public void dbInsert2223333444( ) {//한건등록
		try {
		 //Statement명령어대신 PreparedStatement명령어로 대체
		 System.out.print("code >> "); String code = sc.nextLine();
		 System.out.print("name >> "); String name = sc.nextLine();
		 System.out.print("title >> "); String title = sc.nextLine();
		 msg= "insert into test values("+code+",'"+name+"','"+title+"',sysdate, 0)";
		 
		 ST.executeUpdate(msg); //~.executeUpdate(i/d/u)
		 System.out.println("test테이블 저장성공success");
		 dbSelectAll(); 
		}catch (Exception e) { System.out.println("신규등록에러");}
	}//end--------------------
}/////////////////////////////////////////////class END





