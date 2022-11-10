package com.RoomInfo;

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
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.ResultSet;
import java.util.Date;
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

public class BedNoEntry extends JPanel{

	JLabel lblImage=new JLabel(new ImageIcon("images/backgroundImage4.jpg"));

	JPanel panelWest=new JPanel();
	JPanel panelWestNorth=new JPanel();
	JPanel panelWestCenter=new JPanel();
	JPanel panelWestSouth=new JPanel();
	JPanel panelCenter=new JPanel();
	JPanel panelCenterNorth=new JPanel();
	JPanel panelCenterCenter=new JPanel();

	JLabel lblSearch=new JLabel("Search: ");
	SuggestText cmbSearch=new SuggestText();
	JLabel lblSearchIcon=new JLabel(new ImageIcon("Images/CenterImage.png"));

	JLabel lblWordName=new JLabel("Word Name");
	JLabel lblBedNo=new JLabel("Bed No.");
	JLabel lblBedCharge=new JLabel("Bed Charge");

	SuggestText cmbWordName=new SuggestText();
	JTextField txtBedNo=new JTextField(20);
	JTextField txtBedCharge=new JTextField();

	JLabel lblWordNameSearch=new JLabel("Word Name: ");
	SuggestText cmbWordNameSearch=new SuggestText();
	JLabel lblRoomNoSearch=new JLabel("Room No.");

	JButton btnSearch=new JButton("Search");
	JButton btnViewAll=new JButton("View All");

	String col[]={"Word No.","Word Name","Bed No.","Bed Charge"};
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

	public BedNoEntry(){
		setPreferredSize(new Dimension(1365, 730));
		setBackground(Color.GREEN);
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
		cmbWordName.txtSuggest.setEditable(b);
		txtBedNo.setEditable(b);
		txtBedCharge.setEditable(b);
	}
	public void txtClear(){
		cmbSearch.txtSuggest.setText("");
		cmbWordName.txtSuggest.setText("");
		cmbWordNameSearch.txtSuggest.setText("");
		txtBedNo.setText("BedNo-");
		txtBedCharge.setText("");
	}
	public void refreshWork(){
		txtClear();
		btnIni(true);
		editable(true);
		cmbWordNameDataLoad();
		//cmbWordNameSearchDataLoad();
		tableDataLoad();
		cmbSearchDataLoad();
		isUpdate=false;
	}
	public void btnAction(){
		btnAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(isUpdate){
					if(checkConfirmation("Sure to update?")){
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
						if(isExistBedNo()){
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
		});
		cmbSearch.cmbSuggest.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(!cmbSearch.txtSuggest.getText().trim().toString().isEmpty()){
					StringTokenizer token=new StringTokenizer(cmbSearch.txtSuggest.getText().trim().
							toString(), "#");
					String wordName=token.nextToken();
					String bedNo=token.nextToken();
					searchDataLoad(wordName,bedNo);
					btnIni(false);
					editable(false);
					tableDataLoad();
				}
			}
		});
		table.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				String wordName=model.getValueAt(table.getSelectedRow(), 1).toString();
				String bedNo=model.getValueAt(table.getSelectedRow(), 2).toString();
				searchDataLoad(wordName,bedNo);
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
		btnViewAll.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				tableDataLoad();
				cmbWordNameSearch.txtSuggest.setText("");
			}
		});
		btnSearch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(!cmbWordNameSearch.txtSuggest.getText().trim().toString().isEmpty()){
					cmbSearchDataWordWise();
				}
			}
		});
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
	public boolean checkValidation(){
		if(!cmbWordName.txtSuggest.getText().trim().toString().isEmpty()){
			if(!txtBedNo.getText().trim().toString().isEmpty()){
				if(!txtBedCharge.getText().trim().toString().isEmpty()){
					return true;
				}
				else{
					JOptionPane.showMessageDialog(null, "insert Bed Charge please.");
				}
			}
			else{
				JOptionPane.showMessageDialog(null, "insert Bed No. please.");
			}
		}
		else{
			JOptionPane.showMessageDialog(null, "select Word No. and Word Name please.");
		}
		return false;
	}
	public boolean isExistBedNo(){
		try {
			StringTokenizer token=new StringTokenizer(cmbWordName.txtSuggest.getText().trim().toString(), "#");
			String wordNo=token.nextToken();

			String sql="select * from tbbedinfo where wordNo='"+wordNo+"' "
					+ "and bedNo='"+txtBedNo.getText().trim().toString()+"'";
			dbConneciton.connection();
			ResultSet rs=dbConneciton.sta.executeQuery(sql);
			while(rs.next()){
				JOptionPane.showMessageDialog(null, "This bed is already Exist in this Word!");
				return false;
			}
			dbConneciton.con.close();
		} 
		catch (Exception e) {
			JOptionPane.showMessageDialog(null, e+"isExistBedNo()");
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
			StringTokenizer token=new StringTokenizer(cmbWordName.txtSuggest.getText().trim().toString(), "#");
			String wordNo=token.nextToken();
			String wordName=token.nextToken();

			String sql="insert into tbbedinfo(wordNo,wordName,bedNo,bedCharge,userName,userip,entryTime)"
					+ "values('"+wordNo+"',"
					+ "'"+wordName+"',"
					+ "'"+txtBedNo.getText().trim().toString()+"',"
					+ "'"+txtBedCharge.getText().trim().toString()+"',"
					+ "'','',now())";
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
			String sql="select wordNo,wordName,bedNo,bedCharge from tbbedinfo order by wordNo";
			dbConneciton.connection();
			ResultSet rs=dbConneciton.sta.executeQuery(sql);
			while(rs.next()){
				model.addRow(new Object[]{rs.getString("wordNo"),rs.getString("wordName")
						,rs.getString("bedNo"),rs.getString("bedCharge")});
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
			String sql="select wordName,bedNo from tbbedinfo";
			dbConneciton.connection();
			ResultSet rs=dbConneciton.sta.executeQuery(sql);
			while(rs.next()){
				cmbSearch.v.add(rs.getString("wordName")+"#"+rs.getString("bedNo"));
			}
			dbConneciton.con.close();
		} 
		catch (Exception e) {
			JOptionPane.showMessageDialog(null, e+"cmbSearchDataLoad()");
		}
	}
	public void searchDataLoad(String wordName, String bedNo){
		try {
			String sql="select wordNo,wordName,bedNo,bedCharge from tbbedinfo "
					+ "where wordName='"+wordName+"' and bedNo='"+bedNo+"'";
			dbConneciton.connection();
			ResultSet rs=dbConneciton.sta.executeQuery(sql);
			while(rs.next()){
				cmbWordName.txtSuggest.setText(rs.getString("wordNo")+"#"+rs.getString("wordName"));
				txtBedNo.setText(rs.getString("bedNo"));
				txtBedCharge.setText(rs.getString("bedCharge"));
			}
			dbConneciton.con.close();
		} 
		catch (Exception e) {
			JOptionPane.showMessageDialog(null, e+"searchDataLoad()");
		}
	}
	public boolean deleteData(){
		try {
			StringTokenizer token=new StringTokenizer(cmbWordName.txtSuggest.getText().trim().toString(),
					"#");
			String wordNo=token.nextToken();

			String sql="delete from tbbedinfo where wordNo='"+wordNo+"' and "
					+ "bedNo='"+txtBedNo.getText().trim().toString()+"'";
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
	public void cmbWordNameSearchDataLoad(){
		try {
			cmbWordNameSearch.v.clear();
			cmbWordNameSearch.v.add("");
			String sql="select wordNo,wordName from tbwordinfo";
			dbConneciton.connection();
			ResultSet rs=dbConneciton.sta.executeQuery(sql);
			while(rs.next()){
				cmbWordNameSearch.v.add(rs.getString("wordNo")+"#"+rs.getString("wordName"));
			}
			dbConneciton.con.close();
		} 
		catch (Exception e) {
			JOptionPane.showMessageDialog(null, e+"cmbWordNameSearchDataLoad()");
		}
	}
	public void cmbSearchDataWordWise(){
		try {
			StringTokenizer token=new StringTokenizer(cmbWordNameSearch.txtSuggest.getText().trim().toString()
					,"#");
			String wordNo=token.nextToken();
			
			for(int i=model.getRowCount()-1; i>=0; i--){
				model.removeRow(i);
			}
			String sql="select wordNo,wordName,bedNo,bedCharge from tbbedinfo where "
					+ "wordNo='"+wordNo+"'";
			dbConneciton.connection();
			ResultSet rs=dbConneciton.sta.executeQuery(sql);
			while(rs.next()){
				model.addRow(new Object[]{rs.getString("wordNo"),rs.getString("wordName")
						,rs.getString("bedNo"),rs.getString("bedCharge")});
			}
			dbConneciton.con.close();
		} 
		catch (Exception e) {
			JOptionPane.showMessageDialog(null, e+"cmbSearchDataWordWise()");
		}
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
		TitledBorder title=BorderFactory.createTitledBorder("Bed Entry Form");
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
		panelWestCenter.add(lblWordName,c);

		c.gridx=1;
		c.gridy=0;
		panelWestCenter.add(cmbWordName.cmbSuggest,c);

		c.gridx=0;
		c.gridy=1;
		panelWestCenter.add(lblBedNo,c);

		c.gridx=1;
		c.gridy=1;
		panelWestCenter.add(txtBedNo,c);
		txtBedNo.setText("BedNo-");

		c.gridx=0;
		c.gridy=2;
		panelWestCenter.add(lblBedCharge,c);

		c.gridx=1;
		c.gridy=2;
		panelWestCenter.add(txtBedCharge,c);
	}
	private void panelWestSouth() {
		panelWestSouth.setOpaque(false);
		panelWestSouth.setPreferredSize(new Dimension(0, 280));
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
	private void panelCenter(){
		panelCenter.setOpaque(false);
		TitledBorder title=BorderFactory.createTitledBorder("Bed List");
		title.setTitleColor(Color.decode("#8B0000"));
		title.setTitleFont(new Font("Carlibri", Font.BOLD, 14));
		panelCenter.setBorder(title);

		panelCenter.setLayout(new BorderLayout());
		panelCenter.add(panelCenterNorth, BorderLayout.NORTH);
		panelCenterNorth();
		panelCenter.add(panelCenterCenter, BorderLayout.CENTER);
		panelCenterCenter();
	}
	private void panelCenterNorth() {
		panelCenterNorth.setPreferredSize(new Dimension(0, 60));
		panelCenterNorth.setOpaque(false);
		GridBagConstraints cn=new GridBagConstraints();
		GridBagLayout grid=new GridBagLayout();
		panelCenterNorth.setLayout(grid);

		cn.gridx=0;
		cn.gridy=0;
		cn.fill=GridBagConstraints.BOTH;
		cn.insets=new Insets(5, 5, 5, 5);
		panelCenterNorth.add(lblWordNameSearch,cn);

		cn.gridx=1;
		cn.gridy=0;
		cn.fill=GridBagConstraints.BOTH;
		cn.insets=new Insets(5, 5, 5, 5);
		panelCenterNorth.add(cmbWordNameSearch.cmbSuggest,cn);
		cmbWordNameSearch.cmbSuggest.setPreferredSize(new Dimension(200, 30));

		cn.gridx=2;
		cn.gridy=0;
		cn.fill=GridBagConstraints.BOTH;
		cn.insets=new Insets(5, 5, 5, 5);
		panelCenterNorth.add(btnSearch,cn);
		btnSearch.setBackground(Color.decode("#E68A00"));
		btnSearch.setForeground(Color.WHITE);

		cn.gridx=3;
		cn.gridy=0;
		cn.fill=GridBagConstraints.BOTH;
		cn.insets=new Insets(5, 5, 5, 5);
		panelCenterNorth.add(btnViewAll,cn);
		btnViewAll.setBackground(Color.decode("#DA190B"));
		btnViewAll.setForeground(Color.WHITE);
	}
	private void panelCenterCenter() {
		panelCenterCenter.setOpaque(false);
		GridBagConstraints cn=new GridBagConstraints();
		GridBagLayout grid=new GridBagLayout();
		panelCenterCenter.setLayout(grid);

		cn.gridx=0;
		cn.gridy=0;
		cn.fill=GridBagConstraints.BOTH;
		cn.insets=new Insets(5, 5, 5, 5);
		panelCenterCenter.add(scrollTable,cn);
		scrollTable.setPreferredSize(new Dimension(800,600));
		table.getTableHeader().setReorderingAllowed(false);
	}
}
