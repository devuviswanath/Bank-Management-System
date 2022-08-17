package banking;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.table.DefaultTableModel;

public class ViewAllCustomer extends JInternalFrame {

	private JPanel jpFind = new JPanel();
	private JPanel jpShow = new JPanel ();
	private DefaultTableModel dtmCustomer;
	private JTable tbCustomer;
	private JScrollPane jspTable;

	private int row = 0;
	private int total = 0;

	//String Type Array use to Load Records into File.
	private String rowData[][];

	private FileInputStream fis;
	private DataInputStream dis;
	ViewAllCustomer() {
		String account ;
		String name ;
		Double balance;
		String date;
		String prints ="";
		String prints2 ="";
		int cont = 0;
		int cont2 = 0;
		
		String url ="jdbc:sqlserver://localhost;databaseName=BankManagement;integratedSecurity=true";
		
		System.out.println("Connecting database...");

		try (Connection conn = DriverManager.getConnection(url)) {
		    System.out.println("Database connected!");
		    
			String sqlInsert = "select * from Account";
			Statement statement = conn.createStatement();
									
			ResultSet rs = statement.executeQuery(sqlInsert);
			
			 while (rs.next()) {
				 account = rs.getString("No_Account");
				  name = rs.getString("User_Name");
				  date = rs.getString("Date");
				  balance = rs.getDouble("balance");
				  String data;
					String data0 = "               BankSystem [Pvt] Limited.               \n";
					String data1 = "               Customer Balance Report.              \n\n";
					String data2 = "  Account No.:       " + account + "\n";
					String data3 = "  Customer Name:     " + name + "\n";
					String data4 = "  Date:   " + date + "\n\n";
					String data5 = "  Current Balance:   " + balance + "\n\n";
					String data6 = "\n";
					String sep0 = " -----------------------------------------------------------\n";
					String sep1 = " -----------------------------------------------------------\n";
					String sep2 = " -----------------------------------------------------------\n";
					String sep3 = " -----------------------------------------------------------\n";
					String sep4 = " -----------------------------------------------------------\n\n";

					data = data0 + sep0 + data1 + data2 + sep1 + data3 + sep2 + data4 + sep3 + data5 + sep4 + data6;
					
				 //cont = cont+1; 
				 JOptionPane.showMessageDialog(null, data);
			 }
			 
			 
			
			 
			 
			 //conn.close();
			 //statement.close();
		} catch (SQLException e) {

			e.printStackTrace();
		}
		
		
	}	

}