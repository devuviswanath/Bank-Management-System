package banking;

import java.awt.*;

import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import java.util.*;
import java.text.*;
import java.io.*;
import java.awt.PrintJob.*;
import javax.swing.plaf.metal.*;

public class BankingSystem extends JFrame implements ActionListener, ItemListener {

	//Main Place on Form where All Child Forms will Shown.
	private JDesktopPane desktop = new JDesktopPane ();

	//For Program's MenuBar.
	private JMenuBar bar;

	//All the Main Menu of the Program.
	private JMenu mnuFile, mnuEdit, mnuView,mnuService;

	private JMenuItem addNew;				//File Menu Options.
	private	JMenuItem delRec;	//Edit Menu Options.
	private	JMenuItem search, searchName, allCustomer;				//View Menu Options.
	private	JMenuItem deposit, withdraw; // Service menu for cash transfer
	//PopupMenu of Program.
	private JPopupMenu popMenu = new JPopupMenu ();

	//MenuItems for PopupMenu of the Program.
	private JMenuItem open, dep, with, del, find, all;

	//For Program's ToolBar.
	private	JToolBar toolBar;

	//For ToolBar's Button.
	private	JButton btnNew, btnDep, btnWith,btnDel, btnSrch;

	//Main Form StatusBar where Program's Name & Welcome Message Display.
	private JPanel statusBar = new JPanel ();
	

	//Labels for Displaying Program's Name & saying Welcome to Current User on StatusBar.
	private JLabel welcome;
	private JLabel author;
	//private JLabel displayLabel;

	//Making the LookAndFeel Menu.
	private String strings[] = {"1. Metal", "2. Motif", "3. Windows"};
	private UIManager.LookAndFeelInfo looks[] = UIManager.getInstalledLookAndFeels ();
	private ButtonGroup group = new ButtonGroup ();
	private JRadioButtonMenuItem radio[] = new JRadioButtonMenuItem[strings.length];

	//Getting the Current System Date.
	private java.util.Date currDate = new java.util.Date ();
	private SimpleDateFormat sdf = new SimpleDateFormat ("dd MMMM yyyy", Locale.getDefault());
	private String d = sdf.format (currDate);

	//Following all Variables are use in BankingSystem's IO's.

	//Variable use in Reading the BankingSystem Records File & Store it in an Array.
	private int count = 0;
	private int rows = 0;
	private	int total = 0;

	//String Type Array use to Load Records From File.
	private String records[][] = new String [500][6];

	//Variable for Reading the BankingSystem Records File.
	private FileInputStream fis;
	private DataInputStream dis;

	//Constructor of The Bank Program to Initialize all Variables of Program.

	public BankingSystem () {

		//Setting Program's Title.
		super ("Bank Management System.");

		UIManager.addPropertyChangeListener (new UISwitchListener ((JComponent)getRootPane()));

		//Creating the MenuBar.
		bar = new JMenuBar ();

		//Setting the Main Window of Program.
		setIconImage (getToolkit().getImage ("src/main/resources/Images/Bank.gif"));
		setSize (700, 550);
		setJMenuBar (bar);

		//Closing Code of Main Window.
		addWindowListener (new WindowAdapter () {
			public void windowClosing (WindowEvent we) {
				quitApp ();
			}
		}
		);

		//Setting the Location of Application on Screen.
		setLocation((Toolkit.getDefaultToolkit().getScreenSize().width  - getWidth()) / 2,
			(Toolkit.getDefaultToolkit().getScreenSize().height - getHeight()) / 2);

		//Creating the MenuBar Items.
		mnuFile = new JMenu ("File");
		mnuFile.setMnemonic ((int)'F');
		mnuEdit = new JMenu ("Edit");
		mnuEdit.setMnemonic ((int)'E');
		mnuView = new JMenu ("View");
		mnuView.setMnemonic ((int)'V');
		mnuService = new JMenu ("Service");
		mnuService.setMnemonic ((int)'S');

		//Creating the MenuItems of Program.
		//MenuItems for FileMenu.
		
		addNew = new JMenuItem ("Open New Account", new ImageIcon("src/main/resources/Images/Open.gif"));
		addNew.setAccelerator (KeyStroke.getKeyStroke(KeyEvent.VK_N, Event.CTRL_MASK));
		addNew.setMnemonic ((int)'N');
		addNew.addActionListener (this);
	

		//MenuItems for EditMenu.
		
		delRec = new JMenuItem ("Delete Customer", new ImageIcon ("src/main/resources/Images/delete.gif"));
		delRec.setAccelerator (KeyStroke.getKeyStroke(KeyEvent.VK_D, Event.CTRL_MASK));
		delRec.setMnemonic ((int)'D');
		delRec.addActionListener (this);

		//MenuItems for ViewMenu.
		search = new JMenuItem ("Search By No.", new ImageIcon ("src/main/resources/Images/Search.gif"));
		search.setAccelerator (KeyStroke.getKeyStroke(KeyEvent.VK_S, Event.CTRL_MASK));
		search.setMnemonic ((int)'S');	
		search.addActionListener (this);
		
		searchName = new JMenuItem ("Search By Name");
		searchName.setAccelerator (KeyStroke.getKeyStroke(KeyEvent.VK_M, Event.CTRL_MASK));
		searchName.setMnemonic ((int)'M');
		searchName.addActionListener (this);

		
		allCustomer = new JMenuItem ("View All Customer");
		allCustomer.setAccelerator (KeyStroke.getKeyStroke(KeyEvent.VK_A, Event.CTRL_MASK));
		allCustomer.setMnemonic ((int)'A');
		allCustomer.addActionListener (this);

		

		//MenuItems for ServiceMenu.
		
		deposit = new JMenuItem ("Deposit Money");
		deposit.setAccelerator (KeyStroke.getKeyStroke(KeyEvent.VK_T, Event.CTRL_MASK));
		deposit.setMnemonic ((int)'T');
		deposit.addActionListener (this);
		
		withdraw = new JMenuItem ("Withdraw Money");
		withdraw.setAccelerator (KeyStroke.getKeyStroke(KeyEvent.VK_W, Event.CTRL_MASK));
		withdraw.setMnemonic ((int)'W');	
		withdraw.addActionListener (this);

		//Adding MenuItems to Menu.
	
		//File Menu Items.
		mnuFile.add (addNew);

		//Edit Menu Items.

		mnuEdit.add (delRec);

		//View Menu Items.
		mnuView.add (search);
		mnuView.addSeparator ();
		mnuView.add (searchName);
		mnuView.addSeparator ();
		mnuView.add (allCustomer);

		//Service Menu Items.	
		mnuService.add (deposit);
		mnuService.addSeparator ();
		mnuService.add (withdraw);

		//Adding Menu's to Bar.
		bar.add (mnuFile);
		bar.add (mnuEdit);
		bar.add (mnuView);
		bar.add (mnuService);
		
		//MenuItems for PopupMenu.
		open = new JMenuItem ("Open New Account", new ImageIcon ("src/main/resources/Images/NotePad.gif"));
		open.addActionListener (this);
		dep = new JMenuItem ("Deposit Money", new ImageIcon ("src/main/resources/Images/ImationDisk.gif"));
		dep.addActionListener (this);
		with = new JMenuItem ("Withdraw Money", new ImageIcon ("src/main/resources/Images/SuperDisk.gif"));
		with.addActionListener (this);
		del = new JMenuItem ("Delete Customer", new ImageIcon ("src/main/resources/Images/delete.gif"));
		del.addActionListener (this);
		find = new JMenuItem ("Search Customer", new ImageIcon ("src/main/resources/Images/find.gif"));
		find.addActionListener (this);
		all = new JMenuItem ("View All Customer", new ImageIcon ("src/main/resources/Images/search.gif"));
		all.addActionListener (this);

		//Adding Menu's to PopupMenu.
		popMenu.add (open);
		popMenu.addSeparator();
		popMenu.add (dep);
		popMenu.add (with);
		popMenu.add (del);
		popMenu.add (find);
		popMenu.add (all);

		//Following Procedure display the PopupMenu of Program.
		addMouseListener (new MouseAdapter () {
			public void mousePressed (MouseEvent me) { checkMouseTrigger (me); }
			public void mouseReleased (MouseEvent me) { checkMouseTrigger (me); }
			private void checkMouseTrigger (MouseEvent me) {
				if (me.isPopupTrigger ())
					popMenu.show (me.getComponent (), me.getX (), me.getY ());
			}
		}
		);
		
		//Creating the ToolBar's Buttons of Program
		btnNew = new JButton ("Create New Account");
		btnNew.setBounds(400, 20, 200, 50);
		btnNew.addActionListener (this);
		btnDep = new JButton ("Deposit Money");
		btnDep.setBounds (400, 80, 200, 50);
		btnDep.addActionListener (this);
		btnWith = new JButton ("Withdraw Money");
		btnWith.setBounds (400, 140, 200, 50);
		btnWith.addActionListener (this);
		btnDel = new JButton ("Delete Customer");
		btnDel.setBounds (400, 200, 200, 50);
		btnDel.addActionListener (this);
		btnSrch = new JButton ("Search Customer");
		btnSrch.setBounds (400, 260, 200, 50);
		btnSrch.addActionListener (this);
		

		//Creating the ToolBar of Program.
		toolBar = new JToolBar ();
		toolBar.add (btnNew);
		toolBar.addSeparator ();
		toolBar.add (btnDep);
		toolBar.add (btnWith);
		toolBar.addSeparator ();
		toolBar.add (btnDel);
		toolBar.addSeparator ();
		toolBar.add (btnSrch);
		toolBar.addSeparator ();

		//Creating the StatusBar of Program.
		author = new JLabel (" " + "Bank Management System", Label.LEFT);
		author.setForeground (Color.black);
		author.setToolTipText ("Program's Title");
		welcome = new JLabel ("Welcome Today is " + d + " ", JLabel.RIGHT);
		welcome.setForeground (Color.black);
		welcome.setToolTipText ("Welcoming the User & System Current Date");
		statusBar.setLayout (new BorderLayout());
		statusBar.add (author, BorderLayout.WEST);
		statusBar.add (welcome, BorderLayout.EAST);
		
		desktop.add(btnNew);
		desktop.add(btnDep);
		desktop.add(btnWith);
		desktop.add(btnDel);
		desktop.add(btnSrch);
		desktop.setBackground(Color.lightGray);
		

		getContentPane().add (toolBar, BorderLayout.NORTH);
		getContentPane().add (desktop, BorderLayout.CENTER);
		//getContentPane().add (deskpanel, BorderLayout.CENTER);
		getContentPane().add (statusBar, BorderLayout.SOUTH);

		//Showing The Main Form of Application.
		setVisible (true);

	}

	//Function For Performing different Actions By Menus of Program.

	public void actionPerformed (ActionEvent ae) {
		
		Object obj = ae.getSource();

		if (obj == addNew || obj == open || obj == btnNew) {

			boolean b = openChildWindow ("Create New Account");
			if (b == false) {
				CreateAccount newAcc = new CreateAccount ();
				desktop.add (newAcc);
				newAcc.show ();
			}

		}
		else if (obj == deposit || obj == dep || obj == btnDep) {

			boolean b = openChildWindow ("Deposit Money");
			if (b == false) {
				DepositMoney depMon = new DepositMoney ();
				desktop.add (depMon);
				depMon.show ();
			}

		}
		else if (obj == withdraw || obj == with || obj == btnWith) {

			boolean b = openChildWindow ("Withdraw Money");
			if (b == false) {
				WithdrawMoney withMon = new WithdrawMoney ();
				desktop.add (withMon);
				withMon.show ();
			}

		}
		else if (obj == delRec || obj == del || obj == btnDel) {

			boolean b = openChildWindow ("Delete Account Holder");
			if (b == false) {
				DeleteCustomer delCus = new DeleteCustomer ();
				desktop.add (delCus);
				delCus.show ();
			}

		}
		else if (obj == search || obj == find || obj == btnSrch) {

			boolean b = openChildWindow ("Search Customer [By No.]");
			if (b == false) {
				SearchAccount fndAcc = new SearchAccount ();
				desktop.add (fndAcc);
				fndAcc.show ();
			}

		}
		else if (obj == searchName) {

			boolean b = openChildWindow ("Search Customer [By Name]");
			if (b == false) {
				SearchName fndNm = new SearchName ();
				desktop.add (fndNm);
				fndNm.show ();
			}

		}

		else if (obj == allCustomer || obj == all) {

			boolean b = openChildWindow ("View All Account Holders");
			if (b == false) {
				ViewAllCustomer viewCus = new ViewAllCustomer ();
				desktop.add (viewCus);
				viewCus.show ();
			}

		}

	}

	//Function Perform By LookAndFeel Menu.

	public void itemStateChanged (ItemEvent e) {

		for( int i = 0; i < radio.length; i++ )
			if(radio[i].isSelected()) {
				changeLookAndFeel (i);
			}

	}	

	//Function For Closing the Program.

	private void quitApp () {

		try {
			//Show a Confirmation Dialog.
		    	int reply = JOptionPane.showConfirmDialog (this,
					"Are you really want to exit\nFrom BankingSystem?",
					"BankingSystem - Exit", JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE);
			//Check the User Selection.
			if (reply == JOptionPane.YES_OPTION) {
				setVisible (false);	//Hide the Frame.
				dispose();            	//Free the System Resources.
				System.out.println ("Thanks for Using BankingSystem");
				System.exit (0);        //Close the Application.
			}
			else if (reply == JOptionPane.NO_OPTION) {
				setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
			}
		} 

		catch (Exception e) {}

	}

	//Function for Changing the Program's Look.

	public void changeLookAndFeel (int val) {

		try {
			UIManager.setLookAndFeel (looks[val].getClassName());
			SwingUtilities.updateComponentTreeUI (this);
		}
		catch (Exception e) { }

	}

	//Loop Through All the Opened JInternalFrame to Perform the Task.

	private boolean openChildWindow (String title) {

		JInternalFrame[] childs = desktop.getAllFrames ();
		for (int i = 0; i < childs.length; i++) {
			if (childs[i].getTitle().equalsIgnoreCase (title)) {
				childs[i].show ();
				return true;
			}
		}
		return false;

	}


}


