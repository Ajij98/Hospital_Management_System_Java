package com.Security;

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

public class NewUserWork extends JPanel{

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
	
	JLabel lblDpt=new JLabel("User Type:");
	ImageIcon iconPreview=new ImageIcon("icons/view.png");
	JButton btnPreview=new JButton("Preview Report",iconPreview);
	SuggestText cmbReport=new SuggestText();

	String col[]={"User ID","User Name","Email","Phone","Address"};
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
	ImageIcon iconImage=new ImageIcon("Images/printer1.png");

	SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd");

	boolean isUpdate=false;

	JFileChooser fileChooser;
	File Imagefile=null;
	String imageStorFolder="F:/HospitalImages";
	BufferedImage buffer=null;
	boolean isUpload=false;

	public NewUserWork() {
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
		txtName.setEditable(b);
		txtUserName.setEditable(b);
		txtDesignation.setEditable(b);
		txtPass.setEditable(b);
		dateOfjoin.setEnabled(b);
		txtEmailAddress.setEditable(b);
		txtNationalId.setEditable(b);
		txtAddress.setEditable(b);
		txtMobileNumber.setEditable(b);
		cmbUserType.cmbSuggest.setEnabled(b);
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
		lblUpload.setIcon(new ImageIcon(""));
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
					if(checkEmail()){
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
				}
				else{
					if(checkValidation()){
						if(checkEmail()){
							if(checkMobileNumberLess()){
								if(checkMobileNumberGreater()){
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
					String userId=token.nextToken();
					searchDataLoad(userId);
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
				String userId=model.getValueAt(table.getSelectedRow(), 0).toString();
				searchDataLoad(userId);
				cmbSearchDataLoad();
				cmbSearch.txtSuggest.setText("");
				btnIni(false);
				editable(false);
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
					showReport("Report/UserReport.jrxml", query);
				}
				else{
					JOptionPane.showMessageDialog(null, "Select User Type please.");
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
			query="select userId,userName,email,address,mobileNum,userType from tbnewuserinfo";
		}
		else if(cmbReport.txtSuggest.getText().trim().toString().equals("Admin")){
			String userType=cmbReport.txtSuggest.getText().trim().toString();
			query="select userId,userName,email,address,mobileNum,userType from tbnewuserinfo "
					+ "where userType='"+userType+"'";
		}
		else if(cmbReport.txtSuggest.getText().trim().toString().equals("Doctor")){
			String userType=cmbReport.txtSuggest.getText().trim().toString();
			query="select userId,userName,email,address,mobileNum,userType from tbnewuserinfo "
					+ "where userType='"+userType+"'";
		}
		else if(cmbReport.txtSuggest.getText().trim().toString().equals("Receptionist")){
			String userType=cmbReport.txtSuggest.getText().trim().toString();
			query="select userId,userName,email,address,mobileNum,userType from tbnewuserinfo "
					+ "where userType='"+userType+"'";
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

			if(isUpload){
				File StoreFile=new File(imageStorFolder);
				if(!StoreFile.isDirectory()){
					StoreFile.mkdirs();
				}
				buffer=ImageIO.read(Imagefile);
				File ImageNameFile=new File(imageStorFolder+"/"+txtUserId.getText()+".jpg");
				if(ImageNameFile.exists()){
					ImageNameFile.delete();
				}
				ImageIO.write(buffer,"jpg",ImageNameFile);
			}
			String dbPicture=isUpload?txtUserId.getText().trim()+".jpg":"";

			String sql="insert into tbnewuserinfo(userId,name,userName,designation,password,dateOfJoin,"
					+ "email,nationalId,address,mobileNum,userType,picture,userip,entryTime)"
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
			String sql="select userId,userName,email,mobileNum,address from tbnewuserinfo";
			dbConneciton.connection();
			ResultSet rs=dbConneciton.sta.executeQuery(sql);
			while(rs.next()){
				model.addRow(new Object[]{rs.getString("userId"),rs.getString("userName"),
						rs.getString("email"),rs.getString("mobileNum"),rs.getString("address")});
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
			String sql="select userId,userName from tbnewuserinfo";
			dbConneciton.connection();
			ResultSet rs=dbConneciton.sta.executeQuery(sql);
			while(rs.next()){
				cmbSearch.v.add(rs.getString("userId")+"#"+rs.getString("userName"));
			}
			dbConneciton.con.close();
		} 
		catch (Exception e) {
			JOptionPane.showMessageDialog(null, e+"cmbSearchDataLoad()");
		}
	}
	public void searchDataLoad(String userId){
		try {
			String sql="select userId,name,userName,designation,password,dateOfJoin,email,nationalId,"
					+ "address,mobileNum,userType,picture from tbnewuserinfo where userId='"+userId+"'";
			dbConneciton.connection();
			System.out.println(sql);
			ResultSet rs=dbConneciton.sta.executeQuery(sql);
			if(rs.next()){
				txtUserId.setText(rs.getString("userId"));
				txtName.setText(rs.getString("name"));
				txtUserName.setText(rs.getString("userName"));
				txtDesignation.setText(rs.getString("designation"));
				txtPass.setText(rs.getString("password"));
				dateOfjoin.setDate(rs.getDate("dateOfJoin"));
				txtEmailAddress.setText(rs.getString("email"));
				txtNationalId.setText(rs.getString("nationalId"));
				txtAddress.setText(rs.getString("address"));
				txtMobileNumber.setText(rs.getString("mobileNum"));
				cmbUserType.txtSuggest.setText(rs.getString("userType"));
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
			String imagepath=imageStorFolder+"/"+txtUserId.getText().trim()+".jpg";
			File imageDeleteFile=new File(imagepath);
			imageDeleteFile.delete();

			String sql="delete from tbnewuserinfo where "
					+ "userId='"+txtUserId.getText().trim().toString()+"'";
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
		TitledBorder titleNewUser=BorderFactory.createTitledBorder("User List");
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
		scrollTable.setPreferredSize(new Dimension(590, 600));
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
		cmbReport.v.add("Admin");
		cmbReport.v.add("Doctor");
		cmbReport.v.add("Receptionist");

		panelCenterSouth.add(btnPreview);
		btnPreview.setPreferredSize(new Dimension(160, 35));
		btnPreview.setBackground(Color.decode("#E68A00"));
		btnPreview.setForeground(Color.WHITE);
	}
	private void panelWest() {
		TitledBorder titleNewUser=BorderFactory.createTitledBorder("User Registration Form");
		panelWest.setBorder(titleNewUser);
		titleNewUser.setTitleColor(Color.decode("#8B0000"));
		titleNewUser.setTitleFont(new Font("Carlibri", Font.BOLD, 14));

		panelWest.setOpaque(false);
		panelWest.setPreferredSize(new Dimension(720, 0));
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
	private void panelWestCenterCenter() {
		panelWestCenterCenter.setOpaque(false);
		GridBagConstraints cn=new GridBagConstraints();
		GridBagLayout grid=new GridBagLayout();
		panelWestCenterCenter.setLayout(grid);
		cn.fill=GridBagConstraints.BOTH;
		cn.insets=new Insets(5, 5, 5, 5);

		cn.gridx=0;
		cn.gridy=0;
		panelWestCenterCenter.add(lblUserID,cn);

		cn.gridx=1;
		cn.gridy=0;
		panelWestCenterCenter.add(txtUserId,cn);
		txtUserId.setEditable(false);

		cn.gridx=0;
		cn.gridy=1;
		panelWestCenterCenter.add(lblName,cn);

		cn.gridx=1;
		cn.gridy=1;
		panelWestCenterCenter.add(txtName,cn);

		cn.gridx=0;
		cn.gridy=2;
		panelWestCenterCenter.add(lblUserName,cn);

		cn.gridx=1;
		cn.gridy=2;
		panelWestCenterCenter.add(txtUserName,cn);

		cn.gridx=0;
		cn.gridy=3;
		panelWestCenterCenter.add(lblDesignation,cn);

		cn.gridx=1;
		cn.gridy=3;
		panelWestCenterCenter.add(txtDesignation,cn);

		cn.gridx=0;
		cn.gridy=4;
		panelWestCenterCenter.add(lblPass,cn);

		cn.gridx=1;
		cn.gridy=4;
		panelWestCenterCenter.add(txtPass,cn);

		cn.gridx=0;
		cn.gridy=5;
		panelWestCenterCenter.add(lblDateOfJoin,cn);

		cn.gridx=1;
		cn.gridy=5;
		panelWestCenterCenter.add(dateOfjoin,cn);
		dateOfjoin.setDateFormatString("dd-MM-yyyy");
		dateOfjoin.setDate(new Date());

		cn.gridx=0;
		cn.gridy=6;
		panelWestCenterCenter.add(lblEmailAddress,cn);

		cn.gridx=1;
		cn.gridy=6;
		panelWestCenterCenter.add(txtEmailAddress,cn);

		cn.gridx=0;
		cn.gridy=7;
		panelWestCenterCenter.add(lblNationalId,cn);

		cn.gridx=1;
		cn.gridy=7;
		panelWestCenterCenter.add(txtNationalId,cn);

		cn.gridx=0;
		cn.gridy=8;
		panelWestCenterCenter.add(lblAddress,cn);

		cn.gridx=1;
		cn.gridy=8;
		panelWestCenterCenter.add(scroll,cn);

		cn.gridx=0;
		cn.gridy=9;
		panelWestCenterCenter.add(lblMobile,cn);

		cn.gridx=1;
		cn.gridy=9;
		panelWestCenterCenter.add(txtMobileNumber,cn);
		txtMobileNumber.setText("+88");

		cn.gridx=0;
		cn.gridy=10;
		panelWestCenterCenter.add(lblUserType,cn);

		cn.gridx=1;
		cn.gridy=10;
		panelWestCenterCenter.add(cmbUserType.cmbSuggest,cn);
		cmbUserType.v.add("");
		cmbUserType.v.add("Admin");
		cmbUserType.v.add("Doctor");
		cmbUserType.v.add("Receptionist");
	}
	private void panelWestSouth() {
		panelWestSouth.setOpaque(false);
		panelWestSouth.setPreferredSize(new Dimension(0, 80));
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
