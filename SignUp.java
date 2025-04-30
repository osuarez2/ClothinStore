import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JFrame;
import javax.swing.JButton;
import java.sql.*;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;




public class SignUp {


	
	private JFrame frame;
	private JTextField userNameTF;
	private JTextField userEmailTF;
	private JTextField userPasswordTF;
	private JTextField userAddressTF;
	
	public SignUp() { initialize(); 
	
	frame.setVisible(true);
	
	}
	
	
	private void initialize() {
		frame = new JFrame();
		frame.setTitle("Sign Up");
		frame.setBounds(100, 300, 1000, 600);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		createSigningUpButton(); 
		createUserNameLBL(); 
		createUserNameTF(); 
		createUserEmailLBL(); 
		createUserEmailTF(); 
		createUserPasswordLBL(); 
		createUserPasswordTF(); 
		createUserAddressLBL(); 
		createUserAddressTF(); 
		createSignInLBL();
		createSignInButton();
	
	}
	

	
	public void createUserNameLBL() {
		
		
		JLabel userNameLBL = new JLabel("Name");
		userNameLBL.setBounds(30, 30, 100, 25);
		frame.getContentPane().add(userNameLBL);
		
		}
		
	public void createUserNameTF() {
		
		userNameTF = new JTextField();
		userNameTF.setBounds(140, 30, 200, 25);
		frame.getContentPane().add(userNameTF);
		userNameTF.setColumns(10);
		
		}
		
	
	public void createUserEmailLBL() {
	
	
	JLabel userEmailLBL = new JLabel("Email");
	userEmailLBL.setBounds(30, 70, 100, 25);
	frame.getContentPane().add(userEmailLBL);
	
	}
	
	public void createUserEmailTF() {
	
	userEmailTF = new JTextField();
	userEmailTF.setBounds(140, 70, 200, 25);
	frame.getContentPane().add(userEmailTF);
	userEmailTF.setColumns(10);
	
	}
	
	public void createUserPasswordLBL() {
	
	JLabel userPasswordLBL = new JLabel("Password");
	userPasswordLBL.setBounds(30, 110, 100, 25);
	frame.getContentPane().add(userPasswordLBL);
	
	}
	
	public void createUserPasswordTF () {
	
	userPasswordTF = new JTextField();
	userPasswordTF.setBounds(140, 110, 200, 25);
	frame.getContentPane().add(userPasswordTF);
	userPasswordTF.setColumns(10);
	

	}
	
	public void createUserAddressLBL() {
		
		JLabel userAddressLBL = new JLabel("Address");
		userAddressLBL.setBounds(30, 150, 100, 25);
		frame.getContentPane().add(userAddressLBL);
		
		}
		
		public void createUserAddressTF () {
		
		userAddressTF = new JTextField();
		userAddressTF.setBounds(140, 150, 200, 25);
		frame.getContentPane().add(userAddressTF);
		userAddressTF.setColumns(10);
		

		}
	
	

	public void createSigningUpButton() {
		JButton signUpButton = new JButton("Sign Up");
		signUpButton.setBounds(140, 190, 100, 30);
		frame.getContentPane().add(signUpButton);
		signUpButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Put code here that you want to run when this button is clicked
				signUp(); // Adds the new patient to the database when the button is clicked
			
			}
		});
	}
	
	public void createSignInLBL() {
		
		
		JLabel signInLBL = new JLabel("Have an account?");
		signInLBL.setBounds(10, 290, 100, 25);
		frame.getContentPane().add(signInLBL);
		
		}
		
	
	
	public void createSignInButton() {
		JButton signInButton = new JButton("Sign In Here!");
		signInButton.setBounds(10, 300, 117, 29);
		frame.getContentPane().add(signInButton);
		signInButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Put code here that you want to run when this button is clicked
				goToSignInPage(); // Adds the new patient to the database when the button is clicked
			}
		});
	}
	
	
	
	public void signUp() {
	    try {
	    	String query = "INSERT INTO users (user_name, user_email, user_password, user_address, userType_id)VALUES (?, ?, ?, ?,?)";
	        PreparedStatement stm = Database.connection.prepareStatement(query);
	        
	        stm.setString(1, userNameTF.getText());
	        stm.setString(2, userEmailTF.getText());
	        stm.setString(3, userPasswordTF.getText());
	        stm.setString(4, userAddressTF.getText());
	        stm.setInt(5, 3);
	        
	    	stm.executeUpdate();
	    	JOptionPane.showMessageDialog(null, "Account Succesfully Created!", "Welcome!", JOptionPane.DEFAULT_OPTION);
	    	
	    	frame.dispose();
	    	new SignIn();
	    	
		} catch (Exception e) {
			System.out.println(e);
		}
	}
	
		
	public void goToSignInPage() {
		frame.dispose();
		new SignIn();
	}		
	
	
}