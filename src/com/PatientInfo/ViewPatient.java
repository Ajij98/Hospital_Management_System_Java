package com.PatientInfo;

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
import java.util.Date;
import java.util.StringTokenizer;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;
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

public class ViewPatient extends JPanel{

	JPanel panelNorth=new JPanel();
	JPanel panelCenter=new JPanel();

	JLabel lblImage=new JLabel(new ImageIcon("images/backgroundImage4.jpg"));

	String col[]={"PatientId","PatientName","Dieases","Local Gurdian","ContactNo.","Thana",
			"District","Admission Date","Admission Time","Doctor","Specialist","Word Name","Bed No"};
	Object row[][]={};
	DefaultTableModel model=new DefaultTableModel(row, col);
	JTable table=new JTable(model);
	JScrollPane scroll=new JScrollPane(table, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
			JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
	
	JLabel lblDpt=new JLabel("Word Name:");
	ImageIcon iconPreview=new ImageIcon("icons/view.png");
	JButton btnPreview=new JButton("Preview Report",iconPreview);
	SuggestText cmbReport=new SuggestText();

	JLabel lblWord=new JLabel("Word");
	SuggestText cmbWord=new SuggestText();

	JButton btnSearch=new JButton("Search");
	JButton btnViewAll=new JButton("View All");

	public ViewPatient(){
		setPreferredSize(new Dimension(1365, 730));
		setBackground(Color.YELLOW);
		cmp();
		btnAction();
	}
	public void btnAction(){
		btnSearch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(!cmbWord.txtSuggest.getText().trim().toString().isEmpty()){
					StringTokenizer token=new StringTokenizer(cmbWord.txtSuggest.getText().trim().
							toString(), "#");		
					token.nextToken();
					String wordName=token.nextToken();
					tableSearchDataLoad(wordName);
				}
				else{
					JOptionPane.showMessageDialog(null, "select word please.");
				}
			}
		});
		btnViewAll.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cmbWord.txtSuggest.setText("");
				cmbWordNameDataLoad();
				tableDataLoad();
			}
		});
		btnPreview.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(!cmbReport.txtSuggest.getText().trim().toString().isEmpty()){
					String query=getReportQuery();
					showReport("Report/PatientReport.jrxml", query);
				}
				else{
					JOptionPane.showMessageDialog(null, "Select Word Name please.");
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
			query="select patientId,patientName,dieases,doctorName,admissionData,contactNum "
					+ "from tbpatientinfo";
		}
		else if(cmbReport.txtSuggest.getText().trim().toString().equals("ICU")){
			String wordName=cmbReport.txtSuggest.getText().trim().toString();
			query="select patientId,patientName,dieases,doctorName,admissionData,contactNum "
					+ "from tbpatientinfo where wordName='"+wordName+"'";
		}
		else if(cmbReport.txtSuggest.getText().trim().toString().equals("Respiratory Unit")){
			String wordName=cmbReport.txtSuggest.getText().trim().toString();
			query="select patientId,patientName,dieases,doctorName,admissionData,contactNum "
					+ "from tbpatientinfo where wordName='"+wordName+"'";
		}
		else if(cmbReport.txtSuggest.getText().trim().toString().equals("Radiology Unit")){
			String wordName=cmbReport.txtSuggest.getText().trim().toString();
			query="select patientId,patientName,dieases,doctorName,admissionData,contactNum "
					+ "from tbpatientinfo where wordName='"+wordName+"'";
		}
		else if(cmbReport.txtSuggest.getText().trim().toString().equals("Surgical Unit")){
			String wordName=cmbReport.txtSuggest.getText().trim().toString();
			query="select patientId,patientName,dieases,doctorName,admissionData,contactNum "
					+ "from tbpatientinfo where wordName='"+wordName+"'";
		}
		else if(cmbReport.txtSuggest.getText().trim().toString().equals("NICU")){
			String wordName=cmbReport.txtSuggest.getText().trim().toString();
			query="select patientId,patientName,dieases,doctorName,admissionData,contactNum "
					+ "from tbpatientinfo where wordName='"+wordName+"'";
		}
		else if(cmbReport.txtSuggest.getText().trim().toString().equals("Dialysis Unit")){
			String wordName=cmbReport.txtSuggest.getText().trim().toString();
			query="select patientId,patientName,dieases,doctorName,admissionData,contactNum "
					+ "from tbpatientinfo where wordName='"+wordName+"'";
		}
		else if(cmbReport.txtSuggest.getText().trim().toString().equals("HDU")){
			String wordName=cmbReport.txtSuggest.getText().trim().toString();
			query="select patientId,patientName,dieases,doctorName,admissionData,contactNum "
					+ "from tbpatientinfo where wordName='"+wordName+"'";
		}
		else if(cmbReport.txtSuggest.getText().trim().toString().equals("CCU")){
			String wordName=cmbReport.txtSuggest.getText().trim().toString();
			query="select patientId,patientName,dieases,doctorName,admissionData,contactNum "
					+ "from tbpatientinfo where wordName='"+wordName+"'";
		}
		else if(cmbReport.txtSuggest.getText().trim().toString().equals("Emergency Unit")){
			String wordName=cmbReport.txtSuggest.getText().trim().toString();
			query="select patientId,patientName,dieases,doctorName,admissionData,contactNum "
					+ "from tbpatientinfo where wordName='"+wordName+"'";
		}
		else if(cmbReport.txtSuggest.getText().trim().toString().equals("Neurology Unit")){
			String wordName=cmbReport.txtSuggest.getText().trim().toString();
			query="select patientId,patientName,dieases,doctorName,admissionData,contactNum "
					+ "from tbpatientinfo where wordName='"+wordName+"'";
		}
		else if(cmbReport.txtSuggest.getText().trim().toString().equals("Gastrology Unit")){
			String wordName=cmbReport.txtSuggest.getText().trim().toString();
			query="select patientId,patientName,dieases,doctorName,admissionData,contactNum "
					+ "from tbpatientinfo where wordName='"+wordName+"'";
		}
		else if(cmbReport.txtSuggest.getText().trim().toString().equals("Pediatric Unit")){
			String wordName=cmbReport.txtSuggest.getText().trim().toString();
			query="select patientId,patientName,dieases,doctorName,admissionData,contactNum "
					+ "from tbpatientinfo where wordName='"+wordName+"'";
		}
		return query;
	}
	public void cmbWordNameDataLoad(){
		try {
			cmbWord.v.clear();
			cmbWord.v.add("");
			String sql="select wordNo,wordName from tbwordinfo";
			dbConneciton.connection();
			ResultSet rs=dbConneciton.sta.executeQuery(sql);
			while(rs.next()){
				cmbWord.v.add(rs.getString("wordNo")+"#"+rs.getString("wordName"));
			}
			dbConneciton.con.close();
		} 
		catch (Exception e) {
			JOptionPane.showMessageDialog(null, e+"cmbWordNameDataLoad()");
		}
	}
	public void tableDataLoad(){
		try {
			for(int i=model.getRowCount()-1; i>=0; i--){
				model.removeRow(i);
			}
			String sql="select patientid,patientName,dieases,localGurdian,contactNum,thana,district,"
					+ "admissionData,admissionTime,doctorName,specialist,wordName,bedNo "
					+ "from tbpatientinfo";
			dbConneciton.connection();
			ResultSet rs=dbConneciton.sta.executeQuery(sql);
			while(rs.next()){
				model.addRow(new Object[]{rs.getString("patientid"),rs.getString("patientName"),
						rs.getString("dieases"),rs.getString("localGurdian"),rs.getString("contactNum"),
						rs.getString("thana"),rs.getString("district"),rs.getDate("admissionData"),
						rs.getString("admissionTime"),rs.getString("doctorName"),rs.getString("specialist"),
						rs.getString("wordName"),rs.getString("bedNo")});
			}
			dbConneciton.con.close();
		} 
		catch (Exception e) {
			JOptionPane.showMessageDialog(null, e+"tableDataLoad()");
		}
	}
	public void tableSearchDataLoad(String wordName){
		try {
			for(int i=model.getRowCount()-1; i>=0; i--){
				model.removeRow(i);
			}
			String sql="select patientid,patientName,dieases,localGurdian,contactNum,thana,district,"
					+ "admissionData,admissionTime,doctorName,specialist,wordName,bedNo "
					+ "from tbpatientinfo where wordName='"+wordName+"'";
			dbConneciton.connection();
			ResultSet rs=dbConneciton.sta.executeQuery(sql);
			while(rs.next()){
				model.addRow(new Object[]{rs.getString("patientid"),rs.getString("patientName"),
						rs.getString("dieases"),rs.getString("localGurdian"),rs.getString("contactNum"),
						rs.getString("thana"),rs.getString("district"),rs.getDate("admissionData"),
						rs.getString("admissionTime"),rs.getString("doctorName"),rs.getString("specialist"),
						rs.getString("wordName"),rs.getString("bedNo")});
			}
			dbConneciton.con.close();
		} 
		catch (Exception e) {
			JOptionPane.showMessageDialog(null, e+"tableSearchDataLoad()");
		}
	}
	public void cmp(){
		FlowLayout flow=new FlowLayout();
		flow.setVgap(0);
		setLayout(flow);
		add(lblImage);		
		lblImage.setLayout(new BorderLayout());
		lblImage.add(panelCenter, BorderLayout.CENTER);
		panelCenter();
		lblImage.add(panelNorth, BorderLayout.NORTH);
		panelNorth();
	}
	public void panelCenter(){
		panelCenter.setOpaque(false);
		TitledBorder title=BorderFactory.createTitledBorder("Patient List");
		title.setTitleColor(Color.decode("#8B0000"));
		title.setTitleFont(new Font("Carlibri", Font.BOLD, 14));
		title.setTitleJustification(TitledBorder.CENTER);
		panelCenter.setBorder(title);

		GridBagConstraints cn=new GridBagConstraints();
		GridBagLayout grid=new GridBagLayout();
		panelCenter.setLayout(grid);

		cn.gridx=0;
		cn.gridy=0;
		cn.fill=GridBagConstraints.BOTH;
		cn.insets=new Insets(5, 5, 5, 5);
		panelCenter.add(scroll,cn);
		scroll.setPreferredSize(new Dimension(1320,530));
		table.getTableHeader().setReorderingAllowed(false);
	}
	public void panelNorth(){
		panelNorth.setOpaque(false);
		panelNorth.setPreferredSize(new Dimension(0, 120));
		TitledBorder title=BorderFactory.createTitledBorder("Search Patient(Word wise)");
		title.setTitleColor(Color.decode("#8B0000"));
		title.setTitleFont(new Font("Carlibri", Font.BOLD, 14));
		panelNorth.setBorder(title);

		GridBagConstraints cn=new GridBagConstraints();
		GridBagLayout grid=new GridBagLayout();
		panelNorth.setLayout(grid);

		cn.gridx=0;
		cn.gridy=0;
		cn.fill=GridBagConstraints.BOTH;
		cn.insets=new Insets(5, 5, 5, 5);
		panelNorth.add(lblWord, cn);
		lblWord.setFont(new Font("", Font.BOLD, 15));
		lblWord.setForeground(Color.decode("#E8F2FE"));

		cn.gridx=1;
		cn.gridy=0;
		cn.fill=GridBagConstraints.BOTH;
		cn.insets=new Insets(5, 5, 5, 20);
		panelNorth.add(cmbWord.cmbSuggest, cn);
		cmbWord.cmbSuggest.setPreferredSize(new Dimension(320, 32));

		cn.gridx=2;
		cn.gridy=0;
		cn.fill=GridBagConstraints.BOTH;
		cn.insets=new Insets(5, 5, 5, 5);
		panelNorth.add(btnSearch, cn);
		btnSearch.setPreferredSize(new Dimension(90, 30));
		btnSearch.setBackground(Color.decode("#EA8E05"));
		btnSearch.setForeground(Color.WHITE);

		cn.gridx=3;
		cn.gridy=0;
		cn.fill=GridBagConstraints.BOTH;
		cn.insets=new Insets(5, 5, 5, 5);
		panelNorth.add(btnViewAll, cn);
		btnViewAll.setPreferredSize(new Dimension(80, 30));
		btnViewAll.setBackground(Color.decode("#E42214"));
		btnViewAll.setForeground(Color.WHITE);
		
		cn.gridx=5;
		cn.gridy=0;
		cn.fill=GridBagConstraints.BOTH;
		cn.insets=new Insets(5, 5, 5, 5);
		panelNorth.add(lblDpt, cn);

		cn.gridx=6;
		cn.gridy=0;
		cn.fill=GridBagConstraints.BOTH;
		cn.insets=new Insets(5, 5, 5, 5);
		panelNorth.add(cmbReport.cmbSuggest, cn);
		cmbReport.cmbSuggest.setPreferredSize(new Dimension(180, 32));
		cmbReport.v.add("");
		cmbReport.v.add("All");
		cmbReport.v.add("ICU");
		cmbReport.v.add("Respiratory Unit");
		cmbReport.v.add("Radiology Unit");
		cmbReport.v.add("Surgical Unit");
		cmbReport.v.add("NICU");
		cmbReport.v.add("Dialysis Unit");
		cmbReport.v.add("HDU");
		cmbReport.v.add("CCU");
		cmbReport.v.add("Emergency Unit");
		cmbReport.v.add("Neurology Unit");
		cmbReport.v.add("Gastrology Unit");
		cmbReport.v.add("Pediatric Unit");

		cn.gridx=7;
		cn.gridy=0;
		cn.fill=GridBagConstraints.BOTH;
		cn.insets=new Insets(5, 5, 5, 5);
		panelNorth.add(btnPreview, cn);
		btnPreview.setPreferredSize(new Dimension(160, 35));
		btnPreview.setBackground(Color.decode("#E68A00"));
		btnPreview.setForeground(Color.WHITE);
	}
}
