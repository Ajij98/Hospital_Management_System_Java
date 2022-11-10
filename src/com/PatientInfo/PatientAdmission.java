package com.PatientInfo;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.JobAttributes;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.StringTokenizer;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;

import com.example.Admin.SuggestText;
import com.example.Admin.dbConneciton;
import com.toedter.calendar.JDateChooser;

public class PatientAdmission extends JPanel{

	JLabel lblImage=new JLabel(new ImageIcon("images/backgroundImage4.jpg"));
	JPanel panelNorth=new JPanel();
	JPanel panelNorthCenter=new JPanel();
	JPanel panelNorthEast=new JPanel();
	JPanel panelCenter=new JPanel();
	JPanel panelCenterCenter=new JPanel();
	JPanel panelCenterEast=new JPanel();
	JPanel panelSouth=new JPanel();

	JLabel lblPatientId=new JLabel("PatientId");
	JLabel lblPatientName=new JLabel("PatientName");
	JLabel lblAge=new JLabel("Age");
	JLabel lblSex=new JLabel("Gender");
	JLabel lblDiseases=new JLabel("Dieases");
	JLabel lblLocalGurdian=new JLabel("Local Gurdian");
	JLabel lblContact=new JLabel("Contact No.");
	JLabel lblVillage=new JLabel("Village");
	JLabel lblPostOffice=new JLabel("Post Office");
	JLabel lblThana=new JLabel("Thana");
	JLabel lblDistrict=new JLabel("District");
	JLabel lblAdmissionDate=new JLabel("Admission Date");
	JLabel lblAdmissionTime=new JLabel("Admission Time");

	JTextField txtPatientId=new JTextField(20);
	JTextField txtPatientName=new JTextField();
	JTextField txtAge=new JTextField();
	SuggestText cmbGender=new SuggestText();
	SuggestText cmbDieases=new SuggestText();
	JTextField txtLocalGurdian=new JTextField();
	JTextField txtContact=new JTextField();
	JTextField txtVillage=new JTextField(20);
	JTextField txtPostOffice=new JTextField();
	JTextField txtThana=new JTextField();
	JTextField txtDistrict=new JTextField();
	JDateChooser dateAdmissionDate=new JDateChooser();
	JTextField txtAdmissionTime=new JTextField();

	JLabel lblConsultantName=new JLabel("Consultant");
	JLabel lblConsultantFee=new JLabel("Consultant Fee");
	JLabel lblDepartment=new JLabel("Specialist");
	JLabel lblRefConsultant=new JLabel("Ref. Consultant");

	SuggestText cmbConsultant=new SuggestText();
	JTextField txtConsultantFee=new JTextField();
	SuggestText cmbDepartment=new SuggestText();
	SuggestText cmbRefConsultant=new SuggestText();

	JLabel lblwordName=new JLabel("WordName");
	JLabel lblBedNo=new JLabel("Bed No.");
	JLabel lblBedCharge=new JLabel("Bed Charge");

	SuggestText cmbWordName=new SuggestText();
	SuggestText cmbBedNo=new SuggestText();
	JTextField txtBedCharge=new JTextField();

	String col[]={"PatientId","Patient Name","WordNo","WordName","BedNo","Admission Date","Admission Time"};
	Object row[][]={};
	DefaultTableModel model=new DefaultTableModel(row, col){
		public boolean isCellEditable(int row, int col){
			return false;
		}
	};
	JTable table=new JTable(model);
	JScrollPane scrollTable=new JScrollPane(table,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
			JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

	ImageIcon iconAdd=new ImageIcon("Images/add1.png");
	JButton btnAdd=new JButton("Add",iconAdd);
	ImageIcon iconEdit=new ImageIcon("Images/btnEdit.png");
	JButton btnEdit=new JButton("Edit",iconEdit);
	ImageIcon iconRefresh=new ImageIcon("Images/btnRefresh.png");
	JButton btnRefresh=new JButton("Refresh",iconRefresh);
	ImageIcon iconDelete=new ImageIcon("Images/cancel-icon.png");
	JButton btnDelete=new JButton("Delete",iconDelete);

	boolean isUpdate=false;

	public PatientAdmission(){
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
		txtPatientName.setEditable(b);
		txtAge.setEditable(b); 
		cmbGender.cmbSuggest.setEnabled(b);
		cmbDieases.cmbSuggest.setEnabled(b); 
		txtLocalGurdian.setEditable(b);
		txtContact.setEditable(b);
		txtVillage.setEditable(b);
		txtPostOffice.setEditable(b);
		txtThana.setEditable(b);
		txtDistrict.setEditable(b);
		dateAdmissionDate.setEnabled(b);
		txtAdmissionTime.setEditable(b);
		cmbDepartment.cmbSuggest.setEnabled(b);
		cmbConsultant.cmbSuggest.setEnabled(b);
		txtConsultantFee.setEditable(b);
		cmbRefConsultant.cmbSuggest.setEnabled(b);
		cmbWordName.cmbSuggest.setEnabled(b);
		cmbBedNo.cmbSuggest.setEnabled(b);
		txtBedCharge.setEditable(b);
	}
	public void txtClear(){
		txtPatientId.setText("");
		txtPatientName.setText("");
		txtAge.setText("");
		cmbGender.txtSuggest.setText("");
		cmbDieases.txtSuggest.setText("");
		txtLocalGurdian.setText("");
		txtContact.setText("+88");
		txtVillage.setText("");
		txtPostOffice.setText("");
		txtThana.setText("");
		txtDistrict.setText("");
		dateAdmissionDate.setDate(new Date());
		txtAdmissionTime.setText("");
		cmbDepartment.txtSuggest.setText("");
		cmbConsultant.txtSuggest.setText("");
		txtConsultantFee.setText("");
		cmbRefConsultant.txtSuggest.setText("");
		cmbWordName.txtSuggest.setText("");
		cmbBedNo.txtSuggest.setText("");
		txtBedCharge.setText("");
	}
	public void refreshWork(){
		txtClear();
		btnIni(true);
		editable(true);
		autoId();
		tableDataLoad();
		isUpdate=false;
	}
	public void btnAction(){
		btnAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(isUpdate){
					if(checkMobileNumberLess()){
						if(checkMobileNumberGreater()){
							if(checkConfirmation("Sure to Update?")){
								if(deleteData()){
									if(insertData()){
										JOptionPane.showMessageDialog(null, "Data updated successfully.Thank you.");
										refreshWork();
									}
								}
							}
						}
					}
				}
				else{
					if(checkValidation()){
						if(checkMobileNumberLess()){
							if(checkMobileNumberGreater()){
								if(checkConfirmation("Sure to save?")){
									if(insertData()){
										JOptionPane.showMessageDialog(null, "Data saved successfully.Thank you.");
										refreshWork();
									}
								}
							}
						}
					}
				}
			}
		});
		table.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				String patientId=model.getValueAt(table.getSelectedRow(), 0).toString();
				searchDataLoad(patientId);
				btnIni(false);
				editable(false);
			}
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
		cmbDepartment.cmbSuggest.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(!cmbDepartment.txtSuggest.getText().trim().toString().isEmpty()){
					String department=cmbDepartment.txtSuggest.getText().trim().toString();
					cmbConsultantDataLoad(department);
				}
				else{
					cmbConsultant.v.clear();
					cmbConsultant.txtSuggest.setText("");
					txtConsultantFee.setText("");
				}
			}
		});
		cmbConsultant.cmbSuggest.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(!cmbConsultant.txtSuggest.getText().trim().toString().isEmpty()){
					StringTokenizer token=new StringTokenizer(cmbConsultant.txtSuggest.getText().trim().
							toString(), "#");		
					String consultantId=token.nextToken();
					txtConsultantFeeDataLoad(consultantId);
				}
				else{
					txtConsultantFee.setText("");
				}
			}
		});
		cmbWordName.cmbSuggest.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(!cmbWordName.txtSuggest.getText().trim().toString().isEmpty()){
					StringTokenizer token=new StringTokenizer(cmbWordName.txtSuggest.getText().trim().
							toString(), "#");		
					token.nextToken();
					String wordName=token.nextToken();
					cmbBedNoDataLoad(wordName);
				}
				else{
					cmbBedNo.txtSuggest.setText("");
					cmbBedNo.v.clear();
					txtBedCharge.setText("");
				}
			}
		});
		cmbBedNo.cmbSuggest.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(!cmbBedNo.txtSuggest.getText().trim().toString().isEmpty()){	
					StringTokenizer token=new StringTokenizer(cmbWordName.txtSuggest.getText().trim().
							toString(), "#");		
					token.nextToken();
					String wordName=token.nextToken();

					String bedNo=cmbBedNo.txtSuggest.getText().trim().toString();
					txtBedChargeDataLoad(wordName,bedNo);
				}
				else{
					txtBedCharge.setText("");
				}
			}
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
		txtContact.addKeyListener(new KeyListener() {
			public void keyTyped(KeyEvent e) {
				char ch=e.getKeyChar();
				if(!(Character.isDigit(ch)) || (ch==KeyEvent.VK_BACK_SPACE) || (ch==KeyEvent.VK_DELETE)){
					getToolkit().beep();
					e.consume();
				}
			}
			public void keyReleased(KeyEvent e) {
				if(txtContact.getText().trim().toString().length()<3){
					txtContact.setText("+88");
				}
			}
			public void keyPressed(KeyEvent e) {
			}
		});
		txtConsultantFee.addKeyListener(new KeyListener() {
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
		txtBedCharge.addKeyListener(new KeyListener() {
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
	}
	public void autoId(){
		try {
			String sql="select ifnull(max(cast(substring(patientid,locate('-',patientid)+1,"
					+ "length(patientid)-locate('-',patientid))as UNSIGNED)),0)+1 as id from tbpatientinfo";
			dbConneciton.connection();
			ResultSet rs=dbConneciton.sta.executeQuery(sql);
			if(rs.next()){
				txtPatientId.setText("PatientId-"+rs.getString("id"));
			}
			dbConneciton.con.close();
		} 
		catch (Exception e) {
			JOptionPane.showMessageDialog(null, e+"autoId()");
		}
	}
	public void cmbConsultantDataLoad(String department){
		try {
			cmbConsultant.txtSuggest.setText("");
			cmbConsultant.v.clear();
			txtConsultantFee.setText("");
			cmbConsultant.v.clear();
			cmbConsultant.v.add("");
			String sql="select doctorId,doctorName from tbdoctorinfo where "
					+ "specialist='"+department+"'";
			dbConneciton.connection();
			ResultSet rs=dbConneciton.sta.executeQuery(sql);
			while(rs.next()){
				cmbConsultant.v.add(rs.getString("doctorId")+"#"+rs.getString("doctorName"));
			}
			dbConneciton.con.close();
		} 
		catch (Exception e) {
			JOptionPane.showMessageDialog(null, e+"cmbConsultantDataLoad()");
		}
	}
	public void txtConsultantFeeDataLoad(String consultantId){
		try {
			String sql="select proceduralFee from tbdoctorinfo where doctorId='"+consultantId+"'";
			dbConneciton.connection();
			ResultSet rs=dbConneciton.sta.executeQuery(sql);
			while(rs.next()){
				txtConsultantFee.setText(rs.getString("proceduralFee"));
			}
			dbConneciton.con.close();
		} 
		catch (Exception e) {
			JOptionPane.showMessageDialog(null, e+"txtConsultantFeeDataLoad()");
		}
	}	
	public void cmbReferenceConsultantDataLoad(){
		try {
			cmbRefConsultant.v.clear();
			cmbRefConsultant.v.add("");
			String sql="select doctorId,doctorName from tbdoctorinfo";
			dbConneciton.connection();
			ResultSet rs=dbConneciton.sta.executeQuery(sql);
			while(rs.next()){
				cmbRefConsultant.v.add(rs.getString("doctorId")+"#"+rs.getString("doctorName"));
			}
			dbConneciton.con.close();
		} 
		catch (Exception e) {
			JOptionPane.showMessageDialog(null, e+"cmbReferenceConsultantDataLoad()");
		}
	}
	public void cmbWordNameDataLoad(){
		try {
			cmbWordName.v.clear();
			cmbWordName.v.add("");
			String sql="select wordNo,wordName from tbwordinfo";
			dbConneciton.connection();
			ResultSet rs=dbConneciton.sta.executeQuery(sql);
			while(rs.next()){
				cmbWordName.v.add(rs.getString("wordNo")+"#"+rs.getString("wordName"));
			}
			dbConneciton.con.close();
		} 
		catch (Exception e) {
			JOptionPane.showMessageDialog(null, e+"cmbWordNameDataLoad()");
		}
	}
	public void cmbBedNoDataLoad(String wordName){
		try {
			cmbBedNo.v.clear();
			cmbBedNo.v.add("");
			cmbBedNo.txtSuggest.setText("");
			String sql="select bedNo from tbbedinfo where wordName='"+wordName+"'";
			dbConneciton.connection();
			ResultSet rs=dbConneciton.sta.executeQuery(sql);
			while(rs.next()){
				cmbBedNo.v.add(rs.getString("bedNo"));
			}
			dbConneciton.con.close();
		} 
		catch (Exception e) {
			JOptionPane.showMessageDialog(null, e+"cmbBedNoDataLoad()");
		}
	}
	public void txtBedChargeDataLoad(String wordName,String bedNo){
		try {
			String sql="select bedCharge from tbbedinfo where wordName='"+wordName+"' and"
					+ " bedNo='"+bedNo+"'";
			dbConneciton.connection();
			ResultSet rs=dbConneciton.sta.executeQuery(sql);
			while(rs.next()){
				txtBedCharge.setText(rs.getString("bedCharge"));
			}
			dbConneciton.con.close();
		} 
		catch (Exception e) {
			JOptionPane.showMessageDialog(null, e+"txtBedChargeDataLoad()");
		}
	}
	public boolean checkValidation(){
		if(!txtPatientId.getText().trim().toString().isEmpty()){
			if(!txtPatientName.getText().trim().toString().isEmpty()){
				if(!txtAge.getText().trim().toString().isEmpty()){
					if(!cmbGender.txtSuggest.getText().trim().toString().isEmpty()){
						if(!cmbDieases.txtSuggest.getText().trim().toString().isEmpty()){
							if(!txtLocalGurdian.getText().trim().toString().isEmpty()){
								if(!txtContact.getText().trim().toString().isEmpty()){
									if(!txtVillage.getText().trim().toString().isEmpty()){
										if(!txtPostOffice.getText().trim().toString().isEmpty()){
											if(!txtThana.getText().trim().toString().isEmpty()){
												if(!txtDistrict.getText().trim().toString().isEmpty()){
													if(!txtAdmissionTime.getText().trim().toString().isEmpty()){
														if(!cmbDepartment.txtSuggest.getText().trim().toString().isEmpty()){
															if(!cmbConsultant.txtSuggest.getText().trim().toString().isEmpty()){
																if(!txtConsultantFee.getText().trim().toString().isEmpty()){
																	if(!cmbWordName.txtSuggest.getText().trim().toString().isEmpty()){
																		if(!cmbBedNo.txtSuggest.getText().trim().toString().isEmpty()){
																			if(!txtBedCharge.getText().trim().toString().isEmpty()){
																				return true;
																			}
																			else{
																				JOptionPane.showMessageDialog(null, "insert bed charge please.");
																			}
																		}
																		else{
																			JOptionPane.showMessageDialog(null, "select ben no. please.");
																		}
																	}
																	else{
																		JOptionPane.showMessageDialog(null, "select word name please.");
																	}
																}
																else{
																	JOptionPane.showMessageDialog(null, "insert consultant fee please.");
																}
															}
															else{
																JOptionPane.showMessageDialog(null, "select consultant please.");
															}
														}
														else{
															JOptionPane.showMessageDialog(null, "select department please.");
														}
													}
													else{
														JOptionPane.showMessageDialog(null, "insert admission time please.");
													}
												}
												else{
													JOptionPane.showMessageDialog(null, "insert district please.");
												}
											}
											else{
												JOptionPane.showMessageDialog(null, "insert thana please.");
											}
										}
										else{
											JOptionPane.showMessageDialog(null, "insert post office please.");
										}
									}
									else{
										JOptionPane.showMessageDialog(null, "insert village please.");
									}
								}
								else{
									JOptionPane.showMessageDialog(null, "insert contact no. please.");
								}
							}
							else{
								JOptionPane.showMessageDialog(null, "insert local gurdian please.");
							}
						}
						else{
							JOptionPane.showMessageDialog(null, "select dieases please.");
						}
					}
					else{
						JOptionPane.showMessageDialog(null, "select gender please.");
					}
				}
				else{
					JOptionPane.showMessageDialog(null, "insert age please.");
				}
			}
			else{
				JOptionPane.showMessageDialog(null, "insert patient name please.");
			}
		}
		else{
			JOptionPane.showMessageDialog(null, "insert patient id please.");
		}
		return false;
	}
	public boolean checkMobileNumberLess(){
		if(txtContact.getText().trim().toString().length()<14){
			JOptionPane.showMessageDialog(null, "Mobile Number less then 11 digit...","Warning",
					JOptionPane.WARNING_MESSAGE);
			return false;
		}
		else{
			return true;
		}
	}
	public boolean checkMobileNumberGreater(){
		if(txtContact.getText().trim().toString().length()>14){
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
			String refDoctorId="";
			String refDoctorName="";

			StringTokenizer tokenDoctor=new StringTokenizer(cmbConsultant.txtSuggest.getText().trim().
					toString(), "#");
			String doctorId=tokenDoctor.nextToken();
			String doctorName=tokenDoctor.nextToken();

			StringTokenizer tokenWord=new StringTokenizer(cmbWordName.txtSuggest.getText().trim().
					toString(), "#");
			String wordNo=tokenWord.nextToken();
			String wordName=tokenWord.nextToken();

			if(!cmbRefConsultant.txtSuggest.getText().trim().isEmpty()){
				StringTokenizer tokenRefDoctor=new StringTokenizer(cmbRefConsultant.txtSuggest.getText().
						trim().toString(), "#");
				refDoctorId=tokenRefDoctor.nextToken();
				refDoctorName=tokenRefDoctor.nextToken();
			}

			String admissionDate=new SimpleDateFormat("yyyy-MM-dd").format(dateAdmissionDate.getDate());

			String sql="insert into tbpatientinfo(patientid,patientName,age,gender,dieases,"
					+ "localGurdian,contactNum,village,postOffice,thana,district,admissionData,"
					+ "admissionTime,specialist,doctorId,doctorName,consultantFee,refDoctorId,refDoctorName,"
					+ "wordNo,wordName,bedNo,bedCharge,userip,entryTime)"
					+ "values('"+txtPatientId.getText().trim().toString()+"',"
					+ "'"+txtPatientName.getText().trim().toString()+"',"
					+ "'"+txtAge.getText().trim().toString()+"',"
					+ "'"+cmbGender.txtSuggest.getText().trim().toString()+"',"
					+ "'"+cmbDieases.txtSuggest.getText().trim().toString()+"',"
					+ "'"+txtLocalGurdian.getText().trim().toString()+"',"
					+ "'"+txtContact.getText().trim().toString()+"',"
					+ "'"+txtVillage.getText().trim().toString()+"',"
					+ "'"+txtPostOffice.getText().trim().toString()+"',"
					+ "'"+txtThana.getText().trim().toString()+"',"
					+ "'"+txtDistrict.getText().trim().toString()+"',"
					+ "'"+admissionDate+"',"
					+ "'"+txtAdmissionTime.getText().trim().toString()+"',"
					+ "'"+cmbDepartment.txtSuggest.getText().trim().toString()+"',"
					+ "'"+doctorId+"',"
					+ "'"+doctorName+"',"
					+ "'"+txtConsultantFee.getText().trim().toString()+"',"
					+ "'"+refDoctorId+"',"
					+ "'"+refDoctorName+"',"
					+ "'"+wordNo+"',"
					+ "'"+wordName+"',"
					+ "'"+cmbBedNo.txtSuggest.getText().trim().toString()+"',"
					+ "'"+txtBedCharge.getText().trim().toString()+"',"
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
			String sql="select patientid,patientName,wordNo,wordName,bedNo,admissionData,admissionTime "
					+ "from tbpatientinfo";
			dbConneciton.connection();
			ResultSet rs=dbConneciton.sta.executeQuery(sql);
			while(rs.next()){
				model.addRow(new Object[]{rs.getString("patientid"),rs.getString("patientName"),
						rs.getString("wordNo"),rs.getString("wordName"),rs.getString("bedNo"),
						rs.getDate("admissionData"),rs.getString("admissionTime")});
			}
			dbConneciton.con.close();
		} 
		catch (Exception e) {
			JOptionPane.showMessageDialog(null, e+"tableDataLoad()");
		}
	}
	public void searchDataLoad(String patientId){
		try {
			String sql="select patientid,patientName,age,gender,dieases,"
					+ "localGurdian,contactNum,village,postOffice,thana,district,admissionData,"
					+ "admissionTime,specialist,doctorId,doctorName,consultantFee,refDoctorId,refDoctorName,"
					+ "wordNo,wordName,bedNo,bedCharge from tbpatientinfo where patientid='"+patientId+"'";
			dbConneciton.connection();
			System.out.println(sql);
			ResultSet rs=dbConneciton.sta.executeQuery(sql);

			while(rs.next()){
				txtPatientId.setText(rs.getString("patientid"));
				txtPatientName.setText(rs.getString("patientName"));
				txtAge.setText(rs.getString("age"));
				cmbGender.txtSuggest.setText(rs.getString("dieases"));
				cmbDieases.txtSuggest.setText(rs.getString("dieases"));
				txtLocalGurdian.setText(rs.getString("localGurdian"));
				txtContact.setText(rs.getString("contactNum"));
				txtVillage.setText(rs.getString("village"));
				txtPostOffice.setText(rs.getString("postOffice"));
				txtThana.setText(rs.getString("thana"));
				txtDistrict.setText(rs.getString("district"));
				dateAdmissionDate.setDate(rs.getDate("admissionData"));
				txtAdmissionTime.setText(rs.getString("admissionTime"));
				cmbDepartment.txtSuggest.setText(rs.getString("specialist"));
				cmbConsultant.txtSuggest.setText(rs.getString("doctorId")+"#"+rs.getString("doctorName"));
				txtConsultantFee.setText(rs.getString("consultantFee"));
				if(!(rs.getString("refDoctorId")+rs.getString("refDoctorName")).trim().toString().isEmpty()){
					cmbRefConsultant.txtSuggest.setText(rs.getString("refDoctorId")+"#"+
							rs.getString("refDoctorName"));
				}
				else{
					cmbRefConsultant.txtSuggest.setText(rs.getString("")+"#"+rs.getString(""));
				}
				cmbWordName.txtSuggest.setText(rs.getString("wordNo")+"#"+rs.getString("wordName"));
				cmbBedNo.txtSuggest.setText(rs.getString("bedNo"));
				txtBedCharge.setText(rs.getString("bedCharge"));
			}
			dbConneciton.con.close();
		} 
		catch (Exception e) {
			JOptionPane.showMessageDialog(null, e+" searchDataLoad()");
		}
	}
	public boolean deleteData(){
		try {
			String sql="delete from tbpatientinfo where "
					+ "patientid='"+txtPatientId.getText().trim().toString()+"'";
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
		setPreferredSize(new Dimension(1365	,730));		
		FlowLayout flow=new FlowLayout();
		flow.setVgap(0);
		setLayout(flow);
		add(lblImage);		
		lblImage.setLayout(new BorderLayout());
		lblImage.add(panelNorth, BorderLayout.NORTH);
		panelNorth();
		lblImage.add(panelCenter, BorderLayout.CENTER);
		panelCenter();
		lblImage.add(panelSouth, BorderLayout.SOUTH);
		panelSouth();
	}
	public void panelNorth(){
		panelNorth.setOpaque(false);
		panelNorth.setPreferredSize(new Dimension(0, 340));
		panelNorth.setLayout(new GridLayout(1, 2));
		panelNorth.add(panelNorthCenter, BorderLayout.CENTER);
		panelNorthCenter();
		panelNorth.add(panelNorthEast, BorderLayout.EAST);
		panelNorthEast();		
	}
	private void panelNorthCenter() {
		panelNorthCenter.setOpaque(false);
		TitledBorder title=BorderFactory.createTitledBorder("Patient Information");
		title.setTitleColor(Color.decode("#8B0000"));
		title.setTitleFont(new Font("Carlibri", Font.BOLD, 14));
		panelNorthCenter.setBorder(title);

		GridBagConstraints c=new GridBagConstraints();
		GridBagLayout grid=new GridBagLayout();
		panelNorthCenter.setLayout(grid);
		c.fill=GridBagConstraints.BOTH;
		c.insets=new Insets(5, 5, 5, 5);

		c.gridx=0;
		c.gridy=0;
		panelNorthCenter.add(lblPatientId,c);

		c.gridx=1;
		c.gridy=0;
		panelNorthCenter.add(txtPatientId,c);
		txtPatientId.setEditable(false);

		c.gridx=0;
		c.gridy=1;
		panelNorthCenter.add(lblPatientName,c);

		c.gridx=1;
		c.gridy=1;
		panelNorthCenter.add(txtPatientName,c);

		c.gridx=0;
		c.gridy=2;
		panelNorthCenter.add(lblAge,c);

		c.gridx=1;
		c.gridy=2;
		panelNorthCenter.add(txtAge, c);

		c.gridx=0;
		c.gridy=3;
		panelNorthCenter.add(lblSex,c);

		c.gridx=1;
		c.gridy=3;
		panelNorthCenter.add(cmbGender.cmbSuggest,c);
		cmbGender.v.add("");
		cmbGender.v.add("Male");
		cmbGender.v.add("Female");

		c.gridx=0;
		c.gridy=4;
		panelNorthCenter.add(lblDiseases,c);

		c.gridx=1;
		c.gridy=4;
		panelNorthCenter.add(cmbDieases.cmbSuggest,c);
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
		c.gridy=5;
		panelNorthCenter.add(lblLocalGurdian,c);

		c.gridx=1;
		c.gridy=5;
		panelNorthCenter.add(txtLocalGurdian,c);

		c.gridx=0;
		c.gridy=6;
		panelNorthCenter.add(lblContact,c);

		c.gridx=1;
		c.gridy=6;
		panelNorthCenter.add(txtContact,c);
		txtContact.setText("+88");

		c.gridx=2;
		c.gridy=0;
		panelNorthCenter.add(lblVillage,c);

		c.gridx=3;
		c.gridy=0;
		panelNorthCenter.add(txtVillage,c);

		c.gridx=2;
		c.gridy=1;
		panelNorthCenter.add(lblPostOffice,c);

		c.gridx=3;
		c.gridy=1;
		panelNorthCenter.add(txtPostOffice,c);

		c.gridx=2;
		c.gridy=2;
		panelNorthCenter.add(lblThana,c);

		c.gridx=3;
		c.gridy=2;
		panelNorthCenter.add(txtThana,c);

		c.gridx=2;
		c.gridy=3;
		panelNorthCenter.add(lblDistrict,c);

		c.gridx=3;
		c.gridy=3;
		panelNorthCenter.add(txtDistrict,c);

		c.gridx=2;
		c.gridy=4;
		panelNorthCenter.add(lblAdmissionDate,c);

		c.gridx=3;
		c.gridy=4;
		panelNorthCenter.add(dateAdmissionDate,c);
		dateAdmissionDate.setOpaque(false);
		dateAdmissionDate.setDate(new Date());
		dateAdmissionDate.setDateFormatString("dd-MM-yyyy");

		c.gridx=2;
		c.gridy=5;
		panelNorthCenter.add(lblAdmissionTime,c);

		c.gridx=3;
		c.gridy=5;
		panelNorthCenter.add(txtAdmissionTime,c);
	}
	private void panelNorthEast() {
		panelNorthEast.setOpaque(false);
		TitledBorder title=BorderFactory.createTitledBorder("Search Here...");
		title.setTitleColor(Color.decode("#8B0000"));
		title.setTitleFont(new Font("Carlibri", Font.BOLD, 14));
		panelNorthEast.setBorder(title);

		GridBagConstraints cn=new GridBagConstraints();
		GridBagLayout grid=new GridBagLayout();
		panelNorthEast.setLayout(grid);

		cn.gridx=0;
		cn.gridy=0;
		cn.fill=GridBagConstraints.BOTH;
		cn.insets=new Insets(5, 5, 5, 5);
		panelNorthEast.add(scrollTable,cn);
		scrollTable.setPreferredSize(new Dimension(630,280));
		table.getTableHeader().setReorderingAllowed(false);
	}
	public void panelCenter(){
		panelCenter.setOpaque(false);
		panelCenter.setLayout(new GridLayout(1, 2));
		panelCenter.add(panelCenterCenter, BorderLayout.CENTER);
		panelCenterCenter();
		panelCenter.add(panelCenterEast, BorderLayout.EAST);
		panelCenterEast();
	}
	public void panelCenterCenter() {
		panelCenterCenter.setOpaque(false);
		TitledBorder title=BorderFactory.createTitledBorder("Consultant information");
		title.setTitleColor(Color.decode("#8B0000"));
		title.setTitleFont(new Font("Carlibri", Font.BOLD, 14));
		panelCenterCenter.setBorder(title);

		GridBagConstraints c=new GridBagConstraints();
		GridBagLayout grid=new GridBagLayout();
		panelCenterCenter.setLayout(grid);

		c.gridx=0;
		c.gridy=0;
		c.fill=GridBagConstraints.BOTH;
		c.insets=new Insets(10, 5, 5, 5);
		panelCenterCenter.add(lblDepartment,c);

		c.gridx=1;
		c.gridy=0;
		c.fill=GridBagConstraints.BOTH;
		c.insets=new Insets(10, 5, 5, 100);
		panelCenterCenter.add(cmbDepartment.cmbSuggest,c);
		cmbDepartment.cmbSuggest.setPreferredSize(new Dimension(250, 28));
		cmbDepartment.v.add("");
		cmbDepartment.v.add("Cardiologist");
		cmbDepartment.v.add("Nurologist");
		cmbDepartment.v.add("Gastrologist");
		cmbDepartment.v.add("Psychologist");
		cmbDepartment.v.add("Radiologist");
		cmbDepartment.v.add("Hematologist");
		cmbDepartment.v.add("Gynocologist");

		c.gridx=0;
		c.gridy=1;
		c.fill=GridBagConstraints.BOTH;
		c.insets=new Insets(10, 5, 5, 5);
		panelCenterCenter.add(lblConsultantName,c);

		c.gridx=1;
		c.gridy=1;
		c.fill=GridBagConstraints.BOTH;
		c.insets=new Insets(10, 5, 5, 100);
		panelCenterCenter.add(cmbConsultant.cmbSuggest,c);

		c.gridx=0;
		c.gridy=2;
		c.fill=GridBagConstraints.BOTH;
		c.insets=new Insets(10, 5, 5, 5);
		panelCenterCenter.add(lblConsultantFee,c);

		c.gridx=1;
		c.gridy=2;
		c.fill=GridBagConstraints.BOTH;
		c.insets=new Insets(10, 5, 5, 100);
		panelCenterCenter.add(txtConsultantFee,c);
		txtConsultantFee.setPreferredSize(new Dimension(100, 28));

		c.gridx=0;
		c.gridy=3;
		c.fill=GridBagConstraints.BOTH;
		c.insets=new Insets(10, 5, 5, 5);
		panelCenterCenter.add(lblRefConsultant,c);

		c.gridx=1;
		c.gridy=3;
		c.fill=GridBagConstraints.BOTH;
		c.insets=new Insets(10, 5, 5, 100);
		panelCenterCenter.add(cmbRefConsultant.cmbSuggest,c);
	}
	public void panelCenterEast() {
		panelCenterEast.setOpaque(false);
		TitledBorder title=BorderFactory.createTitledBorder("Word Information");
		title.setTitleColor(Color.decode("#8B0000"));
		title.setTitleFont(new Font("Carlibri", Font.BOLD, 14));
		panelCenterEast.setBorder(title);

		GridBagConstraints c=new GridBagConstraints();
		GridBagLayout grid=new GridBagLayout();
		panelCenterEast.setLayout(grid);

		c.gridx=0;
		c.gridy=0;
		c.fill=GridBagConstraints.BOTH;
		c.insets=new Insets(10, 5, 5, 5);
		panelCenterEast.add(lblwordName,c);

		c.gridx=1;
		c.gridy=0;
		c.fill=GridBagConstraints.BOTH;
		c.insets=new Insets(10, 5, 5, 100);
		panelCenterEast.add(cmbWordName.cmbSuggest,c);
		cmbWordName.cmbSuggest.setPreferredSize(new Dimension(250, 28));

		c.gridx=0;
		c.gridy=2;
		c.fill=GridBagConstraints.BOTH;
		c.insets=new Insets(10, 5, 5, 5);
		panelCenterEast.add(lblBedNo,c);

		c.gridx=1;
		c.gridy=2;
		c.fill=GridBagConstraints.BOTH;
		c.insets=new Insets(10, 5, 5, 100);
		panelCenterEast.add(cmbBedNo.cmbSuggest,c);

		c.gridx=0;
		c.gridy=3;
		c.fill=GridBagConstraints.BOTH;
		c.insets=new Insets(10, 5, 5, 5);
		panelCenterEast.add(lblBedCharge,c);

		c.gridx=1;
		c.gridy=3;
		c.fill=GridBagConstraints.BOTH;
		c.insets=new Insets(10, 5, 5, 100);
		panelCenterEast.add(txtBedCharge,c);
	}
	public void panelSouth(){
		panelSouth.setOpaque(false);
		panelSouth.setPreferredSize(new Dimension(0, 100));	
		FlowLayout flow=new FlowLayout();
		panelSouth.setLayout(flow);
		flow.setHgap(15);
		flow.setVgap(20);

		panelSouth.add(btnAdd);
		btnAdd.setPreferredSize(new Dimension(100, 35));	
		btnAdd.setBackground(Color.decode("#46A049"));
		btnAdd.setForeground(Color.WHITE);
		panelSouth.add(btnEdit);
		btnEdit.setPreferredSize(new Dimension(100, 35));
		btnEdit.setBackground(Color.decode("#0B7DDA"));
		btnEdit.setForeground(Color.WHITE);
		panelSouth.add(btnRefresh);
		btnRefresh.setPreferredSize(new Dimension(110, 35));
		btnRefresh.setBackground(Color.decode("#E68A00"));
		btnRefresh.setForeground(Color.WHITE);
		panelSouth.add(btnDelete);
		btnDelete.setPreferredSize(new Dimension(100, 35));
		btnDelete.setBackground(Color.decode("#DA190B"));
		btnDelete.setForeground(Color.WHITE);
	}
}




















