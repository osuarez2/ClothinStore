import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;


public class AdminDashboard {


	
	private JFrame frame;
	private JComboBox<String> editAcctTypeCB;
	private JComboBox<String> deleteAcctTypeCB;
	
	
	
	public AdminDashboard() { initialize();
	
	}
	
	
	private void initialize() {
		frame = new JFrame();
		frame.setVisible(true);
		frame.setTitle("Dashboard");
		frame.setBounds(100, 400, 1000, 600);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel welcomeLabel = new JLabel("Welcome, " + GlobalSession.getUserName() + "!");
		welcomeLabel.setBounds(55, 5, 300, 25);
		frame.getContentPane().add(welcomeLabel);

		
		
		
		

		
		createAddEmployeeAcctButton(); 
		
		createViewOrdersButton();
		
		createEditAcctsLBL(); 
		
		createEditAcctsComboBox(); 
		
		createDeleteAcctsLBL(); 
		
		createDeleteAcctsComboBox(); 
		
		createSignOutButton();
		
	
		
	}
	
	
	
	public void createAddEmployeeAcctButton() {
		JButton addEmployeeButton = new JButton("Add Employee");
		addEmployeeButton.setBounds(55, 30, 140,30);
		frame.getContentPane().add(addEmployeeButton);
		addEmployeeButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Put code here that you want to run when this button is clicked
				goToAddNewEmployeePage(); // Adds the new patient to the database when the button is clicked
			}
		});
	}
	
	
	public void createViewOrdersButton() {
		JButton viewOrdersButton = new JButton("View Orders Placed");
		viewOrdersButton.setBounds(55, 220,160,30);
		frame.getContentPane().add(viewOrdersButton);
		viewOrdersButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Put code here that you want to run when this button is clicked
				goToOrdersPage(); // Adds the new patient to the database when the button is clicked
			}
		});
	}
	
	public void createEditAcctsLBL() {
	
	JLabel editLabel = new JLabel("Edit Account Type:");
	editLabel.setBounds(55, 90, 150, 16);
	frame.getContentPane().add(editLabel);

	}
	
	public void createEditAcctsComboBox() {
		editAcctTypeCB = new JComboBox<String>();
		editAcctTypeCB.setBounds(55, 110, 157, 27);
		editAcctTypeCB.addItem("Customer Account");
		editAcctTypeCB.addItem("Employee Account");
		
		editAcctTypeCB.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String selected = (String) editAcctTypeCB.getSelectedItem();

				if (selected.equals("Customer Account")) {
					frame.dispose();
					new EditCustomerAcct();
				} else if (selected.equals("Employee Account")) {
					frame.dispose();
					new EditEmployeeAcct();
				}
			}
		});

		frame.getContentPane().add(editAcctTypeCB);
	}
	
	
	public void createDeleteAcctsLBL() {
	JLabel deleteLabel = new JLabel("Delete Account Type:");
	deleteLabel.setBounds(55, 150, 150, 16);
	frame.getContentPane().add(deleteLabel);
	
	}
	
	
	
	public void createDeleteAcctsComboBox() {
		deleteAcctTypeCB = new JComboBox<String>();
		deleteAcctTypeCB.setBounds(55, 180, 157, 27);
		deleteAcctTypeCB.addItem("Customer Account");
		deleteAcctTypeCB.addItem("Employee Account");
		
		deleteAcctTypeCB.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String selected = (String) deleteAcctTypeCB.getSelectedItem();

				if (selected.equals("Customer Account")) {
					frame.dispose();
					new DeleteCustomerAcct();
				} else if (selected.equals("Employee Account")) {
					frame.dispose();
					new DeleteEmployeeAcct();
				}
			}
		});

		frame.getContentPane().add(deleteAcctTypeCB);
	}
	
	
	public void createSignOutButton() {
		JButton addSignOutButton = new JButton("Sign Out");
		addSignOutButton.setBounds(55, 260, 100, 30);
		frame.getContentPane().add(addSignOutButton);
		addSignOutButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				goToSignIn();
				
			}
			
		
			
		});
	}
	
	

	
	
	public void goToAddNewEmployeePage() {
		frame.dispose();
		new AddNewEmployee();
	}
	
	public void goToOrdersPage() {
		frame.dispose();
		new Orders();
	}
	public void goToSignIn() {
	    // Clear the session
	    GlobalSession.setUser(0, "Guest", "");
	    frame.dispose();
	    new SignIn();
	}	

	
}