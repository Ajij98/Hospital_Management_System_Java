package com.example.Admin;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.Timer;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;

import com.DoctorInfo.DoctorRegistration;
import com.EmployeeInfo.EmployeeHistory;
import com.EmployeeInfo.EmployeeReg;
import com.DoctorInfo.DoctorAppointment;
import com.DoctorInfo.DoctorHistory;
import com.PatientInfo.DischargePatient;
import com.PatientInfo.PatientAdmission;
import com.PatientInfo.ViewPatient;
import com.RoomInfo.BedNoEntry;
import com.RoomInfo.WordEntry;
import com.Security.ChangePassWork;
import com.Security.NewUserWork;
import com.toedter.calendar.JDateChooser;

public class WorkingPanel extends JPanel{
	
	JPanel mainPanel=new JPanel();
	JPanel panelNorthNew=new JPanel();
	JLabel lblBackgroundImage=new JLabel(new ImageIcon("images/wp2.jpg"));
	
	JPanel panelNorth=new JPanel();
	JPanel panelNorthCenter=new JPanel();
	JPanel panelNorthEast=new JPanel();
	JPanel panelCenter=new JPanel();
	JPanel panelWest=new JPanel();
	JPanel panelWestNorth=new JPanel();
	JPanel panelWestCenter=new JPanel();
	JPanel panelWestSouth=new JPanel();
	
	JButton btnPatientInfo=new JButton("   Patietn Information        ", new ImageIcon("icon1/patient.png"));
	JButton btnDoctorinfo=new JButton("   Doctor Information        ", new ImageIcon("icon1/doctor.png"));
	JButton btnRoomInfo=new JButton("   Room Information         ", new ImageIcon("icon1/room.png"));
	JButton btnEmployeeInfo=new JButton("   Staff Information            ", new ImageIcon("icon1/employee.png"));
	JButton btnSecurity=new JButton("   Security                       ", new ImageIcon("icon1/security.png"));
	JButton btnAboutUs=new JButton("   About Us                      ", new ImageIcon("icon1/about.png"));
	
	ImageIcon icon1=new ImageIcon("icon1/patient.png");
	JButton btnPatientAdmission=new JButton("Add New Patient", icon1);
	ImageIcon icon2=new ImageIcon("icon1/view.png");
	JButton btnPatietHistory=new JButton("View Patient History", icon2);
	ImageIcon icon3=new ImageIcon("icon1/view.png");
	JButton btnDischargePatient=new JButton("Discharge Patient", icon3);
	
	ImageIcon iconAppoinment=new ImageIcon("icon1/EmergencyAdd.png");
	JButton btnAppoinment=new JButton("Appoinment",iconAppoinment);
	ImageIcon iconAddDoctor=new ImageIcon("icon1/patient.png");
	JButton btnAddDoctor=new JButton("Doctor Registration", iconAddDoctor);
	ImageIcon iconViewDoctor=new ImageIcon("icon1/view.png");
	JButton btnViewDoctor=new JButton("View Doctors", iconViewDoctor);
	
	ImageIcon iconWordEntry=new ImageIcon("icon1/EmergencyAdd.png");
	JButton btnWordEntry=new JButton("Word Entry",iconWordEntry);
	ImageIcon iconBedEntry=new ImageIcon("icon1/view.png");
	JButton btnBedEntry=new JButton("Bed Entry", iconBedEntry);
	
	ImageIcon iconEmployee=new ImageIcon("icon1/EmergencyAdd.png");
	JButton btnAddEmployee=new JButton("Add New Employee",iconEmployee);
	ImageIcon iconViewEmployee=new ImageIcon("icon1/EmergencyAdd.png");
	JButton btnViewEmployee=new JButton("Employee Histroy",iconViewEmployee);
	
	ImageIcon iconNewUser=new ImageIcon("icon1/EmergencyAdd.png");
	JButton btnNewUser=new JButton("New User Reg.",iconNewUser);
	ImageIcon iconChangePassword=new ImageIcon("icon1/EmergencyAdd.png");
	JButton btnChangePassword=new JButton("ChangePassword",iconChangePassword);
	
	ImageIcon iconFacilities=new ImageIcon("icon1/EmergencyAdd.png");
	JButton btnFacilities=new JButton("Facilities",iconFacilities);
	ImageIcon iconBranch=new ImageIcon("icon1/EmergencyAdd.png");
	JButton btnBranch=new JButton("Our Branch's",iconBranch);
	
	JLabel lblPatientInfo=new JLabel("Patient Details");
	JLabel lblDoctorInfo=new JLabel("Doctor Details");
	JLabel lblRoomInfo=new JLabel("Room Details");
	JLabel lblLabInfo=new JLabel("Lab Details");
	JLabel lblEmployeeInfo=new JLabel("Employee Details");
	JLabel lblAccounts=new JLabel("Account Details");
	JLabel lblSecurity=new JLabel("Security");
	JLabel lblAboutUs=new JLabel("About Hospital");
	JLabel lblOthers=new JLabel("Othes");
		
	JLabel lblHospita1=new JLabel("HOSPITAL MANAGEMENT SYSTEM");
	JLabel lblHospitalLogo=new JLabel(new ImageIcon("icons/hospitallogo1.png"));
	
	PatientAdmission patientAdmission=new PatientAdmission();
	ViewPatient viewPatient=new ViewPatient();
	DischargePatient dischargePatient=new DischargePatient();
	
	WordEntry wordEntry=new WordEntry();
	BedNoEntry bedEntry=new BedNoEntry();
	
	DoctorRegistration addNewDoctor=new DoctorRegistration();
	DoctorHistory viewDoctor=new DoctorHistory();
	DoctorAppointment doctorAppointment=new DoctorAppointment();
	
	EmployeeReg employeeReg=new EmployeeReg();
	EmployeeHistory employeeHistory=new EmployeeHistory();
	
	NewUserWork newUser=new NewUserWork();
	ChangePassWork changePass=new ChangePassWork();
	
	ImageIcon iconBack=new ImageIcon("icons/left-arrow.png");
	JButton btnBack=new JButton("Back",iconBack);
	
	JLabel lblUserIcon=new JLabel(new ImageIcon("icons/User-icon.png"));
	JLabel lblCurrentUser=new JLabel("current user :");
	JLabel lblUser=new JLabel();
	
	ImageIcon iconLogout=new ImageIcon("icons/logout.png");
	JButton btnLogout=new JButton("Logout",iconLogout);
	
	JLabel lblNewDate=new JLabel("Date:");
	JLabel lblDate=new JLabel();

	JLabel lblT=new JLabel("Time:");
	JLabel lblTime=new JLabel();
	
	JFrame frame;
	SessionBean sessionBean=new SessionBean();
	
	public WorkingPanel(JFrame frm){
		this.sessionBean=sessionBean;
		this.frame=frm;
		cmp();
		btnAction();
		btnIni(false);
		showCurrentTime();
		showCurrentDate();
		//checkUserType();
	}
	public void btnIni(boolean b){
		btnPatientAdmission.setEnabled(b);
		btnPatietHistory.setEnabled(b);
		btnDischargePatient.setEnabled(b);
		btnAddDoctor.setEnabled(b);
		btnAppoinment.setEnabled(b);
		btnViewDoctor.setEnabled(b);
		btnWordEntry.setEnabled(b);
		btnBedEntry.setEnabled(b);
		btnAddEmployee.setEnabled(b);
		btnViewEmployee.setEnabled(b);
		btnNewUser.setEnabled(b);
		btnChangePassword.setEnabled(b);
		btnFacilities.setEnabled(b);
		btnBranch.setEnabled(b);
	}
	public void checkUserType(){
		if(!sessionBean.getUserType().equals("Admin")){
			btnEmployeeInfo.setEnabled(false);
			btnSecurity.setEnabled(false);
		}
		else {
			btnEmployeeInfo.setEnabled(true);
			btnSecurity.setEnabled(true);
		}
	}
	public void btnAction(){
		btnPatientAdmission.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				mainPanel.setVisible(false);
				allPanelTrueFalse();
				patientAdmission.setVisible(true);
				panelNorthNew();
				patientAdmission.autoId();
				patientAdmission.cmbWordNameDataLoad();
				patientAdmission.cmbReferenceConsultantDataLoad();
				patientAdmission.tableDataLoad();
			}
		});		
		btnPatietHistory.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				mainPanel.setVisible(false);
				allPanelTrueFalse();
				viewPatient.setVisible(true);
				panelNorthNew();
				viewPatient.tableDataLoad();
				viewPatient.cmbWordNameDataLoad();
			}
		});
		btnDischargePatient.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				mainPanel.setVisible(false);
				allPanelTrueFalse();
				dischargePatient.setVisible(true);
				panelNorthNew();
				dischargePatient.autoId();
				dischargePatient.cmbPatientDataLoad();
				dischargePatient.tableDataLoad();
			}
		});
		btnAddDoctor.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				mainPanel.setVisible(false);
				allPanelTrueFalse();
				addNewDoctor.setVisible(true);
				panelNorthNew();
				addNewDoctor.autoId();
				addNewDoctor.tableDataLoad();
				addNewDoctor.cmbSearchDataLoad();
			}
		});
		btnViewDoctor.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				mainPanel.setVisible(false);
				allPanelTrueFalse();
				viewDoctor.setVisible(true);
				panelNorthNew();
				viewDoctor.tableDataLoad();
			}
		});
		btnAppoinment.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				mainPanel.setVisible(false);
				allPanelTrueFalse();
				doctorAppointment.setVisible(true);
				panelNorthNew();
				doctorAppointment.autoId();
				doctorAppointment.cmbPatientDataLoad();
				doctorAppointment.tableDataLoad();
				doctorAppointment.cmbSearchDataLoad();
				doctorAppointment.cmbReferenceDoctorDataLoad();
			}
		});
		btnWordEntry.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				mainPanel.setVisible(false);
				allPanelTrueFalse();
				wordEntry.setVisible(true);
				panelNorthNew();
				wordEntry.autoId();
				wordEntry.tableDataLoad();
				wordEntry.cmbSearchDataLoad();
			}
		});
		btnBedEntry.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				mainPanel.setVisible(false);
				allPanelTrueFalse();
				bedEntry.setVisible(true);
				panelNorthNew();
				bedEntry.cmbWordNameDataLoad();
				bedEntry.tableDataLoad();
				bedEntry.cmbSearchDataLoad();
				bedEntry.cmbWordNameSearchDataLoad();
			}
		});
		btnAddEmployee.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				mainPanel.setVisible(false);
				allPanelTrueFalse();
				employeeReg.setVisible(true);
				panelNorthNew();
				employeeReg.autoId();
				employeeReg.cmbReferenceDataLoad();
				employeeReg.tableDataLoad();
				employeeReg.cmbSearchDataLoad();
			}
		});
		btnViewEmployee.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				mainPanel.setVisible(false);
				allPanelTrueFalse();
				employeeHistory.setVisible(true);
				panelNorthNew();
				employeeHistory.tableDataLoad();
			}
		});
		btnNewUser.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				mainPanel.setVisible(false);
				allPanelTrueFalse();
				newUser.setVisible(true);
				panelNorthNew();
				newUser.autoId();
				newUser.tableDataLoad();
				newUser.cmbSearchDataLoad();
			}
		});
		btnChangePassword.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				mainPanel.setVisible(false);
				allPanelTrueFalse();
				changePass.setVisible(true);
				panelNorthNew();
				changePass.cmbUserNameDataLoad();
			}
		});
		btnPatientInfo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnIni(false);
				btnPatientAdmission.setEnabled(true);
				btnPatietHistory.setEnabled(true);
				btnDischargePatient.setEnabled(true);
			}
		});
		btnDoctorinfo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnIni(false);
				btnAddDoctor.setEnabled(true);
				btnAppoinment.setEnabled(true);
				btnViewDoctor.setEnabled(true);
			}
		});
		btnRoomInfo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnIni(false);
				btnWordEntry.setEnabled(true);
				btnBedEntry.setEnabled(true);
			}
		});
		btnEmployeeInfo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnIni(false);
				btnAddEmployee.setEnabled(true);
				btnViewEmployee.setEnabled(true);
			}
		});
		btnSecurity.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnIni(false);
				btnNewUser.setEnabled(true);
				btnChangePassword.setEnabled(true);
			}
		});
		btnAboutUs.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnIni(false);
				btnFacilities.setEnabled(true);
				btnBranch.setEnabled(true);
			}
		});
		btnPatientInfo.addMouseListener(new MouseAdapter() {
			public void mouseExited(MouseEvent e) {
				btnPatientInfo.setBackground(new Color(26, 50, 54));
			}
			public void mouseEntered(MouseEvent e) {
				btnPatientInfo.setBackground(Color.decode("#D13F13"));
			}
		});
		btnDoctorinfo.addMouseListener(new MouseAdapter() {
			public void mouseExited(MouseEvent e) {
				btnDoctorinfo.setBackground(new Color(26, 50, 54));
			}
			public void mouseEntered(MouseEvent e) {
				btnDoctorinfo.setBackground(Color.decode("#D13F13"));
			}
		});
		btnRoomInfo.addMouseListener(new MouseAdapter() {
			public void mouseExited(MouseEvent e) {
				btnRoomInfo.setBackground(new Color(26, 50, 54));
			}
			public void mouseEntered(MouseEvent e) {
				btnRoomInfo.setBackground(Color.decode("#D13F13"));
			}
		});
		btnEmployeeInfo.addMouseListener(new MouseAdapter() {
			public void mouseExited(MouseEvent e) {
				btnEmployeeInfo.setBackground(new Color(26, 50, 54));
			}
			public void mouseEntered(MouseEvent e) {
				btnEmployeeInfo.setBackground(Color.decode("#D13F13"));
			}
		});
		btnSecurity.addMouseListener(new MouseAdapter() {
			public void mouseExited(MouseEvent e) {
				btnSecurity.setBackground(new Color(26, 50, 54));
			}
			public void mouseEntered(MouseEvent e) {
				btnSecurity.setBackground(Color.decode("#D13F13"));
			}
		});
		btnAboutUs.addMouseListener(new MouseAdapter() {
			public void mouseExited(MouseEvent e) {
				btnAboutUs.setBackground(new Color(26, 50, 54));
			}
			public void mouseEntered(MouseEvent e) {
				btnAboutUs.setBackground(Color.decode("#D13F13"));
			}
		});
		
		
		
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				allPanelTrueFalse();
				panelNorthNew.setVisible(false);
				mainPanel.setVisible(true);
			}
		});
		btnLogout.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(checkConfirmation("Sure to Logout?")){
					frame.setVisible(false);
					Login lg=new Login();
					lg.setVisible(true);
				}
			}
		});
	}
	public boolean checkConfirmation(String caption){
		int a=JOptionPane.showConfirmDialog(null, caption,"Confirmation", JOptionPane.YES_NO_OPTION);
		if(a==JOptionPane.YES_OPTION){
			return true;
		}
		return false;
	}
	public void cmp(){
		setBackground(Color.BLUE);
		setLayout(new BorderLayout());
		add(panelNorthNew, BorderLayout.NORTH);
		panelNorthNew.setVisible(false);
		add(mainPanel, BorderLayout.CENTER);
		BorderLayout border=new BorderLayout();
		mainPanel.setLayout(border);
		border.setVgap(0);		
		mainPanel.add(lblBackgroundImage, BorderLayout.CENTER);
		lblBackgroundImage();
		addPanel();
	}
	public void panelNorthNew(){
		panelNorthNew.setVisible(true);
		panelNorthNew.setPreferredSize(new Dimension(1365, 30));
		panelNorthNew.setBackground(new Color(23, 95, 135));
		panelNorthNew.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
		FlowLayout flow=new FlowLayout();
		flow.setAlignment(FlowLayout.LEFT);
		flow.setVgap(0);
		flow.setHgap(0);
		
		panelNorthNew.setLayout(flow);
		panelNorthNew.add(btnBack);
		btnBack.setBackground(new Color(23, 95, 135));
		btnBack.setForeground(Color.WHITE);
		btnBack.setBorderPainted(false);
	}
	public void lblBackgroundImage() {
		lblBackgroundImage.setLayout(new BorderLayout());
		lblBackgroundImage.add(panelNorth, BorderLayout.NORTH);
		panelNorth();
		lblBackgroundImage.add(panelCenter, BorderLayout.CENTER);
		panelCenter();
		lblBackgroundImage.add(panelWest, BorderLayout.WEST);
		panelWest();
	}
	public void panelNorth(){
		panelNorth.setBackground(new Color(58, 168, 222));
		panelNorth.setPreferredSize(new Dimension(0, 40));
		panelNorth.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.BLACK));
		panelNorth.setLayout(new BorderLayout());
		panelNorth.add(panelNorthCenter, BorderLayout.CENTER);
		panelNorthCenter();
		panelNorth.add(panelNorthEast, BorderLayout.EAST);
		panelNorthEast();
	}
	public void panelNorthCenter(){
		panelNorthCenter.setBackground(new Color(58, 168, 222));
		FlowLayout flow=new FlowLayout();
		flow.setAlignment(FlowLayout.LEFT);
		flow.setHgap(10);
		flow.setVgap(8);
		panelNorthCenter.setLayout(flow);
		panelNorthCenter.add(lblHospitalLogo);
		panelNorthCenter.add(lblHospita1);
		lblHospita1.setFont(new Font("Carlibri", Font.PLAIN, 18));
		lblHospita1.setForeground(Color.WHITE);
	}	
	public void showCurrentTime(){
		new Timer(0, new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Date date=new Date();
				SimpleDateFormat s=new SimpleDateFormat("hh:mm:ss a");
				lblTime.setText(s.format(date));
			}
		}).start();
	}
	public void showCurrentDate(){
		Date date=new Date();
		SimpleDateFormat format=new SimpleDateFormat("dd-MM-yyyy");
		lblDate.setText(format.format(date));
	}
	public void panelNorthEast(){
		panelNorthEast.setBackground(new Color(58, 168, 222));
		panelNorthEast.setPreferredSize(new Dimension(410, 0));
		FlowLayout flow=new FlowLayout();
		panelNorthEast.setLayout(flow);
		flow.setHgap(10);
		flow.setVgap(3);
		
		panelNorthEast.add(lblNewDate);
		lblNewDate.setForeground(Color.BLACK);
		lblNewDate.setFont(new Font("carlibri", Font.BOLD, 14));
		
		panelNorthEast.add(lblDate);
		lblDate.setForeground(Color.WHITE);
		lblDate.setFont(new Font("carlibri", Font.BOLD, 14));
		
		panelNorthEast.add(lblT);
		lblT.setForeground(Color.BLACK);
		lblT.setFont(new Font("carlibri", Font.BOLD, 14));
		
		panelNorthEast.add(lblTime);
		lblTime.setForeground(Color.WHITE);
		lblTime.setFont(new Font("carlibri", Font.BOLD, 14));
		
		panelNorthEast.add(btnLogout);
		btnLogout.setPreferredSize(new Dimension(120, 32));
		btnLogout.setBackground(Color.decode("#B8CFE5"));
	}
	public void panelCenter(){	
		panelCenter.setBackground(new Color(255, 255, 255, 100));
		GridBagConstraints cn=new GridBagConstraints();
		GridBagLayout grid=new GridBagLayout();
		panelCenter.setLayout(grid);
		
		cn.gridx=0;
		cn.gridy=0;
		cn.fill=GridBagConstraints.BOTH;
		cn.insets=new Insets(5, 5, 10, 5);
		panelCenter.add(lblPatientInfo, cn);
		lblPatientInfo.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.BLACK));
		lblPatientInfo.setFont(new Font("Tahoma", Font.BOLD, 13));
		
		cn.gridx=0;
		cn.gridy=1;
		cn.fill=GridBagConstraints.BOTH;
		cn.insets=new Insets(5, 5, 20, 5);
		panelCenter.add(btnPatientAdmission, cn);
		btnPatientAdmission.setPreferredSize(new Dimension(180, 40));
		btnPatientAdmission.setBackground(Color.decode("#8EB800"));
		btnPatientAdmission.setForeground(Color.WHITE);
		
		cn.gridx=1;
		cn.gridy=1;
		cn.fill=GridBagConstraints.BOTH;
		cn.insets=new Insets(5, 5, 20, 5);
		panelCenter.add(btnPatietHistory, cn);
		btnPatietHistory.setPreferredSize(new Dimension(180, 40));
		btnPatietHistory.setBackground(Color.decode("#8EB800"));
		btnPatietHistory.setForeground(Color.WHITE);		
		
		cn.gridx=2;
		cn.gridy=1;
		cn.fill=GridBagConstraints.BOTH;
		cn.insets=new Insets(5, 5, 20, 5);
		panelCenter.add(btnDischargePatient, cn);
		btnDischargePatient.setPreferredSize(new Dimension(180, 40));
		btnDischargePatient.setBackground(Color.decode("#8EB800"));
		btnDischargePatient.setForeground(Color.WHITE);
		
		cn.gridx=0;
		cn.gridy=2;
		cn.fill=GridBagConstraints.BOTH;
		cn.insets=new Insets(10, 5, 10, 5);
		panelCenter.add(lblDoctorInfo, cn);
		lblDoctorInfo.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.BLACK));
		lblDoctorInfo.setFont(new Font("Tahoma", Font.BOLD, 13));
		
		cn.gridx=0;
		cn.gridy=3;
		cn.fill=GridBagConstraints.BOTH;
		cn.insets=new Insets(5, 5, 20, 5);
		panelCenter.add(btnAddDoctor, cn);
		btnAddDoctor.setPreferredSize(new Dimension(180, 40));
		btnAddDoctor.setBackground(Color.decode("#A46499"));
		btnAddDoctor.setForeground(Color.WHITE);
		
		cn.gridx=1;
		cn.gridy=3;
		cn.fill=GridBagConstraints.BOTH;
		cn.insets=new Insets(5, 5, 20, 5);
		panelCenter.add(btnAppoinment, cn);
		btnAppoinment.setPreferredSize(new Dimension(180, 40));
		btnAppoinment.setBackground(Color.decode("#A46499"));
		btnAppoinment.setForeground(Color.WHITE);
		
		cn.gridx=2;
		cn.gridy=3;
		cn.fill=GridBagConstraints.BOTH;
		cn.insets=new Insets(5, 5, 20, 5);
		panelCenter.add(btnViewDoctor, cn);
		btnViewDoctor.setPreferredSize(new Dimension(180, 40));
		btnViewDoctor.setBackground(Color.decode("#A46499"));
		btnViewDoctor.setForeground(Color.WHITE);
		
		cn.gridx=0;
		cn.gridy=4;
		cn.fill=GridBagConstraints.BOTH;
		cn.insets=new Insets(10, 5, 10, 5);
		panelCenter.add(lblRoomInfo, cn);
		lblRoomInfo.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.BLACK));
		lblRoomInfo.setFont(new Font("Tahoma", Font.BOLD, 13));		
		
		cn.gridx=0;
		cn.gridy=5;
		cn.fill=GridBagConstraints.BOTH;
		cn.insets=new Insets(5, 5, 20, 5);
		panelCenter.add(btnWordEntry, cn);
		btnWordEntry.setPreferredSize(new Dimension(180, 40));
		btnWordEntry.setBackground(Color.decode("#1A3279"));
		btnWordEntry.setForeground(Color.WHITE);
		
		cn.gridx=1;
		cn.gridy=5;
		cn.fill=GridBagConstraints.BOTH;
		cn.insets=new Insets(5, 5, 20, 5);
		panelCenter.add(btnBedEntry, cn);
		btnBedEntry.setPreferredSize(new Dimension(180, 40));
		btnBedEntry.setBackground(Color.decode("#1A3279"));
		btnBedEntry.setForeground(Color.WHITE);
		
		cn.gridx=0;
		cn.gridy=6;
		cn.fill=GridBagConstraints.BOTH;
		cn.insets=new Insets(10, 5, 10, 5);
		panelCenter.add(lblEmployeeInfo, cn);
		lblEmployeeInfo.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.BLACK));
		lblEmployeeInfo.setFont(new Font("Tahoma", Font.BOLD, 13));
		
		cn.gridx=0;
		cn.gridy=7;
		cn.fill=GridBagConstraints.BOTH;
		cn.insets=new Insets(5, 5, 20, 5);
		panelCenter.add(btnAddEmployee, cn);
		btnAddEmployee.setPreferredSize(new Dimension(180, 40));
		btnAddEmployee.setBackground(Color.decode("#FC8C2C"));
		btnAddEmployee.setForeground(Color.WHITE);
		
		cn.gridx=1;
		cn.gridy=7;
		cn.fill=GridBagConstraints.BOTH;
		cn.insets=new Insets(5, 5, 20, 5);
		panelCenter.add(btnViewEmployee, cn);
		btnViewEmployee.setPreferredSize(new Dimension(180, 40));
		btnViewEmployee.setBackground(Color.decode("#FC8C2C"));
		btnViewEmployee.setForeground(Color.WHITE);
		
		cn.gridx=0;
		cn.gridy=8;
		cn.fill=GridBagConstraints.BOTH;
		cn.insets=new Insets(10, 5, 10, 5);
		panelCenter.add(lblSecurity, cn);
		lblSecurity.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.BLACK));
		lblSecurity.setFont(new Font("Tahoma", Font.BOLD, 13));
		
		cn.gridx=0;
		cn.gridy=9;
		cn.fill=GridBagConstraints.BOTH;
		cn.insets=new Insets(5, 5, 20, 5);
		panelCenter.add(btnNewUser, cn);
		btnNewUser.setPreferredSize(new Dimension(180, 40));
		btnNewUser.setBackground(Color.decode("#36518B"));
		btnNewUser.setForeground(Color.WHITE);
		
		cn.gridx=1;
		cn.gridy=9;
		cn.fill=GridBagConstraints.BOTH;
		cn.insets=new Insets(5, 5, 20, 5);
		panelCenter.add(btnChangePassword, cn);
		btnChangePassword.setPreferredSize(new Dimension(180, 40));
		btnChangePassword.setBackground(Color.decode("#36518B"));
		btnChangePassword.setForeground(Color.WHITE);
		
		cn.gridx=0;
		cn.gridy=10;
		cn.fill=GridBagConstraints.BOTH;
		cn.insets=new Insets(10, 5, 10, 5);
		panelCenter.add(lblAboutUs, cn);
		lblAboutUs.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.BLACK));
		lblAboutUs.setFont(new Font("Tahoma", Font.BOLD, 13));
		
		cn.gridx=0;
		cn.gridy=11;
		cn.fill=GridBagConstraints.BOTH;
		cn.insets=new Insets(5, 5, 20, 5);
		panelCenter.add(btnFacilities, cn);
		btnFacilities.setPreferredSize(new Dimension(180, 40));
		btnFacilities.setBackground(Color.decode("#BD141A"));
		btnFacilities.setForeground(Color.WHITE);
		
		cn.gridx=1;
		cn.gridy=11;
		cn.fill=GridBagConstraints.BOTH;
		cn.insets=new Insets(5, 5, 20, 5);
		panelCenter.add(btnBranch, cn);
		btnBranch.setPreferredSize(new Dimension(180, 40));
		btnBranch.setBackground(Color.decode("#BD141A"));
		btnBranch.setForeground(Color.WHITE);
	}
	public void panelWest(){
		panelWest.setBackground(Color.green);
		panelWest.setPreferredSize(new Dimension(230, 0));
		panelWest.setLayout(new BorderLayout());
		panelWest.add(panelWestNorth, BorderLayout.NORTH);
		panelWestNorth();
		panelWest.add(panelWestCenter, BorderLayout.CENTER);
		panelWestCenter();
		panelWest.add(panelWestSouth, BorderLayout.SOUTH);
		panelWestSouth();
	}
	public void panelWestNorth(){
		panelWestNorth.setPreferredSize(new Dimension(0, 60));
		panelWestNorth.setBackground(new Color(26, 50, 54));
		panelWestNorth.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.WHITE));
		FlowLayout flow=new FlowLayout();
		panelWestNorth.setLayout(flow);
		flow.setAlignment(FlowLayout.LEFT);
		flow.setVgap(15);
		flow.setHgap(8);
		
		panelWestNorth.add(lblUserIcon);
		
		panelWestNorth.add(lblCurrentUser);
		lblCurrentUser.setForeground(Color.WHITE);
		lblCurrentUser.setFont(new Font("Carlibri", Font.PLAIN, 13));
		
		panelWestNorth.add(lblUser);
		lblUser.setText(sessionBean.getUserType());
		lblUser.setForeground(Color.GREEN);
		lblUser.setFont(new Font("Carlibri", Font.BOLD, 15));
	}
	public void panelWestCenter(){
		Font font=new Font("Carlibri", Font.PLAIN, 13);
		panelWestCenter.setBackground(new Color(26, 50, 54));
		GridLayout grid=new GridLayout(9, 1);
		panelWestCenter.setLayout(grid);
		grid.setVgap(10);	
		
		panelWestCenter.add(btnPatientInfo);
		btnPatientInfo.setBackground(new Color(26, 50, 54));
		btnPatientInfo.setForeground(Color.WHITE);
		btnPatientInfo.setFont(font);
		btnPatientInfo.setBorderPainted(false);
		
		panelWestCenter.add(btnDoctorinfo);
		btnDoctorinfo.setBackground(new Color(26, 50, 54));
		btnDoctorinfo.setForeground(Color.WHITE);
		btnDoctorinfo.setFont(font);
		btnDoctorinfo.setBorderPainted(false);
		
		panelWestCenter.add(btnRoomInfo);
		btnRoomInfo.setBackground(new Color(26, 50, 54));
		btnRoomInfo.setForeground(Color.WHITE);
		btnRoomInfo.setFont(font);
		btnRoomInfo.setBorderPainted(false);
		
		panelWestCenter.add(btnEmployeeInfo);
		btnEmployeeInfo.setBackground(new Color(26, 50, 54));
		btnEmployeeInfo.setForeground(Color.WHITE);
		btnEmployeeInfo.setFont(font);
		btnEmployeeInfo.setBorderPainted(false);
		
		panelWestCenter.add(btnSecurity);
		btnSecurity.setBackground(new Color(26, 50, 54));
		btnSecurity.setForeground(Color.WHITE);
		btnSecurity.setFont(font);
		btnSecurity.setBorderPainted(false);
		
		panelWestCenter.add(btnAboutUs);
		btnAboutUs.setBackground(new Color(26, 50, 54));
		btnAboutUs.setForeground(Color.WHITE);
		btnAboutUs.setFont(font);
		btnAboutUs.setBorderPainted(false);
	}
	public void panelWestSouth(){
		panelWestSouth.setPreferredSize(new Dimension(0, 160));
		panelWestSouth.setBackground(new Color(26, 50, 54));
	}
	public void addPanel(){
		FlowLayout flow=new FlowLayout();
		setLayout(flow);
		flow.setVgap(0);
		add(patientAdmission);
		add(viewPatient);
		add(dischargePatient);
		add(wordEntry);
		add(bedEntry);
		add(addNewDoctor);
		add(viewDoctor);
		add(doctorAppointment);
		add(employeeReg);
		add(employeeHistory);
		add(newUser);
		add(changePass);
		allPanelTrueFalse();
	}
	public void allPanelTrueFalse(){
		patientAdmission.setVisible(false);
		viewPatient.setVisible(false);
		dischargePatient.setVisible(false);
		wordEntry.setVisible(false);
		bedEntry.setVisible(false);
		addNewDoctor.setVisible(false);
		viewDoctor.setVisible(false);
		doctorAppointment.setVisible(false);
		employeeReg.setVisible(false);
		employeeHistory.setVisible(false);
		newUser.setVisible(false);
		changePass.setVisible(false);
	}
}

















