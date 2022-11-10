package com.DoctorInfo;

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
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
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
import com.example.Admin.dbConneciton;
import com.toedter.calendar.JDateChooser;

public class DoctorAppointment extends JPanel{

	JPanel panelWest=new JPanel();
	JPanel panelWestNorth=new JPanel();
	JPanel panelWestCenter=new JPanel();
	JPanel panelWestSouth=new JPanel();
	JPanel panelCenter=new JPanel();

	JLabel lblImage=new JLabel(new ImageIcon("images/backgroundImage4.jpg"));

	String col[]={"AppointmentNo","Doctor","Fee","Patient","Dieases","AppointmentDate","Time"};
	Object row[][]={};
	DefaultTableModel model=new DefaultTableModel(row, col);
	JTable table=new JTable(model);
	JScrollPane scroll=new JScrollPane(table, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
			JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

	JLabel lblSearch=new JLabel("Search: ");
	SuggestText cmbSearch=new SuggestText();
	JLabel lblSearchIcon=new JLabel(new ImageIcon("Images/CenterImage.png"));

	JLabel lblAppointmentNo=new JLabel("Appointment No.");
	JLabel lblSelectDepartment=new JLabel("Select Department");
	JLabel lblSelectDoctor=new JLabel("Select Doctor");
	JLabel lblFees=new JLabel("Fees");
	JLabel lblPatientName=new JLabel("Patient Name");
	JLabel lblGender=new JLabel("Gender");
	JLabel lblAge=new JLabel("Age");
	JLabel lblDieases=new JLabel("Dieases");
	JLabel lblAddress=new JLabel("Address");
	JLabel lblRefDoctor=new JLabel("Ref. Doctor");
	JLabel lblAppointmentDate=new JLabel("Appointment Date");
	JLabel lblAppointmentTime=new JLabel("Time");

	JTextField txtAppointmentNo=new JTextField(20);
	SuggestText cmbSelectDpt=new SuggestText();
	SuggestText cmbSelectDoctor=new SuggestText();
	JTextField txtFees=new JTextField();
	SuggestText cmbPatientName=new SuggestText();
	SuggestText cmbGender=new SuggestText();
	JTextField txtAge=new JTextField();
	SuggestText cmbDieases=new SuggestText();
	JTextArea txtAddress=new JTextArea(3, 20);
	JScrollPane scrollAddress=new JScrollPane(txtAddress, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, 
			JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
	SuggestText cmbRefDoctor=new SuggestText();
	JDateChooser dateAppointmentDate=new JDateChooser();
	JTextField txtTime=new JTextField();

	ImageIcon iconAdd=new ImageIcon("Images/add1.png");
	JButton btnAdd=new JButton("Add",iconAdd);
	ImageIcon iconEdit=new ImageIcon("Images/btnEdit.png");
	JButton btnEdit=new JButton("Edit",iconEdit);
	ImageIcon iconRefresh=new ImageIcon("Images/btnRefresh.png");
	JButton btnRefresh=new JButton("Refresh",iconRefresh);
	ImageIcon iconDelete=new ImageIcon("Images/cancel-icon.png");
	JButton btnDelete=new JButton("Delete",iconDelete);

	boolean isUpdate=false;

	public DoctorAppointment(){
		cmp();
		btnAction();
		btnIni(true);
		editable(true);
	}
	public void btnIni(boolean b){
		btnAdd.setEnabled(b);
		btnEdit.setEnabled(!b);
		btnDelete.setEnabled(!b);
	}
	public void editable(boolean b){
		cmbSelectDpt.cmbSuggest.setEnabled(b);
		cmbSelectDoctor.cmbSuggest.setEnabled(b);
		txtFees.setEditable(b);
		cmbPatientName.cmbSuggest.setEnabled(b);
		cmbGender.cmbSuggest.setEnabled(b);
		txtAge.setEditable(b);
		cmbDieases.cmbSuggest.setEnabled(b);
		txtAddress.setEditable(b);
		cmbRefDoctor.cmbSuggest.setEnabled(b);
		dateAppointmentDate.setEnabled(b);
		txtTime.setEditable(b);
	}
	public void txtClear(){
		txtAppointmentNo.setText("");
		cmbSelectDpt.txtSuggest.setText("");
		cmbSelectDoctor.txtSuggest.setText("");
		txtFees.setText("");
		cmbPatientName.txtSuggest.setText("");
		cmbGender.txtSuggest.setText("");
		txtAge.setText("");
		cmbDieases.txtSuggest.setText("");
		txtAddress.setText("");
		cmbRefDoctor.txtSuggest.setText("");
		dateAppointmentDate.setDate(new Date());
		txtTime.setText("");
		cmbSearch.txtSuggest.setText("");
	}
	public void refreshWork(){
		txtClear();
		btnIni(true);
		editable(true);
		autoId();
		tableDataLoad();
		cmbSearchDataLoad();
		isUpdate=false;
	}
	public void btnAction(){
		btnAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(isUpdate){
					if(checkConfirmation("Sure to Update?")){
						if(deleteData()){
							if(insertData()){
								JOptionPane.showMessageDialog(null, "Data updated successfully.Thank you.");
								refreshWork();
							}
						}
					}
				}
				else{
					if(checkValidation()){
						if(checkConfirmation("sure to save?")){
							if(insertData()){
								JOptionPane.showMessageDialog(null, "Data saved successfully.Thank you.");
								refreshWork();
							}
						}
					}
				}
			}
		});
		cmbSelectDpt.cmbSuggest.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(!cmbSelectDpt.txtSuggest.getText().trim().toString().isEmpty()){
					String department=cmbSelectDpt.txtSuggest.getText().trim().toString();
					cmbDoctorDataLoad(department);
				}
				else{
					cmbSelectDoctor.v.clear();
					cmbSelectDoctor.txtSuggest.setText("");
				}
			}
		});	
		cmbSelectDoctor.cmbSuggest.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(!cmbSelectDoctor.txtSuggest.getText().trim().toString().isEmpty()){
					StringTokenizer token=new StringTokenizer(cmbSelectDoctor.txtSuggest.getText().trim().
							toString(), "#");
					String doctorId=token.nextToken();
					txtProceduralFeeDataLoad(doctorId);
				}
				else{
					txtFees.setText("");
				}
			}
		});
		cmbSearch.cmbSuggest.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(!cmbSearch.txtSuggest.getText().trim().toString().isEmpty()){
					StringTokenizer token=new StringTokenizer(cmbSearch.txtSuggest.getText().trim().
							toString(), "#");
					String appointmentNo=token.nextToken();
					searchDataLoad(appointmentNo);
					tableDataLoad();
					btnIni(false);
					editable(false);
				}
				else{
					refreshWork();
				}
			}
		});
		table.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				String appointmentNo=model.getValueAt(table.getSelectedRow(), 0).toString();
				searchDataLoad(appointmentNo);
				cmbSearchDataLoad();
				cmbSearch.txtSuggest.setText("");
				btnIni(false);
				editable(false);
			}
		});
		txtFees.addKeyListener(new KeyListener() {
			public void keyTyped(KeyEvent e) {
				char ch=e.getKeyChar();
				if(!(Character.isDigit(ch)) || (ch==KeyEvent.VK_BACK_SPACE) || (ch==KeyEvent.VK_DELETE)){
					getToolkit().beep();
					e.consume();
				}
			}
			public void keyReleased(KeyEvent e) {}
			public void keyPressed(KeyEvent e) {}
		});
		txtAge.addKeyListener(new KeyListener() {
			public void keyTyped(KeyEvent e) {
				char ch=e.getKeyChar();
				if(!(Character.isDigit(ch)) || (ch==KeyEvent.VK_BACK_SPACE) || (ch==KeyEvent.VK_DELETE)){
					getToolkit().beep();
					e.consume();
				}
			}
			public void keyReleased(KeyEvent e) {}
			public void keyPressed(KeyEvent e) {}
		});
		btnEdit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				isUpdate=true;
				btnIni(true);
				editable(true);
			}
		});
		btnRefresh.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				refreshWork();
			}
		});

	}
	public void autoId(){
		try {
			String sql="select ifnull(max(cast(substring(appointmentNo,locate('-',appointmentNo)+1,"
					+ "length(appointmentNo)-locate('-',appointmentNo)) as UNSIGNED)),0)+1 as id from"
					+ " tbdoctorappointment";
			dbConneciton.connection();
			ResultSet rs=dbConneciton.sta.executeQuery(sql);
			while(rs.next()){
				txtAppointmentNo.setText("AppointmentNo-"+rs.getString("id"));
			}
			dbConneciton.con.close();
		} 
		catch (Exception e) {
			JOptionPane.showMessageDialog(null, e+"autoId()");
		}
	}
	public boolean checkValidation(){
		if(!txtAppointmentNo.getText().trim().toString().isEmpty()){
			if(!cmbSelectDpt.txtSuggest.getText().trim().toString().isEmpty()){
				if(!cmbSelectDoctor.txtSuggest.getText().trim().toString().isEmpty()){
					if(!txtFees.getText().trim().toString().isEmpty()){
						if(!cmbPatientName.txtSuggest.getText().trim().toString().isEmpty()){
							if(!cmbGender.txtSuggest.getText().trim().toString().isEmpty()){
								if(!txtAge.getText().trim().toString().isEmpty()){
									if(!cmbDieases.txtSuggest.getText().trim().toString().isEmpty()){
										if(!txtAddress.getText().trim().toString().isEmpty()){
											if(!txtTime.getText().trim().toString().isEmpty()){
												return true;
											}
											else{
												JOptionPane.showMessageDialog(null, "insert Time please.");
											}
										}
										else{
											JOptionPane.showMessageDialog(null, "insert address please.");
										}
									}
									else{
										JOptionPane.showMessageDialog(null, "select dieases please.");
									}
								}
								else{
									JOptionPane.showMessageDialog(null, "insert age please.");
								}
							}
							else{
								JOptionPane.showMessageDialog(null, "select gender please.");
							}
						}
						else{
							JOptionPane.showMessageDialog(null, "select patient please.");
						}
					}
					else{
						JOptionPane.showMessageDialog(null, "insert procedural fee please.");
					}
				}
				else{
					JOptionPane.showMessageDialog(null, "select doctor please.");
				}
			}
			else{
				JOptionPane.showMessageDialog(null, "select department please.");
			}
		}
		else{
			JOptionPane.showMessageDialog(null, "insert appointment no. please.");
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
	public void cmbPatientDataLoad(){
		try {
			cmbPatientName.v.clear();
			cmbPatientName.v.add("");
			String sql="select patientId,patientName from tbpatientinfo";
			dbConneciton.connection();
			ResultSet rs=dbConneciton.sta.executeQuery(sql);
			while(rs.next()){
				cmbPatientName.v.add(rs.getString("patientId")+"#"+rs.getString("patientName"));
			}
			dbConneciton.con.close();
		} 
		catch (Exception e) {
			JOptionPane.showMessageDialog(null, e+"cmbPatientDataLoad()");
		}
	}
	public void cmbDoctorDataLoad(String department){
		try {	
			cmbSelectDoctor.v.clear();
			cmbSelectDoctor.v.add("");
			String sql="select doctorId,doctorName from tbdoctorinfo where specialist='"+department+"'";
			dbConneciton.connection();
			ResultSet rs=dbConneciton.sta.executeQuery(sql);
			while(rs.next()){
				cmbSelectDoctor.v.add(rs.getString("doctorId")+"#"+rs.getString("doctorName"));
			}
			dbConneciton.con.close();
		} 
		catch (Exception e) {
			JOptionPane.showMessageDialog(null, e+"cmbDoctorDataLoad()");
		}
	}
	public void txtProceduralFeeDataLoad(String doctorId){
		try {
			String sql="select proceduralFee from tbdoctorinfo where doctorId='"+doctorId+"'";
			dbConneciton.connection();
			ResultSet rs=dbConneciton.sta.executeQuery(sql);
			while(rs.next()){
				txtFees.setText(rs.getString("proceduralFee"));
			}
			dbConneciton.con.close();
		} 
		catch (Exception e) {
			JOptionPane.showMessageDialog(null, e+"txtProceduralFeeDataLoad()");
		}
	}
	public void cmbReferenceDoctorDataLoad(){
		try {
			cmbRefDoctor.v.clear();
			cmbRefDoctor.v.add("");
			String sql="select doctorId,doctorName from tbdoctorinfo";
			dbConneciton.connection();
			ResultSet rs=dbConneciton.sta.executeQuery(sql);
			while(rs.next()){
				cmbRefDoctor.v.add(rs.getString("doctorId")+"#"+rs.getString("doctorName"));
			}
			dbConneciton.con.close();
		} 
		catch (Exception e) {
			JOptionPane.showMessageDialog(null, e+"cmbReferenceDoctorDataLoad()");
		}
	}
	public boolean insertData(){
		try {
			String refDoctorId="";
			String refDoctorName="";
			
			StringTokenizer tokenDoctor=new StringTokenizer(cmbSelectDoctor.txtSuggest.getText().trim().
					toString(), "#");
			String doctorId=tokenDoctor.nextToken();
			String doctorName=tokenDoctor.nextToken();
			
			StringTokenizer tokenPatient=new StringTokenizer(cmbPatientName.txtSuggest.getText().trim().
					toString(), "#");
			String patientId=tokenPatient.nextToken();
			String patientName=tokenPatient.nextToken();
			
			if(!cmbRefDoctor.txtSuggest.getText().trim().isEmpty()){
				StringTokenizer tokenRefDoctor=new StringTokenizer(cmbRefDoctor.txtSuggest.getText().trim().
						toString(), "#");
				refDoctorId=tokenRefDoctor.nextToken();
				refDoctorName=tokenRefDoctor.nextToken();
			}
			
			String appointmentDate=new SimpleDateFormat("yyyy-MM-dd").format(dateAppointmentDate.getDate());
			
			String sql="insert into tbdoctorappointment(appointmentNo,department,doctorId,doctorName,"
					+ "fees,patientId ,patientName,gender,age,dieases,address,refDoctorId,refDoctorName,"
					+ "date,time,userip,entryTime)"
					+ "values('"+txtAppointmentNo.getText().trim().toString()+"',"
					+ "'"+cmbSelectDpt.txtSuggest.getText().trim().toString()+"',"
					+ "'"+doctorId+"',"
					+ "'"+doctorName+"',"
					+ "'"+txtFees.getText().trim().toString()+"',"
					+ "'"+patientId+"',"
					+ "'"+patientName+"',"
					+ "'"+cmbGender.txtSuggest.getText().trim().toString()+"',"
					+ "'"+txtAge.getText().trim().toString()+"',"
					+ "'"+cmbDieases.txtSuggest.getText().trim().toString()+"',"
					+ "'"+txtAddress.getText().trim().toString()+"',"
					+ "'"+refDoctorId+"',"
					+ "'"+refDoctorName+"',"
					+ "'"+appointmentDate+"',"
					+ "'"+txtTime.getText().trim().toString()+"',"
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
	public void tableDataLoad(){
		try {
			for(int i=model.getRowCount()-1; i>=0; i--){
				model.removeRow(i);
			}			
			String sql="select appointmentNo,doctorName,fees,patientName,dieases,date,time "
					+ "from tbdoctorappointment";
			dbConneciton.connection();
			ResultSet rs=dbConneciton.sta.executeQuery(sql);
			while(rs.next()){
				model.addRow(new Object[]{rs.getString("appointmentNo"),rs.getString("doctorName"),
						rs.getString("fees"),rs.getString("patientName"),rs.getString("dieases"),
						rs.getDate("date"),rs.getString("time")});
			}
			dbConneciton.con.close();
		} 
		catch (Exception e) {
			JOptionPane.showMessageDialog(null, e+"tableDataLoad()");
		}
	}
	public void cmbSearchDataLoad(){
		try {
			cmbSearch.v.clear();
			cmbSearch.v.add("");
			String sql="select appointmentNo,patientName from tbdoctorappointment";
			dbConneciton.connection();
			ResultSet rs=dbConneciton.sta.executeQuery(sql);
			while(rs.next()){
				cmbSearch.v.add(rs.getString("appointmentNo")+"#"+rs.getString("patientName"));
			}
			dbConneciton.con.close();
		} 
		catch (Exception e) {
			JOptionPane.showMessageDialog(null, e+"cmbSearchDataLoad()");
		}
	}
	public void searchDataLoad(String appointmentNo){
		try {
			String sql="select appointmentNo,department,doctorId,doctorName,fees,patientId,patientName,"
					+ "gender,age,dieases,address,refDoctorId,refDoctorName,date,time from tbdoctorappointment where "
					+ "appointmentNo='"+appointmentNo+"'";
			dbConneciton.connection();
			System.out.println(sql);
			ResultSet rs=dbConneciton.sta.executeQuery(sql);

			while(rs.next()){
				txtAppointmentNo.setText(rs.getString("appointmentNo"));
				cmbSelectDpt.txtSuggest.setText(rs.getString("department"));
				cmbSelectDoctor.txtSuggest.setText(rs.getString("doctorId")+"#"+rs.getString("doctorName"));
				txtFees.setText(rs.getString("fees"));
				cmbPatientName.txtSuggest.setText(rs.getString("patientId")+"#"+rs.getString("patientName"));
				cmbGender.txtSuggest.setText(rs.getString("gender"));
				txtAge.setText(rs.getString("age"));
				cmbDieases.txtSuggest.setText(rs.getString("dieases"));
				txtAddress.setText(rs.getString("address"));
				cmbRefDoctor.txtSuggest.setText(rs.getString("refDoctorId")+"#"+rs.getString("refDoctorName"));
				dateAppointmentDate.setDate(rs.getDate("date"));
				txtTime.setText(rs.getString("time"));
			}
			dbConneciton.con.close();
		} 
		catch (Exception e) {
			JOptionPane.showMessageDialog(null, e+" searchDataLoad()");
		}
	}
	public boolean deleteData(){
		try {
			String sql="delete from tbdoctorappointment where "
					+ "appointmentNo='"+txtAppointmentNo.getText().trim().toString()+"'";
			dbConneciton.connection();
			dbConneciton.sta.executeUpdate(sql);
			dbConneciton.con.close();
			return true;
		} 
		catch (Exception e) {
			JOptionPane.showMessageDialog(null, e+"deleteData()");
		}
		return false;
	}
	public void cmp(){
		setPreferredSize(new Dimension(1365	,720));
		FlowLayout flow=new FlowLayout();
		flow.setVgap(0);
		setLayout(flow);
		add(lblImage);		
		lblImage.setLayout(new BorderLayout());
		lblImage.add(panelWest, BorderLayout.WEST);
		panelWest();
		lblImage.add(panelCenter, BorderLayout.CENTER);
		panelCenter();
	}
	public void panelWest(){
		TitledBorder title=BorderFactory.createTitledBorder("Appointment Form");
		title.setTitleColor(Color.decode("#8B0000"));
		title.setTitleFont(new Font("Carlibri", Font.BOLD, 14));
		panelWest.setBorder(title);

		panelWest.setPreferredSize(new Dimension(540, 0));
		panelWest.setOpaque(false);
		panelWest.setLayout(new BorderLayout());
		panelWest.add(panelWestNorth, BorderLayout.NORTH);
		panelWestNorth();
		panelWest.add(panelWestCenter, BorderLayout.CENTER);
		panelWestCenter();
		panelWest.add(panelWestSouth, BorderLayout.SOUTH);
		panelWestSouth();
	}
	private void panelWestNorth() {
		panelWestNorth.setOpaque(false);
		panelWestNorth.setPreferredSize(new Dimension(0, 80));
		GridBagConstraints cn=new GridBagConstraints();
		GridBagLayout grid=new GridBagLayout();
		panelWestNorth.setLayout(grid);

		cn.gridx=0;
		cn.gridy=0;
		cn.fill=GridBagConstraints.BOTH;
		cn.insets=new Insets(25, 5, 5, 5);
		panelWestNorth.add(lblSearch, cn);

		cn.gridx=1;
		cn.gridy=0;
		cn.fill=GridBagConstraints.BOTH;
		cn.insets=new Insets(25, 5, 5, 5);
		panelWestNorth.add(cmbSearch.cmbSuggest, cn);
		cmbSearch.cmbSuggest.setPreferredSize(new Dimension(300, 32));

		cn.gridx=2;
		cn.gridy=0;
		cn.fill=GridBagConstraints.BOTH;
		cn.insets=new Insets(25, 5, 5, 5);
		panelWestNorth.add(lblSearchIcon, cn);
	}
	private void panelWestCenter() {
		panelWestCenter.setOpaque(false);
		GridBagConstraints c=new GridBagConstraints();
		GridBagLayout grid=new GridBagLayout();
		panelWestCenter.setLayout(grid);
		c.fill=GridBagConstraints.BOTH;
		c.insets=new Insets(5, 5, 5, 5);

		c.gridx=0;
		c.gridy=0;
		panelWestCenter.add(lblAppointmentNo,c);

		c.gridx=1;
		c.gridy=0;
		panelWestCenter.add(txtAppointmentNo,c);
		txtAppointmentNo.setEditable(false);

		c.gridx=0;
		c.gridy=1;
		panelWestCenter.add(lblSelectDepartment,c);

		c.gridx=1;
		c.gridy=1;
		panelWestCenter.add(cmbSelectDpt.cmbSuggest,c);
		cmbSelectDpt.v.add("");
		cmbSelectDpt.v.add("Cardiologist");
		cmbSelectDpt.v.add("Gastrologist");
		cmbSelectDpt.v.add("Gynocologist");
		cmbSelectDpt.v.add("Nurologist");
		cmbSelectDpt.v.add("Psychologist");
		cmbSelectDpt.v.add("Radiologist");

		c.gridx=0;
		c.gridy=2;
		panelWestCenter.add(lblSelectDoctor,c);

		c.gridx=1;
		c.gridy=2;
		panelWestCenter.add(cmbSelectDoctor.cmbSuggest,c);

		c.gridx=0;
		c.gridy=3;
		panelWestCenter.add(lblFees,c);

		c.gridx=1;
		c.gridy=3;
		panelWestCenter.add(txtFees,c);

		c.gridx=0;
		c.gridy=4;
		panelWestCenter.add(lblPatientName,c);

		c.gridx=1;
		c.gridy=4;
		panelWestCenter.add(cmbPatientName.cmbSuggest,c);

		c.gridx=0;
		c.gridy=5;
		panelWestCenter.add(lblGender,c);

		c.gridx=1;
		c.gridy=5;
		panelWestCenter.add(cmbGender.cmbSuggest,c);
		cmbGender.v.add("");
		cmbGender.v.add("Male");
		cmbGender.v.add("Female");

		c.gridx=0;
		c.gridy=6;
		panelWestCenter.add(lblAge,c);

		c.gridx=1;
		c.gridy=6;
		panelWestCenter.add(txtAge,c);

		c.gridx=0;
		c.gridy=7;
		panelWestCenter.add(lblDieases,c);

		c.gridx=1;
		c.gridy=7;
		panelWestCenter.add(cmbDieases.cmbSuggest,c);
		cmbDieases.v.add("");
		cmbDieases.v.add("Fever");
		cmbDieases.v.add("Diarrhea");
		cmbDieases.v.add("Chikungunya");
		cmbDieases.v.add("Malaria");
		cmbDieases.v.add("Cholera");
		cmbDieases.v.add("Hepatitis");
		cmbDieases.v.add("Tuberclulosis");
		cmbDieases.v.add("Heart Dieases");
		cmbDieases.v.add("Respiratory Infections");
		cmbDieases.v.add("Brain Cancer");
		cmbDieases.v.add("Stroke and Transient ischemic Attack(TIA)");
		cmbDieases.v.add("Stomach (Gastric) Cancer");
		cmbDieases.v.add("Peptic Ulcer Dieases");
		cmbDieases.v.add("Alcohol or Substance use disorder");
		cmbDieases.v.add("Adult Attention Deficit/Hyperactivity disorder(ADD/ADHD)");
		cmbDieases.v.add("Abdominal Aortic Aneurysm(AAA)");
		cmbDieases.v.add("Alzheimer's dieases");
		cmbDieases.v.add("Apendicitis");
		cmbDieases.v.add("Anemia");
		cmbDieases.v.add("Acute Lymphoblastic Lukemia(ALL)");
		cmbDieases.v.add("Bone Marrow Failure Syndromes");
		cmbDieases.v.add("Chromic Lymphocytic Leukemia");
		cmbDieases.v.add("Absence of Periods");
		cmbDieases.v.add("Premenstrul sysdrome(PMS)");

		c.gridx=0;
		c.gridy=8;
		panelWestCenter.add(lblAddress,c);

		c.gridx=1;
		c.gridy=8;
		panelWestCenter.add(scrollAddress,c);

		c.gridx=0;
		c.gridy=9;
		panelWestCenter.add(lblRefDoctor,c);

		c.gridx=1;
		c.gridy=9;
		panelWestCenter.add(cmbRefDoctor.cmbSuggest,c);

		c.gridx=0;
		c.gridy=10;
		panelWestCenter.add(lblAppointmentDate,c);

		c.gridx=1;
		c.gridy=10;
		panelWestCenter.add(dateAppointmentDate,c);
		dateAppointmentDate.setDateFormatString("dd-MM-yyyy");
		dateAppointmentDate.setDate(new Date());

		c.gridx=0;
		c.gridy=11;
		panelWestCenter.add(lblAppointmentTime,c);

		c.gridx=1;
		c.gridy=11;
		panelWestCenter.add(txtTime,c);
	}
	private void panelWestSouth() {
		panelWestSouth.setOpaque(false);
		panelWestSouth.setPreferredSize(new Dimension(0, 100));
		FlowLayout flow=new FlowLayout();
		panelWestSouth.setLayout(flow);
		flow.setHgap(15);
		flow.setVgap(0);

		panelWestSouth.add(btnAdd);
		btnAdd.setPreferredSize(new Dimension(100, 35));
		btnAdd.setBackground(Color.decode("#46A049"));
		btnAdd.setForeground(Color.WHITE);
		panelWestSouth.add(btnEdit);
		btnEdit.setPreferredSize(new Dimension(100, 35));
		btnEdit.setBackground(Color.decode("#0B7DDA"));
		btnEdit.setForeground(Color.WHITE);
		panelWestSouth.add(btnRefresh);
		btnRefresh.setPreferredSize(new Dimension(110, 35));
		btnRefresh.setBackground(Color.decode("#E68A00"));
		btnRefresh.setForeground(Color.WHITE);
		panelWestSouth.add(btnDelete);
		btnDelete.setPreferredSize(new Dimension(100, 35));
		btnDelete.setBackground(Color.decode("#DA190B"));
		btnDelete.setForeground(Color.WHITE);
	}
	public void panelCenter(){
		TitledBorder title=BorderFactory.createTitledBorder("Appointment List");
		title.setTitleColor(Color.decode("#8B0000"));
		title.setTitleFont(new Font("Carlibri", Font.BOLD, 14));
		panelCenter.setBorder(title);
		panelCenter.setOpaque(false);
		GridBagConstraints cn=new GridBagConstraints();
		GridBagLayout grid=new GridBagLayout();
		panelCenter.setLayout(grid);

		cn.gridx=0;
		cn.gridy=0;
		cn.fill=GridBagConstraints.BOTH;
		cn.insets=new Insets(5, 5, 5, 5);
		panelCenter.add(scroll,cn);
		scroll.setPreferredSize(new Dimension(800,650));
		table.getTableHeader().setReorderingAllowed(false);
	}
}
