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

public class WithdrawMoney extends JInternalFrame implements ActionListener {

	private JPanel jpWith = new JPanel();
	private JLabel lbNo, lbName, lbDate, lbWithdraw;
	private JTextField txtNo, txtName, txtWithdraw;
	private JComboBox cboMonth, cboDay, cboYear;
	private JButton btnSave, btnCancel;
	String account, name, date;
	double balance;

	private int recCount = 0;
	private int rows = 0;
	private	int total = 0;
	private	int curr;
	private	int withdraw;

	//String Type Array use to Load Records From File.
	private String records[][] = new String [500][6];

	private FileInputStream fis;
	private DataInputStream dis;

	WithdrawMoney () {

		// super(Title, Resizable, Closable, Maximizable, Iconifiable)
		super ("Withdraw Money", false, true, false, true);
		setSize (335, 235);

		jpWith.setLayout (null);

		lbNo = new JLabel ("Account No:");
		lbNo.setForeground (Color.black);
		lbNo.setBounds (15, 20, 80, 25);
	        lbName = new JLabel ("Person Name:");
		lbName.setForeground (Color.black);
	        lbName.setBounds (15, 55, 80, 25);
		lbDate = new JLabel ("With. Date:");
		lbDate.setForeground (Color.black);
		lbDate.setBounds (15, 90, 80, 25);
		lbWithdraw = new JLabel ("With. Amount:");
		lbWithdraw.setForeground (Color.black);
		lbWithdraw.setBounds (15, 125, 80, 25);

		txtNo = new JTextField ();
		txtNo.setHorizontalAlignment (JTextField.RIGHT);
		//Checking the Accunt No. Provided By User on Lost Focus of the TextBox.

		txtNo.setBounds (105, 20, 205, 25);

		txtName = new JTextField ();
		txtName.setEnabled (true);
		txtName.setBounds (105, 55, 205, 25);
		txtWithdraw = new JTextField ();
		txtWithdraw.setHorizontalAlignment (JTextField.RIGHT);
		txtWithdraw.setBounds (105, 125, 205, 25);

		//Restricting The User Input to only Numerics in Numeric TextBoxes.
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
		txtWithdraw.addKeyListener (new KeyAdapter() {
			public void keyTyped (KeyEvent ke) {
				char c = ke.getKeyChar ();
				if (!((Character.isDigit (c) || (c == KeyEvent.VK_BACK_SPACE)))) {
					getToolkit().beep ();
					ke.consume ();
      				}
    			}
  		}
		);

		//Creating Date Option.
		String Months[] = {"January", "February", "March", "April", "May", "June",
			"July", "August", "September", "October", "November", "December"};
		cboMonth = new JComboBox (Months);
		cboDay = new JComboBox ();
		cboYear = new JComboBox ();
		for (int i = 1; i <= 31; i++) {
			String days = "" + i;
			cboDay.addItem (days);
		}
		for (int i = 2000; i <= 2015; i++) {
			String years = "" + i;
			cboYear.addItem (years);
		}

		//Aligning The Date Option Controls.
		cboMonth.setBounds (105, 90, 92, 25);
		cboDay.setBounds (202, 90, 43, 25);
		cboYear.setBounds (250, 90, 60, 25);

		//Aligning The Buttons.
		btnSave = new JButton ("Save");
		btnSave.setBounds (20, 165, 120, 25);
		btnSave.addActionListener (this);
		btnCancel = new JButton ("Cancel");
		btnCancel.setBounds (185, 165, 120, 25);
		btnCancel.addActionListener (this);

		//Adding the All the Controls to Panel.
		jpWith.add (lbNo);
		jpWith.add (txtNo);
		jpWith.add (lbName);
		jpWith.add (txtName);
		jpWith.add (lbDate);
		jpWith.add (cboMonth);
		jpWith.add (cboDay);
		jpWith.add (cboYear);
		jpWith.add (lbWithdraw);
		jpWith.add (txtWithdraw);
		jpWith.add (btnSave);
		jpWith.add (btnCancel);

		//Adding Panel to Window.
		getContentPane().add (jpWith);
//In the End Showing the New Account Window.
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
		else if (txtWithdraw.getText().equals("")) {
			JOptionPane.showMessageDialog (this, "Please! Provide Withdrawal Amount.",
					"BankingSystem - EmptyField", JOptionPane.PLAIN_MESSAGE);
			txtWithdraw.requestFocus ();
			valid = false;
		}
		return valid;
	}

	//Function use By Buttons of Window to Perform Action as User Click Them.
	public void actionPerformed (ActionEvent ae) {
		
		

		if (ae.getSource() == btnSave) {
			if (validation() == false) {
				JOptionPane.showMessageDialog(null, "Try Again", "Warning", JOptionPane.WARNING_MESSAGE);
			}else {
			save();
			
			}
		}
	

	}
	public void save() {
		account = txtNo.getText();
		name = txtName.getText();
		balance =Double.parseDouble( txtWithdraw.getText());
		
		String url ="jdbc:sqlserver://localhost;databaseName=BankManagement;integratedSecurity=true";
		
		System.out.println("Connecting database...");

		try (Connection conn = DriverManager.getConnection(url)) {
		    System.out.println("Database connected!");
			String sqlInsert = "UPDATE Account set balance = balance - '"+balance+"' where No_Account = '"+account+"'";
			
			Statement statement = conn.createStatement();						
			statement.executeUpdate(sqlInsert);
			
			System.out.println("Row added to the table");

			String sqlget = "select * from Account";
			ResultSet rs = statement.executeQuery(sqlget);
			setVisible(false);
			JFrame message = new JFrame();
			
			if (rs.next()) {
		      //  JOptionPane.showMessageDialog(null,"Amount Updated" + "Accoun N0: "+rs.getString(1) + " Name: " + rs.getString(2)+ " Date: " + rs.getString(3)+ " Balance: " + rs.getString(4), "Deposit Money", JOptionPane.INFORMATION_MESSAGE);
			  JOptionPane.showMessageDialog(null,"Amount Updated", "Deposit Money", JOptionPane.INFORMATION_MESSAGE);
		    }
			
			
		} catch (SQLException e) {
		    throw new IllegalStateException("Cannot connect the database!", e);
		}
		
		
	}
}	