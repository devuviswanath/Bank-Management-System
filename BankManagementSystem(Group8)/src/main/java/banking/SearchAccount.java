package banking;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class SearchAccount extends JInternalFrame implements ActionListener {

	private JPanel jpFind = new JPanel();
	private JLabel lbNo, lbName, lbDate, lbBal;
	private JTextField txtNo, txtName, txtDate, txtBal;
	private JButton btnFind, btnCancel;
	String account, name, date;
	double balance;

	private int count = 0;
	private int rows = 0;
	private	int total = 0;

	//String Type Array use to Load Records From File.
	private String records[][] = new String [500][6];
	//String Type Array use to Save Records into File.
	private String saves[][] = new String [500][6];
	
	private FileInputStream fis;
	private DataInputStream dis;

	SearchAccount () {

		//super(Title, Resizable, Closable, Maximizable, Iconifiable)
		super ("Search Customer [By No.]", false, true, false, true);
		setSize (350, 235);

		jpFind.setLayout (null);

		lbNo = new JLabel ("Account No:");
		lbNo.setForeground (Color.black);
		lbNo.setBounds (15, 20, 80, 25);
	        lbName = new JLabel ("Person Name:");
		lbName.setForeground (Color.black);
	        lbName.setBounds (15, 55, 80, 25);

		txtNo = new JTextField ();
		txtNo.setHorizontalAlignment (JTextField.RIGHT);
		txtNo.setBounds (125, 20, 200, 25);
		txtName = new JTextField ();
		txtName.setEnabled (true);
		txtName.setBounds (125, 55, 200, 25);

		//Restricting The User Input to only Numerics.
		txtNo.addKeyListener (new KeyAdapter() {
			public void keyTyped (KeyEvent ke) {
				char c = ke.getKeyChar ();
				if (!((Character.isDigit (c) || (c == KeyEvent.VK_BACK_SPACE)))) {
					getToolkit().beep ();
					ke.consume ();
      				}
    			}
  		}
		);

		//Aligning The Buttons.
		btnFind = new JButton ("Search");
		btnFind.setBounds (20, 165, 120, 25);
		btnFind.addActionListener (this);
		btnCancel = new JButton ("Cancel");
		btnCancel.setBounds (200, 165, 120, 25);
		btnCancel.addActionListener (this);

		//Adding the All the Controls to Panel.
		jpFind.add (lbNo);
		jpFind.add (txtNo);
		jpFind.add (lbName);
		jpFind.add (txtName);
		jpFind.add (btnFind);
		jpFind.add (btnCancel);

		//Adding Panel to Window.
		getContentPane().add (jpFind);//In the End Showing the New Account Window.
		setVisible (true);

	}
	public boolean validation() {
		boolean valid = true;
		if (txtNo.getText().equals("")) {
			JOptionPane.showMessageDialog (this, "Please! Provide Id of Customer.",
					"BankingSystem - EmptyField", JOptionPane.PLAIN_MESSAGE);
			txtNo.requestFocus();
			valid = false;
		}
		else if (txtName.getText().equals("")) {
			JOptionPane.showMessageDialog (this, "Please! Provide Name of Customer.",
					"BankingSystem - EmptyField", JOptionPane.PLAIN_MESSAGE);
			txtName.requestFocus ();
			valid = false;
		}
		
		return valid;
	}

	//Function use By Buttons of Window to Perform Action as User Click Them.
	public void actionPerformed (ActionEvent ae) {
		
		

		if (ae.getSource() == btnFind) {
			if (validation() == false) {
				JOptionPane.showMessageDialog(null, "Try Again", "Warning", JOptionPane.WARNING_MESSAGE);
			}else {
			save();
			
			}
		}	

	}

	//Function use to load all Records from File when Application Execute.
	public void save() {
		String account ;
		String name ;
		Double balance;
		String date;
		String prints ="";
		String prints2 ="";
		int cont = 0;
		int cont2 = 0;
		
		String account1 = txtNo.getText();
		String name1 = txtName.getText();
		
		
		String url ="jdbc:sqlserver://localhost;databaseName=BankManagement;integratedSecurity=true";
		
		System.out.println("Connecting database...");

		try (Connection conn = DriverManager.getConnection(url)) {
		    System.out.println("Database connected!");
		    
			String sqlInsert = "select * from Account where No_Account = '"+account1+"'";
			Statement statement = conn.createStatement();
									
			ResultSet rs = statement.executeQuery(sqlInsert);
			setVisible(false);
			JFrame message = new JFrame();
			 if (rs.next()) {
				// System.out.println(rs.getString("No_Account") + ", " + rs.getString("User_Name")+ ", " + rs.getString("Date")+ ", " + rs.getString("balance"));
				  
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
				 JOptionPane.showMessageDialog(null, data);
			 }
			 
			 
			
			 
			 
			 //conn.close();
			 //statement.close();
		} catch (SQLException e) {

			e.printStackTrace();
		}
		
		
	}

}	