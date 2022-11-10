package com.RoomInfo;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.JobAttributes;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.ResultSet;
import java.util.StringTokenizer;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;

import com.example.Admin.SuggestText;
import com.example.Admin.dbConneciton;

public class WordEntry extends JPanel{

	JLabel lblImage=new JLabel(new ImageIcon("images/backgroundImage4.jpg"));

	JPanel panelWest=new JPanel();
	JPanel panelWestNorth=new JPanel();
	JPanel panelWestCenter=new JPanel();
	JPanel panelWestSouth=new JPanel();
	JPanel panelCenter=new JPanel();
	
	JLabel lblSearch=new JLabel("Search: ");
	SuggestText cmbSearch=new SuggestText();
	JLabel lblSearchIcon=new JLabel(new ImageIcon("Images/CenterImage.png"));

	JLabel lblWordNo=new JLabel("Word No.");
	JLabel lblWordName=new JLabel("Word Name");

	JTextField txtWordNo=new JTextField(20);
	JTextField txtWordName=new JTextField();

	String col[]={"Word No.","Word Name"};
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

	public WordEntry(){
		setPreferredSize(new Dimension(1365, 730));
		setBackground(Color.ORANGE);
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
		txtWordName.setEditable(b);
	}
	public void txtClear(){
		txtWordNo.setText("");
		txtWordName.setText("");
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
						if(isExistWordName()){
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
		});
		cmbSearch.cmbSuggest.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(!cmbSearch.txtSuggest.getText().trim().toString().isEmpty()){
					StringTokenizer token=new StringTokenizer(cmbSearch.txtSuggest.getText().trim().
							toString(), "#");
					String wordNo=token.nextToken();
					searchDataLoad(wordNo);
					btnIni(false);
					editable(false);
					tableDataLoad();
				}
				else{
					refreshWork();
				}
			}
		});
		table.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				String wordNo=model.getValueAt(table.getSelectedRow(), 0).toString();
				searchDataLoad(wordNo);
				btnIni(false);
				editable(false);
				cmbSearchDataLoad();
				cmbSearch.txtSuggest.setText("");
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
	}
	public void autoId(){
		try {
			String sql="select ifnull(max(cast(substring(wordNo,locate('-',wordNo)+1,"
					+ "length(wordNo)-locate('-',wordNo)) as UNSIGNED)),0)+1 as wordNo from tbwordinfo";
			dbConneciton.connection();
			ResultSet rs=dbConneciton.sta.executeQuery(sql);
			if(rs.next()){
				txtWordNo.setText("WordNo-"+rs.getString("wordNo"));
			}
			dbConneciton.con.close();
		} 
		catch (Exception e) {
			JOptionPane.showMessageDialog(null, e+"autoId()");
		}
	}
	public boolean checkValidation(){
		if(!txtWordNo.getText().trim().toString().isEmpty()){
			if(!txtWordName.getText().trim().toString().isEmpty()){
				return true;
			}
			else{
				JOptionPane.showMessageDialog(null, "insert wordName please.");
			}
		}
		else{
			JOptionPane.showMessageDialog(null, "insert wordNo please.");
		}
		return false;
	}
	public boolean isExistWordName(){
		try {
			String sql="select * from tbwordinfo where wordName='"+txtWordName.getText().trim().toString()+"'";
			dbConneciton.connection();
			ResultSet rs=dbConneciton.sta.executeQuery(sql);
			while(rs.next()){
				JOptionPane.showMessageDialog(null, "This word is already exist!","Warning...",
						JOptionPane.WARNING_MESSAGE);
				return false;
			}
			dbConneciton.con.close();
		} 
		catch (Exception e) {
			JOptionPane.showMessageDialog(null, e+"isExistWordName()");
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
	public boolean insertData(){
		try {
			String sql="insert into tbwordinfo(wordNo,wordName,userip,entryTime)"
					+ "values('"+txtWordNo.getText().trim().toString()+"',"
					+ "'"+txtWordName.getText().trim().toString()+"',"
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
			String sql="select wordNo,wordName from tbwordinfo order by wordNo";
			dbConneciton.connection();
			ResultSet rs=dbConneciton.sta.executeQuery(sql);
			while(rs.next()){
				model.addRow(new Object[]{rs.getString("wordNo"),rs.getString("wordName")});
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
			String sql="select wordNo,wordName from tbwordinfo order by wordNo";
			dbConneciton.connection();
			ResultSet rs=dbConneciton.sta.executeQuery(sql);
			while(rs.next()){
				cmbSearch.v.add(rs.getString("wordNo")+"#"+rs.getString("wordName"));
			}
			dbConneciton.con.close();
		} 
		catch (Exception e) {
			JOptionPane.showMessageDialog(null, e+"cmbSearchDataLoad()");
		}
	}
	public void searchDataLoad(String wordNo){
		try {
			String sql="select wordNo,wordName from tbwordinfo where wordNo='"+wordNo+"'";
			dbConneciton.connection();
			ResultSet rs=dbConneciton.sta.executeQuery(sql);
			while(rs.next()){
				txtWordNo.setText(rs.getString("wordNo"));
				txtWordName.setText(rs.getString("wordName"));
			}
			dbConneciton.con.close();
		} 
		catch (Exception e) {
			JOptionPane.showMessageDialog(null, e+"searchDataLoad()");
		}
	}
	public boolean deleteData(){
		try {
			String sql="delete from tbwordinfo where wordNo='"+txtWordNo.getText().trim().toString()+"'";
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
		TitledBorder title=BorderFactory.createTitledBorder("Word Entry Form");
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
		panelWestCenter.add(lblWordNo,c);

		c.gridx=1;
		c.gridy=0;
		panelWestCenter.add(txtWordNo,c);
		txtWordNo.setEditable(false);

		c.gridx=0;
		c.gridy=1;
		panelWestCenter.add(lblWordName,c);

		c.gridx=1;
		c.gridy=1;
		panelWestCenter.add(txtWordName,c);
	}
	private void panelWestSouth() {
		panelWestSouth.setOpaque(false);
		panelWestSouth.setPreferredSize(new Dimension(0, 270));
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
		TitledBorder title=BorderFactory.createTitledBorder("Word List");
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
		panelCenter.add(scrollTable,cn);
		scrollTable.setPreferredSize(new Dimension(800,650));
		table.getTableHeader().setReorderingAllowed(false);
	}
}
