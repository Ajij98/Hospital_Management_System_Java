package com.Security;

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
import java.util.StringTokenizer;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

import org.omg.CORBA.ACTIVITY_COMPLETED;
import com.example.Admin.SuggestText;
import com.example.Admin.dbConneciton;

public class ChangePassWork extends JPanel{

	JPanel panelChanegPassNew=new JPanel();

	JPanel panelChanegPassNewNorth=new JPanel();
	JPanel panelChanegPassNewCenter=new JPanel();

	JLabel lblImage=new JLabel(new ImageIcon("images/backgroundImage4.jpg"));

	JLabel lblUserName=new JLabel("User Name");
	JLabel lblOldPass=new JLabel("Old Password");
	JLabel lblNewPass=new JLabel("New Password");
	JLabel lblConfirmPass=new JLabel("Confirm Password");
	Font font=new Font("clibari", Font.BOLD, 14);

	JPasswordField txtOldPass=new JPasswordField(20);
	JPasswordField txtNewPass=new JPasswordField();
	JPasswordField txtConfirmPass=new JPasswordField(); 
	SuggestText  cmbUserName=new SuggestText();

	ImageIcon iconChangePass=new ImageIcon("Images/lock.png");
	JButton btnChangePass=new JButton("ChangePassword",iconChangePass);
	ImageIcon iconRefresh=new ImageIcon("Images/refresh1.png");
	JButton btnRefresh=new JButton("Refresh",iconRefresh);

	public ChangePassWork() {
		cmp();
		btnAction();
	}
	public void refreshWork(){
		cmbUserName.txtSuggest.setText("");
		txtOldPass.setText("");
		txtNewPass.setText("");
		txtConfirmPass.setText("");
	}
	public void btnAction(){
		btnChangePass.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(checkValidation()){
					if(checkOldPassword()){
						if(CheckconfirmPass()){
							if(checkConfirmation("Sure to change password?")){
								if(updatePassword()){
									JOptionPane.showMessageDialog(null,"Your password changed successfully");
									refreshWork();
								}
							}
						}
					}
				}
			}
		});
		btnRefresh.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				refreshWork();
			}
		});
	}
	public void cmbUserNameDataLoad(){
		try {
			cmbUserName.v.clear();
			cmbUserName.v.add("");
			String sql="select userId,userName from tbnewuserinfo";
			dbConneciton.connection();
			ResultSet rs=dbConneciton.sta.executeQuery(sql);
			while(rs.next()){
				cmbUserName.v.add(rs.getString("userId")+"#"+rs.getString("userName"));
			}
			dbConneciton.con.close();
		} 
		catch (Exception e) {
			JOptionPane.showMessageDialog(null, e+"cmbUserNameDataLoad()");
		}
	}
	public boolean checkValidation(){
		if(!cmbUserName.txtSuggest.getText().trim().toString().isEmpty()){
			if(!txtOldPass.getText().trim().toString().isEmpty()){
				if(!txtNewPass.getText().trim().toString().isEmpty()){
					if(!txtConfirmPass.getText().trim().toString().isEmpty()){
						return true;
					}
					else{
						JOptionPane.showMessageDialog(null, "insert confirmPassword");
					}
				}
				else{
					JOptionPane.showMessageDialog(null, "insert NewPassword");
				}
			}
			else{
				JOptionPane.showMessageDialog(null, "insert OldPassword");
			}
		}
		else{
			JOptionPane.showMessageDialog(null, "select User Name");
		}
		return false;
	}
	public boolean checkOldPassword(){
		try {
			StringTokenizer token=new StringTokenizer(cmbUserName.txtSuggest.getText().toString().trim(),"#");
			String sql="select password from tbnewuserinfo where userId like '"+token.nextToken()+"'";
			dbConneciton.connection();
			ResultSet rs=dbConneciton.sta.executeQuery(sql);
			while(rs.next()){
				if(txtOldPass.getText().trim().equals(rs.getString("password"))){
					return true;
				}
				else{
					JOptionPane.showMessageDialog(null,"Your old password is not correct");
				}
			}
			dbConneciton.con.close();
		} 
		catch (Exception e) {
			JOptionPane.showMessageDialog(null,e+"checkOldPassword()");
		}
		return false;
	}
	public boolean CheckconfirmPass(){
		if(txtNewPass.getText().trim().equals(txtConfirmPass.getText().trim())){
			return true;
		}
		else{
			JOptionPane.showMessageDialog(null, "your confirm password is not matched with New Password");
		}
		return false;
	}
	public boolean checkConfirmation(String caption){
		int a=JOptionPane.showConfirmDialog(null, caption,"confirmation",JOptionPane.YES_NO_OPTION);
		if(a==JOptionPane.YES_OPTION){
			return true;
		}
		return false;
	}
	public boolean updatePassword()
	{
		try {
			StringTokenizer token=new StringTokenizer(cmbUserName.txtSuggest.getText().toString().
					trim(),"#");
			
			String sql="update tbnewuserinfo set password='"+txtConfirmPass.getText().trim()+"' "
					+ "where userId like '"+token.nextToken()+"'";
			dbConneciton.connection();
			dbConneciton.sta.executeUpdate(sql);
			dbConneciton.con.close();
			return true;
		}
		catch (Exception e) {
			JOptionPane.showMessageDialog(null, e+"updatePassword()");
		}
		return false;
	}
	private void cmp() {
		TitledBorder titleChangePass=BorderFactory.createTitledBorder("Change Password");
		setBorder(titleChangePass);
		titleChangePass.setTitleJustification(TitledBorder.CENTER);
		titleChangePass.setTitleColor(Color.decode("#8B0000"));
		titleChangePass.setTitleFont(new Font("clibari", Font.BOLD, 18));

		setPreferredSize(new Dimension(1365	,720));
		FlowLayout flow=new FlowLayout();
		flow.setVgap(0);
		setLayout(flow);
		add(lblImage);		
		lblImage.setLayout(new BorderLayout());
		lblImage.add(panelChanegPassNew, BorderLayout.CENTER);
		panelChanegPassNew();	
	}
	private void panelChanegPassNew() {
		panelChanegPassNew.setOpaque(false);
		panelChanegPassNew.setLayout(new BorderLayout());	
		panelChanegPassNew.add(panelChanegPassNewNorth,BorderLayout.NORTH);
		panelChanegPassNewNorth();
		panelChanegPassNew.add(panelChanegPassNewCenter,BorderLayout.CENTER);
		panelChanegPassNewCenter();	
	}
	private void panelChanegPassNewCenter() {
		panelChanegPassNewCenter.setOpaque(false);
		panelChanegPassNewCenter.setBackground(Color.LIGHT_GRAY);
		FlowLayout flow=new FlowLayout();
		panelChanegPassNewCenter.setLayout(flow);
		flow.setHgap(30);
		flow.setVgap(0);

		panelChanegPassNewCenter.add(btnChangePass);
		btnChangePass.setPreferredSize(new Dimension(170, 35));
		btnChangePass.setBackground(Color.decode("#46A049"));
		btnChangePass.setForeground(Color.WHITE);

		panelChanegPassNewCenter.add(btnRefresh);
		btnRefresh.setPreferredSize(new Dimension(150, 35));
		btnRefresh.setBackground(Color.decode("#0B7DDA"));
		btnRefresh.setForeground(Color.WHITE);
	}
	private void panelChanegPassNewNorth() {
		panelChanegPassNewNorth.setOpaque(false);
		panelChanegPassNewNorth.setPreferredSize(new Dimension(0, 380));

		GridBagConstraints cn=new GridBagConstraints();
		GridBagLayout grid=new GridBagLayout();
		panelChanegPassNewNorth.setLayout(grid);
		cn.fill=GridBagConstraints.BOTH;


		cn.gridx=0;
		cn.gridy=0;		
		cn.insets=new Insets(120, 5, 5, 5);
		panelChanegPassNewNorth.add(lblUserName,cn);
		lblUserName.setFont(font);

		cn.gridx=1;
		cn.gridy=0;
		cn.insets=new Insets(120, 5, 5, 5);
		panelChanegPassNewNorth.add(cmbUserName.cmbSuggest,cn);

		cn.gridx=0;
		cn.gridy=1;
		cn.insets=new Insets(5, 5, 5, 5);
		panelChanegPassNewNorth.add(lblOldPass,cn);
		lblOldPass.setFont(font);

		cn.gridx=1;
		cn.gridy=1;
		cn.insets=new Insets(5, 5, 5, 5);
		panelChanegPassNewNorth.add(txtOldPass,cn);

		cn.gridx=0;
		cn.gridy=2;
		cn.insets=new Insets(5, 5, 5, 5);
		panelChanegPassNewNorth.add(lblNewPass,cn);
		lblNewPass.setFont(font);

		cn.gridx=1;
		cn.gridy=2;
		cn.insets=new Insets(5, 5, 5, 5);
		panelChanegPassNewNorth.add(txtNewPass,cn);

		cn.gridx=0;
		cn.gridy=3;
		cn.insets=new Insets(5, 5, 5, 5);
		panelChanegPassNewNorth.add(lblConfirmPass,cn);
		lblConfirmPass.setFont(font);

		cn.gridx=1;
		cn.gridy=3;
		cn.insets=new Insets(5, 5, 5, 5);
		panelChanegPassNewNorth.add(txtConfirmPass,cn);
	}
}
