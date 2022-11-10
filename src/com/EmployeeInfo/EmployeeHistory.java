package com.EmployeeInfo;

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

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.View;

import com.example.Admin.SuggestText;
import com.example.Admin.dbConneciton;

public class EmployeeHistory extends JPanel{

	JPanel panelNorth=new JPanel();
	JPanel panelCenter=new JPanel();

	JLabel lblImage=new JLabel(new ImageIcon("images/backgroundImage4.jpg"));

	String col[]={"Employee Id","Employee Name","Gender","Designation","Salary","Phone","Email","Address",
			"Join Date"};
	Object row[][]={};
	DefaultTableModel model=new DefaultTableModel(row, col);
	JTable table=new JTable(model);
	JScrollPane scroll=new JScrollPane(table, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
			JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

	JLabel lblDepartment=new JLabel("Employee Dept.");
	SuggestText cmbDepartment=new SuggestText();

	JButton btnSearch=new JButton("Search");
	JButton btnViewAll=new JButton("View All");

	public EmployeeHistory(){
		cmp();
		btnAction();
	}
	public void btnAction(){
		btnSearch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(!cmbDepartment.txtSuggest.getText().trim().toString().isEmpty()){
					String department=cmbDepartment.txtSuggest.getText().trim().toString();
					tableSearchDataLoad(department);
				}
				else{
					JOptionPane.showMessageDialog(null, "select department please.");
				}
			}
		});
		btnViewAll.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cmbDepartment.txtSuggest.setText("");
				tableDataLoad();
			}
		});
	}
	public void tableDataLoad(){
		try {
			for(int i=model.getRowCount()-1; i>=0; i--){
				model.removeRow(i);
			}
			String sql="select employeeId,employeeName,gender,designation,salary,"
					+ "phone,email,address,joinDate from tbEmployeeinfo";
			dbConneciton.connection();
			ResultSet rs=dbConneciton.sta.executeQuery(sql);
			while(rs.next()){
				model.addRow(new Object[]{rs.getString("employeeId"),rs.getString("employeeName"),
						rs.getString("gender"),rs.getString("designation"),rs.getString("salary"),
						rs.getString("phone"),rs.getString("email"),rs.getString("address"),
						rs.getString("joinDate")});
			}
			dbConneciton.con.close();
		} 
		catch (Exception e) {
			JOptionPane.showMessageDialog(null, e+"tableDataLoad()");
		}
	}
	public void tableSearchDataLoad(String department){
		try {
			for(int i=model.getRowCount()-1; i>=0; i--){
				model.removeRow(i);
			}
			String sql="select employeeId,employeeName,gender,designation,salary,phone,email,address,"
					+ "joinDate from tbEmployeeinfo where designation='"+department+"'";
			dbConneciton.connection();
			ResultSet rs=dbConneciton.sta.executeQuery(sql);
			while(rs.next()){
				model.addRow(new Object[]{rs.getString("employeeId"),rs.getString("employeeName"),
						rs.getString("gender"),rs.getString("designation"),rs.getString("salary"),
						rs.getString("phone"),rs.getString("email"),rs.getString("address"),
						rs.getString("joinDate")});
			}
			dbConneciton.con.close();
		} 
		catch (Exception e) {
			JOptionPane.showMessageDialog(null, e+"tableSearchDataLoad()");
		}
	}
	public void cmp(){
		setPreferredSize(new Dimension(1365, 720));
		setBackground(Color.YELLOW);
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
		TitledBorder title=BorderFactory.createTitledBorder("Employee List");
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
		TitledBorder title=BorderFactory.createTitledBorder("Search Employee(Department Wise)");
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
		panelNorth.add(lblDepartment, cn);
		lblDepartment.setFont(new Font("", Font.BOLD, 15));
		lblDepartment.setForeground(Color.decode("#E8F2FE"));

		cn.gridx=1;
		cn.gridy=0;
		cn.fill=GridBagConstraints.BOTH;
		cn.insets=new Insets(5, 5, 5, 20);
		panelNorth.add(cmbDepartment.cmbSuggest, cn);
		cmbDepartment.cmbSuggest.setPreferredSize(new Dimension(320, 32));
		cmbDepartment.v.add("");
		cmbDepartment.v.add("Physician(surgon,Hospitalist)");
		cmbDepartment.v.add("Nurse");
		cmbDepartment.v.add("Techs");
		cmbDepartment.v.add("Therapist");
		cmbDepartment.v.add("Medical Assistants");
		cmbDepartment.v.add("Pharmacist");
		cmbDepartment.v.add("Medical Laboratory Technologist");
		cmbDepartment.v.add("Accountant");
		cmbDepartment.v.add("Human Resources & Recruiting");
		cmbDepartment.v.add("Executive(CEO,CFO,CIO)");
		cmbDepartment.v.add("Food Servicec");
		cmbDepartment.v.add("Environmental Service");
		cmbDepartment.v.add("Administrative Assistant");

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
	}
}