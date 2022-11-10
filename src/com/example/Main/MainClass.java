package com.example.Main;

import javax.swing.JOptionPane;
import javax.swing.UIManager;

import com.example.Admin.Login;
import com.example.Admin.dbConneciton;

public class MainClass {

	public static void main(String args[]){
		try {
			//UIManager.setLookAndFeel("com.jtattoo.plaf.aluminium.AluminiumLookAndFeel");
			//UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
			//UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
			dbConneciton.connection();
			dbConneciton.con.close();
			System.out.println("connected successfully");
		} 
		catch (Exception e) {
			JOptionPane.showMessageDialog(null, e);
		}
		Login lg=new Login();  
	}
}
