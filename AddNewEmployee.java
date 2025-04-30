import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JFrame;
import javax.swing.JButton;
import java.sql.*;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;


public class AddNewEmployee {


	
	private JFrame frame;
	private JTextField employeeNameTF;
	private JTextField employeeEmailTF;
	private JTextField employeePasswordTF;

	
	public AddNewEmployee() { initialize(); 
	
	frame.setVisible(true);
	
	}
	
	
	
	private void initialize() {
		frame = new JFrame();
		frame.setTitle("New Employee");
		frame.setBounds(100, 300, 1000, 600);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		createAddEmployeeButton(); 
		createBackToDashboardButton(); 
		
		createEmployeeNameLBL(); 
		createEmployeeNameTF(); 
		createEmployeeEmailLBL(); 
		createEmployeeEmailTF(); 
		createEmployeePasswordLBL(); 
		createEmployeePasswordTF(); 
		
	}
	
	
	
	public void createEmployeeNameLBL() {
		
		
		JLabel employeeNameLBL = new JLabel("Name");
		employeeNameLBL.setBounds(30, 30, 100, 25);
		frame.getContentPane().add(employeeNameLBL);
		
		}
		
	public void createEmployeeNameTF() {
		
		employeeNameTF = new JTextField();
		employeeNameTF.setBounds(140, 30, 200, 25);
		frame.getContentPane().add(employeeNameTF);
		employeeNameTF.setColumns(10);
		
		}
		
		
	
	public void createEmployeeEmailLBL() {
	
	
	JLabel employeeEmailLBL = new JLabel("Email");
	employeeEmailLBL.setBounds(30, 70, 100, 25);
	frame.getContentPane().add(employeeEmailLBL);
	
	}
	
	public void createEmployeeEmailTF() {
	
	employeeEmailTF = new JTextField();
	employeeEmailTF.setBounds(140, 70, 200, 25);
	frame.getContentPane().add(employeeEmailTF);
	employeeEmailTF.setColumns(10);
	
	}
	
	public void createEmployeePasswordLBL() {
	
	JLabel employeePasswordLBL = new JLabel("Password");
	employeePasswordLBL.setBounds(30, 110, 100, 25);
	frame.getContentPane().add(employeePasswordLBL);
	
	}
	
	public void createEmployeePasswordTF () {
	
	employeePasswordTF = new JTextField();
	employeePasswordTF.setBounds(140, 110, 200, 25);
	frame.getContentPane().add(employeePasswordTF);
	employeePasswordTF.setColumns(10);
	

	}
	
	
	public void createAddEmployeeButton() {
		JButton addEmployeeButton = new JButton("Add Employee");
		addEmployeeButton.setBounds(140, 150, 100, 60);
		frame.getContentPane().add(addEmployeeButton);
		addEmployeeButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				addEmployee();
				
			}
			
			
			
			
			
		});
	}
	
	
	
	
	
	public void addEmployee() {
	    try {
	    	String query = "INSERT INTO users (user_name, user_email, user_password, userType_id)VALUES (?, ?, ?, ?)";
	        PreparedStatement stm = Database.connection.prepareStatement(query);
	        
	        stm.setString(1, employeeNameTF.getText());
	        stm.setString(2, employeeEmailTF.getText());
	        stm.setString(3, employeePasswordTF.getText());
	        stm.setInt(4, 2); //hardcoded for now
	    	stm.executeUpdate();
	    	JOptionPane.showMessageDialog(null, "Account Succesfully Created!", "Welcome!", JOptionPane.DEFAULT_OPTION);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	public void createBackToDashboardButton() {
		JButton backButton = new JButton("Back To Dashboard");
		backButton.setBounds(55, 240,160,30);
		frame.getContentPane().add(backButton);
		backButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				goToAdminDashboard(); 
			}
		});
	}
	
	
	public void goToAdminDashboard() {
		frame.dispose();
		new AdminDashboard();
	}		
	
	
	
	
}