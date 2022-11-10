package com.example.Admin;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.SimpleAttributeSet;

import com.example.Admin.SuggestText;
import com.toedter.calendar.JDateChooser;

public class SignupWork extends JPanel{

	JPanel panelCenter=new JPanel();
	JPanel panelSouth=new JPanel();

	JLabel lblImage=new JLabel(new ImageIcon("Images/event14.jpg"));

	JLabel lblUserID=new JLabel("UserId");
	JLabel lblName=new JLabel("Name");
	JLabel lblUserName=new JLabel("UserName");
	JLabel lblDesignation=new JLabel("Designation");
	JLabel lblPass=new JLabel("Password");
	JLabel lblDateOfJoin=new JLabel("DateofJoin");
	JLabel lblEmailAddress=new JLabel("Email Address");
	JLabel lblNationalId=new JLabel("NationalId");
	JLabel lblAddress=new JLabel("Address");
	JLabel lblMobile=new JLabel("Mobile");
	JLabel lblUserType=new JLabel("UserType");
	JLabel lblExecutive=new JLabel("Executive");
	Font font=new Font("clibari", Font.BOLD, 12);

	JTextField txtUserId=new JTextField(20);
	JTextField txtName=new JTextField();
	JTextField txtUserName=new JTextField();
	JTextField txtDesignation=new JTextField();
	JPasswordField txtPass=new JPasswordField();
	JDateChooser dateOfjoin=new JDateChooser();
	JTextField txtEmailAddress=new JTextField();
	JTextField txtNationalId=new JTextField();
	JTextArea txtAddress=new JTextArea(3,5);
	JTextField txtMobileNumber=new JTextField();
	SuggestText cmbUserType=new SuggestText();
	JScrollPane scroll=new JScrollPane(txtAddress,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
			JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

	ImageIcon iconAdd=new ImageIcon("icons/add1.png");
	JButton btnSubmit=new JButton("Submit",iconAdd);
	ImageIcon iconDelete=new ImageIcon("icons/cancel-icon.png");
	JButton btnCancle=new JButton("Cancle",iconDelete);

	JFrame frame;

	public SignupWork(JFrame frm){
		this.frame=frm;
		cmp();
		btnAction();
	}
	public void txtClear(){
		txtName.setText("");
		txtUserName.setText("");
		txtDesignation.setText("");
		txtPass.setText("");
		dateOfjoin.setDate(new Date());
		txtEmailAddress.setText("");
		txtNationalId.setText("");
		txtAddress.setText("");
		txtMobileNumber.setText("+88");
		cmbUserType.txtSuggest.setText("");
	}
	public void refreshWork(){
		autoId();
		txtClear();
	}
	public void btnAction(){
		btnSubmit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(checkValidation()){
					if(checkEmail()){
						if(checkMobileNumberLess()){
							if(checkMobileNumberGreater()){
								if(checkConfirmation("sure to save?")){
									if(insertData()){
										JOptionPane.showMessageDialog(null, "Data saved successfully.Thank you.");
										refreshWork();
										Login lg=new Login();
										lg.setVisible(true);
										frame.setVisible(false);
									}
								}
							}
						}
					}
				}
			}
		});
		txtMobileNumber.addKeyListener(new KeyListener() {
			public void keyTyped(KeyEvent e) {
				char ch=e.getKeyChar();
				if(!(Character.isDigit(ch)) || (ch==KeyEvent.VK_BACK_SPACE) || (ch==KeyEvent.VK_DELETE)){
					getToolkit().beep();
					e.consume();
				}
			}
			public void keyReleased(KeyEvent e) {
				if(txtMobileNumber.getText().trim().toString().length()<3){
					txtMobileNumber.setText("+88");
				}
			}
			public void keyPressed(KeyEvent e) {
			}
		});
		btnCancle.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Login lg=new Login();
				lg.setVisible(true);
				frame.setVisible(false);
			}
		});
	}
	public void autoId(){
		try {
			String sql="select ifnull(max(cast(substring(userId,locate('-',userId)+1,"
					+ "length(userId)-locate('-',userId)) as UNSIGNED)),0)+1 as id "
					+ "from tbnewuserinfo";
			dbConneciton.connection();
			ResultSet rs=dbConneciton.sta.executeQuery(sql);
			while(rs.next()){
				txtUserId.setText("UserId-"+rs.getString("id"));
			}
			dbConneciton.con.close();
		} 
		catch (Exception e) {
			JOptionPane.showMessageDialog(null, e+"autoId()");
		}
	}
	public boolean checkValidation(){
		if(!txtUserId.getText().trim().toString().isEmpty()){
			if(!txtName.getText().trim().toString().isEmpty()){
				if(!txtUserName.getText().trim().toString().isEmpty()){
					if(!txtDesignation.getText().trim().toString().isEmpty()){
						if(!txtPass.getText().trim().toString().isEmpty()){
							if(!txtEmailAddress.getText().trim().toString().isEmpty()){
								if(!txtNationalId.getText().trim().toString().isEmpty()){
									if(!txtAddress.getText().trim().toString().isEmpty()){
										if(!txtMobileNumber.getText().trim().toString().isEmpty()){
											if(!cmbUserType.txtSuggest.getText().trim().toString().isEmpty()){
												return true;
											}
											else{
												JOptionPane.showMessageDialog(null, "select User Type please.");
											}
										}
										else{
											JOptionPane.showMessageDialog(null, "insert mobile number please.");
										}
									}
									else{
										JOptionPane.showMessageDialog(null, "insert address please.");
									}
								}
								else{
									JOptionPane.showMessageDialog(null, "insert national id please.");
								}
							}
							else{
								JOptionPane.showMessageDialog(null, "insert email please.");
							}
						}
						else{
							JOptionPane.showMessageDialog(null, "insert password please.");
						}
					}
					else{
						JOptionPane.showMessageDialog(null, "insert designation please.");
					}
				}
				else{
					JOptionPane.showMessageDialog(null, "insert userName please.");
				}
			}
			else{
				JOptionPane.showMessageDialog(null, "insert Name please.");
			}
		}
		else{
			JOptionPane.showMessageDialog(null, "insert Employee Id please.");
		}
		return false;
	}
	public boolean isExistUserName(){
		try {
			String sql="select * from tbnewuserinfo where "
					+ "userName='"+txtUserName.getText().trim().toString()+"'";
			dbConneciton.connection();
			ResultSet rs=dbConneciton.sta.executeQuery(sql);
			while(rs.next()){
				JOptionPane.showMessageDialog(null, "This User Name Already Exist!","Warning...",
						JOptionPane.ERROR_MESSAGE);
				return false;
			}
		} 
		catch (Exception e) {
			JOptionPane.showMessageDialog(null, e);
		}
		return true;
	}
	public boolean checkEmail(){
		String email_format="^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*"
				+ "@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
		Pattern email_pattern=Pattern.compile(email_format);
		Matcher email_match=email_pattern.matcher(txtEmailAddress.getText().trim());
		if(!email_match.matches()){
			JOptionPane.showMessageDialog(null, "Email Format is not correct."
					+ "please write Mail Address like this format (abcde12@gmail.com)","Warning",
					JOptionPane.WARNING_MESSAGE);
			return false;
		}
		return true;
	}
	public boolean checkMobileNumberLess(){
		if(txtMobileNumber.getText().trim().toString().length()<14){
			JOptionPane.showMessageDialog(null, "Mobile Number less then 11 digit...","Warning",
					JOptionPane.WARNING_MESSAGE);
			return false;
		}
		else{
			return true;
		}
	}
	public boolean checkMobileNumberGreater(){
		if(txtMobileNumber.getText().trim().toString().length()>14){
			JOptionPane.showMessageDialog(null, "Mobile Number greater then 11 digit...","Warning",
					JOptionPane.WARNING_MESSAGE);
			return false;
		}
		else{
			return true;
		}
	}
	public boolean checkConfirmation(String caption){
		int a=JOptionPane.showConfirmDialog(null, caption,"confirmation",JOptionPane.YES_NO_OPTION);
		if(a==JOptionPane.YES_OPTION){
			return true;
		}
		return false;
	}
	public boolean insertData(){
		try {
			String joinDate=new SimpleDateFormat("yyyy-MM-dd").format(dateOfjoin.getDate());

			String sql="insert into tbnewuserinfo(userId,name,userName,designation,password,dateOfJoin,"
					+ "email,nationalId,address,mobileNum,userType,userip,entryTime)"
					+ "values('"+txtUserId.getText().trim().toString()+"',"
					+ "'"+txtName.getText().trim().toString()+"',"
					+ "'"+txtUserName.getText().trim().toString()+"',"
					+ "'"+txtDesignation.getText().trim().toString()+"',"
					+ "'"+txtPass.getText().trim().toString()+"',"
					+ "'"+joinDate+"',"
					+ "'"+txtEmailAddress.getText().trim().toString()+"',"
					+ "'"+txtNationalId.getText().trim().toString()+"',"
					+ "'"+txtAddress.getText().trim().toString()+"',"
					+ "'"+txtMobileNumber.getText().trim().toString()+"',"
					+ "'"+cmbUserType.txtSuggest.getText().trim().toString()+"',"
					+ "'',now())";

			dbConneciton.connection();
			dbConneciton.sta.executeUpdate(sql);
			dbConneciton.con.close();
			return true;
		} 
		catch (Exception e) {
			JOptionPane.showMessageDialog(null, e+"insertData()");
		}
		return false;
	}
	public void cmp(){
		TitledBorder title=BorderFactory.createTitledBorder("User Registration Form");
		title.setTitleColor(new Color(12, 44, 59));
		title.setTitleFont(new Font("Carlibri", Font.BOLD, 16));
		title.setTitleJustification(TitledBorder.CENTER);
		setBorder(title);

		setOpaque(false);
		setPreferredSize(new Dimension(1130	,725));
		FlowLayout flow=new FlowLayout();
		setLayout(new BorderLayout());
		flow.setVgap(0);
		add(lblImage, BorderLayout.CENTER);

		lblImage.setLayout(new BorderLayout());
		lblImage.add(panelCenter, BorderLayout.CENTER);
		panelCenter();
		lblImage.add(panelSouth, BorderLayout.SOUTH);
		panelSouth();
	}
	private void panelCenter() {
		panelCenter.setOpaque(false);
		GridBagConstraints cn=new GridBagConstraints();
		GridBagLayout grid=new GridBagLayout();
		panelCenter.setLayout(grid);
		cn.fill=GridBagConstraints.BOTH;
		cn.insets=new Insets(5, 5, 5, 5);

		cn.gridx=0;
		cn.gridy=0;
		panelCenter.add(lblUserID,cn);
		lblUserID.setForeground(Color.WHITE);

		cn.gridx=1;
		cn.gridy=0;
		panelCenter.add(txtUserId,cn);
		txtUserId.setEditable(false);

		cn.gridx=0;
		cn.gridy=1;
		panelCenter.add(lblName,cn);
		lblName.setForeground(Color.WHITE);

		cn.gridx=1;
		cn.gridy=1;
		panelCenter.add(txtName,cn);

		cn.gridx=0;
		cn.gridy=2;
		panelCenter.add(lblUserName,cn);
		lblUserName.setForeground(Color.WHITE);

		cn.gridx=1;
		cn.gridy=2;
		panelCenter.add(txtUserName,cn);

		cn.gridx=0;
		cn.gridy=3;
		panelCenter.add(lblDesignation,cn);
		lblDesignation.setForeground(Color.WHITE);

		cn.gridx=1;
		cn.gridy=3;
		panelCenter.add(txtDesignation,cn);

		cn.gridx=0;
		cn.gridy=4;
		panelCenter.add(lblPass,cn);
		lblPass.setForeground(Color.WHITE);

		cn.gridx=1;
		cn.gridy=4;
		panelCenter.add(txtPass,cn);

		cn.gridx=0;
		cn.gridy=5;
		panelCenter.add(lblDateOfJoin,cn);
		lblDateOfJoin.setForeground(Color.WHITE);

		cn.gridx=1;
		cn.gridy=5;
		panelCenter.add(dateOfjoin,cn);
		dateOfjoin.setDateFormatString("dd-MM-yyyy");
		dateOfjoin.setDate(new Date());

		cn.gridx=0;
		cn.gridy=6;
		panelCenter.add(lblEmailAddress,cn);
		lblEmailAddress.setForeground(Color.WHITE);

		cn.gridx=1;
		cn.gridy=6;
		panelCenter.add(txtEmailAddress,cn);

		cn.gridx=0;
		cn.gridy=7;
		panelCenter.add(lblNationalId,cn);
		lblNationalId.setForeground(Color.WHITE);

		cn.gridx=1;
		cn.gridy=7;
		panelCenter.add(txtNationalId,cn);

		cn.gridx=0;
		cn.gridy=8;
		panelCenter.add(lblAddress,cn);
		lblAddress.setForeground(Color.WHITE);

		cn.gridx=1;
		cn.gridy=8;
		panelCenter.add(scroll,cn);

		cn.gridx=0;
		cn.gridy=9;
		panelCenter.add(lblMobile,cn);
		lblMobile.setForeground(Color.WHITE);

		cn.gridx=1;
		cn.gridy=9;
		panelCenter.add(txtMobileNumber,cn);
		txtMobileNumber.setText("+88");

		cn.gridx=0;
		cn.gridy=10;
		panelCenter.add(lblUserType,cn);
		lblUserType.setForeground(Color.WHITE);

		cn.gridx=1;
		cn.gridy=10;
		panelCenter.add(cmbUserType.cmbSuggest,cn);
		cmbUserType.v.add("");
		cmbUserType.v.add("Doctor");
		cmbUserType.v.add("Receptionist");
	}
	private void panelSouth() {
		panelSouth.setOpaque(false);
		panelSouth.setPreferredSize(new Dimension(0, 120));
		FlowLayout flow=new FlowLayout();
		panelSouth.setLayout(flow);
		flow.setHgap(15);
		flow.setVgap(30);

		panelSouth.add(btnSubmit);
		btnSubmit.setPreferredSize(new Dimension(100, 35));
		btnSubmit.setBackground(Color.decode("#46A049"));
		btnSubmit.setForeground(Color.WHITE);

		panelSouth.add(btnCancle);
		btnCancle.setPreferredSize(new Dimension(110, 35));
		btnCancle.setBackground(Color.decode("#E68A00"));
		btnCancle.setForeground(Color.WHITE);
	}
}
