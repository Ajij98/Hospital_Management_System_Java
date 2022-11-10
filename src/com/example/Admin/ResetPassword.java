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

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;


public class ResetPassword extends JPanel{

	JPanel panelCenter=new JPanel();
	JPanel panelSouth=new JPanel();

	JLabel lblNewPassword=new JLabel("New Password");
	JLabel lblConfirmPassword=new JLabel("Confirm Password");
	JTextField txtNewpass=new JTextField(20);
	JTextField txtConfirampass=new JTextField();

	JButton bntResetPassword=new JButton("Reset Password");
	JButton btnCencel=new JButton("Cencel");
	JButton btnRefresh=new JButton("Refresh");

	JFrame frame=new JFrame();
	String userName;

	public ResetPassword(JFrame frame,String userName){
		this.userName=userName;
		this.frame=frame;
		cmp();
		btnAction();
	}
	public void refreshWork(){
		txtNewpass.setText("");
		txtConfirampass.setText("");
	}
	public void btnAction(){
		bntResetPassword.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(checkValidation()){
					if(checkPassword()){
						if(resetPassword()){
							JOptionPane.showMessageDialog(null, "Your password reset successfully."+"\n"+
									"Now you can login with your new password."+"\n"+"Thank you.");
							refreshWork();
							Login lg=new Login();
							lg.setVisible(true);
							frame.setVisible(false);
						}
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
	public boolean checkValidation(){
		if(!txtNewpass.getText().toString().trim().isEmpty()){
			if(!txtConfirampass.getText().toString().trim().isEmpty()){
				return true;
			}
			else{
				JOptionPane.showMessageDialog(null, "insert Confirmpassword please.");
			}
		}
		else{
			JOptionPane.showMessageDialog(null, "insert Newpassword please.");
		}
		return false;
	}
	public boolean checkPassword(){
		if(txtNewpass.getText().toString().trim().equals(txtConfirampass.getText().toString().trim())){
			return true;
		}
		else{
			JOptionPane.showMessageDialog(null, "Confirmpassword not match with Newpassword");
		}
		return false;
	}
	public boolean resetPassword(){
		try {
			String sql="update tbnewuserinfo set password='"+txtConfirampass.getText().toString().trim()+"' "
					+ "where userName like '"+userName+"'";
			dbConneciton.connection();
			dbConneciton.sta.executeUpdate(sql);
			dbConneciton.con.close();
			return true;
		} 
		catch (Exception e) {
			JOptionPane.showMessageDialog(null, e+"resetPassword()");
		}
		return false;
	}
	public void cmp(){
		BorderLayout border=new BorderLayout();
		setLayout(border);
		add(panelCenter, BorderLayout.CENTER);
		panelCenter();
		add(panelSouth, BorderLayout.SOUTH);	
		panelSouth();
	}
	public void panelCenter(){
		panelCenter.setBackground(Color.decode("#92000D")); 
		GridBagConstraints cn=new GridBagConstraints();
		GridBagLayout grid=new GridBagLayout();
		panelCenter.setLayout(grid);

		cn.gridx=0;
		cn.gridy=0;
		cn.fill=GridBagConstraints.BOTH;
		cn.insets=new Insets(5, 5, 5, 5);
		panelCenter.add(lblNewPassword, cn);
		lblNewPassword.setFont(new Font("Century Gothic", Font.PLAIN, 12));
		lblNewPassword.setForeground(Color.WHITE);

		cn.gridx=1;
		cn.gridy=0;
		cn.fill=GridBagConstraints.BOTH;
		cn.insets=new Insets(5, 5, 5, 5);
		panelCenter.add(txtNewpass, cn);

		cn.gridx=0;
		cn.gridy=1;
		cn.fill=GridBagConstraints.BOTH;
		cn.insets=new Insets(5, 5, 5, 5);
		panelCenter.add(lblConfirmPassword, cn);
		lblConfirmPassword.setFont(new Font("Century Gothic", Font.PLAIN, 12));
		lblConfirmPassword.setForeground(Color.WHITE);

		cn.gridx=1;
		cn.gridy=1;
		cn.fill=GridBagConstraints.BOTH;
		cn.insets=new Insets(5, 5, 5, 5);
		panelCenter.add(txtConfirampass, cn);
	}
	public void panelSouth(){
		panelSouth.setPreferredSize(new Dimension(0, 80));
		panelSouth.setBackground(Color.BLACK);
		FlowLayout flow=new FlowLayout();
		panelSouth.setLayout(flow);

		panelSouth.setBackground(Color.decode("#92000D"));  
		panelSouth.add(bntResetPassword);
		panelSouth.add(btnCencel);
		panelSouth.add(btnRefresh);
		bntResetPassword.setFont(new Font("Century Gothic", Font.PLAIN, 12));
		bntResetPassword.setPreferredSize(new Dimension(140, 35));
		bntResetPassword.setBackground(Color.WHITE);
		btnCencel.setFont(new Font("Century Gothic", Font.PLAIN, 12));
		btnCencel.setPreferredSize(new Dimension(110, 35));
		btnCencel.setBackground(Color.WHITE);
		btnRefresh.setFont(new Font("Century Gothic", Font.PLAIN, 12));
		btnRefresh.setPreferredSize(new Dimension(110, 35));
		btnRefresh.setBackground(Color.WHITE);
	}
}















