package com.example.Admin;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.Scanner;
import java.util.StringTokenizer;

import javax.swing.JFrame;

import org.omg.CORBA.PUBLIC_MEMBER;

public class dbConneciton {
	public static Connection con=null;
	public static Statement sta=null;
	public static void connection(){
		try{
			int a=1;
			String server=null,port=null,userName=null,password=null,dbFile=null;
			File f1=new File("src/dbConnection.txt");
			Scanner scan=new Scanner(f1);
			while(scan.hasNextLine()){
				if(a==1){
					String s=scan.nextLine();
					StringTokenizer token=new StringTokenizer(s);
					token.nextToken();
					server=token.nextToken();
				}
				else if(a==2){
					String s=scan.nextLine();
					StringTokenizer token=new StringTokenizer(s);
					token.nextToken();
					port=token.nextToken();
				}
				else if(a==3){
					String s=scan.nextLine();
					StringTokenizer token=new StringTokenizer(s);
					token.nextToken();
					userName=token.nextToken();
				}
				else if(a==4){
					String s=scan.nextLine();
					StringTokenizer token=new StringTokenizer(s);
					token.nextToken();
					password=token.nextToken();
				}
				else if(a==5){
					String s=scan.nextLine();
					StringTokenizer token=new StringTokenizer(s);
					token.nextToken();
					dbFile=token.nextToken();
					break;
				}
				a++;
			}

			String url="jdbc:mysql://"+server+":"+port+"/"+dbFile; 
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			con=DriverManager.getConnection(url,userName,"");
			sta=con.createStatement();			
			System.out.println("Connect");
			
			
			/*String url="jdbc:mysql://localhost:3306/hms_update"; 
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			con=DriverManager.getConnection(url,"root","");
			sta=con.createStatement();*/
		}
		catch(Exception exp){
			System.out.println(exp);
		}
	}
}
