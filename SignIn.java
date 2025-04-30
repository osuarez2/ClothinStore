import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JFrame;
import javax.swing.JButton;
import java.sql.*;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;


public class SignIn {

	
	private JFrame frame;
	private JTextField emailTF;
	private JTextField passwordTF;
	
	public SignIn() { initialize(); 
	
	frame.setVisible(true);
	
	}
	
	
	
	private void initialize() {
		frame = new JFrame();
		frame.setTitle("Sign In");
		frame.setBounds(100, 300, 1000, 600);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		
		createSignUpLBL();
		createSignUpButton() ;
		createSigningInButton(); 
		createEmailLBL(); 
		createEmailTF(); 
		createPasswordLBL(); 
		createPasswordTF();
	
	}
	

	public void createSignUpLBL() {
	
	
	JLabel signUpLBL = new JLabel("Don't have an account?");
	signUpLBL.setBounds(10, 250, 200, 25);
	frame.getContentPane().add(signUpLBL);
	
	}
	
	
	public void createSignUpButton() {
		JButton signUpButton = new JButton("Sign Up Here!");
		signUpButton.setBounds(10, 280, 150, 29);
		frame.getContentPane().add(signUpButton);
		signUpButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Put code here that you want to run when this button is clicked
				goToSignUpPage(); // Adds the new patient to the database when the button is clicked
			}
		});
	}
	
	public void createSigningInButton() {
		JButton signInButton = new JButton("Sign In");
		signInButton.setBounds(83, 238, 117, 29);
		frame.getContentPane().add(signInButton);
		signInButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Put code here that you want to run when this button is clicked
				signIn(); // Adds the new patient to the database when the button is clicked
			}
		});
	}

	
	
	public void createEmailLBL() {
	
	
	JLabel emailLBL = new JLabel("Email");
	emailLBL.setBounds(30, 30, 100, 25);
	frame.getContentPane().add(emailLBL);
	
	}
	
	public void createEmailTF() {
	
	emailTF = new JTextField();
	emailTF.setBounds(140, 30, 200, 25);
	frame.getContentPane().add(emailTF);
	emailTF.setColumns(10);
	
	}
	
	public void createPasswordLBL() {
	
	JLabel passwordLBL = new JLabel("Password");
	passwordLBL.setBounds(30, 70, 100, 25);
	frame.getContentPane().add(passwordLBL);
	
	}
	
	public void createPasswordTF () {
	
	passwordTF = new JTextField();
	passwordTF.setBounds(140, 70, 200, 25);
	frame.getContentPane().add(passwordTF);
	passwordTF.setColumns(10);
	

	}
	
	public void signIn() {
	    try {
	        String query = "SELECT * FROM users WHERE user_email = ? AND user_password = ?";
	        PreparedStatement stm = Database.connection.prepareStatement(query);
	        
	        stm.setString(1, emailTF.getText());
	        stm.setString(2, passwordTF.getText());
	        
	        ResultSet rs = stm.executeQuery();
	        
	        if (rs.next()) {
	        	int userId = rs.getInt("user_id");
	            String userName = rs.getString("user_name");
	            String userEmail = rs.getString("user_email");
	            int userType = rs.getInt("userType_id");
	            boolean passwordResetRequired = rs.getBoolean("password_reset_required");
	            
	            GlobalSession.setUser(userId, userName, userEmail);
	           
	            
	            JOptionPane.showMessageDialog(null, "Welcome back, " + userName + "!", "Sign In Successful", JOptionPane.INFORMATION_MESSAGE);
	            
	            
	            frame.dispose(); // Close login window

	            if (userType == 2 && passwordResetRequired) {
	                // Employee must reset password
	                new ResetPassword(userEmail);
	            } else {
	                // Normal behavior
	                if (userType == 1) {
	                    new AdminDashboard();
	                } else if (userType == 2) {
	                    new EmployeeDashboard();
	                } else if (userType == 3) {
	                    new storeMainMenu();
	                } else {
	                    JOptionPane.showMessageDialog(null, "Unknown user type.", "Error", JOptionPane.ERROR_MESSAGE);
	                }
	            }

	            
	            
	        } else {
	            
	            JOptionPane.showMessageDialog(null, "Incorrect email or password.", "Sign In Failed", JOptionPane.ERROR_MESSAGE);
	        }
	        
	    } catch (SQLException e) {
	        e.printStackTrace();
	        JOptionPane.showMessageDialog(null, "Error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
	    }
	}
	

	public void goToSignUpPage() {
		frame.dispose();
		new SignUp();
	}		
	
}
	
	
	
