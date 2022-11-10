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
import java.awt.image.BufferedImage;
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
import javax.swing.JTextField;
import javax.swing.Timer;
import javax.swing.border.TitledBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.SimpleAttributeSet;

import com.example.Admin.SuggestText;
import com.example.Admin.dbConneciton;
import com.toedter.calendar.JDateChooser;

import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JRDesignQuery;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;

public class DoctorRegistration extends JPanel{

	JPanel panelWest=new JPanel();
	JPanel panelCenter=new JPanel();
	JPanel panelCenterCenter=new JPanel();
	JPanel panelCenterSouth=new JPanel();
	JPanel panelWestNorth=new JPanel();
	JPanel panelWestCenter=new JPanel();
	JPanel panelWestSouth=new JPanel();
	JPanel panelWestCenterCenter=new JPanel();
	JPanel panelWestCenterEast=new JPanel();

	JLabel lblImage=new JLabel(new ImageIcon("images/backgroundImage4.jpg"));

	String col[]={"DoctorId","DoctorName","Specialist","Timing","Contact No.","Procecdural Fee"};
	Object row[][]={};
	DefaultTableModel model=new DefaultTableModel(row, col);
	JTable table=new JTable(model);
	JScrollPane scroll=new JScrollPane(table, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
			JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

	JLabel lblSearch=new JLabel("Search: ");
	SuggestText cmbSearch=new SuggestText();
	JLabel lblSearchIcon=new JLabel(new ImageIcon("Images/CenterImage.png"));

	JLabel lblUpload=new JLabel();
	ImageIcon iconUpload=new ImageIcon("Images/upload22.png");
	JButton btnUpload=new JButton("Upload",iconUpload);

	JLabel lblDoctorId=new JLabel("DoctorId");
	JLabel lblDoctorName=new JLabel("Doctor Name");
	JLabel lblSex=new JLabel("Gender");
	JLabel lblAge=new JLabel("Age");
	JLabel lblBloodGroup=new JLabel("Blood Group");
	JLabel lblDrQualification=new JLabel("Dr. Qualification");
	JLabel lblSpecialist=new JLabel("Specialist");
	JLabel lblTiming=new JLabel("Timing");
	JLabel lblNid=new JLabel("National Id");
	JLabel lblEmail=new JLabel("Email");
	JLabel lblContact=new JLabel("Contact No.");
	JLabel lblProceduralFee=new JLabel("Procedural Fee");
	JLabel lblJoiningDate=new JLabel("Joining Date");

	JTextField txtDoctorId=new JTextField(20);
	JTextField txtDoctorName=new JTextField();
	SuggestText cmbSex=new SuggestText();
	JTextField txtAge=new JTextField();
	SuggestText cmbBloodGroup=new SuggestText();
	JTextField txtQualification=new JTextField();
	SuggestText cmbSpecialist=new SuggestText();
	JTextField txtTiming=new JTextField();
	JTextField txtNid=new JTextField();
	JTextField txtEmail=new JTextField();
	JTextField txtContact=new JTextField();
	JTextField txtProceduralFee=new JTextField();
	JDateChooser dateJoiningDate=new JDateChooser();

	ImageIcon iconAdd=new ImageIcon("Images/add1.png");
	JButton btnAdd=new JButton("Add",iconAdd);
	ImageIcon iconEdit=new ImageIcon("Images/btnEdit.png");
	JButton btnEdit=new JButton("Edit",iconEdit);
	ImageIcon iconRefresh=new ImageIcon("Images/btnRefresh.png");
	JButton btnRefresh=new JButton("Refresh",iconRefresh);
	ImageIcon iconDelete=new ImageIcon("Images/cancel-icon.png");
	JButton btnDelete=new JButton("Delete",iconDelete);

	JLabel lblDpt=new JLabel("Select Department:");
	ImageIcon iconPreview=new ImageIcon("icons/view.png");
	JButton btnPreview=new JButton("Preview Report",iconPreview);
	SuggestText cmbReport=new SuggestText();

	JFileChooser fileChooser;
	File Imagefile=null;
	String imageStorFolder="F:/HospitalImages";
	BufferedImage buffer=null;
	boolean isUpload=false;

	boolean isUpdate=false;

	public DoctorRegistration(){
		cmp();
		btnAction();
		btnIni(true);
		editable(true);
	}
	public void btnIni(boolean b){
		btnAdd.setEnabled(b);
		btnEdit.setEnabled(!b);
		btnDelete.setEnabled(!b);
		btnUpload.setEnabled(b);
	}
	public void editable(boolean b){
		txtDoctorName.setEditable(b);
		cmbSex.cmbSuggest.setEnabled(b);
		txtAge.setEditable(b);
		cmbBloodGroup.cmbSuggest.setEnabled(b);
		txtQualification.setEditable(b);
		cmbSpecialist.cmbSuggest.setEnabled(b);
		txtTiming.setEditable(b);
		txtNid.setEditable(b);
		txtEmail.setEditable(b);
		txtContact.setEditable(b);
		txtProceduralFee.setEditable(b);
	}
	public void txtClear(){
		txtDoctorId.setText("");
		txtDoctorName.setText("");
		cmbSex.txtSuggest.setText("");
		txtAge.setText("");
		cmbBloodGroup.txtSuggest.setText("");
		txtQualification.setText("");
		cmbSpecialist.txtSuggest.setText("");
		txtTiming.setText("");
		txtNid.setText("");
		txtEmail.setText("");
		txtContact.setText("+88");
		txtProceduralFee.setText("");
		dateJoiningDate.setDate(new Date());
		cmbSearch.txtSuggest.setText("");
		lblUpload.setIcon(new ImageIcon(""));
		cmbReport.txtSuggest.setText("");
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
						if(checkEmail()){
							if(checkMobileNumberLess()){
								if(checkMobileNumberGreater()){
									if(isExistPhone()){
										if(checkConfirmation("sure to save?")){
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
				}
			}
		});
		cmbSearch.cmbSuggest.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(!cmbSearch.txtSuggest.getText().trim().toString().isEmpty()){
					StringTokenizer token=new StringTokenizer(cmbSearch.txtSuggest.getText().trim().
							toString(), "#");
					String doctorId=token.nextToken();
					searchDataLoad(doctorId);
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
				String doctorId=model.getValueAt(table.getSelectedRow(), 0).toString();
				searchDataLoad(doctorId);
				cmbSearchDataLoad();
				cmbSearch.txtSuggest.setText("");
				btnIni(false);
				editable(false);
			}
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
		btnUpload.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				uploadAction();
			}
		});
		btnPreview.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(!cmbReport.txtSuggest.getText().trim().toString().isEmpty()){
					String query=getReportQuery();
					showReport("Report/DoctorReport.jrxml", query);
				}
				else{
					JOptionPane.showMessageDialog(null, "Select department please.");
				}
			}
		});
	}
	public void showReport(String jrxml, String query){
		try {
			dbConneciton.connection();
			JasperDesign jd=JRXmlLoader.load(jrxml);
			JRDesignQuery jrq=new JRDesignQuery();
			jrq.setText(query);
			jd.setQuery(jrq);
			JasperReport jr=JasperCompileManager.compileReport(jd);
			JasperPrint jp=JasperFillManager.fillReport(jr, null, dbConneciton.con);
			JasperViewer.viewReport(jp, false);
			dbConneciton.con.close();
		} 
		catch (Exception e) {
			JOptionPane.showMessageDialog(null, e+"showReport()");
		}
	}
	public String getReportQuery(){	
		String query="";
		if(cmbReport.txtSuggest.getText().trim().toString().equals("All")){
			query="select doctorId,doctorName,drQualification,specialist,proceduralFee,contactNo "
					+ "from tbdoctorinfo order by specialist";
		}
		else if(cmbReport.txtSuggest.getText().trim().toString().equals("Cardiologist")){
			String specilist=cmbReport.txtSuggest.getText().trim().toString();
			query="select doctorId,doctorName,drQualification,specialist,proceduralFee,contactNo "
					+ "from tbdoctorinfo where specialist='"+specilist+"' order by specialist";
		}
		else if(cmbReport.txtSuggest.getText().trim().toString().equals("Nurologist")){
			String specilist=cmbReport.txtSuggest.getText().trim().toString();
			query="select doctorId,doctorName,drQualification,specialist,proceduralFee,contactNo "
					+ "from tbdoctorinfo where specialist='"+specilist+"' order by specialist";
		}
		else if(cmbReport.txtSuggest.getText().trim().toString().equals("Gastrologist")){
			String specilist=cmbReport.txtSuggest.getText().trim().toString();
			query="select doctorId,doctorName,drQualification,specialist,proceduralFee,contactNo "
					+ "from tbdoctorinfo where specialist='"+specilist+"' order by specialist";
		}
		else if(cmbReport.txtSuggest.getText().trim().toString().equals("Psychologist")){
			String specilist=cmbReport.txtSuggest.getText().trim().toString();
			query="select doctorId,doctorName,drQualification,specialist,proceduralFee,contactNo "
					+ "from tbdoctorinfo where specialist='"+specilist+"' order by specialist";
		}
		else if(cmbReport.txtSuggest.getText().trim().toString().equals("Radiologist")){
			String specilist=cmbReport.txtSuggest.getText().trim().toString();
			query="select doctorId,doctorName,drQualification,specialist,proceduralFee,contactNo "
					+ "from tbdoctorinfo where specialist='"+specilist+"' order by specialist";
		}
		else if(cmbReport.txtSuggest.getText().trim().toString().equals("Hematologist")){
			String specilist=cmbReport.txtSuggest.getText().trim().toString();
			query="select doctorId,doctorName,drQualification,specialist,proceduralFee,contactNo "
					+ "from tbdoctorinfo where specialist='"+specilist+"' order by specialist";
		}
		else if(cmbReport.txtSuggest.getText().trim().toString().equals("Gynocologist")){
			String specilist=cmbReport.txtSuggest.getText().trim().toString();
			query="select doctorId,doctorName,drQualification,specialist,proceduralFee,contactNo "
					+ "from tbdoctorinfo where specialist='"+specilist+"' order by specialist";
		}
		return query;
	}
	public void uploadAction(){
		fileChooser=new JFileChooser();
		fileChooser.setDialogTitle("Image Selection");
		FileNameExtensionFilter filter=new FileNameExtensionFilter("image","jpg","PNG","gif","jpeg");
		fileChooser.setFileFilter(filter);
		fileChooser.showOpenDialog(this);
		Imagefile=fileChooser.getSelectedFile();
		if(Imagefile!=null){
			Image image=Toolkit.getDefaultToolkit().getImage(Imagefile.getPath());
			Image resize=image.getScaledInstance(lblUpload.getWidth(),lblUpload.getHeight(),
					Image.SCALE_DEFAULT);
			ImageIcon imageIcon=new ImageIcon(resize);
			lblUpload.setIcon(imageIcon);
			isUpload=true;	
		}
	}
	public void autoId(){
		try {
			String sql="select ifnull(max(cast(substring(doctorId,locate('-',doctorId)+1,"
					+ "length(doctorId)-locate('-',doctorId)) as UNSIGNED)),0)+1 as id "
					+ "from tbdoctorinfo";
			dbConneciton.connection();
			ResultSet rs=dbConneciton.sta.executeQuery(sql);
			while(rs.next()){
				txtDoctorId.setText("DoctorId-"+rs.getString("id"));
			}
			dbConneciton.con.close();
		} 
		catch (Exception e) {
			JOptionPane.showMessageDialog(null, e+"autoId()");
		}
	}
	public boolean checkValidation(){
		if(!txtDoctorId.getText().trim().toString().isEmpty()){
			if(!txtDoctorName.getText().trim().toString().isEmpty()){
				if(!cmbSex.txtSuggest.getText().trim().toString().isEmpty()){
					if(!txtAge.getText().trim().toString().isEmpty()){
						if(!cmbBloodGroup.txtSuggest.getText().trim().toString().isEmpty()){
							if(!txtQualification.getText().trim().toString().isEmpty()){
								if(!cmbSpecialist.txtSuggest.getText().trim().toString().isEmpty()){
									if(!txtTiming.getText().trim().toString().isEmpty()){
										if(!txtNid.getText().trim().toString().isEmpty()){
											if(!txtEmail.getText().trim().toString().isEmpty()){
												if(!txtContact.getText().trim().toString().isEmpty()){
													if(!txtProceduralFee.getText().trim().toString().isEmpty()){
														return true;
													}
													else{
														JOptionPane.showMessageDialog(null, "insert procedural fee please.");
													}
												}
												else{
													JOptionPane.showMessageDialog(null, "insert contact No. please.");
												}
											}
											else{
												JOptionPane.showMessageDialog(null, "insert procedural fee please.");
											}
										}
										else{
											JOptionPane.showMessageDialog(null, "insert contact No. please.");
										}
									}
									else{
										JOptionPane.showMessageDialog(null, "insert timing please.");
									}
								}
								else{
									JOptionPane.showMessageDialog(null, "select specialist please.");
								}
							}
							else{
								JOptionPane.showMessageDialog(null, "insert qualification please.");
							}
						}
						else{
							JOptionPane.showMessageDialog(null, "select blood group please.");
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
				JOptionPane.showMessageDialog(null, "insert doctor name please.");
			}
		}
		else{
			JOptionPane.showMessageDialog(null, "insert doctor id please.");
		}
		return false;
	}
	public boolean isExistPhone(){
		try {
			String sql="select * from tbdoctorinfo where contactNo='"+txtContact.getText().trim().toString()+"'";
			dbConneciton.connection();
			ResultSet rs=dbConneciton.sta.executeQuery(sql);
			while(rs.next()){
				JOptionPane.showMessageDialog(null, "This Number already exist!","Warning...",
						JOptionPane.WARNING_MESSAGE);
				return false;
			}
			dbConneciton.con.close();
		} 
		catch (Exception e) {
			JOptionPane.showMessageDialog(null, e+"isExistPhone()");
		}
		return true;
	}
	public boolean checkEmail(){
		String email_format="^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*"
				+ "@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
		Pattern email_pattern=Pattern.compile(email_format);
		Matcher email_match=email_pattern.matcher(txtEmail.getText().trim());
		if(!email_match.matches()){
			JOptionPane.showMessageDialog(null, "Email Format is not correct."
					+ "please write Mail Address like this format (abcde12@gmail.com)","Warning",
					JOptionPane.WARNING_MESSAGE);
			return false;
		}
		return true;
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
			String joinDate=new SimpleDateFormat("yyyy-MM-dd").format(dateJoiningDate.getDate());

			if(isUpload){
				File StoreFile=new File(imageStorFolder);
				if(!StoreFile.isDirectory()){
					StoreFile.mkdirs();
				}
				buffer=ImageIO.read(Imagefile);
				File ImageNameFile=new File(imageStorFolder+"/"+txtDoctorId.getText()+".jpg");
				if(ImageNameFile.exists()){
					ImageNameFile.delete();
				}
				ImageIO.write(buffer,"jpg",ImageNameFile);
			}
			String dbPicture=isUpload?txtDoctorId.getText().trim()+".jpg":"";

			String sql="insert into tbdoctorinfo(doctorId,doctorName,Gender,age,bloodGroup,drQualification,"
					+ "specialist,timing,nationalId,email,contactNo,proceduralFee,joiningDate,userName,"
					+ "picture,userip,entryTime)"
					+ "values('"+txtDoctorId.getText().trim().toString()+"',"
					+ "'"+txtDoctorName.getText().trim().toString()+"',"
					+ "'"+cmbSex.txtSuggest.getText().trim().toString()+"',"
					+ "'"+txtAge.getText().trim().toString()+"',"
					+ "'"+cmbBloodGroup.txtSuggest.getText().trim().toString()+"',"
					+ "'"+txtQualification.getText().trim().toString()+"',"
					+ "'"+cmbSpecialist.txtSuggest.getText().trim().toString()+"',"
					+ "'"+txtTiming.getText().trim().toString()+"',"
					+ "'"+txtNid.getText().trim().toString()+"',"
					+ "'"+txtEmail.getText().trim().toString()+"',"
					+ "'"+txtContact.getText().trim().toString()+"',"
					+ "'"+txtProceduralFee.getText().trim().toString()+"',"
					+ "'"+joinDate+"','',"
					+ "'"+dbPicture+"','',now())";

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
			String sql="select doctorId,doctorName,specialist,timing,contactNo,proceduralFee "
					+ "from tbdoctorinfo";
			dbConneciton.connection();
			ResultSet rs=dbConneciton.sta.executeQuery(sql);
			while(rs.next()){
				model.addRow(new Object[]{rs.getString("doctorId"),rs.getString("doctorName"),
						rs.getString("specialist"),rs.getString("timing"),rs.getString("contactNo"),
						rs.getString("proceduralFee")});
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
			String sql="select doctorId,doctorName from tbdoctorinfo";
			dbConneciton.connection();
			ResultSet rs=dbConneciton.sta.executeQuery(sql);
			while(rs.next()){
				cmbSearch.v.add(rs.getString("doctorId")+"#"+rs.getString("doctorName"));
			}
			dbConneciton.con.close();
		} 
		catch (Exception e) {
			JOptionPane.showMessageDialog(null, e+"cmbSearchDataLoad()");
		}
	}
	public void searchDataLoad(String doctorId){
		try {
			String sql="select doctorId,doctorName,Gender,age,bloodGroup,drQualification,specialist,timing,"
					+ "nationalId,email,contactNo,proceduralFee,joiningDate,picture from tbdoctorinfo"
					+ " where doctorId like '"+doctorId+"'";
			dbConneciton.connection();
			System.out.println(sql);
			ResultSet rs=dbConneciton.sta.executeQuery(sql);
			if(rs.next()){
				txtDoctorId.setText(rs.getString("doctorId"));
				txtDoctorName.setText(rs.getString("doctorName"));
				cmbSex.txtSuggest.setText(rs.getString("Gender"));
				txtAge.setText(rs.getString("age"));
				cmbBloodGroup.txtSuggest.setText(rs.getString("bloodGroup"));
				txtQualification.setText(rs.getString("drQualification"));
				cmbSpecialist.txtSuggest.setText(rs.getString("specialist"));
				txtTiming.setText(rs.getString("timing"));
				txtNid.setText(rs.getString("nationalId"));
				txtEmail.setText(rs.getString("email"));
				txtContact.setText(rs.getString("contactNo"));
				txtProceduralFee.setText(rs.getString("proceduralFee"));
				dateJoiningDate.setDate(rs.getDate("joiningDate"));
			}
			if(!rs.getString("picture").trim().isEmpty()){
				Imagefile=new File(imageStorFolder+"/"+rs.getString("picture"));
				Image image=Toolkit.getDefaultToolkit().getImage(Imagefile.getPath());
				Image resize=image.getScaledInstance(lblUpload.getWidth(),lblUpload.getHeight(),
						Image.SCALE_DEFAULT);
				ImageIcon imageIcon=new ImageIcon(resize);
				lblUpload.setIcon(imageIcon);
				isUpload=true;	
			}
			else{
				lblUpload.setIcon(new ImageIcon(""));
			}
			dbConneciton.con.close();
		} 
		catch (Exception e) {
			JOptionPane.showMessageDialog(null, e+" searchDataLoad()");
		}
	}
	public boolean deleteData(){
		try {
			String imagepath=imageStorFolder+"/"+txtDoctorId.getText().trim()+".jpg";
			File imageDeleteFile=new File(imagepath);
			imageDeleteFile.delete();

			String sql="delete from tbdoctorinfo where "
					+ "doctorId='"+txtDoctorId.getText().trim().toString()+"'";
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
		TitledBorder title=BorderFactory.createTitledBorder("Doctor Registration Form");
		title.setTitleColor(Color.decode("#8B0000"));
		title.setTitleFont(new Font("Carlibri", Font.BOLD, 14));
		panelWest.setBorder(title);

		panelWest.setPreferredSize(new Dimension(680, 0));
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
		panelWestCenter.setLayout(new BorderLayout());
		panelWestCenter.add(panelWestCenterCenter,BorderLayout.CENTER);
		panelWestCenterCenter();
		panelWestCenter.add(panelWestCenterEast, BorderLayout.EAST);
		panelWestCenterEast();
	}
	private void panelWestCenterCenter() {
		panelWestCenterCenter.setOpaque(false);
		GridBagConstraints c=new GridBagConstraints();
		GridBagLayout grid=new GridBagLayout();
		panelWestCenterCenter.setLayout(grid);
		c.fill=GridBagConstraints.BOTH;
		c.insets=new Insets(5, 5, 5, 5);

		c.gridx=0;
		c.gridy=0;
		panelWestCenterCenter.add(lblDoctorId,c);
		lblDoctorId.setFont(new Font("", Font.BOLD, 15));
		lblDoctorId.setForeground(Color.decode("#E8F2FE"));

		c.gridx=1;
		c.gridy=0;
		panelWestCenterCenter.add(txtDoctorId,c);
		txtDoctorId.setEditable(false);

		c.gridx=0;
		c.gridy=1;
		panelWestCenterCenter.add(lblDoctorName,c);

		c.gridx=1;
		c.gridy=1;
		panelWestCenterCenter.add(txtDoctorName,c);

		c.gridx=0;
		c.gridy=2;
		panelWestCenterCenter.add(lblSex,c);

		c.gridx=1;
		c.gridy=2;
		panelWestCenterCenter.add(cmbSex.cmbSuggest,c);
		cmbSex.v.add("");
		cmbSex.v.add("Male");
		cmbSex.v.add("Female");

		c.gridx=0;
		c.gridy=3;
		panelWestCenterCenter.add(lblAge,c);

		c.gridx=1;
		c.gridy=3;
		panelWestCenterCenter.add(txtAge,c);

		c.gridx=0;
		c.gridy=4;
		panelWestCenterCenter.add(lblBloodGroup,c);

		c.gridx=1;
		c.gridy=4;
		panelWestCenterCenter.add(cmbBloodGroup.cmbSuggest,c);
		cmbBloodGroup.v.add("");
		cmbBloodGroup.v.add("A+");
		cmbBloodGroup.v.add("B+");
		cmbBloodGroup.v.add("AB+");
		cmbBloodGroup.v.add("O+");
		cmbBloodGroup.v.add("A-");
		cmbBloodGroup.v.add("B-");
		cmbBloodGroup.v.add("AB-");
		cmbBloodGroup.v.add("O-");

		c.gridx=0;
		c.gridy=5;
		panelWestCenterCenter.add(lblDrQualification,c);

		c.gridx=1;
		c.gridy=5;
		panelWestCenterCenter.add(txtQualification,c);

		c.gridx=0;
		c.gridy=6;
		panelWestCenterCenter.add(lblSpecialist,c);

		c.gridx=1;
		c.gridy=6;
		panelWestCenterCenter.add(cmbSpecialist.cmbSuggest,c);
		cmbSpecialist.v.add("");
		cmbSpecialist.v.add("Cardiologist");
		cmbSpecialist.v.add("Nurologist");
		cmbSpecialist.v.add("Gastrologist");
		cmbSpecialist.v.add("Psychologist");
		cmbSpecialist.v.add("Radiologist");
		cmbSpecialist.v.add("Hematologist");
		cmbSpecialist.v.add("Gynocologist");

		c.gridx=0;
		c.gridy=7;
		panelWestCenterCenter.add(lblTiming,c);

		c.gridx=1;
		c.gridy=7;
		panelWestCenterCenter.add(txtTiming,c);	

		c.gridx=0;
		c.gridy=8;
		panelWestCenterCenter.add(lblNid,c);

		c.gridx=1;
		c.gridy=8;
		panelWestCenterCenter.add(txtNid,c);

		c.gridx=0;
		c.gridy=9;
		panelWestCenterCenter.add(lblEmail,c);

		c.gridx=1;
		c.gridy=9;
		panelWestCenterCenter.add(txtEmail,c);

		c.gridx=0;
		c.gridy=10;
		panelWestCenterCenter.add(lblContact,c);

		c.gridx=1;
		c.gridy=10;
		panelWestCenterCenter.add(txtContact,c);
		txtContact.setText("+88");

		c.gridx=0;
		c.gridy=11;
		panelWestCenterCenter.add(lblProceduralFee,c);

		c.gridx=1;
		c.gridy=11;
		panelWestCenterCenter.add(txtProceduralFee,c);

		c.gridx=0;
		c.gridy=12;
		panelWestCenterCenter.add(lblJoiningDate,c);

		c.gridx=1;
		c.gridy=12;
		panelWestCenterCenter.add(dateJoiningDate,c);
		dateJoiningDate.setOpaque(false);
		dateJoiningDate.setDate(new Date());
		dateJoiningDate.setDateFormatString("dd-MM-yyyy");
	}
	private void panelWestCenterEast() {
		panelWestCenterEast.setOpaque(false);
		panelWestCenterEast.setPreferredSize(new Dimension(180, 0));
		GridBagConstraints cn=new GridBagConstraints();
		GridBagLayout grid=new GridBagLayout();
		panelWestCenterEast.setLayout(grid);

		cn.gridx=0;
		cn.gridy=0;
		cn.fill=GridBagConstraints.BOTH;
		cn.insets=new Insets(5, 5, 5, 15);
		panelWestCenterEast.add(lblUpload,cn);
		lblUpload.setPreferredSize(new Dimension(142, 165));
		lblUpload.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));

		cn.gridx=0;
		cn.gridy=1;
		cn.fill=GridBagConstraints.BOTH;
		cn.insets=new Insets(5, 20, 201, 30);
		panelWestCenterEast.add(btnUpload,cn);
		btnUpload.setPreferredSize(new Dimension(80, 35)); 
		btnUpload.setBackground(Color.decode("#B8CFE5"));
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
		panelCenter.setOpaque(false);
		panelCenter.setLayout(new BorderLayout());
		panelCenter.add(panelCenterCenter,BorderLayout.CENTER);
		panelCenterCenter();
		panelCenter.add(panelCenterSouth, BorderLayout.SOUTH);
		panelCenterSouth();
	}
	public void panelCenterCenter(){
		panelCenterCenter.setOpaque(false);
		TitledBorder title=BorderFactory.createTitledBorder("Doctor List");
		title.setTitleColor(Color.decode("#8B0000"));
		title.setTitleFont(new Font("Carlibri", Font.BOLD, 14));
		panelCenterCenter.setBorder(title);
		panelCenterCenter.setOpaque(false);
		GridBagConstraints cn=new GridBagConstraints();
		GridBagLayout grid=new GridBagLayout();
		panelCenterCenter.setLayout(grid);

		cn.gridx=0;
		cn.gridy=0;
		cn.fill=GridBagConstraints.BOTH;
		cn.insets=new Insets(5, 5, 5, 5);
		panelCenterCenter.add(scroll,cn);
		scroll.setPreferredSize(new Dimension(640,590));
		table.getTableHeader().setReorderingAllowed(false);
	}
	public void panelCenterSouth(){
		panelCenterSouth.setOpaque(false);
		panelCenterSouth.setPreferredSize(new Dimension(0, 70));
		FlowLayout flow=new FlowLayout();
		panelCenterSouth.setLayout(flow);
		flow.setHgap(15);

		panelCenterSouth.add(lblDpt);

		panelCenterSouth.add(cmbReport.cmbSuggest);
		cmbReport.cmbSuggest.setPreferredSize(new Dimension(180, 32));
		cmbReport.v.add("");
		cmbReport.v.add("All");
		cmbReport.v.add("Cardiologist");
		cmbReport.v.add("Nurologist");
		cmbReport.v.add("Gastrologist");
		cmbReport.v.add("Psychologist");
		cmbReport.v.add("Radiologist");
		cmbReport.v.add("Hematologist");
		cmbReport.v.add("Gynocologist");

		panelCenterSouth.add(btnPreview);
		btnPreview.setPreferredSize(new Dimension(160, 35));
		btnPreview.setBackground(Color.decode("#E68A00"));
		btnPreview.setForeground(Color.WHITE);
	}
}
