package com.example.Admin;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import com.mysql.jdbc.log.Log;

public class ForgotPassword extends JPanel{
	
	JPanel mainPanel=new JPanel();
	JPanel panelCenter=new JPanel();
	JPanel panelSouth=new JPanel();
	
	JLabel lblUserName=new JLabel("UserName");
	JLabel lblEmail=new JLabel("Emailid");
	JLabel lblNationalId=new JLabel("Nationalid");
	JLabel lblMobileNumber=new JLabel("Mobile number");
	
	JTextField txtUserName=new JTextField(20);
	JTextField txtEmailid=new JTextField();
	JTextField txtNationalid=new JTextField();
	JTextField txtMobileNumber=new JTextField();
	
	JButton btnOk=new JButton("Ok");
	JButton btnCencel=new JButton("Cencel");
	JButton btnRefresh=new JButton("Refresh");
	
	JFrame frame=new JFrame();
	
	public ForgotPassword(JFrame frame){
		this.frame=frame;
		cmp();
		btnAction();
	}
	public void refreshWork(){
		txtUserName.setText("");
		txtEmailid.setText("");
		txtNationalid.setText("");
		txtMobileNumber.setText("+88");
	}
	public void btnAction(){
		btnOk.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(checkValidation()){
					if(checkUserName()){
						if(checkOtherInfo()){
							JOptionPane.showMessageDialog(null, "All information is correct."+"\n"+"Now you"
									+ " can reset your password.");
							resetPasswordWork();
							refreshWork();
						}
					}
					else{
						JOptionPane.showMessageDialog(null, "incorrect UserName");
					}
				}
			}
		});
		btnRefresh.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				refreshWork();
			}
		});
		btnCencel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Login lg=new Login();
				lg.setVisible(true);	
				frame.setVisible(false);
			}
		});
	}
	public void resetPasswordWork(){
		ResetPassword setNewPass=new ResetPassword(frame,txtUserName.getText().toString().trim());
		mainPanel.setVisible(false);
		add(setNewPass);
		setNewPass.setVisible(true);
		setSize(480, 270);
	}
	public boolean checkValidation(){
		if(!txtUserName.getText().trim().toString().isEmpty()){
			if(!txtEmailid.getText().trim().toString().isEmpty()){
				if(!txtNationalid.getText().trim().toString().isEmpty()){
					if(!txtMobileNumber.getText().trim().toString().isEmpty()){
						return true;
					}
					else{
						JOptionPane.showMessageDialog(null, "insert Mobile Number please!");
					}
				}
				else{
					JOptionPane.showMessageDialog(null, "insert Nationalid please!");
				}
			}
			else{
				JOptionPane.showMessageDialog(null, "insert Emailid please!");
			}
		}
		else{
			JOptionPane.showMessageDialog(null, "insert username please!");
		}
		return false;
	}
	public boolean checkUserName(){
		try {
			String sql="select userName from tbnewuserinfo";
			dbConneciton.connection();
			ResultSet rs=dbConneciton.sta.executeQuery(sql);
			while(rs.next()){
				if(txtUserName.getText().trim().toString().equals(rs.getString("userName"))){
					return true;
				}
			}
			dbConneciton.con.close();
		} 
		catch (Exception e) {
			JOptionPane.showMessageDialog(null, e+"checkUserName()");
		}
		return false;
	}
	public boolean checkOtherInfo(){
		try {
			String sql="select email,nationalId,mobileNum from tbnewuserinfo where userName "
					+ "like '"+txtUserName.getText().toString().trim()+"'";
			dbConneciton.connection();
			ResultSet rs=dbConneciton.sta.executeQuery(sql);
			while(rs.next()){
				if(txtEmailid.getText().toString().trim().equals(rs.getString("email"))){
					if(txtNationalid.getText().toString().trim().equals(rs.getString("nationalId"))){
						if(txtMobileNumber.getText().toString().trim().equals(rs.getString("mobileNum"))){
							return true;
						}
						else{
							JOptionPane.showMessageDialog(null, "incorrect Mobile Number");
						}
					}
					else{
						JOptionPane.showMessageDialog(null, "incorrect NationalID");
					}
				}
				else{
					JOptionPane.showMessageDialog(null, "incorrect EmailID");
				}
			}
		} 
		catch (Exception e) {
			JOptionPane.showMessageDialog(null, e+"checkOtherInfo()");
		}
		return false;
	}
	public void cmp(){
		BorderLayout border=new BorderLayout();
		setLayout(border);
		add(mainPanel, BorderLayout.CENTER);
		mainPanel.setLayout(new BorderLayout());
		mainPanel.add(panelCenter, BorderLayout.CENTER);
		panelCenter();
		mainPanel.add(panelSouth, BorderLayout.SOUTH);	
		panelSouth();
	}
	public void panelCenter(){
		panelCenter.setBackground(new Color(12, 44, 59)); 
		GridBagConstraints cn=new GridBagConstraints();
		GridBagLayout grid=new GridBagLayout();
		panelCenter.setLayout(grid);
		
		cn.gridx=0;
		cn.gridy=0;
		cn.fill=GridBagConstraints.BOTH;
		cn.insets=new Insets(5, 5, 5, 5);
		panelCenter.add(lblUserName, cn);
		lblUserName.setFont(new Font("Century Gothic", Font.BOLD, 12));
		lblUserName.setForeground(Color.WHITE);
		
		cn.gridx=1;
		cn.gridy=0;
		cn.fill=GridBagConstraints.BOTH;
		cn.insets=new Insets(5, 5, 5, 5);
		panelCenter.add(txtUserName, cn);
		
		cn.gridx=0;
		cn.gridy=1;
		cn.fill=GridBagConstraints.BOTH;
		cn.insets=new Insets(5, 5, 5, 5);
		panelCenter.add(lblEmail, cn);
		lblEmail.setFont(new Font("Century Gothic", Font.BOLD, 12));
		lblEmail.setForeground(Color.WHITE);
		
		cn.gridx=1;
		cn.gridy=1;
		cn.fill=GridBagConstraints.BOTH;
		cn.insets=new Insets(5, 5, 5, 5);
		panelCenter.add(txtEmailid, cn);
		
		cn.gridx=0;
		cn.gridy=2;
		cn.fill=GridBagConstraints.BOTH;
		cn.insets=new Insets(5, 5, 5, 5);
		panelCenter.add(lblNationalId, cn);
		lblNationalId.setFont(new Font("Century Gothic", Font.BOLD, 12));
		lblNationalId.setForeground(Color.WHITE);
		
		cn.gridx=1;
		cn.gridy=2;
		cn.fill=GridBagConstraints.BOTH;
		cn.insets=new Insets(5, 5, 5, 5);
		panelCenter.add(txtNationalid, cn);
		
		cn.gridx=0;
		cn.gridy=3;
		cn.fill=GridBagConstraints.BOTH;
		cn.insets=new Insets(5, 5, 5, 5);
		panelCenter.add(lblMobileNumber, cn);
		lblMobileNumber.setFont(new Font("Century Gothic", Font.BOLD, 12));
		lblMobileNumber.setForeground(Color.WHITE);
		
		cn.gridx=1;
		cn.gridy=3;
		cn.fill=GridBagConstraints.BOTH;
		cn.insets=new Insets(5, 5, 5, 5);
		panelCenter.add(txtMobileNumber, cn);
		txtMobileNumber.setText("+88");
	}
	public void panelSouth(){
		panelSouth.setPreferredSize(new Dimension(0, 80));
		FlowLayout flow=new FlowLayout();
		panelSouth.setLayout(flow);
		
		panelSouth.setBackground(new Color(12, 44, 59));  
		panelSouth.add(btnOk);
		panelSouth.add(btnCencel);
		panelSouth.add(btnRefresh);
		btnOk.setFont(new Font("Century Gothic", Font.BOLD, 13));
		btnOk.setPreferredSize(new Dimension(100, 32));
		btnOk.setBackground(Color.decode("#E68A00"));
		btnOk.setForeground(Color.WHITE);
		btnCencel.setFont(new Font("Century Gothic", Font.BOLD, 13));
		btnCencel.setPreferredSize(new Dimension(100, 32));
		btnCencel.setBackground(Color.decode("#E68A00"));
		btnCencel.setForeground(Color.WHITE);
		btnRefresh.setFont(new Font("Century Gothic", Font.BOLD, 13));
		btnRefresh.setPreferredSize(new Dimension(100, 32));
		btnRefresh.setBackground(Color.decode("#E68A00"));
		btnRefresh.setForeground(Color.WHITE);
	}
}












