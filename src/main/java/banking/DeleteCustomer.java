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

public class DeleteCustomer extends JInternalFrame implements ActionListener {

	private JPanel jpDel = new JPanel();
	private JLabel lbNo, lbName, lbDate, lbBal;
	private JTextField txtNo, txtName, txtDate, txtBal;
	private JButton btnDel, btnCancel;
	String account, name, date;
	double balance;

	private int recCount = 0;
	private int rows = 0;
	private	int total = 0;

	//String Type Array use to Load Records From File.
	private String records[][] = new String [500][6];

	private FileInputStream fis;
	private DataInputStream dis;

	DeleteCustomer () {

		// super(Title, Resizable, Closable, Maximizable, Iconifiable)
		super ("Delete Account Holder", false, true, false, true);
		setSize (350, 235);
		
		jpDel.setBounds (0, 0, 500, 115);
		jpDel.setLayout (null);

		lbNo = new JLabel ("Account No:");
		lbNo.setForeground (Color.black);
		lbNo.setBounds (15, 20, 80, 25);
	    lbName = new JLabel ("Person Name:");
		lbName.setForeground (Color.black);
	    lbName.setBounds (15, 55, 90, 25);

		txtNo = new JTextField ();
		txtNo.setHorizontalAlignment (JTextField.RIGHT);
		txtNo.setBounds (125, 20, 200, 25);
		txtName = new JTextField ();
		txtName.setEnabled (true);
		txtName.setBounds (125, 55, 200, 25);

		//Aligning The Buttons.
		btnDel = new JButton ("Delete");
		btnDel.setBounds (20, 165, 120, 25);
		btnDel.addActionListener (this);
		btnCancel = new JButton ("Cancel");
		btnCancel.setBounds (200, 165, 120, 25);
		btnCancel.addActionListener (this);

		//Adding the All the Controls to Panel.
		jpDel.add (lbNo);
		jpDel.add (txtNo);
		jpDel.add (lbName);
		jpDel.add (txtName);
		jpDel.add (btnDel);
		jpDel.add (btnCancel);

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

		//Adding Panel to Window.
		getContentPane().add (jpDel);

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
		
		return valid;
	}

	//Function use By Buttons of Window to Perform Action as User Click Them.
	public void actionPerformed (ActionEvent ae) {
		
		

		if (ae.getSource() == btnDel) {
			if (validation() == false) {
				JOptionPane.showMessageDialog(null, "Try Again", "Warning", JOptionPane.WARNING_MESSAGE);
			}else {
			save();
			
			}
		}
	
		

	}

	public void save() {
		String account1 = txtNo.getText();
		
		
		String url ="jdbc:sqlserver://localhost;databaseName=BankManagement;integratedSecurity=true";
		
		System.out.println("Connecting database...");

		try (Connection conn = DriverManager.getConnection(url)) {
		    System.out.println("Database connected!");
			String sqlInsert = "DELETE FROM Account  where No_Account = '"+account1+"'";
			
			Statement statement = conn.createStatement();						
			statement.executeUpdate(sqlInsert);
			
			System.out.println("Row deleted from the table");

			String sqlget = "select * from Account";
			ResultSet rs = statement.executeQuery(sqlget);
			setVisible(false);
			JFrame message = new JFrame();
			JOptionPane.showMessageDialog(message, "Account Deleted");
			while (rs.next()) {
		        System.out.println(rs.getString(1) + " " + rs.getString(2));
		    }
			
			
		} catch (SQLException e) {
		    throw new IllegalStateException("Cannot connect the database!", e);
		}
		
		
	}

}	