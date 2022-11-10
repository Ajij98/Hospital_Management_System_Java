package com.EmployeeInfo;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.JobAttributes;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.lang.ref.Reference;
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
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;

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

public class EmployeeReg extends JPanel{

	JPanel panelCenter=new JPanel();
	JPanel panelCenterCenter=new JPanel();
	JPanel panelCenterSouth=new JPanel();
	JPanel panelWest=new JPanel();
	JPanel panelWestNorth=new JPanel();
	JPanel panelWestCenter=new JPanel();
	JPanel panelWestSouth=new JPanel();
	JPanel panelWestCenterCenter=new JPanel();
	JPanel panelWestCenterEast=new JPanel();

	JLabel lblImage=new JLabel(new ImageIcon("images/backgroundImage4.jpg"));

	JLabel lblSearch=new JLabel("Search: ");
	SuggestText cmbSearch=new SuggestText();
	JLabel lblSearchIcon=new JLabel(new ImageIcon("Images/CenterImage.png"));

	ImageIcon iconAdd=new ImageIcon("Images/add1.png");
	JButton btnAdd=new JButton("Add",iconAdd);
	ImageIcon iconEdit=new ImageIcon("Images/btnEdit.png");
	JButton btnEdit=new JButton("Edit",iconEdit);
	ImageIcon iconRefresh=new ImageIcon("Images/btnRefresh.png");
	JButton btnRefresh=new JButton("Refresh",iconRefresh);
	ImageIcon iconDelete=new ImageIcon("Images/cancel-icon.png");
	JButton btnDelete=new JButton("Delete",iconDelete);

	JLabel lblUpload=new JLabel();
	ImageIcon iconUpload=new ImageIcon("Images/upload22.png");
	JButton btnUpload=new JButton("Upload",iconUpload);

	JLabel lblEmployeeId=new JLabel("Employee Id");
	JLabel lblEmployeeName=new JLabel("Employee Name");
	JLabel lblGender=new JLabel("Gender");
	JLabel lblDesignation=new JLabel("Designation");
	JLabel lblSalary=new JLabel("Salary");
	JLabel lblPhone=new JLabel("Phone");
	JLabel lblEmail=new JLabel("Email");
	JLabel lblAddress=new JLabel("Address");
	JLabel lblJoiningDate=new JLabel("Join Date");
	JLabel lblReference=new JLabel("Reference");
	Font font=new Font("clibari", Font.BOLD, 12);

	JTextField txtEmployeeId=new JTextField(20);
	JTextField txtEmployeeName=new JTextField();
	SuggestText cmbGender=new SuggestText();
	SuggestText cmbDesignation=new SuggestText();
	JTextField txtSalary=new JTextField();
	JTextField txtPhone=new JTextField();
	JTextField txtEmail=new JTextField();
	JTextArea txtAddress=new JTextArea(3, 20);
	JDateChooser dateJoinDate=new JDateChooser();
	SuggestText cmbReference=new SuggestText();
	JScrollPane scrollAddress=new JScrollPane(txtAddress,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
			JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

	JLabel lblDpt=new JLabel("Employee Department:");
	ImageIcon iconPreview=new ImageIcon("icons/view.png");
	JButton btnPreview=new JButton("Preview Report",iconPreview);
	SuggestText cmbReport=new SuggestText();

	String col[]={"Employee ID","Employee Name","Designation","Phone","Email","Join Date"};
	Object row[][]={};
	DefaultTableModel model=new DefaultTableModel(row, col)
	{
		public boolean isCellEditable(int row,int col){
			return false;
		}
	};
	JTable table=new JTable(model);
	JScrollPane scrollTable=new JScrollPane(table,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
			JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

	SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd");

	boolean isUpdate=false;

	JFileChooser fileChooser;
	File Imagefile=null;
	String imageStorFolder="F:/HospitalImages";
	BufferedImage buffer=null;
	boolean isUpload=false;

	public EmployeeReg() {
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
		txtEmployeeName.setEditable(b);
		cmbGender.cmbSuggest.setEnabled(b);
		cmbDesignation.cmbSuggest.setEnabled(b);
		txtSalary.setEnabled(b);
		txtPhone.setEditable(b);
		txtEmail.setEnabled(b);
		txtAddress.setEditable(b);
		dateJoinDate.setEnabled(b);
		cmbReference.cmbSuggest.setEnabled(b);
	}
	public void txtClear(){
		txtEmployeeName.setText("");
		cmbGender.txtSuggest.setText("");
		cmbDesignation.txtSuggest.setText("");
		txtSalary.setText("");
		txtPhone.setText("+88");
		txtEmail.setText("");
		txtAddress.setText("");
		dateJoinDate.setDate(new Date());
		cmbReference.txtSuggest.setText("");
		lblUpload.setIcon(new ImageIcon(""));
		cmbSearch.txtSuggest.setText("");
		cmbReport.txtSuggest.setText("");
	}
	public void refreshWork(){
		txtClear();
		btnIni(true);
		editable(true);
		autoId();
		tableDataLoad();
		cmbSearchDataLoad();
		cmbReferenceDataLoad();
		isUpdate=false;
		isUpload=false;
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
						if(checkMobileNumberLess()){
							if(checkMobileNumberGreater()){
								if(checkEmail()){
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
		});
		cmbSearch.cmbSuggest.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(!cmbSearch.txtSuggest.getText().trim().toString().isEmpty()){
					StringTokenizer token=new StringTokenizer(cmbSearch.txtSuggest.getText().trim().
							toString(), "#");
					String employeeId=token.nextToken();
					searchDataLoad(employeeId);
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
				String employeeId=model.getValueAt(table.getSelectedRow(), 0).toString();
				searchDataLoad(employeeId);
				cmbSearchDataLoad();
				cmbSearch.txtSuggest.setText("");
				btnIni(false);
				editable(false);
			}
		});
		txtPhone.addKeyListener(new KeyListener() {
			public void keyTyped(KeyEvent e) {
				char ch=e.getKeyChar();
				if(!(Character.isDigit(ch)) || (ch==KeyEvent.VK_BACK_SPACE) || (ch==KeyEvent.VK_DELETE)){
					getToolkit().beep();
					e.consume();
				}
			}
			public void keyReleased(KeyEvent e) {
				if(txtPhone.getText().trim().toString().length()<3){
					txtPhone.setText("+88");
				}
			}
			public void keyPressed(KeyEvent e) {
			}
		});
		txtSalary.addKeyListener(new KeyListener() {
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
		btnUpload.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				uploadAction();
			}
		});
		btnPreview.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(!cmbReport.txtSuggest.getText().trim().toString().isEmpty()){
					String query=getReportQuery();
					showReport("Report/EmployeeReport.jrxml", query);
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
			query="select employeeId,employeeName,designation,salary,phone,address from tbemployeeinfo";
		}
		else if(cmbReport.txtSuggest.getText().trim().toString().equals("Physician(surgon,Hospitalist)")){
			String department=cmbReport.txtSuggest.getText().trim().toString();
			query="select employeeId,employeeName,designation,salary,phone,address from "
					+ "tbemployeeinfo where designation='"+department+"'";
		}
		else if(cmbReport.txtSuggest.getText().trim().toString().equals("Nurse")){
			String department=cmbReport.txtSuggest.getText().trim().toString();
			query="select employeeId,employeeName,designation,salary,phone,address from "
					+ "tbemployeeinfo where designation='"+department+"'";
		}
		else if(cmbReport.txtSuggest.getText().trim().toString().equals("Techs")){
			String department=cmbReport.txtSuggest.getText().trim().toString();
			query="select employeeId,employeeName,designation,salary,phone,address from "
					+ "tbemployeeinfo where designation='"+department+"'";
		}
		else if(cmbReport.txtSuggest.getText().trim().toString().equals("Therapist")){
			String department=cmbReport.txtSuggest.getText().trim().toString();
			query="select employeeId,employeeName,designation,salary,phone,address from "
					+ "tbemployeeinfo where designation='"+department+"'";
		}
		else if(cmbReport.txtSuggest.getText().trim().toString().equals("Medical Assistants")){
			String department=cmbReport.txtSuggest.getText().trim().toString();
			query="select employeeId,employeeName,designation,salary,phone,address from "
					+ "tbemployeeinfo where designation='"+department+"'";;
		}
		else if(cmbReport.txtSuggest.getText().trim().toString().equals("Medical Laboratory Technologist")){
			String department=cmbReport.txtSuggest.getText().trim().toString();
			query="select employeeId,employeeName,designation,salary,phone,address from "
					+ "tbemployeeinfo where designation='"+department+"'";
		}
		else if(cmbReport.txtSuggest.getText().trim().toString().equals("Accountant")){
			String department=cmbReport.txtSuggest.getText().trim().toString();
			query="select employeeId,employeeName,designation,salary,phone,address from "
					+ "tbemployeeinfo where designation='"+department+"'";
		}
		else if(cmbReport.txtSuggest.getText().trim().toString().equals("Human Resources & Recruiting")){
			String department=cmbReport.txtSuggest.getText().trim().toString();
			query="select employeeId,employeeName,designation,salary,phone,address from "
					+ "tbemployeeinfo where designation='"+department+"'";
		}
		else if(cmbReport.txtSuggest.getText().trim().toString().equals("Executive(CEO,CFO,CIO)")){
			String department=cmbReport.txtSuggest.getText().trim().toString();
			query="select employeeId,employeeName,designation,salary,phone,address from "
					+ "tbemployeeinfo where designation='"+department+"'";
		}
		else if(cmbReport.txtSuggest.getText().trim().toString().equals("Food Servicec")){
			String department=cmbReport.txtSuggest.getText().trim().toString();
			query="select employeeId,employeeName,designation,salary,phone,address from "
					+ "tbemployeeinfo where designation='"+department+"'";
		}
		else if(cmbReport.txtSuggest.getText().trim().toString().equals("Environmental Service")){
			String department=cmbReport.txtSuggest.getText().trim().toString();
			query="select employeeId,employeeName,designation,salary,phone,address from "
					+ "tbemployeeinfo where designation='"+department+"'";;
		}
		else if(cmbReport.txtSuggest.getText().trim().toString().equals("Administrative Assistant")){
			String department=cmbReport.txtSuggest.getText().trim().toString();
			query="select employeeId,employeeName,designation,salary,phone,address from "
					+ "tbemployeeinfo where designation='"+department+"'";
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
			String sql="select ifnull(max(cast(substring(employeeId,locate('-',employeeId)+1,"
					+ "length(employeeId)-locate('-',employeeId)) as UNSIGNED)),0)+1 as id "
					+ "from tbEmployeeinfo";
			dbConneciton.connection();
			ResultSet rs=dbConneciton.sta.executeQuery(sql);
			while(rs.next()){
				txtEmployeeId.setText("EmployeeId-"+rs.getString("id"));
			}
			dbConneciton.con.close();
		} 
		catch (Exception e) {
			JOptionPane.showMessageDialog(null, e+"autoId()");
		}
	}
	public boolean checkValidation(){
		if(!txtEmployeeId.getText().trim().toString().isEmpty()){
			if(!txtEmployeeName.getText().trim().toString().isEmpty()){
				if(!cmbGender.txtSuggest.getText().trim().toString().isEmpty()){
					if(!cmbDesignation.txtSuggest.getText().trim().toString().isEmpty()){
						if(!txtSalary.getText().trim().toString().isEmpty()){
							if(!txtPhone.getText().trim().toString().isEmpty()){
								if(!txtEmail.getText().trim().toString().isEmpty()){
									if(!txtAddress.getText().trim().toString().isEmpty()){
										return true;
									}
									else{
										JOptionPane.showMessageDialog(null, "insert Address please.");
									}
								}
								else{
									JOptionPane.showMessageDialog(null, "insert Email please.");
								}
							}
							else{
								JOptionPane.showMessageDialog(null, "insert Phone please.");
							}
						}
						else{
							JOptionPane.showMessageDialog(null, "insert Salary please.");
						}
					}
					else{
						JOptionPane.showMessageDialog(null, "insert Designation please.");
					}
				}
				else{
					JOptionPane.showMessageDialog(null, "select Gender please.");
				}
			}
			else{
				JOptionPane.showMessageDialog(null, "insert Employee Name please.");
			}
		}
		else{
			JOptionPane.showMessageDialog(null, "insert Employee Id please.");
		}
		return false;
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
		if(txtPhone.getText().trim().toString().length()<14){
			JOptionPane.showMessageDialog(null, "Mobile Number less then 11 digit...","Warning",
					JOptionPane.WARNING_MESSAGE);
			return false;
		}
		else{
			return true;
		}
	}
	public boolean checkMobileNumberGreater(){
		if(txtPhone.getText().trim().toString().length()>14){
			JOptionPane.showMessageDialog(null, "Mobile Number greater then 11 digit...","Warning",
					JOptionPane.WARNING_MESSAGE);
			return false;
		}
		else{
			return true;
		}
	}
	public void cmbReferenceDataLoad(){
		try {
			cmbReference.v.clear();
			cmbReference.v.add("");
			String sql="select employeeId,employeeName from tbEmployeeinfo";
			dbConneciton.connection();
			ResultSet rs=dbConneciton.sta.executeQuery(sql);
			while(rs.next()){
				cmbReference.v.add(rs.getString("employeeId")+"#"+rs.getString("employeeName"));
			}
			dbConneciton.con.close();
		} 
		catch (Exception e) {
			JOptionPane.showMessageDialog(null, e+"cmbReferenceDataLoad()");
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
			String joinDate=new SimpleDateFormat("yyyy-MM-dd").format(dateJoinDate.getDate());

			if(isUpload){
				File StoreFile=new File(imageStorFolder);
				if(!StoreFile.isDirectory()){
					StoreFile.mkdirs();
				}
				buffer=ImageIO.read(Imagefile);
				File ImageNameFile=new File(imageStorFolder+"/"+txtEmployeeId.getText()+".jpg");
				if(ImageNameFile.exists()){
					ImageNameFile.delete();
				}
				ImageIO.write(buffer,"jpg",ImageNameFile);
			}
			String dbPicture=isUpload?txtEmployeeId.getText().trim()+".jpg":"";

			String refId="";
			String refName="";

			if(!cmbReference.txtSuggest.getText().trim().isEmpty()){
				StringTokenizer token=new StringTokenizer(cmbReference.txtSuggest.getText().trim().
						toString(), "#");
				refId=token.nextToken();
				refName=token.nextToken();
			}

			String sql="insert into tbEmployeeinfo(employeeId,employeeName,gender,designation,salary,"
					+ "phone,email,address,joinDate,referenceId,referenceName,picture,userip,entryTime)"
					+ "values('"+txtEmployeeId.getText().trim().toString()+"',"
					+ "'"+txtEmployeeName.getText().trim().toString()+"',"
					+ "'"+cmbGender.txtSuggest.getText().trim().toString()+"',"
					+ "'"+cmbDesignation.txtSuggest.getText().trim().toString()+"',"
					+ "'"+txtSalary.getText().trim().toString()+"',"
					+ "'"+txtPhone.getText().trim().toString()+"',"
					+ "'"+txtEmail.getText().trim().toString()+"',"
					+ "'"+txtAddress.getText().trim().toString()+"',"
					+ "'"+joinDate+"',"
					+ "'"+refId+"',"
					+ "'"+refName+"',"
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
			String sql="select employeeId,employeeName,designation,phone,email,joinDate "
					+ "from tbEmployeeinfo";
			dbConneciton.connection();
			ResultSet rs=dbConneciton.sta.executeQuery(sql);
			while(rs.next()){
				model.addRow(new Object[]{rs.getString("employeeId"),rs.getString("employeeName"),
						rs.getString("designation"),rs.getString("phone"),rs.getString("email"),
						rs.getString("joinDate")});
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
			String sql="select employeeId,employeeName from tbEmployeeinfo";
			dbConneciton.connection();
			ResultSet rs=dbConneciton.sta.executeQuery(sql);
			while(rs.next()){
				cmbSearch.v.add(rs.getString("employeeId")+"#"+rs.getString("employeeName"));
			}
			dbConneciton.con.close();
		} 
		catch (Exception e) {
			JOptionPane.showMessageDialog(null, e+"cmbSearchDataLoad()");
		}
	}
	public void searchDataLoad(String employeeId){
		try {
			String sql="select employeeId,employeeName,gender,designation,salary,"
					+ "phone,email,address,joinDate,referenceId,referenceName,picture from tbEmployeeinfo"
					+ " where employeeId like '"+employeeId+"'";
			dbConneciton.connection();
			System.out.println(sql);
			ResultSet rs=dbConneciton.sta.executeQuery(sql);
			if(rs.next()){
				txtEmployeeId.setText(rs.getString("employeeId"));
				txtEmployeeName.setText(rs.getString("employeeName"));
				cmbGender.txtSuggest.setText(rs.getString("gender"));
				cmbDesignation.txtSuggest.setText(rs.getString("designation"));
				txtSalary.setText(rs.getString("salary"));
				txtPhone.setText(rs.getString("phone"));
				txtEmail.setText(rs.getString("email"));
				txtAddress.setText(rs.getString("address"));
				dateJoinDate.setDate(rs.getDate("joinDate"));
				if(!(rs.getString("referenceId")+rs.getString("referenceName")).toString().isEmpty()){
					cmbReference.txtSuggest.setText(rs.getString("referenceId")+"#"+
							rs.getString("referenceName"));
				}
				else{
					cmbReference.txtSuggest.setText("");
				}
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
			String imagepath=imageStorFolder+"/"+txtEmployeeId.getText().trim()+".jpg";
			File imageDeleteFile=new File(imagepath);
			imageDeleteFile.delete();

			String sql="delete from tbEmployeeinfo where "
					+ "employeeId='"+txtEmployeeId.getText().trim().toString()+"'";
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
	public void panelCenter() {
		panelCenter.setOpaque(false);
		panelCenter.setLayout(new BorderLayout());
		panelCenter.add(panelCenterCenter,BorderLayout.CENTER);
		panelCenterCenter();
		panelCenter.add(panelCenterSouth, BorderLayout.SOUTH);
		panelCenterSouth();
	}
	public void panelCenterCenter(){
		TitledBorder titleNewUser=BorderFactory.createTitledBorder("Employee List");
		panelCenterCenter.setBorder(titleNewUser);
		titleNewUser.setTitleColor(Color.decode("#8B0000"));
		titleNewUser.setTitleFont(new Font("Carlibri", Font.BOLD, 14));

		panelCenterCenter.setOpaque(false);
		GridBagConstraints cn=new GridBagConstraints();
		GridBagLayout grid=new GridBagLayout();
		panelCenterCenter.setLayout(grid);

		cn.gridx=0;
		cn.gridy=0;
		cn.fill=GridBagConstraints.BOTH;
		cn.insets=new Insets(1, 1, 1, 1);
		panelCenterCenter.add(scrollTable, cn);
		scrollTable.setPreferredSize(new Dimension(660, 600));
		table.getTableHeader().setReorderingAllowed(false);
		table.setRowHeight(table.getRowHeight()+7);
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
		cmbReport.v.add("Physician(surgon,Hospitalist)");
		cmbReport.v.add("Nurse");
		cmbReport.v.add("Techs");
		cmbReport.v.add("Therapist");
		cmbReport.v.add("Medical Assistants");
		cmbReport.v.add("Pharmacist");
		cmbReport.v.add("Medical Laboratory Technologist");
		cmbReport.v.add("Accountant");
		cmbReport.v.add("Human Resources & Recruiting");
		cmbReport.v.add("Executive(CEO,CFO,CIO)");
		cmbReport.v.add("Food Servicec");
		cmbReport.v.add("Environmental Service");
		cmbReport.v.add("Administrative Assistant");

		panelCenterSouth.add(btnPreview);
		btnPreview.setPreferredSize(new Dimension(160, 35));
		btnPreview.setBackground(Color.decode("#E68A00"));
		btnPreview.setForeground(Color.WHITE);
	}
	private void panelWest() {
		TitledBorder titleNewUser=BorderFactory.createTitledBorder("Employee Registration Form");
		panelWest.setBorder(titleNewUser);
		titleNewUser.setTitleColor(Color.decode("#8B0000"));
		titleNewUser.setTitleFont(new Font("Carlibri", Font.BOLD, 14));

		panelWest.setOpaque(false);
		panelWest.setPreferredSize(new Dimension(660, 0));
		panelWest.setLayout(new BorderLayout());
		panelWest.add(panelWestNorth,BorderLayout.NORTH);
		panelWestNorth();
		panelWest.add(panelWestCenter, BorderLayout.CENTER);
		panelWestCenter();
		panelWest.add(panelWestSouth, BorderLayout.SOUTH);
		panelWestSouth();
	}
	private void panelWestNorth() {
		panelWestNorth.setOpaque(false);
		panelWestNorth.setPreferredSize(new Dimension(0, 70));
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
	private void panelWestCenterEast() {
		panelWestCenterEast.setOpaque(false);
		panelWestCenterEast.setPreferredSize(new Dimension(180, 0));
		GridBagConstraints cn=new GridBagConstraints();
		GridBagLayout grid=new GridBagLayout();
		panelWestCenterEast.setLayout(grid);

		cn.gridx=0;
		cn.gridy=0;
		cn.fill=GridBagConstraints.BOTH;
		cn.insets=new Insets(80, 5, 5, 5);
		panelWestCenterEast.add(lblUpload,cn);
		lblUpload.setPreferredSize(new Dimension(142, 165));
		lblUpload.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));

		cn.gridx=0;
		cn.gridy=1;
		cn.fill=GridBagConstraints.BOTH;
		cn.insets=new Insets(5, 20, 180, 20);
		panelWestCenterEast.add(btnUpload,cn);
		btnUpload.setPreferredSize(new Dimension(80, 35)); 
		btnUpload.setBackground(Color.decode("#B8CFE5"));
	}
	private void panelWestCenterCenter() {
		panelWestCenterCenter.setOpaque(false);
		GridBagConstraints cn=new GridBagConstraints();
		GridBagLayout grid=new GridBagLayout();
		panelWestCenterCenter.setLayout(grid);
		cn.fill=GridBagConstraints.BOTH;

		cn.gridx=0;
		cn.gridy=0;
		cn.insets=new Insets(5, 5, 5, 5);
		panelWestCenterCenter.add(lblEmployeeId,cn);

		cn.gridx=1;
		cn.gridy=0;
		cn.insets=new Insets(5, 5, 5, 5);
		panelWestCenterCenter.add(txtEmployeeId,cn);
		txtEmployeeId.setEditable(false);

		cn.gridx=0;
		cn.gridy=1;
		cn.insets=new Insets(5, 5, 5, 5);
		panelWestCenterCenter.add(lblEmployeeName,cn);

		cn.gridx=1;
		cn.gridy=1;
		cn.insets=new Insets(5, 5, 5, 5);
		panelWestCenterCenter.add(txtEmployeeName,cn);

		cn.gridx=0;
		cn.gridy=2;
		cn.insets=new Insets(5, 5, 5, 5);
		panelWestCenterCenter.add(lblGender,cn);

		cn.gridx=1;
		cn.gridy=2;
		cn.insets=new Insets(5, 5, 5, 5);
		panelWestCenterCenter.add(cmbGender.cmbSuggest,cn);
		cmbGender.v.add("");
		cmbGender.v.add("Male");
		cmbGender.v.add("Female");

		cn.gridx=0;
		cn.gridy=3;
		cn.insets=new Insets(5, 5, 5, 5);
		panelWestCenterCenter.add(lblDesignation,cn);

		cn.gridx=1;
		cn.gridy=3;
		cn.insets=new Insets(5, 5, 5, 5);
		panelWestCenterCenter.add(cmbDesignation.cmbSuggest,cn);
		cmbDesignation.v.add("");
		cmbDesignation.v.add("Physician(surgon,Hospitalist)");
		cmbDesignation.v.add("Nurse");
		cmbDesignation.v.add("Techs");
		cmbDesignation.v.add("Therapist");
		cmbDesignation.v.add("Medical Assistants");
		cmbDesignation.v.add("Pharmacist");
		cmbDesignation.v.add("Medical Laboratory Technologist");
		cmbDesignation.v.add("Accountant");
		cmbDesignation.v.add("Human Resources & Recruiting");
		cmbDesignation.v.add("Executive(CEO,CFO,CIO)");
		cmbDesignation.v.add("Food Servicec");
		cmbDesignation.v.add("Environmental Service");
		cmbDesignation.v.add("Administrative Assistant");

		cn.gridx=0;
		cn.gridy=4;
		cn.insets=new Insets(5, 5, 5, 5);
		panelWestCenterCenter.add(lblSalary,cn);

		cn.gridx=1;
		cn.gridy=4;
		cn.insets=new Insets(5, 5, 5, 5);
		panelWestCenterCenter.add(txtSalary,cn);

		cn.gridx=0;
		cn.gridy=5;
		cn.insets=new Insets(5, 5, 5, 5);
		panelWestCenterCenter.add(lblPhone,cn);

		cn.gridx=1;
		cn.gridy=5;
		cn.insets=new Insets(5, 5, 5, 5);
		panelWestCenterCenter.add(txtPhone,cn);
		txtPhone.setText("+88");

		cn.gridx=0;
		cn.gridy=6;
		cn.insets=new Insets(5, 5, 5, 5);
		panelWestCenterCenter.add(lblEmail,cn);

		cn.gridx=1;
		cn.gridy=6;
		cn.insets=new Insets(5, 5, 5, 5);
		panelWestCenterCenter.add(txtEmail,cn);

		cn.gridx=0;
		cn.gridy=7;
		cn.insets=new Insets(5, 5, 5, 5);
		panelWestCenterCenter.add(lblAddress,cn);

		cn.gridx=1;
		cn.gridy=7;
		cn.insets=new Insets(5, 5, 5, 5);
		panelWestCenterCenter.add(scrollAddress,cn);

		cn.gridx=0;
		cn.gridy=8;
		cn.insets=new Insets(5, 5, 5, 5);
		panelWestCenterCenter.add(lblJoiningDate,cn);

		cn.gridx=1;
		cn.gridy=8;
		cn.insets=new Insets(5, 5, 5, 5);
		panelWestCenterCenter.add(dateJoinDate,cn);
		dateJoinDate.setDate(new Date());
		dateJoinDate.setDateFormatString("dd-MM-yyyy");

		cn.gridx=0;
		cn.gridy=9;
		cn.insets=new Insets(5, 5, 50, 5);
		panelWestCenterCenter.add(lblReference,cn);

		cn.gridx=1;
		cn.gridy=9;
		cn.insets=new Insets(5, 5, 50, 5);
		panelWestCenterCenter.add(cmbReference.cmbSuggest,cn);
	}
	private void panelWestSouth() {
		panelWestSouth.setOpaque(false);
		panelWestSouth.setPreferredSize(new Dimension(0, 140));
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

}
