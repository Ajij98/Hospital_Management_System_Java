package com.PatientInfo;

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
import java.text.DecimalFormat;
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

import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JRDesignQuery;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;

public class DischargePatient extends JPanel{

	JPanel panelWest=new JPanel();
	JPanel panelWestNorth=new JPanel();
	JPanel panelWestCenter=new JPanel();
	JPanel panelWestSouth=new JPanel();
	JPanel panelCenter=new JPanel();

	JLabel lblImage=new JLabel(new ImageIcon("images/backgroundImage4.jpg"));

	String col[]={"Discharge No","Patient Id","Patient Name","Discount","Total Amount","Dischagre Date"};
	Object row[][]={};
	DefaultTableModel model=new DefaultTableModel(row, col);
	JTable table=new JTable(model);
	JScrollPane scroll=new JScrollPane(table, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
			JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

	JLabel lblPatientId=new JLabel("Patient Id: ");
	SuggestText cmbPatientId=new SuggestText();

	JLabel lblDischargeNo=new JLabel("Discharge No");
	JLabel lblAdmissionDate=new JLabel("Admission Date");
	JLabel lblCurrentDate=new JLabel("Current Date");
	JLabel lblTotalDays=new JLabel("Total Days");
	JLabel lblBedNo=new JLabel("Bed No");
	JLabel lblBedCharge=new JLabel("Bed charge");
	JLabel lblTotalBedCharge=new JLabel("Total bed Charge");
	JLabel lblMedicineBill=new JLabel("Medicine Bill");
	JLabel lblFoodBill=new JLabel("Food Bill");
	JLabel lblTotalAmount=new JLabel("Total Amount");
	JLabel lblDiscountAmount=new JLabel("Discount Amount");
	JLabel lblTotalAmountAfterDiscount=new JLabel("After Discount Amount");
	JLabel lblDischargeDate=new JLabel("Discharge Date");

	JTextField txtDischargeNo=new JTextField(20);
	JDateChooser dateAdmissionDate=new JDateChooser();
	JDateChooser dateCurrentDate=new JDateChooser();
	JTextField txtTotalDays=new JTextField();
	JTextField txtBedNo=new JTextField();
	JTextField txtBedCharge=new JTextField();
	JTextField txtTotalBedCharge=new JTextField();
	JTextField txtMedicineBill=new JTextField();
	JTextField txtFoodBill=new JTextField();
	JTextField txtTotalAmount=new JTextField();
	JTextField txtDiscountAmount=new JTextField();
	JTextField txtTotalAmountAFDIS=new JTextField();
	JDateChooser dateDischargeDate=new JDateChooser();

	ImageIcon iconAdd=new ImageIcon("Images/add1.png");
	JButton btnAdd=new JButton("Add",iconAdd);
	ImageIcon iconEdit=new ImageIcon("Images/btnEdit.png");
	JButton btnEdit=new JButton("Edit",iconEdit);
	ImageIcon iconRefresh=new ImageIcon("Images/btnRefresh.png");
	JButton btnRefresh=new JButton("Refresh",iconRefresh);
	ImageIcon iconDelete=new ImageIcon("Images/cancel-icon.png");
	JButton btnDelete=new JButton("Delete",iconDelete);
	
	DecimalFormat format=new DecimalFormat("#0.00");

	boolean isUpdate=false;

	public DischargePatient(){
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
		cmbPatientId.cmbSuggest.setEnabled(b);
		dateAdmissionDate.setEnabled(b);
		dateCurrentDate.setEnabled(b);
		txtTotalDays.setEditable(b);
		txtBedNo.setEditable(b);
		txtBedCharge.setEditable(b);
		txtTotalBedCharge.setEditable(b);
		txtMedicineBill.setEditable(b);
		txtFoodBill.setEditable(b);
		txtTotalAmount.setEditable(b);
		txtDiscountAmount.setEditable(b);
		txtTotalAmountAFDIS.setEditable(b);
		dateDischargeDate.setEnabled(b);
	}
	public void txtClear(){
		cmbPatientId.txtSuggest.setText("");
		dateAdmissionDate.setDate(new Date());
		dateCurrentDate.setDate(new Date());
		txtTotalDays.setText("");
		txtBedNo.setText("");
		txtBedCharge.setText("");
		txtTotalBedCharge.setText("");
		txtMedicineBill.setText("");
		txtFoodBill.setText("");
		txtTotalAmount.setText("");
		txtDiscountAmount.setText("");
		txtTotalAmountAFDIS.setText("");
		dateDischargeDate.setDate(new Date());
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
						if(isExistDischargePatient()){
							if(checkConfirmation("sure to save?")){
								if(insertData()){
									JOptionPane.showMessageDialog(null, "Data saved successfully.Thank you.");
									
									if(checkConfirmation("Do you want to print Report?")){
										String query=getReportQuery();
										showReport("Report/DischargeReport.jrxml", query);
									}
									
									refreshWork();
								}
							}
						}
					}
				}
			}
		});
		cmbPatientId.cmbSuggest.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(!cmbPatientId.txtSuggest.getText().trim().toString().isEmpty()){
					StringTokenizer token=new StringTokenizer(cmbPatientId.txtSuggest.getText().trim().
							toString(), "#");
					String patientId=token.nextToken();
					txtBedNoAndBedChargeDataLoad(patientId);
				}
				else{
					txtBedNo.setText("");
					txtBedCharge.setText("");
				}
			}
		});
		table.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				String dischargeNo=model.getValueAt(table.getSelectedRow(), 0).toString();
				searchDataLoad(dischargeNo);
				btnIni(false);
				editable(false);
			}
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
		txtTotalBedCharge.addKeyListener(new KeyListener() {
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
		txtMedicineBill.addKeyListener(new KeyListener() {
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
		txtFoodBill.addKeyListener(new KeyListener() {
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
		txtTotalAmount.addKeyListener(new KeyListener() {
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
		txtDiscountAmount.addKeyListener(new KeyListener() {
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
		txtTotalAmountAFDIS.addKeyListener(new KeyListener() {
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
		txtTotalDays.addKeyListener(new KeyListener() {
			public void keyTyped(KeyEvent e) {}
			public void keyReleased(KeyEvent e) {
				totalBedChargeCalculation();
			}
			public void keyPressed(KeyEvent e) {}
		});
		txtBedCharge.addKeyListener(new KeyListener() {
			public void keyTyped(KeyEvent e) {}
			public void keyReleased(KeyEvent e) {
				totalBedChargeCalculation();
			}
			public void keyPressed(KeyEvent e) {}
		});
		txtTotalBedCharge.addKeyListener(new KeyListener() {
			public void keyTyped(KeyEvent e) {}
			public void keyReleased(KeyEvent e) {
				totalAmountCalculation();
			}
			public void keyPressed(KeyEvent e) {}
		});
		txtMedicineBill.addKeyListener(new KeyListener() {
			public void keyTyped(KeyEvent e) {}
			public void keyReleased(KeyEvent e) {
				totalAmountCalculation();
			}
			public void keyPressed(KeyEvent e) {}
		});
		txtFoodBill.addKeyListener(new KeyListener() {
			public void keyTyped(KeyEvent e) {}
			public void keyReleased(KeyEvent e) {
				totalAmountCalculation();
			}
			public void keyPressed(KeyEvent e) {}
		});
		txtTotalAmount.addKeyListener(new KeyListener() {
			public void keyTyped(KeyEvent e) {}
			public void keyReleased(KeyEvent e) {
				afterDiscountAmountCalculation();
			}
			public void keyPressed(KeyEvent e) {}
		});
		txtDiscountAmount.addKeyListener(new KeyListener() {
			public void keyTyped(KeyEvent e) {}
			public void keyReleased(KeyEvent e) {
				afterDiscountAmountCalculation();
			}
			public void keyPressed(KeyEvent e) {}
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
		String query="select patientId,patientName,admissionDate,currentDate,totalDays,totalBedCharge,"
					+ "medicineBill,foodBill,totalAmount,discountAmount,afterDiscountAmount,"
					+ "dischargeDate from tbdischargeinfo where "
					+ "dischargeNo='"+txtDischargeNo.getText().trim().toString()+"'";
		return query;
	}
	public void totalBedChargeCalculation(){
		double totalDays,bedCharge,totalBedCharge;
		totalDays=Double.parseDouble(txtTotalDays.getText().trim().isEmpty()?"0":
			txtTotalDays.getText().trim());
		bedCharge=Double.parseDouble(txtBedCharge.getText().trim().toString().isEmpty()?"0":
			txtBedCharge.getText().trim().toString());
		totalBedCharge=totalDays*bedCharge;
		txtTotalBedCharge.setText(format.format(totalBedCharge));
	}
	public void totalAmountCalculation(){
		double totalBedCharge,medicineBill,foodBill,totalAmount;
		totalBedCharge=Double.parseDouble(txtTotalBedCharge.getText().trim().toString().isEmpty()?"0":
			txtTotalBedCharge.getText().trim().toString());
		medicineBill=Double.parseDouble(txtMedicineBill.getText().trim().toString().isEmpty()?"0":
			txtMedicineBill.getText().trim().toString());
		foodBill=Double.parseDouble(txtFoodBill.getText().trim().toString().isEmpty()?"0":
			txtFoodBill.getText().trim().toString());
		totalAmount=totalBedCharge+medicineBill+foodBill;
		txtTotalAmount.setText(format.format(totalAmount));
	}
	public void afterDiscountAmountCalculation(){
		double totalAmount,discountAmount,totalAmountAfterDiscount;
		totalAmount=Double.parseDouble(txtTotalAmount.getText().trim().toString().isEmpty()?"0":
			txtTotalAmount.getText().trim().toString());
		discountAmount=Double.parseDouble(txtDiscountAmount.getText().trim().toString().isEmpty()?"0":
			txtDiscountAmount.getText().trim().toString());
		totalAmountAfterDiscount=totalAmount-discountAmount;
		txtTotalAmountAFDIS.setText(format.format(totalAmountAfterDiscount));
	}
	public void autoId(){
		try {
			String sql="select ifnull(max(cast(substring(dischargeNo,locate('-',dischargeNo)+1,"
					+ "length(dischargeNo)-locate('-',dischargeNo)) as UNSIGNED)),0)+1 as id from"
					+ " tbDischargeinfo";
			dbConneciton.connection();
			ResultSet rs=dbConneciton.sta.executeQuery(sql);
			while(rs.next()){
				txtDischargeNo.setText("Discharge No-"+rs.getString("id"));
			}
			dbConneciton.con.close();
		} 
		catch (Exception e) {
			JOptionPane.showMessageDialog(null, e+"autoId()");
		}
	}
	public boolean checkValidation(){
		if(!cmbPatientId.txtSuggest.getText().trim().toString().isEmpty()){
			if(!txtDischargeNo.getText().trim().toString().isEmpty()){
				if(!txtTotalDays.getText().trim().toString().isEmpty()){
					if(!txtBedNo.getText().trim().toString().isEmpty()){
						if(!txtBedCharge.getText().trim().toString().isEmpty()){
							if(!txtTotalBedCharge.getText().trim().toString().isEmpty()){
								if(!txtMedicineBill.getText().trim().toString().isEmpty()){
									if(!txtFoodBill.getText().trim().toString().isEmpty()){
										if(!txtTotalAmount.getText().trim().toString().isEmpty()){
											if(!txtDiscountAmount.getText().trim().toString().isEmpty()){
												if(!txtTotalAmountAFDIS.getText().trim().toString().isEmpty()){
													return true;
												}
												else{
													JOptionPane.showMessageDialog(null, "insert after discount amount please.");
												}
											}
											else{
												JOptionPane.showMessageDialog(null, "insert discount amount please.");
											}
										}
										else{
											JOptionPane.showMessageDialog(null, "insert total amount please.");
										}
									}
									else{
										JOptionPane.showMessageDialog(null, "insert food bill please.");
									}
								}
								else{
									JOptionPane.showMessageDialog(null, "insert medicine bill please.");
								}
							}
							else{
								JOptionPane.showMessageDialog(null, "insert total bed charge please.");
							}
						}
						else{
							JOptionPane.showMessageDialog(null, "insert bed charge please.");
						}
					}
					else{
						JOptionPane.showMessageDialog(null, "insert bed no. please.");
					}
				}
				else{
					JOptionPane.showMessageDialog(null, "insert total days please.");
				}
			}
			else{
				JOptionPane.showMessageDialog(null, "insert discharge no. please.");
			}
		}
		else{
			JOptionPane.showMessageDialog(null, "select patient id please.");
		}
		return false;
	}
	public boolean isExistDischargePatient(){
		try {
			StringTokenizer token=new StringTokenizer(cmbPatientId.txtSuggest.getText().trim().toString(),"#");
			String patientId=token.nextToken();
			
			String sql="select * from tbdischargeinfo where "
					+ "patientId='"+patientId+"'";
			dbConneciton.connection();
			ResultSet rs=dbConneciton.sta.executeQuery(sql);
			while(rs.next()){
				JOptionPane.showMessageDialog(null, "This Patient already Discharged!","Warning...",
						JOptionPane.WARNING_MESSAGE);
				return false;
			}
			dbConneciton.con.close();
		} 
		catch (Exception e) {
			JOptionPane.showMessageDialog(null, e+"isExistDischargePatient()");
		}
		return true;
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
			cmbPatientId.v.clear();
			cmbPatientId.v.add("");
			String sql="select patientId,patientName from tbpatientinfo";
			dbConneciton.connection();
			ResultSet rs=dbConneciton.sta.executeQuery(sql);
			while(rs.next()){
				cmbPatientId.v.add(rs.getString("patientId")+"#"+rs.getString("patientName"));
			}
			dbConneciton.con.close();
		} 
		catch (Exception e) {
			JOptionPane.showMessageDialog(null, e+"cmbPatientDataLoad()");
		}
	}
	public void txtBedNoAndBedChargeDataLoad(String bedNo){
		try {
			String sql="select bedNo,bedCharge from tbpatientinfo where patientId='"+bedNo+"'";
			dbConneciton.connection();
			ResultSet rs=dbConneciton.sta.executeQuery(sql);
			while(rs.next()){
				txtBedNo.setText(rs.getString("bedNo"));
				txtBedCharge.setText(rs.getString("bedCharge"));
			}
			dbConneciton.con.close();
		} 
		catch (Exception e) {
			JOptionPane.showMessageDialog(null, e+"txtBedNoAndBedChargeDataLoad()");
		}
	}
	public boolean insertData(){
		try {
			StringTokenizer token=new StringTokenizer(cmbPatientId.txtSuggest.getText().trim().
					toString(), "#");
			String patientId=token.nextToken();
			String patientName=token.nextToken();

			String admissionDate=new SimpleDateFormat("yyyy-MM-dd").format(dateAdmissionDate.getDate());
			String currentDate=new SimpleDateFormat("yyyy-MM-dd").format(dateCurrentDate.getDate());
			String dischargeDate=new SimpleDateFormat("yyyy-MM-dd").format(dateDischargeDate.getDate());

			String sql="insert into tbdischargeinfo(patientId,patientName,dischargeNo,admissionDate,"
					+ "currentDate,totalDays,bedNo,bedCharge,totalBedCharge,medicineBill,foodBill,"
					+ "totalAmount,discountAmount,afterDiscountAmount,dischargeDate,userip,entryTime)"
					+ "values('"+patientId+"',"
					+ "'"+patientName+"',"
					+ "'"+txtDischargeNo.getText().trim().toString()+"',"
					+ "'"+admissionDate+"',"
					+ "'"+currentDate+"',"
					+ "'"+txtTotalDays.getText().trim().toString()+"',"
					+ "'"+txtBedNo.getText().trim().toString()+"',"
					+ "'"+txtBedCharge.getText().trim().toString()+"',"
					+ "'"+txtTotalBedCharge.getText().trim().toString()+"',"
					+ "'"+txtMedicineBill.getText().trim().toString()+"',"
					+ "'"+txtFoodBill.getText().trim().toString()+"',"
					+ "'"+txtTotalAmount.getText().trim().toString()+"',"
					+ "'"+txtDiscountAmount.getText().trim().toString()+"',"
					+ "'"+txtTotalAmountAFDIS.getText().trim().toString()+"',"
					+ "'"+dischargeDate+"',"
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
			String sql="select dischargeNo,patientId,patientName,discountAmount,totalAmount,dischargeDate "
					+ "from tbdischargeinfo";
			dbConneciton.connection();
			ResultSet rs=dbConneciton.sta.executeQuery(sql);
			while(rs.next()){
				model.addRow(new Object[]{rs.getString("dischargeNo"),rs.getString("patientId"),
						rs.getString("patientName"),rs.getString("discountAmount"),
						rs.getString("totalAmount"),rs.getString("dischargeDate")});
			}
			dbConneciton.con.close();
		} 
		catch (Exception e) {
			JOptionPane.showMessageDialog(null, e+"tableDataLoad()");
		}
	}
	public void searchDataLoad(String dischargeNo){
		try {
			String sql="select patientId,patientName,dischargeNo,admissionDate,currentDate,totalDays,"
					+ "bedNo,bedCharge,totalBedCharge,medicineBill,foodBill,totalAmount,discountAmount,"
					+ "afterDiscountAmount,dischargeDate from tbdischargeinfo where "
					+ "dischargeNo='"+dischargeNo+"'";
			dbConneciton.connection();
			System.out.println(sql);
			ResultSet rs=dbConneciton.sta.executeQuery(sql);
			while(rs.next()){
				cmbPatientId.txtSuggest.setText(rs.getString("patientId")+"#"+rs.getString("patientName"));
				txtDischargeNo.setText(rs.getString("dischargeNo"));
				dateAdmissionDate.setDate(rs.getDate("admissionDate"));
				dateCurrentDate.setDate(rs.getDate("currentDate"));
				txtTotalDays.setText(rs.getString("totalDays"));
				txtBedNo.setText(rs.getString("bedNo"));
				txtBedCharge.setText(rs.getString("bedCharge"));
				txtTotalBedCharge.setText(rs.getString("totalBedCharge"));
				txtMedicineBill.setText(rs.getString("medicineBill"));
				txtFoodBill.setText(rs.getString("foodBill"));
				txtTotalAmount.setText(rs.getString("totalAmount"));
				txtDiscountAmount.setText(rs.getString("discountAmount"));
				txtTotalAmountAFDIS.setText(rs.getString("afterDiscountAmount"));
				dateDischargeDate.setDate(rs.getDate("dischargeDate"));
			}
			dbConneciton.con.close();
		} 
		catch (Exception e) {
			JOptionPane.showMessageDialog(null, e+" searchDataLoad()");
		}
	}
	public boolean deleteData(){
		try {
			String sql="delete from tbdischargeinfo where "
					+ "dischargeNo='"+txtDischargeNo.getText().trim().toString()+"'";
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
		TitledBorder title=BorderFactory.createTitledBorder("Patient Information");
		title.setTitleColor(Color.decode("#8B0000"));
		title.setTitleFont(new Font("Carlibri", Font.BOLD, 14));
		panelWestNorth.setBorder(title);

		panelWestNorth.setOpaque(false);
		panelWestNorth.setPreferredSize(new Dimension(0, 80));
		GridBagConstraints cn=new GridBagConstraints();
		GridBagLayout grid=new GridBagLayout();
		panelWestNorth.setLayout(grid);

		cn.gridx=0;
		cn.gridy=0;
		cn.fill=GridBagConstraints.BOTH;
		cn.insets=new Insets(5, 5, 5, 5);
		panelWestNorth.add(lblPatientId, cn);

		cn.gridx=1;
		cn.gridy=0;
		cn.fill=GridBagConstraints.BOTH;
		cn.insets=new Insets(5, 5, 5, 5);
		panelWestNorth.add(cmbPatientId.cmbSuggest, cn);
		cmbPatientId.cmbSuggest.setPreferredSize(new Dimension(250, 33));
	}
	private void panelWestCenter() {
		TitledBorder title=BorderFactory.createTitledBorder("Billing Information");
		title.setTitleColor(Color.decode("#8B0000"));
		title.setTitleFont(new Font("Carlibri", Font.BOLD, 14));
		panelWestCenter.setBorder(title);
		panelWestCenter.setOpaque(false);

		GridBagConstraints c=new GridBagConstraints();
		GridBagLayout grid=new GridBagLayout();
		panelWestCenter.setLayout(grid);
		c.fill=GridBagConstraints.BOTH;
		c.insets=new Insets(5, 5, 5, 5);

		c.gridx=0;
		c.gridy=0;
		panelWestCenter.add(lblDischargeNo,c);

		c.gridx=1;
		c.gridy=0;
		panelWestCenter.add(txtDischargeNo,c);
		txtDischargeNo.setEditable(false);

		c.gridx=0;
		c.gridy=1;
		panelWestCenter.add(lblAdmissionDate,c);

		c.gridx=1;
		c.gridy=1;
		panelWestCenter.add(dateAdmissionDate,c);
		dateAdmissionDate.setDate(new Date());
		dateAdmissionDate.setDateFormatString("dd-MM-yyyy");
		dateAdmissionDate.setOpaque(false);

		c.gridx=0;
		c.gridy=2;
		panelWestCenter.add(lblCurrentDate,c);

		c.gridx=1;
		c.gridy=2;
		panelWestCenter.add(dateCurrentDate,c);
		dateCurrentDate.setDate(new Date());
		dateCurrentDate.setDateFormatString("dd-MM-yyyy");
		dateCurrentDate.setOpaque(false);

		c.gridx=0;
		c.gridy=3;
		panelWestCenter.add(lblTotalDays,c);

		c.gridx=1;
		c.gridy=3;
		panelWestCenter.add(txtTotalDays,c);

		c.gridx=0;
		c.gridy=4;
		panelWestCenter.add(lblBedNo,c);

		c.gridx=1;
		c.gridy=4;
		panelWestCenter.add(txtBedNo,c);

		c.gridx=0;
		c.gridy=5;
		panelWestCenter.add(lblBedCharge,c);

		c.gridx=1;
		c.gridy=5;
		panelWestCenter.add(txtBedCharge,c);

		c.gridx=0;
		c.gridy=6;
		panelWestCenter.add(lblTotalBedCharge,c);

		c.gridx=1;
		c.gridy=6;
		panelWestCenter.add(txtTotalBedCharge,c);

		c.gridx=0;
		c.gridy=7;
		panelWestCenter.add(lblMedicineBill,c);

		c.gridx=1;
		c.gridy=7;
		panelWestCenter.add(txtMedicineBill,c);

		c.gridx=0;
		c.gridy=8;
		panelWestCenter.add(lblFoodBill,c);

		c.gridx=1;
		c.gridy=8;
		panelWestCenter.add(txtFoodBill,c);

		c.gridx=0;
		c.gridy=9;
		panelWestCenter.add(lblTotalAmount,c);

		c.gridx=1;
		c.gridy=9;
		panelWestCenter.add(txtTotalAmount,c);

		c.gridx=0;
		c.gridy=10;
		panelWestCenter.add(lblDiscountAmount,c);

		c.gridx=1;
		c.gridy=10;
		panelWestCenter.add(txtDiscountAmount,c);

		c.gridx=0;
		c.gridy=11;
		panelWestCenter.add(lblTotalAmountAfterDiscount,c);

		c.gridx=1;
		c.gridy=11;
		panelWestCenter.add(txtTotalAmountAFDIS,c);

		c.gridx=0;
		c.gridy=12;
		panelWestCenter.add(lblDischargeDate,c);

		c.gridx=1;
		c.gridy=12;
		panelWestCenter.add(dateDischargeDate,c);
		dateDischargeDate.setDate(new Date());
		dateDischargeDate.setDateFormatString("dd-MM-yyyy");
		dateDischargeDate.setOpaque(false);
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
