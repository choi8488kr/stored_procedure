package net.tis.day0710;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Date;
import java.util.Scanner;


public class DBGuest { 
		Connection CN=null;//DB서버정보및 user/pwd기억, CN참조해서 명령어생성
		Statement ST=null;//정적인명령어 ST=CN.createStatement(X);
		PreparedStatement PST=null; //동적인명령어 PST=CN.prepareStatememt(msg)
		CallableStatement CST=null; //storedprocedure연결
		ResultSet RS=null;//RS=ST.executeQuery("select~") ; 조회결과를 RS기억
		String msg="" ; 
		int Gtotal=0; //전체레코드갯수
		Scanner sc = new Scanner(System.in);
			
	 public DBGuest() {
		 try{
	     Class.forName("oracle.jdbc.driver.OracleDriver"); //드라이브로드
	     String url="jdbc:oracle:thin:@127.0.0.1:1521:XE" ;
	     CN=DriverManager.getConnection("jdbc:oracle:thin:@127.0.0.1:1521:XE","system","1234");
	     System.out.println("오라클연결성공success 월요일");
	     ST=CN.createStatement();
		 }catch (Exception e) {	}
	 }//생성자
	 
	public static void main(String[] args) {
		DBGuest gg = new DBGuest();
				
		Scanner scin = new Scanner(System.in);
		while(true) {
			System.out.print("\n1전체출력 2페이지 3이름조회 정 9종료>>> ");
			int sel=scin.nextInt();
			if(sel==1){gg.guestSelectAll();}
			else if(sel==2){ gg.guestPage();}
			else if(sel==3){ gg.guestSearchName();}
			else if(sel==9){ gg.myexit(); break; }
		}
		scin.close();
	}//main end
	
	
		
	public int dbCount(){//레코드갯수 Statement
		int mytotal=0;
		try {
			msg="select count(*) as cnt from guest";
			RS=ST.executeQuery(msg);
			if(RS.next()==true) {
				mytotal = RS.getInt("cnt");
			}
		}catch (Exception e) { System.out.println(e);}
		return mytotal;
	}//end--------------------

	
	public void guestSelectAll( ) {//전체출력
		try {
		System.out.println("=============================================");
		Gtotal=dbCount();
		System.out.println("================================총레코드갯수:"+Gtotal +"건");
		msg="select * from guest " ;
		RS = ST.executeQuery(msg);
		while(RS.next()==true) {
		
			
		}
		System.out.println("=============================================");
		}catch (Exception e) { System.out.println("전체조회에러");}
	}//end--------------------
	
	
	public void guestPage() {
		try {
			
		}catch (Exception ex) { System.out.println(ex);}
	}//end--------------------
	
	
	public void guestSearchName( ) {//name필드조회
		System.out.print("\n조회이름입력>>>");
		String data = sc.nextLine();
		try {		
			msg="select * from guest where name like '%" +data+ "%' " ;
		}catch (Exception e) { System.out.println("이름조회에러");}
	}//end--------------------
	
	
	public void myexit() {
		System.out.println("프로그램을 종료합니다");
		System.exit(1);
	}//end--------------------
	
	
	
}/////////////////////////////////////////////class END





